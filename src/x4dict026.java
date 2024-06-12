package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-026 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2009-01-29
 * @since           2009-01-29
 *
 * Program:         x4dict026.java
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

public class x4dict026
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    public String UnitFamilyCode;
    public String Dict24Use;
    public String Dict25Use;
    public String Dict26Use;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict026 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("C:/x4load5/x4dict/");
        x4dict026.init();
        boolean bl2 = x4dict026.exists(26, "1/MILLI-EV");
        sysOut.println("  CS: chk=" + bl2);
        sysOut.println(" qqq: chk=" + x4dict.exists(26, "qqq"));
        x4dict026.init();
        x4dict026 x4dict0262 = x4dict026.findinx4dict("B*V");
        if (x4dict0262 != null) {
            sysOut.println(" x4f=" + x4dict0262.toString());
        }
        sysOut.println(" getUnitFamilyCodeExpansion('B*V')=" + x4dict026.getUnitFamilyCodeExpansion("B*V"));
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public x4dict026() {
    }

    public x4dict026(String string) {
        super(string);
        this.Code = this.UnitFamilyCode = x4dict026.strExtractStr(string, 13, 16, "");
        this.Dict24Use = x4dict026.strExtractStr(string, 44, 45, "");
        this.Dict25Use = x4dict026.strExtractStr(string, 46, 47, "");
        this.Dict26Use = x4dict026.strExtractStr(string, 47, 48, "");
        this.shortHelp = x4dict026.strExtractStr(string, 50, 99, "");
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict026.x4dict_new=" + string.trim());
        }
        x4dict026 x4dict0262 = new x4dict026(string);
        return x4dict0262;
    }

    public static x4dict026 findinx4dict(String string) {
        x4dict026.init();
        x4dict026 x4dict0262 = (x4dict026)x4dict026.findinx4dict(string, vDict);
        return x4dict0262;
    }

    public static String getUnitFamilyCodeExpansion(String string) {
        x4dict026 x4dict0262 = x4dict026.findinx4dict(string);
        if (x4dict0262 == null) {
            return "";
        }
        return x4dict0262.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init026()---");
            }
            x4dict026 x4dict0262 = new x4dict026();
            String string = x4dict026.getFileName(26);
            vDict = x4dict0262.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = "---x4dict026:  UnitFamilyCode=" + x4dict026.strpad(this.UnitFamilyCode, 4) + "" + " Dict24Use=" + x4dict026.strpad(this.Dict24Use, 2) + "" + " Dict25Use=" + x4dict026.strpad(this.Dict25Use, 2) + "" + " Dict26Use=" + x4dict026.strpad(this.Dict26Use, 2) + "" + " ---x4dict: " + super.toString();
        return string;
    }
}
