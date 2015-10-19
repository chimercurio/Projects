package it.sp4te.CooperativeSpectrumSensing.Agents;

import java.util.*;

import it.sp4te.CooperativeSpectrumSensing.Detection.Detection;
import it.sp4te.CooperativeSpectrumSensing.Detection.Threshold;
import it.sp4te.CooperativeSpectrumSensing.DomainModel.Signal;
import it.sp4te.CooperativeSpectrumSensing.Functions.Moment;
import it.sp4te.CooperativeSpectrumSensing.Functions.Pr;
import it.sp4te.CooperativeSpectrumSensing.Functions.SignalFunctions;

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
	ArrayList<ArrayList<Double>> MomentSignalEnergy;
	ArrayList<ArrayList<Double>> MomentNoiseEnergy;
	ArrayList<Pr> PrSignal;
	ArrayList<Pr> PrNoise;

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
	 * @see momentGenerator
	 * @see momentEnergy
	 * @see prGenerators
	 **/

	public SecondaryUser(Signal s, int length, double energy, int attempts, int inf, int sup) {
		// Genero i momenti nelle due ipotesi h0 e h1
		MomentsSignal = SignalFunctions.momentGenerator(s, length, energy, attempts, inf, sup);
		MomentsNoise = SignalFunctions.momentGenerator(null, length, energy, attempts, inf, sup);

		// Calcolo l'energia
		MomentSignalEnergy = SignalFunctions.momentEnergy(MomentsSignal);
		MomentNoiseEnergy = SignalFunctions.momentEnergy(MomentsNoise);

		// Calcolo pr
		PrSignal = SignalFunctions.prGenerator(MomentsSignal);
		PrNoise = SignalFunctions.prGenerator(MomentsNoise);

	}

	/**
	 * Metodo per lo Spectrum Senging dell'energy Detector. 
	 * 
	 * @param block Numero di blocchi in cui dividere il segnale per l'energy Detector
	 * @param pfa Probabilit� di falso allarme
	 * @return Array con le percentuali di detection ordinate per SNR
	 * @throws Exception 
	 * @see energyDetection
	 * @see energyDetectorThreshold
	 * @see orderSignal
	 **/

	public ArrayList<Double> spectrumSensingEnergyDetector(int block,Double pfa) throws Exception{
		HashMap<Double, Double> EnergyDetection = new HashMap<Double, Double>();

		for (int i = 0; i < this.MomentSignalEnergy.size(); i++) {
			Double ED = Detection.energyDetection(
					Threshold.energyDetectorThreshold(pfa, this.MomentNoiseEnergy.get(i)), MomentSignalEnergy.get(i),block);
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
	 * @throws Exception 
	 * @see TraditionalenergyDetection
	 * @see energyDetectorThreshold
	 * @see orderSignal
	 **/
	public ArrayList<Double> spectrumSensingTraditionalEnergyDetector(double pfa) throws Exception {
		HashMap<Double, Double> EnergyDetection = new HashMap<Double, Double>();

		for (int i = 0; i < this.MomentSignalEnergy.size(); i++) {
			Double ED = Detection.TraditionalEnergyDetection(
					Threshold.energyDetectorThreshold(pfa, this.MomentNoiseEnergy.get(i)), MomentSignalEnergy.get(i));
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
	 * @throws Exception 
	 * @see proposedMethodDetection
	 * @see proposedThreshold
	 * @see orderSignal
	 **/

	public ArrayList<Double> spectrumSensingProposedDetector(Double pfa) throws Exception {
		HashMap<Double, Double> ProposedDetection = new HashMap<Double, Double>();

		for (int i = 0; i < this.MomentSignalEnergy.size(); i++) {
			Double PD = Detection.proposedMethodDetection(Threshold.proposedThreshold(pfa, this.PrNoise.get(i)),
					this.PrSignal.get(i));
			ProposedDetection.put(this.MomentsSignal.get(i).getSnr(), PD);
		}
		return orderSignal(ProposedDetection);
	}

	/**
	 * Metodo per ordinare unamappa SNR->Detection.
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