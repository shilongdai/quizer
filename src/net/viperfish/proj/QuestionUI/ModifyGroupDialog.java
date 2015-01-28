package net.viperfish.proj.QuestionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.viperfish.proj.QuestionProj.Quizer;

public class ModifyGroupDialog extends ModifyQuestionContainerDialog {
	public ModifyGroupDialog(Quizer q, final MainUI t) {
		mQuizer = q;
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mQuizer.renameGroup(textField.getText());
				t.refreshTree();
				textField.setText("");
			}

		});
	}
}
