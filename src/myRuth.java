package zvv.x4;

/**
 * Convert quantity: Rutherford Ratio to MB/SR
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-08-28
 * @since           2017-11-13
 *
 * Program:         myRuth.java
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

import java.io.PrintWriter;

class myRuth {
    boolean extDebug = false;
    x4cm2lab c2lab = null;
    int iAlgorithm = 0;

    myRuth() {
    }

    public static void main(String[] stringArray) {
        String string;
        int n;
        double d = 1.0;
        PrintWriter printWriter = new PrintWriter(System.out, true);
        System.out.println("Hello!!");
        myRuth myRuth2 = new myRuth();
        for (n = 0; n < stringArray.length; ++n) {
            string = stringArray[n];
            if (!string.toLowerCase().equals("-debug")) continue;
            myRuth2.extDebug = true;
            System.out.println("   debug=[" + myRuth2.extDebug + "]");
        }
        int n2 = 4009;
        float f = 9.012183f;
        int n3 = 2004;
        float f2 = 4.002603f;
        float f3 = 174.4f;
        f3 = 10.0f;
        float f4 = 50500.0f;
        d = myRuth2.getRuthTerm(n2, n3, f, f2, f4, f3);
        float f5 = 0.881f;
        float f6 = (float)((double)f5 * d);
        System.out.println("\n ZATrg=" + n2 + " ZAPrj=" + n3 + " aTrg=" + f + " aPrj=" + f2 + " Ene=" + f4 + " Theta=" + f3);
        System.out.println(" Energy=" + f4 + " convF=" + d + " yy0=" + f5 + " yy1=" + f6 + "(mb/sr)" + "\n     orig: ang-cm=14.4(deg)" + " data-cm=240.6(mb/sr)" + " data=496.5357(mb/sr)");
        System.exit(0);
        n2 = 6012;
        n3 = 2004;
        f = 12.0f;
        f2 = 4.002603f;
        f3 = 170.0f;
        n2 = 72000;
        f = 196.96655f;
        n2 = 72197;
        f = 196.96655f;
        n3 = 2004;
        f2 = 4.0026f;
        f3 = 166.0f;
        f4 = 4100.0f;
        d = myRuth2.getRuthTerm(n2, n3, f, f2, f4, f3);
        f5 = 7.0f;
        f6 = (float)((double)f5 * d);
        System.out.println("\n ZATrg=" + n2 + " ZAPrj=" + n3 + " aTrg=" + f + " aPrj=" + f2 + " Ene=" + f4 + " Theta=" + f3);
        System.out.println(" Energy=" + f4 + " convF=" + d + " yy0=" + f5 + " yy1=" + f6 + " orig:yy1=" + 65.0);
        f4 = 4280.0f;
        d = myRuth2.getRuthTerm(n2, n3, f, f2, f4, f3);
        f5 = 55.0f;
        f6 = (float)((double)f5 * d);
        if (myRuth2.extDebug) {
            System.out.println("\n");
        }
        System.out.println(" Energy=" + f4 + " convF=" + d + " yy0=" + f5 + " yy1=" + f6 + " orig:yy1=" + 455.0);
        f4 = 4290.0f;
        d = myRuth2.getRuthTerm(n2, n3, f, f2, f4, f3);
        f5 = 75.0f;
        f6 = (float)((double)f5 * d);
        if (myRuth2.extDebug) {
            System.out.println("\n");
        }
        System.out.println(" Energy=" + f4 + " convF=" + d + " yy0=" + f5 + " yy1=" + f6 + " orig:yy1=" + 611.0);
        boolean bl = false;
        for (n = 0; n < stringArray.length; ++n) {
            String string2;
            string = stringArray[n];
            if (string.toLowerCase().equals("-debug")) {
                myRuth2.extDebug = true;
                continue;
            }
            if (string.toLowerCase().startsWith("ang=")) {
                string2 = string.substring(4);
                f3 = myRuth2.str2float(string2, f3);
                System.out.println("   Theta=[" + f3 + "]");
                continue;
            }
            if (!string.toLowerCase().startsWith("e=")) continue;
            string2 = string.substring(2);
            f4 = myRuth2.str2float(string2, f3);
            System.out.println("   ene=[" + f4 + "]");
        }
        d = myRuth2.getRuthTerm(n2, n3, f, f2, f4, f3);
        if (myRuth2.extDebug) {
            System.out.println("\n");
        }
        System.out.println("\n ZATrg=" + n2 + " ZAPrj=" + n3 + " aTrg=" + f + " aPrj=" + f2 + " Ene=" + f4 + " Theta=" + f3);
        System.out.println(" Energy=" + f4 + " convF=" + d + " yy0=" + f5 + " yy1=" + f6 + " orig:yy1=" + 611.0);
    }

    public void set_x4cm2lab(x4cm2lab x4cm2lab2) {
        this.c2lab = x4cm2lab2;
    }

    public double getRuthTerm(int n, int n2, float f, float f2, float f3, float f4) {
        if (f4 == 180.0f) {
            f4 = 179.9999f;
        }
        double d = Math.cos((double)f4 / 180.0 * Math.PI);
        double d2 = 1.0 - d * d;
        this.iAlgorithm = 0;
        double d3 = n2 / 1000;
        double d4 = f2;
        double d5 = n / 1000;
        double d6 = f;
        if (this.extDebug) {
            System.out.println(" Z1=" + d3 + " Z2=" + d5 + " M1=" + d4 + " M2=" + d6);
        }
        double d7 = d3 * d5 / (double)f3;
        d7 *= d7;
        double d8 = d6 * d6 - d4 * d4 * d2;
        if (this.extDebug) {
            System.out.println("getRuthTerm: M2**2=" + (float)(d6 * d6) + " M1**2=" + (float)(d4 * d4) + " M1^2*sin2=" + (float)(d4 * d4 * d2) + " sin**2=" + (float)d2 + " pp2=" + (float)d8);
        }
        if (d8 <= 0.0) {
            this.iAlgorithm = 2;
            if (this.c2lab == null) {
                return 0.0;
            }
            if (this.c2lab.THETA0cm == null) {
                this.c2lab.lab2cm(f3, f4, 1.0);
                if (this.extDebug) {
                    System.out.println("getRuthTerm: ang.lab:" + f4 + " -> ang.cm:" + (float)this.c2lab.thetap);
                }
                if (("" + this.c2lab.thetap).equals("NaN")) {
                    return 0.0;
                }
                f4 = (float)this.c2lab.thetap;
                this.c2lab.THETA0cm = new Double(this.c2lab.thetap);
            } else {
                if (this.extDebug) {
                    System.out.println("getRuthTerm: ang.lab:" + f4 + " use original ang.cm:" + this.c2lab.THETA0cm.floatValue());
                }
                f4 = this.c2lab.THETA0cm.floatValue();
            }
            return this.getRuthTerm0x(n, n2, f, f2, f3, f4);
        }
        this.iAlgorithm = 1;
        d8 = Math.sqrt(d8);
        double d9 = d8 + d6 * d;
        d9 *= d9;
        double d10 = d6 * d2 * d2 * d8;
        if (this.extDebug) {
            System.out.println(" pp1=" + d7 + " pp2=" + d8 + "\n pp3=" + d9 + " pp4=" + d10);
        }
        double d11 = 5183743.6;
        double d12 = d11 * d7 * d9 / d10;
        if (this.extDebug) {
            System.out.println(" sigruth=" + d12);
        }
        return d12;
    }

    public double getRuthTerm0x(int n, int n2, float f, float f2, float f3, float f4) {
        double d;
        double d2;
        this.iAlgorithm = 2;
        double d3 = 6.58211889E-16;
        double d4 = 137.03599976;
        double d5 = 9.31494013E8;
        double d6 = 2.99792458E8;
        double d7 = (double)f3 * 1000.0;
        double d8 = Math.cos((double)f4 / 180.0 * Math.PI);
        int n3 = n;
        int n4 = n2;
        double d9 = f;
        double d10 = d2 = (double)f2;
        double d11 = n3 / 1000;
        double d12 = n4 / 1000;
        double d13 = 0.0;
        double d14 = d9 / d10;
        double d15 = 2.0 * d5 / (d3 * d6 * 1.0E14 * (d3 * d6 * 1.0E14));
        double d16 = d5 / (2.0 * d4 * d4);
        double d17 = Math.sqrt(d15 * d2 * d7) * d14 / (d14 + 1.0);
        double d18 = d11 * d12 * Math.sqrt(d16 * d2 / d7);
        if (n4 != n3) {
            d = d18 * d18 / (d17 * d17 * (1.0 - d8) * (1.0 - d8));
        } else {
            double d19 = Math.log((1.0 + d8) / (1.0 - d8));
            double d20 = Math.cos(d18 * d19);
            int n5 = d13 % 2.0 == 0.0 ? 1 : -1;
            double d21 = d20 * (double)n5 / (2.0 * d13 + 1.0);
            double d22 = d21 + (1.0 + d8 * d8) / (1.0 - d8 * d8);
            d = d22 * 2.0 * (d18 * d18 / (d17 * d17 * (1.0 - d8 * d8)));
        }
        return d *= 1000.0;
    }

    public float str2float(String string, float f) {
        try {
            float f2 = Float.parseFloat(string);
            return f2;
        }
        catch (Exception exception) {
            return f;
        }
    }
}
