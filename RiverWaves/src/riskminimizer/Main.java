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

/**
 * See http://neuroph.sourceforge.net/tutorials/StockMarketPredictionTutorial.html
 * @author Dr.V.Steinhauer
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        // test time serie 1: 03.2009 dax
        double[] y = {
            3710.07, 3890.94, 3695.49, 3666.41, 3692.03,
            3886.98, 3914.10, 3956.22, 3953.60, 4044.54,
            3987.77, 3996.32, 4043.46, 4068.74, 4176.37,
            4187.36, 4223.29, 4259.37, 4203.55, 3989.23}; //next value is 4084.76 (this value must be predicted ) at 31.03.2009
//        double[] y = {5533.24, 5434.34, 5484.85, 5498.26, 5536.37,
//            5503.93, 5500.39, 5511.1, 5592.12, 5648.34, 5680.41, 5722.05, 5688.44, 5604.07,
//            5615.51, 5532.33, 5598.46, 5713.51, 5776.56, 5817.88, 5795.32, 5877.36,
//            5875.91, 5885.89, 5936.72, 5928.63, 5945.11, 5903.56, 5970.99,
//            6024.28, 6012.31, 5982.43, 5987.5, 6017.27, 6039.0, 6132.95};//next value is 6120.05( this value must be predicted ) at 26.03.2010

        helpAnalyser(y);

        RiverWaves td = new RiverWaves();
        double result = td.autodetection(y);
        result = result * td.daxmax;
        System.out.println("Autopredicted Value is " + result);


        System.out.println("Time stamp N3:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
        System.exit(0);
    }

    private static void helpAnalyser(double[] y) {

        double[] ytmp = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            ytmp[i] = y[i];
        }

        LinearRegression lr = new LinearRegression();
        double linearRegressionRfactor = lr.calculateRegression(null, ytmp);
        System.out.println("linearRegression RFactor:" + linearRegressionRfactor);
        ytmp= lr.subtractLinearRegression(null, ytmp);

        FourierAnalyser fa = new FourierAnalyser();
        int interval1 = fa.transform(ytmp);
        interval1 = interval1 -1;
        System.out.println("best harmonic:" + interval1+"\n");

    }
}
