package net.viperfish.proj.QuestionUI;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.viperfish.proj.QuestionProj.Quizer;

public class SelectListener implements TreeSelectionListener {

	private Quizer mBank;

	public SelectListener(Quizer bank) {
		mBank = bank;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		TreePath path = arg0.getPath();
		Object[] items = path.getPath();
		DefaultMutableTreeNode[] treeItem = new DefaultMutableTreeNode[items.length];
		for (int i = 0; i < items.length; i++) {
			treeItem[i] = (DefaultMutableTreeNode) items[i];
		}
		switch (treeItem.length) {
		case 0: {
			mBank.reset();
			break;
		}
		case 1: {
			mBank.reset();
			break;
		}
		case 2: {
			mBank.selectCategory(treeItem[1].getUserObject().toString());
			break;
		}
		case 3: {
			mBank.selectCategory(treeItem[1].getUserObject().toString());
			mBank.selectGroup(treeItem[2].getUserObject().toString());
			break;
		}
		case 4: {
			mBank.selectCategory(treeItem[1].getUserObject().toString());
			mBank.selectGroup(treeItem[2].getUserObject().toString());
			mBank.selectQuestion(((QuestionDisplay) treeItem[3].getUserObject())
					.getNum());
			break;
		}
		default: {
			return;
		}
		}
	}
}
