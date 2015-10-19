package it.sp4te.CooperativeSpectrumSensing.DomainModel;

import java.util.ArrayList;
import java.util.Random;

/** Classe per la generazione del rumore **/

public class Noise {

	private int noiseLenght;
	private double variance;
	ArrayList<Double> samplesRe;
	ArrayList<Double> samplesIm;
	Random sample;

	/**
	 * Costruttore dell'oggetto Rumore. Il rumore sar� gaussiano con varianza unitaria.
	 * L'oggetto � costituito da 2 vettori, relativi a parte reale e parte immaginaria.
	 * 
	 * @param snr L'SNR a cui generare il rumore
	 * @param noiseLenght Lunghezza del rumore
	 * @param energy Energia del rumore
	 **/

	public Noise(double snr, int noiseLenght, double energy) {

		double a = Math.pow(10, (snr / 10));
		this.variance = (energy / a);
		this.noiseLenght = noiseLenght;

		this.samplesRe = new ArrayList<Double>();
		for (int i = 0; i < this.noiseLenght; i++) {
			sample = new Random();
			samplesRe.add(i, (sample.nextGaussian() * Math.sqrt(variance)));

		}
		this.samplesIm = new ArrayList<Double>();
		for (int i = 0; i < this.noiseLenght; i++) {
			sample = new Random();
			samplesIm.add(i, (sample.nextGaussian() * Math.sqrt(variance)));
		}
	}


	public int getNoiseLenght() {
		return noiseLenght;
	}

	public void setNoiseLenght(int signalLenght) {
		this.noiseLenght = signalLenght;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

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

	public Random getSample() {
		return sample;
	}

	public void setSample(Random sample) {
		this.sample = sample;
	}

}
