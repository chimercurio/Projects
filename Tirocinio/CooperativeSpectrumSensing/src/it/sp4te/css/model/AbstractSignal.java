package it.sp4te.css.model;

import java.util.ArrayList;

public abstract class AbstractSignal {
	ArrayList<Double> samplesRe;
	ArrayList<Double> samplesIm;
	int lenght;
	
	
	public ArrayList<Double> getSamplesRe() {
		return samplesRe;
	}

	public void setSamplesRe(ArrayList<Double> samplesRe) {
		this.samplesRe = samplesRe;
	}

	public ArrayList<Double> getSamplesIm() {
		return samplesIm;
	}

	public void setSamplesIm(ArrayList<Double> samplesIm) {
		this.samplesIm = samplesIm;
	}
	
	public int getLenght() {
		return lenght;
	}

	public void setLenght(int signalLenght) {
		this.lenght = signalLenght;
	}
	
}
