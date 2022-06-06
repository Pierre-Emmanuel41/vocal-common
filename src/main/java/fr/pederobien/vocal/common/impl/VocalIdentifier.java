package fr.pederobien.vocal.common.impl;

import java.util.HashMap;
import java.util.Map;

import fr.pederobien.utils.ByteWrapper;

public enum VocalIdentifier {
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