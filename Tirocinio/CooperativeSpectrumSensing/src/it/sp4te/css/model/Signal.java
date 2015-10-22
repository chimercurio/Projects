package it.sp4te.css.model;

import java.util.ArrayList;

/** <p>Titolo: Signal</p>
 * <p>Descrizione: Classe per la generazione del segnale</p>
 * @author Pietro Coronas **/

public class Signal extends AbstractSignal{

	/**
	 * Costruttore del segnale modulato QPSK e con potenza unitaria. L'oggetto segnale contiene 2 array,
	 * rispettivamente relativi a parte reale e parte immaginaria.
	 * 
	 * @param signalLenght Lunghezza del segnale
	 **/

	public Signal(int signalLenght) {
		this.lenght = signalLenght;
		samplesRe = new ArrayList<Double>();
		samplesIm = new ArrayList<Double>();
		for (int i = 0; i < this.lenght; i++) {
			double v = Math.random();
			if (v < 0.5) {
				samplesRe.add(i, v / Math.sqrt(this.lenght));}
			else{
				samplesRe.add(i, -v / Math.sqrt(this.lenght));}
			double p = Math.random();
			if (p < 0.5) {
				samplesIm.add(i,p / Math.sqrt(this.lenght));}
			else{
				samplesIm.add(i, -p / Math.sqrt(this.lenght));}

			}
		}
	




	
	

}
