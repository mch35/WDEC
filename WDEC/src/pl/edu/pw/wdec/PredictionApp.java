package pl.edu.pw.wdec;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PredictionApp extends Application {

	private Stage stage;
	private BorderPane predictionDetails;
	
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		
		initPredictionDetails();
		initCharts();
	}

	private void initPredictionDetails() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PredictionApp.class.getResource("view/PredictionDetails.fxml"));

			predictionDetails = loader.load();
			Scene scene = new Scene(predictionDetails);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initCharts() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PredictionApp.class.getResource("view/Charts.fxml"));

			AnchorPane charts = loader.load();			
			predictionDetails.setRight(charts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
