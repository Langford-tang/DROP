
package org.drip.sample.statistics;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.*;
import org.drip.measure.statistics.MultivariateDiscrete;
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
 * <i>CorrelatedRdSequenceAntithetic</i> demonstrates the Generation of the Statistical Measures for the
 * 	Input Correlated Sequence Set created using the Multi-Path Correlated Random Variable Generator without
 *  Quadratic Re-sampling, but with Antithetic Variables.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/statistics/README.md">Correlated R<sup>d</sup> Random Sequence Statistics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CorrelatedRdSequenceAntithetic {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumPath = 1;
		int iNumVertex = 50000;
		boolean bApplyAntithetic = true;

		double[][] aadblCorrelationInput = new double[][] {
			{1.000, 0.161, 0.245, 0.352, 0.259, 0.166, 0.003, 0.038, 0.114},	// USD_LIBOR_3M
			{0.161, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// EUR_LIBOR_3M
			{0.245, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// JPY_LIBOR_3M
			{0.352, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// CHF_LIBOR_3M
			{0.259, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000},	// GBP_LIBOR_3M
			{0.166, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000},	// EURUSD
			{0.003, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000},	// JPYUSD
			{0.038, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000},	// CHFUSD
			{0.114, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000},	// GBPUSD
		};

		CorrelatedPathVertexDimension cpvd = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			aadblCorrelationInput,
			iNumVertex,
			iNumPath,
			bApplyAntithetic,
			null
		);

		VertexRd vertexRd = cpvd.multiPathVertexRd()[0];

		MultivariateDiscrete mds = new MultivariateDiscrete (vertexRd.flatform());

		double[] adblMeanOutput = mds.mean();

		double[] adblErrorOutput = mds.error();

		double[] adblVarianceOutput = mds.variance();

		double[][] aadblCovarianceOutput = mds.covariance();

		double[][] aadblCorrelationOutput = mds.correlation();

		double[] adblStandardDeviationOutput = mds.standardDeviation();

		System.out.println();

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||                R^1 METRICS                ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||    L -> R:                                ||");

		System.out.println ("\t||            - Mean                         ||");

		System.out.println ("\t||            - Error                        ||");

		System.out.println ("\t||            - Variance                     ||");

		System.out.println ("\t||            - Standard Deviation           ||");

		System.out.println ("\t||-------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i)
			System.out.println ("\t|| " +
				FormatUtil.FormatDouble (adblMeanOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblErrorOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblVarianceOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblStandardDeviationOutput[i], 1, 5, 1.) + " ||"
			);

		System.out.println ("\t||-------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                     INPUT CORRELATION                                    ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCorrelationInput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                    OUTPUT CORRELATION                                    ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCorrelationOutput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                    OUTPUT COVARIANCE                                     ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCovarianceOutput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
