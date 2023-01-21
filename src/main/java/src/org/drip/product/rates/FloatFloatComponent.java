
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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>FloatFloatComponent</i> contains the implementation of the Float-Float Index Basis Swap product
 * contract/valuation details. It is made off one Reference Floating stream and one Derived floating stream.
 * It exports the following functionality:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Standard/Custom Constructor for the FloatFloatComponent
 *  	</li>
 *  	<li>
 *  		Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 *  	</li>
 *  	<li>
 *  		Coupon/Notional Outstanding as well as schedules
 *  	</li>
 *  	<li>
 *  		Retrieve the constituent floating streams
 *  	</li>
 *  	<li>
 *  		Market Parameters: Discount, Forward, Credit, Treasury Curves
 *  	</li>
 *  	<li>
 *  		Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 *  	</li>
 *  	<li>
 *  		Valuation: Named Measure Generation
 *  	</li>
 *  	<li>
 *  		Calibration: The codes and constraints generation
 *  	</li>
 *  	<li>
 *  		Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 *  	</li>
 *  	<li>
 *  		Serialization into and de-serialization out of byte arrays
 *  	</li>
 *  </ul>
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

public class FloatFloatComponent extends org.drip.product.rates.DualStreamComponent {
	private java.lang.String _strCode = "";
	private org.drip.product.rates.Stream _floatDerived = null;
	private org.drip.product.rates.Stream _floatReference = null;
	private org.drip.param.valuation.CashSettleParams _csp = null;

