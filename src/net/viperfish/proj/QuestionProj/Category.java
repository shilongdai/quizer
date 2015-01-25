package net.viperfish.proj.QuestionProj;
import java.io.Serializable;
import java.util.*;
public class Category implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TreeMap<String,Group> storage;
	protected String name;
	
	public Category() {
		storage = new TreeMap<String,Group>();
	}
	public Category(String name) {
		storage = new TreeMap<String,Group>();
		this.name = new String(name);
	}
	
	public Category(Category src) {
		this.storage = new TreeMap<String,Group>(src.storage);
		this.name = new String(src.name);
	}
	
	public void setName(String name) {
		this.name = new String(name);
	}
	
	public String getName() {
		return new String(name);
	}
	
	public void addGroup(String groupName) {
		Group newGroup = new Group(groupName);
		storage.put(groupName, newGroup);
	}
	
	public void addGroup(Group toAdd) {
		storage.put(toAdd.getName(), toAdd);
	}
	
	public void deleteGroup(String groupName) {
		storage.remove(groupName);
	}
	
	public Group getGroup(String groupName) {
		return storage.get(groupName);
	}
	
	public Collection<Group> getAll() {
		LinkedList<Group> temp = new LinkedList<Group>();
		for(Map.Entry<String, Group> i : storage.entrySet()) {
			temp.add(i.getValue());
		}
		return temp;
	}
	
}
