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

	/**
	 * Returns particular prediction calculated before for given production level.
	 * 
	 * @param production
	 * @return
	 */
	public Prediction getPrediction(Integer production);

	/**
	 * Sets predicted demand.
	 * 
	 * @param demand
	 */
	public void setPredictedDemand(Integer demand);
}
