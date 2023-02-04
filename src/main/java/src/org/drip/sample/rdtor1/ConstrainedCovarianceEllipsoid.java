
package org.drip.sample.rdtor1;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ConstrainedCovarianceEllipsoid</i> demonstrates the Construction and Usage of a Co-variance Ellipsoid
 * 	with Linear Constraints.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/rdtor1/README.md">Constrained/Unconstrained Covariance Ellipsoid Function</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstrainedCovarianceEllipsoid {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[][] aadblCovarianceMatrix = new double[][] {
			{0.09, 0.12},
			{0.12, 0.04}
		};

		double[] adblEqualityConstraint = new double[] {
			1.,
			1.
		};

		double dblEqualityConstraintConstant = -1.;

		AffineMultivariate lmConstraintRdToR1 = new AffineMultivariate (
			adblEqualityConstraint,
			dblEqualityConstraintConstant
		);

		CovarianceEllipsoidMultivariate ceObjectiveRdToR1 = new CovarianceEllipsoidMultivariate (aadblCovarianceMatrix);

		LagrangianMultivariate ceec = new LagrangianMultivariate (
			ceObjectiveRdToR1,
			new RdToR1[] {
				lmConstraintRdToR1
			}
		);

		double[][] aadblVariate = {
			{0.0, 1.0, 1.0},
			{0.1, 0.9, 1.0},
			{0.2, 0.8, 1.0},
			{0.3, 0.7, 1.0},
			{0.4, 0.6, 1.0},
			{0.5, 0.5, 1.0},
			{0.6, 0.4, 1.0},
			{0.7, 0.3, 1.0},
			{0.8, 0.2, 1.0},
			{0.9, 0.1, 1.0},
			{1.0, 0.0, 1.0},
		};

		System.out.println ("\n\n\t|------------------------||");

		System.out.println ("\t|       POINT VALUE      ||");

		System.out.println ("\t|------------------------||");

		for (double[] adblVariate : aadblVariate)
			System.out.println (
				"\t|  [" + adblVariate[0] +
				" | " + adblVariate[1] +
				"] = " + FormatUtil.FormatDouble (ceec.evaluate (adblVariate), 1, 4, 1.) + " ||"
			);

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\n\t|-------------------------------------------||");

		System.out.println ("\t|                 JACOBIAN                  ||");

		System.out.println ("\t|-------------------------------------------||");

		for (double[] adblVariate : aadblVariate) {
			String strJacobian = "";

			double[] adblJacobian = ceec.jacobian (adblVariate);

			for (double dblJacobian : adblJacobian)
				strJacobian += FormatUtil.FormatDouble (dblJacobian, 1, 4, 1.) + ",";

			System.out.println (
				"\t|  [" + adblVariate[0] +
				" | " + adblVariate[1] +
				"] = {" + strJacobian + "} ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||");

		double[][] aadblHessian = ceec.hessian (
			new double[] {
				0.20,
				0.80,
				1.
			}
		);

		System.out.println ("\n\n\t|----------------------------||");

		System.out.println ("\t|          HESSIAN           ||");

		System.out.println ("\t|----------------------------||");

		for (double[] adblHessian : aadblHessian) {
			String strHessian = "";

			for (double dblHessian : adblHessian)
				strHessian += FormatUtil.FormatDouble (dblHessian, 1, 4, 1.) + ",";

			System.out.println ("\t| [" + strHessian + "] ||");
		}

		System.out.println ("\t|----------------------------||");

		EnvManager.TerminateEnv();
	}
}
