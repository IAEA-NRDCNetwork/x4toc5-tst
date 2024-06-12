package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-236 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2009-02-19
 * @since           2009-02-19
 *
 * Program:         x4dict236.java
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

public class x4dict236
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String SF58;
    String ReactionType;
    String UnitFamilyCode;
    boolean resParamFlag = false;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict236 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict236.init();
        boolean bl2 = x4dict236.exists(236, "(CUM),FY");
        sysOut.println(" (CUM),FY: chk=" + bl2);
        sysOut.println(" qqq:      chk=" + x4dict.exists(236, "qqq"));
        x4dict236 x4dict2362 = (x4dict236)x4dict236.findinx4dict(236, "(CUM),FY");
        if (x4dict2362 != null) {
            sysOut.println(" x4f=" + x4dict2362.toString());
        }
        if ((x4dict2362 = (x4dict236)x4dict236.findinx4dict(236, ",EN")) != null) {
            sysOut.println(" x4f=" + x4dict2362.toString());
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict236() {
    }

    x4dict236(String string) {
        super(string);
        this.Code = this.SF58 = x4dict236.strExtractStr(string, 13, 42, "");
        this.ReactionType = x4dict236.strExtractStr(string, 44, 46, "");
        this.UnitFamilyCode = x4dict236.strExtractStr(string, 48, 51, "");
        this.shortHelp = x4dict236.strExtractStr(string, 53, 100, "");
        if (x4dict236.strExtractStr(string, 52, 52, "").equals(".")) {
            this.resParamFlag = true;
        }
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict236.x4dict_new=" + string.trim());
        }
        x4dict236 x4dict2362 = new x4dict236(string);
        return x4dict2362;
    }

    public static x4dict236 findinx4dict(String string) {
        x4dict236.init();
        x4dict236 x4dict2362 = (x4dict236)x4dict236.findinx4dict(string, vDict);
        return x4dict2362;
    }

    public static String getReactionType(String string) {
        x4dict236 x4dict2362 = x4dict236.findinx4dict(string);
        if (x4dict2362 == null) {
            return null;
        }
        return x4dict2362.ReactionType;
    }

    public static String getExpansion(String string) {
        x4dict236 x4dict2362 = x4dict236.findinx4dict(string);
        if (x4dict2362 == null) {
            return "";
        }
        return x4dict2362.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init236()---");
            }
            x4dict236 x4dict2362 = new x4dict236();
            String string = x4dict236.getFileName(236);
            vDict = x4dict2362.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = "---x4dict236:  SF58=" + x4dict236.strpad(this.SF58, 28) + " " + " ReactionType=" + x4dict236.strpad(this.ReactionType, 4) + " " + " UnitFamilyCode=" + x4dict236.strpad(this.UnitFamilyCode, 4) + " " + " resParam=" + this.resParamFlag + " " + " ---x4dict: " + super.toString();
        return string;
    }
}
