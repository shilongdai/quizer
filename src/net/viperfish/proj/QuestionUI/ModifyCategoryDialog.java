package net.viperfish.proj.QuestionUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.viperfish.proj.QuestionProj.Quizer;

public class ModifyCategoryDialog extends ModifyQuestionContainerDialog {
	public ModifyCategoryDialog(Quizer q, final MainUI tree) {
		mQuizer = q;
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mQuizer.renameCategory(textField.getText());
				tree.refreshTree();
				textField.setText("");
				setVisible(false);
			}

		});
	}
}
