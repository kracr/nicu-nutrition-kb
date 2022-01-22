package com.inicu.device.camera;

import java.io.IOException;
import java.text.ParseException;

import javax.mail.Message.RecipientType;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class CameraThread extends Thread{

	@Autowired
	DeviceDataService deviceservice;

	public String timeout;
	public CameraThread() {
		
	}

	public CameraThread(String s) {
		this.timeout = s;
		
	}

	@Override
	public void run() {
		timeout = BasicConstants.props.getProperty(BasicConstants.IMAGE_TIMEOUT);
		System.out.println("running service to delete previous records of camera after "+timeout+" minutes");
		while(true){
			System.out.println("timeout "+timeout);
		deviceservice.deleteCameraFeed(timeout);
		try {
			Thread.sleep(Integer.valueOf(timeout)*60000);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	

}
