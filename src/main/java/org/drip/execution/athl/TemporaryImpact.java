
package org.drip.execution.athl;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>TemporaryImpact</i> implements the Temporary Market Impact with Exponent/Coefficients that have been
 * determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * (2003). The References are:
 * 
 * <br><br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl">ATHL</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TemporaryImpact extends org.drip.execution.impact.TransactionFunctionPower {
	private org.drip.execution.parameters.AssetFlowSettings _afp = null;

	/**
	 * TemporaryImpact Constructor
	 * 
	 * @param afp The Asset Flow Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TemporaryImpact (
		final org.drip.execution.parameters.AssetFlowSettings afp)
		throws java.lang.Exception
	{
		if (null == (_afp = afp))
			throw new java.lang.Exception ("TemporaryImpact Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset Flow Parameters
	 * 
	 * @return The Asset Flow Parameters
	 */

	public org.drip.execution.parameters.AssetFlowSettings assetFlowParameters()
	{
		return _afp;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("TemporaryImpact::regularize => Invalid Inputs");

		return 1. / (_afp.averageDailyVolume() * dblTradeInterval);
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		return _afp.dailyVolatility();
	}

	@Override public double constant()
	{
		return org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_COEFFICIENT;
	}

	@Override public double exponent()
	{
		return org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_EXPONENT;
	}

	@Override public double evaluate (
		final double dblNormalizedX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("TemporaryImpact::evaluate => Invalid Inputs");

		double dblBeta = org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_EXPONENT;
		double dblEta = org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_COEFFICIENT;

		return dblEta * (dblNormalizedX < 0. ? -1. : 1.) * java.lang.Math.pow (java.lang.Math.abs
			(dblNormalizedX), dblBeta);
	}

	@Override public double derivative  (
		final double dblNormalizedX,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder || !org.drip.quant.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("TemporaryImpact::derivative => Invalid Inputs");

		double dblCoefficient = 1.;
		double dblBeta = org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_EXPONENT;
		double dblEta = org.drip.execution.athl.CalibrationEmpirics.TEMPORARY_IMPACT_COEFFICIENT;

		for (int i = 0; i < iOrder; ++i)
			dblCoefficient = dblCoefficient * (dblBeta - i);

		return dblEta * (dblNormalizedX < 0. ? -1. : 1.) * dblCoefficient * java.lang.Math.pow
			(java.lang.Math.abs (dblNormalizedX), dblBeta - iOrder);
	}
}
