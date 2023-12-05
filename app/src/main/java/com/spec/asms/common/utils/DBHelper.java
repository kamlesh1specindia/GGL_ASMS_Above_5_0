package com.spec.asms.common.utils;

/**
 * DB Helper class for operation with Database.
 * Purpose : Copy database from assets folder to device.
 * @author jenisha
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.Exception.CopyDatabaseException;
import com.spec.asms.common.Exception.CreateDatabaseExeception;

public class DBHelper extends SQLiteOpenHelper{

	private Context context;
	private static String DB_PATH=null;
	public SQLiteDatabase database;
	private static final int DATABASE_VERSION=7;
	private static final String DB_NAME="dbASMS.sqlite";
	public  static String Lock = "dblock";


	public DBHelper(Context context) throws CreateDatabaseExeception{

		super(context, context.getDatabasePath(DB_NAME).getAbsolutePath(),null,DATABASE_VERSION);
		try{
			this.context=context;
//			DB_PATH = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
				DB_PATH = this.context.getDatabasePath(DB_NAME).getAbsolutePath();
			} else{
				DB_PATH = context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath().toString()+"/databases/";
			}

			SQLiteDatabase dbexist = checkdatabase();
			if(dbexist!=null){
				if(DATABASE_VERSION > dbexist.getVersion()){
					onUpgrade(dbexist, dbexist.getVersion(), DATABASE_VERSION);
					dbexist.setVersion(DATABASE_VERSION);
					dbexist.close();
				}
			}
			if (dbexist==null)createdatabase();

		}catch (Exception e) {
			throw new CreateDatabaseExeception("Database can't create");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		try{
			 /*String sql = "create table if not exists " +
		                Constants.TBL_MST_LOG +
		                "(datetime INTEGER, " +
		                "objectstatus TEXT, " +
		                "submitstatus TEXT, " +
		                "responsestatus TEXT, " +
		                "batchsize INTEGER, " +
		                "totalsubmitted INTEGER, " +
		                "totalfailure INTEGER, " +
		                "responsetime INTEGER, " +
		                "error INTEGER, " +
		                "date TEXT)";
			 sqLiteDatabase.execSQL(sql);*/
			// System.out.println(":::::::: onUpgrade Called ::::::::");
			/* if(!isFieldExist(sqLiteDatabase, Constants.TBL_MST_CUSTOMER,  Constants.DB_PHONE)){
				 String query = "alter table " + Constants.TBL_MST_CUSTOMER +" add column "+ Constants.DB_PHONE + " TEXT ";
				 sqLiteDatabase.execSQL(query);
			 }*/

			/* if(!isFieldExist(sqLiteDatabase, Constants.TBL_MST_CUSTOMER,  Constants.DB_CUSTOMER_STATUS)){
				 String addStatusQuery = "alter table " + Constants.TBL_MST_CUSTOMER +" add column "+ Constants.DB_CUSTOMER_STATUS + " TEXT ";
				  sqLiteDatabase.execSQL(addStatusQuery);
			 }*/
			 /*if(!isFieldExist(sqLiteDatabase, Constants.TBL_MST_CUSTOMER,  Constants.DB_SURAKSHA_TUBE_EXPIRY_DATE)){
				 String addExpiryDate = "alter table " + Constants.TBL_MST_CUSTOMER +" add column "+ Constants.DB_SURAKSHA_TUBE_EXPIRY_DATE + " TEXT ";
				 sqLiteDatabase.execSQL(addExpiryDate);
			 }*/
			if(newVersion >= 7) {
				sqLiteDatabase.execSQL("ALTER TABLE " + Constants.TBL_DTL_MAINTAINANCE + " ADD COLUMN phone TEXT");
				sqLiteDatabase.execSQL("ALTER TABLE " + Constants.TBL_DTL_MAINTAINANCE + " ADD COLUMN altphone TEXT");
			}

			if(!isFieldExist(sqLiteDatabase, Constants.TBL_MST_CUSTOMER,  Constants.DB_METER_NUMBER)){
				String addExpiryDate = "alter table " + Constants.TBL_MST_CUSTOMER +" add column "+ Constants.DB_METER_NUMBER + " TEXT ";
				sqLiteDatabase.execSQL(addExpiryDate);
			}

		}catch(Exception e){
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String errorMessage = sw.toString();
			Log.e(Constants.TAG_DB_HELPER, "DateTime:-"+Utility.getcurrentTimeStamp()+errorMessage);
		}
	}


	public boolean isFieldExist(SQLiteDatabase db, String tableName, String fieldName)
	{
		boolean isExist = false;

		Cursor res = null;

		try {

			res = db.rawQuery("Select * from "+ tableName +" limit 1", null);

			int colIndex = res.getColumnIndex(fieldName);
			if (colIndex!=-1){
				isExist = true;
			}

		} catch (Exception e) {
		} finally {
			try { if (res !=null){ res.close(); } } catch (Exception e1) {}
		}

		return isExist;
	}

	/**
	 * Copy database from asset folder to device
	 * @throws CopyDatabaseException
	 *
	 * @exception throws IOException.
	 *
	 */
	private void copydatabase() throws CopyDatabaseException{

		try{

			InputStream myinput = context.getApplicationContext().getAssets().open(DB_NAME);
			Log.i("Package Name=",context.getApplicationContext().getPackageName().toString());
			File outFile = new File(DB_PATH);
			if(!outFile.exists())
				outFile.mkdirs();
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
				outFile = new File(DB_PATH);
				outFile.createNewFile();
			} else {

				outFile = new File(context.getExternalFilesDir(null).getAbsolutePath().toString() + "/databases/dbASMS.sqlite");
				outFile.createNewFile();
			}
			OutputStream myoutput = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myinput.read(buffer)) > 0) {
				myoutput.write(buffer, 0, length);
			}

			myoutput.flush();
			myoutput.close();
			myinput.close();
		}catch (Exception e) {
			throw new CopyDatabaseException("Database can't copy");
		}
	}


	/**
	 * Create database if doesn't exist.
	 * @throws CopyDatabaseException
	 *
	 * @exception: IOException if error in copying database.
	 * */
	public void createdatabase() throws CopyDatabaseException {
		try{
			SQLiteDatabase dbexist = checkdatabase();
			if (dbexist!=null) {
				dbexist.close();
			} else {
				this.getReadableDatabase();
				this.close();
				copydatabase();
			}
		}catch(SQLiteException sqle){
			sqle.printStackTrace();
		}

	}

	/**
	 * Check database exist or not.
	 *
	 * @exception: SQLiteException if database path is incorrect.
	 *
	 * */
	private SQLiteDatabase checkdatabase() {


		SQLiteDatabase checkDB = null;
		try {
			String myPath = (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ? DB_PATH : DB_PATH + DB_NAME;

			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			// database does't exist yet.
			e.printStackTrace();

		}

		/*if (checkDB != null) {
			checkDB.close();
		}*/

		return checkDB;
//		boolean checkdb = false;
//		try {
//			String myPath = DB_PATH + DB_NAME;
//			File dbfile = new File(myPath);
//			checkdb = dbfile.exists();
//		} catch (SQLiteException sle) {
//			Log.e("SQLException",sle.getLocalizedMessage());
//		}
//		return checkdb;
	}

	public SQLiteDatabase openWritableDatabase(){
		try{
			String mypath =  "";
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
				mypath = DB_PATH;
			} else {
				mypath = DB_PATH + DB_NAME;
			}
			database = SQLiteDatabase.openDatabase(mypath, null,
					SQLiteDatabase.OPEN_READWRITE);
		}catch(SQLiteException sle){
			Log.e("SQLException","Exception while opning database : "+sle);
			sle.printStackTrace();
		}
		return database;
	}

	public void closeDB()
	{
		if(database != null)
			database.close();
	}

	//DML operation
	public Cursor cursorSelectAll(String tableName, String[] fieldList) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					tableName, // Table Name
					fieldList, // List of items to fetch
					null,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}

	public long insertToTable(String tableName,ContentValues values)
	{
		long id=-1;
		try{

			synchronized (Lock) {
				id = openWritableDatabase().insert(tableName,null,values);
				closeDB();
			}
			return id;
		}catch(SQLiteException sqle){
			sqle.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public int updateToTable(String tableName,ContentValues values,String whereClause,String[] whereArgs)
	{
		int id=-1;
		synchronized (Lock) {
			id = openWritableDatabase().update(tableName, values, whereClause, whereArgs);
			closeDB();
		}
		return id;
	}

	public long delete(String tableName,String whereClause,String[] whereArgs)
	{
		long id = -1;
		synchronized (Lock) {

			id = openWritableDatabase().delete(tableName, whereClause, whereArgs);
			closeDB();
		}
		return id;
	}

	public int countTotalRows(String tableName,String whereClause)
	{
		int count=0;
		openWritableDatabase();
		String sql = null;
		if(whereClause.equals(""))
		{
			sql = "Select count(*) from "+ tableName;
		}else{
			sql = "Select count(*) from "+ tableName + " where " +whereClause;
		}
		Cursor cursor = database.rawQuery(sql,null);
		cursor.moveToFirst();
		count=cursor.getInt(0);
		cursor.close();
		closeDB();
		return count;
	}

	public Cursor cursorSelectByWhere(String tableName, String[] fieldList,String where) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					tableName, // Table Name
					fieldList, // List of items to fetch
					where,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}

	public Cursor cursorSelectByWhereData(String tableName, String[] fieldList,String where) {
		Cursor cursor ;
//		String limit = "LIMIT 50" + " OFFSET " + offset;
		//String orderBy = " " +Constants.MAINTAINANCE_FIELDS[0] + " LIMIT 50" + " OFFSET  " + 250;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					tableName, // Table Name
					fieldList, // List of items to fetch
					where,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}

	public Cursor cursorSelectByWhereLimit(String tableName, String[] fieldList,String where,int batchsize) {
		Cursor cursor ;
		String limit = " LIMIT " + batchsize;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					tableName, // Table Name
					fieldList, // List of items to fetch
					where + limit,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}


	public Cursor cursorAllSelectByWhere(String tableName,String where) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					tableName, // Table Name
					null, // List of items to fetch
					where,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}

	public Cursor cursorAllSelectDistinct(String tableName,String []fieldList,String where) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					true,
					tableName,
					fieldList,
					where,
					null,
					null,
					null,
					null,
					null);

		}
		return cursor;
	}

	public Cursor cursorAllSelectDistinct(String tableName,String []fieldList) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					true,
					tableName,
					fieldList,
					null,
					null,
					null,
					null,
					null,
					null);

		}
		return cursor;
	}

	public Cursor cursorSelectAllDistict(String tableName, String[] fieldList) {
		Cursor cursor ;
		synchronized (Lock) {
			openWritableDatabase();
			cursor = database.query(
					true,
					tableName, // Table Name
					fieldList,
					null,// List of items to fetch
					null,       // SQL WHERE
					null,       // Selection Args
					null,       // SQL GROUP BY
					null,       // SQL HAVING
					null);//"createdon");    // SQL ORDER BY
		}
		return cursor;
	}

}