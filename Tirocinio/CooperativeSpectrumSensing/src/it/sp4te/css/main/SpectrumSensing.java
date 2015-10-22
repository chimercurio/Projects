package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;

import it.sp4te.css.agents.SecondaryUser;
import it.sp4te.css.graphgenerator.GraphGenerator;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;

/**
 * <p>Titolo: SpectrumSensing</p>
 * <p> Descrizione della classe: La classe si occupa di creare il segnale e l'utente secondario . Tramite l'utente secondario effettua i diversi
 * tipi di Detection. Infine passa alla creazione del grafico.</p>
 * @author Pietro Coronas
 **/

public class SpectrumSensing {

	/**
	 * Metodo main per l'esecuzione
	 * @param args Rappresenta l'input
	 * @throws Exception Pfa deve essere scelto in modo che 1-2pfa sia compreso tra -1 e 1
	 * @see Signal
	 * @see SecondaryUser
	 * @see GraphGenerator#drawGraph(String, HashMap, int, int)
	 * 
	 * **/
	public static void main(String args[]) throws Exception {
		//ArrayList<Double> EnergyDetection = new ArrayList<Double>();
		ArrayList<Double> ProposedDetection = new ArrayList<Double>();
		ArrayList<Double> TraditionalEnergyDetection = new ArrayList<Double>();

		// Creo una mappa per creare il grafico. La mappa deve essere formata da
		// nomeDetection->valori
		HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();

		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 1000;
		int inf = -30;
		int sup = 5;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilit� di falso allarme
		

		// Genero il segnale
		Signal s = new Signal(length);

		// Genero utente secondario
		SecondaryUser SU = new SecondaryUser(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup,block);

		// Calcolo EnergyDetection
		//EnergyDetection = SU.spectrumSensingEnergyDetector(pfa);
		// Calcolo ProposedDetection
		ProposedDetection = SU.spectrumSensingProposedDetector(pfa);
		//Calcolo Traditional Detection
		TraditionalEnergyDetection=SU.spectrumSensingTraditionalEnergyDetector(pfa);

		// Inizializzo la Mappa per il grafico
		//DetectionGraph.put("Energy Detection", EnergyDetection);
		DetectionGraph.put("Proposed Detection", ProposedDetection);
		DetectionGraph.put("Traditional Energy Detection", TraditionalEnergyDetection);

		GraphGenerator.drawGraph("Detection Methods",DetectionGraph, inf, sup);

	}
}