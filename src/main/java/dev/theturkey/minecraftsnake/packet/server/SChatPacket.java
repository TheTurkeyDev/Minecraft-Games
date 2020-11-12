package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.Player;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.ServerCore;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;
import dev.theturkey.minecraftsnake.packet.client.CChatPacket;

public class SChatPacket implements IPacket
{
	private String message;

	public static SChatPacket read(InputData data)
	{
		try
		{
			SChatPacket packet = new SChatPacket();
			packet.message = PacketHelper.readString(data.getIn());
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
		for(Player player : ServerCore.getServer().players)
			player.sendPacket(new CChatPacket("<" + client.getPlayer().getUsername() + "> " + message));
	}

	@Override
	public void write(PacketBuffer buffer)
	{

	}

	@Override
	public int getID()
	{
		return 0x02;
	}

	@Override
	public String toString()
	{
		return "SChatPacket{" +
				"message='" + message + '\'' +
				'}';
	}
}
