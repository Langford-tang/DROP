
package org.drip.sample.efficientfrontier;

import java.util.*;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.mpt.MarkovitzBullet;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>UnboundedMarkovitzBullet</i> demonstrates the Construction of the Efficient Frontier using the
 * Unconstrained Quadratic Mean Variance Optimizer.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/efficientfrontier/README.md">Efficient Frontier Markovitz Bullet Variants</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnboundedMarkovitzBullet
{

	private static void DisplayPortfolioMetrics (
		final HoldingsAllocation optimalOutput)
		throws Exception
	{
		AssetComponent[] globalMinimumAssetComponentArray =
			optimalOutput.optimalPortfolio().assetComponentArray();

		String strDump = "\t|" + FormatUtil.FormatDouble (
				optimalOutput.optimalMetrics().excessReturnsMean(), 1, 4, 100.
			) + "% |" + FormatUtil.FormatDouble (
				optimalOutput.optimalMetrics().excessReturnsStandardDeviation(), 1, 4, 100.
			) + " |";

		for (AssetComponent assetComponent : globalMinimumAssetComponentArray)
		{
			strDump += " " + FormatUtil.FormatDouble (
				assetComponent.amount(), 3, 2, 100.
			) + "% |";
		}

		System.out.println (strDump + "|");
	}

	public static final void main (
		final String[] agrumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int frontierSampleUnits = 20;
		double riskToleranceFactor = 0.;
		String seriesLocation =
			"C:\\DROP\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (
			seriesLocation
		);

		String[] variateHeaderArray = csvGrid.headers();

		String[] assetIDArray = new String[variateHeaderArray.length - 1];
		double[][] variateSampleGrid = new double[variateHeaderArray.length - 1][];

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			assetIDArray[assetIndex] = variateHeaderArray[assetIndex + 1];

			variateSampleGrid[assetIndex] = csvGrid.doubleArrayAtColumn (assetIndex + 1);
		}

		MarkovitzBullet markovitzBullet = new QuadraticMeanVarianceOptimizer().efficientFrontier (
			new HoldingsAllocationControl (
				assetIDArray,
				CustomRiskUtilitySettings.RiskTolerant (riskToleranceFactor),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					variateSampleGrid
				)
			),
			frontierSampleUnits
		);

		System.out.println ("\n\n\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     GLOBAL MINIMUM VARIANCE AND MAXIMUM RETURNS PORTFOLIOS                    ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		String header = "\t| RETURNS | RISK % |";

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			header += "   " + assetIDArray[assetIndex] + "    |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		DisplayPortfolioMetrics (markovitzBullet.globalMinimumVariance());

		DisplayPortfolioMetrics (markovitzBullet.longOnlyMaximumReturns());

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n\n");

		TreeMap<Double, HoldingsAllocation> frontierPortfolioMap = markovitzBullet.optimalPortfolioMap();

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|         EFFICIENT FRONTIER: PORTFOLIO RISK & RETURNS + CORRESPONDING ASSET ALLOCATION         ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		for (Map.Entry<Double, HoldingsAllocation> me : frontierPortfolioMap.entrySet())
		{
			DisplayPortfolioMetrics (
				me.getValue()
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n");

		EnvManager.TerminateEnv();
	}
}
