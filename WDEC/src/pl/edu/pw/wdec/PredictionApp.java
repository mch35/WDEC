package pl.edu.pw.wdec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.wdec.model.Prediction;
import pl.edu.pw.wdec.model.PredictionsProvider;
import pl.edu.pw.wdec.util.PredictionUtils;
import pl.edu.pw.wdec.view.ChartsController;
import pl.edu.pw.wdec.view.PredictionDetailsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main app class. It's a bridge between particular controllers.
 * 
 * @author Michal Chilczuk
 *
 */
public class PredictionApp extends Application {
	final Logger logger = LoggerFactory.getLogger(PredictionApp.class);

	/** Main app window/stage */
	private Stage stage;
	/** Pane with controls on left and charts on the right */
	private BorderPane predictionDetails;
	/** Controller able to manage charts */
	private ChartsController chartsController;
	/** Main data provider */
	private PredictionsProvider predictionProvider;

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.predictionProvider = new PredictionsProviderImpl();
		initPredictionDetails();
		initCharts();

		stage.show();
	}

	private void initPredictionDetails() {
		try {
			FXMLLoader loader = PredictionUtils
					.getViewLoader("view/PredictionDetails.fxml");

			predictionDetails = loader.load();

			PredictionDetailsController controller = loader.getController();
			controller.setPredictionApp(this);

			Scene scene = new Scene(predictionDetails);
			stage.setScene(scene);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Platform.exit();
		}
	}

	private void initCharts() {
		try {
			FXMLLoader loader = PredictionUtils
					.getViewLoader("view/Charts.fxml");

			AnchorPane charts = loader.load();
			chartsController = loader.getController();

			predictionDetails.setRight(charts);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Platform.exit();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Calculates new predictions basing on given price and quality, 
	 * then populates it to charts.
	 * 
	 * @param price
	 * @param quality
	 */
	public void updatePredictions(Double price, Double quality) {
		predictionProvider.setPrice(price);
		predictionProvider.setQuality(quality);
		chartsController.setPredictionData(predictionProvider.getPredictions());
	}

	/**
	 * Returns prediction calculated before for given production level
	 * 
	 * @param production
	 * @return
	 */
	public Prediction getPrediction(Integer production)
	{
		Prediction prediction = predictionProvider.getPrediction(production);
		
		if(prediction == null)
		{
			prediction = new Prediction();
		}
		
		return prediction;
	}

	/**
	 * Mock provider
	 * 
	 * @author Michal Chilczuk
	 *
	 */
	private class PredictionsProviderImpl implements PredictionsProvider {
		private Map<Integer, Prediction> predictions;
		private Double price;
		private Double quality;
		
		public PredictionsProviderImpl()
		{
			List<Integer> productionLevels = new LinkedList<Integer>();

			for (int i = 0; i < 100; i += 5) {
				productionLevels.add(i);
			}
			
			setProductions(productionLevels);
		}

		private void setProductions(List<Integer> productions) {
			predictions = new HashMap<>();
			
			for (Integer production : productions) {
				Prediction prediction = new Prediction();
				prediction.setProduction(production);
				prediction.setProfit(0);
				prediction.setRisk(0);
				predictions.put(production, prediction);
			}
		}
		
		public final List<Prediction> getPredictions() {
			for (Prediction prediction : predictions.values()) {
				prediction.setRisk(getRisk(prediction.getProduction()));
				prediction.setProfit(getProfit(prediction.getProduction()));
			}

			return new LinkedList<Prediction>(predictions.values());
		}

		private Double getRisk(Integer production) {
			return price * production;
		}

		private Double getProfit(Integer production) {
			return quality * production;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public void setQuality(Double quality) {
			this.quality = quality;
		}

		public Prediction getPrediction(Integer production) {
			return predictions.get(production);
		}
	}
}
