package controllers;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import maze.Maze;
import maze.generators.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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

    @FXML
    private Button solveButton;

    @FXML
    private Button exportButton;

    @FXML
    private ChoiceBox<String> solveChoice;

    private double basicPause = 0.5;
    private GraphicsContext graphicsContext;

    public static Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> algorithms = FXCollections.observableArrayList("DFS","Prim","Kruskal","Wilson","BinaryTree");
        algorithmChoice.setValue("DFS");
        algorithmChoice.setItems(algorithms);

        ObservableList<String> solvers = FXCollections.observableArrayList("DFS","Dijkstra");
        solveChoice.setValue("DFS");
        solveChoice.setItems(solvers);

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

        int mazeSize = (int)gridSizeSlider.getValue();
        if(mazeCanvas.getWidth() % mazeSize != 0)
        {
            while(true)
            {
                mazeSize += 1;
                if(mazeCanvas.getWidth() % mazeSize == 0)
                {
                    break;
                }
            }
        }

        Maze maze = new Maze(mazeSize, (int)mazeCanvas.getWidth()/mazeSize, graphicsContext);
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

    @FXML
    private void saveAction() {
        WritableImage screenshot = mazeCanvas.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File toSaveFile = fileChooser.showSaveDialog(new Stage());

        try {
            if (toSaveFile != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", toSaveFile);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
