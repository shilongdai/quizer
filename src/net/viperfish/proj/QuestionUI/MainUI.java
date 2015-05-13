package net.viperfish.proj.QuestionUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import net.viperfish.proj.QuestionProj.Question;
import net.viperfish.proj.QuestionProj.Quizer;

public class MainUI {

	private JFrame frmQuizer;
	private final JButton btnNewButton = new JButton("Add");
	private Quizer mBank;
	private SaveActionListener mSaveAction;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModal;
	private AddCategoryDialog mAddCategory;
	private AddGroupDialog mAddGroupDialog;
	private QuestionAddDialog mAddQuestion;
	private SelectListener mSelecter;
	private SessionDialog mAnsweringDialog;
	private ModifyCategoryDialog modifyCategory;
	private ModifyGroupDialog modifyGroup;
	private ModifyQuestionDialog modifyQuestion;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e1) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmQuizer.setVisible(true);
					window.frmQuizer
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		mBank = new Quizer();
		try {
			mBank.load();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"Failed to Load QuestionBank, exiting");
			System.exit(1);
		}

		mSaveAction = new SaveActionListener(mBank);
		root = new DefaultMutableTreeNode("QuestionBank");
		treeModal = new DefaultTreeModel(root);
		mSelecter = new SelectListener(mBank);
		mAddCategory = new AddCategoryDialog(mBank, this);
		mAddGroupDialog = new AddGroupDialog(mBank, this);
		mAddQuestion = new QuestionAddDialog(mBank, this);
		mAnsweringDialog = new SessionDialog(mBank);
		modifyCategory = new ModifyCategoryDialog(mBank, this);
		modifyGroup = new ModifyGroupDialog(mBank, this);
		modifyQuestion = new ModifyQuestionDialog(mBank, this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmQuizer = new JFrame();
		frmQuizer.setTitle("Quizer");
		frmQuizer.setBounds(100, 100, 450, 480);
		frmQuizer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frmQuizer.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnStart = new JButton("Start");
		btnStart.setToolTipText("Start a session");
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (mBank.isGroupSelected() && !mBank.isQuestionSelected()) {
					mBank.startGroupSession();
				} else if (mBank.isCategorySelected()
						&& !mBank.isGroupSelected()) {
					mBank.startCategorySession();
				} else {
					return;
				}
				if (mBank.done()) {
					return;
				} else {
					mAnsweringDialog.reset();
					mAnsweringDialog.setVisible(true);
				}
			}

		});
		panel.add(btnStart);

		JToolBar toolBar = new JToolBar();
		frmQuizer.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnSave = new JButton("Save");
		// add action
		btnSave.addActionListener(mSaveAction);

		toolBar.add(btnSave);
		toolBar.add(btnNewButton);
		btnNewButton.setToolTipText("Add content");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (mBank.isCategorySelected() && !mBank.isGroupSelected()) {
					mBank.deleteCategory();
				} else if (mBank.isGroupSelected()
						&& !mBank.isQuestionSelected()) {
					mBank.deleteGroup();
				} else if (mBank.isQuestionSelected()) {
					mBank.deleteQuestion();
				}
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						refreshTree();
					}

				});
			}

		});
		toolBar.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		frmQuizer.getContentPane().add(scrollPane, BorderLayout.CENTER);

		initTree();
		JTree tree = new JTree(treeModal);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(mSelecter);
		scrollPane.setViewportView(tree);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!mBank.isCategorySelected()) {
					mAddCategory.setVisible(true);
				} else if (mBank.isCategorySelected()
						&& !mBank.isGroupSelected()) {
					mAddGroupDialog.setVisible(true);
				} else if (mBank.isGroupSelected()
						&& !mBank.isQuestionSelected()) {
					mAddQuestion.setVisible(true);
				}
			}
		});

	}

	public void refreshTree() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				root = new DefaultMutableTreeNode("Question Bank");
				initTree();
				treeModal.setRoot(root);
				mBank.reset();
			}

		});
	}

	private void initTree() {
		Collection<String> tempCate = mBank.getAllCategoryName();
		Collection<String> tempGroup;
		Collection<Question> tempQuestion;
		DefaultMutableTreeNode tempCategoryNode;
		DefaultMutableTreeNode tempGroupNode;
		DefaultMutableTreeNode tempQuestionNode;
		QuestionDisplay q;
		for (String i : tempCate) {
			mBank.selectCategory(i);
			tempGroup = mBank.getAllGroupName();
			tempCategoryNode = new DefaultMutableTreeNode(i);
			tempCategoryNode.setUserObject(i);
			root.add(tempCategoryNode);
			for (String j : tempGroup) {
				mBank.selectGroup(j);
				tempQuestion = mBank.getAllQuestion();
				tempGroupNode = new DefaultMutableTreeNode(j);
				tempGroupNode.setUserObject(j);
				tempCategoryNode.add(tempGroupNode);
				for (Question k : tempQuestion) {
					q = new QuestionDisplay(k);
					tempQuestionNode = new DefaultMutableTreeNode(q);
					tempQuestionNode.setUserObject(q);
					tempGroupNode.add(tempQuestionNode);

				}
			}
		}
	}
}
