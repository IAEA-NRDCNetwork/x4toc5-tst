package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR DATA: x4data internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-10-05
 * @since           2011-12-21
 *
 * Program:         x4data.java
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

public class x4data {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    x4sysid x4sys = null;
    Vector allLines = new Vector();
    int N1 = 0;
    int N2 = 0;
    int i0strdata = 0;
    int i1strdata = 0;
    int DnCol = 0;
    int DnRow = 0;
    int nmaxStr = 6;
    int lnStr = 11;
    int i0strSubent = 0;
    x4subent subent = null;
    int n1strings = 0;
    boolean flagNotempty = false;
    boolean flagCommon = false;
    int nStrExpected;
    String txtKW = "";
    String[] headers = null;
    String[] pointers = null;
    String[] units = null;
    Float[][] data = null;
    String[][] sdata = null;
    char[] cpointers = null;
    int iPlaceElement = -1;
    int iPlaceMass = -1;
    int iPlaceIsomer = -1;
    String strCompNotes = null;
    String data1str = "";

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4data");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        sysOut.println("args.length: " + stringArray.length + "\n");
        Vector<String> vector = new Vector<String>();
        vector.addElement("SUBENT        10447006   20030530   20030918");
        vector.addElement("BIB                  3          3");
        vector.addElement("REACTION   (9-F-19(N,A)7-N-16,,SIG)");
        vector.addElement("STATUS     Data taken from private communication, C.M.Bartle,1975.");
        vector.addElement("HISTORY    (19810506A) BIB additions.");
        vector.addElement("ENDBIB               3");
        vector.addElement("NOCOMMON             0          0");
        vector.addElement("DATA                 9         10");
        vector.addElement("EN         EN-RSL     DATA      1DATA-ERR  1EN-NRM    2MISC      2");
        vector.addElement("DATA      2DATA-ERR  2MONIT     2");
        vector.addElement("MEV        MEV        NO-DIM     PER-CENT   MEV        MEV");
        vector.addElement("MB         PER-CENT   MB");
        vector.addElement(" 5.454      0.203      5.281  -03            5.412      0.229");
        vector.addElement("  2.878     27.9       544.9");
        vector.addElement(" 5.565      0.189      9.270 E-03 19.1       5.522      0.216");
        vector.addElement("  5.101     19.7       550.3");
        vector.addElement(" 5.678      0.191      1.607 E-02 13.8       5.634      0.223");
        vector.addElement("  9.078     14.7       564.9");
        vector.addElement(" 5.787      0.176      2.364 E-02 12.0       5.742      0.204");
        vector.addElement(" 13.69      13.0       579.0");
        vector.addElement(" 5.898      0.174      2.859 E-02 10.8       5.851      0.192");
        vector.addElement(" 16.96      11.9       593.2");
        vector.addElement(" 6.005      0.168      3.203 E-02 10.2       5.958      0.197");
        vector.addElement(" 19.45      11.4       607.1");
        vector.addElement(" 6.114      0.167      4.129 E-02  9.7       6.065      0.183");
        vector.addElement(" 26.29      10.9       636.6");
        vector.addElement(" 6.163      0.152      4.137 E-02  9.5       6.114      0.187");
        vector.addElement(" 27.09      10.7       654.7");
        vector.addElement(" 6.327      0.154      3.323 E-02  9.9       6.278      0.183");
        vector.addElement(" 23.94      11.1       720.4");
        vector.addElement(" 6.433      0.147      3.270 E-02  9.6       6.382      0.194");
        vector.addElement(" 25.04      10.8       765.8");
        vector.addElement("ENDDATA             24");
        vector.addElement("ENDSUBENT           12");
        x4data x4data2 = new x4data(vector, 1);
        x4data2.printStrings();
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4data() {
    }

    x4data(x4subent x4subent2) {
        this(x4subent2.allLines, "DATA", false, x4subent2.i0strSubent);
        this.subent = x4subent2;
    }

