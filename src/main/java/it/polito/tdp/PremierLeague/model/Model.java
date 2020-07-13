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
    	this.mesi.put(1, "Gennaio");
    	this.mesi.put(2, "Febbraio");
    	this.mesi.put(3, "Marzo");
    	this.mesi.put(4, "Aprile");
    	this.mesi.put(5, "Maggio");
    	this.mesi.put(6, "Giugno");
    	this.mesi.put(7, "Luglio");
    	this.mesi.put(8, "Agosto");
    	this.mesi.put(9, "Settembre");
    	this.mesi.put(10, "Ottobre");
    	this.mesi.put(11, "Novembre");
    	this.mesi.put(12, "Dicembre");
    	
    	//System.out.println(this.mesi.size() + "ciao " );
    	
    	return mesi;
    }
    
    public List<String> getListMonth(){
    	List<String> ltemp = new LinkedList<String>(this.mesi.values());
    	
    	return ltemp;
    }
    
    public void creaGrafo() {
    	
    		
    	
    //	System.out.println("Questo è il mese che ho selezionato "+ mese) ;
    	this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	this.dao.listAllMatches(allMatch);
    	if(this.dao.getMatchMese(mese).size()!=0) {
    		Graphs.addAllVertices(this.grafo, this.dao.getMatchMese(mese));
    		for(Arco a : this.dao.getArchi(allMatch)) {
    			if(this.grafo.containsVertex(a.getM1()) && this.grafo.containsVertex(a.getM2())) {
    				System.out.println("a "+  a.getM1() +" e "+  a.getM2());
    				if(a.getM1()!=a.getM2()) {
    				DefaultWeightedEdge e = this.grafo.getEdge(a.getM1(),a.getM2() );
    				if(e==null) {
    				Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), a.getPeso());
    					}
    				}
    			}
    		}
    		
    	}
    	//System.out.println(this.grafo);
    	
    	
    }
    
    
    public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
    
    public boolean esistonoMAtch(String s) {
    	for(int i : this.mesi.keySet()) {
    		if(this.mesi.get(i).compareTo(s)==0) {
    			mese = i;
    		}
    	}
    	System.out.println(mese);
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
    			
    			
    		}
    	}
    	
    	for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
    		System.out.println("ciao " +d.toString());
    		if(this.grafo.getEdgeWeight(d)==max) {
    			Match m1 = this.grafo.getEdgeSource(d);
    		    Match m2 = this.grafo.getEdgeTarget(d);
    		    lMax.add(new Arco(m1,m2,(int) this.grafo.getEdgeWeight(d)));
    		    
    		   
    		}
    	}
    	//System.out.println(max);
    	
    	
    	
        for(Arco a : lMax) {
    	System.out.println("C" + a.toString()+ "\n");
        }
    	return lMax; 
    	
    	
    	
    }
	
	
}
