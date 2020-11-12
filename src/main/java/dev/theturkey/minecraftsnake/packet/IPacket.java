package dev.theturkey.minecraftsnake.packet;

import dev.theturkey.minecraftsnake.PlayerConnection;

public interface IPacket
{
	void handle(PlayerConnection client);

	void write(PacketBuffer buffer);

	int getID();
}