    x4data(Vector vector, int n) {
        this(vector, "DATA", false, n);
    }

    x4data(Vector vector, String string, boolean bl, int n) {
        String string2;
        int n2;
        String string3 = string;
        String string4 = "NO" + string;
        String string5 = "END" + string;
        this.x4sys = null;
        this.flagCommon = bl;
        this.txtKW = string;
        this.i0strSubent = n;
        this.allLines = vector;
        string3 = string3 + " ";
        for (n2 = 0; n2 < vector.size(); ++n2) {
            string2 = (String)vector.elementAt(n2);
            if (string2.startsWith(string4)) {
                this.i0strdata = n2;
                this.i1strdata = n2;
                this.flagNotempty = false;
                this.txtKW = string4;
                this.x4sys = new x4sysid(string2);
                return;
            }
            if (!string2.startsWith(string3) || this.i0strdata > 0) continue;
            this.flagNotempty = true;
            this.i0strdata = n2;
            this.x4sys = new x4sysid(string2);
            break;
        }
        while (n2 < vector.size()) {
            string2 = (String)vector.elementAt(n2);
            if (string2.startsWith(string5)) {
                this.i1strdata = n2;
                break;
            }
            ++n2;
        }
        if (this.x4sys == null) {
            return;
        }
        if (this.i0strdata >= this.i1strdata) {
            return;
        }
        this.N1 = this.x4sys.N1;
        this.N2 = this.x4sys.N2;
        this.DnCol = this.N1;
        if (this.DnCol > 18) {
            this.DnCol = 18;
        }
        this.n1strings = (this.DnCol + this.nmaxStr - 1) / this.nmaxStr;
        this.DnRow = this.N2;
        if (bl) {
            this.DnRow = 1;
        }
        this.nStrExpected = this.n1strings * (this.DnRow + 2);
        if (extDebug) {
            sysOut.println("....." + string + " N1=" + this.N1 + " N2=" + this.N2 + " Dim=" + this.DnCol + "*" + this.DnRow + " n1strings=" + this.n1strings + " nStrExpected=" + this.nStrExpected + " i0strdata=" + this.i0strdata + " i1strdata=" + this.i1strdata);
        }
        this.setHeaders();
        this.setData();
    }

    public void setHeaders() {
        String string;
        int n;
        String string2;
        int n2;
        if (this.allLines == null) {
            return;
        }
        if (this.allLines.size() <= 0) {
            return;
        }
        if (this.i0strdata >= this.i1strdata) {
            return;
        }
        this.headers = new String[this.DnCol];
        this.pointers = new String[this.DnCol];
        this.cpointers = new char[this.DnCol];
        int n3 = this.i0strdata + 1;
        int n4 = 0;
        for (n2 = 0; n2 < this.n1strings; ++n2) {
            string2 = (String)this.allLines.elementAt(n2 + n3);
            while (n4 < this.DnCol && n4 < (n2 + 1) * this.nmaxStr) {
                n = n4 % this.nmaxStr * this.lnStr;
                this.headers[n4] = string = this.strExtractStr(string2, n + 1, n + this.lnStr - 1, "");
                this.pointers[n4] = string = this.strExtractStr(string2, n + this.lnStr, n + this.lnStr, "");
                this.cpointers[n4] = string.length() > 0 ? string.charAt(0) : (char)32;
                ++n4;
            }
        }
        this.units = new String[this.DnCol];
        n3 = this.i0strdata + 1 + this.n1strings;
        n4 = 0;
        for (n2 = 0; n2 < this.n1strings; ++n2) {
            string2 = (String)this.allLines.elementAt(n2 + n3);
            while (n4 < this.DnCol && n4 < (n2 + 1) * this.nmaxStr) {
                n = n4 % this.nmaxStr * this.lnStr;
                this.units[n4] = string = this.strExtractStr(string2, n + 1, n + this.lnStr - 1, "");
                ++n4;
            }
        }
        for (n2 = 0; n2 < this.DnCol; ++n2) {
            if (this.headers[n2].equals("ELEMENT")) {
                this.iPlaceElement = n2;
            }
            if (this.headers[n2].equals("MASS")) {
                this.iPlaceMass = n2;
            }
            if (!this.headers[n2].equals("ISOMER")) continue;
            this.iPlaceIsomer = n2;
        }
    }

