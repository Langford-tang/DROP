
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
 * <i>DiagonalScalingOperator</i> implements the Scaling Operator that is used to determine the Bounds of the
 * R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined by:
 * 
 * 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 * 		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i>
 * 			<b>Cambridge University Press</b> Cambridge UK
 * 	</li>
 * 	<li>
 *  	Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 *  		Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237
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

public abstract class DiagonalScalingOperator implements org.drip.spaces.cover.OperatorClassCoveringBounds {
	private double[] _adblDiagonalScaler = null;
	private double _dblScalingProductSupremumBound = java.lang.Double.NaN;

	/**
	 * DiagonalScalingOperator Constructor
	 * 
	 * @param adblDiagonalScaler The Diagonal Scaling Multiplier Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiagonalScalingOperator (
		final double[] adblDiagonalScaler)
		throws java.lang.Exception
	{
		if (null == (_adblDiagonalScaler = adblDiagonalScaler))
			throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

		double dblScalingProduct = 1.;
		int iScalingSize = _adblDiagonalScaler.length;

		if (0 == iScalingSize)
			throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

		for (int i = 0; i < iScalingSize; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblDiagonalScaler[i]) || 0. >
				_adblDiagonalScaler[i])
				throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

			if (0 == i) _dblScalingProductSupremumBound = _adblDiagonalScaler[i];

			if (i > 0) {
				if (_adblDiagonalScaler[i - 1] < _adblDiagonalScaler[i])
					throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

				double dblCurrentSupremumBound = java.lang.Math.pow ((dblScalingProduct *=
					_adblDiagonalScaler[i]) / iScalingSize, 1. / i);

				if (_dblScalingProductSupremumBound < dblCurrentSupremumBound)
					_dblScalingProductSupremumBound = dblCurrentSupremumBound;
			}
		}
	}

	/**
	 * Retrieve the Diagonal Scaling Multiplier Array
	 * 
	 * @return The Diagonal Scaling Multiplier Array
	 */

	public double[] scaler()
	{
		return _adblDiagonalScaler;
	}

	@Override public int entropyNumberIndex()
	{
		return _adblDiagonalScaler.length;
	}

	@Override public double entropyNumberLowerBound()
	{
		return _dblScalingProductSupremumBound;
	}

	@Override public double entropyNumberUpperBound()
	{
		return 6. * _dblScalingProductSupremumBound;
	}
}
