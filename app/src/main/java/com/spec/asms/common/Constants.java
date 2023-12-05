package com.spec.asms.common;


/**
 * Constants class to define constants. 
 * That will used in whole application.
 */
public class Constants 
{
	//Splash Screen
	public static final long SPLASH_DUREATION = 4000;
	public static final long SPLASH_SLEEP_TIME = 3000;

	public static final String EMPTY_STRING = "";
	public static final String APP_PACKAGE_NAME = "com.spec.asms";
	public static final String APP_NAME = "ASMS";
	public static final String SALT = "this is a asmsggcl salt"; 
	public static final String ERROR_MARK = "*";
	public static String LABLE_DEVICE_ID =""; 
	public static String URL_APK_DOWNLNLOAD="http://10.37.55.133/asms/tablet/TestDatabaseActivity.apk";
	public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

	//Dialog Confirmation
	public static final String LABLE_TITLE_CONFIRMATION = "Submit-Confirmation";
	
	//Date and Time Formatter constant
	//	public static final String DATE_FORMAT = "MM/dd/yyyy";
	//	public static final String TIME_FORMAT = "hh:mm:ssa";
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm:ssa";
	public static final String TIME_FORMAT_DISPLAY = "hh:mma";
	public static final String DATE_DIALOG_FRAGMENT = "DateDialogFragment";
	public static final int START_TIME_DIALOG_ID = 1;
	public static final int END_TIME_DIALOG_ID = 2;

	//Webservice URL
	public static String WS_URL = ""; //http://10.37.55.133/asms/ggclasms.asmx?WSDL
	public static final String WS_NAMESPACE = "http://tempuri.org/";
	public static final String METHOD_AUTHENTICATE = "Authentication";
	public static final String METHOD_BATCH_SUBMIT = "BatchSubmit";
	public static final String METHOD_CONFIRMATION_SYNC_DATA = "ConfirmationSyncData";
	public static final String METHOD_GET_ALL_MARSTERS = "GetAllMasters";
	public static final String METHOD_SYNC_CUSTOMER_DATA = "SyncCustomerData";
	public static final String METHOD_CHANGE_PASSWORD = "ChangePassword";
	public static final String METHOD_GET_DATE_FOR_HCL = "GetDateForHCL1";
	public static final String METHOD_TEST_CONNECTION = "TestService";
	public static final String METHOD_UPDATE_AVAILABLE="IsUpdateAvailable";
	public static final String METHOD_URL_RELATIVE_PATH="RelativePath";

	//String Blank and space constant
	public static final String LABEL_BLANK = "";
	public static final String LABEL_DOT = ".";
	public static final String LABEL_SPACE = " ";
	public static final String LABEL_TRUE = "true";
	public static final String LABEL_FALSE = "false";
	public static final String LABEL_PROGRESS = "Loading...";
	public static final String LABEL_PROGRESS_LOGIN = "Authenticating...";
	public static final String LABEL_PROGRESS_TEST_CONNECTION = "Testing connection...";
	public static final String LABEL_PROGRESS_CHANGE_PWD = "Password changing...";
	public static final String LABEL_PROGRESS_FETCH_HCL1_DATE = "Fetching HCL 1 date from server...";
	public static final String LABEL_PROGRESS_CONFIRMING_SYNC = "Confirming data synchronized...";
	public static final String LABEL_PROGRESS_GETALLMASTERS = "Fetching data from server...";
	public static final String LABEL_PROGRESS_CUSTOMER = "Fetching customer list from server...";
	public static final String LABEL_PROGRESS_CUSTOMER_DB = "Loading customer list from database...";
	public static final String LABEL_PROGRESS_CUSTOMER_LIST = "Loading assigned customer list ...";
	public static final String LABEL_PROGRESS_SEARCHING_CUSTOMER_LIST = "Searching customers ...";
	public static final String LABEL_PROGRESS_SYNC = "Synchronizing data with server...";
	public static final String LABEL_DIALOG_ALERT = "Logout - Confirmation";
	public static final String LABEL_DIALOG_CONFIRM = "Confirmation";
	public static final String LABEL_DELET_CONFIRMATION="Uninstall Application";
	public static final String LABEL_UPDATING_CUSTOMER_STATUS = "Pleae wait...";
	
	public static final String LABEL_LOGIN = "LoginCkh";
	public static final String LABEL_IS_CUST_AVAILABLE = "iscustavilable";
	public static final String LABEL_PASSWORD_LOCK = "isPwdLock";
	public static final String LABEL_PROGRESS_CLEANUP = "Checking and clearing asynced data...";
	public static final String LABEL_PROGRESS_CLEARING = "Clearing all data...";
	public static final String LABEL_ALERT_CLEAR_SUCCESS = "Cleaning Successful!";
	public static final String LABEL_ALERT_CLEAR_FAILED = "Cleaning Failed , There is no synced data or data is not 30 days old!";
	public static final String LABEL_ALERT_CLEANUP_CONFORMATION = "Your sync data will be deleted. Are you sure you want to clean up?";
	public static final String LABEL_ALERT_CLEAR_CONFORMATION = "All data of application will be deleted permanently. Are you sure you want to clear all data?";
	public static final String LABEL_ALERT_DOWNLOAD_NEW_APK="New version is available,Do you want to download?";
	public static final String LABEL_ALERT_DOWNLOAD_NEW_APK_2="New version is available, Press 'OK' to download and install.";
	public static final String LABEL_ALERT_DELETE_APPLICATION="Data will be synced and application will be uninstalled!";
	//Validation
	public static final String LABEL_VALIDATE_TESTING = "Testing";
	public static final String LABEL_VALIDATE_TESTING_PRESSURETYPE = "High or Low Pressure";
	public static final String LABEL_VALIDATE_RCC = "R.C.C";
	public static final String LABEL_VALIDATE_RCC_MS_NAIL = "MS NAIL DIA 1/4\" X 3\"L";
	public static final String LABEL_VALIDATE_RCC_MS_NUT = "M.S Nut for 5/16” for R.C.C.";
	public static final String LABEL_VALIDATE_RCC_GUARD = "RCC Guard";
	public static final String LABEL_VALIDATE_PAINTING_PAINT = "Paint";
	public static final String LABEL_VALIDATE_PAINTING_ORING_METER = "O RING for Meter";
	public static final String LABEL_VALIDATE_PAINTING_ORING_DOM = "O RING for DOM Regulator";
	public static final String LABEL_VALIDATE_PAINTING_ORING_AUDCO = "O RING for Audco gland";
	public static final String LABEL_VALIDATE_PAINTING = "Painting and O ring";
	public static final String LABEL_VALIDATE_CLAMPING = "Clamping";
	public static final String LABEL_VALIDATE_CLAMPING_CLAMP_HALF = "Clamp ½”";
	public static final String LABEL_VALIDATE_CLAMPING_CHEESE_HEAD_SCREW = "Cheese head screw";
	public static final String LABEL_VALIDATE_CLAMPING_SUPPLY_OF_PIPELINE_PROTECTION_CLAMP = "Supply of Pipeline Protection Clamp";
	public static final String LABEL_VALIDATE_CLAMPING_CLAMP_ONE = "Clamp 1”";
	public static final String LABEL_VALIDATE_CLAMPING_WOOD_SCREW = "Wood Screw";
	public static final String LABEL_VALIDATE_CLAMPING_ROUL_PLUG = "Roul Plug/Grip Plug";
	public static final String LABEL_VALIDATE_GIFITTINGS = "GI Fittings";
	public static final String LABEL_VALIDATE_GIFITTINGS_ELBOW = "Elbow";
	public static final String LABEL_VALIDATE_GIFITTINGS_TEE = "Tee";
	public static final String LABEL_VALIDATE_GIFITTINGS_HEXNIPPLE = "Hex Nipple";
	public static final String LABEL_VALIDATE_GIFITTINGS_UNION = "Union";
	public static final String LABEL_VALIDATE_GIFITTINGS_PLUG = "Plug";
	public static final String LABEL_VALIDATE_GIFITTINGS_CAP = "GI Cap";
	public static final String LABEL_VALIDATE_GIFITTINGS_COUPLING = "GI Coupling";
	public static final String LABEL_VALIDATE_SURAKSHA = "Suraksha Tube";
	public static final String LABEL_VALIDATE_OTHER_CHECKS = "Other Checks";
	public static final String LABEL_VALIDATE_OTHER_SUPPLY_BUSH = "Supply of Bush";
	public static final String LABEL_VALIDATE_OTHER_RUBBER_CAP = "Rubber Cap";
	public static final String LABEL_VALIDATE_OTHER_ISOLATION_BALL_VALVE = "Isolation Ball Valve";
	public static final String LABEL_VALIDATE_OTHER_BRASS_BALL_COCK = "Gas Tap / Brass Ball Cock";
	public static final String LABEL_VALIDATE_OTHER_BRASS_BALL_VALVE = "Brass Ball Valve";
	
	
	public static final String LABEL_VALIDATE_SURAKSHA_SIZE1 = "7.5mm for 1 Meter";
	public static final String LABEL_VALIDATE_SURAKSHA_SIZE2 = "7.9mm 1.5 Meter";
	public static final String LABEL_VALIDATE_SURAKSHA_SIZE3 = "12.5 mm 1.5 Meter";
	public static final String LABEL_VALIDATE_SURAKSHA_SIZE4 = "Clamp Hose 8mm Pipe";
	public static final String LABEL_VALIDATE_SURAKSHA_SIZE5 = "Clamp Hose 20mm Pipe";
		
