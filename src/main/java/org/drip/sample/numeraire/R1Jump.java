
package org.drip.sample.numeraire;

import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.JumpDiffusionEvolver;
import org.drip.measure.realization.*;
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
 * <i>R1Jump</i> demonstrates the Jump Evolution of a Default-able Asset. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/numeraire/README.md">R<sup>1</sup> Joint Jump Diffusion Numeraire</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class R1Jump {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTimeWidth = 1. / 24.;
		double dblTime = 0.;
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblAssetHazard = 0.05;
		double dblAssetDefaultMagnitude = 0.6;
		double dblTerminalAssetNumeraire = 1.;

		int iNumTimeStep = (int) (1. / dblTimeWidth);
		double[] adblTimeWidth = new double[iNumTimeStep];

		for (int i = 0; i < iNumTimeStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEvolver meAsset = new JumpDiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblAssetDrift,
				dblAssetVolatility
			),
			HazardJumpEvaluator.Standard (
				dblAssetHazard,
				dblAssetDefaultMagnitude
			)
		);

		double[] adblAssetNumeraireTimeSeries = SequenceGenerator.Gaussian (iNumTimeStep);

		double[] adblDefaultIndicatorTimeSeries = SequenceGenerator.Uniform (iNumTimeStep);

		JumpDiffusionEdge[] aR1AssetLR = meAsset.incrementSequence (
			new JumpDiffusionVertex (
				0.,
				dblTerminalAssetNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.JumpDiffusion (
				adblTimeWidth,
				adblAssetNumeraireTimeSeries,
				adblDefaultIndicatorTimeSeries
			),
			dblTimeWidth
		);

		System.out.println();

		for (int i = 0; i < iNumTimeStep; ++i) {
			dblTime = dblTime + dblTimeWidth;

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (dblTime, 1, 6, 1.) + " => " +
				FormatUtil.FormatDouble (aR1AssetLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].diffusionWander(), 1, 4, 1.) + " | " +
				aR1AssetLR[i].stochasticJumpEdge().jumpOccurred() + " ||"
			);
		}

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
