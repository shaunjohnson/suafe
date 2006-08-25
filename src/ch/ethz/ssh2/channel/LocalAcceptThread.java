
package ch.ethz.ssh2.channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * LocalAcceptThread.
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: LocalAcceptThread.java,v 1.6 2005/12/07 10:25:48 cplattne Exp $
 */
public class LocalAcceptThread extends Thread implements IChannelWorkerThread
{
	ChannelManager cm;
	int local_port;
	String host_to_connect;
	int port_to_connect;

	ServerSocket ss;

	public LocalAcceptThread(ChannelManager cm, int local_port, String host_to_connect, int port_to_connect)
	{
		this.cm = cm;
		this.local_port = local_port;
		this.host_to_connect = host_to_connect;
		this.port_to_connect = port_to_connect;
	}

	public void run()
	{
		try
		{
			ss = new ServerSocket(local_port);

			cm.registerThread(this);

			while (true)
			{
				Socket s = ss.accept();

				Channel cn = cm.openDirectTCPIPChannel(host_to_connect, port_to_connect, s.getInetAddress()
						.getHostAddress(), s.getPort());

				StreamForwarder r2l = new StreamForwarder(cn, null, null, cn.stdoutStream, s.getOutputStream(),
						"RemoteToLocal");
				StreamForwarder l2r = new StreamForwarder(cn, r2l, s, s.getInputStream(), cn.stdinStream,
						"LocalToRemote");

				r2l.setDaemon(true);
				l2r.setDaemon(true);
				r2l.start();
				l2r.start();
			}
		}
		catch (IOException e)
		{
			try
			{
				if (ss != null)
					ss.close();
			}
			catch (IOException e1)
			{
			}
		}
	}

	public void stopWorking()
	{
		try
		{
			if (ss != null)
				ss.close();

			/* This will lead to an IOException in the ss.accept() call */
		}
		catch (IOException e)
		{
		}
	}
}
