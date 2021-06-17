package it.polito.tdp.food.model;

public class Arco {
	
	Portion p1;
	Portion p2;
	Integer peso;
	public Arco(Portion p1, Portion p2, Integer peso) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Portion getP1() {
		return p1;
	}
	public void setP1(Portion p1) {
		this.p1 = p1;
	}
	public Portion getP2() {
		return p2;
	}
	public void setP2(Portion p2) {
		this.p2 = p2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	

}
