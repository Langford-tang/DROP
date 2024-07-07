
package org.drip.numerical.linearalgebra;

import org.drip.numerical.common.NumberUtil;

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
 * <i>ShermanMorrisonSolver</i> implements the O(n) solver for a Tridiagonal Matrix with Periodic Boundary
 * 	Conditions. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Batista, M., and A. R. A. Ibrahim-Karawia (2009): The use of Sherman-Morrison-Woodbury formula
 * 				to solve cyclic block tridiagonal and cyclic block penta-diagonal linear systems of equations
 * 				<i>Applied Mathematics of Computation</i> <b>210 (2)</b> 558-563
 * 		</li>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Gallopoulos, E., B. Phillippe, and A. H. Sameh (2016): <i>Parallelism in Matrix Computations</i>
 * 				<b>Spring</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Ryaben�kii, V. S., and S. V. Tsynkov (2006): <i>Theoretical Introduction to Numerical
 * 				Analysis</i> <b>Wolters Kluwer</b> Aalphen aan den Rijn, Netherlands
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Tridiagonal Matrix Algorithm
 * 				https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShermanMorrisonSolver extends TridiagonalSolver
{

	/**
	 * Batistia-Karawia Default Gamma
	 */

	public static final double BATISTA_KARAWIA_DEFAULT_GAMMA = 1.;

	private double _gamma = Double.NaN;

	/**
	 * Construct a Standard Gamma Instance of Sherman Morrison Solver
	 * 
	 * @param squareMatrix Square Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @return Standard Gamma Instance of Sherman Morrison Solver
	 */

	public static final ShermanMorrisonSolver Standard (
		final double[][] squareMatrix,
		final double[] rhsArray)
	{
		try {
			return new ShermanMorrisonSolver (
				squareMatrix,
				rhsArray,
				BATISTA_KARAWIA_DEFAULT_GAMMA
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Batista-Karawia Instance of Sherman Morrison Solver
	 * 
	 * @param squareMatrix Square Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @return Standard Batista-Karawia Instance of Sherman Morrison Solver
	 */

	public static final ShermanMorrisonSolver StandardBatistaKarawia (
		final double[][] squareMatrix,
		final double[] rhsArray)
	{
		if (null == squareMatrix || 0 == squareMatrix.length) {
			return null;
		}

		int index = 1;
		double gamma = -1. * squareMatrix[0][0];

		while (0. == gamma && index < squareMatrix.length) {
			gamma = -1. * squareMatrix[index][index];
			++index;
		}

		if (0. == gamma) {
			return null;
		}

		try {
			return new ShermanMorrisonSolver (squareMatrix, rhsArray, gamma);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>ShermanMorrisonSolver</i> Constructor
	 * 
	 * @param squareMatrix Square Matrix
	 * @param rhsArray RHS Array
	 * @param gamma Gamma
	 * 
	 * @throws Exception Thrown if the Square Matrix is not Periodic Tridiagonal
	 */

	public ShermanMorrisonSolver (
		final double[][] squareMatrix,
		final double[] rhsArray,
		final double gamma)
		throws Exception
	{
		super (squareMatrix, rhsArray);

		if (!MatrixUtil.IsPeriodicTridiagonal (squareMatrix) ||
			!NumberUtil.IsValid (_gamma = gamma) || 0. == _gamma)
		{
			throw new Exception ("ShermanMorrisonSolver Constructor => Matrix not Periodic Tridiagonal");
		}
	}

	/**
	 * Retrieve the Gamma
	 * 
	 * @return Gamma
	 */

	public double gamma()
	{
		return _gamma;
	}

	/**
	 * Construct a Batista-Karawia Modification to the Periodic Tridiagonal Matrix
	 * 
	 * @return Batista-Karawia Modification to the Periodic Tridiagonal Matrix
	 */

	public double[][] batistaKarawiaMatrix()
	{
		double[] rhsArray = rhsArray();

		double[][] squareMatrix = squareMatrix();

		double[][] batistaKarawiaMatrix = new double[rhsArray.length][rhsArray.length];

    	for (int i = 0; i < squareMatrix.length; ++i) {
        	for (int j = 0; j < squareMatrix.length; ++j) {
    			batistaKarawiaMatrix[i][j] = j >= i - 1 && j <= i + 1 ? squareMatrix[i][j] : 0.;
        	}
    	}

    	int lastIndex = rhsArray.length - 1;
    	batistaKarawiaMatrix[0][0] = squareMatrix[0][0] - _gamma;
    	batistaKarawiaMatrix[lastIndex][lastIndex] = squareMatrix[lastIndex][lastIndex] - (
			squareMatrix[lastIndex][0] * squareMatrix[0][lastIndex] / _gamma
		);
    	return batistaKarawiaMatrix;
	}

	/**
	 * Construct a Batista-Karawia U RHS Array
	 * 
	 * @return Batista-Karawia U RHS Array
	 */

	public double[] uRHSArray()
	{
		double[] rhsArray = rhsArray();

		double[][] squareMatrix = squareMatrix();

		double[] uRHSArray = new double[rhsArray.length];

    	for (int i = 0; i < rhsArray.length; ++i) {
    		uRHSArray[i] = 0.;
    	}

    	uRHSArray[0] = _gamma;
    	int lastIndex = rhsArray.length - 1;
    	uRHSArray[lastIndex] = squareMatrix[lastIndex][0];
    	return uRHSArray;
	}

	/**
	 * Compute the Q Solution Array
	 * 
	 * @return Q Solution Array
	 */

	public double[] qSolutionArray()
	{
		double[][] batistaKarawiaMatrix = batistaKarawiaMatrix();

		if (null == batistaKarawiaMatrix) {
			return null;
		}

		try {
			return new StrictlyTridiagonalSolver (batistaKarawiaMatrix, uRHSArray()).forwardSweepBackSubstitution();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Y Solution Array
	 * 
	 * @return Y Solution Array
	 */

	public double[] ySolutionArray()
	{
		double[][] batistaKarawiaMatrix = batistaKarawiaMatrix();

		if (null == batistaKarawiaMatrix) {
			return null;
		}

		try {
			return new StrictlyTridiagonalSolver (batistaKarawiaMatrix, rhsArray()).forwardSweepBackSubstitution();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Batista-Karawia V Array
	 * 
	 * @return Batista-Karawia V Array
	 */

	public double[] vArray()
	{
		double[] rhsArray = rhsArray();

		double[][] squareMatrix = squareMatrix();

		double[] vArray = new double[rhsArray.length];

    	for (int i = 0; i < rhsArray.length; ++i) {
    		vArray[i] = 0.;
    	}

    	vArray[0] = 1.;
    	int lastIndex = rhsArray.length - 1;
    	vArray[lastIndex] = squareMatrix[0][lastIndex] / _gamma;
    	return vArray;
	}

	/**
	 * Compute the Solution Array based on Q/Y Scheme
	 * 
	 * @return Solution Array
	 */

	public double[] qySolver()
	{
		double[] vArray = vArray();

		double[] rhsArray = rhsArray();

		double[] qSolutionArray = null;
		double[] ySolutionArray = null;
		double vqDotProductScaler = Double.NaN;

		double[][] batistaKarawiaMatrix = batistaKarawiaMatrix();

		try {
			qSolutionArray = new StrictlyTridiagonalSolver (
				batistaKarawiaMatrix,
				uRHSArray()
			).forwardSweepBackSubstitution();

			ySolutionArray = new StrictlyTridiagonalSolver (
				batistaKarawiaMatrix,
				rhsArray
			).forwardSweepBackSubstitution();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double[] solutionArray = MatrixUtil.Product (
			MatrixUtil.CrossProduct (qSolutionArray, vArray),
			ySolutionArray
		);

		if (null == solutionArray) {
			return null;
		}

		try {
			vqDotProductScaler = 1. / (1. + MatrixUtil.DotProduct (vArray, qSolutionArray));
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < rhsArray.length; ++i) {
			solutionArray[i] = ySolutionArray[i] - solutionArray[i] * vqDotProductScaler;
		}

		return solutionArray;
	}

	/**
	 * Solve the Periodic Tridiagonal System given the RHS
	 * 
	 * @return The Solution
	 */

	public double[] solve()
	{
		return qySolver();
	}
}
