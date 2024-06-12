package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-024 internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2019-03-07
 * @since           2009-01-29
 *
 * Program:         x4dict024.java
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

public class x4dict024
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    public String Heading = "";
    public String DataType = "";
    public String FamilyCode = "";
    public String PlottingFlags = "";
    public String UnitFamilyCode = "";
    public String SpecialUseFlag = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict024.init();
        boolean bl2 = x4dict024.exists(24, "+ANG-RSL");
        sysOut.println("  CS: chk=" + bl2);
        sysOut.println(" qqq: chk=" + x4dict.exists(24, "qqq"));
        x4dict024.init();
        x4dict024.printExtDict();
        x4dict024.printByFamilyCode("*");
        x4dict024 x4dict0242 = x4dict024.findinx4dict("+ANG-RSL");
        if (x4dict0242 != null) {
            sysOut.println(" x4f=" + x4dict0242.toString());
        }
        sysOut.println("+ANG-RSL:");
        String string = x4dict024.getUnitFamilyCode("+ANG-RSL");
        if (string != null) {
            sysOut.println(" UnitFamilyCode=" + string);
        }
        if ((string = x4dict024.getDataTypeByHeading("+ANG-RSL")) != null) {
            sysOut.println(" getDataTypeByHeading=" + string);
        }
        if ((string = x4dict024.getPlottingFlags("+ANG-RSL")) != null) {
            sysOut.println(" getPlottingFlags=" + string);
        }
        if ((x4dict0242 = x4dict024.findinx4dict("MONIT-ERR")) != null) {
            sysOut.println(" x4f=" + x4dict0242.toString());
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public x4dict024() {
    }

    public x4dict024(String string) {
        super(string);
        this.Code = this.Heading = x4dict024.strExtractStr(string, 13, 23, "");
        this.DataType = x4dict024.strExtractStr(string, 44, 45, "");
        this.FamilyCode = x4dict024.strExtractStr(string, 46, 46, "");
        this.PlottingFlags = x4dict024.strExtractStr(string, 47, 53, "");
        this.UnitFamilyCode = x4dict024.strExtractStr(string, 54, 57, "");
        this.SpecialUseFlag = x4dict024.strExtractStr(string, 58, 58, "");
        this.shortHelp = x4dict024.strExtractStr(string, 59, 118, "");
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict024.x4dict_new=" + string.trim());
        }
        x4dict024 x4dict0242 = new x4dict024(string);
        return x4dict0242;
    }

    public static x4dict024 findinx4dict(String string) {
        x4dict024.init();
        x4dict024 x4dict0242 = (x4dict024)x4dict024.findinx4dict(string, vDict);
        return x4dict0242;
    }

    public static String getUnitFamilyCode(String string) {
        x4dict024 x4dict0242 = x4dict024.findinx4dict(string);
        if (x4dict0242 == null) {
            return null;
        }
        return x4dict0242.UnitFamilyCode;
    }

    public static String getDataTypeByHeading(String string) {
        x4dict024 x4dict0242 = x4dict024.findinx4dict(string);
        if (x4dict0242 == null) {
            return null;
        }
        return x4dict0242.DataType;
    }

    public static String getPlottingFlags(String string) {
        x4dict024 x4dict0242 = x4dict024.findinx4dict(string);
        if (x4dict0242 == null) {
            return null;
        }
        return x4dict0242.PlottingFlags;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init024()---");
            }
            x4dict024 x4dict0242 = new x4dict024();
            String string = x4dict024.getFileName(24);
            vDict = x4dict0242.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public static void printExtDict() {
        x4dict024.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict024 x4dict0242 = (x4dict024)vDict.elementAt(i);
            sysOut.println(x4dict024.padstr(i + 1 + ")", 5) + x4dict0242.toExtString());
        }
    }

    public static void printByFamilyCode(String string) {
        x4dict024.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict024 x4dict0242 = (x4dict024)vDict.elementAt(i);
            if (!x4dict0242.FamilyCode.equals(string)) continue;
            sysOut.println(x4dict024.padstr(i + 1 + ")", 5) + x4dict0242.toString());
        }
    }

    public String toString() {
        String string = "---x4dict024:  Heading=" + x4dict024.strpad(this.Heading + "", 10) + "" + " DataType=" + x4dict024.strpad(this.DataType + "", 2) + "" + " FamilyCode=" + x4dict024.strpad(this.FamilyCode + "", 1) + " " + " PlotFlags=" + x4dict024.strpad(this.PlottingFlags + "", 7) + "" + " UnitFamilyCode=" + x4dict024.strpad(this.UnitFamilyCode + "", 4) + "" + " SpecialUse=" + x4dict024.strpad(this.SpecialUseFlag + "", 1) + "" + " ---x4dict: " + super.toString();
        return string;
    }

    public String toExtString() {
        String string = " " + x4dict024.strpad(this.Heading + "", 10) + "" + " FamilyCode=" + x4dict024.strpad(this.FamilyCode + "", 1) + " " + " UnitFamily=" + x4dict024.strpad(this.UnitFamilyCode + "", 4) + "" + " Special=" + x4dict024.strpad(this.SpecialUseFlag + "", 1) + "" + " :: " + this.shortHelp;
        string = this.strOriginal;
        String string2 = this.DataType;
        String string3 = x4dict024dt.getDataTypeExpansion(this.DataType);
        if (!string3.equals("")) {
            string = string + "\r\n\t\t DataType\t" + string2 + "\t" + string3;
        }
        string2 = this.FamilyCode;
        string3 = x4dict024dt.getFamilyCodeExpansion(this.FamilyCode);
        if ((this.FamilyCode.equals("E") || this.FamilyCode.equals("F")) && (this.Heading.indexOf("LVL-INI") >= 0 || this.Heading.indexOf("LVL-FIN") >= 0)) {
            string3 = "";
        }
        string = string + "\r\n\t\t FamilyCode\t" + string2 + "\t" + string3;
        string2 = this.PlottingFlags;
        string = string + "\r\n\t\t PlottingFlags\t" + string2;
        string2 = x4dict024.strExtractStr(this.PlottingFlags, 1, 3, "");
        string3 = x4dict024dt.getPlottingFlagsExpansion(string2);
        if (!string3.equals("")) {
            string = string + "\r\n\t\t :independ-var\t" + string2 + "\t" + string3;
        }
        if (!(string3 = x4dict024dt.getPlottingFlagsExpansion(string2 = x4dict024.strExtractStr(this.PlottingFlags, 4, 6, ""))).equals("")) {
            string = string + "\r\n\t\t :dependent-var\t" + string2 + "\t" + string3;
        }
        string2 = x4dict024.strExtractStr(this.PlottingFlags, 7, 7, "");
        string3 = x4dict024dt.getLabCMFlagExpansion(this.PlottingFlags);
        if (!string3.equals("")) {
            string = string + "\r\n\t\t center-of-mass\t" + string2 + "\t" + string3;
        }
        string2 = this.UnitFamilyCode;
        string3 = x4dict026.getUnitFamilyCodeExpansion(this.UnitFamilyCode);
        string = string + "\r\n\t\t UnitFamily\t" + string2 + "\t" + string3;
        string2 = this.SpecialUseFlag;
        string3 = "";
        if (!string2.equals("")) {
            string = string + "\r\n\t\t SpecialUse\t" + string2 + "\t" + string3;
        }
        if (!(string2 = this.shortHelp).equals("")) {
            string = string + "\r\n\t\t Expansion\t\t" + string2;
        }
        string = string + "\r\n";
        return string;
    }
}
