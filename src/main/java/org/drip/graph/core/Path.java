
package org.drip.graph.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>Path</i> contains a contiguous Series of Edges representing a Path from a Source to a Destination. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dijkstra, E. W. (1959): A Note on Two Problems in Connection with Graphs <i>Numerische
 *  			Mathematik</i> <b>1</b> 269-271
 *  	</li>
 *  	<li>
 *  		Felner, A. (2011): Position Paper: Dijkstra�s Algorithm versus Uniform Cost Search or a Case
 *  			against Dijkstra�s Algorithm <i>Proceedings of the 4<sup>th</sup> International Symposium on
 *  			Combinatorial Search</i> 47-51
 *  	</li>
 *  	<li>
 *  		Mehlhorn, K. W., and P. Sanders (2008): <i>Algorithms and Data Structures: The Basic Toolbox</i>
 *  			<b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Russell, S., and P. Norvig (2009): <i>Artificial Intelligence: A Modern Approach 3<sup>rd</sup>
 *  			Edition</i> <b>Prentice Hall</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/shortestpath/README.md">Shortest Path Generation Algorithm Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Path
{
	private java.lang.String _sourceVertexName = "";
	private java.lang.String _destinationVertexName = "";
	private java.util.List<org.drip.graph.core.Edge> _edgeList = null;

	/**
	 * Path Constructor
	 * 
	 * @param edgeList Contiguous List of Edges
	 * 
	 * @throws java.lang.Exception Thrown if the Input is not Valid
	 */

	public Path (
		final java.util.List<org.drip.graph.core.Edge> edgeList)
		throws java.lang.Exception
	{
		if (null == (_edgeList = edgeList))
		{
			throw new java.lang.Exception (
				"Path Constructor => Invalid Inputs"
			);
		}

		org.drip.graph.core.Edge previousEdge = null;

		for (org.drip.graph.core.Edge edge : edgeList)
		{
			if (null == edge)
			{
				throw new java.lang.Exception (
					"Path Constructor => Invalid Inputs"
				);
			}

			java.lang.String edgeSourceVertexName = edge.sourceVertexName();

			if (null != previousEdge &&
				!previousEdge.destinationVertexName().equalsIgnoreCase (
					edgeSourceVertexName
				)
			)
			{
				throw new java.lang.Exception (
					"Path Constructor => Invalid Inputs"
				);
			}

			if (_sourceVertexName.isEmpty())
			{
				_sourceVertexName = edgeSourceVertexName;
			}

			_destinationVertexName = edge.destinationVertexName();

			previousEdge = edge;
		}
	}

	/**
	 * Retrieve the Source Vertex Name
	 * 
	 * @return The Source Vertex Name
	 */

	public java.lang.String sourceVertexName()
	{
		return _sourceVertexName;
	}

	/**
	 * Retrieve the Destination Vertex Name
	 * 
	 * @return The Destination Vertex Name
	 */

	public java.lang.String destinationVertexName()
	{
		return _destinationVertexName;
	}

	/**
	 * Retrieve the Contiguous List of Edges
	 * 
	 * @return The Contiguous List of Edges
	 */

	public java.util.List<org.drip.graph.core.Edge> edgeList()
	{
		return _edgeList;
	}

	/**
	 * Generate the List of Path Vertex Names
	 * 
	 * @return List of Path Vertex Names
	 */

	public java.util.List<java.lang.String> vertexList()
	{
		java.util.List<java.lang.String> vertexList = new java.util.ArrayList<java.lang.String>();

		vertexList.add (
			_sourceVertexName
		);

		for (org.drip.graph.core.Edge edge : _edgeList)
		{
			vertexList.add (
				edge.destinationVertexName()
			);
		}

		return vertexList;
	}

	/**
	 * Retrieve the Total Length of the Path
	 * 
	 * @return Total Length of the Path
	 */

	public double totalLength()
	{
		double totalLength = 0.;

		for (org.drip.graph.core.Edge edge : _edgeList)
		{
			totalLength = totalLength + edge.weight();
		}

		return totalLength;
	}
}
