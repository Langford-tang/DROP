
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
 * <i>SegmentBasisFunctionGenerator</i> generates B Spline Functions of different order. It provides the
 * 	following functionality:
 * 
 *  <ul>
 * 		<li>Create a Tension Monic B Spline Basis Functions</li>
 * 		<li>Create a sequence of Monic Basis Functions</li>
 * 		<li>Create a sequence of B Splines of the specified order from the given inputs</li>
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

public class SegmentBasisFunctionGenerator {

	/**
	 * Create a Tension Monic B Spline Basis Function
	 * 
	 * @param strHatType The Primitive Hat Type
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param iDerivOrder The Derivative Order
	 * @param dblTension Tension
	 * 
	 * @return The Tension Monic B Spline Basis Function Instance
	 */

	public static final org.drip.spline.bspline.SegmentBasisFunction Monic (
		final java.lang.String strHatType,
		final java.lang.String strShapeControlType,
		final double[] adblPredictorOrdinate,
		final int iDerivOrder,
		final double dblTension)
	{
		org.drip.spline.bspline.TensionBasisHat[] aTBH =
			org.drip.spline.bspline.BasisHatPairGenerator.GenerateHatPair (strHatType, strShapeControlType,
				adblPredictorOrdinate[0], adblPredictorOrdinate[1], adblPredictorOrdinate[2], iDerivOrder,
					dblTension);

		if (null == aTBH || 2 != aTBH.length) return null;

		try {
			return new org.drip.spline.bspline.SegmentMonicBasisFunction (aTBH[0], aTBH[1]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Sequence of Monic Basis Functions
	 * 
	 * @param strHatType The Primitive Hat Type
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param iDerivOrder The Derivative Order
	 * @param dblTension Tension
	 * 
	 * @return Sequence of Tension Monic B Spline Basis Functions
	 */

	public static final org.drip.spline.bspline.SegmentBasisFunction[] MonicSequence (
		final java.lang.String strHatType,
		final java.lang.String strShapeControlType,
		final double[] adblPredictorOrdinate,
		final int iDerivOrder,
		final double dblTension)
	{
		if (null == adblPredictorOrdinate) return null;

		int iNumMonic = adblPredictorOrdinate.length - 2;
		org.drip.spline.bspline.SegmentBasisFunction[] aSBFMonic = new
			org.drip.spline.bspline.SegmentBasisFunction[iNumMonic];

		if (0 >= iNumMonic) return null;

		for (int i = 0; i < iNumMonic; ++i) {
			TensionBasisHat[] aTBH = BasisHatPairGenerator.GenerateHatPair (strHatType, strShapeControlType,
				adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1], adblPredictorOrdinate[i + 2],
					iDerivOrder, dblTension);

			if (null == aTBH || 2 != aTBH.length) return null;

			try {
				aSBFMonic[i] = new org.drip.spline.bspline.SegmentMonicBasisFunction (aTBH[0], aTBH[1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aSBFMonic;
	}

	/**
	 * Create a sequence of B Splines of the specified order from the given inputs.
	 * 
	 * @param iTargetBSplineOrder Desired B Spline Order
	 * @param aSBFPrev Array of Segment Basis Functions
	 * 
	 * @return The sequence of B Splines of the specified order.
	 */

	public static final org.drip.spline.bspline.SegmentBasisFunction[] MulticSequence (
		final int iTargetBSplineOrder,
		final org.drip.spline.bspline.SegmentBasisFunction[] aSBFPrev)
	{
		if (2 >= iTargetBSplineOrder || null == aSBFPrev) return null;

		int iNumSBF = aSBFPrev.length - 1;
		org.drip.spline.bspline.SegmentBasisFunction[] aSBF = new
			org.drip.spline.bspline.SegmentBasisFunction[iNumSBF];

		if (1 > iNumSBF) return null;

		for (int i = 0; i < iNumSBF; ++i) {
			try {
				aSBF[i] = new org.drip.spline.bspline.SegmentMulticBasisFunction (aSBFPrev[i],
					aSBFPrev[i + 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return iTargetBSplineOrder == aSBF[0].bSplineOrder() ? aSBF : MulticSequence (iTargetBSplineOrder,
			aSBF);
	}
}
