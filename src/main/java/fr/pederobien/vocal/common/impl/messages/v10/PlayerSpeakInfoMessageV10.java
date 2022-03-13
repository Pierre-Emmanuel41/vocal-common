package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class PlayerSpeakInfoMessageV10 extends VocalMessage {
	private String playerName;
	private byte[] data;

	/**
	 * Creates a message representing an audio sample to dispatch on server side.
	 * 
	 * @param header The message header.
	 */
	protected PlayerSpeakInfoMessageV10(IVocalHeader header) {
		super(VocalIdentifier.PLAYER_SPEAK_INFO, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		int first = 0;
		ByteWrapper wrapper = ByteWrapper.wrap(payload);

		// Player name
		int playerNameLength = wrapper.getInt(first);
		first += 4;
		playerName = wrapper.getString(first, playerNameLength);
		first += playerNameLength;

		// Player data
		int playerDataLength = wrapper.getInt(first);
		first += 4;
		data = wrapper.extract(first, playerDataLength);
		first += playerDataLength;

		super.setProperties(playerName, data);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		playerName = (String) properties[0];
		data = (byte[]) properties[1];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		// Player's name
		wrapper.putString(playerName, true);

		// Player data
		wrapper.put(data, true);

		return wrapper.get();
	}

	/**
	 * @return The name of the speaking player.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @return The bytes array corresponding to one sample.
	 */
	public byte[] getData() {
		return data;
	}
}
