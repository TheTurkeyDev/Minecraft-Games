package dev.theturkey.minecraftsnake;

import dev.theturkey.minecraftsnake.packet.IPacket;

import java.util.UUID;

public class Player
{
	private PlayerConnection connection;
	private String username = "";
	private UUID uuid;

	public Player(PlayerConnection connection, UUID uuid)
	{
		this.connection = connection;
		this.uuid = uuid;
		this.connection.setPlayer(this);
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void sendPacket(IPacket packet)
	{
		connection.sendPacket(packet);
	}
}
