package it.sp4te.css.agents;

import java.util.*;

import it.sp4te.css.detection.Detector;
import it.sp4te.css.model.Signal;
import it.sp4te.css.signalprocessing.Moment;
import it.sp4te.css.signalprocessing.SignalProcessor;

/**
 * <p>Titolo: SecondaryUser</p>
 * <p>Descrizione della classe: Questa classe si occupa di effettuare tutte le operazione relative allo
 * Spectrum sensing: Calcolo dei momenti del secondo e quarto ordine nelle due
 * ipotesi,calcolo dell'energia dei momenti,calcolo dei vettori PR,spectrum
 * sensing con Energy Detector,spectrum sensing con Detector proposto</p>
 * @author Pietro Coronas
 **/

public class SecondaryUser {
	ArrayList<Moment> MomentsSignal;
	ArrayList<Moment> MomentsNoise;
	ArrayList<ArrayList<Double>> MediumEnergy;

	/**
	 * Il costruttore inizializza i valori che saranno usati nei diversi tipi di
	 * detection.
	 * 
	 * @param s Segnale su cui effettuare la Detection
	 * @param length lunghezza del segnale
	 * @param energy Energia del segnale
	 * @param attempts Numero di prove su cui viene effettuata la simulazione
	 * @param inf Estremo inferiore di SNR su cui � stata effettuata la simulazione
	 * @param sup Estremo superiore di SNR su cui � stata effettuata la simulazione
	 * @see SignalProcessor#computeMoment
	 * @see SignalProcessor#computeEnergy
	 * @see SignalProcessor#computePr
	 * @see SignalProcessor#computeMediumEnergy
	 * 
	 **/

	public SecondaryUser(Signal s, int length, double energy, int attempts, int inf, int sup,int block) {
		// Genero i momenti nelle due ipotesi h0 e h1
		MomentsSignal = SignalProcessor.computeMoment(s, length, energy, attempts, inf, sup);
		MomentsNoise =SignalProcessor.computeMoment(null, length, energy, attempts, inf, sup);
		MediumEnergy=SignalProcessor.computeMediumEnergy(s, length, energy, attempts, inf, sup, block);

	}

	/**
	 * Metodo per lo Spectrum Senging dell'energy Detector. 
	 * 
	 * @param block Numero di blocchi in cui dividere il segnale per l'energy Detector
	 * @param pfa Probabilit� di falso allarme
	 * @return Array con le percentuali di detection ordinate per SNR
	 * @throws Exception Pfa deve essere scelto in modo che 1-2pfa sia compreso tra -1 e 1
	 * @see Detection#energyDetection
	 * @see #orderSignal
	 * @see Threshold#energyDetectorThreshold
	 **/

	public ArrayList<Double> spectrumSensingEnergyDetector(Double pfa) throws Exception{
		HashMap<Double, Double> EnergyDetection = new HashMap<Double, Double>();
		ArrayList<ArrayList<Double>> MomentNoiseEnergy = SignalProcessor.momentEnergy(MomentsNoise);
		
		for (int i = 0; i < MediumEnergy.size(); i++) {
			Double ED = Detector.energyDetection(
					SignalProcessor.energyDetectorThreshold(pfa, MomentNoiseEnergy.get(i)), MediumEnergy.get(i));
			EnergyDetection.put(this.MomentsSignal.get(i).getSnr(), ED);
		}

		return orderSignal(EnergyDetection);
	}
	
	
	/**
	 * Metodo per lo Spectrum Senging dell'energy Detector effettuato senza dividere il segnale in blocchi,
	 * considerando ogni singolo valore dell'energia. 
	 * 
	 * @param pfa Probabilit� di falso allarme
	 * @return Array con le percentuali di detection ordinate per SNR
	 * @throws Exception  Pfa deve essere scelto in modo che 1-2pfa sia compreso tra -1 e 1
	 * @see Detection#TraditionalEnergyDetection
	 * @see #orderSignal
	 * @see Threshold#energyDetectorThreshold
	 **/
	
	public ArrayList<Double> spectrumSensingTraditionalEnergyDetector(double pfa) throws Exception {
		HashMap<Double, Double> EnergyDetection = new HashMap<Double, Double>();
		ArrayList<ArrayList<Double>> MomentSignalEnergy = SignalProcessor.momentEnergy(MomentsSignal);
		ArrayList<ArrayList<Double>> MomentNoiseEnergy = SignalProcessor.momentEnergy(MomentsNoise);
		
		for (int i = 0; i < MomentSignalEnergy.size(); i++) {
			Double ED = Detector.energyDetection(
					SignalProcessor.energyDetectorThreshold(pfa, MomentNoiseEnergy.get(i)), MomentSignalEnergy.get(i));
			EnergyDetection.put(this.MomentsSignal.get(i).getSnr(), ED);
		}

		return orderSignal(EnergyDetection);
	}

	/**
	 * Metodo per lo Spectrum sensing del metodo proposto. Il procedimento � simile a quello dell'energy Detector ma con
	 * la differenza che utilizza gli oggetti PR al posto dei momenti del secondo e quarto ordine.
	 * 
	 * @param pfa Probabilit� di falso allarme
	 * @return Array con le percentuali di detection ordinate per SNR
	 * @throws Exception Pfa deve essere scelto in modo che 1-2pfa sia compreso tra -1 e 1
	 * @see Detection#proposedMethodDetection
	 * @see #orderSignal
	 * @see Threshold#proposedThreshold
	 * 
	 **/

	public ArrayList<Double> spectrumSensingProposedDetector(Double pfa) throws Exception {
		HashMap<Double, Double> ProposedDetection = new HashMap<Double, Double>();
		ArrayList<ArrayList<Double>> PrSignal = SignalProcessor.computePr(MomentsSignal);
		ArrayList<ArrayList<Double>> PrNoise = SignalProcessor.computePr(MomentsNoise);
		
		for (int i = 0; i < PrSignal.size(); i++) {
			Double PD = Detector.proposedMethodDetection(SignalProcessor.proposedThreshold(pfa, PrNoise.get(i)),
					PrSignal.get(i));
			ProposedDetection.put(this.MomentsSignal.get(i).getSnr(), PD);
		}
		return orderSignal(ProposedDetection);
	}

	/**
	 * Metodo per ordinare una mappa in base alla chiave.
	 * In questo caso � utilizzato su una mappa che ha come chiave l'SNR e come valore la % di Detection Relativa.
	 * 
	 * @param signalmapToOrder mappa con chiave SNR e valore la relativa % di detection 
	 * @return la mappa ordinata in base all'SNR
	 **/

	public static ArrayList<Double> orderSignal(HashMap<Double, Double> signalmapToOrder) {

		ArrayList<Double> snr = new ArrayList<Double>();
		for (Double key : signalmapToOrder.keySet()) {
			snr.add(key);
		}
		Collections.sort(snr);

		ArrayList<Double> Edetection = new ArrayList<Double>();
		for (Double key : snr) {
			Edetection.add(signalmapToOrder.get(key));
		}
		return Edetection;
	}

}