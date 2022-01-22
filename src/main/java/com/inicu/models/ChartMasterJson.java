package com.inicu.models;

public class ChartMasterJson {

	ChartGraphDataJson graphData;
	InterGrowthChartGraphDataJson interGrowthGraphData;
	GrowthChartMainJSON plotData;

	public ChartGraphDataJson getGraphData() {
		return graphData;
	}

	public void setGraphData(ChartGraphDataJson graphData) {
		this.graphData = graphData;
	}

	public InterGrowthChartGraphDataJson getInterGrowthGraphData() {
		return interGrowthGraphData;
	}

	public void setInterGrowthGraphData(InterGrowthChartGraphDataJson interGrowthGraphData) {
		this.interGrowthGraphData = interGrowthGraphData;
	}

	public GrowthChartMainJSON getPlotData() {
		return plotData;
	}

	public void setPlotData(GrowthChartMainJSON plotData) {
		this.plotData = plotData;
	}
}
