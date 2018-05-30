package astar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


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
    Button genObstBtn = new Button();


    public void initialize(){
        canvas.setWidth(width);
        canvas.setHeight(height);
        environment.getChildren().add(canvas);

        runBtn.setText("Build Path");
        runBtn.setLayoutY(900);
        runBtn.layoutXProperty().bind(environment.widthProperty().divide(2).subtract(runBtn.widthProperty().divide(2)));
        environment.getChildren().add(runBtn);

        genObstBtn.setText("Generate Obstacles");
        genObstBtn.setLayoutY(900);
        genObstBtn.layoutXProperty().bind(environment.widthProperty().divide(2).add(runBtn.widthProperty().divide(2)));
        environment.getChildren().add(genObstBtn);

        draw();
        createNodes();
        setNeighbours();

        startNode = grid[0][0];
        goalNode = grid[rows-1][cols-1];

        addObstacles();
        drawNodes();

        runBtn.setOnAction(e -> {
            new Thread(new Runnable(){
                public void run(){
                    reset();
                    aStar(startNode, goalNode);
                }
            }).start();

        });

        genObstBtn.setOnAction(e -> {

                    canvas.getGraphicsContext2D().clearRect(0,0,900,900);
                    draw();
                    createNodes();
                    setNeighbours();

                    startNode = grid[0][0];
                    goalNode = grid[rows-1][cols-1];
                    startNode.setNodeColour(Color.web("#ff00ff"));
                    goalNode.setNodeColour(Color.web("#ff00ff"));

                    addObstacles();
                    drawNodes();
        });


    }

    // Draw Background.
    public void draw(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#5cd65c"));
        g.fillRect(0,0,width,height);
    }

    public void createNodes(){
        for(int i=0; i<width; i+=20) {
            for (int j = 0; j < height; j += 20) {
                grid[i/20][j/20] = new Node(i+10,j+10, i/20, j/20);
            }
        }
    }

    public void addObstacles(){

        for(int i = 0; i < 900; i++){
            Random r = new Random();
            int x = r.nextInt(45);
            int y = r.nextInt(45);

            grid[x][y].setNodeColour(Color.BLACK);
            grid[x][y].setObstacle(true);
        }

        startNode.setObstacle(false);
        goalNode.setObstacle(false);
    }

    // Draw Node Grid.
    public void drawNodes(){
        GraphicsContext g = canvas.getGraphicsContext2D();

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
        g.setFill(Color.BLUE);
        g.setStroke(Color.BLUE);
        g.setLineWidth(5);
        g.strokeLine(node1.getX(),node1.getY(),node2.getX(),node2.getY());
    }

    public void aStar(Node start, Node goal){

        ArrayList<Node> closedSet = new ArrayList<>();
        ArrayList<Node> openSet = new ArrayList<>();
        ArrayList<Node> currentPath = new ArrayList();

        openSet.add(start);

        start.setG(0.0);
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
            System.out.println(current.getF());

            if(current.equals(goal)){
                System.out.println("done");

                currentPath.clear();
                Node temp = current;
                while(temp.getCameFrom() != null){
                    currentPath.add(temp.getCameFrom());
                    Node finalTemp = temp;
                    Platform.runLater(() -> {
                        drawPath(finalTemp, finalTemp.getCameFrom());
                    });
                    temp.setNodeColour(Color.BLUE);
                    temp = temp.getCameFrom();
                }
                startNode.setNodeColour(Color.BLUE);

                Platform.runLater(() -> {
                    drawNodes();
                });
                return;
                // return path
            }

            openSet.remove(current);
            closedSet.add(current);
            current.setNodeColour(Color.RED);

            ArrayList<Node> neighbours = current.getNeighbours();

            for(Node neighbour : neighbours){
                if(!neighbour.isObstacle()){
                    if(!closedSet.contains(neighbour)){
                        if(!openSet.contains(neighbour)){
                            openSet.add(neighbour);
                            neighbour.setNodeColour(Color.ORANGE);
                        }

                        double temp_score = current.getG() + 1;

                        if (temp_score < neighbour.getG()){
                            neighbour.setCameFrom(current);
                            neighbour.setG(temp_score);
                            neighbour.calculateF(goal);
                        }
                    }
                }
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                drawNodes();
            });
        }
        System.out.println("Failure");
    }


    public void reset(){
        for(int i=0; i<rows; i+=1){
            for(int j=0; j<cols; j+=1){
                if(!grid[i][j].isObstacle()){
                    grid[i][j].setNodeColour(Color.WHITE);
                }
            }
        }
        Platform.runLater(() -> drawNodes());
    }
}
