
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
 * <i>ZeroRateDiscountCurve</i> manages the Discounting Latent State, using the Zero Rate as the State
 * Response Representation. It exports the following functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Compute the discount factor, forward rate, or the zero rate from the Zero Rate Latent State
 *  	</li>
 *  	<li>
 *  		Create a ForwardRateEstimator instance for the given Index
 *  	</li>
 *  	<li>
 *  		Retrieve Array of the Calibration Components
 *  	</li>
 *  	<li>
 *  		Retrieve the Curve Construction Input Set
 *  	</li>
 *  	<li>
 *  		Compute the Jacobian of the Discount Factor Latent State to the input Quote
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 *  	</li>
 *  	<li>
 *  		Serialize into and de-serialize out of byte array
 *  	</li>
 *  </ul>
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

public class ZeroRateDiscountCurve extends org.drip.state.discount.MergedDiscountForwardCurve {
	private org.drip.spline.grid.Span _span = null;
	private double _dblRightFlatForwardRate = java.lang.Double.NaN;
	private org.drip.analytics.input.CurveConstructionInputSet _ccis = null;

	private ZeroRateDiscountCurve shiftManifestMeasure (
		final double[] adblShiftedManifestMeasure)
	{
		return null;
	}

	/**
	 * ZeroRateDiscountCurve constructor
	 * 
	 * @param strCurrency Currency
	 * @param span The Span Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ZeroRateDiscountCurve (
		final java.lang.String strCurrency,
		final org.drip.spline.grid.Span span)
		throws java.lang.Exception
	{
		super ((int) span.left(), strCurrency, null);

		_dblRightFlatForwardRate = (_span = span).calcResponseValue (_span.right());
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		int iStartDate = epoch().julian();

		if (iDate <= iStartDate) return 1.;

		return (java.lang.Math.exp (-1. * zero (iDate) * (iDate - iStartDate) / 365.25)) * turnAdjust
			(iStartDate, iDate);
	}

	public double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		int iStartDate = epoch().julian();

		if (iDate1 < iStartDate || iDate2 < iStartDate) return 0.;

		return 365.25 / (iDate2 - iDate1) * java.lang.Math.log (df (iDate1) / df (iDate2));
	}

	@Override public double zero (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _span.left()) return 1.;

		return iDate <= _span.right() ? _span.calcResponseValue (iDate) : _dblRightFlatForwardRate;
	}

	@Override public org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		return null;
	}

	@Override public java.lang.String latentStateQuantificationMetric()
	{
		return org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public ZeroRateDiscountCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblShift)) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		int iNumComp = aCC.length;
		double[] adblShiftedManifestMeasure = new double[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			adblShiftedManifestMeasure[i] += dblShift;

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public ZeroRateDiscountCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblShift)) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		int iNumComp = aCC.length;
		double[] adblShiftedManifestMeasure = new double[iNumComp];

		if (iSpanIndex >= iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i)
			adblShiftedManifestMeasure[i] += (i == iSpanIndex ? dblShift : 0.);

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public org.drip.state.discount.MergedDiscountForwardCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		if (null == rvtp) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = _ccis.quoteMap();

		if (null == mapQuote || 0 == mapQuote.size()) return null;

		int iNumComp = aCC.length;
		double[] adblQuote = new double[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			adblQuote[i] = mapQuote.get (aCC[i].primaryCode()).get (strManifestMeasure);

		double[] adblShiftedManifestMeasure = org.drip.analytics.support.Helper.TweakManifestMeasure
			(adblQuote, rvtp);

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public ZeroRateDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		return null;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null == _ccis ? null : _ccis.components();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstrumentCode)
	{
		return null;
	}
}
