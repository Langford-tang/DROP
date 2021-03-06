
package org.drip.function.e2erf;

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
 * <i>ErrorFunctionInverse</i> implements the E<sub>2</sub> erf Inverse erf<sup>-1</sup>. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN � A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann�s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/README.md">E<sub>2</sub> erf and erf<sup>-1</sup> Implementations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ErrorFunctionInverse extends org.drip.numerical.estimation.R1ToR1Estimator
{
	private org.drip.numerical.estimation.R1ToR1Series _r1ToR1SeriesGenerator = null;

	/**
	 * Construct Winitzki (2008) Version of the Analytical E<sub>2</sub> erf Inverse
	 * 
	 * @param a a
	 * 
	 * @return Winitzki (2008) Version of the Analytical E<sub>2</sub> erf Inverse
	 */

	public static final org.drip.function.e2erf.ErrorFunctionInverse Winitzki2008 (
		final double a)
	{
		try
		{
			return !org.drip.numerical.common.NumberUtil.IsValid (a) ? null :
				new org.drip.function.e2erf.ErrorFunctionInverse (
					null,
					null
				)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= -1. || z >= 1.)
					{
						throw new java.lang.Exception
							("ErrorFunctionInverse::Winitzki2008::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 0.;
					}

					if (0. > z)
					{
						return -1. * evaluate (-1. * z);
					}

					double twoOverPIA = 2. / (java.lang.Math.PI * a);

					double lnOneMinusZ2 = java.lang.Math.log (1. - z * z);

					double halfLnOneMinusZ2 = 0.5 * lnOneMinusZ2;
					double twoOverPIAPlusHalfLnOneMinusZ2 = twoOverPIA + halfLnOneMinusZ2;

					double erfi = java.lang.Math.sqrt (
						java.lang.Math.sqrt (
							twoOverPIAPlusHalfLnOneMinusZ2 * twoOverPIAPlusHalfLnOneMinusZ2 -
								(lnOneMinusZ2 / a)
						) - twoOverPIAPlusHalfLnOneMinusZ2
					);

					return erfi < 0. ? -1. * erfi : erfi;
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
	 * Construct Winitzki (2008a) Version of the Analytical E<sub>2</sub> erf Inverse
	 * 
	 * @return Winitzki (2008a) Version of the Analytical E<sub>2</sub> erf Inverse
	 */

	public static final org.drip.function.e2erf.ErrorFunctionInverse Winitzki2008a()
	{
		return Winitzki2008 (
			8. * (java.lang.Math.PI - 3.) / (3. * java.lang.Math.PI * (4. - java.lang.Math.PI))
		);
	}

	/**
	 * Construct Winitzki (2008b) Version of the Analytical E<sub>2</sub> erf Inverse
	 * 
	 * @return Winitzki (2008b) Version of the Analytical E<sub>2</sub> erf Inverse
	 */

	public static final org.drip.function.e2erf.ErrorFunctionInverse Winitzki2008b()
	{
		return Winitzki2008 (0.147);
	}

	/**
	 * Construct the Euler-MacLaurin Instance of the E<sub>2</sub> erf Inverse
	 * 
	 * @param termCount The Count of Approximation Terms
	 * 
	 * @return The Euler-MacLaurin Instance of the E<sub>2</sub> erf Inverse
	 */

	public static final ErrorFunctionInverse MacLaurin (
		final int termCount)
	{
		final org.drip.function.e2erf.MacLaurinSeries e2InverseMacLaurinSeriesGenerator =
			org.drip.function.e2erf.MacLaurinSeries.ERFI (termCount);

		if (null == e2InverseMacLaurinSeriesGenerator)
		{
			return null;
		}

		return new ErrorFunctionInverse (
			e2InverseMacLaurinSeriesGenerator,
			null
		)
		{
			@Override public double evaluate (
				final double z)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (z) || -1. >= z || 1. <= z)
				{
					throw new java.lang.Exception
						("ErrorFunctionInverse::MacLaurin::evaluate => Invalid Inputs");
				}

				double erfi = e2InverseMacLaurinSeriesGenerator.cumulative (
					0.,
					z
				);

				return erfi > 1. ? 1. : erfi;
			}
		};
	}

	protected ErrorFunctionInverse (
		final org.drip.numerical.estimation.R1ToR1Series r1ToR1SeriesGenerator,
		final org.drip.numerical.differentiation.DerivativeControl dc)
	{
		super (dc);

		_r1ToR1SeriesGenerator = r1ToR1SeriesGenerator;
	}

	@Override public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _r1ToR1SeriesGenerator ? seriesEstimate (
			x,
			null,
			null
		) : seriesEstimate (
			x,
			_r1ToR1SeriesGenerator.termWeightMap(),
			_r1ToR1SeriesGenerator
		);
	}

	/**
	 * Compute the Probit Value for the given p
	 * 
	 * @param p P
	 * 
	 * @return The Probit Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double probit (
		final double p)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (p))
		{
			throw new java.lang.Exception ("ErrorFunctionInverse::probit => Invalid Inputs");
		}

		return java.lang.Math.sqrt (2.) * evaluate (2. * p - 1.);
	}

	/**
	 * Compute the Inverse CDF Value for the given p
	 * 
	 * @param p P
	 * 
	 * @return The Inverse CDF Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double inverseCDF (
		final double p)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (p))
		{
			throw new java.lang.Exception ("ErrorFunctionInverse::inverseCDF => Invalid Inputs");
		}

		return java.lang.Math.sqrt (2.) * evaluate (2. * p - 1.);
	}
}
