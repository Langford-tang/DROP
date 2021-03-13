
package org.drip.product.fra;

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
 * <i>FRAStandardCapFloor</i> implements the Caps and Floors on the Standard FRA.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fra/README.md">Standard/Market FRAs - Caps/Floors</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FRAStandardCapFloor extends org.drip.product.option.OptionComponent
{
	private boolean _bIsCap = false;
	private double _dblStrike = java.lang.Double.NaN;
	private org.drip.product.rates.Stream _stream = null;

	private java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> _lsFRACapFloorlet = new
		java.util.ArrayList<org.drip.product.fra.FRAStandardCapFloorlet>();

	/**
	 * FRAStandardCapFloor constructor
	 * 
	 * @param strName Name of the Cap/Floor Instance
	 * @param stream The Underlying Stream
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCap Is the FRA Option a Cap? TRUE - YES
	 * @param dblStrike Strike of the Underlying Component's Measure
	 * @param ltds Last Trading Date Setting
	 * @param csp Cash Settle Parameters
	 * @param fpg The Fokker Planck Pricer Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FRAStandardCapFloor (
		final java.lang.String strName,
		final org.drip.product.rates.Stream stream,
		final java.lang.String strManifestMeasure,
		final boolean bIsCap,
		final double dblStrike,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.param.valuation.CashSettleParams csp,
		final org.drip.pricer.option.FokkerPlanckGenerator fpg)
		throws java.lang.Exception
	{
		super (strName, org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard
			(stream.effective(), stream.forwardLabel(), dblStrike), strManifestMeasure, dblStrike,
				stream.initialNotional(), ltds, csp);

		if (null == (_stream = stream) || !org.drip.numerical.common.NumberUtil.IsValid (_dblStrike = dblStrike))
			throw new java.lang.Exception ("FRAStandardCapFloor Constructor => Invalid Inputs");

		_bIsCap = bIsCap;

		org.drip.state.identifier.ForwardLabel fri = _stream.forwardLabel();

		if (null == fri)
			throw new java.lang.Exception ("FRAStandardCapFloor Constructor => Invalid Floater Index");

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods()) {
			org.drip.product.fra.FRAStandardComponent fra =
				org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard (new
					org.drip.analytics.date.JulianDate (period.startDate()), fri, _dblStrike);

			_lsFRACapFloorlet.add (new org.drip.product.fra.FRAStandardCapFloorlet (fra.name() + "::LET",
				fra, strManifestMeasure, _bIsCap, _dblStrike, _stream.notional (period.startDate()), ltds,
					fpg, csp));
		}
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		double dblPV = 0.;
		double dblPrice = 0.;
		double dblUpfront = 0.;
		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = null;

		long lStart = System.nanoTime();

		final int iValueDate = valParams.valueDate();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapStreamResult = _stream.value
			(valParams, pricerParams, csqs, vcp);

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFRAResult = fracfl.value
				(valParams, pricerParams, csqs, vcp);

			if (null == mapFRAResult) continue;

			if (mapFRAResult.containsKey ("Price")) dblPrice += mapFRAResult.get ("Price");

			if (mapFRAResult.containsKey ("PV")) dblPV += mapFRAResult.get ("PV");

			if (mapFRAResult.containsKey ("Upfront")) dblUpfront += mapFRAResult.get ("Upfront");
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapResult.put ("ATMFairPremium", mapStreamResult.get ("FairPremium"));

		mapResult.put ("Price", dblPrice);

		mapResult.put ("PV", dblPV);

		mapResult.put ("Upfront", dblUpfront);

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				double dblCapFloorletPrice = 0.;

				for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
					int iExerciseDate = fracfl.exerciseDate().julian();

					if (iExerciseDate <= iValueDate) continue;

					dblCapFloorletPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblVolatility);
				}

				return dblCapFloorletPrice;
			}
		};

		try {
			fpfo = (new org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblPrice, funcVolPricer,
				null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return mapResult;
		}

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		if (null != fpfo && fpfo.containsRoot())
			mapResult.put ("FlatVolatility", fpfo.getRoot());
		else
			mapResult.put ("FlatVolatility", java.lang.Double.NaN);

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("ATMFairPremium");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("FlatVolatility");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("Upfront");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		double dblPV = 0.;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet)
			dblPV += fracfl.pv (valParams, pricerParams, csqc, vcp);

		return dblPV;
	}

	/**
	 * Retrieve the Stream Instance Underlying the Cap
	 * 
	 * @return The Stream Instance Underlying the Cap
	 */

	public org.drip.product.rates.Stream stream()
	{
		return _stream;
	}

	/**
	 * Indicate if this is a Cap or Floor
	 * 
	 * @return TRUE - The Product is a Cap
	 */

	public boolean isCap()
	{
		return _bIsCap;
	}

	/**
	 * Retrieve the List of the Underlying Caplets/Floorlets
	 * 
	 * @return The List of the Underlying Caplets/Floorlets
	 */

	public java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> capFloorlets()
	{
		return _lsFRACapFloorlet;
	}

	/**
	 * Compute the ATM Cap/Floor Price from the Flat Volatility
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblFlatVolatility The Flat Volatility
	 * 
	 * @return The Cap/Floor ATM Price
	 * 
	 * @throws java.lang.Exception Thrown if the ATM Price cannot be calculated
	 */

	public double atmPriceFromVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblFlatVolatility)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.numerical.common.NumberUtil.IsValid (dblFlatVolatility))
			throw new java.lang.Exception ("FRAStandardCapFloor::atmPriceFromVolatility => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		double dblPrice = 0.;

		org.drip.product.fra.FRAStandardCapFloorlet fraLeading = _lsFRACapFloorlet.get (0);

		java.lang.String strManifestMeasure = fraLeading.manifestMeasure();

		org.drip.pricer.option.FokkerPlanckGenerator fpg = fraLeading.pricer();

		org.drip.product.params.LastTradingDateSetting ltds = fraLeading.lastTradingDateSetting();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapStreamResult = _stream.value
			(valParams, pricerParams, csqs, vcp);

		if (null == mapStreamResult || !mapStreamResult.containsKey ("FairPremium"))
			throw new java.lang.Exception
				("FRAStandardCapFloor::atmPriceFromVolatility => Cannot calculate Fair Premium");

		double dblCapATMFairPremium = mapStreamResult.get ("FairPremium");

		org.drip.state.identifier.ForwardLabel forwardLabel = _stream.forwardLabel();

		java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> lsATMFRACapFloorlet = new
			java.util.ArrayList<org.drip.product.fra.FRAStandardCapFloorlet>();

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods()) {
			org.drip.product.fra.FRAStandardComponent fra =
				org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard (new
					org.drip.analytics.date.JulianDate (period.startDate()), forwardLabel,
						dblCapATMFairPremium);

			lsATMFRACapFloorlet.add (new org.drip.product.fra.FRAStandardCapFloorlet (fra.name() + "::LET",
				fra, strManifestMeasure, _bIsCap, dblCapATMFairPremium, _stream.notional
					(period.startDate()), ltds, fpg, cashSettleParams()));
		}

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : lsATMFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			dblPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblFlatVolatility);
		}

		return dblPrice;
	}

	/**
	 * Imply the Flat Cap/Floor Volatility from the Calibration ATM Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCalibPrice The Calibration Price
	 * 
	 * @return The Cap/Floor Flat Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Flat Volatility cannot be calculated
	 */

	public double volatilityFromATMPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCalibPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.numerical.common.NumberUtil.IsValid (dblCalibPrice))
			throw new java.lang.Exception ("FRAStandardCapFloor::volatilityFromATMPrice => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				return atmPriceFromVolatility (valParams, pricerParams, csqs, vcp, dblVolatility);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCalibPrice, funcVolPricer, null,
				org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("FRAStandardCapFloor::volatilityFromATMPrice => Cannot imply Flat Vol");

		return fpfo.getRoot();
	}

	/**
	 * Compute the Cap/Floor Price from the Flat Volatility
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblFlatVolatility The Flat Volatility
	 * 
	 * @return The Cap/Floor Price
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public double priceFromFlatVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblFlatVolatility)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.numerical.common.NumberUtil.IsValid (dblFlatVolatility))
			throw new java.lang.Exception ("FRAStandardCapFloor::priceFromFlatVolatility => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		double dblPrice = 0.;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			dblPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblFlatVolatility);
		}

		return dblPrice;
	}

	/**
	 * Imply the Flat Cap/Floor Volatility from the Calibration Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCalibPrice The Calibration Price
	 * 
	 * @return The Cap/Floor Flat Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public double flatVolatilityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCalibPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.numerical.common.NumberUtil.IsValid (dblCalibPrice))
			throw new java.lang.Exception ("FRAStandardCapFloor::flatVolatilityFromPrice => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				return priceFromFlatVolatility (valParams, pricerParams, csqs, vcp, dblVolatility);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCalibPrice, funcVolPricer, null,
				org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("FRAStandardCapFloor::flatVolatilityFromPrice => Cannot imply Flat Vol");

		return fpfo.getRoot();
	}

	/**
	 * Strip the Piece-wise Constant Forward Rate Volatility of the Unmarked Segment of the Volatility Term
	 *  Structure
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCapVolatility The Flat Cap Volatility
	 * @param mapDateVol The Date/Volatility Map
	 * 
	 * @return TRUE - The Forward Rate Volatility of the Unmarked Segment of the Volatility Term Structure
	 * 	successfully implied
	 */

	public boolean stripPiecewiseForwardVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCapVolatility,
		final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Double> mapDateVol)
	{
		if (null == valParams || null == mapDateVol) return false;

		int iIndex = 0;
		double dblPreceedingCapFloorletPV = 0.;
		double dblCapPrice = java.lang.Double.NaN;
		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = null;

		try {
			dblCapPrice = priceFromFlatVolatility (valParams, pricerParams, csqs, vcp, dblCapVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		final int iValueDate = valParams.valueDate();

		final java.util.List<java.lang.Integer> lsCalibCapFloorletIndex = new
			java.util.ArrayList<java.lang.Integer>();

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			if (mapDateVol.containsKey (dtExercise)) {
				double dblExerciseVolatility = mapDateVol.get (dtExercise);

				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCapFloorlet =
					fracfl.valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
						dblExerciseVolatility * dblExerciseVolatility * (iExerciseDate - iValueDate) /
							365.25);

				if (null == mapCapFloorlet || !mapCapFloorlet.containsKey ("Price")) return false;

				dblPreceedingCapFloorletPV += mapCapFloorlet.get ("Price");
			} else
				lsCalibCapFloorletIndex.add (iIndex);

			++iIndex;
		}

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				int iIndex = 0;
				double dblSucceedingCapFloorletPV = 0.;

				for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
					int iExerciseDate = fracfl.exerciseDate().julian();

					if (iExerciseDate <= iValueDate) continue;

					if (lsCalibCapFloorletIndex.contains (iIndex)) {
						java.util.Map<java.lang.String, java.lang.Double> mapOutput =
							fracfl.valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
								dblVolatility * dblVolatility * (iExerciseDate - iValueDate) / 365.25);
	
						if (null == mapOutput || !mapOutput.containsKey ("Price"))
							throw new java.lang.Exception
								("FRAStandardCapFloor::implyVolatility => Cannot generate Calibration Measure");
	
						dblSucceedingCapFloorletPV += mapOutput.get ("Price");
					}

					++iIndex;
				}

				return dblSucceedingCapFloorletPV;
			}
		};

		try {
			fpfo = (new org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCapPrice -
				dblPreceedingCapFloorletPV, funcVolPricer, null,
					org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
						(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
							5.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		if (null == fpfo || !fpfo.containsRoot()) return false;

		double dblVolatility = fpfo.getRoot();

		iIndex = 0;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			if (lsCalibCapFloorletIndex.contains (iIndex))
				mapDateVol.put (fracfl.exerciseDate(), dblVolatility);

			++iIndex;
		}

		return true;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs || null == valParams || !(pqs instanceof
			org.drip.product.calib.VolatilityProductQuoteSet))
			return null;

		if (valParams.valueDate() > maturityDate().julian()) return null;

		double dblOptionPV = 0.;
		org.drip.product.calib.VolatilityProductQuoteSet vpqs =
			(org.drip.product.calib.VolatilityProductQuoteSet) pqs;

		if (!vpqs.containsOptionPV()) return null;

		try {
			dblOptionPV = vpqs.optionPV();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.product.fra.FRAStandardCapFloorlet frascf : _lsFRACapFloorlet) {
			org.drip.state.estimator.PredictorResponseWeightConstraint prwcFRASCF = frascf.volatilityPRWC
				(valParams, pricerParams, csqs, vcp, pqs);

			if (null == prwcFRASCF || !prwc.absorb (prwcFRASCF)) return null;
		}

		return !prwc.updateValue (dblOptionPV) || !prwc.updateDValueDManifestMeasure ("OptionPV", 1.) ? null
			: prwc;
	}
}
