
package org.drip.product.credit;

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
 * <i>CDSBasket</i> implements the basket default swap product contract details. It contains effective date,
 * maturity date, coupon, coupon day count, coupon frequency, basket components, basket notional, loss pay
 * lag, and optionally the outstanding notional schedule and the flat basket recovery. It also contains
 * methods to serialize out of and de-serialize into byte arrays.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit/README.md">Credit Products - Components and Baskets</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSBasket extends org.drip.product.definition.BasketProduct {
	private double[] _adblWeight = null;
	private java.lang.String _strName = "";
	private org.drip.product.definition.Component[] _aComp = null;

	/**
	 * Construct a CDS Basket from the components and their weights
	 * 
	 * @param aComp Array of components
	 * @param adblWeight Weights of the components
	 * @param strName Name of the basket
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CDSBasket (
		final org.drip.product.definition.Component[] aComp,
		final double[] adblWeight,
		final java.lang.String strName)
		throws java.lang.Exception
	{
		if (null == aComp || 0 == aComp.length || null == adblWeight || 0 == adblWeight.length ||
			aComp.length != adblWeight.length || null == strName || strName.isEmpty())
			throw new java.lang.Exception ("CDSBasket ctr: Invalid inputs!");

		_strName = strName;
		double dblCumulativeWeight = 0.;
		_adblWeight = new double[adblWeight.length];
		_aComp = new org.drip.product.definition.Component[aComp.length];

		for (int i = 0; i < aComp.length; ++i)
			dblCumulativeWeight += _adblWeight[i];

		for (int i = 0; i < aComp.length; ++i) {
			if (null == (_aComp[i] = aComp[i]))
				throw new java.lang.Exception ("CDSBasket ctr: Invalid Inputs!");

			_adblWeight[i] = adblWeight[i] / dblCumulativeWeight;
		}
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.product.definition.Component[] components()
	{
		return _aComp;
	}

	@Override public int measureAggregationType (
		final java.lang.String strMeasureName)
	{
		if ("AccrualDays".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_UNIT_ACCUMULATE;

		if ("Accrued".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("Accrued01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("CleanDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("CleanPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("DirtyDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("DirtyPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("DV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("ExpLoss".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("ExpLossNoRec".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairAccrualDays".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_UNIT_ACCUMULATE;

		if ("FairAccrued".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairAccrued01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairCleanDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairCleanPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairDirtyDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairDirtyPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairExpLoss".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairExpLossNoRec".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairFairPremium".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("FairLossNoRecPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairLossPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairParSpread".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("FairPremium".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("FairPremiumPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("FairUpfront".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("LossNoRecPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("LossPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketAccrualDays".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_UNIT_ACCUMULATE;

		if ("MarketAccrued".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketAccrued01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketCleanDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketCleanPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketDirtyDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketDirtyPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketDV01".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketExpLoss".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketExpLossNoRec".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketFairPremium".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("MarketLossNoRecPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketLossPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketParSpread".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("MarketPremiumPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("MarketUpfront".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("ParSpread".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		if ("PremiumPV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("PV".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_CUMULATIVE;

		if ("Upfront".equalsIgnoreCase (strMeasureName))
			return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_WEIGHTED_CUMULATIVE;

		return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_IGNORE;
	}
}
