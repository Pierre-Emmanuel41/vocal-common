package fr.pederobien.vocal.common.impl;

import java.util.Map;
import java.util.function.Function;

import fr.pederobien.messenger.impl.ProtocolManager;
import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.vocal.common.impl.messages.v10.ProtocolV10;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalProtocolManager {
	private ProtocolManager manager;
	private Map<VocalIdentifier, String> associations;
	private Function<IHeader, String> parser;
	private float version;
	private IProtocol protocol;

	/**
	 * Creates a protocol manager for the communication between a vocal client and a vocal server..
	 * 
	 * @param sequence The first sequence number from which next messages sequence are incremented by 1.
	 */
	public VocalProtocolManager(int sequence) {
		parser = header -> {
			IVocalHeader vocalHeader = (IVocalHeader) header;
			return associations.get(vocalHeader.getIdentifier());
		};
		manager = new ProtocolManager(sequence, version -> new VocalHeader(version), parser);

		initializeAssociations();
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
		IMessage message = protocol.get(associations.get(identifier));
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
		IMessage message = protocol.get(associations.get(identifier));
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

	private void initializeAssociations() {
		associations.put(VocalIdentifier.PLAYER_SPEAK_INFO, VocalIdentifier.PLAYER_SPEAK_INFO.name());
		associations.put(VocalIdentifier.PLAYER_SPEAK_SET, VocalIdentifier.PLAYER_SPEAK_SET.name());
	}

	private void initializeProtocols() {
		IProtocol protocolV10 = new ProtocolV10(this);
		manager.register(protocolV10.getVersion(), protocolV10);
	}
}
