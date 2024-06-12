package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR DATA: x4common internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2019-06-10
 * @since           2007-09-27
 *
 * Program:         x4common.java
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

public class x4common
extends x4data {
    static PrintWriter sysOut = new PrintWriter(System.out, true);

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4common");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        Vector<String> vector = new Vector<String>();
        vector.addElement("SUBENT        10447006   20030530   20030918");
        vector.addElement("BIB                  3          3");
        vector.addElement("REACTION   (9-F-19(N,A)7-N-16,,SIG)");
        vector.addElement("STATUS     Data taken from private communication, C.M.Bartle,1975.");
        vector.addElement("HISTORY    (19810506A) BIB additions.");
        vector.addElement("ENDBIB               3");
        vector.addElement("NOCOMMON             0          0");
        vector.addElement("DATA                 4          2");
        vector.addElement("EN         EN-RSL-FW  DATA       DATA-ERR");
        vector.addElement("MEV        KEV        MB         MB");
        vector.addElement(" 4.7600E+00 1.0000E+02 1.0200E+02 6.0000E+00");
        vector.addElement(" 5.5400E+00 8.0000E+01 1.5300E+02 9.0000E+00");
        vector.addElement("ENDDATA              4");
        vector.addElement("ENDSUBENT           12");
        x4common x4common2 = new x4common(vector, 1);
        x4common2.printStrings();
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4common() {
    }

    x4common(x4subent x4subent2) {
        super(x4subent2.allLines, "COMMON", true, x4subent2.i0strSubent);
        this.subent = x4subent2;
    }

    x4common(Vector vector, int n) {
        super(vector, "COMMON", true, n);
    }
}
