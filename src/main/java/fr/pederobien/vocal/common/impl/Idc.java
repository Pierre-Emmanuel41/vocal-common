package fr.pederobien.vocal.common.impl;

import fr.pederobien.utils.ByteWrapper;

public enum Idc {
	// Idc when a player speak.
	PLAYER_SPEAK(0),

	// Idc when cannot be parsed.
	UNKNOWN(-1);

	private int code;
	private byte[] bytes;

	private Idc(int code) {
		this.code = code;
		bytes = ByteWrapper.create().putInt(code).get();
	}

	@Override
	public String toString() {
		return "Idc={" + super.toString() + "," + code + "}";
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getCode() {
		return code;
	}

	/**
	 * Get the Idc associated to the given code.
	 * 
	 * @param code The code associated to the Idc to get.
	 * 
	 * @return The Idc associated to the given code or UNKNOWN if there is no Idc associated to the given code.
	 */
	public static Idc fromCode(int code) {
		return code == 0 ? PLAYER_SPEAK : UNKNOWN;
	}
}