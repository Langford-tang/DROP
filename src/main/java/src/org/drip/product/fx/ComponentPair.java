
package org.drip.product.fx;

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
 * <i>ComponentPair</i> contains the implementation of the dual cross currency components. It is composed of
 * two different Rates Components - one each for each currency.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/README.md">FX Forwards, Cross Currency Swaps</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ComponentPair extends org.drip.product.definition.BasketProduct {
	private java.lang.String _strName = "";
	private org.drip.param.period.FixingSetting _fxFixingSetting = null;
	private org.drip.product.definition.CalibratableComponent _rcDerived = null;
	private org.drip.product.definition.CalibratableComponent _rcReference = null;

	/**
	 * ComponentPair constructor
	 * 
	 * @param strName The ComponentPair Instance Name
	 * @param rcReference The Reference Component
	 * @param rcDerived The Derived Component
	 * @param fxFixingSetting FX Fixing Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComponentPair (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent rcReference,
		final org.drip.product.definition.CalibratableComponent rcDerived,
		final org.drip.param.period.FixingSetting fxFixingSetting)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_rcDerived = rcDerived) || null ==
			(_rcReference = rcReference))
			throw new java.lang.Exception ("ComponentPair ctr: Invalid Inputs!");

		_fxFixingSetting = fxFixingSetting;
	}

	/**
	 * Retrieve the Reference Component
	 * 
	 * @return The Reference Component
	 */

	public org.drip.product.definition.CalibratableComponent referenceComponent()
	{
		return _rcReference;
	}

	/**
	 * Retrieve the Derived Component
	 * 
	 * @return The Derived Component
	 */

	public org.drip.product.definition.CalibratableComponent derivedComponent()
	{
		return _rcDerived;
	}

	/**
	 * Retrieve the FX Fixing Setting
	 * 
	 * @return The FX Fixing Setting
	 */

	public org.drip.param.period.FixingSetting fxFixingSetting()
	{
		return _fxFixingSetting;
	}

	/**
	 * Retrieve the FX Code
	 * 
	 * @return The FX Code
	 */

	public java.lang.String fxCode()
	{
		java.lang.String strDerivedComponentCouponCurrency = _rcDerived.payCurrency();

		java.lang.String strReferenceComponentCouponCurrency = _rcReference.payCurrency();

		return strDerivedComponentCouponCurrency.equalsIgnoreCase (strReferenceComponentCouponCurrency) ?
			null : strReferenceComponentCouponCurrency + "/" + strDerivedComponentCouponCurrency;
	}

	/**
	 * Generate the Derived Forward Latent State Segment Specification
	 * 
	 * @param valParams Valuation Parameters
	 * @param mktParams Market Parameters
	 * @param dblBasis The Basis on either the Reference Component or the Derived Component
	 * @param bBasisOnDerivedComponent TRUE - Apply the Basis on the Derived Component
	 * @param bBasisOnDerivedStream TRUE - Apply the Basis on the Derived Stream (FALSE - Reference Stream)
	 * 
	 * @return The Derived Forward Latent State Segment Specification
	 */

	public org.drip.state.inference.LatentStateSegmentSpec derivedForwardSpec (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double dblBasis,
		final boolean bBasisOnDerivedComponent,
		final boolean bBasisOnDerivedStream)
	{
		org.drip.product.calib.ProductQuoteSet pqs = null;
		org.drip.state.identifier.ForwardLabel forwardLabel = null;

		org.drip.product.definition.CalibratableComponent comp = derivedComponent();

		if (comp instanceof org.drip.product.rates.DualStreamComponent)
			forwardLabel = ((org.drip.product.rates.DualStreamComponent)
				comp).derivedStream().forwardLabel();
		else {
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
				mapForwardLabel = comp.forwardLabel();

			if (null != mapForwardLabel && 0 != mapForwardLabel.size())
				forwardLabel = mapForwardLabel.get ("BASE");
		}

		try { 
			pqs = comp.calibQuoteSet (new org.drip.state.representation.LatentStateSpecification[] {new
				org.drip.state.representation.LatentStateSpecification
					(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
						org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
							forwardLabel)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOP = value (valParams, null,
			mktParams, null);

		org.drip.product.definition.CalibratableComponent rcReference = referenceComponent();

		java.lang.String strReferenceComponentName = rcReference.name();

		org.drip.product.definition.CalibratableComponent rcDerived = derivedComponent();

		java.lang.String strDerivedComponentName = rcDerived.name();

		java.lang.String strReferenceComponentPV = strReferenceComponentName + "[PV]";

		if (!bBasisOnDerivedComponent) {
			java.lang.String strReferenceComponentDerivedStreamCleanDV01 = strReferenceComponentName +
				"[DerivedCleanDV01]";
			java.lang.String strReferenceComponentReferenceStreamCleanDV01 = strReferenceComponentName +
				"[ReferenceCleanDV01]";

			if (null == mapOP || !mapOP.containsKey (strReferenceComponentPV) || !mapOP.containsKey
				(strReferenceComponentReferenceStreamCleanDV01) || !mapOP.containsKey
					(strReferenceComponentDerivedStreamCleanDV01))
				return null;

			if (!pqs.set ("PV", -1. * (mapOP.get (strReferenceComponentPV) + 10000. * (bBasisOnDerivedStream
				? mapOP.get (strReferenceComponentDerivedStreamCleanDV01) : mapOP.get
					(strReferenceComponentReferenceStreamCleanDV01)) * dblBasis)))
				return null;
		} else {
			java.lang.String strDerivedComponentReferenceStreamCleanDV01 = strDerivedComponentName +
				"[ReferenceCleanDV01]";
			java.lang.String strDerivedComponentDerivedStreamCleanDV01 = strDerivedComponentName +
				"[DerivedCleanDV01]";

			if (null == mapOP || !mapOP.containsKey (strReferenceComponentPV) || !mapOP.containsKey
				(strDerivedComponentReferenceStreamCleanDV01) || !mapOP.containsKey
					(strDerivedComponentDerivedStreamCleanDV01))
				return null;

			if (!pqs.set ("PV", -1. * (mapOP.get (strReferenceComponentPV) + 10000. * (bBasisOnDerivedStream
				? mapOP.get (strDerivedComponentDerivedStreamCleanDV01) : mapOP.get
					(strDerivedComponentReferenceStreamCleanDV01)) * dblBasis)))
				return null;
		}

		try {
			return new org.drip.state.inference.LatentStateSegmentSpec (comp, pqs);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Derived Funding/Forward Merged Latent State Segment Specification
	 * 
	 * @param valParams Valuation Parameters
	 * @param mktParams Market Parameters
	 * @param dblReferenceComponentBasis The Reference Component Basis
	 * @param bBasisOnDerivedLeg TRUE - Apply basis on the Derived Leg
	 * @param dblSwapRate The Swap Rate
	 * 
	 * @return The Derived Forward/Funding Latent State Segment Specification
	 */

	public org.drip.state.inference.LatentStateSegmentSpec derivedFundingForwardSpec (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double dblReferenceComponentBasis,
		final boolean bBasisOnDerivedLeg,
		final double dblSwapRate)
	{
		double dblFX = 1.;
		org.drip.product.calib.ProductQuoteSet pqs = null;
		org.drip.state.identifier.ForwardLabel forwardLabel = null;
		org.drip.state.identifier.FundingLabel fundingLabel = null;

		org.drip.product.definition.CalibratableComponent compDerived = derivedComponent();

		org.drip.product.definition.CalibratableComponent compReference = referenceComponent();

		if (compDerived instanceof org.drip.product.rates.DualStreamComponent) {
			org.drip.product.rates.Stream streamDerived = ((org.drip.product.rates.DualStreamComponent)
				compDerived).derivedStream();

			forwardLabel = streamDerived.forwardLabel();

			fundingLabel = streamDerived.fundingLabel();
		} else {
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
				mapForwardLabel = compDerived.forwardLabel();

			org.drip.state.identifier.FundingLabel fundingLabelDerived = compDerived.fundingLabel();

			if (null != mapForwardLabel && 0 != mapForwardLabel.size())
				forwardLabel = mapForwardLabel.get ("DERIVED");

			if (null != fundingLabelDerived) fundingLabel = fundingLabelDerived;
		}

		try { 
			pqs = compDerived.calibQuoteSet (new org.drip.state.representation.LatentStateSpecification[]
				{new org.drip.state.representation.LatentStateSpecification
					(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING,
						org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
							fundingLabel), new org.drip.state.representation.LatentStateSpecification
								(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
									org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
										forwardLabel)});

			if (null != _fxFixingSetting && org.drip.param.period.FixingSetting.FIXING_PRESET_STATIC ==
				_fxFixingSetting.type()) {
				org.drip.state.fx.FXCurve fxfc = mktParams.fxState (fxLabel()[0]);

				if (null == fxfc) return null;

				dblFX = fxfc.fx (_fxFixingSetting.staticDate());
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOP = compReference.value
			(valParams, null, mktParams, null);

		if (null == mapOP || !mapOP.containsKey ("PV") || !pqs.set ("SwapRate", dblSwapRate)) return null;

		if (bBasisOnDerivedLeg) {
			if (!mapOP.containsKey ("DerivedCleanDV01") || !pqs.set ("PV", dblFX * (mapOP.get ("PV") + 10000.
				* mapOP.get ("DerivedCleanDV01") * dblReferenceComponentBasis)))
				return null;
		} else {
			if (!mapOP.containsKey ("ReferenceCleanDV01") || !pqs.set ("PV", -1. * dblFX * (mapOP.get ("PV")
				+ 10000. * mapOP.get ("ReferenceCleanDV01") * dblReferenceComponentBasis)))
				return null;
		}

		try {
			return new org.drip.state.inference.LatentStateSegmentSpec (compDerived, pqs);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.state.identifier.FXLabel[] fxLabel()
	{
		java.lang.String strReferenceCurrency = _rcReference.payCurrency();

		java.lang.String strDerivedCurrency = _rcDerived.payCurrency();

		return new org.drip.state.identifier.FXLabel[] {org.drip.state.identifier.FXLabel.Standard
			(strReferenceCurrency + "/" + strDerivedCurrency), org.drip.state.identifier.FXLabel.Standard
				(strDerivedCurrency + "/" + strReferenceCurrency)};
	}

	@Override public org.drip.product.definition.Component[] components()
	{
		return new org.drip.product.definition.Component[] {_rcReference, _rcDerived};
	}

	@Override public int measureAggregationType (
		final java.lang.String strMeasureName)
	{
		return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_UNIT_ACCUMULATE;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOutput = super.value
			(valParams, pricerParams, csqs, vcp);

		if (null == mapOutput) return null;

		org.drip.product.definition.CalibratableComponent rcReference = referenceComponent();

		org.drip.product.definition.CalibratableComponent rcDerived = derivedComponent();

		java.lang.String strReferenceCompName = rcReference.name();

		java.lang.String strDerivedCompName = rcDerived.name();

		java.lang.String strDerivedCompPV = strDerivedCompName + "[PV]";
		java.lang.String strReferenceCompPV = strReferenceCompName + "[PV]";
		java.lang.String strDerivedCompDerivedDV01 = strDerivedCompName + "[DerivedCleanDV01]";
		java.lang.String strReferenceCompDerivedDV01 = strReferenceCompName + "[DerivedCleanDV01]";
		java.lang.String strDerivedCompReferenceDV01 = strDerivedCompName + "[ReferenceCleanDV01]";
		java.lang.String strReferenceCompReferenceDV01 = strReferenceCompName + "[ReferenceCleanDV01]";
		java.lang.String strDerivedCompCumulativeConvexityPremium = strDerivedCompName +
			"[CumulativeConvexityAdjustmentPremium]";
		java.lang.String strDerivedCompCumulativeConvexityAdjustment = strDerivedCompName +
			"[CumulativeConvexityAdjustmentFactor]";
		java.lang.String strReferenceCompCumulativeConvexityPremium = strReferenceCompName +
			"[CumulativeConvexityAdjustmentPremium]";
		java.lang.String strReferenceCompCumulativeConvexityAdjustment = strReferenceCompName +
			"[QuantoAdjustmentFactor]";

		if (!mapOutput.containsKey (strDerivedCompPV) || !mapOutput.containsKey (strReferenceCompPV) ||
			!mapOutput.containsKey (strReferenceCompReferenceDV01) || !mapOutput.containsKey
				(strReferenceCompDerivedDV01) || !mapOutput.containsKey (strDerivedCompReferenceDV01) ||
					!mapOutput.containsKey (strDerivedCompDerivedDV01) || !mapOutput.containsKey
						(strDerivedCompCumulativeConvexityPremium) || !mapOutput.containsKey
							(strReferenceCompCumulativeConvexityPremium)) {
			mapOutput.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

			return mapOutput;
		}

		double dblDerivedCompPV = mapOutput.get (strDerivedCompPV);

		double dblReferenceCompPV = mapOutput.get (strReferenceCompPV);

		double dblDerivedCompDerivedDV01 = mapOutput.get (strDerivedCompDerivedDV01);

		double dblDerivedCompReferenceDV01 = mapOutput.get (strDerivedCompReferenceDV01);

		double dblReferenceCompDerivedDV01 = mapOutput.get (strReferenceCompDerivedDV01);

		double dblReferenceCompReferenceDV01 = mapOutput.get (strReferenceCompReferenceDV01);

		mapOutput.put ("ReferenceCompReferenceBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblReferenceCompReferenceDV01);

		mapOutput.put ("ReferenceCompDerivedBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblReferenceCompDerivedDV01);

		mapOutput.put ("DerivedCompReferenceBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblDerivedCompReferenceDV01);

		mapOutput.put ("DerivedCompDerivedBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblDerivedCompDerivedDV01);

		if (mapOutput.containsKey (strReferenceCompCumulativeConvexityAdjustment))
			mapOutput.put ("ReferenceCumulativeConvexityAdjustmentFactor", mapOutput.get
				(strReferenceCompCumulativeConvexityAdjustment));

		double dblReferenceCumulativeConvexityAdjustmentPremium = mapOutput.get
			(strReferenceCompCumulativeConvexityPremium);

		mapOutput.put ("ReferenceCumulativeConvexityAdjustmentPremium",
			dblReferenceCumulativeConvexityAdjustmentPremium);

		if (mapOutput.containsKey (strDerivedCompCumulativeConvexityAdjustment))
			mapOutput.put ("DerivedCumulativeConvexityAdjustmentFactor", mapOutput.get
				(strDerivedCompCumulativeConvexityAdjustment));

		double dblDerivedCumulativeConvexityAdjustmentPremium = mapOutput.get
			(strDerivedCompCumulativeConvexityPremium);

		mapOutput.put ("DerivedCumulativeConvexityAdjustmentPremium",
			dblDerivedCumulativeConvexityAdjustmentPremium);

		try {
			mapOutput.put ("CumulativeConvexityAdjustmentPremium", _rcReference.initialNotional() *
				dblReferenceCumulativeConvexityAdjustmentPremium + _rcDerived.initialNotional() *
					dblDerivedCumulativeConvexityAdjustmentPremium);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		mapOutput.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		return mapOutput;
	}
}
