package it.polito.TdP.country.model;

public class CountryPair {
	private Country c1;
	private Country c2;
	
	public Country getC1() {
		return c1;
	}

	public Country getC2() {
		return c2;
	}

	public CountryPair(Country c1, Country c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}
	

}
