package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;

/**
 * EXFOR Rartial Uncertainties to Covariance Matrix
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-07-20
 * @since           2012-06-05
 *
 * Program:         x4mkcov.java
 * Property of:     International Atomic Energy Agency
 * Organization:    Nuclear Data Section
 *                  International Atomic Energy Agency (IAEA)
 *                  Wagramer Strasse 5, P.O.Box 100, A-1400
 *                  Vienna, Austria
 * Project:         Multi-Platform Nuclear Reaction Databases
 * Usage:           with proper acknolegement to the IAEA-NDS and author
 * Re-distribution: restricted while the project has not been finished
 * Modifications:   can be done with permission of IAEA-NDS and author
 * Note:            this is privately developed software and it comes with
 *                  NO WARRANTY
 */

public class x4mkcov {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    float emin = 1.0E-5f;
    float emax = 2.0E7f;
    int flagLog = 0;
    float pEneInt = 0.5f;
    float einterval = 1.0E7f;
    float[] ene = null;
    float[][] corr = null;
    double[][] matrix3Gauss = null;
    boolean flagExternalCorr = false;
    boolean lower3 = true;
    boolean symetric = false;
    boolean linearDependant = false;
    String strOutMercParams = "";

    public static void main(String[] stringArray) {
        float f;
        int n;
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4mkcov");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2010\n");
        float[] fArray = new float[]{1000000.0f, 2000000.0f, 3000000.0f, 4000000.0f, 5000000.0f, 6000000.0f, 7000000.0f, 8000000.0f, 9000000.0f, 1.0E7f, 1.1E7f, 1.2E7f, 1.3E7f, 1.4E7f, 1.5E7f, 1.6E7f, 1.7E7f, 1.8E7f, 1.9E7f, 2.0E7f, 2.1E7f};
        x4mkcov x4mkcov2 = new x4mkcov();
        fArray = new float[]{100.0f, 32.0f, 100.0f, 50.0f, 30.0f, 100.0f, 41.0f, 25.0f, 39.0f, 100.0f, 48.0f, 29.0f, 46.0f, 38.0f, 100.0f, 50.0f, 30.0f, 48.0f, 39.0f, 46.0f, 100.0f, 14.0f, 8.0f, 13.0f, 11.0f, 13.0f, 13.0f, 100.0f, 43.0f, 26.0f, 41.0f, 33.0f, 39.0f, 41.0f, 11.0f, 100.0f};
        int n2 = 8;
        float[][] fArray2 = x4mkcov2.zarray2matrix(n2, n2, fArray);
        sysOut.println("corr=" + fArray2);
        for (int i = 0; i < n2; ++i) {
            sysOut.print("Y:" + i);
            for (int j = 0; j < n2; ++j) {
                String string = fArray2[j][i] != 0.0f ? "" + fArray2[j][i] : "";
                string = x4mkcov2.strpad(string, 5);
                sysOut.print(" " + j + ":" + string);
            }
            sysOut.println("");
        }
        System.exit(0);
        fArray = new float[]{1.0E-5f, 1.0E-4f, 0.001f, 0.01f, 0.1f, 1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 2000000.0f, 3000000.0f, 5000000.0f, 8000000.0f, 1.0E7f, 1.4E7f, 1.7E7f, 2.0E7f, 1.0E8f};
        fArray = new float[]{1.0E-5f, 1.0E-4f, 0.001f, 0.01f, 0.1f, 1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f, 9.9999998E10f, 1.0E12f, 9.9999998E12f, 1.0E14f, 9.9999999E14f};
        fArray = new float[100];
        float f2 = 1.0E-5f;
        for (n = 0; n < fArray.length; ++n) {
            fArray[n] = f2;
            f2 = (float)((double)f2 * 1.33);
        }
        f2 = 0.01f;
        for (n = 0; n < fArray.length; ++n) {
            fArray[n] = f2;
            f2 = (float)((double)f2 * 1.05);
        }
        x4mkcov2.setLogEnergyScale(1);
        x4mkcov2.setEnergyInterval(0.05f);
        fArray = new float[100];
        f2 = 1.0E-5f;
        for (n = 0; n < fArray.length; ++n) {
            fArray[n] = f2;
            f2 = (float)((double)f2 * 1.33);
        }
        x4mkcov2.setLogEnergyScale(1);
        x4mkcov2.setEnergyInterval(0.1f);
        fArray2 = x4mkcov2.makeCorrMERC(fArray);
        sysOut.println("---out-2011.06.23---");
        x4mkcov2.sym3Matrix2Full(fArray2);
        x4mkcov2.printMatrixFull(fArray2);
        double[][] dArray = x4mkcov2.matrix2trian_gauss(fArray2);
        x4mkcov2.printMatrixFull(dArray);
        x4mkcov2.isMatrixPositiveDefinite(fArray2);
        x4mkcov2.pause("---");
        fArray2 = x4mkcov2.makeCorrMERC(fArray);
        x4mkcov2.setLogEnergyScale(1);
        x4mkcov2.printMatrix(fArray, fArray2, sysOut);
        fArray2 = x4mkcov2.makeCorrMercPart(fArray, 0.5f);
        x4mkcov2.printMatrix(fArray, fArray2, sysOut);
        fArray2 = x4mkcov2.makeCorrMercPart(fArray, 0.333f);
        x4mkcov2.printMatrix(fArray, fArray2, sysOut);
        fArray2 = x4mkcov2.makeCorrMercPart(fArray, 0.8f);
        x4mkcov2.printMatrix(fArray, fArray2, sysOut);
        x4mkcov x4mkcov3 = new x4mkcov();
        x4mkcov3.setLogEnergyScale(1);
        x4mkcov3.setEnergyInterval(0.05f);
        n = 0;
        float f3 = f2 = 1.0E-5f;
        while ((double)f3 < 2.0E7) {
            f = x4mkcov3.getCorrMERC(f2, f3);
            sysOut.println(" " + (f3 - f2) + " " + f);
            ++n;
            f3 = (float)((double)f3 * 1.01);
        }
        sysOut.println("---out2x---");
        x4mkcov3 = new x4mkcov();
        x4mkcov3.setEnergyMinmax(1.0E-5f, 2.0E7f);
        x4mkcov3.setEnergyMinmax(0.1f, 1.0f);
        x4mkcov3.setEnergyInterval(1.0f);
        x4mkcov3.setLogEnergyScale(1);
        n = 0;
        f3 = f2 = 0.1f;
        while ((double)f3 < 1000000.0) {
            f = x4mkcov3.getCorrMERC(f3, f2);
            sysOut.println(" " + (f3 - f2) + " " + f);
            ++n;
            f3 = (float)((double)f3 * 1.01);
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4mkcov() {
    }

    x4mkcov(x4mkcov x4mkcov2) {
        this.emin = x4mkcov2.emin;
        this.emax = x4mkcov2.emax;
        this.flagLog = x4mkcov2.flagLog;
        this.pEneInt = x4mkcov2.pEneInt;
        this.einterval = x4mkcov2.einterval;
        this.flagExternalCorr = x4mkcov2.flagExternalCorr;
    }

    public int setLogEnergyScale(int n) {
        if (n != 0) {
            n = 1;
        }
        if (n == this.flagLog) {
            return this.flagLog;
        }
        if (n != 0) {
            this.emax = (float)Math.log(this.emax);
            this.emin = (float)Math.log(this.emin);
        } else {
            this.emax = (float)Math.exp(this.emax);
            this.emin = (float)Math.exp(this.emin);
        }
        this.einterval = (this.emax - this.emin) * this.pEneInt;
        this.flagLog = n;
        return this.flagLog;
    }

    public float getLogEne(float f) {
        if (this.flagLog == 0) {
            return f;
        }
        if (f <= 0.0f) {
            return f;
        }
        float f2 = (float)Math.log(f);
        return f2;
    }

    public void setEnergyMinmax(float f, float f2) {
        this.emax = this.getLogEne(f2);
        this.emin = this.getLogEne(f);
        this.einterval = (this.emax - this.emin) * this.pEneInt;
    }

    public void setEnergyInterval(float f) {
        if (f <= 0.0f) {
            return;
        }
        this.pEneInt = f;
        this.einterval = (this.emax - this.emin) * f;
    }

    public void setEnergyMinmax(float[] fArray) {
        this.emax = -1.0E37f;
        this.emin = 1.0E37f;
        int n = fArray.length;
        for (int i = 0; i < n; ++i) {
            if (fArray[i] > this.emax) {
                this.emax = fArray[i];
            }
            if (!(fArray[i] < this.emin)) continue;
            this.emin = fArray[i];
        }
        this.setEnergyMinmax(this.emin, this.emax);
    }

    public float[][] makeCorrMercPart(float[] fArray, float f) {
        this.setEnergyInterval(f);
        return this.makeCorrMERC(fArray);
    }

    public float[][] setExternalCorrMERC(float[] fArray, float[][] fArray2) {
        float[][] fArray3 = null;
        this.ene = fArray;
        this.corr = null;
        if (fArray2 == null) {
            return null;
        }
        int n = fArray.length;
        int n2 = fArray2[0].length;
        if (n != n2) {
            return null;
        }
        fArray3 = fArray2;
        this.flagExternalCorr = true;
        this.corr = fArray3;
        return fArray3;
    }

    public float[][] makeCorrMERC(float[] fArray) {
        int n;
        int n2;
        float[][] fArray2 = null;
        this.ene = fArray;
        this.corr = null;
        int n3 = fArray.length;
        try {
            fArray2 = new float[n3][n3];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            for (n = 0; n < n3; ++n) {
                fArray2[n2][n] = 0.0f;
            }
        }
        for (n = 0; n < n3; ++n) {
            for (n2 = 0; n2 <= n; ++n2) {
                float f = Math.abs(this.getLogEne(fArray[n2]) - this.getLogEne(fArray[n]));
                fArray2[n2][n] = f <= 0.0f ? 1.0f : (f > this.einterval ? 0.0f : 1.0f - f / this.einterval);
            }
        }
        this.corr = fArray2;
        return fArray2;
    }

    public float getCorrMERC_00x(float f, float f2) {
        float f3 = 0.0f;
        float f4 = Math.abs(this.getLogEne(f) - this.getLogEne(f2));
        if (f4 <= 0.0f) {
            f3 = 1.0f;
        } else if (f4 > this.einterval) {
            f3 = 0.0f;
        } else {
            if (this.flagLog != 0) {
                f4 = Math.abs(this.getLogEne(Math.abs(f2 - f)));
            }
            f3 = 1.0f - f4 / this.einterval;
        }
        return f3;
    }

    public float getCorrMERC_0(float f, float f2) {
        float f3 = 0.0f;
        float f4 = Math.abs(this.getLogEne(f) - this.getLogEne(f2));
        f3 = f4 <= 0.0f ? 1.0f : (f4 > this.einterval ? 0.0f : 1.0f - f4 / this.einterval);
        return f3;
    }

    public float getCorrMERC_1x(float f, float f2) {
        float f3 = 0.0f;
        float f4 = f - f2;
        if (f4 == 0.0f) {
            return 1.0f;
        }
        if (f4 < 0.0f) {
            f4 = -f4;
        }
        f3 = (f4 = Math.abs(this.getLogEne(f4))) <= 0.0f ? 1.0f : (f4 > this.einterval ? 0.0f : 1.0f - f4 / this.einterval);
        return f3;
    }

    public float getCorrMERC_1(float f, float f2) {
        float f3;
        float f4 = 0.0f;
        float f5 = f - f2;
        if (f5 == 0.0f) {
            return 1.0f;
        }
        if (f5 < 0.0f) {
            f5 = -f5;
        }
        if (f < 0.0f) {
            f = -f;
        }
        if (f2 < 0.0f) {
            f2 = -f2;
        }
        if (f < 1.0E-32f) {
            f = 1.0E-32f;
        }
        if (f2 < 1.0E-32f) {
            f2 = 1.0E-32f;
        }
        if ((f5 = Math.abs(this.getLogEne(f3 = f2 > f ? f2 / f : f / f2))) <= 0.0f) {
            f4 = 1.0f;
        } else if (f5 > this.einterval) {
            f4 = 0.0f;
        } else {
            f4 = 1.0f - f5 / this.einterval;
            f5 = (float)((double)Math.abs(f2 - f) / Math.exp(this.einterval));
            f5 = Math.abs(this.getLogEne(f5));
            f4 = (float)Math.exp(-f5);
        }
        return f4;
    }

    public float getCorrMERC(float f, float f2) {
        if (this.flagLog == 0) {
            return this.getCorrMERC_0(f, f2);
        }
        return this.getCorrMERC_1x(f, f2);
    }

    public float getCorrMERC_GeLiEff(float f, float f2) {
        float f3 = 0.0f;
        float f4 = f - f2;
        double d = 0.02803;
        double d2 = -1.0659;
        double d3 = 0.0126;
        double d4 = 0.0102;
        double d5 = 0.67;
        d5 = (d3 /= d) * (d4 /= d2) * d5;
        double d6 = d3 * d3 + d2 * d2 * Math.log(f) * Math.log(f2) * d4 * d4 + d2 * (Math.log(f) + Math.log(f2)) * d5;
        double d7 = d3 * d3 + d2 * d2 * Math.log(f) * Math.log(f) * d4 * d4 + 2.0 * d2 * Math.log(f) * d5;
        double d8 = d3 * d3 + d2 * d2 * Math.log(f2) * Math.log(f2) * d4 * d4 + 2.0 * d2 * Math.log(f2) * d5;
        double d9 = d6 / Math.sqrt(d7) / Math.sqrt(d8);
        f3 = (float)d9;
        return f3;
    }

    public float float2percents(float f, int n) {
        long l;
        if (n == 0) {
            return f;
        }
        if (n == 1) {
            l = 10L;
        } else if (n == 2) {
            l = 100L;
        } else if (n == 3) {
            l = 1000L;
        } else if (n == 4) {
            l = 10000L;
        } else if (n == 5) {
            l = 100000L;
        } else if (n == 6) {
            l = 1000000L;
        } else if (n == 7) {
            l = 10000000L;
        } else {
            return f;
        }
        long l2 = (long)((double)(f * (float)l) + 0.5);
        float f2 = (float)l2 / (float)l;
        return f2 * 100.0f;
    }

    public void vector_percent2nodim(float[] fArray) {
        if (fArray == null) {
            return;
        }
        int n = fArray.length;
        for (int i = 0; i < n; ++i) {
            fArray[i] = fArray[i] / 100.0f;
        }
    }

    public void matrix_percent2nodim(float[][] fArray) {
        if (fArray == null) {
            return;
        }
        int n = fArray[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                fArray[j][i] = fArray[j][i] / 100.0f;
            }
        }
    }

