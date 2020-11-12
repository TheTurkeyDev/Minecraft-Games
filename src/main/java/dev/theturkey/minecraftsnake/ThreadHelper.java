package dev.theturkey.minecraftsnake;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHelper
{
	private static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

	public static void runOnMain(Runnable runnable)
	{
		queue.add(runnable);
	}

	public static void executeWaiting()
	{
		while(!queue.isEmpty())
		{
			try
			{
				queue.take().run();
			} catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
