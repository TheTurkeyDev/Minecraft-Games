package dev.theturkey.minecraftsnake;

import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;
import dev.theturkey.minecraftsnake.packet.PacketManager;
import dev.theturkey.minecraftsnake.packet.PacketWrapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class PlayerConnection implements Runnable
{
	private Socket client;
	public DataOutputStream out;
	public DataInputStream in;
	private boolean connected = false;

	public ServerState state = ServerState.HAND_SHAKE;
	public int protocol = -1;
	private Cipher cipher;
	private boolean encrypted = false;

	public String playerName = "";
	private Player player = null;

	public PlayerConnection(Socket client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		try
		{
			out = new DataOutputStream(client.getOutputStream());
			in = new DataInputStream(client.getInputStream());
		} catch(Exception e)
		{
			e.printStackTrace();
			return;
		}

		connected = true;

		while(connected)
		{
			int length;
			int packetID;
			try
			{
				length = PacketHelper.readVarInt(in);
				packetID = PacketHelper.readVarInt(in);
			} catch(Exception e)
			{
				connected = false;
				break;
			}

			//TODO: This isn't clean
			length -= PacketHelper.writeVarInt(packetID).length;

			InputData data = new InputData(length, this);

			PacketWrapper wrapper = PacketManager.getPacket(packetID, state);
			if(wrapper != null)
			{
				IPacket packet = wrapper.createPacket.apply(data);
				System.out.println("RECV: " + packet.toString());
				packet.handle(this);
				//ThreadHelper.runOnMain(() -> packet.handle(this));
			}
			else
			{
				System.out.println("Unkown packet! " + packetID + " w/ state " + state);
			}
		}

		//TODO: Disconnect logic
		System.out.println("Disconnect");
		if(player != null)
			ThreadHelper.runOnMain(() -> ServerCore.getServer().removePlayer(player));
	}

	public void sendPacket(IPacket packet)
	{
		PacketBuffer buffer = new PacketBuffer(packet.getID());
		packet.write(buffer);
		buffer.send(this);
		System.out.println("SENT: " + packet.toString());
	}

	private byte[] getKeyBytes(final byte[] key)
	{
		byte[] keyBytes = new byte[16];
		System.arraycopy(key, 0, keyBytes, 0, Math.min(key.length, keyBytes.length));
		return keyBytes;
	}

	public void genCipher(final byte[] key) throws Exception
	{
		byte[] keyBytes = getKeyBytes(key);
		cipher = Cipher.getInstance("AES/CFB/NoPadding");
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		encrypted = true;
	}


	public Cipher getCipher()
	{
		return this.cipher;
	}

	public boolean isEncrypted()
	{
		return encrypted;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}
}
