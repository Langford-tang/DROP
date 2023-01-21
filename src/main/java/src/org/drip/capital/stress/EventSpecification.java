
package org.drip.capital.stress;

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
 * <i>EventSpecification</i> contains the Name of a Stress Event and its Probability. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/README.md">Economic Risk Capital Stress Event Settings</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EventSpecification
{
	private java.lang.String _name = "";
	private double _probability = java.lang.Double.NaN;

	/**
	 * Construct the 2008 Baseline Version of the Systemic Stress Event Specification
	 * 
	 * @return The 2008 Baseline Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification Systemic2008Baseline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_2008,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the 1974 Baseline Version of the Systemic Stress Event Specification
	 * 
	 * @return The 1974 Baseline Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification Systemic1974Baseline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_1974,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Deep Down-turn Version of the Systemic Stress Event Specification
	 * 
	 * @return The Deep Down-turn Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification SystemicDeepDownturn()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.DEEP_DOWNTURN,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Dollar Decline Version of the Systemic Stress Event Specification
	 * 
	 * @return The Dollar Decline Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification SystemicDollarDecline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.DOLLAR_DECLINE,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Interest Rate Shock Version of the Systemic Stress Event Specification
	 * 
	 * @return The Interest Rate Shock Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification SystemicInterestRateShock()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.INTEREST_RATE_SHOCK,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Lost Decade Version of the Systemic Stress Event Specification
	 * 
	 * @return The Lost Decade Version of Systemic Stress Event Specification
	 */

	public static final EventSpecification SystemicLostDecade()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.SystemicScenarioDefinition.LOST_DECADE,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EventSpecification Constructor
	 * 
	 * @param name The Stress Event Name
	 * @param probability The Stress Event Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EventSpecification (
		final java.lang.String name,
		final double probability)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_probability = probability
			) || 0. > _probability || 1. < _probability)
		{
			throw new java.lang.Exception (
				"EventSpecification Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Name of the Stress Event
	 * 
	 * @return Name of the Stress Event
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Probability of the Stress Event
	 * 
	 * @return Probability of the Stress Event
	 */

	public double probability()
	{
		return _probability;
	}
}
