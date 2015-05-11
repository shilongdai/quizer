package net.viperfish.proj.QuestionProj;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * a player of the quizer program
 * 
 * @author sdai
 *
 */
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

	/**
	 * a writer that will write the question bank for this quizer into file
	 * 
	 * @author sdai
	 *
	 */
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

	/**
	 * load the QuestionBank
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void load() throws ClassNotFoundException, IOException {
		mBank = QuestionBank.load();
	}

	/**
	 * save the QuestionBank
	 */
	public void save() {
		threadPool.execute(new QuestionBankWriter());
	}

	/**
	 * shutdown all the writer thread
	 */
	public void dispose() {
		save();
		threadPool.shutdown();
	}

	/**
	 * is a quiz session done
	 * 
	 * @return whether it's done or not
	 */
	public boolean done() {
		if (currentQuizQuestion == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * select a category from the questionBank
	 * 
	 * @param name
	 *            the name of the category
	 */
	public void selectCategory(String name) {
		currentCategory = mBank.getCategory(name);
		currentGroup = null;
		currentQuestion = null;
	}

	/**
	 * select a group from selected categoey
	 * 
	 * @param name
	 */
	public void selectGroup(String name) {
		currentGroup = currentCategory.getGroup(name);
		currentQuestion = null;
	}

	/**
	 * select a question from a selected group
	 * 
	 * @param num
	 *            the number of question
	 */
	public void selectQuestion(int num) {
		currentQuestion = currentGroup.get(num);
	}

	/**
	 * rename the selected Category
	 * 
	 * @param newName
	 *            the new name
	 */
	public void renameCategory(String newName) {
		Category temp = new Category(currentCategory);
		temp.setName(newName);
		mBank.deleteCategory(currentCategory.getName());
		mBank.addCategory(temp);
	}

	/**
	 * rename the selected group
	 * 
	 * @param newName
	 *            the new name
	 */
	public void renameGroup(String newName) {
		Group temp = new Group(currentGroup);
		temp.setName(newName);
		currentCategory.deleteGroup(currentGroup.getName());
		currentCategory.addGroup(temp);
	}

	/**
	 * replace the selected question
	 * 
	 * @param newQuestion
	 *            the new question
	 */
	public void modifyQuestion(Question newQuestion) {
		currentGroup.delete(currentQuestion.getNum());
		currentGroup.add(newQuestion);
	}

	/**
	 * delete selected category
	 */
	public void deleteCategory() {
		mBank.deleteCategory(currentCategory.getName());
		currentCategory = null;
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	/**
	 * delete the selected group
	 */
	public void deleteGroup() {
		currentCategory.deleteGroup(currentGroup.getName());
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	/**
	 * delete the selected question
	 */
	public void deleteQuestion() {
		currentGroup.delete(currentQuestion.getNum());
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	/**
	 * get a list of all the name of categories in the question bank
	 * 
	 * @return
	 */
	public Collection<String> getAllCategoryName() {
		LinkedList<String> temp = new LinkedList<String>();
		Collection<Category> allCate = mBank.getAll();
		for (Category i : allCate) {
			temp.add(i.getName());
		}
		return temp;
	}

	/**
	 * get all the name of groups in the selected category
	 * 
	 * @return
	 */
	public Collection<String> getAllGroupName() {
		LinkedList<String> temp = new LinkedList<String>();
		Collection<Group> allGroup = currentCategory.getAll();
		for (Group i : allGroup) {
			temp.add(i.getName());
		}
		return temp;
	}

	/**
	 * get all question in selected group
	 * 
	 * @return
	 */
	public Collection<Question> getAllQuestion() {
		return currentGroup.getAll();
	}

	/**
	 * whether a category has been selected
	 * 
	 * @return true for selected, false for not
	 */
	public boolean isCategorySelected() {
		if (currentCategory == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * whether a group has been selected
	 * 
	 * @return true for selected, false for not
	 */
	public boolean isGroupSelected() {
		if (currentGroup == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * whether a question has been selected
	 * 
	 * @return true for selected, false for not
	 */
	public boolean isQuestionSelected() {
		if (currentQuestion == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * unselect and reset everything
	 */
	public void reset() {
		score = 0;
		currentCategory = null;
		currentGroup = null;
		currentQuestion = null;
		currentQuizQuestion = null;
	}

	/**
	 * get the number of question in the current quiz session
	 * 
	 * @return the size
	 */
	public int getSessionSize() {
		return sessionSize;
	}

	/**
	 * start a session, with all questions in a group
	 */
	public void startGroupSession() {
		score = 0;
		sessionQuestions = (LinkedList<Question>) getAllQuestion();
		sessionSize = sessionQuestions.size();
		if (sessionQuestions.isEmpty()) {
			currentQuizQuestion = null;
		} else {
			currentQuizQuestion = sessionQuestions.getFirst();
		}
	}

	/**
	 * start a session, randomly select at most 5 question in each group under
	 * the current category
	 */
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
		if (sessionQuestions.isEmpty()) {
			currentQuizQuestion = null;
		} else {
			currentQuizQuestion = sessionQuestions.getFirst();
		}
	}

	/**
	 * get the next question in the current session
	 * 
	 * @return
	 */
	public Question nextQuestion() {
		return new Question(currentQuizQuestion);
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	/**
	 * increment score by 1
	 */
	public void incrementScore() {
		score = score + 1;
	}

	/**
	 * answer the current question in the session
	 * 
	 * @param answer
	 *            the answer of the question
	 * @return whether is correct or not
	 */

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

	/**
	 * get the current score of the player
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * add a category to the question bank
	 * 
	 * @param name
	 *            the name of the category
	 */
	public void addCategory(String name) {
		mBank.addCategory(name);
	}

	/**
	 * add a group to the selected category
	 * 
	 * @param name
	 *            the name of the group
	 */

	public void addGroup(String name) {
		currentCategory.addGroup(name);
	}

	/**
	 * add a question to the selected group
	 * 
	 * @param q
	 *            the question to be added
	 */
	public void addQuestion(Question q) {
		currentGroup.add(q);
	}
}
