package fr.pederobien.vocal.common.impl;

import fr.pederobien.messenger.interfaces.IErrorCode;
import fr.pederobien.utils.ByteWrapper;

public enum VocalErrorCode implements IErrorCode {

	/**
	 * Code when no errors happened.
	 */
	NONE("No error."),

	/**
	 * Code when a timeout occurs.
	 */
	TIMEOUT("Request times out"),

	/**
	 * Code when player has not the permission to send the request.
	 */
	PERMISSION_REFUSED("Permission refused."),

	/**
	 * Code when an unexpected error attempt on the server.
	 */
	UNEXPECTED_ERROR("An unexpected error occurs."),

	/**
	 * Code when the request is malformed.
	 */
	REQUEST_MALFORMED("The request is malformed."),

	/**
	 * Code when the version is not supported.
	 */
	INCOMPATIBLE_VERSION("The protocol version is not supported"),

	/**
	 * Code when there are no treatment associated to the given identifier.
	 */
	IDENTIFIER_UNKNOWN("There is no treatment associated to the given identifier."),

	/**
	 * Code when server plugins cancelled a request.
	 */
	REQUEST_CANCELLED("The request has been cancelled by the server"),

	/**
	 * Code when a client try to join a server whereas it has already joined the server.
	 */
	SERVER_ALREADY_JOINED("The client has already joined the server"),

	/**
	 * Code when the player involved in the request does not match with the player's vocal client.
	 */
	PLAYER_DOES_NOT_MATCH("The player involved in the request does not match with the player hosted by the client"),

	/**
	 * Code when the player does not exist.
	 */
	PLAYER_NOT_FOUND("The player does not exist."),

	/**
	 * Code when trying to rename a player with an already used name.
	 */
	PLAYER_ALREADY_EXISTS("The player already exist"),

	/**
	 * Code when cannot be parsed.
	 */
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
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorCode={" + super.toString() + "," + code + "," + message + "}";
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
