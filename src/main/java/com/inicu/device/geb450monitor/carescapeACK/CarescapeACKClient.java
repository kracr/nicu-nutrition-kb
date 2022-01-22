package com.inicu.device.geb450monitor.carescapeACK;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class CarescapeACKClient implements Runnable {

	private int PORT = 0;
	private String ipAddress = "";
	public String encodedMessage = "";
	public String prevEncodedMessage = "";

	public CarescapeACKClient(String ipAddress, int PORT) {
		this.PORT = PORT;
		this.ipAddress = ipAddress;
	}

	@Override
	public void run() {

		Socket socket = null;
		DataInputStream input = null;
		String line = "";
		try {
			socket = new Socket(ipAddress, PORT);
			input = new DataInputStream(socket.getInputStream());

			while (true) {
				while ((line = input.readLine()) != null) {
					line = line + "\r\n";

					if (line.contains("MSH")) {
						line = line.substring(1, line.length() - 1);
						try {
							if (prevEncodedMessage.length() != 0) {
								if (!prevEncodedMessage.equalsIgnoreCase(encodedMessage)) {
									GEB450ParseData.parse(encodedMessage);
								}
							}

							prevEncodedMessage = encodedMessage;
							encodedMessage = "";
							encodedMessage = encodedMessage + line;

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						if (line.length() > 2) {
							encodedMessage = encodedMessage + line;
						}
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}

	}

}
