package it.sp4te.CooperativeSpectrumSensing.Main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import it.sp4te.CooperativeSpectrumSensing.Agents.SecondaryUser;
import it.sp4te.CooperativeSpectrumSensing.DomainModel.Signal;
import it.sp4te.CooperativeSpectrumSensing.Functions.SignalFunctions;
import it.sp4te.CooperativeSpectrumSensing.GraphGenerator.GraphGenerator;

public class SpectrumSensing {

	public static void main(String args[]) throws Exception {	
	HashMap<Double,Double> EnergyDetection=	new HashMap<Double,Double>();
	HashMap<String,ArrayList<Double>> EnergyDetectionGraph= new HashMap<String,ArrayList<Double>>();
	
		//Setto i parametri
		int length=1000; //poi 10000
		int attempts= 1000;
		int inf= -30;
		int sup= 5;
		
		//Genero il segnale
		Signal s = new Signal(length);
		
		//Genero utente secondario
		SecondaryUser SU= new SecondaryUser();
		
		//Calcolo EnergyDetector
		EnergyDetection=SU.SpectrumSensingEnergyDetector(s, length, SignalFunctions.signalEnergy(s), attempts, inf, sup);		
		
		//E' NECESSARIO UN ORDINAMENTO IN BASE AGLI SNR
		//Ordino gli snr
		ArrayList<Double> snr= new ArrayList<Double>();
		for (Double key : EnergyDetection.keySet()) {
			snr.add(key);}
		Collections.sort(snr);
		
		//Prendo le detection
		ArrayList<Double> Edetection= new ArrayList<Double>();
		for (Double key : snr) {
			Edetection.add(EnergyDetection.get(key));}
	
		
		//Creo una mappa per creare il grafico. La mappa deve essere formata da nomeDetection->valori
		
		EnergyDetectionGraph.put("Energy Detection", Edetection);
		GraphGenerator.drawGraph(EnergyDetectionGraph);
		
		
	}
}