    public void setData() {
        if (this.allLines == null) {
            return;
        }
        if (this.allLines.size() <= 0) {
            return;
        }
        if (this.i0strdata >= this.i1strdata) {
            return;
        }
        if (this.DnCol > 18) {
            this.DnCol = 18;
        }
        try {
            this.data = new Float[this.DnRow][this.DnCol];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            System.exit(-1);
        }
        try {
            this.sdata = new String[this.DnRow][this.DnCol];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            System.exit(-1);
        }
        for (int i = 0; i < this.DnRow; ++i) {
            int n = this.i0strdata + 1 + this.n1strings * 2 + this.n1strings * i;
            int n2 = 0;
            for (int j = 0; j < this.n1strings; ++j) {
                String string = (String)this.allLines.elementAt(j + n);
                while (n2 < this.DnCol && n2 < (j + 1) * this.nmaxStr) {
                    int n3 = n2 % this.nmaxStr * this.lnStr;
                    String string2 = this.strExtractStr(string, n3 + 1, n3 + this.lnStr, "");
                    this.data[i][n2] = this.str2Float(string2);
                    this.sdata[i][n2] = this.data1str;
                    ++n2;
                }
            }
        }
    }

    public String getStrData1(int n, int n2) {
        String string = null;
        if (this.allLines == null) {
            return null;
        }
        if (this.allLines.size() <= 0) {
            return null;
        }
        if (this.i0strdata >= this.i1strdata) {
            return null;
        }
        if (n2 >= this.DnRow) {
            return null;
        }
        if (n >= this.DnCol) {
            return null;
        }
        int n3 = this.i0strdata + 1 + this.n1strings * 2 + this.n1strings * n2;
        int n4 = n / this.nmaxStr;
        String string2 = (String)this.allLines.elementAt(n4 + n3);
        int n5 = n % this.nmaxStr * this.lnStr;
        string = this.strExtractStr(string2, n5 + 1, n5 + this.lnStr, "");
        return string;
    }

    public String strExtractStr(String string, int n, int n2, String string2) {
        String string3 = string2;
        if (string == null) {
            return string3;
        }
        int n3 = string.length();
        string3 = n2 > n3 ? (n > n3 ? "" : string.substring(n - 1)) : string.substring(n - 1, n2);
        string3 = x4data.delEndSpace(string3);
        return string3;
    }

    public void setSubentLines(Vector vector) {
        this.allLines = new Vector();
        if (vector == null) {
            return;
        }
        if (vector.size() <= 0) {
            return;
        }
        this.allLines = vector;
        String string = (String)vector.elementAt(0);
    }

    public Vector getLines() {
        Vector<String> vector = new Vector<String>();
        if (this.allLines.size() > 0) {
            for (int i = this.i0strdata; i <= this.i1strdata; ++i) {
                String string = (String)this.allLines.elementAt(i);
                vector.addElement(string);
            }
        }
        return vector;
    }

    public void printStrings() {
        if (this.allLines.size() > 0) {
            for (int i = this.i0strdata; i <= this.i1strdata; ++i) {
                String string = (String)this.allLines.elementAt(i);
                if (!this.flagCommon) {
                    sysOut.println("...D..." + string);
                    continue;
                }
                sysOut.println("...C..." + string);
            }
        }
    }

