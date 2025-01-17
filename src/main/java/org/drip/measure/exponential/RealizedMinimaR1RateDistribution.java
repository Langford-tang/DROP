
package org.drip.measure.exponential;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>RealizedMinimaR1RateDistribution</i> implements the Rate Parameterization of the Realized Minimum among
 *  the Set of R<sup>1</sup> Exponential Distributions. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Exponential Distribution (2019): Exponential Distribution
 * 				https://en.wikipedia.org/wiki/Exponential_distribution
 * 		</li>
 * 		<li>
 * 			Norton, M., V. Khokhlov, and S. Uryasev (2019): Calculating CVaR and bPOE for Common Probability
 * 				Distributions with Application to Portfolio Optimization and Density Estimation <i>Annals of
 * 				Operations Research</i> <b>299 (1-2)</b> 1281-1315
 * 		</li>
 * 		<li>
 * 			Ross, S. M. (2009): <i>Introduction to Probability and Statistics for Engineers and Scientists
 * 				4<sup>th</sup> Edition</i> <b>Associated Press</b> New York, NY
 * 		</li>
 * 		<li>
 * 			Schmidt, D. F., and D. Makalic (2009): Universal Models for the Exponential Distribution <i>IEEE
 * 				Transactions on Information Theory</i> <b>55 (7)</b> 3087-3090
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/exponential/README.md">R<sup>1</sup> Exponential Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RealizedMinimaR1RateDistribution
	extends R1RateDistribution
{
	private R1RateDistribution[] _r1RateDistributionArray = null;

	/**
	 * Standard Instance of RealizedMinimaR1RateDistribution
	 * 
	 * @param lambdaArray Array of the Distribution Lambdas
	 * 
	 * @return Standard Instance of RealizedMinimaR1RateDistribution
	 */

	public static final RealizedMinimaR1RateDistribution Standard (
		final double[] lambdaArray)
	{
		if (null == lambdaArray)
		{
			return null;
		}

		double compositeDistributionLambda = 0.;
		int exponentialDistributionCount = lambdaArray.length;

		if (0 == exponentialDistributionCount)
		{
			return null;
		}

		try
		{
			R1RateDistribution[] r1RateDistributionArray =
				new R1RateDistribution[exponentialDistributionCount];

			for (int distributionIndex = 0;
				distributionIndex < exponentialDistributionCount;
				++distributionIndex)
			{
				r1RateDistributionArray[distributionIndex] =
					new R1RateDistribution (lambdaArray[distributionIndex]);

				compositeDistributionLambda += lambdaArray[distributionIndex];
			}

			return new RealizedMinimaR1RateDistribution (
				r1RateDistributionArray,
				compositeDistributionLambda
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private RealizedMinimaR1RateDistribution (
		final R1RateDistribution[] r1RateDistributionArray,
		final double compositeDistributionLambda)
		throws Exception
	{
		super (
			compositeDistributionLambda
		);

		_r1RateDistributionArray = r1RateDistributionArray;
	}

	/**
	 * Retrieve the R<sup>1</sup> Exponential Distribution Array
	 * 
	 * @return The R<sup>1</sup> Exponential Distribution Array
	 */

	public R1RateDistribution[] r1RateDistributionArray()
	{
		return _r1RateDistributionArray;
	}

	/**
	 * Calculate the Probability that the specified Index corresponds to the Realized Minimum
	 * 
	 * @param index Specified Index
	 * 
	 * @return Probability that the specified Index corresponds to the Realized Minimum
	 * 
	 * @throws Exception Thrown if the Index cannot be calculated
	 */

	public double probabilityOfIndexAsMinimum (
		final int index)
		throws Exception
	{
		if (index >= _r1RateDistributionArray.length)
		{
			throw new Exception (
				"RealizedMinimaR1RateDistribution::probabilityOfIndexAsMinimum => Invalid Index"
			);
		}

		return _r1RateDistributionArray[index].rate() / rate();
	}
}
