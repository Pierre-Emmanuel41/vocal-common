package fr.pederobien.vocal.common.interfaces;

import fr.pederobien.messenger.interfaces.IMessage;

public interface IVocalMessage extends IMessage {

	/**
	 * @return The header associated to this message.
	 */
	IVocalHeader getHeader();
}
