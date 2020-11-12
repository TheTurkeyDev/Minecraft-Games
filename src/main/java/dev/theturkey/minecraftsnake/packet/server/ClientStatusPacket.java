package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class ClientStatusPacket implements IPacket
{
	public int actionID;

	public static ClientStatusPacket read(InputData data)
	{
		try
		{
			ClientStatusPacket packet = new ClientStatusPacket();
			packet.actionID = PacketHelper.readVarInt(data.getIn());
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

	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{

	}

	@Override
	public int getID()
	{
		return 0x03;
	}


	@Override
	public String toString()
	{
		return "ClientStatus{" +
				"actionID=" + actionID +
				'}';
	}
}
