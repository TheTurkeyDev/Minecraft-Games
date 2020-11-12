package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class LoginDisconnectPacket implements IPacket
{
	private String reason;

	public LoginDisconnectPacket(String reason)
	{
		this.reason = reason;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeString(reason);
	}

	@Override
	public int getID()
	{
		return 0;
	}
}
