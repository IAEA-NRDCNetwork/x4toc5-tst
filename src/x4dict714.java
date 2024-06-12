package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * Internal Dictionary-714: identical to EXFOR14A.DAT (X4TOC4.F)
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-10-03
 * @since           2011-04-15
 *
 * Program:         x4dict714.java
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

public class x4dict714
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    int MF = 0;
    int MT = 0;
    int ZAProj = 0;
    int OpNum = 0;
    String C4ReaCode = "";
    String shortCode = "";
    String SF2 = "";
    String SF3 = "";
    String SF58 = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict714 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict714.init();
        boolean bl2 = x4dict714.exists(714, "((N,P),DA)+((N,N+P),DA,P)");
        sysOut.println(" ((N,P),DA)+((N,N+P),DA,P): chk=" + bl2);
        sysOut.println(" qqq:      chk=" + x4dict.exists(714, "qqq"));
        x4dict714 x4dict7142 = (x4dict714)x4dict714.findinx4dict(714, "((N,P),DA)+((N,N+P),DA,P)");
        if (x4dict7142 != null) {
            sysOut.println(" x4f=" + x4dict7142.toString());
        }
        if ((x4dict7142 = (x4dict714)x4dict714.findinx4dict(714, ",EN")) != null) {
            sysOut.println(" x4f=" + x4dict7142.toString());
        }
        if ((x4dict7142 = (x4dict714)x4dict714.findinx4dict(714, "(N,F)MASS,CHN,FY,,MXW")) != null) {
            sysOut.println(" x4f=" + x4dict7142.toString());
        }
        x4dict714.printCurrentDicts();
        x4dict714.printDict(714);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict714() {
    }

    x4dict714(String string) {
        super(string);
        String string2;
        this.Code = this.C4ReaCode = x4dict714.strExtractStr(string, 1, 48, "");
        this.statusFlag = "";
        this.strDate = "";
        this.shortHelp = "";
        this.ZAProj = x4dict714.strExtractInt(string, 49, 53, 0);
        this.MF = x4dict714.strExtractInt(string, 54, 56, 0);
        this.MT = x4dict714.strExtractInt(string, 57, 60, 0);
        this.OpNum = x4dict714.strExtractInt(string, 61, 63, 0);
        this.shortCode = string2 = this.Code;
        this.SF2 = "";
        this.SF3 = "";
        this.SF58 = "";
        int n = string2.indexOf(",");
        int n2 = string2.indexOf(")");
        if (!string2.startsWith("((") && n > 0) {
            this.shortCode = string2.substring(n);
            if (n > 1) {
                this.SF2 = string2.substring(1, n);
            }
            if (n2 > n) {
                this.SF3 = string2.substring(n + 1, n2);
            }
        }
        if (n2 > 0) {
            this.SF58 = string2.substring(n2 + 1);
        }
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict714.x4dict_new=" + string.trim());
        }
        x4dict714 x4dict7142 = new x4dict714(string);
        return x4dict7142;
    }

    public static x4dict714 findinx4dict(String string) {
        x4dict714.init();
        String string2 = string;
        if (string2.length() > 48) {
            string2 = string2.substring(0, 48);
        }
        x4dict714 x4dict7142 = (x4dict714)x4dict714.findinx4dict(string2, vDict);
        return x4dict7142;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init714()---");
            }
            x4dict714 x4dict7142 = new x4dict714();
            String string = x4dict714.getFileName(714);
            vDict = x4dict7142.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict714.strExtractStr(string, 80, 80, "");
        return string2.equals("");
    }

    public String toString() {
        String string = "---x4dict714:  MF=" + x4dict714.strpad("" + this.MF, 3) + " MT=" + x4dict714.strpad("" + this.MT, 4) + " ZAProj=" + x4dict714.strpad("" + this.ZAProj, 5) + " OpNum=" + x4dict714.strpad("" + this.OpNum, 2) + " C4ReaCode=" + x4dict714.strpad("" + this.C4ReaCode, 33) + " shortCode=" + x4dict714.strpad("" + this.shortCode, 28) + " SF2=" + x4dict714.strpad("" + this.SF2, 8) + " SF3=" + x4dict714.strpad("" + this.SF3, 8) + " SF58=" + x4dict714.strpad("" + this.SF58, 8) + " ---x4dict: " + super.toString();
        return string;
    }
}
