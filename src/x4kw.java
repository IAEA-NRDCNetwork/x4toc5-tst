package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * EXFOR-KEYWORD object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2015-09-17
 * @since           2010-10-21
 *
 * Program:         x4kw.java
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

public class x4kw {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    x4sysid x4sys = null;
    x4syskw syskw = null;
    int syskwID = -1;
    String keyword = "";
    Vector allLines = new Vector();
    Vector codes = new Vector();
    int i0str = 0;
    int lnStr = 11;
    x4bib xbib = null;
    x4dict002 x4d = null;
    int id_edit = 0;
    int ii_edit = 0;
    int idcode_next = 0;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4kw");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        sysOut.println("args.length: " + stringArray.length + "\n");
        Vector<String> vector = new Vector<String>();
        vector.addElement("INC-SOURCE (D(ab)-LI) Deuterons on thick Li target.                ");
        vector.addElement("            matrix of the cross sections, where                 ");
        vector.addElement("           (D(ab ");
        vector.addElement("            -LI) Deuterons on thick Li target.                ");
        vector.addElement("          y(abcdef) 111111111111111111111111111111               ");
        vector.addElement("           222222222222        ");
        x4kw x4kw2 = new x4kw(vector, 1);
        x4kw2.printStrings();
        x4kw2.println();
        sysOut.println("");
        vector = new Vector();
        vector.addElement("TITLE      Neutron Total Cross Sections, 2.5 - 15 MeV.         ");
        vector.addElement("            I. Experimental.                                   ");
        x4kw2 = new x4kw(vector, 1);
        x4kw2.printStrings();
        x4kw2.println();
        sysOut.println("");
        vector = new Vector();
        vector.addElement("HISTORY    (19730815C)                                          ");
        vector.addElement("           (19810403A) CONVERTED TO REACTION FORMALISM          ");
        vector.addElement("           (19821019A) FIELD HEADING CHANGED.                   ");
        vector.addElement("           (19841205A) DATA HEADING CORRECTION.                 ");
        vector.addElement("          1(19870505A) BIB UPDATE.                              ");
        vector.addElement("           (19990907A  Converted to new date formalism.         ");
        x4kw2 = new x4kw(vector, 1);
        x4kw2.printStrings();
        sysOut.println("");
        x4kw2.println();
        sysOut.println("...idcode_next=" + x4kw2.idcode_next);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4kw() {
    }

    public void setBib(x4bib x4bib2) {
        this.xbib = x4bib2;
    }

