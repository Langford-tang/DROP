
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
 * <i>BalanceSheetCapital</i> holds the Quantities used to compute the Capital Compliance Ratios in the BCBS
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

public class BalanceSheetCapital
{
	private double _at1 = java.lang.Double.NaN;
	private double _rwa = java.lang.Double.NaN;
	private double _cet1 = java.lang.Double.NaN;
	private double _totalExposure = java.lang.Double.NaN;
	private double _additionalCapital = java.lang.Double.NaN;

	/**
	 * BalanceSheetCapital Constructor
	 * 
	 * @param cet1 Common Equity Tier 1 Capital
	 * @param at1 Additional Tier 1 Capital
	 * @param additionalCapital Additional Capital
	 * @param rwa Risk Weighted Assets
	 * @param totalExposure Total Exposure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheetCapital (
		final double cet1,
		final double at1,
		final double additionalCapital,
		final double rwa,
		final double totalExposure)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_cet1 = cet1) || 0. > _cet1 ||
			!org.drip.numerical.common.NumberUtil.IsValid (_at1 = at1) || 0. > _at1 ||
			!org.drip.numerical.common.NumberUtil.IsValid (_additionalCapital = additionalCapital) ||
				0. > _additionalCapital ||
			!org.drip.numerical.common.NumberUtil.IsValid (_rwa = rwa) || 0. > _rwa ||
			!org.drip.numerical.common.NumberUtil.IsValid (_totalExposure = totalExposure) ||
				0. > _totalExposure)
		{
			throw new java.lang.Exception ("BalanceSheetCapital Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Common Equity Tier 1 Capital
	 * 
	 * @return The Common Equity Tier 1 Capital
	 */

	public double commonEquityTier1()
	{
		return _cet1;
	}

	/**
	 * Retrieve the Additional Tier 1 Capital
	 * 
	 * @return The Additional Tier 1 Capital
	 */

	public double additionalTier1()
	{
		return _at1;
	}

	/**
	 * Retrieve the Additional Capital
	 * 
	 * @return The Additional Capital
	 */

	public double additionalCapital()
	{
		return _additionalCapital;
	}

	/**
	 * Retrieve the Risk Weighted Assets
	 * 
	 * @return The Risk Weighted Assets
	 */

	public double riskWeightedAssets()
	{
		return _rwa;
	}

	/**
	 * Retrieve the Total Exposure
	 * 
	 * @return The Total Exposure
	 */

	public double totalExposure()
	{
		return _totalExposure;
	}

	/**
	 * Retrieve the Tier 1 Capital
	 * 
	 * @return The Tier 1 Capital
	 */

	public double tier1()
	{
		return _cet1 + _at1;
	}

	/**
	 * Retrieve the Total Capital
	 * 
	 * @return The Total Capital
	 */

	public double totalCapital()
	{
		return _cet1 + _at1 + _additionalCapital;
	}

	/**
	 * Retrieve the CET 1 Ratio
	 * 
	 * @return The CET 1 Ratio
	 */

	public double cet1Ratio()
	{
		return _cet1 / _rwa;
	}

	/**
	 * Retrieve the Tier 1 Ratio
	 * 
	 * @return The Tier 1 Ratio
	 */

	public double tier1Ratio()
	{
		return (_cet1 + _at1) / _rwa;
	}

	/**
	 * Retrieve the Total Capital Ratio
	 * 
	 * @return The Total Capital Ratio
	 */

	public double totalCapitalRatio()
	{
		return (_cet1 + _at1 + _additionalCapital) / _rwa;
	}

	/**
	 * Retrieve the Leverage Ratio
	 * 
	 * @return The Leverage Ratio
	 */

	public double leverageRatio()
	{
		return (_cet1 + _at1) / _totalExposure;
	}

	/**
	 * Generate the Balance Sheet Capital Metrics
	 * 
	 * @return The Balance Sheet Capital Metrics
	 */

	public org.drip.capital.bcbs.CapitalMetrics capitalMetrics()
	{
		double commonEquityCapitalRatio = _cet1 / _rwa;
		double totalCapitalRatio = (_cet1 + _at1 + _additionalCapital) / _rwa;

		try
		{
			return new org.drip.capital.bcbs.CapitalMetrics (
				(_cet1 + _at1) / _totalExposure,
				commonEquityCapitalRatio,
				commonEquityCapitalRatio,
				(_cet1 + _at1) / _rwa,
				totalCapitalRatio,
				totalCapitalRatio
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
