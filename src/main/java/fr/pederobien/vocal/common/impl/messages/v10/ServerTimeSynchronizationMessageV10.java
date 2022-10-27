package fr.pederobien.vocal.common.impl.messages.v10;

import java.time.LocalTime;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;
import fr.pederobien.vocal.common.impl.VocalIdentifier;
import fr.pederobien.vocal.common.impl.messages.VocalMessage;

public class ServerTimeSynchronizationMessageV10 extends VocalMessage {
	private LocalTime time;

	public ServerTimeSynchronizationMessageV10(IHeader header) {
		super(VocalIdentifier.SERVER_TIME_SYNCHRO, header);
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
		super.setProperties(time);
		return this;
	}

	@Override
	public void setProperties(Object... properties) {
		super.setProperties(properties);

		if (properties.length == 0 || getHeader().isError())
			return;

		time = (LocalTime) properties[0];
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

		return wrapper.get();
	}

}
