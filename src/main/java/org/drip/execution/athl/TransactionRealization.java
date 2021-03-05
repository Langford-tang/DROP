
package org.drip.execution.athl;

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
 * <i>TransactionRealization</i> holds the Suite of Empirical Drift/Wander Signals that have been emitted off
 * of a Transaction Run using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), using the
 * Parameterization of Almgren (2003). The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/README.md">Almgren-Thum-Hauptmann-Li Calibration</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TransactionRealization {
	private double _dblT = java.lang.Double.NaN;
	private double _dblX = java.lang.Double.NaN;
	private double _dblTPost = java.lang.Double.NaN;
	private double _dblTSQRT = java.lang.Double.NaN;
	private double _dblVolatility = java.lang.Double.NaN;
	private org.drip.execution.impact.TransactionFunction _tfPermanent = null;
	private org.drip.execution.impact.TransactionFunction _tfTemporary = null;

	/**
	 * TransactionRealization Constructor
	 * 
	 * @param tfPermanent The Permanent Market Impact Transaction Function
	 * @param tfTemporary The Temporary Market Impact Transaction Function
	 * @param dblVolatility The Asset Daily Volatility
	 * @param dblX The Transaction Amount
	 * @param dblT The Transaction Completion Time in Days
	 * @param dblTPost The Transaction Completion Time in Days Adjusted for the Permanent Lag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TransactionRealization (
		final org.drip.execution.impact.TransactionFunction tfPermanent,
		final org.drip.execution.impact.TransactionFunction tfTemporary,
		final double dblVolatility,
		final double dblX,
		final double dblT,
		final double dblTPost)
		throws java.lang.Exception
	{
		if (null == (_tfPermanent = tfPermanent) || null == (_tfTemporary = tfTemporary) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblVolatility = dblVolatility) || 0. > _dblVolatility
				|| !org.drip.numerical.common.NumberUtil.IsValid (_dblX = dblX) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblT = dblT) || 0. > _dblT ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblTPost = dblTPost) || _dblT >=
							_dblTPost)
			throw new java.lang.Exception  ("TransactionRealization Constructor => Invalid Inputs");

		_dblTSQRT = java.lang.Math.sqrt (_dblT);
	}

	/**
	 * Retrieve the Permanent Market Impact Transaction Function
	 * 
	 * @return The Permanent Market Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction permanentMarketImpactFunction()
	{
		return _tfPermanent;
	}

	/**
	 * Retrieve the Temporary Market Impact Transaction Function
	 * 
	 * @return The Temporary Market Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction temporaryMarketImpactFunction()
	{
		return _tfTemporary;
	}

	/**
	 * Retrieve the Asset Daily Volatility
	 * 
	 * @return The Asset Daily Volatility
	 */

	public double volatility()
	{
		return _dblVolatility;
	}

	/**
	 * Retrieve the Transaction Amount X
	 * 
	 * @return The Transaction Amount X
	 */

	public double x()
	{
		return _dblX;
	}

	/**
	 * Retrieve the Transaction Completion Time T in Days
	 * 
	 * @return The Transaction Completion Time T in Days
	 */

	public double t()
	{
		return _dblT;
	}

	/**
	 * Retrieve the Transaction Completion Time in Days Adjusted for the Permanent Lag TPost
	 * 
	 * @return The Transaction Completion Time in Days Adjusted for the Permanent Lag TPost
	 */

	public double tPost()
	{
		return _dblTPost;
	}

	/**
	 * Emit the IJK Signal
	 * 
	 * @param dblIRandom The Random "I" Instance
	 * @param dblJRandom The Random "J" Instance
	 * 
	 * @return The IJK Signal Instance
	 */

	public org.drip.execution.athl.IJK emitSignal (
		final double dblIRandom,
		final double dblJRandom)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblIRandom) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblJRandom))
			return null;

		try {
			return new org.drip.execution.athl.IJK (new org.drip.execution.athl.TransactionSignal
				(_tfPermanent.evaluate (_dblX, _dblT), _dblVolatility * _dblTSQRT * dblIRandom, 0.), new
					org.drip.execution.athl.TransactionSignal (_tfTemporary.evaluate (_dblX, _dblT),
						_dblVolatility * java.lang.Math.sqrt (_dblT / 12. * (4. - (3. * _dblT / _dblTPost)))
							* dblJRandom, 0.5 * (_dblTPost - _dblT) / _dblTSQRT * dblIRandom));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
