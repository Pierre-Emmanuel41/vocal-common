package fr.pederobien.vocal.common.impl;

import fr.pederobien.vocal.common.interfaces.IVocalMessage;

public class VocalMessageFactory {
	private VocalProtocolManager manager;

	/**
	 * Creates a message factory associated to the mumble communication protocol.
	 * 
	 * @param firstSequenceNumber The first sequence number from which next messages sequence is incremented by 1.
	 */
	private VocalMessageFactory(int firstSequenceNumber) {
		manager = new VocalProtocolManager(firstSequenceNumber);
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
	 * @param errorCode  The message errorCode.
	 * @param properties The message properties.
	 * 
	 * @return The created message.
	 */
	public IVocalMessage create(VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		return manager.create(identifier, errorCode, properties);
	}

	/**
	 * Creates a message based on the given parameters associated to a specific version of the communication protocol.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param identifier The identifier of the request to create.
	 * @param errorCode  The message errorCode.
	 * @param properties The message properties.
	 * 
	 * @return A message associated to the given protocol version.
	 */
	public IVocalMessage create(float version, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		return manager.create(version, identifier, errorCode, properties);
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
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified. The latest version of the communication protocol is used to create the returned message.
	 * 
	 * @param message    The message to answer.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	public IVocalMessage answer(IVocalMessage message, Object... properties) {
		return manager.answer(message, properties);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified. A specific version of the communication protocol is used to create the returned message.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param message    The message to answer.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	public IVocalMessage answer(float version, IVocalMessage message, Object... properties) {
		return manager.answer(version, message, properties);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented.
	 * 
	 * @param message    The message to answer.
	 * @param identifier The response identifier.
	 * @param errorCode  The response ErrorCode.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IVocalMessage answer(IVocalMessage message, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		return (IVocalMessage) manager.answer(message.getHeader().getSequence(), identifier, errorCode, properties);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented. A specific
	 * version of the communication protocol is used to create the answer.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param message    The message to answer.
	 * @param identifier The identifier of the answer request.
	 * @param errorCode  The response ErrorCode.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IVocalMessage answer(float version, IVocalMessage message, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		return manager.answer(version, message.getHeader().getSequence(), identifier, errorCode, properties);
	}
}
