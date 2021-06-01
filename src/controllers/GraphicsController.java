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
import maze.solvers.DFSSolver;
import maze.solvers.DijkstraSolver;
import maze.solvers.Solver;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GraphicsController implements Initializable {
    private static final double BASIC_PAUSE = 0.5;
    static public Maze maze;
    static public boolean isSolving = false;
    static public boolean isSolved = false;
    static public boolean isGenerating = false;
    static public boolean isGenerated = false;
    public static GraphicsContext graphicsContext;
    public static Timeline timeline;

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
    private Button pngButton;
    @FXML
    private Button serialButton;
    @FXML
    private Button importButton;
    @FXML
    private ChoiceBox<String> solveChoice;
    @FXML
    private Button resetButton;

    private Solver solver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> algorithms = FXCollections.observableArrayList("DFS", "Prim", "Kruskal", "Wilson", "BinaryTree");
        algorithmChoice.setValue("DFS");
        algorithmChoice.setItems(algorithms);

        ObservableList<String> solvers = FXCollections.observableArrayList("DFS", "Dijkstra");
        solveChoice.setValue("DFS");
        solveChoice.setItems(solvers);

        graphicsContext = mazeCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(204, 204, 204));
        graphicsContext.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());
        timeline = new Timeline();
    }

    private int calculateMazeSize() {
        int mazeSize = (int) gridSizeSlider.getValue();
        if (mazeCanvas.getWidth() % mazeSize != 0) {
            while (true) {
                mazeSize += 1;
                if (mazeCanvas.getWidth() % mazeSize == 0) {
                    break;
                }
            }
        }
        return mazeSize;
    }

    @FXML
    private void generateMaze() {
        if (!isSolving) {
            int mazeSize = calculateMazeSize();
            maze = new Maze(mazeSize, (int) mazeCanvas.getWidth() / mazeSize);
            MazeGenerator mazeGenerator = switch (algorithmChoice.getValue()) {
                case "DFS" -> new DFSGenerator(maze);
                case "Prim" -> new PrimGenerator(maze);
                case "Kruskal" -> new KruskalGenerator(maze);
                case "Wilson" -> new WilsonGenerator(maze);
                case "BinaryTree" -> new BinaryTreeGenerator(maze);
                default -> throw new IllegalStateException("Unexpected value: " + algorithmChoice.getValue());
            };
            mazeGenerator.setPause(BASIC_PAUSE / speedSlider.getValue());

            isGenerating = true;
            isGenerated = false;

            mazeGenerator.generate();
        }
    }

    @FXML
    private void solveMaze() {
        if (isGenerated && !isSolving && !isSolved) {
            Solver solver = switch (solveChoice.getValue()) {
                case "DFS" -> new DFSSolver(maze);
                case "Dijkstra" -> new DijkstraSolver(maze);
                default -> throw new IllegalStateException("Unexpected value: " + solveChoice.getValue());
            };
            solver.setPause(BASIC_PAUSE / speedSlider.getValue());

            isSolving = true;
            isSolved = false;
            solver.solve();
        }
    }

    @FXML
    private void resetSolver() {
        if (maze != null && !isGenerating) {
            if (timeline != null && isSolving) {
                timeline.stop();
                isSolving = false;
            }
            maze.unSolve();
            maze.showMaze();
            isSolved = false;
        }
    }

    @FXML
    private void savePNG() {
        WritableImage screenshot = mazeCanvas.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File toSaveFile = fileChooser.showSaveDialog(new Stage());
        if (toSaveFile != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", toSaveFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    private void saveMaze() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SER Files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File toSaveFile = fileChooser.showSaveDialog(new Stage());
        if (toSaveFile != null && maze != null) {
            try (FileOutputStream saveFileStream = new FileOutputStream(toSaveFile);
                 ObjectOutputStream outStream = new ObjectOutputStream(saveFileStream)) {
                maze.unSolve();
                outStream.writeObject(maze);
                outStream.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    private void importMaze() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SER Files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File toLoadFile = fileChooser.showOpenDialog(new Stage());
        if (toLoadFile != null) {
            try (FileInputStream loadFileStream = new FileInputStream(toLoadFile);
                 ObjectInputStream inStream = new ObjectInputStream(loadFileStream)) {

                maze = (Maze) inStream.readObject();
                maze.showMaze();
                isSolved = false;
                isGenerated = true;

            } catch (ClassNotFoundException classException) {
                System.err.println("Can't load the catalog class!");
                classException.printStackTrace();
            } catch (IOException ioException) {
                System.err.println("Can't read from the file!");
                ioException.printStackTrace();
            }
        }
    }
}
