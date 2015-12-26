package ar.com.santalucia.rest;

import java.util.Vector;

public class JsonPack {
	private Vector<Long> values;
	
	public JsonPack(){
		// Vector inicial de 3 elementos
		values = new Vector<Long>(3);
	}

	public Vector<Long> getValues() {
		return values;
	}

	public void setValues(Vector<Long> values) {
		this.values = values;
	}
}
