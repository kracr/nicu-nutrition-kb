package com.inicu.models;

import com.inicu.postgres.entities.SaCnsIvh;

public class IVHCurrentEventPOJO {
	
	SaCnsIvh currentIvh;

	public IVHCurrentEventPOJO() {
		super();
		this.currentIvh = new SaCnsIvh();
	}

	public SaCnsIvh getCurrentIvh() {
		return currentIvh;
	}

	public void setCurrentIvh(SaCnsIvh currentIvh) {
		this.currentIvh = currentIvh;
	}
}
