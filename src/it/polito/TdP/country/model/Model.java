package it.polito.TdP.country.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.TdP.DAO.CountryDAO;

public class Model {
	private Graph<Country, DefaultEdge> grafo;
	private List<Country> countries;
	private Map<Country, Country> alberoVisita;
	
	public Model(){
		
	}
	
	public List<Country> getCountries(){
		if(this.countries == null) {
			CountryDAO dao = new CountryDAO();
			this.countries = dao.listCountry();
		}
		return this.countries;
	}
	
	public List<Country> getRaggiungibili(Country partenza){
		Graph<Country, DefaultEdge> g = this.getGrafo();
		BreadthFirstIterator<Country, DefaultEdge> bfi = 
				new BreadthFirstIterator<Country, DefaultEdge>(g, partenza);
		List<Country> list = new LinkedList<>();
		Map<Country, Country> albero = new HashMap<>();
		albero.put(partenza, null);	//root dell'albero 
		bfi.addTraversalListener(new CountryTraversalListener(g, albero));
		while(bfi.hasNext()) {
			list.add(bfi.next());
		}
		this.alberoVisita = albero;
		return list;
	}
	
	public void creaGrafo1() {
		this.grafo = 
				new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		CountryDAO dao = new CountryDAO();
		//crea i vertici del grafo (che sono le nazioni)
		Graphs.addAllVertices(grafo, this.getCountries());
		
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
		Graphs.addAllVertices(grafo, this.getCountries());
		
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
			Graphs.addAllVertices(grafo, this.getCountries());
			
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

	private Graph<Country, DefaultEdge> getGrafo() {
		if(this.grafo == null) {
			this.creaGrafo3();
		}
		return this.grafo;
	}

	public List<Country> getPercorso(Country destinazione) {
		List<Country> percorso = new LinkedList<>();

		Country c = destinazione;
		while(c != null) {
			percorso.add(c);
			c = alberoVisita.get(c);
		}
		return percorso;
	}
	
}
