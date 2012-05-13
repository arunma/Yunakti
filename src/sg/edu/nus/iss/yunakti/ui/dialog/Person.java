package sg.edu.nus.iss.yunakti.ui.dialog;

public class Person {
	private String name;
	
	public Person(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}