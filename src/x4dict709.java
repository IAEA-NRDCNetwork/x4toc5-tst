package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-709 internal dictionary
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2021-11-29
 * @since           2011-02-27
 *
 * Program:         x4dict709.java
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

public class x4dict709
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String X4Compound;
    String CompoundType;
    public int NumericalEquiv;
    public int ZATarget = 0;
    public int ZTarget = 0;
    public int ATarget = 0;
    String UnitFamilyCode;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2019\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict709 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict709.init();
        boolean bl2 = x4dict709.exists(709, "1-H-D2O");
        sysOut.println("[1-H-D2O] chk=" + bl2);
        x4dict709 x4dict7092 = (x4dict709)x4dict709.findinx4dict(709, "1-H-D2O");
        if (x4dict7092 != null) {
            sysOut.println("[" + x4dict7092.Code + "]:\n" + x4dict7092.toString());
        }
        sysOut.println(" qqq:      chk=" + x4dict.exists(709, "qqq"));
        x4dict7092 = (x4dict709)x4dict709.findinx4dict(709, "(CUM),FY");
        if (x4dict7092 != null) {
            sysOut.println(" x4f=" + x4dict7092.toString());
        }
        if ((x4dict7092 = (x4dict709)x4dict709.findinx4dict(709, ",EN")) != null) {
            sysOut.println(" x4f=" + x4dict7092.toString());
        }
        x4dict.printDict(709);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict709() {
    }

    x4dict709(String string) {
        super(string);
        this.Code = this.X4Compound = x4dict709.strExtractStr(string, 13, 42, "").trim();
        this.NumericalEquiv = x4dict709.strExtractInt(string, 49, 55, 0);
        this.ZATarget = x4dict709.strExtractInt(string, 58, 63, 0);
        this.ZTarget = this.ZATarget / 1000;
        this.CompoundType = x4dict709.strExtractStr(string, 65, 65, "");
        this.UnitFamilyCode = x4dict709.strExtractStr(string, 48, 51, "");
        this.shortHelp = x4dict709.strExtractStr(string, 84, 113, "");
        if (this.ZATarget <= 0) {
            this.ZTarget = this.NumericalEquiv / 10000;
            this.CompoundType = this.X4Compound.endsWith("-CMP") ? "c" : (this.X4Compound.endsWith("-WTR") ? "w" : (this.X4Compound.endsWith("-D2O") ? "w" : (this.X4Compound.endsWith("-OXI") ? "o" : (this.X4Compound.endsWith("-HYD") ? "h" : (this.X4Compound.endsWith("-ALY") ? "y" : "?")))));
            this.ZATarget = this.ZTarget * 1000 + this.ATarget;
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict709.strExtractStr(string, 13, 24, "");
        return !string2.equals("");
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict709.x4dict_new=" + string.trim());
        }
        x4dict709 x4dict7092 = new x4dict709(string);
        return x4dict7092;
    }

    public static x4dict709 findinx4dict(String string) {
        x4dict709.init();
        x4dict709 x4dict7092 = (x4dict709)x4dict709.findinx4dict(string, vDict);
        return x4dict7092;
    }

    public static String getCompoundType(String string) {
        x4dict709 x4dict7092 = x4dict709.findinx4dict(string);
        if (x4dict7092 == null) {
            return null;
        }
        return x4dict7092.CompoundType;
    }

    public static String getExpansion(String string) {
        x4dict709 x4dict7092 = x4dict709.findinx4dict(string);
        if (x4dict7092 == null) {
            return "";
        }
        return x4dict7092.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init709()---");
            }
            x4dict709 x4dict7092 = new x4dict709();
            String string = x4dict709.getFileName(709);
            vDict = x4dict7092.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = "---x4dict709:  X4Compound=[" + this.X4Compound + "] " + "\tType=[" + this.CompoundType + "]" + " OldNum=" + this.NumericalEquiv + " ZA=" + this.ZATarget + " shortHelp:[" + this.shortHelp + "]";
        return string;
    }
}
