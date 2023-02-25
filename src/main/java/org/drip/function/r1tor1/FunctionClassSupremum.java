
package org.drip.function.r1tor1;

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
 * <i>FunctionClassSupremum</i> implements the Univariate Function that corresponds to the Supremum among the
 * specified Class of Functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FunctionClassSupremum extends org.drip.function.definition.R1ToR1 {
	class IndexSupremum {
		int _iIndex = -1;
		double _dblSupremum = java.lang.Double.NaN;

		IndexSupremum (
			final int iIndex,
			final double dblSupremum)
		{
			_iIndex = iIndex;
			_dblSupremum = dblSupremum;
		}
	};

	private org.drip.function.definition.R1ToR1[] _aAUClass = null;

	private IndexSupremum supremum (
		final double dblVariate)
	{
		int iIndex = 0;
		int iNumFunction = _aAUClass.length;

		try {
			double dblSupremum = _aAUClass[0].evaluate (dblVariate);

			for (int i = 1; i < iNumFunction; ++i) {
				double dblValue = _aAUClass[i].evaluate (dblVariate);

				if (dblValue > dblSupremum) {
					iIndex = i;
					dblSupremum = dblValue;
				}
			}

			return new IndexSupremum (iIndex, dblSupremum);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FunctionClassSupremum Cnstructor
	 * 
	 * @param aAUClass Array of Functions in the Class
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FunctionClassSupremum (
		final org.drip.function.definition.R1ToR1[] aAUClass)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_aAUClass = aAUClass) || 0 == _aAUClass.length)
			throw new java.lang.Exception ("FunctionClassSupremum ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Class of Functions
	 * 
	 * @return The Class of Functions
	 */

	public org.drip.function.definition.R1ToR1[] functionClass()
	{
		return _aAUClass;
	}

	/**
	 * Retrieve the Supremum Function corresponding to the specified Variate
	 * 
	 * @param dblVariate The Variate
	 * 
	 * @return The Supremum Function corresponding to the specified Variate
	 */

	public org.drip.function.definition.R1ToR1 supremumFunction (
		final double dblVariate)
	{
		IndexSupremum is = supremum (dblVariate);

		return null == is ? null : _aAUClass[is._iIndex];
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		IndexSupremum is = supremum (dblVariate);

		if (null == is) throw new java.lang.Exception ("FunctionClassSupremum::evaluate => Invalid Inputs");

		return is._dblSupremum;
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		IndexSupremum is = supremum (dblVariate);

		if (null == is)
			throw new java.lang.Exception ("FunctionClassSupremum::derivative => Invalid Inputs");

		return _aAUClass[is._iIndex].derivative (dblVariate, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		IndexSupremum is = supremum (0.5 * (dblBegin + dblEnd));

		if (null == is) throw new java.lang.Exception ("FunctionClassSupremum::integrate => Invalid Inputs");

		return _aAUClass[is._iIndex].integrate (dblBegin, dblEnd);
	}
}
