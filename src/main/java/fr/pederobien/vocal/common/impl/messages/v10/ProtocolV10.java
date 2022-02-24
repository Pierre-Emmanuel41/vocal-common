package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.impl.MessageCreator;
import fr.pederobien.messenger.impl.Protocol;
import fr.pederobien.vocal.common.impl.VocalProtocolManager;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class ProtocolV10 extends Protocol {

	/**
	 * Creates a protocol associated to the version 1.0.
	 * 
	 * @param mumbleManager The manager associated to this protocol.
	 */
	public ProtocolV10(VocalProtocolManager mumbleManager) {
		super(mumbleManager.getManager().getIdentifiers(), 1.0f, mumbleManager.getManager().getHeader(), mumbleManager.getManager().getParser());

		register(new MessageCreator(VocalProtocolManager.PLAYER_SPEAK_INFO, header -> new PlayerSpeakInfoMessageV10((IVocalHeader) header)));
		register(new MessageCreator(VocalProtocolManager.PLAYER_SPEAK_SET, header -> new PlayerSpeakSetMessageV10((IVocalHeader) header)));
	}
}
