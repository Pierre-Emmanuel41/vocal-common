package fr.pederobien.vocal.common.interfaces;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.vocal.common.impl.VocalErrorCode;
import fr.pederobien.vocal.common.impl.VocalIdentifier;

public interface IVocalHeader extends IHeader {

	/**
	 * @return The identifier associated to this request.
	 */
	VocalIdentifier getIdentifier();

	/**
	 * @return The header error code.
	 */
	VocalErrorCode getErrorCode();

	/**
	 * @return True if and only if the error code is not equals to {@link ErrorCode#NONE}.
	 */
	default boolean isError() {
		return getErrorCode() != VocalErrorCode.NONE;
	}
}
