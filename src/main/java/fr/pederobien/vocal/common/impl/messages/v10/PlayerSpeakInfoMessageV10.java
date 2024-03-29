package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;
import fr.pederobien.vocal.common.interfaces.IVocalHeader;

public class PlayerSpeakInfoMessageV10 extends VocalMessage {
	private String playerName;
	private byte[] data;
	private boolean isMono, isEncoded;

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
		if (payload.length == 0 || getHeader().isError())
			return this;

		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(payload);

		// Player name
		playerName = wrapper.nextString(wrapper.nextInt());

		// Player data
		data = wrapper.next(wrapper.nextInt());

		// Data mono status
		isMono = wrapper.nextInt() == 1;

		// Data encoded status
		isEncoded = wrapper.nextInt() == 1;

		super.setProperties(playerName, data, isMono, isEncoded);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		int index = 0;

		// Player's name
		playerName = (String) properties[index++];

		// Player's audio sample
		data = (byte[]) properties[index++];

		// Data encoded status
		isEncoded = (boolean) properties[index++];

		// Data mono status
		isMono = (boolean) properties[index++];
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
}
