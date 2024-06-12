package zvv.x4;

import java.io.PrintWriter;

/**
 * EXFOR DATA: convert CM to Lab
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-01-28
 * @since           2015-02-02
 *
 * Program:         x4cm2lab.java
 * Author:          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * Created:         2015-02-02
 * Last modified:   2022-01-28
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

public class x4cm2lab {
    static PrintWriter sysOut0 = new PrintWriter(System.out, true);
    boolean extDebug = false;
    PrintWriter sysOut = null;
    double Energy = 0.0;
    double dEnergy = 0.0;
    double Data = 0.0;
    double dData = 0.0;
    double ThetaLab = 0.0;
    boolean flagEN_CM = false;
    boolean flagANG_CM = false;
    boolean flagDATA_CM = false;
    boolean flagRutherfordRatio = false;
    double qvaluekev = 0.0;
    double M1 = 0.0;
    double M2 = 0.0;
    double M3 = 0.0;
    double M4 = 0.0;
    double SigmaLab = 0.0;
    double THETA1 = 0.0;
    double dSigmaLab = 0.0;
    double Ratio = 0.0;
    String strRatio = "";
    Double THETA0cm = null;
    Double Theta0max = null;
    Double Theta1max = null;
    boolean warningTheta0max = false;
    int ierr = 0;
    double E1 = 0.0;
    double E3 = 0.0;
    double E4 = 0.0;
    double PHI0 = 0.0;
    double Sigma = 0.0;
    double dSigma = 0.0;
    double dSigma_rel = 0.0;
    double ecm = 0.0;
    double thetap = 0.0;
    double sigmap = 0.0;
    double dsigmap = 0.0;
    double E1cm = 0.0;
    double E2cm = 0.0;
    double E3cm = 0.0;
    double E4cm = 0.0;
    double phi = 0.0;
    double sigmaphi = 0.0;
    double sigmaphip = 0.0;
    double sigmaphi2sigma = 0.0;
    double e_inv = 0.0;
    double sigmathet_inv = 0.0;
    double sigmaphi_inv = 0.0;
    double theta_inv = 0.0;
    double phi_inv = 0.0;
    String errMsg1 = null;
    String errMsg2 = null;
    int iErr = 0;
    int iWarning = 0;

    public static void main(String[] stringArray) {
        sysOut0 = new PrintWriter(System.out, true);
        sysOut0.println(" x4cm2lab");
        sysOut0.println(" V.Zerkin, IAEA, Vienna, 2015\n");
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4cm2lab x4cm2lab2 = new x4cm2lab();
        x4cm2lab2.setPrintWriter(sysOut0);
        x4reaction x4reaction2 = new x4reaction(' ', "3-LI-6(D,P)3-LI-7,,DA");
        x4reaction2 = new x4reaction(' ', "3-LI-7(A,EL)3-LI-7,,DA");
        x4reaction2 = new x4reaction(' ', "2-HE-3(D,EL)2-HE-3,,DA");
        x4cm2lab2.set_x4reaction("3-LI-7(A,EL)3-LI-7,,DA");
        x4cm2lab2.mf4cm3flags(false, true, false);
        x4cm2lab2.mf4cm3flags(false, true, true);
        x4cm2lab2.mf4sig2lab(2489000.0, 10000.0, 0.07837, 0.004213, 150.0, 0.0);
        x4cm2lab2.mf4sig2lab(2592000.0, 10000.0, 0.1003, 0.00253, 150.0, 0.0);
    }

    public void sysOut_println(String string) {
        if (this.sysOut != null) {
            this.sysOut.println(string);
        }
    }

    public void sysOut_print(String string) {
        if (this.sysOut != null) {
            this.sysOut.print(string);
        }
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.sysOut = printWriter;
    }

    x4cm2lab() {
    }

    public void set_x4reaction(String string) {
        x4reaction x4reaction2 = new x4reaction(' ', string);
        this.sysOut_println("Reaction: " + x4reaction2.reacode);
        this.set_x4reaction(x4reaction2);
    }

    public void set_x4reaction(x4reaction x4reaction2) {
        this.setM1234MeV(x4reaction2.xProj1.MASS_mev, x4reaction2.xTarg1.MASS_mev, x4reaction2.xEjec1.MASS_mev, x4reaction2.xProd1.MASS_mev);
    }

    public void setM1234MeV(double d, double d2, double d3, double d4) {
        this.M1 = d / 931.4941;
        this.M2 = d2 / 931.4941;
        this.M3 = d3 / 931.4941;
        this.M4 = d4 / 931.4941;
        this.qvaluekev = (d + d2 - (d3 + d4)) * 1000.0;
        this.sysOut_println(" am1_u=" + (float)this.M1 + "\t M1_mev=" + (float)d);
        this.sysOut_println(" am2_u=" + (float)this.M2 + "\t M2_mev=" + (float)d2);
        this.sysOut_println(" am3_u=" + (float)this.M3 + "\t M3_mev=" + (float)d3);
        this.sysOut_println(" am4_u=" + (float)this.M4 + "\t M4_mev=" + (float)d4);
        this.sysOut_println(" Q_kev=" + this.qvaluekev);
    }

    public void mf4cm3flags(boolean bl, boolean bl2, boolean bl3) {
        this.flagEN_CM = bl;
        this.flagANG_CM = bl2;
        this.flagDATA_CM = bl3;
    }

    public void mf4sig2lab(double d, double d2, double d3, double d4, double d5, double d6) {
        this.Energy = d;
        this.dEnergy = d2;
        if (this.flagEN_CM) {
            double d7 = 1.0 + this.M1 / this.M2;
            this.Energy *= d7;
            this.dEnergy *= d7;
        }
        this.Data = d3;
        this.dData = d4;
        this.ThetaLab = d5;
        this.cm2lab(this.Energy / 1000000.0, this.qvaluekev / 1000.0, d5, this.M1, this.M2, this.M3, this.M4, d3, d4, this.flagANG_CM);
        if (!this.flagRutherfordRatio && this.flagDATA_CM) {
            this.Data = this.SigmaLab;
            this.dData = this.dSigmaLab;
        }
        this.ThetaLab = this.THETA1;
        if (this.flagANG_CM) {
            this.ThetaLab = (double)((int)(this.THETA1 * 10.0 + 0.5)) / 10.0;
        }
        this.sysOut_println("\tEnLab=" + (float)this.Energy / 1000.0f + "keV" + "\n" + "\tThetaCM=" + (float)d5 + "\tSigCM=" + (float)d3 * 1000.0f + " mb/sr");
        this.sysOut_println("\tThetaLab=" + (float)this.ThetaLab + "\tSigLab=" + (float)this.Data * 1000.0f + " mb/sr");
    }

    public void cm2lab(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, boolean bl) {
        double d10;
        double d11;
        double d12;
        double d13;
        double d14;
        double d15;
        this.SigmaLab = d8;
        this.THETA0cm = null;
        this.THETA1 = d3;
        this.warningTheta0max = false;
        double d16 = d + d2;
        double d17 = d4 * d7 * d / d16 / (d4 + d5) / (d6 + d7);
        double d18 = d4 * d6 * d / d16 / (d4 + d5) / (d6 + d7);
        double d19 = d5 * d6 / (d4 + d5) / (d6 + d7) * (1.0 + d4 / d5 * (d2 / d16));
        double d20 = d5 * d7 / (d4 + d5) / (d6 + d7) * (1.0 + d4 / d5 * (d2 / d16));
        if (d4 > d5 && this.Theta0max == null) {
            this.Theta0max = new Double(Math.acos(-d5 / d4) * 180.0 / Math.PI);
            d15 = Math.acos(-d5 / d4);
            d14 = Math.cos(d15);
            d13 = Math.sin(d15);
            d12 = Math.sqrt(d4 * d6 / d5 / d7 / (1.0 + (d4 + d5) / d5 * d2 / d));
            d11 = d13 / (d12 + d14);
            d10 = Math.atan(d11);
            this.Theta1max = d10 * 180.0 / Math.PI;
            if (this.Theta1max < 0.0) {
                this.Theta1max = 180.0 + this.Theta1max;
            }
        }
        if (bl) {
            this.THETA0cm = new Double(d3);
            d15 = d3 * Math.PI / 180.0;
            d14 = Math.cos(d15);
            d13 = Math.sin(d15);
            d12 = Math.sqrt(d4 * d6 / d5 / d7 / (1.0 + (d4 + d5) / d5 * d2 / d));
            d11 = d13 / (d12 + d14);
            d10 = Math.atan(d11);
            this.THETA1 = d10 * 180.0 / Math.PI;
            if (this.THETA1 < 0.0) {
                this.THETA1 = 180.0 + this.THETA1;
            }
            if (d4 > d5 && d3 > this.Theta0max) {
                this.warningTheta0max = true;
            }
            this.sysOut_println("\t.............THETA1_Lab=" + (float)this.THETA1);
        }
        d10 = this.THETA1 * Math.PI / 180.0;
        double d21 = Math.cos(d10);
        double d22 = Math.sin(d10);
        double d23 = d22 * d22;
        double d24 = d21 + Math.sqrt(d20 / d18 - d23);
        double d25 = d18 * d24 * d24;
        if (d18 != 0.0 && d20 / d18 - d23 > 0.0) {
            this.Ratio = Math.sqrt(d17 * d19) * Math.sqrt(d20 / d18 - d23) / d25;
            this.SigmaLab = d8 / this.Ratio;
            this.dSigmaLab = d9 / this.Ratio;
            this.ierr = 0;
        } else {
            if (d18 == 0.0) {
                this.strRatio = "Problem:      A13=" + d18 + " CM2LabFactor=NaN (use:factor=1)";
                this.ierr = 1;
            } else {
                this.strRatio = "Problem:      A24=" + d20 + " A13=" + d18 + " SIN2=" + d23 + " CM2LabFactor=NaN (use:factor=1)";
                this.ierr = 1;
            }
            this.Ratio = 1.0;
            this.SigmaLab = d8 / this.Ratio;
            this.dSigmaLab = d9 / this.Ratio;
        }
        this.sysOut_println("<pre>-------");
        this.sysOut_println("\tTHETA0=" + (float)d3 + " THETA1=" + (float)this.THETA1 + " E1=" + (float)d);
        this.sysOut_println("\tET=" + (float)d16 + " QVALUE=" + (float)d2);
        this.sysOut_println("\tA14=" + d17 + "\tA13=" + d18);
        this.sysOut_println("\tA23=" + d19 + "\tA24=" + d20);
        this.sysOut_println("\tA14+A13+A23+A24=" + (float)(d17 + d18 + d19 + d20));
        this.sysOut_println("\tA13.GT.A24=" + (d18 > d20));
        this.sysOut_println("\tCOS1=" + d21);
        this.sysOut_println("\tRAD1=" + d10);
        this.sysOut_println("\tSIN2=" + d23);
        this.sysOut_println("\tE3ET=" + (float)d25);
        this.sysOut_println("\tRatio=" + this.Ratio);
        this.sysOut_println("\tSigmaCM=" + d8);
        this.sysOut_println("\tSigmaLab=" + this.SigmaLab);
        this.sysOut_println("</pre>");
    }

    public void labtocm_ini() {
        this.E1 = 0.0;
        this.E3 = 0.0;
        this.E4 = 0.0;
        this.PHI0 = 0.0;
        this.ecm = 0.0;
        this.thetap = 0.0;
        this.sigmap = 0.0;
        this.phi = 0.0;
        this.sigmaphi = 0.0;
        this.sigmaphip = 0.0;
        this.sigmaphi2sigma = 0.0;
        this.E1cm = 0.0;
        this.E2cm = 0.0;
        this.E3cm = 0.0;
        this.E4cm = 0.0;
        this.e_inv = 0.0;
        this.sigmathet_inv = 0.0;
        this.sigmaphi_inv = 0.0;
        this.theta_inv = 0.0;
        this.errMsg1 = null;
        this.errMsg2 = null;
        this.iErr = 0;
        this.iWarning = 0;
    }

    public void lab2cm(double d, double d2, double d3) {
        double d4 = this.M1;
        double d5 = this.M2;
        double d6 = this.M3;
        double d7 = this.M4;
        double d8 = 931.4941;
        double d9 = d;
        double d10 = (d4 + d5 - d6 - d7) * d8;
        this.sysOut_println("\tQvalue=" + (float)(d10 *= 1000.0));
        d10 = this.qvaluekev;
        this.sysOut_println("\t+++1+++ Qvalue=" + (float)d10);
        this.labtocm_ini();
        this.E1 = d;
        double d11 = d6 + d7;
        this.ecm = d9 * d5 / (d4 + d5);
        this.E1cm = d9 * d5 * d5 / (d4 + d5) / (d4 + d5);
        this.E2cm = d9 * d4 * d5 / (d4 + d5) / (d4 + d5);
        double d12 = d + d10;
        double d13 = this.M1 * this.M4 * d / d12 / (this.M1 + this.M2) / (this.M3 + this.M4);
        double d14 = this.M1 * this.M3 * d / d12 / (this.M1 + this.M2) / (this.M3 + this.M4);
        double d15 = this.M2 * this.M3 / (this.M1 + this.M2) / (this.M3 + this.M4) * (1.0 + this.M1 / this.M2 * (d10 / d12));
        double d16 = this.M2 * this.M4 / (this.M1 + this.M2) / (this.M3 + this.M4) * (1.0 + this.M1 / this.M2 * (d10 / d12));
        this.E4cm = d15 * d12;
        this.E3cm = d16 * d12;
        double d17 = d2 * Math.PI / 180.0;
        double d18 = d3;
        double d19 = Math.sqrt(d4 / d5 * d6 / d7 * (this.ecm / (this.ecm + d10)));
        double d20 = d17 + Math.asin(d19 * Math.sin(d17));
        double d21 = d14 + d16 + 2.0 * Math.sqrt(d13 * d15) * Math.cos(d20);
        this.E3 = d21 * d12;
        double d22 = Math.PI - d20;
        double d23 = d13 + d15 + 2.0 * Math.sqrt(d13 * d15) * Math.cos(d22);
        this.E4 = d23 * d12;
        double d24 = Math.sqrt(d6 * d21 / (d7 * d23)) * Math.sin(d17);
        this.phi = Math.asin(d24);
        this.PHI0 = this.phi * 180.0 / Math.PI;
        this.PHI0 = (double)((int)(this.PHI0 * 10.0 + 0.5)) / 10.0;
        double d25 = Math.sin(d17) * Math.sin(d17);
        double d26 = Math.sin(d20) * Math.sin(d20);
        double d27 = Math.sin(d22) * Math.sin(d22);
        double d28 = Math.cos(d22) * Math.cos(d22);
        this.sigmap = d18 * d25 / d26 * Math.cos(d20 - d17);
        if (d28 > 0.0 && Math.cos(d22 - this.phi) != 0.0) {
            this.sigmaphi2sigma = d25 / d28 * Math.cos(d20 - d17) / Math.cos(d22 - this.phi);
            if (d18 > 0.0) {
                this.sigmaphi = d18 * d25 / d28 * Math.cos(d20 - d17) / Math.cos(d22 - this.phi);
            }
            if (d18 > 0.0) {
                this.sigmaphip = this.sigmaphi * d28 / d27 * Math.cos(d22 - this.phi);
            }
        }
        this.thetap = d20 * 180.0 / Math.PI;
        this.sysOut_println(" direct kinematics 2(1,3)4 in cm");
        this.sysOut_println(" energy=" + this.ecm + "  theta=" + this.thetap + "  xsec=" + this.sigmap);
    }
}
