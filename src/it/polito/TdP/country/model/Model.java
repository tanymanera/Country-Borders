package it.polito.TdP.country.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.TdP.DAO.CountryDAO;

public class Model {
	private Graph<Country, DefaultEdge> grafo;
	private List<Country> countries;
	
	public Model(){
		
	}
	
	public void creaGrafo1() {
		this.grafo = 
				new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		CountryDAO dao = new CountryDAO();
		//crea i vertici del grafo (che sono le nazioni)
		Graphs.addAllVertices(grafo, dao.listCountry());
		
		//crea gli archi del grafo
		//per ogni coppia di vertici si controlla se 
		//esiste il corrispondente arco e in caso positivo
		//si crea l'arco: n^2 interrogazioni al db
		for(Country c1: grafo.vertexSet()) {
			for(Country c2: grafo.vertexSet()) {
				if(dao.confinanti(c1, c2)) {
					grafo.addEdge(c1, c2);
				}
			}
		}
		
	}
	
	public void creaGrafo2() {
		this.grafo = 
				new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		CountryDAO dao = new CountryDAO();
		//crea i vertici del grafo (che sono le nazioni)
		Graphs.addAllVertices(grafo, dao.listCountry());
		
		//crea gli archi del grafo
		//per ogni vertice del grafo, vengono restituiti dal
		//db tutti i vertici confinanti, e vengono creati i 
		//rispettivi archi: n interrogazioni a db
		for(Country c: grafo.vertexSet()) {
			List<Country> confinanti = dao.listAdiacenti(c);
			for(Country c2: confinanti) {
				grafo.addEdge(c, c2);
			}
		}
	}

		public void creaGrafo3() {
			this.grafo = 
					new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
			
			CountryDAO dao = new CountryDAO();
			//crea i vertici del grafo (che sono le nazioni)
			Graphs.addAllVertices(grafo, dao.listCountry());
			
			//crea gli archi del grafo
			//1 sola interrogazione al db
			for(CountryPair cp: dao.listCoppieCountryConfinanti()) {
				grafo.addEdge(cp.getC1(), cp.getC2());
			}
		}
	
	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo1();
		System.out.format("Numero di vertici: %d, numero di archi: %d.",
				model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size());
	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}
}
