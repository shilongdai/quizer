package net.viperfish.proj.QuestionProj;
import java.io.Serializable;
import java.util.*;
public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716041486772357383L;
	protected Map<Integer,Question> storage;
	protected String name;
	
	public Group() {
		storage = new TreeMap<Integer,Question>();
	}
	
	public Group(String name) {
		storage = new TreeMap<Integer,Question>();
		this.name = new String(name);
	}
	
	public Group(final Group src) {
		storage = new TreeMap<Integer,Question>(src.storage);
		name = new String(src.name);
	}
	public void add(Question q) {
		q.setNumber(storage.size());
		storage.put(q.getNum(), q);
	}
	
	public void delete(int num) {
		storage.remove(new Integer(num));
	}
	
	public Question get(int num) {
		return storage.get(new Integer(num));
	}
	
	public Collection<Question> getAll() {
		LinkedList<Question> temp = new LinkedList<Question>();
		for(Map.Entry<Integer, Question> iter : storage.entrySet() ) {
			temp.add(iter.getValue());
		}
		return temp;
	}
	
	public void setName(String toSet) {
		name = new String(toSet);
	}
	
	public String getName() {
		return name;
	}
	
}
