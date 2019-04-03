import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Test {
    static double total=0;

    public static void main(String[] args) {
        Graph<Integer, String> g2 = new DirectedSparseGraph<Integer, String>();

        // Phase 1
        int numbOfVertices = 4;

//        // Create Graph Vertices
//        for (int i = 0; i < numbOfVertices; i++) {
//            g2.addVertex((Integer)i);
//        }
//        // Crete Graph Edges
//        for (int i = 0; i < numbOfVertices; i++) {
//            if (new Random().nextBoolean()) {
//                int randomEdge = new Random().nextInt(((numbOfVertices - 1) - 0) + 1) + 0;
//                while (randomEdge == i) {
//                    randomEdge = new Random().nextInt(((numbOfVertices - 1) - 0) + 1) + 0;
//                }
//                g2.addEdge("Edge-" + i , i, randomEdge);
//            }
//        }

        g2.addVertex(0);
        g2.addVertex(1);
        g2.addVertex(2);
        g2.addVertex(3);

        g2.addEdge("Edge-1", 0, 1);
        g2.addEdge("Edge-2", 0, 2);
        g2.addEdge("Edge-3", 0, 3);

        g2.addEdge("Edge-4", 1, 0);
        g2.addEdge("Edge-5", 2, 0);
        g2.addEdge("Edge-6", 3, 0);

        System.out.println("Phase #1:\nThe graph g2 = " + g2.toString() + "\n--------------------------------------");


        //Phase 2
        System.out.println("Phase 2:");
        generation((numbOfVertices * (numbOfVertices - 1)) / 2, numbOfVertices, g2.getEdgeCount());


        // Phase 3
        System.out.println("Phase 3:");
        double d = 0.85;
        double newSurfers = Math.round((1 - d) / numbOfVertices * 1000.0) / 1000.0;

        double weights[] = new double[numbOfVertices];
        Arrays.fill(weights, 0.25);

        boolean flag = true;
        double newWeight = 0;

        for (int j = 1; j <= 100; j++) {
            for (int i = 0; i < numbOfVertices && flag; i++) {
                Collection successors = g2.getSuccessors(i);
                for (Iterator<Integer> iterator = successors.iterator(); iterator.hasNext();) {
                    int number = iterator.next();
                    newWeight += ((d * weights[number])/g2.getInEdges(number).size());
                }
                newWeight += newSurfers;
                if (newWeight != weights[i] && Math.abs(newWeight - weights[i]) < 0.0001) {
                    flag = false;
                    System.out.println("Exited at iteration #" + j);
                    break;
                }
                weights[i] = newWeight;
                newWeight = 0;
            }
        }
//        Arrays.sort(weights);
        double highestRank = weights[0];
        int indexOfHighestNodeRank = 0;
        for (int i = 1; i < weights.length; i++) {
            if (weights[i] > highestRank) {
                highestRank = weights[i];
                indexOfHighestNodeRank = i;
            }
        }
        System.out.println("Highest Node Rank is Node #" + indexOfHighestNodeRank + " with rank: " + (Math.round(highestRank * 1000.0) / 1000.0));
        System.out.println("Node Ranks:");
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Node #" + i + " rank: " + Math.round(weights[i] * 1000.0) / 1000.0);
        }




        // Draw the Graph
        Layout<Integer, String> layout = new CircleLayout(g2);
        layout.setSize(new Dimension(750,750)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer,String> vv =
                new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(800,800)); //Sets the viewing area size
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JFrame frame = new JFrame("Graph Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    static void generation(int p, int n , int e) {
        int E = p; // to start from total connected graph
        while(E>=0){ // calculate maximum number of simple graph with e
            if (E != e){ // to ignore the same # of edges within graph
                double pCe = (factorial(p)) / ((factorial(p - E)) * (factorial(E)));
                total += pCe;
            }
            E--; // to go down to calculate # of graph with E edges
        }
        System.out.println("Total Number of Non-isomorphic Graphs = " + total + "\n--------------------------------------");
    }
     static double factorial(int n) {
        double fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
