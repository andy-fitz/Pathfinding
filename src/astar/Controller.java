package astar;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {

    private double width = 900;
    private double height = 900;
    private int rows = 45;
    private int cols = 45;

    private Node[][] grid = new Node[rows][cols];

    @FXML
    Pane environment;
    Canvas canvas = new Canvas();

    public void initialize(){
        canvas.setWidth(width);
        canvas.setHeight(height);
        environment.getChildren().add(canvas);
        draw();
        drawNodes();
        drawPath(grid[20][25], grid[44][30]);
        System.out.println(grid.length);
    }

    // Draw Background.
    public void draw(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.GREEN);
        g.fillRect(0,0,width,height);
    }

    // Draw Node Grid.
    public void drawNodes(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);

        for(int i=0; i<width; i+=20){
            for(int j=0; j<height; j+=20){
                g.fillOval(i+5,j+5,10,10);
                grid[i/20][j/20] = new Node(i+10,j+10, i/20, j/20);
            }
        }

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                grid[i][j].addNeighbours(grid);
            }
        }
    }

    // Draw a line between two Nodes.
    public void drawPath(Node node1, Node node2){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.ORANGE);
        g.setStroke(Color.ORANGE);
        g.setLineWidth(5);
        g.strokeLine(node1.getX(),node1.getY(),node2.getX(),node2.getY());
    }


}
