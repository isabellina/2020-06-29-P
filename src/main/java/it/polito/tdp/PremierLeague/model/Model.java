package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	
	private PremierLeagueDAO dao ;
	private Map<Integer,String> mesi = new TreeMap<Integer,String>();
	private Graph<Match,DefaultWeightedEdge> grafo;
	private int mese;
	private Map<Integer,Match> allMatch ;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.allMatch = new HashMap<Integer,Match>();
		
	}
	
    public Map<Integer,String> getMesi(){
    	mesi.put(1, "Gennaio");
    	mesi.put(2, "Febbraio");
    	mesi.put(3, "Marzo");
    	mesi.put(4, "Aprile");
    	mesi.put(5, "Maggio");
    	mesi.put(6, "Giugno");
    	mesi.put(7, "Luglio");
    	mesi.put(8, "Agosto");
    	mesi.put(9, "Settembre");
    	mesi.put(10, "Ottobre");
    	mesi.put(11, "Novembre");
    	mesi.put(12, "Dicembre");
    	
    	return mesi;
    }
    
    public void creaGrafo(String s) {
    	
    		for(String str : this.mesi.values()) {
    			if(str.compareTo(s)==0) {
    				mese = str.indexOf(s);
    			}
    		}
    	
    	mese = mese+1;
    	this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	this.dao.listAllMatches(allMatch);
    	if(this.dao.getMatchMese(mese).size()!=0) {
    		Graphs.addAllVertices(this.grafo, this.dao.getMatchMese(mese));
    		for(Arco a : this.dao.getArchi(allMatch)) {
    			if(this.grafo.containsVertex(a.getM1()) && this.grafo.containsVertex(a.getM2())) {
    				DefaultWeightedEdge e = this.grafo.getEdge(a.getM1(),a.getM2() );
    				if(e==null) {
    				Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), a.getPeso());
    				}
    			}
    		}
    		
    	}
    	System.out.println(this.grafo);
    	
    	
    }
    
    public boolean esistonoMAtch() {
    	if(this.dao.getMatchMese(mese).size()==0) {
    		return false;
    	}
    	return true;
    }
    
    
    public List<Arco> getMax(){
    	List<Arco> lMax = new LinkedList<Arco>();
    	int max = -1;
    	for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
    		if(this.grafo.getEdgeWeight(d)>max) {
    			max =(int) this.grafo.getEdgeWeight(d) ;
    			Match a = this.grafo.getEdgeSource(d);
    			Match b = this.grafo.getEdgeTarget(d);
    			int p = (int) this.grafo.getEdgeWeight(d);
    			lMax.add(new Arco (a,b,p));
    		}
    	}
    	return lMax;
    	
    }
	
	
}
