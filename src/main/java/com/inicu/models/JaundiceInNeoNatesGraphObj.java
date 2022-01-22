package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class JaundiceInNeoNatesGraphObj {

	private HashMap<Object,NiceGraphObj> niceGraph;
	
	private HashMap<Object, NicuGraphObj> bhutaniGraph;

	public HashMap<Object, NiceGraphObj> getNiceGraph() {
		return niceGraph;
	}

	public HashMap<Object, NicuGraphObj> getBhutaniGraph() {
		return bhutaniGraph;
	}

	public void setNiceGraph(HashMap<Object, NiceGraphObj> niceGraph) {
		this.niceGraph = niceGraph;
	}

	public void setBhutaniGraph(HashMap<Object, NicuGraphObj> bhutaniGraph) {
		this.bhutaniGraph = bhutaniGraph;
	}
	
	

	
	
}
