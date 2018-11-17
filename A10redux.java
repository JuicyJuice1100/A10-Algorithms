/* A10.java - Driver code for this assignment
*
*  @version CS 321 - Fall 2018 - A10
*
*  @author 1st STUDENT'S FULL NAME GOES HERE
*
*  @author 2nd STUDENT'S FULL NAME GOES HERE (DELETE THIS LINE IF NOT NEEDED)
*
*  @author 3rd STUDENT'S FULL NAME GOES HERE (DELETE THIS LINE IF NOT NEEDED)
*
*/

import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

class A10redux {

  static int size;        // number of edges on one side of the square
  static int minTurns;    // minimum required number of turns in the solution
  static int turnPenalty; // penalty for each extra turn over the minimum

  /* define your static variables below (NO instance variables allowed) */
  static Map<Integer, Node> nodeTable;
  static int sizeOfMap;



  /* load problem definition from the given file into applicable static
   * variables.
   * This method does not send anything to the console. It must catch applicable
   * exceptions and output meaningful error messages.
   */
  static void loadProblem(String fileName) {
    try{
      File file = new File(fileName);
      BufferedReader br = new BufferedReader(new FileReader(file));
      LinkedList<String> data = new LinkedList<String>(Arrays.asList(br.readLine().split("\\s+")));
      minTurns = Integer.parseInt(data.pop());
      turnPenalty = Integer.parseInt(data.pop());
      size = Integer.parseInt(data.pop());
      sizeOfMap = ((int)Math.pow(size + 1, 2));

      nodeTable = new HashMap<Integer, Node>();


      createRight(data, 0, new ArrayList<Integer>(), 1, "");
      createLeft(data, size + 2);

      br.close();
    } catch(IOException ex){
      System.out.println("Error in load problem method. Shutting down....");
    }

  }// loadProblem method

  /* solve the problem (previously defined using the loadProblem method) using
   * dynamic programming. Both the optimal value and an optimal solution must
   * be computed and stored in/retrievable from static variables, using the
   * following two methods.
   * This method does not send anything to the console.
   */
  static void solveProblem() {
    // currentMax = Integer.MIN_VALUE;
    for(int i = 1; i <= sizeOfMap; i++){
      Turn data = new Turn();
      data = solveHelper(0, i, 1, "");
      // data.points = data.points - getNumberOfTurns(data.turns)*turnPenalty;
      // currentMax = Math.max(data.points, currentMax);
      costTable.replace(i, data);
    }
  }// solveProblem method

  /* return the value of an optimal solution obtained with the solveProblem
   * method.
   * This method does not send anything to the console.
   * You can assume that I will always call this method right before calling
   * the getOptimalSolution method below.
   */
  static int getOptimalValue() {

  }// getOptimalValue method

  /* return an optimal solution (e.g., "LRRLLLRR") obtained with the
   * solveProblem method.
   * This method does not send anything to the console.
   * You can assume that I will always call getOptimalValue right before calling
   * this method.
   */
  static String getOptimalSolution() {

  }// getOptimalValue method


  /* Implement any helper method(s) that you need (if any) below
   * All methods in this class MUST be static.
   */

  static int getNumberOfTurns(String str){
    int count = 0;
    for(int i = 0; i < str.length() - 1; i++){
      if(str.charAt(i) != str.charAt(i+1)){
        count++;
      }
    }
    return count;
  }

  //insert all the values for left turns
  static void createLeft(LinkedList<String> data, int currentNode){ // currentNode starts at size + 2
    if(currentNode > sizeOfMap){
      return;
    }

    int points = Integer.parseInt(data.pop());
    if(nodeTable.get(currentNode).parentNodes.contains(currentNode - 1)){
      int leftParent, rightParent;
      leftParent = nodeTable.get(currentNode - 1).points + nodeTable.get(currentNode).points;
      rightParent = nodeTable.get(currentNode - size - 1).points + points;
      nodeTable.get(currentNode).points = Math.max(leftParent, rightParent);
      if(Integer.compare(leftParent, rightParent) == 1){
        nodeTable.get(currentNode).turns = "R" + nodeTable.get(currentNode).turns;
      } else {
        nodeTable.get(currentNode).turns = "L" + nodeTable.get(currentNode).turns;
      }
    } else {
      nodeTable.get(currentNode).parentNodes.add(currentNode - size - 1);
      nodeTable.get(currentNode).points += points;
      nodeTable.get(currentNode).turns = "L" + nodeTable.get(currentNode).turns;
    }

    createLeft(data, currentNode + 1);
  }

  //insert all the values for right turns
  static void createRight(LinkedList<String> data, int points, List<Integer> parentNodes, int currentNode, String turns){
    if(currentNode == sizeOfMap){
      Node node = new Node();
      node.points = points;
      node.parentNodes = parentNodes;
      node.turns = turns;
      nodeTable.put(currentNode, node);
      return;
    }
    if((currentNode - 1) % (size + 1) == 0){
      parentNodes.clear();
      points = 0;
      turns = "";
    }
    Node node = new Node();
    node.points = points;
    node.parentNodes = parentNodes;
    node.turns = turns;

    nodeTable.put(currentNode, node);

    points += Integer.parseInt(data.pop());
    parentNodes.add(currentNode);
    turns = turns.concat("R");

    createRight(data, points, parentNodes, currentNode + 1, turns);
  }

  public static class Node {
    int points;
    String turns;
    List<Integer> parentNodes; 
  };

  /* Do not modify this method in your submission
   */
  public static void main(String[] args) {

    String fileName = args[0];

    loadProblem(fileName);
    solveProblem();
    int val = getOptimalValue();
    System.out.println( getOptimalSolution() + " " + val );
  }// main method

}// A10 class
