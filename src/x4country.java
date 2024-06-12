package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-CINDA Country internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2019-01-30
 * @since           2008-12-12
 *
 * Program:         x4country.java
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

public class x4country
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String Country = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4country dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4country.init();
        boolean bl2 = x4country.exists(1003, "USA");
        sysOut.println(" [USA] chk=" + bl2);
        sysOut.println(" [CAN] chk=" + x4dict.exists(1003, "CAN"));
        sysOut.println(" [JPN] chk=" + x4dict.exists(1003, "JPN"));
        x4country x4country2 = (x4country)x4country.findinx4dict(1003, "J,NP/A");
        if (x4country2 != null) {
            sysOut.println(" x4f=" + x4country2.toString());
        }
        if ((x4country2 = (x4country)x4country.findinx4dict(1003, "J,NST")) != null) {
            sysOut.println(" x4f=" + x4country2.toString());
        }
        if ((x4country2 = (x4country)x4country.findinx4dict(1003, "USA")) != null) {
            sysOut.println(" x4f=" + x4country2.toString() + "[" + x4country2.Country + "]");
        }
        if ((x4country2 = (x4country)x4country.findinx4dict(1003, "FR")) != null) {
            sysOut.println(" x4f=" + x4country2.toString() + "[" + x4country2.Country + "]");
        }
        if ((x4country2 = (x4country)x4country.findinx4dict(1003, "JPN")) != null) {
            sysOut.println(" x4f=" + x4country2.toString() + "[" + x4country2.Country + "]");
        }
        if ((x4country2 = (x4country)x4country.findinx4dict(1003, "ZZZIAE")) != null) {
            sysOut.println(" x4f=" + x4country2.toString() + "[" + x4country2.Country + "]");
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public static x4country findinx4dict(String string) {
        x4country.init();
        x4country x4country2 = (x4country)x4country.findinx4dict(string, vDict);
        return x4country2;
    }

    public static String getExpansion(String string) {
        x4country x4country2 = x4country.findinx4dict(string);
        if (x4country2 == null) {
            return "";
        }
        return x4country2.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init1003()---");
            }
            vDict = new Vector();
            x4country.addDict2Dict1003("INSTITUTE");
            String string = x4dict.getFileName(1003);
            hashDicts.put(string, vDict);
        }
    }

    public static void addDict2Dict1003(String string) {
        int n = -1;
        n = x4country.findUniDictByName(string);
        Vector vector = x4dict.getVectorDict(n);
        for (int i = 0; i < vector.size(); ++i) {
            x4dict x4dict2 = (x4dict)vector.elementAt(i);
            if (x4dict2.Key2.equals("")) continue;
            String string2 = "";
            String string3 = "";
            if (x4dict2.Code.length() < 4) continue;
            string2 = x4dict2.Code.substring(1, 4).trim();
            string3 = x4dict2.Code.substring(4).trim();
            if (!string2.equals("ZZZ") && !string2.equals(string3)) continue;
            x4country x4country2 = new x4country();
            x4country2.Code = x4dict2.Code.substring(1, 4).trim();
            if (x4country2.Code.equals("ZZZ")) {
                x4country2.Code = x4dict2.Code.substring(1).trim();
            }
            x4country2.shortHelp = x4dict2.shortHelp;
            x4country2.Country = x4dict2.Key2;
            vDict.addElement(x4country2);
        }
    }
}
