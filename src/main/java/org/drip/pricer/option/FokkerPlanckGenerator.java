
package org.drip.pricer.option;

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
 * <i>FokkerPlanckGenerator</i> holds the base functionality that the performs the PDF evolution oriented
 * Option Pricing.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/README.md">Custom Pricing Algorithms and the Derivative Fokker Planck Trajectory Generators</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/README.md">Deterministic/Stochastic Volatility Settings/Greeks</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class FokkerPlanckGenerator implements org.drip.param.pricer.GenericPricer {

	/**
	 * Compute the Expected Payoff of the Option from the Inputs
	 * 
	 * @param dblStrike Option Strike
	 * @param dblTimeToExpiry Option Time To Expiry
	 * @param dblRiskFreeRate Option Risk Free Rate
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblInitialVolatility Option Initial Volatility Value
	 * @param bAsPrice TRUE - Return the Discounted Payoff
	 * 
	 * @return The Expected Option Payoff
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Payoff cannot be calculated
	 */

	public abstract double payoff (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblInitialVolatility,
		final boolean bAsPrice)
		throws java.lang.Exception;

	/**
	 * Carry out a Sensitivity Run and generate the Pricing related measure set
	 * 
	 * @param dblStrike Option Strike
	 * @param dblTimeToExpiry Option Time To Expiry
	 * @param dblRiskFreeRate Option Risk Free Rate
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblInitialVolatility Option Initial Volatility Value
	 * 
	 * @return The Greeks Sensitivities Output
	 */

	public abstract org.drip.pricer.option.Greeks greeks (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblInitialVolatility);

	/**
	 * Compute the Expected Payoff of the Option from the Inputs
	 * 
	 * @param iSpotDate Spot Date
	 * @param iExpiryDate Expiry Date
	 * @param dblStrike Option Strike
	 * @param dcFunding The Funding Curve
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblInitialVolatility Option Initial Volatility Value
	 * @param bAsPrice TRUE - Return the Discounted Payoff
	 * 
	 * @return The Expected Option Payoff
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Payoff cannot be calculated
	 */

	public double payoff (
		final int iSpotDate,
		final int iExpiryDate,
		final double dblStrike,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblInitialVolatility,
		final boolean bAsPrice)
		throws java.lang.Exception
	{
		if (iExpiryDate <= iSpotDate || !org.drip.numerical.common.NumberUtil.IsValid (dblStrike) || null ==
			dcFunding || !org.drip.numerical.common.NumberUtil.IsValid (dblInitialVolatility))
			throw new java.lang.Exception ("FokkerPlanckGenerator::payoff => Invalid Inputs");

		return payoff (dblStrike, 1. * (iExpiryDate - iSpotDate) / 365.25, dcFunding.libor (iSpotDate,
			iExpiryDate), dblUnderlier, bIsPut, bIsForward, dblInitialVolatility, bAsPrice);
	}

	/**
	 * Compute the Expected Payoff of the Option from the Inputs
	 * 
	 * @param iSpotDate Spot Date
	 * @param iExpiryDate Expiry Date
	 * @param dblStrike Option Strike
	 * @param dcFunding The Funding Curve
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param funcVolatilityR1ToR1 The R^1 To R^1 Volatility Term Structure
	 * @param bAsPrice TRUE - Return the Discounted Payoff
	 * 
	 * @return The Expected Option Payoff
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Payoff cannot be calculated
	 */

	public double payoff (
		final int iSpotDate,
		final int iExpiryDate,
		final double dblStrike,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final org.drip.function.definition.R1ToR1 funcVolatilityR1ToR1,
		final boolean bAsPrice)
		throws java.lang.Exception
	{
		if (iExpiryDate <= iSpotDate || !org.drip.numerical.common.NumberUtil.IsValid (dblStrike) || null ==
			dcFunding || null == funcVolatilityR1ToR1)
			throw new java.lang.Exception ("FokkerPlanckGenerator::payoff => Invalid Inputs");

		int iDaysToExpiry = iExpiryDate - iSpotDate;

		double dblRiskFreeRate = dcFunding.libor (iSpotDate, iExpiryDate);

		org.drip.function.definition.R1ToR1 funcVarianceR1ToR1 = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return funcVolatilityR1ToR1.evaluate (dblX) * funcVolatilityR1ToR1.evaluate (dblX);
			}
		};

		double dblEffectiveVolatility = java.lang.Math.sqrt (funcVarianceR1ToR1.integrate (iSpotDate,
			iExpiryDate) / iDaysToExpiry);

		return payoff (dblStrike, 1. * iDaysToExpiry / 365.25, dblRiskFreeRate, dblUnderlier, bIsPut,
			bIsForward, dblEffectiveVolatility, bAsPrice);
	}

	/**
	 * Carry out a Sensitivity Run and generate the Pricing related measure set
	 * 
	 * @param iSpotDate Spot Date
	 * @param iExpiryDate Expiry Date
	 * @param dblStrike Option Strike
	 * @param dcFunding The Funding Curve
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblIntegratedSurfaceVariance The Integrated Surface Variance
	 * 
	 * @return The Greeks Output generated from the Sensitivities Run
	 */

	public org.drip.pricer.option.Greeks greeks (
		final int iSpotDate,
		final int iExpiryDate,
		final double dblStrike,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblIntegratedSurfaceVariance)
	{
		if (iExpiryDate <= iSpotDate || !org.drip.numerical.common.NumberUtil.IsValid (dblStrike) || null ==
			dcFunding || !org.drip.numerical.common.NumberUtil.IsValid (dblIntegratedSurfaceVariance))
			return null;

		double dblTimeToExpiry = 1. * (iExpiryDate - iSpotDate) / 365.25;

		try {
			return greeks (dblStrike, dblTimeToExpiry, dcFunding.libor (iSpotDate, iExpiryDate),
				dblUnderlier, bIsPut, bIsForward, java.lang.Math.sqrt (dblIntegratedSurfaceVariance /
					dblTimeToExpiry));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Carry out a Sensitivity Run and generate the Pricing related measure set
	 * 
	 * @param iSpotDate Spot Date
	 * @param iExpiryDate Expiry Date
	 * @param dblStrike Option Strike
	 * @param dcFunding The Funding Curve
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param funcVolatilityR1ToR1 The R^1 To R^1 Volatility Term Structure
	 * 
	 * @return The Greeks Output generated from the Sensitivities Run
	 */

	public org.drip.pricer.option.Greeks greeks (
		final int iSpotDate,
		final int iExpiryDate,
		final double dblStrike,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final org.drip.function.definition.R1ToR1 funcVolatilityR1ToR1)
	{
		if (iExpiryDate <= iSpotDate || !org.drip.numerical.common.NumberUtil.IsValid (dblStrike) || null ==
			dcFunding || null == funcVolatilityR1ToR1)
			return null;

		double dblRiskFreeRate = java.lang.Double.NaN;
		double dblEffectiveVolatility = java.lang.Double.NaN;
		double dblTimeToExpiry = 1. * (iExpiryDate - iSpotDate) / 365.25;

		org.drip.function.definition.R1ToR1 funcVarianceR1ToR1 = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return funcVolatilityR1ToR1.evaluate (dblX) * funcVolatilityR1ToR1.evaluate (dblX);
			}
		};

		try {
			dblRiskFreeRate = dcFunding.libor (iSpotDate, iExpiryDate);

			dblEffectiveVolatility = java.lang.Math.sqrt (funcVarianceR1ToR1.integrate (iSpotDate,
				iExpiryDate) / (365.25 * dblTimeToExpiry));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return greeks (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblUnderlier, bIsPut, bIsForward,
			dblEffectiveVolatility);
	}

	/**
	 * Imply the Effective Volatility From the Option Price
	 * 
	 * @param dblStrike Strike
	 * @param dblTimeToExpiry Time To Expiry
	 * @param dblRiskFreeRate Risk Free Rate
	 * @param dblUnderlier The Underlier
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblPrice The Price
	 * 
	 * @return The Implied Effective Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Effective Volatility cannot be implied
	 */

	public double impliedVolatilityFromPrice (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 au = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblSpotVolatility)
				throws java.lang.Exception
			{
				return payoff (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblUnderlier, bIsPut, bIsForward,
					dblSpotVolatility, true) - dblPrice;
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpop = new
			org.drip.function.r1tor1solver.FixedPointFinderBrent (0., au, true).findRoot();

		if (null == fpop || !fpop.containsRoot())
			throw new java.lang.Exception
				("FokkerPlanckGenerator::impliedVolatilityFromPrice => Cannot imply Volatility");

		return java.lang.Math.abs (fpop.getRoot());
	}

	/**
	 * Imply the Effective Volatility From the Option Price
	 * 
	 * @param iSpotDate Spot Date
	 * @param iExpiryDate Expiry Date
	 * @param dblStrike Option Strike
	 * @param dcFunding The Funding Curve
	 * @param dblUnderlier Option Underlier Value
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblPrice The Price
	 * 
	 * @return The Implied Effective Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Effective Volatility cannot be implied
	 */

	public double impliedVolatilityFromPrice (
		final int iSpotDate,
		final int iExpiryDate,
		final double dblStrike,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 au = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblInitialVolatility)
				throws java.lang.Exception
			{
				return payoff (iSpotDate, iExpiryDate, dblStrike, dcFunding, dblUnderlier, bIsPut,
					bIsForward, dblInitialVolatility, true) - dblPrice;
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpop = new
			org.drip.function.r1tor1solver.FixedPointFinderBrent (0., au, true).findRoot();

		if (null == fpop || !fpop.containsRoot())
			throw new java.lang.Exception
				("FokkerPlanckGenerator::impliedVolatilityFromPrice => Cannot imply Volatility");

		return java.lang.Math.abs (fpop.getRoot());
	}

	/**
	 * Imply the Effective Black-Scholes Volatility From the Option Price
	 * 
	 * @param dblStrike Strike
	 * @param dblTimeToExpiry Time To Expiry
	 * @param dblRiskFreeRate Risk Free Rate
	 * @param dblUnderlier The Underlier
	 * @param bIsPut TRUE - The Option is a Put
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblPrice The Price
	 * 
	 * @return The Implied Black Scholes Effective Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Black Scholes Effective Volatility cannot be implied
	 */

	public double impliedBlackScholesVolatility (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new org.drip.pricer.option.BlackScholesAlgorithm().impliedVolatilityFromPrice (dblStrike,
			dblTimeToExpiry, dblRiskFreeRate, dblUnderlier, bIsPut, bIsForward, dblPrice);
	}
}