	//Preference
	public static final String PREF_PAINT_SATISFACTORY = "isPaint"; 
	public static final String PREF_ORING_SATISFACTORY = "isORing";
	public static final String PREF_PAINT_TXT = "paintTxt";
	public static final String PREF_ORING_TXT = "oRingTxt";
	public static final String PREF_LANGUAGE = "language";
	public static final String PREF_SAFETY_TIPS="safetyTips";
	public static final String PREF_START_TIME="strtTime";
	public static final String PREF_END_TIME="endTime";
	public static final String PREF_START_DATE="startDate";
	public static final String PREF_END_DATE="edtDate";
	public static final String PREF_USER_ADMIN="user";
	public static final String PREF_NOTICE_NUMBER="noticeNumber";

	//Language Tyface
	public static final String TYPFACE_GUJRATI = "fonts/saral.TTF";
	
	//Language Selection
	public static final int LANGUAGE_ENGLISH = 1;
	public static final int LANGUAGE_GUJRATI = 2;

	//Database Related Constant
	public static final String TBL_MST_ROLE = "mstrole";
	public static final String[] USER_ROLE_FIELDS = {"roleid","rolename","issync","createdby","createdon","updatedby","updatedon"};

	public static final String TBL_MST_USER = "mstuser";
	public static final String[] USER_MASTER_FIELDS = {"userid","roleid","username","password","lastlogintime","issync","createdby","createdon","updatedby","updatedon","islock"};
	
	public static final String TBL_MST_USER_LOCK = "mstuserlock";
	public static final String[] USER_LOCK_FIELDS = {"userid","username","password","deviceid","passwordupdateddate","islock"};

	public static final String TBL_MST_DAMAGES = "mstdamages";
	public static final String TBL_MST_CAUSES = "mstcauses";
	 
	public static final String TBL_MST_CONFORMANCE = "mstconformance"; 
	public static final String[]CONFORMANCE_MASTER_FIELDS = {"conformanceid","reason"};

	public static final String TBL_MST_CUSTOMER = "mstcustomer";
	public static final String[] CUSTOMER_MASTER_FIELDS = {"customerid","customername","customeradd","statusid","walksequence","issync","createdby","createdon","updatedby","updatedon","maintainanceorderid","orderid","isdeleted","mrunit","societyname","phone","CustomerStatus","expirydate","MeterNumber"};

	public static final String TBL_MST_CUSTOME_PARAMETER ="mstcustomparmeter";
	public static final String[] CUSTOMER_PARAMETER_FIELDS = {"id","key","value","createdby","createdon","updatedby","updatedon"};
	
	public static final String TBL_MST_FAQ = "mstfaq";
	public static final String TBL_MST_GEYSER_TYPE = "mstgeysertype";
	public static final String TBL_MST_GIFITTING = "mstgifitting";
	public static final String TBL_MST_LEAKAGE = "mstleakage";
	public static final String TBL_MST_STATUS = "mststatus";
	public static final String TBL_MST_VALIDATION_RULE = "mstvalidationrule";
	public static final String TBL_MST_MAKE = "mstmake";
	public static final String TBL_MST_BATCH_SUBMIT = "mstbatchsubmit";
	
	public static final String TBL_MST_LOG = "mstlog";
	public static final String [] LOG_FIELDS = {"datetime","objectstatus","submitstatus","responsestatus","batchsize","totalsubmitted","totalfailure","responsetime","error"};
	
	//Details Table
	public static final String TBL_DTL_ADMIN = "dtladmin";
	public static final String TBL_DTL_CLAMPING = "dtlclamping";
	public static final String TBL_DTL_CLEANUP = "dtlcleanup";
	public static final String TBL_DTL_DAMAGE_CAUSE = "dtldamagecause";

	public static final String TBL_DTL_CONFORMANCE = "dtlconformance";
	public static final String[]CONFORMANCE_DETAIL_FIELDS = {"conformancedetailid","maintainanceid","conformanceid","text","issync","createdby","createdon","updatedby","updatedon","isnc"};

	public static final String TBL_DTL_CUSTOMER_FEEDBACK = "dtlcustomerfeedback";
	public static final String[] CUST_FEED_FIELDS = {"customerfbid","maintainanceid","comments","signature","receiptno","noticeno","issync","createdby","createdon","updatedby","updatedon"};

	public static final String TBL_DTL_GIFITTING = "dtlgifitting";
	public static final String TBL_DTL_LEAKAGE = "dtlleakage";

	public static final String TBL_DTL_MAINTAINANCE = "dtlmaintainance";
	public static final String[] MAINTAINANCE_FIELDS = {"maintainanceid","maintainanceorderid","customerid","date","time","statuscode","issync","createdby","createdon","updatedby","updatedon" ,"starttime","endtime" , "phone", "altphone"};

