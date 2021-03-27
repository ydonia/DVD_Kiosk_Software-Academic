package Redbox_test;

import RedBox.KioskeBinarySearchTree;
import RedBox.Movie;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class Apptest {


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    // test the insert function for the BST
    public void AddTitle()
    {
        // initialize a BST with a few variables
        KioskeBinarySearchTree<Integer> BST = new KioskeBinarySearchTree<Integer>();
        if (!BST.insert(20))
        {
            assertTrue("the function did not insert", false);
        }
        if (!BST.insert(10))
        {
            assertTrue("the function did not insert", false);
        }
        if (!BST.insert(25))
        {
            assertTrue("the function did not insert", false);
        }

    }

    // test the delete function for the BST
    public void RemoveTitle()
    {
        // initialize a BST
        KioskeBinarySearchTree<Integer> BST = new KioskeBinarySearchTree<Integer>();
        BST.insert(20);
        BST.insert(10);
        BST.insert(25);

        // call the delete function on one of the elements in the BST
        BST.delete(10);
        // check to see if that element has been removed
        boolean found = BST.search(10);
        if (found)
        {
            assertTrue("Element was not deleted from the BST", false);
        }
    }

    // test to see if RentTitle function worked in Node
    public void RentTitle()
    {
        // initialize a BST
        KioskeBinarySearchTree<Movie> BST = new KioskeBinarySearchTree<Movie>();
        Movie movie = new Movie("Title", 3);
        movie.setRented(0);
        BST.insert(movie);

        BST.getElement(movie).rentTitle(1); // rent one of the movies
        // the function must have incremented rented by 1 and decremented available by 1
        if (movie.getRented() != 1 || movie.getAvailable() != 2)
        {
            assertTrue("function did not rent properly", false);
        }
    }

    // test to see if ReturnTitle function worked in Node
    public void ReturnTitle()
    {
        // initialize a BST
        KioskeBinarySearchTree<Movie> BST = new KioskeBinarySearchTree<Movie>();
        Movie movie = new Movie("Title", 3);
        movie.setRented(2);
        BST.insert(movie);

        BST.getElement(movie).returnTitle(1); // return one of the movies
        // the function must have decremented rented by 1 and incremented available by 1
        if (movie.getRented() != 1 || movie.getAvailable() != 4)
        {
            assertTrue("function did not return properly", false);
        }
    }


}
