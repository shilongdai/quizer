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

public class SessionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblCorrectOrNot;
	private JLabel lblQuestion;
	private JTextArea txtrTheQuestion;
	private JLabel lblYourAnswer;
	private JButton okButton;
	private JButton cancelButton;
	private Quizer mQuizer;
	private Question current;
	private JTextArea textArea;
	private JButton btnY;
	private JButton btnN;
	private DisplayScoreDialog scoreBoard;
	private boolean answering = true;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * Create the dialog.
	 */
	public SessionDialog(Quizer q) {
		mQuizer = q;
		scoreBoard = new DisplayScoreDialog();
		setBounds(100, 100, 450, 410);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(75dlu;default)"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(70dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, }));
		{
		}
		{
			{
				lblCorrectOrNot = new JLabel("Correct or Not");
				contentPanel.add(lblCorrectOrNot, "2, 2");
			}
		}
		btnY = new JButton("Y");
		btnY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mQuizer.incrementScore();
				btnY.setEnabled(false);
				btnN.setEnabled(false);
			}

		});
		contentPanel.add(btnY, "3, 2");
		btnN = new JButton("N");
		btnN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				btnY.setEnabled(false);
				btnN.setEnabled(false);
			}

		});
		contentPanel.add(btnN, "4, 2");
		{
			lblQuestion = new JLabel("Question:");
			contentPanel.add(lblQuestion, "2, 4");
		}
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "2, 5, fill, fill");
			{
				txtrTheQuestion = new JTextArea();
				txtrTheQuestion.setLineWrap(true);
				scrollPane.setViewportView(txtrTheQuestion);
				txtrTheQuestion.setText("The question");
				txtrTheQuestion.setEditable(false);
			}
		}
		{
			lblYourAnswer = new JLabel("Your Answer:");
			contentPanel.add(lblYourAnswer, "2, 6");
		}
		{
			scrollPane_1 = new JScrollPane();
			contentPanel.add(scrollPane_1, "2, 7, fill, fill");
			{
				textArea = new JTextArea();
				textArea.setLineWrap(true);
				scrollPane_1.setViewportView(textArea);
				textArea.setEditable(true);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						boolean automaticScore = false;
						double temp, score, size;
						String userAnswer;
						if (answering) {
							toDisplayAnswer();
							userAnswer = textArea.getText();
							automaticScore = mQuizer.answer(userAnswer);
							if (automaticScore) {
								EventQueue.invokeLater(new Runnable() {

									@Override
									public void run() {
										lblCorrectOrNot.setText("Correct");
										btnY.setVisible(false);
										btnN.setVisible(false);
									}

								});
							}
							answering = false;
						} else if (!answering) {

							if (mQuizer.done()) {

								size = mQuizer.getSessionSize();
								score = mQuizer.getScore();
								temp = (score / size) * 100;
								scoreBoard.setScore(temp);
								EventQueue.invokeLater(new Runnable() {

									@Override
									public void run() {
										setVisible(false);
										scoreBoard.setVisible(true);
									}

								});
							} else {
								reset();
							}
							answering = true;
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
					public void actionPerformed(ActionEvent e) {
						setVisible(false);

					}

				});
				buttonPane.add(cancelButton);
			}
		}
	}

	public void reset() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				scoreBoard.setVisible(false);
				answering = true;
				current = mQuizer.nextQuestion();
				txtrTheQuestion.setText(current.getQuestion());
				textArea.setText("");
				textArea.setText("");
				lblCorrectOrNot.setVisible(false);
				btnY.setVisible(false);
				btnN.setVisible(false);
				okButton.setText("OK");
				cancelButton.setText("Cancel");
				lblQuestion.setText("Question");
				lblYourAnswer.setText("Your Answer");
			}

		});
	}

	private void toDisplayAnswer() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				lblQuestion.setText("Correct Answer");
				txtrTheQuestion.setText(current.getAnswer());
				okButton.setText("Next");
				lblCorrectOrNot.setVisible(true);
				btnY.setVisible(true);
				btnY.setEnabled(true);
				btnN.setVisible(true);
				btnN.setEnabled(true);
			}

		});
	}
}
