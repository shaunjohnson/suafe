 /*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

package com.martiansoftware.jsap;

import java.util.Map;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URL;
import java.awt.Color;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** Encapsulates the results of JSAP's parse() methods.  The most basic means of
 * obtaining a parse result from a JSAPResult is the {@link #getObject(String)}
 * method, but a number of getXXX() methods are provided
 * to make your code more readable and to avoid re-casting.
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class JSAPResult implements ExceptionMap {

    /**
     * Contains all of the results, as a Map of Lists keyed by
     * parameter ID.
     */
    private Map allResults = null;

    /**
     * Contains a map of the QualifiedSwitch IDs with Booleans
     * indicating whether they were present.
     */
    private Map qualifiedSwitches = null;
    
    /**
     * Contains all of the exceptions, as a Map of Lists, keyed by
     * parameter ID.
     * "General" exceptions that are not associated with a specific parameter
     * have a null
     * key.
     */
    private Map allExceptions = null;

    /**
     * A chronological listing of all of the exceptions thrown during parsing.
     */
    private List chronologicalErrorMessages = null;

    /**
     * A set containing the IDs of parameters supplied by the user
     * (as opposed to default values)
     */
    private Set userSpecifiedIDs = null;
    
    /**
     * If true, any values "add"ed to this JSAPResult came from the
     * user, and not from any default values.
     */
    private boolean valuesFromUser = false;
    
    /**
     *  Creates new JSAPResult
     */
    protected JSAPResult() {
        allResults = new java.util.HashMap();
        allExceptions = new java.util.HashMap();
        chronologicalErrorMessages = new java.util.LinkedList();
        qualifiedSwitches = new java.util.HashMap();
        userSpecifiedIDs = new java.util.HashSet();
    }

    /**
     * Sets internal flag indicating sources of subsequent values
     * added to this JSAPResult
     * @param valuesFromUser if true, values subsequently added to
     * this JSAPResult originated from the user.
     */
    void setValuesFromUser(boolean valuesFromUser) {
    	this.valuesFromUser = valuesFromUser;
    }
    
    /**
     * Returns true if this JSAPResult contains any results for
     * the specified id.  Note that these results may be default
     * values, and thus contains(id) might return true even when
     * the user has not herself supplied the parameter.
     * 
     * <P>This is just a means to see if there are values to retrieve.
     * 
     * @param id the ID to check
     * @return true if there are any values in this JSAPResult associated
     * with the specified ID.
     */
    public boolean contains(String id) {
    	return (allResults.containsKey(id));
    }
    
    /**
     * Returns true if this JSAPResult contains any <i>user-specified</i>
     * values for the specified id.  If this JSAPResult contains default values
     * (or no values) for the specified id, this method returns false.
     * 
     * @param id the ID to check
     * @return indication of whether the user specified a value for the specified id.
     */
    public boolean userSpecified(String id) {
    	return (userSpecifiedIDs.contains(id));
    }
    
    /**
     * Adds the specified values to any existing values already associated with
     * the specified id, if any.
     * @param id the unique ID of the parameter with which the specified values
     * are associated.
     * @param values a List containing the additional values to associate
     * with the specified ID.
     */
    protected void add(String id, List values) {
        List al = null;
        if (allResults.containsKey(id)) {
            al = (List) allResults.get(id);
        } else {
            al = new java.util.ArrayList();
            allResults.put(id, al);
        }
        al.addAll(values);
        if (valuesFromUser) {
        	userSpecifiedIDs.add(id);
        }
    }

    /**
     * Adds the specified exception to the exception map.  Exceptions are
     * keyed by the ID of the parameters with which they are associated.
     * "General"
     * exceptions not associated with a particular parameter have a null key.
     * @param id the unique ID of the parameter with which the specified values
     * are associated.
     * @param exception the exception to associate with the specified key.
     * @see com.martiansoftware.jsap.ExceptionMap#addException(String,Exception)
     */
    public void addException(String id, Exception exception) {
        List el = null;
        if (allExceptions.containsKey(id)) {
            el = (List) allExceptions.get(id);
        } else {
            el = new java.util.ArrayList();
            allExceptions.put(id, el);
        }
        el.add(exception);
        chronologicalErrorMessages.add(exception.getMessage());
    }

    /**
     * Returns the <b>first</b> object associated with the specified ID.  If
     * more than one object is
     * expected, call getObjectArray() instead.
     * @param id the unique ID of the parameter for which the first value is
     * requested
     * @return the <b>first</b> object associated with the specified ID.  If
     * more than one object is
     * expected, call getObjectArray() instead.
     */
    public Object getObject(String id) {
        Object result = null;
        List al = (List) allResults.get(id);
        if ((al != null) && (al.size() > 0)) {
            result = al.get(0);
        }
        return (result);
    }

    /**
     * Returns all values associated with the specified ID.  If no values are
     * currently associated with
     * the specified ID, an empty (zero-length) array is returned.
     * @param id the unique ID of the parameter for which the values are
     * requested
     * @return all values associated with the specified ID.  If no values are
     * currently associated with
     * the specified ID, an empty (zero-length) array is returned.
     */
    public Object[] getObjectArray(String id) {
        List al = (List) allResults.get(id);
        if (al == null) {
            al = new java.util.ArrayList(0);
        }
        return (al.toArray());
    }

    /**
     * Returns an array containing all of the values associated with the
     * specified ID. The runtime type
     * of the returned array is that of the specified array. If the list fits
     * in the specified array,
     * it is returned therein. Otherwise, a new array is allocated with the
     * runtime type of the specified
     * array and the size of this list..
     * @param id the unique ID of the parameter for which the values are
     * requested
     * @param a  the array into which the elements of the list are to be
     * stored, if it is big enough;
     * otherwise, a new array of the same runtime type is allocated for this
     * purpose.
     * @return an array containing all of the values associated with the
     * specified ID. The runtime type
     * of the returned array is that of the specified array. If the list fits
     * in the specified array,
     * it is returned therein. Otherwise, a new array is allocated with the
     * runtime type of the specified
     * array and the size of this list..
     */
    public Object[] getObjectArray(String id, Object[] a) {
        List al = (List) allResults.get(id);
        if (al == null) {
            al = new java.util.ArrayList();
        }
        return (al.toArray(a));
    }

    /**
     * Returns the first boolean value associated with the specified id.
     * @param id the id of the boolean value to retrieve
     * @return the boolean value associated with the specified id.
     * @see #getBoolean(String, boolean)
     * @see #getBooleanArray(String)
     */
    public boolean getBoolean(String id) {
    	Boolean b = (Boolean) qualifiedSwitches.get(id);
    	if (b == null) {
        	if (! contains(id)) throw new UnspecifiedParameterException(id);
    		b = (Boolean) getObject(id);
    	}
        return (b.booleanValue());
    }

    /**
     * Returns the first boolean value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Boolean, the specified default value is returned.
     * @param id the id of the boolean value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Boolean.
     * @return the first boolean value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Boolean, the specified default value is returned.
     * @see #getBoolean(String)
     * @see #getBooleanArray(String)
     */
    public boolean getBoolean(String id, boolean defaultValue) {
        Boolean b = (Boolean) qualifiedSwitches.get(id); 
        if (b == null) {
        	b = (Boolean) getObject(id);
        }
        return ((b == null) ? defaultValue : b.booleanValue());
    }

    /**
     * Returns an array of boolean values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the boolean value(s) to return.
     * @return an array containing the boolean value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getBoolean(String)
     * @see #getBoolean(String,boolean)
     */
    public boolean[] getBooleanArray(String id) {
        Boolean[] tmp = (Boolean[]) getObjectArray(id, new Boolean[0]);
        int tmplength = tmp.length;
        boolean[] result = new boolean[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].booleanValue();
        }
        return (result);
    }

    /**
     * Returns the first integer value associated with the specified id.
     * @param id the id of the integer value to retrieve
     * @return the integer value associated with the specified id.
     * @see #getInt(String,int)
     * @see #getIntArray(String)
     */
    public int getInt(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Integer) getObject(id)).intValue());
    }

    /**
     * Returns the first integer value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Integer, the specified default value is returned.
     * @param id the id of the boolean value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Integer.
     * @return the first integer value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Integer, the specified default value is returned.
     * @see #getInt(String)
     * @see #getIntArray(String)
     */
    public int getInt(String id, int defaultValue) {
        Integer result = (Integer) getObject(id);
        return ((result == null) ? defaultValue : result.intValue());
    }
    // KPB<<<<<<
    /**
     * Returns the string value associated with the specified ID for QualifiedSwitches.
     * 
     * @param id the unique ID of the parameter for which the value is requested
     * @return the value for the QualifiedSwitch associated with the specified ID
     *         or null is no such value is present or the ID does not belong
     *         to a QualifiedSwitch
     */
    public String getQualifiedSwitchValue(String id) {
    	Object result = null;
    	List al = (List) allResults.get(id);
    	if ((al != null) && (al.size() == 2)) {
    		result = al.get(1);
    	}
    	return(String)result;
    }
    // KPB<<<<<
    
    /**
     * Returns an array of integer values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the integer value(s) to return.
     * @return an array containing the integer value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getInt(String)
     * @see #getInt(String,int)
     */
    public int[] getIntArray(String id) {
        Integer[] tmp = (Integer[]) getObjectArray(id, new Integer[0]);
        int tmplength = tmp.length;
        int[] result = new int[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].intValue();
        }
        return (result);
    }

    /**
     * Returns the first long value associated with the specified id.
     * @param id the id of the long value to retrieve
     * @return the long value associated with the specified id.
     * @see #getLong(String,long)
     * @see #getLongArray(String)
     */
    public long getLong(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Long) getObject(id)).longValue());
    }

    /**
     * Returns the first long value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Long, the specified default value is returned.
     * @param id the id of the long value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Long.
     * @return the first long value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Long, the specified default value is returned.
     * @see #getLong(String)
     * @see #getLongArray(String)
     */
    public long getLong(String id, long defaultValue) {
        Long result = (Long) getObject(id);
        return ((result == null) ? defaultValue : result.longValue());
    }

    /**
     * Returns an array of long values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the long value(s) to return.
     * @return an array containing the long value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getLong(String)
     * @see #getLong(String,long)
     */
    public long[] getLongArray(String id) {
        Long[] tmp = (Long[]) getObjectArray(id, new Long[0]);
        int tmplength = tmp.length;
        long[] result = new long[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].longValue();
        }
        return (result);
    }

    //    public Iterator iterator(String id) {
    //        Object[] o = getObjectArray(id, new Object[0]);
    //    }

    /**
     * Returns the first byte value associated with the specified id.
     * @param id the id of the byte value to retrieve
     * @return the byte value associated with the specified id.
     * @see #getByte(String,byte)
     * @see #getByteArray(String)
     */
    public byte getByte(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Byte) getObject(id)).byteValue());
    }

    /**
     * Returns the first byte value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Byte, the specified default value is returned.
     * @param id the id of the byte value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Byte.
     * @return the first byte value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Byte, the specified default value is returned.
     * @see #getByte(String)
     * @see #getByteArray(String)
     */
    public byte getByte(String id, byte defaultValue) {
        Byte result = (Byte) getObject(id);
        return ((result == null) ? defaultValue : result.byteValue());
    }

    /**
     * Returns an array of byte values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the byte value(s) to return.
     * @return an array containing the byte value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getByte(String)
     * @see #getByte(String,byte)
     */
    public byte[] getByteArray(String id) {
        Byte[] tmp = (Byte[]) getObjectArray(id, new Byte[0]);
        int tmplength = tmp.length;
        byte[] result = new byte[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].byteValue();
        }
        return (result);
    }

    /**
     * Returns the first char value associated with the specified id.
     * @param id the id of the char value to retrieve
     * @return the char value associated with the specified id.
     * @see #getChar(String,char)
     * @see #getCharArray(String)
     */
    public char getChar(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Character) getObject(id)).charValue());
    }

    /**
     * Returns the first char value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Character, the specified default value is returned.
     * @param id the id of the char value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Character.
     * @return the first char value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Character, the specified default value is returned.
     * @see #getChar(String)
     * @see #getCharArray(String)
     */
    public char getChar(String id, char defaultValue) {
        Character result = (Character) getObject(id);
        return ((result == null) ? defaultValue : result.charValue());
    }

    /**
     * Returns an array of char values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the char value(s) to return.
     * @return an array containing the char value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getChar(String)
     * @see #getChar(String,char)
     */
    public char[] getCharArray(String id) {
        Character[] tmp = (Character[]) getObjectArray(id, new Character[0]);
        int tmplength = tmp.length;
        char[] result = new char[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].charValue();
        }
        return (result);
    }

    /**
     * Returns the first short value associated with the specified id.
     * @param id the id of the short value to retrieve
     * @return the short value associated with the specified id.
     * @see #getShort(String,short)
     * @see #getShortArray(String)
     */
    public short getShort(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Short) getObject(id)).shortValue());
    }

    /**
     * Returns the first short value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Short, the specified default value is returned.
     * @param id the id of the short value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Short.
     * @return the first short value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Short, the specified default value is returned.
     * @see #getShort(String)
     * @see #getShortArray(String)
     */
    public short getShort(String id, short defaultValue) {
        Short result = (Short) getObject(id);
        return ((result == null) ? defaultValue : result.shortValue());
    }

    /**
     * Returns an array of short values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the short value(s) to return.
     * @return an array containing the short value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getShort(String)
     * @see #getShort(String,short)
     */
    public short[] getShortArray(String id) {
        Short[] tmp = (Short[]) getObjectArray(id, new Short[0]);
        int tmplength = tmp.length;
        short[] result = new short[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].shortValue();
        }
        return (result);
    }

    /**
     * Returns the first double value associated with the specified id.
     * @param id the id of the double value to retrieve
     * @return the double value associated with the specified id.
     * @see #getDouble(String,double)
     * @see #getDoubleArray(String)
     */
    public double getDouble(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Double) getObject(id)).doubleValue());
    }

    /**
     * Returns the first double value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Double, the specified default value is returned.
     * @param id the id of the double value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Double.
     * @return the first double value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Double, the specified default value is returned.
     * @see #getDouble(String)
     * @see #getDoubleArray(String)
     */
    public double getDouble(String id, double defaultValue) {
        Double result = (Double) getObject(id);
        return ((result == null) ? defaultValue : result.doubleValue());
    }

    /**
     * Returns an array of double values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the double value(s) to return.
     * @return an array containing the double value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getDouble(String)
     * @see #getDouble(String,double)
     */
    public double[] getDoubleArray(String id) {
        Double[] tmp = (Double[]) getObjectArray(id, new Double[0]);
        int tmplength = tmp.length;
        double[] result = new double[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].doubleValue();
        }
        return (result);
    }

    /**
     * Returns the first float value associated with the specified id.
     * @param id the id of the float value to retrieve
     * @return the float value associated with the specified id.
     * @see #getFloat(String,float)
     * @see #getFloatArray(String)
     */
    public float getFloat(String id) {
    	if (! contains(id)) throw new UnspecifiedParameterException(id);
        return (((Float) getObject(id)).floatValue());
    }

    /**
     * Returns the first float value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Float, the specified default value is returned.
     * @param id the id of the float value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Float.
     * @return the first float value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Float, the specified default value is returned.
     * @see #getFloat(String)
     * @see #getFloatArray(String)
     */
    public float getFloat(String id, float defaultValue) {
        Float result = (Float) getObject(id);
        return ((result == null) ? defaultValue : result.floatValue());
    }

    /**
     * Returns an array of float values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the float value(s) to return.
     * @return an array containing the float value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getFloat(String)
     * @see #getFloat(String,float)
     */
    public float[] getFloatArray(String id) {
        Float[] tmp = (Float[]) getObjectArray(id, new Float[0]);
        int tmplength = tmp.length;
        float[] result = new float[tmplength];
        for (int i = 0; i < tmplength; ++i) {
            result[i] = tmp[i].floatValue();
        }
        return (result);
    }

    /**
     * Returns the first String value associated with the specified id.
     * @param id the id of the String value to retrieve
     * @return the String value associated with the specified id.
     * @see #getString(String,String)
     * @see #getStringArray(String)
     */
    public String getString(String id) {
        return ((String) getObject(id));
    }

    /**
     * Returns the first String value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.String, the specified default value is returned.
     * @param id the id of the String value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.String.
     * @return the first String value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.String, the specified default value is returned.
     * @see #getString(String)
     * @see #getStringArray(String)
     */
    public String getString(String id, String defaultValue) {
        String result = (String) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of String values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the String value(s) to return.
     * @return an array containing the String value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getString(String)
     * @see #getString(String,String)
     */
    public String[] getStringArray(String id) {
        return ((String[]) getObjectArray(id, new String[0]));
    }

    /**
     * Returns the first BigDecimal value associated with the specified id.
     * @param id the id of the BigDecimal value to retrieve
     * @return the BigDecimal value associated with the specified id.
     * @see #getBigDecimal(String, BigDecimal)
     * @see #getBigDecimalArray(String)
     * @see java.math.BigDecimal
     */
    public BigDecimal getBigDecimal(String id) {
        return ((BigDecimal) getObject(id));
    }

    /**
     * Returns the first BigDecimal value associated with the specified id.
     * If the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.math.BigDecimal, the specified default value is returned.
     * @param id the id of the BigDecimal value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.math.BigDecimal.
     * @return the first BigDecimal value associated with the specified id.
     * If the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.BigDecimal, the specified default value is returned.
     * @see #getBigDecimal(String)
     * @see #getBigDecimalArray(String)
     * @see java.math.BigDecimal
     */
    public BigDecimal getBigDecimal(String id, BigDecimal defaultValue) {
        BigDecimal result = (BigDecimal) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of BigDecimal values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the BigDecimal value(s) to return.
     * @return an array containing the BigDecimal value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getBigDecimal(String)
     * @see #getBigDecimal(String,BigDecimal)
     * @see java.math.BigDecimal
     */
    public BigDecimal[] getBigDecimalArray(String id) {
        return ((BigDecimal[]) getObjectArray(id, new BigDecimal[0]));
    }

    /**
     * Returns the first BigInteger value associated with the specified id.
     * @param id the id of the BigInteger value to retrieve
     * @return the BigInteger value associated with the specified id.
     * @see #getBigInteger(String, BigInteger)
     * @see #getBigIntegerArray(String)
     * @see java.math.BigInteger
     */
    public BigInteger getBigInteger(String id) {
        return ((BigInteger) getObject(id));
    }

    /**
     * Returns the first BigInteger value associated with the specified id.
     * If the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.math.BigInteger, the specified default value is returned.
     * @param id the id of the BigInteger value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are not
     * of type
     * java.math.BigInteger.
     * @return the first boolean value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.BigInteger, the specified default value is returned.
     * @see #getBigInteger(String)
     * @see #getBigIntegerArray(String)
     * @see java.math.BigInteger
     */
    public BigInteger getBigInteger(String id, BigInteger defaultValue) {
        BigInteger result = (BigInteger) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of BigInteger values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the BigInteger value(s) to return.
     * @return an array containing the BigInteger value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getBigInteger(String)
     * @see #getBigInteger(String,BigInteger)
     * @see java.math.BigInteger
     */
    public BigInteger[] getBigIntegerArray(String id) {
        return ((BigInteger[]) getObjectArray(id, new BigInteger[0]));
    }

    /**
     * Returns the first Class value associated with the specified id.
     * @param id the id of the Class value to retrieve
     * @return the Class value associated with the specified id.
     * @see #getClass(String, Class)
     * @see #getClassArray(String)
     * @see java.lang.Class
     */
    public Class getClass(String id) {
        return ((Class) getObject(id));
    }

    /**
     * Returns the first Class value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Class, the specified default value is returned.
     * @param id the id of the Class value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Class.
     * @return the first Class value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Class, the specified default value is returned.
     * @see #getClass(String)
     * @see #getClassArray(String)
     * @see java.lang.Class
     */
    public Class getClass(String id, Class defaultValue) {
        Class result = (Class) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of Class values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the Class value(s) to return.
     * @return an array containing the Class value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getClass(String)
     * @see #getClass(String,Class)
     * @see java.lang.Class
     */
    public Class[] getClassArray(String id) {
        return ((Class[]) getObjectArray(id, new Class[0]));
    }

    /**
     * Returns the first InetAddress value associated with the specified id.
     * @param id the id of the InetAddress value to retrieve
     * @return the InetAddress value associated with the specified id.
     * @see #getInetAddress(String, InetAddress)
     * @see #getInetAddressArray(String)
     * @see java.net.InetAddress
     */
    public InetAddress getInetAddress(String id) {
        return ((InetAddress) getObject(id));
    }

    /**
     * Returns the first InetAddress value associated with the specified id.
     * If the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.net.InetAddress, the specified default value is returned.
     * @param id the id of the InetAddress value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type java.net.InetAddress.
     * @return the first InetAddress value associated with the specified id.
     * If the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.InetAddress, the specified default value is returned.
     * @see #getInetAddress(String)
     * @see #getInetAddressArray(String)
     * @see java.net.InetAddress
     */
    public InetAddress getInetAddress(String id, InetAddress defaultValue) {
        InetAddress result = (InetAddress) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of InetAddress values associated with the specified id.
     * If the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the InetAddress value(s) to return.
     * @return an array containing the InetAddress value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getInetAddress(String)
     * @see #getInetAddress(String,InetAddress)
     * @see java.net.InetAddress
     */
    public InetAddress[] getInetAddressArray(String id) {
        return ((InetAddress[]) getObjectArray(id, new InetAddress[0]));
    }

    /**
     * Returns the first Package value associated with the specified id.
     * @param id the id of the Package value to retrieve
     * @return the Package value associated with the specified id.
     * @see #getPackage(String, Package)
     * @see #getPackageArray(String)
     * @see java.lang.Package
     */
    public Package getPackage(String id) {
        return ((Package) getObject(id));
    }

    /**
     * Returns the first Package value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Package, the specified default value is returned.
     * @param id the id of the Package value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Package.
     * @return the first Package value associated with the specified id.  If
     * the specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.lang.Package, the specified default value is returned.
     * @see #getPackage(String)
     * @see #getPackageArray(String)
     * @see java.lang.Package
     */
    public Package getPackage(String id, Package defaultValue) {
        Package result = (Package) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of Package values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the Package value(s) to return.
     * @return an array containing the Package value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getPackage(String)
     * @see #getPackage(String,Package)
     * @see java.lang.Package
     */
    public Package[] getPackageArray(String id) {
        return ((Package[]) getObjectArray(id, new Package[0]));
    }

    /**
     * Returns the first URL value associated with the specified id.
     * @param id the id of the URL value to retrieve
     * @return the URL value associated with the specified id.
     * @see #getURL(String,URL)
     * @see #getURLArray(String)
     * @see java.net.URL
     */
    public URL getURL(String id) {
        return ((URL) getObject(id));
    }

    /**
     * Returns the first URL value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.net.URL, the specified default value is returned.
     * @param id the id of the URL value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.net.URL.
     * @return the first URL value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.net.URL, the specified default value is returned.
     * @see #getURL(String)
     * @see #getURLArray(String)
     * @see java.net.URL
     */
    public URL getURL(String id, URL defaultValue) {
        URL result = (URL) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of URL values associated with the specified id.  If the
     * specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the URL value(s) to return.
     * @return an array containing the URL value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getURL(String)
     * @see #getURL(String,URL)
     * @see java.net.URL
     */
    public URL[] getURLArray(String id) {
        return ((URL[]) getObjectArray(id, new URL[0]));
    }

    /**
     * Returns the first Color value associated with the specified id.
     * @param id the id of the Color value to retrieve
     * @return the Color value associated with the specified id.
     * @see #getColor(String,Color)
     * @see #getColorArray(String)
     * @see java.awt.Color
     */
    public Color getColor(String id) {
        return ((Color) getObject(id));
    }

    /**
     * Returns the first File value associated with the specified id.
     * @param id the id of the File value to retrieve
     * @return the File value associated with the specified id.
     * @see #getFile(String,File)
     * @see #getFileArray(String)
     * @see java.io.File
     */
    public File getFile(String id) {
    	return ((File) getObject(id));
    }
    
    /**
     * Returns the first Color value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.awt.Color, the specified default value is returned.
     * @param id the id of the Color value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.lang.Color.
     * @return the first Color value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.awt.Color, the specified default value is returned.
     * @see #getColor(String)
     * @see #getColorArray(String)
     * @see java.awt.Color
     */
    public Color getColor(String id, Color defaultValue) {
        Color result = (Color) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns the first File value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.io.File, the specified default value is returned.
     * @param id the id of the File value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are
     * not of type
     * java.io.File.
     * @return the first File value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.io.File, the specified default value is returned.
     * @see #getFile(String)
     * @see #getFileArray(String)
     * @see java.io.File
     */
    public File getFile(String id, File defaultValue) {
    	File result = (File) getObject(id);
        return ((result == null) ? defaultValue : result);
    }
    
    /**
     * Returns an array of Color values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the Color value(s) to return.
     * @return an array containing the Color value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getColor(String)
     * @see #getColor(String,Color)
     * @see java.awt.Color
     */
    public Color[] getColorArray(String id) {
        return ((Color[]) getObjectArray(id, new Color[0]));
    }

    /**
     * Returns an array of File values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the File value(s) to return.
     * @return an array containing the File value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getFile(String)
     * @see #getFile(String,File)
     * @see java.io.File
     */
    public File[] getFileArray(String id) {
        return ((File[]) getObjectArray(id, new File[0]));
    }
    /**
     * Returns the first Date value associated with the specified id.
     * @param id the id of the Date value to retrieve
     * @return the Date value associated with the specified id.
     * @see #getDate(String,Date)
     * @see #getDateArray(String)
     * @see java.util.Date
     */
    public Date getDate(String id) {
        return ((Date) getObject(id));
    }

    /**
     * Returns the first Date value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.util.Date, the specified default value is returned.
     * @param id the id of the Date value to retrieve
     * @param defaultValue the value to return if the specified id does not
     * exist within this
     * JSAPResult, or if the object(s) associated with the specified id are not
     * of type
     * java.util.Date.
     * @return the first Date value associated with the specified id.  If the
     * specified id does not
     * exist within this JSAPResult, or if the object(s) associated with the
     * specified id are not of
     * type java.util.Date, the specified default value is returned.
     * @see #getDate(String)
     * @see #getDateArray(String)
     * @see java.util.Date
     */
    public Date getDate(String id, Date defaultValue) {
        Date result = (Date) getObject(id);
        return ((result == null) ? defaultValue : result);
    }

    /**
     * Returns an array of Date values associated with the specified id.  If
     * the specified id does
     * not exist within this JSAPResult, this method returns an empty array
     * (i.e., array.length==0).
     * @param id the id of the Date value(s) to return.
     * @return an array containing the Date value(s) associated with the
     * specified id, or an
     * empty array if the specified id does not exist within this JSAPResult.
     * @see #getDate(String)
     * @see #getDate(String,Date)
     * @see java.util.Date
     */
    public Date[] getDateArray(String id) {
        return ((Date[]) getObjectArray(id, new Date[0]));
    }

    /**
     * Returns the first exception associated with the specified parameter ID.
     * "General"
     * exceptions can be retrieved with a null id.  If no exceptions are
     * associated
     * with the specified parameter ID, null is returned.
     * @param id the unique ID of the parameter for which the first exception
     * is requested
     * @return the first exception associated with the specified ID, or null
     * if no
     * exceptions are associated with the specified ID.
     * @see com.martiansoftware.jsap.ExceptionMap#getException(String)
     */
    public Exception getException(String id) {
        Exception result = null;
        List el = (List) allExceptions.get(id);
        if ((el != null) && (el.size() > 0)) {
            result = (Exception) el.get(0);
        }
        return (result);
    }

    /**
     * Returns an array of ALL exceptions associated with the specified
     * parameter ID.
     * If no exceptions are associated with the specified parameter ID, an empty
     * (zero-length) array is returned.
     * @param id the unique ID of the parameter for which the exceptions are
     * requested.
     * @return an array of ALL exceptions associated with the specified
     * parameter ID,
     * or an empty (zero-length) array if no exceptions are associated with the
     * specified parameter ID.
     * @see com.martiansoftware.jsap.ExceptionMap#getExceptionArray(String)
     */
    public Exception[] getExceptionArray(String id) {
        Exception[] result = new Exception[0];
        List el = (List) allExceptions.get(id);
        if (el != null) {
            result = (Exception[]) el.toArray(result);
        }
        return (result);
    }

    /**
     * Returns an Iterator ovar ALL exceptions associated with the specified
     * parameter ID.
     * If no exceptions are associated with the specified parameter ID, an empty
     * iterator (NOT null) is returned.
     * @param id the unique ID of the parameter for which the exceptions are
     * requested.
     * @return an Iterator over ALL exceptions associated with the specified
     * parameter ID
     */
    public Iterator getExceptionIterator(String id) {
        List el = (List) allExceptions.get(id);
        if (el == null) {
            el = new java.util.ArrayList();
        }
        return (el.iterator());
    }

    /**
     * Returns an iterator over all error messages generated during parsing.
     * If no errors occured, the iterator will be empty.
     * @return an iterator over all error messages generated during parsing.
     */
    public Iterator getErrorMessageIterator() {
        return (chronologicalErrorMessages.iterator());
    }

    /**
     * Returns an Iterator over the IDs of all parameters with associated
     * exceptions (which can in turn be obtained via
     * getExceptionIterator(String)).
     * General exceptions not associated with any particular parameter are
     * associated with the null ID, so null may be returned by this Iterator.
     *
     * @return an Iterator over the IDs of all parameters with associated
     * exceptions.
     */
    public Iterator getBadParameterIDIterator() {
        return (allExceptions.keySet().iterator());
    }

    /**
     * Returns a boolean indicating whether the parse that produced this
     * JSAPResult
     * was successful.  If this method returns false, detailed information
     * regarding
     * the reasons for the failed parse can be obtained via the getException()
     * methods.
     * @return a boolean indicating whether the parse that produced this
     * JSAPResult
     * was successful.
     */
    public boolean success() {
        return (allExceptions.size() == 0);
    }

    /**
     * Informs the JSAPResult of a QualifiedSwitch (necessary
     * for getBoolean() to work properly for QualifiedSwitches)
     * @param qsid the ID of the QualifiedSwitch
     * @param present boolean indicating whether the QualifiedSwitch is present
     */
    void registerQualifiedSwitch(String qsid, boolean present) {
    	qualifiedSwitches.put(qsid, new Boolean(present));
    }
}
