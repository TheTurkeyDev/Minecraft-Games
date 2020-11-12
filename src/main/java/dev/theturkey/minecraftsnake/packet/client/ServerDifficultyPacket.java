package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class ServerDifficultyPacket implements IPacket
{
	private byte difficulty;

	public ServerDifficultyPacket(byte difficulty)
	{
		this.difficulty = difficulty;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeByte(difficulty);
	}

	@Override
	public int getID()
	{
		return 0x0d;
	}
}
