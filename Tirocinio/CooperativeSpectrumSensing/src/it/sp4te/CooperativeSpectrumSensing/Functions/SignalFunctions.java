package it.sp4te.CooperativeSpectrumSensing.Functions;

import java.util.ArrayList;

import it.sp4te.CooperativeSpectrumSensing.DomainModel.Signal;

/**
 * <p>Titolo:SignalFunctions</p>
 * <p>Descrizione: Classe che contiene Funzioni utili da applicare sul segnale: -Calcolo
 * dell'energia -Generazione Momenti -Generazione PR -Calcolo dell'energia dei
 * momenti</p>
 * @author Pietro Coronas
 **/

public class SignalFunctions {

	/** 
	 * Metodo per il calcolo dell'energia del segnale 
	 * 
	 * @param s Segnale su cui calcolare l'energia
	 * @return energia del segnale **/
	
	public static double signalEnergy(Signal s) {
		double p = 0.0;
		for (int i = 0; i < s.getSignalLenght(); i++) {
			p = p + Math.abs(Math.pow(s.getSamplesRe().get(i), 2)) + Math.abs((Math.pow(s.getSamplesIm().get(i), 2)));
		}
		return p / s.getSignalLenght();
	}

	/** 
	 * Metodo per la generazione dei momenti del secondo e quarto ordine.
	 * 
	 * @param s Il segnale
	 * @param length Lunghezza del segnale 
	 * @param energy Energia del segnale
	 * @param attempts Numero di prove su cui effettuare la simulazione
	 * @param inf Estremo inferiore di SNR su cui effettuare la simulazione
	 * @param sup Estremo superiore di SNR su cui effettuare la simulazione 
	 * @return Una lista di Momenti**/
	
	
	public static ArrayList<Moment> momentGenerator(Signal s, int length, double energy, int attempts, int inf,
			int sup) {
		ArrayList<Moment> Moments = new ArrayList<Moment>();
		for (double i = inf; i < sup; i++) {
			Moment m = new Moment(s, attempts, energy, i, length);
			Moments.add(m);
		}
		return Moments;
	}

	/**
	 *  Metodo per il calcolo dell'energia dei momenti 
	 * 
	 * @param Moment Array di oggetti momento
	 * @return Energia **/
	
	public static ArrayList<ArrayList<Double>> momentEnergy(ArrayList<Moment> Moment) {
		ArrayList<ArrayList<Double>> energy = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < Moment.size(); i++) {
			energy.add(Moment.get(i).getEnergy());
		}
		return energy;
	}

	/** 
	 * Metodo per la generazione degli oggetti PR 
	 * 
	 * @param Moment Lista di Oggetti Momento su cui calcolare Pr
	 * @return una lista di Oggetti Pr **/
	
	public static ArrayList<Pr> prGenerator(ArrayList<Moment> Moment) {
		ArrayList<Pr> PrResult = new ArrayList<Pr>();
		for (int i = 0; i < Moment.size(); i++) {
			Pr prTemp = new Pr(Moment.get(i));
			PrResult.add(prTemp);

		}
		return PrResult;
	}

}
