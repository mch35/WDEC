package pl.edu.pw.wdec.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Contains particular prediction for given production level.
 * 
 * @author Michal Chilczuk
 *
 */
public class Prediction {
	/** Production level for which prediction is calculated. */
	private IntegerProperty production;
	/** Risk of prediction */
	private DoubleProperty risk;
	/** Profit of prediction */
	private DoubleProperty profit;
	/** Variable cost for this prediction */
	private DoubleProperty unitCost;
	/** Income for this prediction */
	private DoubleProperty income;
	/** Cost of this prediction */
	private DoubleProperty cost;
	
	public Prediction()
	{
		this.production = new SimpleIntegerProperty();
		this.risk = new SimpleDoubleProperty();
		this.profit = new SimpleDoubleProperty();
		this.unitCost = new SimpleDoubleProperty();
		this.income = new SimpleDoubleProperty();
		this.cost = new SimpleDoubleProperty();
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

	public final DoubleProperty unitCostProperty() {
		return this.unitCost;
	}

	public final double getUnitCost() {
		return this.unitCostProperty().get();
	}

	public final void setUnitCost(final double variableCost) {
		this.unitCostProperty().set(variableCost);
	}

	public final DoubleProperty incomeProperty() {
		return this.income;
	}

	public final double getIncome() {
		return this.incomeProperty().get();
	}

	public final void setIncome(final double income) {
		this.incomeProperty().set(income);
	}

	public final DoubleProperty costProperty() {
		return this.cost;
	}

	public final double getCost() {
		return this.costProperty().get();
	}

	public final void setCost(final double cost) {
		this.costProperty().set(cost);
	}
}