	/**
	 * Construct the FloatFloatComponent from the Reference and the Derived Floating Streams.
	 * 
	 * @param floatReference The Reference Floating Stream (e.g., 6M LIBOR/EURIBOR Leg)
	 * @param floatDerived The Derived Floating Stream (e.g., 3M LIBOR/EURIBOR Leg)
	 * @param csp Cash Settle Parameters Instance
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public FloatFloatComponent (
		final org.drip.product.rates.Stream floatReference,
		final org.drip.product.rates.Stream floatDerived,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		if (null == (_floatReference = floatReference) || null == (_floatDerived = floatDerived))
			throw new java.lang.Exception ("FloatFloatComponent ctr: Invalid Inputs");

		_csp = csp;
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

	@Override public java.lang.String name()
	{
		return _strCode;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapCouponCurrency.put ("DERIVED", _floatDerived.couponCurrency());

		mapCouponCurrency.put ("REFERENCE", _floatReference.couponCurrency());

		return mapCouponCurrency;
	}

	@Override public java.lang.String payCurrency()
	{
		return _floatReference.payCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return null;
	}

	@Override public double initialNotional()
		throws java.lang.Exception
	{
		return _floatReference.initialNotional();
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return _floatReference.notional (iDate);
	}

	@Override public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return _floatReference.notional (iDate1, iDate2);
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return _floatReference.coupon (iAccrualEndDate, valParams, csqs);
	}

	@Override public int freq()
	{
		return _floatReference.freq();
	}

	@Override public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>();

		mapForwardLabel.put (
			"REFERENCE",
			_floatReference.forwardLabel()
		);

		mapForwardLabel.put (
			"DERIVED",
			_floatDerived.forwardLabel()
		);

		return mapForwardLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			otcFixFloatLabel()
	{
		return null;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _floatReference.fundingLabel();
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return org.drip.state.identifier.GovvieLabel.Standard (payCurrency());
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		org.drip.state.identifier.FXLabel fxLabelReference = _floatReference.fxLabel();

		org.drip.state.identifier.FXLabel fxLabelDerived = _floatDerived.fxLabel();

		if (null != fxLabelReference && null != fxLabelDerived) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel> mapFXLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>();

		if (null != fxLabelReference) mapFXLabel.put ("REFERENCE", fxLabelReference);

		if (null != fxLabelDerived) mapFXLabel.put ("DERIVED", fxLabelDerived);

		return mapFXLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public org.drip.product.rates.Stream referenceStream()
	{
		return _floatReference;
	}

	@Override public org.drip.product.rates.Stream derivedStream()
	{
		return _floatDerived;
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		org.drip.analytics.date.JulianDate dtFloatReferenceEffective = _floatReference.effective();

		org.drip.analytics.date.JulianDate dtFloatDerivedEffective = _floatDerived.effective();

		if (null == dtFloatReferenceEffective || null == dtFloatDerivedEffective) return null;

		return dtFloatReferenceEffective.julian() < dtFloatDerivedEffective.julian() ?
			dtFloatReferenceEffective : dtFloatDerivedEffective;
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		org.drip.analytics.date.JulianDate dtFloatReferenceMaturity = _floatReference.maturity();

		org.drip.analytics.date.JulianDate dtFloatDerivedMaturity = _floatDerived.maturity();

		if (null == dtFloatReferenceMaturity || null == dtFloatDerivedMaturity) return null;

		return dtFloatReferenceMaturity.julian() > dtFloatDerivedMaturity.julian() ?
			dtFloatReferenceMaturity : dtFloatDerivedMaturity;
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		org.drip.analytics.date.JulianDate dtFloatReferenceFirstCoupon = _floatReference.firstCouponDate();

		org.drip.analytics.date.JulianDate dtFloatDerivedFirstCoupon = _floatDerived.firstCouponDate();

		if (null == dtFloatReferenceFirstCoupon || null == dtFloatDerivedFirstCoupon) return null;

		return dtFloatReferenceFirstCoupon.julian() < dtFloatDerivedFirstCoupon.julian() ?
			dtFloatReferenceFirstCoupon : dtFloatDerivedFirstCoupon;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCP = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		lsCP.addAll (_floatReference.cashFlowPeriod());

		lsCP.addAll (_floatDerived.cashFlowPeriod());

		return lsCP;
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _csp;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFloatReferenceStreamResult =
			_floatReference.value (valParams, pricerParams, csqs, vcp);

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFloatDerivedStreamResult =
			_floatDerived.value (valParams, pricerParams, csqs, vcp);

		if (null == mapFloatReferenceStreamResult || 0 == mapFloatReferenceStreamResult.size() || null ==
			mapFloatDerivedStreamResult || 0 == mapFloatDerivedStreamResult.size())
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		if (!org.drip.analytics.support.Helper.AccumulateMeasures (mapResult,
			_floatReference.name(), mapFloatReferenceStreamResult))
			return null;

		if (!org.drip.analytics.support.Helper.AccumulateMeasures (mapResult, _floatDerived.name(),
			mapFloatDerivedStreamResult))
			return null;

		mapResult.put ("ReferenceAccrued01", mapFloatReferenceStreamResult.get ("Accrued01"));

		mapResult.put ("ReferenceAccrued", mapFloatReferenceStreamResult.get ("FloatAccrued"));

		double dblReferenceCleanDV01 = mapFloatReferenceStreamResult.get ("CleanDV01");

		mapResult.put ("ReferenceCleanDV01", dblReferenceCleanDV01);

		double dblReferenceCleanPV = mapFloatReferenceStreamResult.get ("CleanPV");

		mapResult.put ("ReferenceCleanPV", dblReferenceCleanPV);

		mapResult.put ("ReferenceDirtyDV01", mapFloatReferenceStreamResult.get ("DirtyDV01"));

		double dblReferenceDirtyPV = mapFloatReferenceStreamResult.get ("DirtyPV");

		mapResult.put ("ReferenceDirtyPV", dblReferenceDirtyPV);

		mapResult.put ("ReferenceDV01", mapFloatReferenceStreamResult.get ("DV01"));

		mapResult.put ("ReferenceFixing01", mapFloatReferenceStreamResult.get ("Fixing01"));

		double dblReferencePV = mapFloatReferenceStreamResult.get ("PV");

		mapResult.put ("ReferencePV", dblReferencePV);

		mapResult.put ("ReferenceCumulativeConvexityAdjustmentFactor", mapFloatReferenceStreamResult.get
			("CumulativeConvexityAdjustmentFactor"));

		double dblReferenceCumulativeConvexityAdjustmentPremium = mapFloatReferenceStreamResult.get
			("CumulativeConvexityAdjustmentPremiumUpfront");

		mapResult.put ("ReferenceCumulativeConvexityAdjustmentPremium",
			dblReferenceCumulativeConvexityAdjustmentPremium);

		mapResult.put ("ReferenceResetDate", mapFloatReferenceStreamResult.get ("ResetDate"));

		mapResult.put ("ReferenceResetRate", mapFloatReferenceStreamResult.get ("ResetRate"));

		mapResult.put ("DerivedAccrued01", mapFloatDerivedStreamResult.get ("Accrued01"));

		mapResult.put ("DerivedAccrued", mapFloatDerivedStreamResult.get ("FloatAccrued"));

		double dblDerivedCleanDV01 = mapFloatDerivedStreamResult.get ("CleanDV01");

		mapResult.put ("DerivedCleanDV01", dblDerivedCleanDV01);

		double dblDerivedCleanPV = mapFloatDerivedStreamResult.get ("CleanPV");

		mapResult.put ("DerivedCleanPV", dblDerivedCleanPV);

		mapResult.put ("DerivedDirtyDV01", mapFloatDerivedStreamResult.get ("DirtyDV01"));

		double dblDerivedDirtyPV = mapFloatDerivedStreamResult.get ("DirtyPV");

		mapResult.put ("DerivedDirtyPV", dblDerivedDirtyPV);

		mapResult.put ("DerivedDV01", mapFloatDerivedStreamResult.get ("DV01"));

		mapResult.put ("DerivedFixing01", mapFloatDerivedStreamResult.get ("Fixing01"));

		double dblDerivedPV = mapFloatDerivedStreamResult.get ("PV");

		mapResult.put ("DerivedPV", dblDerivedPV);

		mapResult.put ("DerivedCumulativeConvexityAdjustmentFactor", mapFloatDerivedStreamResult.get
			("CumulativeConvexityAdjustmentFactor"));

		double dblDerivedCumulativeConvexityAdjustmentPremium = mapFloatDerivedStreamResult.get
			("CumulativeConvexityAdjustmentPremiumUpfront");

		mapResult.put ("DerivedCumulativeConvexityAdjustmentPremium",
			dblDerivedCumulativeConvexityAdjustmentPremium);

		mapResult.put ("DerivedResetDate", mapFloatDerivedStreamResult.get ("ResetDate"));

		mapResult.put ("DerivedResetRate", mapFloatDerivedStreamResult.get ("ResetRate"));

		double dblCleanPV = dblReferenceCleanPV + dblDerivedCleanPV;

		mapResult.put ("CleanPV", dblCleanPV);

		mapResult.put ("DirtyPV", dblDerivedCleanPV + dblDerivedDirtyPV);

		mapResult.put ("PV", dblReferencePV + dblDerivedPV);

		mapResult.put ("CumulativeConvexityAdjustmentPremium", _floatReference.initialNotional() *
			dblReferenceCumulativeConvexityAdjustmentPremium + _floatDerived.initialNotional() *
				dblDerivedCumulativeConvexityAdjustmentPremium);

		mapResult.put ("Upfront", mapFloatReferenceStreamResult.get ("Upfront") +
			mapFloatDerivedStreamResult.get ("Upfront"));

		mapResult.put ("ReferenceParBasisSpread", -1. * (dblReferenceCleanPV + dblDerivedCleanPV) /
			dblReferenceCleanDV01);

		mapResult.put ("DerivedParBasisSpread", -1. * (dblReferenceCleanPV + dblDerivedCleanPV) /
			dblDerivedCleanDV01);

		double dblValueNotional = java.lang.Double.NaN;

		try {
			dblValueNotional = notional (valParams.valueDate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (org.drip.numerical.common.NumberUtil.IsValid (dblValueNotional)) {
				double dblCleanPrice = 100. * (1. + (dblCleanPV / initialNotional() / dblValueNotional));

				mapResult.put ("CleanPrice", dblCleanPrice);

				mapResult.put ("Price", dblCleanPrice);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("CleanPrice");

		setstrMeasureNames.add ("CleanPV");

		setstrMeasureNames.add ("DerivedAccrued01");

		setstrMeasureNames.add ("DerivedAccrued");

		setstrMeasureNames.add ("DerivedCleanDV01");

		setstrMeasureNames.add ("DerivedCleanPV");

		setstrMeasureNames.add ("DerivedDirtyDV01");

		setstrMeasureNames.add ("DerivedDirtyPV");

		setstrMeasureNames.add ("DerivedDV01");

		setstrMeasureNames.add ("DerivedFixing01");

		setstrMeasureNames.add ("DerivedParBasisSpread");

		setstrMeasureNames.add ("DerivedPV");

		setstrMeasureNames.add ("DerivedCumulativeConvexityAdjustmentFactor");

		setstrMeasureNames.add ("DerivedCumulativeConvexityAdjustmentPremium");

		setstrMeasureNames.add ("DerivedResetDate");

		setstrMeasureNames.add ("DerivedResetRate");

		setstrMeasureNames.add ("DirtyPV");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("CumulativeConvexityAdjustmentPremium");

		setstrMeasureNames.add ("ReferenceAccrued01");

		setstrMeasureNames.add ("ReferenceAccrued");

		setstrMeasureNames.add ("ReferenceCleanDV01");

		setstrMeasureNames.add ("ReferenceCleanPV");

		setstrMeasureNames.add ("ReferenceDirtyDV01");

		setstrMeasureNames.add ("ReferenceDirtyPV");

		setstrMeasureNames.add ("ReferenceDV01");

		setstrMeasureNames.add ("ReferenceFixing01");

		setstrMeasureNames.add ("ReferenceParBasisSpread");

		setstrMeasureNames.add ("ReferenceCumulativeConvexityAdjustmentFactor");

		setstrMeasureNames.add ("ReferenceCumulativeConvexityAdjustmentPremium");

		setstrMeasureNames.add ("ReferencePV");

		setstrMeasureNames.add ("ReferenceResetDate");

		setstrMeasureNames.add ("ReferenceResetRate");

		setstrMeasureNames.add ("Upfront");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
		throws java.lang.Exception
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFloatReferenceStreamResult =
			_floatReference.value (valParams, pricerParams, csqs, quotingParams);

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFloatDerivedStreamResult =
			_floatDerived.value (valParams, pricerParams, csqs, quotingParams);

		if (null == mapFloatReferenceStreamResult || !mapFloatReferenceStreamResult.containsKey ("DirtyPV")
			|| null == mapFloatDerivedStreamResult || !mapFloatDerivedStreamResult.containsKey ("DirtyPV"))
			throw new java.lang.Exception
				("FloatFloatComponent::pv => Cannot Compute Constituent Stream Value Metrics");

		return mapFloatReferenceStreamResult.get ("DirtyPV") + mapFloatDerivedStreamResult.get ("DirtyPV");
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.FloatFloatQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs || !(pqs instanceof org.drip.product.calib.FloatFloatQuoteSet))
			return null;

		if (valParams.valueDate() >= maturityDate().julian()) return null;

		double dblPV = 0.;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsDerived = null;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsReference = null;
		org.drip.product.calib.FloatFloatQuoteSet ffqs = (org.drip.product.calib.FloatFloatQuoteSet) pqs;

		if (!ffqs.containsPV() && !ffqs.containsDerivedParBasisSpread() &&
			!ffqs.containsReferenceParBasisSpread())
			return null;

		org.drip.state.representation.LatentStateSpecification[] aLSS = pqs.lss();

		try {
			fsqsDerived = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			fsqsReference = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			if (ffqs.containsPV()) dblPV = ffqs.pv();

			if (ffqs.containsDerivedParBasisSpread() && !fsqsDerived.setSpread
				(ffqs.derivedParBasisSpread()))
				return null;

			if (ffqs.containsReferenceParBasisSpread() && !fsqsReference.setSpread
				(ffqs.referenceParBasisSpread()))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcDerived = _floatDerived.fundingPRWC
			(valParams, pricerParams, csqs, quotingParams, fsqsDerived);

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcReference =
			_floatReference.fundingPRWC (valParams, pricerParams, csqs, quotingParams, fsqsReference);

		if (null == prwcDerived && null == prwcReference) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (!prwc.absorb (prwcDerived)) return null;

		if (!prwc.absorb (prwcReference)) return null;

		return !prwc.updateValue (dblPV) ? null : prwc;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs || !(pqs instanceof org.drip.product.calib.FloatFloatQuoteSet))
			return null;

		if (valParams.valueDate() >= maturityDate().julian()) return null;

		double dblPV = 0.;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsDerived = null;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsReference = null;
		org.drip.product.calib.FloatFloatQuoteSet ffqs = (org.drip.product.calib.FloatFloatQuoteSet) pqs;

		if (!ffqs.containsPV() && !ffqs.containsDerivedParBasisSpread() &&
			!ffqs.containsReferenceParBasisSpread())
			return null;

		org.drip.state.representation.LatentStateSpecification[] aLSS = pqs.lss();

		try {
			fsqsDerived = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			fsqsReference = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			if (ffqs.containsPV()) dblPV = ffqs.pv();

			if (ffqs.containsDerivedParBasisSpread()) fsqsDerived.setSpread (ffqs.derivedParBasisSpread());

			if (ffqs.containsReferenceParBasisSpread())
				fsqsReference.setSpread (ffqs.referenceParBasisSpread());
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcDerived = _floatDerived.forwardPRWC
			(valParams, pricerParams, csqs, quotingParams, fsqsDerived);

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcReference =
			_floatReference.forwardPRWC (valParams, pricerParams, csqs, quotingParams, fsqsReference);

		if (null == prwcDerived && null == prwcReference) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (!prwc.absorb (prwcDerived)) return null;

		if (!prwc.absorb (prwcReference)) return null;

		if (!prwc.updateValue (dblPV)) return null;

		return prwc;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs || !(pqs instanceof org.drip.product.calib.FloatFloatQuoteSet))
			return null;

		if (valParams.valueDate() >= maturityDate().julian()) return null;

		double dblPV = 0.;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsDerived = null;
		org.drip.product.calib.FloatingStreamQuoteSet fsqsReference = null;
		org.drip.product.calib.FloatFloatQuoteSet ffqs = (org.drip.product.calib.FloatFloatQuoteSet) pqs;

		if (!ffqs.containsPV() && !ffqs.containsDerivedParBasisSpread() &&
			!ffqs.containsReferenceParBasisSpread())
			return null;

		org.drip.state.representation.LatentStateSpecification[] aLSS = pqs.lss();

		try {
			fsqsDerived = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			fsqsReference = new org.drip.product.calib.FloatingStreamQuoteSet (aLSS);

			if (ffqs.containsPV()) dblPV = ffqs.pv();

			if (ffqs.containsDerivedParBasisSpread() && !fsqsDerived.setSpread
				(ffqs.derivedParBasisSpread()))
				return null;

			if (ffqs.containsReferenceParBasisSpread() && !fsqsReference.setSpread
				(ffqs.referenceParBasisSpread()))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcDerived =
			_floatDerived.fundingForwardPRWC (valParams, pricerParams, csqs, quotingParams, fsqsDerived);

		org.drip.state.estimator.PredictorResponseWeightConstraint prwcReference =
			_floatReference.fundingForwardPRWC (valParams, pricerParams, csqs, quotingParams, fsqsReference);

		if (null == prwcDerived && null == prwcReference) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (!prwc.absorb (prwcDerived)) return null;

		if (!prwc.absorb (prwcReference)) return null;

		if (!prwc.updateValue (dblPV)) return null;

		return prwc;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
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
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
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
