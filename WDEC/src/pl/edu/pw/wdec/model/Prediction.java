package pl.edu.pw.wdec.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Prediction {
	private IntegerProperty production;
	private DoubleProperty risk;
	private DoubleProperty profit;
	
	public Prediction()
	{
		this.production = new SimpleIntegerProperty();
		this.risk = new SimpleDoubleProperty();
		this.profit = new SimpleDoubleProperty();
	}

	public final IntegerProperty productionProperty() {
		return this.production;
	}

	public final int getProduction() {
		return this.productionProperty().get();
	}

	public final void setProduction(final int production) {
		this.productionProperty().set(production);
	}

	public final DoubleProperty riskProperty() {
		return this.risk;
	}

	public final double getRisk() {
		return this.riskProperty().get();
	}

	public final void setRisk(final double risk) {
		this.riskProperty().set(risk);
	}

	public final DoubleProperty profitProperty() {
		return this.profit;
	}

	public final double getProfit() {
		return this.profitProperty().get();
	}

	public final void setProfit(final double profit) {
		this.profitProperty().set(profit);
	}
}
