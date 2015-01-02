package pl.edu.wdec.util;

import pl.edu.pw.wdec.PredictionApp;
import javafx.fxml.FXMLLoader;


/**
 * Utils for PredictionApp
 * 
 * @author Michal Chilczuk
 *
 */
public class PredictionUtils
{
	public static FXMLLoader getViewLoader(String path)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PredictionApp.class.getResource(path));
		
		return loader;
	}

}
