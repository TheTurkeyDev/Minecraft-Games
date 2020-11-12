package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.ServerCore;
import dev.theturkey.minecraftsnake.ServerState;
import dev.theturkey.minecraftsnake.WebRequestBuilder;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;
import dev.theturkey.minecraftsnake.packet.client.LoginSuccessPacket;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class EncryptionResponsePacket implements IPacket
{
	private byte[] sharedSecreteBytes;
	private byte[] verifBytes;

	public EncryptionResponsePacket(byte[] sharedSecreteBytes, byte[] verifBytes)
	{
		this.sharedSecreteBytes = sharedSecreteBytes;
		this.verifBytes = verifBytes;
	}

	public static EncryptionResponsePacket read(InputData data)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, ServerCore.rPriKey);
			//String ServerID = PacketHelper.readString(data.getIn());
			//System.out.println(ServerID);
			int bytesLen = PacketHelper.readVarInt(data.getIn());
			byte[] sharedSecreteBytes = cipher.doFinal(PacketHelper.readBytes(data.getIn(), bytesLen));
			int verifBytesLen = PacketHelper.readVarInt(data.getIn());
			byte[] verifBytes = cipher.doFinal(PacketHelper.readBytes(data.getIn(), verifBytesLen));

			return new EncryptionResponsePacket(sharedSecreteBytes, verifBytes);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void handle(PlayerConnection client)
	{
		try
		{
			client.genCipher(sharedSecreteBytes);
		} catch(Exception e)
		{
			e.printStackTrace();
			return;
		}

		try
		{
			Thread.sleep(2000);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		WebRequestBuilder requestBuilder = new WebRequestBuilder("https://sessionserver.mojang.com/session/minecraft/hasJoined");
		requestBuilder.addURLProp("username", client.playerName);
		requestBuilder.addURLProp("serverId", hash(new String(sharedSecreteBytes) + new String(ServerCore.rPubKey.getEncoded())));

		try
		{
			String resp = requestBuilder.executeRequest();
			System.out.println(resp);
		} catch(Exception e)
		{
			e.printStackTrace();
			return;
		}


		//TODO: Check verif
		LoginSuccessPacket loginSuccessPacket = new LoginSuccessPacket("TurkeyDev", UUID.fromString("276130dd-8c9a-4814-8328-2578f034e422"));
		client.sendPacket(loginSuccessPacket);
		client.state = ServerState.PLAY;


	}

	@Override
	public void write(PacketBuffer buffer)
	{

	}

	@Override
	public int getID()
	{
		return 1;
	}

	@Override
	public String toString()
	{
		return "EncryptionResponsePacket{}";
	}

	// Code from https://gist.github.com/unascribed/70e830d471d6a3272e3f
	public static String hash(String str)
	{
		try
		{
			byte[] digest = digest(str, "SHA-1");
			return new BigInteger(digest).toString(16);
		} catch(NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static byte[] digest(String str, String algorithm) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
		return md.digest(strBytes);
	}
}