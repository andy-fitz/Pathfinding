package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {

    private double width = 900;
    private double height = 900;

    private Node[][] grid = new Node[45][45];


    @FXML
    Pane environment;
    Canvas canvas = new Canvas();

    public void initialize(){
        canvas.setWidth(width);
        canvas.setHeight(height);
        environment.getChildren().add(canvas);
        draw();
        drawNodes();
    }

    public void draw(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.GREEN);
        g.fillRect(0,0,width,height);
    }

    public void drawNodes(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);

        for(int i=0; i<width; i+=20){
            for(int j=0; j<height; j+=20){
                g.fillOval(i+5,j+5,10,10);
                grid[i/20][j/20] = new Node(i+10,j+10);
                System.out.println(grid[i/20][j/20].toString());
            }
        }


    }


}
