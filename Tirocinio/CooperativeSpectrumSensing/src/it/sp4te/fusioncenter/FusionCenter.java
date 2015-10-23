package it.sp4te.fusioncenter;

import java.util.ArrayList;
import java.util.HashMap;
import it.sp4te.css.detection.Detector;

public class FusionCenter {
	
	/**Metodo di fusione con la tecnica and. Ritorna 1 (l'utente primario � presente) se tutte le decisioni
	 * di tutti i dispositivi sono uguali ad 1, 0 altrimenti**/
	
	public static int andFusion(ArrayList<Integer> decisions){
		int fusionDecision=1;
		for(int i=0;i<decisions.size();i++){
			if(decisions.get(i)==0){
				fusionDecision=0;
			}
		}
		return fusionDecision;
	}
	
	
	/**Metodo di fusione con la tecnica or. Ritorna 1 (l'utente primario � presente) se almeno una delle decisioni
	 * dei dispositivi sono uguali ad 1, 0 altrimenti**/
	
	public static int orFusion(ArrayList<Integer> decisions){
		int fusionDecision=0;
		
		for(int i=0;i<decisions.size();i++){
			if(decisions.get(i)==1){
				fusionDecision=1;
			}
		}
		return fusionDecision;
	}
	
	
	
	/**Metodo di fusione con la tecnica majority. Ritorna 1 (l'utente primario � presente) se la maggioranza (met� +1) delle decisioni
	 * + uguale a 1, zero altrimenti**/
	
	public static int majorityFusion(ArrayList<Integer> decisions){
		int majority=(decisions.size()/2)+1;
		int contPresence=0;
		int fusionDecision = 0;
		for(int i=0;i<decisions.size();i++){
			if(decisions.get(i)==1){
				contPresence++;
			}
		}
		if(contPresence>=majority){
			fusionDecision=1;
		}
		
	
	return fusionDecision;}
	
	 public  ArrayList<Double> decisionAndFusion(int inf,int sup,HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision){
		 ArrayList<Double> EnergyDetection = new  ArrayList<Double>();
	  	 
	 for(int i=0;i<35;i++){
		 ArrayList<ArrayList<Integer>> decisions=new ArrayList<ArrayList<Integer>>();
     	for(String SU: userToBinaryDecision.keySet()){
		 ArrayList<Integer>snrDecisionVector= userToBinaryDecision.get(SU).get(i);
     	  decisions.add(snrDecisionVector);}

     	EnergyDetection.add(Detector.andFusionDetection(decisions));
     	}
		return EnergyDetection;
	 }
	 
	 public  ArrayList<Double> decisionOrFusion(int inf,int sup,HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision){
		 ArrayList<Double> EnergyDetection = new  ArrayList<Double>();
	  	 
	 for(int i=0;i<35;i++){
		 ArrayList<ArrayList<Integer>> decisions=new ArrayList<ArrayList<Integer>>();
     	for(String SU: userToBinaryDecision.keySet()){
		 ArrayList<Integer>snrDecisionVector= userToBinaryDecision.get(SU).get(i);
     	  decisions.add(snrDecisionVector);}

     	EnergyDetection.add(Detector.orFusionDetection(decisions));
     	}
		return EnergyDetection;
	 }
	 
	 public  ArrayList<Double> decisionMajorityFusion(int inf,int sup,HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision){
		 ArrayList<Double> EnergyDetection = new  ArrayList<Double>();
	  	 
	 for(int i=0;i<35;i++){
		 ArrayList<ArrayList<Integer>> decisions=new ArrayList<ArrayList<Integer>>();
     	for(String SU: userToBinaryDecision.keySet()){
		 ArrayList<Integer>snrDecisionVector= userToBinaryDecision.get(SU).get(i);
     	  decisions.add(snrDecisionVector);}

     	EnergyDetection.add(Detector.majorityFusionDetection(decisions));
     	}
		return EnergyDetection;
	 }
	 
	}
