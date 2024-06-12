package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-025 internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-08-22
 * @since           2007-09-25
 *
 * Program:         x4dict025.java
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

public class x4dict025
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    public String Units = "?";
    public String UnitFamilyCode = "?";
    public float ConversionFactor = 1.0f;
    Float tConversionFactor = null;
    float Adder = 0.0f;
    int Operation = 0;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict025 dictExtension");
            System.exit(0);
        }
        x4dict.setDirOfArchivedDicts("x4dict/");
        boolean bl = false;
        x4dict025.init();
        x4dict025 x4dict0252 = x4dict025.findByUnitFamilyCode("YDE");
        if (x4dict0252 != null) {
            sysOut.println(" x4f=" + x4dict0252.toString());
        } else {
            sysOut.println(" x4f=" + x4dict0252);
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public x4dict025() {
    }

    public x4dict025(String string) {
        super(string);
        this.Code = this.Units = x4dict025.strExtractStr(string, 13, 23, "");
        this.UnitFamilyCode = x4dict025.strExtractStr(string, 79, 82, "");
        this.ConversionFactor = x4dict025.strExtractFloat(string, 83, 93, 1.0f);
        String string2 = x4dict025.strExtractStr(string, 83, 93, "");
        Float f = this.str2Float(string2);
        if (f != null) {
            this.ConversionFactor = f.floatValue();
        }
        this.tConversionFactor = f;
        this.shortHelp = x4dict025.strExtractStr(string, 44, 78, "");
        x4dict725 x4dict7252 = x4dict725.findinx4dict(this.Units);
        if (extDebug) {
            System.out.print(" Units=[" + this.Units + "]");
        }
        if (x4dict7252 != null) {
            if (extDebug) {
                System.out.print(" BasicUnits=" + x4dict725.getBasicUnits(this.Units));
                System.out.print(" Oper=" + x4dict725.getOperation(this.Units));
                System.out.print(" Add=" + x4dict725.getAdder(this.Units));
                System.out.print(" Mult=" + x4dict725.getMultiplier(this.Units));
                if (x4dict725.getMultiplier(this.Units) != this.ConversionFactor) {
                    System.out.print(" <>");
                }
            }
            if (f == null) {
                this.ConversionFactor = x4dict725.getMultiplier(this.Units);
            }
            if (x4dict725.getOperation(this.Units) == 9) {
                this.Operation = x4dict725.getOperation(this.Units);
            }
            this.Adder = x4dict725.getAdder(this.Units);
        }
        if (extDebug) {
            System.out.print(" CovFactor=" + f + ":" + this.ConversionFactor);
            System.out.println("");
        }
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict025.x4dict_new=" + string.trim());
        }
        x4dict025 x4dict0252 = new x4dict025(string);
        return x4dict0252;
    }

    public static x4dict025 findinx4dict(String string) {
        x4dict025.init();
        x4dict025 x4dict0252 = (x4dict025)x4dict025.findinx4dict(string, vDict);
        return x4dict0252;
    }

    public static x4dict025 findByUnitFamilyCode(String string) {
        x4dict025 x4dict0252;
        int n;
        x4dict025.init();
        for (n = 0; n < vDict.size(); ++n) {
            x4dict0252 = (x4dict025)vDict.elementAt(n);
            if (!x4dict0252.UnitFamilyCode.equals(string) || x4dict0252.ConversionFactor != 1.0f) continue;
            return x4dict0252;
        }
        for (n = 0; n < vDict.size(); ++n) {
            x4dict0252 = (x4dict025)vDict.elementAt(n);
            if (!x4dict0252.UnitFamilyCode.equals(string)) continue;
            return x4dict0252;
        }
        return null;
    }

    public static String getUnitFamilyCode(String string) {
        x4dict025 x4dict0252 = x4dict025.findinx4dict(string);
        if (x4dict0252 == null) {
            return null;
        }
        return x4dict0252.UnitFamilyCode;
    }

    public static float getConversionFactor(String string) {
        x4dict025 x4dict0252 = x4dict025.findinx4dict(string);
        if (x4dict0252 == null) {
            return 0.0f;
        }
        return x4dict0252.ConversionFactor;
    }

    public static String getBasicUnits(String string) {
        x4dict025.init();
        String string2 = x4dict025.getUnitFamilyCode(string);
        if (string2 == null) {
            return null;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict025 x4dict0252 = (x4dict025)vDict.elementAt(i);
            if (!x4dict0252.UnitFamilyCode.equals(string2) || x4dict0252.tConversionFactor == null || x4dict0252.ConversionFactor != 1.0f) continue;
            return x4dict0252.Units;
        }
        return string;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init025()---");
            }
            x4dict025 x4dict0252 = new x4dict025();
            String string = x4dict025.getFileName(25);
            vDict = x4dict0252.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = "---x4dict025:  Units=" + x4dict025.strpad(this.Units, 10) + "" + " UnitFamily=" + x4dict025.strpad(this.UnitFamilyCode, 4) + "" + " ConvFactor=" + x4dict025.strpad(this.ConversionFactor + "", 12) + " " + " Operation=" + x4dict025.strpad(this.Operation + "", 2) + " " + " BasicUnits=" + x4dict025.strpad(x4dict025.getBasicUnits(this.Code) + "", 10) + " " + " Adder=" + this.Adder + " " + " ---x4dict: " + super.toString();
        return string;
    }
}
