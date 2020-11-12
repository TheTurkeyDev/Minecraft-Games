package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

import java.util.UUID;

public class LoginSuccessPacket implements IPacket
{
	private String userName;
	private UUID uuid;

	public LoginSuccessPacket(String userName, UUID uuid)
	{
		this.userName = userName;
		this.uuid = uuid;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeString(uuid.toString());
		buffer.writeString(userName);
	}

	@Override
	public int getID()
	{
		return 2;
	}

	@Override
	public String toString()
	{
		return "LoginSuccessPacket{" +
				"userName='" + userName + '\'' +
				", uuid=" + uuid +
				'}';
	}
}
