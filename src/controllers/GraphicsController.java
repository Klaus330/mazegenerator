package controllers;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.generators.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphicsController implements Initializable {
    public static Timeline timeline;

    @FXML
    private Canvas mazeCanvas;

    @FXML
    private ChoiceBox<String> algorithmChoice;

    @FXML
    private Slider gridSizeSlider;

    private GraphicsContext graphicsContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> algorithms = FXCollections.observableArrayList("DFS","Prim","Kruskal","Wilson");
        algorithmChoice.setValue("DFS");
        algorithmChoice.setItems(algorithms);

        graphicsContext = mazeCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());

    }

    @FXML
    private void generateMaze()
    {
        graphicsContext.clearRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());

        Maze maze = new Maze((int)gridSizeSlider.getValue(), (int)mazeCanvas.getWidth()/(int)gridSizeSlider.getValue(), graphicsContext);
        MazeGenerator mazeGenerator = switch (algorithmChoice.getValue()) {
            case "DFS" -> new DFSGenerator(maze, graphicsContext);
            case "Prim" -> new PrimGenerator(maze, graphicsContext);
            case "Kruskal" -> new KruskalGenerator(maze, graphicsContext);
            case "Wilson" -> new WilsonGenerator(maze, graphicsContext);
            default -> throw new IllegalStateException("Unexpected value: " + algorithmChoice.getValue());
        };
        mazeGenerator.generate();
    }
}
