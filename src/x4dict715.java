package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * Internal Dictionary-715: identical to V.Pronyaev's table
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-04-22
 * @since           2011-09-21
 *
 * Program:         x4dict715.java
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

public class x4dict715
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    int MF = 0;
    int MT = 0;
    int LR = 0;
    String SF20 = "";
    String SF2 = "";
    String SF2exc = "";
    String SF3 = "";
    String SF4 = "";
    String SF5 = "";
    String SF6 = "";
    String SF7 = "";
    String SF27 = "";
    boolean flagSF2exc = false;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2011-2017\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict715 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict715.init();
        boolean bl2 = x4dict715.exists(715, "((N,P),DA)+((N,N+P),DA,P)");
        sysOut.println(" ((N,P),DA)+((N,N+P),DA,P): chk=" + bl2);
        sysOut.println(" qqq:      chk=" + x4dict.exists(715, "qqq"));
        x4dict715 x4dict7152 = (x4dict715)x4dict715.findinx4dict(715, "((N,P),DA)+((N,N+P),DA,P)");
        if (x4dict7152 != null) {
            sysOut.println(" x4f=" + x4dict7152.toString());
        }
        if ((x4dict7152 = (x4dict715)x4dict715.findinx4dict(715, ",EN")) != null) {
            sysOut.println(" x4f=" + x4dict7152.toString());
        }
        if ((x4dict7152 = x4dict715.find715("N", "G", "56-BA-135", "", "DE", "")) != null) {
            sysOut.println(" x4f=" + x4dict7152.toString());
        }
        x4dict715.printCurrentDicts();
        x4dict715.printDict(715);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict715() {
    }

    x4dict715(String string) {
        super(string);
        this.statusFlag = "";
        this.strDate = "";
        this.shortHelp = "";
        string = x4dict715.strpad(string, 72);
        this.MF = x4dict715.strExtractInt(string, 1, 4, 0);
        this.MT = x4dict715.strExtractInt(string, 5, 9, 0);
        this.LR = x4dict715.strExtractInt(string, 10, 14, 0);
        this.SF2 = x4dict715.strExtractStr(string, 18, 26, "");
        this.SF3 = x4dict715.strExtractStr(string, 27, 35, "");
        this.SF4 = x4dict715.strExtractStr(string, 36, 44, "");
        this.SF5 = x4dict715.strExtractStr(string, 45, 53, "");
        this.SF6 = x4dict715.strExtractStr(string, 54, 60, "");
        this.SF7 = x4dict715.strExtractStr(string, 63, 70, "");
        this.Code = this.SF27 = "(" + this.SF2 + "," + this.SF3 + ")" + this.SF4 + "," + this.SF5 + "," + this.SF6 + "," + this.SF7;
        this.SF20 = this.SF2;
        int n = this.SF2.indexOf("-");
        if (n > 0) {
            this.SF2exc = this.SF2.substring(n + 1);
            this.SF2 = this.SF20.substring(0, n);
            this.flagSF2exc = true;
        }
        if (this.SF2.equals("Z")) {
            this.SF2 = "*";
        }
    }

    public static String strExtractStr(String string, int n, int n2, String string2) {
        String string3 = x4dict.strExtractStr(string, n, n2, string2);
        if ((string3 = string3.trim()).toUpperCase().equals("BLANK")) {
            string3 = "";
        } else if (string3.equals("")) {
            string3 = "*";
        }
        return string3.trim();
    }

    public String getFieldValue(String string, int n, int n2) {
        if ((string = this.strReplaceChar(string.substring(n, n2), ' ', "")).equals("BLANK")) {
            string = "";
        } else if (string.equals("")) {
            string = "*";
        }
        return string;
    }

    public boolean cmp1(String string, String string2) {
        if (extDebug) {
            System.out.println("\td:[" + string + "]" + " r:[" + string2 + "]");
        }
        if (string.equals("*")) {
            return true;
        }
        return string.equals(string2);
    }

    public boolean cmp(String string, String string2, String string3, String string4, String string5, String string6) {
        if (extDebug) {
            System.out.print("\nx4dict715.cmp::" + this.MF + "," + this.MT + "\tdict:" + this.Code + "\n" + "\t  reac::[(" + string + "," + string2 + ")" + string3 + "," + string4 + "," + string5 + "," + string6 + "]\n");
        }
        if (string2 != null && !this.cmp1(this.SF3, string2)) {
            return false;
        }
        if (!this.cmp1(this.SF4, string3)) {
            return false;
        }
        if (!this.cmp1(this.SF5, string4)) {
            return false;
        }
        if (!this.cmp1(this.SF6, string5)) {
            return false;
        }
        if (!this.cmp1(this.SF7, string6)) {
            return false;
        }
        if (this.flagSF2exc ? this.cmp1(this.SF2, string) : !this.cmp1(this.SF2, string)) {
            return false;
        }
        if (extDebug) {
            System.out.println("x4dict715.cmp:OK MF=" + this.MF + " MT=" + this.MT);
        }
        return true;
    }

    public boolean cmpsf6(String string, String string2) {
        if (string != null && !this.cmp1(this.SF6, string)) {
            return false;
        }
        if (string2 != null && !this.cmp1(this.SF7, string2)) {
            return false;
        }
        if (extDebug) {
            System.out.println("OK MF=" + this.MF + " MT=" + this.MT);
        }
        return true;
    }

    public static x4dict715 find715(String string, String string2, String string3, String string4, String string5, String string6) {
        x4dict715.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict715 x4dict7152 = (x4dict715)vDict.elementAt(i);
            if (!x4dict7152.cmp(string, string2, string3, string4, string5, string6) || x4dict7152.MT <= 0) continue;
            return x4dict7152;
        }
        return null;
    }

    public static x4dict715 find715(x4reacstr x4reacstr2) {
        x4dict715.init();
        if (x4reacstr2 == null) {
            return null;
        }
        String string = x4reacstr2.SF6;
        if (string.indexOf("/SUM") >= 0) {
            string = x4dict715.myStrReplace(string, "/SUM", "");
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict715 x4dict7152 = (x4dict715)vDict.elementAt(i);
            if (!x4dict7152.cmp(x4reacstr2.SF2, x4reacstr2.SF3, x4reacstr2.SF4, x4reacstr2.SF5, string, x4reacstr2.SF7) || x4dict7152.MT <= 0) continue;
            return x4dict7152;
        }
        return null;
    }

    public static int find715MF(x4reacstr x4reacstr2) {
        x4dict715.init();
        if (x4reacstr2 == null) {
            return 0;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict715 x4dict7152 = (x4dict715)vDict.elementAt(i);
            if (!x4dict7152.cmp(x4reacstr2.SF2, null, x4reacstr2.SF4, x4reacstr2.SF5, x4reacstr2.SF6, x4reacstr2.SF7) || x4dict7152.MT <= 0) continue;
            return x4dict7152.MF;
        }
        return 0;
    }

    public static x4dict715 find715bySF6(x4reacstr x4reacstr2) {
        x4dict715.init();
        if (x4reacstr2 == null) {
            return null;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict715 x4dict7152 = (x4dict715)vDict.elementAt(i);
            if (!x4dict7152.cmpsf6(x4reacstr2.SF6, x4reacstr2.SF7) || x4dict7152.MT > 0) continue;
            return x4dict7152;
        }
        return null;
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict715.x4dict_new=" + string.trim());
        }
        x4dict715 x4dict7152 = new x4dict715(string);
        return x4dict7152;
    }

    public static x4dict715 findinx4dict(String string) {
        x4dict715.init();
        x4dict715 x4dict7152 = (x4dict715)x4dict715.findinx4dict(string, vDict);
        return x4dict7152;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init715()---");
            }
            x4dict715 x4dict7152 = new x4dict715();
            String string = x4dict715.getFileName(715);
            vDict = x4dict7152.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = string.trim();
        if (string2.startsWith("#")) {
            return false;
        }
        if (string2.length() < 10) {
            return false;
        }
        return x4dict715.strExtractInt(string, 1, 4, 0) > 0;
    }

    public String toString() {
        String string = "---x4dict715:  MF=" + x4dict715.strpad("" + this.MF, 3) + " MT=" + x4dict715.strpad("" + this.MT, 4) + " LR=" + x4dict715.strpad("" + this.LR, 3) + " SF20=" + x4dict715.strpad("" + this.SF20, 8) + " SF2=" + x4dict715.strpad("" + this.SF2, 8) + " SF2exc=" + x4dict715.strpad("" + this.SF2exc, 8) + " SF3=" + x4dict715.strpad("" + this.SF3, 8) + " SF4=" + x4dict715.strpad("" + this.SF4, 8) + " SF5=" + x4dict715.strpad("" + this.SF5, 8) + " SF6=" + x4dict715.strpad("" + this.SF6, 8) + " SF7=" + x4dict715.strpad("" + this.SF7, 8) + " SF27=" + x4dict715.strpad("" + this.SF27, 8) + " ---x4dict: " + super.toString();
        return string;
    }
}
