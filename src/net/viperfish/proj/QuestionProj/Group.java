package net.viperfish.proj.QuestionProj;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * a group of questions under a category
 * 
 * @author sdai
 *
 */
public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716041486772357383L;
	protected Map<Integer, Question> storage;
	protected String name;

	public Group() {
		storage = new TreeMap<Integer, Question>();
	}

	public Group(String name) {
		storage = new TreeMap<Integer, Question>();
		this.name = new String(name);
	}

	public Group(final Group src) {
		storage = new TreeMap<Integer, Question>(src.storage);
		name = new String(src.name);
	}

	/**
	 * add a question to this group
	 * 
	 * @param q
	 *            the question that will be added
	 */
	public void add(Question q) {
		q.setNumber(storage.size());
		storage.put(q.getNum(), q);
	}

	/**
	 * delete a question by providing the question number
	 * 
	 * @param num
	 *            the number of the question
	 */
	public void delete(int num) {
		storage.remove(new Integer(num));
	}

	/**
	 * get a question with its number
	 * 
	 * @param num
	 *            the number of the question
	 * @return the question with the number, null if not found
	 */
	public Question get(int num) {
		return storage.get(new Integer(num));
	}

	/**
	 * get all question in the group
	 * 
	 * @return a collection of questions
	 */
	public Collection<Question> getAll() {
		LinkedList<Question> temp = new LinkedList<Question>();
		for (Map.Entry<Integer, Question> iter : storage.entrySet()) {
			temp.add(iter.getValue());
		}
		return temp;
	}

	/**
	 * set the name of the group
	 * 
	 * @param toSet
	 *            the name of the group to set
	 */
	public void setName(String toSet) {
		name = new String(toSet);
	}

	/**
	 * get the name of the group
	 * 
	 * @return the name of the group
	 */
	public String getName() {
		return name;
	}

}
