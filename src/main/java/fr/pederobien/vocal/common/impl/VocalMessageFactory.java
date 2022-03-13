package fr.pederobien.vocal.common.impl;

import fr.pederobien.vocal.common.interfaces.IVocalMessage;

public class VocalMessageFactory {
	private VocalProtocolManager manager;

	/**
	 * Creates a message factory associated to the mumble communication protocol.
	 * 
	 * @param beginIdentifier The first identifier from which next messages identifier are incremented by 1.
	 */
	private VocalMessageFactory(int beginIdentifier) {
		manager = new VocalProtocolManager(beginIdentifier);
	}

	/**
	 * Creates a message factory associated to the mumble communication protocol.
	 * 
	 * @param beginIdentifier The first identifier from which next messages identifier are incremented by 1.
	 */
	public static VocalMessageFactory getInstance(int beginIdentifier) {
		return new VocalMessageFactory(beginIdentifier);
	}

	/**
	 * Create a message based on the given parameters.
	 * 
	 * @param identifier the request identifier.
	 * @param properties The message properties.
	 * 
	 * @return The created message.
	 */
	public IVocalMessage create(VocalIdentifier identifier, Object... properties) {
		return (IVocalMessage) manager.create(identifier, properties);
	}

	/**
	 * Parse the given buffer in order to create the associated header and the payload.
	 * 
	 * @param buffer The bytes array received from the remote.
	 * 
	 * @return A new message.
	 */
	public IVocalMessage parse(byte[] buffer) {
		return (IVocalMessage) manager.getManager().parse(buffer);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented.
	 * 
	 * @param message    The message to answer.
	 * @param identifier The response identifier.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IVocalMessage answer(IVocalMessage message, VocalIdentifier identifier, Object... properties) {
		return (IVocalMessage) manager.answer(message.getHeader().getSequence(), identifier, properties);
	}
}