	public static final String TBL_DTL_MAKE_AND_GEYSER = "dtlmakeandgeyser";
	public static final String TBL_DTL_OTHER_CHECKS = "dtlotherchecks";
	public static final String TBL_DTL_PAINTINGORING="dtlpaintingoring";
	public static final String TBL_DTL_RCC = "dtlrcc";
	public static final String TBL_DTL_SURAKSHA_TUBE = "dtlsurakshatube";
	public static final String TBL_DTL_OTHER_SURAKSHA_TUBE = "dtlothersurakshatube";
	public static final String TBL_DTL_TESTING = "dtltesting";

	// database column name
	public static final String DB_URL_ID = "adminurlid";
	public static final String DB_URL = "url";
	public static final String DB_USER_ID = "userid";
	public static final String DB_CLAMPING_ID = "clampingid";
	public static final String DB_MAINTAINANCE_ID = "maintainanceid";
	public static final String DB_IS_SAND_FILLING = "issandfilling";
	public static final String DB_MSNAIL_CHARGABLE = "msnailchargable";
    public static final String DB_MSNUT_CHARGABLE = "msnutchargable";
    public static final String DB_RCC_GUARD_CHARGABLE = "rccguardchargable";
    public static final String DB_MSNAIL_SUPPLIEDBY = "msnailsuppliedby";
    public static final String DB_MSNUT_SUPPLIEDBY = "msnutsuppliedby";
    public static final String DB_RCC_GUARD_SUPPLIEDBY = "rccguardsuppliedby";
	public static final String DB_ISWORKING = "isworking";
	public static final String DB_PIPELINE_PROTECTION_CLAMP = "pipelineprotectionclamp";
	public static final String DB_CLAMP_HALF_C = "chrgclamponebytwo";
	public static final String DB_CLAMP_HALF_NC = "clamponebytwo";
	public static final String DB_CLAMP_ONE_C = "chrgclampone";
	public static final String DB_CLAMP_ONE_NC = "clampone";
	public static final String DB_CHEESEHEADSCREW_C = "chrgcheeseheadscrew";
	public static final String DB_CHEESEHEADSCREW_NC = "cheeseheadcrew";
	public static final String DB_WOODSCREW_C = "chrgwoodscrew";
	public static final String DB_WOODSCREW_NC = "woodscrew";
	public static final String DB_ROUL_PLUG_C = "chrgroulplug";
	public static final String DB_ROUL_PLUG_NC = "roulplug";
	public static final String DB_SPLR_HALF = "splrclamponebytwo";
	public static final String DB_SPLR_ONE = "splrclampone";
	public static final String DB_SPLR_CHEESE_HEAD_SCREW = "splrcheeseheadscrew";
	public static final String DB_SPLR_WOOKD_SCREW = "splrwoodscrew";
	public static final String DB_SPLR_ROUL_PLUG = "splrroulplug";
	
	public static final String DB_ISSYNC = "issync";
	public static final String DB_CREATEDBY = "createdby";
	public static final String DB_CREATEDON = "createdon";
	public static final String DB_UPDATEDBY = "updatedby";
	public static final String DB_UPDATEDON = "updatedon";
	public static final String DB_ISDELETED = "isdeleted";
	public static final String DB_KEY = "key";
	public static final String DB_MRUNIT = "mrunit";
	public static final String DB_SOCIETYNAME = "societyname";
	public static final String DB_PHONE = "phone";
    public static final String DB_CUSTOMER_STATUS = "CustomerStatus";
    public static final String DB_METER_NUMBER = "MeterNumber";
	//public static final String DB_SURAKSHA_TUBE_EXPIRY_DATE = "STubeExpiryDate";
	 

	public static final String DB_CLEANUP_ID = "cleanupid";
	public static final String[]CLEANUP_DETAIL_FIELDS = {"cleanupid","lastcleanupdate","cleanupperiod","userid",};

	public static final String DB_LAST_CLEANUP_DATE = "lastcleanupdate";
	public static final String DB_CLEANUP_PEROID = "cleanupperiod";

	public static final String DB_CONFORMANCE_DETAIL_ID = "conformancedetailid";
	public static final String DB_CONFORMANCE_ID = "conformanceid";
	public static final String DB_TEXT = "text";
	public static final String DB_IS_NC = "isnc";

	public static final String DB_CUSTOMER_FB_ID = "customerfbid";
	public static final String DB_COMMENTS = "comments";
	public static final String DB_SIGNATURE = "signature";
	public static final String DB_RECEIPT_NO = "receiptno";
	public static final String DB_NOTICE_NO = "noticeno";

	public static final String DB_IS_GEYSER_AVAILABLE = "isgeyseravilable";
	public static final String DB_MAKE_GEYSER_ID = "makegeyserid";
	public static final String DB_MAKEID = "makeid";
	public static final String DB_MAKE_TEXT = "maketext";
	public static final String DB_MAKE_OTHER_TEXT = "makeothertext";
	public static final String DB_GEYSER_TYPE_ID = "geysertypeid";
	public static final String DB_ISINSIDE_BATHROOM = "isinsidebathroom";

	public static final String DB_DTL_GIFITTING_ID = "dtlgifittingid";
	public static final String DB_TESTING_ID = "testingid";
	public static final String DB_GIFITTING_ID = "dtlgifittingid";
	public static final String DB_MST_GIFITTING_ID = "gifittingid";
	public static final String DB_ELBOW = "elbow";
	public static final String DB_TEE = "tee";
	public static final String DB_HAXNIPPLE = "haxnipple";
	public static final String DB_GI_UNION = "giunion";
	public static final String DB_PLUG = "plug";
	public static final String DB_GI_CAP = "gicap";
	public static final String DB_GI_COUPLING = "gicoupling";
	

	public static final String DB_DTL_LKG_ID = "dtllkgid";
	public static final String DB_LEAKAGE_ID = "leakageid";

	/*public static final String DB_OTHER_CHECK_ID = "othercheckid";
	public static final String DB_RUBBERCAP = "rubbercap";
	public static final String DB_BRASSBALL_VALVE = "brassballvalve";
	public static final String DB_BRASSBALL_COCK= "brassballcock";
	public static final String DB_IS_METER_OK = "ismeterok" ;
	public static final String DB_METER_READING = "meterreading" ;
	public static final String DB_MTR_PERFORMANCE = "mtrperformance";
	public static final String DB_MTR_READABLE = "mtrreadable";
	public static final String DB_GIPIPE_INSIDE_BM = "gipipeinsidebm";*/
	
