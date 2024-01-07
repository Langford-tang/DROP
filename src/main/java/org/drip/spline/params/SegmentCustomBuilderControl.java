
package org.drip.spline.params;

import org.drip.spline.basis.FunctionSetBuilderParams;

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
 * <i>SegmentCustomBuilderControl</i> holds the parameters the guide the creation/behavior of the segment. It
 * 	holds the segment elastic/inelastic parameters and the named basis function set.
 *
 * <br>
 *  <ul>
 * 		<li><i>SegmentCustomBuilderControl</i> constructor</li>
 * 		<li>Retrieve the Basis Spline Name</li>
 * 		<li>Retrieve the Basis Set Parameters</li>
 * 		<li>Retrieve the Segment Inelastic Controller</li>
 * 		<li>Retrieve the Segment Shape Controller</li>
 * 		<li>Retrieve the Preceeding Manifest Sensitivity Control Parameters</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentCustomBuilderControl
{
	private String _basisSpline = "";
	private FunctionSetBuilderParams _functionSetBuilderParams = null;
	private ResponseScalingShapeControl _responseScalingShapeControl = null;
	private SegmentInelasticDesignControl _segmentInelasticDesignControl = null;
	private org.drip.spline.params.PreceedingManifestSensitivityControl _pmsc = null;

	/**
	 * <i>SegmentCustomBuilderControl</i> constructor
	 * 
	 * @param strBasisSpline Named Segment Basis Spline
	 * @param fsbp Segment Basis Set Construction Parameters
	 * @param sdic Segment Design Inelastic Parameters
	 * @param rssc Segment Shape Controller
	 * @param pmsc Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public SegmentCustomBuilderControl (
		final java.lang.String strBasisSpline,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.spline.params.SegmentInelasticDesignControl sdic,
		final org.drip.spline.params.ResponseScalingShapeControl rssc,
		final org.drip.spline.params.PreceedingManifestSensitivityControl pmsc)
		throws java.lang.Exception
	{
		if (null == (_basisSpline = strBasisSpline) || null == (_functionSetBuilderParams = fsbp) ||
			null == (_segmentInelasticDesignControl = sdic))
			throw new java.lang.Exception ("SegmentCustomBuilderControl ctr => Invalid Inputs");

		_pmsc = pmsc;
		_responseScalingShapeControl = rssc;
	}

	/**
	 * Retrieve the Basis Spline Name
	 * 
	 * @return The Basis Spline Name
	 */

	public String basisSpline()
	{
		return _basisSpline;
	}

	/**
	 * Retrieve the Basis Set Parameters
	 * 
	 * @return The Basis Set Parameters
	 */

	public FunctionSetBuilderParams basisSetParams()
	{
		return _functionSetBuilderParams;
	}

	/**
	 * Retrieve the Segment Inelastic Parameters
	 * 
	 * @return The Segment Inelastic Parameters
	 */

	public SegmentInelasticDesignControl inelasticParams()
	{
		return _segmentInelasticDesignControl;
	}

	/**
	 * Retrieve the Segment Shape Controller
	 * 
	 * @return The Segment Shape Controller
	 */

	public ResponseScalingShapeControl shapeController()
	{
		return _responseScalingShapeControl;
	}

	/**
	 * Retrieve the Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @return The Preceeding Manifest Sensitivity Control Parameters
	 */

	public org.drip.spline.params.PreceedingManifestSensitivityControl preceedingManifestSensitivityControl()
	{
		return _pmsc;
	}
}
