/*
 * Derek Trauner
 * SPC_ID: 2491481
 * Program to be a generic class for a list of different objects, provided in starter code. used for multiple types of objects
 */

package com.example;

import java.util.ArrayList;

public class DataSetGeneric<E extends Measurable> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	public DataSetGeneric() {
	}

	@Override
	public boolean add(E objectToAdd) {
		return super.add(objectToAdd);
	}

	// Returns size of DataSetGeneric
	public int size() {
		return super.size();
	}

	public Measurable getMin() {
		if (this.isEmpty()) {
			return null;
		}
		Measurable minMeasurable = this.get(0);
		for (Measurable measurable : this) {
			if (measurable.getMeasure() < minMeasurable.getMeasure()) {
				minMeasurable = measurable;
			}
		}
		return minMeasurable;
	}

	public Measurable getMax() {
		if (this.isEmpty()) {
			return null;
		}
		Measurable maxMeasurable = this.get(0);
		for (Measurable measurable : this) {
			if (measurable.getMeasure() > maxMeasurable.getMeasure()) {
				maxMeasurable = measurable;
			}
		}
		return maxMeasurable;
	}

	@Override
	public String toString() {
		StringBuilder returnString = new StringBuilder();
		for (Measurable measurable : this) {
			returnString.append(measurable.toString() + "\n");
		}
		return returnString.toString();
	}
}