package mobi.mixiong.util;

/**
 * from
 * https://github.com/twitter/snowflake/blob/master/src/main/scala/com/twitter/
 * service/snowflake/IdWorker.scala
 *
 */
public class IdWorker {

	// 毫秒数＋机器编号＋毫秒内序列号
	// 从2016年1月1日起
	private static final long idepoch = 0;
	private static final long workerIdBits = 18L;
	private static final long shuffleBits = 8 * 8 - workerIdBits;
	private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	private static final long sequenceBits = 7L;
	private static final long workerIdShift = sequenceBits;
	private static final long timestampLeftShift = sequenceBits + workerIdBits;
	private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;
	private static long sequence = 0l;

	public long getTime() {
		return System.currentTimeMillis();
	}

	public static long getId(long workId) {
		long id = nextId(workId);
		return id;
	}

	private static synchronized long nextId(long workId) {
		if (workId < 0 || workId > maxWorkerId) {
			workId =  workId << shuffleBits >>> shuffleBits;
		}
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new IllegalStateException("Clock moved backwards.");
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		long id = ((timestamp - idepoch) << timestampLeftShift)//
				| (workId << workerIdShift)//
				| sequence;
		return id;
	}

	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private static long timeGen() {
		return System.currentTimeMillis();
	}

	private static char[][] idc = lut();

	private static char[] byteToChars(Byte b) {
		return idc[b + 128];
	}

	private static char[][] lut() {
		char[][] rv = new char[256][2];
		int idx = 0;

		for(byte b = -128; b >= -128 && b <= 127 && idx < 256; ++idx) {
			byte bb;
			if (b < 0) {
				bb = (byte)(b + 256);
			} else {
				bb = b;
			}

			String s = String.format("%02x", bb);
			rv[idx] = new char[]{s.charAt(0), s.charAt(1)};
			++b;
		}

		return rv;
	}

	public static long workId = IpUtils.localIpLong();

	public static String nextString() {
		long id = nextId(workId);
		StringBuilder b = new StringBuilder(16);
		b.append(byteToChars((byte)((int)(id >> 56 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 48 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 40 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 32 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 24 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 16 & 255L))));
		b.append(byteToChars((byte)((int)(id >> 8 & 255L))));
		b.append(byteToChars((byte)((int)(id & 255L))));
		return b.toString();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("IdWorker{");
		sb.append(", idepoch=").append(idepoch);
		sb.append(", lastTimestamp=").append(lastTimestamp);
		sb.append(", sequence=").append(sequence);
		sb.append('}');
		return sb.toString();
	}

}
