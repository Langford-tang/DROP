
package org.drip.market.exchange;

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
 * <i>TreasuryFuturesContractContainer</i> holds the Details of some of the Common Treasury Futures Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Deliverable Swap, STIR, Treasury Futures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesContractContainer {
	private static
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>
			_mapNameContract = null;
	private static
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>
			_mapCodeTenorContract = null;

	private static final boolean AddContract (
		final java.lang.String[] astrName,
		final java.lang.String strID,
		final java.lang.String strCode,
		final java.lang.String strType,
		final java.lang.String strTenor)
	{
		try {
			org.drip.market.exchange.TreasuryFuturesContract tfc = new
				org.drip.market.exchange.TreasuryFuturesContract (strID, strCode, strType, strTenor);

			for (java.lang.String strName : astrName)
				_mapNameContract.put (strName, tfc);

			_mapCodeTenorContract.put (strCode + "::" + strTenor, tfc);

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Treasury Futures Contract Container with the Conventions
	 * 
	 * @return TRUE - The Treasury Futures Contracts Container successfully initialized with the Contracts
	 */

	public static final boolean Init()
	{
		if (null != _mapNameContract) return true;

		_mapNameContract = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>();

		_mapCodeTenorContract = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>();

		/*
		 * Australian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"YM1"}, "YM1", "AGB", "NOTE", "03Y")) return false;

		if (!AddContract (new java.lang.String[] {"XM1"}, "XM1", "AGB", "NOTE", "10Y")) return false;

		/*
		 * Canadian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"CN1"}, "CN1", "CAN", "NOTE", "10Y")) return false;

		/*
		 * Danish Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"DGB"}, "DGB", "DGB", "NOTE", "10Y")) return false;

		/*
		 * French Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"OAT1"}, "OAT1", "FRTR", "NOTE", "10Y")) return false;

		/*
		 * German Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"DU1", "SCHATZ"}, "DU1", "DBR", "NOTE", "02Y"))
			return false;

		if (!AddContract (new java.lang.String[] {"OE1", "BOBL"}, "OE1", "DBR", "NOTE", "05Y")) return false;

		if (!AddContract (new java.lang.String[] {"RX1", "BUND"}, "RX1", "DBR", "NOTE", "10Y")) return false;

		if (!AddContract (new java.lang.String[] {"UB1", "BUXL"}, "UB1", "DBR", "NOTE", "30Y")) return false;

		/*
		 * Italian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"IK1"}, "IK1", "BTPS", "NOTE", "10Y")) return false;

		/*
		 * Japanese Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"JB1"}, "JB1", "JGB", "NOTE", "10Y")) return false;

		/*
		 * Spanish Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"FBB1"}, "FBB1", "SPGB", "NOTE", "10Y")) return false;

		/*
		 * Swiss Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"GSWISS"}, "GSWISS", "GSWISS", "NOTE", "10Y"))
			return false;

		/*
		 * UK Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"WB1"}, "WB1", "GILT", "NOTE", "02Y")) return false;

		if (!AddContract (new java.lang.String[] {"G1"}, "G1", "GILT", "NOTE", "10Y")) return false;

		/*
		 * US Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"TU1"}, "TU1", "UST", "NOTE", "02Y")) return false;

		if (!AddContract (new java.lang.String[] {"FV1"}, "FV1", "UST", "NOTE", "05Y")) return false;

		if (!AddContract (new java.lang.String[] {"TY1"}, "TY1", "UST", "NOTE", "10Y")) return false;

		if (!AddContract (new java.lang.String[] {"US1"}, "US1", "UST", "NOTE", "20Y")) return false;

		if (!AddContract (new java.lang.String[] {"WN1", "ULTRA"}, "WN1", "UST", "NOTE", "30Y"))
			return false;

		return true;
	}

	/**
	 * Retrieve the Treasury Futures Contract by Name
	 * 
	 * @param strTreasuryFuturesName The Treasury Futures Name
	 * 
	 * @return The Treasury Futures Contract
	 */

	public static final org.drip.market.exchange.TreasuryFuturesContract TreasuryFuturesContract (
		final java.lang.String strTreasuryFuturesName)
	{
		return !_mapNameContract.containsKey (strTreasuryFuturesName) ? null : _mapNameContract.get
			(strTreasuryFuturesName);
	}

	/**
	 * Retrieve the Treasury Futures Contract by Code and Tenor
	 * 
	 * @param strCode The Treasury Code
	 * @param strTenor The Futures Tenor
	 * 
	 * @return The Treasury Futures Contract
	 */

	public static final org.drip.market.exchange.TreasuryFuturesContract TreasuryFuturesContract (
		final java.lang.String strCode,
		final java.lang.String strTenor)
	{
		java.lang.String strCodeTenor = strCode + "::" + strTenor;

		return !_mapNameContract.containsKey (strCodeTenor) ? null : _mapNameContract.get (strCodeTenor);
	}
}
