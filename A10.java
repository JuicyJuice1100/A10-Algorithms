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

class A10 {

  static int size;        // number of edges on one side of the square
  static int minTurns;    // minimum required number of turns in the solution
  static int turnPenalty; // penalty for each extra turn over the minimum

  /* define your static variables below (NO instance variables allowed) */
  static int[][] table;
  static Map<Integer, Node> pathValues = new HashMap<Integer, Node>();
  static int index;



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

      table = new int[(int)(Math.pow(size + 1, 2)-1)][2];

      createRight(data); //gets all the rightTurn values
      createLeft(data); //gets all the leftTurn values

      initializeMap();

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
    for (Map.Entry<K,V> entry : map.entrySet()) {
      K key = entry.getKey();
      V value = entry.getValue();
      
      
    }
  }// solveProblem method

  /* return the value of an optimal solution obtained with the solveProblem
   * method.
   * This method does not send anything to the console.
   * You can assume that I will always call this method right before calling
   * the getOptimalSolution method below.
   */
  static int getOptimalValue() {

    /* To be completed */

    return -1;
  }// getOptimalValue method

  /* return an optimal solution (e.g., "LRRLLLRR") obtained with the
   * solveProblem method.
   * This method does not send anything to the console.
   * You can assume that I will always call getOptimalValue right before calling
   * this method.
   */
  static String getOptimalSolution() {

    /* To be completed */

    return "";
  }// getOptimalValue method


  /* Implement any helper method(s) that you need (if any) below
   * All methods in this class MUST be static.
   */

  //inserts all the values for left turns into leftArray
  static void createLeft(LinkedList<String> data){
    // leftArray = new ArrayList<Integer>(rightArray.size());


    // while(!data.isEmpty(){
    //   leftArray.add(Integer.parseInt(data.pop()));
    // }
    for(int i = 0; i < table.length; i++){
      if(i >= table.length - size){
        table[i][0] = Integer.MIN_VALUE;
      } else {
        table[i][0] = Integer.parseInt(data.pop());
      }
    }
  }

  //insert all the values for right turns into rightArray
  static void createRight(LinkedList<String> data){
    // rightArray = new ArrayList<Integer>(half);

    // while(data.size() > half){
    //   rightArray.add(Integer.parseInt(data.pop()));
    // }

    for(int i = 0; i < table.length; i++){
      if((i + 1) % (size + 1) == 0){
        table[i][1] = Integer.MIN_VALUE;
      } else {
        table[i][1] = Integer.parseInt(data.pop());
      }
    }
  }

  static void initializeMap(){
    for(int i = 2; i <= table.length + 1; i++){
      Node node = new Node();
      pathValues.put(i, node);
    }
    // Node node = new Node();
    // node.points = 0;
    // node.path = null;
    // pathValues.put(1, node);
  }

  static int moveRight(int index){
    return index++;
  }

  static int moveLeft(int index){
    return index + size + 1;
  }

  static class Node {
    int points;
    String path;
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
