import com.google.common.collect.Iterables;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

// Mirror to array list

// Phase 1:
//  1- Create random number of vertices
//  2- Create random number of edges

// Phase 3:
//  Weight
//

public class Test {
    public static void main(String[] args) {
        Graph<Integer, String> g2 = new DirectedSparseGraph<Integer, String>();



        int numbOfVertices = 4;

//        g2.addVertex(0);
//        g2.addVertex(1);
//        g2.addVertex(2);
//        g2.addVertex(3);
//
//        g2.addEdge("Edge-1", 0, 1);
//        g2.addEdge("Edge-2", 0, 2);
//        g2.addEdge("Edge-3", 0, 3);
//
//        g2.addEdge("Edge-4", 1, 0);
//        g2.addEdge("Edge-5", 2, 0);
//        g2.addEdge("Edge-6", 3, 0);

        for (int i = 0; i < numbOfVertices; i++) {
            g2.addVertex((Integer)i);
        }

        for (int i = 0; i < numbOfVertices; i++) {
            if (new Random().nextBoolean()) {
                int randomEdge = new Random().nextInt(((numbOfVertices - 1) - 0) + 1) + 0;
                while (randomEdge == i) {
                    randomEdge = new Random().nextInt(((numbOfVertices - 1) - 0) + 1) + 0;
                }
                g2.addEdge("Edge-" + i , i, randomEdge);
            }
        }

//        System.out.println("The graph g2 = " + g2.toString());


        Layout<Integer, String> layout = new CircleLayout(g2);
        layout.setSize(new Dimension(300,300)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer,String> vv =
                new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

        double d = 0.85;
        double newSurfers = (1 - d) / numbOfVertices;

        double weights[] = new double[numbOfVertices];
        Arrays.fill(weights, 0.25);


        double newWeight = 0;
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < numbOfVertices; i++) {
                Collection successors = g2.getSuccessors(i);
                for (Iterator<Integer> iterator = successors.iterator(); iterator.hasNext();) {
                    int number = iterator.next();
                    newWeight += ((d * weights[number])/g2.getInEdges(number).size());
                }
                newWeight += newSurfers;
                if (newWeight - weights[i] < 0.9) {
                    break;
                }
                weights[i] = newWeight;
                newWeight = 0;
            }
        }
        System.out.println(Arrays.toString(weights));
    }
}