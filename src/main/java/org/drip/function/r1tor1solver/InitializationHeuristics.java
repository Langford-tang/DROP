
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>InitializationHeuristics</i> implements several heuristics used to kick off the fixed point
 * bracketing/search process.
 * <br><br>
 * The following custom heuristics are implemented as part of the heuristics based kick-off:
 * <br>
 * <ul>
 * 	<li>
 * 		Custom Bracketing Control Parameters: Any of the standard bracketing control parameters can be
 * 			customized to kick-off the bracketing search.
 * 	</li>
 * 	<li>
 * 		Soft Left/Right Bracketing Hints: The left/right starting bracket edges are used as soft bracketing
 * 			initialization hints.
 * 	</li>
 * 	<li>
 * 		Soft Mid Bracketing Hint: A mid bracketing level is specified to indicate the soft bracketing
 * 			kick-off.
 * 	</li>
 * 	<li>
 * 		Hard Bracketing Floor/Ceiling: A pair of hard floor and ceiling limits are specified as a constraint
 * 			to the bracketing.
 * 	</li>
 * 	<li>
 * 		Hard Search Boundaries: A pair of hard left and right boundaries are specified to kick-off the final
 * 			fixed point search.
 * 	</li>
 * </ul>
 * <br><br>
 * These heuristics are further interpreted and developed inside the ExecutionInitializer and the
 * 	ExecutionControl implementations.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Solvers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InitializationHeuristics {

	/**
	 * Start bracket initialization from the Generic Bracket Initializer
	 */

	public static final int BRACKETING_GENERIC_BCP = 0;

	/**
	 * Start bracket initialization from Pre-specified left/right edge hints
	 */

	public static final int BRACKETING_EDGE_HINTS = 1;

	/**
	 * Start bracket initialization from Pre-specified Starting Mid Bracketing Variate
	 */

	public static final int BRACKETING_MID_HINT = 2;

	/**
	 * Restrict the bracket initialization to within the specified Floor and Ceiling
	 */

	public static final int BRACKETING_FLOOR_CEILING = 4;

	/**
	 * Start search from Pre-specified Hard Search Brackets
	 */

	public static final int SEARCH_HARD_BRACKETS = 8;

	/**
	 * Start search from Custom Bracketing Control Parameters
	 */

	public static final int BRACKETING_CUSTOM_BCP = 16;

	private int _iDeterminant = BRACKETING_GENERIC_BCP;
	private double _dblBracketFloor = java.lang.Double.NaN;
	private double _dblBracketCeiling = java.lang.Double.NaN;
	private double _dblSearchStartLeft = java.lang.Double.NaN;
	private double _dblSearchStartRight = java.lang.Double.NaN;
	private double _dblStartingBracketMid = java.lang.Double.NaN;
	private double _dblStartingBracketLeft = java.lang.Double.NaN;
	private double _dblStartingBracketRight = java.lang.Double.NaN;
	private org.drip.function.r1tor1solver.BracketingControlParams _bcpCustom = null;

	/**
	 * Construct an Initialization Heuristics Instance from the hard search edges
	 * 
	 * @param dblSearchStartLeft Search Start Left Edge
	 * @param dblSearchStartRight Search Start Right Edge
	 * 
	 * @return InitializationHeuristics instance
	 */

	public static final InitializationHeuristics FromHardSearchEdges (
		final double dblSearchStartLeft,
		final double dblSearchStartRight)
	{
		try {
			return new InitializationHeuristics (SEARCH_HARD_BRACKETS, dblSearchStartLeft,
				dblSearchStartRight, java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN,
					java.lang.Double.NaN, java.lang.Double.NaN, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Initialization Heuristics Instance from the bracketing edge soft hints
	 * 
	 * @param dblStartingBracketLeft Starting Soft Left Bracketing Edge Hint
	 * @param dblStartingBracketRight Starting Soft Right Bracketing Edge Hint
	 * 
	 * @return InitializationHeuristics instance
	 */

	public static final InitializationHeuristics FromBracketingEdgeHints (
		final double dblStartingBracketLeft,
		final double dblStartingBracketRight)
	{
		try {
			return new InitializationHeuristics (BRACKETING_EDGE_HINTS, java.lang.Double.NaN,
				java.lang.Double.NaN, dblStartingBracketLeft, dblStartingBracketRight, java.lang.Double.NaN,
					java.lang.Double.NaN, java.lang.Double.NaN, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Initialization Heuristics Instance from the bracketing mid hint
	 * 
	 * @param dblStartingBracketMid Starting Soft Right Bracketing Mid Hint
	 * 
	 * @return InitializationHeuristics instance
	 */

	public static final InitializationHeuristics FromBracketingMidHint (
		final double dblStartingBracketMid)
	{
		try {
			return new InitializationHeuristics (BRACKETING_MID_HINT, java.lang.Double.NaN,
				java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN, dblStartingBracketMid,
					java.lang.Double.NaN, java.lang.Double.NaN, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Initialization Heuristics Instance from the bracketing hard floor/ceiling
	 * 
	 * @param dblBracketFloor Starting Hard Left Bracketing Floor
	 * @param dblBracketCeiling Starting Hard Right Bracketing Ceiling
	 * 
	 * @return InitializationHeuristics instance
	 */

	public static final InitializationHeuristics FromBracketingFloorCeiling (
		final double dblBracketFloor,
		final double dblBracketCeiling)
	{
		try {
			return new InitializationHeuristics (BRACKETING_FLOOR_CEILING, java.lang.Double.NaN,
				java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN,
					dblBracketFloor, dblBracketCeiling, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Initialization Heuristics Instance from Custom Bracketing Control Parameters
	 * 
	 * @param bcpCustom Custom Bracketing Control Parameters
	 * 
	 * @return InitializationHeuristics instance
	 */

	public static final InitializationHeuristics FromBracketingCustomBCP (
		final org.drip.function.r1tor1solver.BracketingControlParams bcpCustom)
	{
		try {
			return new InitializationHeuristics (BRACKETING_CUSTOM_BCP, java.lang.Double.NaN,
				java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN, java.lang.Double.NaN,
					java.lang.Double.NaN, java.lang.Double.NaN, bcpCustom);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Initialization Heuristics Instance from the set of Heuristics Parameters
	 * 
	 * @param iDeterminant Initialization Heuristics Instance Type
	 * @param dblSearchStartLeft Hard Search Start Left Edge
	 * @param dblSearchStartRight Hard Search Start Right Edge
	 * @param dblStartingBracketLeft Starting Soft Left Bracketing Edge Hint
	 * @param dblStartingBracketRight Starting Soft Right Bracketing Edge Hint
	 * @param dblStartingBracketMid Starting Soft Right Bracketing Mid Hint
	 * @param dblBracketFloor Starting Hard Left Bracketing Floor
	 * @param dblBracketCeiling Starting Hard Right Bracketing Ceiling
	 * @param bcpCustom Custom Bracketing Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Input Determinant/parameters are unknown/invalid
	 */

	public InitializationHeuristics (
		final int iDeterminant,
		final double dblSearchStartLeft,
		final double dblSearchStartRight,
		final double dblStartingBracketLeft,
		final double dblStartingBracketRight,
		final double dblStartingBracketMid,
		final double dblBracketFloor,
		final double dblBracketCeiling,
		final org.drip.function.r1tor1solver.BracketingControlParams bcpCustom)
		throws java.lang.Exception
	{
		if (BRACKETING_EDGE_HINTS == (_iDeterminant = iDeterminant)) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStartingBracketLeft = dblStartingBracketLeft)
				|| !org.drip.numerical.common.NumberUtil.IsValid (_dblStartingBracketRight =
					dblStartingBracketRight))
				throw new java.lang.Exception
					("InitializationHeuristics constructor: Invalid BRACKETING_EDGE_HINTS params!");
		} else if (BRACKETING_MID_HINT == _iDeterminant) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStartingBracketMid = dblStartingBracketMid))
				throw new java.lang.Exception
					("InitializationHeuristics constructor: Invalid BRACKETING_MID_HINT params!");
		} else if (BRACKETING_FLOOR_CEILING == _iDeterminant) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_dblBracketFloor = dblBracketFloor) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblBracketCeiling = dblBracketCeiling))
				throw new java.lang.Exception
					("InitializationHeuristics constructor: Invalid BRACKETING_FLOOR_CEILING params!");
		} else if (SEARCH_HARD_BRACKETS == _iDeterminant) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_dblSearchStartLeft = dblSearchStartLeft) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblSearchStartRight = dblSearchStartRight))
				throw new java.lang.Exception
					("InitializationHeuristics constructor: Invalid SEARCH_HARD_BRACKETS params!");
		} else if (BRACKETING_CUSTOM_BCP == _iDeterminant) {
			if (null == (_bcpCustom = bcpCustom))
				throw new java.lang.Exception
					("InitializationHeuristics constructor: Invalid BRACKETING_CUSTOM_BCP params!");
		} else if (BRACKETING_GENERIC_BCP != _iDeterminant)
			throw new java.lang.Exception
				("InitializationHeuristics constructor: Invalid BRACKETING_GENERIC_BCP params!");
	}

	/**
	 * Retrieve the Determinant
	 * 
	 * @return The Determinant
	 */

	public int getDeterminant()
	{
		return _iDeterminant;
	}

	/**
	 * Retrieve the Hard Left Search Start
	 * 
	 * @return The Hard Left Search Start
	 */

	public double getSearchStartLeft()
	{
		return _dblSearchStartLeft;
	}

	/**
	 * Retrieve the Hard Right Search Start
	 * 
	 * @return The Hard Right Search Start
	 */

	public double getSearchStartRight()
	{
		return _dblSearchStartRight;
	}

	/**
	 * Retrieve the Soft Bracket Start Mid
	 * 
	 * @return The Soft Bracket Start Mid
	 */

	public double getStartingBracketMid()
	{
		return _dblStartingBracketMid;
	}

	/**
	 * Retrieve the Soft Bracket Start Left
	 * 
	 * @return The Soft Bracket Start Left
	 */

	public double getStartingBracketLeft()
	{
		return _dblStartingBracketLeft;
	}

	/**
	 * Retrieve the Hard Bracket Floor
	 * 
	 * @return The Hard Bracket Floor
	 */

	public double getBracketFloor()
	{
		return _dblBracketFloor;
	}

	/**
	 * Retrieve the Soft Bracket Start Right
	 * 
	 * @return The Soft Bracket Start Right
	 */

	public double getStartingBracketRight()
	{
		return _dblStartingBracketRight;
	}

	/**
	 * Retrieve the Hard Bracket Ceiling
	 * 
	 * @return The Hard Bracket Ceiling
	 */

	public double getBracketCeiling()
	{
		return _dblBracketCeiling;
	}

	/**
	 * Retrieve the Custom BCP
	 * 
	 * @return The Custom BCP
	 */

	public org.drip.function.r1tor1solver.BracketingControlParams getCustomBCP()
	{
		return _bcpCustom;
	}
}
