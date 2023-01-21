
package org.drip.market.otc;

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
 * <i>CrossFloatSwapConvention</i> contains the Details of the Cross-Currency Floating Swap of an OTC
 * Contract.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CrossFloatSwapConvention {
	private int _iSpotLag = -1;
	private int _iFixingType = -1;
	private boolean _bFXMTM = false;
	private org.drip.market.otc.CrossFloatStreamConvention _crossStreamDerived = null;
	private org.drip.market.otc.CrossFloatStreamConvention _crossStreamReference = null;

	private org.drip.product.rates.Stream floatingStream (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.market.otc.CrossFloatStreamConvention cfsc,
		final org.drip.param.period.FixingSetting fxFixingSetting,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblNotional)
	{
		java.lang.String strFloaterTenor = cfsc.tenor();

		org.drip.state.identifier.ForwardLabel forwardLabel = org.drip.state.identifier.ForwardLabel.Create
			(org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (cfsc.currency()),
				strFloaterTenor);

		if (null == forwardLabel) return null;

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfusReference = new
				org.drip.param.period.ComposableFloatingUnitSetting (strFloaterTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabel,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								cfsc.applySpread() ? dblBasis : 0.);

			org.drip.param.period.CompositePeriodSetting cpsReference = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (strFloaterTenor),
						strFloaterTenor, _crossStreamReference.currency(), null, dblNotional, null, null,
							fxFixingSetting, null);

			java.util.List<java.lang.Integer> lsReferenceEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					strFloaterTenor, strMaturityTenor, null);

			return new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(lsReferenceEdgeDate, cpsReference, cfusReference));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CrossFloatSwapConvention Constructor
	 * 
	 * @param crossStreamReference Reference Cross Float Stream
	 * @param crossStreamDerived Derived Cross Float Stream
	 * @param iFixingType Fixing Type
	 * @param bFXMTM TRUE - The Cross Currency Swap is of the FX MTM type
	 * @param iSpotLag Spot Lag
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public CrossFloatSwapConvention (
		final org.drip.market.otc.CrossFloatStreamConvention crossStreamReference,
		final org.drip.market.otc.CrossFloatStreamConvention crossStreamDerived,
		final int iFixingType,
		final boolean bFXMTM,
		final int iSpotLag)
		throws java.lang.Exception
	{
		if (null == (_crossStreamReference = crossStreamReference) || null == (_crossStreamDerived =
			crossStreamDerived) || !org.drip.param.period.FixingSetting.ValidateType (_iFixingType =
				iFixingType) || 0 > (_iSpotLag = iSpotLag))
			throw new java.lang.Exception ("CrossFloatSwapConvention ctr: Invalid Inputs");

		_bFXMTM = bFXMTM;
	}

	/**
	 * Retrieve the Reference Convention
	 * 
	 * @return The Reference Convention
	 */

	public org.drip.market.otc.CrossFloatStreamConvention referenceConvention()
	{
		return _crossStreamReference;
	}

	/**
	 * Retrieve the Derived Convention
	 * 
	 * @return The Derived Convention
	 */

	public org.drip.market.otc.CrossFloatStreamConvention derivedConvention()
	{
		return _crossStreamDerived;
	}

	/**
	 * Retrieve the Fixing Setting Type
	 * 
	 * @return The Fixing Setting Type
	 */

	public int fixingType()
	{
		return _iFixingType;
	}

	/**
	 * Retrieve the Spot Lag
	 * 
	 * @return The Spot Lag
	 */

	public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Retrieve the FX MTM Flag
	 * 
	 * @return The FX MTM Flag
	 */

	public boolean isFXMTM()
	{
		return _bFXMTM;
	}

	/**
	 * Create an Instance of the Float-Float Component
	 * 
	 * @param dtSpot Spot Date
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblBasis Basis
	 * @param dblReferenceNotional Notional of the Reference Stream
	 * @param dblDerivedNotional Notional of the Derived Stream
	 * 
	 * @return Instance of the Float-Float Component
	 */

	public org.drip.product.rates.FloatFloatComponent createFloatFloatComponent (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblReferenceNotional,
		final double dblDerivedNotional)
	{
		if (null == dtSpot) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction
				(_crossStreamReference.currency()).calendar() + "," +
					org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction
						(_crossStreamDerived.currency()).calendar());

		try {
			org.drip.param.period.FixingSetting fxFixingSetting = _bFXMTM ? null : new
				org.drip.param.period.FixingSetting (_iFixingType, null, dtEffective.julian());

			return new org.drip.product.rates.FloatFloatComponent (floatingStream (dtEffective,
				_crossStreamReference, fxFixingSetting, strMaturityTenor, dblBasis, dblReferenceNotional),
					floatingStream (dtEffective, _crossStreamDerived, fxFixingSetting, strMaturityTenor,
						dblBasis, dblDerivedNotional), null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return _crossStreamReference + " " + _crossStreamDerived + " " + _iFixingType + " " + _iSpotLag + " "
			+ _bFXMTM;
	}
}
