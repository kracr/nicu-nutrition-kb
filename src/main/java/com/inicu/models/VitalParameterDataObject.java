package com.inicu.models;

public class VitalParameterDataObject {

    private PatientChartData chartData;
    private PatientGraphData graphData;

    public PatientChartData getChartData() {
        return chartData;
    }

    public void setChartData(PatientChartData chartData) {
        this.chartData = chartData;
    }

    public PatientGraphData getGraphData() {
        return graphData;
    }

    public void setGraphData(PatientGraphData graphData) {
        this.graphData = graphData;
    }
}
