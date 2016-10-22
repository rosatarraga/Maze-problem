package search.algorithms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import static java.lang.Thread.sleep;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import search.Action;
import search.Node;
import search.SearchAlgorithm;
import search.State;
import problems.maze.MazeProblem;

/**
 *
 * @author Rosa Tarraga
 */
public class DepthFirst extends SearchAlgorithm {

    public DepthFirst() {

        actionSequence = new ArrayList<Action>();
    }

    @Override
    public void search() {

        Queue<State> closed = new LinkedList<State>();
        Stack<Node> opened = new Stack<Node>();
        ArrayList<Node> successors = new ArrayList<Node>();
        Node actual;
        boolean goal = false;
        System.out.println("Initial node: " + problem.initialState());
        System.out.println("Final node " + problem.goalState());

        opened.add(new Node(problem.initialState())); //as frontier<-new fifo-queue...initial state

        do { //while frontier!=0

            actual = opened.peek(); //insert the head of the queue into actual
            actual = opened.pop();

            // Checks if it has been already explored
            if (!closed.contains(actual.getState())) {

                // Found the goal state
                if (problem.testGoal(actual.getState())) {

                    goal = true;
                    break; //it means that we have reached the goal
                } // We must explore the node
                else {

                    // Obtains the successors
                    successors = getSuccessors(actual);

                    // Add all the succesors into the frontier or set of open nodes
                    while (!successors.isEmpty()) {

                        // Usamos el mÃ©todo "remove" para eliminarlo de la lista a la vez que lo retornamos, asÃ­ no tenemos que hacerlo manualmente despuÃ©s
                        opened.add(successors.remove(0));
                    }

                    // Add actual state to the explored or closed set
                    closed.add(actual.getState());
                }
            }

        } while (opened.size() > 0);

        //  while (nodes.peek().getState() != problem.initialState()) {
        //resultado.add(nodos.peek().getAction());
        // Obtenemos la soluciÃ³n
        if (goal) {

            // El coste se va acumulando cada vez que pasamos por un nodo, puedes obtenerlo directamente
            totalCost = actual.getCost();

            // Gets the path
            while (!(actual.getState().equals(problem.initialState()))) {

                actionSequence.add(actual.getAction());
                actual = actual.getParent();
            }

            Collections.reverse(actionSequence);
        }

    }
}
