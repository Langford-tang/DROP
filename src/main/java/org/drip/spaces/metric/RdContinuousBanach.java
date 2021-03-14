
package org.drip.spaces.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>RdContinuousBanach</i> implements the Normed, Bounded/Unbounded Continuous l<sub>p</sub> R<sup>d</sup>
 * Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric/README.md">Hilbert/Banach Normed Metric Spaces</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdContinuousBanach extends org.drip.spaces.tensor.RdContinuousVector implements
	org.drip.spaces.metric.RdNormed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.Rd _distRd = null;

	/**
	 * Construct the Standard l^p R^d Continuous Banach Space Instance
	 * 
	 * @param iDimension The Space Dimension
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l^p R^d Continuous Banach Space Instance
	 */

	public static final RdContinuousBanach StandardBanach (
		final int iDimension,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm)
	{
		try {
			return 0 >= iDimension ? null : new RdContinuousBanach (new
				org.drip.spaces.tensor.R1ContinuousVector[iDimension], distRd, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l^Infinity) R^d Continuous Banach Space Instance
	 * 
	 * @param iDimension The Space Dimension
	 * @param distRd The R^d Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l^Infinity) R^d Continuous Banach Space Instance
	 */

	public static final RdContinuousBanach SupremumBanach (
		final int iDimension,
		final org.drip.measure.continuous.Rd distRd)
	{
		try {
			return 0 >= iDimension ? null : new RdContinuousBanach (new
				org.drip.spaces.tensor.R1ContinuousVector[iDimension], distRd, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RdContinuousBanach Space Constructor
	 * 
	 * @param aR1CV Array of R^1 Continuous Vector
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdContinuousBanach (
		final org.drip.spaces.tensor.R1ContinuousVector[] aR1CV,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (aR1CV);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("RdContinuousBanach Constructor: Invalid p-norm");

		_distRd = distRd;
	}

	@Override public int pNorm()
	{
		return _iPNorm;
	}

	@Override public org.drip.measure.continuous.Rd borelSigmaMeasure()
	{
		return _distRd;
	}

	@Override public double sampleSupremumNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdContinuousBanach::sampleSupremumNorm => Invalid Inputs");

		int iDimension = adblX.length;

		double dblNorm = java.lang.Math.abs (adblX[0]);

		for (int i = 1; i < iDimension; ++i) {
			double dblAbsoluteX = java.lang.Math.abs (adblX[i]);

			dblNorm = dblNorm > dblAbsoluteX ? dblNorm : dblAbsoluteX;
		}

		return dblNorm;
	}

	@Override public double sampleMetricNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdContinuousBanach::sampleMetricNorm => Invalid Inputs");

		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (adblX);

		double dblNorm = 0.;
		int iDimension = adblX.length;

		for (int i = 0; i < iDimension; ++i)
			dblNorm += java.lang.Math.pow (java.lang.Math.abs (adblX[i]), _iPNorm);

		return java.lang.Math.pow (dblNorm, 1. / _iPNorm);
	}

	@Override public double[] populationMode()
	{
		if (null == _distRd) return null;

		org.drip.function.definition.RdToR1 funcRdToR1 = new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] adblX)
				throws java.lang.Exception
			{
				return _distRd.density (adblX);
			}
		};

		org.drip.function.definition.VariateOutputPair vopMode = funcRdToR1.maxima (leftDimensionEdge(),
			rightDimensionEdge());

		return null == vopMode ? null : vopMode.variates();
	}

	@Override public double populationSupremumNorm()
		throws java.lang.Exception
	{
		if (null == _distRd)
			throw new java.lang.Exception ("RdContinuousBanach::populationSupremumNorm => Invalid Inputs");

		return sampleSupremumNorm (populationMode());
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (null == _distRd)
			throw new java.lang.Exception ("RdContinuousBanach::populationMetricNorm => Invalid Inputs");

		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (populationMode());

		org.drip.function.definition.RdToR1 funcRdToR1 = new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] adblX)
				throws java.lang.Exception
			{
				double dblNorm = 0.;
				int iDimension = adblX.length;

				for (int i = 0; i < iDimension; ++i)
					dblNorm += java.lang.Math.pow (java.lang.Math.abs (adblX[i]), _iPNorm);

				return dblNorm * _distRd.density (adblX);
			}
		};

		return java.lang.Math.pow (funcRdToR1.integrate (leftDimensionEdge(), rightDimensionEdge()), 1. /
			_iPNorm);
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == _distRd || null == funcRdToR1)
			throw new java.lang.Exception
				("RdContinuousBanach::borelMeasureSpaceExpectation => Invalid Inputs");

		org.drip.function.definition.RdToR1 funcDensityRdToR1 = new org.drip.function.definition.RdToR1
			(null) {
			@Override public int dimension()
			{
				return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] adblX)
				throws java.lang.Exception
			{
				return funcRdToR1.evaluate (adblX) * _distRd.density (adblX);
			}
		};

		return funcDensityRdToR1.integrate (leftDimensionEdge(), rightDimensionEdge());
	}
}
