/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph;

/**
 *
 * @author dhemi
 */
public class Edge<E,T extends Comparable<T>> {
    private String operation;
    private Vertex<E,T> from;
    private Vertex<E,T> to;
    
    public Edge(Vertex<E,T> from, Vertex<E,T> to,String operation){
        this.from = from;
        this.to = to;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public Vertex<E, T> getFrom() {
        return from;
    }

    public Vertex<E, T> getTo() {
        return to;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setFrom(Vertex<E, T> from) {
        this.from = from;
    }

    public void setTo(Vertex<E, T> to) {
        this.to = to;
    }
    
    
}
