package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;

import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.agents.SecondaryUser;
import it.sp4te.css.graphgenerator.GraphGenerator;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;
import it.sp4te.fusioncenter.FusionCenter;

public class ClassicCooperativeSpectrumSensing {

	public static void main(String args[]) throws Exception {
		//------------------------------------------------Presenza utente primario-------------------//
		ArrayList<Double> CooperativeEnergyDetectionAndFusion = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionOrFusion = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionMajorityFusion = new ArrayList<Double>();;
		
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		
		HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();
		
		// Setto i parametri
				int length = 1000; // poi 10000
				int attempts = 100;
				int inf = -30;
				int sup = 5;
				int block=10; //blocchi energy Detector
				double pfa=0.01; //probabilit� di falso allarme
		
		FusionCenter FC=new FusionCenter();
		PrimaryUser PU= new PrimaryUser();
        SecondaryUser FirstSU=new SecondaryUser();
        SecondaryUser SecondSU=new SecondaryUser();
        SecondaryUser ThirdSU=new SecondaryUser();
        SecondaryUser fourthSU=new SecondaryUser();
        SecondaryUser fifthSU=new SecondaryUser();
        
        
        Signal s = PU.createAndSend(length);
        
        FirstSU.listenChannel(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        SecondSU.listenChannel(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        ThirdSU.listenChannel(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        fourthSU.listenChannel(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        fifthSU.listenChannel(s, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        

        
        userToBinaryDecision.put(FirstSU.toString(), FirstSU.computeBinaryDecision(pfa));
        userToBinaryDecision.put(SecondSU.toString(), SecondSU.computeBinaryDecision(pfa));
        userToBinaryDecision.put(ThirdSU.toString(), ThirdSU.computeBinaryDecision(pfa));
        userToBinaryDecision.put(fourthSU.toString(),  fourthSU.computeBinaryDecision(pfa));
        userToBinaryDecision.put(fifthSU.toString(), fifthSU.computeBinaryDecision(pfa));
        
        CooperativeEnergyDetectionAndFusion=FC.decisionAndFusion(inf, sup,userToBinaryDecision);
        CooperativeEnergyDetectionOrFusion=FC.decisionOrFusion(inf, sup,userToBinaryDecision);
        CooperativeEnergyDetectionMajorityFusion=FC.decisionMajorityFusion(inf, sup,userToBinaryDecision);
        
        DetectionGraph.put("CED with AND fusion", CooperativeEnergyDetectionAndFusion);
        DetectionGraph.put("CED with OR fusion", CooperativeEnergyDetectionOrFusion);
        DetectionGraph.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusion);

		GraphGenerator.drawGraph("Presence of PU in Cooperative Energy Detection (CED)",DetectionGraph, inf, sup);
        
	//------------------------------------------------Assenza utente primario-------------------//
		ArrayList<Double> CooperativeEnergyDetectionAndFusionAbsence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionOrFusionAbsence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionMajorityFusionAbsence = new ArrayList<Double>();;
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String, ArrayList<Double>> DetectionGraph2 = new HashMap<String, ArrayList<Double>>();


		 SecondaryUser FirstSU2=new SecondaryUser();
	     SecondaryUser SecondSU2=new SecondaryUser();
	     SecondaryUser ThirdSU2=new SecondaryUser();
	     SecondaryUser fourthSU2=new SecondaryUser();
	     SecondaryUser fifthSU2=new SecondaryUser();
	     FirstSU2.listenChannel(null, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
	     SecondSU2.listenChannel(null, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
	     ThirdSU2.listenChannel(null, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
	     fourthSU2.listenChannel(null, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
	     fifthSU2.listenChannel(null, length, SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
        
	     userToBinaryDecisionAbsence.put(FirstSU2.toString(), FirstSU2.computeBinaryDecision(pfa));
	     userToBinaryDecisionAbsence.put(SecondSU2.toString(), SecondSU2.computeBinaryDecision(pfa));
	     userToBinaryDecisionAbsence.put(ThirdSU2.toString(), ThirdSU2.computeBinaryDecision(pfa));
	     userToBinaryDecisionAbsence.put(fourthSU2.toString(),  fourthSU2.computeBinaryDecision(pfa));
	     userToBinaryDecisionAbsence.put(fifthSU2.toString(), fifthSU2.computeBinaryDecision(pfa));
        
	     CooperativeEnergyDetectionAndFusionAbsence=FC.decisionAndFusion(inf, sup,userToBinaryDecisionAbsence);
	     CooperativeEnergyDetectionOrFusionAbsence=FC.decisionOrFusion(inf, sup,userToBinaryDecisionAbsence);
	     CooperativeEnergyDetectionMajorityFusionAbsence=FC.decisionMajorityFusion(inf, sup,userToBinaryDecisionAbsence);
	       
        
        DetectionGraph2.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionAbsence);
        DetectionGraph2.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionAbsence);
        DetectionGraph2.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionAbsence);
        
        GraphGenerator.drawGraph("Absence of PU in Cooperative Energy Detection (CED)",DetectionGraph2, inf, sup);
	
	}
        
        
	}
