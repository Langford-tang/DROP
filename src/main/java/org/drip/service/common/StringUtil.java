
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>StringUtil</i> implements string utility functions. It exports the following functions:
 * 
 *  <ul>
 *  	<li>
 * 			Decompose + Transform string arrays into appropriate target type set/array/list, and vice versa
 *  	</li>
 *  	<li>
 * 			General-purpose String processor functions, such as GUID generator, splitter, type converter and
 * 				input checker
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StringUtil {

	/**
	 * Null serialized string
	 */

	public static final java.lang.String NULL_SER_STRING = "<<null>>";

	/**
	 * Serialization Version - ALWAYS prepend this on all derived classes
	 */

	public static final double VERSION = 2.4;

	/**
	 * Look for a match of the field in the input array
	 * 
	 * @param strFieldToMatch Field To Match
	 * @param astrMatchSet Array of fields to compare with
	 * @param bCaseMatch TRUE - Match case
	 * 
	 * @return TRUE - Match found according to the criteria specified
	 */

	public static final boolean MatchInStringArray (
		final java.lang.String strFieldToMatch,
		final java.lang.String[] astrMatchSet,
		final boolean bCaseMatch)
	{
		if (null == strFieldToMatch || strFieldToMatch.isEmpty() || null == astrMatchSet || 0 ==
			astrMatchSet.length)
			return false;

		for (java.lang.String strMatchSetEntry : astrMatchSet) {
			if (null == strMatchSetEntry || strMatchSetEntry.isEmpty()) continue;

			if (strMatchSetEntry.equals (strFieldToMatch)) return true;

			if (!bCaseMatch && strMatchSetEntry.equalsIgnoreCase (strFieldToMatch)) return true;
		}

		return false;
	}

	/**
	 * Look for a match of the field in the field set to an entry in the input array
	 * 
	 * @param astrFieldToMatch Field Array To Match
	 * @param astrMatchSet Array of fields to compare with
	 * @param bCaseMatch TRUE - Match case
	 * 
	 * @return TRUE - Match found according to the criteria specified
	 */

	public static final boolean MatchInStringArray (
		final java.lang.String[] astrFieldToMatch,
		final java.lang.String[] astrMatchSet,
		final boolean bCaseMatch)
	{
		if (null == astrFieldToMatch || 0 == astrFieldToMatch.length || null == astrMatchSet || 0 ==
			astrMatchSet.length)
			return false;

		for (java.lang.String strFieldToMatch : astrFieldToMatch) {
			if (MatchInStringArray (strFieldToMatch, astrMatchSet, bCaseMatch)) return true;
		}

		return false;
	}

	/**
	 * Format the given string parameter into an argument
	 * 
	 * @param strArg String Argument
	 * 
	 * @return Parameter from the Argument
	 */

	public static final java.lang.String MakeStringArg (
		final java.lang.String strArg)
	{
		if (null == strArg) return "null";

		if (strArg.isEmpty()) return "\"\"";

		return "\"" + strArg.trim() + "\"";
	}

	/**
	 * Check the Input String to Check for NULL - and return it
	 * 
	 * @param strIn Input String
	 * @param bEmptyToNULL TRUE if Empty String needs to be converted to NULL
	 * 
	 * @return The Processed String
	 */

	public static final java.lang.String ProcessInputForNULL (
		final java.lang.String strIn,
		final boolean bEmptyToNULL)
	{
		if (null == strIn) return null;

		if (strIn.isEmpty()) return bEmptyToNULL ? null : "";

		if ("null".equalsIgnoreCase (strIn.trim())) return null;

		if (strIn.trim().toUpperCase().startsWith ("NO")) return null;

		return strIn;
	}

	/**
	 * Parse and Split the Input Phrase into a String Array using the specified Delimiter
	 * 
	 * @param strPhrase Input Phrase
	 * @param strDelim Delimiter
	 * 
	 * @return Array of Sub-Strings
	 */

	public static final java.lang.String[] Split (
		final java.lang.String strPhrase,
		final java.lang.String strDelim)
	{
		if (null == strPhrase || strPhrase.isEmpty() || null == strDelim || strDelim.isEmpty()) return null;

		java.util.List<java.lang.Integer> lsDelimIndex = new java.util.ArrayList<java.lang.Integer>();

		int iDelimIndex = -1;

		while (-1 != (iDelimIndex = strPhrase.indexOf (strDelim, iDelimIndex + 1)))
			lsDelimIndex.add (iDelimIndex);

		int iNumField = lsDelimIndex.size();

		if (0 == iNumField) return null;

		int iBeginIndex = 0;
		java.lang.String[] astr = new java.lang.String[iNumField + 1];

		for (int i = 0; i < iNumField; ++i) {
			int iFinishIndex = lsDelimIndex.get (i);

			astr[i] = iBeginIndex >= iFinishIndex ? "" : strPhrase.substring (iBeginIndex, iFinishIndex);

			iBeginIndex = lsDelimIndex.get (i) + 1;
		}

		astr[iNumField] = strPhrase.substring (iBeginIndex);

		return astr;
	}

	/**
	 * Check if the string represents an unitary boolean
	 * 
	 * @param strUnitaryBoolean String input
	 * 
	 * @return TRUE - Unitary Boolean
	 */

	public static final boolean ParseFromUnitaryString (
		final java.lang.String strUnitaryBoolean)
	{
		if (null == strUnitaryBoolean || strUnitaryBoolean.isEmpty() || !"1".equalsIgnoreCase
			(strUnitaryBoolean.trim()))
			return false;

		return true;
	}

	/**
	 * Make an array of Integers from a string tokenizer
	 * 
	 * @param st Tokenizer containing delimited doubles
	 *  
	 * @return Double array
	 */

	public static final int[] MakeIntegerArrayFromStringTokenizer (
		final java.util.StringTokenizer st)
	{
		if (null == st) return null;

		java.util.List<java.lang.Integer> li = new java.util.ArrayList<java.lang.Integer>();

		while (st.hasMoreTokens())
			li.add (java.lang.Integer.parseInt (st.nextToken()));

		if (0 == li.size()) return null;

		int[] ai = new int[li.size()];

		int i = 0;

		for (int iValue : li)
			ai[i++] = iValue;

		return ai;
	}

	/**
	 * Make an array of double from a string tokenizer
	 * 
	 * @param stdbl Tokenizer containing delimited doubles
	 *  
	 * @return Double array
	 */

	public static final double[] MakeDoubleArrayFromStringTokenizer (
		final java.util.StringTokenizer stdbl)
	{
		if (null == stdbl) return null;

		java.util.List<java.lang.Double> lsdbl = new java.util.ArrayList<java.lang.Double>();

		while (stdbl.hasMoreTokens())
			lsdbl.add (java.lang.Double.parseDouble (stdbl.nextToken()));

		if (0 == lsdbl.size()) return null;

		double[] adbl = new double[lsdbl.size()];

		int i = 0;

		for (double dbl : lsdbl)
			adbl[i++] = dbl;

		return adbl;
	}

	/**
	 * Generate a GUID string
	 * 
	 * @return String representing the GUID
	 */

	public static final java.lang.String GUID()
	{
	    return java.util.UUID.randomUUID().toString();
	}

	/**
	 * Split the string array into pairs of key-value doubles and returns them
	 * 
	 * @param lsdblKey [out] List of Keys
	 * @param lsdblValue [out] List of Values
	 * @param strArray [in] String containing KV records
	 * @param strRecordDelim [in] Record Delimiter
	 * @param strKVDelim [in] Key-Value Delimiter
	 * 
	 * @return True if parsing is successful
	 */

	public static final boolean KeyValueListFromStringArray (
		final java.util.List<java.lang.Double> lsdblKey,
		final java.util.List<java.lang.Double> lsdblValue,
		final java.lang.String strArray,
		final java.lang.String strRecordDelim,
		final java.lang.String strKVDelim)
	{
		if (null == strArray || strArray.isEmpty() || null == strRecordDelim || strRecordDelim.isEmpty() ||
			null == strKVDelim || strKVDelim.isEmpty() || null == lsdblKey || null == lsdblValue)
			return false;

		java.lang.String[] astr = Split (strArray, strRecordDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) return false;

			java.lang.String[] astrRecord = Split (astr[i], strKVDelim);

			if (null == astrRecord || 2 != astrRecord.length || null == astrRecord[0] ||
				astrRecord[0].isEmpty() || null == astrRecord[1] || astrRecord[1].isEmpty())
				return false;

			lsdblKey.add (java.lang.Double.parseDouble (astrRecord[0]));

			lsdblValue.add (java.lang.Double.parseDouble (astrRecord[1]));
		}

		return true;
	}

	/**
	 * Create a list of integers from a delimited string
	 * 
	 * @param lsi [Output] List of Integers
	 * @param strList Delimited String input
	 * @param strDelim Delimiter
	 * 
	 * @return True if successful
	 */

	public static final boolean IntegerListFromString (
		final java.util.List<java.lang.Integer> lsi,
		final java.lang.String strList,
		final java.lang.String strDelim)
	{
		if (null == lsi || null == strList || strList.isEmpty() || null == strDelim || strDelim.isEmpty())
			return false;

		java.lang.String[] astr = Split (strList, strDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) continue;

			lsi.add (java.lang.Integer.parseInt (astr[i]));
		}

		return true;
	}

	/**
	 * Create a list of booleans from a delimited string
	 * 
	 * @param lsb [Output] List of Booleans
	 * @param strList Delimited String input
	 * @param strDelim Delimiter
	 * 
	 * @return True if successful
	 */

	public static final boolean BooleanListFromString (
		final java.util.List<java.lang.Boolean> lsb,
		final java.lang.String strList,
		final java.lang.String strDelim)
	{
		if (null == lsb || null == strList || strList.isEmpty() || null == strDelim || strDelim.isEmpty())
			return false;

		java.lang.String[] astr = Split (strList, strDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) continue;

			lsb.add (java.lang.Boolean.parseBoolean (astr[i]));
		}

		return true;
	}

	/**
	 * Convert the String Array to a Record Delimited String
	 * 
	 * @param astr Input String Array
	 * @param strRecordDelimiter The String Record Delimiter
	 * @param strNULL NULL String Indicator
	 * 
	 * @return The Record Delimited String Array
	 */

	public static final java.lang.String StringArrayToString (
		final java.lang.String[] astr,
		final java.lang.String strRecordDelimiter,
		final java.lang.String strNULL)
	{
		if (null == astr || null == strRecordDelimiter || strRecordDelimiter.isEmpty() || null == strNULL ||
			strNULL.isEmpty())
			return null;

		int iNumStr = astr.length;

		if (0 == iNumStr) return null;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (int i = 0; i < iNumStr; ++i) {
			java.lang.String str = astr[i];

			if (0 != i) sb.append (strRecordDelimiter);

			sb.append (null == str || str.isEmpty() ? strNULL : str);
		}

		return sb.toString();
	}

	/**
	 * Indicate if the Input String is Empty
	 * 
	 * @param str The Input String
	 * 
	 * @return TRUE - The Input String is Empty
	 */

	public static final boolean IsEmpty (
		final java.lang.String str)
	{
		return null == str || str.isEmpty();
	}

	/**
	 * Indicate it the pair of Strings Match each other in Value
	 * 
	 * @param strLeft The Left String
	 * @param strRight The Right String
	 * 
	 * @return TRUE - The Strings Match
	 */

	public static final boolean StringMatch (
		final java.lang.String strLeft,
		final java.lang.String strRight)
	{
		boolean bIsLeftEmpty = IsEmpty (strLeft);

		boolean bIsRightEmpty = IsEmpty (strRight);

		if (bIsLeftEmpty && bIsRightEmpty) return true;

		if ((bIsLeftEmpty && !bIsRightEmpty) || (!bIsLeftEmpty && bIsRightEmpty)) return false;

		return strLeft.equalsIgnoreCase (strRight);
	}
}
