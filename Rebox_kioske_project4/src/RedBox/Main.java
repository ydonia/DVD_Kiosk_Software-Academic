package RedBox;

import com.sun.source.tree.BinaryTree;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.StringTokenizer;

/*
Youssef Donia
ymd180001
Project 4
 */

public class Main {

    public static void main(String [] args)
    {

            // Initialize the Binary Search Tree
            KioskeBinarySearchTree<Movie> kioskeInventory = new KioskeBinarySearchTree<Movie>();

            // read from the inventory.dat file, and populate the BST
            // initialize a path for the file
            File inventoryFile = new File("inventory.dat");
            String inventoryLine = "";
            Scanner scanner = ScannerFactory.GetScannerInstance(inventoryFile);
            StringTokenizer tokenizer;
            String title = "";
            int quantityAvailable;

            // while loop to iterate over the file
            while(scanner.hasNextLine()) {

                // read line from file into a string
                inventoryLine = scanner.nextLine();

                // TODO: make sure to ignore the "" and commas
                inventoryLine = inventoryLine.replaceAll(",", ""); // remove commas
                inventoryLine = inventoryLine.replaceAll("\"", ""); // remove quotation marks

                // separate string into tokens
                tokenizer = new StringTokenizer(inventoryLine);
                // store tokens into String title and int available respectively
                title = tokenizer.nextToken();
                while (tokenizer.countTokens() > 1)
                {
                    title += " " + tokenizer.nextToken(); // continue filling in the title
                }
                quantityAvailable = Integer.parseInt(tokenizer.nextToken());

                // store into a movie
                Movie movieToAdd = new Movie(title, quantityAvailable);

                // store the movie into the BST
                kioskeInventory.insert(movieToAdd);

            } // end of while loop

        // close the scanner instance
        ScannerFactory.CloseScannerInstance();

            // process the transaction.log file

            // initialize a path for the file
            File transactionFile = new File("transaction.log");

            // TODO: must use Singleton pattern for scanner
            scanner = ScannerFactory.GetScannerInstance(transactionFile);
            String transactionLine = "";
            String command = "";
            String movieTitle = "";
            int amount;
            StringTokenizer tokenizer1;
            Movie found;
            // while loop to iterate over the whole file
            while(scanner.hasNextLine())
            {
                // read line from file into string line
                transactionLine = scanner.nextLine();
                // TODO: make sure to ignore the case, "", and spaces after commas
                transactionLine = transactionLine.replaceAll(",", ""); // remove commas
                transactionLine = transactionLine.replaceAll("\"", ""); // remove quotation marks

                tokenizer1 = new StringTokenizer(transactionLine);

                // read in the values from the file into the variables
                command = tokenizer1.nextToken();
                movieTitle = tokenizer1.nextToken();
                while (tokenizer1.countTokens() > 1)
                {
                    movieTitle += " " + tokenizer1.nextToken();
                }
                amount = Integer.parseInt(tokenizer1.nextToken());

                // check that the line follows one of the formats its supposed to follow and command is correct
                // also check the the number to add or number to remove are valid numbers
                if (tokenizer1.countTokens() != 0 || (command.compareToIgnoreCase("add") != 0 &&
                        command.compareToIgnoreCase("remove") != 0 && command.compareToIgnoreCase("rent") != 0 &&
                        command.compareToIgnoreCase("return") != 0)|| amount < 1)
                {
                    // write the line to a file called "error.log" if not valid
                    File errorFile = new File("error.log");
                    try(PrintWriter fileWriter = new PrintWriter(errorFile))
                    {
                        fileWriter.println(transactionLine);
                        System.out.println("format of line is invalid, transaction written to \"error.log\".");

                    } catch(IOException e)
                    {
                        System.out.println("Was not able to create and write to \"error.log\" file");
                    }
                } else {  // if line is valid, process the transaction and add to the file "redbox_kioske.txt"

                    // create a movie to be able to search the BST
                    Movie movie = new Movie();
                    movie.setTitle(movieTitle);
                    found = null;


                    // increase number of available movies
                    if (command.compareToIgnoreCase("add") == 0) {
                        // search for the movie in the BST
                        found = kioskeInventory.getElement(movie);
                        if (found == null) {
                            // insert the movie into the BST
                            movie.setAvailable(amount);
                            kioskeInventory.insert(movie);
                        } else {
                            // increase the number of available movies by the amount given
                            found.addAvailable(amount);
                        }
                        // decrease number of available movies
                    } else if (command.compareToIgnoreCase("remove") == 0) {
                        // search for the movie in the BST
                        found = kioskeInventory.getElement(movie);
                        if (found == null) {
                            // display error message
                            System.out.println("The movie is unfortunately not in our inventory.");
                        } else {
                            // increase the number of available movies by the amount given
                            found.removeAvailable(amount);
                            if (found.getAvailable() == 0 && found.getRented() == 0)
                            {
                                kioskeInventory.delete(found);
                            }
                        }
                        // rent a movie
                    } else if (command.compareToIgnoreCase("rent") == 0) {
                        // search for the movie in the BST
                        found = kioskeInventory.getElement(movie);
                        if (found == null) {
                            // display error message
                            System.out.println("The movie is unfortunately not in our inventory.");
                        } else {
                            // increase the number of available movies by the amount given
                            found.rentTitle(amount);
                        }
                        // return a movie
                    } else if (command.compareToIgnoreCase("return") == 0)
                    {
                        // search for the movie in the BST
                        found = kioskeInventory.getElement(movie);
                        if (found == null) {
                            // display error message
                            System.out.println("The movie is unfortunately not in our inventory.");
                        } else {
                            // increase the number of available movies by the amount given
                            found.returnTitle(amount);
                        }
                    }
                }
            } // end of while loop

        // close the scanner instance
        ScannerFactory.CloseScannerInstance();


        // write the updated BST into the file
        File outputFile = new File("redbox_kioske.txt");

        kioskeInventory.writeToFile(outputFile);

        // print the contents of the output file
        scanner = ScannerFactory.GetScannerInstance(outputFile);
        String outputLine = "";
        // while loop to iterate over the file
        outputLine += String.format("%-33s %-20s %-7s", "Title", "Available", "Rented") + "\n";
        outputLine += "-------------------------------------------------------------" + "\n";
        while (scanner.hasNextLine())
        {
            outputLine += scanner.nextLine() + "\n";
        }

        System.out.print(outputLine);
    }
}
