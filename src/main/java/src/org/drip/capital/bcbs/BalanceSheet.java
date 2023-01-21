
package org.drip.capital.bcbs;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BalanceSheet</i> holds the Quantities used to compute the Capital/Liquidity Ratios in the BCBS
 * Standards. The References are:
 *  
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Basel Committee on Banking Supervision (2017): Basel III Leverage Ratio Framework and Disclosure
 * 				Requirements https://www.bis.org/publ/bcbs270.pdf
 * 		</li>
 * 		<li>
 * 			Central Banking (2013): Fed and FDIC agree 6% Leverage Ratio for US SIFIs
 * 				https://www.centralbanking.com/central-banking/news/2280726/fed-and-fdic-agree-6-leverage-ratio-for-us-sifis
 * 		</li>
 * 		<li>
 * 			European Banking Agency (2013): Implementing Basel III in Europe: CRD IV Package
 * 				https://eba.europa.eu/regulation-and-policy/implementing-basel-iii-europe
 * 		</li>
 * 		<li>
 * 			Federal Reserve (2013): Liquidity Coverage Ratio � Liquidity Risk Measurements, Standards, and
 * 				Monitoring
 * 				https://web.archive.org/web/20131102074614/http:/www.federalreserve.gov/FR_notice_lcr_20131024.pdf
 * 		</li>
 * 		<li>
 * 			Wikipedia (2018): Basel III https://en.wikipedia.org/wiki/Basel_III
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/README.md">BCBS and Jurisdictional Capital Ratios</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BalanceSheet
{
	private org.drip.capital.bcbs.BalanceSheetCapital _balanceSheetCapital = null;
	private org.drip.capital.bcbs.BalanceSheetFunding _balanceSheetFunding = null;
	private org.drip.capital.bcbs.BalanceSheetLiquidity _balanceSheetLiquidity = null;

	/**
	 * BalanceSheet Constructor
	 * 
	 * @param balanceSheetCapital Balance Sheet Capital Composite
	 * @param balanceSheetLiquidity Balance Sheet Liquidity Composite
	 * @param balanceSheetFunding Balance Sheet Funding Composite
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheet (
		final org.drip.capital.bcbs.BalanceSheetCapital balanceSheetCapital,
		final org.drip.capital.bcbs.BalanceSheetLiquidity balanceSheetLiquidity,
		final org.drip.capital.bcbs.BalanceSheetFunding balanceSheetFunding)
		throws java.lang.Exception
	{
		if (null == (_balanceSheetCapital = balanceSheetCapital) ||
			null == (_balanceSheetLiquidity = balanceSheetLiquidity) ||
			null == (_balanceSheetFunding = balanceSheetFunding))
		{
			throw new java.lang.Exception ("BalanceSheet Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Balance Sheet Capital Composite
	 * 
	 * @return The Balance Sheet Capital Composite
	 */

	public org.drip.capital.bcbs.BalanceSheetCapital balanceSheetCapital()
	{
		return _balanceSheetCapital;
	}

	/**
	 * Retrieve the Balance Sheet Liquidity Composite
	 * 
	 * @return The Balance Sheet Liquidity Composite
	 */

	public org.drip.capital.bcbs.BalanceSheetLiquidity balanceSheetLiquidity()
	{
		return _balanceSheetLiquidity;
	}

	/**
	 * Retrieve the Balance Sheet Funding Composite
	 * 
	 * @return The Balance Sheet Funding Composite
	 */

	public org.drip.capital.bcbs.BalanceSheetFunding balanceSheetFunding()
	{
		return _balanceSheetFunding;
	}

	/**
	 * Retrieve the Tier 1 Capital
	 * 
	 * @return The Tier 1 Capital
	 */

	public double tier1()
	{
		return _balanceSheetCapital.tier1();
	}

	/**
	 * Retrieve the Total Capital
	 * 
	 * @return The Total Capital
	 */

	public double totalCapital()
	{
		return _balanceSheetCapital.totalCapital();
	}

	/**
	 * Retrieve the CET 1 Ratio
	 * 
	 * @return The CET 1 Ratio
	 */

	public double cet1Ratio()
	{
		return _balanceSheetCapital.cet1Ratio();
	}

	/**
	 * Retrieve the Tier 1 Ratio
	 * 
	 * @return The Tier 1 Ratio
	 */

	public double tier1Ratio()
	{
		return _balanceSheetCapital.tier1Ratio();
	}

	/**
	 * Retrieve the Total Capital Ratio
	 * 
	 * @return The Total Capital Ratio
	 */

	public double totalCapitalRatio()
	{
		return _balanceSheetCapital.totalCapitalRatio();
	}

	/**
	 * Retrieve the Leverage Ratio
	 * 
	 * @return The Leverage Ratio
	 */

	public double leverageRatio()
	{
		return _balanceSheetCapital.leverageRatio();
	}

	/**
	 * Retrieve the Liquidity Coverage Ratio
	 *  
	 * @param hqlaSettings THe HQLA Settings
	 * 
	 * @return The Liquidity Coverage Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liquidityCoverageRatio (
		final org.drip.capital.bcbs.HighQualityLiquidAssetSettings hqlaSettings)
		throws java.lang.Exception
	{
		return _balanceSheetLiquidity.liquidityCoverageRatio (hqlaSettings);
	}

	/**
	 * Retrieve the Net Stable Funding Ratio
	 * 
	 * @return The Net Stable Funding Ratio
	 */

	public double netStableFundingRatio()
	{
		return _balanceSheetFunding.netStableFundingRatio();
	}

	/**
	 * Generate the Balance Sheet Capital Metrics
	 * 
	 * @return The Balance Sheet Capital Metrics
	 */

	public org.drip.capital.bcbs.CapitalMetrics capitalMetrics()
	{
		return _balanceSheetCapital.capitalMetrics();
	}

	/**
	 * Generate the Balance Sheet Liquidity Metrics
	 *  
	 * @param hqlaSettings The HQLA Settings
	 * 
	 * @return The Balance Sheet Liquidity Metrics
	 */

	public org.drip.capital.bcbs.LiquidityMetrics liquidityMetrics (
		final org.drip.capital.bcbs.HighQualityLiquidAssetSettings hqlaSettings)
	{
		try
		{
			return new org.drip.capital.bcbs.LiquidityMetrics (
				_balanceSheetLiquidity.liquidityCoverageRatio (hqlaSettings),
				_balanceSheetFunding.netStableFundingRatio()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
