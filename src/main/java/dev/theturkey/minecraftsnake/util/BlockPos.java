package dev.theturkey.minecraftsnake.util;

public class BlockPos
{
	private static final int NUM_X_BITS = 21;
	private static final int NUM_Z_BITS = NUM_X_BITS;
	private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
	private static final int Y_SHIFT = 0 + NUM_Z_BITS;
	private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
	private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
	private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
	private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;


	private int x;
	private int y;
	private int z;

	public BlockPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public long toLong()
	{
		return ((long) this.x & X_MASK) << X_SHIFT | ((long) this.y & Y_MASK) << Y_SHIFT | ((long) this.z & Z_MASK);
	}

	@Override
	public String toString()
	{
		return "BlockPos{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
