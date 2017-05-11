package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Function;
import model.FunctionF1;
import model.Gen;
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
	Spinner<Integer> nSpinner;
	@FXML
	Spinner<Integer> threadsNumSpinner11;
	@FXML
	Spinner<Integer> mSpinner11;
	@FXML
	Spinner<Double> sigmaSpinner11;
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
	LineChart<Number, Number> fitnessChart;
	@FXML
	ToggleGroup algorithmSelect;
	@FXML
	TextField minSigma11;
	
	public void addTextLine(String text) {
		populationTA.setText(populationTA.getText() + "\n" + text);
	}
	
	public void clearArea() {
		populationTA.clear();
	}
	
	public void addPointToChart(double value) {
		int x = fitnessChart.getData().get(0).getData().size();
		fitnessChart.getData().get(0).getData().add(new Data<Number, Number>(x, value));
	}

	public void clearChart() {
		fitnessChart.getData().get(0).getData().clear();
	}
	
	public void startButtonClicked(Event event) {
		clearArea();
		clearChart();
		DecimalFormat df = new DecimalFormat("0.###E0");
		double min = -1;
		double max = 0;
		
		if (algorithmSelect.getSelectedToggle() == algorithm11) {
			addTextLine("Wybrano algorytm równoleg³y 1+1");
			addTextLine("");
			Function function = new FunctionF1(nSpinner.getValue(), -20, 20);
			double tempSigmaMin = 0;
			try{
				tempSigmaMin = Double.parseDouble(minSigma11.getText());
			} catch (NumberFormatException e){
				e.printStackTrace(); //prints error
				minSigma11.setText("0");
			};
					
			MainRunnable runnable = new MainRunnable(function, sigmaSpinner11.getValue(), tempSigmaMin,
					iterationsSpinner11.getValue(), mSpinner11.getValue(), c1Spinner11.getValue(), c2Spinner11.getValue(), threadsNumSpinner11.getValue());
			runnable.run();
			
			addTextLine("Znalezione minimum globalne to wartoœæ: " + df.format(runnable.getMyRunnable().getBestIndividual().getFinalFunctionValue()));
			
			addTextLine("Wartoœæ <sigma> dla najlepszego rozwi¹zania: " + df.format(runnable.getMyRunnable().getSigma()));
			addTextLine("");
			addTextLine("Wartoœci zmiennych:");
			int k = 0;
			for(Gen gen : runnable.getMyRunnable().getBestIndividual().getGens()){
				++k;
				addTextLine(k + ". " + df.format(gen.getX()));
			}
			
			double temp = 0;
			for(int i = 0; i < runnable.getMyRunnable().getKIterations(); ++i){
				if(runnable.getMyRunnable().getResults()[i] != 0){
					temp = Math.log10(runnable.getMyRunnable().getResults()[i]);
					addPointToChart(temp);
					if (temp > max)
						max = temp;
					if (temp < min)
						min = temp;
				} else {
					addPointToChart(min);
				}
			}
		}
		
		if (algorithmSelect.getSelectedToggle() == algorithmMiLambda) {
			addTextLine("Wybrano algorytm Mi Lambda");
			Function function = new FunctionF1(nSpinner.getValue(), -20.0, 20.0);
			SimulationMiLambda simulation = new SimulationMiLambda(function, sigmaSpinnerMiLambda.getValue(), lambdaSpinnerMiLambda.getValue(), miSpinnerMiLambda.getValue(), iterationsSpinnerMiLambda.getValue());
			simulation.runSimulation();
			
			addTextLine("Znalezione minimum globalne to wartoœæ: " + df.format(simulation.selectBest().getFinalFunctionValue()));
			addTextLine("");
			addTextLine("Wartoœci zmiennych:");
			int k = 0;
			for(Gen gen : simulation.selectBest().getGens()){
				++k;
				addTextLine(k + ". " + df.format(gen.getX()));
			}
			
			double temp = 0;
			for(int i = 0; i < simulation.getKIeterations(); ++i){
				if(simulation.getResults()[i] != 0){
					temp = Math.log10(simulation.getResults()[i]);
					addPointToChart(temp);
					if (temp > max)
						max = temp;
					if (temp < min)
						min = temp;
				} else {
					addPointToChart(min);
				}
			}
		}
		((NumberAxis) fitnessChart.getYAxis()).setLowerBound((int)min - 1);
		((NumberAxis) fitnessChart.getYAxis()).setUpperBound((int)max + 1);
	}
	
	public void initialize(URL location, ResourceBundle resources) {		
		nSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
		threadsNumSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 100));
		mSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
		iterationsSpinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1000));
		sigmaSpinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1, 0.01));
		c1Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 0.82, 0.01));
		c2Spinner11.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1.2, 0.01));
		miSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10));
		lambdaSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 70));
		sigmaSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 1, 0.01));
		iterationsSpinnerMiLambda.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1000));
		minSigma11.setText(String.valueOf(0.0001));
		algorithmSelect = new ToggleGroup();
		algorithm11.setToggleGroup(algorithmSelect);
		algorithmMiLambda.setToggleGroup(algorithmSelect);
		algorithmSelect.selectToggle(algorithm11);
		fitnessChart.getData().add(new Series<Number, Number>());
	}
}
