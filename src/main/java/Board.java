import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Board {

   public static void main (String[] args) throws Exception {
        board();
   }

    //asks for users input on board size, then creates and renders the board
    public static void board() throws Exception {

    // **Uncomment these lines and comment out loadBoard() for a random soup of your own size**
    // System.out.println("How tall do you want your board?");
    // Scanner input = new Scanner(System.in);
    // int a = input.nextInt();
    // System.out.println("How wide do you want your board?");
    // int b = input.nextInt();
    // deadState(a,b);
       loadBoard("toad.txt");
   }

    //method creates an empty 2d array with all values set to 0. This is the 'board' on which the game will be played
    public static int[][] deadState(int a, int b) {

        int[][] array = new int[a][b];
          for(int i = 0; i<a; i++) {
              for(int j = 0; j<b; j++) {
                  array[i][j] = 0;
              }
          }
          randomState(array);
          return array;
    }

    //iterates through the board an uses rng to decide if the cell should be alive or dead (i.e 0 or 1)
    public static int[][] randomState(int[][] array) {

       for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                Random rand = new Random();
                int upperBound = 10;
                int intRand = rand.nextInt(upperBound);
                if (intRand > 3) {
                    array[i][j] = 1;
                }
            }
        }
        render(array);
        nextGeneration(array, array[1].length, array.length);
        return array;
    }

    //method to print the board as it should appear
    public static void render(int[][] array){

       for(int i = 0; i < array.length; i++){
           System.out.print("*");
       }
        for (int[] ints : array) {
            for (int anInt : ints) {
                if (anInt == 1) {
                    System.out.print('#' + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static void nextGeneration(int[][] array, int M, int N) {

        int[][] future = new int[M][N];
        // Loop through every cell
        for (int l = 1; l < M - 1; l++) {
            for (int m = 1; m < N - 1; m++) {
                // finding no Of Neighbours that are alive
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        aliveNeighbours += array[l + i][m + j];

                // The cell needs to be subtracted from
                // its neighbours as it was counted before
                aliveNeighbours -= array[l][m];

                // Implementing the Rules of Life

                // Cell is lonely and dies
                if ((array[l][m] == 1) && (aliveNeighbours < 2))
                    future[l][m] = 0;

                    // Cell dies due to over population
                else if ((array[l][m] == 1) && (aliveNeighbours > 3))
                    future[l][m] = 0;

                    // A new cell is born
                else if ((array[l][m] == 0) && (aliveNeighbours == 3))
                    future[l][m] = 1;

                    // Remains the same
                else
                    future[l][m] = array[l][m];
            }
        }
        render(future);
        try {
            nextGeneration(future, M, N);
        }catch (java.lang.StackOverflowError exc){

        }
    }

    //loads a board from a text file by using
    public static int[][] loadBoard(String fileName) throws Exception {

       int[][] array = null;
        //finds specified file
       BufferedReader buffer = new BufferedReader(new FileReader(fileName));
       String line;
       int row = 0;
       int size = 0;
       //reads through the file, trims the line at spaces and assigns each value to its own index in string array
       while((line = buffer.readLine()) != null){
           String[] vals = line.trim().split("\\s+");
           if(array == null) {
               size = vals.length;
               array = new int[size][size];
           }
           //assigns the value of the String array to its correct position in the new array we will render
           for(int col = 0; col < size; col++){
               array[row][col] = Integer.parseInt(vals[col]);;
           }
           row++;
       }
       //render the new array, wait, then call the method again
        render(array);
        TimeUnit.SECONDS.sleep(4);
        nextGeneration(array, array[1].length, array.length);
       return array;
    }






}




