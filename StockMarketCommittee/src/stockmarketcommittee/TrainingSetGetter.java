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

import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;


public class TrainingSetGetter {

    private TrainingSet trainingSet ;

    public TrainingSet getTrainingSet() {
        trainingSet = new TrainingSet();
        double daxmax = 10000.0D;
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3710.0D / daxmax, 3690.0D / daxmax, 3890.0D / daxmax, 3695.0D / daxmax}, new double[]{3666.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3690.0D / daxmax, 3890.0D / daxmax, 3695.0D / daxmax, 3666.0D / daxmax}, new double[]{3692.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3890.0D / daxmax, 3695.0D / daxmax, 3666.0D / daxmax, 3692.0D / daxmax}, new double[]{3886.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3695.0D / daxmax, 3666.0D / daxmax, 3692.0D / daxmax, 3886.0D / daxmax}, new double[]{3914.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3666.0D / daxmax, 3692.0D / daxmax, 3886.0D / daxmax, 3914.0D / daxmax}, new double[]{3956.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3692.0D / daxmax, 3886.0D / daxmax, 3914.0D / daxmax, 3956.0D / daxmax}, new double[]{3953.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3886.0D / daxmax, 3914.0D / daxmax, 3956.0D / daxmax, 3953.0D / daxmax}, new double[]{4044.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3914.0D / daxmax, 3956.0D / daxmax, 3953.0D / daxmax, 4044.0D / daxmax}, new double[]{3987.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3956.0D / daxmax, 3953.0D / daxmax, 4044.0D / daxmax, 3987.0D / daxmax}, new double[]{3996.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3953.0D / daxmax, 4044.0D / daxmax, 3987.0D / daxmax, 3996.0D / daxmax}, new double[]{4043.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4044.0D / daxmax, 3987.0D / daxmax, 3996.0D / daxmax, 4043.0D / daxmax}, new double[]{4068.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3987.0D / daxmax, 3996.0D / daxmax, 4043.0D / daxmax, 4068.0D / daxmax}, new double[]{4176.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3996.0D / daxmax, 4043.0D / daxmax, 4068.0D / daxmax, 4176.0D / daxmax}, new double[]{4187.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4043.0D / daxmax, 4068.0D / daxmax, 4176.0D / daxmax, 4187.0D / daxmax}, new double[]{4223.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4068.0D / daxmax, 4176.0D / daxmax, 4187.0D / daxmax, 4223.0D / daxmax}, new double[]{4259.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4176.0D / daxmax, 4187.0D / daxmax, 4223.0D / daxmax, 4259.0D / daxmax}, new double[]{4203.0D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4187.0D / daxmax, 4223.0D / daxmax, 4259.0D / daxmax, 4203.0D / daxmax}, new double[]{3989.0D / daxmax}));
        return trainingSet;
    }

    public void setTrainingSet(TrainingSet trainingSet) {
        this.trainingSet = trainingSet;
    }

}
