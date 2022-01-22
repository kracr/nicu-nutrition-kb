package com.inicu.postgres.entities;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the inicu_device_type database table.
 * 
 */
@Entity
@Table(name="ref_device_type")
@NamedQuery(name="InicuDeviceType.findAll", query="SELECT i FROM InicuDeviceType i")
public class InicuDeviceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long devicetypeid;

	private String description;

	@Column(name="device_type")
	private String deviceType;

	//bi-directional many-to-one association to InicuDevice
	@OneToMany(mappedBy="inicuDeviceType")
	private List<InicuDevice> inicuDevices;

	public InicuDeviceType() {
	}

	public Long getDevicetypeid() {
		return this.devicetypeid;
	}

	public void setDevicetypeid(Long devicetypeid) {
		this.devicetypeid = devicetypeid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public List<InicuDevice> getInicuDevices() {
		return this.inicuDevices;
	}

	public void setInicuDevices(List<InicuDevice> inicuDevices) {
		this.inicuDevices = inicuDevices;
	}

	public InicuDevice addInicuDevice(InicuDevice inicuDevice) {
		getInicuDevices().add(inicuDevice);
		inicuDevice.setInicuDeviceType(this);

		return inicuDevice;
	}

	public InicuDevice removeInicuDevice(InicuDevice inicuDevice) {
		getInicuDevices().remove(inicuDevice);
		inicuDevice.setInicuDeviceType(null);

		return inicuDevice;
	}

}