package fr.pederobien.vocal.common.impl.messages.v10;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;

public class UnregisterPlayerFromServerV10 extends VocalMessage {
	private String name;

	/**
	 * Creates a message in order to registered a player on a vocal server.
	 * 
	 * @param header The message header.
	 */
	public UnregisterPlayerFromServerV10(IHeader header) {
		super(VocalIdentifier.UNREGISTER_PLAYER_FROM_SERVER, header);
	}

	@Override
	public IMessage parse(byte[] payload) {
		if (payload.length == 0 || getHeader().isError())
			return this;

		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(payload);

		// Player's name
		name = wrapper.nextString(wrapper.nextInt());

		super.setProperties(name);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (properties.length == 0 || getHeader().isError())
			return;

		int currentIndex = 0;

		// Player's name
		name = (String) properties[currentIndex++];
	}

	@Override
	protected byte[] generateProperties() {
		ByteWrapper wrapper = ByteWrapper.create();

		if (getProperties().length == 0 || getHeader().isError())
			return wrapper.get();

		// Player's name
		wrapper.putString(name, true);

		return wrapper.get();
	}

	/**
	 * @return The name of the player to remove.
	 */
	public String getPlayerName() {
		return name;
	}
}
