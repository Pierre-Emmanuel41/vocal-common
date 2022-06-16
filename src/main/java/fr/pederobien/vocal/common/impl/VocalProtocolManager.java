package fr.pederobien.vocal.common.impl;

import java.util.Optional;

import fr.pederobien.messenger.impl.ProtocolManager;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.vocal.common.impl.messages.v10.ProtocolV10;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;
import fr.pederobien.vocal.common.interfaces.IVocalMessage;

public class VocalProtocolManager {
	private ProtocolManager manager;
	private IProtocol latestProtocol;

	/**
	 * Creates a protocol manager associated to the mumble communication protocol.
	 * 
	 * @param firstSequenceNumber The first sequence number from which next messages sequence is incremented by 1.
	 */
	public VocalProtocolManager(int firstSequenceNumber) {
		manager = new ProtocolManager(firstSequenceNumber, version -> new VocalHeader(version), header -> ((IVocalHeader) header).getIdentifier().name());

		initializeProtocols();

		latestProtocol = manager.getLatest();
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
		IVocalMessage message = (IVocalMessage) latestProtocol.get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setProperties(identifier, errorCode);
		message.setProperties(properties);
		return message;
	}

	/**
	 * Create a message based on the given parameters associated to a specific version of the communication protocol.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param identifier The identifier of the request to create.
	 * @param errorCode  The message errorCode.
	 * @param properties The message properties.
	 * 
	 * @return A message associated to the given protocol version.
	 */
	public IVocalMessage create(float version, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		Optional<IProtocol> optProtocol = manager.getProtocol(version);
		if (!optProtocol.isPresent())
			return null;

		IVocalMessage message = (IVocalMessage) optProtocol.get().get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setProperties(identifier, errorCode);
		message.setProperties(properties);
		return message;
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified. The latest version of the communication protocol is used to create the answer.
	 * 
	 * @param message    The message to answer.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	public IVocalMessage answer(IVocalMessage message, Object... properties) {
		return (IVocalMessage) latestProtocol.answer(message, properties);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified. A specific version of the communication protocol is used to create the answer.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param message    The message to answer.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	public IVocalMessage answer(float version, IVocalMessage message, Object... properties) {
		Optional<IProtocol> optProtocol = manager.getProtocol(version);
		if (!optProtocol.isPresent())
			return null;

		return (IVocalMessage) optProtocol.get().answer(message, properties);
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented.
	 * 
	 * @param sequence   The sequence number of the request to answer.
	 * @param identifier The response identifier.
	 * @param errorCode  The response ErrorCode.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IVocalMessage answer(int sequence, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		IVocalMessage message = (IVocalMessage) latestProtocol.get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setSequence(sequence);
		message.getHeader().setProperties(identifier, errorCode);
		message.setProperties(properties);
		return message;
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented. A specific
	 * version of the communication protocol is used to create the answer.
	 * 
	 * @param version    The protocol version to use for the returned message.
	 * @param sequence   The sequence number of the answer request.
	 * @param identifier The identifier of the request to create.
	 * @param errorCode  The response ErrorCode.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IVocalMessage answer(float version, int sequence, VocalIdentifier identifier, VocalErrorCode errorCode, Object... properties) {
		Optional<IProtocol> optProtocol = manager.getProtocol(version);
		if (!optProtocol.isPresent())
			return null;

		IVocalMessage message = (IVocalMessage) optProtocol.get().get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setSequence(sequence);
		message.getHeader().setProperties(identifier, errorCode);
		message.setProperties(properties);
		return message;
	}

	/**
	 * @return The protocol manager in order to generate/parse messages.
	 */
	public ProtocolManager getManager() {
		return manager;
	}

	/**
	 * @return The latest protocol version.
	 */
	public IProtocol getLatestProtocol() {
		return latestProtocol;
	}

	private void initializeProtocols() {
		IProtocol protocolV10 = new ProtocolV10(this);
		manager.register(protocolV10.getVersion(), protocolV10);
	}
}