    public void printHeaders() {
        if (this.x4sys == null) {
            return;
        }
        sysOut.println("....." + this.x4sys.keyword + " N1=" + this.N1 + " N2=" + this.N2 + " Dim=" + this.DnCol + "*" + this.DnRow + " n1strings=" + this.n1strings + " nStrExpected=" + this.nStrExpected + " i0strdata=" + this.i0strdata + " i1strdata=" + this.i1strdata + " i0str-file=" + (this.i0strdata + this.i0strSubent));
        for (int i = 0; i < this.DnCol; ++i) {
            sysOut.println(" iCol=" + this.strpad(i + "", 2) + " header=" + this.strpad(this.headers[i], 11) + " " + " pointer=" + this.strpad(this.pointers[i], 1) + " " + " units=" + this.strpad(this.units[i], 11));
        }
    }

    public Vector getHeaders() {
        Vector<String> vector = new Vector<String>();
        if (this.x4sys == null) {
            return vector;
        }
        for (int i = 0; i < this.DnCol; ++i) {
            String string = this.headers[i];
            vector.addElement(string);
        }
        return vector;
    }

    public boolean hasHeader(String string) {
        if (this.x4sys == null) {
            return false;
        }
        for (int i = 0; i < this.DnCol; ++i) {
            String string2 = this.headers[i];
            if (!string2.equals(string)) continue;
            return true;
        }
        return false;
    }

    public Vector getUnits() {
        Vector<String> vector = new Vector<String>();
        if (this.x4sys == null) {
            return vector;
        }
        for (int i = 0; i < this.DnCol; ++i) {
            String string = this.units[i];
            vector.addElement(string);
        }
        return vector;
    }

    public int addx4vars(Vector vector, boolean bl, char c, char[] cArray) {
        int n = 0;
        for (int i = 0; i < this.DnCol; ++i) {
            boolean bl2 = false;
            if (bl) {
                if (this.cpointers[i] == c || this.cpointers[i] == ' ') {
                    bl2 = true;
                }
            } else {
                bl2 = true;
            }
            if (!bl2) continue;
            x4var x4var2 = new x4var(this, i, cArray);
            int n2 = -1;
            for (int j = 0; j < vector.size(); ++j) {
                x4var x4var3 = (x4var)vector.elementAt(j);
                if (!x4var2.Header.equals(x4var3.Header)) continue;
                n2 = j;
                break;
            }
            if (n2 >= 0) {
                vector.insertElementAt(x4var2, n2 + 1);
            } else {
                vector.addElement(x4var2);
            }
            ++n;
        }
        return n;
    }

    public int addx4varsEnRes(Vector vector, Vector vector2) {
        int n = 0;
        for (int i = 0; i < vector2.size(); ++i) {
            x4var x4var2 = (x4var)vector2.elementAt(i);
            vector.addElement(x4var2);
            ++n;
        }
        return n;
    }

    public Float getValue(int n, int n2) {
        Float f = null;
        if (this.flagCommon) {
            if (n >= 0 && n < this.DnCol) {
                f = this.data[0][n];
            }
        } else if (n >= 0 && n < this.DnCol && n2 >= 0 && n2 < this.DnRow) {
            f = this.data[n2][n];
        }
        return f;
    }

    public void printData() {
        int n;
        int n2;
        int n3 = 5;
        for (n2 = 0; n2 < this.DnRow; ++n2) {
            for (n = 0; n < this.DnCol; ++n) {
                String string = this.data[n2][n] == null ? "null" : this.data[n2][n] + "";
                if (string.length() + 1 <= n3) continue;
                n3 = string.length() + 1;
            }
        }
        for (n2 = 0; n2 < this.DnRow; ++n2) {
            sysOut.print(this.padstr(n2 + ") ", 6));
            for (n = 0; n < this.DnCol; ++n) {
                if (this.data[n2][n] == null) {
                    sysOut.print(this.strpad("null", n3));
                    continue;
                }
                sysOut.print(this.strpad(this.data[n2][n] + "", n3));
            }
            sysOut.println("");
        }
    }

