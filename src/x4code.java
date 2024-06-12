package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-CODE internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-08-19
 * @since           2010-09-23
 *
 * Program:         x4code.java
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

public class x4code {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    x4kw xkw = new x4kw();
    Object xobj = null;
    String pointer = "";
    char cpointer = (char)32;
    String code = "";
    String origcode = "";
    Vector codeLines = new Vector();
    Vector freeLines = new Vector();
    int i0str = 0;
    int i0free = 0;
    int nBrackets = 0;
    int id_edit = 0;
    int ii_edit = 0;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4code");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        sysOut.println("args.length: " + stringArray.length + "\n");
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4code() {
    }

    x4code(int n, int n2, String string, Vector vector, Vector vector2, int n3, x4kw x4kw2) {
        this.i0str = n;
        this.i0free = n2;
        this.pointer = string;
        this.codeLines = vector;
        this.freeLines = vector2;
        this.nBrackets = n3;
        if (x4kw2 != null) {
            this.xkw = x4kw2;
        }
        if (vector2.size() == 0) {
            this.i0free = n + vector.size() - 1;
        }
        this.setcode();
        this.setpointer();
    }

    public void setcode() {
        String string = "";
        for (int i = 0; i < this.codeLines.size(); ++i) {
            String string2 = (String)this.codeLines.elementAt(i);
            string = string + string2.trim();
        }
        if (string.startsWith("(")) {
            string = string.substring(1);
        }
        if (string.endsWith(")")) {
            int n = string.length();
            string = string.substring(0, n - 1);
        }
        this.code = string;
        this.setorigcode();
    }

    public void setorigcode() {
        String string = "";
        for (int i = 0; i < this.codeLines.size(); ++i) {
            String string2 = (String)this.codeLines.elementAt(i);
            if (!string.equals("")) {
                string = string + "\n";
            }
            string = string + string2.trim();
        }
        if (string.startsWith("(")) {
            string = string.substring(1);
        }
        if (string.endsWith(")")) {
            int n = string.length();
            string = string.substring(0, n - 1);
        }
        this.origcode = string;
    }

    public void setpointer() {
        this.cpointer = this.pointer.length() <= 0 ? (char)32 : this.pointer.charAt(0);
    }

    public void setObj(Object object) {
        this.xobj = object;
    }

    public void println() {
        String string;
        int n;
        sysOut.println(".....x4code:P=[" + this.cpointer + "]" + " LC=" + this.codeLines.size() + "  LF=" + this.freeLines.size() + " [" + this.code + "]");
        for (n = 0; n < this.codeLines.size(); ++n) {
            string = (String)this.codeLines.elementAt(n);
            sysOut.println("\t\t code" + this.strpad(n + 1 + ":", 4) + " [" + string + "]");
        }
        for (n = 0; n < this.freeLines.size(); ++n) {
            string = (String)this.freeLines.elementAt(n);
            sysOut.println("\t\t free" + this.strpad(n + 1 + ":", 4) + " [" + string + "]");
        }
    }

    public Vector getLines() {
        int n;
        Vector<String> vector = new Vector<String>();
        boolean bl = false;
        if (this.freeLines.size() > 0 && this.i0str + this.codeLines.size() - 1 == this.i0free) {
            bl = true;
        }
        String string = "";
        for (n = 0; n < this.codeLines.size(); ++n) {
            string = (String)this.codeLines.elementAt(n);
            if (n == this.codeLines.size() - 1 && bl) break;
            vector.addElement(string);
        }
        for (n = 0; n < this.freeLines.size(); ++n) {
            String string2 = (String)this.freeLines.elementAt(n);
            if (n == 0 && bl) {
                string2 = string + string2;
            }
            vector.addElement(string2);
        }
        return vector;
    }

    public Vector getFreeLines() {
        return this.freeLines;
    }

    public String getFreeText(String string) {
        String string2 = "";
        for (int i = 0; i < this.freeLines.size(); ++i) {
            String string3 = (String)this.freeLines.elementAt(i);
            string2 = string2 + string3;
            if (i >= this.freeLines.size() - 1) continue;
            string2 = string2 + string;
        }
        return string2;
    }

    public String getCodeText(String string) {
        String string2 = "";
        for (int i = 0; i < this.codeLines.size(); ++i) {
            String string3 = (String)this.codeLines.elementAt(i);
            string2 = string2 + string3;
            if (i >= this.codeLines.size() - 1) continue;
            string2 = string2 + string;
        }
        return string2;
    }

    public int get_iStrInFile() {
        int n = this.i0str;
        return n += this.xkw.get_iStrInFile();
    }

    public String get_StrInFile(int n) {
        if (this.xkw == null) {
            return "";
        }
        return this.xkw.get_StrInFile(this.i0str);
    }

    public String get_Subent() {
        if (this.xkw == null) {
            return "";
        }
        return this.xkw.get_Subent();
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
