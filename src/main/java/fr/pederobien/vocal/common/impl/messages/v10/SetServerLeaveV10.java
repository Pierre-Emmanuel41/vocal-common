package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class SetServerLeaveV10 extends VocalMessage {

	/**
	 * Creates a message in order to leave a mumble server.
	 * 
	 * @param header The message header.
	 */
	protected SetServerLeaveV10(IVocalHeader header) {
		super(VocalIdentifier.SET_SERVER_LEAVE, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		return this;
	}

	@Override
	protected byte[] generateProperties() {
		return new byte[0];
	}
}
