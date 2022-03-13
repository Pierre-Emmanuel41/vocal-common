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

		register(new MessageCreator(VocalIdentifier.PLAYER_SPEAK_INFO.name(), header -> new PlayerSpeakInfoMessageV10((IVocalHeader) header)));
		register(new MessageCreator(VocalIdentifier.PLAYER_SPEAK_SET.name(), header -> new PlayerSpeakSetMessageV10((IVocalHeader) header)));
	}
}
