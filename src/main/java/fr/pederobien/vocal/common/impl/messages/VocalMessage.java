package fr.pederobien.vocal.common.impl.messages;

import fr.pederobien.messenger.impl.Message;
import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;
import fr.pederobien.vocal.common.interfaces.IVocalMessage;

public abstract class VocalMessage extends Message implements IVocalMessage {

	/**
	 * Creates a vocal message represented by a name and a vocal header. The message name is used for storage only but is never used
	 * during the bytes generation.
	 * 
	 * @param identifier The message identifier.
	 * @param header     The message header.
	 */
	public VocalMessage(VocalIdentifier identifier, IHeader header) {
		super(identifier.name(), header);
	}

	@Override
	public IVocalHeader getHeader() {
		return (IVocalHeader) super.getHeader();
	}
}
