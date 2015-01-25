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
public class QuestionBank implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -292933982620484738L;
	protected TreeMap<String,Category> storage;
	
	
	public QuestionBank() {
		storage =  new TreeMap<String,Category>();
	}
	
	public QuestionBank(QuestionBank src) {
		this.storage = new TreeMap<String,Category>(src.storage);
	}
	
	public void addCategory(String name) {
		storage.put(name, new Category(name));
	}
	
	public void addCategory(Category toAdd) {
		storage.put(toAdd.getName(), toAdd);
	}
	
	public void deleteCategory(String name) {
		storage.remove(name);
	}
	
	public Category getCategory(String name) {
		return storage.get(name);
	}
	
	public Collection<Category> getAll() {
		LinkedList<Category> temp = new LinkedList<Category>();
		for(Map.Entry<String, Category> i : storage.entrySet()) {
			temp.add(i.getValue());
		}
		return temp;
	}
	
	public static void save(QuestionBank toSave) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream( new GZIPOutputStream(new FileOutputStream("QuestionBank")));
		try {
			out.writeObject(toSave);
			out.flush();
		} catch (IOException e) {
			throw e;
		}
		finally {
			out.close();
		}
		
	}
	
	public static QuestionBank load() throws IOException, ClassNotFoundException {
		if(!new File("QuestionBank").exists()) {
			return new QuestionBank();
		}
		ObjectInputStream in = new ObjectInputStream( new GZIPInputStream( new FileInputStream("QuestionBank")));
		try
		{
			return (QuestionBank)in.readObject();
		}
		finally {
			in.close();
		}
	}
}