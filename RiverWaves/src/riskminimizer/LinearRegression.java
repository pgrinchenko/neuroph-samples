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


/*
 * linear regression y = a + b * x
 * least squares method
 * 
 */
/**
 * The part of simple stock market components, easy to use
 * the stock market interface for neural network.
 *
 * @author Valentin Steinhauer <valentin.steinhauer@t-online.de>
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class LinearRegression {

    private double a;
    private double b;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    //The output value is RMS for least squares regression
    //Use the getA and getB to get the result-values of regression
    public double calculateRegression(double[] x, double[] y) {

        if (x == null) {
            x = new double[y.length];
            for (int i = 0; i < y.length; i++) {
                x[i] = i + 1;
            }
        }

        double xsum = 0.0;
        double ysum = 0.0;

        for (int j = 0; j < y.length; j++) {
            xsum = xsum + x[j];
            ysum = ysum + y[j];
        }

        double xm = xsum / x.length;
        double ym = ysum / y.length;

        double sum1 = 0.0;
        double sum2 = 0.0;

        for (int j = 0; j < y.length; j++) {
            sum1 = sum1 + (x[j] - xm) * (y[j] - ym);
            sum2 = sum2 + (x[j] - xm) * (x[j] - xm);
        }

        double bX = sum1 / sum2;
        double aX = ym - bX * xm;

        setA(aX);
        setB(bX);

        double rfactor = 0.0D;

        double diff;
        for (int j = 0; j < y.length; j++) {
            diff = y[j] - (aX + bX * x[j]);
            if (y[j] != 0.0) {
                rfactor = rfactor + Math.abs(diff / y[j]);
            }
        }

        //System.out.println("linear regression: y = a + b * x -> b =" + bX + " a=" + aX );

        return (rfactor / y.length) * 100.0D;
    }

    public double[] subtractLinearRegression(double[] x, double[] y) {

        if (x == null) {
            x = new double[y.length];
            for (int i = 0; i < y.length; i++) {
                x[i] = i + 1;
            }
        }

        double aX = this.getA();
        double bX = this.getB();

        for (int j = 0; j < y.length; j++) {
            y[j] = y[j] - (aX + bX * x[j]);
        }

        return y;
    }
}
