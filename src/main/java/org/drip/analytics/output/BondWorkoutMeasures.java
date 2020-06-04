
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>BondWorkoutMeasures</i> encapsulates the parsimonius yet complete set of measures generated out of a
 * full bond analytics run to a given work-out. It contains the following:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Credit Risky/Credit Riskless Clean/Dirty Coupon Measures
 * 		</li>
 * 		<li>
 * 			Credit Risky/Credit Riskless Par/Principal PV
 * 		</li>
 * 		<li>
 * 			Loss Measures such as expected Recovery, Loss on instantaneous default, and default exposure
 * 				with/without recovery
 * 		</li>
 * 		<li>
 * 			Unit Coupon measures such as Accrued 01, first coupon/index rate
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Period Product Targeted Valuation Measures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondWorkoutMeasures {
	private double _dblAccrued01 = java.lang.Double.NaN;
	private double _dblRecoveryPV = java.lang.Double.NaN;
	private BondCouponMeasures _bcmCreditRiskyClean = null;
	private BondCouponMeasures _bcmCreditRiskyDirty = null;
	private double _dblFirstIndexRate = java.lang.Double.NaN;
	private double _dblFirstCouponRate = java.lang.Double.NaN;
	private BondCouponMeasures _bcmCreditRisklessClean = null;
	private BondCouponMeasures _bcmCreditRisklessDirty = null;
	private double _dblDefaultExposure = java.lang.Double.NaN;
	private double _dblExpectedRecovery = java.lang.Double.NaN;
	private double _dblCreditRiskyParPV = java.lang.Double.NaN;
	private double _dblCreditRisklessParPV = java.lang.Double.NaN;
	private double _dblDefaultExposureNoRec = java.lang.Double.NaN;
	private double _dblCreditRiskyPrincipalPV = java.lang.Double.NaN;
	private double _dblCreditRisklessPrincipalPV = java.lang.Double.NaN;
	private double _dblLossOnInstantaneousDefault = java.lang.Double.NaN;

	/**
	 * 
	 * BondWorkoutMeasures constructor
	 * 
	 * @param bcmCreditRiskyDirty Dirty credit risky BondMeasuresCoupon
	 * @param bcmCreditRisklessDirty Dirty credit risk-less BondMeasuresCoupon
	 * @param dblCreditRiskyParPV Credit risky Par PV
	 * @param dblCreditRisklessParPV Credit risk-less par PV
	 * @param dblCreditRiskyPrincipalPV Credit Risky Principal PV
	 * @param dblCreditRisklessPrincipalPV Credit Risk-less Principal PV
	 * @param dblRecoveryPV Recovery PV
	 * @param dblExpectedRecovery Expected Recovery
	 * @param dblDefaultExposure PV on instantaneous default
	 * @param dblDefaultExposureNoRec PV on instantaneous default with zero recovery
	 * @param dblLossOnInstantaneousDefault Loss On Instantaneous Default
	 * @param dblAccrued01 Accrued01
	 * @param dblFirstCouponRate First Coupon Rate
	 * @param dblFirstIndexRate First Index Rate
	 * @param dblCashPayDF Cash Pay Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BondWorkoutMeasures (
		final BondCouponMeasures bcmCreditRiskyDirty,
		final BondCouponMeasures bcmCreditRisklessDirty,
		final double dblCreditRiskyParPV,
		final double dblCreditRisklessParPV,
		final double dblCreditRiskyPrincipalPV,
		final double dblCreditRisklessPrincipalPV,
		final double dblRecoveryPV,
		final double dblExpectedRecovery,
		final double dblDefaultExposure,
		final double dblDefaultExposureNoRec,
		final double dblLossOnInstantaneousDefault,
		final double dblAccrued01,
		final double dblFirstCouponRate,
		final double dblFirstIndexRate,
		final double dblCashPayDF)
		throws java.lang.Exception
	{
		if (null == (_bcmCreditRisklessDirty = bcmCreditRisklessDirty) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblCreditRisklessParPV = dblCreditRisklessParPV) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblCreditRisklessPrincipalPV =
					dblCreditRisklessPrincipalPV) || !org.drip.numerical.common.NumberUtil.IsValid (_dblAccrued01
						= dblAccrued01) || !org.drip.numerical.common.NumberUtil.IsValid (_dblFirstCouponRate =
							dblFirstCouponRate))
			throw new java.lang.Exception ("BondWorkoutMeasures ctr: Invalid Inputs!");

		_dblRecoveryPV = dblRecoveryPV;
		_dblFirstIndexRate = dblFirstIndexRate;
		_dblDefaultExposure = dblDefaultExposure;
		_dblExpectedRecovery = dblExpectedRecovery;
		_bcmCreditRiskyDirty = bcmCreditRiskyDirty;
		_dblCreditRiskyParPV = dblCreditRiskyParPV;
		_dblDefaultExposureNoRec = dblDefaultExposureNoRec;
		_dblCreditRiskyPrincipalPV = dblCreditRiskyPrincipalPV;
		_dblLossOnInstantaneousDefault = dblLossOnInstantaneousDefault;

		if (!(_bcmCreditRisklessClean = new org.drip.analytics.output.BondCouponMeasures
			(_bcmCreditRisklessDirty.dv01(), _bcmCreditRisklessDirty.indexCouponPV(),
				_bcmCreditRisklessDirty.couponPV(), _bcmCreditRisklessDirty.pv())).adjustForSettlement
					(dblCashPayDF))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRisklessClean");

		if (!_bcmCreditRisklessClean.adjustForAccrual (_dblAccrued01, _dblFirstCouponRate, dblFirstIndexRate,
			false))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRisklessClean");

		if (null != _bcmCreditRiskyDirty && ((!(_bcmCreditRiskyClean = new BondCouponMeasures
			(_bcmCreditRiskyDirty.dv01(), _bcmCreditRiskyDirty.indexCouponPV(),
				_bcmCreditRiskyDirty.couponPV(), _bcmCreditRiskyDirty.pv())).adjustForSettlement
					(dblCashPayDF)) || !_bcmCreditRiskyClean.adjustForAccrual (_dblAccrued01,
						_dblFirstCouponRate, _dblFirstCouponRate, false)))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRiskyClean");
	}

	/**
	 * Retrieve the Credit Risky Clean Bond Coupon Measures
	 * 
	 * @return Credit Risky Clean Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRiskyCleanbcm()
	{
		return _bcmCreditRiskyClean;
	}

	/**
	 * Retrieve the Credit Risk-less Clean Bond Coupon Measures
	 * 
	 * @return Credit Risk-less Clean Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRisklessCleanbcm()
	{
		return _bcmCreditRisklessClean;
	}

	/**
	 * Retrieve the Credit Risky Dirty Bond Coupon Measures
	 * 
	 * @return Credit Risky Dirty Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRiskyDirtybcm()
	{
		return _bcmCreditRiskyDirty;
	}

	/**
	 * Retrieve the Credit Risk-less Dirty Bond Coupon Measures
	 * 
	 * @return Credit Risk-less Dirty Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRisklessDirtybcm()
	{
		return _bcmCreditRisklessDirty;
	}

	/**
	 * Retrieve the Accrued01
	 * 
	 * @return Accrued01
	 */

	public double accrued01()
	{
		return _dblAccrued01;
	}

	/**
	 * Retrieve the First Coupon Rate
	 * 
	 * @return First Coupon Rate
	 */

	public double firstCouponRate()
	{
		return _dblFirstCouponRate;
	}

	/**
	 * Retrieve the First Index Rate
	 * 
	 * @return First Index Rate
	 */

	public double firstIndexRate()
	{
		return _dblFirstIndexRate;
	}

	/**
	 * Retrieve the Credit Risky Par PV
	 * 
	 * @return The Credit Risky Par PV
	 */

	public double creditRiskyParPV()
	{
		return _dblCreditRiskyParPV;
	}

	/**
	 * Retrieve the Credit Risk-less Par PV
	 * 
	 * @return The Credit Risk-less Par PV
	 */

	public double creditRisklessParPV()
	{
		return _dblCreditRisklessParPV;
	}

	/**
	 * Retrieve the Credit Risky Principal PV
	 * 
	 * @return The Credit Risky Principal PV
	 */

	public double creditRiskyPrincipalPV()
	{
		return _dblCreditRiskyPrincipalPV;
	}

	/**
	 * Retrieve the Credit Risk-less Principal PV
	 * 
	 * @return The Credit Risk-less Principal PV
	 */

	public double creditRisklessPrincipalPV()
	{
		return _dblCreditRisklessPrincipalPV;
	}

	/**
	 * Retrieve the Recovery PV
	 * 
	 * @return The Recovery PV
	 */

	public double recoveryPV()
	{
		return _dblRecoveryPV;
	}

	/**
	 * Retrieve the Expected Recovery
	 * 
	 * @return The Expected Recovery
	 */

	public double expectedRecovery()
	{
		return _dblExpectedRecovery;
	}

	/**
	 * Retrieve Default Exposure - Same as PV on instantaneous default
	 * 
	 * @return The Default Exposure
	 */

	public double defaultExposure()
	{
		return _dblDefaultExposure;
	}

	/**
	 * Retrieve the Default Exposure without recovery - Same as PV on instantaneous default without recovery
	 * 
	 * @return The Default Exposure without recovery
	 */

	public double defaultExposureNoRec()
	{
		return _dblDefaultExposureNoRec;
	}

	/**
	 * Retrieve the Loss On Instantaneous Default
	 * 
	 * @return Loss On Instantaneous Default
	 */

	public double lossOnInstantaneousDefault()
	{
		return _dblLossOnInstantaneousDefault;
	}

	/**
	 * Return the state as a measure map
	 * 
	 * @param strPrefix Measure name prefix
	 * 
	 * @return Map of the measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> toMap (
		final java.lang.String strPrefix)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapMeasures.put (strPrefix + "Accrued", _dblAccrued01 * _dblFirstCouponRate);

		mapMeasures.put (strPrefix + "Accrued01", _dblAccrued01);

		mapMeasures.put (strPrefix + "CleanCouponPV", _bcmCreditRisklessClean.couponPV());

		mapMeasures.put (strPrefix + "CleanDV01", _bcmCreditRisklessClean.dv01());

		mapMeasures.put (strPrefix + "CleanIndexCouponPV", _bcmCreditRisklessClean.indexCouponPV());

		mapMeasures.put (strPrefix + "CleanPrice", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "CleanPV", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "CreditRisklessParPV", _dblCreditRisklessParPV);

		mapMeasures.put (strPrefix + "CreditRisklessPrincipalPV", _dblCreditRisklessPrincipalPV);

		mapMeasures.put (strPrefix + "CreditRiskyParPV", _dblCreditRiskyParPV);

		mapMeasures.put (strPrefix + "CreditRiskyPrincipalPV", _dblCreditRiskyPrincipalPV);

		mapMeasures.put (strPrefix + "DefaultExposure", _dblDefaultExposure);

		mapMeasures.put (strPrefix + "DefaultExposureNoRec", _dblDefaultExposureNoRec);

		mapMeasures.put (strPrefix + "DirtyCouponPV", _bcmCreditRisklessDirty.couponPV());

		mapMeasures.put (strPrefix + "DirtyDV01", _bcmCreditRisklessDirty.dv01());

		mapMeasures.put (strPrefix + "DirtyIndexCouponPV", _bcmCreditRisklessDirty.indexCouponPV());

		mapMeasures.put (strPrefix + "DirtyPrice", _bcmCreditRisklessDirty.pv());

		mapMeasures.put (strPrefix + "DirtyPV", _bcmCreditRisklessDirty.pv());

		mapMeasures.put (strPrefix + "DV01", _bcmCreditRisklessClean.dv01());

		mapMeasures.put (strPrefix + "ExpectedRecovery", _dblExpectedRecovery);

		mapMeasures.put (strPrefix + "FirstCouponRate", _dblFirstCouponRate);

		mapMeasures.put (strPrefix + "FirstIndexRate", _dblFirstIndexRate);

		mapMeasures.put (strPrefix + "LossOnInstantaneousDefault", _dblLossOnInstantaneousDefault);

		mapMeasures.put (strPrefix + "ParPV", _dblCreditRisklessParPV);

		mapMeasures.put (strPrefix + "PrincipalPV", _dblCreditRisklessPrincipalPV);

		mapMeasures.put (strPrefix + "PV", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "RecoveryPV", _dblRecoveryPV);

		org.drip.service.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRisklessDirty.toMap
			(strPrefix + "RisklessDirty"));

		org.drip.service.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRisklessClean.toMap
			(strPrefix + "RisklessClean"));

		if (null != _bcmCreditRiskyDirty) {
			mapMeasures.put (strPrefix + "CleanCouponPV", _bcmCreditRiskyClean.couponPV());

			mapMeasures.put (strPrefix + "CleanDV01", _bcmCreditRiskyClean.dv01());

			mapMeasures.put (strPrefix + "CleanIndexCouponPV", _bcmCreditRiskyClean.indexCouponPV());

			mapMeasures.put (strPrefix + "CleanPrice", _bcmCreditRiskyClean.pv());

			mapMeasures.put (strPrefix + "CleanPV", _bcmCreditRiskyClean.pv());

			mapMeasures.put (strPrefix + "DirtyCouponPV", _bcmCreditRiskyDirty.couponPV());

			mapMeasures.put (strPrefix + "DirtyDV01", _bcmCreditRiskyDirty.dv01());

			mapMeasures.put (strPrefix + "DirtyIndexCouponPV", _bcmCreditRiskyDirty.indexCouponPV());

			mapMeasures.put (strPrefix + "DirtyPrice", _bcmCreditRiskyDirty.pv());

			mapMeasures.put (strPrefix + "DirtyPV", _bcmCreditRiskyDirty.pv());

			mapMeasures.put (strPrefix + "DV01", _bcmCreditRiskyClean.dv01());

			mapMeasures.put (strPrefix + "ParPV", _dblCreditRiskyParPV);

			mapMeasures.put (strPrefix + "PrincipalPV", _dblCreditRiskyPrincipalPV);

			mapMeasures.put (strPrefix + "PV", _bcmCreditRiskyClean.pv());

			org.drip.service.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRiskyDirty.toMap
				(strPrefix + "RiskyDirty"));

			org.drip.service.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRiskyClean.toMap
				(strPrefix + "RiskyClean"));
		}

		return mapMeasures;
	}
}
