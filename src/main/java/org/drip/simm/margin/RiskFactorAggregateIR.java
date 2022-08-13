
package org.drip.simm.margin;

import java.util.Map;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.parameters.BucketSensitivitySettingsIR;

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
 * <i>RiskFactorAggregateIR</i> holds the Sensitivity Margin Aggregates for each of the IR Risk Factors -
 * OIS, LIBOR 1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/README.md">ISDA SIMM Risk Factor Margin Metrics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskFactorAggregateIR
{
	private double _concentrationRiskFactor = Double.NaN;
	private Map<String, Double> _oisSensitivityMargin = null;
	private Map<String, Double> _primeSensitivityMargin = null;
	private Map<String, Double> _libor1MSensitivityMargin = null;
	private Map<String, Double> _libor3MSensitivityMargin = null;
	private Map<String, Double> _libor6MSensitivityMargin = null;
	private Map<String, Double> _libor12MSensitivityMargin = null;
	private Map<String, Double> _municipalSensitivityMargin = null;

	/**
	 * RiskFactorAggregateIR Constructor
	 * 
	 * @param oisSensitivityMargin The OIS Sensitivity Margin
	 * @param libor1MSensitivityMargin The LIBOR 1M Sensitivity Margin
	 * @param libor3MSensitivityMargin The LIBOR 3M Sensitivity Margin
	 * @param libor6MSensitivityMargin The LIBOR 6M Sensitivity Margin
	 * @param libor12MSensitivityMargin The LIBOR 12M Sensitivity Margin
	 * @param primeSensitivityMargin The PRIME Sensitivity Margin
	 * @param municipalSensitivityMargin The Municipal Sensitivity Margin
	 * @param concentrationRiskFactor The Currency's Concentration Risk Factor
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateIR (
		final Map<String, Double> oisSensitivityMargin,
		final Map<String, Double> libor1MSensitivityMargin,
		final Map<String, Double> libor3MSensitivityMargin,
		final Map<String, Double> libor6MSensitivityMargin,
		final Map<String, Double> libor12MSensitivityMargin,
		final Map<String, Double> primeSensitivityMargin,
		final Map<String, Double> municipalSensitivityMargin,
		final double concentrationRiskFactor)
		throws Exception
	{
		if (null == (_oisSensitivityMargin = oisSensitivityMargin) || 0 == _oisSensitivityMargin.size() ||
			null == (_libor1MSensitivityMargin = libor1MSensitivityMargin) ||
				0 == _libor1MSensitivityMargin.size() ||
			null == (_libor3MSensitivityMargin = libor3MSensitivityMargin) ||
				0 == _libor3MSensitivityMargin.size() ||
			null == (_libor6MSensitivityMargin = libor6MSensitivityMargin) ||
				0 == _libor6MSensitivityMargin.size() ||
			null == (_libor12MSensitivityMargin = libor12MSensitivityMargin) ||
				0 == _libor12MSensitivityMargin.size() ||
			null == (_municipalSensitivityMargin = municipalSensitivityMargin) ||
				0 == _municipalSensitivityMargin.size() ||
			null == (_primeSensitivityMargin = primeSensitivityMargin) ||
				0 == _primeSensitivityMargin.size() ||
			!NumberUtil.IsValid (
				_concentrationRiskFactor = concentrationRiskFactor
			)
		)
		 {
			 throw new Exception (
				 "RiskFactorAggregateIR Constructor => Invalid Inputs"
			 );
		 }
	}

	/**
	 * Retrieve the OIS Sensitivity Margin Map
	 * 
	 * @return The OIS Sensitivity Margin Map
	 */

	public Map<String, Double> oisSensitivityMargin()
	{
		return _oisSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 1M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 1M Sensitivity Margin Map
	 */

	public Map<String, Double> libor1MSensitivityMargin()
	{
		return _libor1MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 3M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 3M Sensitivity Margin Map
	 */

	public Map<String, Double> libor3MSensitivityMargin()
	{
		return _libor3MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 6M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 6M Sensitivity Margin Map
	 */

	public Map<String, Double> libor6MSensitivityMargin()
	{
		return _libor6MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 12M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 12M Sensitivity Margin Map
	 */

	public Map<String, Double> libor12MSensitivityMargin()
	{
		return _libor12MSensitivityMargin;
	}

	/**
	 * Retrieve the PRIME Sensitivity Margin Map
	 * 
	 * @return The PRIME Sensitivity Margin Map
	 */

	public Map<String, Double> primeSensitivityMargin()
	{
		return _primeSensitivityMargin;
	}

	/**
	 * Retrieve the MUNICIPAL Sensitivity Margin Map
	 * 
	 * @return The MUNICIPAL Sensitivity Margin Map
	 */

	public Map<String, Double> municipalSensitivityMargin()
	{
		return _municipalSensitivityMargin;
	}

	/**
	 * Retrieve the Bucket Concentration Risk Factor
	 * 
	 * @return The Bucket Concentration Risk Factor
	 */

	public double concentrationRiskFactor()
	{
		return _concentrationRiskFactor;
	}

	/**
	 * Compute the Cumulative OIS Sensitivity Margin
	 * 
	 * @return The Cumulative OIS Sensitivity Margin
	 */

	public double cumulativeOISSensitivityMargin()
	{
		double cumulativeOISSensitivityMargin = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry : _oisSensitivityMargin.entrySet())
		{
			cumulativeOISSensitivityMargin = cumulativeOISSensitivityMargin +
				oisSensitivityMarginEntry.getValue();
		}

		return cumulativeOISSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR1M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR1M Sensitivity Margin
	 */

	public double cumulativeLIBOR1MSensitivityMargin()
	{
		double cumulativeLIBOR1MSensitivityMargin = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry : _libor1MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR1MSensitivityMargin = cumulativeLIBOR1MSensitivityMargin +
				libor1MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR1MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR3M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR3M Sensitivity Margin
	 */

	public double cumulativeLIBOR3MSensitivityMargin()
	{
		double cumulativeLIBOR3MSensitivityMargin = 0.;

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR3MSensitivityMargin = cumulativeLIBOR3MSensitivityMargin +
				libor3MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR3MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR6M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR6M Sensitivity Margin
	 */

	public double cumulativeLIBOR6MSensitivityMargin()
	{
		double cumulativeLIBOR6MSensitivityMargin = 0.;

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR6MSensitivityMargin = cumulativeLIBOR6MSensitivityMargin +
				libor6MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR6MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR12M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR12M Sensitivity Margin
	 */

	public double cumulativeLIBOR12MSensitivityMargin()
	{
		double cumulativeLIBOR12MSensitivityMargin = 0.;

		for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			cumulativeLIBOR12MSensitivityMargin = cumulativeLIBOR12MSensitivityMargin +
				libor12MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR12MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative PRIME Sensitivity Margin
	 * 
	 * @return The Cumulative PRIME Sensitivity Margin
	 */

	public double cumulativePRIMESensitivityMargin()
	{
		double cumulativePRIMESensitivityMargin = 0.;

		for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
		{
			cumulativePRIMESensitivityMargin = cumulativePRIMESensitivityMargin +
				primeSensitivityMarginEntry.getValue();
		}

		return cumulativePRIMESensitivityMargin;
	}

	/**
	 * Compute the Cumulative MUNICIPAL Sensitivity Margin
	 * 
	 * @return The Cumulative MUNICIPAL Sensitivity Margin
	 */

	public double cumulativeMUNICIPALSensitivityMargin()
	{
		double cumulativeMUNICIPALSensitivityMargin = 0.;

		for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
			_municipalSensitivityMargin.entrySet())
		{
			cumulativeMUNICIPALSensitivityMargin = cumulativeMUNICIPALSensitivityMargin +
				municipalSensitivityMarginEntry.getValue();
		}

		return cumulativeMUNICIPALSensitivityMargin;
	}

	/**
	 * Compute the Cumulative Sensitivity Margin
	 * 
	 * @return The Cumulative Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		return cumulativeOISSensitivityMargin() +
			cumulativeLIBOR1MSensitivityMargin() +
			cumulativeLIBOR3MSensitivityMargin() +
			cumulativeLIBOR6MSensitivityMargin() +
			cumulativeLIBOR12MSensitivityMargin() +
			cumulativePRIMESensitivityMargin() +
			cumulativeMUNICIPALSensitivityMargin();
	}

	/**
	 * Compute the Linear OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_OIS (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"RiskFactorAggregateIR::linearMarginCovariance_OIS_OIS => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_OIS = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginOuterEntry : _oisSensitivityMargin.entrySet())
		{
			String outerTenor = oisSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = oisSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> oisSensitivityMarginInnerEntry :
				_oisSensitivityMargin.entrySet())
			{
				String innerTenor = oisSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_OIS_OIS = linearMarginCovariance_OIS_OIS + outerSensitivityMargin *
					oisSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_OIS;
	}

	/**
	 * Compute the Curvature OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_OIS (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"RiskFactorAggregateIR::curvatureMarginCovariance_OIS_OIS => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_OIS = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginOuterEntry : _oisSensitivityMargin.entrySet())
		{
			String outerTenor = oisSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = oisSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> oisSensitivityMarginInnerEntry : _oisSensitivityMargin.entrySet())
			{
				String innerTenor = oisSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_OIS_OIS = curvatureMarginCovariance_OIS_OIS +
					outerSensitivityMargin * oisSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_OIS;
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR1M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR1M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR1M = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginOuterEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor1MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor1MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor1MSensitivityMarginInnerEntry :
				_libor1MSensitivityMargin.entrySet())
			{
				String innerTenor = libor1MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR1M = linearMarginCovariance_LIBOR1M_LIBOR1M +
					outerSensitivityMargin * libor1MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR1M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR1M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR1M = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginOuterEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor1MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor1MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor1MSensitivityMarginInnerEntry :
				_libor1MSensitivityMargin.entrySet()
			)
			{
				String innerTenor = libor1MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				linearMarginCovariance_LIBOR1M_LIBOR1M = linearMarginCovariance_LIBOR1M_LIBOR1M +
					outerSensitivityMargin * libor1MSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_LIBOR3M = 0.;

		for (Map.Entry<String, Double> libor3MSensitivityMarginOuterEntry :
			_libor3MSensitivityMargin.entrySet()
		)
		{
			double outerSensitivityMargin = libor3MSensitivityMarginOuterEntry.getValue();

			String outerTenor = libor3MSensitivityMarginOuterEntry.getKey();

			for (Map.Entry<String, Double> libor3MSensitivityMarginInnerEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				String innerTenor = libor3MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR3M = linearMarginCovariance_LIBOR3M_LIBOR3M +
					outerSensitivityMargin * libor3MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_LIBOR3M = 0.;

		for (Map.Entry<String, Double> libor3MSensitivityMarginOuterEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			String outerTenor = libor3MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor3MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor3MSensitivityMarginInnerEntry :
				_libor3MSensitivityMargin.entrySet()
			)
			{
				String innerTenor = libor3MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_LIBOR3M_LIBOR3M = curvatureMarginCovariance_LIBOR3M_LIBOR3M +
					outerSensitivityMargin * libor3MSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Compute the Linear LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR6M_LIBOR6M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR6M_LIBOR6M = 0.;

		for (Map.Entry<String, Double> libor6MSensitivityMarginOuterEntry :
			_libor6MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor6MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor6MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginInnerEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String innerTenor = libor6MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR6M_LIBOR6M = linearMarginCovariance_LIBOR6M_LIBOR6M +
					outerSensitivityMargin * libor6MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Compute the Curvature LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_LIBOR6M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR6M_LIBOR6M = 0.;

		for (Map.Entry<String, Double> libor6MSensitivityMarginOuterEntry :
			_libor6MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor6MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor6MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginInnerEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String innerTenor = libor6MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_LIBOR6M_LIBOR6M = curvatureMarginCovariance_LIBOR6M_LIBOR6M +
					outerSensitivityMargin * libor6MSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Compute the Linear LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::marginCovariance_LIBOR12M_LIBOR12M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR12M_LIBOR12M = 0.;

		for (Map.Entry<String, Double> libor12MSensitivityMarginOuterEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor12MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor12MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginInnerEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String innerTenor = libor12MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR12M_LIBOR12M = linearMarginCovariance_LIBOR12M_LIBOR12M +
					outerSensitivityMargin * libor12MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Compute the Curvature LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_LIBOR12M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR12M_LIBOR12M = 0.;

		for (Map.Entry<String, Double> libor12MSensitivityMarginOuterEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String outerTenor = libor12MSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = libor12MSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginInnerEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				String innerTenor = libor12MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_LIBOR12M_LIBOR12M = curvatureMarginCovariance_LIBOR12M_LIBOR12M +
					outerSensitivityMargin * libor12MSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Compute the Linear PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_PRIME_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_PRIME_PRIME => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_PRIME_PRIME = 0.;

		for (Map.Entry<String, Double> primeSensitivityMarginOuterEntry :
			_primeSensitivityMargin.entrySet()
		)
		{
			String outerTenor = primeSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = primeSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginInnerEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String innerTenor = primeSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_PRIME_PRIME = linearMarginCovariance_PRIME_PRIME +
					outerSensitivityMargin * primeSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_PRIME_PRIME;
	}

	/**
	 * Compute the Curvature PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_PRIME_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_PRIME_PRIME => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_PRIME_PRIME = 0.;

		for (Map.Entry<String, Double> primeSensitivityMarginOuterEntry :
			_primeSensitivityMargin.entrySet()
		)
		{
			String outerTenor = primeSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = primeSensitivityMarginOuterEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginInnerEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String innerTenor = primeSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_PRIME_PRIME = curvatureMarginCovariance_PRIME_PRIME +
					outerSensitivityMargin * primeSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_PRIME_PRIME;
	}

	/**
	 * Compute the Linear MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_MUNICIPAL_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_MUNICIPAL_MUNICIPAL => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_MUNICIPAL_MUNICIPAL = 0.;

		for (Map.Entry<String, Double> municipalSensitivityMarginOuterEntry :
			_municipalSensitivityMargin.entrySet()
		)
		{
			double outerSensitivityMargin = municipalSensitivityMarginOuterEntry.getValue();

			String outerTenor = municipalSensitivityMarginOuterEntry.getKey();

			for (Map.Entry<String, Double> municipalSensitivityMarginInnerEntry :
				_municipalSensitivityMargin.entrySet())
			{
				String innerTenor = municipalSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_MUNICIPAL_MUNICIPAL = linearMarginCovariance_MUNICIPAL_MUNICIPAL +
					outerSensitivityMargin * municipalSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (
							innerTenor
						) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Curvature MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_MUNICIPAL_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_MUNICIPAL_MUNICIPAL => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_MUNICIPAL_MUNICIPAL = 0.;

		for (Map.Entry<String, Double> municipalSensitivityMarginOuterEntry :
			_municipalSensitivityMargin.entrySet()
		)
		{
			double outerSensitivityMargin = municipalSensitivityMarginOuterEntry.getValue();

			String outerTenor = municipalSensitivityMarginOuterEntry.getKey();

			for (Map.Entry<String, Double> municipalSensitivityMarginInnerEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String innerTenor = municipalSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (
					innerTenor
				) ? 1. : tenorCorrelation.entry (
					outerTenor,
					innerTenor
				);

				curvatureMarginCovariance_MUNICIPAL_MUNICIPAL = curvatureMarginCovariance_MUNICIPAL_MUNICIPAL
					+ outerSensitivityMargin * municipalSensitivityMarginInnerEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Linear OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR1M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_LIBOR1M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR1M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			String oisTenor = oisSensitivityMarginEntry.getKey();

			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
				_libor1MSensitivityMargin.entrySet()
			)
			{
				String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR1M = linearMarginCovariance_OIS_LIBOR1M +
					oisSensitivityMargin * libor1MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							libor1MTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor1MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR1M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR1M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR1M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR1M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			String oisTenor = oisSensitivityMarginEntry.getKey();

			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
				_libor1MSensitivityMargin.entrySet()
			)
			{
				String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					libor1MTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					libor1MTenor
				);

				curvatureMarginCovariance_OIS_LIBOR1M = curvatureMarginCovariance_OIS_LIBOR1M +
					oisSensitivityMargin * libor1MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
					crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR1M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR3M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet()
			)
			{
				String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR3M = linearMarginCovariance_OIS_LIBOR3M +
					oisSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							libor3MTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor3MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR3M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					libor3MTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					libor3MTenor
				);

				curvatureMarginCovariance_OIS_LIBOR3M = curvatureMarginCovariance_OIS_LIBOR3M +
					oisSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
					crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_LIBOR6M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR6M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR6M = linearMarginCovariance_OIS_LIBOR6M +
					oisSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							libor6MTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR6M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR6M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					libor6MTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					libor6MTenor
				);

				curvatureMarginCovariance_OIS_LIBOR6M = curvatureMarginCovariance_OIS_LIBOR6M +
					oisSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
					crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_LIBOR12M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR12M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivity = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR12M = linearMarginCovariance_OIS_LIBOR12M +
					oisSensitivity * libor12MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							libor12MTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR12M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR12M = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivity = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					libor12MTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					libor12MTenor
				);

				curvatureMarginCovariance_OIS_LIBOR12M = curvatureMarginCovariance_OIS_LIBOR12M +
					oisSensitivity * libor12MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
					crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_PRIME => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_PRIME = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_PRIME = linearMarginCovariance_OIS_PRIME + oisSensitivityMargin *
					primeSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							primeTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_PRIME => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_PRIME = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					primeTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					primeTenor
				);

				curvatureMarginCovariance_OIS_PRIME = curvatureMarginCovariance_OIS_PRIME +
					oisSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation *
					crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_OIS_MUNICIPAL => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_MUNICIPAL = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			String oisTenor = oisSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_MUNICIPAL = linearMarginCovariance_OIS_MUNICIPAL +
					oisSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							oisTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_OIS_MUNICIPAL => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_MUNICIPAL = 0.;

		for (Map.Entry<String, Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet()
		)
		{
			String oisTenor = oisSensitivityMarginEntry.getKey();

			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					oisTenor,
					municipalTenor
				);

				curvatureMarginCovariance_OIS_MUNICIPAL = curvatureMarginCovariance_OIS_MUNICIPAL +
					oisSensitivityMargin * municipalSensitivityMarginEntry.getValue() * crossTenorCorrelation
					* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR3M = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet()
			)
			{
				String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR3M = linearMarginCovariance_LIBOR1M_LIBOR3M +
					libor1MSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (
							libor3MTenor
						) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor3MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR3M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR3M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_LIBOR3M = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (Map.Entry<String, Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet()
			)
			{
				String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (
					libor3MTenor
				) ? 1. : tenorCorrelation.entry (
					libor1MTenor,
					libor3MTenor
				);

				curvatureMarginCovariance_LIBOR1M_LIBOR3M = curvatureMarginCovariance_LIBOR1M_LIBOR3M +
					libor1MSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * 
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR3M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR6M => Invalid Inputs"
			);
		}

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR6M = 0.;

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR6M = linearMarginCovariance_LIBOR1M_LIBOR6M +
					libor1MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (
							libor6MTenor
						) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR6M => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR1M_LIBOR6M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (
					libor6MTenor
				) ? 1. : tenorCorrelation.entry (
					libor1MTenor,
					libor6MTenor
				);

				curvatureMarginCovariance_LIBOR1M_LIBOR6M = curvatureMarginCovariance_LIBOR1M_LIBOR6M +
					libor1MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR6M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR12M => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR1M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR12M = linearMarginCovariance_LIBOR1M_LIBOR12M +
					libor1MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (
							libor12MTenor
						) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR12M => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR1M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (
					libor12MTenor
				) ? 1. : tenorCorrelation.entry (
					libor1MTenor,
					libor12MTenor
				);

				curvatureMarginCovariance_LIBOR1M_LIBOR12M = curvatureMarginCovariance_LIBOR1M_LIBOR12M +
					libor1MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_PRIME => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR1M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry : _libor1MSensitivityMargin.entrySet())
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_PRIME = linearMarginCovariance_LIBOR1M_PRIME +
					libor1MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (
							primeTenor
						) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_PRIME => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR1M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet()
		)
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (
					primeTenor
				) ? 1. : tenorCorrelation.entry (
					libor1MTenor,
					primeTenor
				);

				curvatureMarginCovariance_LIBOR1M_PRIME = curvatureMarginCovariance_LIBOR1M_PRIME +
					libor1MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
					* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR1M_MUNICIPAL => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR1M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry : _libor1MSensitivityMargin.entrySet())
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_MUNICIPAL = linearMarginCovariance_LIBOR1M_MUNICIPAL +
					libor1MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_MUNICIPAL => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR1M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor1MSensitivityMarginEntry : _libor1MSensitivityMargin.entrySet())
		{
			String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					libor1MTenor,
					municipalTenor
				);

				curvatureMarginCovariance_LIBOR1M_MUNICIPAL = curvatureMarginCovariance_LIBOR1M_MUNICIPAL +
					libor1MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR6M => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR3M_LIBOR6M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR6M = linearMarginCovariance_LIBOR3M_LIBOR6M +
					libor3MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (
							libor6MTenor
						) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR6M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR6M => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR3M_LIBOR6M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet()
			)
			{
				String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (
					libor6MTenor
				) ? 1. : tenorCorrelation.entry (
					libor3MTenor,
					libor6MTenor
				);

				curvatureMarginCovariance_LIBOR3M_LIBOR6M = curvatureMarginCovariance_LIBOR3M_LIBOR6M +
					libor3MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR6M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR12M => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR3M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR12M = linearMarginCovariance_LIBOR3M_LIBOR12M +
					libor3MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (
							libor12MTenor
						) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR12M => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR3M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (
					libor12MTenor
				) ? 1. : tenorCorrelation.entry (
					libor3MTenor,
					libor12MTenor
				);

				curvatureMarginCovariance_LIBOR3M_LIBOR12M = curvatureMarginCovariance_LIBOR3M_LIBOR12M +
					libor3MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR3M_PRIME => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR3M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_PRIME = linearMarginCovariance_LIBOR3M_PRIME +
					libor3MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (
							primeTenor
						) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_PRIME => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR3M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet()
			)
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (
					primeTenor
				) ? 1. : tenorCorrelation.entry (
					libor3MTenor,
					primeTenor
				);

				curvatureMarginCovariance_LIBOR3M_PRIME = curvatureMarginCovariance_LIBOR3M_PRIME +
					libor3MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
					* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR3M_MUNICIPAL => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR3M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_MUNICIPAL = linearMarginCovariance_LIBOR3M_MUNICIPAL +
					libor3MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_MUNICIPAL => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR3M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor3MSensitivityMarginEntry : _libor3MSensitivityMargin.entrySet())
		{
			String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					libor3MTenor,
					municipalTenor
				);

				curvatureMarginCovariance_LIBOR3M_MUNICIPAL = curvatureMarginCovariance_LIBOR3M_MUNICIPAL +
					libor3MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR6M_LIBOR12M => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR6M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_LIBOR12M = linearMarginCovariance_LIBOR6M_LIBOR12M +
					libor6MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (
							libor12MTenor
						) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_LIBOR12M (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_LIBOR12M => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR6M_LIBOR12M = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet()
			)
			{
				String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (
					libor12MTenor
				) ? 1. : tenorCorrelation.entry (
					libor6MTenor,
					libor12MTenor
				);

				curvatureMarginCovariance_LIBOR6M_LIBOR12M = curvatureMarginCovariance_LIBOR6M_LIBOR12M +
					libor6MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR6M_PRIME => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR6M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_PRIME = linearMarginCovariance_LIBOR6M_PRIME +
					libor6MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (
							primeTenor
						) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_PRIME => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR6M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (
					primeTenor
				) ? 1. : tenorCorrelation.entry (
					libor6MTenor,
					primeTenor
				);

				curvatureMarginCovariance_LIBOR6M_PRIME = curvatureMarginCovariance_LIBOR6M_PRIME +
					libor6MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
					* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR6M_MUNICIPAL => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR6M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_MUNICIPAL = linearMarginCovariance_LIBOR6M_MUNICIPAL +
					libor6MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_MUNICIPAL => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR6M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor6MSensitivityMarginEntry : _libor6MSensitivityMargin.entrySet())
		{
			String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					libor6MTenor,
					municipalTenor
				);

				curvatureMarginCovariance_LIBOR6M_MUNICIPAL = curvatureMarginCovariance_LIBOR6M_MUNICIPAL +
					libor6MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR12M_PRIME => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR12M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR12M_PRIME = linearMarginCovariance_LIBOR12M_PRIME +
					libor12MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (
							primeTenor
						) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_PRIME (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_PRIME => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR12M_PRIME = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
			{
				String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor12MTenor.equalsIgnoreCase (
					primeTenor
				) ? 1. : tenorCorrelation.entry (
					libor12MTenor,
					primeTenor
				);

				curvatureMarginCovariance_LIBOR12M_PRIME = curvatureMarginCovariance_LIBOR12M_PRIME +
					libor12MSensitivityMargin * primeSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_PRIME *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_LIBOR12M_MUNICIPAL => Invalid Inputs"
			);
		}

		double linearMarginCovariance_LIBOR12M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR12M_MUNICIPAL = linearMarginCovariance_LIBOR12M_MUNICIPAL +
					libor12MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_MUNICIPAL => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_LIBOR12M_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet()
		)
		{
			String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor12MTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					libor12MTenor,
					municipalTenor
				);

				curvatureMarginCovariance_LIBOR12M_MUNICIPAL = curvatureMarginCovariance_LIBOR12M_MUNICIPAL +
					libor12MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_PRIME_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::linearMarginCovariance_PRIME_MUNICIPAL => Invalid Inputs"
			);
		}

		double linearMarginCovariance_PRIME_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
		{
			String primeTenor = primeSensitivityMarginEntry.getKey();

			double primeSensitivityMargin = primeSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_PRIME_MUNICIPAL = linearMarginCovariance_PRIME_MUNICIPAL +
					primeSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						primeTenor.equalsIgnoreCase (
							municipalTenor
						) ? 1. : tenorCorrelation.entry (
							primeTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_PRIME_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_PRIME_MUNICIPAL (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new Exception (
				"IRFactorAggregate::curvatureMarginCovariance_PRIME_MUNICIPAL => Invalid Inputs"
			);
		}

		double curvatureMarginCovariance_PRIME_MUNICIPAL = 0.;

		LabelCorrelation tenorCorrelation = bucketSensitivitySettingsIR.crossTenorCorrelation();

		for (Map.Entry<String, Double> primeSensitivityMarginEntry : _primeSensitivityMargin.entrySet())
		{
			String primeTenor = primeSensitivityMarginEntry.getKey();

			double primeSensitivityMargin = primeSensitivityMarginEntry.getValue();

			for (Map.Entry<String, Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet()
			)
			{
				String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = primeTenor.equalsIgnoreCase (
					municipalTenor
				) ? 1. : tenorCorrelation.entry (
					primeTenor,
					municipalTenor
				);

				curvatureMarginCovariance_PRIME_MUNICIPAL = curvatureMarginCovariance_PRIME_MUNICIPAL +
					primeSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
					crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_PRIME_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear Margin Co-variance
	 */

	public SensitivityAggregateIR linearMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		try
		{
			return new SensitivityAggregateIR (
				linearMarginCovariance_OIS_OIS (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_LIBOR1M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_OIS_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_LIBOR1M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR1M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR3M_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR3M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR3M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR3M_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR3M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR6M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR6M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR6M_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR6M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR12M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR12M_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_LIBOR12M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_PRIME_PRIME (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_PRIME_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				linearMarginCovariance_MUNICIPAL_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				cumulativeSensitivityMargin()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Curvature Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature Margin Co-variance
	 */

	public SensitivityAggregateIR curvatureMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		try
		{
			return new SensitivityAggregateIR (
				curvatureMarginCovariance_OIS_OIS (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_LIBOR1M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_OIS_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_LIBOR1M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR1M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR3M_LIBOR3M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR3M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR3M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR3M_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR3M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR6M_LIBOR6M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR6M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR6M_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR6M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR12M_LIBOR12M (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR12M_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_LIBOR12M_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_PRIME_PRIME (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_PRIME_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				curvatureMarginCovariance_MUNICIPAL_MUNICIPAL (
					bucketSensitivitySettingsIR
				),
				cumulativeSensitivityMargin()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
