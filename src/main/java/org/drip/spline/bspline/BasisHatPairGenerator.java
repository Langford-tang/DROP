
package org.drip.spline.bspline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>BasisHatPairGenerator</i> implements the generation functionality behind the hat basis function pair.
 * 	It provides the following functionality:
 * 
 *  <ul>
 * 		<li>Generate the array of the Hyperbolic Phy and Psy Hat Function Pair</li>
 * 		<li>Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts</li>
 * 		<li>Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts</li>
 * 		<li>Generate the array of the Hat Function Pair From their Raw Counterparts</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/README.md">de Boor Rational/Exponential/Tension B-Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisHatPairGenerator
{

	/**
	 * Raw Tension Hyperbolic B Spline Basis Hat Phy and Psy
	 */

	public static final String RAW_TENSION_HYPERBOLIC = "RAW_TENSION_HYPERBOLIC";

	/**
	 * Processed Tension Hyperbolic B Spline Basis Hat Phy and Psy
	 */

	public static final String PROCESSED_TENSION_HYPERBOLIC = "PROCESSED_TENSION_HYPERBOLIC";

	/**
	 * Processed Cubic Rational B Spline Basis Hat Phy and Psy
	 */

	public static final String PROCESSED_CUBIC_RATIONAL = "PROCESSED_CUBIC_RATIONAL";

	/**
	 * Generate the array of the Hyperbolic Phy and Psy Hat Function Pair
	 * 
	 * @param leadingPredictorOrdinate The Leading Predictor Ordinate
	 * @param followingPredictorOrdinate The Following Predictor Ordinate
	 * @param trailingPredictorOrdinate The Trailing Predictor Ordinate
	 * @param tension Tension
	 * 
	 * @return The array of Hyperbolic Phy and Psy Hat Function Pair
	 */

	public static final TensionBasisHat[] HyperbolicTensionHatPair (
		final double leadingPredictorOrdinate,
		final double followingPredictorOrdinate,
		final double trailingPredictorOrdinate,
		final double tension)
	{
		try {
			return new TensionBasisHat[] {
				new ExponentialTensionLeftHat (
					leadingPredictorOrdinate,
					followingPredictorOrdinate,
					tension
				),
				new ExponentialTensionRightHat (
					followingPredictorOrdinate,
					trailingPredictorOrdinate,
					tension
				)
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts
	 * 
	 * @param leadingPredictorOrdinate The Leading Predictor Ordinate
	 * @param followingPredictorOrdinate The Following Predictor Ordinate
	 * @param trailingPredictorOrdinate The Trailing Predictor Ordinate
	 * @param derivativeOrder The Derivative Order
	 * @param tension Tension
	 * 
	 * @return The array of Hyperbolic Phy and Psy Hat Function Pair
	 */

	public static final TensionBasisHat[] ProcessedHyperbolicTensionHatPair (
		final double leadingPredictorOrdinate,
		final double followingPredictorOrdinate,
		final double trailingPredictorOrdinate,
		final int derivativeOrder,
		final double tension)
	{
		try {
			return new TensionBasisHat[] {
				new TensionProcessedBasisHat (
					new ExponentialTensionLeftRaw (
						leadingPredictorOrdinate,
						followingPredictorOrdinate,
						tension
					),
					derivativeOrder
				),
				new TensionProcessedBasisHat (
					new ExponentialTensionRightRaw (
						followingPredictorOrdinate,
						trailingPredictorOrdinate,
						tension
					),
					derivativeOrder
				)
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts
	 * 
	 * @param shapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param leadingPredictorOrdinate The Leading Predictor Ordinate
	 * @param followingPredictorOrdinate The Following Predictor Ordinate
	 * @param trailingPredictorOrdinate The Trailing Predictor Ordinate
	 * @param derivativeOrder The Derivative Order
	 * @param tension Tension
	 * 
	 * @return The array of Cubic Rational Phy and Psy Hat Function Pair
	 */

	public static final TensionBasisHat[] ProcessedCubicRationalHatPair (
		final String shapeControlType,
		final double leadingPredictorOrdinate,
		final double followingPredictorOrdinate,
		final double trailingPredictorOrdinate,
		final int derivativeOrder,
		final double tension)
	{
		try {
			return new TensionBasisHat[] {
				new TensionProcessedBasisHat (
					new CubicRationalLeftRaw (
						leadingPredictorOrdinate,
						followingPredictorOrdinate,
						shapeControlType,
						tension
					),
					derivativeOrder
				),
				new TensionProcessedBasisHat (
					new CubicRationalRightRaw (
						followingPredictorOrdinate,
						trailingPredictorOrdinate,
						shapeControlType,
						tension
					),
					derivativeOrder
				)
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Hat Function Pair From their Raw Counterparts
	 * 
	 * @param hatType The Primitive Hat Type
	 * @param shapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param leadingPredictorOrdinate The Leading Predictor Ordinate
	 * @param followingPredictorOrdinate The Following Predictor Ordinate
	 * @param trailingPredictorOrdinate The Trailing Predictor Ordinate
	 * @param derivativeOrder The Derivative Order
	 * @param tension Tension
	 * 
	 * @return The array of Cubic Rational Phy and Psy Hat Function Pair
	 */

	public static final TensionBasisHat[] GenerateHatPair (
		final String hatType,
		final String shapeControlType,
		final double leadingPredictorOrdinate,
		final double followingPredictorOrdinate,
		final double trailingPredictorOrdinate,
		final int derivativeOrder,
		final double tension)
	{
		if (null == hatType || (!RAW_TENSION_HYPERBOLIC.equalsIgnoreCase (hatType) &&
			!PROCESSED_TENSION_HYPERBOLIC.equalsIgnoreCase (hatType) &&
			!PROCESSED_CUBIC_RATIONAL.equalsIgnoreCase (hatType))) {
			return null;
		}

		if (BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC.equalsIgnoreCase (hatType)) {
			return HyperbolicTensionHatPair (
				leadingPredictorOrdinate,
				followingPredictorOrdinate,
				trailingPredictorOrdinate,
				tension
			);
		}

		if (BasisHatPairGenerator.PROCESSED_TENSION_HYPERBOLIC.equalsIgnoreCase (hatType)) {
			return ProcessedHyperbolicTensionHatPair (
				leadingPredictorOrdinate,
				followingPredictorOrdinate,
				trailingPredictorOrdinate,
				derivativeOrder,
				tension
			);
		}

		return ProcessedCubicRationalHatPair (
			shapeControlType,
			leadingPredictorOrdinate,
			followingPredictorOrdinate,
			trailingPredictorOrdinate,
			derivativeOrder,
			tension
		);
	}
}
