package net.viperfish.proj.QuestionProj;

import java.io.Serializable;

/**
 * a question
 * 
 * @author sdai
 *
 */
public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8051588718234249219L;
	protected String questionContent;
	protected String answer;
	protected int num;

	public Question() {
		questionContent = new String();
		answer = new String();
	}

	public Question(final Question src) {
		this.questionContent = src.getQuestion();
		this.answer = src.getAnswer();
		this.num = src.getNum();
	}

	public Question(int num) {
		questionContent = new String();
		answer = new String();
		this.num = num;
	}

	/**
	 * set the question for this question
	 * 
	 * @param toSet
	 *            the question
	 */
	public void setQuestion(String toSet) {
		questionContent = new String(toSet);
	}

	/**
	 * set the answer for this question
	 * 
	 * @param toSet
	 *            the answer
	 */
	public void setAnswer(String toSet) {
		answer = new String(toSet);
	}

	/**
	 * set the number for this question
	 * 
	 * @param num
	 *            the number of this question
	 */
	public void setNumber(int num) {
		this.num = num;
	}

	/**
	 * get the answer of this question
	 * 
	 * @return the answer
	 */
	public String getAnswer() {
		return new String(answer);
	}

	/**
	 * get the question for this question
	 * 
	 * @return the question
	 */
	public String getQuestion() {
		return new String(questionContent);
	}

	/**
	 * get the number of this question
	 * 
	 * @return the number
	 */
	public int getNum() {
		return num;
	}

	@Override
	public int hashCode() {
		return answer.hashCode() + questionContent.hashCode() + num;
	}

	@Override
	public String toString() {
		return num + ":" + questionContent + ":" + answer;
	}

}
