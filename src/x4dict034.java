package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-034 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-08-22
 * @since           2007-09-25
 *
 * Program:         x4dict034.java
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

public class x4dict034
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static String DictNameExtension = "034";
    static Vector vDict = null;
    String genMod = "";
    String strNumericalEquiv = "";

    public static void main(String[] stringArray) {
        int n;
        String string = null;
        Object var11_2 = null;
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict034 dictExtension");
            System.exit(0);
        }
        if ((n = 0) < stringArray.length) {
            string = stringArray[n++];
        }
        extDebug = true;
        boolean bl = x4dict034.exists("ASY");
        sysOut.println(" chk=" + bl);
        sysOut.println(" chk=" + x4dict034.exists("qqq"));
        x4dict034 x4dict0342 = x4dict034.findinx4dict("ASY");
        if (x4dict0342 != null) {
            sysOut.println(" x4f=" + x4dict0342.toString());
        }
        sysOut.println("   exists('(A)')=" + x4dict034.exists("(A)"));
        sysOut.println(" isGenMod('(A)')=" + x4dict034.isGenMod("(A)"));
        sysOut.println("   exists('ASY')=" + x4dict034.exists("ASY"));
        sysOut.println(" isGenMod('ASY')=" + x4dict034.isGenMod("ASY"));
        sysOut.println("   exists('2L2')=" + x4dict034.exists("2L2"));
        sysOut.println(" isGenMod('2L2')=" + x4dict034.isGenMod("2L2"));
        sysOut.println("   exists('fff')=" + x4dict034.exists("fff"));
        sysOut.println(" isGenMod('fff')=" + x4dict034.isGenMod("fff"));
        sysOut.println(" isGenMod('RAW')=" + x4dict034.isGenMod("RAW"));
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict034() {
    }

    x4dict034(String string) {
        super(string);
        this.genMod = x4dict034.strExtractStr(string, 54, 57, "");
        this.strNumericalEquiv = x4dict034.strExtractStr(string, 44, 53, "");
        this.shortHelp = x4dict034.strExtractStr(string, 59, 113, "");
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict034.x4dict_new=" + string.trim());
        }
        x4dict034 x4dict0342 = new x4dict034(string);
        if (extDebug) {
            sysOut.println(" x4dict034.genMod:[" + x4dict0342.genMod + "]" + " Code:[" + x4dict0342.Code + "]");
        }
        return x4dict0342;
    }

    public static x4dict034 findinx4dict(String string) {
        x4dict034.init();
        x4dict034 x4dict0342 = (x4dict034)x4dict034.findinx4dict(string, vDict);
        return x4dict0342;
    }

    public static boolean exists(String string) {
        x4dict034 x4dict0342 = x4dict034.findinx4dict(string);
        return x4dict0342 != null;
    }

    public boolean equals(String string) {
        return this.Code.equals(string);
    }

    public static boolean isGenMod(String string) {
        x4dict034 x4dict0342 = x4dict034.findinx4dict(string);
        if (x4dict0342 == null) {
            return false;
        }
        return x4dict0342.genMod.equals("GENQ");
    }

    public static Vector getGenMods() {
        x4dict034.init();
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict034 x4dict0342 = (x4dict034)vDict.elementAt(i);
            if (!x4dict0342.genMod.equals("GENQ")) continue;
            vector.addElement(x4dict0342.Code);
        }
        return vector;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init34()---");
            }
            x4dict034 x4dict0342 = new x4dict034();
            String string = x4dict034.getFileName(34);
            vDict = x4dict0342.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = "---x4dict034:  genMod=" + x4dict034.strpad(this.genMod, 4) + "; " + " NumEqv=" + this.strNumericalEquiv + "; " + " ---x4dict: " + super.toString();
        return string;
    }
}
