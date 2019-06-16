// XXX CMPS - Assignment 1 - BINGO XXX
//
//ASCII pic can be found at
//https://asciiart.website/index.php?art=movies/star%20wars

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.stream.*;
import java.io.File;


public class Test {

    public static int firstInput;
    public static boolean isBingo = false;
    public static int[][] zerosIndexes = new int[5][2];

    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String RESET = "\033[0m";  // Text Reset



    // check for bingo
    public static boolean checkBingo(ArrayList<ArrayList<Integer>> list, int dimension) {

            int columnCount = 0;

            for(int i = 0; i < list.size(); i++) {
                // count zeros in rows
                for(int j = 0; j < list.get(i).size(); j++) {

                    if(list.get(i).get(j) == 0) {

                        zerosIndexes[j][0] = i;
                        zerosIndexes[j][1] = j;
                        ++columnCount;
                    }
                }

                isBingo = (columnCount == dimension);
                if(isBingo == true) {

                    return isBingo;
                }
                columnCount = 0;

                // count zeros in column
                for(int j = 0; j < list.get(i).size(); j++) {

                    if(list.get(j).get(i) == 0) {

                        zerosIndexes[j][0] = j;
                        zerosIndexes[j][1] = i;
                        ++columnCount;
                    }
                }

                isBingo = (columnCount == dimension);
                if(isBingo == true) {

                    return isBingo;
                }
                columnCount = 0;
            }

            int count = 0;
            int count2 = 0;
            // check diagnals
            for(int i = 0, j = list.size()-1; i < list.size(); i++, j--) {

                // back slash diagal
                if(list.get(i).get(i) == 0) {
                    zerosIndexes[i][0] = i;
                    zerosIndexes[i][1] = i;
                    count++;
                }
            }

            isBingo = (count == dimension);
            if(isBingo == true) {
                 return isBingo;
            }

            for(int i = 0, j = list.size()-1; i < list.size(); i++, j--) {

                // forward slash diagnol
                if(list.get(j).get(i) == 0) {
                    zerosIndexes[i][0] = i;
                    zerosIndexes[i][1] = j;
                    count2++;
                }
            }

            isBingo = (count2 == dimension);
            if(isBingo == true) {

                return isBingo;
            }
        return isBingo;
    }


    // creates an initial directory list containing numbers that can be called
    public static ArrayList<Integer> setDirectory(int dimension) {

        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 1; i <= (dimension*15); i++) {

            list.add(i);
        }
        return list;
    }


    // removes called numbers from the calledNumbersDirectory 
    public static void removeNumber(int n, ArrayList<Integer> list) {

        if(list.contains(n)) {

            list.remove(n);
        }
    }

    // gets the next random number 
    public static int generateRandomNumber(ArrayList<Integer> list) {

        Random rand = new Random();
        int randInt = list.get(rand.nextInt(list.size()));

        return randInt;
    }

    // updates the array with the new called number in generateRandomNumber()
    public static ArrayList<ArrayList<Integer>> updateCard(ArrayList<ArrayList<Integer>> list, ArrayList<Integer> calledNumbers, int n, int dimension) {

        for(int i = 0; i < dimension; i++) {

            if(list.get(i).contains(n)) {

                for(int j = 0; j < dimension; j++) {

                    if(list.get(i).get(j) == n) {

                        list.get(i).set(j, 0);
                    }
                }
            }
        }

        calledNumbers.remove(Integer.valueOf(n));

        return list;
    }


    // prints the updated table
    public static void printCard(ArrayList<ArrayList<Integer>> list) {

        System.out.println(RED + "\n  B  I  N  G  O" + RESET);

        if(firstInput == 1) {

            for(int i = 0; i < list.size(); i++) {

                for(int j = 0; j < list.size(); j++) {

                    if(list.get(j).get(i) == 0) {

                        System.out.printf(YELLOW + "%3d" + RESET, list.get(j).get(i));
                    }
                    else {

                        System.out.printf(GREEN + "%3d" + RESET, list.get(j).get(i));
                    }
                }
                System.out.println();
            }
        }

        else {

            for(int i = 0; i < list.size(); i++) {

                for(int j = 0; j < list.size(); j++) {

                    // zerosIndexes
                    if((i == zerosIndexes[j][1] && j == zerosIndexes[j][0]) || (i == zerosIndexes[i][1] && j == zerosIndexes[i][0])) {

                        System.out.printf(YELLOW + "%3d" + RESET, list.get(j).get(i));
                    }
                    else {
                        System.out.printf(GREEN + "%3d" + RESET, list.get(j).get(i));
                    }
                }
                System.out.println();
            }
        }
    }


    // creates a list of random values between two ranges and adds to a list
    public static ArrayList<Integer> getRandomValues(int minRange, int maxRange, int dimension) {

        ArrayList<Integer> randList = new ArrayList<>();
        Random randInt = new Random();

        for(int i = minRange; i <= maxRange; i++) {

            randList.add(i);
        }
        // shuffle the list
        Collections.shuffle(randList);

        // keep first 5 items in the list
        randList.subList(dimension, randList.size()).clear();

        return randList;
    }


    // makes bingo card
    public static ArrayList<ArrayList<Integer>> makeCard(int dimension) {

        int min = 1;
        int max = 3*dimension; 

        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<ArrayList<Integer>> cardList = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i < dimension; i++) {

            list = getRandomValues(min, max, dimension);
            cardList.add(list);
            min = max+1;
            max += 3*dimension;
        }

        // replace center square value with 0
        cardList.get(dimension/2).set(dimension/2,0);

        return cardList;
    }

    // add asic art when game is over
    public static void asciiArt() {

        try {
            File file = new File("starwars.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                System.out.println("\t\t" + scanner.nextLine());
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    // run game after user choose to manually call numbers or radomly automate numbers
    public static void runGame() {

        int dimension = 5;
        ArrayList<ArrayList<Integer>> aCard = makeCard(dimension);

        ArrayList<Integer> userCalledNumbers = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 1 for user caller.\nType 2 for random caller: ");

        firstInput = scanner.nextInt();
        // user input values
        if(firstInput == 1) {

            while(isBingo == false) {

                printCard(aCard);

                System.out.print("Choose the next number:  ");
                int nextNumber = scanner.nextInt();
                updateCard(aCard, userCalledNumbers, nextNumber, dimension);

                isBingo = checkBingo(aCard, dimension);

                if(isBingo == true) {

                    printCard(aCard);
                    asciiArt();
                }

            }
        }

        // random values
        else {

            ArrayList<Integer>calledNumbersDirectory = setDirectory(dimension);
            while(isBingo == false) {

                int nextNumber = generateRandomNumber(calledNumbersDirectory);
                updateCard(aCard, calledNumbersDirectory, nextNumber, dimension);

                isBingo = checkBingo(aCard, dimension);
                if(isBingo == true) {

                    printCard(aCard);
                    asciiArt();
                    for(int i[]: zerosIndexes) {

                        for(int j: i) {
                            System.out.print(j + " ");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }


    public static void main(String[] args){

        runGame();

    }
}

