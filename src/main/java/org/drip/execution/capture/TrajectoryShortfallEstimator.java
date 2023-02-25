
package org.drip.execution.capture;

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
 * <i>TrajectoryShortfallEstimator</i> estimates the Price/Short Fall Distribution associated with the
 * Trading Trajectory generated using the specified Evolution Parameters. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/README.md">Execution Trajectory Transaction Cost Capture</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TrajectoryShortfallEstimator implements
	org.drip.execution.sensitivity.ControlNodesGreekGenerator {
	private org.drip.execution.strategy.DiscreteTradingTrajectory _tt = null;

	/**
	 * TrajectoryShortfallEstimator Constructor
	 * 
	 * @param tt The Trading Trajectory Instance
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrajectoryShortfallEstimator (
		final org.drip.execution.strategy.DiscreteTradingTrajectory tt)
		throws java.lang.Exception
	{
		if (null == (_tt = tt))
			throw new java.lang.Exception ("TrajectoryShortfallEstimator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Underlying Trading Trajectory Instance
	 * 
	 * @return The Underlying Trading Trajectory Instance
	 */

	public org.drip.execution.strategy.DiscreteTradingTrajectory trajectory()
	{
		return _tt;
	}

	/**
	 * Generate the Detailed Cost Realization Sequence given the Specified Inputs
	 * 
	 * @param dblStartingEquilibriumPrice The Starting Equilibrium Price
	 * @param aWS Array of the Realized Walk Random Variable Suite
	 * @param apep The Price Evolution Parameters
	 * 
	 * @return The Detailed Cost Realization Sequence given the Specified Inputs
	 */

	public org.drip.execution.capture.TrajectoryShortfallRealization totalCostRealizationDetail (
		final double dblStartingEquilibriumPrice,
		final org.drip.execution.dynamics.WalkSuite[] aWS,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblStartingEquilibriumPrice) || null == aWS)
			return null;

		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double[] adblHoldings = _tt.holdings();

		int iNumTimeNode = adblExecutionTimeNode.length;
		double dblPreviousEquilibriumPrice = dblStartingEquilibriumPrice;

		if (aWS.length + 1 != iNumTimeNode) return null;

		java.util.List<org.drip.execution.discrete.ShortfallIncrement> lsSI = new
			java.util.ArrayList<org.drip.execution.discrete.ShortfallIncrement>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.ShortfallIncrement si = null;

			try {
				si = ( new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1])).costIncrementRealization
						(dblPreviousEquilibriumPrice, aWS[i - 1], apep);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == si) return null;

			lsSI.add (si);

			dblPreviousEquilibriumPrice = si.compositePriceIncrement().newEquilibriumPrice();
		}

		try {
			return new org.drip.execution.capture.TrajectoryShortfallRealization (lsSI);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Detailed Total Cost Distribution for the Trading Trajectory
	 * 
	 * @param apep The Price Evolution Parameters
	 * 
	 * @return The Detailed Total Cost Distribution for the Trading Trajectory
	 */

	public org.drip.execution.capture.TrajectoryShortfallAggregate totalCostDistributionDetail (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double[] adblHoldings = _tt.holdings();

		int iNumTimeNode = adblExecutionTimeNode.length;

		java.util.List<org.drip.execution.discrete.ShortfallIncrementDistribution> lsSID = new
			java.util.ArrayList<org.drip.execution.discrete.ShortfallIncrementDistribution>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			lsSID.add (s.costIncrementDistribution (apep));
		}

		try {
			return new org.drip.execution.capture.TrajectoryShortfallAggregate (lsSID);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Total Cost Distribution Synopsis Distribution for the Trading Trajectory
	 * 
	 * @param apep Arithmetic Price Evolution Parameters Instance
	 * 
	 * @return The Total Cost Distribution Synopsis Distribution for the Trading Trajectory
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal totalCostDistributionSynopsis (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		org.drip.execution.capture.TrajectoryShortfallAggregate tsa = totalCostDistributionDetail (apep);

		return null == tsa ? null : tsa.totalCostDistribution();
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.permanentImpactExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.permanentImpactVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.temporaryImpactExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.temporaryImpactVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.marketDynamicsExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.marketDynamicsVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek expectationContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.expectationContribution (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek varianceContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.varianceContribution (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Estimate the Optimal Adjustment Attributable to the Serial Correlation
	 *  
	 * @param apep The Arithmetic Price Walk Parameters
	 * 
	 * @return The Optimal Adjustment Attributable to the Serial Correlation
	 */

	public org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[] serialCorrelationAdjustment (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		int iNumTimeNode = adblExecutionTimeNode.length;
		org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[] aOSCA = new
			org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		try {
			aOSCA[0] = new org.drip.execution.discrete.OptimalSerialCorrelationAdjustment (0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == (aOSCA[i] = s.serialCorrelationAdjustment (apep))) return null;
		}

		return aOSCA;
	}
}
