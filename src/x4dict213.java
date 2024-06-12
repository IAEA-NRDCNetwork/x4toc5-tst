package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-213 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2007-09-25
 * @since           2007-09-25
 *
 * Program:         x4dict213.java
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

public class x4dict213
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String ReactionType;
    int SortFlag;
    String IndepVarFamilyCode;
    String CindaQuantity;
    String WebQuantity;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict213 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict213.init();
        boolean bl2 = x4dict213.exists(213, "CS");
        sysOut.println("  CS: chk=" + bl2);
        sysOut.println(" qqq: chk=" + x4dict.exists(213, "qqq"));
        x4dict213.init();
        x4dict213 x4dict2132 = x4dict213.findinx4dict("CS");
        if (x4dict2132 != null) {
            sysOut.println(" x4f=" + x4dict2132.toString());
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict213() {
    }

    x4dict213(String string) {
        super(string);
        this.Code = this.ReactionType = x4dict213.strExtractStr(string, 13, 16, "");
        this.IndepVarFamilyCode = x4dict213.strExtractStr(string, 55, 65, "");
        this.CindaQuantity = x4dict213.strExtractStr(string, 44, 47, "");
        this.WebQuantity = x4dict213.strExtractStr(string, 49, 51, "");
        this.SortFlag = x4dict213.strExtractInt(string, 53, 54, 0);
        this.shortHelp = x4dict213.strExtractStr(string, 66, 120, "");
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict213.x4dict_new=" + string.trim());
        }
        x4dict213 x4dict2132 = new x4dict213(string);
        return x4dict2132;
    }

    public static x4dict213 findinx4dict(String string) {
        x4dict213.init();
        x4dict213 x4dict2132 = (x4dict213)x4dict213.findinx4dict(string, vDict);
        return x4dict2132;
    }

    public static String getExpansion(String string) {
        x4dict213 x4dict2132 = x4dict213.findinx4dict(string);
        if (x4dict2132 == null) {
            return "";
        }
        return x4dict2132.shortHelp;
    }

    public static String getIndepVarFamilyCode(String string) {
        x4dict213 x4dict2132 = x4dict213.findinx4dict(string);
        if (x4dict2132 == null) {
            return "";
        }
        return x4dict2132.IndepVarFamilyCode;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init213()---");
            }
            x4dict213 x4dict2132 = new x4dict213();
            String string = x4dict213.getFileName(213);
            vDict = x4dict2132.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict213.strExtractStr(string, 13, 16, "");
        return !string2.equals("");
    }

    public String toString() {
        String string = "---x4dict213:  ReactionType=" + x4dict213.strpad(this.ReactionType, 4) + " " + " CindaQuantity=" + x4dict213.strpad(this.CindaQuantity, 4) + " " + " WebQuantity=" + x4dict213.strpad(this.WebQuantity, 4) + " " + " IndepVarFamilyCode=" + x4dict213.strpad(this.IndepVarFamilyCode, 11) + " " + " SortFlag=" + x4dict213.strpad(this.SortFlag + "", 2) + " " + " ---x4dict: " + super.toString();
        return string;
    }
}
