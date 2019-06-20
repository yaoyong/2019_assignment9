package jobinterviewquestions;

import algs4.*;
import com.sun.corba.se.impl.activation.ServerTableEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MSTs {
    /*
    Question 1
    Bottleneck minimum spanning tree.
    KrushkalMST 
    */
    
 
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<Edge> allEdges = new ArrayList<>();

        Graph(int vertices) {
            this.vertices = vertices;
        }

        public void addEgde(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            allEdges.add(edge); //add to total edges
        }
        
        public void kruskalMST(){
            PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.weight));

            //add all the edges to priority queue, //sort the edges on weights
            for (int i = 0; i <allEdges.size() ; i++) {
                pq.add(allEdges.get(i));
            }

            //create a parent []
            int [] parent = new int[vertices];

            //makeset
            makeSet(parent);

            ArrayList<Edge> mst = new ArrayList<>();

            //process vertices - 1 edges
            int index = 0;
            while(index<vertices-1){
                Edge edge = pq.remove();
                //check if adding this edge creates a cycle
                int x_set = find(parent, edge.source);
                int y_set = find(parent, edge.destination);

                if(x_set==y_set){
                    //ignore, will create cycle
                }else {
                    //add it to our final result
                    mst.add(edge);
                    index++;
                    union(parent,x_set,y_set);
                }
            }
            //print MST
            System.out.println("Minimum Spanning Tree: ");
            printGraph(mst);
        }

        public void makeSet(int [] parent){
            //Make set- creating a new element with a parent pointer to itself.
            for (int i = 0; i <vertices ; i++) {
                parent[i] = i;
            }
        }

        public int find(int [] parent, int vertex){
            //chain of parent pointers from x upwards through the tree
            // until an element is reached whose parent is itself
            if(parent[vertex]!=vertex)
                return find(parent, parent[vertex]);;
            return vertex;
        }

        public void union(int [] parent, int x, int y){
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);
            //make x as parent of y
            parent[y_set_parent] = x_set_parent;
        }

        public void printGraph(ArrayList<Edge> edgeList){
            for (int i = 0; i <edgeList.size() ; i++) {
                Edge edge = edgeList.get(i);
                System.out.println("Edge-" + i + " source: " + edge.source +
                        " destination: " + edge.destination +
                        " weight: " + edge.weight);
            }
        }
    }
    public static void main(String[] args) {
            int vertices = 6;
            Graph graph = new Graph(vertices);
            graph.addEgde(0, 1, 4);
            graph.addEgde(0, 2, 3);
            graph.addEgde(1, 2, 1);
            graph.addEgde(1, 3, 2);
            graph.addEgde(2, 3, 4);
            graph.addEgde(3, 4, 2);
            graph.addEgde(4, 5, 6);
            graph.kruskalMST();
    }
 
    
    /*
    Question 2
    Is an edge in a MST.
    */

    public boolean edgeInMST(EdgeWeightedGraph G, Edge e) {
        SET<Integer> vertices = new SET<Integer>();
        double weight = e.weight();
        for (Edge edge: G.edges()) {
            if (edge.weight() < weight) {
                int v = edge.either();
                int w = edge.other(v);
                vertices.add(v); vertices.add(w);
            }
        }
        int v = e.either();
        int w = e.other(v);
        if (vertices.contains(v) && vertices.contains(w)) return false;
        return true;
    }

    /*
    Question 3
    Minimum-weight feedback edge set.
     */

    public Queue<Edge> MFES(EdgeWeightedGraph G) {
        MaxPQ<Edge> pq = new MaxPQ<Edge>();
        Queue<Edge> mfes = new Queue<Edge>();
        int size = 0;

        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(G.V());
        while (!pq.isEmpty()) {
            Edge e = pq.delMax();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
            }
            else {
                mfes.enqueue(e);
            }
        }
        return mfes;

    }


}
