package com.inicu.models;

import java.util.Date;
import java.util.List;

public class GrowthChartJSON {
	
	  private String weight;
	  private String height;
	  private String headcircum;
	  private GestationObj gestation;
	  private Float gestationIdToSort;
	//  private float gest;
	  public String getWeight()
	  {
		  return this.weight;
	  }
	  public void setWeight(String weight)
	  {
		  this.weight=weight;
	  }
	  public String getHeight()
	  {
		  return this.height;
	  }
	  public void setHeight(String height)
	  {
		  this.height=height;
	  }
	  public GestationObj getGestation()
	  {
		  return this.gestation;
	  }
	  public void setGestation(GestationObj gestation)
	  {
		  this.gestation=gestation;
	  }
	  public String getHeadcircum()
	  {
		  return this.headcircum;
	  }
	  public void setHeadcircum(String headcircum)
	  {
		  this.headcircum=headcircum;
	  }
	 /* public float getGest()
	  {
		  return this.gest;
	  }
       public void setGest(float gest)
       {
    	   this.gest=gest;
       }*/
	public Float getGestationIdToSort() {
		return gestationIdToSort;
	}
	public void setGestationIdToSort(Float gestationIdToSort) {
		this.gestationIdToSort = gestationIdToSort;
	}
	
	
	
}
