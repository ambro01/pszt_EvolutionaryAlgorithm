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
import model.Gen;
import model.Individual;
import model.MainRunnable;
import model.SimulationMiLambda;

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
	Spinner<Double> sigma_minSpinner11;
	@FXML
	Spinner<Integer> iterationsSpinner11;
	@FXML
	Spinner<Double> c1Spinner11;
	@FXML
	Spinner<Double> c2Spinner11;
	@FXML
	Spinner<Integer> miSpinnerMiLambda;
	@FXML
	Spinner<Integer> lambdaSpinnerMiLambda;
	@FXML
	Spinner<Double> sigmaSpinnerMiLambda;
	@FXML
	Spinner<Integer> iterationsSpinnerMiLambda;
	@FXML
	LineChart<Integer, Double> fitnessChart;
	
	ToggleGroup algorithmSelect;
	
	public void addTextLine(String text) {
		populationTA.setText(populationTA.getText() + "\n" + text);
	}
	
	public void clearArea() {
		populationTA.clear();
	}
	
	public void addPointToChart(double value) {
		int x = fitnessChart.getData().get(0).getData().size();
		fitnessChart.getData().get(0).getData().add(new Data<Integer, Double>(x, value));
	}

	public void clearChart() {
		fitnessChart.getData().get(0).getData().clear();
	}
	
	public void startButtonClicked(Event event) {
		clearArea();
		clearChart();
		//addPointToChart(new Random().nextGaussian());	// only for testing chart
		if (algorithmSelect.getSelectedToggle() == algorithm11) {
			addTextLine("Wybrano algorytm równoleg³y 1+1");
			addTextLine("");
			
			MainRunnable runnable = new MainRunnable(nSpinner.getValue(), sigmaSpinner11.getValue(), sigma_minSpinner11.getValue(),
					iterationsSpinner11.getValue(), mSpinner11.getValue(), c1Spinner11.getValue(), c2Spinner11.getValue(), threadsNumSpinner11.getValue());
			runnable.run();
			
			addTextLine("Znalezione minimum globalne to wartoœæ: " + String.valueOf(runnable.getMyRunnable().selectBest().getFinalFunctionValue()));
			addTextLine("");
			addTextLine("Wartoœci zmiennych:");
			for(Gen gen : runnable.getMyRunnable().selectBest().getGens()){
				addTextLine(String.valueOf(gen.getX()));
			}
			
			for(int i = 0; i < runnable.getMyRunnable().getResults().length; ++i){
				addPointToChart(runnable.getMyRunnable().getResults()[i]);
			}
		}
		
		if (algorithmSelect.getSelectedToggle() == algorithmMiLambda) {
			addTextLine("Wybrano algorytm Mi Lambda");
			
			SimulationMiLambda simulation = new SimulationMiLambda(nSpinner.getValue(), sigmaSpinnerMiLambda.getValue(), lambdaSpinnerMiLambda.getValue(), miSpinnerMiLambda.getValue(), iterationsSpinnerMiLambda.getValue());
			simulation.runSimulation();
			
			addTextLine("Znalezione minimum globalne to wartoœæ: " + String.valueOf(simulation.selectBest().getFinalFunctionValue()));
			addTextLine("");
			addTextLine("Wartoœci zmiennych:");
			for(Gen gen : simulation.selectBest().getGens()){
				addTextLine(String.valueOf(gen.getX()));
			}
			
			for(int i = 0; i < simulation.getResults().length; ++i){
				addPointToChart(simulation.getResults()[i]);
			}
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		delayTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		nSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		threadsNumSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 100));
		mSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
		iterationsSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1000));
		sigmaSpinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1, 0.01));
		sigma_minSpinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0.00001, 0.00001));
		c1Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 0.82, 0.01));
		c2Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1.2, 0.01));
		miSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10));
		lambdaSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 70));
		sigmaSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1, 0.01));
		iterationsSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1000));
		algorithmSelect = new ToggleGroup();
		algorithm11.setToggleGroup(algorithmSelect);
		algorithmMiLambda.setToggleGroup(algorithmSelect);
		algorithmSelect.selectToggle(algorithm11);
		fitnessChart.getData().add(new Series<Integer, Double>());
	}
}
