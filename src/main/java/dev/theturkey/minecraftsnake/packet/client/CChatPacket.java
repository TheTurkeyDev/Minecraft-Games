package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class CChatPacket implements IPacket
{
	private String message;

	public CChatPacket(String message)
	{
		this.message = message;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeString("{\"text\":\"" + message + "\",\"bold\":\"false\"}");
		buffer.writeByte((byte) 0);
	}

	@Override
	public int getID()
	{
		return 0x0F;
	}

	@Override
	public String toString()
	{
		return "CChatPacket{" +
				"message='" + message + '\'' +
				'}';
	}
}
