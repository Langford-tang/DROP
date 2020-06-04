
package org.drip.sample.forwardratefuturespnl;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.attribution.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.FundingFuturesAPI;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>ED1Attribution</i> demonstrates the Invocation of the Historical PnL Horizon PnL Attribution analysis
 * for the ED1 Series.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/forwardratefuturespnl/README.md">Forward Rate Futures PnL Attribution</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ED1Attribution {

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";
		String strPrintLocation = "C:\\DROP\\Daemons\\Transforms\\FundingFuturesCloses\\ED1ClosesReconstitutor.csv";

		CSVGrid csvGrid = CSVParser.StringGrid (
			strPrintLocation,
			true
		);

		JulianDate[] adtSpot = csvGrid.dateArrayAtColumn (0);

		double[] adblForwardRate = csvGrid.doubleArrayAtColumn (2);

		JulianDate[] adtExpiry = csvGrid.dateArrayAtColumn (3);

		List<PositionChangeComponents> lsPCC = FundingFuturesAPI.HorizonChangeAttribution (
			adtSpot,
			adtExpiry,
			adblForwardRate,
			strCurrency
		);

		System.out.println ("FirstDate, SecondDate, Previous DV01, Previous Forward Rate, Spot DV01, Spot Forward Rate, 1D Gross PnL, 1D Market PnL, 1D Roll-down PnL, 1D Accrual PnL, 1D Explained PnL, 1D Unexplianed PnL, Floater Label");

		for (PositionChangeComponents pcc : lsPCC)
			System.out.println (
				pcc.firstDate() + ", " +
				pcc.secondDate() + ", " +
				FormatUtil.FormatDouble (pcc.pmsFirst().r1 ("DV01"), 1, 8, 1.) + ", " +
				FormatUtil.FormatDouble (pcc.pmsFirst().r1 ("ForwardRate"), 1, 8, 100.) + ", " +
				FormatUtil.FormatDouble (pcc.pmsSecond().r1 ("DV01"), 1, 8, 1.) + ", " +
				FormatUtil.FormatDouble (pcc.pmsSecond().r1 ("ForwardRate"), 1, 8, 100.) + ", " +
				FormatUtil.FormatDouble (pcc.grossChange(), 1, 8, 10000.) + ", " +
				FormatUtil.FormatDouble (pcc.marketRealizationChange(), 1, 8, 10000.) + ", " +
				FormatUtil.FormatDouble (pcc.marketRollDownChange(), 1, 8, 10000.) + ", " +
				FormatUtil.FormatDouble (pcc.accrualChange(), 1, 8, 10000.) + ", " +
				FormatUtil.FormatDouble (pcc.explainedChange(), 1, 8, 10000.) + ", " +
				FormatUtil.FormatDouble (pcc.unexplainedChange(), 1, 8, 10000.) + ", " +
				pcc.pmsFirst().c1 ("FloaterLabel")
			);

		EnvManager.TerminateEnv();
	}
}
