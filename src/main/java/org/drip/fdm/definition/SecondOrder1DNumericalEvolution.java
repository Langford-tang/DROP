
package org.drip.fdm.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>SecondOrder1DNumericalEvolution</i> implements key Second Order Finite Difference Schemes for
 *  R<sup>1</sup> State Space Evolution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Cebeci, T. (2002): <i>Convective Heat Transfer</i> <b>Horizon Publishing</b> Hammond, IN
 * 		</li>
 * 		<li>
 * 			Crank, J., and P. Nicolson (1947): A Practical Method for Numerical Evaluation of Solutions of
 * 				Partial Differential Equations of the Heat Conduction Type <i>Proceedings of the Cambridge
 * 				Philosophical Society</i> <b>43 (1)</b> 50-67
 * 		</li>
 * 		<li>
 * 			Thomas, J. W. (1995): <i>Numerical Partial Differential Equations: Finite Difference Methods</i>
 * 				<b>Springer-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Alternating-direction implicit method
 * 				https://en.wikipedia.org/wiki/Alternating-direction_implicit_method
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Crank�Nicolson method
 * 				https://en.wikipedia.org/wiki/Crank%E2%80%93Nicolson_method
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pde/README.md">Numerical Solution Schemes for PDEs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/README.md">Finite Difference PDE Evolver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SecondOrder1DNumericalEvolution
{
	private SecondOrder1DPDE _secondOrder1DPDE = null;
	private VonNeumann1DStabilityValidator _vonNeumann1DStabilityValidator = null;

	/**
	 * <i>SecondOrder1DNumericalEvolution</i> Constructor
	 * 
	 * @param secondOrder1DPDE Second Order R<sup>1</sup> State Space Evolution PDE
	 * @param vonNeuman1DStabilityValidator Space/Time von Neumann R<sup>1</sup> Stability Validator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public SecondOrder1DNumericalEvolution (
		final SecondOrder1DPDE secondOrder1DPDE,
		final VonNeumann1DStabilityValidator vonNeumann1DStabilityValidator)
		throws Exception
	{
		if (null == (_secondOrder1DPDE= secondOrder1DPDE) ||
			(null == (_vonNeumann1DStabilityValidator = vonNeumann1DStabilityValidator)))
		{
			throw new Exception ("SecondOrder1DNumericalEvolution Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Second Order R<sup>1</sup> State Space Evolution PDE
	 * 
	 * @return Second Order R<sup>1</sup> State Space Evolution PDE
	 */

	public SecondOrder1DPDE secondOrder1DPDE()
	{
		return _secondOrder1DPDE;
	}

	/**
	 * Retrieve the Space/Time von Neumann R<sup>1</sup> Stability Validator
	 * 
	 * @return Space/Time von Neumann R<sup>1</sup> Stability Validator
	 */

	public VonNeumannStabilityValidator vonNeumann1DStabilityValidator()
	{
		return _vonNeumann1DStabilityValidator;
	}

	/**
	 * Compute the State Increment using the Euler Forward Difference State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Euler Forward Difference State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double eulerForwardDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return _secondOrder1DPDE.timeDifferential (time, space);
	}

	/**
	 * Compute the State Increment using the Euler Backward Difference State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Euler Backward Difference State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double eulerBackwardDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return _secondOrder1DPDE.timeDifferential (
			time,
			space + _vonNeumann1DStabilityValidator.r1SpaceStep()
		);
	}

	/**
	 * Compute the State Increment using the Crank-Nicolson State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Crank-Nicolson State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double crankNicolsonDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return 0.5 * (
			_secondOrder1DPDE.timeDifferential (time, space) + _secondOrder1DPDE.timeDifferential (
				time,
				space + _vonNeumann1DStabilityValidator.r1SpaceStep()
			)
		);
	}
}
