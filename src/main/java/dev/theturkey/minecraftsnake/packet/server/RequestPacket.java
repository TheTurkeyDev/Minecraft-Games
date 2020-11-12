package dev.theturkey.minecraftsnake.packet.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.client.ResponsePacket;

public class RequestPacket implements IPacket
{
	public static RequestPacket read(InputData data)
	{
		return new RequestPacket();
	}

	@Override
	public void handle(PlayerConnection client)
	{
		JsonObject json = new JsonObject();

		JsonObject version = new JsonObject();
		json.add("version", version);
		version.addProperty("name", "Snake");
		version.addProperty("protocol", client.protocol);
		//version.addProperty("protocol", -1);

		JsonObject players = new JsonObject();
		json.add("players", players);
		players.addProperty("online", Integer.MIN_VALUE);
		players.addProperty("max", Integer.MAX_VALUE);
		JsonArray playersSample = new JsonArray();
		players.add("sample", playersSample);
		JsonObject player1 = new JsonObject();
		playersSample.add(player1);
		player1.addProperty("name", "TurkeyDev");
		player1.addProperty("id", "276130dd-8c9a-4814-8328-2578f034e422");

		JsonObject description = new JsonObject();
		json.add("description", description);
		description.addProperty("text", "                    SNEK                    ");
		description.addProperty("bold", "true");
		description.addProperty("color", "dark_green");

		JsonArray extras = new JsonArray();
		description.add("extra", extras);
		JsonObject description2 = new JsonObject();
		extras.add(description2);
		description2.addProperty("text", "                   404 Minecraft Not found               ");
		description2.addProperty("bold", "true");
		description2.addProperty("color", "dark_red");

		client.sendPacket(new ResponsePacket(json.toString()));
	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{

	}

	@Override
	public int getID()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "REQUEST";
	}
}
