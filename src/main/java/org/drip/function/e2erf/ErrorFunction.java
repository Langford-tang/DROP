
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
 * <i>ErrorFunction</i> implements the E<sub>2</sub> Error Function (erf). The References are:
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

public class ErrorFunction extends org.drip.numerical.estimation.R1ToR1IntegrandLimitEstimator
{
	private org.drip.numerical.estimation.R1ToR1Series _r1ToR1SeriesGenerator = null;

	/**
	 * Construct the Euler-MacLaurin Instance of the E<sub>2</sub> erf
	 * 
	 * @param termCount The Count of Approximation Terms
	 * 
	 * @return The Euler-MacLaurin Instance of the E<sub>2</sub> erf
	 */

	public static final ErrorFunction MacLaurin (
		final int termCount)
	{
		final org.drip.function.e2erf.MacLaurinSeries e2MacLaurinSeriesGenerator =
			org.drip.function.e2erf.MacLaurinSeries.ERF (termCount);

		if (null == e2MacLaurinSeriesGenerator)
		{
			return null;
		}

		try
		{
			return new ErrorFunction (
				e2MacLaurinSeriesGenerator,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunction::MacLaurin::evaluate => Invalid Inputs");
					}

					double erf = 2. / java.lang.Math.sqrt (java.lang.Math.PI) *
						e2MacLaurinSeriesGenerator.cumulative (
							0.,
							z
						);

					return erf > 1. ? 1. : erf;
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
	 * Construct the Convergent Hans Heinrich Burmann Version of the E<sub>2</sub> erf
	 * 
	 * @return The Convergent Hans Heinrich Burmann Version of the E<sub>2</sub> erf
	 */

	public static final ErrorFunction HansHeinrichBurmannConvergent()
	{
		final org.drip.numerical.estimation.R1ToR1Series
			hansHeinrichBurmannConvergentSeriesGenerator =
				org.drip.function.e2erf.HansHeinrichBurmannSeries.Convergent();

		if (null == hansHeinrichBurmannConvergentSeriesGenerator)
		{
			return null;
		}

		try
		{
			return new ErrorFunction (
				hansHeinrichBurmannConvergentSeriesGenerator,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunction::HansHeinrichBurmannConvergent::evaluate => Invalid Inputs");
					}

					double erf = 2. / java.lang.Math.sqrt (java.lang.Math.PI) *
						java.lang.Math.sqrt (1. - java.lang.Math.exp (-1. * z * z)) *
						hansHeinrichBurmannConvergentSeriesGenerator.cumulative (
							0.,
							z
						);

					return erf > 1. ? 1. : erf;
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
	 * Construct the Schopf-Supancic (2014) Hans Heinrich Burmann Version of the E<sub>2</sub> erf
	 * 
	 * @return The Schopf-Supancic (2014) Hans Heinrich Burmann Version of the E<sub>2</sub> erf
	 */

	public static final ErrorFunction HansHeinrichBurmannSchopfSupancic2014()
	{
		final org.drip.numerical.estimation.R1ToR1Series hansHeinrichBurmannConvergentSeriesGenerator
			= org.drip.function.e2erf.HansHeinrichBurmannSeries.SchopfSupancic2014();

		if (null == hansHeinrichBurmannConvergentSeriesGenerator)
		{
			return null;
		}

		try
		{
			return new ErrorFunction (
				hansHeinrichBurmannConvergentSeriesGenerator,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunction::HansHeinrichBurmannSchopfSupancic2014::evaluate => Invalid Inputs");
					}

					double erf = 2. / java.lang.Math.sqrt (java.lang.Math.PI) *
						java.lang.Math.sqrt (1. - java.lang.Math.exp (-1. * z * z)) *
						hansHeinrichBurmannConvergentSeriesGenerator.cumulative (
							0.,
							z
						);

					return erf > 1. ? 1. : erf;
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
	 * ErrorFunction Constructor
	 * 
	 * @param r1ToR1SeriesGenerator R<sup>1</sup> To R<sup>1</sup> Series Generator
	 * @param dc Differential Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ErrorFunction (
		final org.drip.numerical.estimation.R1ToR1Series r1ToR1SeriesGenerator,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (
			dc,
			0.
		);

		_r1ToR1SeriesGenerator = r1ToR1SeriesGenerator;
	}

	@Override public double derivative (
		final double z,
		final int order)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) ||
			1 > order)
		{
			throw new java.lang.Exception ("ErrorFunction::derivative => Invalid Inputs");
		}

		return 1 == order ? 2. * java.lang.Math.exp (-1. * z * z) / java.lang.Math.sqrt (java.lang.Math.PI) :
			super.derivative (
				z,
				order
			);
	}

	@Override public org.drip.function.definition.R1ToR1 antiDerivative()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("ErrorFunction::antiDerivative::evaluate => Invalid Inputs");
				}

				return x * this.evaluate (x) + java.lang.Math.exp (-1. * x * x) / java.lang.Math.sqrt
					(java.lang.Math.PI);
			}
		};
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

	@Override public org.drip.function.definition.R1ToR1 integrand()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double z)
			{
				return 2. * java.lang.Math.exp (-1. * z * z) / java.lang.Math.sqrt (java.lang.Math.PI);
			}
		};
	}

	/**
	 * Compute the Q Value for the given X
	 * 
	 * @param x X
	 * 
	 * @return The Q Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double q (
		final double x)
		throws java.lang.Exception
	{
		return 0.5 * (1. - evaluate (x / java.lang.Math.sqrt (2.)));
	}

	/**
	 * Compute the CDF Value for the given X
	 * 
	 * @param x X
	 * 
	 * @return The CDF Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double cdf (
		final double x)
		throws java.lang.Exception
	{
		return 0.5 * (1. + evaluate (x / java.lang.Math.sqrt (2.)));
	}

	/**
	 * Compute the erfc Value for the given X
	 * 
	 * @param x X
	 * 
	 * @return The erfc Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double erfc (
		final double x)
		throws java.lang.Exception
	{
		return 1. - evaluate (x);
	}

	/**
	 * Compute the E<sub>2</sub> erf Gaussian Density Integral over -inf to +inf
	 * 
	 * @param a The Scale Parameter
	 * @param b The Displacement Parameter
	 * @param r1UnivariateNormal The R<sup>1</sup> Gaussian Distribution Parameters
	 * 
	 * @return The E<sub>2</sub> erf Gaussian Density Integral over -inf to +inf
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double gaussianDensityIntegral (
		final double a,
		final double b,
		final org.drip.measure.gaussian.R1UnivariateNormal r1UnivariateNormal)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (a) ||
			!org.drip.numerical.common.NumberUtil.IsValid (b) ||
			null == r1UnivariateNormal)
		{
			throw new java.lang.Exception ("ErrorFunction::gaussianDensityIntegral => Invalid Inputs");
		}

		double sigma = r1UnivariateNormal.variance();

		return evaluate (
			(a * r1UnivariateNormal.mean() + b) / java.lang.Math.sqrt (1. + 2 * sigma * sigma * a * a)
		);
	}
}
