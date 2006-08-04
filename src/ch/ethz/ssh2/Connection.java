
package ch.ethz.ssh2;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ch.ethz.ssh2.auth.AuthenticationManager;
import ch.ethz.ssh2.channel.ChannelManager;
import ch.ethz.ssh2.crypto.CryptoWishList;
import ch.ethz.ssh2.crypto.cipher.BlockCipherFactory;
import ch.ethz.ssh2.crypto.digest.MAC;
import ch.ethz.ssh2.transport.KexManager;
import ch.ethz.ssh2.transport.TransportManager;

/**
 * A <code>Connection</code> is used to establish an encrypted TCP/IP
 * connection to a SSH2 server.
 * <p>
 * Typically, one
 * <ul>
 * <li>Creates a <code>Connection</code> object.</li>
 * <li>Calls the <code>connect</code> method.</li>
 * <li>Calls some of the authentication methods.</li>
 * <li>Calls one or several times the <code>openSession</code>,
 * <code>createPortForwarder</code> or similar methods.</li>
 * <li>Finally, one must close the connection with the <code>close</code>
 * method.</li>
 * </ul>
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: Connection.java,v 1.16 2005/12/07 13:14:24 cplattne Exp $
 */

public class Connection
{
	public final static String identification = "Ganymed Build_209_beta3";

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @return The list of supported cipher algorithms by this implementation.
	 */
	public static synchronized String[] getAvailableCiphers()
	{
		return BlockCipherFactory.getDefaultCipherList();
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @return The list of supported MAC algorthims by this implementation.
	 */
	public static synchronized String[] getAvailableMACs()
	{
		return MAC.getMacList();
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @return The list of supported server host key algorthims by this implementation.
	 */
	public static synchronized String[] getAvailableServerHostKeyAlgorithms()
	{
		return KexManager.getDefaultServerHostkeyAlgorithmList();
	}

	private AuthenticationManager am;

	private boolean authenticated = false;
	private ChannelManager cm;

	private CryptoWishList cryptoWishList = new CryptoWishList();

	private DHGexParameters dhgexpara = new DHGexParameters();

	private String hostname;

	private int port;

	private TransportManager tm;

	private boolean tcpNoDelay = false;

	/**
	 * Same as <code>Connection(hostname, 22)</code>. 
	 * 
	 * @param hostname the hostname of the SSH2 server.
	 */
	public Connection(String hostname)
	{
		this(hostname, 22);
	}

	/**
	 * Prepares a fresh <code>Connection</code> object which can then be used
	 * to establish a connection to the specified SSH2 server.
	 * 
	 * @param hostname
	 *            the host where we later want to connect.
	 * @param port
	 *            port on the server, normally 22.
	 */
	public Connection(String hostname, int port)
	{
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * After a successful connect, one has to authenticate oneself. This method
	 * is based on DSA (it uses DSA to sign a challenge sent by the server).
	 * <p>
	 * If the authentication phase is complete, <code>true</code> will be
	 * returned. If the server does not accept the request (or if further
	 * authentication steps are needed), <code>false</code> is returned and
	 * one can retry either by using this or any other authentication method
	 * (use the <code>getRemainingAuthMethods</code> method to get a list of
	 * the remaining possible methods).
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param pem
	 *            A <code>String</code> containing the DSA private key of the
	 *            user in OpenSSH key format (PEM, you can't miss the
	 *            "-----BEGIN DSA PRIVATE KEY-----" tag). The string may contain
	 *            linefeeds.
	 * @param password
	 *            If the PEM string is 3DES encrypted ("DES-EDE3-CBC"), then you
	 *            must specify the password. Otherwise, this argument will be
	 *            ignored and can be set to <code>null</code>.
	 * 
	 * @return whether the connection is now authenticated.
	 * @throws IOException
	 * 
	 * @deprecated You should use one of the <code>authenticateWithPublicKey</code> methods,
	 *            this method is just a wrapper for it and will disappear in future
	 *            builds.
	 * 
	 */
	public synchronized boolean authenticateWithDSA(String user, String pem, String password) throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Connection is not established!");

		if (authenticated)
			throw new IllegalStateException("Connection is already authenticated!");

		if (am == null)
			am = new AuthenticationManager(tm);

		if (cm == null)
			cm = new ChannelManager(tm);

		if (user == null)
			throw new IllegalArgumentException("user argument is null");

		if (pem == null)
			throw new IllegalArgumentException("pem argument is null");

		authenticated = am.authenticatePublicKey(user, pem.toCharArray(), password);

		return authenticated;
	}

	/**
	 * A wrapper that calls <code>authenticateWithKeyboardInteractive()</code>
	 * with a <code>null</code> submethod list.
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param cb
	 *            An <code>InteractiveCallback</code> which will be used to
	 *            determine the responses to the questions asked by the server.
	 * @return whether the connection is now authenticated.
	 * @throws IOException
	 */
	public synchronized boolean authenticateWithKeyboardInteractive(String user, InteractiveCallback cb)
			throws IOException
	{
		return authenticateWithKeyboardInteractive(user, null, cb);
	}

	/**
	 * After a successful connect, one has to authenticate oneself. This method
	 * is based on "keyboard-interactive", specified in
	 * draft-ietf-secsh-auth-kbdinteract-XX. Basically, you have to define a
	 * callback object which will be feeded with challenges generated by the
	 * server. Answers are then sent back to the server. It is possible that the
	 * callback will be called several times during the invocation of this
	 * method (e.g., if the server replies to the callback's answer(s) with
	 * another challenge...)
	 * <p>
	 * If the authentication phase is complete, <code>true</code> will be
	 * returned. If the server does not accept the request (or if further
	 * authentication steps are needed), <code>false</code> is returned and
	 * one can retry either by using this or any other authentication method
	 * (use the <code>getRemainingAuthMethods</code> method to get a list of
	 * the remaining possible methods).
	 * <p>
	 * Note: some SSH servers advertise "keyboard-interactive", however, any
	 * interactive request will be denied (without having sent any challenge to
	 * the client).
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param submethods
	 *            An array of submethod names, see
	 *            draft-ietf-secsh-auth-kbdinteract-XX. May be <code>null</code>
	 *            to indicate an empty list.
	 * @param cb
	 *            An <code>InteractiveCallback</code> which will be used to
	 *            determine the responses to the questions asked by the server.
	 * 
	 * @return whether the connection is now authenticated.
	 * @throws IOException
	 */
	public synchronized boolean authenticateWithKeyboardInteractive(String user, String[] submethods,
			InteractiveCallback cb) throws IOException
	{
		if (cb == null)
			throw new IllegalArgumentException("Callback may not ne NULL!");

		if (tm == null)
			throw new IllegalStateException("Connection is not established!");

		if (authenticated)
			throw new IllegalStateException("Connection is already authenticated!");

		if (am == null)
			am = new AuthenticationManager(tm);

		if (cm == null)
			cm = new ChannelManager(tm);

		if (user == null)
			throw new IllegalArgumentException("user argument is null");

		authenticated = am.authenticateInteractive(user, submethods, cb);

		return authenticated;
	}

	/**
	 * After a successfull connect, one has to authenticate oneself. This method
	 * sends username and password to the server.
	 * <p>
	 * If the authentication phase is complete, <code>true</code> will be
	 * returned. If the server does not accept the request (or if further
	 * authentication steps are needed), <code>false</code> is returned and
	 * one can retry either by using this or any other authentication method
	 * (use the <code>getRemainingAuthMethods</code> method to get a list of
	 * the remaining possible methods).
	 * <p>
	 * Note: if this method fails, then please double-check that it is actually
	 * offered by the server (use <code>getRemainingAuthMethods</code>). Often,
	 * password authentication is disabled, but users are not aware of it.
	 * Many servers only offer "publickey" and "keyboard-interactive". However,
	 * even though "keyboard-interactive" feels like password authentication
	 * (e.g., when using putty) it is not the same mechanism.
	 * 
	 * @param user
	 * @param password
	 * @return if the connection is now authenticated.
	 * @throws IOException
	 */
	public synchronized boolean authenticateWithPassword(String user, String password) throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Connection is not established!");

		if (authenticated)
			throw new IllegalStateException("Connection is already authenticated!");

		if (am == null)
			am = new AuthenticationManager(tm);

		if (cm == null)
			cm = new ChannelManager(tm);

		if (user == null)
			throw new IllegalArgumentException("user argument is null");

		if (password == null)
			throw new IllegalArgumentException("password argument is null");

		authenticated = am.authenticatePassword(user, password);

		return authenticated;
	}

	/**
	 * After a successful connect, one has to authenticate oneself.
	 * The authentication method "publickey" works by signing a challenge
	 * sent by the server. The signature is either DSA or RSA based - it
	 * just depends on the type of private key you specify, either a DSA
	 * or RSA private key in PEM format. And yes, this is may seem to be a
	 * little confusing, the method is called "publickey" in the SSH2 protocol
	 * specification, however since we need to generate a signature, you
	 * actually have to supply a private key =).
	 * <p>
	 * The private key contained in the PEM file may also be encrypted ("Proc-Type: 4,ENCRYPTED").
	 * The library supports DES-CBC and DES-EDE3-CBC encryption, as well
	 * as the more exotic PEM encrpytions AES-128-CBC, AES-192-CBC and AES-256-CBC.
	 * <p>
	 * If the authentication phase is complete, <code>true</code> will be
	 * returned. If the server does not accept the request (or if further
	 * authentication steps are needed), <code>false</code> is returned and
	 * one can retry either by using this or any other authentication method
	 * (use the <code>getRemainingAuthMethods</code> method to get a list of
	 * the remaining possible methods).
	 * <p>
	 * NOTE PUTTY USERS: Event though your key file may start with "-----BEGIN..."
	 * it is not in the expected format. You have to convert it to the OpenSSH
	 * key format by using the "puttygen" tool (can be downloaded from the Putty
	 * website). Simply load your key and then use the "Conversions/Export OpenSSH key"
	 * functionality to get a proper PEM file.
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param pemPrivateKey
	 *            A <code>char[]</code> containing a DSA or RSA private key of the
	 *            user in OpenSSH key format (PEM, you can't miss the
	 *            "-----BEGIN DSA PRIVATE KEY-----" or "-----BEGIN RSA PRIVATE KEY-----"
	 *            tag). The char array may contain linebreaks/linefeeds.
	 * @param password
	 *            If the PEM structure is encrypted ("Proc-Type: 4,ENCRYPTED") then
	 *            you must specify a password. Otherwise, this argument will be ignored
	 *            and can be set to <code>null</code>.
	 * 
	 * @return whether the connection is now authenticated.
	 * @throws IOException
	 */
	public synchronized boolean authenticateWithPublicKey(String user, char[] pemPrivateKey, String password)
			throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Connection is not established!");

		if (authenticated)
			throw new IllegalStateException("Connection is already authenticated!");

		if (am == null)
			am = new AuthenticationManager(tm);

		if (cm == null)
			cm = new ChannelManager(tm);

		if (user == null)
			throw new IllegalArgumentException("user argument is null");

		if (pemPrivateKey == null)
			throw new IllegalArgumentException("pemPrivateKey argument is null");

		authenticated = am.authenticatePublicKey(user, pemPrivateKey, password);

		return authenticated;
	}

