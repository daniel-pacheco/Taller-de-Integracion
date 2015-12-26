package ar.com.santalucia.rest;

import java.util.Vector;

public class JsonPack {
	private Vector<Integer> values;
	
	public JsonPack(){
		// Vector inicial de 3 elementos
		values = new Vector<Integer>(3);
	}

	public Vector<Integer> getValues() {
		return values;
	}

	public void setValues(Vector<Integer> values) {
		this.values = values;
	}
}
