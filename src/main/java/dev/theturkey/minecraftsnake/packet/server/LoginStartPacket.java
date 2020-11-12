package dev.theturkey.minecraftsnake.packet.server;

import com.sun.javafx.geom.Vec3d;
import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.Player;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.ServerCore;
import dev.theturkey.minecraftsnake.ServerState;
import dev.theturkey.minecraftsnake.ThreadHelper;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;
import dev.theturkey.minecraftsnake.packet.client.CChatPacket;
import dev.theturkey.minecraftsnake.packet.client.CPluginMessagePacket;
import dev.theturkey.minecraftsnake.packet.client.EncryptionRequestPacket;
import dev.theturkey.minecraftsnake.packet.client.JoinGamePacket;
import dev.theturkey.minecraftsnake.packet.client.LoginSuccessPacket;
import dev.theturkey.minecraftsnake.packet.client.PlayerPositionLookPacket;
import dev.theturkey.minecraftsnake.packet.client.PlayerSpawnPosition;
import dev.theturkey.minecraftsnake.packet.client.ServerDifficultyPacket;
import dev.theturkey.minecraftsnake.util.BlockPos;

import java.util.UUID;

public class LoginStartPacket implements IPacket
{
	public String username = "N/A";

	public static LoginStartPacket read(InputData data)
	{
		LoginStartPacket packet = new LoginStartPacket();
		try
		{
			packet.username = PacketHelper.readString(data.getIn());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return packet;
	}

	@Override
	public void handle(PlayerConnection connection)
	{
		connection.playerName = username;
		if(ServerCore.OFFLINE_MODE)
		{
			UUID uuid = UUID.fromString("276130dd-8c9a-4814-8328-2578f034e422");
			Player player = new Player(connection, uuid);
			player.setUsername(username);
			ThreadHelper.runOnMain(() -> ServerCore.getServer().addPlayer(player));
			LoginSuccessPacket loginSuccessPacket = new LoginSuccessPacket(player.getUsername(), uuid);
			connection.sendPacket(loginSuccessPacket);
			connection.state = ServerState.PLAY;

			try
			{
				Thread.sleep(1000);
			} catch(InterruptedException e)
			{
				e.printStackTrace();
			}

			JoinGamePacket joinGamePacket = new JoinGamePacket();
			connection.sendPacket(joinGamePacket);
			CPluginMessagePacket serverBrand = new CPluginMessagePacket("MC|Brand", "Snake".getBytes());
			connection.sendPacket(serverBrand);
			ServerDifficultyPacket serverDifficultyPacket = new ServerDifficultyPacket((byte) 0);
			connection.sendPacket(serverDifficultyPacket);
			PlayerSpawnPosition playerSpawnPacket = new PlayerSpawnPosition(new BlockPos(0, 100, 0));
			connection.sendPacket(playerSpawnPacket);
			PlayerPositionLookPacket playerPositionLookPacket = new PlayerPositionLookPacket(new Vec3d(0, 100, 0), 0, 0);
			connection.sendPacket(playerPositionLookPacket);
			CChatPacket chatPacket = new CChatPacket("Hello!");
			connection.sendPacket(chatPacket);
		}
		else
		{
			EncryptionRequestPacket encryptionRequest = new EncryptionRequestPacket();
			connection.sendPacket(encryptionRequest);
		}

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
		return "LoginStartPacket{" +
				"username='" + username + '\'' +
				'}';
	}
}
