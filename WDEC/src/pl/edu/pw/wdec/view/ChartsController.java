package pl.edu.pw.wdec.view;

import java.util.List;

import pl.edu.pw.wdec.model.Prediction;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

/**
 * Controls charts in prediction app.
 * 
 * @author Michal Chilczuk
 *
 */
public class ChartsController {
	@FXML
	private LineChart<Integer, Double> riskChart;
	@FXML
	private LineChart<Integer, Double> profitChart;
	
	@FXML
	private void initialize()
	{
		
	}
	
	/**
	 * Sets prediction data for which charts have to be generated.
	 * 
	 * @param predictions List of prediction data
	 */
	public void setPredictionData(List<Prediction> predictions)
	{
		Series<Integer, Double> riskSeries = new Series<>();
		Series<Integer, Double> profitSeries = new Series<>();

		riskChart.getData().clear();
		profitChart.getData().clear();
		
		for(Prediction prediction : predictions)
		{
			riskSeries.getData().add(new Data<>(prediction.getProduction(), prediction.getRisk()));
			profitSeries.getData().add(new Data<>(prediction.getProduction(), prediction.getProfit()));
		}
		
		riskChart.getData().add(riskSeries);
		profitChart.getData().add(profitSeries);
	}
}
