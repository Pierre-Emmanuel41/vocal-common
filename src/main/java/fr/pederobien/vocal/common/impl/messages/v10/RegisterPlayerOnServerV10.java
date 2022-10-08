package fr.pederobien.vocal.common.impl.messages.v10;

import java.util.ArrayList;
import java.util.List;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.impl.messages.v10.model.PlayerInfo;

public class RegisterPlayerOnServerV10 extends VocalMessage {
	private PlayerInfo playerInfo;

	/**
	 * Creates a message in order to registered a player on a vocal server.
	 * 
	 * @param header The message header.
	 */
	public RegisterPlayerOnServerV10(IHeader header) {
		super(VocalIdentifier.REGISTER_PLAYER_ON_SERVER, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		if (payload.length == 0 || getHeader().isError())
			return this;

		List<Object> properties = new ArrayList<Object>();
		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(payload);

		// Player's name
		String name = wrapper.nextString(wrapper.nextInt());
		properties.add(name);

		// Player's mute status
		boolean isMute = wrapper.nextInt() == 1;
		properties.add(isMute);

		// Player's deafen status
		boolean isDeafen = wrapper.nextInt() == 1;
		properties.add(isDeafen);

		playerInfo = new PlayerInfo(name, isMute, isDeafen, false);
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
		String name = (String) properties[currentIndex++];

		// Player's mute status
		boolean isMute = (boolean) properties[currentIndex++];

		// Player's deafen status
		boolean isDeafen = (boolean) properties[currentIndex++];

		playerInfo = new PlayerInfo(name, isMute, isDeafen, false);
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		if (getProperties().length == 0 || getHeader().isError())
			return wrapper.get();

		// Player's name
		wrapper.putString(playerInfo.getName(), true);

		// Player's mute status
		wrapper.putInt(playerInfo.isMute() ? 1 : 0);

		// Player's deafen status
		wrapper.putInt(playerInfo.isDeafen() ? 1 : 0);

		return wrapper.get();
	}

	/**
	 * @return A description of the player to add.
	 */
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
}
