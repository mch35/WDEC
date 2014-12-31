package pl.edu.pw.wdec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.wdec.model.Prediction;
import pl.edu.pw.wdec.model.PredictionsProvider;
import pl.edu.pw.wdec.view.ChartsController;
import pl.edu.pw.wdec.view.PredictionDetailsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PredictionApp extends Application {
	final Logger logger = LoggerFactory.getLogger(PredictionApp.class);
	
	private Stage stage;
	private BorderPane predictionDetails;
	private ChartsController chartsController;
	
	private PredictionsProvider predictionProvider;
	
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.predictionProvider = new PredictionsProviderImpl();
		initPredictionDetails();
		initCharts();
	}

	private void initPredictionDetails() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PredictionApp.class.getResource("view/PredictionDetails.fxml"));

			predictionDetails = loader.load();
			PredictionDetailsController controller = loader.getController();
			controller.setPredictionApp(this);
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
			chartsController = loader.getController();
			predictionDetails.setRight(charts);
			predictionProvider.setProductions(getDefaultProductionLevels());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<Integer> getDefaultProductionLevels() {
		List<Integer> productionLevels = new LinkedList<Integer>();
		
		for(int i = 0; i < 100; i+=5)
		{
			productionLevels.add(i);
		}
		
		return productionLevels;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void updatePredictionDetails(Double price, Double quality)
	{
		predictionProvider.setPrice(price);
		predictionProvider.setQuality(quality);
		List<Prediction> predictions = predictionProvider.getPredictions();
		chartsController.setPredictionData(predictions);
	}
	
	private class PredictionsProviderImpl implements PredictionsProvider
	{
		private ObservableList<Prediction> predictions;		
		private Double price;		
		private Double quality;
		
		public List<Prediction> getPredictions() {
			for(Prediction prediction : predictions)
			{
				prediction.setRisk(getRisk(prediction.getProduction()));
				prediction.setProfit(getProfit(prediction.getProduction()));
			}
			
			return predictions;
		}
		
		private Double getRisk(Integer production)
		{
			return price*production;
		}
		
		private Double getProfit(Integer production)
		{
			return quality*production;
		}

		@Override
		public void setProductions(List<Integer> productions) {
			predictions = FXCollections.observableArrayList();
			
			for(Integer production : productions)
			{
				Prediction prediction = new Prediction();
				prediction.setProduction(production);
				prediction.setProfit(0);
				prediction.setRisk(0);
				predictions.add(prediction);
			}
		}

		@Override
		public void setPrice(Double price) {
			this.price = price;
		}

		@Override
		public void setQuality(Double quality) {
			this.quality = quality;
		}
	}
}
