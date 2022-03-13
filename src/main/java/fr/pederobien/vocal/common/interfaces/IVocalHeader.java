package fr.pederobien.vocal.common.interfaces;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.vocal.common.impl.VocalIdentifier;

public interface IVocalHeader extends IHeader {

	/**
	 * @return The identifier associated to this request.
	 */
	VocalIdentifier getIdentifier();
}
