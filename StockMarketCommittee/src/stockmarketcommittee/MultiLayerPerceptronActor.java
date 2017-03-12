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

package stockmarketcommittee;

import java.util.Vector;
import org.jetlang.channels.Channel;
import org.jetlang.fibers.Fiber;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

public class MultiLayerPerceptronActor extends Actor {

    public MultiLayerPerceptronActor(Channel<Message> inChannel,
            Channel<Message> outChannel,
            Fiber fiber) {
        super(inChannel, outChannel, fiber);
    }

    @Override
    public void act(Message message) {
        String payload = (String) message.payload;
        if (Main.STOP.equals(payload)) {
            return;
        }
        TrainingSet trainingSet = message.trainingSet;
        int maxIterations = 10000;
        NeuralNetwork neuralNet = new MultiLayerPerceptron(4,9,1);
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.0001);//0-1
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.60);//0-1
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//0-1        
        neuralNet.learnInSameThread(trainingSet);        
        TrainingSet testSet = new TrainingSet();
        double daxmax = 10000.0D;
        testSet.addElement(new TrainingElement(new double[]{4223.0D / daxmax, 4259.0D / daxmax, 4203.0D / daxmax, 3989.0D / daxmax}));
        for (TrainingElement testElement : testSet.trainingElements()) {
            neuralNet.setInput(testElement.getInput());
            neuralNet.calculate();
            Vector<Double> networkOutput = neuralNet.getOutput();
            double predicted = networkOutput.get(0);
            try {
                int k = Integer.parseInt(payload);
                Main.ValuesFromCommittee[k] = predicted;
            } catch (java.lang.NumberFormatException nfe) {
            }            
        }        
    }
}




