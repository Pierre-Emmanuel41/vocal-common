package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class SetPlayerMuteStatusV10 extends VocalMessage {
	private String playerName;
	private boolean isMute;

	/**
	 * Creates a message in order to set the mute status of a player.
	 * 
	 * @param header The message header.
	 */
	protected SetPlayerMuteStatusV10(IVocalHeader header) {
		super(VocalIdentifier.SET_PLAYER_MUTE, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		if (getHeader().isError())
			return this;

		int first = 0;
		ByteWrapper wrapper = ByteWrapper.wrap(payload);

		// Player's name
		int playerNameLength = wrapper.getInt(first);
		first += 4;
		playerName = wrapper.getString(first, playerNameLength);
		first += playerNameLength;

		// Player's mute status
		isMute = wrapper.getInt(first) == 1;

		super.setProperties(playerName, isMute);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (getHeader().isError())
			return;

		playerName = (String) properties[0];
		isMute = (boolean) properties[1];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		if (getHeader().isError())
			return wrapper.get();

		// Player's name
		wrapper.putString(playerName, true);

		// Player's mute status
		wrapper.putInt(isMute ? 1 : 0);
		return wrapper.get();
	}

	/**
	 * @return The player name whose the mute status has changed.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @return True if the player is mute, false otherwise.
	 */
	public boolean isMute() {
		return isMute;
	}
}
