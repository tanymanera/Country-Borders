package it.polito.TdP.country.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class CountryTraversalListener implements TraversalListener<Country, DefaultEdge> {
	private Graph<Country, DefaultEdge> graph;
	private Map<Country, Country> map;

	public CountryTraversalListener(Graph<Country, DefaultEdge> g, Map<Country, Country> albero) {
		super();
		this.graph = g;
		this.map = albero;
		
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> evento) {
		/* evento getEdge() è l'arco appena attraversato
		 * arco: graph.edgeSource/Dest( evento.getEdge());
		 */
		Country c1 = graph.getEdgeSource(evento.getEdge());
		Country c2 = graph.getEdgeTarget(evento.getEdge());
		if(map.containsKey(c1) && map.containsKey(c2)) {
			return;
		}
		//la struttura della mappa è vertice -> vertice padre
		if(map.containsKey(c1)) {
			//c2 è il vertice nuovo
			map.put(c2, c1);
		} else {
			//c1 è il vertice nuovo
			map.put(c1, c2);
		}
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

}
