package com.inicu.models;

import java.util.List;

public class ChartGraphDataJson {

	List<ChartExpectancyDataJson> length;
	List<ChartExpectancyDataJson> weight;
	List<ChartExpectancyDataJson> headCircum;
	
	public List<ChartExpectancyDataJson> getLength() {
		return length;
	}
	public void setLength(List<ChartExpectancyDataJson> length) {
		this.length = length;
	}
	public List<ChartExpectancyDataJson> getWeight() {
		return weight;
	}
	public void setWeight(List<ChartExpectancyDataJson> weight) {
		this.weight = weight;
	}
	public List<ChartExpectancyDataJson> getHeadCircum() {
		return headCircum;
	}
	public void setHeadCircum(List<ChartExpectancyDataJson> headCircum) {
		this.headCircum = headCircum;
	}
}
