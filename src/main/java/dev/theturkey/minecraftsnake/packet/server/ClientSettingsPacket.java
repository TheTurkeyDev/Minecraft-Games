package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class ClientSettingsPacket implements IPacket
{
	public String locale;
	public byte viewDistance;
	public int chatMode;
	public boolean chatColors;
	public int skinParts;
	private boolean rightHandMain;

	public static ClientSettingsPacket read(InputData data)
	{
		try
		{
			ClientSettingsPacket packet = new ClientSettingsPacket();
			packet.locale = PacketHelper.readString(data.getIn());
			packet.viewDistance = PacketHelper.readByte(data.getIn());
			packet.chatMode = PacketHelper.readVarInt(data.getIn());
			packet.chatColors = PacketHelper.readBoolean(data.getIn());
			packet.skinParts = PacketHelper.readUByte(data.getIn());
			packet.rightHandMain = PacketHelper.readBoolean(data.getIn());
			return packet;
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{

	}

	@Override
	public int getID()
	{
		return 0x04;
	}

	@Override
	public String toString()
	{
		return "ClientSettingsPacket{" +
				"locale='" + locale + '\'' +
				", viewDistance=" + viewDistance +
				", chatMode=" + chatMode +
				", chatColors=" + chatColors +
				", skinParts=" + skinParts +
				", rightHandMain=" + rightHandMain +
				'}';
	}
}