    x4kw(Vector vector, int n) {
        int n2;
        x4code x4code2;
        this.allLines = vector;
        this.x4sys = null;
        this.i0str = n;
        if (vector == null) {
            return;
        }
        if (vector.size() <= 0) {
            return;
        }
        this.allLines = vector;
        String string = (String)vector.elementAt(0);
        this.x4sys = new x4sysid(string);
        this.syskw = x4syskw.getKw(this.x4sys.keyword);
        if (this.syskw != null) {
            this.syskwID = this.syskw.iSysID;
        }
        this.keyword = this.x4sys.keyword;
        this.x4d = x4dict002.findinx4dict(this.keyword);
        if (extDebug) {
            sysOut.println("...x4sys.keyword=[" + this.x4sys.keyword + "] syskwID=" + this.syskwID);
        }
        String string2 = "";
        String string3 = "";
        boolean bl = false;
        Vector<String> vector2 = new Vector<String>();
        Vector<String> vector3 = new Vector<String>();
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < vector.size(); ++i) {
            string = (String)vector.elementAt(i);
            String string4 = this.strExtractStr(string, 11, 11, "");
            String string5 = this.strExtractStr(string, 12, "");
            boolean bl2 = false;
            String string6 = "";
            if (!string4.equals("")) {
                bl2 = true;
            }
            if (!bl && (string3.length() > 0 || vector3.size() > 0) && string5.startsWith("(")) {
                bl2 = true;
            }
            if (bl2) {
                if (string3.length() > 0 || vector3.size() > 0) {
                    x4code2 = new x4code(n4, n5, string2, vector2, vector3, n3, this);
                    x4code2.id_edit = this.codes.size();
                    this.codes.addElement(x4code2);
                    if (extDebug) {
                        sysOut.println("---out--- pointer=[" + string2 + "]" + " code=[" + string3 + "]" + " code:" + vector2.size() + " free:" + vector3.size() + " i00=" + n4 + " i00free=" + n5 + " nBrackets=" + n3);
                        for (n2 = 0; n2 < vector2.size(); ++n2) {
                            sysOut.println("   +++strCode" + n2 + "[" + (String)vector2.elementAt(n2) + "]");
                        }
                        for (n2 = 0; n2 < vector3.size(); ++n2) {
                            sysOut.println("   +++strFree" + n2 + "[" + (String)vector3.elementAt(n2) + "]");
                        }
                    }
                }
                string2 = string4;
                string3 = "";
                bl = false;
                vector2 = new Vector();
                vector3 = new Vector();
                n3 = 0;
                n4 = i;
                n5 = i;
            }
            if (!bl && string5.startsWith("(")) {
                string3 = "(";
                string6 = "(";
                string5 = string5.substring(1);
                n3 = 1;
                bl = true;
            }
            if (!bl) {
                vector3.addElement(string5);
                continue;
            }
            int n6 = string5.length();
            for (int j = 0; j < n6; ++j) {
                char c = string5.charAt(j);
                string3 = string3 + c;
                string6 = string6 + c;
                if (c == '(') {
                    ++n3;
                    continue;
                }
                if (c != ')' || --n3 > 0) continue;
                if (j < n6 - 1) {
                    vector3.addElement(string5.substring(j + 1));
                    n5 = i;
                } else {
                    n5 = i + 1;
                }
                bl = false;
                break;
            }
            vector2.addElement(string6);
        }
        if (string3.length() > 0 || vector3.size() > 0) {
            x4code2 = new x4code(n4, n5, string2, vector2, vector3, n3, this);
            x4code2.id_edit = this.codes.size();
            this.codes.addElement(x4code2);
            if (extDebug) {
                sysOut.println("---out--- pointer=[" + string2 + "]" + " code=[" + string3 + "]" + " code:" + vector2.size() + " free:" + vector3.size() + " i00=" + n4 + " i00free=" + n5 + " nBrackets=" + n3);
                for (n2 = 0; n2 < vector2.size(); ++n2) {
                    sysOut.println("   +++strCode" + n2 + "[" + (String)vector2.elementAt(n2) + "]");
                }
                for (n2 = 0; n2 < vector3.size(); ++n2) {
                    sysOut.println("   +++strFree" + n2 + "[" + (String)vector3.elementAt(n2) + "]");
                }
            }
        }
        this.idcode_next = this.codes.size();
    }

    public String strExtractStr(String string, int n, String string2) {
        String string3 = string2;
        if (string == null) {
            return string3;
        }
        int n2 = string.length();
        string3 = n > n2 ? "" : string.substring(n - 1);
        string3 = x4kw.delEndSpace(string3);
        return string3;
    }

    public String strExtractStr(String string, int n, int n2, String string2) {
        String string3 = string2;
        if (string == null) {
            return string3;
        }
        int n3 = string.length();
        string3 = n2 > n3 ? (n > n3 ? "" : string.substring(n - 1)) : string.substring(n - 1, n2);
        string3 = x4kw.delEndSpace(string3);
        return string3;
    }

    public void printStrings() {
        if (this.allLines.size() > 0) {
            for (int i = 0; i < this.allLines.size(); ++i) {
                String string = (String)this.allLines.elementAt(i);
                sysOut.println("....." + string);
            }
        }
    }

    public void printStrings(PrintWriter printWriter) {
        if (this.allLines.size() > 0) {
            for (int i = 0; i < this.allLines.size(); ++i) {
                String string = (String)this.allLines.elementAt(i);
                printWriter.println(string);
            }
        }
    }

    public void println() {
        sysOut.println(".....x4kw: [" + this.keyword + "]\t syskwID=" + this.syskwID + " codes:" + this.codes.size());
        x4code.setPrintWriter(sysOut);
        for (int i = 0; i < this.codes.size(); ++i) {
            x4code x4code2 = (x4code)this.codes.elementAt(i);
            sysOut.print("\t x4kw:code" + (i + 1) + ":\t");
            x4code2.println();
        }
        sysOut.print("");
    }

    public int get_iStrInFile() {
        int n = this.i0str;
        if (this.xbib != null) {
            n += this.xbib.get_iStrInFile();
        }
        return n;
    }

    public String get_StrInFile(int n) {
        if (this.xbib != null) {
            return this.xbib.get_StrInFile(this.i0str + n);
        }
        if (this.allLines == null) {
            return "";
        }
        if (this.allLines.size() < n) {
            return "";
        }
        String string = (String)this.allLines.elementAt(n);
        return string;
    }

    public String get_Subent() {
        if (this.xbib == null) {
            return "";
        }
        return this.xbib.get_Subent();
    }

    public Vector getLines() {
        String string = this.keyword;
        Vector<String> vector = new Vector<String>();
        if (this.codes.size() <= 0) {
            vector.addElement(string);
        } else {
            for (int i = 0; i < this.codes.size(); ++i) {
                x4code x4code2 = (x4code)this.codes.elementAt(i);
                Vector vector2 = x4code2.getLines();
                for (int j = 0; j < vector2.size(); ++j) {
                    String string2 = (String)vector2.elementAt(j);
                    string2 = j == 0 ? x4code2.cpointer + string2 : " " + string2;
                    vector.addElement(this.strpad(string, 10) + string2);
                    string = "";
                }
            }
        }
        return vector;
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
