
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
 * <i>Tree</i> holds the Vertexes and the Edges associated with a Tree. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bollobas, B. (1998): <i>Modern Graph Theory</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Eppstein, D. (1999): Spanning Trees and Spanners
 *  			https://www.ics.uci.edu/~eppstein/pubs/Epp-TR-96-16.pdf
 *  	</li>
 *  	<li>
 *  		Gross, J. L., and J. Yellen (2005): <i>Graph Theory and its Applications</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Kocay, W., and D. L. Kreher (2004): <i>Graphs, Algorithms, and Optimizations</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Spanning Tree https://en.wikipedia.org/wiki/Spanning_tree
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Tree
	extends org.drip.graph.core.Network
{

	/**
	 * Tree Constructor
	 */

	public Tree()
	{
		super();
	}

	/**
	 * Add a Stand-alone Vertex to the Network
	 *  
	 * @param vertexName The Stand-alone Vertex Name
	 * 
	 * @return TRUE - The Stand-alone Vertex successfully added to the Network
	 */

	public boolean addStandaloneVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty() ||
			!_vertexMap.isEmpty() ||
			!_edgeMap.isEmpty()
		)
		{
			return false;
		}

		try
		{
			_vertexMap.put (
				vertexName,
				new org.drip.graph.core.Vertex (
					vertexName
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
	 * Absorb the Specified Tree and Edge
	 * 
	 * @param tree The Tree
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Tree and Edge successfully absorbed
	 */

	public boolean absorbTreeAndEdge (
		final org.drip.graph.core.Tree tree,
		final org.drip.graph.core.Edge edge)
	{
		if (null == tree ||
			!addEdge (
				edge
			)
		)
		{
			return false;
		}

		java.util.Collection<org.drip.graph.core.Edge> treeEdgeCollection = tree.edgeMap().values();

		if (null == treeEdgeCollection || 0 == treeEdgeCollection.size())
		{
			return true;
		}

		for (org.drip.graph.core.Edge treeEdge : treeEdgeCollection)
		{
			if (!addEdge (
				treeEdge
			))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Construct and Retrieve the Edge Priority Queue from the Graph
	 * 
	 * @param graph The Graph
	 * @param minHeap TRUE - The Edge Priority Queue is in the Descending Order of Distance
	 * 
	 * @return The Tree Adjacency Map
	 */

	public org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge> edgePriorityQueue (
		final org.drip.graph.core.DirectedGraph graph,
		final boolean minHeap)
	{
		if (null == graph)
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.graph.core.Edge> graphEdgeMap = graph.edgeMap();

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex> graphVertexMap = graph.vertexMap();

		org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge> edgePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, org.drip.graph.core.Edge> (
				minHeap
			);

		for (java.lang.String vertexName : _vertexMap.keySet())
		{
			if (!graphVertexMap.containsKey (
				vertexName
			))
			{
				return null;
			}

			org.drip.graph.core.Vertex vertex = graphVertexMap.get (
				vertexName
			);

			if (vertex.isLeaf())
			{
				continue;
			}

			for (java.lang.String graphEdgeKey : vertex.edgeMap().keySet())
			{
				org.drip.graph.core.Edge graphEdge = graphEdgeMap.get (
					graphEdgeKey
				);

				java.lang.String sourceVertexName = graphEdge.sourceVertexName();

				java.lang.String destinationVertexName = graphEdge.destinationVertexName();

				if (_vertexMap.containsKey (
					sourceVertexName
				) && _vertexMap.containsKey (
					destinationVertexName
				))
				{
					continue;
				}

				edgePriorityQueue.insert (
					graphEdge.weight(),
					graphEdge
				);
			}
		}

		return edgePriorityQueue;
	}

	/**
	 * Retrieve the Maximum Bottleneck Edge of the Tree
	 * 
	 * @return The Maximum Bottleneck Edge of the Tree
	 */

	public org.drip.graph.core.Edge maximumBottleneckEdge()
	{
		java.util.Map<java.lang.String, org.drip.graph.core.Edge> edgeMap = edgeMap();

		if (null == edgeMap || 0 == edgeMap.size())
		{
			return null;
		}

		org.drip.graph.core.Edge bottleneckEdge = null;

		for (org.drip.graph.core.Edge edge : edgeMap.values())
		{
			if (null == bottleneckEdge)
			{
				bottleneckEdge = edge;
			}
			else
			{
				if (edge.weight() > bottleneckEdge.weight())
				{
					bottleneckEdge = edge;
				}
			}
		}

		return bottleneckEdge;
	}

	/**
	 * Retrieve the Minimum Bottleneck Edge of the Tree
	 * 
	 * @return The Minimum Bottleneck Edge of the Tree
	 */

	public org.drip.graph.core.Edge minimumBottleneckEdge()
	{
		java.util.Map<java.lang.String, org.drip.graph.core.Edge> edgeMap = edgeMap();

		if (null == edgeMap || 0 == edgeMap.size())
		{
			return null;
		}

		org.drip.graph.core.Edge bottleneckEdge = null;

		for (org.drip.graph.core.Edge edge : edgeMap.values())
		{
			if (null == bottleneckEdge)
			{
				bottleneckEdge = edge;
			}
			else
			{
				if (edge.weight() < bottleneckEdge.weight())
				{
					bottleneckEdge = edge;
				}
			}
		}

		return bottleneckEdge;
	}
}
