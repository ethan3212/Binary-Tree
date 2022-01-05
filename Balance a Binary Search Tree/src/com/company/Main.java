package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.Vector;

class Node
{
    public int iData;
    public double dData;
    public Node leftChild;
    public Node rightChild;

    public void displayNode()
    {
        System.out.print("{");
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }    // end displayNod()

}   // end class Node

class Tree
{
    private Node root;

    public Tree()
    {
        root = null;
    }   // end Tree

    public Node find(int key)
    {
        Node current = root;

        // while no match, start at root
        while(current.iData != key)
        {
            // go left
            if(key < current.iData)
                current = current.leftChild;

            // go right
            else
                current = current.rightChild;

            // if no child, didn't find it
            if(current == null)
                return null;

        }   // end while

        // found it
        return current;

    }   // end find()

    public void insert(int id) {
        Node newNode = new Node();
        newNode.iData = id;

        // no node in root
        if (root == null)
            root = newNode;

        // root occupied
        else
        {
            // start at root
            Node current = root;
            Node parent;

            // exits internally
            while(true)
            {
                parent = current;

                // go left
                if(id < current.iData)
                {
                    current = current.leftChild;

                    // if end of the line,
                    if(current == null)
                    {
                        // insert on left
                        parent.leftChild = newNode;
                        return;
                    }   // end if
                }   // end go left

                else
                {
                    current = current.rightChild;

                    // if end of the line
                    if(current == null)
                    {
                        // insert on right
                        parent.rightChild = newNode;
                        return;
                    }   // end if
                }   // end else go right
            }   // end while
        }   // end else not root
    }    // end insert

    public boolean delete(int key)
    {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        // search for node
        while(current.iData != key)
        {
            // go left?
            parent = current;
            if(key < current.iData)
            {
                isLeftChild = true;
                current = current.leftChild;
            }   // end if

            // or go right
            else
            {
                isLeftChild = false;
                current = current.rightChild;
            }   // end else

            // end of the line, didn't find it
            if(current == null)
                return false;

        }   // end while

        // if no children, delete it
        if(current.leftChild == null && current.rightChild == null)
        {
            // if root is empty, tree is empty
            if(current == root)
                root = null;

            // disconnect from parent
            else if(isLeftChild)
                parent.leftChild = null;

            else
                parent.rightChild = null;
        }   // end if

        // if no right child, replace with left subtree
        else if(current.rightChild == null)
            if(current == root)
                root = current.leftChild;

            else if(isLeftChild)
                parent.leftChild = current.leftChild;

            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if(current.leftChild == null)
            if(current == root)
                root = current.rightChild;

            else if(isLeftChild)
                parent.leftChild = current.rightChild;

            else
                parent.rightChild = current.rightChild;

            // two children, so replace with in order successor
        else
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if(current == root)
                root = successor;

            else if(isLeftChild)
                parent.leftChild = successor;

            else
                parent.rightChild = successor;

            successor.leftChild = current.leftChild;

        }   // end else two children

        return true;

    }   // end delete()

    // returns node with next-highest value after delNode, goes to right child, the right child's left descendants
    private Node getSuccessor(Node delNode)
    {
        Node successorParent = delNode;
        Node successor = delNode;

        // go to right child
        Node current = delNode.rightChild;

        // until no more left children, go to left child
        while(current != null)
        {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }   // end while

        // if successor not right child, make connections
        if(successor != delNode.rightChild)
        {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }   // end if

        return successor;

    }   // end getSuccessor()

    public void traverse(int traverseType)
    {
        switch(traverseType)
        {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);

                break;

            case 2:
                System.out.println("\nInorder traversal: ");
                inOrder(root);

                break;

            case 3:
                System.out.println("\nPostorder traversal: ");
                postOrder(root);

                break;
        }   // end switch

        System.out.println();

    }   // end traverse()

    private void preOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }   // end if

    }   // end preOrder()

    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }   // end if

    }   // end inOrder()

    private void postOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }   // end if

    }   //  end postOrder()

    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");

        while(isRowEmpty == false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(" ");

            while(globalStack.isEmpty() == false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if(temp.leftChild != null || temp.rightChild != null)
                        isRowEmpty = false;
                }   // end if

                else
                {
                    System.out.print("_ _");
                    localStack.push(null);
                    localStack.push(null);
                }   // end else

                for(int j=0; j<nBlanks*2-2; j++)
                    System.out.print(" ");

            }   // end while globalStack not empty

            System.out.println();
            nBlanks /= 2;

            while(localStack.isEmpty() == false)
                globalStack.push(localStack.pop());

        }   // end while isRowEmpty is false
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");

    }   // end displayTree()

}   // end class Tree

public class Main
{
    public static void main(String[] args) throws IOException
    {
        int value;
        Tree theTree = new Tree();

        theTree.insert(50);
        theTree.insert(25);
        theTree.insert(75);
        theTree.insert(12);
        theTree.insert(37);
        theTree.insert(43);
        theTree.insert(30);
        theTree.insert(33);
        theTree.insert(87);
        theTree.insert(93);
        theTree.insert(97);

        while(true)
        {
            System.out.print("Enter first letter of show, ");
            System.out.print("insert, find, delete, traverse, or balance : ");
            int choice = getChar();

            switch(choice)
            {
                case 's':
                    theTree.displayTree();

                    break;

                case 'i':
                    System.out.print("Enter the value to insert: ");
                    value = getInt();
                    theTree.insert(value);

                    break;

                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getInt();
                    Node found = theTree.find(value);

                    if(found != null)
                    {
                        System.out.print("Found: ");
                        found.displayNode();
                        System.out.print("\n");
                    }   // end if

                    else
                        System.out.print("Could not find ");
                    System.out.print(value + "\n");

                    break;

                case 'd':
                    System.out.print("Enter value to delete: ");
                    value = getInt();
                    boolean didDelete = theTree.delete(value);

                    if(didDelete)
                        System.out.print("Deleted " + value + "\n");

                    else
                        System.out.print("Could not delete ");
                    System.out.print(value + "\n");

                    break;

                case 't':
                    System.out.print("Enter type 1, 2, or 3: ");
                    value = getInt();
                    theTree.traverse(value);

                    break;

                default:
                    System.out.print("Invalid entry\n");

            }   // end switch

        }   // end while

    }   // end main()

    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;

    }   // end getString()

    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);

    }   // end getChar()

    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);

    }   // end getInt()
    
}   // end class Main

