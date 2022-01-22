package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import scala.math.BigInt;

import java.sql.Timestamp;


/**
 * The persistent class for the device_detail database table.
 * 
 */
@Entity
@Table(name="device_detail")
@NamedQuery(name="DeviceDetail.findAll", query="SELECT d FROM DeviceDetail d")
public class DeviceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long deviceid;

	@Column(columnDefinition="bool")
	private Boolean available;

	private Timestamp creationtime;

	private String description;

	private String devicebrand;

	private String deviceserialno;

	private String devicetype;

	private String domainid;

	private String ipaddress;

	private Timestamp modificationtime;

	private String portno;
	
	private String devicename;
	
	private String branchname;
	
	private long inicu_deviceid;
	
	private String usbport;
	
	private String baudrate;
	
	private String parity;
	
	private String stopbits;
	
	private String databits;

	private String tinydeviceserialno;

	@Column(columnDefinition="bool")
	private Boolean isboardactive;
	
	public long getInicu_deviceid() {
		return inicu_deviceid;
	}

	public void setInicu_deviceid(long inicu_deviceid) {
		this.inicu_deviceid = inicu_deviceid;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	@Column(columnDefinition="timetz")
	private Timestamp endtime;
	
	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public DeviceDetail() {
	}

	public Long getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}

	public Boolean getAvailable() {
		return this.available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDevicebrand() {
		return this.devicebrand;
	}

	public void setDevicebrand(String devicebrand) {
		this.devicebrand = devicebrand;
	}

	public String getDeviceserialno() {
		return this.deviceserialno;
	}

	public void setDeviceserialno(String deviceserialno) {
		this.deviceserialno = deviceserialno;
	}

	public String getDevicetype() {
		return this.devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getDomainid() {
		return this.domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getPortno() {
		return this.portno;
	}

	public void setPortno(String portno) {
		this.portno = portno;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public Boolean getIsboardactive() {
		return isboardactive;
	}

	public void setIsboardactive(Boolean isboardactive) {
		this.isboardactive = isboardactive;
	}

	public String getUsbport() {
		return usbport;
	}

	public void setUsbport(String usbport) {
		this.usbport = usbport;
	}

	public String getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	public String getStopbits() {
		return stopbits;
	}

	public void setStopbits(String stopbits) {
		this.stopbits = stopbits;
	}

	public String getDatabits() {
		return databits;
	}

	public void setDatabits(String databits) {
		this.databits = databits;
	}

	public String getTinydeviceserialno() {
		return tinydeviceserialno;
	}

	public void setTinydeviceserialno(String tinydeviceserialno) {
		this.tinydeviceserialno = tinydeviceserialno;
	}
}