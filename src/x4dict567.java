package zvv.x4;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR-Dictionary-567 internal dictionary
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2019-01-30
 * @since           2011-06-08
 *
 * Program:         x4dict567.java
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

public class x4dict567
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    static Hashtable hDict = null;
    String RefType = "";
    String CountryCode = "";
    String CountryName = "";
    String Country = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2014\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict567 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict567.init();
        x4dict567 x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "J,NST");
        if (x4dict5672 != null) {
            sysOut.println(" x4f=" + x4dict5672.toString());
            sysOut.println(" x4f.CountryCode=" + x4dict5672.CountryCode);
            sysOut.println(" x4f.Country=" + x4dict5672.Country);
        }
        System.exit(0);
        boolean bl2 = x4dict567.exists(567, "C,DEMIDOV");
        sysOut.println(" [C,DEMIDOV] chk=" + bl2);
        bl2 = x4dict567.exists(567, "B,DEMIDOV");
        sysOut.println(" [B,DEMIDOV] chk=" + bl2);
        sysOut.println(" [R,ORNL-TM-] chk=" + x4dict.exists(567, "R,ORNL-TM-"));
        x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "J,NP/A");
        if (x4dict5672 != null) {
            sysOut.println(" x4f=" + x4dict5672.toString());
        }
        if ((x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "R,ORNL-TM-")) != null) {
            sysOut.println(" x4f=" + x4dict5672.toString());
        }
        if ((x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "C,DEMIDOV")) != null) {
            sysOut.println("___C,DEMIDOV: x4f=" + x4dict5672.toString());
        }
        if ((x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "B,DEMIDOV")) != null) {
            sysOut.println("___B,DEMIDOV x4f=" + x4dict5672.toString());
        }
        if ((x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "R,INDC(NDS)-")) != null) {
            sysOut.println(" x4f=" + x4dict5672.toString());
            sysOut.println(" x4f.CountryCode=" + x4dict5672.CountryCode);
        }
        if ((x4dict5672 = (x4dict567)x4dict567.findinx4dict(567, "R,JINR-E6-")) != null) {
            sysOut.println(" x4f=" + x4dict5672.toString());
            sysOut.println(" x4f.CountryCode=" + x4dict5672.CountryCode);
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public static x4dict567 findinx4dict(String string) {
        x4dict567.init();
        x4dict567 x4dict5672 = (x4dict567)x4dict567.findinx4dict(string, vDict);
        return x4dict5672;
    }

    public static String getExpansion(String string) {
        x4dict567 x4dict5672 = x4dict567.findinx4dict(string);
        if (x4dict5672 == null) {
            return "";
        }
        return x4dict5672.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init567()---");
            }
            vDict = new Vector();
            x4dict567.addDict2Dict567("JOURNAL", "J,");
            x4dict567.addDict2Dict567("REPORT", "R,");
            x4dict567.addDict2Dict567("CONF", "C,");
            x4dict567.addDict2Dict567("BOOK", "C,");
            String string = x4dict.getFileName(567);
            hashDicts.put(string, vDict);
            hDict = new Hashtable();
            for (int i = 0; i < vDict.size(); ++i) {
                x4dict567 x4dict5672 = (x4dict567)vDict.elementAt(i);
                hDict.put(x4dict5672.Code, x4dict5672.shortHelp);
            }
        }
    }

    public static void addDict2Dict567(String string, String string2) {
        int n = -1;
        n = x4dict567.findUniDictByName(string);
        Vector vector = x4dict.getVectorDict(n);
        for (int i = 0; i < vector.size(); ++i) {
            x4dict x4dict2 = (x4dict)vector.elementAt(i);
            x4dict567 x4dict5672 = new x4dict567();
            x4dict5672.Code = string2 + x4dict2.Code;
            x4dict5672.statusFlag = x4dict2.statusFlag;
            x4dict5672.shortHelp = x4dict2.shortHelp;
            x4dict5672.RefType = string;
            String string3 = x4dict2.Key2.trim();
            if (string3.length() >= 4 && (string3 = string3.substring(1, 4).startsWith("ZZZ") ? string3.substring(1).trim() : string3.substring(1, 4).trim()).length() > 6) {
                string3 = string3.substring(0, 6);
            }
            x4dict5672.CountryCode = string3;
            x4country x4country2 = x4country.findinx4dict(string3);
            if (x4country2 != null) {
                x4dict5672.Country = x4country2.Country;
                x4dict5672.CountryName = x4country2.shortHelp;
            }
            vDict.addElement(x4dict5672);
        }
    }
}
