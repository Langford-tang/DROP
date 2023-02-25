
package org.drip.specialfunction.incompletegamma;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>UpperSFixedSeriesTerm</i> implements a Single Term in the Upper Incomplete Gamma Expansion Series for a
 * Fixed s, starting from s = 0 if Recurrence is used. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of
 * 				Definite Integrals involving Elementary Functions via Differentiation of Special Functions
 * 				<i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over
 *				e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞
 *				https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Incomplete Gamma and Related Functions
 * 				https://dlmf.nist.gov/8
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Incomplete Gamma Function
 * 				https://en.wikipedia.org/wiki/Incomplete_gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/gammaincomplete/README.md">Upper/Lower Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UpperSFixedSeriesTerm
{

	/**
	 * Construct the NIST (2019) Limit Version of the Upper s = 0 Term
	 * 
	 * @return The NIST (2019) Limit Version of the Upper s = 0 Term 
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm NIST2019()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("UpperSFixedSeriesTerm::NIST2019::value => Invalid Inputs");
					}

					return 0. == z ? (0 == order ? 1. : 0.) : (order % 2 == 0 ? 1. : -1.) *
						java.lang.Math.exp (
							order * java.lang.Math.log (z) - java.lang.Math.log (order) -
							new org.drip.specialfunction.loggamma.NemesAnalyticEstimator (null).evaluate (order + 1)
						);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the NIST (2019) Limit Version of the Upper s = -n Term
	 * 
	 * @param n n
	 * 
	 * @return The NIST (2019) Limit Version of the Upper s = -n Term 
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm NIST2019 (
		final int n)
	{
		if (0 >= n)
		{
			return null;
		}

		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 > order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("UpperSFixedSeriesTerm::NIST2019::value => Invalid Inputs");
					}

					return 0. == z || n <= order + 1 ? 0. : (order % 2 == 0 ? 1. : -1.) *
						java.lang.Math.exp (
							order * java.lang.Math.log (z) +
							new org.drip.specialfunction.loggamma.NemesAnalyticEstimator (null).evaluate (n - order)
						);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Weisstein Version of the Upper s .gt. 0 Term
	 * 
	 * @param s s
	 * 
	 * @return The Weisstein Version of the Upper s .gt. 0 Term 
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Weisstein (
		final int s)
	{
		if (0 >= s)
		{
			return null;
		}

		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 > order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("UpperSFixedSeriesTerm::Weisstein::value => Invalid Inputs");
					}

					return 0. == z ? (0 == order ? 1. : 0.) : java.lang.Math.pow (
						z,
						order
					) * java.lang.Math.exp (-z) /
						new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (order + 1);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
