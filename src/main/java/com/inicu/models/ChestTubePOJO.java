package com.inicu.models;

import com.inicu.postgres.entities.ProcedureChesttube;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public class ChestTubePOJO {

	ProcedureChesttube emptyTube;
	
	List<ProcedureChesttube> leftTubes;
	
	List<ProcedureChesttube> rightTubes;
	
	private ProcedureChesttube rightObjectToShow;
	
	private ProcedureChesttube leftObjectToShow;
	
	private Boolean isPastTubeRight;
	
	private Boolean isPastTubeLeft;

	@Transient
	private Boolean ischesttubeRight;

	@Transient
	private Boolean ischesttubeLeft;

	public ChestTubePOJO() {
		super();
		this.emptyTube = new ProcedureChesttube();
		this.leftTubes = new ArrayList<>();
		this.rightTubes = new ArrayList<>();
	}

	public Boolean getPastTubeRight() {
		return isPastTubeRight;
	}

	public void setPastTubeRight(Boolean pastTubeRight) {
		isPastTubeRight = pastTubeRight;
	}

	public Boolean getPastTubeLeft() {
		return isPastTubeLeft;
	}

	public void setPastTubeLeft(Boolean pastTubeLeft) {
		isPastTubeLeft = pastTubeLeft;
	}

	public Boolean getIschesttubeRight() {
		return ischesttubeRight;
	}

	public void setIschesttubeRight(Boolean ischesttubeRight) {
		this.ischesttubeRight = ischesttubeRight;
	}

	public Boolean getIschesttubeLeft() {
		return ischesttubeLeft;
	}

	public void setIschesttubeLeft(Boolean ischesttubeLeft) {
		this.ischesttubeLeft = ischesttubeLeft;
	}

	public ProcedureChesttube getEmptyTube() {
		return emptyTube;
	}


	public void setEmptyTube(ProcedureChesttube emptyTube) {
		this.emptyTube = emptyTube;
	}


	public List<ProcedureChesttube> getLeftTubes() {
		return leftTubes;
	}

	public List<ProcedureChesttube> getRightTubes() {
		return rightTubes;
	}

	public void setLeftTubes(List<ProcedureChesttube> leftTubes) {
		this.leftTubes = leftTubes;
	}

	public void setRightTubes(List<ProcedureChesttube> rightTubes) {
		this.rightTubes = rightTubes;
	}


	public ProcedureChesttube getRightObjectToShow() {
		return rightObjectToShow;
	}


	public ProcedureChesttube getLeftObjectToShow() {
		return leftObjectToShow;
	}


	public void setRightObjectToShow(ProcedureChesttube rightObjectToShow) {
		this.rightObjectToShow = rightObjectToShow;
	}


	public void setLeftObjectToShow(ProcedureChesttube leftObjectToShow) {
		this.leftObjectToShow = leftObjectToShow;
	}


	public Boolean getIsPastTubeRight() {
		return isPastTubeRight;
	}


	public Boolean getIsPastTubeLeft() {
		return isPastTubeLeft;
	}


	public void setIsPastTubeRight(Boolean isPastTubeRight) {
		this.isPastTubeRight = isPastTubeRight;
	}


	public void setIsPastTubeLeft(Boolean isPastTubeLeft) {
		this.isPastTubeLeft = isPastTubeLeft;
	}


	


	

}
