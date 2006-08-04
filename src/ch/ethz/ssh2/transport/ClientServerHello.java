
package ch.ethz.ssh2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ch.ethz.ssh2.Connection;

/**
 * ClientServerHello.
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: ClientServerHello.java,v 1.6 2005/12/07 10:25:48 cplattne Exp $
 */
public class ClientServerHello
{
	String server_line;
	String client_line;

	String server_versioncomment;

	public ClientServerHello(InputStream bi, OutputStream bo) throws IOException
	{
		client_line = "SSH-2.0-" + Connection.identification;

		bo.write((client_line + "\r\n").getBytes());
		bo.flush();

		byte[] serverVersion = new byte[255];

		for (int i = 0; i < 50; i++)
		{
			int pos = 0;
			boolean need10 = false;
			int len = 0;
			while (true)
			{
				int c = bi.read();
				if (c == -1)
					throw new IOException("Premature connection close");

				serverVersion[pos++] = (byte) c;

				if (c == 13)
				{
					need10 = true;
					continue;
				}

				if (c == 10)
					break;

				if (need10 == true)
					throw new IOException("Malformed server identification string, line does not end correctly.");

				len++;
				if (pos >= 255)
					throw new IOException("Server identification string too long.");
			}

			server_line = new String(serverVersion, 0, len);

			if (server_line.startsWith("SSH-"))
				break;
		}

		if (server_line.startsWith("SSH-") == false)
			throw new IOException("Malformed server identification string, does not begin with 'SSH-'");

		if (server_line.startsWith("SSH-1.99-"))
			server_versioncomment = server_line.substring(9);
		else if (server_line.startsWith("SSH-2.0-"))
			server_versioncomment = server_line.substring(8);
		else
			throw new IOException("Server uses incompatible protocol, it is not SSH2 compatible.");
	}

	/**
	 * @return Returns the client_versioncomment.
	 */
	public byte[] getClientString()
	{
		return client_line.getBytes();
	}

	/**
	 * @return Returns the server_versioncomment.
	 */
	public byte[] getServerString()
	{
		return server_line.getBytes();
	}
}
