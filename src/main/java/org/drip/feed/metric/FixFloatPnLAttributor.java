
package org.drip.feed.metric;

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
 * <i>FixFloatPnLAttributor</i> generates the Date Valuation and Position Change PnL Explain Attributions for
 * the Standard OTC Fix Float Swap.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/metric/README.md">Feed Horizon - PnL Explain/Attribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatPnLAttributor {

	/**
	 * Generate the Explain Components for the specified Fix Float Product
	 * 
	 * @param strCurrency The Fix-Float Swap Currency
	 * @param strMaturityTenor The Fix-Float Swap Maturity Tenor
	 * @param iHorizonGap The Valuation Horizon Gap
	 * @param strFeedTranformLocation The Closing Funding Curve Quotes Location
	 * @param astrFundingDepositTenor The Funding Curve Deposit Instrument Maturity Tenors
	 * @param aiFundingDepositColumn The Funding Curve Deposit Instrument Quote Columns
	 * @param astrFundingFixFloatTenor The Funding Curve Fix Float Swap Instrument Maturity Tenors
	 * @param aiFundingFixFloatColumn The Funding Curve Fix Float Swap Instrument Quote Columns
	 * @param astrRollDownHorizonTenor Array of the Roll Down Horizon Tenors
	 * 
	 * @return List of the Position Change Components
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		TenorHorizonExplainComponents (
			final java.lang.String strCurrency,
			final java.lang.String strMaturityTenor,
			final int iHorizonGap,
			final java.lang.String strFeedTranformLocation,
			final java.lang.String[] astrFundingDepositTenor,
			final int[] aiFundingDepositColumn,
			final java.lang.String[] astrFundingFixFloatTenor,
			final int[] aiFundingFixFloatColumn,
			final java.lang.String[] astrRollDownHorizonTenor)
	{
		if (null == astrFundingDepositTenor || null == aiFundingDepositColumn || null ==
			astrFundingFixFloatTenor || null == aiFundingFixFloatColumn)
			return null;

		int iNumFundingDeposit = astrFundingDepositTenor.length;
		int iNumFundingFixFloat = astrFundingFixFloatTenor.length;
		double[][] aadblFundingDepositClose = new double[iNumFundingDeposit][];
		double[][] aadblFundingFixFloatClose = new double[iNumFundingFixFloat][];

		if (0 == iNumFundingDeposit || iNumFundingDeposit != aiFundingDepositColumn.length || 0 ==
			iNumFundingFixFloat || iNumFundingFixFloat != aiFundingFixFloatColumn.length)
			return null;

		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid
			(strFeedTranformLocation, true);

		if (null == csvGrid) return null;

		org.drip.analytics.date.JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		int iNumClose = adtClose.length;
		double[][] aadblFundingDepositQuote = new double[iNumClose][iNumFundingDeposit];
		double[][] aadblFundingFixFloatQuote = new double[iNumClose][iNumFundingFixFloat];
		org.drip.analytics.date.JulianDate[] adtSpot = new org.drip.analytics.date.JulianDate[iNumClose];

		for (int i = 0; i < iNumFundingDeposit; ++i)
			aadblFundingDepositClose[i] = csvGrid.doubleArrayAtColumn (aiFundingDepositColumn[i]);

		for (int i = 0; i < iNumFundingFixFloat; ++i)
			aadblFundingFixFloatClose[i] = csvGrid.doubleArrayAtColumn (aiFundingFixFloatColumn[i]);

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];

			for (int j = 0; j < iNumFundingDeposit; ++j)
				aadblFundingDepositQuote[i][j] = aadblFundingDepositClose[j][i];

			for (int j = 0; j < iNumFundingFixFloat; ++j)
				aadblFundingFixFloatQuote[i][j] = aadblFundingFixFloatClose[j][i];
		}

		return org.drip.service.product.FixFloatAPI.HorizonChangeAttribution (adtSpot, iHorizonGap,
			astrFundingDepositTenor, aadblFundingDepositQuote, astrFundingFixFloatTenor,
				aadblFundingFixFloatQuote, strCurrency, strMaturityTenor, astrRollDownHorizonTenor,
					org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);
	}

	/**
	 * Generate the Tenor Horizon Explain Components
	 * 
	 * @param strCurrency The Fix-Float Swap Currency
	 * @param astrMaturityTenor Array of Fix-Float Swap Maturity Tenors
	 * @param aiHorizonGap Array of the Valuation Horizon Gaps
	 * @param strFeedTranformLocation The Closing Funding Curve Quotes Location
	 * @param astrFundingDepositTenor The Funding Curve Deposit Instrument Maturity Tenors
	 * @param aiFundingDepositColumn The Funding Curve Deposit Instrument Quote Columns
	 * @param astrFundingFixFloatTenor The Funding Curve Fix Float Swap Instrument Maturity Tenors
	 * @param aiFundingFixFloatColumn The Funding Curve Fix Float Swap Instrument Quote Columns
	 * @param astrRollDownHorizonTenor Array of the Roll Down Horizon Tenors
	 * 
	 * @return List of the Position Change Components
	 */

	public static final boolean TenorHorizonExplainComponents (
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor,
		final int[] aiHorizonGap,
		final java.lang.String strFeedTranformLocation,
		final java.lang.String[] astrFundingDepositTenor,
		final int[] aiFundingDepositColumn,
		final java.lang.String[] astrFundingFixFloatTenor,
		final int[] aiFundingFixFloatColumn,
		final java.lang.String[] astrRollDownHorizonTenor)
	{
		boolean bFirstRun = true;

		for (java.lang.String strMaturityTenor : astrMaturityTenor) {
			for (int iHorizonGap : aiHorizonGap) {
				java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC =
					org.drip.feed.metric.FixFloatPnLAttributor.TenorHorizonExplainComponents (strCurrency,
						strMaturityTenor, iHorizonGap, strFeedTranformLocation, astrFundingDepositTenor,
							aiFundingDepositColumn, astrFundingFixFloatTenor, aiFundingFixFloatColumn,
								astrRollDownHorizonTenor);

				if (null != lsPCC) {
					java.lang.String strHorizonTenor = 1 == iHorizonGap ? "1D" : ((iHorizonGap / 22) + "M");

					for (org.drip.historical.attribution.PositionChangeComponents pcc : lsPCC) {
						if (null != pcc) {
							if (bFirstRun) {
								System.out.println (pcc.header() + "horizontenor,");

								bFirstRun = false;
							}

							System.out.println (pcc.content() + strHorizonTenor + ",");
						}
					}
				}
			}
		}

		return true;
	}
}
