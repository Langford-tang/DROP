
package org.drip.sample.bondsink;

import org.drip.analytics.date.*;
import org.drip.numerical.common.Array2D;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;

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
 * <i>Saharanpur</i> generates the Full Suite of Replication Metrics for the Sinker Bond Saharanpur.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondsink/README.md">Sinkable Amortizing Capitalizing Bond Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Saharanpur {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			10
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0130411 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01345,	// 98.655
			0.01470,	// 98.530
			0.01575,	// 98.425
			0.01660,	// 98.340
			0.01745,    // 98.255
			0.01845     // 98.155
		};

		String[] astrFixFloatTenor = new String[] {
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};

		String[] astrGovvieTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.016410, //  2Y
			0.017863, //  3Y
			0.019030, //  4Y
			0.020035, //  5Y
			0.020902, //  6Y
			0.021660, //  7Y
			0.022307, //  8Y
			0.022879, //  9Y
			0.023363, // 10Y
			0.023820, // 11Y
			0.024172, // 12Y
			0.024934, // 15Y
			0.025581, // 20Y
			0.025906, // 25Y
			0.025973, // 30Y
			0.025838, // 40Y
			0.025560  // 50Y
		};

		double[] adblGovvieYield = new double[] {
			0.01219, //  1Y
			0.01391, //  2Y
			0.01590, //  3Y
			0.01937, //  5Y
			0.02200, //  7Y
			0.02378, // 10Y
			0.02677, // 20Y
			0.02927  // 30Y
		};

		String[] astrCreditTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCreditQuote = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		double dblFX = 1.;
		int iSettleLag = 3;
		int iCouponFreq = 4;
		String strName = "Saharanpur";
		double dblCleanPrice = 1.058939;
		double dblIssuePrice = 1.0;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.055; 
		double dblIssueAmount = 1.0e00;
		String strTreasuryCode = "UST";
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2012,
			DateUtil.DECEMBER,
			20
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2027,
			DateUtil.DECEMBER,
			20
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,
			strName,
			dblCouponRate,
			iCouponFreq,
			strCouponDayCount,
			dtEffective,
			dtMaturity,
			Array2D.FromArray (
				new int[] {
					DateUtil.CreateFromYMD (2017,  3, 20).julian(),
					DateUtil.CreateFromYMD (2017,  6, 20).julian(),
					DateUtil.CreateFromYMD (2017,  9, 20).julian(),
					DateUtil.CreateFromYMD (2017, 12, 20).julian(),
					DateUtil.CreateFromYMD (2018,  3, 20).julian(),
					DateUtil.CreateFromYMD (2018,  6, 20).julian(),
					DateUtil.CreateFromYMD (2018,  9, 20).julian(),
					DateUtil.CreateFromYMD (2018, 12, 20).julian(),
					DateUtil.CreateFromYMD (2019,  3, 20).julian(),
					DateUtil.CreateFromYMD (2019,  6, 20).julian(),
					DateUtil.CreateFromYMD (2019,  9, 20).julian(),
					DateUtil.CreateFromYMD (2019, 12, 20).julian(),
					DateUtil.CreateFromYMD (2020,  3, 20).julian(),
					DateUtil.CreateFromYMD (2020,  6, 20).julian(),
					DateUtil.CreateFromYMD (2020,  9, 20).julian(),
					DateUtil.CreateFromYMD (2020, 12, 20).julian(),
					DateUtil.CreateFromYMD (2021,  3, 20).julian(),
					DateUtil.CreateFromYMD (2021,  6, 20).julian(),
					DateUtil.CreateFromYMD (2021,  9, 20).julian(),
					DateUtil.CreateFromYMD (2021, 12, 20).julian(),
					DateUtil.CreateFromYMD (2022,  3, 20).julian(),
					DateUtil.CreateFromYMD (2022,  6, 20).julian(),
					DateUtil.CreateFromYMD (2022,  9, 20).julian(),
					DateUtil.CreateFromYMD (2022, 12, 20).julian(),
					DateUtil.CreateFromYMD (2023,  3, 20).julian(),
					DateUtil.CreateFromYMD (2023,  6, 20).julian(),
					DateUtil.CreateFromYMD (2023,  9, 20).julian(),
					DateUtil.CreateFromYMD (2023, 12, 20).julian(),
					DateUtil.CreateFromYMD (2024,  3, 20).julian(),
					DateUtil.CreateFromYMD (2024,  6, 20).julian(),
					DateUtil.CreateFromYMD (2024,  9, 20).julian(),
					DateUtil.CreateFromYMD (2024, 12, 20).julian(),
					DateUtil.CreateFromYMD (2025,  3, 20).julian(),
					DateUtil.CreateFromYMD (2025,  6, 20).julian(),
					DateUtil.CreateFromYMD (2025,  9, 20).julian(),
					DateUtil.CreateFromYMD (2025, 12, 20).julian(),
					DateUtil.CreateFromYMD (2026,  3, 20).julian(),
					DateUtil.CreateFromYMD (2026,  6, 20).julian(),
					DateUtil.CreateFromYMD (2026,  9, 20).julian(),
					DateUtil.CreateFromYMD (2026, 12, 20).julian(),
					DateUtil.CreateFromYMD (2027,  3, 20).julian(),
					DateUtil.CreateFromYMD (2027,  6, 20).julian(),
					DateUtil.CreateFromYMD (2027,  9, 20).julian(),
				},
				new double[] {
					0.794090176,
					0.780424395,
					0.766570709,
					0.752526535,
					0.738289254,
					0.723856210,
					0.709224712,
					0.694392031,
					0.679355400,
					0.664112016,
					0.648659035,
					0.632993575,
					0.617112716,
					0.601013495,
					0.584692909,
					0.568147916,
					0.551375428,
					0.534372319,
					0.517135418,
					0.499661509,
					0.481947333,
					0.463989588,
					0.445784924,
					0.427329945,
					0.408621211,
					0.389655232,
					0.370428470,
					0.350937340,
					0.331178208,
					0.311147387,
					0.290841142,
					0.270255687,
					0.249387182,
					0.228231734,
					0.206785399,
					0.185044178,
					0.163004014,
					0.140660798,
					0.118010363,
					0.095048484,
					0.071770880,
					0.048173208,
					0.024251069,
				}
			),
			null
		);

		BondReplicator abr = BondReplicator.CorporateSenior (
			dblCleanPrice,
			dblIssuePrice,
			dblIssueAmount,
			dtSpot,
			astrDepositTenor,
			adblDepositQuote,
			adblFuturesQuote,
			astrFixFloatTenor,
			adblFixFloatQuote,
			dblSpreadBump,
			dblSpreadDurationMultiplier,
			strTreasuryCode,
			astrGovvieTenor,
			adblGovvieYield,
			astrCreditTenor,
			adblCreditQuote,
			dblFX,
			Double.NaN,
			iSettleLag,
			bond
		);

		BondReplicationRun abrr = abr.generateRun();

		System.out.println (abrr.display());

		EnvManager.TerminateEnv();
	}
}
