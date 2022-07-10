package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.impl.MessageCreator;
import fr.pederobien.messenger.impl.Protocol;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.VocalProtocolManager;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class ProtocolV10 extends Protocol {

	/**
	 * Creates a protocol associated to the version 1.0.
	 * 
	 * @param mumbleManager The manager associated to this protocol.
	 */
	public ProtocolV10(VocalProtocolManager mumbleManager) {
		super(mumbleManager.getManager().getSequence(), 1.0f, mumbleManager.getManager().getHeader(), mumbleManager.getManager().getParser());

		// Communication protocol messages
		register(new MessageCreator(VocalIdentifier.GET_CP_VERSIONS.name(), header -> new GetCommunicationProtocolVersionsV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.SET_CP_VERSION.name(), header -> new SetCommunicationProtocolVersionV10((IVocalHeader) header)));

		// Server configuration
		register(new MessageCreator(VocalIdentifier.GET_SERVER_CONFIGURATION.name(), header -> new GetServerConfigurationV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.SET_SERVER_JOIN.name(), header -> new SetServerJoinV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.SET_SERVER_LEAVE.name(), header -> new SetServerLeaveV10((IVocalHeader) header)));

		// Player messages
		register(new MessageCreator(VocalIdentifier.REGISTER_PLAYER_ON_SERVER.name(), header -> new RegisterPlayerOnServerV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.UNREGISTER_PLAYER_FROM_SERVER.name(), header -> new UnregisterPlayerFromServerV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.SET_PLAYER_NAME.name(), header -> new SetPlayerNameV10((IVocalHeader) header)));

		// Audio messages
		register(new MessageCreator(VocalIdentifier.PLAYER_SPEAK_INFO.name(), header -> new PlayerSpeakInfoMessageV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.PLAYER_SPEAK_SET.name(), header -> new PlayerSpeakSetMessageV10((IVocalHeader) header)));
	}
}