	public static final String DB_OTHER_CHECK_ID = "othercheckid";
   // public static final String DB_SUPPLY_BUSH = "supplybush";
    public static final String DB_RUBBERCAP = "rubbercap";
	public static final String DB_RUBBER_CAP_CHARGABLE = "rubbercapchargable";
	public static final String DB_RUBBER_CAP_SUPPLIED_BY = "rubbercapsuppliedby";
	public static final String DB_BRASSBALL_VALVE = "brassballvalve";
	public static final String DB_BRASSBALL_VALUE_CHARGABLE = "brassballvaluechargable";
	public static final String DB_BRASS_BALL_VALUE_SUPPLIED_BY = "brassballvaluesuppliedby";
	public static final String DB_BRASSBALL_COCK= "brassballcock";
	public static final String DB_BRASSBALL_COCK_CHARGABLE = "brassballcockchargable";
	public static final String DB_BRASSBALL_COCK_SUPPLIED_BY = "brassballcocksuppliedby";
	public static final String DB_SUPPLY_BUSH_VALUE = "isolationball";
	public static final String DB_SUPPLY_BUSH_VALUE_CHARGABLE = "isolationballchargable";
	public static final String DB_SUPPLY_BUSH_SUPPLIED_BY = "isolationballsuppliedby";
	public static final String DB_IS_METER_OK = "ismeterok" ;
	public static final String DB_METER_READING = "meterreading" ;
	public static final String DB_MTR_PERFORMANCE = "mtrperformance";
	public static final String DB_MTR_READABLE = "mtrreadable";
	public static final String DB_GIPIPE_INSIDE_BM = "gipipeinsidebm";
	public static final String DB_PVC_SLEEVE = "pvcsleeve";

	public static final String DB_CUSTOMER_ID = "customerid";
	public static final String DB_DATE = "date";
	public static final String DB_TIME = "time";
	public static final String DB_STATUS = "status";

	public static final String DB_PAINTINGID = "paintingid";
	public static final String DB_ISPAINTING_WORK = "ispaintingwork";
	public static final String DB_PAINT = "paint";
	public static final String DB_ISORING_WORK = "isoringwork";
	public static final String DB_ORMETER = "ormeter";
	public static final String DB_ORDOM_REGULATOR = "ordomregulator";
	public static final String DB_ORAUDCOGLAND = "oraudcogland";

	public static final String DB_SURAKSHA_TUBE_ID = "surakshatubeid";
	public static final String DB_IS_REPLACED = "isreplaced";
	public static final String DB_EXPIRY_DATE = "expirydate";
	public static final String DB_REPLACE_EXPIRY_DATE = "replaceexpirydate";
	public static final String DB_SIZE_751C = "size751c";
	public static final String DB_SIZE_751NC = "size751nc";
	public static final String DB_SIZE_7915C = "size7915c";
	public static final String DB_SIZE_7915NC = "size7915nc";
	public static final String DB_SIZE_12515C = "size12515c";
	public static final String DB_SIZE_12515NC = "size12515nc";
	public static final String DB_SIZE_CLAMP_8C = "clampsize8c";
	public static final String DB_SIZE_CLAMP_8NC = "clampsize8nc";
	public static final String DB_SIZE_CLAMP_20C = "clampsize20c";
    public static final String DB_SIZE_CLAMP_20NC = "clampsize20nc";
    public static final String DB_KSPL_751 = "KSplr1Meter75mm";
    public static final String DB_KSPL_7915 = "KSplr15Meter79mm";
    public static final String DB_KSPL_12515 = "KChrg15Meter125mm";
    public static final String DB_KSPL_8 = "KSplrClampHose8mmPipe";
    public static final String DB_KSPL_20 = "KSplrClampHose20mmPipe";
	
	/*public static final String DB_SIZE1 = "size1";
	public static final String DB_SIZE2 = "size2";
	public static final String DB_SIZE3 = "size3";*/
	//public static final String DB_SIZE4 = "size4";
	/*public static final String DB_CLAMPSIZE1 = "clampsize1";
	public static final String DB_CLAMPSIZE2 = "clampsize2";*/

	public static final String DB_RCC_ID = "rccid";
	public static final String DB_RCC_GUARD = "rccguard";
	public static final String DB_MSNAIL = "msnail";
	public static final String DB_MSNUT = "msnut";
	public static final String DB_COACH_SCREW = "coachscrew";
	public static final String DB_MAINTAINANCE_ORDER_ID = "maintainanceorderid";
	public static final String DB_ORDER_ID = "orderid";

	//testing
	public static final String DB_INITIAL_PRESSURE = "initialpressure";
	public static final String DB_FINAL_PRESSURE = "finalpressure";
	public static final String DB_PRESSURE_DROP = "pressuredrop";
	public static final String DB_DURATION = "duration";
	public static final String DB_GAS_LKG_DETECTION_TEST = "gaslkgdetectiontest";
	public static final String DB_PRESSURE_TYPE = "pressuretype";
	public static final String DB_GAS_LKG_RECTIFIED = "gaslkgrectified";

	public static final String DB_REASON = "reason";
	public static final String DB_CUSTOMER_NAME = "customername";
	public static final String DB_CUSTOMER_ADD = "customeradd";
	public static final String DB_STATUS_ID = "statusid";
	public static final String DB_WALK_SEQUENCE = "walksequence";

	public static final String DB_FAQ_ID = "faqid";
	public static final String DB_QUESTION = "question";
	public static final String DB_ANSWER = "answer";

	public static final String DB_ROLE_ID = "roleid";
	public static final String DB_ROLE_NAME = "rolename";
	public static final String DB_STATUS_CODE = "statuscode";

	public static final String DB_USERNAME = "username";
	public static final String DB_PASSWORD = "password";
	public static final String DB_LAST_LOGIN_TIME = "lastlogintime";

	public static final String DB_VALIDATION_RULE_ID = "validationruleid";
	public static final String DB_FORMNAME = "formname";
	public static final String DB_FIELDNAME = "fieldname";
	public static final String DB_MINVALUE = "minvalue";
	public static final String DB_MAXVALUE = "maxvalue";

	public static final String DB_MAKE_ID = "makeid";
	public static final String DB_VALUE = "value";
	
	public static final String DB_DAMAGE_ID = "damageid";
	public static final String DB_OBJECT_ID = "objectid";
	public static final String DB_DAMAGE_CODE = "damagecode";
	public static final String DB_FIELD_NAME_GUJ = "fieldnameguj";
	public static final String DB_CAUSE_ID = "causeid";
	public static final String DB_CAUSE_CODE = "causecode";
	public static final String DB_CODE_GROUP = "codegroup";
	public static final String DB_OBJECT_CODE = "objectcode";
	public static final String DB_PARENT_CODE_ID = "parentcodeid";
	public static final String DB_DTL_DAMAGE_CAUSE_ID = "dtldamagecauseid";
	
	public static final String DB_REQ_ELBOW = "Elbow";
	public static final String DB_REQ_TEE = "Tee";
	public static final String DB_REQ_HEX_NIPPLE = "Hex Nipple";
	public static final String DB_REQ_UNION = "Union";
	public static final String DB_REQ_PLUG = "Plug";
	public static final String DB_REQ_GI_CAP = "GI Cap";
	public static final String DB_REQ_GI_COUPLING = "GI Coupling";
	
	public static final String DB_BATCH_SUBMIT_ID = "batchsubmitid";
	public static final String DB_TOT_RECORDS = "totrecords";
	public static final String DB_SUCCESS_RECORDS = "sucessrecord";
	public static final String DB_UNSUCCESS_RECORDS = "unsucessrecord";
	

	
	
	public static final String XMLPULLPARSEREXCEPTION = "XmlPullParserException";
	public static final String IOEXCEPTION = "IOException";
	public static final String SOCKET_EXCEPTION = "SocketException";
	
