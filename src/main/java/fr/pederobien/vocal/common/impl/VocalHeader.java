package fr.pederobien.vocal.common.impl;

import fr.pederobien.messenger.impl.Header;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalHeader extends Header implements IVocalHeader {
	/**
	 * The number of bytes reserved for the message header. It correspond to:</br>
	 * </br>
	 * +0: Communication protocol version.</br>
	 * +4: The sequence number.</br>
	 * +8: The identifier.</br>
	 * +12: The error code.
	 */
	public static final int HEADER_LENGH = 16;
	private VocalIdentifier identifier;
	private VocalErrorCode errorCode;

	/**
	 * Creates a new header associated to the given version. The value of the identifier is {@link VocalIdentifier#UNKNOWN}.
	 * 
	 * @param version The protocol version used to create this header.
	 */
	public VocalHeader(float version) {
		super(version);
		identifier = VocalIdentifier.UNKNOWN;
		errorCode = VocalErrorCode.NONE;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		identifier = (VocalIdentifier) properties[0];
		errorCode = (VocalErrorCode) properties[0];
	}

	@Override
	protected byte[] generateProperties() {
		return ByteWrapper.create().put(identifier.getBytes()).get();
	}

	@Override
	public VocalHeader parse(byte[] buffer) {
		super.parse(buffer);
		ByteWrapper wrapper = ByteWrapper.wrap(buffer);

		// +8: Identifier
		identifier = VocalIdentifier.fromCode(wrapper.getInt(VocalMessage.IDENTIFIER_INDEX - VocalMessage.BEGIN_WORD.length));

		// +12: ErrorCode
		errorCode = VocalErrorCode.fromCode(wrapper.getInt(VocalMessage.ERROR_CODE_INDEX - VocalMessage.BEGIN_WORD.length));
		super.setProperties(identifier, errorCode);
		return this;
	}

	@Override
	public VocalIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public VocalErrorCode getErrorCode() {
		return errorCode;
	}
}
