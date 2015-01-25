package net.viperfish.proj.QuestionProj;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Quizer {
	protected Object lock;
	protected ExecutorService threadPool;
	protected Question currentQuestion;
	protected Question currentQuizQuestion;
	protected Group currentGroup;
	protected Category currentCategory;
	protected LinkedList<Question> sessionQuestions;
	protected int score;
	protected QuestionBank mBank;
	protected int sessionSize;

	public Quizer() {
		currentQuestion = null;
		currentQuizQuestion = null;
		currentGroup = null;
		currentCategory = null;
		sessionQuestions = null;
		score = 0;
		mBank = null;
		lock = new Object();
		threadPool = Executors.newCachedThreadPool();
	}

	protected class QuestionBankWriter implements Runnable {

		@Override
		public void run() {

			synchronized (lock) {
				try {
					QuestionBank.save(new QuestionBank(mBank));
				} catch (IOException e) {
					return;
				}
			}

		}

	}

	public void load() throws ClassNotFoundException, IOException {
		mBank = QuestionBank.load();
	}

	public void save() {
		threadPool.execute(new QuestionBankWriter());
	}

	public void dispose() {
		save();
		threadPool.shutdown();
	}

	public boolean done() {
		if (currentQuizQuestion == null) {
			return true;
		} else {
			return false;
		}
	}

	public void selectCategory(String name) {
		currentCategory = mBank.getCategory(name);
		currentGroup = null;
		currentQuestion = null;
	}

	public void selectGroup(String name) {
		currentGroup = currentCategory.getGroup(name);
		currentQuestion = null;
	}

	public void selectQuestion(int num) {
		currentQuestion = currentGroup.get(num);
	}

	public void renameCategory(String newName) {
		Category temp = new Category(currentCategory);
		temp.setName(newName);
		mBank.deleteCategory(currentCategory.getName());
		mBank.addCategory(temp);
	}

	public void renameGroup(String newName) {
		Group temp = new Group(currentGroup);
		temp.setName(newName);
		currentCategory.deleteGroup(currentGroup.getName());
		currentCategory.addGroup(temp);
	}

	public void modifyQuestion(Question newQuestion) {
		currentGroup.delete(currentQuestion.getNum());
		currentGroup.add(newQuestion);
	}

	public void deleteCategory() {
		mBank.deleteCategory(currentCategory.getName());
		currentCategory = null;
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	public void deleteGroup() {
		currentCategory.deleteGroup(currentGroup.getName());
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	public void deleteQuestion() {
		currentGroup.delete(currentQuestion.getNum());
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	public Collection<String> getAllCategoryName() {
		LinkedList<String> temp = new LinkedList<String>();
		Collection<Category> allCate = mBank.getAll();
		for (Category i : allCate) {
			temp.add(i.getName());
		}
		return temp;
	}

	public Collection<String> getAllGroupName() {
		LinkedList<String> temp = new LinkedList<String>();
		Collection<Group> allGroup = currentCategory.getAll();
		for (Group i : allGroup) {
			temp.add(i.getName());
		}
		return temp;
	}

	public Collection<Question> getAllQuestion() {
		return currentGroup.getAll();
	}

	public boolean isCategorySelected() {
		if (currentCategory == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isGroupSelected() {
		if (currentGroup == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isQuestionSelected() {
		if (currentQuestion == null) {
			return false;
		} else {
			return true;
		}
	}

	public void reset() {
		currentCategory = null;
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	public int getSessionSize() {
		return sessionSize;
	}

	public void startGroupSession() {
		score = 0;
		sessionQuestions = (LinkedList<Question>) getAllQuestion();
		sessionSize = sessionQuestions.size();
		currentQuizQuestion = sessionQuestions.getFirst();
	}

	public void startCategorySession() {
		score = 0;
		sessionQuestions = new LinkedList<Question>();
		Collection<Group> temp = currentCategory.getAll();
		LinkedList<Question> tempQuestion;
		for (Group i : temp) {
			tempQuestion = (LinkedList<Question>) i.getAll();
			Collections.shuffle(tempQuestion);
			if (tempQuestion.size() > 5) {
				for (int j = 0; j < 5; j++) {
					sessionQuestions.add(tempQuestion.get(j));
				}
			} else {
				sessionQuestions.addAll(tempQuestion);
			}
		}
		sessionSize = sessionQuestions.size();
		currentQuizQuestion = sessionQuestions.getFirst();
	}

	public Question nextQuestion() {
		return new Question(currentQuizQuestion);
	}

	public void incrementScore() {
		++score;
	}

	public boolean answer(String answer) {
		if (answer == currentQuizQuestion.getAnswer()) {
			++score;
			sessionQuestions.pop();
			currentQuizQuestion = sessionQuestions.getFirst();
			return true;
		} else {
			sessionQuestions.pop();
			if (!sessionQuestions.isEmpty()) {
				currentQuizQuestion = sessionQuestions.getFirst();
			} else {
				currentQuizQuestion = null;
			}
			return false;
		}
	}

	public int getScore() {
		return score;
	}

	public void addCategory(String name) {
		mBank.addCategory(name);
	}

	public void addGroup(String name) {
		currentCategory.addGroup(name);
	}

	public void addQuestion(Question q) {
		currentGroup.add(q);
	}
}
