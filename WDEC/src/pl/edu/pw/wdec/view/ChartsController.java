package pl.edu.pw.wdec.view;

import java.util.List;

import pl.edu.pw.wdec.model.Prediction;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class ChartsController {
	@FXML
	private LineChart<Integer, Double> riskChart;
	@FXML
	private CategoryAxis xRiskAxis;	
	@FXML
	private CategoryAxis xProfitAxis;
	@FXML
	private LineChart<Integer, Double> profitChart;
	
	@FXML
	private void initialize()
	{
		
	}
	
	public void setPredictionData(List<Prediction> predictions)
	{
		Series<Integer, Double> riskData = new Series<>();
		Series<Integer, Double> profitData = new Series<>();

		riskChart.getData().clear();
		profitChart.getData().clear();
		
		for(Prediction prediction : predictions)
		{
			riskData.getData().add(new Data<>(prediction.getProduction(), prediction.getRisk()));
			profitData.getData().add(new Data<>(prediction.getProduction(), prediction.getProfit()));
		}
		
		riskChart.getData().add(riskData);
		profitChart.getData().add(profitData);
	}
}
