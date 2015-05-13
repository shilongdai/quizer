package net.viperfish.proj.QuestionUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.viperfish.proj.QuestionProj.Question;
import net.viperfish.proj.QuestionProj.Quizer;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class QuestionAddDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9183429434423516283L;
	private final JPanel contentPanel = new JPanel();
	boolean questionSet = false;
	private Quizer mQuizer;
	Question toAdd;
	JTextArea textArea;
	JButton okButton;
	JLabel lblQuestion;
	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public QuestionAddDialog(Quizer q, final MainUI tree) {
		toAdd = new Question();
		mQuizer = q;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));
		{
			lblQuestion = new JLabel("Question:");
			contentPanel.add(lblQuestion, "4, 2");
		}
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "2, 4, 3, 1, fill, fill");
			{
				textArea = new JTextArea();
				textArea.setLineWrap(true);
				scrollPane.setViewportView(textArea);
				textArea.setEditable(true);
			}
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
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								// TODO Auto-generated method stub
								if (!questionSet) {
									toAdd.setQuestion(textArea.getText());
									questionSet = true;
									textArea.setText("");
									lblQuestion.setText("Answer:");
									okButton.setText("Done");
								} else {
									toAdd.setAnswer(textArea.getText());
									questionSet = false;
									textArea.setText("");
									lblQuestion.setText("Question:");
									okButton.setText("Next");
									mQuizer.addQuestion(toAdd);
									tree.refreshTree();
									setVisible(false);
									toAdd = new Question();
								}
							}

						});

					}

				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						textArea.setText("");
						lblQuestion.setText("Question:");
						okButton.setText("Next");
						setVisible(false);
					}

				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
