package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-Dictionary-002 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2007-09-25
 * @since           2007-09-25
 *
 * Program:         x4dict002.java
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

public class x4dict002
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    String kwRequired = "";
    int SortFlag = 0;
    String codeRequired = "";
    int dictOfCode = 0;
    boolean requiredKw = false;
    boolean requiredCode = false;
    String wrk = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2009\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict002 dictExtension");
            System.exit(0);
        }
        boolean bl = false;
        x4dict.setDirOfArchivedDicts("C:/x4load5/x4dict/");
        x4dict002.init();
        boolean bl2 = x4dict002.exists(2, "INSTITUTE");
        sysOut.println("INSTITUTE: chk=" + bl2);
        sysOut.println("      qqq: chk=" + x4dict.exists(2, "qqq"));
        x4dict002.init();
        x4dict002 x4dict0022 = x4dict002.findinx4dict("INC-SOURCE");
        if (x4dict0022 != null) {
            sysOut.println(" x4f=" + x4dict0022.toString());
        }
        if ((x4dict0022 = x4dict002.findinx4dict("DETECTOR")) != null) {
            sysOut.println(" x4f=" + x4dict0022.toString());
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict002() {
    }

    x4dict002(String string) {
        super(string);
        this.Code = x4dict002.strExtractStr(string, 13, 22, "");
        this.shortHelp = x4dict002.strExtractStr(string, 44, 68, "");
        this.kwRequired = x4dict002.strExtractStr(string, 69, 69, "");
        this.SortFlag = x4dict002.strExtractInt(string, 70, 71, 0);
        this.codeRequired = x4dict002.strExtractStr(string, 72, 72, "");
        this.dictOfCode = x4dict002.strExtractInt(string, 73, 75, 0);
        if (this.kwRequired.equals("R")) {
            this.requiredKw = true;
        }
        if (this.codeRequired.equals("R")) {
            this.requiredCode = true;
        }
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict002.x4dict_new=" + string.trim());
        }
        x4dict002 x4dict0022 = new x4dict002(string);
        return x4dict0022;
    }

    public static x4dict002 findinx4dict(String string) {
        x4dict002.init();
        x4dict002 x4dict0022 = (x4dict002)x4dict002.findinx4dict(string, vDict);
        return x4dict0022;
    }

    public static String getExpansion(String string) {
        x4dict002 x4dict0022 = x4dict002.findinx4dict(string);
        if (x4dict0022 == null) {
            return "";
        }
        return x4dict0022.shortHelp;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init002()---");
            }
            x4dict002 x4dict0022 = new x4dict002();
            String string = x4dict002.getFileName(2);
            vDict = x4dict0022.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public static void initWrk() {
        x4dict002.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict002 x4dict0022 = (x4dict002)vDict.elementAt(i);
            x4dict0022.wrk = "";
        }
    }

    public static Vector getRequiredButAbsent() {
        Vector<x4dict002> vector = new Vector<x4dict002>();
        x4dict002.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict002 x4dict0022 = (x4dict002)vDict.elementAt(i);
            if (!x4dict0022.requiredKw || x4dict0022.wrk.equals("P")) continue;
            vector.addElement(x4dict0022);
        }
        return vector;
    }

    public static String getOneOfRequired() {
        String string = "";
        x4dict002.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict002 x4dict0022 = (x4dict002)vDict.elementAt(i);
            if (!x4dict0022.kwRequired.equals("B")) continue;
            string = string + " [" + x4dict0022.Code + "]";
        }
        return string;
    }

    public static x4dict002 setWrk(String string) {
        x4dict002.init();
        x4dict002 x4dict0022 = (x4dict002)x4dict002.findinx4dict(string, vDict);
        if (x4dict0022 != null) {
            x4dict0022.wrk = "P";
        }
        return x4dict0022;
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict002.strExtractStr(string, 13, 22, "");
        return !string2.equals("");
    }

    public String toString() {
        String string = "---x4dict002:  Code=[" + this.Code + "] " + " shortHelp=[" + this.shortHelp + "] " + " kwRequired=[" + this.kwRequired + "] " + " SortFlag=[" + this.SortFlag + "] " + " codeRequired=[" + this.codeRequired + "] " + " dictOfCode=[" + this.dictOfCode + "] " + " ---x4dict: " + super.toString();
        return string;
    }
}
