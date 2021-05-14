package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.generators.DFSGenerator;
import maze.generators.MazeGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicsController implements Initializable {
    @FXML
    private Canvas mazeCanvas;

    private GraphicsContext graphicsContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = mazeCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());

        Maze maze = new Maze(4, (int)mazeCanvas.getWidth()/4);
        MazeGenerator mazeGenerator = new DFSGenerator(maze,graphicsContext);
        mazeGenerator.initMaze();
    }
}
