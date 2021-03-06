package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectChat extends DataWatcherObject<String> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = StringSerializer.readString(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		StringSerializer.writeString(to, version, value);
	}

}
