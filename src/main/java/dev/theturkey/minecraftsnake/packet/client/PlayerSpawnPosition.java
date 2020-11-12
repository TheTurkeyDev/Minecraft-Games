package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.util.BlockPos;

public class PlayerSpawnPosition implements IPacket
{
	private BlockPos pos;

	public PlayerSpawnPosition(BlockPos pos)
	{
		this.pos = pos;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeBlockPos(pos);
	}

	@Override
	public int getID()
	{
		return 0x46;
	}

	@Override
	public String toString()
	{
		return "PlayerSpawnPosition{" +
				"pos=" + pos +
				'}';
	}
}
