package com.inicu.models;

import com.inicu.postgres.entities.SaCnsAsphyxia;

public class AshpyxiaCurrentEventPOJO {

	SaCnsAsphyxia saCnsAsphyxia;
	
	public AshpyxiaCurrentEventPOJO() {
		super();
		this.saCnsAsphyxia = new SaCnsAsphyxia();
	}

	public SaCnsAsphyxia getSaCnsAsphyxia() {
		return saCnsAsphyxia;
	}

	public void setSaCnsAsphyxia(SaCnsAsphyxia saCnsAsphyxia) {
		this.saCnsAsphyxia = saCnsAsphyxia;
	}
}
