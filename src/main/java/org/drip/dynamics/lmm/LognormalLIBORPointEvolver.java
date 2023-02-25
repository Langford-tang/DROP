
package org.drip.dynamics.lmm;

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
 * <i>LognormalLIBORPointEvolver</i> sets up and implements the Multi-Factor No-arbitrage Dynamics of the
 * Point Rates State Quantifiers traced from the Evolution of the LIBOR Forward Rate as formulated in:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): <i>Log-normality of Rates and Term Structure
 *  			Models</i> <b>The University of New South Wales</b>
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): <i>Nominal Annual Rates and Log-normal Volatility Structure</i> <b>The
 *  			University of New South Wales</b>
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics
 * 				<i>Mathematical Finance</i> <b>7 (2)</b> 127-155
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/README.md">LMM Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LognormalLIBORPointEvolver implements org.drip.dynamics.evolution.PointStateEvolver {
	private org.drip.state.forward.ForwardCurve _fc = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;
	private org.drip.dynamics.lmm.LognormalLIBORVolatility _llv = null;

	private double forwardDerivative (
		final int iViewDate)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 freR1ToR1 = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				return _fc.forward ((int) dblDate);
			}
		};

		return freR1ToR1.derivative (iViewDate, 1);
	}

	private double continuousForwardRateIncrement (
		final int iViewDate,
		final double dblAnnualizedIncrement,
		final double[] adblMultivariateRandom)
		throws java.lang.Exception
	{
		final int iNumFactor = adblMultivariateRandom.length;

		final double dblSpotTimeIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		org.drip.function.definition.R1ToR1 continuousForwardRateR1ToR1 = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				double dblForwardPointVolatilityModulus = 0.;
				double dblPointVolatilityMultifactorRandom = 0.;

				double[] adblContinuousForwardVolatility = _llv.continuousForwardVolatility ((int) dblDate,
					_fc);

				if (null != adblContinuousForwardVolatility) {
					for (int i = 0; i < iNumFactor; ++i) {
						dblForwardPointVolatilityModulus += adblContinuousForwardVolatility[i] *
							adblContinuousForwardVolatility[i];
						dblPointVolatilityMultifactorRandom += adblContinuousForwardVolatility[i] *
							adblMultivariateRandom[i];
					}
				}

				return (_fc.forward ((int) dblDate) + 0.5 * dblForwardPointVolatilityModulus) *
					dblAnnualizedIncrement + dblPointVolatilityMultifactorRandom * dblSpotTimeIncrementSQRT;
			}
		};

		return continuousForwardRateR1ToR1.derivative (iViewDate, 1);
	}

	private double spotRateIncrement (
		final double dblSpotDate,
		final double dblViewDate,
		final double dblAnnualizedIncrement,
		final double[] adblMultivariateRandom)
		throws java.lang.Exception
	{
		final int iNumFactor = adblMultivariateRandom.length;

		final double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		org.drip.function.definition.R1ToR1 spotRateR1ToR1 = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				double dblPointVolatilityMultifactorRandom = 0.;

				double[] adblContinuousForwardVolatility = _llv.continuousForwardVolatility ((int) dblDate,
					_fc);

				if (null != adblContinuousForwardVolatility) {
					for (int i = 0; i < iNumFactor; ++i)
						dblPointVolatilityMultifactorRandom += adblContinuousForwardVolatility[i] *
							adblMultivariateRandom[i];
				}

				return _fc.forward ((int) dblDate) * dblAnnualizedIncrement +
					dblPointVolatilityMultifactorRandom * dblAnnualizedIncrementSQRT;
			}
		};

		return spotRateR1ToR1.derivative (dblViewDate, 1);
	}

	/**
	 * LognormalLIBORPointEvolver Constructor
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param llv The Log-normal LIBOR Volatility Instance
	 * @param fc The Forward Curve Instance
	 * @param dc The Discount Curve Instance
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public LognormalLIBORPointEvolver (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final org.drip.dynamics.lmm.LognormalLIBORVolatility llv,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.state.discount.MergedDiscountForwardCurve dc)
		throws java.lang.Exception
	{
		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward) || null == (_llv = llv)
			|| null == (_fc = fc) || null == (_dc = dc))
			throw new java.lang.Exception ("LognormalLIBORPointEvolver ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _lslFunding;
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return _lslForward;
	}

	/**
	 * Retrieve the Log-normal LIBOR Volatility Instance
	 * 
	 * @return The Log-normal LIBOR Volatility Instance
	 */

	public org.drip.dynamics.lmm.LognormalLIBORVolatility llv()
	{
		return _llv;
	}

	/**
	 * Retrieve the Forward Curve Instance
	 * 
	 * @return The Forward Curve Instance
	 */

	public org.drip.state.forward.ForwardCurve forwardCurve()
	{
		return _fc;
	}

	/**
	 * Retrieve the Discount Curve Instance
	 * 
	 * @return The Discount Curve Instance
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve discountCurve()
	{
		return _dc;
	}

	@Override public org.drip.dynamics.lmm.BGMPointUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMPointUpdate lsqmPrev)
	{
		if (iSpotDate > iViewDate || (null != lsqmPrev && !(lsqmPrev instanceof
			org.drip.dynamics.lmm.BGMPointUpdate)))
			return null;

		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		double[] adblMultivariateRandom = _llv.msg().random();

		java.lang.String strTenor = _lslForward.tenor();

		double dblLIBOR = java.lang.Double.NaN;
		double dblSpotRate = java.lang.Double.NaN;
		double dblDiscountFactor = java.lang.Double.NaN;
		double dblContinuouslyCompoundedForwardRate = java.lang.Double.NaN;
		org.drip.dynamics.lmm.BGMPointUpdate bgmPrev = null == lsqmPrev ? null :
			(org.drip.dynamics.lmm.BGMPointUpdate) lsqmPrev;

		int iForwardDate = new org.drip.analytics.date.JulianDate (iViewDate).addTenor (strTenor).julian();

		try {
			if (null == bgmPrev) {
				dblLIBOR = _fc.forward (iForwardDate);

				dblDiscountFactor = _dc.df (iViewDate);

				dblSpotRate = _dc.forward (iSpotDate, iSpotDate + 1);

				dblContinuouslyCompoundedForwardRate = _dc.forward (iViewDate, iViewDate + 1);
			} else {
				dblLIBOR = bgmPrev.libor();

				dblSpotRate = bgmPrev.spotRate();

				dblDiscountFactor = bgmPrev.discountFactor();

				dblContinuouslyCompoundedForwardRate = bgmPrev.continuousForwardRate();
			}

			double[] adblLognormalFactorPointVolatility = _llv.factorPointVolatility (iSpotDate, iViewDate);

			double[] adblContinuousForwardVolatility = _llv.continuousForwardVolatility (iViewDate, _fc);

			double dblCrossVolatilityDotProduct = 0.;
			double dblLognormalPointVolatilityModulus = 0.;
			double dblLIBORVolatilityMultiFactorRandom = 0.;
			double dblContinuousForwardVolatilityModulus = 0.;
			double dblForwardVolatilityMultiFactorRandom = 0.;
			int iNumFactor = adblLognormalFactorPointVolatility.length;

			for (int i = 0; i < iNumFactor; ++i) {
				dblLognormalPointVolatilityModulus += adblLognormalFactorPointVolatility[i] *
					adblLognormalFactorPointVolatility[i];
				dblCrossVolatilityDotProduct += adblLognormalFactorPointVolatility[i] *
					adblContinuousForwardVolatility[i];
				dblLIBORVolatilityMultiFactorRandom += adblLognormalFactorPointVolatility[i] *
					adblMultivariateRandom[i] * dblAnnualizedIncrementSQRT;
				dblContinuousForwardVolatilityModulus += adblContinuousForwardVolatility[i] *
					adblContinuousForwardVolatility[i];
				dblForwardVolatilityMultiFactorRandom += adblContinuousForwardVolatility[i] *
					adblMultivariateRandom[i] * dblAnnualizedIncrementSQRT;
			}

			double dblDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);

			double dblLIBORDCF = dblDCF * dblLIBOR;

			double dblLIBORIncrement = dblAnnualizedIncrement * (forwardDerivative (iForwardDate) + dblLIBOR
				* dblCrossVolatilityDotProduct + (dblLognormalPointVolatilityModulus * dblLIBOR * dblLIBORDCF
					/ (1. + dblLIBORDCF))) + dblLIBOR * dblLIBORVolatilityMultiFactorRandom;

			double dblContinuousForwardRateIncrement = continuousForwardRateIncrement (iViewDate,
				dblAnnualizedIncrement, adblMultivariateRandom);

			double dblSpotRateIncrement = spotRateIncrement (iSpotDate, iViewDate, dblAnnualizedIncrement,
				adblMultivariateRandom);

			double dblEvolvedContinuousForwardRate = dblContinuouslyCompoundedForwardRate +
				dblContinuousForwardRateIncrement;
			double dblDiscountFactorIncrement = dblDiscountFactor * (dblSpotRate -
				dblContinuouslyCompoundedForwardRate) * dblAnnualizedIncrement -
					dblForwardVolatilityMultiFactorRandom;

			return org.drip.dynamics.lmm.BGMPointUpdate.Create (_lslFunding, _lslForward, iSpotDate,
				iSpotDate + iSpotTimeIncrement, iViewDate, dblLIBOR + dblLIBORIncrement, dblLIBORIncrement,
					dblEvolvedContinuousForwardRate, dblContinuousForwardRateIncrement, dblSpotRate +
						dblSpotRateIncrement, dblSpotRateIncrement, dblDiscountFactor +
							dblDiscountFactorIncrement, dblDiscountFactorIncrement, java.lang.Math.exp
								(dblEvolvedContinuousForwardRate) - 1., (java.lang.Math.exp (dblDCF *
									dblEvolvedContinuousForwardRate) - 1.) / dblDCF, java.lang.Math.sqrt
										(dblLognormalPointVolatilityModulus), java.lang.Math.sqrt
											(dblContinuousForwardVolatilityModulus));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
