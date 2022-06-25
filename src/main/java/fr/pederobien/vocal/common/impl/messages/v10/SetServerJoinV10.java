package fr.pederobien.vocal.common.impl.messages.v10;

import java.util.ArrayList;
import java.util.List;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class SetServerJoinV10 extends VocalMessage {
	private String playerName;
	private boolean isMute, isDeafen;

	/**
	 * Creates a message in order to join a mumble server.
	 * 
	 * @param header The message header.
	 */
	protected SetServerJoinV10(IVocalHeader header) {
		super(VocalIdentifier.SET_SERVER_JOIN, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		if (payload.length == 0 || getHeader().isError())
			return this;

		List<Object> properties = new ArrayList<Object>();
		int first = 0;
		ByteWrapper wrapper = ByteWrapper.wrap(payload);

		// Player's name
		int playerNameLength = wrapper.getInt(first);
		first += 4;
		playerName = wrapper.getString(first, playerNameLength);
		properties.add(playerName);
		first += playerNameLength;

		// Player's mute status
		isMute = wrapper.getInt(first) == 1;
		properties.add(isMute);
		first += 4;

		// Player's deafen status
		isDeafen = wrapper.getInt(first) == 1;
		properties.add(isDeafen);
		first += 4;

		super.setProperties(properties.toArray());
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (properties.length == 0 || getHeader().isError())
			return;

		int currentIndex = 0;

		// Player's name
		playerName = (String) properties[currentIndex++];

		// Player's mute status
		isMute = (boolean) properties[currentIndex++];

		// Player's deafen status
		isDeafen = (boolean) properties[currentIndex++];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		if (getProperties().length == 0 || getHeader().isError())
			return wrapper.get();

		// Player's name
		wrapper.putString(playerName, true);

		// Player's mute status
		wrapper.putInt(isMute ? 1 : 0);

		// Player's deafen status
		wrapper.putInt(isDeafen ? 1 : 0);

		return wrapper.get();
	}

	/**
	 * @return The player name.
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

	/**
	 * @return True if the player is deafen, false otherwise.
	 */
	public boolean isDeafen() {
		return isDeafen;
	}
}
