
package org.drip.portfolioconstruction.alm;

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
 * <i>NetLiabilityMetrics</i> holds the Results of the Computation of the Net Liability Cash Flows and PV
 * Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/README.md">Sharpe-Tint Asset Liability Manager</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityMetrics
{
	private double _basicConsumptionPV = java.lang.Double.NaN;
	private double _workingAgeIncomePV = java.lang.Double.NaN;
	private double _pensionBenefitsIncomePV = java.lang.Double.NaN;
	private java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> _netLiabilityCashFlowList
		= null;

	/**
	 * NetLiabilityMetrics Constructor
	 * 
	 * @param netLiabilityCashFlowList List of Net Liability Cash Flows
	 * @param workingAgeIncomePV PV of the Working Age Income
	 * @param pensionBenefitsIncomePV PV of the Pension Benefits Income
	 * @param basicConsumptionPV PV of the Basic Consumption
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NetLiabilityMetrics (
		final java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow>
			netLiabilityCashFlowList,
		final double workingAgeIncomePV,
		final double pensionBenefitsIncomePV,
		final double basicConsumptionPV)
		throws java.lang.Exception
	{
		if (null == (_netLiabilityCashFlowList = netLiabilityCashFlowList) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_workingAgeIncomePV = workingAgeIncomePV) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pensionBenefitsIncomePV =
				pensionBenefitsIncomePV) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_basicConsumptionPV = basicConsumptionPV))
		{
			throw new java.lang.Exception ("NetLiabilityMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the List of Net Liability Cash Flows
	 * 
	 * @return The List of Net Liability Cash Flows
	 */

	public java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> netLiabilityCashFlowList()
	{
		return _netLiabilityCashFlowList;
	}

	/**
	 * Retrieve the PV of the Working Age Income
	 * 
	 * @return The PV of the Working Age Income
	 */

	public double workingAgeIncomePV()
	{
		return _workingAgeIncomePV;
	}

	/**
	 * Retrieve the PV of the Pension Benefits Income
	 * 
	 * @return The PV of the Pension Benefits Income
	 */

	public double pensionBenefitsIncomePV()
	{
		return _pensionBenefitsIncomePV;
	}

	/**
	 * Retrieve the PV of the Basic Consumption
	 * 
	 * @return The PV of the Basic Consumption
	 */

	public double basicConsumptionPV()
	{
		return _basicConsumptionPV;
	}
}
