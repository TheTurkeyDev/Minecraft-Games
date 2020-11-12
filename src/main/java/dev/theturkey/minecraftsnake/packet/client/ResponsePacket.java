package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class ResponsePacket implements IPacket
{
	private String json;

	public ResponsePacket(String json)
	{
		this.json = json;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer out)
	{
		out.writeString(json);
	}

	@Override
	public int getID()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "ResponsePacket{" +
				"json='" + json + '\'' +
				'}';
	}
}
