
package org.drip.param.quote;

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
 * <i>ProductTick</i> holds the tick related product parameters - it contains the product ID, the quote
 * composite, the source, the counter party, and whether the quote can be treated as a mark.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/README.md">Multi-sided Multi-Measure Ticks Quotes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProductTick {
	private boolean _bIsMark = false;
	private java.lang.String _strSource = "";
	private java.lang.String _strProductID = "";
	private java.lang.String _strCounterParty = "";
	private org.drip.param.definition.ProductQuote _pq = null;

	/**
	 * Empty ProductTick constructor
	 */

	public ProductTick()
	{
	}

	/**
	 * ProductTick constructor
	 * 
	 * @param strProductID Product ID
	 * @param pq Product Quote
	 * @param strCounterParty Counter Party
	 * @param strSource Quote Source
	 * @param bIsMark TRUE - This Quote may be treated as a Mark
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ProductTick (
		final java.lang.String strProductID,
		final org.drip.param.definition.ProductQuote pq,
		final java.lang.String strCounterParty,
		final java.lang.String strSource,
		final boolean bIsMark)
		throws java.lang.Exception
	{
		if (null == (_strProductID = strProductID) || _strProductID.isEmpty() || null == (_pq = pq))
			throw new java.lang.Exception ("ProductTick ctr: Invalid Inputs");

		_bIsMark = bIsMark;
		_strSource = strSource;
		_strCounterParty = strCounterParty;
	}

	/**
	 * Retrieve the Product ID
	 * 
	 * @return Product ID
	 */

	public java.lang.String productID()
	{
		return _strProductID;
	}

	/**
	 * Retrieve the Product Quote
	 * 
	 * @return Product Quote
	 */

	public org.drip.param.definition.ProductQuote productQuote()
	{
		return _pq;
	}

	/**
	 * Retrieve the Quote Source
	 * 
	 * @return Quote Source
	 */

	public java.lang.String source()
	{
		return _strSource;
	}

	/**
	 * Retrieve the Counter Party
	 * 
	 * @return Counter Party
	 */

	public java.lang.String counterParty()
	{
		return _strCounterParty;
	}

	/**
	 * Indicate whether the quote may be treated as a mark
	 * 
	 * @return TRUE - Treat the Quote as a Mark
	 */

	public boolean isMark()
	{
		return _bIsMark;
	}
}
