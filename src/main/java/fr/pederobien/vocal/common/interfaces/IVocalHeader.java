package fr.pederobien.vocal.common.interfaces;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.vocal.common.impl.Idc;
import fr.pederobien.vocal.common.impl.Oid;

public interface IVocalHeader extends IHeader {

	/**
	 * @return The header idc.
	 */
	Idc getIdc();

	/**
	 * @return The header oid.
	 */
	Oid getOid();
}
