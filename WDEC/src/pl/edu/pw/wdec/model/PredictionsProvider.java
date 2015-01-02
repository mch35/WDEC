package pl.edu.pw.wdec.model;

import java.util.List;

/**
 * Objects that calculate and provide prediction data have to implement this interface.
 * 
 * @author Michal Chilczuk
 *
 */
public interface PredictionsProvider
{	
	
	/**
	 * Sets production levels for which predictions will be calculated.
	 * 
	 * @param productions List of production levels
	 */
	public void setProductions(final List<Integer> productions);
	
	/**
	 * Sets price for which predictions have to be calculated.
	 * 
	 * @param price
	 */
	public void setPrice(final Double price);
	
	/**
	 * Sets quality for which predictions have to be calculated.
	 * 
	 * @param quality
	 */
	public void setQuality(final Double quality);
	
	/**
	 * Returns predictions for currently set price and quality.
	 * 
	 * @return List of predictions.
	 */
	public List<Prediction> getPredictions();
}
