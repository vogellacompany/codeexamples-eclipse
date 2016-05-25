package com.vogella.rcp.databinding.parts;
public class Person extends ModelObject {
	private String name;
	private String[] programmingSkills;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public String[] getProgrammingSkills() {
		return programmingSkills;
	}

	public void setProgrammingSkills(String[] programmingSkills) {
		firePropertyChange("programmingSkills", this.programmingSkills,
				this.programmingSkills = programmingSkills);
	}
}