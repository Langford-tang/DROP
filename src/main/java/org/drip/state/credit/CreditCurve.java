
package org.drip.state.credit;

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
 * <i>CreditCurve</i> is the stub for the survival curve functionality. It extends the Curve object by
 * exposing the following functions:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Set of curve and market identifiers
 *  	</li>
 *  	<li>
 * 			Recovery to a specific date/tenor, and effective recovery between a date interval
 *  	</li>
 *  	<li>
 * 			Hazard Rate to a specific date/tenor, and effective hazard rate between a date interval
 *  	</li>
 *  	<li>
 * 			Survival to a specific date/tenor, and effective survival between a date interval
 *  	</li>
 *  	<li>
 *  		Set/unset date of specific default
 *  	</li>
 *  	<li>
 *  		Generate scenario curves from the base credit curve (flat/parallel/custom)
 *  	</li>
 *  	<li>
 *  		Set/unset the Curve Construction Inputs, Latent State, and the Manifest Metrics
 *  	</li>
 *  	<li>
 *  		Serialization/De-serialization to and from Byte Arrays
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/credit/README.md">Credit Latent State Curve Representation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditCurve implements org.drip.analytics.definition.Curve {
	private static final int NUM_DF_QUADRATURES = 5;

	protected java.lang.String _strCurrency = "";
	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;
	protected org.drip.state.identifier.EntityCDSLabel _label = null;
	protected int _iSpecificDefaultDate = java.lang.Integer.MIN_VALUE;

	/*
	 * Manifest Measure Inputs that go into building the Curve Span
	 */

	protected boolean _bFlat = false;
	protected double[] _adblCalibQuote = null;
	protected java.lang.String[] _astrCalibMeasure = null;
	protected org.drip.state.govvie.GovvieCurve _gc = null;
	protected org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
	protected org.drip.param.valuation.ValuationParams _valParam = null;
	protected org.drip.param.pricer.CreditPricerParams _pricerParam = null;
	protected org.drip.param.market.LatentStateFixingsContainer _lsfc = null;
	protected org.drip.product.definition.CalibratableComponent[] _aCalibInst = null;
	protected org.drip.param.valuation.ValuationCustomizationParams _quotingParams = null;
	protected org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> _mapMeasure = null;
	protected org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
		_mapQuote = null;

	protected CreditCurve (
		final int iEpochDate,
		final org.drip.state.identifier.EntityCDSLabel label,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_iEpochDate = iEpochDate) || null == (_label =
			label) || null == (_strCurrency = strCurrency) || _strCurrency.isEmpty())
			throw new java.lang.Exception ("CreditCurve ctr: Invalid Inputs");
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	@Override public java.lang.String currency()
	{
		return _strCurrency;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iEpochDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Set the Specific Default Date
	 * 
	 * @param iSpecificDefaultDate Date of Specific Default
	 * 
	 * @return TRUE if successful
	 */

	public boolean setSpecificDefault (
		final int iSpecificDefaultDate)
	{
		_iSpecificDefaultDate = iSpecificDefaultDate;
		return true;
	}

	/**
	 * Remove the Specific Default Date
	 * 
	 * @return TRUE if successful
	 */

	public boolean unsetSpecificDefault()
	{
		_iSpecificDefaultDate = java.lang.Integer.MIN_VALUE;
		return true;
	}

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public abstract double survival (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("CreditCurve::survival => Invalid Date");

		return survival (dt.julian());
	}

	/**
	 * Calculate the survival to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::survival => Bad tenor");

		return survival (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return survival (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveSurvival = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveSurvival += (survival (iDate) + survival (iDate + iQuadratureWidth));
		}

		return dblEffectiveSurvival / (2. * iNumQuadratures);
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::effectiveSurvival => Invalid date");

		return effectiveSurvival (dt1.julian(), dt2.julian());
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 tenors
	 * 
	 * @param strTenor1 First tenor
	 * @param strTenor2 Second tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("CreditCurve::effectiveSurvival => bad tenor");

		return effectiveSurvival (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor
			(strTenor1), new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor2));
	}

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public abstract double recovery (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("CreditCurve::recovery => Invalid Date");

		return recovery (dt.julian());
	}

	/**
	 * Calculate the recovery rate to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::recovery => Invalid Tenor");

		return recovery (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return recovery (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveRecovery = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveRecovery += (recovery (iDate) + recovery (iDate + iQuadratureWidth));
		}

		return dblEffectiveRecovery / (2. * iNumQuadratures);
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::effectiveRecovery => Invalid date");

		return effectiveRecovery (dt1.julian(), dt2.julian());
	}

	/**
	 * Calculate the time-weighted recovery between a pair of tenors
	 * 
	 * @param strTenor1 First Tenor
	 * @param strTenor2 Second Tenor
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("CreditCurve::effectiveRecovery => Invalid tenor");

		return effectiveRecovery (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor
			(strTenor1), new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor2));
	}

	/**
	 * Calculate the hazard rate between a pair of forward dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::hazard => Invalid dates");

		if (dt1.julian() < _iEpochDate || dt2.julian() < _iEpochDate) return 0.;

		return 365.25 / (dt2.julian() - dt1.julian()) * java.lang.Math.log (survival (dt1) / survival (dt2));
	}

	/**
	 * Calculate the hazard rate to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		return hazard (dt, new org.drip.analytics.date.JulianDate (_iEpochDate));
	}

	/**
	 * Calculate the hazard rate to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::hazard => Bad Tenor");

		return hazard (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Create a flat hazard curve from the inputs
	 * 
	 * @param dblFlatNodeValue Flat hazard node value
	 * @param bSingleNode Uses a single node for Calibration (True)
	 * @param dblRecovery (Optional) Recovery to be used in creation of the flat curve
	 * 
	 * @return New CreditCurve instance
	 */

	public abstract CreditCurve flatCurve (
		final double dblFlatNodeValue,
		final boolean bSingleNode,
		final double dblRecovery);

	/**
	 * Set the calibration inputs for the CreditCurve
	 * 
	 * @param valParam ValuationParams
	 * @param bFlat Flat calibration desired (True)
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param pricerParam PricerParams
	 * @param aCalibInst Array of calibration instruments
	 * @param adblCalibQuote Array of calibration quotes
	 * @param astrCalibMeasure Array of calibration measures
	 * @param lsfc Latent State Fixings Container
	 * @param quotingParams Quoting Parameters
	 */

	public void setInstrCalibInputs (
		final org.drip.param.valuation.ValuationParams valParam,
		final boolean bFlat,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		_dc = dc;
		_gc = gc;
		_lsfc = lsfc;
		_bFlat = bFlat;
		_valParam = valParam;
		_aCalibInst = aCalibInst;
		_pricerParam = pricerParam;
		_quotingParams = quotingParams;
		_adblCalibQuote = adblCalibQuote;
		_astrCalibMeasure = astrCalibMeasure;

		_mapQuote = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

		_mapMeasure = new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		for (int i = 0; i < aCalibInst.length; ++i) {
			_mapMeasure.put (_aCalibInst[i].primaryCode(), astrCalibMeasure[i]);

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapManifestMeasureCalibQuote
				= new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			mapManifestMeasureCalibQuote.put (_astrCalibMeasure[i], adblCalibQuote[i]);

			_mapQuote.put (_aCalibInst[i].primaryCode(), mapManifestMeasureCalibQuote);

			java.lang.String[] astrSecCode = _aCalibInst[i].secondaryCode();

			if (null != astrSecCode) {
				for (int j = 0; j < astrSecCode.length; ++j)
					_mapQuote.put (astrSecCode[j], mapManifestMeasureCalibQuote);
			}
		}
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return false;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return _aCalibInst;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		if (null == _mapQuote || 0 == _mapQuote.size() || null == strInstr || strInstr.isEmpty() ||
			!_mapQuote.containsKey (strInstr))
			return null;

		return _mapQuote.get (strInstr);
	}
}
