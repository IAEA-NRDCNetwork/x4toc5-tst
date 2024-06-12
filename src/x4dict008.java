package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-008 internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2014-10-13
 * @since           2014-10-13
 *
 * Program:         x4dict008.java
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

public class x4dict008
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    public int zz;
    public String Symbol;
    public String Expansion;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2014\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict008 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict008.init();
        x4dict008 x4dict0082 = new x4dict008();
        x4dict008.printCurrentDicts();
        boolean bl2 = x4dict008.exists(8, "AL");
        sysOut.println("AL: chk=" + bl2);
        sysOut.println("      qqq: chk=" + x4dict008.exists(8, "al"));
        sysOut.println("      qqq: chk=" + x4dict.exists(8, "al"));
        sysOut.println("      qqq: o:" + x4dict.exists(8, "o"));
        x4dict0082 = x4dict008.findinx4dict("h");
        if (x4dict0082 != null) {
            sysOut.println(" x4f=" + x4dict0082.toString());
        }
        if ((x4dict0082 = x4dict008.findinx4dict("AG")) != null) {
            sysOut.println(" x4f=" + x4dict0082.toString());
        }
        if ((x4dict0082 = x4dict008.findinx4dict("Al")) != null) {
            sysOut.println(" x4f=" + x4dict0082.toString());
        }
        if ((x4dict0082 = x4dict008.findinx4dict("U")) != null) {
            sysOut.println(" x4f=" + x4dict0082.toString());
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict008() {
    }

    x4dict008(String string) {
        super(string);
        this.Code = x4dict008.strExtractStr(string, 44, 45, "");
        this.shortHelp = x4dict008.strExtractStr(string, 46, 66, "");
        this.zz = x4dict008.strExtractInt(string, 13, 15, 0);
        this.Expansion = this.shortHelp;
        this.Symbol = this.Code;
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict008.x4dict_new=" + string.trim());
        }
        x4dict008 x4dict0082 = new x4dict008(string);
        return x4dict0082;
    }

    public static x4dict008 findinx4dict(String string) {
        x4dict008.init();
        x4dict008 x4dict0082 = (x4dict008)x4dict008.findinx4dict(string.toUpperCase(), vDict);
        return x4dict0082;
    }

    public static boolean exists(int n, String string) {
        x4dict x4dict2 = x4dict008.findinx4dict(n, string.toUpperCase().trim());
        return x4dict2 != null;
    }

    public static String getExpansion(String string) {
        x4dict008 x4dict0082 = x4dict008.findinx4dict(string);
        if (x4dict0082 == null) {
            return "";
        }
        return x4dict0082.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init008()---");
            }
            x4dict008 x4dict0082 = new x4dict008();
            String string = x4dict008.getFileName(8);
            vDict = x4dict0082.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict008.strExtractStr(string, 13, 46, "");
        if (string2.equals("")) {
            return false;
        }
        this.zz = x4dict008.strExtractInt(string, 13, 15, 0);
        return this.zz > 0;
    }

    public String toString() {
        String string = "---x4dict008:  Code=[" + this.Code + "] " + " shortHelp=[" + this.shortHelp + "] " + " zz=[" + this.zz + "] ";
        return string;
    }
}
