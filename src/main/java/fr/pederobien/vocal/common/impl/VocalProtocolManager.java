package fr.pederobien.vocal.common.impl;

import fr.pederobien.messenger.impl.ProtocolManager;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.vocal.common.impl.messages.v10.ProtocolV10;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalProtocolManager {
	private ProtocolManager manager;
	private float version;
	private IProtocol protocol;

	/**
	 * Creates a protocol manager associated to the mumble communication protocol.
	 * 
	 * @param beginIdentifier The first identifier from which next messages identifier are incremented by 1.
	 */
	public VocalProtocolManager(int beginIdentifier) {
		manager = new ProtocolManager(beginIdentifier, version -> new VocalHeader(version), header -> ((IVocalHeader) header).getIdentifier().name());

		initializeProtocols();

		version = 1.0f;
		protocol = manager.getProtocol(version).get();
	}

	/**
	 * Create a message based on the given parameters.
	 * 
	 * @param identifier the request identifier.
	 * @param properties The message properties.
	 * 
	 * @return The created message.
	 */
	public IMessage create(VocalIdentifier identifier, Object... properties) {
		IMessage message = protocol.get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setProperties(identifier);
		message.setProperties(properties);
		return message;
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented.
	 * 
	 * @param sequence   The sequence number of the request to answer.
	 * @param identifier The response identifier.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IMessage answer(int sequence, VocalIdentifier identifier, Object... properties) {
		IMessage message = protocol.get(identifier.name());
		if (message == null)
			return null;

		message.getHeader().setSequence(sequence);
		message.getHeader().setProperties(identifier);
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
	public IProtocol getProtocol() {
		return protocol;
	}

	private void initializeProtocols() {
		IProtocol protocolV10 = new ProtocolV10(this);
		manager.register(protocolV10.getVersion(), protocolV10);
	}
}