	/**
	 * A convenience wrapper function which reads in a private key (PEM format, either DSA or RSA)
	 * and then calls <code>authenticateWithPublicKey(String, char[], String)</code>.
	 * <p>
	 * NOTE PUTTY USERS: Event though your key file may start with "-----BEGIN..."
	 * it is not in the expected format. You have to convert it to the OpenSSH
	 * key format by using the "puttygen" tool (can be downloaded from the Putty
	 * website). Simply load your key and then use the "Conversions/Export OpenSSH key"
	 * functionality to get a proper PEM file.
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param pemFile
	 *            A <code>File</code> object pointing to a file containing a DSA or RSA
	 *            private key of the user in OpenSSH key format (PEM, you can't miss the
	 *            "-----BEGIN DSA PRIVATE KEY-----" or "-----BEGIN RSA PRIVATE KEY-----"
	 *            tag).
	 * @param password
	 *            If the PEM file is encrypted then you must specify the password.
	 *            Otherwise, this argument will be ignored and can be set to <code>null</code>.
	 * 
	 * @return whether the connection is now authenticated.
	 * @throws IOException
	 */
	public synchronized boolean authenticateWithPublicKey(String user, File pemFile, String password)
			throws IOException
	{
		if (pemFile == null)
			throw new IllegalArgumentException("pemFile argument is null");

		char[] buff = new char[256];

		CharArrayWriter cw = new CharArrayWriter();

		FileReader fr = new FileReader(pemFile);

		while (true)
		{
			int len = fr.read(buff);
			if (len < 0)
				break;
			cw.write(buff, 0, len);
		}

		fr.close();

		return authenticateWithPublicKey(user, cw.toCharArray(), password);
	}