	public static final String JSON_CONNECTION_FAILED = "ConnectionFailed";
	public static final String JSON_NETWORK_FAILED = "NetworkFailed";
	
	// JSON Objects
	public static final String JSON_FAILED = "AuthFailed";
	public static final String JSON_LOGIN_USERNAME = "UserName";
	public static final String JSON_LOGIN_PASSWORD = "Password";
	public static final String JSON_LOGIN_DEVICEID = "DeviceNo";
	public static final String JSON_MAINTENANCE_ORDER_ID = "MaintenanceOrderID";
	public static final String JSON_NEW_PASSWORD = "NewPassword";
	public static final String JSON_CONFIRM_PASSWORD = "ConfirmPassword";
	public static final String JSON_CHANGE_PWD_APPDATE_TIME = "ChangePwdDateTime";
	public static final String JSON_LOGIN_APPDATE_TIME = "AppDateTime";
	public static final String JSON_LOGIN_ANDROID_API_LEVEL = "androidAPILevel";
	public static final String JSON_LOGIN_ISLOCKED = "IsLocked";
	public static final String JSON_LOGIN_PSWRD_UPDATEDATE = "PasswordUpdatedDate";
	public static final String JSON_MAIN_MAINTENANCE_FORM_ID = "JsonMaintenanceFormId";
	public static final String JSON_REASSIGNMENT_ID = "JsonReAssignmentId";
	public static final String JSON_MAINTENANCE_FORM_DATA = "JsonMaintenanceFormData";
	public static final String JSON_LOGIN = "Login";
	public static final String JSON_MAINTENANCE_FORM_ID = "MaintenanceFormID";//"MaintenanceFormID";
	public static final String JSON_WORK_ON_GAS = "WorkOnGas";
	public static final String JSON_DEVICE_NO = "DeviceNo";
	public static final String JSON_DEVICE_MANUFACTURER = "DeviceManufacturer";
	public static final String JSON_DEVICE_MODEL = "DeviceModel";
	public static final String JSON_TEST_STATUS = "Status";
	public static final String JSON_RELATIVE_PATH = "RelativePath";
	public static final String JSON_IS_UPDATE_AVIAILABLE = "IsUpdateAvailable";
	
	
	public static final String JSON_ORING_SATISFACTORY= "ORingSatisfactory";
	public static final String JSON_FOR_METER = "ForMeter";
	public static final String JSON_FOR_DOM_REGULARTOR = "ForDOMRegulator";
	public static final String JSON_FOR_AUDCO_GLAND = "ForAudcoGland";
	public static final String JSON_ELBOW = "Elbow";
	public static final String JSON_TEE = "Tee";
	public static final String JSON_HEX_NIPPLE = "HexNipple";
	public static final String JSON_GI_UNION = "GIUnion";
	public static final String JSON_UNION = "Union";
	public static final String JSON_PLUG = "Plug";
	public static final String JSON_GI_CAP = "GICap";
	public static final String JSON_GI_COUPLING = "GICoupling";
	public static final String JSON_RCC_WORK_SATISFACTORY = "RCCWorkSatisfactory";
	public static final String JSON_RCC_SANDFILLING_CEMENTING = "SandFillingCementing";
	public static final String JSON_MSN_NAILDIA_1BY4_C = "ChrgMSNailDia1by4";
	public static final String JSON_MSN_NAILDIA_1BY4_NC = "MSNailDia1by4";
	public static final String JSON_MSNUT_5BY16_C = "ChrgMSNut5by16";
	public static final String JSON_MSNUT_5BY16_NC = "MSNut5by16";
	public static final String JSON_RCC_GUARD_C = "ChrgRCCGuard";
	public static final String JSON_RCC_GUARD_NC = "RCCGuard";
	public static final String JSON_RCC_SPL_MSN_NAILDIA_1BY4 = "SplrMSNailDia1by4";
	public static final String JSON_RCC_SPLMSNUT_5BY16 = "SplrMSNut5by16";
	public static final String JSON_RCC_SPL_GUARD = "SplrRCCGuard";
	
	public static final String JSON_SURAKSHA_TUBE_REPLACED = "SurakshaTubeReplaced";
	public static final String JSON_RUBBER_TUBE_EXPIRY_DATE = "RubberTubeExpiryDate";
	public static final String JSON_EXPIRY_DATE_OF_REPLACE_RUBBER_TUBE = "ExpiryDateOfReplacedRubberTube";
	public static final String JSON_KC_SIZE_751 ="KChrg1Meter75mm";
	public static final String JSON_K_SIZE_751 ="K1Meter75mm";
	public static final String JSON_KC_SIZE_7915 ="KChrg15Meter79mm";
	public static final String JSON_K_SIZE_7915 ="K15Meter79mm";
	public static final String JSON_KC_SIZE_12515 ="KChrg15Meter125mm";
	public static final String JSON_K_SIZE_12515 ="K15Meter125mm";
	public static final String JSON_KC_SIZE_8 ="KChrgClampHose8mmPipe";
	public static final String JSON_K_SIZE_8 ="KClampHose8mmPipe";
	public static final String JSON_KC_SIZE_20 ="KChrgClampHose20mmPipe";
	public static final String JSON_K_SIZE_20 ="KClampHose20mmPipe";
	
	public static final String JSON_KSPL_751 = "KSplr1Meter75mm";
	public static final String JSON_KSPL_7915 = "KSplr15Meter79mm";
	public static final String JSON_KSPL_12515 = "KSplr15Meter125mm";
	public static final String JSON_KSPL_8 = "KSplrClampHose8mmPipe";
	public static final String JSON_KSPL_20 = "KSplrClampHose20mmPipe";
	
	
	
	public static final String JSON_C_SIZE_751 ="ChrgMeter1mm75";
	public static final String JSON_NC_SIZE_751 ="Meter1mm75";
	public static final String JSON_C_SIZE_7915 ="ChrgMeter15mm79";
	public static final String JSON_NC_SIZE_7915 ="Meter15mm79";
	public static final String JSON_C_SIZE_12515 ="ChrgMeter15mm125";
	public static final String JSON_NC_SIZE_12515 ="Meter15mm125";
	public static final String JSON_C_SIZE_8 ="ChrgClampHose8mmPipe";
	public static final String JSON_NC_SIZE_8 ="ClampHose8mmPipe";
	public static final String JSON_C_SIZE_20 ="ChrgClampHose20mmPipe";
	public static final String JSON_NC_SIZE_20 ="ClampHose20mmPipe";
	
	public static final String JSON_OSPL_751 = "SplrMeter1mm75";
	public static final String JSON_OSPL_7915 = "SplrMeter15mm79";
	public static final String JSON_OSPL_12515 = "SplrMeter15mm125";
	public static final String JSON_OSPL_8 = "SplrClampHose8mmPipe";
	public static final String JSON_OSPL_20 = "SplrClampHose20mmPipe";
	
	
	
