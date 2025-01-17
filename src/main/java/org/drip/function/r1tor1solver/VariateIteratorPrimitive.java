
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
 * <i>VariateIteratorPrimitive</i> implements the various Primitive Variate Iterator routines.
 * 
 * VariateIteratorPrimitive implements the following iteration primitives:
 * <br>
 * <ul>
 * 	<li>
 * 		Bisection
 * 	</li>
 * 	<li>
 * 		False Position
 * 	</li>
 * 	<li>
 * 		Quadratic
 * 	</li>
 * 	<li>
 * 		Inverse Quadratic
 * 	</li>
 * 	<li>
 * 		Ridder
 * 	</li>
 * </ul>
 * <br>
 * It may be readily enhanced to accommodate additional primitives.
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

public class VariateIteratorPrimitive {

	/**
	 * Bisection
	 */

	public static int BISECTION = 0;

	/**
	 * False Position
	 */

	public static int FALSE_POSITION = 1;

	/**
	 * Quadratic Interpolation
	 */

	public static int QUADRATIC_INTERPOLATION = 2;

	/**
	 * Inverse Quadratic Interpolation
	 */

	public static int INVERSE_QUADRATIC_INTERPOLATION = 3;

	/**
	 * Ridder's Method
	 */

	public static int RIDDER = 4;

