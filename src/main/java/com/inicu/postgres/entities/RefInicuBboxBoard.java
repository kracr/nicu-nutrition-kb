package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ref_inicu_bbox_boards database table.
 * 
 */
@Entity
@Table(name="ref_inicu_bbox_boards")
@NamedQuery(name="RefInicuBboxBoard.findAll", query="SELECT r FROM RefInicuBboxBoard r")
public class RefInicuBboxBoard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="board_id")
	private Long boardId;

	@Column(columnDefinition="bool")
	private Boolean active;

	@Column(name="bboard_macid")
	private String bboardMacid;

	@Column(name="bboard_name")
	private String bboardName;

	private Long bboxid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	public RefInicuBboxBoard() {
	}

	public Long getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBboardMacid() {
		return this.bboardMacid;
	}

	public void setBboardMacid(String bboardMacid) {
		this.bboardMacid = bboardMacid;
	}

	public String getBboardName() {
		return this.bboardName;
	}

	public void setBboardName(String bboardName) {
		this.bboardName = bboardName;
	}

	public Long getBboxid() {
		return this.bboxid;
	}

	public void setBboxid(Long bboxid) {
		this.bboxid = bboxid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

}