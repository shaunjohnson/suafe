
package ch.ethz.ssh2.crypto.dh;

import ch.ethz.ssh2.DHGexParameters;
import ch.ethz.ssh2.crypto.digest.HashForSSH2Types;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * DhGroupExchange.
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: DhGroupExchange.java,v 1.4 2005/08/12 23:37:18 cplattne Exp $
 */
public class DhGroupExchange
{
	/* Given by the standard */

	BigInteger p;
	BigInteger g;

	/* Client public and private */

	BigInteger e;
	BigInteger x;

	/* Server public */

	BigInteger f;

	/* Shared secret */

	BigInteger k;

	public DhGroupExchange(BigInteger p, BigInteger g)
	{
		this.p = p;
		this.g = g;
	}

	public void init()
	{
		k = null;

		x = new BigInteger(p.bitLength() - 1, new SecureRandom());
		e = g.modPow(x, p);
	}

	/**
	 * @return Returns the e.
	 */
	public BigInteger getE()
	{
		if (e == null)
			throw new IllegalStateException("Not initialized!");

		return e;
	}

	/**
	 * @return Returns the shared secret k.
	 */
	public BigInteger getK()
	{
		if (k == null)
			throw new IllegalStateException("Shared secret not yet known, need f first!");

		return k;
	}

	/**
	 * Sets f and calculates the shared secret.
	 */
	public void setF(BigInteger f)
	{
		if (e == null)
			throw new IllegalStateException("Not initialized!");

		BigInteger zero = BigInteger.valueOf(0);

		if (zero.compareTo(f) >= 0 || p.compareTo(f) <= 0)
			throw new IllegalArgumentException("Invalid f specified!");

		this.f = f;
		this.k = f.modPow(x, p);
	}

	public byte[] calculateH(byte[] clientversion, byte[] serverversion, byte[] clientKexPayload,
			byte[] serverKexPayload, byte[] hostKey, DHGexParameters para)
	{
		HashForSSH2Types hash = new HashForSSH2Types("SHA1");

		hash.updateByteString(clientversion);
		hash.updateByteString(serverversion);
		hash.updateByteString(clientKexPayload);
		hash.updateByteString(serverKexPayload);
		hash.updateByteString(hostKey);
		hash.updateUINT32(para.getMin_group_len());
		hash.updateUINT32(para.getPref_group_len());
		hash.updateUINT32(para.getMax_group_len());
		hash.updateBigInt(p);
		hash.updateBigInt(g);
		hash.updateBigInt(e);
		hash.updateBigInt(f);
		hash.updateBigInt(k);

		return hash.getDigest();
	}
}
