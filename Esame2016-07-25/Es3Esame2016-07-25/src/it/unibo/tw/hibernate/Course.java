package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.Set;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String name;
	
	private Set<Student> students;
	
	// --- constructor ----------
	
	public Course() 
	{
		
	}
	
	
	// --- getters and setters --------------
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/*metodi usati nella soluzione hibernate.bis dove il mapping è creato in automatico*/
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	// --- utilities ----------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