	/**
	 * Iterate for the next variate using bisection
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Right variate
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double Bisection (
		final double dblX1,
		final double dblX2)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2))
			throw new java.lang.Exception ("VariateIteratorPrimitive::Bisection => Invalid inputs " + dblX2);

		return 0.5 * (dblX1 + dblX2);
	}

	/**
	 * Iterate for the next variate using false position
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Right variate
	 * @param dblY1 Left OF value
	 * @param dblY2 Right OF value
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double FalsePosition (
		final double dblX1,
		final double dblX2,
		final double dblY1,
		final double dblY2)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2) || !org.drip.numerical.common.NumberUtil.IsValid (dblY1) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblY2))
			throw new java.lang.Exception ("VariateIteratorPrimitive::FalsePosition => Invalid inputs");

		return dblX1 + ((dblX1 - dblX2) / (dblY2 - dblY1) * dblY1);
	}

	/**
	 * Iterate for the next variate using quadratic interpolation
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Intermediate variate
	 * @param dblX3 Right variate
	 * @param dblY1 Left OF value
	 * @param dblY2 Intermediate OF value
	 * @param dblY3 Right OF value
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double QuadraticInterpolation (
		final double dblX1,
		final double dblX2,
		final double dblX3,
		final double dblY1,
		final double dblY2,
		final double dblY3)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2) || !org.drip.numerical.common.NumberUtil.IsValid (dblX3) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblY1) || !org.drip.numerical.common.NumberUtil.IsValid
					(dblY2) || !org.drip.numerical.common.NumberUtil.IsValid (dblY3))
			throw new java.lang.Exception
				("VariateIteratorPrimitive.QuadraticInterpolation => Invalid inputs!");

		double dblA = dblY1 / (dblX1 - dblX2) / (dblX1 - dblX3);
		dblA       += dblY2 / (dblX2 - dblX3) / (dblX2 - dblX1);
		dblA       += dblY3 / (dblX3 - dblX1) / (dblX3 - dblX2);
		double dblB = -1. * (dblX2 + dblX3) * dblY1 / (dblX1 - dblX2) / (dblX1 - dblX3);
		dblB       -=       (dblX3 + dblX1) * dblY2 / (dblX2 - dblX3) / (dblX2 - dblX1);
		dblB       -=       (dblX1 + dblX2) * dblY3 / (dblX3 - dblX1) / (dblX3 - dblX2);
		double dblC = dblX2 * dblX3 * dblY1 / (dblX1 - dblX2) / (dblX1 - dblX3);
		dblC       += dblX3 * dblX1 * dblY2 / (dblX2 - dblX3) / (dblX2 - dblX1);
		dblC       += dblX1 * dblX2 * dblY3 / (dblX3 - dblX1) / (dblX3 - dblX2);
		double dblSQRTArg = dblB * dblB - 4. * dblA * dblC;

		if (0. > dblSQRTArg)
			throw new java.lang.Exception
				("VariateIteratorPrimitive.QuadraticInterpolation => No real roots!");

		double dblSQRT = java.lang.Math.sqrt (dblSQRTArg);

		double dblRoot1 = (-1. * dblB + dblSQRT) / 2. / dblA;
		double dblRoot2 = (-1. * dblB - dblSQRT) / 2. / dblA;

		if (dblX1 > dblRoot1 || dblX3 < dblRoot1) return dblRoot2;

		if (dblX1 > dblRoot2 || dblX3 < dblRoot2) return dblRoot1;

		return java.lang.Math.abs (dblX2 - dblRoot1) < java.lang.Math.abs (dblX2 - dblRoot2) ? dblRoot1 :
			dblRoot2;
	}

	/**
	 * Iterate for the next variate using inverse quadratic interpolation
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Intermediate variate
	 * @param dblX3 Right variate
	 * @param dblY1 Left OF value
	 * @param dblY2 Intermediate OF value
	 * @param dblY3 Right OF value
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double InverseQuadraticInterpolation (
		final double dblX1,
		final double dblX2,
		final double dblX3,
		final double dblY1,
		final double dblY2,
		final double dblY3)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2) || !org.drip.numerical.common.NumberUtil.IsValid (dblX3) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblY1) || !org.drip.numerical.common.NumberUtil.IsValid
					(dblY2) || !org.drip.numerical.common.NumberUtil.IsValid (dblY3))
			throw new java.lang.Exception
				("VariateIteratorPrimitive.InverseQuadraticInterpolation => Invalid inputs!");

		double dblNextRoot = (dblY2 * dblY3 * dblX1 / (dblY1 - dblY2) / (dblY1 - dblY3));
		dblNextRoot       += (dblY3 * dblY1 * dblX2 / (dblY2 - dblY3) / (dblY2 - dblY1));
		dblNextRoot       += (dblY1 * dblY2 * dblX3 / (dblY3 - dblY1) / (dblY3 - dblY2));
		return dblNextRoot;
	}

	/**
	 * Iterate for the next variate using Ridder's method
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Intermediate variate
	 * @param dblX3 Right variate
	 * @param dblY1 Left OF value
	 * @param dblY2 Intermediate OF value
	 * @param dblY3 Right OF value
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double Ridder (
		final double dblX1,
		final double dblX2,
		final double dblX3,
		final double dblY1,
		final double dblY2,
		final double dblY3)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2) || !org.drip.numerical.common.NumberUtil.IsValid (dblX3) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblY1) || !org.drip.numerical.common.NumberUtil.IsValid
					(dblY2) || !org.drip.numerical.common.NumberUtil.IsValid (dblY3))
			throw new java.lang.Exception ("VariateIteratorPrimitive.Ridder => Invalid inputs!");

		double dblSQRTArg = dblY3 * dblY3 - dblY1 * dblY2;

		if (0. > dblSQRTArg)
			throw new java.lang.Exception ("VariateIteratorPrimitive.Ridder => No real roots!");

		return dblX3 + (dblX3 - dblX1) * dblY3 * java.lang.Math.signum (dblY1 - dblY2) / java.lang.Math.sqrt
			(dblSQRTArg);
	}

	/**
	 * Iterate for the next variate using the multi-function method
	 * 
	 * @param dblX1 Left variate
	 * @param dblX2 Intermediate variate
	 * @param dblX3 Right variate
	 * @param dblY1 Left OF value
	 * @param dblY2 Intermediate OF value
	 * @param dblY3 Right OF value
	 * @param of Objective Function
	 * @param dblOFTarget OF Target
	 * @param rfop Root Finder Output
	 * 
	 * @return The next variate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double MultiFunction (
		final double dblX1,
		final double dblX2,
		final double dblX3,
		final double dblY1,
		final double dblY2,
		final double dblY3,
		final org.drip.function.definition.R1ToR1 of,
		final double dblOFTarget,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX1) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblX2) || !org.drip.numerical.common.NumberUtil.IsValid (dblX3) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblY1) || !org.drip.numerical.common.NumberUtil.IsValid
					(dblY2) || !org.drip.numerical.common.NumberUtil.IsValid (dblY3) ||
						!org.drip.numerical.common.NumberUtil.IsValid (dblOFTarget) || null == rfop || null == of)
			throw new java.lang.Exception ("VariateIteratorPrimitive.MultiFunction => Invalid inputs!");

		double dblNextRoot = Bisection (dblX1, dblX2);

		if (!rfop.incrOFCalcs())
			throw new java.lang.Exception
				("VariateIteratorPrimitive.MultiFunction => Cannot increment rfop!");

		double dblTargetDiff = java.lang.Math.abs (of.evaluate (dblNextRoot) - dblOFTarget);

		try {
			double dblRootSecant = FalsePosition (dblX1, dblX2, dblY1, dblY2);

			if (!rfop.incrOFCalcs())
				throw new java.lang.Exception
					("VariateIteratorPrimitive.MultiFunction => Cannot increment rfop!");

			double dblTargetDiffSecant = java.lang.Math.abs (of.evaluate (dblRootSecant) - dblOFTarget);

			if (dblTargetDiffSecant < dblTargetDiff) {
				dblNextRoot = dblRootSecant;
				dblTargetDiff = dblTargetDiffSecant;
			}
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		try {
			double dblRootQuadraticInterpolation = QuadraticInterpolation (dblX1, dblX2, dblX3, dblY1, dblY2,
				dblY3);

			if (!rfop.incrOFCalcs())
				throw new java.lang.Exception
					("VariateIteratorPrimitive.MultiFunction => Cannot increment rfop!");

			double dblTargetDiffQuadraticInterpolation = java.lang.Math.abs (of.evaluate
				(dblRootQuadraticInterpolation) - dblOFTarget);

			if (dblTargetDiffQuadraticInterpolation < dblTargetDiff) {
				dblNextRoot = dblRootQuadraticInterpolation;
				dblTargetDiff = dblTargetDiffQuadraticInterpolation;
			}
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		try {
			double dblRootInverseQuadraticInterpolation = QuadraticInterpolation (dblX1, dblX2, dblX3, dblY1,
				dblY2, dblY3);

			if (!rfop.incrOFCalcs())
				throw new java.lang.Exception
					("VariateIteratorPrimitive.MultiFunction => Cannot increment rfop!");

			double dblTargetDiffInverseQuadraticInterpolation = java.lang.Math.abs (of.evaluate
				(dblRootInverseQuadraticInterpolation) - dblOFTarget);

			if (dblTargetDiffInverseQuadraticInterpolation < dblTargetDiff) {
				dblNextRoot = dblRootInverseQuadraticInterpolation;
				dblTargetDiff = dblTargetDiffInverseQuadraticInterpolation;
			}
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		try {
			double dblRootRidder = Ridder (dblX1, dblX2, dblX3, dblY1, dblY2, dblY3);

			if (!rfop.incrOFCalcs())
				throw new java.lang.Exception
					("VariateIteratorPrimitive.MultiFunction => Cannot increment rfop!");

			double dblTargetDiffRidder = java.lang.Math.abs (of.evaluate (dblRootRidder) - dblOFTarget);

			if (dblTargetDiffRidder < dblTargetDiff) {
				dblNextRoot = dblRootRidder;
				dblTargetDiff = dblTargetDiffRidder;
			}
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		return dblNextRoot;
	}
}
