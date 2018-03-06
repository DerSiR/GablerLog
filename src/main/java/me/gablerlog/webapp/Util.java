package me.gablerlog.webapp;

import java.text.SimpleDateFormat;

public class Util {
	
	public static final SimpleDateFormat GENERAL_DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String capitalizeFirstLetter(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}
	
	private static final int BILLION = 1_000_000_000;
	
	/**
	 * Generates a tracking ID for a given order number
	 * 
	 * @param orderId
	 * @return
	 */
	public static String generateTrackingID(long orderId) {
		int id1 = (int) (orderId % BILLION);
		int id2 = (int) ((orderId - id1) / BILLION % BILLION);
		int id3 = (int) (((orderId - id1) / BILLION - id2) / BILLION % BILLION);
		
		id1 = generateUniqueInt(id1);
		id2 = generateUniqueInt(id2);
		
		long mx = multiplexKeys(id1, id2);
		
		return "GL-"
				+ id3
				+ encodeBase34((int) (mx >>> 32), true)
				+ encodeBase34((int) (mx & 0x0000_0000_ffff_ffff), true);
	}
	
	/**
	 * Hashing function with full avalanche. Generates a unique
	 * integer number anywhere from {@link Integer#MIN_VALUE} to
	 * {@link Integer#MAX_VALUE} based on another integer.
	 * 
	 * <p> Requires the initial value to be greater than
	 * <tt>0</tt>
	 * 
	 * @param key
	 * @return a unique integer from {@link Integer#MIN_VALUE}
	 *         to {@link Integer#MAX_VALUE}
	 * @see <a
	 *      href="https://gist.github.com/badboy/6267743">https://gist.github.com/badboy/6267743</a>
	 */
	public static int generateUniqueInt(int key) {
		key = (key + 0x7ed55d16) + (key << 12);
		key = (key ^ 0xc761c23c) ^ (key >>> 19);
		key = (key + 0x165667b1) + (key << 5);
		key = (key + 0xd3a2646c) ^ (key << 9);
		key = (key + 0xfd7046c5) + (key << 3);
		key = (key ^ 0xb55a4f09) ^ (key >>> 16);
		return key;
	}
	
	private static final int INTERLEAVE_MASK = 0b01010101_01010101_01010101_01010101;
	
	/**
	 * Interleaves two integers by swapping alternating bits to
	 * increase entropy.
	 * 
	 * <p> The resulting integers will be returned as a single
	 * long value. The first number will be placed in the
	 * leftmost 32 bits while the second number will take the
	 * remaining 32 rightmost bits.
	 * 
	 * <p> The leftmost 32 bits are calculated like following,
	 * while the rightmost 32 bits are using the inverse mask:
	 * 
	 * <blockquote>
	 * {@code int mask = 0b01010101_01010101_01010101_01010101;}
	 * <br> {@code long mx1 = key1 & mask | key2 & ~mask; } <br>
	 * {@code long mx2 = key2 & mask | key1 & ~mask; }
	 * </blockquote>
	 * 
	 * @param key1
	 *            an integer number greater than <tt>0</tt>
	 * @param key2
	 *            an integer number greater than <tt>0</tt>
	 * @return a single long value containing the multiplexed
	 *         numbers
	 */
	public static long multiplexKeys(int key1, int key2) {
		long mx1 = key1 & INTERLEAVE_MASK | key2 & ~INTERLEAVE_MASK;
		long mx2 = key2 & INTERLEAVE_MASK | key1 & ~INTERLEAVE_MASK;
		
		mx1 <<= 32;
		mx1 >>>= 32;
		mx2 <<= 32;
		
		return mx1 | mx2;
	}
	
	private static final int BASE34_UNSIGNED_INT_MAX_LENGTH = 7;
	
	private static final char BASE34_PADDING = 'Y';
	private static final char BASE34_SIGN	 = 'Z';
	
	/**
	 * Encodes an integer into a base 34 string, containing only
	 * numbers and characters from <tt>A-X</tt>.
	 * 
	 * <p> When the parameter <tt>pad</tt> is set, the resulting
	 * string will be alternated like the following:
	 * 
	 * <dl> <dt>The integer is negative</dt> <dd>The sign will
	 * be replaced by a <tt>Z</tt></dd> <dt>The integer is
	 * positive</dt> <dd>The sign will be replaced by a
	 * <tt>Y</tt></dd> <dt>The resulting string is shorter than
	 * the max length it could occupy, based on the minimum and
	 * maximum values for an integer</dt> <dd>The string will be
	 * padded with <tt>Y</tt> until the max length is
	 * reached</dd> </dl>
	 * 
	 * @param key
	 *            any integer number
	 * @param pad
	 *            enable string padding
	 * @return the base 34 encoded string for a given integer
	 */
	public static String encodeBase34(int key, boolean pad) {
		String base34;
		
		if (pad) {
			StringBuilder sb = new StringBuilder(
					Integer.toString(Math.abs(key), 34));
			
			for (int i = sb.length(); i < BASE34_UNSIGNED_INT_MAX_LENGTH; i++) {
				sb.insert(0, BASE34_PADDING);
			}
			sb.insert(0, (key < 0) ? BASE34_SIGN : BASE34_PADDING);
			
			base34 = sb.toString();
			
		} else {
			base34 = Integer.toString(key, 34);
		}
		
		return base34.toUpperCase();
	}
}
