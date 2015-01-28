package net.viperfish.proj.QuestionUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.viperfish.proj.QuestionProj.Question;
import net.viperfish.proj.QuestionProj.Quizer;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ModifyQuestionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblQuestion;
	private JTextArea textArea;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isQuestionSet;
	private Quizer mQuizer;
	private Question modify;
	private Question changer;

	/**
	 * Create the dialog.
	 */
	public ModifyQuestionDialog(Quizer q, MainUI t) {
		isQuestionSet = false;
		mQuizer = q;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));
		{
			lblQuestion = new JLabel("Question:");
			contentPanel.add(lblQuestion, "2, 2");
		}
		{
			textArea = new JTextArea();
			contentPanel.add(textArea, "2, 4, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Next");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						changer = new Question();
						if (!isQuestionSet) {
							changer.setQuestion(textArea.getText());
							isQuestionSet = true;
							textArea.setText(modify.getAnswer());
							lblQuestion.setText("Answer");
							okButton.setText("Done");

						} else {
							changer.setAnswer(textArea.getText());
							isQuestionSet = false;
							textArea.setText("");
							lblQuestion.setText("Question");
							okButton.setText("Next");
							mQuizer.modifyQuestion(changer);
							setVisible(false);
						}

					}

				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						isQuestionSet = false;
						textArea.setText("");
						lblQuestion.setText("Question");
						okButton.setText("Next");
						setVisible(false);
					}

				});
				buttonPane.add(cancelButton);
			}
		}
	}

	public void setQuestion(Question q) {
		modify = q;
		textArea.setText(q.getQuestion());
	}

}
