package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;

import it.sp4te.css.agents.TrustedSecondaryUser;
import it.sp4te.css.agents.FusionCenter;
import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.graphgenerator.Chart4jGraphGenerator;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;
import it.sp4te.css.signalprocessing.Utils;

/**Questa classe modella uno scenario cooperativo ideale in cui sono presenti solamente utenti secondari fidati,
 * utilizzando le tecniche di fusione AND,OR e MAJORITY nei casi in cui l'utente primario sia presente o assente**/



public class ClassicCooperativeSpectrumSensing {


	public static void main(String args[]) throws Exception {
		//------------------------------------------------Presenza utente primario-------------------//
		ArrayList<Double> CooperativeEnergyDetectionAndFusion = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionOrFusion = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionMajorityFusion = new ArrayList<Double>();;

		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision=new HashMap<String,ArrayList<ArrayList<Integer>>>();
        HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();

        ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers;
        
		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 100;
		int inf = -30;
		int sup = 5;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilitÓ di falso allarme
		int numberTSU=5;//numero di utenti fidati

		//Creo il Fusion center
		FusionCenter FC=new FusionCenter();
		//Creo l'utente primario
		PrimaryUser PU= new PrimaryUser();
		//creo il segnale
		Signal s = PU.createAndSend(length);
		
		//Creo gli utenti secondari
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
				//mappa
		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);


		//Tutte le decisioni di tutti gli utenti secondari passano al fusion center che riporterÓ una decisione
		//globale secondo tre tecniche di fusione: AND OR e MAJORITY. 
		CooperativeEnergyDetectionAndFusion=FC.andDecision(inf, sup,userToBinaryDecision);
		CooperativeEnergyDetectionOrFusion=FC.orDecision(inf, sup,userToBinaryDecision);
		CooperativeEnergyDetectionMajorityFusion=FC.majorityDecision(inf, sup,userToBinaryDecision);

		DetectionGraph.put("CED AND fusion", CooperativeEnergyDetectionAndFusion);
		DetectionGraph.put("CED OR fusion", CooperativeEnergyDetectionOrFusion);
		DetectionGraph.put("CED MAJORITY fusion", CooperativeEnergyDetectionMajorityFusion);

		Chart4jGraphGenerator graphPresence= new Chart4jGraphGenerator();
		graphPresence.drawSNRtoDetectionGraph("Presence of PU in Cooperative Energy Detection (CED)",DetectionGraph, inf, sup);

		
		//------------------------------------------------Assenza utente primario-------------------//
		//ArrayList<Double> CooperativeEnergyDetectionAndFusionAbsence = new ArrayList<Double>();;
		//ArrayList<Double> CooperativeEnergyDetectionOrFusionAbsence = new ArrayList<Double>();;
		//ArrayList<Double> CooperativeEnergyDetectionMajorityFusionAbsence = new ArrayList<Double>();;
		//HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		//HashMap<String, ArrayList<Double>> DetectionGraph2 = new HashMap<String, ArrayList<Double>>();
		//  ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers2;
		
		//  TrustedSecondaryUsers2= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		//   for(int i=0;i<TrustedSecondaryUsers.size();i++){
        	//		userToBinaryDecisionAbsence.put(TrustedSecondaryUsers2.get(i).toString(), TrustedSecondaryUsers2.get(i).computeBinaryDecisionVector(pfa));	
		//	}

		//	CooperativeEnergyDetectionAndFusionAbsence=FC.andDecision(inf, sup,userToBinaryDecisionAbsence);
		//	CooperativeEnergyDetectionOrFusionAbsence=FC.orDecision(inf, sup,userToBinaryDecisionAbsence);
		//	CooperativeEnergyDetectionMajorityFusionAbsence=FC.majorityDecision(inf, sup,userToBinaryDecisionAbsence);


		//	DetectionGraph2.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionAbsence);
		//	DetectionGraph2.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionAbsence);
		//	DetectionGraph2.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionAbsence);

		//	GraphGenerator.drawGraph("Absence of PU in Cooperative Energy Detection (CED)",DetectionGraph2, inf, sup);

			}


		}

