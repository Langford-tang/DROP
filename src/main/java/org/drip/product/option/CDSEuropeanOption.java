
package org.drip.product.option;

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
 * <i>CDSEuropeanOption</i> implements the Payer/Receiver European Option on a CDS.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/README.md">Options on Fixed Income Components</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CDSEuropeanOption extends org.drip.product.option.OptionComponent {
	private boolean _bIsReceiver = false;
	private org.drip.pricer.option.FokkerPlanckGenerator _fpg = null;
	private org.drip.product.definition.CreditDefaultSwap _cds = null;

	/**
	 * CDSEuropeanOption constructor
	 * 
	 * @param strName Name
	 * @param cds The Underlying CDS Component
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsReceiver Is the Option a Receiver/Payer? TRUE - Receiver
	 * @param dblStrike Strike of the Underlying Component's Measure
	 * @param ltds Last Trading Date Setting
	 * @param fpg The Fokker Planck Pricer Instance
	 * @param csp Cash Settle Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CDSEuropeanOption (
		final java.lang.String strName,
		final org.drip.product.definition.CreditDefaultSwap cds,
		final java.lang.String strManifestMeasure,
		final boolean bIsReceiver,
		final double dblStrike,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.pricer.option.FokkerPlanckGenerator fpg,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		super (strName, cds, strManifestMeasure, dblStrike, cds.initialNotional(), ltds, csp);

		if (null == (_fpg = fpg))
			throw new java.lang.Exception ("CDSEuropeanOption ctr: Invalid Option Pricer");

		_cds = cds;
		_bIsReceiver = bIsReceiver;
	}

	/**
	 * Generate the Standard CDS European Option Measures from the Integrated Surface Variance
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqc The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblIntegratedSurfaceVariance The Integrated Surface Variance
	 * 
	 * @return The Standard CDS European Option Measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> valueFromSurfaceVariance (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblIntegratedSurfaceVariance)
	{
		if (null == valParams) return null;

		int iValueDate = valParams.valueDate();

		org.drip.product.params.LastTradingDateSetting ltds = lastTradingDateSetting();

		try {
			if (null != ltds && iValueDate >= ltds.lastTradingDate (_cds.effectiveDate().julian(),
				_cds.payCurrency()))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		long lStart = System.nanoTime();

		int iExerciseDate = exerciseDate().julian();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCDSOutput = _cds.value
			(valParams, pricerParams, csqc, vcp);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapCDSOutput || !mapCDSOutput.containsKey ("DV01") || !mapCDSOutput.containsKey
			(strManifestMeasure))
			return null;

		double dblCDSDV01 = mapCDSOutput.get ("DV01");

		double dblATMManifestMeasure = mapCDSOutput.get (strManifestMeasure);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblATMManifestMeasure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblCDSDV01))
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
			(_cds.fundingLabel());

		try {
			double dblStrike = strike();

			double dblNotional = notional();

			double dblMoneynessFactor = dblATMManifestMeasure / dblStrike;
			double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;
			double dblManifestMeasureIntrinsic = _bIsReceiver ? dblATMManifestMeasure - dblStrike : dblStrike
				- dblATMManifestMeasure;

			if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
				dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
			else if (strManifestMeasure.equalsIgnoreCase ("FairPremium") ||
				strManifestMeasure.equalsIgnoreCase ("ParSpread") || strManifestMeasure.equalsIgnoreCase
					("QuantoAdjustedParSpread"))
				dblManifestMeasurePriceTransformer = dblCDSDV01;

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer)) return null;

			double dblTTE = (iExerciseDate - iValueDate) / 365.25;

			org.drip.pricer.option.Greeks optGreek = _fpg.greeks (dblStrike, dblTTE, -(java.lang.Math.log
				(dcFunding.df (iExerciseDate))) / dblTTE, dblATMManifestMeasure, _bIsReceiver, true,
					java.lang.Math.sqrt (dblIntegratedSurfaceVariance / dblTTE));

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			double dblForwardIntrinsic = optGreek.expectedPayoff();

			double dblForwardATMIntrinsic = optGreek.expectedATMPayoff();

			double dblSpotPrice = dblForwardIntrinsic * dblManifestMeasurePriceTransformer;

			double dblFPGCharm = optGreek.charm();

			double dblFPGColor = optGreek.color();

			double dblFPGDelta = optGreek.delta();

			double dblFPGGamma = optGreek.gamma();

			double dblFPGRho = optGreek.rho();

			double dblFPGSpeed = optGreek.speed();

			double dblFPGTheta = optGreek.theta();

			double dblFPGUltima = optGreek.ultima();

			double dblFPGVanna = optGreek.vanna();

			double dblFPGVega = optGreek.vega();

			double dblFPGVeta = optGreek.veta();

			double dblFPGVomma = optGreek.vomma();

			mapResult.put ("ATMManifestMeasure", dblATMManifestMeasure);

			mapResult.put ("ATMPrice", dblForwardATMIntrinsic * dblManifestMeasurePriceTransformer);

			mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

			mapResult.put ("Charm", dblFPGCharm * dblManifestMeasurePriceTransformer);

			mapResult.put ("Color", dblFPGColor * dblManifestMeasurePriceTransformer);

			mapResult.put ("Delta", dblFPGDelta * dblManifestMeasurePriceTransformer);

			mapResult.put ("EffectiveVolatility", optGreek.effectiveVolatility());

			mapResult.put ("ExpectedATMPayoff", optGreek.expectedATMPayoff());

			mapResult.put ("ExpectedPayoff", optGreek.expectedPayoff());

			mapResult.put ("ForwardATMIntrinsic", dblForwardATMIntrinsic);

			mapResult.put ("ForwardIntrinsic", dblForwardIntrinsic);

			mapResult.put ("FPGCharm", dblFPGCharm);

			mapResult.put ("FPGColor", dblFPGColor);

			mapResult.put ("FPGDelta", dblFPGDelta);

			mapResult.put ("FPGGamma", dblFPGGamma);

			mapResult.put ("FPGRho", dblFPGRho);

			mapResult.put ("FPGSpeed", dblFPGSpeed);

			mapResult.put ("FPGTheta", dblFPGTheta);

			mapResult.put ("FPGUltima", dblFPGUltima);

			mapResult.put ("FPGVanna", dblFPGVanna);

			mapResult.put ("FPGVega", dblFPGVega);

			mapResult.put ("FPGVeta", dblFPGVeta);

			mapResult.put ("FPGVomma", dblFPGVomma);

			mapResult.put ("Gamma", dblFPGGamma * dblManifestMeasurePriceTransformer);

			mapResult.put ("IntegratedSurfaceVariance", dblIntegratedSurfaceVariance);

			mapResult.put ("ManifestMeasureIntrinsic", dblManifestMeasureIntrinsic);

			mapResult.put ("ManifestMeasureIntrinsicValue", dblManifestMeasureIntrinsic *
				dblManifestMeasurePriceTransformer);

			mapResult.put ("ManifestMeasurePriceTransformer", dblManifestMeasurePriceTransformer);

			mapResult.put ("MoneynessFactor", dblMoneynessFactor);

			mapResult.put ("Price", dblSpotPrice);

			mapResult.put ("Prob1", optGreek.prob1());

			mapResult.put ("Prob2", optGreek.prob2());

			mapResult.put ("PV", dblSpotPrice * dblNotional);

			mapResult.put ("Rho", dblFPGRho * dblManifestMeasurePriceTransformer);

			mapResult.put ("Speed", dblFPGSpeed * dblManifestMeasurePriceTransformer);

			mapResult.put ("SpotPrice", dblSpotPrice);

			mapResult.put ("Theta", dblFPGTheta * dblManifestMeasurePriceTransformer);

			mapResult.put ("Ultima", optGreek.ultima() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Upfront", dblSpotPrice);

			mapResult.put ("Vanna", optGreek.vanna() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Vega", dblFPGVega * dblManifestMeasurePriceTransformer);

			mapResult.put ("Veta", optGreek.veta() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Vomma", optGreek.vomma() * dblManifestMeasurePriceTransformer);

			return mapResult;
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("ATMManifestMeasure");

		setstrMeasureNames.add ("ATMPrice");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("Charm");

		setstrMeasureNames.add ("Color");

		setstrMeasureNames.add ("Delta");

		setstrMeasureNames.add ("EffectiveVolatility");

		setstrMeasureNames.add ("ExpectedATMPayoff");

		setstrMeasureNames.add ("ExpectedPayoff");

		setstrMeasureNames.add ("ForwardATMIntrinsic");

		setstrMeasureNames.add ("ForwardIntrinsic");

		setstrMeasureNames.add ("FPGCharm");

		setstrMeasureNames.add ("FPGColor");

		setstrMeasureNames.add ("FPGDelta");

		setstrMeasureNames.add ("FPGGamma");

		setstrMeasureNames.add ("FPGRho");

		setstrMeasureNames.add ("FPGSpeed");

		setstrMeasureNames.add ("FPGTheta");

		setstrMeasureNames.add ("FPGUltima");

		setstrMeasureNames.add ("FPGVanna");

		setstrMeasureNames.add ("FPGVega");

		setstrMeasureNames.add ("FPGVeta");

		setstrMeasureNames.add ("FPGVomma");

		setstrMeasureNames.add ("Gamma");

		setstrMeasureNames.add ("IntegratedSurfaceVariance");

		setstrMeasureNames.add ("ManifestMeasureIntrinsic");

		setstrMeasureNames.add ("ManifestMeasureIntrinsicValue");

		setstrMeasureNames.add ("ManifestMeasurePriceTransformer");

		setstrMeasureNames.add ("MoneynessFactor");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("Prob1");

		setstrMeasureNames.add ("Prob2");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("Rho");

		setstrMeasureNames.add ("Speed");

		setstrMeasureNames.add ("SpotPrice");

		setstrMeasureNames.add ("Theta");

		setstrMeasureNames.add ("Ultima");

		setstrMeasureNames.add ("Upfront");

		setstrMeasureNames.add ("Vanna");

		setstrMeasureNames.add ("Vega");

		setstrMeasureNames.add ("Veta");

		setstrMeasureNames.add ("Vomma");

		return setstrMeasureNames;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		return _cds.couponCurrency();
	}

	@Override public java.lang.String payCurrency()
	{
		return _cds.payCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return _cds.principalCurrency();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		try {
			return valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
				org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
					(org.drip.state.identifier.CustomLabel.Standard (_cds.name() + "_" + manifestMeasure())),
						valParams.valueDate(), exerciseDate().julian()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
		throws java.lang.Exception
	{
		if (null == valParams) throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		int iExerciseDate = exerciseDate().julian();

		org.drip.product.params.LastTradingDateSetting ltds = lastTradingDateSetting();

		java.lang.String strPayCurrency = payCurrency();

		if (null != ltds && iValueDate >= ltds.lastTradingDate (_cds.effectiveDate().julian(),
			strPayCurrency))
			throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCDSOutput = _cds.value
			(valParams, pricerParams, csqs, quotingParams);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapCDSOutput || !mapCDSOutput.containsKey (strManifestMeasure))
			throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		double dblFixedCleanDV01 = mapCDSOutput.get ("CleanDV01");

		double dblATMManifestMeasure = mapCDSOutput.get (strManifestMeasure);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblATMManifestMeasure))
			throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		double dblIntegratedSurfaceVariance =
			org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
				(org.drip.state.identifier.CustomLabel.Standard (_cds.name() + "_" + strManifestMeasure)),
					iValueDate, iExerciseDate);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblIntegratedSurfaceVariance))
			throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		double dblIntegratedSurfaceVolatility = java.lang.Math.sqrt (dblIntegratedSurfaceVariance);

		double dblStrike = strike();

		double dblMoneynessFactor = dblATMManifestMeasure / dblStrike;

		double dblLogMoneynessFactor = java.lang.Math.log (dblMoneynessFactor);

		double dblForwardIntrinsic = java.lang.Double.NaN;
		double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;
		double dblDPlus = (dblLogMoneynessFactor + 0.5 * dblIntegratedSurfaceVariance) /
			dblIntegratedSurfaceVolatility;
		double dblDMinus = (dblLogMoneynessFactor - 0.5 * dblIntegratedSurfaceVariance) /
			dblIntegratedSurfaceVolatility;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strPayCurrency));

		if (null == dcFunding) throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		org.drip.state.credit.CreditCurve cc = csqs.creditState (_cds.creditLabel());

		if (null == cc) throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
			dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate) * cc.survival (iExerciseDate);
		else if (strManifestMeasure.equalsIgnoreCase ("FairPremium") || strManifestMeasure.equalsIgnoreCase
			("ParSpread") || strManifestMeasure.equalsIgnoreCase ("Rate"))
			dblManifestMeasurePriceTransformer = dblFixedCleanDV01;

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer))
			throw new java.lang.Exception ("CDSEuropeanOption::pv => Invalid Inputs");

		if (_bIsReceiver)
			dblForwardIntrinsic = dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF (dblDPlus)
				- dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (dblDMinus);
		else
			dblForwardIntrinsic = dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDMinus) -
				dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDPlus);

		return dblForwardIntrinsic * dblManifestMeasurePriceTransformer;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.ProductQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

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
}
