
package org.drip.measure.exponential;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.continuous.R1Univariate;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.numerical.integration.R1ToR1Integrator;
import org.drip.specialfunction.digamma.BinetFirstIntegral;
import org.drip.specialfunction.gamma.Definitions;

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
 * <i>TwoIIDSum</i> implements the PDF of the Sum of Two IID Exponential Random Variables. The References
 *  are:
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

public class TwoIIDSum
	extends R1Univariate
{
	private static final int QUADRATURE_POINT_COUNT = 100;

	private R1RateDistribution _largerR1RateDistribution = null;
	private R1RateDistribution _smallerR1RateDistribution = null;

	/**
	 * TwoIIDSum Constructor
	 * 
	 * @param firstR1RateDistribution First R<sup>1</sup> Exponential Distribution
	 * @param secondR1RateDistribution Second R<sup>1</sup> Exponential Distribution
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public TwoIIDSum (
		final R1RateDistribution firstR1RateDistribution,
		final R1RateDistribution secondR1RateDistribution)
		throws Exception
	{
		if (null == firstR1RateDistribution || null == secondR1RateDistribution)
		{
			throw new Exception (
				"TwoIIDSum Constructor: Invalid Inputs"
			);
		}

		double firstRate = firstR1RateDistribution.rate();

		double secondRate = secondR1RateDistribution.rate();

		if (firstRate > secondRate)
		{
			_largerR1RateDistribution = firstR1RateDistribution;
			_smallerR1RateDistribution = secondR1RateDistribution;
		}
		else
		{
			_largerR1RateDistribution = secondR1RateDistribution;
			_smallerR1RateDistribution = firstR1RateDistribution;
		}
	}

	/**
	 * Retrieve the Larger Exponential Distribution
	 * 
	 * @return The Larger Exponential Distribution
	 */

	public R1RateDistribution largerR1RateDistribution()
	{
		return _largerR1RateDistribution;
	}

	/**
	 * Retrieve the Smaller Exponential Distribution
	 * 
	 * @return The Smaller Exponential Distribution
	 */

	public R1RateDistribution smallerR1RateDistribution()
	{
		return _smallerR1RateDistribution;
	}

	@Override public double[] support()
	{
		return new double[]
		{
			0.,
			Double.POSITIVE_INFINITY
		};
	}

	@Override public double density (
		final double t)
		throws Exception
	{
		if (Double.isInfinite (
			t
		))
		{
			return 0.;
		}

		if (!supported (
			t
		))
		{
			throw new Exception (
				"TwoIIDSum::density => Variate not in Range"
			);
		}

		double largerRate = _largerR1RateDistribution.rate();

		double smallerRate = _smallerR1RateDistribution.rate();

		if (largerRate == smallerRate)
		{
			return smallerRate * smallerRate * t * Math.exp (
				-1. * smallerRate * t
			);
		}

		return smallerRate * largerRate * (
			Math.exp (
				-1. * smallerRate * t
			) - Math.exp (
				-1. * largerRate * t
			)
		) / (largerRate - smallerRate);
	}

	@Override public double mean()
		throws Exception
	{
		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
			0.,
			QUADRATURE_POINT_COUNT
		).integrate (
			new R1ToR1 (
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					return z * density (
						z
					);
				}
			}
		);
	}

	@Override public double mode()
		throws Exception
	{
		double largerRate = _largerR1RateDistribution.rate();

		double smallerRate = _smallerR1RateDistribution.rate();

		return largerRate == smallerRate ? 1. / largerRate : (
			Math.log (
				largerRate
			) - Math.log (
				smallerRate
			)
		) / (largerRate - smallerRate);
	}

	@Override public double variance()
		throws Exception
	{
		final double mean = mean();

		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
			0.,
			QUADRATURE_POINT_COUNT
		).integrate (
			new R1ToR1 (
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					return (z - mean) * (z - mean) * density (
						z
					);
				}
			}
		);
	}

	@Override public double cumulative (
		final double upper)
		throws Exception
	{
		if (Double.isNaN (
			upper
		))
		{
			throw new Exception (
				"TwoIIDSum::cumulative => Invalid Upper Variate"
			);
		}

		return R1ToR1Integrator.Boole (
			new R1ToR1 (
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					return density (
						z
					);
				}
			},
			0.,
			upper
		);
	}

	@Override public double differentialEntropy()
		throws Exception
	{
		double largerRate = _largerR1RateDistribution.rate();

		double smallerRate = _smallerR1RateDistribution.rate();

		return largerRate == smallerRate ? Double.NaN : 1. + Definitions.EULER_MASCHERONI + Math.log (
			(largerRate - smallerRate) / (largerRate * smallerRate)
		) + new BinetFirstIntegral (
			null
		).evaluate (
			largerRate / (largerRate - smallerRate)
		);
	}
}
