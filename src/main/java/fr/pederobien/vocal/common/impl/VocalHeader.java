package fr.pederobien.vocal.common.impl;

import fr.pederobien.messenger.impl.Header;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalHeader extends Header implements IVocalHeader {
	/**
	 * The number of bytes reserved for the message header. It correspond to:</br>
	 * </br>
	 * +0: Communication protocol version.</br>
	 * +4: The sequence number.</br>
	 * +8: The identifier.</br>
	 */
	public static final int HEADER_LENGH = 12;
	private VocalIdentifier identifier;

	/**
	 * Creates a new header associated to the given version. The value of the identifier is {@link VocalIdentifier#UNKNOWN}.
	 * 
	 * @param version The protocol version used to create this header.
	 */
	public VocalHeader(float version) {
		super(version);
		identifier = VocalIdentifier.UNKNOWN;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);
		identifier = (VocalIdentifier) properties[0];
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
		identifier = VocalIdentifier.fromCode(wrapper.getInt(8));

		super.setProperties(identifier);
		return this;
	}

	@Override
	public VocalIdentifier getIdentifier() {
		return identifier;
	}
}
