
package org.drip.sequence.custom;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>GlivenkoCantelliUniformDeviation</i> contains the Implementation of the Bounded Objective Function
 * dependent on Multivariate Random Variables where the Multivariate Function is a Linear Combination of
 * Bounded Univariate Functions acting on each Random Variate.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/custom">Custom</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GlivenkoCantelliUniformDeviation extends org.drip.sequence.functional.BoundedMultivariateRandom
	implements org.drip.sequence.functional.SeparableMultivariateRandom {
	private double[] _adblWeight = null;
	private org.drip.sequence.functional.BoundedIdempotentUnivariateRandom _biur = null;

	/**
	 * GlivenkoCantelliUniformDeviation Constructor
	 * 
	 * @param biur The Bounded Idempotent Univariate Random Function
	 * @param iNumSample Number of Empirical Samples
	 * 
	 * @return The GlivenkoCantelliUniformDeviation Instance
	 */

	public static final GlivenkoCantelliUniformDeviation Create (
		final org.drip.sequence.functional.BoundedIdempotentUnivariateRandom biur,
		final int iNumSample)
	{
		try {
			return new GlivenkoCantelliUniformDeviation (biur,
				org.drip.analytics.support.Helper.NormalizedEqualWeightedArray (iNumSample));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * GlivenkoCantelliUniformDeviation Constructor
	 * 
	 * @param biur The Bounded Idempotent Univariate Random Function
	 * @param adblWeight Array of Variable Weights
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GlivenkoCantelliUniformDeviation (
		final org.drip.sequence.functional.BoundedIdempotentUnivariateRandom biur,
		final double[] adblWeight)
		throws java.lang.Exception
	{
		if (null == (_adblWeight = adblWeight) || 0 == _adblWeight.length || null == (_biur = biur))
			throw new java.lang.Exception ("GlivenkoCantelliUniformDeviation ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Separable Bounded Idempotent Univariate Random Function
	 * 
	 * @return The Separable Bounded Idempotent Univariate Random Function
	 */

	public org.drip.sequence.functional.BoundedIdempotentUnivariateRandom separableUnivariateRandom()
	{
		return _biur;
	}

	/**
	 * Retrieve the Weights
	 * 
	 * @return The Weights
	 */

	public double[] weights()
	{
		return _adblWeight;
	}

	@Override public int dimension()
	{
		return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		double dblValue = 0.;
		int iNumVariate = adblVariate.length;

		if (_adblWeight.length < iNumVariate)
			throw new java.lang.Exception ("GlivenkoCantelliUniformDeviation::evaluate => Invalid Inputs");

		for (int i = 0; i < iNumVariate; ++i)
			dblValue += _adblWeight[i] * _biur.evaluate (adblVariate[i]);

		return dblValue;
	}

	@Override public double targetVariateVarianceBound (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		return _adblWeight[iTargetVariateIndex] * _biur.agnosticVarianceBound();
	}

	@Override public double targetVariateVariance (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam = _biur.sequenceMetrics();

		if (null == ssam)
			throw new java.lang.Exception
				("GlivenkoCantelliUniformDeviation::targetVariateVariance => Cannot calculate Target Variate Metrics");

		return _adblWeight[iTargetVariateIndex] * ssam.empiricalVariance();
	}
}
