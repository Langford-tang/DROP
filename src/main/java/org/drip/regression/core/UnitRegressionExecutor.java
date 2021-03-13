
package org.drip.regression.core;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>UnitRegressionExecutor</i> implements the UnitRegressor, and splits the regression execution into pre-,
 * execute, and post-regression. It provides default implementations for pre-regression and post-regression.
 * Most typical regressors only need to over-ride the execRegression method.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md">Regression Engine Core and the Unit Regressors</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/README.md">Regression Engine Core - Unit Regressors</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class UnitRegressionExecutor implements org.drip.regression.core.UnitRegressor {
	private static final boolean _bDisplayStatus = false;

	private java.lang.String _strRegressorSet = "";
	private java.lang.String _strRegressorName = "";

	/**
	 * Constructor for the unit regression executor
	 * 
	 * @param strRegressorName Name of the unit regressor
	 * @param strRegressorSet Name of the regressor set
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	protected UnitRegressionExecutor (
		final java.lang.String strRegressorName,
		final java.lang.String strRegressorSet)
		throws java.lang.Exception
	{
		if (null == (_strRegressorName = strRegressorName) || strRegressorName.isEmpty() || null ==
			(_strRegressorSet = strRegressorSet) || _strRegressorSet.isEmpty())
			throw new java.lang.Exception ("UnitRegressionExecutor ctr: Invalid inputs");
	}

	/**
	 * One-time initialization to set up the objects needed for the regression
	 * 
	 * @return TRUE - Initialization successful
	 */

	public boolean preRegression()
	{
		return true;
	}

	/**
	 * Execute the regression call within this function
	 * 
	 * @return The result of the regression
	 */

	public abstract boolean execRegression();

	/**
	 * Clean-up of the objects set-up for the regression
	 * 
	 * @param rnvd Regression Run Detail object to capture the regression details
	 * 
	 * @return TRUE - Clean-up successful
	 */

	public boolean postRegression (
		final org.drip.regression.core.RegressionRunDetail rnvd)
	{
		return true;
	}

	@Override public org.drip.regression.core.RegressionRunOutput regress()
	{
		org.drip.regression.core.RegressionRunOutput ro = null;

		try {
			ro = new org.drip.regression.core.RegressionRunOutput (_strRegressorSet + "." +
				_strRegressorName);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!preRegression()) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName +
					": Cannot set-up the regressor!");

			return null;
		}

		long lStartTime = System.nanoTime();

		if (!execRegression()) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName + ": failed");

			ro.setTerminationStatus (false);

			return ro;
		}

		ro._lExecTime = (long) (1.e-03 * (System.nanoTime() - lStartTime));

		if (!postRegression (ro.getRegressionDetail())) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName +
					": Regressor clean-up unsuccessful!");

			return null;
		}

		if (_bDisplayStatus) System.out.println (_strRegressorSet + "." + _strRegressorName + ": succeeded");

		ro.setTerminationStatus (true);

		return ro;
	}

	@Override public java.lang.String getName()
	{
		return _strRegressorName;
	}
}
