package pl.edu.pw.wdec.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.wdec.PredictionApp;
import pl.edu.pw.wdec.model.Prediction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller which handles left bar fields and reacts for changes.
 * 
 * @author Michal Chilczuk
 *
 */
public class PredictionDetailsController {
	final Logger logger = LoggerFactory.getLogger(PredictionDetailsController.class);
	@FXML
	private TextField priceField;
	@FXML
	private TextField qualityField;
	@FXML
	private TextField productionField;
	@FXML
	private Button computeButton;
	@FXML	
	private Button showButton;
	@FXML	
	private Label unitCostLabel;
	@FXML	
	private Label costLabel;
	@FXML	
	private Label incomeLabel;
	@FXML	
	private Label profitLabel;
	@FXML	
	private Label riskLabel;
	
	private PredictionApp predictionApp;
	
	@FXML
	private void initialize()
	{
		setLabels(new Prediction());
	}

	public PredictionApp getPredictionApp() {
		return predictionApp;
	}

	public void setPredictionApp(PredictionApp predictionApp) {
		this.predictionApp = predictionApp;
	}

	@FXML
	private void handleComputeButtonAction()
	{
		try
		{
			Double price = Double.valueOf(priceField.getText());
			Double quality = Double.valueOf(qualityField.getText());
			
			predictionApp.updatePredictions(price, quality);
		}
		catch(NumberFormatException e)
		{
			logger.info("Invalid number format. {}", e.getMessage());
		}
	}
	
	@FXML
	private void handleShowButtonAction()
	{
		try
		{
			Integer production = Integer.valueOf(productionField.getText());
			Prediction prediction = predictionApp.getPrediction(production);
			setLabels(prediction);
		}
		catch(NumberFormatException e)
		{
			logger.info("Invalid number format. {}", e.getMessage());			
		}
	}

	private void setLabels(Prediction prediction) {
		unitCostLabel.setText(String.format("%.2f", prediction.getUnitCost()));
		costLabel.setText(String.format("%.2f", prediction.getCost()));
		incomeLabel.setText(String.format("%.2f", prediction.getIncome()));
		profitLabel.setText(String.format("%.2f", prediction.getProfit()));
		riskLabel.setText(String.format("%.2f", prediction.getRisk()));
	}
}
