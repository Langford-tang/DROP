
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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesConventionContainer</i> holds the Details of the Treasury Futures Contracts.
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

public class TreasuryFuturesConventionContainer {
	private static java.util.Map<java.lang.String, org.drip.market.exchange.TreasuryFuturesConvention>
		_mapFutures = null;

	/**
	 * Initialize the Bond Futures Convention Container with the Conventions
	 * 
	 * @return TRUE - The Bond Futures Convention Container successfully initialized with the Conventions
	 */

	public static final boolean Init()
	{
		if (null != _mapFutures) return true;

		_mapFutures = new java.util.TreeMap<java.lang.String,
			org.drip.market.exchange.TreasuryFuturesConvention>();

		int[] aiStandardDeliveryMonth = {org.drip.analytics.date.DateUtil.FEBRUARY,
			org.drip.analytics.date.DateUtil.MAY, org.drip.analytics.date.DateUtil.AUGUST,
				org.drip.analytics.date.DateUtil.NOVEMBER};

		try {
			_mapFutures.put ("AUD-BANK-BILLS-3M", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-BANKBILLS-3M", new java.lang.String[] {"AUD-BANKBILLS-3M"}, "AUD", "AUD", "3M",
					1000000., 0.03125, 100000., new java.lang.String[] {"ASX"}, "BANK", "BILLS", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("85D", "95D", new
										java.lang.String[]
											{"Australia and New Zealand Banking Group Limited",
												"Commonwealth Bank of Australia",
													"National Australia Bank Limited",
														"Westpac Banking Corporation"}, 0.), new
															org.drip.market.exchange.TreasuryFuturesSettle (1, 1,
																0, 0,
																	org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_FLAT, false,
					java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("AUD-TREASURY-BOND-3Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-TREASURY-BOND-3Y", new java.lang.String[] {"YMA"}, "AUD", "AUD", "3Y", 100000., 0.03125,
					0., new java.lang.String[] {"SFE"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("2Y", "4Y", new
										java.lang.String[] {}, 0.), new
											org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
												org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_CASH,
													org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_AUD_BOND_FUTURES_STYLE,
				false, java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("AUD-TREASURY-BOND-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-TREASURY-BOND-10Y", new java.lang.String[] {"XMA"}, "AUD", "AUD", "10Y", 100000.,
					0.03125, 0., new java.lang.String[] {"SFE"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("8Y", "12Y", new
										java.lang.String[] {}, 0.), new
											org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
												org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_CASH,
													org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_AUD_BOND_FUTURES_STYLE,
				false, java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-BOND-ULTRA", new org.drip.market.exchange.TreasuryFuturesConvention
				("ULTRA T-BOND", new java.lang.String[] {"UB", "UL", "UBE"}, "USD", "USD", "ULTRA", 100000.,
					0.03125, 0., new java.lang.String[] {"CBOT"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("25Y",
									"MAX", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-BOND-30Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 30-YR BOND", new java.lang.String[] {"ZB", "US"}, "USD", "USD", "30Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("15Y",
									"25Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 10-YR NOTE", new java.lang.String[] {"ZN", "TY"}, "USD", "USD", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("78M",
									"10Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 5-YR NOTE", new java.lang.String[] {"ZF", "FV"}, "USD", "USD", "5Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("50M",
									"63M", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (3, 3, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-3Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 3-YR NOTE", new java.lang.String[] {"Z3N", "3YR"}, "USD", "USD", "3Y", 200000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("33M",
									"3Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 2-YR NOTE", new java.lang.String[] {"ZT", "TU"}, "USD", "USD", "2Y", 200000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("21M",
									"2Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BUXL-30Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUXL", new java.lang.String[] {"BUXL"}, "EUR", "EUR", "30Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX"}, "EURO", "BUXL", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("24Y",
									"35Y", new java.lang.String[] {"EUR-Germany BUXL Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.04, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BUND-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"BUND"}, "EUR", "EUR", "10Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "BUND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("102M",
									"126M", new java.lang.String[] {"EUR-Germany BUND Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BOBL-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"BOBL"}, "EUR", "EUR", "5Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "BOBL", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("54M",
									"66M", new java.lang.String[] {"EUR-Germany BOBL Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-SCHATZ-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"SCHATZ"}, "EUR", "EUR", "2Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "SCHATZ", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("21M",
									"27M", new java.lang.String[] {"EUR-Germany SCHATZ Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-TREASURY-BONO-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR 10Y BONO", new java.lang.String[] {"10Y-BONO"}, "EUR", "EUR", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"MEFF", "SENAF"}, "TREASURY", "BONO", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				false, 7, -1, -1, 10), new org.drip.market.exchange.TreasuryFuturesEligibility ("102M", "MAX",
					new java.lang.String[] {"EUR BONO"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle
						(1, 1, -2, -2,
							org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
								org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-SHORT-GILT-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP SHORT-GILT", new java.lang.String[] {"SHORT-GILT"}, "GBP", "GBP", "2Y", 100000.,
					0.03125, 0., new java.lang.String[] {"LIFFE"}, "SHORT", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("18M",
									"39M", new java.lang.String[] {"GBP Short GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-MEDIUM-GILT-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP MEDIUM-GILT", new java.lang.String[] {"MEDIUM-GILT"}, "GBP", "GBP", "5Y", 100000.,
					0.03125, 0., new java.lang.String[] {"LIFFE"}, "MEDIUM", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("4Y",
									"75M", new java.lang.String[] {"GBP Medium GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-LONG-GILT-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP LONG-GILT", new java.lang.String[] {"LONG-GILT"}, "GBP", "GBP", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"LIFFE", "NLX"}, "MEDIUM", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("105M",
									"13Y", new java.lang.String[] {"GBP Long GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.03, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("JPY-TREASURY-JGB-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("JPY 5Y JGB", new java.lang.String[] {"5Y-JGB"}, "JPY", "JPY", "5Y", 1000000000., 0.03125,
					0., new java.lang.String[] {"TSE"}, "TREASURY", "JGB", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				true, 7, -1, -1, 20), new org.drip.market.exchange.TreasuryFuturesEligibility ("4Y", "63M", new
					java.lang.String[] {"JPY JGB"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle (1,
						1, 0, -7, org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
							org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.03, 0.03, aiStandardDeliveryMonth)));

			_mapFutures.put ("JPY-TREASURY-JGB-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("JPY 10Y JGB", new java.lang.String[] {"10Y-JGB"}, "JPY", "JPY", "10Y", 1000000000., 0.03125,
					0., new java.lang.String[] {"TSE"}, "TREASURY", "JGB", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				true, 7, -1, -1, 20), new org.drip.market.exchange.TreasuryFuturesEligibility ("7Y", "10Y", new
					java.lang.String[] {"JPY JGB"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle (1,
						1, 0, -7, org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
							org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.06, 0.06, aiStandardDeliveryMonth)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Treasury Futures Convention from the Currency, the Type, the Sub-type, and the Maturity
	 *	Tenor
	 * 
	 * @param strCurrency The Currency
	 * @param strUnderlierType The Underlier Type
	 * @param strUnderlierSubtype The Underlier Sub-type
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final org.drip.market.exchange.TreasuryFuturesConvention FromJurisdictionTypeMaturity (
		final java.lang.String strCurrency,
		final java.lang.String strUnderlierType,
		final java.lang.String strUnderlierSubtype,
		final java.lang.String strMaturityTenor)
	{
		if (null == strCurrency || strCurrency.isEmpty() || null == strUnderlierType ||
			strUnderlierType.isEmpty() || null == strUnderlierSubtype || strUnderlierSubtype.isEmpty() ||
				null == strMaturityTenor || strMaturityTenor.isEmpty())
			return null;

		java.lang.String strKey = strCurrency + "-" + strUnderlierType + "-" + strUnderlierSubtype + "-" +
			strMaturityTenor;

		return _mapFutures.containsKey (strKey) ? _mapFutures.get (strKey) : null;
	}
}
