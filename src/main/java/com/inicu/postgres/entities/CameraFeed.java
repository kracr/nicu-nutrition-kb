package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="camera_feed")
@NamedQuery(name="CameraFeed.findAll", query="SELECT b FROM CameraFeed b")
public class CameraFeed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long camera_feed_id;
	
	@Column(name="uhid")
	private String uhid;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(name="image_string")
	private String image_string;

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getImage_string() {
		return image_string;
	}

	public void setImage_string(String image_string) {
		this.image_string = image_string;
	}

	@Override
	public String toString() {
		return "CameraFeed [camera_feed_id=" + camera_feed_id + ", creationtime=" + creationtime + ", uhid="
				+ uhid + ", image_string="+"]";
	}
}