    public Float str2Float(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        this.data1str = string2 = string.trim().toUpperCase();
        try {
            float f = Float.parseFloat(string2);
            return new Float(f);
        }
        catch (Exception exception) {
            string2 = this.myStrReplace(string2, " +", "E+");
            string2 = this.myStrReplace(string2, " -", "E-");
            string2 = this.myStrReplace(string2, " ", "");
            int n = string2.length();
            if (n > 1) {
                char c;
                int n2 = string2.indexOf("+", 1);
                if (n2 < 0) {
                    n2 = string2.indexOf("-", 1);
                }
                if (n2 > 0 && (c = string2.charAt(n2 - 1)) != 'E') {
                    string2 = string2.substring(0, n2) + "E" + string2.substring(n2);
                }
            }
            this.data1str = string2;
            try {
                float f = Float.parseFloat(string2);
                return new Float(f);
            }
            catch (Exception exception2) {
                return null;
            }
        }
    }

    public String pause(String string) {
        System.out.print("\nPause " + string + " ...");
        System.out.flush();
        DataInputStream dataInputStream = new DataInputStream(System.in);
        String string2 = null;
        try {
            string2 = dataInputStream.readLine();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return string2;
    }

    public String x4subIdToAcc(int n) {
        if (n <= 99999999) {
            return "" + n;
        }
        String string = "" + n;
        string = string.substring(2);
        int n2 = n / 10000000 - 10;
        string = (char)(65 + n2) + string;
        return string;
    }

    public int x4acc2ID(String string) {
        char c = Character.toUpperCase(string.charAt(0));
        String string2 = Character.isDigit(c) ? string : Character.getNumericValue(c) - Character.getNumericValue('A') + 10 + string.substring(1);
        int n = Integer.parseInt(string2);
        return n;
    }

    public int str2int(String string, int n) {
        int n2 = n;
        try {
            n2 = Integer.parseInt(string.trim());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    public double str2double(String string, double d) {
        double d2 = d;
        try {
            d2 = Double.parseDouble(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return d2;
    }

    public String strpad(String string, int n) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = string;
            return string2;
        }
        String string3 = string;
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string3 = string3 + " ";
        }
        return string3;
    }

    public String padstr(String string, int n) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = string;
            return string2;
        }
        String string3 = "";
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string3 = string3 + " ";
        }
        string3 = string3 + string;
        return string3;
    }

    public String myStrReplace(String string, String string2, String string3) {
        int n;
        int n2 = string.length();
        int n3 = string2.length();
        int n4 = string3.length();
        String string4 = "";
        int n5 = 0;
        while (n5 < n2 && (n = string.indexOf(string2, n5)) >= 0) {
            if (n > n5) {
                string4 = string4 + string.substring(n5, n);
            }
            string4 = string4 + string3;
            n5 = n + n3;
        }
        if (n5 < n2) {
            string4 = string4 + string.substring(n5, n2);
        }
        return string4;
    }

    public String myStrReplaceCRLF(String string) {
        string = this.myStrReplace(string, "\n\r", " ");
        string = this.myStrReplace(string, "\r\n", " ");
        string = this.myStrReplace(string, "\n", " ");
        string = this.myStrReplace(string, "\r", " ");
        string = this.myStrReplace(string, "  ", " ");
        string = this.myStrReplace(string, "  ", " ");
        return string;
    }

    public String strReplaceChar(String string, char c, String string2) {
        int n = string.length();
        String string3 = "";
        for (int i = 0; i < n; ++i) {
            char c2 = string.charAt(i);
            string3 = c2 == c ? string3 + string2 : string3 + c2;
        }
        return string3;
    }

    public static String delLiderSpace(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (string.substring(i, i + 1).equals(" ")) continue;
            return string.substring(i, n);
        }
        return "";
    }

    public static String delEndSpace(String string) {
        int n = string.length();
        for (int i = n - 1; i >= 0; --i) {
            if (string.substring(i, i + 1).equals(" ")) continue;
            return string.substring(0, i + 1);
        }
        return "";
    }
}
