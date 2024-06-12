package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-725 internal dictionary
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2010-09-17
 * @since           2010-09-17
 *
 * Program:         x4dict725.java
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

public class x4dict725
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String Units = "";
    String BasicUnits = "";
    float Multiplier = 1.0f;
    float Adder = 0.0f;
    int Operation = 0;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict725 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict9101/");
        x4dict725.init();
        boolean bl2 = x4dict725.exists(725, "MEV/A");
        sysOut.println(" MEV/A: chk=" + bl2);
        sysOut.println(" qqq:      chk=" + x4dict.exists(725, "qqq"));
        x4dict725 x4dict7252 = (x4dict725)x4dict725.findinx4dict(725, "MEV/A");
        if (x4dict7252 != null) {
            sysOut.println(" x4f=" + x4dict7252.toString());
        }
        if ((x4dict7252 = (x4dict725)x4dict725.findinx4dict(725, "AMIN")) != null) {
            sysOut.println(" x4f=" + x4dict7252.toString());
        }
        String string = "GEV/A";
        sysOut.print("...[" + string + "]:");
        sysOut.print(" BasicUnits=" + x4dict725.getBasicUnits(string));
        sysOut.print(" Operation=" + x4dict725.getOperation(string));
        sysOut.print(" Multiplier=" + x4dict725.getMultiplier(string));
        sysOut.print(" Adder=" + x4dict725.getAdder(string));
        sysOut.println("");
        x4dict725.printCurrentDicts();
        x4dict725.printDict(725);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict725() {
    }

    x4dict725(String string) {
        super(string);
        this.Units = x4dict725.strExtractStr(string, 1, 11, "");
        this.BasicUnits = x4dict725.strExtractStr(string, 12, 22, "");
        String string2 = x4dict725.strExtractStr(string, 23, 33, "");
        Float f = this.str2Float(string2);
        if (f != null) {
            this.Multiplier = f.floatValue();
        }
        if ((f = this.str2Float(string2 = x4dict725.strExtractStr(string, 34, 44, ""))) != null) {
            this.Adder = f.floatValue();
        }
        this.Operation = x4dict725.strExtractInt(string, 45, 55, 0);
        this.Code = this.Units;
        this.statusFlag = "";
        this.strDate = "";
        this.shortHelp = "";
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict725.x4dict_new=" + string.trim());
        }
        x4dict725 x4dict7252 = new x4dict725(string);
        return x4dict7252;
    }

    public static x4dict725 findinx4dict(String string) {
        x4dict725.init();
        x4dict725 x4dict7252 = (x4dict725)x4dict725.findinx4dict(string, vDict);
        return x4dict7252;
    }

    public static int getOperation(String string) {
        x4dict725 x4dict7252 = x4dict725.findinx4dict(string);
        if (x4dict7252 == null) {
            return 0;
        }
        return x4dict7252.Operation;
    }

    public static String getBasicUnits(String string) {
        x4dict725 x4dict7252 = x4dict725.findinx4dict(string);
        if (x4dict7252 == null) {
            return "?";
        }
        return x4dict7252.BasicUnits;
    }

    public static float getMultiplier(String string) {
        x4dict725 x4dict7252 = x4dict725.findinx4dict(string);
        if (x4dict7252 == null) {
            return 1.0f;
        }
        return x4dict7252.Multiplier;
    }

    public static float getAdder(String string) {
        x4dict725 x4dict7252 = x4dict725.findinx4dict(string);
        if (x4dict7252 == null) {
            return 0.0f;
        }
        return x4dict7252.Adder;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init725()---");
            }
            x4dict725 x4dict7252 = new x4dict725();
            String string = x4dict725.getFileName(725);
            vDict = x4dict7252.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict725.strExtractStr(string, 80, 80, "");
        return string2.equals("");
    }

    public String toString() {
        String string = "---x4dict725:  " + " Units     ".trim() + "=" + x4dict725.strpad("" + this.Units, 8) + " " + " BasicUnits".trim() + "=" + x4dict725.strpad("" + this.BasicUnits, 8) + " " + " Multiplier".trim() + "=" + x4dict725.strpad("" + this.Multiplier, 13) + " " + " Adder     ".trim() + "=" + x4dict725.strpad("" + this.Adder, 13) + " " + " Operation ".trim() + "=" + x4dict725.strpad("" + this.Operation, 3) + " ---x4dict: " + super.toString();
        return string;
    }
}
