
package org.drip.specialfunction.property;

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
 * <i>DigammaSaddlePointEqualityLemma</i> contains the Verifiable Equality Lemmas for the Digamma Saddle
 * Points. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DigammaSaddlePointEqualityLemma
{

	/**
	 * Construct the Quadratic Reciprocal Sum Verifier
	 * 
	 * @param digammaSaddlePointsFunction Digamma Saddle Points Function
	 * 
	 * @return The Quadratic Reciprocal Sum Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property QuadraticReciprocalSum (
		final org.drip.function.definition.R1ToR1 digammaSaddlePointsFunction)
	{
		if (null == digammaSaddlePointsFunction)
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticReciprocalSum::evaluate => Invalid Inputs");
						}

						double quadraticReciprocalSum = 1. / (
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION
						);

						for (int saddlePointIndex = 1; saddlePointIndex <= s; ++saddlePointIndex)
						{
							double saddlePoint = digammaSaddlePointsFunction.evaluate (saddlePointIndex);

							quadraticReciprocalSum = quadraticReciprocalSum + 1. / (
								saddlePoint * saddlePoint
							);
						}

						return quadraticReciprocalSum;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticReciprocalSum::evaluate => Invalid Inputs");
						}

						return org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
							org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
							0.5 * java.lang.Math.PI * java.lang.Math.PI;
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Cubic Reciprocal Sum Verifier
	 * 
	 * @param digammaSaddlePointsFunction Digamma Saddle Points Function
	 * 
	 * @return The Cubic Reciprocal Sum Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property CubicReciprocalSum (
		final org.drip.function.definition.R1ToR1 digammaSaddlePointsFunction)
	{
		if (null == digammaSaddlePointsFunction)
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::CubicReciprocalSum::evaluate => Invalid Inputs");
						}

						double cubicReciprocalSum = 1. / (
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION
						);

						for (int saddlePointIndex = 1; saddlePointIndex <= s; ++saddlePointIndex)
						{
							double saddlePoint = digammaSaddlePointsFunction.evaluate (saddlePointIndex);

							cubicReciprocalSum = cubicReciprocalSum + 1. / (
								saddlePoint * saddlePoint * saddlePoint
							);
						}

						return cubicReciprocalSum;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::CubicReciprocalSum::evaluate => Invalid Inputs");
						}

						return -4. * new org.drip.specialfunction.derived.RiemannZeta (
							null,
							new org.drip.specialfunction.gamma.WindschitlTothAnalytic (null)
						).evaluate (3.) -
							org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI -
							0.5 * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								java.lang.Math.PI * java.lang.Math.PI;
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Quartic Reciprocal Sum Verifier
	 * 
	 * @param digammaSaddlePointsFunction Digamma Saddle Points Function
	 * 
	 * @return The Quartic Reciprocal Sum Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property QuarticReciprocalSum (
		final org.drip.function.definition.R1ToR1 digammaSaddlePointsFunction)
	{
		if (null == digammaSaddlePointsFunction)
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuarticReciprocalSum::evaluate => Invalid Inputs");
						}

						double quarticReciprocalSum = 1. / (
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION
						);

						for (int saddlePointIndex = 1; saddlePointIndex <= s; ++saddlePointIndex)
						{
							double saddlePoint = digammaSaddlePointsFunction.evaluate (saddlePointIndex);

							quarticReciprocalSum = quarticReciprocalSum + 1. / (
								saddlePoint * saddlePoint * saddlePoint * saddlePoint
							);
						}

						return quarticReciprocalSum;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuarticReciprocalSum::evaluate => Invalid Inputs");
						}

						return
							org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
							java.lang.Math.PI * java.lang.Math.PI * java.lang.Math.PI * java.lang.Math.PI /
								9. +
							2. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								java.lang.Math.PI * java.lang.Math.PI / 3. +
							4. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								new org.drip.specialfunction.derived.RiemannZeta (
									null,
									new org.drip.specialfunction.gamma.WindschitlTothAnalytic (null)
								).evaluate (3.);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the First Quadratic Polynomial Reciprocal Sum Verifier
	 * 
	 * @param digammaSaddlePointsFunction Digamma Saddle Points Function
	 * 
	 * @return The First Quadratic Polynomial Reciprocal Sum Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property QuadraticPolynomialReciprocalSum1 (
		final org.drip.function.definition.R1ToR1 digammaSaddlePointsFunction)
	{
		if (null == digammaSaddlePointsFunction)
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticPolynomialReciprocalSum1::evaluate => Invalid Inputs");
						}

						double quadraticReciprocalSum = 1. / (
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION +
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION
						);

						for (int saddlePointIndex = 1; saddlePointIndex <= s; ++saddlePointIndex)
						{
							double saddlePoint = digammaSaddlePointsFunction.evaluate (saddlePointIndex);

							quadraticReciprocalSum = quadraticReciprocalSum + 1. / (
								saddlePoint * saddlePoint + saddlePoint
							);
						}

						return quadraticReciprocalSum;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticPolynomialReciprocalSum1::evaluate => Invalid Inputs");
						}

						return -2.;
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Second Quadratic Polynomial Reciprocal Sum Verifier
	 * 
	 * @param digammaSaddlePointsFunction Digamma Saddle Points Function
	 * 
	 * @return The Second Quadratic Polynomial Reciprocal Sum Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property QuadraticPolynomialReciprocalSum2 (
		final org.drip.function.definition.R1ToR1 digammaSaddlePointsFunction)
	{
		if (null == digammaSaddlePointsFunction)
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticPolynomialReciprocalSum2::evaluate => Invalid Inputs");
						}

						double quadraticReciprocalSum = 1. / (
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION *
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION -
							org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION
						);

						for (int saddlePointIndex = 1; saddlePointIndex <= s; ++saddlePointIndex)
						{
							double saddlePoint = digammaSaddlePointsFunction.evaluate (saddlePointIndex);

							quadraticReciprocalSum = quadraticReciprocalSum + 1. / (
								saddlePoint * saddlePoint - saddlePoint
							);
						}

						return quadraticReciprocalSum;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("DigammaSaddlePointEqualityLemma::QuadraticPolynomialReciprocalSum2::evaluate => Invalid Inputs");
						}

						return org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI + 
							(java.lang.Math.PI * java.lang.Math.PI /
								(6. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI));
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
