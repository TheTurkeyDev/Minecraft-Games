package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.ServerCore;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionRequestPacket implements IPacket
{
	private byte[] veifBytes = new byte[8];

	public EncryptionRequestPacket()
	{
		try
		{
			SecureRandom.getInstanceStrong().nextBytes(veifBytes);
		} catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeString(ServerCore.SERVER_ID, 20);
		byte[] pubBytes = ServerCore.rPubKey.getEncoded();
		buffer.writeVarInt(pubBytes.length);
		buffer.writeBytes(pubBytes);
		buffer.writeVarInt(veifBytes.length);
		buffer.writeBytes(veifBytes);
	}

	@Override
	public int getID()
	{
		return 1;
	}
}