	/**
	 * Close the connection to the SSH2 server. All assigned sessions will be
	 * closed, too. Can be called at any time. Don't forget to call this once
	 * you don't need a connection anymore - otherwise the receiver thread will
	 * run forever.
	 */
	public synchronized void close()
	{
		if (cm != null)
			cm.closeAllChannels();

		if (tm != null)
		{
			Throwable t = new Throwable("Closed due to user request.");
			tm.close(t, true);
			tm = null;
		}
		am = null;
		cm = null;
		authenticated = false;
	}

	/**
	 * Same as <code>connect(null)</code>.
	 * 
	 * @return see comments for the <code>connect(null)</code> method.
	 * @throws IOException
	 */
	public synchronized ConnectionInfo connect() throws IOException
	{
		return connect(null);
	}

	/**
	 * Connect to the SSH2 server and, as soon as the server has presented its
	 * host key, use the <code>verifyServerHostKey</code> method of the
	 * <code>verifier</code> to ask for permission to proceed. Only correctly
	 * signed host keys will be passed to the <code>verifier</code>. If
	 * <code>verifier</code> is <code>null</code>, then any correctly
	 * signed host key will be accepted - this is NOT recommended, since it
	 * makes man-in-the-middle attackes VERY easy.
	 * <p>
	 * Note: The <code>verifyServerHostKey</code> method will NOT be called
	 * from the current thread, the call is being made from a
	 * background thread (there is a background dispatcher thread for every
	 * established connection). 
	 * <p>
	 * Note 2: as long as the key exchange of the underlying connection has not
	 * been completed, this call will block.
	 * 
	 * @param verifier
	 *            An object that implements the
	 *            <code>ServerHostKeyVerifier</code> interface.
	 * @return A <code>ConnectionInfo</code> object containg the details of
	 *         the established connection.
	 * @throws IOException
	 *             If any problem occurs, e.g., the server's host key is not
	 *             correctly signed or not accepted by the <code>verifier</code>.
	 */
	public synchronized ConnectionInfo connect(ServerHostKeyVerifier verifier) throws IOException
	{
		if (tm != null)
			throw new IOException("Connection to " + hostname + " is already in connected state!");

		try
		{
			tm = new TransportManager(hostname, port);

			tm.setTcpNoDelay(tcpNoDelay);

			tm.initialize(cryptoWishList, verifier, dhgexpara);

			/* Wait until first KEX has finished */

			return tm.getConnectionInfo(1);
		}
		catch (IOException e1)
		{
			close();
			throw (IOException) new IOException("The connection to " + hostname + " could not be fully established.")
					.initCause(e1);
		}
	}

