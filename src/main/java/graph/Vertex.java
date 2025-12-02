package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex <E, T extends Comparable<T>> {

    private E key;
    private T value;

    private List<Edge<E,T>> outgoingEdges;

    private List<Edge<E,T>> incomingEdges;

    public Vertex(E key){
        this(key,null);
    }

    public Vertex(E key, T value) {
        this.value = value;
        this.key = key;
        outgoingEdges = new ArrayList<>();
        incomingEdges = new ArrayList<>();
    }

    public T getValue() { return value; }
    public E getKey(){ return key; }
    public void setValue(T value) { this.value = value; }

    public List<Edge<E, T>> getOutgoingEdges() {
        return outgoingEdges;
    }

    public List<Edge<E, T>> getIncomingEdges() {
        return incomingEdges;
    }


    public void clearOutgoingEdges() {
        outgoingEdges.clear();
        for (Edge<E,T> e : new ArrayList<>(outgoingEdges)) {
            Vertex<E,T> to = e.getTo();
            to.getIncomingEdges().remove(e);
        }
    }

    public void clearIncomingEdges() {
        incomingEdges.clear();
        for (Edge<E,T> e : new ArrayList<>(incomingEdges)) {
            Vertex<E,T> from = e.getFrom();
            from.getOutgoingEdges().remove(e);
        }
    }
    
    public void clear(){
        value = null;
        clearOutgoingEdges();
        clearIncomingEdges();
    }

}
