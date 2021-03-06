package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;

import it.sp4te.css.agents.TrustedSecondaryUser;
import it.sp4te.css.agents.FusionCenter;
import it.sp4te.css.agents.MaliciousSecondaryUser;
import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.graphgenerator.Chart4jGraphGenerator;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;
import it.sp4te.css.signalprocessing.Utils;

/**Questo classe modella 4 tipi di scenario : In uno � presente un numero di utenti malevoli che riportano sempre l'assenza dell'utente
 * primario in uno scenario in cui � presente. In un secondo scenario riportano sempre la presenza dell'utente primario in uno scenario in cui
 * � assente. Negli altri 2 scenari abbiamo un utente malevolo intelligente che genera l'opposto del risultato dell'energy Detector: riporta 1 se l'utente �
 * assente e 0 se � presente. Attiviamo questo utente malevolo sia in presenza che in assenza dell'utente primario**/

public class MaliciousCoopeartiveSpectrumSensing {

	public static void main(String args[]) throws Exception {
		//------------------------------------------------Presenza utente primario-------------------//	
		ArrayList<Double> CooperativeEnergyDetectionAndFusionPresence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionOrFusionPresence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionMajorityFusionPresence = new ArrayList<Double>();;

		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionPresence=new HashMap<String,ArrayList<ArrayList<Integer>>>();

		HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();

		ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers;


		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 100;
		int inf = -30;
		int sup = 5;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilit� di falso allarme
		int numberTSU=3;
		int numberMSU=2;

		//Creo il Fusion center
		FusionCenter FC=new FusionCenter();
		//Creo l'utente primario
		PrimaryUser PU= new PrimaryUser();

		//creo il segnale
		Signal s = PU.createAndSend(length);

		//Creo gli utenti secondari
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);



		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
		//mappa
		userToBinaryDecisionPresence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionPresence.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));

		//Tutte le decisioni di tutti gli utenti secondari passano al fusion center che riporter� una decisione
		//globale secondo tre tecniche di fusione: AND OR e MAJORITY. 
		CooperativeEnergyDetectionAndFusionPresence=FC.andDecision(inf, sup,userToBinaryDecisionPresence);
		CooperativeEnergyDetectionOrFusionPresence=FC.orDecision(inf, sup,userToBinaryDecisionPresence);
		CooperativeEnergyDetectionMajorityFusionPresence=FC.majorityDecision(inf, sup,userToBinaryDecisionPresence);

		DetectionGraph.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionPresence);
		DetectionGraph.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionPresence);
		DetectionGraph.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionPresence);

		Chart4jGraphGenerator presenceGraph= new Chart4jGraphGenerator();
		presenceGraph.drawSNRtoDetectionGraph("Presence of PU in Cooperative Energy Detection (CED) with Malicious User",DetectionGraph, inf, sup);


		//------------------------------------------------Assenza utente primario-------------------//
		ArrayList<Double> CooperativeEnergyDetectionAndFusionAbsence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionOrFusionAbsence = new ArrayList<Double>();;
		ArrayList<Double> CooperativeEnergyDetectionMajorityFusionAbsence = new ArrayList<Double>();;
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String, ArrayList<Double>> DetectionGraph2 = new HashMap<String, ArrayList<Double>>();

		ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers2;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers2;


		//Creo gli utenti secondari
		TrustedSecondaryUsers2= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers2=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
		//mappa
		userToBinaryDecisionAbsence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers2, pfa);		
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre presente
		userToBinaryDecisionAbsence.putAll(Utils.generetePresenceBinaryDecisionVectors(MaliciousSecondaryUsers2));



		CooperativeEnergyDetectionAndFusionAbsence=FC.andDecision(inf, sup,userToBinaryDecisionAbsence);
		CooperativeEnergyDetectionOrFusionAbsence=FC.orDecision(inf, sup,userToBinaryDecisionAbsence);
		CooperativeEnergyDetectionMajorityFusionAbsence=FC.majorityDecision(inf, sup,userToBinaryDecisionAbsence);


		DetectionGraph2.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionAbsence);
		DetectionGraph2.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionAbsence);
		DetectionGraph2.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionAbsence);

		Chart4jGraphGenerator absenceGraph= new Chart4jGraphGenerator();
		absenceGraph.drawSNRtoDetectionGraph("Absence of PU in Cooperative Energy Detection (CED) with Malicious User",DetectionGraph2, inf, sup);

		//------------------------------------------------Presenz autente primario con utente malevolo intelligente-------------------//
		CooperativeEnergyDetectionAndFusionPresence.clear() ;
		CooperativeEnergyDetectionOrFusionPresence.clear(); ;
		CooperativeEnergyDetectionMajorityFusionPresence.clear();
		userToBinaryDecisionPresence.clear();
		DetectionGraph.clear();


		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
		//mappa
		userToBinaryDecisionPresence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano l'opposto dell'energy detector: riporta 1 se l'utente � assente, 0 se � presente
		userToBinaryDecisionPresence.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers, pfa)); 	

		CooperativeEnergyDetectionAndFusionPresence=FC.andDecision(inf, sup,userToBinaryDecisionPresence);
		CooperativeEnergyDetectionOrFusionPresence=FC.orDecision(inf, sup,userToBinaryDecisionPresence);
		CooperativeEnergyDetectionMajorityFusionPresence=FC.majorityDecision(inf, sup,userToBinaryDecisionPresence);

		DetectionGraph.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionPresence);
		DetectionGraph.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionPresence);
		DetectionGraph.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionPresence);

		Chart4jGraphGenerator MSUIntelligentPresenceGraph= new Chart4jGraphGenerator();
		MSUIntelligentPresenceGraph.drawSNRtoDetectionGraph("Presence of PU in Cooperative Energy Detection (CED) with Intelligent Malicious User",DetectionGraph, inf, sup);

		//------------------------------------------------Assenza utente primario con utente malevolo intelligente-------------------//


		CooperativeEnergyDetectionAndFusionAbsence.clear() ;
		CooperativeEnergyDetectionOrFusionAbsence.clear(); ;
		CooperativeEnergyDetectionMajorityFusionAbsence.clear();
		userToBinaryDecisionAbsence.clear();
		DetectionGraph2.clear();

		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
		//mappa
		userToBinaryDecisionAbsence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers2, pfa);		

		//Gli utenti malevoli di questa tipologia generano l'opposto dell'energy detector: riporta 1 se l'utente � assente, 0 se � presente
		userToBinaryDecisionAbsence.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers2, pfa)); 

		CooperativeEnergyDetectionAndFusionAbsence=FC.andDecision(inf, sup,userToBinaryDecisionAbsence);
		CooperativeEnergyDetectionOrFusionAbsence=FC.orDecision(inf, sup,userToBinaryDecisionAbsence);
		CooperativeEnergyDetectionMajorityFusionAbsence=FC.majorityDecision(inf, sup,userToBinaryDecisionAbsence);

		DetectionGraph2.put("CED with AND fusion", CooperativeEnergyDetectionAndFusionAbsence);
		DetectionGraph2.put("CED with OR fusion", CooperativeEnergyDetectionOrFusionAbsence);
		DetectionGraph2.put("CED with MAJORITY fusion", CooperativeEnergyDetectionMajorityFusionAbsence);

		Chart4jGraphGenerator MSUIntelligentAbsenceGraph= new Chart4jGraphGenerator();
		 MSUIntelligentAbsenceGraph.drawSNRtoDetectionGraph("Absence of PU in Cooperative Energy Detection (CED) with Intelligent Malicious User",DetectionGraph2, inf, sup);
	}


}