	public static final String JSON_METER_1MM75 = "Meter1mm75";
	public static final String JSON_METER_15MM79 = "Meter15mm79";
	public static final String JSON_CLAMP_HOSE_8MM_PIPE = "ClampHose8mmPipe";
	public static final String JSON_METER_15MM125 = "Meter15mm125";
	public static final String JSON_METER_1MM125 = "Meter1mm125";
	public static final String JSON_CLAMP_HOSE_20MM_PIPE = "ClampHose20mmPipe";
	public static final String JSON_MAKE_ID = "MakeID";
	public static final String JSON_MAKE_OTHERS = "MakeOthers";
	public static final String JSON_GEYSER_TYPE_ID = "GeyserTypeID";
	public static final String JSON_GEYSER_INSIDE = "GeyserInside";
	
	//public static final String JSON_SUPPLIED_BUSH = "Supply3by4into1by2Bush";
	public static final String JSON_RUBBER_CAP_NC = "RubberCap";
	public static final String JSON_RUBBER_CAP_C = "ChrgRubberCap";
	public static final String JSON_BRASS_BALL_VALVE_NC = "BrassBallValve";
	public static final String JSON_BRASS_BALL_VALVE_C = "ChrgBrassBallValve";
	public static final String JSON_BRASS_BALL_COCK_NC = "BrassBallCock";
	public static final String JSON_BRASS_BALL_COCK_C = "ChrgBrassBallCock";
	public static final String JSON_SUPPLY_BUSH_NC = "Supply3by4into1by2Bush";
	public static final String JSON_SUPPLY_BUSH_C = "ChrgSupply3by4into1by2Bush";
	public static final String JSON_SPLR_RUBBER_CAP = "SplrRubberCap";
	public static final String JSON_SPLR_BRASS_BALL_VALVE = "SplrBrassBallValve";
	public static final String JSON_SPLR_BRASS_BALL_COCK = "SplrBrassBallCock";
	public static final String JSON_SPLR_SUPPLY_BUSH_VALVE = "SplrSupply3by4into1by2Bush";
	public static final String JSON_INSTALLATION_OF_PVC_SLEEVE = "InstallationOfPVCSleeve";
	
	
	
	
	public static final String JSON_METER_READING = "MeterReading";
	public static final String JSON_METER_PERFORMANCE = "MeterPerformance";
	public static final String JSON_METER_READABLE = "MeterReadable";
	public static final String JSON_GI_PIPE_INSIDE_BASEMENT = "GIPipeInsideBasement";
	public static final String JSON_NON_CONFORMANCE_ID = "NonConformanceID";
	public static final String JSON_LKG_ID = "LeakageID";
	public static final String JSON_LKG_TEXT = "LeakageText";
	public static final String JSON_IS_APPLICABLE = "IsApplicable";
	public static final String JSON_OTHERS = "Others";
	public static final String JSON_SIGNATURE = "Signature";

	public static final String JSON_CUSTOMER_ID = "CustomerID";
	public static final String JSON_STATUS_CODE_ID = "StatusCodeID";
	public static final String JSON_DATETIME_RECORD = "RecordDateTime";
	public static final String JSON_COMMENTS = "Comments";
	public static final String JSON_RECEIPT_NO = "ReceiptNo";
	public static final String JSON_NOTICE_NO = "NoticeNo";
	public static final String JSON_INITIAL_PRESSURE = "InitialPressure";
	public static final String JSON_FINAL_PRESSURE = "FinalPressure";
	public static final String JSON_PRESSURE_DROP = "PressureDrop";
	public static final String JSON_DURATION = "Duration";
	public static final String JSON_GAS_LKG_TEST_PASS = "GasLeakageTestPass";
	public static final String JSON_PRESSURE_HLN = "PressureHLN";
	public static final String JSON_GAS_LKG_RECTIFIED = "GasLeakageRectified";
	public static final String JSON_CLAMPPING_WORK_SATISFACTORY = "ClampingWorkSatisfactory";
	public static final String JSON_PIPELINE_PROTECTION_CLAMP = "PipelineProtectionClamp";
	public static final String JSON_CLAMP_1BY2_C = "ChrgClamp1by2";
	public static final String JSON_CLAMP_1BY2_NC = "Clamp1by2";
	public static final String JSON_CLAMP1_C = "ChrgClamp1";
	public static final String JSON_CLAMP1_NC = "Clamp1";
	public static final String JSON_CHEESE_HEAD_SCREW_C = "ChrgCheeseHeadScrew";
	public static final String JSON_CHEESE_HEAD_SCREW_NC = "CheeseHeadScrew";
	public static final String JSON_WOOD_SCREW_C = "ChrgWoodScrew";
	public static final String JSON_WOOD_SCREW_NC = "WoodScrew";
	public static final String JSON_ROUL_PLUG_C = "ChrgRoulPlug";
	public static final String JSON_ROUL_PLUG_NC = "RoulPlug";
	public static final String JSON_CLMAP_SPLR_1BY2 = "SplrClamp1by2";
	public static final String JSON_CLMAP_SPLR_1 = "SplrClamp1";
	public static final String JSON_CLMAP_SPLR_CHEESE_HEAD_SCREW = "SplrCheeseHeadScrew";
	public static final String JSON_CLMAP_SPLR_WOOD_SCREW  = "SplrWoodScrew";
	public static final String JSON_CLMAP_SPLR_ROUL_PLUG = "SplrRoulPlug";
	public static final String JSON_PAINTING_WORK_SATISFACTORY = "PaintingWorkSatisfactory";
	public static final String JSON_PAINT = "Paint";
	public static final String JSON_START_TIME_STAMP ="StartTimeStamp";
	public static final String JSON_END_TIME_STAMP ="EndTimeStamp";

	public static final String JSON_MAINTENANCE_FORM = "MaintenanceForm";
	public static final String JSON_MAINTENANCE_FORM_TESTING = "MaintenanceFormTesting";
	public static final String JSON_MAINTENANCE_FORM_CLAMPING = "MaintenanceFormClamping";
	public static final String JSON_MAINTENANCE_FORM_PAINTING = "MaintenanceFormPainting";
	public static final String JSON_MAINTENANCE_FORM_ORING = "MaintenanceFormORing";
	public static final String JSON_MAINTENANCE_FORM_TESTING_LKG = "MaintenanceFormTestingLeakage";	
	public static final String JSON_MAINTENANCE_FORM_TESTING_LKG_GIFITTING = "MaintenanceFormTestingLeakageGIFitting";
	public static final String JSON_MAINTENANCE_FORM_RCC = "MaintenanceFormRCC";
	public static final String JSON_MAINTENANCE_FORM_SURAKSHA_TUBE = "MaintenanceFormSurakshaTube";
	public static final String JSON_MAINTENANCE_FORM_SURAKSHA_TUBE_KITCHEN = "MaintenanceFormSurakshaTubeKitchen";
	public static final String JSON_MAINTENANCE_FORM_MAKE_GEYSER = "MaintenanceFormMakeAndGeyser";
	public static final String JSON_MAINTENANCE_FORM_OTHERS = "MaintenanceFormOthers";
	public static final String JSON_MAINTENANCE_FORM_NON_CONFORMANCE = "MaintenanceFormNonConformance";
	public static final String JSON_MAINTENANCE_FORM_SIGNATURE = "MaintenanceFormSignature";
	public static final String JSON_LIST_ANDROID_DATA = "listAndroidData";
	
