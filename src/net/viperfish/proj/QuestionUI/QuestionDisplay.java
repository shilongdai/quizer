package net.viperfish.proj.QuestionUI;

import net.viperfish.proj.QuestionProj.Question;

public class QuestionDisplay {
	String content;
	int num;

	public QuestionDisplay(Question q) {
		if (q.getQuestion().length() > 25) {
			content = q.getQuestion().substring(0, 25);
		} else {
			content = q.getQuestion();
		}
		num = q.getNum();
	}

	@Override
	public String toString() {
		return content;
	}

	public int getNum() {
		return num;
	}
}
