package fr.pederobien.vocal.common.impl.messages.v10;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.impl.messages.v10.model.PlayerInfo;

public class GetServerConfigurationV10 extends VocalMessage {
	private Map<String, PlayerInfo> serverInfo;

	/**
	 * Creates a message in order to get the configuration of a vocal server.
	 * 
	 * @param header The message header.
	 */
	public GetServerConfigurationV10(IHeader header) {
		super(VocalIdentifier.GET_SERVER_CONFIGURATION, header);

		serverInfo = new LinkedHashMap<String, PlayerInfo>();
	}

	@Override
	public IMessage parse(byte[] payload) {
		if (payload.length == 0 || getHeader().isError())
			return this;

		List<Object> properties = new ArrayList<Object>();
		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(payload);

		// Number of players
		int numberOfChannelPlayers = wrapper.nextInt();
		properties.add(numberOfChannelPlayers);

		for (int j = 0; j < numberOfChannelPlayers; j++) {
			// Player's name
			String playerName = wrapper.nextString(wrapper.nextInt());
			properties.add(playerName);

			// Player's mute status
			boolean isMute = wrapper.nextInt() == 1;
			properties.add(isMute);

			// Player's deafen status
			boolean isDeafen = wrapper.nextInt() == 1;
			properties.add(isDeafen);

			// Player's muteBy status
			boolean isMuteByMainPlayer = wrapper.nextInt() == 1;
			properties.add(isMuteByMainPlayer);

			serverInfo.put(playerName, new PlayerInfo(playerName, isMute, isDeafen, isMuteByMainPlayer));
		}

		super.setProperties(properties.toArray());
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (properties.length == 0 || getHeader().isError())
			return;

		int currentIndex = 0;

		// Number of players registered in the channel
		int numberOfChannelsPlayers = (int) properties[currentIndex++];

		for (int j = 0; j < numberOfChannelsPlayers; j++) {
			// Player's name
			String playerName = (String) properties[currentIndex++];

			// Player's mute status
			boolean playerMute = (boolean) properties[currentIndex++];

			// Player's deafen status
			boolean playerDeafen = (boolean) properties[currentIndex++];

			// Player's muteBy status
			boolean isMuteByMainPlayer = (boolean) properties[currentIndex++];

			serverInfo.put(playerName, new PlayerInfo(playerName, playerMute, playerDeafen, isMuteByMainPlayer));
		}
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		if (getProperties().length == 0 || getHeader().isError())
			return wrapper.get();

		// Number of players
		wrapper.putInt(serverInfo.size());

		for (PlayerInfo playerInfo : serverInfo.values()) {
			// Player's name
			wrapper.putString(playerInfo.getName(), true);

			// Player's mute status
			wrapper.putInt(playerInfo.isMute() ? 1 : 0);

			// Player's deafen status
			wrapper.putInt(playerInfo.isDeafen() ? 1 : 0);

			// Player's muteBy status
			wrapper.putInt(playerInfo.isMuteByMainPlayer() ? 1 : 0);
		}
		return wrapper.get();
	}

	/**
	 * @return A description that contains a vocal server configuration.
	 */
	public Map<String, PlayerInfo> getServerInfo() {
		return serverInfo;
	}
}
