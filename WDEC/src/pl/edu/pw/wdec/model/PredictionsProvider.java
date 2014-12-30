package pl.edu.pw.wdec.model;

import java.util.List;

public interface PredictionsProvider {
	public List<Prediction> getPredictions(Double price, Double quality);
}
