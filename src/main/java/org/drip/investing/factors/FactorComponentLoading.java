
package org.drip.investing.factors;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>FactorComponentLoading</i> holds the Weight and the Loading corresponding to each Factor. The
 *  References are:
 *
 *	<br><br>
 * <ul>
 * 	<li>
 *  	Baltussen, G., L. Swinkels, and P. van Vliet (2021): Global Factor Premiums <i>Journal of Financial
 *  		Economics</i> <b>142 (3)</b> 1128-1154
 * 	</li>
 * 	<li>
 *  	Blitz, D., and P. van Vliet (2007): The Volatility Effect: Lower Risk without Lower Return <i>Journal
 *  		of Portfolio Management</i> <b>34 (1)</b> 102-113
 * 	</li>
 * 	<li>
 *  	Fisher, G. S., R. Shah, and S. Titman (2017): Combining Value and Momentum
 *  		<i>https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2472936</i> <b>eSSRN</b>
 * 	</li>
 * 	<li>
 *  	Houweling, P., and J. van Zundert (2017): Factor Investing in the Corporate Bond Market <i>Financial
 *  		Analysts Journal</i> <b>73 (2)</b> 100-115
 * 	</li>
 * 	<li>
 *  	Wikipedia (2024): Factor Investing <i>https://en.wikipedia.org/wiki/Factor_investing</i>
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/README.md">Factor/Style Based Quantitative Investing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/factors/README.md">Factor Types, Characteristics, and Constitution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FactorComponentLoading
{
	private String _assetID = "";
	private double _score = Double.NaN;
	private double _weight = Double.NaN;
	private double _returns = Double.NaN;
	private int _assetType = Integer.MIN_VALUE;
	private int _riskPremiumCategory = Integer.MIN_VALUE;

	/**
	 * FactorComponentLoading Constructor
	 * 
	 * @param assetID Asset ID
	 * @param assetType Asset Type
	 * @param riskPremiumCategory Risk Premium Category
	 * @param weight Factor Weight
	 * @param returns Factor Returns
	 * @param score Factor Score
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FactorComponentLoading (
		final String assetID,
		final int assetType,
		final int riskPremiumCategory,
		final double weight,
		final double returns,
		final double score)
		throws Exception
	{
		if (null == (_assetID = assetID) || _assetID.isEmpty() ||
			!NumberUtil.IsValid (_weight = weight) ||
			!NumberUtil.IsValid (_returns = returns) ||
			!NumberUtil.IsValid (_score = score))
		{
			throw new Exception ("FactorComponentLoading Constructor => Invalid Inputs");
		}

		_assetType = assetType;
		_riskPremiumCategory = riskPremiumCategory;
	}

	/**
	 * Retrieve the Asset ID
	 * 
	 * @return The Asset ID
	 */

	public String assetID()
	{
		return _assetID;
	}

	/**
	 * Retrieve the Asset Type
	 * 
	 * @return The Asset Type
	 */

	public int assetType()
	{
		return _assetType;
	}

	/**
	 * Retrieve the Risk Premium Category
	 * 
	 * @return The Risk Premium Category
	 */

	public int riskPremiumCategory()
	{
		return _riskPremiumCategory;
	}

	/**
	 * Retrieve the Factor Weight
	 * 
	 * @return The Factor Weight
	 */

	public double weight()
	{
		return _weight;
	}

	/**
	 * Retrieve the Factor Returns
	 * 
	 * @return The Factor Returns
	 */

	public double returns()
	{
		return _returns;
	}

	/**
	 * Indicate if the Weight is Tilted towards the Factor
	 * 
	 * @return TRUE - The Weight is Tilted towards the Factor
	 */

	public boolean tiltTowards()
	{
		return 0. < _weight;
	}

	/**
	 * Indicate if the Weight is Tilted against the Factor
	 * 
	 * @return TRUE - The Weight is Tilted against the Factor
	 */

	public boolean tiltAgainst()
	{
		return 0. > _weight;
	}

	/**
	 * Retrieve the Factor Score
	 * 
	 * @return The Factor Score
	 */

	public double score()
	{
		return _score;
	}

	/**
	 * Flip the Weight Sign
	 * 
	 * @return TRUE - The Weight Sign has been Flipped
	 */

	public boolean flipWeightSign()
	{
		_weight = -1. * _weight;
		return true;
	}
}
