/**
 * 
 */
package com.inicu.hl7.data.acquisition.fileop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sanoob
 *
 */
public class FileOperation {

	public static String oldFilename = "";
	public static String newFileName = "";
	public static String path = "/home/inicu/data/";

	public void writeData(String data) {
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		try {
			File dataFile;
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMhh_mm");
			String timeStamp = dateFormat.format(new Date());
			if (newFileName.equals("")) {

				newFileName = path + timeStamp;

			} else {

				String fnameSplit[] = newFileName.split("_");
				int fMinute = Integer.parseInt(fnameSplit[1]);
				String dateSplit[] = timeStamp.split("_");
				int dateMin = Integer.parseInt(dateSplit[1]);
				if (Math.abs(dateMin - fMinute) > 29) {
					newFileName = path+ timeStamp;
				}
			}

			dataFile = new File(newFileName + ".txt");

			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}

			fileWriter = new FileWriter(dataFile.getAbsoluteFile(), true);
			bufferedWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(bufferedWriter);

			printWriter.println(data);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

				if (printWriter != null)
					printWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}

	public void readFile(String filePath) {

		File readFile = new File(filePath);
		FileInputStream fStream = null;
		BufferedReader fReader = null;
		String fLine = "";
		try {

			fStream = new FileInputStream(readFile);
			fReader = new BufferedReader(new InputStreamReader(fStream));
			while ((fLine = fReader.readLine()) != null) {
				System.out.println(fLine);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			try {
				if (fStream != null)
					fStream.close();
				if (fReader != null)
					fReader.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
