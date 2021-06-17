package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;


public class Model {
	
	SimpleWeightedGraph<Portion,DefaultWeightedEdge> grafo;
	FoodDao dao;
	Map<String,Portion> idMap;
	List<Arco> archi = new ArrayList<Arco>();
	
	public Model() {
	dao = new FoodDao();
	idMap= new HashMap<>();
	dao.listAllPortions(idMap);
	}
	
	public void creaGrafo(Double calories) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// creare i vertici del grafo 
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, calories));
		
		// creare gli archi del grafo 
		for(Arco a: dao.getArchi(idMap,calories)) {
			if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
				if(a.getPeso()>0) {
			  Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(),a.getPeso());		
				archi.add(a);
				}
			}
		}
	}
	
	public int getVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Portion> listaPorzioni(){
		List<Portion> porzioni = new ArrayList<Portion>();
		
		for(Portion p: this.grafo.vertexSet()) {
			porzioni.add(p);
		}
		
		return porzioni;
	}
	
	public Graph<Portion,DefaultWeightedEdge> getGrafo(){
		return grafo;
	}
	
	public List<Portion> getPorzioniCollegate(Portion p){
		ConnectivityInspector<Portion, DefaultWeightedEdge> ci = new ConnectivityInspector<Portion, DefaultWeightedEdge>(grafo);
		
		List<Portion> porzioniCollegate= new ArrayList<Portion>(ci.connectedSetOf(p));
	    porzioniCollegate.remove(p);
	    
	    for(Portion p1: porzioniCollegate) {
	    for(Arco a: archi) {
	    	if((a.getP1().equals(p) && a.getP2().equals(p1)) || (a.getP2().equals(p) && a.getP1().equals(p1))){
	    		p1.setPeso(a.getPeso());
	    	}
	    }
	    }
	    return porzioniCollegate;

	}
	
	
	
}
