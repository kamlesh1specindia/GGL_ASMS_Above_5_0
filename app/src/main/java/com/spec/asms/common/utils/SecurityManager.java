package com.spec.asms.common.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

import com.spec.asms.common.Constants;
/**
 * Security Manager class used to encrypt and decrypt data using secret key,salt. It used AES 128 algorithem.
 *
 * @author : jenisha
 *
 */


public class SecurityManager {

	private static final String ALGORITHM = "AES";
	@SuppressWarnings("unused")
	private static final int ITERATIONS = 2;
	private static final byte[] keyValue = 
			new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

	/**
	 * This method used to encrypt given data in to encrypted form using given salt.
	 * @param value : It takes data as string.
	 * @param salt  : It takes salt as plain text to append with given text.
	 * @return : Encrypted data.
	 */

	public static String encrypt(String value, String salt) {
		String eValue = value;
		try
		{
			//	        Key key = generateKey();
			//	        Cipher c = Cipher.getInstance(ALGORITHM);  
			//	        c.init(Cipher.ENCRYPT_MODE, key);
			//	  
			//	        for (int i = 0; i < ITERATIONS; i++) {
			//	            valueToEnc = salt + eValue;
			//	            byte[] encValue = c.doFinal(valueToEnc.getBytes());
			//	           // new Base64Coder();
			//				//eValue = new Base64Coder().encode(encValue);
			//	            eValue =  Base64.encode(encValue).toString();
			//	          //  eValue = Base64.encodeBase64String(encValue);
			//	        }

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes= new byte[16];
			byte[] b= salt.getBytes("UTF-8");
			int len= b.length;
			if (len > keyBytes.length) len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
			cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

			byte[] results = cipher.doFinal(value.getBytes("UTF-8"));
			//BASE64Encoder encoder = new BASE64Encoder();
			//return encoder.encode(results);
			return Base64.encode(results);
		}catch(Exception ex)
		{
			Log.e(Constants.TAG_PROTECTOR,ex.getMessage());
			Log.d(Constants.DEBUG,":SecurityManager:"+ Utility.convertExceptionToString(ex));
		}
		return eValue;
	}

	/**
	 * This method used to decrypt given encrypted data with same salt used to encrpyt data.
	 * @param value : It takes encrypted data as string.
	 * @param salt  : It takes salt to decrypt data.
	 * @return : Decrypted data.
	 */

	@SuppressWarnings("unused")
	public static String decrypt(String value, String salt) throws Exception {

		String dValue = null;
		String valueToDecrypt = value;
		try
		{
			//	        Key key = generateKey();
			//	        Cipher c = Cipher.getInstance(ALGORITHM);
			//	        c.init(Cipher.DECRYPT_MODE, key);
			//	        for (int i = 0; i < ITERATIONS; i++) {
			//	        	byte[] decordedValue = Base64.decode(valueToDecrypt);
			//	           // byte[] decordedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
			//	        	//byte[] decordedValue = Base64.decodeBase64(valueToDecrypt);
			//	            byte[] decValue = c.doFinal(decordedValue);
			//	            dValue = new String(decValue).substring(salt.length());
			//	            valueToDecrypt = dValue;

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes= new byte[16];
			byte[] b= salt.getBytes("UTF-8");
			int len= b.length;
			if (len > keyBytes.length) len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
			cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);

			byte [] results = cipher.doFinal(Base64.decode(value));
			return new String(results,"UTF-8");

		}catch(Exception ex)
		{
			Log.e(Constants.TAG_PROTECTOR,ex.getMessage());
			Log.d(Constants.DEBUG,":SecurityManager:"+ Utility.convertExceptionToString(ex));
		}
		return dValue;
	}

	/**
	 * This method used to generate a secret key for AES algorithm with a above given key.
	 * @return : key value.
	 */
	@SuppressWarnings("unused")
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		// SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		// key = keyFactory.generateSecret(new DESKeySpec(keyValue));
		return key;
	}

}
