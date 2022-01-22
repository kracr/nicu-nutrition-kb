package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inicu_devices database table.
 * 
 */
@Entity
@Table(name="ref_inicu_devices")
@NamedQuery(name="InicuDevice.findAll", query="SELECT i FROM InicuDevice i")
public class InicuDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long deviceid;

	private String brandname;

	private String devicename;

	private String communicationtype;

	//bi-directional many-to-one association to InicuDeviceType
	@ManyToOne
	@JoinColumn(name="devicetypeid")
	private InicuDeviceType inicuDeviceType;

	public InicuDevice() {
	}

	public Long getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getDevicename() {
		return this.devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public InicuDeviceType getInicuDeviceType() {
		return this.inicuDeviceType;
	}

	public void setInicuDeviceType(InicuDeviceType inicuDeviceType) {
		this.inicuDeviceType = inicuDeviceType;
	}

	public String getCommunicationtype() {
		return communicationtype;
	}

	public void setCommunicationtype(String communicationtype) {
		this.communicationtype = communicationtype;
	}
}