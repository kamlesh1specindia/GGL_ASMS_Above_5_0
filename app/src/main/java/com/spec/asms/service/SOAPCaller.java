package com.spec.asms.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.spec.asms.common.Constants;
import com.spec.asms.common.utils.Utility;


/**
 * A singleton utility class for calling soap based web services. This class have different methods to call soap based web services as per the need.
 * 
 * @author Ishan Dave
 *
 */
public class SOAPCaller{
	private static SOAPCaller singleInstance;
	private String responseCode;

	private SOAPCaller(){}

	public static SOAPCaller getInstance(){
		if(singleInstance == null) singleInstance = new SOAPCaller();

		return singleInstance;
	}
	
	
	/**
	 * Returns JSONObject containing response of web service. If system not able to call the web service or the response string is fault code or string than this method throws Exception.
	 * 
	 * @param methodName String representing name of the method to call the web service.
	 * @param params Map<String, Object> containing required parameters to call the web service.
	 * @return JSONObject
	 * @throws JSONException 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public JSONObject getJSON(final String methodName, Map<String, Object> params) throws JSONException{	
		MySSLSocketFactory.allowAllSSL();
		HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WS_URL,600000);
		String result = null;
		JSONObject resultJSON = null;
		

		try {
			SoapObject request = new SoapObject(Constants.WS_NAMESPACE, methodName);
			if(params != null && params.size() > 0){
				Set<String> keys = params.keySet();
				Iterator<String> iterator = keys.iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					request.addProperty(key, params.get(key));
					System.out.println("Value of keys"+key);
					System.out.println("Value of keysValue"+params.get(key));
					key = null;
				}
			}
			Log.d("SOAPCaller ", "request ::: "+request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;

			Log.d("SOAPCaller ", "envelop ::: "+envelope);
			
			httpTransportSE.call(Constants.WS_NAMESPACE+methodName, envelope); //Socket Exception

			Object responseSoapObj = envelope.getResponse();			

			if(responseSoapObj != null){
				if(responseSoapObj instanceof SoapObject){
					result = ((SoapObject)responseSoapObj).getProperty(0).toString();
				}else if(responseSoapObj instanceof SoapPrimitive){
					result = ((SoapPrimitive)responseSoapObj).toString();
				}					
			}

			resultJSON = new JSONObject(result);

			return resultJSON;
		
		}catch(JSONException jse){
			resultJSON = new JSONObject();
			try{
				resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			}catch(Exception e){
				e.printStackTrace();
			}
			return resultJSON;
		}catch(ConnectException ce){
			if(ce != null){
				ce.printStackTrace();
				Log.d(Constants.DEBUG,":SoapCaller:"+ Utility.convertExceptionToString(ce));
				 StackTraceElement stackTrace = ce.getStackTrace()[0];
				  String method = stackTrace.getMethodName();
				  resultJSON = new JSONObject();
					try{
						if(method != null && method.equalsIgnoreCase(Constants.NETWORK_EXCEPTION_METHOD)){
							resultJSON.put(Constants.NETWORK_EXCEPTION_METHOD,Constants.NETWORK_EXCEPTION_METHOD);
						}else if(method != null && method.equalsIgnoreCase(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
							resultJSON.put(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD,Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
						}else{
							resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				return resultJSON;
				//Log.d("Error while calling the Web Service IO : Method: "+methodName,"Exception");
			}
			
		}catch(SocketException se){
			se.printStackTrace();
			Log.d(Constants.DEBUG,":SoapCaller:"+ Utility.convertExceptionToString(se));
			resultJSON = new JSONObject();
				try{
					resultJSON.put(Constants.SOCKET_EXCEPTION,Constants.SOCKET_EXCEPTION);
				}catch(Exception e){
					e.printStackTrace();
				}
				return resultJSON;
		}catch(IOException ioException)
		{
			ioException.printStackTrace();
			Log.d(Constants.DEBUG,":SoapCaller:"+ Utility.convertExceptionToString(ioException));
			Log.d("Error while calling the Web Service IO : Method: "+methodName,"Exception :::"+ioException.getMessage());
			resultJSON = new JSONObject();
			try{
				resultJSON.put(Constants.IOEXCEPTION,Constants.IOEXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(e));
			}
			return resultJSON;
		}catch(XmlPullParserException xmlException)
		{
			xmlException.printStackTrace();
			Log.d(Constants.DEBUG,":SoapCaller:"+ Utility.convertExceptionToString(xmlException));
			Log.d("Error while calling the Web Service XMLPUL PARSER: Method: "+methodName,"Exception :::"+xmlException.getMessage());
			resultJSON = new JSONObject();
			try{
				resultJSON.put(Constants.XMLPULLPARSEREXCEPTION,Constants.XMLPULLPARSEREXCEPTION);
			}catch(Exception e){
				e.printStackTrace();
			}
			return resultJSON;
		}
		catch (Exception exception) {
			exception.printStackTrace();
			Log.d(Constants.DEBUG,":SoapCaller:"+ Utility.convertExceptionToString(exception));
			if(exception != null)
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception :::"+exception.getMessage());
			else
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception : Message not available");
			resultJSON = new JSONObject();
			try{
				resultJSON.put(Constants.EXCEPTION,Constants.EXCEPTION);
			}catch(Exception ex){
				ex.printStackTrace();
				Log.d(Constants.DEBUG,":AsyncBatchSubmit:"+ Utility.convertExceptionToString(ex));
			}
			return resultJSON;
			//throw exception;
		}
		return resultJSON;
	}

	/**
	 * Returns JSONObject containing response of web service. If system not able to call the web service or the response string is fault code or string than this method throws Exception.
	 * 
	 * @param methodName String representing name of the method to call the web service.
	 * @param params Map<String, Object> containing required parameters to call the web service.
	 * @return JSONObject
	 * @throws JSONException 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public JSONObject getJSON1(final String methodName, Map<String, Object> params) throws JSONException{	
		MySSLSocketFactory.allowAllSSL();
		HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WS_URL,600000);
		String result = null;
		JSONObject resultJSON = null;

		try {
			SoapObject request = new SoapObject(Constants.WS_NAMESPACE, methodName);
			if(params != null && params.size() > 0){
				Set<String> keys = params.keySet();
				Iterator<String> iterator = keys.iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					request.addProperty(key, params.get(key));
					System.out.println("Value of keys"+key);
					System.out.println("Value of keysValue"+params.get(key));
					key = null;
				}
			}
			Log.d("SOAPCaller ", "request ::: "+request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;

			Log.d("SOAPCaller ", "envelop ::: "+envelope);
			httpTransportSE.call(Constants.WS_NAMESPACE+methodName, envelope); //Socket Exception

			Object responseSoapObj = envelope.getResponse();	
						
			ServiceConnection sc = httpTransportSE.getConnection();
			
			List lst = sc.getResponseProperties();
			String s = lst.get(0).toString();
			
			HeaderProperty hp = (HeaderProperty) lst.get(0);
			
			responseCode = hp.getValue();
			
			System.out.println("Response Propertiesl: "+hp.getValue());
			
			if(responseSoapObj != null){
				if(responseSoapObj instanceof SoapObject){
					result = ((SoapObject)responseSoapObj).getProperty(0).toString();
				}else if(responseSoapObj instanceof SoapPrimitive){
					result = ((SoapPrimitive)responseSoapObj).toString();
				}					
			}

			resultJSON = new JSONObject(result);

			return resultJSON;
		
		}catch(IOException ioException)
		{
			Log.d("Error while calling the Web Service IO : Method: "+methodName,"Exception :::"+ioException.getMessage());
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			return resultJSON;
		}catch(XmlPullParserException xmlException)
		{
			Log.d("Error while calling the Web Service XMLPUL PARSER: Method: "+methodName,"Exception :::"+xmlException.getMessage());
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			return resultJSON;
		}
		catch (Exception exception) {
			if(exception != null)
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception :::"+exception.getMessage());
			else
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception : Message not available");
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			return resultJSON;
			//throw exception;
		}
	}
	
	@SuppressWarnings("unused")
	public Object[] getResponse(final String methodName, Map<String, Object> params) throws JSONException,ConnectException,SocketException,IOException,XmlPullParserException,Exception{		
		Object[] responseArr = new Object[2];
		HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WS_URL,600000);
	
		String result = null;
		JSONObject resultJSON = null;

		try {
			SoapObject request = new SoapObject(Constants.WS_NAMESPACE, methodName);
			
			if(params != null && params.size() > 0){
				Set<String> keys = params.keySet();
				Iterator<String> iterator = keys.iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					request.addProperty(key, params.get(key));
					System.out.println("Value of keys"+key);
					System.out.println("Value of keysValue"+params.get(key));
					key = null;
				}
			}
//			Log.d("SOAPCaller ", "request ::: "+request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;
			
		
			
			

//			Log.d("SOAPCaller ", "envelop ::: "+envelope);
			
			httpTransportSE.call(Constants.WS_NAMESPACE+methodName, envelope); //Socket Exception

			Object responseSoapObj = envelope.getResponse();	
						
			ServiceConnection sc = httpTransportSE.getConnection();
			
			List lst = sc.getResponseProperties();
			String s = lst.get(0).toString();
			
			HeaderProperty hp = (HeaderProperty) lst.get(0);
			
			responseCode = hp.getValue();
			
			System.out.println("Response Propertiesl: "+hp.getValue());
			
			if(responseSoapObj != null){
				if(responseSoapObj instanceof SoapObject){
					result = ((SoapObject)responseSoapObj).getProperty(0).toString();
				}else if(responseSoapObj instanceof SoapPrimitive){
					result = ((SoapPrimitive)responseSoapObj).toString();
				}					
			}

			resultJSON = new JSONObject(result);

			responseArr[0] = resultJSON;
			responseArr[1] = responseCode;
			
			
			return responseArr;
		
		}catch(ConnectException ce){
			throw ce;
			/*if(ce != null){
				ce.printStackTrace();
				StackTraceElement stackTrace = ce.getStackTrace()[0];
				String method = stackTrace.getMethodName();
				resultJSON = new JSONObject();
				if(method != null && method.equalsIgnoreCase(Constants.NETWORK_EXCEPTION_METHOD)){
					resultJSON.put(Constants.NETWORK_EXCEPTION_METHOD,Constants.NETWORK_EXCEPTION_METHOD);
				}else if(method != null && method.equalsIgnoreCase(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					resultJSON.put(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD,Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
				}else{
					resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
				}
				responseArr[0] = resultJSON;
				return responseArr;
				//Log.d("Error while calling the Web Service IO : Method: "+methodName,"Exception");
			}*/
			
		}catch(SocketException se){
			throw se;
			/*if(se != null){
				se.printStackTrace();
				StackTraceElement stackTrace = se.getStackTrace()[0];
				String method = stackTrace.getMethodName();
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				se.printStackTrace(pw);
				String errorMessage = sw.toString(); 
				System.out.println("SocketException Error Message :::" + errorMessage);
				
				resultJSON = new JSONObject();
				if(method != null && method.equalsIgnoreCase(Constants.NETWORK_EXCEPTION_METHOD)){
					resultJSON.put(Constants.NETWORK_EXCEPTION_METHOD,Constants.NETWORK_EXCEPTION_METHOD);
				}else if(method != null && method.equalsIgnoreCase(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD)){
					resultJSON.put(Constants.CONNECTION_REFUSED_EXCEPTION_METHOD,Constants.CONNECTION_REFUSED_EXCEPTION_METHOD);
				}else{
					resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
				}
				responseArr[0] = resultJSON;
				return responseArr;
			}*/
		}catch(IOException ioException){
			throw ioException;
			/*StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ioException.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			System.out.println("IOException Error Message :::" + errorMessage);
			
			ioException.printStackTrace();
			Log.d("Error while calling the Web Service IO : Method: "+methodName,"Exception :::"+ioException.getMessage());
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			responseArr[0] = resultJSON;
			return responseArr;*/
		}catch(XmlPullParserException xmlException){
			throw xmlException;
			/*StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			xmlException.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			System.out.println("XMLPullParserException Error Message :::" + errorMessage);
			
			xmlException.printStackTrace();
			Log.d("Error while calling the Web Service XMLPUL PARSER: Method: "+methodName,"Exception :::"+xmlException.getMessage());
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			responseArr[0] = resultJSON;
			return responseArr;*/
		}
		catch (Exception exception) {
			throw exception;
		/*	StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			String errorMessage = sw.toString(); 
			System.out.println("Exception Error Message :::" + errorMessage);
			
			exception.printStackTrace();
			if(exception != null)
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception :::"+exception.getMessage());
			else
				Log.d("Error while calling the Web Service: Method: "+methodName,"Exception : Message not available");
			resultJSON = new JSONObject();
			resultJSON.put(Constants.JSON_FAILED,Constants.JSON_FAILED);
			responseArr[0] = resultJSON;
			return responseArr;*/
			
		}
		
	}

	public String getResponseCode() {
		return responseCode;
	}
	
	
}