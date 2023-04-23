
package org.drip.measure.exponential;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.continuous.R1Univariate;
import org.drip.numerical.common.NumberUtil;

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
 * <i>R1RateDistribution</i> implements the Rate Parameterization of the R<sup>1</sup> Exponential
 * 	Distribution. The References are:
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

public class R1RateDistribution
	extends R1Univariate
{
	private double _lambda = Double.NaN;

	/**
	 * Construct a Standard Scale Parameterized Instance of R<sup>1</sup> Exponential Distribution
	 * 
	 * @param beta The Scale Parameter Beta
	 * 
	 * @return Scale Parameterized Instance of R<sup>1</sup> Exponential Distribution
	 */

	public static final R1RateDistribution ScaleStandard (
		final double beta)
	{
		try
		{
			return new R1RateDistribution (1. / beta);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1RateDistribution Constructor
	 * 
	 * @param lambda Rate Parameter
	 * 
	 * @throws Exception Thrown if lambda is invalid
	 */

	public R1RateDistribution (
		final double lambda)
		throws Exception
	{
		if (!NumberUtil.IsValid (_lambda = lambda) || 0. > _lambda)
		{
			throw new Exception ("R1RateDistribution Constructor => Invalid lambda");
		}
	}

	/**
	 * Retrieve the Rate Parameter Lambda
	 * 
	 * @return Rate Parameter Lambda
	 */

	public double lambda()
	{
		return _lambda;
	}

	/**
	 * Retrieve the Rate Parameter Lambda
	 * 
	 * @return Rate Parameter Lambda
	 */

	public double rate()
	{
		return _lambda;
	}

	/**
	 * Retrieve the Scale Parameter
	 * 
	 * @return Scale Parameter
	 */

	public double scale()
	{
		return 1. / _lambda;
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
		if (!supported (
			t
		))
		{
			throw new Exception (
				"R1RateDistribution::density => Variate not in Range"
			);
		}

		return 0. > t ? 0. : _lambda * Math.exp (
			-1. * _lambda * t
		);
	}

	@Override public double cumulative (
		final double t)
		throws Exception
	{
		if (!supported (
			t
		))
		{
			throw new Exception (
				"R1RateDistribution::cumulative => Variate not in Range"
			);
		}

		return 0. > t ? 0. : 1. - Math.exp (
			-1. * _lambda * t
		);
	}

	@Override public double mean()
		throws Exception
	{
		return 1. / _lambda;
	}

	@Override public double median()
		throws Exception
	{
		return Math.exp (
			2.
		) / _lambda;
	}

	@Override public double mode()
		throws Exception
	{
		return 0.;
	}

	@Override public double quantile (
		final double p)
		throws Exception
	{
		if (!NumberUtil.IsValid (
				p
			) || 0. > p || 1. < p
		)
		{
			throw new Exception (
				"R1RateDistribution::quantile => p is Invalid"
			);
		}

		if (0. == p)
		{
			return support()[0];
		}

		if (1. == p)
		{
			return support()[1];
		}

		return -1. * Math.log (
			1. - p
		) / _lambda;
	}

	@Override public double variance()
		throws Exception
	{
		return 1. / _lambda / _lambda;
	}

	@Override public double skewness()
		throws Exception
	{
		return 2.;
	}

	@Override public double excessKurtosis()
		throws Exception
	{
		return 1. - Math.log (
			_lambda
		);
	}

	@Override public R1ToR1 momentGeneratingFunction()
	{
		return new R1ToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double t)
				throws Exception
			{
				if (!NumberUtil.IsValid (
						t
					)
				)
				{
					throw new Exception (
						"R1RateDistribution::momentGeneratingFunction::evaluate => t is Invalid"
					);
				}

				return t >= _lambda ? 0. : _lambda / (_lambda - t);
			}
		};
	}

	@Override public double fisherInformation()
		throws Exception
	{
		return 1. / _lambda / _lambda;
	}

	@Override public double kullbackLeiblerDivergence (
		final R1Univariate r1UnivariateOther)
		throws Exception
	{
		if (null == r1UnivariateOther ||
			!(r1UnivariateOther instanceof R1RateDistribution)
		)
		{
			throw new Exception (
				"R1RateDistribution::kullbackLeiblerDivergence => Invalid Inputs"
			);
		}

		double lambdaRatio = (((R1RateDistribution) r1UnivariateOther).lambda()) / _lambda;

		return lambdaRatio - Math.log (
			lambdaRatio
		) - 1.;
	}

	@Override public double cvar (
		final double p)
		throws Exception
	{
		if (!NumberUtil.IsValid (
				p
			) || 0. > p || 1. < p
		)
		{
			throw new Exception (
				"R1RateDistribution::cvar => p is Invalid"
			);
		}

		return -1. * (
			1. + Math.log (
				1. - p
			)
		) / _lambda;
	}

	@Override public double bPOE (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (
				x
			)
		)
		{
			throw new Exception (
				"R1RateDistribution::bPOE => x is Invalid"
			);
		}

		return Math.exp (
			1. - _lambda * x
		);
	}

	@Override public double nonCentralMoment (
		final int n)
		throws java.lang.Exception
	{
		return NumberUtil.Factorial (
			n
		) * Math.pow (
			_lambda,
			-n
		);
	}

	@Override public double centralMoment (
		final int n)
		throws Exception
	{
		return NumberUtil.SubFactorial (
			n
		) * Math.pow (
			_lambda,
			-n
		);
	}

	@Override public double iqr()
		throws Exception
	{
		return Math.log (
			3.
		) / _lambda;
	}
}
