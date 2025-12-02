package graph;

import java.util.ArrayList;
import java.util.List;

public class SpreadsheetGraph extends HashGraph<String, String>{

    public SpreadsheetGraph(List<String> keys) {
        super(keys);
        for(int i = 0; i < keys.size(); i++)
            getAllVertices().get(i).setValue("");
    }
    public SpreadsheetGraph(List<String> keys, List<String> values) {
        super(keys, values);
    }

    public String getCellContent (String key){
        return getVertex(key).getValue();
    }

    public void setCellContent (String key, String content){
        getVertex(key).setValue(content);
    }
    
    public void connectTo(String from,String to,String operation){
        Vertex<String,String> v1 = getVertex(from);
        Vertex<String,String> v2 = getVertex(to);
        if(v1 == null || v2 == null){
            throw new NullPointerException("Ingrese celdas validas");
        }
        Edge<String,String> e = new Edge(v1,v2,operation);
        v1.getOutgoingEdges().add(e);
        v2.getIncomingEdges().add(e);
    }
    
    private void connectBlock(String startKey, String endKey, String to, String operation) {
        List<String> keys = getBlockVertices(startKey, endKey);
        for (String k : keys) {
            connectTo(k, to, operation);
        }
    }

    public void connectLinked(String from, String to){
        connectTo(from, to, "linked");
    }

    public void connectSum(String startKey, String endKey, String to){
        connectBlock(startKey, endKey, to, "sum");
    }

    public void connectAvg(String startKey, String endKey, String to){
        connectBlock(startKey, endKey, to, "avg");
    }

    public void connectMax(String startKey, String endKey, String to){
        connectBlock(startKey, endKey, to, "max");
    }

    public void connectMin(String startKey, String endKey, String to){
        connectBlock(startKey, endKey, to, "min");
    }
    
    
    public List<String>  getBlockVertices(String stratKey,String endkey){
        int row1 = Integer.parseInt(stratKey.substring(1));
        int row2 = Integer.parseInt(endkey.substring(1));
        
        int column1 = stratKey.charAt(0) - 'A' + 1;
        int column2 = endkey.charAt(0) - 'A' + 1;
        
        List<String> blockKeys = new ArrayList<>();
        
        for(int i = row1; i <= row2 ; i++){
            for(int j = column1; j<= column2 ; j++){
                char colChar = (char) ('A' + j - 1);
                String key= colChar + String.valueOf(i);
                blockKeys.add(key);
            }
        }
        return blockKeys;
    }
    
    
    
    public void evaluateCell(String v){
        Vertex<String,String> v1 = getVertex(v);
        List<Edge<String,String>> edgesIn = v1.getIncomingEdges();
        
    if (!edgesIn.isEmpty()) {
        String operation = edgesIn.get(0).getOperation();

        switch(operation){
            case "linked" -> linked(edgesIn.get(0));
            case "sum"    -> sum(edgesIn);
            case "avg"    -> avg(edgesIn);
            case "max"    -> max(edgesIn);
            case "min"    -> min(edgesIn);
        }
    }
        
        List<Edge<String,String>> edgesOut = v1.getOutgoingEdges();
        for(Edge<String,String> e : edgesOut){
            evaluateCell(e.getTo().getKey());
        }
    }
    
    protected void linked(Edge<String,String> edges){
        edges.getTo().setValue(edges.getFrom().getValue());
    }
    
    protected void sum(List<Edge<String,String>> edges){
        int sum = 0;
        int count = 0;

        for (Edge<String, String> e : edges) {
            Integer v = safeParse(e.getFrom().getValue());
            if (v != null) {
                sum += v;
                count++;
            }
        }
        if(count == 0){
            edges.get(0).getTo().setValue("");
        }else{
            edges.get(0).getTo().setValue(String.valueOf(sum));
        }
    }
    
     
    protected void avg(List<Edge<String,String>> edges){
        int sum = 0;
        int count = 0;

        for (Edge<String, String> e : edges) {
            Integer v = safeParse(e.getFrom().getValue());
            if (v != null) {
                sum += v;
                count++;
            }
        }

        if (count == 0) {
            edges.get(0).getTo().setValue("");
        } else {
            double avg = (double) sum / count;
            edges.get(0).getTo().setValue(String.valueOf(avg));
        }
    }
    
    
    protected void max(List<Edge<String,String>> edges){
        Integer currentMax = null;

        for (Edge<String, String> e : edges) {
            Integer v = safeParse(e.getFrom().getValue());
            if (v != null) {
                if (currentMax == null || v > currentMax) {
                    currentMax = v;
                }
            }
        }

        if (currentMax == null) {
            edges.get(0).getTo().setValue("");
        } else {
            edges.get(0).getTo().setValue(String.valueOf(currentMax));
        }
    }
    
    
    protected void min(List<Edge<String,String>> edges){
        Integer currentMin = null;

        for (Edge<String, String> e : edges) {
            Integer v = safeParse(e.getFrom().getValue());
            if (v != null) {
                if (currentMin == null || v < currentMin) {
                    currentMin = v;
                }
            }
        }

        if (currentMin == null) {
            edges.get(0).getTo().setValue("");
        } else {
            edges.get(0).getTo().setValue(String.valueOf(currentMin));
        }
    }
    
    
    
    private Integer safeParse(String value) {
        if (value == null || value.isBlank()) {
            return null; 
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Celda no numérica: \"" + value + "\"");
        }
    }



}
