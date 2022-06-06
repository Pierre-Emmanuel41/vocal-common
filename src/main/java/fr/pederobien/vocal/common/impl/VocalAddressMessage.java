package fr.pederobien.vocal.common.impl;

import java.net.InetSocketAddress;

import fr.pederobien.communication.impl.AddressMessage;
import fr.pederobien.messenger.interfaces.IMessage;

public class VocalAddressMessage extends AddressMessage {
	private IMessage message;

	/**
	 * Create a request message to be send to a remote.
	 * 
	 * @param message The message that contains the bytes to send to the remote and the identifier.
	 * @param address The address at which the message should be sent.
	 */
	public VocalAddressMessage(IMessage message, InetSocketAddress address) {
		super(message.generate(), message.getHeader().getSequence(), address);
		this.message = message;
	}

	/**
	 * Create a request message to be send to a remote.
	 * 
	 * @param message The message that contains the bytes to send to the remote and the identifier.
	 */
	public VocalAddressMessage(IMessage message) {
		this(message, null);
	}

	@Override
	public String toString() {
		return message.toString();
	}
}
