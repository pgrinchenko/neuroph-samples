/***
 * Neuroph  http://neuroph.sourceforge.net
 * Copyright by Neuroph Project (C) 2008
 *
 * This file is part of Neuroph framework.
 *
 * Neuroph is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Neuroph is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 */
package riskminimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.util.TrainingSetImport;

/**
 * The part of simple stock market components, easy to use
 * the stock market interface for neural network.
 *
 * @author Valentin Steinhauer <valentin.steinhauer@t-online.de>
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class TrainingSetImportStock extends TrainingSetImport {

    public static TrainingSet importFromArray(double[] values, int inputsCount, int outputsCount) {
        TrainingSet trainingSet = new TrainingSet();
        for (int i = 0; i < values.length - inputsCount; i++) {
            Vector<Double> inputs = new Vector<Double>();
            for (int j = i; j < i + inputsCount; j++) {
                inputs.add(values[j]);
            }
            Vector<Double> outputs = new Vector<Double>();
            if (outputsCount > 0 && i + inputsCount + outputsCount <= values.length) {
                for (int j = i + inputsCount; j < i + inputsCount + outputsCount; j++) {
                    outputs.add(values[j]);
                }
                if (outputsCount > 0) {
                    trainingSet.addElement(new SupervisedTrainingElement(inputs, outputs));
                } else {
                    trainingSet.addElement(new TrainingElement(inputs));
                }
            }
        }
        return trainingSet;
    }

    public static TrainingSet importFromArrayToPredict(double[] values, int inputsCount) {
        TrainingSet trainingSet = new TrainingSet();
        Vector<Double> inputs = new Vector<Double>();
        for (int j = values.length - inputsCount; j < values.length; j++) {
            inputs.add(values[j]);
        }
        trainingSet.addElement(new TrainingElement(inputs));
        return trainingSet;
    }

    public static TrainingSet importFromDataElementList(List dataList) {
        TrainingSet trainingSet = new TrainingSet();
        for (int i = 0; i < dataList.size(); i++) {
            DataElement dataElement = (DataElement) dataList.get(i);
            Vector inputs = dataElement.getInput();
            Vector outputs = dataElement.getOutput();
            if (outputs == null) {
                trainingSet.addElement(new TrainingElement(inputs));
            } else {
                trainingSet.addElement(new SupervisedTrainingElement(inputs, outputs));
            }
        }
        return trainingSet;
    }

    public static double[] simpleNormalizing(double[] y, double norm) {
        for (int i = 0; i < y.length; i++) {
            y[i] = y[i] / norm;
        }
        return y;
    }


    /*
     * values - time series array
     * inputCount - window
     * blocked - number of window to block, -1 means not blocked
     * */
    public static List importDataElementsFromArray(double[] values, int inputsCount, int outputsCount, Stack stack, int next) {
        List list = new ArrayList();
        for (int i = 0; i < values.length - inputsCount; i++) {
            if (stack != null) {
                if (stack.contains(i)) {
                    continue;
                }
                if (stack.contains(next)) {
                    continue;
                }
            }
            if (i == next) {
                continue;
            }
            DataElement de = new DataElement();
            Vector<Double> inputs = new Vector<Double>();
            for (int j = i; j < i + inputsCount; j++) {
                inputs.add(values[j]);
            }
            Vector<Double> outputs = new Vector<Double>();
            if (outputsCount > 0 && i + inputsCount + outputsCount <= values.length) {
                for (int j = i + inputsCount; j < i + inputsCount + outputsCount; j++) {
                    outputs.add(values[j]);
                }
            }
            de.setInput(inputs);
            de.setOutput(outputs);
            list.add(de);
        }
        return list;
    }
}
