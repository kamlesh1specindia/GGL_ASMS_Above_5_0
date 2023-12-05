package com.spec.asms.common.Exception;

/**
 * A class for Copy Database exception to catch.
 * @author jenisha
 *
 */
@SuppressWarnings("serial")
public class CopyDatabaseException extends Exception {

	public CopyDatabaseException() {
	}

	public CopyDatabaseException(String msg) {
		super(msg);
	}

	public String toString() {
		StringBuffer errorMsg = new StringBuffer();
		errorMsg.append("[" + super.getMessage() + "]:");
		return errorMsg.toString();
	}
}