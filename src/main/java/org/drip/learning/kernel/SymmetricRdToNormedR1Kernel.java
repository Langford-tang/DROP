
package org.drip.learning.kernel;

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
 * <i>SymmetricRdToNormedR1Kernel</i> exposes the Functionality behind the Kernel that is Normed
 * R<sup>d</sup> X Normed R<sup>d</sup> To Supremum R<sup>1</sup>, that is, a Kernel that symmetric in the
 * Input Metric Vector Space in terms of both the Metric and the Dimensionality.
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 *  	Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel,
 *  		Switzerland
 * 	</li>
 * 	<li>
 *  	Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  		Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B.
 *  		Scholkopf, and D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel">Statistical Learning Banach Mercer Kernels</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class SymmetricRdToNormedR1Kernel {
	private org.drip.spaces.metric.RdNormed _rdContinuousInput = null;
	private org.drip.spaces.metric.R1Normed _r1ContinuousOutput = null;

	/**
	 * SymmetricRdToNormedR1Kernel Constructor
	 * 
	 * @param rdContinuousInput The Symmetric Input R^d Metric Vector Space
	 * @param r1ContinuousOutput The Output R^1 Metric Vector Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SymmetricRdToNormedR1Kernel (
		final org.drip.spaces.metric.RdNormed rdContinuousInput,
		final org.drip.spaces.metric.R1Normed r1ContinuousOutput)
		throws java.lang.Exception
	{
		if (null == (_rdContinuousInput = rdContinuousInput) || null == (_r1ContinuousOutput =
			r1ContinuousOutput))
			throw new java.lang.Exception ("SymmetricRdToNormedR1Kernel ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Symmetric Input Metric R^d Vector Space
	 * 
	 * @return The Symmetric Input Metric R^d Vector Space
	 */

	public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdContinuousInput;
	}

	/**
	 * Retrieve the Output R^1 Metric Vector Space
	 * 
	 * @return The Output R^1 Metric Vector Space
	 */

	public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return _r1ContinuousOutput;
	}

	/**
	 * Compute the Feature Space Input Dimension
	 * 
	 * @return The Feature Space Input Dimension
	 */

	public int featureSpaceDimension()
	{
		return _rdContinuousInput.dimension();
	}

	/**
	 * Compute the Kernel's R^d X R^d To R^1 Value
	 * 
	 * @param adblX Validated Vector Instance X
	 * @param adblY Validated Vector Instance Y
	 * 
	 * @return The Kernel's R^d X R^d To R^1 Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public abstract double evaluate (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception;
}
