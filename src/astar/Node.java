package astar;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Node {

    // Grid position
    private int i;
    private int j;
    // Centre of Node
    private double x;
    private double y;
    // f(n) = g(n) + h(n)
    private double f;
    private double g = Double.POSITIVE_INFINITY;

    private boolean isObstacle = false;

    private Node cameFrom = null;

    private Color NodeColour = Color.WHITE;

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

    public void calculateF(Node goal) {
        this.f =  g + Math.abs(this.i - goal.getI()) + Math.abs(this.j - goal.getJ());
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public Color getNodeColour() {
        return NodeColour;
    }

    public void setNodeColour(Color nodeColour) {
        NodeColour = nodeColour;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Node getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Node cameFrom) {
        this.cameFrom = cameFrom;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }
}
