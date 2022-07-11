package fr.pederobien.vocal.common.impl;

import java.util.HashMap;
import java.util.Map;

import fr.pederobien.utils.ByteWrapper;

public enum VocalIdentifier {

	/**
	 * Identifier of the message to get the versions of the communication protocol of the remote.
	 */
	GET_CP_VERSIONS,

	/**
	 * Identifier of the message to set the version of the communication protocol of the remote.
	 */
	SET_CP_VERSION,

	/*
	 * Identifier of the message in order to join a vocal server.
	 */
	SET_SERVER_JOIN,

	/**
	 * Identifier of the message to leave a vocal server.
	 */
	SET_SERVER_LEAVE,

	/**
	 * Identifier of the message to get the server configuration.
	 */
	GET_SERVER_CONFIGURATION,

	/**
	 * Identifier of the message to register a player on a vocal server.
	 */
	REGISTER_PLAYER_ON_SERVER,

	/**
	 * Identifier of the message to unregister a player from a vocal server.
	 */
	UNREGISTER_PLAYER_FROM_SERVER,

	/**
	 * Identifier of the message to set the name of a player.
	 */
	SET_PLAYER_NAME,

	/**
	 * Identifier of the message to set the mute status of a player.
	 */
	SET_PLAYER_MUTE,

	// Identifier when a player speak request is sent to the server.
	PLAYER_SPEAK_INFO,

	// Identifier when a player speak request is received from the server.
	PLAYER_SPEAK_SET,

	// Code when the identifier cannot be parsed.
	UNKNOWN(-1);

	private static int codeGenerator;
	private static Map<Integer, VocalIdentifier> identifiers;
	private int code;
	private byte[] bytes;

	private VocalIdentifier() {
		this(generateCode());
	}

	private VocalIdentifier(int code) {
		this.code = code;
		bytes = ByteWrapper.create().putInt(code).get();
	}

	@Override
	public String toString() {
		return "VocalIdentifier={" + super.toString() + "," + code + "}";
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getCode() {
		return code;
	}

	/**
	 * Get the identifier associated to the given code.
	 * 
	 * @param code The code associated to the identifier to get.
	 * 
	 * @return The identifier associated to the given code or UNKNOWN if there is no identifier associated to the given code.
	 */
	public static VocalIdentifier fromCode(int code) {
		VocalIdentifier identifier = identifiers.get(code);
		return identifier == null ? UNKNOWN : identifier;
	}

	private static int generateCode() {
		return codeGenerator++;
	}

	static {
		identifiers = new HashMap<Integer, VocalIdentifier>();
		for (VocalIdentifier identifier : values())
			identifiers.put(identifier.getCode(), identifier);
	}
}
