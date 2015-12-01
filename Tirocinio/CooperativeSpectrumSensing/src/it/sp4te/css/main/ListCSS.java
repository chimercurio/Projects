package it.sp4te.css.main;

import java.util.ArrayList;

import java.util.HashMap;

import it.sp4te.css.agents.FusionCenter;
import it.sp4te.css.agents.MaliciousSecondaryUser;
import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.agents.TrustedSecondaryUser;
import it.sp4te.css.graphgenerator.GraphGenerator;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;
import it.sp4te.css.signalprocessing.Utils;

/**Questa classe modella uno scenario cooperativo  in cui sono presenti  utenti secondari fidati e non,
 * utilizzando la tecnica basata sulla divisione in liste degli utenti**/
public class ListCSS {

	
	public static void main(String args[]) throws Exception {
		ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers;

		//HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		//HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOpposite=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		//HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligent=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence2=new HashMap<String,ArrayList<ArrayList<Integer>>>();

		
	//	ArrayList<Double> ListCooperativeEnergyDetection = new ArrayList<Double>();;
		ArrayList<Double> ListCooperativeEnergyDetectionAbsence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionAbsence = new ArrayList<Double>();;

		//ArrayList<Double> ListCooperativeEnergyDetectionOpposite = new ArrayList<Double>();;
		//ArrayList<Double> ListCooperativeEnergyDetectionIntelligent = new ArrayList<Double>();;
        HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();


		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 800;
		int inf=-16;
		int sup=-6 ;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilit� di falso allarme
		int numberTSU=20;
		int numberMSU=10;//numero di utenti fidati
		int K; //GRIGIA->BIANCA
		int L;// NERA->BIANCA
		int M=4; //BIANCA->GRIGIA
		int N=6;//GRIGIA->NERA
        int j=3;
		//Creo il Fusion center
		FusionCenter FC=new FusionCenter();
		//Creo l'utente primario
		PrimaryUser PU= new PrimaryUser();
		//creo il segnale
		Signal s = PU.createAndSend(length);

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		userToBinaryDecisionAbsence2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecisionAbsence2.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		CooperativeEnergyDetectionAbsence=FC.majorityDecision(inf, sup, userToBinaryDecisionAbsence2);
		
		
	for(int delta=1;delta<8;delta++){
			L=N+(delta*j);
			K=M+delta;
		
			DetectionGraph.clear();
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionAbsence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionAbsence.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		ListCooperativeEnergyDetectionAbsence= FC.ListBasedDecision(inf, sup, userToBinaryDecisionAbsence, attempts, K, L, M, N,"Absence");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionAbsence);
		DetectionGraph.put("Majority", CooperativeEnergyDetectionAbsence);

		
		/**
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();
		
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionOpposite=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionOpposite.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionOpposite= FC.ListBasedDecision(inf, sup, userToBinaryDecisionOpposite, attempts, K, L, M, N,"Opposite");

		DetectionGraph.put("With Opposite MSU", ListCooperativeEnergyDetectionOpposite);
		
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();
		
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionIntelligent=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionIntelligent.putAll(Utils.genereteIntelligentMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionIntelligent= FC.ListBasedDecision(inf, sup, userToBinaryDecisionIntelligent, attempts, K, L, M, N,"Intelligent");

		DetectionGraph.put("With Intelligent MSU", ListCooperativeEnergyDetectionIntelligent);**/

		GraphGenerator.drawAndSaveGraph("Presence of PU in Cooperative ED.30% Absence MSU ",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+".jpg");;
		//GraphGenerator.drawGraph("Presence of PU in List Cooperative Energy Detection.30% MSU ",DetectionGraph, inf, sup);
		}}} 
/**
		for(int i=0;i<45;i++){
		 numberTSU=50-i;
		 numberMSU=0+i;
		 System.out.println(numberTSU+"        "+ numberMSU);
		 TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		 MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		 userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		 userToBinaryDecision.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		 ListCooperativeEnergyDetection.add(FC.ListBasedDecision(inf, sup, userToBinaryDecision, attempts, K, L, M, N,"Absence_-11Db").get(0));
        

		}
	DetectionGraph.put("List CSS: Opposite MSU",  ListCooperativeEnergyDetection);
	
GraphGenerator.drawMaliciousUsersToDetectionGraph("List CSS:K=L=M=N=2",DetectionGraph, inf, sup);

	}**/