    public float[] vector2percents(float[] fArray, int n) {
        float[] fArray2;
        if (fArray == null) {
            return null;
        }
        int n2 = fArray.length;
        try {
            fArray2 = new float[n2];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (int i = 0; i < n2; ++i) {
            fArray2[i] = this.float2percents(fArray[i], n);
        }
        return fArray2;
    }

    public float[][] matrix2percents(float[][] fArray, int n) {
        float[][] fArray2;
        if (fArray == null) {
            return null;
        }
        int n2 = fArray[0].length;
        try {
            fArray2 = new float[n2][n2];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n2; ++j) {
                fArray2[j][i] = this.float2percents(fArray[j][i], n);
            }
        }
        return fArray2;
    }

    public float[] covar2var(float[][] fArray) {
        float[] fArray2;
        if (fArray == null) {
            return null;
        }
        int n = fArray[0].length;
        try {
            fArray2 = new float[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            fArray2[i] = (float)Math.sqrt(fArray[i][i]);
        }
        return fArray2;
    }

    public float[][] covar2corr(float[][] fArray) {
        float[][] fArray2;
        if (fArray == null) {
            return null;
        }
        int n = fArray[0].length;
        try {
            fArray2 = new float[n][n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                fArray2[j][i] = (float)((double)fArray[j][i] / Math.sqrt(fArray[i][i]) / Math.sqrt(fArray[j][j]));
            }
        }
        return fArray2;
    }

    public float[][] corr_var2covar(float[][] fArray, float[] fArray2) {
        float[][] fArray3;
        if (fArray == null) {
            return null;
        }
        int n = fArray[0].length;
        try {
            fArray3 = new float[n][n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                fArray3[j][i] = fArray[j][i] * fArray2[i] * fArray2[j];
            }
        }
        return fArray3;
    }

    public float[][] zarray2matrix(int n, int n2, float[] fArray) {
        float[][] fArray2 = null;
        if (fArray == null) {
            return null;
        }
        if (n <= 0) {
            return null;
        }
        if (n2 <= 0) {
            return null;
        }
        int n3 = fArray.length;
        if (n3 == n * n2) {
            try {
                fArray2 = new float[n][n2];
            }
            catch (OutOfMemoryError outOfMemoryError) {
                return null;
            }
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    fArray2[i][j] = fArray[i * n + j];
                }
            }
            return fArray2;
        }
        if (n == n2 && n3 == n * (n + 1) / 2) {
            int n4;
            int n5;
            try {
                fArray2 = new float[n][n];
            }
            catch (OutOfMemoryError outOfMemoryError) {
                return null;
            }
            for (n5 = 0; n5 < n; ++n5) {
                for (n4 = 0; n4 < n; ++n4) {
                    fArray2[n5][n4] = 0.0f;
                }
            }
            int n6 = 0;
            for (n5 = 0; n5 < n2; ++n5) {
                for (n4 = 0; n4 <= n5; ++n4) {
                    fArray2[n4][n5] = fArray[n6++];
                    fArray2[n5][n4] = fArray2[n4][n5];
                }
            }
        }
        return fArray2;
    }

    public double[][] zarray2matrix(int n, int n2, double[] dArray) {
        double[][] dArray2;
        block18: {
            int n3;
            int n4;
            dArray2 = null;
            if (dArray == null) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            if (n2 <= 0) {
                return null;
            }
            int n5 = dArray.length;
            if (n5 == n * n2) {
                try {
                    dArray2 = new double[n][n2];
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    return null;
                }
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < n; ++j) {
                        dArray2[i][j] = dArray[i * n + j];
                    }
                }
                return dArray2;
            }
            if (n != n2 || n5 < n * (n + 1) / 2 || n5 >= n * n2) break block18;
            this.symetric = true;
            try {
                dArray2 = new double[n][n];
            }
            catch (OutOfMemoryError outOfMemoryError) {
                return null;
            }
            for (n4 = 0; n4 < n; ++n4) {
                for (n3 = 0; n3 < n; ++n3) {
                    dArray2[n4][n3] = 0.0;
                }
            }
            if (this.lower3) {
                int n6 = 0;
                for (n4 = 0; n4 < n2; ++n4) {
                    for (n3 = 0; n3 <= n4; ++n3) {
                        dArray2[n3][n4] = dArray[n6++];
                        dArray2[n4][n3] = dArray2[n3][n4];
                    }
                }
            } else {
                int n7 = 0;
                for (n4 = 0; n4 < n2; ++n4) {
                    for (n3 = n4; n3 < n; ++n3) {
                        dArray2[n3][n4] = dArray[n7++];
                        dArray2[n4][n3] = dArray2[n3][n4];
                    }
                }
            }
        }
        return dArray2;
    }

    public double[] matrix2lowtriangle(int n, double[][] dArray) {
        double[] dArray2 = null;
        if (dArray == null) {
            return null;
        }
        if (n <= 0) {
            return null;
        }
        int n2 = n * (n + 1) / 2;
        try {
            dArray2 = new double[n2];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                dArray2[n3++] = dArray[i][j];
            }
        }
        return dArray2;
    }

    public double[] matrix2array1(double[][] dArray) {
        double[] dArray2 = null;
        if (dArray == null) {
            return null;
        }
        int n = dArray.length;
        int n2 = dArray[0].length;
        if (n2 <= 0) {
            return null;
        }
        int n3 = n2 * n;
        try {
            dArray2 = new double[n3];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        int n4 = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                dArray2[n4++] = dArray[i][j];
            }
        }
        return dArray2;
    }

    public boolean isMatrixPositiveDefinite(float[][] fArray, int n) {
        long l;
        float[][] fArray2;
        if (fArray == null) {
            return false;
        }
        int n2 = fArray[0].length;
        try {
            fArray2 = new float[n2][n2];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return false;
        }
        if (n == 0) {
            return false;
        }
        if (n == 1) {
            l = 10L;
        } else if (n == 2) {
            l = 100L;
        } else if (n == 3) {
            l = 1000L;
        } else if (n == 4) {
            l = 10000L;
        } else if (n == 5) {
            l = 100000L;
        } else if (n == 6) {
            l = 1000000L;
        } else if (n == 7) {
            l = 10000000L;
        } else {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n2; ++j) {
                int n3 = (int)((double)(fArray[j][i] * (float)l) + 0.5);
                fArray2[j][i] = (float)n3 / (float)l;
            }
        }
        boolean bl = this.isMatrixPositiveDefinite(fArray2);
        return bl;
    }

    public int iiMatrixPositiveDefinite(float[][] fArray) {
        int n = 1;
        if (fArray == null) {
            return -3;
        }
        int n2 = fArray[0].length;
        int n3 = fArray.length;
        if (n2 != n3) {
            return -4;
        }
        double[][] dArray = this.matrix2trian_gauss(fArray);
        if (dArray == null) {
            return -2;
        }
        if (this.linearDependant) {
            return 0;
        }
        double[] dArray2 = this.trian_gauss2determinants(dArray);
        if (dArray2 == null) {
            return 0;
        }
        for (int i = 0; i < dArray2.length; ++i) {
            if (dArray2[i] < 0.0) {
                return -1;
            }
            if (dArray2[i] != 0.0) continue;
            n = 0;
        }
        return n;
    }

    public boolean isMatrixPositiveDefinite(float[][] fArray) {
        double[][] dArray = this.matrix2trian_gauss(fArray);
        if (dArray == null) {
            return false;
        }
        if (this.linearDependant) {
            return false;
        }
        double[] dArray2 = this.trian_gauss2determinants(dArray);
        if (dArray2 == null) {
            return false;
        }
        for (int i = 0; i < dArray2.length; ++i) {
            if (!(dArray2[i] <= 0.0)) continue;
            return false;
        }
        return true;
    }

    public boolean isMatrixPositiveDefinite(double[][] dArray) {
        double[][] dArray2 = this.matrix2trian_gauss(dArray);
        if (dArray2 == null) {
            return false;
        }
        if (this.linearDependant) {
            return false;
        }
        double[] dArray3 = this.trian_gauss2determinants(dArray2);
        if (dArray3 == null) {
            return false;
        }
        for (int i = 0; i < dArray3.length; ++i) {
            double d = dArray3[i];
            if (d < 0.0 && -d < 1.0E-16) {
                d = 1.0E-98;
            }
            if (!(d <= 0.0)) continue;
            return false;
        }
        return true;
    }

    public void print8(double[][] dArray, String string) {
        int n = dArray[0].length;
        int n2 = dArray.length;
        for (int i = 0; i < n2 && i < 8; ++i) {
            System.out.print(string + ":" + i + "/" + n2 + ")");
            for (int j = 0; j < n && j < 8; ++j) {
                System.out.print(" " + (float)dArray[i][j]);
            }
            System.out.println("");
        }
    }

    public double[] trian_gauss2determinants(double[][] dArray) {
        int n;
        double[] dArray2 = null;
        int n2 = dArray[0].length;
        try {
            dArray2 = new double[n2];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (n = 0; n < n2; ++n) {
            dArray2[n] = 0.0;
        }
        for (n = 0; n < n2; ++n) {
            if (n == 0) {
                dArray2[0] = dArray[0][0];
                continue;
            }
            if (n == 1) {
                dArray2[1] = dArray[1][1] * dArray2[0];
                continue;
            }
            dArray2[n] = dArray[n][n];
        }
        return dArray2;
    }

    public double[][] matrix2trian_gauss(float[][] fArray) {
        int n;
        int n2;
        int n3;
        boolean bl = false;
        double[][] dArray = null;
        this.matrix3Gauss = null;
        this.linearDependant = false;
        if (fArray == null) {
            return null;
        }
        int n4 = n3 = fArray[0].length;
        try {
            dArray = new double[n3][n3];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            for (n = 0; n < n3; ++n) {
                dArray[n2][n] = fArray[n2][n];
            }
        }
        for (n = 0; n < n4; ++n) {
            double d = dArray[n][n];
            if (d == 0.0) {
                this.linearDependant = true;
                continue;
            }
            for (int i = n + 1; i < n4; ++i) {
                double d2 = dArray[i][n];
                if (d2 == 0.0) continue;
                double d3 = d2 / d;
                for (n2 = n; n2 < n3; ++n2) {
                    double d4;
                    dArray[i][n2] = d4 = dArray[i][n2] - dArray[n][n2] * d3;
                }
                dArray[i][n] = 0.0;
            }
        }
        this.matrix3Gauss = dArray;
        return dArray;
    }

    public double[][] matrix2trian_gauss(double[][] dArray) {
        int n;
        int n2;
        int n3;
        boolean bl = false;
        double[][] dArray2 = null;
        this.matrix3Gauss = null;
        this.linearDependant = false;
        if (dArray == null) {
            return null;
        }
        int n4 = n3 = dArray[0].length;
        try {
            dArray2 = new double[n3][n3];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            for (n = 0; n < n3; ++n) {
                dArray2[n2][n] = dArray[n2][n];
            }
        }
        for (n = 0; n < n4; ++n) {
            double d = dArray2[n][n];
            if (d == 0.0) {
                this.linearDependant = true;
                continue;
            }
            for (int i = n + 1; i < n4; ++i) {
                double d2 = dArray2[i][n];
                if (d2 == 0.0) continue;
                double d3 = d2 / d;
                for (n2 = n; n2 < n3; ++n2) {
                    double d4;
                    dArray2[i][n2] = d4 = dArray2[i][n2] - dArray2[n][n2] * d3;
                }
                dArray2[i][n] = 0.0;
            }
        }
        this.matrix3Gauss = dArray2;
        return dArray2;
    }

    public void sym3Matrix2Full(float[][] fArray) {
        int n;
        int n2 = n = fArray[0].length;
        for (int i = 0; i < n2; ++i) {
            for (int j = i + 1; j < n; ++j) {
                fArray[j][i] = fArray[i][j];
            }
        }
    }

    public void printMatrixFull(double[][] dArray) {
        int n;
        int n2 = n = dArray[0].length;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n3 = (int)(dArray[i][j] * 100.0 + 0.5);
                System.out.print(this.padstr(" " + n3, 3));
            }
            System.out.println("");
        }
    }

    public void printMatrixFull(float[][] fArray) {
        int n;
        int n2 = n = fArray[0].length;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n3 = (int)((double)(fArray[i][j] * 100.0f) + 0.5);
                System.out.print(this.padstr(" " + n3, 3));
            }
            System.out.println("");
        }
    }

    public void printMatrixFull_00x(float[][] fArray) {
        int n;
        int n2 = n = fArray[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                int n3 = (int)((double)(fArray[j][i] * 100.0f) + 0.5);
                System.out.print(this.padstr(" " + n3, 3));
            }
            System.out.println("");
        }
    }

    public void printMatrix(float[][] fArray) {
        int n;
        int n2 = n = fArray[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                int n3 = (int)((double)(fArray[j][i] * 100.0f) + 0.5);
                System.out.print(this.padstr(" " + n3, 3));
            }
            System.out.println("");
        }
    }

    public void printMatrix100(float[][] fArray, boolean bl) {
        int n;
        int n2 = n = fArray[0].length;
        for (int i = 0; i < n; ++i) {
            int n3 = bl ? n : i + 1;
            for (int j = 0; j < n3; ++j) {
                System.out.print(this.padstr(" " + fArray[j][i], 15));
            }
            System.out.println("");
        }
    }

    public void printVector100(float[] fArray, boolean bl) {
        int n;
        int n2 = n = fArray.length;
        for (int i = 0; i < n; ++i) {
            System.out.print(this.padstr(" " + fArray[i], 15));
            if (!bl) continue;
            System.out.println("");
        }
        if (!bl) {
            System.out.println("");
        }
    }

    public void printVector100(Float[] floatArray, boolean bl) {
        int n;
        if (floatArray == null) {
            return;
        }
        int n2 = n = floatArray.length;
        for (int i = 0; i < n; ++i) {
            if (floatArray[i] != null) {
                System.out.print(this.padstr(" " + floatArray[i].floatValue(), 15));
            } else {
                System.out.print(this.padstr(" [null]", 15));
            }
            if (!bl) continue;
            System.out.println("");
        }
        if (!bl) {
            System.out.println("");
        }
    }

    public String getMercParams() {
        float f;
        float f2;
        String string = this.flagLog == 0 ? "LIN" : "LOG";
        string = string + "," + this.pEneInt;
        if (this.flagLog == 0) {
            f2 = this.emin;
            f = this.emax;
        } else {
            f2 = (float)Math.exp(this.emin);
            f = (float)Math.exp(this.emax);
        }
        string = string + "," + this.float2strG((double)f2 / 1000000.0) + "";
        string = string + "," + this.float2strG((double)f / 1000000.0) + "";
        return string;
    }

    public String float2strG(double d) {
        int n;
        String string = "";
        string = "" + d;
        if (string.endsWith(".0")) {
            return x4mkcov.myStrReplace(string, ".0", "");
        }
        string = String.format("%g", d);
        string = x4mkcov.myStrReplace(string, "00000e", "e");
        string = x4mkcov.myStrReplace(string, "0000e", "e");
        string = x4mkcov.myStrReplace(string, "000e", "e");
        string = x4mkcov.myStrReplace(string, "00e", "e");
        if ((string = x4mkcov.myStrReplace(string, "0e", "e")).indexOf("e") < 0) {
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        if ((n = string.indexOf(".")) < 0 && string.indexOf("e") < 0) {
            string = string + ".";
        }
        return string;
    }

    public void printMatrix(float[] fArray, float[][] fArray2, PrintWriter printWriter) {
        this.printMatrix(null, fArray, fArray2, printWriter);
    }

    public void printMatrix(Float[] floatArray, float[] fArray, float[][] fArray2, PrintWriter printWriter) {
        int n;
        int n2 = 4;
        int n3 = fArray.length;
        if (this.flagExternalCorr) {
            printWriter.println("#UDERC (User's Defined Energy Range Correlation) Matrix: a<sub>ij</sub>");
            n2 = 5;
        } else {
            float f;
            float f2;
            printWriter.println("#MERC (Medium Energy Range Correlation) Matrix: a<sub>ij</sub>");
            printWriter.print("#Parameters: ");
            printWriter.print("Lin/Log: " + this.flagLog + "; ");
            if (this.flagLog == 0) {
                f2 = this.emin;
                f = this.emax;
            } else {
                f2 = (float)Math.exp(this.emin);
                f = (float)Math.exp(this.emax);
            }
            printWriter.print(" Energy: " + String.format("%.3e", Float.valueOf(f2)) + " to " + String.format("%.3e", Float.valueOf(f)) + "; ");
            printWriter.print(" Lenth: " + this.pEneInt + "; ");
            printWriter.println("");
        }
        printWriter.print(this.strpad("###########", 17));
        printWriter.print(" " + this.strpad("Err%", n2 + 1));
        for (n = 0; n < n3; ++n) {
            printWriter.print(this.strpad(" " + (n + 1), n2));
        }
        printWriter.println("");
        for (int i = 0; i < n3; ++i) {
            String string = "" + fArray[i];
            string = "" + x4mkcov.float2float(fArray[i]);
            if (string.endsWith(".0")) {
                string = string.substring(0, string.length() - 2);
            }
            printWriter.print(this.strpad(i + 1 + ")", 4));
            printWriter.print(this.strpad("" + string, 13));
            String string2 = "";
            if (floatArray != null && i < floatArray.length && floatArray[i] != null) {
                string2 = String.format("%.2f", Float.valueOf(100.0f * floatArray[i].floatValue()));
            }
            printWriter.print(this.padstr(string2, 5) + " ");
            printWriter.print("<span class=merc>");
            for (n = 0; n <= i; ++n) {
                String string3 = String.format("%.2f", Float.valueOf(fArray2[n][i]));
                if (string3.endsWith(".00")) {
                    string3 = string3.substring(0, string3.length() - 3);
                }
                if (string3.startsWith("0.")) {
                    string3 = string3.substring(1);
                }
                if (string3.startsWith("-0.")) {
                    string3 = "-" + string3.substring(2);
                }
                if (string3.length() == 1) {
                    string3 = string3 + " ";
                }
                printWriter.print(this.padstr(string3 + " ", n2));
            }
            printWriter.println("</span>");
        }
        printWriter.print(this.strpad("###########", 17));
        printWriter.print(" " + this.strpad("Err%", n2 + 1));
        for (n = 0; n < n3; ++n) {
            printWriter.print(this.strpad(" " + (n + 1), n2));
        }
        printWriter.println("");
        if (this.flagExternalCorr) {
            printWriter.println("#/UDERC-Matrix");
        } else {
            printWriter.println("#/MERC-Matrix");
        }
    }

    public void printMatrixSERCLERC(float[] fArray, boolean bl, PrintWriter printWriter) {
        this.printMatrixSERCLERC(null, fArray, bl, printWriter);
    }

    public void printMatrixSERCLERC(Float[] floatArray, float[] fArray, boolean bl, PrintWriter printWriter) {
        int n;
        float f;
        float f2;
        int n2 = fArray.length;
        int n3 = 30;
        printWriter.print("<pre>");
        if (this.flagLog == 0) {
            f2 = this.emin;
            f = this.emax;
        } else {
            f2 = (float)Math.exp(this.emin);
            f = (float)Math.exp(this.emax);
        }
        printWriter.println("");
        printWriter.print(this.strpad("###########", 17));
        printWriter.print(" Err% ");
        for (n = 0; n < n2 && n < n3; ++n) {
            printWriter.print(this.strpad(" " + (n + 1) + ")", 4));
        }
        if (n2 > n3) {
            printWriter.print(". . . " + n2 + ")");
        }
        printWriter.println("");
        for (int i = 0; i < n2 && i < n3; ++i) {
            String string = "" + fArray[i];
            string = "" + x4mkcov.float2float(fArray[i]);
            if (string.endsWith(".0")) {
                string = string.substring(0, string.length() - 2);
            }
            printWriter.print(this.strpad(i + 1 + ")", 4));
            printWriter.print(this.strpad("" + string, 13));
            String string2 = "";
            if (floatArray != null && i < floatArray.length && floatArray[i] != null) {
                string2 = String.format("%.2f", Float.valueOf(100.0f * floatArray[i].floatValue()));
            }
            printWriter.print(this.padstr(string2, 5) + " ");
            printWriter.print("<span class=merc>");
            for (n = 0; n < n2 && n <= i; ++n) {
                String string3;
                float f3 = 1.0f;
                if (bl && n != i) {
                    f3 = 0.0f;
                }
                if ((string3 = String.format("%.2f", Float.valueOf(f3))).endsWith(".00")) {
                    string3 = string3.substring(0, string3.length() - 3);
                }
                if (string3.startsWith("0.")) {
                    string3 = string3.substring(1);
                }
                if (string3.equals("0")) {
                    string3 = ".";
                }
                if (string3.length() == 1) {
                    string3 = string3 + " ";
                }
                printWriter.print(this.padstr(string3 + " ", 4));
            }
            printWriter.println("</span>");
        }
        if (n2 > n3) {
            printWriter.print(this.strpad(". . . . . . . . . . . . . . . . . .", 17));
        }
        printWriter.println("");
        printWriter.print("</pre>");
    }

    public void printArray(float[] fArray) {
        int n = fArray.length;
        for (int i = 0; i < n; ++i) {
            String string = "" + fArray[i];
            if (string.endsWith(".0")) {
                string = string.substring(0, string.length() - 2);
            }
            System.out.print(this.padstr(" " + string, 3));
        }
        System.out.println("");
    }

    public static float float2float(float f) {
        double d = 0.0;
        String string = String.format("%e", Float.valueOf(f));
        try {
            d = Double.parseDouble(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return (float)d;
    }

    public String pause(String string) {
        System.out.print("\nPause " + string + " ...");
        System.out.flush();
        DataInputStream dataInputStream = new DataInputStream(System.in);
        String string2 = null;
        try {
            string2 = dataInputStream.readLine();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return string2;
    }

    public String strpad(String string, int n) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = string;
            return string2;
        }
        String string3 = string;
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string3 = string3 + " ";
        }
        return string3;
    }

    public String padstr(String string, int n) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = string;
            return string2;
        }
        String string3 = "";
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string3 = string3 + " ";
        }
        string3 = string3 + string;
        return string3;
    }

    public static String myStrReplace(String string, String string2, String string3) {
        int n;
        int n2 = string.length();
        int n3 = string2.length();
        int n4 = string3.length();
        String string4 = "";
        int n5 = 0;
        while (n5 < n2 && (n = string.indexOf(string2, n5)) >= 0) {
            if (n > n5) {
                string4 = string4 + string.substring(n5, n);
            }
            string4 = string4 + string3;
            n5 = n + n3;
        }
        if (n5 < n2) {
            string4 = string4 + string.substring(n5, n2);
        }
        return string4;
    }
}
