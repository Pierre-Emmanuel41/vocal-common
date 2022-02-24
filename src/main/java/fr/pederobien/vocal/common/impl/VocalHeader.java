package fr.pederobien.vocal.common.impl;

import fr.pederobien.messenger.impl.Header;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalHeader extends Header implements IVocalHeader {
	private Idc idc;
	private Oid oid;

	/**
	 * Creates a new header associated to the given version. The value of the Idc is {@link Idc#UNKNOWN}, the Oid is
	 * {@link Oid#UNKNOWN}.
	 * 
	 * @param version The protocol version used to create this header.
	 */
	public VocalHeader(float version) {
		super(version);
		idc = Idc.UNKNOWN;
		oid = Oid.UNKNOWN;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);
		idc = (Idc) properties[0];
		oid = (Oid) properties[1];
	}

	@Override
	protected byte[] generateProperties() {
		return ByteWrapper.create().put(idc.getBytes()).put(oid.getBytes()).get();
	}

	@Override
	public VocalHeader parse(byte[] buffer) {
		super.parse(buffer);
		ByteWrapper wrapper = ByteWrapper.wrap(buffer);

		// +8: Idc
		idc = Idc.fromCode(wrapper.getInt(8));

		// +12: Oid
		oid = Oid.fromCode(wrapper.getInt(12));
		super.setProperties(idc, oid);
		return this;
	}

	@Override
	public Idc getIdc() {
		return idc;
	}

	@Override
	public Oid getOid() {
		return oid;
	}

}
