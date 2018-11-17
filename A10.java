/* A10.java - Driver code for this assignment
*
*  @version CS 321 - Fall 2018 - A10
*
*  @author 1st Justin Espiritu
*
*  @author 2nd Robert Freiberg
*
*
*/

import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

class A10 {

  static int size;        // number of edges on one side of the square
  static int minTurns;    // minimum required number of turns in the solution
  static int turnPenalty; // penalty for each extra turn over the minimum

  /* define your static variables below (NO instance variables allowed) */
  static Map<Integer, Node> pathTable;
  static Map<Integer, Turn> costTable;
  static int sizeOfMap;
  static int currentMax;



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

      pathTable = new HashMap<Integer, Node>();

      createRight(data); //gets all the rightTurn values
      createLeft(data); //gets all the leftTurn values

      costTable = new HashMap<Integer, Turn>();

      initializeCostTable();

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
      data.points -= getNumberOfTurns(data.turns) * turnPenalty;
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

    return costTable.get(sizeOfMap).points;
  }// getOptimalValue method

  /* return an optimal solution (e.g., "LRRLLLRR") obtained with the
   * solveProblem method.
   * This method does not send anything to the console.
   * You can assume that I will always call getOptimalValue right before calling
   * this method.
   */
  static String getOptimalSolution() {

    return costTable.get(sizeOfMap).turns;
  }// getOptimalValue method


  /* Implement any helper method(s) that you need (if any) below
   * All methods in this class MUST be static.
   */

  //inserts all the values for left turns into leftArray
  static void createLeft(LinkedList<String> data){
    for(int i = 1; i < sizeOfMap; i++){
      if(i > sizeOfMap - size - 1){
        pathTable.get(i).leftNode = -1;
        pathTable.get(i).leftPoints = Integer.MIN_VALUE;
      } else {
        pathTable.get(i).leftNode = i + size + 1;
        pathTable.get(i).leftPoints = Integer.parseInt(data.pop());
      }
    }
  }

  //insert all the values for right turns into rightArray
  static void createRight(LinkedList<String> data){
    for(int i = 1; i < sizeOfMap; i++){
      Node node = new Node();
      if(i % (size + 1) == 0){
        node.rightPoints = Integer.MIN_VALUE;
        node.rightNode = -1;
        pathTable.put(i, node);
      } else {
        node.rightPoints = Integer.parseInt(data.pop());
        node.rightNode = i + 1;
        pathTable.put(i, node);
      }
    }
  }

  static void initializeCostTable(){
    for(int i = 1; i <= sizeOfMap; i++){
      costTable.put(i, null);
    }
  }

  static Turn solveHelper(int points, int goal, int currentNode, String turns){
    if(currentNode == goal || currentNode == -1 || currentNode > goal){
      Turn data = new Turn();
      data.points = points;
      data.turns = turns;
      return data;
    } else {

      Turn left = solveHelper(points + pathTable.get(currentNode).leftPoints, goal, pathTable.get(currentNode).leftNode, turns + "L");
      Turn right = solveHelper(points + pathTable.get(currentNode).rightPoints, goal, pathTable.get(currentNode).rightNode, turns + "R");
      
      
      if(left.compareTo(right) == 1){
        return left;
      } else {
        return right;
      }
    }
  }

  static int getNumberOfTurns(String str){
    int count = 0;
    for(int i = 0; i < str.length() - 1; i++){
      if(str.charAt(i) != str.charAt(i+1)){
        count++;
      }
    }
    return count;
  }

  static class Node {
    int leftPoints, rightPoints, leftNode, rightNode;
  };

  static class Turn implements Comparable{
    int points;
    String turns;

    public int compareTo(Turn o) {
      return Integer.compare(this.points, o.points);
    }
  }

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
