
package org.drip.specialfunction.beta;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.specialfunction.loggamma.InfiniteSumEstimator;

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
 * <i>LogGammaEstimator</i> implements the Log Beta Function using the Log Gamma Function. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Generate the Weierstrass Infinite Product Series Version of Log Beta Estimator</li>
 * 		<li><i>LogGammaEstimator</i> Constructor</li>
 * 		<li>Retrieve the Log Gamma Function</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/beta/README.md">Estimation Techniques for Beta Function</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LogGammaEstimator implements R2ToR1
{
	private R1ToR1 _r1ToR1LogGamma = null;

	/**
	 * Generate the Weierstrass Infinite Product Series Version of Log Beta Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Weierstrass Infinite Product Series Version of Log Beta Estimator
	 */

	public static final LogGammaEstimator Weierstrass (
		final int termCount)
	{
		try {
			return new LogGammaEstimator (InfiniteSumEstimator.Weierstrass (termCount));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>LogGammaEstimator</i> Constructor
	 * 
	 * @param r1ToR1LogGamma The Log Gamma Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public LogGammaEstimator (
		final R1ToR1 r1ToR1LogGamma)
		throws Exception
	{
		if (null == (_r1ToR1LogGamma = r1ToR1LogGamma)) {
			throw new Exception ("LogGammaEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Log Gamma Function
	 * 
	 * @return The Log Gamma Function
	 */

	public R1ToR1 r1ToR1LogGamma()
	{
		return _r1ToR1LogGamma;
	}

	@Override public double evaluate (
		final double x,
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (x) || !NumberUtil.IsValid (y)) {
			throw new Exception ("LogGammaEstimator::evaluate => Invalid Inputs");
		}

		return 0. == x || 0. == y ? 0. :
			_r1ToR1LogGamma.evaluate (x) + _r1ToR1LogGamma.evaluate (y) - _r1ToR1LogGamma.evaluate (x + y);
	}
}
