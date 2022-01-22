package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class NiceGraphObj {


	List<HashMap<Object, HashMap<Object, Object>>> plotData;
	HashMap<Object, Object[]> graphPoints;
	
	
	public List<HashMap<Object, HashMap<Object, Object>>> getPlotData() {
		return plotData;
	}
	public HashMap<Object, Object[]> getGraphPoints() {
		return graphPoints;
	}
	public void setPlotData(List<HashMap<Object, HashMap<Object, Object>>> plotData) {
		this.plotData = plotData;
	}
	public void setGraphPoints(HashMap<Object, Object[]> graphPoints) {
		this.graphPoints = graphPoints;
	}
	
}
