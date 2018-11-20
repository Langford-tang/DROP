
package org.drip.simm.product;

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
 * <i>BucketSensitivityCR</i> holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across CR Tenor
 * Factors. The References are:
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product">Product</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivityCR
{
	private org.drip.simm.product.RiskFactorTenorSensitivity _cumulativeTenorSensitivityMap = null;
	private java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
		_tenorSensitivityMap = null;

	private org.drip.simm.margin.BucketAggregateCR linearAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregate = curveAggregate
			(bucketSensitivitySettings);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		org.drip.simm.margin.SensitivityAggregateCR sensitivityAggregate = riskFactorAggregate.linearMargin
			(bucketSensitivitySettings);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateCR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.simm.margin.BucketAggregateCR curvatureAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregate = curveAggregate
			(bucketSensitivitySettings);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		org.drip.simm.margin.SensitivityAggregateCR sensitivityAggregate =
			riskFactorAggregate.curvatureMargin (bucketSensitivitySettings);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.BucketAggregateCR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivityCR Constructor
	 * 
	 * @param tenorSensitivityMap The Risk Factor Tenor Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityCR (
		final java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
			tenorSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_tenorSensitivityMap = tenorSensitivityMap) || 0 == _tenorSensitivityMap.size())
		{
			throw new java.lang.Exception ("BucketSensitivityCR Constructor => Invalid Inputs");
		}

		java.util.Map<java.lang.String, java.lang.Double> riskFactorTenorSensitivityMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
			tenorSensitivityMapEntry : _tenorSensitivityMap.entrySet())
		{
			java.util.Map<java.lang.String, java.lang.Double> componentRiskFactorTenorSensitivityMap =
				tenorSensitivityMapEntry.getValue().sensitivityMap();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double>
				componentRiskFactorTenorSensitivityMapEntry :
				componentRiskFactorTenorSensitivityMap.entrySet())
			{
				java.lang.String tenor = componentRiskFactorTenorSensitivityMapEntry.getKey();

				if (riskFactorTenorSensitivityMap.containsKey (tenor))
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						riskFactorTenorSensitivityMap.get (tenor) +
							componentRiskFactorTenorSensitivityMap.get (tenor)
					);
				}
				else
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						componentRiskFactorTenorSensitivityMap.get (tenor)
					);
				}
			}
		}

		_cumulativeTenorSensitivityMap = new org.drip.simm.product.RiskFactorTenorSensitivity
			(riskFactorTenorSensitivityMap);
	}

	/**
	 * Retrieve the Cumulative Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Cumulative Risk Factor Tenor Sensitivity Map
	 */

	public org.drip.simm.product.RiskFactorTenorSensitivity cumulativeTenorSensitivityMap()
	{
		return _cumulativeTenorSensitivityMap;
	}

	/**
	 * Retrieve the Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Risk Factor Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.product.RiskFactorTenorSensitivity>
		tenorSensitivityMap()
	{
		return _tenorSensitivityMap;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulativeTenorSensitivity()
	{
		return _cumulativeTenorSensitivityMap.cumulative();
	}

	/**
	 * Compute the Sensitivity Concentration Risk Factor
	 * 
	 * @param sensitivityConcentrationThreshold The Sensitivity Concentration Threshold
	 * 
	 * @return The Sensitivity Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double sensitivityConcentrationRiskFactor (
		final double sensitivityConcentrationThreshold)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (sensitivityConcentrationThreshold))
		{
			throw new java.lang.Exception
				("BucketSensitivityCR::sensitivityConcentrationRiskFactor => Invalid Inputs");
		}

		return java.lang.Math.max (
			java.lang.Math.sqrt (
				java.lang.Math.max (
					cumulativeTenorSensitivity(),
					0.
				) / sensitivityConcentrationThreshold
			),
			1.
		);
	}

	/**
	 * Generate the Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin =
			_cumulativeTenorSensitivityMap.sensitivityMargin (bucketSensitivitySettings.tenorRiskWeight());

		if (null == tenorSensitivityMargin)
		{
			return tenorSensitivityMargin;
		}

		double sensitivityConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor
				(bucketSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntry :
			tenorSensitivityMargin.entrySet())
		{
			java.lang.String tenor = tenorSensitivityMarginEntry.getKey();

			tenorSensitivityMargin.put (
				tenor,
				tenorSensitivityMargin.get (tenor) * sensitivityConcentrationRiskFactor
			);
		}

		return tenorSensitivityMargin;
	}

	/**
	 * Generate the CR Margin Factor Curve Tenor Aggregate
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The CR Margin Factor Curve Tenor Aggregate
	 */

	public org.drip.simm.margin.RiskFactorAggregateCR curveAggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		java.util.Set<java.lang.String> componentNameSet = _tenorSensitivityMap.keySet();

		java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>
			componentTenorSensitivityMargin = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.util.Map<java.lang.String,
					java.lang.Double>>();

		double sensitivityConcentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor
				(bucketSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.lang.String componentName : componentNameSet)
		{
			java.util.Map<java.lang.String, java.lang.Double> tenorSensitivity = _tenorSensitivityMap.get
				(componentName).sensitivityMap();

			java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityEntry :
				tenorSensitivity.entrySet())
			{
				java.lang.String tenor = tenorSensitivityEntry.getKey();

				tenorSensitivityMargin.put (
					tenor,
					tenorSensitivity.get (tenor) * sensitivityConcentrationRiskFactor
				);
			}

			componentTenorSensitivityMargin.put (
				componentName,
				tenorSensitivityMargin
			);
		}

		try
		{
			return new org.drip.simm.margin.RiskFactorAggregateCR (
				componentTenorSensitivityMargin,
				sensitivityConcentrationRiskFactor
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Bucket CR Sensitivity Margin Aggregate
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * 
	 * @return The Bucket IR Sensitivity Margin Aggregate
	 */

	public org.drip.simm.margin.BucketAggregateCR aggregate (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		return bucketSensitivitySettingsCR instanceof org.drip.simm.parameters.BucketCurvatureSettingsCR ?
			curvatureAggregate (bucketSensitivitySettingsCR) : linearAggregate (bucketSensitivitySettingsCR);
	}
}
