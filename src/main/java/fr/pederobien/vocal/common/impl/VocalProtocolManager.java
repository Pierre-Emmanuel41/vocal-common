package fr.pederobien.vocal.common.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.pederobien.messenger.impl.ProtocolManager;
import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.vocal.common.impl.messages.v10.ProtocolV10;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class VocalProtocolManager {
	/**
	 * The name of the message to dispatch an audio sample.
	 */
	public static final String PLAYER_SPEAK_INFO = "PlayerSpeakInfo";

	/**
	 * The name of the message to play an audio sample.
	 */
	public static final String PLAYER_SPEAK_SET = "PlayerSpeakSet";

	private ProtocolManager manager;
	private Map<Idc, Map<Oid, String>> associations;
	private Function<IHeader, String> parser;
	private float version;
	private IProtocol protocol;

	/**
	 * Creates a protocol manager associated to the mumble communication protocol.
	 * 
	 * @param beginIdentifier The first identifier from which next messages identifier are incremented by 1.
	 */
	public VocalProtocolManager(int beginIdentifier) {
		parser = header -> {
			IVocalHeader mumbleHeader = (IVocalHeader) header;
			return associations.get(mumbleHeader.getIdc()).get(mumbleHeader.getOid());
		};
		manager = new ProtocolManager(beginIdentifier, version -> new VocalHeader(version), parser);

		initializeAssociations();
		initializeProtocols();

		version = 1.0f;
		protocol = manager.getProtocol(version).get();
	}

	/**
	 * Create a message based on the given parameters.
	 * 
	 * @param idc     The message idc.
	 * @param oid     The message oid.
	 * @param payload The message payload.
	 * 
	 * @return The created message.
	 */
	public IMessage create(Idc idc, Oid oid, Object... payload) {
		IMessage message = protocol.get(associations.get(idc).get(oid));
		if (message == null)
			return null;

		message.getHeader().setProperties(idc, oid);
		message.setProperties(payload);
		return message;
	}

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. The identifier is not incremented.
	 * 
	 * @param message    The message to answer.
	 * @param idc        The response IDC.
	 * @param oid        The response OID.
	 * @param properties The response properties.
	 * 
	 * @return The message associated to the answer.
	 */
	public IMessage answer(int identifier, Idc idc, Oid oid, Object... properties) {
		IMessage message = protocol.get(associations.get(idc).get(oid));
		if (message == null)
			return null;

		message.getHeader().setSequence(identifier);
		message.getHeader().setProperties(idc, oid);
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
		associations = new HashMap<Idc, Map<Oid, String>>();

		// Player speak map
		Map<Oid, String> playerSpeakMap = new HashMap<Oid, String>();
		playerSpeakMap.put(Oid.INFO, PLAYER_SPEAK_INFO);
		playerSpeakMap.put(Oid.SET, PLAYER_SPEAK_SET);
		associations.put(Idc.PLAYER_SPEAK, playerSpeakMap);
	}

	private void initializeProtocols() {
		IProtocol protocolV10 = new ProtocolV10(this);
		manager.register(protocolV10.getVersion(), protocolV10);
	}
}
