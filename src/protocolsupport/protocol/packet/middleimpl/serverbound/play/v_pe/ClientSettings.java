package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		locale = cache.getLocale();
		int fromClient = VarNumberSerializer.readSVarInt(clientdata);
		System.err.println("View-Distance From clinet: " + fromClient + " server: " + Bukkit.getViewDistance());
		viewDist = (byte) (fromClient - 1);
		chatMode = 0;
		chatColors = true;
		skinFlags = 255;
		mainHand = 1;
		System.err.println("Sending to client: " + ((viewDist > Bukkit.getViewDistance()) ? (Bukkit.getViewDistance() + 1) : (viewDist + 1)));
		sendResponse();
	}
	
	public void sendResponse() {
		ByteBuf chunkResponse = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(chunkResponse, PEPacketIDs.CHUNK_RADIUS);
		chunkResponse.writeByte(0);
		chunkResponse.writeByte(0);
		//should exactly match the view distance that server uses to broadcast chunks. +1 because mcpe includes the chunk client is standing in in calculations, while pc does not
		VarNumberSerializer.writeSVarInt(chunkResponse, (viewDist > Bukkit.getViewDistance()) ? (Bukkit.getViewDistance() + 1) : (viewDist + 1));
		connection.sendRawPacket(MiscSerializer.readAllBytes(chunkResponse));
	}

}
