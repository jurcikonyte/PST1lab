package com.prekes.web.prekesweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // map to db table. Default table name is the name of entity class
public class Darbuotojas implements Comparable<Darbuotojas> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // JPA autogenerates value of id
	private int id;
	
	private String vardas;
	private String miestas;
	
	protected Darbuotojas() {}
	
	public Darbuotojas(String vardas, String miestas) {
		this.vardas = vardas;
		this.miestas = miestas;
	}

	public Darbuotojas(int id, String vardas, String miestas) {
		super();
		this.id = id;
		this.vardas = vardas;
		this.miestas = miestas;
	}

	@Override
	public String toString() {
		return "Darbuotojas [id=" + id + ", vardas=" + vardas + " miestas=" + miestas + "]";
	}

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
		Darbuotojas other = (Darbuotojas) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Darbuotojas o) {
		return Integer.compare(this.id, o.getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		 if (id < 0) {
	            throw new NullPointerException();
	        }
	        this.id = id;
	}

	public String getVardas() {
		return vardas;
	}

	public void setVardas(String vardas) {
		if (vardas.isBlank()) {
            throw new NullPointerException();
        }
        this.vardas = vardas;
	}

	public String getMiestas() {
		return miestas;
	}

	public void setMiestas(String miestas) {
		if (miestas.isBlank()) {
            throw new NullPointerException();
        }
        this.miestas = miestas;
	}

}
