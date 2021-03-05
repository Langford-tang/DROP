
package org.drip.exposure.mpor;

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
 * <i>CollateralAmountEstimatorOutput</i> contains the Estimation Output of the Hypothecation Collateral that
 * is to be Posted during a Single Run of a Collateral Hypothecation Group Valuation. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading
 *  				Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market
 *  				<i>World Scientific Publishing </i> <b>Singapore</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/README.md">Margin Period Collateral Amount Estimation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralAmountEstimatorOutput
{
	private double _postingRequirement = java.lang.Double.NaN;
	private double _clientWindowMarginValue = java.lang.Double.NaN;
	private double _dealerWindowMarginValue = java.lang.Double.NaN;
	private double _clientPostingRequirement = java.lang.Double.NaN;
	private double _dealerPostingRequirement = java.lang.Double.NaN;
	private double _clientCollateralThreshold = java.lang.Double.NaN;
	private double _dealerCollateralThreshold = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _clientMarginDate = null;
	private org.drip.analytics.date.JulianDate _dealerMarginDate = null;

	/**
	 * CollateralAmountEstimatorOutput Constructor
	 * 
	 * @param dealerMarginDate The Dealer Margin Date
	 * @param clientMarginDate The Client Margin Date
	 * @param dealerWindowMarginValue The Margin Value at the Dealer Default Window
	 * @param dealerCollateralThreshold The Dealer Collateral Threshold
	 * @param dealerPostingRequirement The Dealer Collateral Posting Requirement
	 * @param clientWindowMarginValue The Margin Value at the Client Default Window
	 * @param clientCollateralThreshold The Client Collateral Threshold
	 * @param clientPostingRequirement The Client Collateral Posting Requirement
	 * @param postingRequirement The Total Collateral Posting Requirement
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralAmountEstimatorOutput (
		final org.drip.analytics.date.JulianDate dealerMarginDate,
		final org.drip.analytics.date.JulianDate clientMarginDate,
		final double dealerWindowMarginValue,
		final double dealerCollateralThreshold,
		final double dealerPostingRequirement,
		final double clientWindowMarginValue,
		final double clientCollateralThreshold,
		final double clientPostingRequirement,
		final double postingRequirement)
		throws java.lang.Exception
	{
		if (null == (_dealerMarginDate = dealerMarginDate) ||
			null == (_clientMarginDate = clientMarginDate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dealerWindowMarginValue = dealerWindowMarginValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dealerCollateralThreshold =
				dealerCollateralThreshold) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dealerPostingRequirement = dealerPostingRequirement)
				||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientWindowMarginValue = clientWindowMarginValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientCollateralThreshold =
				clientCollateralThreshold) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientPostingRequirement = clientPostingRequirement)
				||
			!org.drip.numerical.common.NumberUtil.IsValid (_postingRequirement = postingRequirement))
		{
			throw new java.lang.Exception ("CollateralAmountEstimatorOutput Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Dealer Margin Date
	 * 
	 * @return The Dealer Margin Date
	 */

	public org.drip.analytics.date.JulianDate dealerMarginDate()
	{
		return _dealerMarginDate;
	}

	/**
	 * Retrieve the Client Margin Date
	 * 
	 * @return The Client Margin Date
	 */

	public org.drip.analytics.date.JulianDate clientMarginDate()
	{
		return _clientMarginDate;
	}

	/**
	 * Retrieve the Margin Value at the Dealer Default Window
	 * 
	 * @return The Margin Value at the Dealer Default Window
	 */

	public double dealerWindowMarginValue()
	{
		return _dealerWindowMarginValue;
	}

	/**
	 * Retrieve the Dealer Collateral Threshold
	 * 
	 * @return The Dealer Collateral Threshold
	 */

	public double dealerCollateralThreshold()
	{
		return _dealerCollateralThreshold;
	}

	/**
	 * Retrieve the Dealer Posting Requirement
	 * 
	 * @return The Dealer Posting Requirement
	 */

	public double dealerPostingRequirement()
	{
		return _dealerPostingRequirement;
	}

	/**
	 * Retrieve the Margin Value at the Client Default Window
	 * 
	 * @return The Margin Value at the Client Default Window
	 */

	public double clientWindowMarginValue()
	{
		return _clientWindowMarginValue;
	}

	/**
	 * Retrieve the Client Collateral Threshold
	 * 
	 * @return The Client Collateral Threshold
	 */

	public double clientCollateralThreshold()
	{
		return _clientCollateralThreshold;
	}

	/**
	 * Retrieve the Client Posting Requirement
	 * 
	 * @return The Client Posting Requirement
	 */

	public double clientPostingRequirement()
	{
		return _clientPostingRequirement;
	}

	/**
	 * Retrieve the Total Collateral Posting Requirement
	 * 
	 * @return The Total Collateral Posting Requirement
	 */

	public double postingRequirement()
	{
		return _postingRequirement;
	}
}
