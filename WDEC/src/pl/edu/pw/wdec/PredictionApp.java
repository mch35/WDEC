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
	public void updatePredictions(Double price, Double quality, Integer demand) {
		predictionProvider.setPrice(price);
		predictionProvider.setQuality(quality);
		predictionProvider.setPredictedDemand(demand);
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
		private Double price = 0.0;
		private Double quality = 0.0;
		private Integer demand = 0;
		
		public PredictionsProviderImpl()
		{
			List<Integer> productionLevels = new LinkedList<Integer>();

			for (int i = 1; i < 400000; i += 10000) {
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
				calculatePrediction(prediction);
			}

			return new LinkedList<Prediction>(predictions.values());
		}

		private void calculatePrediction(Prediction prediction) {
			Double unitCost = computeUnitCost(prediction.getProduction(), quality);
			prediction.setUnitCost(unitCost);
			
			Integer salePrediction = getSalePrediction(price, quality, demand); // maksymalny popyt w sztukach
			
			prediction.setRisk(getRisk(prediction.getProduction(), salePrediction));
			Integer sold = (int) Math.round(prediction.getRisk() * prediction.getProduction());
			
			prediction.setCost(getCost(prediction.getProduction(), unitCost));
			prediction.setIncome(getIncome(sold, price));
			prediction.setProfit(prediction.getIncome() - prediction.getCost());
		}

		private double getIncome(Integer sold, Double price) {
			return price * sold;
		}

		private double getCost(int production, Double unitCost) {
			return unitCost * production + 10000;
		}

		private Integer getSalePrediction(Double price, Double quality, Integer demand) {			
			int salePrediction = (int) (Math.round((1 - getPriceFactor(price) + getQualityFactor(quality)) * demand));
			if(salePrediction < 0)
				salePrediction = 0;
			if(salePrediction > demand)
				salePrediction = demand;
			
			return salePrediction;
		}
		
		private double getQualityFactor(Double quality) {
			//return -9.544E-12*quality*quality*quality*quality*quality*quality + 0.000000003647*quality*quality*quality*quality*quality - 0.0000005506*quality*quality*quality*quality + 0.00004183*quality*quality*quality - 0.001678*quality*quality + 0.033*quality - 0.15;
			return 7.932E-7*quality*quality*quality - 1.943E-4*quality*quality + 0.019*quality - 0.207;
		}

		private Double getPriceFactor(Double price)
		{
			//return 0.0000003584*price*price*price*price*price - 0.00003342*price*price*price*price + 0.001167*price*price*price - 0.018*price*price + 0.121*price - 0.017;
			return 1.135E-3*price*price + 0.035*price + 0.1;
		}

		private Double getRisk(Integer production, Integer salePrediction) {
			if(production == 0)
				return 1.0;
			
			if(salePrediction > production)
				return 1.0;
			
			Double result = (double) (salePrediction / production);
			
			
						
			return result;
		}
			
		private Double computeUnitCost(Integer production, Double quality){
			/*return -1.353e-28*(Math.pow(production, 5)) + 4.755e-22*(Math.pow(production, 4)) - 4.408e-16*(Math.pow(production, 3)) + 1.588e-10*(Math.pow(production, 2)) - 1.518e-5*production 
						+ 1.474e-5*(Math.pow(quality, 3))- 1.87e-3*(Math.pow(quality, 2))+ 0.103*quality + 6.706;
			*/
			return 3.656E-34*(Math.pow(production, 6)) - 4.718E-28*(Math.pow(production, 5)) + 5.741E-22*(Math.pow(production, 4)) - 4.486E-16*(Math.pow(production, 3)) + 0.000000000158*(Math.pow(production, 2)) - 0.00001507*production + 6.352+1.428E-12*(Math.pow(quality, 6)) - 0.0000000003434*(Math.pow(quality, 5)) + 0.0000001044*(Math.pow(quality, 4)) - 0.000002273*(Math.pow(quality, 3)) - 0.0007455*(Math.pow(quality, 2)) + 0.075*quality - 0.074;
		}
		
		public void setQuality(Double quality) {
			this.quality = quality;
		}

		public Prediction getPrediction(Integer production) {
			Prediction prediction = predictions.get(production);
			
			if(prediction == null)
			{
				prediction = new Prediction();
				prediction.setProduction(production);
				
				calculatePrediction(prediction);

				predictions.put(production, prediction);
			}
			
			return prediction;
		}

		@Override
		public void setPrice(Double price) {
			this.price = price;
		}

		@Override
		public void setPredictedDemand(Integer demand) {
			this.demand = demand;
		}
	}
}
