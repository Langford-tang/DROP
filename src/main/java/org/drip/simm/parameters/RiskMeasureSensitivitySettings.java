
package org.drip.simm.parameters;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>RiskMeasureSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual Risk Measure Buckets. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivitySettings
{
	private org.drip.measure.stochastic.LabelCorrelation _crossBucketCorrelation = null;
	private java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
		_bucketSettingsMap = null;

	/**
	 * Construct an ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_EQ_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_EQ_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_EQ_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_EQ_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_EQ_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.equity.EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.equity.EQSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_EQ_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.equity.EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_CT_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_CT_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> bucketMap =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap();

		java.util.Set<java.lang.Integer> bucketKeySet = bucketMap.keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_CT_20 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> bucketMap =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap();

		java.util.Set<java.lang.Integer> bucketKeySet = bucketMap.keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_CT_21 (bucketIndex)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer20.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_CT_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Set<java.lang.Integer> bucketKeySet =
			org.drip.simm.commodity.CTSettingsContainer21.BucketMap().keySet();

		try
		{
			for (int bucketIndex : bucketKeySet)
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_CT_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				org.drip.simm.commodity.CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryDeltaMap();

		java.util.Set<java.lang.Integer> fxConcentrationCategoryDeltaKey =
			fxConcentrationCategoryDeltaMap.keySet();

		java.util.List<java.lang.String> deltaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				deltaCategoryList.add ("" + deltaCategoryIndex);

				bucketDeltaSettingsMap.put (
					"" + deltaCategoryIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_FX_20 (deltaCategoryIndex)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.Integer, java.lang.Double> fxConcentrationCategoryDeltaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryDeltaMap();

		java.util.Set<java.lang.Integer> fxConcentrationCategoryDeltaKey =
			fxConcentrationCategoryDeltaMap.keySet();

		java.util.List<java.lang.String> deltaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				deltaCategoryList.add ("" + deltaCategoryIndex);

				bucketDeltaSettingsMap.put (
					"" + deltaCategoryIndex,
					org.drip.simm.parameters.BucketSensitivitySettings.ISDA_FX_21 (deltaCategoryIndex)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_20()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryVegaKey =
			fxConcentrationCategoryVegaMap.keySet();

		java.util.List<java.lang.String> vegaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (java.lang.String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (vegaCategoryOuter);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_FX_20 (vegaCategoryOuter)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_21()
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryVegaMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryVegaKey =
			fxConcentrationCategoryVegaMap.keySet();

		java.util.List<java.lang.String> vegaCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (java.lang.String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (vegaCategoryOuter);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					org.drip.simm.parameters.BucketVegaSettings.ISDA_FX_21 (vegaCategoryOuter)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_20 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryCurvatureMap =
			org.drip.simm.fx.FXRiskThresholdContainer20.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryCurvatureKey =
			fxConcentrationCategoryCurvatureMap.keySet();

		java.util.List<java.lang.String> curvatureCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (java.lang.String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (curvatureCategoryOuter);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_FX_20 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics20.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					curvatureCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_21 (
		final int vegaDurationDays)
	{
		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettings>();

		java.util.Map<java.lang.String, java.lang.Double> fxConcentrationCategoryCurvatureMap =
			org.drip.simm.fx.FXRiskThresholdContainer21.CategoryVegaMap();

		java.util.Set<java.lang.String> fxConcentrationCategoryCurvatureKey =
			fxConcentrationCategoryCurvatureMap.keySet();

		java.util.List<java.lang.String> curvatureCategoryList = new java.util.ArrayList<java.lang.String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (java.lang.String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (curvatureCategoryOuter);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					org.drip.simm.parameters.BucketCurvatureSettings.ISDA_FX_21 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						org.drip.simm.fx.FXSystemics21.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					curvatureCategoryList,
					crossBucketCorrelationMatrix
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskMeasureSensitivitySettings Constructor
	 * 
	 * @param bucketSettingsMap The Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation The Cross Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettings (
		final java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
			bucketSettingsMap,
		final org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (null == (_bucketSettingsMap = bucketSettingsMap) || 0 == _bucketSettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation))
		{
			throw new java.lang.Exception ("RiskMeasureSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Sensitivity Settings Map
	 * 
	 * @return The Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettings>
		bucketSettingsMap()
	{
		return _bucketSettingsMap;
	}
}
