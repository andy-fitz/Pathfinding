package astar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Controller {

    private double width = 900;
    private double height = 900;
    private int rows = 45;
    private int cols = 45;

    private Node startNode;
    private Node goalNode;

    private Node[][] grid = new Node[rows][cols];

    @FXML
    Pane environment;
    Canvas canvas = new Canvas();
    Button runBtn = new Button();

    public void initialize(){
        canvas.setWidth(width);
        canvas.setHeight(height);
        environment.getChildren().add(canvas);

        runBtn.setText("Build Path");
        runBtn.setLayoutY(900);
        runBtn.layoutXProperty().bind(environment.widthProperty().divide(2).subtract(runBtn.widthProperty().divide(2)));
        environment.getChildren().add(runBtn);

        draw();
        createNodes();
        drawNodes();
        setNeighbours();

        startNode = grid[0][0];
        goalNode = grid[10][cols-1];

        runBtn.setOnAction(e -> {
            new Thread(new Runnable(){
                public void run(){
                    aStar(startNode, goalNode);
                }
            }).start();

        });
        //drawPath(grid[20][25], grid[44][30]);
    }

    // Draw Background.
    public void draw(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.GREEN);
        g.fillRect(0,0,width,height);
    }

    public void createNodes(){
        for(int i=0; i<width; i+=20) {
            for (int j = 0; j < height; j += 20) {
                grid[i/20][j/20] = new Node(i+10,j+10, i/20, j/20);
            }
        }
    }

    // Draw Node Grid.
    public void drawNodes(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);

        for(int i=0; i<width; i+=20){
            for(int j=0; j<height; j+=20){
                g.setFill(grid[i/20][j/20].getNodeColour());
                g.fillOval(i+5,j+5,10,10);
            }
        }
    }

    public void setNeighbours(){
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

    public void aStar(Node start, Node goal){

        ArrayList<Node> closedSet = new ArrayList<>();
        ArrayList<Node> openSet = new ArrayList<>();

        openSet.add(start);

        start.setG(0);
        start.calculateF(goal);

        Node current;

        while (!openSet.isEmpty()){

            int lowest = 0;
            for(int i = 0;i < openSet.size(); i++){
                if(openSet.get(i).getF() < openSet.get(lowest).getF()){
                    lowest = i;
                }
            }
            current = openSet.get(lowest);
            if(current.equals(goal)){
                System.out.println("done");
                // return path
            }

            openSet.remove(current);
            closedSet.add(current);
            current.setNodeColour(Color.RED);

            ArrayList<Node> neighbours = current.getNeighbours();

            for(Node neighbour : neighbours){
                if(!closedSet.contains(neighbour)){
                    if(!openSet.contains(neighbour)){
                        openSet.add(neighbour);
                        neighbour.setNodeColour(Color.ORANGE);
                    }

                    double temp_score = current.getG() + 1;
                    if (temp_score < neighbour.getG()){
                        neighbour.setG(temp_score);
                        neighbour.calculateF(goal);
                    }
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                drawNodes();
            });

        }

        System.out.println("Failure");
    }

}
