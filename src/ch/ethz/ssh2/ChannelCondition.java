
package ch.ethz.ssh2;

/**
 * Contains constants that can be used to specify what conditions to wait for on
 * a SSH2 channel (e.g., represented by a {@link Session}).
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: ChannelCondition.java,v 1.1 2005/12/05 17:13:26 cplattne Exp $
 */

public abstract interface ChannelCondition
{
	/**
	 * A timeout has occurred, none of your requested conditions is fulfilled.
	 * However, other conditions may be true - therefore, NEVER use the "=="
	 * operator to test for this (or any other) condition. Always use
	 * something like ((cond & ChannelCondition.CLOSED) != 0).
	 */
	public static final int TIMEOUT = 1;

	/**
	 * The underlying SSH2 channel, however not necessarily the whole connection,
	 * has been closed. This implies <code>EOF</code>. This flag is only set after we
	 * have received a <code>close</code> message from the remote server for
	 * this channel (or if the connection is closed). Note that there may still
	 * be unread stdout or stderr data.
	 */
	public static final int CLOSED = 2;

	/**
	 * There is stdout data available that is ready to be consumed.
	 */
	public static final int STDOUT_DATA = 4;

	/**
	 * There is stderr data available that is ready to be consumed.
	 */
	public static final int STDERR_DATA = 8;

	/**
	 * EOF on has been reached, no more _new_ stdout or stderr data will arrive
	 * from the remote server. However, there may be unread stdout or stderr
	 * data, i.e, <code>STDOUT_DATA</code> or/and <code>STDERR_DATA</code>
	 * may be set at the same time!
	 */
	public static final int EOF = 16;

	/**
	 * The exit status of the remote process is available.
	 * Some servers never send the exist status, or occasionally "forget" to do so.
	 */
	public static final int EXIT_STATUS = 32;

	/**
	 * The exit signal of the remote process is available.
	 */
	public static final int EXIT_SIGNAL = 64;

}
