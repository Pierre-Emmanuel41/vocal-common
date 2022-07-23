package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.VolumeResult;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class PlayerSpeakSetMessageV10 extends VocalMessage {
	private String playerName;
	private byte[] data;
	private boolean isMono, isEncoded;
	private VolumeResult volume;

	/**
	 * Creates a message to play an audio sample on client side.
	 * 
	 * @param header The message header.
	 */
	protected PlayerSpeakSetMessageV10(IVocalHeader header) {
		super(VocalIdentifier.PLAYER_SPEAK_SET, header);
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

		// Data mono status
		isMono = wrapper.getInt(first) == 1;
		first += 4;

		// Data encoded status
		isEncoded = wrapper.getInt(first) == 1;
		first += 4;

		// Global volume
		double global = wrapper.getDouble(first);
		first += 8;

		// Left volume
		double left = wrapper.getDouble(first);
		first += 8;

		// Right volume
		double right = wrapper.getDouble(first);

		volume = new VolumeResult(global, left, right);
		super.setProperties(playerName, data, isMono, isEncoded, volume);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		int index = 0;

		// Player's name
		playerName = (String) properties[index++];

		// Player's data
		data = (byte[]) properties[index++];

		// Data mono status
		isMono = (boolean) properties[index++];

		// Data encoded status
		isEncoded = (boolean) properties[index++];

		// Data sound volume
		volume = (VolumeResult) properties[index++];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		// Player's name
		wrapper.putString(playerName, true);

		// Player data
		wrapper.put(data, true);

		// Data mono status
		wrapper.putInt(isMono ? 1 : 0);

		// Data encoded status
		wrapper.putInt(isEncoded ? 1 : 0);

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
	 * @return True if the audio sample represented by the bytes array is a mono signal, false otherwise.
	 */
	public boolean isMono() {
		return isMono;
	}

	/**
	 * @return True if the audio sample represented by the bytes array is encoded, false otherwise.
	 */
	public boolean isEncoded() {
		return isEncoded;
	}

	/**
	 * @return The sound volume of the audio sample.
	 */
	public VolumeResult getVolume() {
		return volume;
	}
}
