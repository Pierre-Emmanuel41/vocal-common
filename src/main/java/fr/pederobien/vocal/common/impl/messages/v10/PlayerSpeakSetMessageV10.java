package fr.pederobien.vocal.common.impl.messages.v10;

import java.time.LocalTime;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.VolumeResult;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class PlayerSpeakSetMessageV10 extends VocalMessage {
	private String playerName;
	private LocalTime time;
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
		if (payload.length == 0 || getHeader().isError())
			return this;

		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(payload);

		// Hour
		byte hour = wrapper.next();

		// Minute
		byte minute = wrapper.next();

		// Second
		byte second = wrapper.next();

		// Nano
		int nano = wrapper.nextInt();

		time = LocalTime.of(hour, minute, second, nano);

		// Player name
		playerName = wrapper.nextString(wrapper.nextInt());

		// Player data
		data = wrapper.next(wrapper.nextInt());

		// Data mono status
		isMono = wrapper.nextInt() == 1;

		// Data encoded status
		isEncoded = wrapper.nextInt() == 1;

		// Global volume
		double global = wrapper.nextDouble();

		// Left volume
		double left = wrapper.nextDouble();

		// Right volume
		double right = wrapper.nextDouble();

		volume = new VolumeResult(global, left, right);
		super.setProperties(time, playerName, data, isMono, isEncoded, volume);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (properties.length == 0 || getHeader().isError())
			return;

		int index = 0;

		// Time stamp
		time = (LocalTime) properties[index++];

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

		if (getProperties().length == 0 || getHeader().isError())
			return wrapper.get();

		// Hour
		wrapper.put((byte) time.getHour());

		// Minute
		wrapper.put((byte) time.getMinute());

		// Second
		wrapper.put((byte) time.getSecond());

		// Nano
		wrapper.putInt(time.getNano());

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
	 * @return The time stamp at which this sample has been sent by the server.
	 */
	public LocalTime getTime() {
		return time;
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
