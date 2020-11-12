package dev.theturkey.minecraftsnake.packet;

import dev.theturkey.minecraftsnake.ServerState;
import dev.theturkey.minecraftsnake.packet.server.*;

import java.util.ArrayList;
import java.util.List;

public class PacketManager
{
	private static List<PacketWrapper> packets = new ArrayList<>();

	public static void init()
	{
		packets.add(new PacketWrapper(ServerState.HAND_SHAKE, 0, HandShakePacket::read));
		packets.add(new PacketWrapper(ServerState.STATUS, 0, RequestPacket::read));
		packets.add(new PacketWrapper(ServerState.STATUS, 1, PingPacket::read));
		packets.add(new PacketWrapper(ServerState.LOGIN, 0, LoginStartPacket::read));
		packets.add(new PacketWrapper(ServerState.LOGIN, 1, EncryptionResponsePacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0x02, SChatPacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0x03, ClientStatusPacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0x04, ClientSettingsPacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0x08, CloseWindowPacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0x09, PluginMessagePacket::read));
		packets.add(new PacketWrapper(ServerState.PLAY, 0xb, KeepAliveResponsePacket::read));
	}

	public static PacketWrapper getPacket(int packetID, ServerState state)
	{
		for(PacketWrapper packetWrapper : packets)
		{
			if(packetWrapper.packetID == packetID && packetWrapper.state == state)
			{
				return packetWrapper;
			}
		}
		return null;
	}
}
