
package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespBpd;


public class RespSysBpdPOJO {

	private SaRespBpd currentBpd;
	private Object PMA;
//	private RespSupport respSupport;
	
	private List<SaRespBpd> pastBpdList;

	private Boolean BpdEvent;
	public RespSysBpdPOJO() {
		super();
		this.currentBpd = new SaRespBpd();
//		this.respSupport = new RespSupport();
		this.BpdEvent = false;
	}

	public Object getPMA() {
		return PMA;
	}

	public void setPMA(Object pMA) {
		PMA = pMA;
	}

	public Boolean getBpdEvent() {
		return BpdEvent;
	}

	public void setBpdEvent(Boolean bpdEvent) {
		BpdEvent = bpdEvent;
	}

	public SaRespBpd getCurrentBpd() {
		return currentBpd;
	}

	public void setCurrentBpd(SaRespBpd currentBpd) {
		this.currentBpd = currentBpd;
	}

//	public RespSupport getRespSupport() {
//		return respSupport;
//	}
//
//	public void setRespSupport(RespSupport respSupport) {
//		this.respSupport = respSupport;
//	}

	public List<SaRespBpd> getPastBpdList() {
		return pastBpdList;
	}

	public void setPastBpdList(List<SaRespBpd> pastBpdList) {
		this.pastBpdList = pastBpdList;
	}

	
	
}
