
package org.drip.sample.xvastrategy;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.universe.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.OTCFixFloatLabel;
import org.drip.xva.definition.*;
import org.drip.xva.derivative.ReplicationPortfolioVertexDealer;
import org.drip.xva.gross.*;
import org.drip.xva.netting.CollateralGroupPath;
import org.drip.xva.strategy.*;
import org.drip.xva.vertex.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>FundingGroupUnilateralCSA</i> demonstrates the Simulation Run of the Funding Group Exposure using the
 * 	"Unilateral CSA" Funding Strategy laid out in Burgard and Kjaer (2013). The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xvastrategy/README.md">Burgard Kjaer (2013) XVA Strategies</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingGroupUnilateralCSA {

	private static final double[] AssetValueRealization (
		final DiffusionEvolver deAssetValue,
		final double dblAssetValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep)
		throws Exception
	{
		double[] ablAssetValue = new double[iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];
		ablAssetValue[0] = dblAssetValueInitial;

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aJDE = deAssetValue.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblAssetValueInitial,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				SequenceGenerator.Gaussian (iNumStep)
			),
			dblTimeWidth
		);

		for (int j = 1; j <= iNumStep; ++j)
			ablAssetValue[j] = aJDE[j - 1].finish();

		return ablAssetValue;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		double dblTime = 5.;
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblAssetValueInitial = 1.;
		double dblOISRate = 0.004;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankSeniorRecoveryRate = 0.40;
		double dblBankSubordinateRecoveryRate = 0.15;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		BurgardKjaer[] aBKV1 = new BurgardKjaer[iNumStep + 1];
		BurgardKjaer[] aBKV2 = new BurgardKjaer[iNumStep + 1];
		double dblBankSeniorFundingSpread = dblBankHazardRate / (1. - dblBankSeniorRecoveryRate);
		double dblBankSubordinateFundingSpread = dblBankHazardRate / (1. - dblBankSubordinateRecoveryRate);
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		JulianDate dtSpot = DateUtil.Today();

		CloseOut cog = new CloseOutBilateral (
			dblBankSeniorRecoveryRate,
			dblCounterPartyRecoveryRate
		);

		DiffusionEvolver deAssetValue = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblAssetDrift,
				dblAssetVolatility
			)
		);

		double[] adblAssetValuePath1 = AssetValueRealization (
			deAssetValue,
			dblAssetValueInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblAssetValuePath2 = AssetValueRealization (
			deAssetValue,
			dblAssetValueInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		System.out.println();

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                       PATH VERTEX EXPOSURES AND NUMERAIRE REALIZATIONS                                                       ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                                                                                                                                   ||");

		System.out.println ("\t|            - Path #1 Gross Exposure                                                                                                                          ||");

		System.out.println ("\t|            - Path #1 Positive Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #1 Negative Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #2 Gross Exposure                                                                                                                          ||");

		System.out.println ("\t|            - Path #2 Positive Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #2 Negative Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Collateral Numeraire                                                                                                                            ||");

		System.out.println ("\t|            - Bank Survival Probability                                                                                                                       ||");

		System.out.println ("\t|            - Bank Recovery Rate                                                                                                                              ||");

		System.out.println ("\t|            - Bank Funding Spread                                                                                                                             ||");

		System.out.println ("\t|            - Counter Party Survival Probability                                                                                                              ||");

		System.out.println ("\t|            - Counter Party Recovery Rate                                                                                                                     ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i)
		{
			LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

			latentStateVertexContainer.add (
				OTCFixFloatLabel.Standard ("USD-3M-10Y"),
				Double.NaN
			);

			aMV[i] = MarketVertex.Nodal (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				dblOISRate,
				Math.exp (-0.5 * dblOISRate * (iNumStep - i)),
				dblCSADrift,
				Math.exp (-0.5 * dblCSADrift * (iNumStep - i)),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankSeniorRecoveryRate,
					dblBankSeniorFundingSpread,
					Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSeniorRecoveryRate) * (iNumStep - i)),
					dblBankSubordinateRecoveryRate,
					dblBankSubordinateFundingSpread,
					Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSubordinateRecoveryRate) * (iNumStep - i))
				),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblCounterPartyHazardRate * i),
					dblCounterPartyHazardRate,
					dblCounterPartyRecoveryRate,
					dblCounterPartyFundingSpread,
					Math.exp (-0.5 * dblCounterPartyHazardRate * (1. - dblCounterPartyRecoveryRate) * (iNumStep - i)),
					Double.NaN,
					Double.NaN,
					Double.NaN
				),
				latentStateVertexContainer
			);

			if (0 != i) {
				aBKV1[i] = BurgardKjaerBuilder.OneWayCSA (
					adtVertex[i],
					adblAssetValuePath1[i],
					0.,
					new MarketEdge (
						aMV[i - 1],
						aMV[i]
					),
					cog
				);

				aBKV2[i] = BurgardKjaerBuilder.OneWayCSA (
					adtVertex[i],
					adblAssetValuePath2[i],
					0.,
					new MarketEdge (
						aMV[i - 1],
						aMV[i]
					),
					cog
				);
			} else {
				aBKV1[i] = BurgardKjaerBuilder.Initial (
					adtVertex[i],
					adblAssetValuePath1[i],
					aMV[i],
					cog
				);

				aBKV2[i] = BurgardKjaerBuilder.Initial (
					adtVertex[i],
					adblAssetValuePath2[i],
					aMV[i],
					cog
				);
			}

			System.out.println (
				"\t| " + adtVertex[i] + " => " +
				FormatUtil.FormatDouble (aBKV1[i].collateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV1[i].uncollateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV1[i].variationMarginPosting(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].collateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].uncollateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].variationMarginPosting(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].overnightRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].dealer().survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].dealer().seniorRecoveryRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].dealer().seniorFundingSpread(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].client().survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].client().seniorRecoveryRate(), 1, 6, 1.) + " ||"
			);
		}

		MarketPath mp = MarketPath.FromMarketVertexArray (aMV);

		CollateralGroupPath[] aCGP1 = new CollateralGroupPath[] {
			new CollateralGroupPath (
				aBKV1,
				mp
			)
		};

		CollateralGroupPath[] aCGP2 = new CollateralGroupPath[] {
			new CollateralGroupPath (
				aBKV2,
				mp
			)
		};

		AlbaneseAndersenNettingGroupPath ngpaa2014_1 = new AlbaneseAndersenNettingGroupPath (
			aCGP1,
			mp
		);

		AlbaneseAndersenFundingGroupPath fgpaa2014_1 = AlbaneseAndersenFundingGroupPath.Mono (
			ngpaa2014_1,
			mp
		);

		AlbaneseAndersenNettingGroupPath ngpaa2014_2 = new AlbaneseAndersenNettingGroupPath (
			aCGP2,
			mp
		);

		AlbaneseAndersenFundingGroupPath fgpaa2014_2 = AlbaneseAndersenFundingGroupPath.Mono (
			ngpaa2014_2,
			mp
		);

		double[] adblPeriodUnilateralCreditAdjustment1 = ngpaa2014_1.periodUnilateralCreditAdjustment();

		double[] adblPeriodBilateralCreditAdjustment1 = ngpaa2014_1.periodBilateralCreditAdjustment();

		double[] adblPeriodCreditAdjustment1 = ngpaa2014_1.periodCreditAdjustment();

		double[] adblPeriodContraLiabilityCreditAdjustment1 = ngpaa2014_1.periodContraLiabilityCreditAdjustment();

		double[] adblPeriodUnilateralCreditAdjustment2 = ngpaa2014_2.periodUnilateralCreditAdjustment();

		double[] adblPeriodBilateralCreditAdjustment2 = ngpaa2014_2.periodBilateralCreditAdjustment();

		double[] adblPeriodCreditAdjustment2 = ngpaa2014_2.periodCreditAdjustment();

		double[] adblPeriodContraLiabilityCreditAdjustment2 = ngpaa2014_2.periodContraLiabilityCreditAdjustment();

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|               PERIOD UNILATERAL CREDIT, BILATERAL CREDIT, CREDIT, & CONTRA LIABILITY CREDIT VALUATION ADJUSTMENTS               ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|    - Forward Period                                                                                                             ||");

		System.out.println ("\t|    - Path #1 Period Unilateral Credit Adjustments                                                                               ||");

		System.out.println ("\t|    - Path #1 Period Bilateral Credit Adjustments                                                                                ||");

		System.out.println ("\t|    - Path #1 Period Credit Adjustments                                                                                          ||");

		System.out.println ("\t|    - Path #1 Period Contra-Liability Credit Adjustments                                                                         ||");

		System.out.println ("\t|    - Path #2 Period Unilateral Credit Adjustments                                                                               ||");

		System.out.println ("\t|    - Path #2 Period Bilateral Credit Adjustments                                                                                ||");

		System.out.println ("\t|    - Path #2 Period Credit Adjustments                                                                                          ||");

		System.out.println ("\t|    - Path #2 Period Contra-Liability Credit Adjustments                                                                         ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 1; i <= iNumStep; ++i) {
			System.out.println ("\t| [" +
				adtVertex[i - 1] + " -> " + adtVertex[i] + "] => " +
				FormatUtil.FormatDouble (adblPeriodUnilateralCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodBilateralCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodContraLiabilityCreditAdjustment1[i - 1], 1, 6, 1.) + " ||| " +
				FormatUtil.FormatDouble (adblPeriodUnilateralCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodBilateralCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodContraLiabilityCreditAdjustment2[i - 1], 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		double[] adblPeriodDebtAdjustment1 = ngpaa2014_1.periodDebtAdjustment();

		double[] adblPeriodFundingValueAdjustment1 = fgpaa2014_1.periodFundingValueAdjustment();

		double[] adblPeriodFundingDebtAdjustment1 = fgpaa2014_1.periodFundingDebtAdjustment();

		double[] adblPeriodFundingCostAdjustment1 = fgpaa2014_1.periodFundingCostAdjustment();

		double[] adblPeriodFundingBenefitAdjustment1 = fgpaa2014_1.periodFundingBenefitAdjustment();

		double[] adblPeriodSymmetricFundingValueAdjustment1 = fgpaa2014_1.periodSymmetricFundingValueAdjustment();

		double[] adblPeriodDebtAdjustment2 = ngpaa2014_2.periodDebtAdjustment();

		double[] adblPeriodFundingValueAdjustment2 = fgpaa2014_2.periodFundingValueAdjustment();

		double[] adblPeriodFundingDebtAdjustment2 = fgpaa2014_2.periodFundingDebtAdjustment();

		double[] adblPeriodFundingCostAdjustment2 = fgpaa2014_2.periodFundingCostAdjustment();

		double[] adblPeriodFundingBenefitAdjustment2 = fgpaa2014_2.periodFundingBenefitAdjustment();

		double[] adblPeriodSymmetricFundingValueAdjustment2 = fgpaa2014_2.periodSymmetricFundingValueAdjustment();

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                             DEBT VALUATION, FUNDING VALUATION, FUNDING DEBT, FUNDING COST, FUNDING BENEFIT, & SYMMETRIC FUNDING VALUATION ADJUSTMENTS                              ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|  L -> R:                                                                                                                                                                           ||");

		System.out.println ("\t|          - Path #1 Debt Valuation Adjustment                                                                                                                                       ||");

		System.out.println ("\t|          - Path #1 Funding Valuation Adjustment                                                                                                                                    ||");

		System.out.println ("\t|          - Path #1 Funding Debt Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #1 Funding Cost Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #1 Funding Benefit Adjustment                                                                                                                                      ||");

		System.out.println ("\t|          - Path #1 Symmatric Funding Valuation Adjustment                                                                                                                          ||");

		System.out.println ("\t|          - Path #2 Debt Valuation Adjustment                                                                                                                                       ||");

		System.out.println ("\t|          - Path #2 Funding Valuation Adjustment                                                                                                                                    ||");

		System.out.println ("\t|          - Path #2 Funding Debt Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #2 Funding Cost Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #2 Funding Benefit Adjustment                                                                                                                                      ||");

		System.out.println ("\t|          - Path #2 Symmatric Funding Valuation Adjustment                                                                                                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 1; i <= iNumStep; ++i) {
			System.out.println ("\t| [" +
				adtVertex[i - 1] + " -> " + adtVertex[i] + "] => " +
				FormatUtil.FormatDouble (adblPeriodDebtAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingValueAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingDebtAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingCostAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingBenefitAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodSymmetricFundingValueAdjustment1[i - 1], 1, 6, 1.) + " || " +
				FormatUtil.FormatDouble (adblPeriodDebtAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingValueAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingDebtAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingCostAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingBenefitAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodSymmetricFundingValueAdjustment2[i - 1], 1, 6, 1.) + " || "
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (
			new MonoPathExposureAdjustment[] {
				new MonoPathExposureAdjustment (
					new AlbaneseAndersenFundingGroupPath[] {fgpaa2014_1}
				),
				new MonoPathExposureAdjustment (
					new AlbaneseAndersenFundingGroupPath[] {fgpaa2014_2}
				)
			}
		);

		JulianDate[] adtVertexNode = eaa.vertexDates();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|         DATE         =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + " |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		double[] adblExposure = eaa.collateralizedExposure();

		strDump = "\t|       EXPOSURE       =>";

		for (int j = 0; j < adblExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposure = eaa.collateralizedPositiveExposure();

		strDump = "\t|  POSITIVE EXPOSURE   =>";

		for (int j = 0; j < adblPositiveExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposure = eaa.collateralizedNegativeExposure();

		strDump = "\t|  NEGATIVE EXPOSURE   =>";

		for (int j = 0; j < adblNegativeExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblExposurePV = eaa.collateralizedExposurePV();

		strDump = "\t|      EXPOSURE PV     =>";

		for (int j = 0; j < adblExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposurePV = eaa.collateralizedPositiveExposurePV();

		strDump = "\t| POSITIVE EXPOSURE PV =>";

		for (int j = 0; j < adblPositiveExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposurePV = eaa.collateralizedNegativeExposurePV();

		strDump = "\t| NEGATIVE EXPOSURE PV =>";

		for (int j = 0; j < adblNegativeExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		System.out.println ("\t||-------------------||");

		System.out.println ("\t||  UCVA  => " + FormatUtil.FormatDouble (eaa.ucva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t|| FTDCVA => " + FormatUtil.FormatDouble (eaa.ftdcva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVA   => " + FormatUtil.FormatDouble (eaa.cva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVACL => " + FormatUtil.FormatDouble (eaa.cvacl().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  DVA   => " + FormatUtil.FormatDouble (eaa.dva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FVA   => " + FormatUtil.FormatDouble (eaa.fva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FDA   => " + FormatUtil.FormatDouble (eaa.fda().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FCA   => " + FormatUtil.FormatDouble (eaa.fca().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FBA   => " + FormatUtil.FormatDouble (eaa.fba().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  SFVA  => " + FormatUtil.FormatDouble (eaa.sfva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||-------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t|| BURGARD KJAER REPLICATION PORTFOLIO #1 ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||    L -> R:                             ||");

		System.out.println ("\t||           - Bank Bond Units            ||");

		System.out.println ("\t||           - Bank Subordinate Units     ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i) {
			ReplicationPortfolioVertexDealer rpvb = aBKV1[i].dealerReplicationPortfolio();

			System.out.println ("\t|| [" + adtVertex[i] + "] =>   " +
				FormatUtil.FormatDouble (rpvb.seniorNumeraireHoldings(), 1, 3, 1.) + "  |  " +
				FormatUtil.FormatDouble (rpvb.subordinateNumeraireHoldings(), 1, 3, 1.) + "   || "
			);
		}

		System.out.println ("\t||----------------------------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t|| BURGARD KJAER REPLICATION PORTFOLIO #2 ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||    L -> R:                             ||");

		System.out.println ("\t||           - Bank Bond Units            ||");

		System.out.println ("\t||           - Bank Subordinate Units     ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i) {
			ReplicationPortfolioVertexDealer rpvb = aBKV2[i].dealerReplicationPortfolio();

			System.out.println ("\t|| [" + adtVertex[i] + "] =>   " +
				FormatUtil.FormatDouble (rpvb.seniorNumeraireHoldings(), 1, 3, 1.) + "  |  " +
				FormatUtil.FormatDouble (rpvb.subordinateNumeraireHoldings(), 1, 3, 1.) + "   || "
			);
		}

		System.out.println ("\t||----------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
