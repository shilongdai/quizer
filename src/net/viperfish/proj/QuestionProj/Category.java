package net.viperfish.proj.QuestionProj;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * a category of questions, include groups
 * 
 * @author sdai
 *
 */
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TreeMap<String, Group> storage;
	protected String name;

	public Category() {
		storage = new TreeMap<String, Group>();
	}

	/**
	 * construct a category with the name of the category
	 * 
	 * @param name
	 *            the name of the category
	 */
	public Category(String name) {
		storage = new TreeMap<String, Group>();
		this.name = new String(name);
	}

	/**
	 * copy constructor
	 * 
	 * @param src
	 */
	public Category(Category src) {
		this.storage = new TreeMap<String, Group>(src.storage);
		this.name = new String(src.name);
	}

	/**
	 * set the name of the category
	 * 
	 * @param name
	 *            the name of the category
	 */
	public void setName(String name) {
		this.name = new String(name);
	}

	/**
	 * get the name of the category
	 * 
	 * @return the name of the category
	 */
	public String getName() {
		return new String(name);
	}

	/**
	 * add a group, with name of groupname to the category
	 * 
	 * @param groupName
	 *            the name of the group
	 */
	public void addGroup(String groupName) {
		Group newGroup = new Group(groupName);
		storage.put(groupName, newGroup);
	}

	/**
	 * add a group to the category
	 * 
	 * @param toAdd
	 *            the group to add
	 */

	public void addGroup(Group toAdd) {
		storage.put(toAdd.getName(), toAdd);
	}

	/**
	 * remove a group from the category, by providing the name
	 * 
	 * @param groupName
	 *            the name of the group
	 */
	public void deleteGroup(String groupName) {
		storage.remove(groupName);
	}

	/**
	 * get a group by providing the name of the group
	 * 
	 * @param groupName
	 *            the name of the group
	 * @return null if no group with the name is found
	 */
	public Group getGroup(String groupName) {
		return storage.get(groupName);
	}

	/**
	 * get all the group in the category
	 * 
	 * @return a collection of all the groups
	 */
	public Collection<Group> getAll() {
		LinkedList<Group> temp = new LinkedList<Group>();
		for (Map.Entry<String, Group> i : storage.entrySet()) {
			temp.add(i.getValue());
		}
		return temp;
	}

}
