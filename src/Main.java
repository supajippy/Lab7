import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}


    static Stage stage= new Stage();
    BufferedReader entree;
    BorderPane bord;



    @Override
    public void start(Stage primaryStage) {

    primaryStage=Main.stage;
    BorderPane bord= creemenu();
    primaryStage.setTitle("Graphiques");
    primaryStage.setResizable(false);
    primaryStage.setHeight(500);
    primaryStage.setWidth(500);
    primaryStage.setScene(new Scene(bord));
    primaryStage.show();

    }

    public BorderPane creemenu(){



       bord=new BorderPane();

        MenuItem regions= new MenuItem("Regions");
        MenuItem lignes=new MenuItem("Lignes");
        MenuItem barres=new MenuItem("Barres");
        MenuItem png= new MenuItem("PNG");
        MenuItem gif= new MenuItem("GIF");

        Menu importer = new Menu("Importer");
        Menu exporter=new Menu("Exporter");

        png.setOnAction(event -> export("png"));
        gif.setOnAction(event -> export("gif"));

        lignes.setOnAction(event -> {
            FileChooser fc= new FileChooser();
            fc.setTitle("Veuillez sélectionner un fichier");
            File fichier = fc.showOpenDialog(Main.stage);
            List<String> donnees= null;

            try { entree = new BufferedReader(new FileReader(fichier)); }
            catch (FileNotFoundException e) { }
            catch (NullPointerException i){}

            try { donnees = Files.readAllLines(fichier.toPath()); }
            catch (IOException e) { }
            catch (NullPointerException i){}

            String[] donneesX= donnees.get(0).split(",");
            String[] donneesY= donnees.get(1).split(",");

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();

            xAxis.setLabel("Mois");
            yAxis.setLabel("Température");

            final LineChart<String,Number> root =
                    new LineChart<String,Number>(xAxis,yAxis);
            root.setTitle("Températures Moyennes");

            XYChart.Series series = new XYChart.Series();
            series.setName("Données");
            for(int i=0; i<donneesX.length;i++)
            {
                series.getData().add(new XYChart.Data<>(donneesX[i],Float.parseFloat(donneesY[i])));
            }
            root.getData().addAll(series);
            bord.setCenter(root);

        });
        barres.setOnAction(event -> {
            FileChooser fc= new FileChooser();
            fc.setTitle("Veuillez sélectionner un fichier");
            File fichier = fc.showOpenDialog(Main.stage);
            List<String> donnees= null;

            try { entree = new BufferedReader(new FileReader(fichier)); }
            catch (FileNotFoundException e) { }
            catch (NullPointerException i){}

            try { donnees = Files.readAllLines(fichier.toPath()); }
            catch (IOException e) { }
            catch (NullPointerException i){}

            String[] donneesX= donnees.get(0).split(",");
            String[] donneesY= donnees.get(1).split(",");

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();

            xAxis.setLabel("Mois");
            yAxis.setLabel("Température");

            final BarChart<String,Number> root =
                    new BarChart<>(xAxis,yAxis);
            root.setTitle("Températures Moyennes");

            XYChart.Series series = new XYChart.Series();
            series.setName("Données");
            for(int i=0; i<donneesX.length;i++)
            {
                series.getData().add(new XYChart.Data<>(donneesX[i],Float.parseFloat(donneesY[i])));
            }
            root.getData().addAll(series);
            bord.setCenter(root);

        });
        regions.setOnAction(event -> {
            FileChooser fc= new FileChooser();
            fc.setTitle("Veuillez sélectionner un fichier");
            File fichier = fc.showOpenDialog(Main.stage);
            List<String> donnees= null;

            try { entree = new BufferedReader(new FileReader(fichier)); }
            catch (FileNotFoundException e) { }
            catch (NullPointerException i){}

            try { donnees = Files.readAllLines(fichier.toPath()); }
            catch (IOException e) { }
            catch (NullPointerException i){}

            String[] donneesX= donnees.get(0).split(",");
            String[] donneesY= donnees.get(1).split(",");

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();

            xAxis.setLabel("Mois");
            yAxis.setLabel("Température");

            final AreaChart<String,Number> root =
                    new AreaChart<>(xAxis,yAxis);
            root.setTitle("Températures Moyennes");

            XYChart.Series series = new XYChart.Series();
            series.setName("Données");
            for(int i=0; i<donneesX.length;i++)
            {
                series.getData().add(new XYChart.Data<>(donneesX[i],Float.parseFloat(donneesY[i])));
            }
            root.getData().addAll(series);
            bord.setCenter(root);

        });


        importer.getItems().addAll(lignes,regions,barres);
        exporter.getItems().addAll(png,gif);

        MenuBar menuBar= new MenuBar(importer,exporter);


        bord.setTop(menuBar);

        return bord;
    }
    private void export(String format) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Enregistrer sous");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichier Image", "*." ));

        File fichier = fc.showSaveDialog(Main.stage);
        WritableImage image = bord.snapshot(new SnapshotParameters(), null);

        try { ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, fichier); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
