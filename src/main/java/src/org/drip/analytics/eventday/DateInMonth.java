
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
 * <i>DateInMonth</i> exports Functionality that generates the specific Event Date inside of the specified
 *	Month/Year.
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

public class DateInMonth {

	/**
	 * Instance Date Generation Rules - Generate from Lag from Front/Back
	 */

	public static final int INSTANCE_GENERATOR_RULE_EDGE_LAG = 1;

	/**
	 * Instance Date Generation Rule - Generate from Specified Day in Week/Week in Month
	 */

	public static final int INSTANCE_GENERATOR_RULE_WEEK_DAY = 2;

	/**
	 * Instance Date Generation Rule - Generate Using the Specific Day of the Month
	 */

	public static final int INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH = 3;

	private int _iLag = -1;
	private int _iDayOfWeek = -1;
	private int _iWeekInMonth = -1;
	private boolean _bFromBack = false;
	private int _iSpecificDayInMonth = -1;
	private int _iInstanceGeneratorRule = -1;

	/**
	 * DateInMonth Constructor
	 * 
	 * @param iInstanceGeneratorRule Instance Generation Rule
	 * @param bFromBack TRUE - Apply Rules from Back of EOM
	 * @param iLag The Lag
	 * @param iDayOfWeek Day of Week
	 * @param iWeekInMonth Week in the Month
	 * @param iSpecificDayInMonth Specific Daye In Month
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public DateInMonth (
		final int iInstanceGeneratorRule,
		final boolean bFromBack,
		final int iLag,
		final int iDayOfWeek,
		final int iWeekInMonth,
		final int iSpecificDayInMonth)
		throws java.lang.Exception
	{
		_bFromBack = bFromBack;

		if (INSTANCE_GENERATOR_RULE_EDGE_LAG == (_iInstanceGeneratorRule = iInstanceGeneratorRule)) {
			if (0 > (_iLag = iLag)) throw new java.lang.Exception ("DateInMonth ctr: Invalid Inputs");
		} else if (INSTANCE_GENERATOR_RULE_WEEK_DAY == _iInstanceGeneratorRule) {
			_iDayOfWeek = iDayOfWeek;
			_iWeekInMonth = iWeekInMonth;
		} else
			_iSpecificDayInMonth = iSpecificDayInMonth;
	}

	/**
	 * Retrieve the Instance Generation Rule
	 * 
	 * @return The Instance Generation Rule
	 */

	public int instanceGenerator()
	{
		return _iInstanceGeneratorRule;
	}

	/**
	 * Retrieve the Flag indicating whether the Lag is from the Front/Back
	 * 
	 * @return TRUE - The Lag is from the Back.
	 */

	public boolean fromBack()
	{
		return _bFromBack;
	}

	/**
	 * Retrieve the Date Lag
	 * 
	 * @return The Date Lag
	 */

	public int lag()
	{
		return _iLag;
	}

	/**
	 * Retrieve the Week In Month
	 * 
	 * @return The Week In Month
	 */

	public int weekInMonth()
	{
		return _iWeekInMonth;
	}

	/**
	 * Retrieve the Day Of Week
	 * 
	 * @return The Day Of Week
	 */

	public int dayOfWeek()
	{
		return _iDayOfWeek;
	}

	/**
	 * Retrieve the Specific Day in Month
	 * 
	 * @return The Specific Day in Month
	 */

	public int specificDayInMonth()
	{
		return _iSpecificDayInMonth;
	}

	/**
	 * Generate the Particular Day of the Year, the Month, according to the Calendar
	 * 
	 * @param iYear Target Year
	 * @param iMonth Target Month
	 * @param strCalendar Target Calendar
	 * 
	 * @return The Particular Day
	 */

	public org.drip.analytics.date.JulianDate instanceDay (
		final int iYear,
		final int iMonth,
		final java.lang.String strCalendar)
	{
		try {
			if (INSTANCE_GENERATOR_RULE_EDGE_LAG == _iInstanceGeneratorRule) {
				if (_bFromBack) {
					org.drip.analytics.date.JulianDate dtBase =
						org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth,
							org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear));

					return null == dtBase ? null : dtBase.subtractBusDays (_iLag, strCalendar);
				}

				org.drip.analytics.date.JulianDate dtBase = org.drip.analytics.date.DateUtil.CreateFromYMD
					(iYear, iMonth, 1);

				return null == dtBase ? null : dtBase.addBusDays (_iLag, strCalendar);
			}

			if (INSTANCE_GENERATOR_RULE_WEEK_DAY == _iInstanceGeneratorRule) {
				if (_bFromBack) {
					org.drip.analytics.date.JulianDate dtEOM = org.drip.analytics.date.DateUtil.CreateFromYMD
						(iYear, iMonth, org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear));

					if (null == dtEOM) return null;

					while (_iDayOfWeek != org.drip.analytics.date.DateUtil.Day
						(org.drip.analytics.date.DateUtil.JavaDateFromJulianDate (dtEOM)))
						dtEOM = dtEOM.subtractDays (1);

					org.drip.analytics.date.JulianDate dtUnadjusted = dtEOM.subtractDays (_iWeekInMonth * 7);

					return null == dtUnadjusted ? null : dtUnadjusted.subtractBusDays (0, strCalendar);
				}

				org.drip.analytics.date.JulianDate dtSOM = org.drip.analytics.date.DateUtil.CreateFromYMD
					(iYear, iMonth, 1);

				if (null == dtSOM) return null;

				while (_iDayOfWeek != org.drip.analytics.date.DateUtil.Day
					(org.drip.analytics.date.DateUtil.JavaDateFromJulianDate (dtSOM)))
					dtSOM = dtSOM.addDays (1);

				org.drip.analytics.date.JulianDate dtUnadjusted = dtSOM.addDays (_iWeekInMonth * 7);

				return null == dtUnadjusted ? null : dtUnadjusted.addBusDays (0, strCalendar);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtBase = org.drip.analytics.date.DateUtil.CreateFromYMD (iYear,
			iMonth, _iSpecificDayInMonth);

		if (null == dtBase) return null;

		return _bFromBack ? dtBase.subtractBusDays (0, strCalendar) : dtBase.addBusDays (0, strCalendar);
	}

	@Override public java.lang.String toString()
	{
		return "[DateInMonth => Instance Generator Rule: " + _iInstanceGeneratorRule + " | From Back Flag: "
			+ _bFromBack + " | Day Of Week: " + _iDayOfWeek + " | Week In Month: " + _iWeekInMonth +
				" | Specific Day In Month: " + _iSpecificDayInMonth + " | Lag: " + _iLag + "]";
	}
}
