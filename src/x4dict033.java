package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-033 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2015-02-03
 * @since           2011-02-17
 *
 * Program:         x4dict033.java
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

public class x4dict033
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    public String ParticleCode;
    public String NumericalEquiv23;
    public String NumericalEquiv7;
    public String AllowedSF;
    public Float ZA;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2011-2014\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict033 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict033.init();
        boolean bl2 = x4dict033.exists(33, "HE3");
        sysOut.println("   HE3: chk=" + bl2);
        sysOut.println(" qqq: chk=" + x4dict.exists(33, "qqq"));
        x4dict033.init();
        x4dict033 x4dict0332 = x4dict033.findinx4dict("B*V");
        if (x4dict0332 != null) {
            sysOut.println(" x4f=" + x4dict0332.toString());
        }
        String string = "P";
        sysOut.println(" getParticleCodeExpansion('" + string + "')=" + x4dict033.getParticleCodeExpansion(string));
        string = "P";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "N";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "HE3";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "9-F-19";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "1-H-1";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "H-1";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "13-AL-27";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "AL-27";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "Al-27";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "PIN";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        string = "G";
        sysOut.println("...getMassMev('" + string + "')=" + x4dict033.getMassMev(string));
        x4dict033.printDict(33);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public x4dict033() {
    }

    public x4dict033(String string) {
        super(string);
        this.Code = this.ParticleCode = x4dict033.strExtractStr(string, 13, 25, "");
        this.NumericalEquiv23 = x4dict033.strExtractStr(string, 44, 49, "");
        this.NumericalEquiv7 = x4dict033.strExtractStr(string, 50, 54, "");
        this.AllowedSF = x4dict033.strExtractStr(string, 55, 58, "");
        this.shortHelp = x4dict033.strExtractStr(string, 59, 98, "");
        this.ZA = this.str2Float(this.NumericalEquiv23);
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict033.x4dict_new=" + string.trim());
        }
        x4dict033 x4dict0332 = new x4dict033(string);
        return x4dict0332;
    }

    public static x4dict033 findinx4dict(String string) {
        x4dict033.init();
        x4dict033 x4dict0332 = (x4dict033)x4dict033.findinx4dict(string, vDict);
        return x4dict0332;
    }

    public static double getMassMev(String string) {
        return x4dict033.getMassMev(string, 0.0);
    }

    public static double getMassMev(String string, double d) {
        x4dict033.init();
        x4dict033 x4dict0332 = x4dict033.findinx4dict(string);
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 != null) {
            return x4dict2272.MASS_mev;
        }
        if (x4dict0332 == null) {
            return d;
        }
        if (string.equals("PIN")) {
            return 139.57;
        }
        if (string.equals("PIP")) {
            return 139.57;
        }
        if (string.equals("PI0")) {
            return 134.976;
        }
        if (x4dict0332.ZA == null) {
            return d;
        }
        x4dict2272 = x4dict227.findByZA(x4dict0332.ZA);
        if (x4dict2272 != null) {
            return x4dict2272.MASS_mev;
        }
        return d;
    }

    public static Double getSpin(String string) {
        x4dict033.init();
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 != null) {
            return x4dict2272.Spin;
        }
        return null;
    }

    public static String getParticleCodeExpansion(String string) {
        x4dict033 x4dict0332 = x4dict033.findinx4dict(string);
        if (x4dict0332 == null) {
            return "";
        }
        return x4dict0332.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init033()---");
            }
            x4dict033 x4dict0332 = new x4dict033();
            String string = x4dict033.getFileName(33);
            vDict = x4dict0332.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict033.strExtractStr(string, 13, 43, "");
        return !string2.equals("");
    }

    public String toString() {
        String string = "---x4dict033:  ParticleCode=" + x4dict033.strpad(this.ParticleCode, 4) + "" + " NumericalEquiv23=[" + this.NumericalEquiv23 + "]" + " ZA=[" + this.ZA + "]" + " NumericalEquiv7=" + x4dict033.strpad(this.NumericalEquiv7, 5) + "" + " AllowedSF=[" + x4dict033.strpad(this.AllowedSF, 4) + "]" + " Short=[" + this.shortHelp + "]";
        return string;
    }
}
