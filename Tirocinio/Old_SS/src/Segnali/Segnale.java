package Segnali;

import java.util.ArrayList;


/**
 * @author  Pietro
 */
public class Segnale {

	/**
	 * @uml.property  name="lunghezzaSegnale"
	 */
	int lunghezzaSegnale;
	/**
	 * @uml.property  name="campioniSegnaleRe"
	 */
	ArrayList<Double> campioniSegnaleRe;
	/**
	 * @uml.property  name="campioniSegnaleImm"
	 */
	ArrayList<Double> campioniSegnaleImm;

	public Segnale(int lunghezzasegnale) {
		this.lunghezzaSegnale = lunghezzasegnale;
		this.campioniSegnaleRe = new ArrayList<Double>();
		this.campioniSegnaleImm = new ArrayList<Double>();
		for (int i = 0; i < this.lunghezzaSegnale; i++) {
			double v = Math.random();
			if (v < 0.5) {

				this.campioniSegnaleRe.add(i, v/Math.sqrt(this.lunghezzaSegnale));
				this.campioniSegnaleImm.add(i, v/Math.sqrt(this.lunghezzaSegnale) );
			} else {
				this.campioniSegnaleRe.add(i, -v/Math.sqrt(this.lunghezzaSegnale));
				this.campioniSegnaleImm.add(i,-v/Math.sqrt(this.lunghezzaSegnale));

			}
		}
	}

	/**
	 * @return
	 * @uml.property  name="lunghezzaSegnale"
	 */
	public int getLunghezzaSegnale() {
		return lunghezzaSegnale;
	}

	/**
	 * @return
	 * @uml.property  name="campioniSegnaleRe"
	 */
	public ArrayList<Double> getCampioniSegnaleRe() {
		return campioniSegnaleRe;
	}

	/**
	 * @return
	 * @uml.property  name="campioniSegnaleImm"
	 */
	public ArrayList<Double> getCampioniSegnaleImm() {
		return campioniSegnaleImm;
	}
	
	public double CalcoloPotenza(){
		double p=0.0;
		for(int i=0;i<this.lunghezzaSegnale;i++){
		p=p + Math.abs(Math.pow(this.campioniSegnaleRe.get(i),2))+Math.abs((Math.pow(this.campioniSegnaleImm.get(i),2)));
		}
		return p/this.lunghezzaSegnale;
	}
	
	
	

	/**
	 * @param campioniSegnaleRe
	 * @uml.property  name="campioniSegnaleRe"
	 */
	public void setCampioniSegnaleRe(ArrayList<Double> campioniSegnaleRe) {
		this.campioniSegnaleRe = campioniSegnaleRe;
	}

	/**
	 * @param campioniSegnaleImm
	 * @uml.property  name="campioniSegnaleImm"
	 */
	public void setCampioniSegnaleImm(ArrayList<Double> campioniSegnaleImm) {
		this.campioniSegnaleImm = campioniSegnaleImm;
	}
}
