
package org.drip.state.estimator;

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
 * <i>CurveStretch</i> expands the regular Multi-Segment Stretch to aid the calibration of Boot-strapped
 * Instruments. In particular, CurveStretch implements the following functions that are used at different
 * stages of curve construction sequence:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Mark the Range of the "built" Segments
 *  	</li>
 *  	<li>
 * 			Clear the built range mark to signal the start of a fresh calibration run
 *  	</li>
 *  	<li>
 * 			Indicate if the specified Predictor Ordinate is inside the "Built" Range
 *  	</li>
 *  	<li>
 * 			Retrieve the MergeSubStretchManager
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/README.md">Multi-Pass Customized Stretch Curve</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurveStretch extends org.drip.spline.stretch.CalibratableMultiSegmentSequence {
	private double _dblBuiltPredictorOrdinateRight = java.lang.Double.NaN;
	private org.drip.state.representation.MergeSubStretchManager _msm = null;

	/**
	 * CurveStretch constructor - Construct a sequence of Basis Spline Segments
	 * 
	 * @param strName Name of the Stretch
	 * @param aCS Array of Segments
	 * @param aSCBC Array of Segment Builder Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CurveStretch (
		final java.lang.String strName,
		final org.drip.spline.segment.LatentStateResponseModel[] aCS,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC)
		throws java.lang.Exception
	{
		super (strName, aCS, aSCBC);

		_dblBuiltPredictorOrdinateRight = getLeftPredictorOrdinateEdge();
	}

	/**
	 * Mark the Range of the "built" Segments, and set the set of Merge Latent States
	 * 
	 * @param iSegment The Current Segment Range Built
	 * @param setLSL Set of the merging Latent State Labels
	 * 
	 * @return TRUE - Range successfully marked as "built"
	 */

	public boolean markSegmentBuilt (
		final int iSegment,
		final java.util.Set<org.drip.state.identifier.LatentStateLabel> setLSL)
	{
		org.drip.spline.segment.LatentStateResponseModel[] aCS = segments();

		if (iSegment >= aCS.length) return false;

		_dblBuiltPredictorOrdinateRight = aCS[iSegment].right();

		if (null == setLSL || 0 == setLSL.size()) return true;

		if (null == _msm) _msm = new org.drip.state.representation.MergeSubStretchManager();

		for (org.drip.state.identifier.LatentStateLabel lsl : setLSL) {
			try {
				if (!_msm.addMergeStretch (new org.drip.state.representation.LatentStateMergeSubStretch
					(aCS[iSegment].left(), aCS[iSegment].right(), lsl)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * Clear the built range mark to signal the start of a fresh calibration run
	 * 
	 * @return TRUE - Built Range successfully cleared
	 */

	public boolean clearBuiltRange()
	{
		_dblBuiltPredictorOrdinateRight = getLeftPredictorOrdinateEdge();

		_msm = null;
		return true;
	}

	/**
	 * Indicate if the specified Predictor Ordinate is inside the "Built" Range
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return TRUE - The specified Predictor Ordinate is inside the "Built" Range
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public boolean inBuiltRange (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("CurveStretch.inBuiltRange => Invalid Inputs");

		return dblPredictorOrdinate >= getLeftPredictorOrdinateEdge() && dblPredictorOrdinate <=
			_dblBuiltPredictorOrdinateRight;
	}

	@Override public org.drip.state.representation.MergeSubStretchManager msm()
	{
		return _msm;
	}
}
