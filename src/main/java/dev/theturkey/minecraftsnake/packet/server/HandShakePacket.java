package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.ServerState;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class HandShakePacket implements IPacket
{
	public int protocolVer;
	public String serverAddress;
	public int port;
	public int nextState;

	public static HandShakePacket read(InputData data)
	{
		try
		{
			HandShakePacket packet = new HandShakePacket();
			packet.protocolVer = PacketHelper.readVarInt(data.getIn());
			packet.serverAddress = PacketHelper.readString(data.getIn());
			packet.port = PacketHelper.readUShort(data.getIn());
			packet.nextState = PacketHelper.readVarInt(data.getIn());
			return packet;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void handle(PlayerConnection client)
	{
		client.protocol = protocolVer;
		if(nextState == 1)
			client.state = ServerState.STATUS;
		else if(nextState == 2)
			client.state = ServerState.LOGIN;
	}

	@Override
	public void write(PacketBuffer buffer)
	{

	}

	@Override
	public int getID()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "HandShakePacket{" +
				"protocolVer=" + protocolVer +
				", serverAddress='" + serverAddress +
				", port=" + port +
				", nextState=" + nextState +
				'}';
	}
}
