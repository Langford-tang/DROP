
package org.drip.product.rates;

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
 * <i>SingleStreamComponent</i> implements fixed income component that is based off of a single stream.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/README.md">Fixed Income Multi-Stream Components</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SingleStreamComponent extends org.drip.product.definition.CalibratableComponent {
	private java.lang.String _strCode = "";
	private java.lang.String _strName = "";
	private org.drip.product.rates.Stream _stream = null;
	private org.drip.param.valuation.CashSettleParams _csp = null;

	/**
	 * SingleStreamComponent constructor
	 * 
	 * @param strName The Component Name
	 * @param stream The Single Stream Instance
	 * @param csp Cash Settle Parameters Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SingleStreamComponent (
		final java.lang.String strName,
		final org.drip.product.rates.Stream stream,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_stream = stream))
			throw new java.lang.Exception ("SingleStreamComponent ctr: Invalid Inputs");

		_csp = csp;
	}

	/**
	 * Retrieve the Stream Instance
	 * 
	 * @return The Stream Instance
	 */

	public org.drip.product.rates.Stream stream()
	{
		return _stream;
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapCouponCurrency.put (name(), _stream.couponCurrency());

		return mapCouponCurrency;
	}

	@Override public java.lang.String payCurrency()
	{
		return _stream.payCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
		forwardLabel()
	{
		org.drip.state.identifier.ForwardLabel forwardLabel = _stream.forwardLabel();

		if (null == forwardLabel) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>();

		mapForwardLabel.put (
			"DERIVED",
			forwardLabel
		);

		return mapForwardLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
		otcFixFloatLabel()
	{
		org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel = _stream.otcFixFloatLabel();

		if (null == otcFixFloatLabel) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			mapOTCFixFloatLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>();

		mapOTCFixFloatLabel.put (
			"DERIVED",
			otcFixFloatLabel
		);

		return mapOTCFixFloatLabel;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _stream.fundingLabel();
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return org.drip.state.identifier.GovvieLabel.Standard (payCurrency());
	}

	@Override public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _stream.creditLabel();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		org.drip.state.identifier.FXLabel fxLabel = _stream.fxLabel();

		if (null == fxLabel) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel> mapFXLabel = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>();

		mapFXLabel.put (name(), fxLabel);

		return mapFXLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public double initialNotional()
		throws java.lang.Exception
	{
		return _stream.initialNotional();
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return _stream.notional (iDate);
	}

	@Override public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return _stream.notional (iDate1, iDate2);
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return _stream.coupon (iAccrualEndDate, valParams, csqs);
	}

	@Override public int freq()
	{
		return _stream.freq();
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return _stream.effective();
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		return _stream.maturity();
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		return _stream.firstCouponDate();
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		return _stream.cashFlowPeriod();
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _csp;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = _stream.value
			(valParams, pricerParams, csqs, quotingParams);

		if (null == mapResult) return null;

		mapResult.put ("ForwardRate", mapResult.get ("Rate"));

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setMeasureNames = _stream.availableMeasures();

		setMeasureNames.add ("ForwardRate");

		return setMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		return _stream.pv (valParams, pricerParams, csqc, vcp);
	}

	@Override public void setPrimaryCode (
		final java.lang.String strCode)
	{
		_strCode = strCode;
	}

	@Override public java.lang.String primaryCode()
	{
		return _strCode;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		if (null == valParams || valParams.valueDate() >= maturityDate().julian() || null == csqs)
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel());

		if (null == dcFunding) return null;

		try {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = value
				(valParams, pricerParams, csqs, quotingParams);

			if (null == mapMeasures) return null;

			int iEffectiveDate = effectiveDate().julian();

			double dblDFEffective = dcFunding.df (iEffectiveDate);

			double dblDFMaturity = dcFunding.df (maturityDate().julian());

			org.drip.numerical.differentiation.WengertJacobian wjDFEffective = dcFunding.jackDDFDManifestMeasure
				(iEffectiveDate, "Rate");

			org.drip.numerical.differentiation.WengertJacobian wjDFMaturity = dcFunding.jackDDFDManifestMeasure
				(maturityDate().julian(), "Rate");

			if (null == wjDFEffective || null == wjDFMaturity) return null;

			org.drip.numerical.differentiation.WengertJacobian wjPVDFMicroJack = new
				org.drip.numerical.differentiation.WengertJacobian (1, wjDFMaturity.numParameters());

			for (int i = 0; i < wjDFMaturity.numParameters(); ++i) {
				if (!wjPVDFMicroJack.accumulatePartialFirstDerivative (0, i, wjDFMaturity.firstDerivative (0,
					i) / dblDFEffective))
					return null;

				if (!wjPVDFMicroJack.accumulatePartialFirstDerivative (0, i, -wjDFEffective.firstDerivative
					(0, i) * dblDFMaturity / dblDFEffective / dblDFEffective))
					return null;
			}

			return wjPVDFMicroJack;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		if (null == valParams || valParams.valueDate() >= maturityDate().julian() || null ==
			strManifestMeasure || strManifestMeasure.isEmpty() || null == csqs)
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel());

		if (null == dcFunding) return null;

		if ("Rate".equalsIgnoreCase (strManifestMeasure)) {
			int iEffectiveDate = effectiveDate().julian();

			try {
				double dblDFEffective = dcFunding.df (iEffectiveDate);

				double dblDFMaturity = dcFunding.df (maturityDate().julian());

				org.drip.numerical.differentiation.WengertJacobian wjDFEffective = dcFunding.jackDDFDManifestMeasure
					(iEffectiveDate, "Rate");

				org.drip.numerical.differentiation.WengertJacobian wjDFMaturity = dcFunding.jackDDFDManifestMeasure
					(maturityDate().julian(), "Rate");

				if (null == wjDFEffective || null == wjDFMaturity) return null;

				org.drip.numerical.differentiation.WengertJacobian wjDFMicroJack = new
					org.drip.numerical.differentiation.WengertJacobian (1, wjDFMaturity.numParameters());

				for (int i = 0; i < wjDFMaturity.numParameters(); ++i) {
					if (!wjDFMicroJack.accumulatePartialFirstDerivative (0, i,
						wjDFMaturity.firstDerivative (0, i) / dblDFEffective))
						return null;

					if (!wjDFMicroJack.accumulatePartialFirstDerivative (0, i, -1. *
						wjDFEffective.firstDerivative (0, i) * dblDFMaturity / dblDFEffective /
							dblDFEffective))
						return null;
				}

				return wjDFMicroJack;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return _stream.forwardPRWC (valParams, pricerParams, csqs, vcp, pqs);
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return _stream.fundingPRWC (valParams, pricerParams, csqs, vcp, pqs);
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return _stream.fundingForwardPRWC (valParams, pricerParams, csqs, vcp, pqs);
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return _stream.fxPRWC (valParams, pricerParams, csqs, vcp, pqs);
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint govviePRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}
}
