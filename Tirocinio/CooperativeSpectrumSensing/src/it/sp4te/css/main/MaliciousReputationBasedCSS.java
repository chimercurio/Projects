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

public class MaliciousReputationBasedCSS {

	public static void main(String args[]) throws Exception {
		ArrayList<Double> reputationBasedCSS = new ArrayList<Double>();;
		ArrayList<Double> MaliciousAbsenceReputationBasedCSS = new ArrayList<Double>();;
		ArrayList<Double> MaliciousPresenceReputationBasedCSS = new ArrayList<Double>();;
		ArrayList<Double> MaliciousOppositeReputationBasedCSS = new ArrayList<Double>();;
		

		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecision=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers;
		HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();

		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 200;
		int inf = -30;
		int sup = 5;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilit� di falso allarme
		int numberTSU=10;//numero di utenti fidati
		int numberMSU=5; //numero utenti malevoli

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
		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);

		//Tutte le decisioni di tutti gli utenti secondari passano al fusion center che riporter� una decisione
		//globale secondo un meccanismo di reputazione
		reputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("Without malicious users", reputationBasedCSS);

		userToBinaryDecision.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();


		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);


		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecision.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		MaliciousAbsenceReputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("With Absence malicious users", MaliciousAbsenceReputationBasedCSS);



		userToBinaryDecision.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecision.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		MaliciousOppositeReputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("With Opposite malicious users", MaliciousOppositeReputationBasedCSS);


		




		GraphGenerator.drawGraph("Reputation Based CSS: Presence of PU",DetectionGraph, inf, sup);

		//--------------------------ABSENCE------------------------------
		userToBinaryDecision.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();
		DetectionGraph.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		//Creo i vettori contenenti le decisioni binarie sulla presenza o assenza dell'utente primario.Le inserisco in una
		//mappa
		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);

		//Tutte le decisioni di tutti gli utenti secondari passano al fusion center che riporter� una decisione
		//globale secondo un meccanismo di reputazione
		reputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("Without malicious users", reputationBasedCSS);


		userToBinaryDecision.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);


		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecision.putAll(Utils.generetePresenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		MaliciousPresenceReputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("With Presence malicious users", MaliciousPresenceReputationBasedCSS);

		userToBinaryDecision.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecision=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecision.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		MaliciousOppositeReputationBasedCSS= FC.reputationBasedDecision(inf, sup, userToBinaryDecision, attempts);
		DetectionGraph.put("With Opposite malicious users", MaliciousOppositeReputationBasedCSS);


		GraphGenerator.drawGraph("Reputation Based CSS: Absence of PU",DetectionGraph, inf, sup);


	}}


