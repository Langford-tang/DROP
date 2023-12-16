
package org.drip.validation.distance;

import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>HypothesisOutcomeSuite</i> holds the Map of Hypotheses Outcomes to be subjected to Discriminatory Power
 * Analysis.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): <i>Discounting, LIBOR, CVA, and Funding: Interest Rate and
 *  			Credit Pricing</i> <b>Palgrave Macmillan</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/README.md">Hypothesis Target Distance Test Builders</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HypothesisOutcomeSuite
{
	private TreeMap<Double, String> _distanceHypothesisMap = new TreeMap<Double, String>();

	private Map<String, GapTestOutcome> _outcomeMap = new CaseInsensitiveHashMap<GapTestOutcome>();

	/**
	 * Empty HypothesisOutcomeSuite Constructor
	 */

	public HypothesisOutcomeSuite()
	{
	}

	/**
	 * Retrieve the Outcome Map
	 * 
	 * @return The Outcome Map
	 */

	public Map<String, GapTestOutcome> outcomeMap()
	{
		return _outcomeMap;
	}

	/**
	 * Retrieve the Distance-Hypothesis Map
	 * 
	 * @return The Distance-Hypothesis Map
	 */

	public TreeMap<Double, String> distanceHypothesisMap()
	{
		return _distanceHypothesisMap;
	}

	/**
	 * Add a Hypothesis - Outcome Instance
	 * 
	 * @param hypothesisID Hypothesis ID
	 * @param gapTestOutcome Gap Test Outcome
	 * 
	 * @return TRUE - The Hypothesis - Outcome successfully added
	 */

	public boolean add (
		final String hypothesisID,
		final GapTestOutcome gapTestOutcome)
	{
		if (null == hypothesisID || hypothesisID.isEmpty() || null == gapTestOutcome) {
			return false;
		}

		_outcomeMap.put (hypothesisID, gapTestOutcome);

		_distanceHypothesisMap.put (gapTestOutcome.distance(), hypothesisID);

		return true;
	}

	/**
	 * Retrieve the Leading/Best Fit Hypothesis and its Test Outcome
	 * 
	 * @return The Leading/Best Fit Hypothesis and its Test Outcome
	 */

	public HypothesisOutcome leadingHypothesis()
	{
		if (0 == _distanceHypothesisMap.size()) {
			return null;
		}

		String leadingHypothesisID = _distanceHypothesisMap.firstEntry().getValue();

		try {
			return new HypothesisOutcome (leadingHypothesisID, _outcomeMap.get (leadingHypothesisID));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
