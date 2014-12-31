package pl.edu.pw.wdec.model;

import java.util.List;

public interface PredictionsProvider
{	
	public void setProductions(final List<Integer> productions);
	
	public void setPrice(final Double price);
	public void setQuality(final Double quality);
	
	public List<Prediction> getPredictions();
}
