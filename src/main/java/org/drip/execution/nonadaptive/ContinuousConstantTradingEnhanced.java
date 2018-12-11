
package org.drip.execution.nonadaptive;

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
 * <i>ContinuousConstantTradingEnhanced</i> contains the Constant Volatility Trading Trajectory generated by
 * the Almgren and Chriss (2003) Scheme under the Criterion of No-Drift AND Constant Temporary Impact
 * Volatility. The References are:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/nonadaptive">Non Adaptive</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ContinuousConstantTradingEnhanced extends
	org.drip.execution.nonadaptive.StaticOptimalSchemeContinuous {

	/**
	 * Create the Standard ContinuousConstantTradingEnhanced Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param apep Almgren 2003 Arithmetic Price Evolution Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The ContinuousConstantTradingEnhanced Instance
	 */

	public static final ContinuousConstantTradingEnhanced Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final double dblRiskAversion)
	{
		try {
			return new ContinuousConstantTradingEnhanced (new org.drip.execution.strategy.OrderSpecification
				(dblStartHoldings, dblFinishTime), apep, new
					org.drip.execution.risk.MeanVarianceObjectiveUtility (dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ContinuousConstantTradingEnhanced (
		final org.drip.execution.strategy.OrderSpecification os,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou)
		throws java.lang.Exception
	{
		super (os, apep, mvou);
	}

	@Override public org.drip.execution.optimum.EfficientTradingTrajectory generate()
	{
		org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep = priceEvolutionParameters();

		double dblLambda = ((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion();

		double dblEpochVolatility = java.lang.Double.NaN;

		try {
			dblEpochVolatility = apep.arithmeticPriceDynamicsSettings().epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		final double dblSigma = dblEpochVolatility;

		org.drip.execution.strategy.OrderSpecification os = orderSpecification();

		org.drip.execution.impact.TransactionFunction tfTemporaryExpectation =
			apep.temporaryExpectation().epochImpactFunction();

		if (!(tfTemporaryExpectation instanceof org.drip.execution.impact.TransactionFunctionLinear))
			return null;

		final double dblEta = ((org.drip.execution.impact.TransactionFunctionLinear)
			tfTemporaryExpectation).slope();

		org.drip.execution.impact.TransactionFunction tfTemporaryVolatility =
			apep.temporaryVolatility().epochImpactFunction();

		if (!(tfTemporaryVolatility instanceof org.drip.execution.impact.TransactionFunctionLinear))
			return null;

		double dblAlpha = ((org.drip.execution.impact.TransactionFunctionLinear)
			tfTemporaryVolatility).offset();

		final double dblExecutionTime = os.maxExecutionTime();

		final double dblX = os.size();

		final double dblTStar = java.lang.Math.sqrt ((dblEta + dblLambda * dblAlpha * dblAlpha) / (dblLambda
			* dblSigma * dblSigma));

		double dblE = 0.5 * dblEta * dblX * dblX / dblTStar;

		double dblV = 0.5 * dblX * dblX * dblSigma * dblSigma * dblTStar * (1. + (dblAlpha * dblAlpha /
			(dblSigma * dblSigma * dblTStar * dblTStar)));

		final org.drip.function.definition.R1ToR1 r1ToR1Holdings = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblT)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblT))
					throw new java.lang.Exception
						("ContinuousConstantTradingEnhanced::generate::evaluate => Invalid Inputs");

				return dblX * java.lang.Math.pow (java.lang.Math.E, -1. * dblT/ dblTStar);
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TradeRate = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblVariate)
				throws java.lang.Exception
			{
				return r1ToR1Holdings.derivative (dblVariate, 1);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectationRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblTradeRate = r1ToR1Holdings.derivative (dblTime, 1);

				return dblEta * dblEta * dblTradeRate * dblTradeRate;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostExpectationRate.integrate (dblTime, dblExecutionTime);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVarianceRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				return dblSigma * dblSigma * dblHoldings * dblHoldings;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostVarianceRate.integrate (dblTime, dblExecutionTime);
			}
		};

		try {
			return new org.drip.execution.optimum.EfficientTradingTrajectoryContinuous (dblExecutionTime,
				dblE, dblV, dblTStar, apep.temporaryExpectation().epochImpactFunction().evaluate (dblX /
					dblExecutionTime) / (apep.arithmeticPriceDynamicsSettings().epochVolatility() *
						java.lang.Math.sqrt (dblExecutionTime)), r1ToR1Holdings, r1ToR1TradeRate,
							r1ToR1TransactionCostExpectation, r1ToR1TransactionCostVariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
