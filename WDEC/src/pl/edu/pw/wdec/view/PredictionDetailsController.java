package pl.edu.pw.wdec.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.wdec.PredictionApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PredictionDetailsController {
	final Logger logger = LoggerFactory.getLogger(PredictionDetailsController.class);
	@FXML
	private TextField priceField;
	@FXML
	private TextField qualityField;
	@FXML
	private Button computeButton;
	
	private PredictionApp predictionApp;
	
	@FXML
	private void initialize()
	{
		
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
			
			predictionApp.updatePredictionDetails(price, quality);
		}
		catch(NumberFormatException ex)
		{
			logger.info("Invalid number format. {}", ex.getMessage());
		}
	}
}
