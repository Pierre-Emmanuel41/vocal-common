package fr.pederobien.vocal.common.impl;

import fr.pederobien.utils.ByteWrapper;

public enum VocalErrorCode {

	// Code when no errors happened.
	NONE("No error."),

	// Code when cannot be parsed.
	UNKNOWN(-1, "Cannot parse the error code.");

	private static int codeGenerator;
	private int code;
	private String message;
	private byte[] bytes;

	private VocalErrorCode(String message) {
		this(generateCode(), message);
	}

	private VocalErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
		bytes = ByteWrapper.create().putInt(code).get();
	}

	@Override
	public String toString() {
		return "ErrorCode={" + super.toString() + "," + code + "," + message + "}";
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Get the ErrorCode code associated to the given code.
	 * 
	 * @param code The code used as key to get the associated ErrorCode.
	 * 
	 * @return The ErrorCode associated to the given code or UNKNOWN if there is no ErrorCode associated to the given code.
	 */
	public static VocalErrorCode fromCode(int code) {
		for (VocalErrorCode errorCode : values())
			if (errorCode.getCode() == code)
				return errorCode;
		return UNKNOWN;
	}

	private static int generateCode() {
		return codeGenerator++;
	}
}
