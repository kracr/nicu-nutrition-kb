package com.inicu.postgres.service;

import org.springframework.stereotype.Repository;

import com.inicu.postgres.entities.DeviceDetail;

@Repository
public interface PatientDeviceService {

	Integer getUniqueDomainId();

	boolean checkifIpExists(String ipAddress);


}
