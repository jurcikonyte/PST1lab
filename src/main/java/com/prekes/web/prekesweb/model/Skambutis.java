package com.prekes.web.prekesweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skambutis implements Comparable<Skambutis> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // JPA autogenerates value of id
	private int id;
    private int atsiliepta;
    private String laikas;
    private int darbuotojoId;
    private int trukme;
    
    public Skambutis() {}

	public Skambutis (int atsiliepta, String laikas, int darbuotojoId, int trukme) {
		this.atsiliepta = atsiliepta;
		this.laikas = laikas;
		this.darbuotojoId = darbuotojoId;
		this.trukme = trukme;
	}

	@Override
	public String toString() { // System.out.println(p)
		return "Skambutis [id=" + id + ", atsiliepta=" + atsiliepta + ", laikas=" + laikas + ", darbuotojoId=" + darbuotojoId + ", trukme=" + trukme +"]";
	}


	@Override
	public int compareTo(Skambutis o) {
		return Integer.compare(this.id, o.id);
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
		Skambutis other = (Skambutis) obj;
		if (this.id != other.id)
			return false;
		return true;
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
    
	public int getAtsiliepta() {
		return atsiliepta;
	}
	
    public void setAtsiliepta(int atsiliepta) {
        if (atsiliepta < 0) {
            throw new NullPointerException();
        }
        this.atsiliepta = atsiliepta;
    }
	public String getLaikas() {
		return laikas;
	}
	
    public void setLaikas(String laikas) {
        if (laikas == null) {
            throw new NullPointerException();
        }
        this.laikas = laikas;
    }
	public int getDarbuotojoId() {
		return darbuotojoId;
	}
	
    public void setDarbuotojoId(int darbuotojoId) {
        if (darbuotojoId < 0) {
            throw new NullPointerException();
        }
        this.darbuotojoId = darbuotojoId;
    }
	public int getTrukme() {
		return trukme;
	}
	
    public void setTrukme(int trukme) {
        if (trukme < 0) {
            throw new NullPointerException();
        }
        this.trukme = trukme;
    }
}
