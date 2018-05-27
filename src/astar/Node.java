package astar;

import java.util.ArrayList;

public class Node {

    private int i;
    private int j;
    private double x;
    private double y;
    private double f;
    private double g;

    private ArrayList<Node> neighbours = new ArrayList<>();

    public Node(double x,double y, int i, int j){
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "Node: " + x + " " + y;
    }

    public void addNeighbours(Node[][] grid){
        System.out.println(i + " " + j);
        if(i < grid.length-1){
            neighbours.add(grid[i+1][j]); // Right
        }
        if(i > 0) {
            neighbours.add(grid[i - 1][j]); // Left
        }
        if (j < grid.length -1) {
            neighbours.add(grid[i][j + 1]); // Bottom
        }
        if(j > 0) {
            neighbours.add(grid[i][j - 1]); // Top
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }
}
