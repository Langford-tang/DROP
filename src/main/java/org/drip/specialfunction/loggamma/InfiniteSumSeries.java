
package org.drip.specialfunction.loggamma;

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
 * <i>InfiniteSumSeries</i> implements Infinite Sum Series for Log Gamma Estimation. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Blagouchine, I. V. (2014): Re-discovery of Malmsten's Integrals, their Evaluation by Contour
 * 				Integration Methods, and some Related Results <i>Ramanujan Journal</i> <b>35 (1)</b> 21-110
 * 		</li>
 * 		<li>
 * 			Borwein, J. M., and R. M. Corless (2017): Gamma Function and the Factorial in the Monthly
 * 				https://arxiv.org/abs/1703.05349 <b>arXiv</b>
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
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/loggamma/README.md">Analytic/Series/Integral Log Gamma Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InfiniteSumSeries
{

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Euler Sum Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Infinite Euler Sum Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Euler (
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= termCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.loggamma.InfiniteSumSeriesTerm.Euler(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Weierstrass Sum Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Infinite Weierstrass Sum Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Weierstrass (
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= termCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.loggamma.InfiniteSumSeriesTerm.Weierstrass(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Malmsten-Blagouchine Fourier Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Malmsten-Blagouchine Fourier Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Fourier (
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= termCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.loggamma.InfiniteSumSeriesTerm.Fourier(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Blagouchine (2015) Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Blagouchine (2015) Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Blagouchine2015 (
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex < termCount; ++termIndex)
			{
				if (2 * termIndex != termCount)
				{
					termWeightMap.put (
						termIndex,
						1.
					);
				}
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.loggamma.InfiniteSumSeriesTerm.Blagouchine2015(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
