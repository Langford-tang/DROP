
package org.drip.xva.netting;

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
 * <i>CreditDebtGroupPath</i> rolls up the Path Realizations of the Sequence in a Single Path Projection Run
 * over Multiple Collateral Hypothecation Groups onto a Single Credit/Debt Netting Group - the Purpose being
 * to calculate Credit Valuation Adjustments. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting/README.md">Credit/Debt/Funding Netting Groups</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditDebtGroupPath
{
	private org.drip.exposure.universe.MarketPath _marketPath = null;
	private org.drip.xva.netting.CollateralGroupPath[] _collateralGroupPathArray = null;

	protected CreditDebtGroupPath (
		final org.drip.xva.netting.CollateralGroupPath[] collateralGroupPathArray,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupPathArray = collateralGroupPathArray) ||
			null == (_marketPath = marketPath))
		{
			throw new java.lang.Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
		}

		int collateralGroupCount = _collateralGroupPathArray.length;

		if (0 == collateralGroupCount)
		{
			throw new java.lang.Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			if (null == _collateralGroupPathArray[collateralGroupIndex])
			{
				throw new java.lang.Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of the Position Hypothecation Group Trajectory Paths
	 * 
	 * @return Array of the Position Hypothecation Group Trajectory Paths
	 */

	public org.drip.xva.netting.CollateralGroupPath[] collateralGroupPaths()
	{
		return _collateralGroupPathArray;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.exposure.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] vertexDates()
	{
		return _collateralGroupPathArray[0].vertexDates();
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposures
	 * 
	 * @return The Array of Vertex Collateralized Exposures
	 */

	public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralizedExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexCollateralizedExposure[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupVertexCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposure[vertexIndex] +=
					collateralGroupVertexCollateralizedExposure[vertexIndex];
			}
		}

		return vertexCollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Exposure PV
	 */

	public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedExposurePV = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexCollateralizedExposurePV[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupVertexCollateralizedExposurePV =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposurePV[vertexIndex] +=
					collateralGroupVertexCollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures
	 */

	public double[] vertexCollateralizedPositiveExposure()
	{
		double[] vertexCollateralizedPositiveExposure = vertexCollateralizedExposure();

		int vertexCount = vertexCollateralizedPositiveExposure.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. > vertexCollateralizedPositiveExposure[vertexIndex])
				vertexCollateralizedPositiveExposure[vertexIndex] = 0.;
		}

		return vertexCollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures PV
	 */

	public double[] vertexCollateralizedPositiveExposurePV()
	{
		double[] vertexCollateralizedPositiveExposurePV = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedPositiveExposurePV.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. > vertexCollateralizedPositiveExposurePV[vertexIndex])
				vertexCollateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		return vertexCollateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposures
	 */

	public double[] vertexCollateralizedNegativeExposure()
	{
		double[] vertexCollateralizedNegativeExposure = vertexCollateralizedExposure();

		int vertexCount = vertexCollateralizedNegativeExposure.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. < vertexCollateralizedNegativeExposure[vertexIndex])
				vertexCollateralizedNegativeExposure[vertexIndex] = 0.;
		}

		return vertexCollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposure PV
	 */

	public double[] vertexCollateralizedNegativeExposurePV()
	{
		double[] vertexCollateralizedNegativeExposurePV = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedNegativeExposurePV.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. < vertexCollateralizedNegativeExposurePV[vertexIndex])
				vertexCollateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		return vertexCollateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Exposures
	 */

	public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexUncollateralizedExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexUncollateralizedExposure[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupVertexUncollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposure[vertexIndex] +=
					collateralGroupVertexUncollateralizedExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Exposure PV
	 */

	public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexUncollateralizedExposurePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexUncollateralizedExposurePV[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupVertexUncollateralizedExposurePV =
				_collateralGroupPathArray[collateralGroupIndex].vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposurePV[vertexIndex] +=
					collateralGroupVertexUncollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposures
	 */

	public double[] vertexUncollateralizedPositiveExposure()
	{
		double[] vertexUncollateralizedPositiveExposure = vertexUncollateralizedExposure();

		int vertexCount = vertexUncollateralizedPositiveExposure.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. > vertexUncollateralizedPositiveExposure[vertexIndex])
				vertexUncollateralizedPositiveExposure[vertexIndex] = 0.;
		}

		return vertexUncollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposure PV
	 */

	public double[] vertexUncollateralizedPositiveExposurePV()
	{
		double[] vertexUncollateralizedPositiveExposurePV = vertexUncollateralizedExposurePV();

		int vertexCount = vertexUncollateralizedPositiveExposurePV.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. > vertexUncollateralizedPositiveExposurePV[vertexIndex])
				vertexUncollateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		return vertexUncollateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposures
	 */

	public double[] vertexUncollateralizedNegativeExposure()
	{
		double[] vertexUncollateralizedNegativeExposure = vertexUncollateralizedExposure();

		int vertexCount = vertexUncollateralizedNegativeExposure.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. < vertexUncollateralizedNegativeExposure[vertexIndex])
				vertexUncollateralizedNegativeExposure[vertexIndex] = 0.;
		}

		return vertexUncollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposure PV
	 */

	public double[] vertexUncollateralizedNegativeExposurePV()
	{
		double[] vertexUncollateralizedNegativeExposurePV = vertexUncollateralizedExposurePV();

		int vertexCount = vertexUncollateralizedNegativeExposurePV.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (0. < vertexUncollateralizedNegativeExposurePV[vertexIndex])
				vertexUncollateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		return vertexUncollateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure
	 * 
	 * @return The Array of Vertex Credit Exposure
	 */

	public double[] vertexCreditExposure()
	{
		return vertexCollateralizedPositiveExposure();
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure PV
	 * 
	 * @return The Array of Vertex Credit Exposure PV
	 */

	public double[] vertexCreditExposurePV()
	{
		return vertexCollateralizedPositiveExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposure
	 * 
	 * @return The Array of Vertex Debt Exposure
	 */

	public double[] vertexDebtExposure()
	{
		return vertexCollateralizedNegativeExposure();
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposure PV
	 * 
	 * @return The Array of Vertex Debt Exposure PV
	 */

	public double[] vertexDebtExposurePV()
	{
		return vertexCollateralizedNegativeExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposure
	 * 
	 * @return The Array of Vertex Funding Exposure
	 */

	public double[] vertexFundingExposure()
	{
		return vertexCollateralizedExposure();
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposure PV
	 * 
	 * @return The Array of Vertex Funding Exposure PV
	 */

	public double[] vertexFundingExposurePV()
	{
		return vertexCollateralizedExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances
	 * 
	 * @return The Array of Vertex Collateral Balances
	 */

	public double[] vertexCollateralBalance()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralBalance = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexCollateralBalance[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralVertexGroupCollateralBalance =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralBalance();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralBalance[vertexIndex] += collateralVertexGroupCollateralBalance[vertexIndex];
			}
		}

		return vertexCollateralBalance;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances PV
	 * 
	 * @return The Array of Vertex Collateral Balances PV
	 */

	public double[] vertexCollateralBalancePV()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralBalancePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexCollateralBalancePV[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralVertexGroupCollateralBalancePV =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralBalancePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralBalancePV[vertexIndex] +=
					collateralVertexGroupCollateralBalancePV[vertexIndex];
			}
		}

		return vertexCollateralBalancePV;
	}

	/**
	 * Compute Period-wise Path Collateral Spread 01
	 * 
	 * @return The Period-wise Path Collateral Spread 01
	 */

	public double[] periodCollateralSpread01()
	{
		int vertexCount = vertexDates().length;

		int periodCount = vertexCount - 1;
		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] periodCollateralSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			periodCollateralSpread01[periodIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralPeriodCollateralSpread01 =
				_collateralGroupPathArray[collateralGroupIndex].periodCollateralSpread01();

			for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
			{
				periodCollateralSpread01[periodIndex] += collateralPeriodCollateralSpread01[periodIndex];
			}
		}

		return periodCollateralSpread01;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 */

	public double[] periodCollateralValueAdjustment()
	{
		int vertexCount = vertexDates().length;

		int periodCount = vertexCount - 1;
		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] periodCollateralValueAdjustment = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			periodCollateralValueAdjustment[periodIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralPeriodCollateralValueAdjustment =
				_collateralGroupPathArray[collateralGroupIndex].periodCollateralValueAdjustment();

			for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
			{
				periodCollateralValueAdjustment[periodIndex] +=
					collateralPeriodCollateralValueAdjustment[periodIndex];
			}
		}

		return periodCollateralValueAdjustment;
	}

	/**
	 * Compute Path Unilateral Credit Adjustment
	 * 
	 * @return The Path Unilateral Credit Adjustment
	 */

	public double unilateralCreditAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePV = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePV.length;
		double unilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexCreditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate());

			double periodIntegrandEnd = vertexCreditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate());

			unilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return unilateralCreditAdjustment;
	}

	/**
	 * Compute Path Bilateral Credit Adjustment
	 * 
	 * @return The Path Bilateral Credit Adjustment
	 */

	public double bilateralCreditAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePV = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePV.length;
		double bilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexCreditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexCreditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return bilateralCreditAdjustment;
	}

	/**
	 * Compute Path Contra-Liability Credit Adjustment
	 * 
	 * @return The Path Contra-Liability Credit Adjustment
	 */

	public double contraLiabilityCreditAdjustment()
	{
		return bilateralCreditAdjustment() - unilateralCreditAdjustment();
	}

	/**
	 * Compute Path Unilateral Debt Adjustment
	 * 
	 * @return The Path Unilateral Debt Adjustment
	 */

	public double unilateralDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double unilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Debt Adjustment
	 * 
	 * @return The Path Bilateral Debt Adjustment
	 */

	public double bilateralDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double bilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			bilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralDebtAdjustment;
	}

	/**
	 * Compute Path Contra-Asset Debt Adjustment
	 * 
	 * @return The Path Contra-Asset Debt Adjustment
	 */

	public double contraAssetDebtAdjustment()
	{
		return bilateralDebtAdjustment() - unilateralDebtAdjustment();
	}

	/**
	 * Compute Path Symmetric Funding Value Spread 01
	 * 
	 * @return The Path Symmetric Funding Value Spread 01
	 */

	public double symmetricFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCollateralizedExposurePV = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedExposurePV.length;
		double symmetricFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			symmetricFundingValueSpread01 = 0.5 * (
				vertexCollateralizedExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability() +
				vertexCollateralizedExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].dealer().survivalProbability()
			) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return symmetricFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Value Spread 01
	 * 
	 * @return The Path Unilateral Funding Value Spread 01
	 */

	public double unilateralFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePV = vertexFundingExposurePV();

		int vertexCount = vertexFundingExposurePV.length;
		double unilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexFundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability();

			unilateralFundingValueSpread01 = 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return unilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Bilateral Funding Value Spread 01
	 * 
	 * @return The Path Bilateral Funding Value Spread 01
	 */

	public double bilateralFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePV = vertexFundingExposurePV();

		int vertexCount = vertexFundingExposurePV.length;
		double bilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexFundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralFundingValueSpread01 = 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return bilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Path Unilateral Funding Debt Adjustment
	 */

	public double unilateralFundingDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double unilateralFundingDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralFundingDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Funding Debt Adjustment
	 * 
	 * @return The Path Bilateral Funding Debt Adjustment
	 */

	public double bilateralFundingDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double bilateralFundingDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			bilateralFundingDebtAdjustment += 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Path Unilateral Collateral Value Adjustment
	 */

	public double unilateralCollateralAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] periodCollateralValueAdjustment = periodCollateralValueAdjustment();

		double unilateralCollateralValueAdjustment = 0.;
		int periodCount = periodCollateralValueAdjustment.length;

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			unilateralCollateralValueAdjustment += 0.5 * periodCollateralValueAdjustment[periodIndex] * (
				marketVertexArray[periodIndex].client().survivalProbability() +
				marketVertexArray[periodIndex + 1].client().survivalProbability()
			);
		}

		return unilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Path Bilateral Collateral Value Adjustment
	 */

	public double bilateralCollateralAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] periodCollateralValueAdjustment = periodCollateralValueAdjustment();

		double bilateralCollateralValueAdjustment = 0.;
		int periodCount = periodCollateralValueAdjustment.length;

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			bilateralCollateralValueAdjustment += 0.5 * periodCollateralValueAdjustment[periodIndex] * (
				marketVertexArray[periodIndex].dealer().survivalProbability() *
				marketVertexArray[periodIndex].client().survivalProbability() +
				marketVertexArray[periodIndex + 1].dealer().survivalProbability() *
				marketVertexArray[periodIndex + 1].client().survivalProbability()
			);
		}

		return bilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Collateral Value Adjustment
	 * 
	 * @return The Path Collateral Value Adjustment
	 */

	public double collateralValueAdjustment()
	{
		return unilateralCollateralAdjustment();
	}

	/**
	 * Compute Period-wise Symmetric Funding Value Spread 01
	 * 
	 * @return The Period-wise Symmetric Funding Value Spread 01
	 */

	public double[] periodSymmetricFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCollateralizedExposurePV = vertexCollateralizedExposurePV();

		int periodCount = vertexCollateralizedExposurePV.length - 1;
		double[] periodSymmetricFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			periodSymmetricFundingValueSpread01[periodIndex] = -0.5 * (
				vertexCollateralizedExposurePV[periodIndex] +
				vertexCollateralizedExposurePV[periodIndex + 1]
			) * (
				marketVertexArray[periodIndex + 1].anchorDate().julian() -
				marketVertexArray[periodIndex].anchorDate().julian()
			) / 365.25;
		}

		return periodSymmetricFundingValueSpread01;
	}

	/**
	 * Compute Period-wise Unilateral Credit Adjustment
	 * 
	 * @return The Period-wise Unilateral Credit Adjustment
	 */

	public double[] periodUnilateralCreditAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePV = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePV.length;
		double[] periodUnilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexCreditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate());

			double periodIntegrandEnd = vertexCreditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate());

			periodUnilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
				marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return periodUnilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Credit Adjustment
	 * 
	 * @return The Period-wise Bilateral Credit Adjustment
	 */

	public double[] periodBilateralCreditAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePV = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePV.length;
		double[] periodBilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexCreditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexCreditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			periodBilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
				marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return periodBilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Contra-Liability Credit Adjustment
	 * 
	 * @return The Period-wise Contra-Liability Credit Adjustment
	 */

	public double[] periodContraLiabilityCreditAdjustment()
	{
		double[] periodUnilateralCreditAdjustment = periodUnilateralCreditAdjustment();

		double[] periodBilateralCreditAdjustment = periodBilateralCreditAdjustment();

		int vertexCount = periodUnilateralCreditAdjustment.length;
		double[] periodContraLiabilityCreditAdjustment = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodContraLiabilityCreditAdjustment[vertexIndex] =
				periodUnilateralCreditAdjustment[vertexIndex] -
				periodBilateralCreditAdjustment[vertexIndex];
		}

		return periodContraLiabilityCreditAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Debt Adjustment
	 * 
	 * @return The Period-wise Unilateral Debt Adjustment
	 */

	public double[] periodUnilateralDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double[] periodUnilateralDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralDebtAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodUnilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Debt Adjustment
	 * 
	 * @return The Period-wise Bilateral Debt Adjustment
	 */

	public double[] periodBilateralDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double[] periodBilateralDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralDebtAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralDebtAdjustment;
	}

	/**
	 * Compute Period Unilateral Funding Value Spread 01
	 * 
	 * @return The Period Unilateral Funding Value Spread 01
	 */

	public double[] periodUnilateralFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePV = vertexFundingExposurePV();

		int periodCount = vertexFundingExposurePV.length - 1;
		double[] periodUnilateralFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			double periodIntegrandStart = vertexFundingExposurePV[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePV[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability();

			periodUnilateralFundingValueSpread01[periodIndex] =
				0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodUnilateralFundingValueSpread01;
	}

	/**
	 * Compute Period Bilateral Funding Value Spread 01
	 * 
	 * @return The Period Bilateral Funding Value Spread 01
	 */

	public double[] periodBilateralFundingValueSpread01()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePV = vertexFundingExposurePV();

		int periodCount = vertexFundingExposurePV.length - 1;
		double[] periodBilateralFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			double periodIntegrandStart = vertexFundingExposurePV[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability() *
				marketVertexArray[periodIndex].dealer().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePV[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability() *
				marketVertexArray[periodIndex + 1].dealer().survivalProbability();

			periodBilateralFundingValueSpread01[periodIndex] =
				0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodBilateralFundingValueSpread01;
	}

	/**
	 * Compute Period Unilateral Funding Debt Adjustment
	 * 
	 * @return The Period Unilateral Funding Debt Adjustment
	 */

	public double[] periodUnilateralFundingDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double[] periodUnilateralFundingDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralFundingDebtAdjustment[vertexIndex - 1] = -0.5 *
				(periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodUnilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Period Bilateral Funding Debt Adjustment
	 * 
	 * @return The Period Bilateral Funding Debt Adjustment
	 */

	public double[] periodBilateralFundingDebtAdjustment()
	{
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePV = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePV.length;
		double[] periodBilateralFundingDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = vertexDebtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralFundingDebtAdjustment[vertexIndex - 1] = -0.5 *
				(periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Credit Adjustment
	 * 
	 * @return The Path Credit Adjustment
	 */

	public abstract double creditAdjustment();

	/**
	 * Compute Path Debt Adjustment
	 * 
	 * @return The Path Debt Adjustment
	 */

	public abstract double debtAdjustment();

	/**
	 * Compute Period-wise Credit Adjustment
	 * 
	 * @return The Period-wise Credit Adjustment
	 */

	public abstract double[] periodCreditAdjustment();

	/**
	 * Compute Period-wise Debt Adjustment
	 * 
	 * @return The Period-wise Debt Adjustment
	 */

	public abstract double[] periodDebtAdjustment();
}
