
package org.drip.state.curve;

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
 * <i>DerivedZeroRate</i> implements the delegated ZeroCurve functionality. Beyond discount factor/zero rate
 * computation at specific cash pay nodes, all other functions are delegated to the embedded discount curve.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/README.md">Basis Spline Based Latent States</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DerivedZeroRate extends org.drip.state.discount.ZeroCurve {
	private static final int NUM_DF_QUADRATURES = 5;

	private org.drip.state.discount.DiscountCurve _dc = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssDF = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssZeroRate = null;

	private static final boolean EntryFromDiscountCurve (
		final org.drip.state.discount.DiscountCurve dc,
		final int iDate,
		final int iFreq,
		final double dblYearFraction,
		final double dblShift,
		final java.util.Map<java.lang.Integer, java.lang.Double> mapDF,
		final java.util.Map<java.lang.Integer, java.lang.Double> mapZeroRate)
	{
		try {
			double dblZeroRate = org.drip.analytics.support.Helper.DF2Yield (iFreq, dc.df (iDate),
				dblYearFraction) + dblShift;

			mapDF.put (iDate, org.drip.analytics.support.Helper.Yield2DF (iFreq, dblZeroRate,
				dblYearFraction));

			mapZeroRate.put (iDate, dblZeroRate);

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final boolean EntryFromYield (
		final int iDate,
		final int iFreq,
		final double dblYearFraction,
		final double dblShiftedYield,
		final java.util.Map<java.lang.Integer, java.lang.Double> mapDF,
		final java.util.Map<java.lang.Integer, java.lang.Double> mapZeroRate)
	{
		try {
			mapDF.put (iDate, org.drip.analytics.support.Helper.Yield2DF (iFreq, dblShiftedYield,
				dblYearFraction));

			mapZeroRate.put (iDate, dblShiftedYield);

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Construct an Instance from the Discount Curve and the related Parameters
	 * 
	 * @param iFreqZC Zero Curve Frequency
	 * @param strDCZC Zero Curve Day Count
	 * @param strCalendarZC Zero Curve Calendar
	 * @param bApplyEOMAdjZC Zero Coupon EOM Adjustment Flag
	 * @param lsCouponPeriod List of Bond coupon periods
	 * @param iWorkoutDate Work-out Date
	 * @param iValueDate Value Date
	 * @param iCashPayDate Cash-Pay Date
	 * @param dc Underlying Discount Curve
	 * @param dblZCBump DC Bump
	 * @param vcp Valuation Customization Parameters
	 * @param scbc Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromDiscountCurve (
		final int iFreqZC,
		final java.lang.String strDCZC,
		final java.lang.String strCalendarZC,
		final boolean bApplyEOMAdjZC,
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod,
		final int iWorkoutDate,
		final int iValueDate,
		final int iCashPayDate,
		final org.drip.state.discount.DiscountCurve dc,
		final double dblZCBump,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == lsCouponPeriod || 2 > lsCouponPeriod.size() || null == dc ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblZCBump) || null == scbc)
			return null;

		int iFreq = 0 == iFreqZC ? 2 : iFreqZC;
		java.lang.String strCalendar = strCalendarZC;

		java.lang.String strDC = null == strDCZC || strDCZC.isEmpty() ? "30/360" : strDCZC;

		if (null != vcp) {
			strDC = vcp.yieldDayCount();

			iFreq = vcp.yieldFreq();

			strCalendar = vcp.yieldCalendar();
		}

		java.util.Map<java.lang.Integer, java.lang.Double> mapDF = new java.util.TreeMap<java.lang.Integer,
			java.lang.Double>();

		java.util.Map<java.lang.Integer, java.lang.Double> mapZeroRate = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		mapDF.put (iValueDate, 1.);

		mapZeroRate.put (iValueDate, 0.);

		for (org.drip.analytics.cashflow.CompositePeriod period : lsCouponPeriod) {
			int iPeriodPayDate = period.payDate();

			if (iValueDate >= iPeriodPayDate) continue;

			int iPeriodStartDate = period.startDate();

			int iPeriodEndDate = period.endDate();

			try {
				if (!EntryFromDiscountCurve (dc, iPeriodPayDate, iFreq,
					org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iPeriodPayDate, strDC,
						true, new org.drip.analytics.daycount.ActActDCParams (iFreq, iPeriodEndDate -
							iPeriodStartDate), strCalendar), dblZCBump, mapDF, mapZeroRate))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		try {
			if (!EntryFromDiscountCurve (dc, iWorkoutDate, iFreq,
				org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iWorkoutDate, strDC, true,
					aap, strCalendar), dblZCBump, mapDF, mapZeroRate))
				return null;

			if (iValueDate != iCashPayDate) {
				if (!EntryFromDiscountCurve (dc, iCashPayDate, iFreq,
					org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iCashPayDate, strDC,
						true, aap, strCalendar), dblZCBump, mapDF, mapZeroRate))
					return null;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iNumNode = mapDF.size();

		int iNode = 0;
		double[] adblDF = new double[iNumNode];
		double[] aiDate = new double[iNumNode];
		double[] adblZeroRate = new double[iNumNode];

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> me : mapDF.entrySet()) {
			adblDF[iNode] = me.getValue();

			aiDate[iNode] = me.getKey();

			adblZeroRate[iNode++] = mapZeroRate.get (me.getKey());
		}

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[adblDF.length - 1]; 

		for (int i = 0; i < adblDF.length - 1; ++i)
			aSCBC[i] = scbc;

		org.drip.spline.stretch.BoundarySettings bsNatural =
			org.drip.spline.stretch.BoundarySettings.NaturalStandard();

		try{
			return new DerivedZeroRate (dc,
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					("DF_STRETCH", aiDate, adblDF, aSCBC, null, bsNatural,
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE),
							org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
				("ZERO_RATE_STRETCH", aiDate, adblZeroRate, aSCBC, null, bsNatural,
					org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance from the Govvie Curve and the related Parameters
	 * 
	 * @param iFreqZC Zero Curve Frequency
	 * @param strDCZC Zero Curve Day Count
	 * @param strCalendarZC Zero Curve Calendar
	 * @param bApplyEOMAdjZC Zero Coupon EOM Adjustment Flag
	 * @param lsCouponPeriod List of bond coupon periods
	 * @param iWorkoutDate Work-out Date
	 * @param iValueDate Value Date
	 * @param iCashPayDate Cash-Pay Date
	 * @param gc Underlying Govvie Curve
	 * @param dblZCBump DC Bump
	 * @param vcp Valuation Customization Parameters
	 * @param scbc Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromGovvieCurve (
		final int iFreqZC,
		final java.lang.String strDCZC,
		final java.lang.String strCalendarZC,
		final boolean bApplyEOMAdjZC,
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod,
		final int iWorkoutDate,
		final int iValueDate,
		final int iCashPayDate,
		final org.drip.state.govvie.GovvieCurve gc,
		final double dblZCBump,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == lsCouponPeriod || 2 > lsCouponPeriod.size() || null == gc ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblZCBump) || null == scbc)
			return null;

		int iFreq = 0 == iFreqZC ? 2 : iFreqZC;
		boolean bApplyCpnEOMAdj = bApplyEOMAdjZC;
		java.lang.String strCalendar = strCalendarZC;
		double dblShiftedYield = java.lang.Double.NaN;

		try {
			dblShiftedYield = gc.yld (iWorkoutDate) + dblZCBump;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		java.lang.String strDC = null == strDCZC || strDCZC.isEmpty() ? "30/360" : strDCZC;

		if (null != vcp) {
			strDC = vcp.yieldDayCount();

			iFreq = vcp.yieldFreq();

			bApplyCpnEOMAdj = vcp.applyYieldEOMAdj();

			strCalendar = vcp.yieldCalendar();
		}

		java.util.Map<java.lang.Integer, java.lang.Double> mapDF = new java.util.TreeMap<java.lang.Integer,
			java.lang.Double>();

		java.util.Map<java.lang.Integer, java.lang.Double> mapZeroRate = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		mapDF.put (iValueDate, 1.);

		mapZeroRate.put (iValueDate, 0.);

		for (org.drip.analytics.cashflow.CompositePeriod period : lsCouponPeriod) {
			int iPeriodPayDate = period.payDate();

			if (iValueDate >= iPeriodPayDate) continue;

			int iPeriodStartDate = period.startDate();

			int iPeriodEndDate = period.endDate();

			try {
				if (!EntryFromYield (iPeriodPayDate, iFreq,
					org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iPeriodPayDate, strDC,
						bApplyCpnEOMAdj, new org.drip.analytics.daycount.ActActDCParams (iFreq,
							iPeriodEndDate - iPeriodStartDate), strCalendar), dblShiftedYield, mapDF,
								mapZeroRate))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		try {
			if (!EntryFromYield (iWorkoutDate, iFreq, org.drip.analytics.daycount.Convention.YearFraction
				(iValueDate, iWorkoutDate, strDC, bApplyCpnEOMAdj, aap, strCalendar), dblShiftedYield, mapDF,
					mapZeroRate))
				return null;

			if (iCashPayDate != iValueDate) {
				if (!EntryFromYield (iCashPayDate, iFreq, org.drip.analytics.daycount.Convention.YearFraction
					(iValueDate, iCashPayDate, strDC, bApplyCpnEOMAdj, aap, strCalendar), dblShiftedYield,
						mapDF, mapZeroRate))
					return null;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iNumNode = mapDF.size();

		int iNode = 0;
		double[] adblDF = new double[iNumNode];
		double[] aiDate = new double[iNumNode];
		double[] adblZeroRate = new double[iNumNode];

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> me : mapDF.entrySet()) {
			adblDF[iNode] = me.getValue();

			aiDate[iNode] = me.getKey();

			adblZeroRate[iNode++] = mapZeroRate.get (me.getKey());
		}

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[adblDF.length - 1]; 

		for (int i = 0; i < adblDF.length - 1; ++i)
			aSCBC[i] = scbc;

		org.drip.spline.stretch.BoundarySettings bsNatural =
			org.drip.spline.stretch.BoundarySettings.NaturalStandard();

		try {
			return new DerivedZeroRate (gc,
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					("DF_STRETCH", aiDate, adblDF, aSCBC, null, bsNatural,
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE),
							org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
				("ZERO_RATE_STRETCH", aiDate, adblZeroRate, aSCBC, null, bsNatural,
					org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance from the Input Curve and the related Parameters
	 * 
	 * @param iFreq Zero Curve Frequency
	 * @param strDayCount Zero Curve Day Count
	 * @param strCalendar Zero Curve Calendar
	 * @param bApplyEOMAdj Zero Coupon EOM Adjustment Flag
	 * @param lsCouponPeriod List of bond coupon periods
	 * @param iWorkoutDate Work-out Date
	 * @param iValueDate Value Date
	 * @param iCashPayDate Cash-Pay Date
	 * @param dc Underlying Discount Curve
	 * @param dblBump DC Bump
	 * @param vcp Valuation Customization Parameters
	 * @param scbc Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromBaseCurve (
		final int iFreq,
		final java.lang.String strDayCount,
		final java.lang.String strCalendar,
		final boolean bApplyEOMAdj,
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod,
		final int iWorkoutDate,
		final int iValueDate,
		final int iCashPayDate,
		final org.drip.state.discount.DiscountCurve dc,
		final double dblBump,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == dc) return null;

		return dc instanceof org.drip.state.govvie.GovvieCurve ? FromGovvieCurve (iFreq, strDayCount,
			strCalendar, bApplyEOMAdj, lsCouponPeriod, iWorkoutDate, iValueDate, iCashPayDate,
				(org.drip.state.govvie.GovvieCurve) dc, dblBump, vcp, scbc) : FromDiscountCurve (iFreq,
					strDayCount, strCalendar, bApplyEOMAdj, lsCouponPeriod, iWorkoutDate, iValueDate,
						iCashPayDate, dc, dblBump, vcp, scbc);
	}

	private DerivedZeroRate (
		final org.drip.state.discount.DiscountCurve dc,
		final org.drip.spline.stretch.MultiSegmentSequence mssDF,
		final org.drip.spline.stretch.MultiSegmentSequence mssZeroRate)
		throws java.lang.Exception
	{
		super (dc.epoch().julian(), dc.currency());

		if (null == (_mssDF = mssDF) || null == (_mssZeroRate = mssZeroRate))
			throw new java.lang.Exception ("DerivedZeroRate Constructor: Invalid Inputs");

		_dc = dc;
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= epoch().julian()) return 1.;

		return _mssDF.responseValue (iDate);
	}

	@Override public double df (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return df (epoch().addTenor (strTenor));
	}

	@Override public double zeroRate (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= epoch().julian()) return 1.;

		return _mssZeroRate.responseValue (iDate);
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return _dc.manifestMeasure (strInstr);
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return _dc.calibComp();
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return _dc.label();
	}

	@Override public org.drip.analytics.definition.Curve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak mmtp)
	{
		return null;
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		 return _dc.setCCIS (ccis);
	}

	@Override public org.drip.state.representation.LatentState parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return _dc.parallelShiftQuantificationMetric (dblShift);
	}

	@Override public org.drip.state.representation.LatentState customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return _dc.customTweakQuantificationMetric (rvtp);
	}

	@Override public double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("DerivedZeroRate::df => Invalid Inputs");

		return df (dt.julian());
	}

	@Override public double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return df (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveDF = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveDF += (df (iDate) + df (iDate + iQuadratureWidth));
		}

		return dblEffectiveDF / (2. * iNumQuadratures);
	}

	@Override public double effectiveDF (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("DerivedZeroRate::effectiveDF => Got null for date");

		return effectiveDF (dt1.julian(), dt2.julian());
	}

	@Override public double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("DerivedZeroRate::effectiveDF => Got bad tenor");

		org.drip.analytics.date.JulianDate dtStart = epoch();

		return effectiveDF (dtStart.addTenor (strTenor1), dtStart.addTenor (strTenor2));
	}
}
