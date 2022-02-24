package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.VocalProtocolManager;
import fr.pederobien.vocal.common.impl.VolumeResult;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class PlayerSpeakSetMessageV10 extends VocalMessage {
	private String playerName;
	private byte[] data;
	private VolumeResult volume;

	/**
	 * Creates a message to play an audio sample on client side.
	 * 
	 * @param header The message header.
	 */
	protected PlayerSpeakSetMessageV10(IVocalHeader header) {
		super(VocalProtocolManager.PLAYER_SPEAK_SET, header);
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

		// Global volume
		double global = wrapper.getDouble(first);
		first += 8;

		// Left volume
		double left = wrapper.getDouble(first);
		first += 8;

		// Right volume
		double right = wrapper.getDouble(first);

		volume = new VolumeResult(global, left, right);
		super.setProperties(playerName, data, volume);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		playerName = (String) properties[0];
		data = (byte[]) properties[1];
		volume = (VolumeResult) properties[2];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		// Player's name
		wrapper.putString(playerName, true);

		// Player data
		wrapper.put(data, true);

		// Global volume
		wrapper.putDouble(volume.getGlobal());

		// Left volume
		wrapper.putDouble(volume.getLeft());

		// Right volume
		wrapper.putDouble(volume.getRight());

		return wrapper.get();
	}

	/**
	 * @return The name of the speaking player
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

	/**
	 * @return The sound volume of the audio sample.
	 */
	public VolumeResult getVolume() {
		return volume;
	}
}
