package it.sp4te.css.agents;

import it.sp4te.css.model.Signal;

public class PrimaryUser {

	
	
	public Signal createAndSend(int length){
		Signal s = new Signal(length);
		return s;
	}
}