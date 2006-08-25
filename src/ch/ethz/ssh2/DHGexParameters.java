
package ch.ethz.ssh2;

/**
 * A <code>DHGexParameters</code> object can be used to specify parameters for
 * the diffie-hellman group exchange. Default values are 1024, 1024 and 4096.
 * All values have to be &gt= 1024 and &lt;= 8192. min_group_len &lt;=
 * pref_group_len &lt;= max_group_len.
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: DHGexParameters.java,v 1.2 2005/08/11 17:34:19 cplattne Exp $
 */

public class DHGexParameters
{
	private int min_group_len = 1024;
	private int pref_group_len = 1024;
	private int max_group_len = 4096;

	private static final int min_allowed = 1024;
	private static final int max_allowed = 8192;

	public DHGexParameters()
	{
	}

	public DHGexParameters(int min_group_len, int pref_group_len, int max_group_len)
	{
		if ((min_group_len < min_allowed) || (min_group_len > max_allowed))
			throw new IllegalArgumentException("min_group_len out of range!");

		if ((pref_group_len < min_allowed) || (pref_group_len > max_allowed))
			throw new IllegalArgumentException("pref_group_len out of range!");

		if ((max_group_len < min_allowed) || (max_group_len > max_allowed))
			throw new IllegalArgumentException("max_group_len out of range!");

		if ((pref_group_len < min_group_len) || (pref_group_len > max_group_len))
			throw new IllegalArgumentException("pref_group_len is incompatible with min and max!");

		if (max_group_len < min_group_len)
			throw new IllegalArgumentException("max_group_len must not be smaller than min_group_len!");

		this.min_group_len = min_group_len;
		this.pref_group_len = pref_group_len;
		this.max_group_len = max_group_len;
	}

	public int getMax_group_len()
	{
		return max_group_len;
	}

	public int getMin_group_len()
	{
		return min_group_len;
	}

	public int getPref_group_len()
	{
		return pref_group_len;
	}
}
