package sample;

import java.util.ArrayList;

public class Node {

    private double x;
    private double y;
    private ArrayList neighbours;

    public Node(double x,double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Node: " + x + " " + y;
    }

    public void addNeighbours(){

    }
}
