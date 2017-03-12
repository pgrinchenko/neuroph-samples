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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetlang.core.Disposable;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.neuroph.core.learning.TrainingSet;

public class Main {

    public final static int NUM_ACTORS = 1;//The number of fibers in the chain
    public final static String STOP = "__STOP__";//STOP SIGNAL
    public final static int NCommittee = 10;//How big is the committee
    public static double ValuesFromCommittee[] = new double[NCommittee];

    public static void main(String[] args) {

        int N = Runtime.getRuntime().availableProcessors();
        System.out.println("N processors:" + N);

        System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        ExecutorService exec = Executors.newCachedThreadPool();
        PoolFiberFactory factory = new PoolFiberFactory(exec);

        // when the stop signal is received, the fiber.dispose() call will
        // call this and decrement the countdown latch. The onstop.await()
        // will block until the latch is zero, so that way the manager waits
        // for all the actors to complete before exiting
        final CountDownLatch onstop = new CountDownLatch(NUM_ACTORS);
        Disposable dispose = new Disposable() {

            public void dispose() {
                onstop.countDown();
            }
        };

        Fiber multiLayerPerceptronFiber = factory.create();
        multiLayerPerceptronFiber.add(dispose);
        MultiLayerPerceptronActor multiLayerPerceptronActor =
                new MultiLayerPerceptronActor(Channels.multiLayerPerceptronChannel, null, multiLayerPerceptronFiber);

        multiLayerPerceptronActor.start();

        TrainingSetGetter tsg = new TrainingSetGetter();
        TrainingSet ts = tsg.getTrainingSet();
        // seed the incoming channel with NCommittee tranings
        for (int i = 0; i < NCommittee; i++) {
            String payload = Integer.toString(i);
            Channels.multiLayerPerceptronChannel.publish(new Message(i, payload, ts));
        }

        // send the stop processing
        Channels.multiLayerPerceptronChannel.publish(new Message(0, Main.STOP, ts));

        try {
            onstop.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        exec.shutdown();

        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        double summe_of_values = 0.0D;
        for (int i = 0; i < NCommittee; i++) {
            summe_of_values = summe_of_values + ValuesFromCommittee[i];
        }
        double middle_value = summe_of_values / NCommittee;
        double summe_of_diffs = 0.0D;
        for (int i = 0; i < NCommittee; i++) {
            summe_of_diffs = summe_of_diffs + Math.abs(ValuesFromCommittee[i] - middle_value);
        }
        double middle_diff = summe_of_diffs / NCommittee;
        double RFactor = (middle_diff / middle_value) * 100.0D;
        System.out.printf("middle_value=%4f2\nmiddle_diffs=%4f3\nRfactor(proc)=%4f2\n", middle_value * 10000.0D, middle_diff * 10000.0D, RFactor);
        System.exit(0);
    }
}


