package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        Tree theTree = new Tree();
        for(int i = 0; i < 10; i++) {
            int number = random.nextInt(10);
            theTree.insert(number);
        }
        int value;
        while(true) {
            System.out.print("Enter first letter of show, ");
            System.out.print("insert, find, delete, or traverse: ");
            int choice = getChar();
            switch(choice) {
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
                    if(found != null) {
                        System.out.print("Found: ");
                        found.displayNode();
                        System.out.print("\n");
                    } else {
                        System.out.print("Could not find ");
                        System.out.print(value + "\n");
                        break;
                    }
                case 'd':
                    System.out.print("Enter value to delete: ");
                    value = getInt();
                    boolean didDelete = theTree.delete(value);
                    if(didDelete) {
                        System.out.print("Deleted " + value + "\n");
                    } else {
                        System.out.print("Could not delete ");
                        System.out.print(value + "\n");
                        break;
                    }
                case 't':
                    System.out.print("Enter type 1, 2, or 3: ");
                    value = getInt();
                    theTree.traverse(value);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }
        }
    }

    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
}
