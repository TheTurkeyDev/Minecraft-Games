package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class JoinGamePacket implements IPacket
{
	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeInt(0);
		buffer.writeByte((byte) 1);
		buffer.writeInt(0);
		buffer.writeByte((byte) 1);
		buffer.writeByte((byte) -1);
		buffer.writeString("minecraft:snake");
		buffer.writeBoolean(true);
	}

	@Override
	public int getID()
	{
		return 0x23;
	}
}