	public static final String JSON_MASTER_DATA= "MasterData";
	public static final String JSON_TECHNICIANS= "Technicians";
	public static final String JSON_ID= "ID";
	public static final String JSON_SQL_TIMESTAMP= "SqlTimeStamp";
	public static final String JSON_UPDATE_PASSWORD_DATE = "PasswordUpdatedDate";
	public static final String JSON_MASTER= "Master";
	public static final String JSON_STATUS_CODE= "StatusCode";
	public static final String JSON_DAMAGE= "Damage";
	public static final String JSON_CAUSE= "Cause";
	public static final String JSON_OBJ= "Obj";
	public static final String JSON_LIST_LKG= "listLeakage";
	public static final String JSON_LIST_NC= "listNC";
	public static final String JSON_DESC= "Desc";
	public static final String JSON_CONTRACTOR= "Contractor";
	public static final String JSON_TEAM= "Team";
	public static final String JSON_NON_CONFORMANCE= "NonConformance";
	public static final String JSON_REASON= "Reason";
	public static final String JSON_FAQ= "Faq";
	public static final String JSON_ANSWER= "Answer";
	public static final String JSON_QUESTION= "Question";
	public static final String JSON_FORM= "Form";
	public static final String JSON_FORMS= "Forms";
	public static final String JSON_VALIDATIONS= "Validations";
	public static final String JSON_VALIDATION_RULE_ID= "ValidationRuleID";
	public static final String JSON_MAX_VALUE= "MaxValue";
	public static final String JSON_MIN_VALUE= "MinValue";
	public static final String JSON_FIELD_NAME= "FieldName";
	public static final String JSON_GEYSER_TYPE= "GeyserType";
	public static final String JSON_MAKE= "Make";
	public static final String JSON_CLEANUP_PERIOD= "CleanUpPeriod";
	public static final String JSON_AUTO_SUBMISSION_INTERVAL = "AutoSubmissionInterval";
	public static final String JSON_BATCH_SIZE = "BatchSize";

	public static final String JSON_DAMAGE_ID = "DamageID";
	public static final String JSON_DAMAGE_CODE= "DamageCode";
	
	public static final String JSON_CAUSE_ID = "CauseID";
	public static final String JSON_CAUSE_CODE = "CauseCode";
	
	public static final String JSON_OBJECT_ID= "ObjectID";
	public static final String JSON_FIELD_NAME_GUJ= "FieldNameGujarati";
	public static final String JSON_CODE_GROUP = "ObjectCodeGroup";
	public static final String JSON_OBJECT_CODE = "ObjectCode";
	public static final String JSON_OBJECT_PARENT_CODE_ID = "ParentCodeID";
	public static final String JSON_OBJECT_LIST_CHILD_OBJ = "listChildObject";
	
	
	//Activity TAG
	public static final String TAG_LOGIN_ACTIVITY = "LoginActivity";
	public static final String TAG_MENU_ACTIVITY = "MenuActivity";
	public static final String TAG_SPLASH_ACTIVITY = "SplashActivity";
	public static final String TAG_VIEW_ASSIGN_ACTIVITY = "ViewAssignmentActivity";
	public static final String TAG_MAINTENANCE_ACTIVITY = "MaintenanceActivity";
	public static final String TAG_CHANGE_CREDENTIALS_ACTIVITY = "ChangePasswordActivity"; //why
	public static final String TAG_PROTECTOR = "ProtectorClass";
	public static final String TAG_SERVICE = "BatchSubmitService";
	public static final String TAG_MAINTAINANCE_FORM = "MaintainanceForm";
	public static final String TAG_TESTING_FORM = "TestingForm";
	public static final String TAG_RCC_FORM = "RccForm";
	public static final String TAG_CLAMPING_FORM = "ClampingForm";
	public static final String TAG_CONFORMANCE_FORM = "ConformanceForm";
	public static final String TAG_FEEDBACK_FORM = "FeedbackForm";
	public static final String TAG_MAKEGEYSER_FORM = "MakeandgeyserForm";
	public static final String TAG_OTHERCHECKS_FORM = "OtherChecksForm";
	public static final String TAG_PAINTING_FORM = "PaintingForm";
	public static final String TAG_DB_HELPER = "DBHelper";

	//Activity Request Code
	public static final int REQ_LOGIN_ACTIVITY = 1;
	public static final int REQ_MENU_ACTIVITY = 2;
	public static final int REQ_SPLASH_ACTIVITY = 3;
	public static final int REQ_VIEW_ASSIGN_ACTIVITY = 4;
	public static final int REQ = 5;
	public static final int REQ_CHANGE_CREDENTIALS_ACTIVITY = 6; 
	public static final int REQ_CUST_FEEDBACK_ACTIVITY = 7; 
 
	//Successful messages
	public static final String INFO_SUCCESSFUL_PASSWORD_UPDATE = "Password updated successfully!";
	public static final String LABLE_NEXT = "Next";
	public static final String LABLE_OK = "Ok"; 
	public static final String LABLE_CANCEL = "Cancel";
	public static final String LABLE_DEFAULT_UNLOCK = "0";
	public static final String LABLE_DEFAULT_LOCK = "1"; 
	public static final String LABLE_YES = "Yes"; 
	public static final String LABLE_NO = "No";	
	public static final int DATE_DIALOG_ID = 0;
	public static final String LABLE_CHANGE_PASSWORD = "Change Password";
	public static final String LABLE_LATER = "Later";
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String TIME_FORMAT = "kk:mm";
	public static final String BLANK = "";
	public static final String TESTING_PRESSURETYPE_HIGH = "High";
	public static final String TESTING_PRESSURETYPE_LOW = "Low";
	public static final String TESTING_PRESSURETYPE_NORMAL = "Normal";	
	public static final String ALERT_TITLE_GENERAL = "ASMS";
	public static final String ALERT_TITLE_GENERAL_ERROR = "ASMS - Error";
	public static final String ALERT_TITLE_GENERAL_INFO = "ASMS - Information";
	public static final String ALERT_TITLE_LOGIN_ERROR = "Login - Error";
	public static final String ALERT_TITLE_NETWORK_ERROR = "Network - Error";
	public static final String ALERT_TITLE_DATE_NOT_FOUND="Last HCL 1 date not found";
	public static final String ALERT_TITLE_CLEANUP = "Cleanup - Confirmation";
	public static final String ALERT_TITLE_CLEAR = "ClearData - Confirmation";
	public static final String TESTING_CONNECTION_SUCESSFUL = "Testing connection successful!";
	public static final String TESTING_CONNECTION_UNSUCESSFULL = "Testing connection not successful.Please try again!";
	public static final String TESTING_CONNECTION_FAILED = "Testing connection not successful. Please check entered url!";
	public static final String BATCH_SUBMIT_SUCESSFUL = "Data submitted successfully!";
	public static final String BATCH_SUBMIT_UNSUCESSFUL = "Data not submitted successful!";
	public static final String NO_BATCH_SUBMIT = "There is no data to submit!";
	public static final String DOWNLOAD_APK_PATH = "Please note the path of downloaded apk!";
	public static final String DROPDOWN_MSG = "Select option from dropdown";
	public static final String DOWNLOAD_MSG = "Downloading...";
	public static final String APP_UPDATE_TITLE = "Application Update - Confirmation";
	public static final String APP_UPDATE_MESSAGE =  "Application version upgrade will remove all your existing customer-maintainance data. \nPlease make sure to \"Submit\" data before installing new application.If data is submited then follow the steps given in next dialog to upgrade version.\n\nPlease enable \"Unknown Sources\" check box from Application or Security tab of Settings Screen if it is disabled in your device.";
	public static final String APP_INSTALL_ERROR = "There is problem in installation, please try again!";
	public static final String BATCH_SUBMIT_START = "Auto submission process running!";
	public static final String BATCH_RESPONSE = "A round of auto submission process completed!";
	public static final String AUTO_SUBMIT_START = "Auto submission process started!";
	
	
	//Warning Message 
	public static final String WARNINIG_FIELDS_EMPTY = "Field cannot be empty!";
	public static final String WARNINIG_FIELDS_MANDITORY = "Field is manditory!";
	public static final String WARNING_VALIDATE_SIGN = "Please do signature!";
	public static final String WARNING_VALIDATE = "Value can't be more than ";
	public static final String WARNING_MESSAGE = "Value should be less than ";
	public static final String WARNING_MESSAGE_2 = "Value should be more than ";
	public static final String EXCEPTION = "Exception";
	
