package net.viperfish.proj.QuestionUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DisplayScoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private double toDisplay;
	private JLabel lblYourScoreIs;

	public void setScore(double s) {
		toDisplay = s;
		lblYourScoreIs.setText("Your Score Is:" + toDisplay);
	}

	/**
	 * Create the dialog.
	 */
	public DisplayScoreDialog() {
		setBounds(100, 100, 450, 149);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblYourScoreIs = new JLabel("Your Score Is:");
			contentPanel.add(lblYourScoreIs);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);
					}

				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
