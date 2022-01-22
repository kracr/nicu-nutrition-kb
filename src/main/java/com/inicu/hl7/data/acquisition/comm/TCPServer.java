package com.inicu.hl7.data.acquisition.comm;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.inicu.hl7.data.acquisition.HL7Parser;
import com.inicu.hl7.data.acquisition.fileop.FileOperation;


public class TCPServer implements Runnable {
	
	private int PORT = 0;

	public TCPServer(int PORT) {
		this.PORT = PORT;
	}

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		try {	
			byte[] buffer = null;
			serverSocket = new ServerSocket(PORT);
			Socket socket = serverSocket.accept();// establishes connection
			DataInputStream dataInput = new DataInputStream(socket.getInputStream());

			while (true) {

				while (dataInput.available() > 0) {
					buffer = new byte[dataInput.available()];
					dataInput.read(buffer);
					String sData = new String(buffer, "utf8");
					
					/*
					 * UNCOMMENT following lines to write data to file
					 */
					
//					new Thread(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							new FileOperation().writeData(sData);
//						}
//					}).start();
//					
					/*
					 * call method to parse hl7
					 */
					
					if( (Integer.valueOf(sData.charAt(0))==11) && (Integer.valueOf(sData.charAt(sData.length()-1))==13) )
					{
						String chunkData = sData.substring(1, sData.length()-3)+'\r';
						if(chunkData!=null&&!chunkData.isEmpty()){
							try{
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										
										new HL7Parser().parse(chunkData);
										
									}
								}).start();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						
					}

				}

			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}

	}

}
