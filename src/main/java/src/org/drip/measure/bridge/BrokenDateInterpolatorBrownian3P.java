
package org.drip.measure.bridge;

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
 * <i>BrokenDateInterpolatorBrownian3P</i> Interpolates the Broken Dates using Three Stochastic Value Nodes
 * using the Three Point Brownian Bridge Scheme.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/README.md">Broken Date Brownian Bridge Interpolator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateInterpolatorBrownian3P implements org.drip.measure.bridge.BrokenDateInterpolator {
	private double _dblT1 = java.lang.Double.NaN;
	private double _dblT2 = java.lang.Double.NaN;
	private double _dblT3 = java.lang.Double.NaN;
	private double _dblV1 = java.lang.Double.NaN;
	private double _dblV2 = java.lang.Double.NaN;
	private double _dblV3 = java.lang.Double.NaN;
	private double _dblBrownianBridgeFactor = java.lang.Double.NaN;

	/**
	 * BrokenDateInterpolatorBrownian3P Constructor
	 * 
	 * @param dblT1 T1
	 * @param dblT2 T2
	 * @param dblT3 T3
	 * @param dblV1 V1
	 * @param dblV2 V2
	 * @param dblV3 V3
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BrokenDateInterpolatorBrownian3P (
		final double dblT1,
		final double dblT2,
		final double dblT3,
		final double dblV1,
		final double dblV2,
		final double dblV3)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblT1 = dblT1) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblT2 = dblT2) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblT3 = dblT3) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblV1 = dblV1) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblV2 = dblV2) ||
							!org.drip.numerical.common.NumberUtil.IsValid (_dblV3 = dblV3) || _dblT1 >= _dblT2 ||
								_dblT2 >= _dblT3)
			throw new java.lang.Exception ("BrokenDateInterpolatorBrownian3P Constructor => Invalid Inputs");

		double dblT3MinusT1 = _dblT3 - _dblT1;
		double dblT3MinusT2 = _dblT3 - _dblT2;
		double dblT2MinusT1 = _dblT2 - _dblT1;

		_dblBrownianBridgeFactor = java.lang.Math.sqrt (dblT3MinusT1 / (dblT3MinusT2 * dblT2MinusT1)) *
			(_dblV2 - (dblT3MinusT2 * _dblV1 / dblT3MinusT1) - (dblT2MinusT1 * _dblV3 / dblT3MinusT1));
	}

	/**
	 * Retrieve T1
	 * 
	 * @return T1
	 */

	public double t1()
	{
		return _dblT1;
	}

	/**
	 * Retrieve T2
	 * 
	 * @return T2
	 */

	public double t2()
	{
		return _dblT2;
	}

	/**
	 * Retrieve T3
	 * 
	 * @return T3
	 */

	public double t3()
	{
		return _dblT3;
	}

	/**
	 * Retrieve V1
	 * 
	 * @return V1
	 */

	public double v1()
	{
		return _dblV1;
	}

	/**
	 * Retrieve V2
	 * 
	 * @return V2
	 */

	public double v2()
	{
		return _dblV2;
	}

	/**
	 * Retrieve V3
	 * 
	 * @return V3
	 */

	public double v3()
	{
		return _dblV3;
	}

	/**
	 * Retrieve the Brownian Bridge Factor
	 * 
	 * @return The Brownian Bridge Factor
	 */

	public double brownianBridgeFactor()
	{
		return _dblBrownianBridgeFactor;
	}

	@Override public double interpolate (
		final double dblT)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblT) || dblT < _dblT1 || dblT > _dblT3)
			throw new java.lang.Exception
				("BrokenDateInterpolatorBrownian3P::interpolate => Invalid Inputs");

		double dblT3MinusT1 = _dblT3 - _dblT1;
		double dblT3MinusT = _dblT3 - dblT;
		double dblTMinusT1 = dblT - _dblT1;

		return (dblT3MinusT * _dblV1 / dblT3MinusT1) + (dblTMinusT1 * _dblV3 / dblT3MinusT1) +
			_dblBrownianBridgeFactor * java.lang.Math.sqrt (dblT3MinusT * dblTMinusT1 / dblT3MinusT1);
	}
}
