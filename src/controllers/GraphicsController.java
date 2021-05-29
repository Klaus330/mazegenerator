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
    @FXML
    private Canvas mazeCanvas;

    @FXML
    private ChoiceBox<String> algorithmChoice;

    @FXML
    private Slider gridSizeSlider;

    @FXML
    private Slider speedSlider;

    private double basicPause = 0.5;
    private GraphicsContext graphicsContext;

    public static Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> algorithms = FXCollections.observableArrayList("DFS","Prim","Kruskal","Wilson","BinaryTree");
        algorithmChoice.setValue("DFS");
        algorithmChoice.setItems(algorithms);

        graphicsContext = mazeCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(204,204,204));
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());
        timeline = new Timeline();
    }

    @FXML
    private void generateMaze()
    {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());

        Maze maze = new Maze((int)gridSizeSlider.getValue(), (int)mazeCanvas.getWidth()/(int)gridSizeSlider.getValue(), graphicsContext);
        MazeGenerator mazeGenerator = switch (algorithmChoice.getValue()) {
            case "DFS" -> new DFSGenerator(maze, graphicsContext);
            case "Prim" -> new PrimGenerator(maze, graphicsContext);
            case "Kruskal" -> new KruskalGenerator(maze, graphicsContext);
            case "Wilson" -> new WilsonGenerator(maze, graphicsContext);
            case "BinaryTree" -> new BinaryTreeGenerator(maze, graphicsContext);
            default -> throw new IllegalStateException("Unexpected value: " + algorithmChoice.getValue());
        };
        mazeGenerator.setPause(basicPause/speedSlider.getValue());
        mazeGenerator.generate();
    }
}
