package com.inicu.models;

import java.util.List;

public class GrowthChartMainJSON {
	
	List<GrowthChartJSON> graphJson;
	String uhid;
	public List<GrowthChartJSON> getListGraph()
	{
		return this.graphJson;
	}
	public void setListGraph(List<GrowthChartJSON> graphJson)
	{
		this.graphJson=graphJson;
	}
	public String getUhid()
	{
		return this.uhid;
	}
	public void setUhid(String uhid)
	{
		this.uhid=uhid;
	}
	

}
