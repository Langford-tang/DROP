
package org.drip.sample.ckls;

import org.drip.dynamics.meanreverting.R1CIRStochasticEvolver;
import org.drip.measure.chisquare.R1NonCentral;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
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
 * <i>CIRFutureValueDistribution</i> demonstrates the Computation of the Future Value Distribution from an
 * 	Evolving R<sup>1</sup> Cox-Ingersoll-Ross Process. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics
 * 				<i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49
 * 		</li>
 * 		<li>
 * 			Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for
 * 				Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b>
 * 				Berlin-Heidelberg
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Fokker-Planck Equation
 * 				https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/ckls/README.md">Analysis of CKLS Process Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CIRFutureValueDistribution
{

	private static final void NonCentralChiSquareParameters (
		final double r0,
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double[] tArray)
		throws Exception
	{
		R1CIRStochasticEvolver r1CIRStochasticEvolver = R1CIRStochasticEvolver.Wiener (
			meanReversionSpeed,
			meanReversionLevel,
			0.05,
			0.01
		);

		java.lang.String dump = "\t| [" +
			FormatUtil.FormatDouble (meanReversionSpeed, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (meanReversionLevel, 1, 1, 1.) + "] =>";

		for (double t : tArray)
		{
			R1NonCentral r1NonCentral = r1CIRStochasticEvolver.futureValueDistribution (
				r0,
				t
			);

			dump = dump + " {" + FormatUtil.FormatDouble (
				r1NonCentral.parameters().degreesOfFreedom(), 1, 4, 1.
			) + " |" + FormatUtil.FormatDouble (
				r1NonCentral.parameters().nonCentralityParameter(), 1, 4, 1.
			) + "}";
		}

		System.out.println (dump);
	}

	private static final void NonCentralChiSquareStatistics (
		final double r0,
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double[] tArray)
		throws Exception
	{
		R1CIRStochasticEvolver r1CIRStochasticEvolver = R1CIRStochasticEvolver.Wiener (
			meanReversionSpeed,
			meanReversionLevel,
			0.05,
			0.01
		);

		java.lang.String dump = "\t| [" +
			FormatUtil.FormatDouble (meanReversionSpeed, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (meanReversionLevel, 1, 1, 1.) + "] =>";

		for (double t : tArray)
		{
			R1NonCentral r1NonCentral = r1CIRStochasticEvolver.futureValueDistribution (
				r0,
				t
			);

			dump = dump + " {" + FormatUtil.FormatDouble (
				r1NonCentral.mean(), 1, 4, 1.
			) + " |" + FormatUtil.FormatDouble (
				r1NonCentral.variance(), 1, 4, 1.
			) + "}";
		}

		System.out.println (dump);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double r0 = 3.0;
		double[] tArray =
		{
			 1.0,
			 2.0,
			 3.0,
			 4.0,
			 5.0,
			 6.0,
			 7.0,
		};
		double[] meanReversionLevelArray =
		{
			 2.0,
			 3.0,
			 4.0,
		};
		double[] meanReversionSpeedArray =
		{
			 0.5,
			 1.0,
			 1.5,
			 2.0,
			 2.5,
		};

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                          CIR FUTURE VALUE DISTRIBUTION                                                            ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                                    ||");

		System.out.println ("\t|                - Mean Reversion Speed                                                                                                             ||");

		System.out.println ("\t|                - Mean Reversion Level                                                                                                             ||");

		System.out.println ("\t|                - Volatility                                                                                                                       ||");

		System.out.println ("\t|                - Chi-Square Degrees of Freedom and Non-Centrality Parameter over t                                                                ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (double meanReversionSpeed : meanReversionSpeedArray)
		{
			for (double meanReversionLevel : meanReversionLevelArray)
			{
				NonCentralChiSquareParameters (
					r0,
					meanReversionSpeed,
					meanReversionLevel,
					tArray
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                          CIR FUTURE VALUE DISTRIBUTION                                                            ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                                    ||");

		System.out.println ("\t|                - Mean Reversion Speed                                                                                                             ||");

		System.out.println ("\t|                - Mean Reversion Level                                                                                                             ||");

		System.out.println ("\t|                - Volatility                                                                                                                       ||");

		System.out.println ("\t|                - Chi-Square Mean and Variance over t                                                                                              ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (double meanReversionSpeed : meanReversionSpeedArray)
		{
			for (double meanReversionLevel : meanReversionLevelArray)
			{
				NonCentralChiSquareStatistics (
					r0,
					meanReversionSpeed,
					meanReversionLevel,
					tArray
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
