
package org.drip.learning.regularization;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>RegularizerBuilder</i> constructs Custom Regularizers for the different Normed Learner Function Types.
 * The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization">Regularization</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RegularizerBuilder {

	/**
	 * Construct an Instance of R^1 Combinatorial To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param funcSpaceR1ToR1 The R^1 Combinatorial To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 R1CombinatorialToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.rxtor1.NormedR1CombinatorialToR1Continuous funcSpaceR1ToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceR1ToR1 ? null : new
				org.drip.learning.regularization.RegularizerR1CombinatorialToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Combinatorial)
						funcSpaceR1ToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceR1ToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^1 Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param funcSpaceR1ToR1 The R^1 Continuous To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 R1ContinuousToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous funcSpaceR1ToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceR1ToR1 ? null : new
				org.drip.learning.regularization.RegularizerR1ContinuousToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Continuous)
						funcSpaceR1ToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceR1ToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^1 Combinatorial/Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param r1Input The R^1 Combinatorial/Continuous Input Space
	 * @param r1ContinuousOutput The R^1 Continuous Output Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial/Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerR1ToR1 ToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.metric.R1Normed r1Input,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final double dblLambda)
	{
		if (null == r1Input) return null;

		try {
			if (r1Input instanceof org.drip.spaces.metric.R1Continuous)
				return new org.drip.learning.regularization.RegularizerR1ContinuousToR1Continuous
					(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Continuous) r1Input,
						r1ContinuousOutput, dblLambda);

			return new org.drip.learning.regularization.RegularizerR1CombinatorialToR1Continuous
				(funcRegularizerR1ToR1, (org.drip.spaces.metric.R1Combinatorial) r1Input,
					r1ContinuousOutput, dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Combinatorial To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param funcSpaceRdToR1 The R^d Combinatorial To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^d Combinatorial To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 RdCombinatorialToR1Continuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.rxtor1.NormedRdCombinatorialToR1Continuous funcSpaceRdToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceRdToR1 ? null : new
				org.drip.learning.regularization.RegularizerRdCombinatorialToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdCombinatorialBanach)
						funcSpaceRdToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceRdToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param funcSpaceRdToR1 The R^d Continuous To R^1 Continuous Learner Function Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^d Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 RdContinuousToR1Continuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.rxtor1.NormedRdContinuousToR1Continuous funcSpaceRdToR1,
		final double dblLambda)
	{
		try {
			return null == funcSpaceRdToR1 ? null : new
				org.drip.learning.regularization.RegularizerRdContinuousToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdContinuousBanach)
						funcSpaceRdToR1.inputMetricVectorSpace(), (org.drip.spaces.metric.R1Continuous)
							funcSpaceRdToR1.outputMetricVectorSpace(), dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of R^d Combinatorial/Continuous To R^1 Continuous Regularizer
	 * 
	 * @param funcRegularizerRdToR1 The R^d To R^1 Regularizer Function
	 * @param rdInput The R^d Combinatorial/Continuous Input Space
	 * @param r1ContinuousOutput The R^1 Continuous Output Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @return The R^1 Combinatorial/Continuous To R^1 Continuous Regularizer Instance
	 */

	public static final org.drip.learning.regularization.RegularizerRdToR1 ToRdContinuous (
		final org.drip.function.definition.RdToR1 funcRegularizerRdToR1,
		final org.drip.spaces.metric.RdNormed rdInput,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final double dblLambda)
	{
		if (null == rdInput) return null;

		try {
			if (rdInput instanceof org.drip.spaces.metric.RdContinuousBanach)
				return new org.drip.learning.regularization.RegularizerRdContinuousToR1Continuous
					(funcRegularizerRdToR1, (org.drip.spaces.metric.RdContinuousBanach) rdInput,
						r1ContinuousOutput, dblLambda);

			return new org.drip.learning.regularization.RegularizerRdCombinatorialToR1Continuous
				(funcRegularizerRdToR1, (org.drip.spaces.metric.RdCombinatorialBanach) rdInput,
					r1ContinuousOutput, dblLambda);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
