package net.viperfish.proj.QuestionProj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * a collection of category, groups and questions
 * 
 * @author sdai
 *
 */
public class QuestionBank implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -292933982620484738L;
	protected TreeMap<String, Category> storage;

	public QuestionBank() {
		storage = new TreeMap<String, Category>();
	}

	public QuestionBank(QuestionBank src) {
		this.storage = new TreeMap<String, Category>(src.storage);
	}

	/**
	 * add a category
	 * 
	 * @param name
	 *            the name of the category
	 */
	public void addCategory(String name) {
		storage.put(name, new Category(name));
	}

	/**
	 * add a category
	 * 
	 * @param toAdd
	 *            the category to add
	 */
	public void addCategory(Category toAdd) {
		storage.put(toAdd.getName(), toAdd);
	}

	/**
	 * delete a category
	 * 
	 * @param name
	 *            the name of the category to delete
	 */
	public void deleteCategory(String name) {
		storage.remove(name);
	}

	/**
	 * get a category of question
	 * 
	 * @param name
	 *            the name of a category to get
	 * @return the category found, null if not found
	 */
	public Category getCategory(String name) {
		return storage.get(name);
	}

	/**
	 * get all the category in this question bank
	 * 
	 * @return
	 */
	public Collection<Category> getAll() {
		LinkedList<Category> temp = new LinkedList<Category>();
		for (Map.Entry<String, Category> i : storage.entrySet()) {
			temp.add(i.getValue());
		}
		return temp;
	}

	/**
	 * save a question bank to file "QuestionBank"
	 * 
	 * @param toSave
	 *            the QuestionBank to be saved
	 * @throws IOException
	 *             if failed to write to disk
	 */
	public static void save(QuestionBank toSave) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(
				new FileOutputStream("QuestionBank")));
		try {
			out.writeObject(toSave);
			out.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}

	}

	/**
	 * load a QuestionBank from file "QuestionBank"
	 * 
	 * @return a loaded QuestionBank
	 * @throws IOException
	 *             if failed to read from file
	 * @throws ClassNotFoundException
	 *             if the file does not contain a QuestionBank
	 */
	public static QuestionBank load() throws IOException,
			ClassNotFoundException {
		if (!new File("QuestionBank").exists()) {
			return new QuestionBank();
		}
		ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(
				new FileInputStream("QuestionBank")));
		try {
			return (QuestionBank) in.readObject();
		} finally {
			in.close();
		}
	}
}