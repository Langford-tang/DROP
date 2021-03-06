
package org.drip.learning.bound;

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
 * <i>CoveringNumberLossBound provides</i> the Upper Probability Bound that the Loss/Deviation of the
 * Empirical from the Actual Mean of the given Learner Class exceeds 'epsilon', using the Covering Number
 * Generalization Bounds. This is expressed as
 * <br><br> 
 *  						C1 (n) * N (epsilon, n) * exp (-n.epsilon^b/C2)
 * <br><br> 
 * where:
 * <ul>
 * 	<li>
 *  	n is the Size of the Sample
 * 	</li>
 * 	<li>
 *  	'epsilon' is the Deviation Empirical Mean from the Population Mean
 * 	</li>
 * 	<li>
 *  	C1 (n) is the sample coefficient function
 * 	</li>
 * 	<li>
 *  	C2 is an exponent scaling constant
 * 	</li>
 * 	<li>
 *  	'b' an exponent ((i.e., the Epsilon Exponent) that depends on the setting (i.e.,
 *  		agnostic/classification/regression/convex etc)
 * 	</li>
 * </ul>
 * <br><br>
 *  
 *  The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound">Covering Numbers, Concentration, Lipschitz Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringNumberLossBound {
	private double _dblExponentScaler = java.lang.Double.NaN;
	private double _dblEpsilonExponent = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _funcSampleCoefficient = null;

	/**
	 * CoveringNumberLossBound Constructor
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblEpsilonExponent The Epsilon Exponent
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CoveringNumberLossBound (
		final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
		final double dblEpsilonExponent,
		final double dblExponentScaler)
		throws java.lang.Exception
	{
		if (null == (_funcSampleCoefficient = funcSampleCoefficient) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEpsilonExponent = dblEpsilonExponent) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblExponentScaler = dblExponentScaler))
			throw new java.lang.Exception ("CoveringNumberLossBound ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Sample Coefficient Function
	 * 
	 * @return The Sample Coefficient Function
	 */

	public org.drip.function.definition.R1ToR1 sampleCoefficient()
	{
		return _funcSampleCoefficient;
	}

	/**
	 * Retrieve the Exponential Epsilon Exponent
	 * 
	 * @return The Exponential Epsilon Exponent
	 */

	public double epsilonExponent()
	{
		return _dblEpsilonExponent;
	}

	/**
	 * Retrieve the Exponent Scaler
	 * 
	 * @return The Exponent Scaler
	 */

	public double exponentScaler()
	{
		return _dblExponentScaler;
	}

	/**
	 * Compute the Upper Bound of the Probability of the Absolute Deviation between the Empirical and the
	 * 	Population Means
	 * 
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation between Population and Empirical Means
	 * 
	 * @return The Upper Bound of the Probability of the Deviation between the Empirical and the Population
	 *  Means
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound of the Probability cannot be computed
	 */

	public double deviationProbabilityUpperBound (
		final int iSampleSize,
		final double dblEpsilon)
		throws java.lang.Exception
	{
		if (0 >= iSampleSize || !org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon)
			throw new java.lang.Exception
				("CoveringNumberLossBound::deviationProbabilityUpperBound => Invalid Inputs");

		return _funcSampleCoefficient.evaluate (iSampleSize) * java.lang.Math.exp (-1. * iSampleSize *
			java.lang.Math.pow (dblEpsilon, _dblEpsilonExponent) / _dblExponentScaler);
	}
}
