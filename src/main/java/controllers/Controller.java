package controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class Controller implements Initializable{
	@FXML 
	TextArea populationTA;
	@FXML
	RadioButton algorithm11;
	@FXML
	RadioButton algorithmMiLambda;
	@FXML 
	Spinner<Integer> delayTimeSpinner;
	@FXML
	Spinner<Integer> nSpinner;
	@FXML
	Spinner<Integer> threadsNumSpinner11;
	@FXML
	Spinner<Integer> mSpinner11;
	@FXML
	Spinner<Double> sigmaSpinner11;
	@FXML
	Spinner<Double> c1Spinner11;
	@FXML
	Spinner<Double> c2Spinner11;
	@FXML
	Spinner<Integer> miSpinnerMiLambda;
	@FXML
	Spinner<Integer> lambdaSpinnerMiLambda;
	@FXML
	LineChart<Integer, Double> fitnessChart;
	
	ToggleGroup algorithmSelect;
	
	public void addTextLine(String text) {
		populationTA.setText(populationTA.getText() + "\n" + text);
	}
	
	public void addPointToChart(double value) {
		int x = fitnessChart.getData().get(0).getData().size();
		fitnessChart.getData().get(0).getData().add(new Data<Integer, Double>(x, value));
	}

	public void clearChart() {
		fitnessChart.getData().get(0).getData().clear();
	}
	
	public void startButtonClicked(Event event) {
		addPointToChart(new Random().nextGaussian());	// only for testing chart
		if (algorithmSelect.getSelectedToggle() == algorithm11) {
			addTextLine("Wybrany Algorytm to równoleg³y 11");
		}
		
		if (algorithmSelect.getSelectedToggle() == algorithmMiLambda) {
			addTextLine("Wybrany algorytm to Mi Lambda");
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		delayTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		nSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		threadsNumSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		mSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		sigmaSpinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1, 0.01));
		c1Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 0.82, 0.01));
		c2Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1.2, 0.01));
		miSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		lambdaSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		algorithmSelect = new ToggleGroup();
		algorithm11.setToggleGroup(algorithmSelect);
		algorithmMiLambda.setToggleGroup(algorithmSelect);
		algorithmSelect.selectToggle(algorithm11);
		fitnessChart.getData().add(new Series<Integer, Double>());
	}
}
