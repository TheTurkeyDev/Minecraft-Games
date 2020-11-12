package dev.theturkey.minecraftsnake;

import dev.theturkey.minecraftsnake.packet.PacketManager;
import dev.theturkey.minecraftsnake.packet.client.KeepAlivePacket;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerCore
{
	public static final int MIN_TICK_TIME = 1000 / 20;
	public static Random random = new Random();
	public static final String SERVER_ID = "SNEK_SERVER";
	public static final boolean OFFLINE_MODE = true;

	public static RSAPublicKey rPubKey;
	public static RSAPrivateKey rPriKey;

	private static ServerCore server;

	public List<Player> players = new ArrayList<>();
	private long ticks = 0;

	public ServerCore()
	{
		server = this;
		PacketManager.init();

		startConnectionHandler();

		long tickingTime = System.currentTimeMillis();
		long lastTicks = 0;
		boolean running = true;
		while(running)
		{
			long startTime = System.currentTimeMillis();
			//UPDATE

			if(ticks % 100 == 0)
			{
				KeepAlivePacket keepAlivePacket = new KeepAlivePacket(random.nextLong());
				for(Player player : players)
				{
					player.sendPacket(keepAlivePacket);
				}
			}

			ThreadHelper.executeWaiting();

			ticks++;

			if(System.currentTimeMillis() - tickingTime > 1000)
			{
				//System.out.println("Ticks in last second: " + (ticks - lastTicks));
				lastTicks = ticks;
				tickingTime = System.currentTimeMillis();
			}

			long duration = System.currentTimeMillis() - startTime;
			try
			{
				Thread.sleep(Math.max(0, MIN_TICK_TIME - duration));
			} catch(InterruptedException e)
			{
				e.printStackTrace();
				running = false;
			}
		}
	}

	public void startConnectionHandler()
	{
		Thread thread = new Thread(() ->
		{
			try
			{
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(2048);
				KeyPair kp = kpg.generateKeyPair();
				rPubKey = (RSAPublicKey) kp.getPublic();
				rPriKey = (RSAPrivateKey) kp.getPrivate();

			} catch(NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				return;
			}

			ServerSocket socket;
			try
			{
				socket = new ServerSocket(25566, 0, InetAddress.getByName(null));
			} catch(Exception e)
			{
				e.printStackTrace();
				return;
			}

			while(true)
			{
				try
				{
					Socket clientSocket = socket.accept();
					PlayerConnection clientThread = new PlayerConnection(clientSocket);
					clientThread.run();
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}

	public void addPlayer(Player player)
	{
		players.add(player);
	}

	public void removePlayer(Player player)
	{
		players.remove(player);
	}

	public static ServerCore getServer()
	{
		return server;
	}

	public static void main(String[] args)
	{
		new ServerCore();
	}
}
