package com.company;

public class Node {
    public int iData;
    public double dData;
    public com.company.Node leftChild;
    public com.company.Node rightChild;

    public void displayNode() {
            System.out.println("{");
            System.out.print(iData);
            System.out.print(", ");
            System.out.print(dData);
            System.out.print("} ");
    }
}
