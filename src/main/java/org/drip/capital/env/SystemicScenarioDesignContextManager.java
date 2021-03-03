
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
 * <i>SystemicScenarioDesignContextManager</i> sets up the Credit Spread Event Container. The References are:
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

public class SystemicScenarioDesignContextManager
{
	private static org.drip.capital.shell.CreditSpreadEventContainer s_CreditSpreadEventContainer = null;

	/**
	 * Initialize the GSST Design Context Manager
	 * 
	 * @return TRUE - The GSST Design Context Manager successfully initialized
	 */

	public static final boolean Init()
	{
		s_CreditSpreadEventContainer = new org.drip.capital.shell.CreditSpreadEventContainer();

		if (!s_CreditSpreadEventContainer.add (
			org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
				"NOV-2008",
				 371.,
				 -39.5,
				-197.,
				 129.,
				   7.8,
				 -55.0,
				 -28.5,
				org.drip.capital.systemicscenario.SystemicStressShockIndicator.Deflationary()
			)
		))
		{
			return false;
		}

		try
		{
			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"APR-1932",
					332.,
					-61.4,
					 42.,
					 96.,
					java.lang.Double.NaN,
					 33.8,
					java.lang.Double.NaN,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"JAN-1975",
					 221.,
					 -29.7,
					 183.,
					 -53.,
					  -8.5,
					 159.0,
					  49.2,
					org.drip.capital.systemicscenario.SystemicStressShockIndicator.Inflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"APR-1938",
					180.,
					-40.5,
					-17.,
					 30.,
					java.lang.Double.NaN,
					 -4.2,
					java.lang.Double.NaN,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"AUG-1982",
					 161.,
					  -2.7,
					-256.,
					 520.,
					  -0.8,
					  -6.0,
					  -7.8,
					org.drip.capital.systemicscenario.SystemicStressShockIndicator.Deflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"APR-1980",
					 147.,
					   4.5,
					 259.,
					-135.,
					  -1.0,
					 149.0,
					   4.1,
					org.drip.capital.systemicscenario.SystemicStressShockIndicator.Inflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"NOV-1970",
					 140.,
					  -7.0,
					-106.,
					 169.,
					java.lang.Double.NaN,
					  -1.0,
					   3.9,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"JAN-2001",
					 125.,
					  -2.0,
					-172.,
					-148.,
					   8.1,
					   9.0,
					   3.2,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"APR-1931",
					 113.,
					 -39.4,
					 -11.,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					 -45.4,
					java.lang.Double.NaN,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.systemicscenario.CreditSpreadEvent.Standard (
					"DEC-1966",
					  94.,
					 -13.1,
					  28.,
					 -36.,
					java.lang.Double.NaN,
					   2.0,
					java.lang.Double.NaN,
					new org.drip.capital.systemicscenario.SystemicStressShockIndicator (
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.DOWN,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UP,
						org.drip.capital.systemicscenario.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Built-in Credit Spread Event Container
	 * 
	 * @return The Built-in Credit Spread Event Container
	 */

	public static final org.drip.capital.shell.CreditSpreadEventContainer CreditSpreadEventContainer()
	{
		return s_CreditSpreadEventContainer;
	}
}
