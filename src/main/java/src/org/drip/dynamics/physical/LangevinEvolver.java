
package org.drip.dynamics.physical;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>LangevinEvolver</i> implements the Noisy Elastic Relaxation Process in a Friction-Thermal Background.
 * 	The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Doob, J. L. (1942): The Brownian Movement and Stochastic Equations <i>Annals of Mathematics</i>
 * 				<b>43 (2)</b> 351-369
 * 		</li>
 * 		<li>
 * 			Gardiner, C. W. (2009): <i>Stochastic Methods: A Handbook for the Natural and Social Sciences
 * 				4<sup>th</sup> Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Karatzas, I., and S. E. Shreve (1991): <i>Brownian Motion and Stochastic Calculus 2<sup>nd</sup>
 * 				Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Risken, H., and F. Till (1996): <i>The Fokker-Planck Equation � Methods of Solution and
 * 				Applications</i> <b>Springer</b>
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/physical/README.md">Implementation of Physical Process Dynamics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LangevinEvolver
	extends org.drip.dynamics.meanreverting.R1VasicekStochasticEvolver
{
	private double _restLength = java.lang.Double.NaN;
	private double _temperature = java.lang.Double.NaN;
	private double _dampingCoefficient = java.lang.Double.NaN;
	private double _elasticityCoefficient = java.lang.Double.NaN;

	/**
	 * R1NoisyRelaxationDrift Constructor
	 * 
	 * @param elasticityCoefficient Elastic Coefficient
	 * @param dampingCoefficient Damping Coefficient
	 * @param restLength Rest Length
	 * @param temperature The Temperature
	 * @param r1StochasticDriver The Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LangevinEvolver (
		final double elasticityCoefficient,
		final double dampingCoefficient,
		final double restLength,
		final double temperature,
		final org.drip.dynamics.ito.R1StochasticDriver r1StochasticDriver)
		throws java.lang.Exception
	{
		super (
			elasticityCoefficient / dampingCoefficient,
			restLength,
			java.lang.Math.sqrt (
				2. * org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * temperature /
					dampingCoefficient
			),
			r1StochasticDriver
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_elasticityCoefficient = elasticityCoefficient
			) || 0. >= _elasticityCoefficient || !org.drip.numerical.common.NumberUtil.IsValid (
				_dampingCoefficient = dampingCoefficient
			) || 0. >= _dampingCoefficient || !org.drip.numerical.common.NumberUtil.IsValid (
				_restLength = restLength
			) || 0. >= _restLength || !org.drip.numerical.common.NumberUtil.IsValid (
				_temperature = temperature
			) || 0. >= _temperature
		)
		{
			throw new java.lang.Exception (
				"R1NoisyRelaxationDrift Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Elasticity Coefficient
	 * 
	 * @return The Elasticity Coefficient
	 */

	public double elasticityCoefficient()
	{
		return _elasticityCoefficient;
	}

	/**
	 * Retrieve the Damping Coefficient
	 * 
	 * @return The Damping Coefficient
	 */

	public double dampingCoefficient()
	{
		return _dampingCoefficient;
	}

	/**
	 * Retrieve the Rest Length
	 * 
	 * @return The Rest Length
	 */

	public double restLength()
	{
		return _restLength;
	}

	/**
	 * Retrieve the Temperature
	 * 
	 * @return The Temperature
	 */

	public double temperature()
	{
		return _temperature;
	}

	/**
	 * Retrieve the Equi-Partition Energy
	 * 
	 * @return The Equi-Partition Energy
	 */

	public double equiPartitionEnergy()
	{
		return 0.5 * org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature;
	}

	/**
	 * Retrieve the Correlation Time
	 * 
	 * @return The Correlation Time
	 */

	public double correlationTime()
	{
		return _dampingCoefficient / _elasticityCoefficient;
	}

	/**
	 * Retrieve the Stokes-Einstein Effective Diffusion Coefficient
	 * 
	 * @return The Stokes-Einstein Effective Diffusion Coefficient
	 */

	public double stokesEinsteinEffectiveDiffusionCoefficient()
	{
		return org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature /
			_dampingCoefficient;
	}

	/**
	 * Retrieve the Fluctuation Co-variance
	 * 
	 * @param t The Time Snapshot
	 * 
	 * @return The Fluctuation Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fluctuationCovariance (
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			t
		))
		{
			throw new java.lang.Exception (
				"LangevinEvolver::fluctuationCovariance => Invalid Inputs"
			);
		}

		return org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature /
			_elasticityCoefficient * java.lang.Math.exp (
				-1. * java.lang.Math.abs (
					t
				) / correlationTime()
			);
	}

	/**
	 * Retrieve the Fluctuation Correlation
	 * 
	 * @param t The Time Snapshot
	 * 
	 * @return The Fluctuation Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fluctuationCorrelation (
		final double t)
		throws java.lang.Exception
	{
		return fluctuationCovariance (
			t
		) / _restLength / _restLength;
	}
}
