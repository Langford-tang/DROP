
package org.drip.capital.env;

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
 * <i>RiskTypeFactory</i> instantiates the Built-in Mapping between Risk Code and Risk Type. Unmapped Risk
 * 	Codes will be excluded. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/README.md">Economic Risk Capital Parameter Factories</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskTypeFactory
{

	/**
	 * Instantiate the Built-in RiskTypeContext
	 * 
	 * @return TRUE - The RiskTypeContext Instance
	 */

	public static org.drip.capital.shell.RiskTypeContext Instantiate()
	{
		java.util.Map<java.lang.String, java.lang.String> rbcRiskTypeMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

		rbcRiskTypeMap.put (
			"CGM_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CGM_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CGM_HFS",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_CRDT_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIB_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIB_HFI",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_HTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_IVAST_ACC",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIB_IVAST_AFS",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIB_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_NT_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIG_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIG_HFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_HTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_NT_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_IVAST_ACC",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIG_MTM",
			"Trading"
		);

		try
		{
			return new org.drip.capital.shell.RiskTypeContext (
				rbcRiskTypeMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
