package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;
import it.sp4te.css.agents.FusionCenter;
import it.sp4te.css.agents.MaliciousSecondaryUser;
import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.agents.TrustedSecondaryUser;
import it.sp4te.css.graphgenerator.*;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.SignalProcessor;
import it.sp4te.css.signalprocessing.Utils;

/**Questa classe modella uno scenario cooperativo  in cui sono presenti  utenti secondari fidati e non,
 * utilizzando la tecnica basata sulla divisione in liste degli utenti**/
public class ListCSS {

	
	public static void main(String args[]) throws Exception {
		ArrayList<TrustedSecondaryUser> TrustedSecondaryUsers;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers;

		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOpposite=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligent=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence2=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOpposite2=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligent2=new HashMap<String,ArrayList<ArrayList<Integer>>>();

		
	//	ArrayList<Double> ListCooperativeEnergyDetection = new ArrayList<Double>();;
		ArrayList<Double> ListCooperativeEnergyDetectionAbsence = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionAbsence = new ArrayList<Double>();;
		ArrayList<Double> ListCooperativeEnergyDetectionIntelligent = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionIntelligent = new ArrayList<Double>();;
		ArrayList<Double> ListCooperativeEnergyDetectionOpposite = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionOpposite = new ArrayList<Double>();;

		//ArrayList<Double> ListCooperativeEnergyDetectionOpposite = new ArrayList<Double>();;
		//ArrayList<Double> ListCooperativeEnergyDetectionIntelligent = new ArrayList<Double>();;
        HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();


		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts = 10;
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

		for(int i=1;i<11;i++){
			numberTSU=50-i;
			numberMSU=i;
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionIntelligent2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionIntelligent2.putAll(Utils.genereteIntelligentMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionIntelligent=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionIntelligent2,attempts);
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionOpposite2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionOpposite2.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionOpposite=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionOpposite2,attempts);
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionAbsence2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionAbsence2.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
			ReputationEnergyDetectionAbsence=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionAbsence2,attempts);
			
			
		for(int delta=1;delta<10;delta++){
			L=N+(delta*j);
			K=M+delta;
		
			DetectionGraph.clear();
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionIntelligent=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecisionIntelligent.putAll(Utils.genereteIntelligentMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionIntelligent= FC.ListBasedDecision(inf, sup, userToBinaryDecisionIntelligent, attempts, K, L, M, N,"_MSU"+numberMSU+"Intelligent");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionIntelligent);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionIntelligent);

		JFreeChartGraphGenerator graphIntelligent= new JFreeChartGraphGenerator("Presence of PU in Cooperative ED");
		graphIntelligent.drawAndSaveGraph("",DetectionGraph, inf, sup, "/home/sp4te/Scrivania/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU+".jpg");;

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionOpposite=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionOpposite.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionOpposite= FC.ListBasedDecision(inf, sup, userToBinaryDecisionOpposite, attempts, K, L, M, N,"MSU"+numberMSU+"_Opposite");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionOpposite);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionOpposite);

		JFreeChartGraphGenerator graphOpposite= new JFreeChartGraphGenerator("Presence of PU in Cooperative ED");
		graphOpposite.drawAndSaveGraph("",DetectionGraph, inf, sup, "/home/sp4te/Scrivania/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU+".jpg");;
		//GraphGenerator.drawGraph("Presence of PU in List Cooperative Energy Detection.30% MSU ",DetectionGraph, inf, sup);

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();


		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionAbsence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionAbsence.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		ListCooperativeEnergyDetectionAbsence= FC.ListBasedDecision(inf, sup, userToBinaryDecisionAbsence, attempts, K, L, M, N,"MSU"+numberMSU+"_Absence");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionAbsence);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionAbsence);

		JFreeChartGraphGenerator graphAbsence= new JFreeChartGraphGenerator("Presence of PU in Cooperative ED");

		graphAbsence.drawAndSaveGraph("",DetectionGraph, inf, sup, "/home/sp4te/Scrivania/Output/"+K+L+M+N+"_AbsenceMSU"+numberMSU+".jpg");;







		
		}}}}


