
package org.drip.sample.xccy;

import org.drip.market.otc.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>OTCCrossCurrencyDefinitions</i> contains all the pre-fixed Definitions of the OTC Cross-Currency
 * 	Float-Float Swap Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xccy/README.md">OTC Cross Currency Swaps Definition</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCCrossCurrencyDefinitions {
	public static final void main (
		String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\tReference Currency");

		System.out.println ("\t\t\tReference Tenor");

		System.out.println ("\t\t\tQuote Basis on Reference");

		System.out.println ("\t\t\tDerived Currency");

		System.out.println ("\t\t\tDerived Tenor");

		System.out.println ("\t\t\tQuote Basis on Derived");

		System.out.println ("\t\t\tFixing Setting Type");

		System.out.println ("\t\t\tSpot Lag in Business Days");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("AUD"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CAD"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CHF"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CLP"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("DKK"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("EUR"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("GBP"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("JPY"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("MXN"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("NOK"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("PLN"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("SEK"));

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		EnvManager.TerminateEnv();
	}
}
