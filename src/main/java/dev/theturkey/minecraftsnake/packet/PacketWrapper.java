package dev.theturkey.minecraftsnake.packet;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.ServerState;

import java.util.Objects;
import java.util.function.Function;

public class PacketWrapper
{
	public ServerState state;
	public int packetID;
	public Function<InputData, IPacket> createPacket;

	public PacketWrapper(ServerState state, int packetID, Function<InputData, IPacket> createPacket)
	{
		this.state = state;
		this.packetID = packetID;
		this.createPacket = createPacket;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		PacketWrapper that = (PacketWrapper) o;
		return packetID == that.packetID &&
				state == that.state;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(state, packetID);
	}
}
