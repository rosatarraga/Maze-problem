/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import search.Action;
import search.Node;
import search.SearchAlgorithm;
import search.State;

/**
 *
 * @author Rosa Tárraga
 */
public class BestFirst extends SearchAlgorithm {

    public BestFirst() {

        actionSequence = new ArrayList<Action>();
    }

    @Override
    public void search() {
        Queue<State> closed = new LinkedList<State>();
        ArrayList<Node> opened = new ArrayList<Node>();
        ArrayList<Node> successors = new ArrayList<Node>();
        Node actual;
        int i = 0;
        boolean goal = false;
        System.out.println("Initial node: " + problem.initialState());
        System.out.println("Final node " + problem.goalState());

        opened.add(new Node(problem.initialState()));

        do {

            actual = opened.get(0);  //insert the head of the queue into actual
            actual = opened.remove(0);

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

                        if (opened.isEmpty()) {
                            opened.add(successors.get(0));
                            successors.remove(0);
                        } else if (successors.get(0).getHeuristic() > opened.get(opened.size() - 1).getHeuristic()) {
                            opened.add(opened.size(), successors.get(0));
                            successors.remove(0);
                        } else if (successors.get(0).getHeuristic() <= opened.get(i).getHeuristic()) {

                            opened.add(i, successors.get(0));
                            successors.remove(0);
                        } else {
                            i++;
                        } 
                    }
                    // Usamos el mÃ©todo "remove" para eliminarlo de la lista a la vez que lo retornamos, asÃ­ no tenemos que hacerlo manualmente despuÃ©s
//                    opened.add(successors.remove(0));
                }

                // Add actual state to the explored or closed set
                closed.add(actual.getState());
            }

        } while (opened.size() > 0);

        if (goal) {
            //the cost is getting acumulated every time we pass through a node
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
