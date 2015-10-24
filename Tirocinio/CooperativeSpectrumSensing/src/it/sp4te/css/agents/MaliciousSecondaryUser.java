package it.sp4te.css.agents;

import java.util.ArrayList;

import it.sp4te.css.signalprocessing.SignalProcessor;

public class MaliciousSecondaryUser extends SecondaryUser {

	
	/**Questo metodo rappresenta il comportamento malevolo di un utente secondario, riportando un vettore di decisioni
	 * binarie che afferma l'assenza dell'utente primario per ogni SNR in ogni prova**/
	
	public ArrayList<ArrayList<Integer>> computeAbsenceBinaryDecision() throws Exception{
		ArrayList<ArrayList<Integer>> decisions= SignalProcessor.makeAbsenceDecisionVector(attempts, inf, sup);
		return decisions;	
		}
	
	public ArrayList<ArrayList<Integer>> computePresenceBinaryDecision() throws Exception{
		ArrayList<ArrayList<Integer>> decisions= SignalProcessor.makePresenceDecisionVector(attempts, inf, sup);
		return decisions;	
		}
}