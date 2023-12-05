package com.spec.asms.vo;

import com.spec.asms.common.Constants;

/**
 * @author bhavikg
 * value object for Leakage
 */
public class LeakageVO {

		private static int lkgId;
		private static int testingId;
		private static int createdBy;
		private static String createdOn;
		private static int updatedBy;
		private static String updatedOn;
		private static int isSync;
		private  String leakageName = Constants.LABEL_BLANK;
		private  boolean checked;
		
		private  int serviceLkgId;
		private  int servicetestingId;
		private  int servicecreatedBy;
		private  String servicecreatedOn;
		private  int serviceupdatedBy;
		private  String serviceupdatedOn;
		private  int serviceisSync;
		
		
		public String getLeakageName() {
			return leakageName;
		}
		public void setLeakageName(String leakageName) {
			this.leakageName = leakageName;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public static int getLkgId() {
			return lkgId;
		}
		public static void setLkgId(int lkgId) {
			LeakageVO.lkgId = lkgId;
		}
		public static int getTestingId() {
			return testingId;
		}
		public static void setTestingId(int testingId) {
			LeakageVO.testingId = testingId;
		}

		
		
		public static int getCreatedBy() {
			return createdBy;
		}
		public static void setCreatedBy(int createdBy) {
			LeakageVO.createdBy = createdBy;
		}
		public static String getCreatedOn() {
			return createdOn;
		}
		public static void setCreatedOn(String createdOn) {
			LeakageVO.createdOn = createdOn;
		}
		public static int getUpdatedBy() {
			return updatedBy;
		}
		public static void setUpdatedBy(int updatedBy) {
			LeakageVO.updatedBy = updatedBy;
		}
		public static String getUpdatedOn() {
			return updatedOn;
		}
		public static void setUpdatedOn(String updatedOn) {
			LeakageVO.updatedOn = updatedOn;
		}
		public static int getIsSync() {
			return isSync;
		}
		public static void setIsSync(int isSync) {
			LeakageVO.isSync = isSync;
		}
		public int getServiceLkgId() {
			return serviceLkgId;
		}
		public void setServiceLkgId(int serviceLkgId) {
			this.serviceLkgId = serviceLkgId;
		}
		public int getServicetestingId() {
			return servicetestingId;
		}
		public void setServicetestingId(int servicetestingId) {
			this.servicetestingId = servicetestingId;
		}
		public int getServicecreatedBy() {
			return servicecreatedBy;
		}
		public void setServicecreatedBy(int servicecreatedBy) {
			this.servicecreatedBy = servicecreatedBy;
		}
		public String getServicecreatedOn() {
			return servicecreatedOn;
		}
		public void setServicecreatedOn(String servicecreatedOn) {
			this.servicecreatedOn = servicecreatedOn;
		}
		public int getServiceupdatedBy() {
			return serviceupdatedBy;
		}
		public void setServiceupdatedBy(int serviceupdatedBy) {
			this.serviceupdatedBy = serviceupdatedBy;
		}
		public String getServiceupdatedOn() {
			return serviceupdatedOn;
		}
		public void setServiceupdatedOn(String serviceupdatedOn) {
			this.serviceupdatedOn = serviceupdatedOn;
		}
		public int getServiceisSync() {
			return serviceisSync;
		}
		public void setServiceisSync(int serviceisSync) {
			this.serviceisSync = serviceisSync;
		}
		
		public static void cleanLeakage()
		{
			 setLkgId(0);
			 setTestingId(0);
			 setCreatedBy(0);
			 setCreatedOn(Constants.LABEL_BLANK);
			 setUpdatedBy(0);
			 setUpdatedOn(Constants.LABEL_BLANK);
			 setIsSync(0);			 
		}	
}
