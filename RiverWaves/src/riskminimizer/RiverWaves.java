/***
 * The Example is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Example is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 */
package riskminimizer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 * See http://neuroph.sourceforge.net/tutorials/StockMarketPredictionTutorial.html
 * @author Dr.V.Steinhauer
 */
public class RiverWaves {

    static List dataList;
    int remove1 = -1;
    int remove2 = -1;
    int remove3 = -1;
    int remove4 = -1;
    static double minrfactor = 999999999.0D;
    static int inputsCount = 4;
    static int outputCount = 1;
    static Stack stack = new Stack();
    static Stack bestStack = new Stack();
    static double bestpredicted = 0.0D;
    static int bestinputsCount = -1;
    static double MaxError = 0.005D;
    static double LearningRate = 0.05D;
    static int MaxIterations = 10000;
    public static double daxmax = 10000.0D;
    

    public RiverWaves() {
        //TODO set MaxIterations,LearningRate,MaxError
    }


    public double autodetection(double[] y) {
        
        y = TrainingSetImportStock.simpleNormalizing(y, daxmax);        
        for (inputsCount = 2; inputsCount < y.length / 2; inputsCount++) {
            stack = new Stack();
            for (;;) {
                stack = filterLoop(y);
                System.out.print("Current inputsCount:" + inputsCount + " Global Result -> Removed Elements:");
                for (int m = 0; m < bestStack.size(); m++) {
                    System.out.print((Integer) bestStack.get(m) + " ");
                }
                System.out.print(" BEST inputsCount:" + bestinputsCount + " BEST Predicted Value:" + (bestpredicted * daxmax) + " MIN RFACTOR:" + minrfactor + "\n");
                int n = (Integer) stack.lastElement();
                if (n == -1) {
                    break;
                }
            }
        }
        return bestpredicted;
    }

    private Stack filterLoop(double[] y) {
        int best = -1;
        for (int i = -1; i < y.length - inputsCount; i++) {
            dataList = TrainingSetImportStock.importDataElementsFromArray(y, inputsCount, outputCount, stack, i);
            TrainingSet trainingSet = TrainingSetImportStock.importFromDataElementList(dataList);
            NeuralNetwork neuralNet = new MultiLayerPerceptron(TransferFunctionType.GAUSSIAN, inputsCount, inputsCount * 2 + 1, outputCount);
//            ((LMS) neuralNet.getLearningRule()).setMaxError(MaxError);
//            ((LMS) neuralNet.getLearningRule()).setLearningRate(LearningRate);
//            ((LMS) neuralNet.getLearningRule()).setMaxIterations(MaxIterations);

            MomentumBackpropagation learningRule = ((MomentumBackpropagation) neuralNet.getLearningRule());
            learningRule.setLearningRate(LearningRate);
            learningRule.setMaxError(MaxError);
            learningRule.setMomentum(0.1);
            learningRule.setMinErrorChange(0.0001);
            learningRule.setMinErrorChangeIterationsLimit(100);//100 by Zoran

            neuralNet.learnInSameThread(trainingSet);

            double difs = 0.0D;
            double sum = 0.0D;
            List dataListFull = TrainingSetImportStock.importDataElementsFromArray(y, inputsCount, outputCount, null, -1);
            for (int k = 0; k < dataListFull.size(); k++) {
                TrainingSet testSet = new TrainingSet();
                DataElement de = (DataElement) dataListFull.get(k);
                testSet.addElement(new TrainingElement(de.getInput()));
                for (TrainingElement testElement : testSet.trainingElements()) {
                    neuralNet.setInput(testElement.getInput());
                    neuralNet.calculate();
                    Vector<Double> networkOutput = neuralNet.getOutput();
                    difs = difs + Math.abs((Double) networkOutput.get(0) - (Double) de.getOutput().get(0));
                    sum = sum + (Double) de.getOutput().get(0);
                }
            }

            double rfactor = ((difs / sum) * 100.0D);

            if (rfactor < minrfactor) {
                minrfactor = rfactor;
                best = i;
                TrainingSet testSet1 = TrainingSetImportStock.importFromArrayToPredict(y, inputsCount);
                for (TrainingElement testElement : testSet1.trainingElements()) {
                    neuralNet.setInput(testElement.getInput());
                    neuralNet.calculate();
                    Vector<Double> networkOutput = neuralNet.getOutput();
                    bestpredicted = (Double) networkOutput.get(0);
                    bestinputsCount = inputsCount;
                    bestStack = stack;
                }
            }
        }
        if (!stack.contains(best)) {
            stack.add(best);
        }
        return stack;
    }


}
