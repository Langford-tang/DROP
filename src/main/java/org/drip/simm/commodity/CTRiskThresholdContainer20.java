
package org.drip.simm.commodity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * <i>CTRiskThresholdContainer20</i> holds the ISDA SIMM 2.0 Commodity Risk Thresholds - the Commodity
 * Buckets and the Delta/Vega Limits defined for the Concentration Thresholds. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity">Commodity</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CTRiskThresholdContainer20
{
	private static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		s_DeltaVegaThresholdMap = new java.util.TreeMap<java.lang.Integer,
			org.drip.simm.common.DeltaVegaThreshold>();

	/**
	 * Initialize the Commodity Risk Threshold Container
	 * 
	 * @return TRUE - The Commodity Risk Threshold Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_DeltaVegaThresholdMap.put (
				1,
				new org.drip.simm.common.DeltaVegaThreshold (
					1400.,
					250.
				)
			);

			s_DeltaVegaThresholdMap.put (
				2,
				new org.drip.simm.common.DeltaVegaThreshold (
					20000.,
					2000.
				)
			);

			s_DeltaVegaThresholdMap.put (
				3,
				new org.drip.simm.common.DeltaVegaThreshold (
					3500.,
					510.
				)
			);

			s_DeltaVegaThresholdMap.put (
				4,
				new org.drip.simm.common.DeltaVegaThreshold (
					3500.,
					510.
				)
			);

			s_DeltaVegaThresholdMap.put (
				5,
				new org.drip.simm.common.DeltaVegaThreshold (
					3500.,
					510.
				)
			);

			s_DeltaVegaThresholdMap.put (
				6,
				new org.drip.simm.common.DeltaVegaThreshold (
					6400.,
					1900.
				)
			);

			s_DeltaVegaThresholdMap.put (
				7,
				new org.drip.simm.common.DeltaVegaThreshold (
					6400.,
					1900.
				)
			);

			s_DeltaVegaThresholdMap.put (
				8,
				new org.drip.simm.common.DeltaVegaThreshold (
					2500.,
					870.
				)
			);

			s_DeltaVegaThresholdMap.put (
				9,
				new org.drip.simm.common.DeltaVegaThreshold (
					2500.,
					870.
				)
			);

			s_DeltaVegaThresholdMap.put (
				10,
				new org.drip.simm.common.DeltaVegaThreshold (
					300.,
					220.
				)
			);

			s_DeltaVegaThresholdMap.put (
				11,
				new org.drip.simm.common.DeltaVegaThreshold (
					2900.,
					450.
				)
			);

			s_DeltaVegaThresholdMap.put (
				12,
				new org.drip.simm.common.DeltaVegaThreshold (
					7600.,
					740.
				)
			);

			s_DeltaVegaThresholdMap.put (
				13,
				new org.drip.simm.common.DeltaVegaThreshold (
					3900.,
					370.
				)
			);

			s_DeltaVegaThresholdMap.put (
				14,
				new org.drip.simm.common.DeltaVegaThreshold (
					3900.,
					370.
				)
			);

			s_DeltaVegaThresholdMap.put (
				15,
				new org.drip.simm.common.DeltaVegaThreshold (
					3900.,
					370.
				)
			);

			s_DeltaVegaThresholdMap.put (
				16,
				new org.drip.simm.common.DeltaVegaThreshold (
					300.,
					220.
				)
			);

			s_DeltaVegaThresholdMap.put (
				17,
				new org.drip.simm.common.DeltaVegaThreshold (
					12000.,
					430.
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Commodity Risk Threshold Bucket Set
	 * 
	 * @return The Commodity Risk Threshold Bucket Set
	 */

	public static final java.util.Set<java.lang.Integer> BucketSet()
	{
		return s_DeltaVegaThresholdMap.keySet();
	}

	/**
	 * Indicate if the Bucket Number is available in the Commodity Risk Threshold Container
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return TRUE - The Bucket Number is available in the Commodity Risk Threshold Container
	 */

	public static final boolean ContainsBucket (
		final int bucketNumber)
	{
		return s_DeltaVegaThresholdMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Threshold indicated by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Threshold indicated by the Bucket Number
	 */

	public static final org.drip.simm.common.DeltaVegaThreshold Threshold (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_DeltaVegaThresholdMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Delta Vega Threshold Map
	 * 
	 * @return The Delta Vega Threshold Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.common.DeltaVegaThreshold>
		DeltaVegaThresholdMap()
	{
		return s_DeltaVegaThresholdMap;
	}
}
