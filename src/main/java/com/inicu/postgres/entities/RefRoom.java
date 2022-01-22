package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * The persistent class for the ref_room database table.
 * 
 */
@Entity
@Table(name="ref_room")
@NamedQuery(name="RefRoom.findAll", query="SELECT r FROM RefRoom r")
public class RefRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "room_seq_id", strategy = "com.inicu.postgres.utility.MedicineIDGenerator", parameters = @Parameter(name = "name", value = "RM-ref_room-roomid") )
	@GeneratedValue(generator = "room_seq_id")
	private String roomid;

	private String description;
	private String loggeduser;

	private String roomname;
	@Column(columnDefinition="boolean")
	private Boolean isactive;
	
	private String branchname;

	public RefRoom() {
	}

	public String getRoomid() {
		return this.roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomname() {
		return this.roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	@Override
	public String toString() {
		return "RefRoom [roomid=" + roomid + ", description=" + description + ", loggeduser=" + loggeduser
				+ ", roomname=" + roomname + ", isactive=" + isactive + ", branchname=" + branchname + "]";
	}
	
}