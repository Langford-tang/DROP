
package org.drip.analytics.eventday;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>Weekend</i> holds the left and the right weekend days. It provides functionality to retrieve them,
 * check if the given day is a weekend, and serialize/de-serialize weekend days.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/README.md">Fixed, Variable, and Custom Holiday Creation</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Weekend {
	private int[] _aiDay = null;

	/**
	 * Create a Weekend Instance with SATURDAY and SUNDAY
	 * 
	 * @return Weekend object
	 */

	public static final Weekend StandardWeekend()
	{
		try {
			return new Weekend (new int[] {org.drip.analytics.date.DateUtil.SUNDAY,
				org.drip.analytics.date.DateUtil.SATURDAY});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the weekend instance object from the array of the weekend days
	 * 
	 * @param aiDay Array of the weekend days
	 * 
	 * @throws java.lang.Exception Thrown if cannot properly de-serialize Weekend
	 */

	public Weekend (
		final int[] aiDay)
		throws java.lang.Exception
	{
		if (null == aiDay) throw new java.lang.Exception ("Weekend ctr: Invalid Inputs");

		int iNumWeekendDays = aiDay.length;;

		if (0 == iNumWeekendDays) throw new java.lang.Exception ("Weekend ctr: Invalid Inputs");

		_aiDay = new int[iNumWeekendDays];

		for (int i = 0; i < iNumWeekendDays; ++i)
			_aiDay[i] = aiDay[i];
	}

	/**
	 * Retrieve the weekend days
	 * 
	 * @return Array of the weekend days
	 */

	public int[] days()
	{
		return _aiDay;
	}

	/**
	 * Is the given date a left weekend day
	 * 
	 * @param iDate Date
	 * 
	 * @return True (Left weekend day)
	 */

	public boolean isLeftWeekend (
		final int iDate)
	{
		if (null == _aiDay || 0 == _aiDay.length) return false;

		if (_aiDay[0] == (iDate % 7)) return true;

		return false;
	}

	/**
	 * Is the given date a right weekend day
	 * 
	 * @param dblDate Date
	 * 
	 * @return True (Right weekend day)
	 */

	public boolean isRightWeekend (
		final double dblDate)
	{
		if (null == _aiDay || 1 >= _aiDay.length) return false;

		if (_aiDay[1] == (dblDate % 7)) return true;

		return false;
	}

	/**
	 * Is the given date a weekend day
	 * 
	 * @param iDate Date
	 * 
	 * @return True (Weekend day)
	 */

	public boolean isWeekend (
		final int iDate)
	{
		return isLeftWeekend (iDate) || isRightWeekend (iDate);
	}
}
