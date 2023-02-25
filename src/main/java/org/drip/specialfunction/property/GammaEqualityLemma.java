
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
 * <i>GammaEqualityLemma</i> contains the Verifiable Equality Lemmas of the Gamma Function. The References
 * are:
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

public class GammaEqualityLemma
{

	/**
	 * Construct the Reflection Formula Verifier
	 * 
	 * @return The Reflection Formula Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ReflectionFormula()
	{
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
								("GammaEqualityLemma::ReflectionFormula::evaluate => Invalid Inputs");
						}

						org.drip.specialfunction.loggamma.InfiniteSumEstimator weierstrassInfiniteProduct =
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

						return java.lang.Math.exp (weierstrassInfiniteProduct.evaluate (1. - s) +
							weierstrassInfiniteProduct.evaluate (s));
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
								("GammaEqualityLemma::ReflectionFormula::evaluate => Invalid Inputs");
						}

						return java.lang.Math.PI / java.lang.Math.sin (java.lang.Math.PI * s);
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
	 * Construct the Duplication Formula Verifier
	 * 
	 * @return The Duplication Formula Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property DuplicationFormula()
	{
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
								("GammaEqualityLemma::DuplicationFormula::evaluate => Invalid Inputs");
						}

						org.drip.specialfunction.loggamma.InfiniteSumEstimator weierstrassInfiniteProduct =
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

						return weierstrassInfiniteProduct.evaluate (s) + weierstrassInfiniteProduct.evaluate
							(s + 0.5);
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
								("GammaEqualityLemma::DuplicationFormula::evaluate => Invalid Inputs");
						}

						return (1. - 2. * s) * java.lang.Math.log (2.) +
							0.5 * java.lang.Math.log (java.lang.Math.PI)  +
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400).evaluate (2. * s);
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
	 * Construct the Multiplication Formula Verifier
	 * 
	 * @param m m
	 * 
	 * @return The Multiplication Formula Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property MultiplicationFormula (
		final int m)
	{
		if (1 >= m)
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
								("GammaEqualityLemma::MultiplicationFormula::evaluate => Invalid Inputs");
						}

						double logGammaSum = 0.;

						org.drip.specialfunction.loggamma.InfiniteSumEstimator weierstrassInfiniteProduct =
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

						for (double i = 0; i < m; ++i)
						{
							logGammaSum = logGammaSum + weierstrassInfiniteProduct.evaluate (s + (i / m));
						}

						return logGammaSum;
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
								("GammaEqualityLemma::MultiplicationFormula::evaluate => Invalid Inputs");
						}

						return 0.5 * (m - 1.) * java.lang.Math.log (2. * java.lang.Math.PI) +
							(0.5 - m * s) * java.lang.Math.log (m)  +
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400).evaluate (m * s);
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
