package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class CloseWindowPacket implements IPacket
{
	public int windowID;

	public static CloseWindowPacket read(InputData data)
	{
		try
		{
			CloseWindowPacket packet = new CloseWindowPacket();
			packet.windowID = PacketHelper.readByte(data.getIn());
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
		return 0x09;
	}


	@Override
	public String toString()
	{
		return "CloseWindow{" +
				"windowID=" + windowID +
				'}';
	}
}
