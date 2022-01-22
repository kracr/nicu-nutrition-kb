package com.inicu.postgres.utility;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.inicu.postgres.entities.Exceptions;

/**
 * Exception handling ...Saving data to Exceptions Table also sending mail to
 * ichrSupport.
 * 
 * @author iNICU
 *
 */

@Service
@Configurable
public class InicuDatabaseExeption extends Exception {

	private static final long serialVersionUID = 1L;

	@Autowired
	InicuPostgresUtililty postgresUtil;

	public InicuDatabaseExeption() {
		super();
	}

	public InicuDatabaseExeption(String message) {
		super(message);
	}

	public InicuDatabaseExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public InicuDatabaseExeption(Throwable cause) {
		super(cause);
	}

	public static Exceptions createExceptionObject(String type, String loggedInUserID, String uhid, String message,
			String companyId) {
		Exceptions exception = new Exceptions();
		if (type != null)
			exception.setExceptionType(type);
		else
			exception.setExceptionType("");
		if (loggedInUserID != null)
			exception.setLoggedinuser(loggedInUserID);
		else
			exception.setLoggedinuser("");
		if (message != null)
			exception.setMessage(message);
		else
			exception.setMessage("");
		if (uhid != null)
			exception.setUhid(uhid);
		else
			exception.setUhid("");

		return exception;
	}

	// Sourabh Verma changes on 5/5/17
	public void newException(String[] receiverArray, RecipientType type, String companyId, String loggedInUserID,
			String uhid, String messageType, String message) {
		String subject = "Notifying " + messageType + "";
		Exceptions exception = (Exceptions) createExceptionObject(messageType, loggedInUserID, uhid, message,
				companyId);
		try {
			postgresUtil.saveObject(exception);
			BasicUtils.sendMail(receiverArray, RecipientType.TO, message, subject, companyId);
		} catch (Exception e) {
			// throw new InicuDatabaseExeption(e.getCause());
			e.getCause();
		}
		// throw new InicuDatabaseExeption(message);
	}

}
