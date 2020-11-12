package dev.theturkey.minecraftsnake.packet.client;

import com.sun.javafx.geom.Vec3d;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class PlayerPositionLookPacket implements IPacket
{
	private Vec3d pos;
	private float yaw;
	private float pitch;

	public PlayerPositionLookPacket(Vec3d pos, float yaw, float pitch)
	{
		this.pos = pos;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeDouble(pos.x);
		buffer.writeDouble(pos.y);
		buffer.writeDouble(pos.z);
		buffer.writeFloat(this.yaw);
		buffer.writeFloat(this.pitch);
		buffer.writeByte((byte) 0);
		buffer.writeVarInt(0);
	}

	@Override
	public int getID()
	{
		return 0x2F;
	}
}
