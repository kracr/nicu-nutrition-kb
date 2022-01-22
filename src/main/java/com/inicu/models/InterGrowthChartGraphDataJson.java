package com.inicu.models;

import java.util.List;

public class InterGrowthChartGraphDataJson {

	List<InterGrowthChartExpectancyDataJson> length;
	List<InterGrowthChartExpectancyDataJson> weight;
	List<InterGrowthChartExpectancyDataJson> headCircum;

	public List<InterGrowthChartExpectancyDataJson> getLength() {
		return length;
	}

	public void setLength(List<InterGrowthChartExpectancyDataJson> length) {
		this.length = length;
	}

	public List<InterGrowthChartExpectancyDataJson> getWeight() {
		return weight;
	}

	public void setWeight(List<InterGrowthChartExpectancyDataJson> weight) {
		this.weight = weight;
	}

	public List<InterGrowthChartExpectancyDataJson> getHeadCircum() {
		return headCircum;
	}

	public void setHeadCircum(List<InterGrowthChartExpectancyDataJson> headCircum) {
		this.headCircum = headCircum;
	}
}