	/**
	 * Creates a new {@link LocalPortForwarder}.
	 * A <code>LocalPortForwarder</code> forwards TCP/IP connections that arrive at a local
	 * port via the secure tunnel to another host (which may or may not be
	 * identical to the remote SSH2 server).
	 * <p>
	 * This method must only be called after one has passed successfully the authentication step.
	 * There is no limit on the number of concurrent forwardings.
	 * 
	 * @param local_port the local port the LocalPortForwarder shall bind to.
	 * @param host_to_connect target address (IP or hostname)
	 * @param port_to_connect target port
	 * @return A {@link LocalPortForwarder} object.
	 * @throws IOException
	 */
	public synchronized LocalPortForwarder createLocalPortForwarder(int local_port, String host_to_connect,
			int port_to_connect) throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Cannot forward ports, you need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("Cannot forward ports, connection is not authenticated.");

		return new LocalPortForwarder(cm, local_port, host_to_connect, port_to_connect);
	}

	/**
	 * Creates a new {@link LocalStreamForwarder}.
	 * A <code>LocalStreamForwarder</code> manages an Input/Outputstream pair
	 * that is being forwarded via the secure tunnel into a TCP/IP connection to another host
	 * (which may or may not be identical to the remote SSH2 server).
	 * 
	 * @param host_to_connect
	 * @param port_to_connect
	 * @return A {@link LocalStreamForwarder} object.
	 * @throws IOException
	 */
	public synchronized LocalStreamForwarder createLocalStreamForwarder(String host_to_connect, int port_to_connect)
			throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Cannot forward, you need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("Cannot forward, connection is not authenticated.");

		return new LocalStreamForwarder(cm, host_to_connect, port_to_connect);
	}

	/**
	 * Create a very basic {@link SCPClient} that can be used to copy
	 * files from/to the SSH2 server.
	 * <p>
	 * Works only after one has passed successfully the authentication step.
	 * There is no limit on the number of concurrent SCP clients.
	 * <p>
	 * Note: This factory method will probably disappear in the future.
	 * 
	 * @return A {@link SCPClient} object.
	 * @throws IOException
	 */
	public synchronized SCPClient createSCPClient() throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Cannot create SCP client, you need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("Cannot create SCP client, connection is not authenticated.");

		return new SCPClient(this);
	}

	/**
	 * Force an asynchronous key re-exchange (the call does not block). The
	 * latest values set for MAC, Cipher and DH group exchange parameters will
	 * be used. If a key exchange is currently in progress, then this method has
	 * the only effect that the so far specified parameters will be used for the
	 * next (server driven) key exchange.
	 * <p>
	 * Note: This implementation will never start a key exchange (other than the initial one)
	 * unless you or the SSH2 server ask for it.
	 * 
	 * @throws IOException
	 *             In case of any failure behind the scenes.
	 */
	public synchronized void forceKeyExchange() throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("You need to establish a connection first.");

		tm.forceKeyExchange(cryptoWishList, dhgexpara);
	}

	/**
	 * Returns a {@link ConnectionInfo} object containing the details of
	 * the connection. Can be called as soon as the connection has been
	 * established (successfully connected).
	 * 
	 * @return A {@link ConnectionInfo} object.
	 * @throws IOException
	 *             In case of any failure behind the scenes.
	 */
	public synchronized ConnectionInfo getConnectionInfo() throws IOException
	{
		if (tm == null)
			throw new IllegalStateException(
					"Cannot get details of connection, you need to establish a connection first.");
		return tm.getConnectionInfo(1);
	}

	/**
	 * After a successful connect, one has to authenticate oneself. This method
	 * can be used to tell which authentication methods are supported by the
	 * server at a certain stage of the authentication process (for the given
	 * username).
	 * <p>
	 * Note 1: the username will only be used if no authentication step was done
	 * so far (it will be used to ask the server for a list of possible
	 * authentication methods). Otherwise, this method ignores the user name and
	 * returns a cached method list (which is based on the information contained
	 * in the last negative server response).
	 * <p>
	 * Note 2: the server may return method names that are not supported by this
	 * implementation.
	 * <p>
	 * After a successful authentication, this method must not be called
	 * anymore.
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * 
	 * @return a (possibly emtpy) array holding authentication method names.
	 * @throws IOException
	 */
	public synchronized String[] getRemainingAuthMethods(String user) throws IOException
	{
		if (user == null)
			throw new IllegalArgumentException("user argument may not be NULL!");

		if (tm == null)
			throw new IllegalStateException("Connection is not established!");

		if (authenticated)
			throw new IllegalStateException("Connection is already authenticated!");

		if (am == null)
			am = new AuthenticationManager(tm);

		if (cm == null)
			cm = new ChannelManager(tm);

		return am.getRemainingMethods(user);
	}

	/**
	 * Determines if the authentication phase is complete. Can be called at any
	 * time.
	 * 
	 * @return <code>true</code> if no further authentication steps are
	 *         needed.
	 */
	public synchronized boolean isAuthenticationComplete()
	{
		return authenticated;
	}

	/**
	 * Returns true if there was at least on failed authentication request and
	 * the last failed authentication request was marked with "partial success"
	 * by the server. This is only needed in the rare case of SSH2 server setups
	 * that cannot be satisfied with a single successful authentication request
	 * (i.e., multiple authentication steps are needed.)
	 * <p>
	 * If you are interested in the details, then have a look at
	 * draft-ietf-secsh-userauth-XX.txt.
	 * 
	 * @return if the there was a failed authentication step and the last one
	 *         was marked as a "partial success".
	 */
	public synchronized boolean isAuthenticationPartialSuccess()
	{
		if (am == null)
			return false;

		return am.getPartialSuccess();
	}

	/**
	 * Checks if a specified authentication method is available. This method is
	 * actually just a wrapper for <code>getRemainingAuthMethods</code>.
	 * 
	 * @param user
	 *            A <code>String</code> holding the username.
	 * @param method
	 *            An authentication method name (e.g., "publickey", "password",
	 *            "keyboard-interactive") as specified by the SSH-2 standard.
	 * @return if the specified authentication method is currently available.
	 * @throws IOException
	 */
	public synchronized boolean isAuthMethodAvailable(String user, String method) throws IOException
	{
		if (method == null)
			throw new IllegalArgumentException("method argument may not be NULL!");

		String methods[] = getRemainingAuthMethods(user);

		for (int i = 0; i < methods.length; i++)
		{
			if (methods[i].compareTo(method) == 0)
				return true;
		}

		return false;
	}

	/**
	 * Open a new {@link Session} on this connection. Works only after one has passed
	 * successfully the authentication step. There is no limit on the number of
	 * concurrent sessions.
	 * 
	 * @return A {@link Session} object.
	 * @throws IOException
	 */
	public synchronized Session openSession() throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("Cannot open session, you need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("Cannot open session, connection is not authenticated.");

		return new Session(cm);
	}

	/**
	 * Removes duplicates from a String array, keeps only first occurence
	 * of each element. Does not destroy order of elements; can handle nulls.
	 * Uses a very efficient O(N^2) algorithm =)
	 * 
	 * @param list a String array.
	 * @return a cleaned String array.
	 */
	private String[] removeDuplicates(String[] list)
	{
		if ((list == null) || (list.length < 2))
			return list;

		String[] list2 = new String[list.length];

		int count = 0;

		for (int i = 0; i < list.length; i++)
		{
			boolean duplicate = false;

			String element = list[i];

			for (int j = 0; j < count; j++)
			{
				if (((element == null) && (list2[j] == null)) || ((element != null) && (element.equals(list2[j]))))
				{
					duplicate = true;
					break;
				}
			}

			if (duplicate)
				continue;

			list2[count++] = list[i];
		}

		if (count == list2.length)
			return list2;

		String[] tmp = new String[count];
		System.arraycopy(list2, 0, tmp, 0, count);

		return tmp;
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @param ciphers
	 */
	public synchronized void setClient2ServerCiphers(String[] ciphers)
	{
		if ((ciphers == null) || (ciphers.length == 0))
			throw new IllegalArgumentException();
		ciphers = removeDuplicates(ciphers);
		BlockCipherFactory.checkCipherList(ciphers);
		cryptoWishList.c2s_enc_algos = ciphers;
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @param macs
	 */
	public synchronized void setClient2ServerMACs(String[] macs)
	{
		if ((macs == null) || (macs.length == 0))
			throw new IllegalArgumentException();
		macs = removeDuplicates(macs);
		MAC.checkMacList(macs);
		cryptoWishList.c2s_mac_algos = macs;
	}

	/**
	 * Sets the parameters for the diffie-hellman group exchange. Unless you
	 * know what you are doing, you will never need this. Default values are
	 * defined in the {@link DHGexParameters} class.
	 * 
	 * @param dgp {@link DHGexParameters}, non null.
	 * 
	 */
	public synchronized void setDHGexParameters(DHGexParameters dgp)
	{
		if (dgp == null)
			throw new IllegalArgumentException();

		dhgexpara = dgp;
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @param ciphers
	 */
	public synchronized void setServer2ClientCiphers(String[] ciphers)
	{
		if ((ciphers == null) || (ciphers.length == 0))
			throw new IllegalArgumentException();
		ciphers = removeDuplicates(ciphers);
		BlockCipherFactory.checkCipherList(ciphers);
		cryptoWishList.s2c_enc_algos = ciphers;
	}

	/**
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @param macs
	 */
	public synchronized void setServer2ClientMACs(String[] macs)
	{
		if ((macs == null) || (macs.length == 0))
			throw new IllegalArgumentException();

		macs = removeDuplicates(macs);
		MAC.checkMacList(macs);
		cryptoWishList.s2c_mac_algos = macs;
	}

	/**
	 * Define the set of allowed server host key algorithms to be used for
	 * the following key exchange operations.
	 * <p>
	 * Unless you know what you are doing, you will never need this.
	 * 
	 * @param algos An array of allowed server host key algorithms.
	 * 	SSH2 defines <code>ssh-dss</code> and <code>ssh-rsa</code>.
	 * 	The entries of the array must be ordered after preference, i.e., the entry at index 0 is
	 *  the most preferred one. You must specify at least one entry.
	 */
	public synchronized void setServerHostKeyAlgorithms(String[] algos)
	{
		if ((algos == null) || (algos.length == 0))
			throw new IllegalArgumentException();

		algos = removeDuplicates(algos);
		KexManager.checkServerHostkeyAlgorithmsList(algos);
		cryptoWishList.serverHostKeyAlgorithms = algos;
	}

	/**
	 * Enable/disable TCP_NODELAY (disable/enable Nagle's algorithm) on the underlying socket.
	 * <p>
	 * Can be called at any time. If the connection has not yet been established
	 * then the passed value will be stored and set after the socket has been set up.
	 * The default value that will be used is <code>false</code>.
	 * 
	 * @param enable argument for the <code>Socket.setTCPNoDelay()</code> method
	 * @throws IOException
	 */
	public synchronized void setTCPNoDelay(boolean enable) throws IOException
	{
		tcpNoDelay = enable;

		if (tm != null)
			tm.setTcpNoDelay(enable);
	}

	/**
	 * Request a remote port forwarding.
	 * If successful, then forwarded connections will be redirected to the given target address.
	 * You can cancle a requested remote port forwarding by calling the
	 * <code>cancelRemotePortForwarding()</code> method.
	 * 
	 * <p>
	 * Note 1: this method typically fails if you
	 * <ul>
	 * <li>pass a port number for which the used remote user has not enough permissions (i.e., port
	 * &lt; 1024)</li>
	 * <li>or pass a port number that is already in use on the remote server</li>
	 * <li>or if remote port forwarding is disabled on the server.</li>
	 * </ul>
	 * <p>
	 * Note 2: (from the openssh man page): By default, the listening socket on the server will be
	 * bound to the loopback interface only. This may be overriden by specifying a bind address.
	 * Specifying a remote bind address will only succeed if the server's <b>GatewayPorts</b> option
	 * is enabled (see sshd_config(5)).
	 * 
	 * @param bindAddress address to bind to on the server:
	 *                    <ul>
	 *                    <li>"" means that connections are to be accepted on all protocol families
	 *                    supported by the SSH implementation</li>
	 *                    <li>"0.0.0.0" means to listen on all IPv4 addresses</li>
	 *                    <li>"::" means to listen on all IPv6 addresses</li>
	 *                    <li>"localhost" means to listen on all protocol families supported by the SSH
	 *                    implementation on loopback addresses only, [RFC3330] and RFC3513]</li>
	 *                    <li>"127.0.0.1" and "::1" indicate listening on the loopback interfaces for
	 *                    IPv4 and IPv6 respectively</li>
	 *                    </ul>
	 * @param bindPort port number to bind on the server (must be &gt; 0)
	 * @param targetAddress the target address (IP or hostname)
	 * @param targetPort the target port
	 * @throws IOException
	 */
	public synchronized void requestRemotePortForwarding(String bindAddress, int bindPort, String targetAddress,
			int targetPort) throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("You need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("The connection is not authenticated.");

		if ((bindAddress == null) || (targetAddress == null) || (bindPort <= 0) || (targetPort <= 0))
			throw new IllegalArgumentException();

		cm.requestGlobalForward(bindAddress, bindPort, targetAddress, targetPort);
	}

	/**
	 * Cancel an earlier requested remote port forwarding. 
	 * Currently active forwardings will not be affected (e.g., disrupted).
	 * Note that further connection forwarding requests may be received until
	 * this method has returned.
	 * 
	 * @param bindPort the allocated port number on the server
	 * @throws IOException
	 */
	public synchronized void cancelRemotePortForwarding(int bindPort) throws IOException
	{
		if (tm == null)
			throw new IllegalStateException("You need to establish a connection first.");

		if (!authenticated)
			throw new IllegalStateException("The connection is not authenticated.");

		cm.requestCancelGlobalForward(bindPort);
	}
}
