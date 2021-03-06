/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import search.Action;
import search.Node;
import search.SearchAlgorithm;
import search.State;

/**
 *
 * @author Rosa Tárraga
 */
public class AStar extends SearchAlgorithm {

    public AStar() {

        actionSequence = new ArrayList<Action>();
    }

    @Override
    public void search() {
        Queue<State> closed = new LinkedList<State>();
        PriorityQueue<Node> opened = new PriorityQueue<Node>(Node.BY_EVALUATION); //priority queue ordered by evaluation
        ArrayList<Node> successors = new ArrayList<Node>();
        Node actual;
        int i = 0;
        boolean goal = false;
        System.out.println("Initial node: " + problem.initialState());
        System.out.println("Final node " + problem.goalState());

        opened.add(new Node(problem.initialState()));

        do { //while frontier!=0

            actual = opened.peek(); //insert the head of the queue into actual
            actual = opened.poll();

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
                          //we use the method remove to delete from the list of successors and add to the list of opened nodes
                        opened.add(successors.remove(0));
                    }

                    // Add actual state to the explored or closed set
                    closed.add(actual.getState());
                }
            }

        } while (opened.size() > 0);

        if (goal) {

            totalCost = actual.getCost();

            // Gets the path
            while (!(actual.getState().equals(problem.initialState()))) {

                actionSequence.add(actual.getAction());
                actual = actual.getParent();
            }

            Collections.reverse(actionSequence);
            System.out.println(actionSequence);
        }
    }

}
