
package org.drip.sample.dual;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.fx.ComponentPair;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.stretch.*;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.estimator.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;
import org.drip.state.inference.*;

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
 * <i>CCBSDiscountCurve</i> demonstrates the setup and construction of the Forward Curve from the CCBS
 * Quotes.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/README.md">G7 Standard Cross Currency Swap</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CCBSDiscountCurve {

	/*
	 * Construct an array of float-float swaps from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FloatFloatComponent[] MakexM6MBasisSwap (
		final JulianDate dtEffective,
		final String strPayCurrency,
		final String strCouponCurrency,
		final double dblNotional,
		final String[] astrMaturityTenor,
		final int iTenorInMonths)
		throws Exception
	{
		FloatFloatComponent[] aFFC = new FloatFloatComponent[astrMaturityTenor.length];

		ComposableFloatingUnitSetting cfusReference = new ComposableFloatingUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				"6M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFloatingUnitSetting cfusDerived = new ComposableFloatingUnitSetting (
			iTenorInMonths + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonths + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsReference = new CompositePeriodSetting (
			2,
			"6M",
			strPayCurrency,
			null,
			-1. * dblNotional,
			null,
			null,
			strPayCurrency.equalsIgnoreCase (strCouponCurrency) ? null :
				new FixingSetting (
					FixingSetting.FIXING_PRESET_STATIC,
					null,
					dtEffective.julian()
				),
			null
		);

		CompositePeriodSetting cpsDerived = new CompositePeriodSetting (
			12 / iTenorInMonths,
			iTenorInMonths + "M",
			strPayCurrency,
			null,
			1. * dblNotional,
			null,
			null,
			strPayCurrency.equalsIgnoreCase (strCouponCurrency) ? null :
				new FixingSetting (
					FixingSetting.FIXING_PRESET_STATIC,
					null,
					dtEffective.julian()
				),
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strPayCurrency,
			0
		);

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			List<Integer> lsReferenceStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				"6M",
				astrMaturityTenor[i],
				null
			);

			List<Integer> lsDerivedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				null
			);

			Stream referenceStream = new Stream (
				CompositePeriodBuilder.FloatingCompositeUnit (
					lsReferenceStreamEdgeDate,
					cpsReference,
					cfusReference
				)
			);

			Stream derivedStream = new Stream (
				CompositePeriodBuilder.FloatingCompositeUnit (
					lsDerivedStreamEdgeDate,
					cpsDerived,
					cfusDerived
				)
			);

			aFFC[i] = new FloatFloatComponent (
				referenceStream,
				derivedStream,
				csp
			);

			aFFC[i].setPrimaryCode (referenceStream.name() + "||" + derivedStream.name());
		}

		return aFFC;
	}

	private static final FixFloatComponent OTCIRS (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] MakeIRS (
		final JulianDate dtEffective,
		final String strCurrency,
		final String[] astrTenor)
		throws Exception
	{
		FixFloatComponent[] aCalibComp = new FixFloatComponent[astrTenor.length];

		for (int i = 0; i < astrTenor.length; ++i)
			aCalibComp[i] = OTCIRS (
				dtEffective,
				strCurrency,
				astrTenor[i],
				0.
			);

		return aCalibComp;
	}

	private static final ComponentPair[] MakeCCSP (
		final JulianDate dtValue,
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final String[] astrTenor,
		final int iTenorInMonths)
		throws Exception
	{
		FloatFloatComponent[] aFFCReference = MakexM6MBasisSwap (
			dtValue,
			strReferenceCurrency,
			strReferenceCurrency,
			1.,
			astrTenor,
			3
		);

		FixFloatComponent[] aIRS = MakeIRS (
			dtValue,
			strDerivedCurrency,
			astrTenor
		);

		ComponentPair[] aCCSP = new ComponentPair[astrTenor.length];

		for (int i = 0; i < aCCSP.length; ++i)
			aCCSP[i] = new ComponentPair (
				"EURUSD_" + astrTenor[i],
				aFFCReference[i],
				aIRS[i],
				null
			);

		return aCCSP;
	}

	private static final void TenorJack (
		final JulianDate dtStart,
		final String strTenor,
		final String strManifestMeasure,
		final MergedDiscountForwardCurve dc)
		throws Exception
	{
		String strCurrency = dc.currency();

		CalibratableComponent irsBespoke = OTCIRS (
			dtStart,
			strCurrency,
			strTenor,
			0.
		);

		WengertJacobian wjDFQuoteBespokeMat = dc.jackDDFDManifestMeasure (
			irsBespoke.maturityDate(),
			strManifestMeasure
		);

		System.out.println ("\t" + strTenor + " => " + wjDFQuoteBespokeMat.displayString());
	}

	/**
	 * Construct the Discount Curve
	 * 
	 * @param strReferenceCurrency Reference Currency
	 * @param strDerivedCurrency Derived Currency
	 * @param dtValue Valuation Date
	 * @param dcReference Reference Discount Curve
	 * @param fc6MReference 6M Reference Forward Curve
	 * @param fc3MReference 3M Reference Forward Curve
	 * @param dblRefDerFX Reference to Derived FC
	 * @param scbc Segment Custom Builder Control
	 * @param astrTenor Tenor Array
	 * @param adblCrossCurrencyBasis Cross Currency Basis Array
	 * @param adblSwapRate Swap Rate Array
	 * @param bBasisOnDerivedLeg TRUE - Basis on Derived Leg
	 * 
	 * @throws Exception Thrown if the Discount Curve cannot be constructed
	 */

	public static final void MakeDiscountCurve (
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final JulianDate dtValue,
		final MergedDiscountForwardCurve dcReference,
		final ForwardCurve fc6MReference,
		final ForwardCurve fc3MReference,
		final double dblRefDerFX,
		final SegmentCustomBuilderControl scbc,
		final String[] astrTenor,
		final double[] adblCrossCurrencyBasis,
		final double[] adblSwapRate,
		final boolean bBasisOnDerivedLeg)
		throws Exception
	{
		ComponentPair[] aCCSP = MakeCCSP (
			dtValue,
			strReferenceCurrency,
			strDerivedCurrency,
			astrTenor,
			3
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFundingState (dcReference);

		mktParams.setForwardState (fc3MReference);

		mktParams.setForwardState (fc6MReference);

		CurrencyPair cp = CurrencyPair.FromCode (strDerivedCurrency + "/" + strReferenceCurrency);

		FXLabel fxLabel = FXLabel.Standard (cp);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				fxLabel.fullyQualifiedName(),
				dtValue,
				cp,
				new String[] {"10Y"},
				new double[] {dblRefDerFX},
				dblRefDerFX
			)
		);

		mktParams.setFixing (
			aCCSP[0].effective(),
			fxLabel,
			dblRefDerFX
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtValue,
			strReferenceCurrency
		);

		LinearLatentStateCalibrator llsc = new LinearLatentStateCalibrator (
			scbc,
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		LatentStateStretchSpec stretchSpec = LatentStateStretchBuilder.ComponentPairDiscountStretch (
			"FIXFLOAT",
			aCCSP,
			valParams,
			mktParams,
			adblCrossCurrencyBasis,
			adblSwapRate,
			bBasisOnDerivedLeg
		);

		MergedDiscountForwardCurve dcDerived = ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
			strDerivedCurrency,
			llsc,
			new LatentStateStretchSpec[] {stretchSpec},
			valParams,
			null,
			null,
			null,
			1.
		);

		mktParams.setFundingState (dcDerived);

		System.out.println ("\t----------------------------------------------------------------");

		if (bBasisOnDerivedLeg)
			System.out.println ("\t     IRS INSTRUMENTS QUOTE REVISION FROM CCBS DERIVED BASIS INPUTS");
		else
			System.out.println ("\t     IRS INSTRUMENTS QUOTE REVISION FROM CCBS REFERENCE BASIS INPUTS");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aCCSP.length; ++i) {
			CalibratableComponent rcDerived = aCCSP[i].derivedComponent();

			CaseInsensitiveTreeMap<Double> mapOP = aCCSP[i].value (
				valParams,
				null, 
				mktParams,
				null
			);

			double dblCalibSwapRate = mapOP.get (rcDerived.name() + "[SwapRate]");

			System.out.println ("\t[" + rcDerived.effectiveDate() + " - " + rcDerived.maturityDate() + "] = " +
				FormatUtil.FormatDouble (dblCalibSwapRate, 1, 3, 100.) +
					"% | " + FormatUtil.FormatDouble (adblSwapRate[i], 1, 3, 100.) + "% | " +
						FormatUtil.FormatDouble (adblSwapRate[i] - dblCalibSwapRate, 2, 0, 10000.) + " | " +
							FormatUtil.FormatDouble (dcDerived.df (rcDerived.maturityDate()), 1, 4, 1.));
		}

		System.out.println ("\t----------------------------------------------------------------------");

		if (bBasisOnDerivedLeg)
			System.out.println ("\t     CCBS DERIVED BASIS TENOR JACOBIAN");
		else
			System.out.println ("\t     CCBS REFERENCE BASIS TENOR JACOBIAN");

		System.out.println ("\t----------------------------------------------------------------------");

		for (int i = 0; i < aCCSP.length; ++i)
			TenorJack (
				dtValue,
				astrTenor[i],
				"PV",
				dcDerived
			);
	}
}
