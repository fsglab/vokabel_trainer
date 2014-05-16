package com.example.vokabelliste;

public class Vokabel {

	private int id;
	private String deutsch;
	private String englisch;

	public Vokabel() {
	}

	public Vokabel(String deutsch, String englisch) {

		this.deutsch = deutsch;
		this.englisch = englisch;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeutsch() {
		return deutsch;
	}

	public void setDeutsch(String deutsch) {
		this.deutsch = deutsch;
	}

	public String getEnglisch() {
		return englisch;
	}

	public void setEnglisch(String englisch) {
		this.englisch = englisch;
	}

	// @Override
	// public String toString(){
	// return
	// "Vokabel [id= "+id+", Deutsch= "+deutsch+", Englisch= "+englisch+"]";
	// }

	@Override
	public String toString() {
		return deutsch + " - " + englisch;
	}

}
