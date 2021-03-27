package RedBox;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class KioskeBinarySearchTree<E extends Comparable<E>>{

    private Node<E> root; // pointer to the root of the BST
    private int size = 0;
    // class Node to be able to traverse the BST
    // TODO: i dont think Node needs to implement comparable any longer
    private static class Node<E>
    {
        // member variables
        Node<E> left; // pointer to move left in BST
        Node<E> right; // pointer to move right in BST
        E element; // pointer to the current node in BST

        // member functions

        // overloaded constructor
        public Node(E e)
        {
            element = e;
            this.left = null;
            this.right = null;
        }

    }

    // to access the size outside of the class
    public int getSize()
    {
        return size;
    }

    // TODO: not sure about this
    public void setRoot(Node<E> root)
    {
        this.root = root;
    }

    // TODO: not sure about this
    public Node<E> getRoot()
    {
        return root;
    }

    // insert an element into the BST
    // TODO: has to be recursive

    //calls insert function that uses recursion
    public boolean insert(E element) {
        return insert(root, element) != null;
    }

    //adds a node to the tree
    public Node<E> insert(Node<E> current, E element)
    {
        // first, check if the list is empty
        if (this.root == null)
        {
            this.root = new Node<E>(element);
            return this.root;
        }

        //if it reaches a leaf node, assign the element there
        if (current == null)
        {
            current = new Node<E>(element);
            return current;
        }

        // if the element is less than/ greater than the current element in BST
        if (element.compareTo(current.element) < 0)
            current.left = insert(current.left, element);
        else if (element.compareTo(current.element) > 0)
            current.right = insert(current.right, element);
        else return null; // if unable to insert element
        // return the unchanged node
        return current;
    }

    // TODO: is this method recursive???????

    //calls search function that uses recursion
    public boolean search(E element) {
        Node<E> returnNode = null;
        returnNode = search(root, element);
        return returnNode != null;

    }

    // searches the binary tree with recursion for an element
    public Node<E> search(Node<E> current, E element)
    {
        // Base Cases: root is null or key is present at root
        if (current == null || current.element == element || current.element.compareTo(element) == 0)
            return current;

        //else recur down the tree
        if (element.compareTo(current.element) < 0) {
            return search(current.left, element);
        }

        //element is bigger than root's element
        return search(current.right, element);
    }

    // method to get an element from the BST
    public E getElement(E element)
    {
        Node<E> current = root; // to start from the root of the BST

        while (current != null)
        {
            // if the element is less, search to the left of the BST
            if (element.compareTo(current.element) < 0)
            {
                current = current.left;
            }
            // if the element is greater, search to the right of the BST
            else if (element.compareTo(current.element) > 0)
            {
                current = current.right;
            }
            else // if the elements are equal
            {
                return current.element; // return the element in the BST
            }
        }
        return null; // if element was not found
    }

    // method to delete an element from the BST
    public boolean delete(E element)
    {
        // iterate to find the node and its parent
        Node<E> parent = null;
        Node<E> current = root;

        while (current != null)
        {
            // if the element is less than the current element
            if (element.compareTo(current.element) < 0)
            {
                // assign current element as parent of the element
                parent = current;
                // search to the left of the BST
                current = current.left;
            } else if(element.compareTo(current.element) > 0)
            {
                parent = current;
                current = current.right;
            } else break; // exit loop because element has already been found
        }

        // if we reach the end of the tree and have not found the element, return false
        if (current == null)
            return false;

        // if the current element does not have a left child
        if (current.left == null)
        {
            // assign the right child of current to be the parent

            if (parent == null) // if no parent exists yet
            {
                // assign the parent to the root of the BST
                root = current.right;
            }
            else
            {
                // if the element is less than the parent
                if (element.compareTo(parent.element) < 0)
                {
                    parent.left = current.right;
                } else
                {
                    parent.right = current.right;
                }
            }
        }
        else // if the current node we want to delete has a left child
        {
            // find the rightmost node in the left subtree and its parent
            Node<E> parentOfRightMost = current;
            Node<E> rightMost = current.left;

            // keep going to the right until rightMost node is found
            while (rightMost.right != null)
            {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            // replace the value of the current element to the value in the rightmost element
            current.element = rightMost.element;

            // delete the node that is currently assigned to rightmost as we put the value in current earlier
            if (parentOfRightMost.right == rightMost)
            {
                parentOfRightMost.right = rightMost.left;
            }
            else // if the parent of right most node is also the current
            {
                parentOfRightMost.left = rightMost.left;
            }
        }

        // decrement size and indicate that element was successfully deleted
        size--;
        return true;
    }


    /*
    // to traverse the BST
    public void inorder()
    {
        inorder(root);
    }

    protected void inorder(Node<E> root)
    {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element);
        inorder(root.right);
    }

     */


    // write the contents of the BST to a file

    public void writeToFile(File file)
    {
        writeToFile(root, file);
    }
    protected void writeToFile(Node<E> root, File file)
    {
        if (root == null) return;
        writeToFile(root.left, file);
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(file, true)))
        {
            //fileWriter.println(root.element);
            fileWriter.append(root.element.toString() + "\n");
            fileWriter.close();
        } catch (IOException e)
        {
            System.out.println("File could not be written to");
        }
        writeToFile(root.right, file);
    }

}
