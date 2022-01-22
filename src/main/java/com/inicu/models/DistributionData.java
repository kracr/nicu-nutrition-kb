package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class DistributionData {

	Double xmin;
	Double xmax;
	Double ymin;
	Double ymax;
	Double firstRange;
	Double secondRange;
	Double thirdRange;
	Double fourthRange;
	Double fifthRange;
	Double sixthRange;

	List<List<Double>> data;
	public Double getXmin() {
		return xmin;
	}
	public void setXmin(Double xmin) {
		this.xmin = xmin;
	}
	public Double getXmax() {
		return xmax;
	}
	public void setXmax(Double xmax) {
		this.xmax = xmax;
	}
	public Double getYmin() {
		return ymin;
	}
	public void setYmin(Double ymin) {
		this.ymin = ymin;
	}
	public Double getYmax() {
		return ymax;
	}
	public void setYmax(Double ymax) {
		this.ymax = ymax;
	}
	public List<List<Double>> getData() {
		return data;
	}
	public void setData(List<List<Double>> data) {
		this.data = data;
	}
	public Double getFirstRange() {
		return firstRange;
	}
	public void setFirstRange(Double firstRange) {
		this.firstRange = firstRange;
	}
	public Double getSecondRange() {
		return secondRange;
	}
	public void setSecondRange(Double secondRange) {
		this.secondRange = secondRange;
	}
	public Double getThirdRange() {
		return thirdRange;
	}
	public void setThirdRange(Double thirdRange) {
		this.thirdRange = thirdRange;
	}
	public Double getFourthRange() {
		return fourthRange;
	}
	public void setFourthRange(Double fourthRange) {
		this.fourthRange = fourthRange;
	}
	public Double getFifthRange() {
		return fifthRange;
	}
	public void setFifthRange(Double fifthRange) {
		this.fifthRange = fifthRange;
	}
	public Double getSixthRange() {
		return sixthRange;
	}
	public void setSixthRange(Double sixthRange) {
		this.sixthRange = sixthRange;
	}
	
}
