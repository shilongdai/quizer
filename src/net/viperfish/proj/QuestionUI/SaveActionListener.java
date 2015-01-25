package net.viperfish.proj.QuestionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.viperfish.proj.QuestionProj.Quizer;

public class SaveActionListener implements ActionListener {

	protected Quizer mTarget;
	protected String error;
	protected boolean isError;

	public SaveActionListener(Quizer target) {
		mTarget = target;
		error = new String();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mTarget.save();
	}

	public boolean success() {
		return isError;
	}

	public String getError() {
		return error;
	}

}