	//CustomerFeedback
	public static final String CUSTFEEDBACK_CHECKBOX_TEXT = "I have read the summary.";
	public static final String SUMMARY_TESTINGTEXT = "DONE";
	public static final String SUMMARY_TESTINGTEXT_NOT_DONE = "NOT DONE";
	public static final String CUSTFEEDBACK_CHECKBOXCONFIRMATION_TITLE = "Summary - Information";
	public static final String CUSTFEEDBACK_CHECKBOXCONFIRMATION_MESSAGE = "Please check \"I have read summary.\"!";
	public static final String CUSTFEEDBACK_SUMMERYTEXTCONFIRMATION_MESSAGE = "Please read the summary!";
	//Paint Validation(Case NO)
	public static final int PAINT = 0;
	public static final int ORING_METER = 1;
	public static final int ORING_DOM = 2;
	public static final int ORING_AUDCO = 3;

	//Clamp Validation(Case NO)
	public static final int CLAMP_HALF = 0;
	public static final int CHEESE_HEAD_SCREW = 1;
	public static final int CLAMP_ONE = 2;
	public static final int WOOD_SCREW = 3;
	public static final int ROUL_PLUG = 4;
	public static final int PIPELINE_PROTECITON_CLAMPS = 5;

	//RCC Validation(Case NO)
	public static final int MS_NAIL = 0;
	public static final int MS_NUT = 1;
	public static final int RCC_GUARD = 2;

	//SurakshaTube Validation(Case NO)
	public static final int SIZE1 = 0;
	public static final int SIZE2 = 1;
	public static final int SIZE3 = 2;
	public static final int SIZE4 = 4;
	public static final int SIZE5 = 5;
	public static final int SIZE6 = 6;

	//GI Fitting Validation(Case NO)
	public static final int ELBOW = 0;
	public static final int TEE = 1;
	public static final int HEXNIPPLE = 2;
	public static final int UNION = 3;
	public static final int PLUG = 4;
	public static final int GICAP = 5;
	public static final int GICOUPLING = 6;
	
	//Other Check Validation 
	public static final int SUPPLY_BUSH = 0;
	public static final int RUBBER_CAP = 1;
	public static final int BRASS_BALL_VALVE = 2;
	public static final int BRASS_BALL_COCK = 3;
	public static final int ISOLATION_BALL_VALE = 4;

	//Error Message
	public static final String ERROR_WEB_SERVICE_URL_NOT_SET = "Please contact to admin!";
	public static final String ERROR_INTERNET_CONNECTION = "Please check your internet connection!";
	public static final String ERROR_INTERNET_LOGIN_CONNECTION = "Please check your internet connection! \n Offline mode will be started.";
	public static final String ERROR_SERVER_CONNECTION = "Time difference. Error in server connection!";
	public static final String ERROR_LOADING_DATA = "Cannot load list please try again!";
	public static final String ERROR_STRING  = "Value can not be Null:... ";
	public static final String ERROR_PASSWORD_LOCKED  = "Your password is locked! ";
	public static final String ERROR_NO_DATA_OFFLINE  = "No data found in offline mode! ";
	
	public static final String ERROR_ENTER_DECIMAL_VALUE  = "Please enter only 4 Digit decimal value";
	public static final String ERROR_FINAL_PRESSURE_VALUE = "Final Pressure value must be less than or equal to Initial Pressure.";
	public static final String ERROR_FINAL_PRESSURE_ZERO_VALUE = "Final Pressure value must be greater than 0.";
	public static final String ERROR_INITIAL_PRESSURE_ZERO_VALUE = "Initial Pressure value must be greater than 0.";
	public static final String ERROR_SELECT_DAMAGE_AND_CAUSE  = "Please select damage and cause value for ";
	public static final String ERROR_CLICK_GI_BUTTON  = " Select Damage & Cause!";
	public static final String ERROR_ENTER_URL  = " Please enter URL for test connection!";
	//public static final String ERROR_JSON_FAILED  = " Could not connect to server, Please check internet connection!";
	public static final String ERROR_JSON_FAILED  = "Server not responding,Please try again!";
	public static final String ERROR_SESSION_TIMEOUT = "Session time out, Please try again!";
	public static final String ERROR_IN_SUBMISSION = "There is error in submission. Please try again!";
	public static final String ERROR_SERVER_NOT_AVAILABLE = "Server not available!";
	//Walk Sequence
	public static final int WALK_SEQ_UP = 1;
	public static final int WALK_SEQ_DOWN = 0;
	
	public static final String CUSTOMER_ID = "custId";
	public static final String CUSTOMER_NAME = "custName";
	public static final String CUSTOMER_STATUS = "custStatus";
	public static final String CUSTOMER_MRUNIT = "custMRUnit";
	
	public static final String MRUNIT_LIST = "MRUList";
	public static final String STATUS_LIST = "statusList";
	public static final String SOCIETY_LIST = "societyName";
	public static final String SELECTED_SOCIETY_LIST = "selectedSocietyList";
	public static final String SELECTION_OF_SOCIETY = "selection";
	
	public static final String SELECTED_MRU_ID= "selectedMRUId";
	public static final String SELECTED_STATUS = "selectedStatusId";
	public static final String SELECTED_STATUS_ID = "CustStatus";
	
	public static final String NETWORK_EXCEPTION_METHOD = "connect";
	public static final String CONNECTION_REFUSED_EXCEPTION_METHOD = "isConnected";
	
	public static final String RESPONSE_ERROR = "Error";
	public static final String DEBUG = "Debug";
	
	public static final String STATUS_OPERATIONAL = "OP";
	public static final String STATUS_TEMPORARY_DISCONNECTED = "TD";
	public static final String STATUS_FORCE_DISCONNECTED = "FDCO";
	
	public static final String SUPPLY_COMPANY = "C";
	public static final String SUPPLY_VENDOR = "V";
	public static final String ZERO = "0";

    
}