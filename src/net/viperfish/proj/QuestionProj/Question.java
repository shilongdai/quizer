package net.viperfish.proj.QuestionProj;

import java.io.Serializable;

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
	
	public void setQuestion(String toSet) {
		questionContent = new String(toSet); 
	}

	public void setAnswer(String toSet) {
		answer = new String(toSet);
	}
	
	public void setNumber(int num) {
		this.num = num;
	}
	
	public String getAnswer() {
		return new String(answer);
	}
	
	public String getQuestion() {
		return new String(questionContent);
	}
	
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
