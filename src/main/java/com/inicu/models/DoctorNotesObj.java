package com.inicu.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.inicu.postgres.entities.VwDoctornotesFinal;

/**
 * 
 * @author iNICU 
 *
 */
public class DoctorNotesObj {
//@NotNull @Pattern(regexp="yyyy-MM-ddTHH:mm:ss.SSSZ")
	/*@Column(columnDefinition="String")
	@NotNull @Pattern(regexp="yyyy-MM-ddTHH:mm:ss.SSSZ")
	*/	private Object loggedInUserId;
		private List<String> noteEntryTimes;
		private Long totalinput;
		private Long totaloutput;
		private String currentage;
		private String dayAfterAdmission;
		private Float currentdateheight;
		private Float currentdateweight;
		private Float diffheight;
		private Float diffweight;
		private Date visitdate;
		private String entryTime;
		private VwDoctornotesFinal notes;
		
		public DoctorNotesObj() {
			this.totalinput=Long.valueOf("0");
			this.totaloutput=Long.valueOf("0");
			this.currentage = "";
			dayAfterAdmission = "";
			this.currentdateheight=Float.valueOf("0");
			this.currentdateweight=Float.valueOf("0");
			this.diffheight = Float.valueOf("0");
			this.diffweight =Float.valueOf("0");
			this.visitdate = new Date();
			this.loggedInUserId = "";
			this.notes = new VwDoctornotesFinal();
			noteEntryTimes = new ArrayList<String>();
		}
		
		
		public String getEntryTime() {
			return entryTime;
		}


		public void setEntryTime(String entryTime) {
			this.entryTime = entryTime;
		}


		public Object getLoggedInUserId() {
			return loggedInUserId;
		}
		public void setLoggedInUserId(Object loggedInUserId) {
			this.loggedInUserId = loggedInUserId;
		}
		public VwDoctornotesFinal getNotes() {
			return notes;
		}
		public void setNotes(VwDoctornotesFinal notes) {
			this.notes = notes;
		}
		public List<String> getNoteEntryTimes() {
			return noteEntryTimes;
		}
		public void setNoteEntryTimes(List<String> noteEntryTimes) {
			this.noteEntryTimes = noteEntryTimes;
		}
		public Long getTotalinput() {
			return totalinput;
		}
		public void setTotalinput(Long totalinput) {
			this.totalinput = totalinput;
		}
		public Long getTotaloutput() {
			return totaloutput;
		}
		public void setTotaloutput(Long totaloutput) {
			this.totaloutput = totaloutput;
		}
		public String getCurrentage() {
			return currentage;
		}
		public void setCurrentage(String currentage) {
			this.currentage = currentage;
		}
		public Float getCurrentdateheight() {
			return currentdateheight;
		}
		public void setCurrentdateheight(Float currentdateheight) {
			this.currentdateheight = currentdateheight;
		}
		public Float getCurrentdateweight() {
			return currentdateweight;
		}
		public void setCurrentdateweight(Float currentdateweight) {
			this.currentdateweight = currentdateweight;
		}
		public Float getDiffheight() {
			return diffheight;
		}
		public void setDiffheight(Float diffheight) {
			this.diffheight = diffheight;
		}
		public Float getDiffweight() {
			return diffweight;
		}
		public void setDiffweight(Float diffweight) {
			this.diffweight = diffweight;
		}
		public Date getVisitdate() {
			return visitdate;
		}
		public void setVisitdate(Date visitdate) {
			this.visitdate = visitdate;
		}
		public String getDayAfterAdmission() {
			return dayAfterAdmission;
		}
		public void setDayAfterAdmission(String dayAfterAdmission) {
			this.dayAfterAdmission = dayAfterAdmission;
		}
		
	

}
