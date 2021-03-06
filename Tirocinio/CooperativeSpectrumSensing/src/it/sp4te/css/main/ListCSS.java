package it.sp4te.css.main;

import java.util.ArrayList;
import java.util.HashMap;
import it.sp4te.css.agents.FusionCenter;
import it.sp4te.css.agents.MaliciousSecondaryUser;
import it.sp4te.css.agents.PrimaryUser;
import it.sp4te.css.agents.TrustedNode;
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
		ArrayList<TrustedNode> TrustedNode;
		ArrayList<MaliciousSecondaryUser> MaliciousSecondaryUsers;

		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsence=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOpposite=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligent=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsenceReputation=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOppositeReputation=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligentReputation=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionAbsenceReputationTN=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionOppositeReputationTN=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		HashMap<String,ArrayList<ArrayList<Integer>>> userToBinaryDecisionIntelligentReputationTN=new HashMap<String,ArrayList<ArrayList<Integer>>>();
		
		ArrayList<Double> ListCooperativeEnergyDetectionAbsence = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionAbsence = new ArrayList<Double>();;
		ArrayList<Double> ReputationTNEnergyDetectionAbsence = new ArrayList<Double>();;
		
		ArrayList<Double> ListCooperativeEnergyDetectionIntelligent = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionIntelligent = new ArrayList<Double>();;
		ArrayList<Double> ReputationTNEnergyDetectionIntelligent = new ArrayList<Double>();;

		ArrayList<Double> ListCooperativeEnergyDetectionOpposite = new ArrayList<Double>();;
		ArrayList<Double> ReputationEnergyDetectionOpposite = new ArrayList<Double>();;
		ArrayList<Double> ReputationTNEnergyDetectionOpposite = new ArrayList<Double>();;


        HashMap<String, ArrayList<Double>> DetectionGraph = new HashMap<String, ArrayList<Double>>();


		// Setto i parametri
		int length = 1000; // poi 10000
		int attempts =600;
		int inf=-15;
		int sup=-5 ;
		int block=10; //blocchi energy Detector
		double pfa=0.01; //probabilit� di falso allarme
		int numberTSU;
		int numberMSU;
		int numberTN=10;//numero di utenti fidati
		int K; //GRIGIA->BIANCA
		int L;// NERA->BIANCA
		int M=8; //BIANCA->GRIGIA
		int N=10;//GRIGIA->NERA
        //int j=3;
		//Creo il Fusion center
		FusionCenter FC=new FusionCenter();
		//Creo l'utente primario
		PrimaryUser PU= new PrimaryUser();
		//creo il segnale
		Signal s = PU.createAndSend(length);
		String path="C:/Users/Pietro/Desktop/Output/";
		String pathTN="C:/Users/Pietro/Desktop/OutputTN/";
	
 for(int i=1;i<4;i++){
			numberTSU=27-i;
			numberMSU=i;
			numberTN=3;
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			
			userToBinaryDecisionIntelligentReputation=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionIntelligentReputation.putAll(Utils.genereteIntelligentOppositeMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionIntelligent=FC.reputationBasedDecision(path,inf, sup, userToBinaryDecisionIntelligentReputation,attempts,"intelligent"+"MSU"+numberMSU);
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionOppositeReputation=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionOppositeReputation.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionOpposite=FC.reputationBasedDecision(path,inf, sup, userToBinaryDecisionOppositeReputation,attempts,"opposite"+"MSU"+numberMSU);
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionAbsenceReputation=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionAbsenceReputation.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
			ReputationEnergyDetectionAbsence=FC.reputationBasedDecision(path,inf, sup, userToBinaryDecisionAbsenceReputation,attempts,"absence"+"MSU"+numberMSU);
			
			//----------------------------------------------------- With trusted Node-----------------------------------------------------------------//
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			TrustedNode= Utils.createTrustedNode(numberTN,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			
			userToBinaryDecisionIntelligentReputationTN=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionIntelligentReputationTN.putAll(Utils.genereteIntelligentOppositeMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationTNEnergyDetectionIntelligent=FC.reputationBasedWithTrustedNodeDecision(pathTN,inf, sup, userToBinaryDecisionIntelligentReputationTN,
					Utils.genereteTrustedNodeBinaryDecisionVectors(TrustedNode, pfa),attempts,"intelligent"+"MSU"+numberMSU);
			
			
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			TrustedNode= Utils.createTrustedNode(numberTN,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			
			userToBinaryDecisionOppositeReputationTN=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionOppositeReputationTN.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationTNEnergyDetectionOpposite=FC.reputationBasedWithTrustedNodeDecision(pathTN,inf, sup, userToBinaryDecisionOppositeReputationTN,
					Utils.genereteTrustedNodeBinaryDecisionVectors(TrustedNode, pfa),attempts,"opposite"+"MSU"+numberMSU);
			
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			TrustedNode.clear();
			
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			TrustedNode= Utils.createTrustedNode(numberTN,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			
			userToBinaryDecisionAbsenceReputationTN=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionAbsenceReputationTN.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
			ReputationTNEnergyDetectionAbsence=FC.reputationBasedWithTrustedNodeDecision(pathTN,inf, sup, userToBinaryDecisionAbsenceReputationTN,
					Utils.genereteTrustedNodeBinaryDecisionVectors(TrustedNode, pfa),attempts,"absence"+"MSU"+numberMSU);
			
			
//-----------------------------------------------------List Based-----------------------------------------------------------------//

//for(int delta=5;delta<10;delta++){
			L= 28; //N+(delta*j);
			K= 14; //M+delta;
			
			DetectionGraph.clear();
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionIntelligent=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecisionIntelligent.putAll(Utils.genereteIntelligentOppositeMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionIntelligent= FC.ListBasedDecision(path,inf, sup, userToBinaryDecisionIntelligent, attempts, K, L, M, N,"_MSU"+numberMSU+"Intelligent");

		
		DetectionGraph.put("ListBased",ListCooperativeEnergyDetectionIntelligent);
		DetectionGraph.put("Reputation",ReputationEnergyDetectionIntelligent);
		DetectionGraph.put("Reputation with TN",ReputationTNEnergyDetectionIntelligent);
		
		JFreeChartGraphGenerator graphIntelligent1= new JFreeChartGraphGenerator("MDT  in Cooperative ED");
		graphIntelligent1.drawAndSaveMDTtoSNRGraph("",DetectionGraph, inf, sup,"C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU+".jpg");;
        
		Utils.generateMDTText("MDT  in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU);
		
		DetectionGraph.clear();
		DetectionGraph.put("ListBased-Reputation", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionIntelligent,ReputationEnergyDetectionIntelligent));
		DetectionGraph.put("ListBased-Reputation with TN", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionIntelligent, ReputationTNEnergyDetectionIntelligent));

		JFreeChartGraphGenerator graphIntelligent= new JFreeChartGraphGenerator("MDT Ratio in Cooperative ED");
		graphIntelligent.drawAndSaveMDTtoSNRRatioGraph("",DetectionGraph, inf, sup,"C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU+"ratio.jpg");;
        
		Utils.generateMDTRatioText("MDT ratio in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU+"Ratio");

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionOpposite=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionOpposite.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionOpposite= FC.ListBasedDecision(path,inf, sup, userToBinaryDecisionOpposite, attempts, K, L, M, N,"MSU"+numberMSU+"_Opposite");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionOpposite);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionOpposite);
		DetectionGraph.put("Reputation with TN", ReputationTNEnergyDetectionOpposite);
		
		JFreeChartGraphGenerator graphOpposite2= new JFreeChartGraphGenerator("MDT  in Cooperative ED");
		graphOpposite2.drawAndSaveMDTtoSNRGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU+".jpg");;
        Utils.generateMDTText("MDT  in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU);

		DetectionGraph.clear();
		DetectionGraph.put("ListBased-Reputation", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionOpposite, ReputationEnergyDetectionOpposite));
		DetectionGraph.put("ListBased-Reputation with TN", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionOpposite, ReputationTNEnergyDetectionOpposite));

		JFreeChartGraphGenerator graphOpposite= new JFreeChartGraphGenerator("MDT Ratio in Cooperative ED");
		graphOpposite.drawAndSaveMDTtoSNRRatioGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU+"ratio.jpg");;
        Utils.generateMDTRatioText("MDT  Ratio in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU+"Ratio");

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();


		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,s,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionAbsence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionAbsence.putAll(Utils.genereteAbsenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		ListCooperativeEnergyDetectionAbsence= FC.ListBasedDecision(path,inf, sup, userToBinaryDecisionAbsence, attempts, K, L, M, N,"MSU"+numberMSU+"_Absence");

		
		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionAbsence);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionAbsence);
		DetectionGraph.put("Reputation with TN", ReputationTNEnergyDetectionAbsence);
		JFreeChartGraphGenerator graphAbsence2= new JFreeChartGraphGenerator("MDT  in Cooperative ED");

		graphAbsence2.drawAndSaveMDTtoSNRGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_AbsenceMSU"+numberMSU+".jpg");;
        Utils.generateMDTText("MDT  in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_AbsenceMSU"+numberMSU);

		
		
		DetectionGraph.clear();
		
		DetectionGraph.put("ListBased-Reputation", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionAbsence,ReputationEnergyDetectionAbsence));
		DetectionGraph.put("ListBased-Reputation with TN", SignalProcessor.computeMDTRatio(ListCooperativeEnergyDetectionAbsence, ReputationTNEnergyDetectionAbsence));



		JFreeChartGraphGenerator graphAbsence= new JFreeChartGraphGenerator("MDT Ratio in Cooperative ED");

		graphAbsence.drawAndSaveMDTtoSNRRatioGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_AbsenceMSU"+numberMSU+"ratio.jpg");;
        Utils.generateMDTRatioText("MDT Ratio in Cooperative ED", DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_AbsenceMSU"+numberMSU+"Ratio");

		}
	

}}
		/**for(int i=12;i<15;i++){
			numberTSU=30-i;
			numberMSU=i;
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionIntelligent2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionIntelligent2.putAll(Utils.genereteIntelligentPresenceMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionIntelligent=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionIntelligent2,attempts,"intelligent"+"MSU"+numberMSU);
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionOpposite2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionOpposite2.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
			ReputationEnergyDetectionOpposite=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionOpposite2,attempts,"opposite"+"MSU"+numberMSU);
			
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
			TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
			userToBinaryDecisionPresence2=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
			userToBinaryDecisionPresence2.putAll(Utils.generetePresenceBinaryDecisionVectors(MaliciousSecondaryUsers));
			ReputationEnergyDetectionPresence=FC.reputationBasedDecision(inf, sup, userToBinaryDecisionPresence2,attempts,"presence"+"MSU"+numberMSU);
			
			
		for(int delta=7;delta<8;delta++){
			L=N+(delta*j);
			K=M+delta;
		
			DetectionGraph.clear();
			TrustedSecondaryUsers.clear();
			MaliciousSecondaryUsers.clear();
			
		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		
		userToBinaryDecisionIntelligent=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		userToBinaryDecisionIntelligent.putAll(Utils.genereteIntelligentPresenceMaliciousBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionIntelligent= FC.ListBasedDecision(inf, sup, userToBinaryDecisionIntelligent, attempts, K, L, M, N,"_MSU"+numberMSU+"Intelligent");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionIntelligent);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionIntelligent);

		JFreeChartGraphGenerator graphIntelligent= new JFreeChartGraphGenerator("Absence of PU in Cooperative ED");
		graphIntelligent.drawAndSaveGraph("",DetectionGraph, inf, sup,"C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_IntelligentMSU"+numberMSU+".jpg");;

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();

		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionOpposite=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionOpposite.putAll(Utils.genereteOppositeBinaryDecisionVectors(MaliciousSecondaryUsers,pfa));
		ListCooperativeEnergyDetectionOpposite= FC.ListBasedDecision(inf, sup, userToBinaryDecisionOpposite, attempts, K, L, M, N,"MSU"+numberMSU+"_Opposite");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionOpposite);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionOpposite);

		JFreeChartGraphGenerator graphOpposite= new JFreeChartGraphGenerator("Absence of PU in Cooperative ED");
		graphOpposite.drawAndSaveGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_OppositeMSU"+numberMSU+".jpg");;

		DetectionGraph.clear();
		TrustedSecondaryUsers.clear();
		MaliciousSecondaryUsers.clear();


		TrustedSecondaryUsers= Utils.createTrustedSecondaryUsers(numberTSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);
		MaliciousSecondaryUsers=Utils.createMaliciousSecondaryUsers(numberMSU,null,s.getLenght(), SignalProcessor.computeEnergy(s), attempts, inf, sup, block);

		userToBinaryDecisionPresence=Utils.genereteBinaryDecisionVectors(TrustedSecondaryUsers, pfa);
		
		//Gli utenti malevoli di questa tipologia generano un vettore di decisioni in cui l'utente primario � sempre assente
		userToBinaryDecisionPresence.putAll(Utils.generetePresenceBinaryDecisionVectors(MaliciousSecondaryUsers));
		ListCooperativeEnergyDetectionPresence= FC.ListBasedDecision(inf, sup, userToBinaryDecisionPresence, attempts, K, L, M, N,"MSU"+numberMSU+"_Presence");

		DetectionGraph.put("ListBased", ListCooperativeEnergyDetectionPresence);
		DetectionGraph.put("Reputation", ReputationEnergyDetectionPresence);

		JFreeChartGraphGenerator graphAbsence= new JFreeChartGraphGenerator("Absence of PU in Cooperative ED");

		graphAbsence.drawAndSaveGraph("",DetectionGraph, inf, sup, "C:/Users/Pietro/Desktop/Output/"+K+L+M+N+"_PresenceMSU"+numberMSU+".jpg");;
		
		}}}}

**/
