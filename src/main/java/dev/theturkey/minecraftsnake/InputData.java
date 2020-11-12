package dev.theturkey.minecraftsnake;

import java.nio.ByteBuffer;

public class InputData
{
	private ByteBuffer in;

	public InputData(int length, PlayerConnection client)
	{
		byte[] data = new byte[length];
		try
		{
			client.in.read(data);
			if(client.isEncrypted())
			{
				this.in = ByteBuffer.wrap(client.getCipher().doFinal(data));
			}
			else
			{
				this.in = ByteBuffer.wrap(data);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ByteBuffer getIn()
	{
		return in;
	}
}
