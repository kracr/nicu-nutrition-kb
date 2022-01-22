package com.inicu.analytics;

public class SignalResult {

	private String signalName=null;
	private double[] curveData = null;
	public String getSignalName() {
		return signalName;
	}
	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}
	public double[] getCurveData() {
		return curveData;
	}
	public void setCurveData(double[] curveData) {
		this.curveData = curveData;
	}
	public SignalResult(String inSignalName,double[] inCurveData)
	{
		this.signalName = inSignalName;
		this.curveData = inCurveData;
	}
}
