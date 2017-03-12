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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * The part of simple stock market components, easy to use
 * the stock market interface for neural network.
 *
 * @author Valentin Steinhauer <valentin.steinhauer@t-online.de>
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class FourierAnalyser {

    private double[] a;
    private double[] b;
    private double RMS = -1.0D;
    double x[];
    double fr[];
    double fi[];
    double gr[];
    double gi[];

    public int transform(double[] y) {
        int n = y.length;
        try {
            //int n = y.length;
            //int m = 1;
            x = new double[n];
            fr = new double[n];
            fi = new double[n];
            gr = new double[n];
            gi = new double[n];
            double h = 1.0 / (n - 1);
            double f0 = 1 / Math.sqrt(n);

            // Assign the data and perform the transform
            for (int i = 0; i < n; ++i) {
                x[i] = h * i;
                //fr[i] = x[i] * (1 - x[i]);
                fr[i] = y[i];
                fi[i] = 0;
                //System.out.println("x,y ->" + x[i] + " " + fr[i]);
            }
            dft();

            System.out.print("Spectrum\n");
            double ampl;
            double maxampl = -99999.9D;
            int imax = n / 2 + 1;
            NumberFormat formatter = new DecimalFormat("0000000.0");
            for (int i = 0; i < n / 2 + 1; ++i) {
                ampl = gr[i] * gr[i] + gi[i] * gi[i];
                if (ampl > maxampl) {
                    maxampl = ampl;
                    imax = i;
                }
                //System.out.println("ampl=" + formatter.format(ampl) + " gr=" + gr[i] + " gi=" + gi[i]);
                System.out.println("number of harmonic="+i+" ampl=" + formatter.format(ampl));
            }
//            System.out.println("The RESULT: n =" + n + " imax+1=" + (imax + 1) + " interval for time prediction n/(imax+1)=" + (n / (imax + 1)));

            // Perform the inverse Fourier transform
//            for (int i = 0; i < n; ++i) {
//                fr[i] = f0 * gr[i];
//                fi[i] = -f0 * gi[i];
//                gr[i] = gi[i] = 0;
//            }
//            System.out.print("Reverse\n");
//            dft();
//
//            for (int i = 0; i < n; ++i) {
//                gr[i] = f0 * gr[i];
//                gi[i] = -f0 * gi[i];
//            }
//
//            // Output the result in every m data steps
//            for (int i = 0; i < n; i++) {
//                System.out.println("test->x,y-teor,y-exp: " + x[i] + " " + gr[i] + " " + y[i]);
//            }

            return n / (2 * imax) + 1;
        } catch (java.lang.ArithmeticException e) {
            return n / 10;
        }
    }

// Method to perform the discrete Foruier transform.  Here
// fr[] and fi[] are the real and imaginary parts of the
// data with the corresponding outputs gr[] and gi[].
    public void dft() {
        int n1 = fr.length;
        double x1 = 2 * Math.PI / n1;
        for (int i = 0; i < n1; ++i) {
            for (int j = 0; j < n1; ++j) {
                double q = x1 * j * i;
                gr[i] += fr[j] * Math.cos(q) + fi[j] * Math.sin(q);
                gi[i] += fi[j] * Math.cos(q) - fr[j] * Math.sin(q);
            }
        }
    }
}
