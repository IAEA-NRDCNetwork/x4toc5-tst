package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR-SUBENT: x4subent object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-03-24
 * @since           2011-12-17
 *
 * Program:         x4subent.java
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

public class x4subent {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    x4sysid x4sys = null;
    x4sysid x4trans = null;
    Vector allLines = new Vector();
    Vector allLinesOriginal = new Vector();
    String Entry = "";
    String Subent = "";
    String Alter11 = "";
    String TransID = "";
    String TransDate = "";
    String Date2DB = "";
    int EntryID = 0;
    int SubentID = 0;
    int sub = 0;
    int nSub = 0;
    int N2 = 0;
    int N3 = 0;
    int N4 = 0;
    int N5 = 0;
    String strDateModif = "";
    String lastEntryStr = "";
    String lastTransStr = "";
    String lastSubentStr = "";
    int i0strSubent = 0;
    int DnCol = 0;
    int DnRow = 0;
    x4subent subent1 = null;
    boolean flagNosubent = false;
    x4bib xbib = null;
    x4common xcommon = null;
    x4data xdata = null;
    boolean tryAttemptToUseOtherVars = false;
    int ii_edit = 0;
    int iie_edit = 0;
    int ie_edit = 0;

    public static void main(String[] stringArray) {
        String string = null;
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4subent");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2014\n");
        sysOut.println("args.length: " + stringArray.length + "\n");
        int n = 0;
        if (n < stringArray.length) {
            string = stringArray[n++];
        }
        x4subent x4subent2 = new x4subent("SUBENT        12634001   20050714   20050909                  1336");
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4subent() {
    }

    x4subent(String string) {
        this.setSubentStr(string);
        this.lastSubentStr = string;
    }

    x4subent(Vector vector, String string, int n) {
        this.setSubentLines(vector);
        if (string != null && string.length() > 66) {
            string = string.substring(0, 66);
        }
        this.lastEntryStr = string;
        this.i0strSubent = n + 1;
    }

    public void setSubent1(x4subent x4subent2) {
        this.subent1 = x4subent2;
        if (this.subent1 != null) {
            ++this.subent1.nSub;
        } else {
            ++this.nSub;
        }
    }

    public void setSubentLines_before2009(Vector vector) {
        this.allLines = new Vector();
        if (vector == null) {
            return;
        }
        if (vector.size() <= 0) {
            return;
        }
        this.allLines = vector;
        String string = (String)vector.elementAt(0);
        this.setSubentStr(string);
    }

    public void setSubentLines(Vector vector) {
        this.allLines = new Vector();
        this.allLinesOriginal = new Vector();
        if (vector == null) {
            return;
        }
        if (vector.size() <= 0) {
            return;
        }
        this.allLinesOriginal = vector;
        for (int i = 0; i < vector.size(); ++i) {
            String string = (String)vector.elementAt(i);
            if (string.length() > 66) {
                string = string.substring(0, 66);
            }
            this.allLines.addElement(string);
        }
        String string = (String)this.allLines.elementAt(0);
        this.setSubentStr(string);
    }

    public Vector trimSubentLines() {
        this.allLines = this.trimSubentLines(this.allLines);
        return this.allLines;
    }

    public Vector trimSubentLines(Vector vector) {
        Vector<String> vector2 = new Vector<String>();
        if (vector == null) {
            return vector2;
        }
        for (int i = 0; i < vector.size(); ++i) {
            String string = (String)vector.elementAt(i);
            if (string.length() > 66) {
                string = string.substring(0, 66);
            }
            string = x4subent.delEndSpace(string);
            vector2.addElement(string);
        }
        return vector2;
    }

    public void setSubentStr(String string) {
        int n;
        int n2;
        int n3;
        this.x4sys = new x4sysid(string);
        if (this.x4sys.keyword.equals("NOSUBENT")) {
            this.flagNosubent = true;
        }
        this.Subent = this.x4sys.strN1;
        this.Alter11 = this.x4sys.pointer;
        if (this.Subent.indexOf(" ") > 0) {
            this.Subent = this.x4sys.strN1shifted;
        }
        if (this.Subent.length() > 8) {
            this.Subent = this.Subent.substring(this.Subent.length() - 8);
        }
        this.Entry = this.Subent.length() > 5 ? this.Subent.substring(0, 5) : this.Subent;
        this.SubentID = this.x4acc2ID(this.Subent);
        this.EntryID = this.SubentID / 1000;
        this.sub = this.SubentID % 1000;
        this.N2 = this.str2int(this.x4sys.strN2, 0);
        this.N3 = this.str2int(this.x4sys.strN3, 0);
        this.N4 = this.str2int(this.x4sys.strN4, 0);
        this.N5 = this.str2int(this.x4sys.strN5, 0);
        if (this.N2 <= 0) {
            this.N2 = this.N3;
        }
        if (this.N2 <= 0) {
            this.N2 = 20050926;
        }
        if ((n3 = this.N2) < 600101) {
            n3 = 20000000 + n3;
        }
        if (n3 < 999999) {
            n3 = 19000000 + n3;
        }
        if ((n2 = n3 % 100) > 31) {
            n3 = n3 - n2 + 1;
        }
        if ((n = n3 / 100 % 100) > 12) {
            n3 = n3 - n * 100 + 100;
        }
        this.strDateModif = "" + n3;
        if (this.x4sys.strN5.trim().length() == 4) {
            this.TransID = this.x4sys.strN5.trim();
        }
        this.TransDate = this.x4sys.strN4.trim();
        this.Date2DB = this.x4sys.strN3.trim();
    }

    public void setTransStr(String string) {
        if (this.lastTransStr == null) {
            this.x4trans = null;
            this.TransID = "";
            return;
        }
        this.x4trans = new x4sysid(string);
        if (this.x4trans.keyword.equals("TRANS")) {
            this.TransID = this.x4trans.strN1;
            this.lastTransStr = string;
        }
        if (this.TransID.equals("") && this.x4sys != null) {
            this.TransID = this.x4sys.strN5;
        }
    }

    public void makeBib() {
        this.xbib = new x4bib(this.allLines, this.i0strSubent);
        this.xbib.setSubent(this);
        this.xbib.makeAll();
    }

    public void makeData() {
        this.xcommon = new x4common(this);
        this.xdata = new x4data(this);
    }

    public int prepareC4F() {
        x4reaction x4reaction2;
        int n;
        int n2 = 0;
        boolean bl = false;
        x4reaction x4reaction3 = null;
        if (this.subent1 == null) {
            return 0;
        }
        if (this.xdata == null) {
            return 0;
        }
        if (this.xdata.DnRow <= 0) {
            return 0;
        }
        String string = "";
        boolean bl2 = false;
        Vector vector = this.xbib.vReactions;
        for (n = 0; n < vector.size(); ++n) {
            x4reaction2 = (x4reaction)vector.elementAt(n);
            n2 += x4reaction2.prepareC4F(this, x4reaction2.cpointer);
            string = string + x4reaction2.absentVarFamilyCode;
            if (!x4reaction2.resonansEnergyGinenInData) continue;
            bl2 = true;
            x4reaction3 = x4reaction2;
        }
        if (extDebug) {
            sysOut.println("++++SUB=" + this.Subent + " reactions-" + vector.size() + " absentVarFamilyCode=[" + string + "]" + " resonansEnergyGinenInData=[" + bl2 + "]");
        }
        if (!bl2) {
            return n2;
        }
        if (string.indexOf("1") < 0) {
            return n2;
        }
        for (n = 0; n < vector.size(); ++n) {
            x4reaction2 = (x4reaction)vector.elementAt(n);
            string = x4reaction2.absentVarFamilyCode;
            if (string.indexOf("1") < 0) continue;
            Vector vector2 = this.getVecAddVarsEnRes(x4reaction3, x4reaction2);
            if (extDebug) {
                sysOut.println("...additional x4vars: " + vector2.size());
                for (int i = 0; i < vector2.size(); ++i) {
                    sysOut.println("===" + (i + 1) + ")\t" + ((x4var)vector2.elementAt(i)).toString());
                }
            }
            x4reaction2.prepareC4F(this, x4reaction2.cpointer, vector2);
        }
        return n2;
    }

    public Vector getVecAddVarsEnRes(x4reaction x4reaction2, x4reaction x4reaction3) {
        boolean bl = false;
        boolean bl2 = false;
        Vector<x4var> vector = new Vector<x4var>();
        for (int i = 0; i < x4reaction2.vVars.size(); ++i) {
            x4var x4var2 = (x4var)x4reaction2.vVars.elementAt(i);
            if (!x4var2.strVarOrderFlag.startsWith("0.") || x4var2.Header.indexOf("DATA") < 0) continue;
            String string = x4var2.Header;
            string = x4var2.Header.equals("DATA-APRX") ? this.myStrReplace(string, "DATA-APRX", "EN-RES") : this.myStrReplace(string, "DATA", "EN-RES");
            x4dict024 x4dict0242 = x4dict024.findinx4dict(string);
            if (x4dict0242 == null) continue;
            x4var x4var3 = new x4var(x4var2.xdata, string, x4var2.iCol, x4reaction3.chrIndepVarFamilyCode);
            vector.addElement(x4var3);
        }
        return vector;
    }

    public int prepareC4F(char c) {
        int n = 0;
        int n2 = 0;
        if (this.subent1 != null && this.xdata != null && this.xdata.DnRow > 0) {
            Vector vector = this.xbib.vReactions;
            for (int i = 0; i < vector.size(); ++i) {
                x4reaction x4reaction2 = (x4reaction)vector.elementAt(i);
                if (x4reaction2.cpointer != c || (n = x4reaction2.prepareC4F(this, c)) <= 0) continue;
                if (!extDebug) break;
                sysOut.println("++++SUB=" + this.Subent + " reaction-" + (i + 1) + "[" + x4reaction2.cpointer + "]" + " nVars=" + n + " nRows=" + n2);
                break;
            }
        }
        return n;
    }

    public void setNewData4VectorCommon() {
        int n;
        char c;
        int n2;
        boolean bl = false;
        if (!this.xbib.isVectorCommon) {
            return;
        }
        char[] cArray = null;
        if (this.xcommon.cpointers == null) {
            return;
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xcommon.cpointers:[" + new String(this.xcommon.cpointers) + "]");
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xcommon.cpointers:[" + new String(this.xcommon.cpointers) + "]" + this.xcommon.cpointers.length);
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xdata  .cpointers:[" + new String(this.xdata.cpointers) + "]" + this.xdata.cpointers.length);
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xcommon:\n headers  :" + this.xcommon.headers.length + " " + this.arr2str11(this.xcommon.headers) + "\n pointers :" + this.xcommon.pointers.length + " " + this.arr2str11(this.xcommon.pointers) + "\n units    :" + this.xcommon.units.length + " " + this.arr2str11(this.xcommon.units) + "\n data     :" + this.xcommon.data.length + "\n sdata    :" + this.xcommon.sdata.length + " " + this.arr2str11(this.xcommon.sdata[0]) + "\n cpointers:" + this.xcommon.cpointers.length + " [" + new String(this.xcommon.cpointers) + "]");
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xdata:\n headers  :" + this.xdata.headers.length + " " + this.arr2str11(this.xdata.headers) + "\n pointers :" + this.xdata.pointers.length + " " + this.arr2str11(this.xdata.pointers) + "\n units    :" + this.xdata.units.length + "\n data     :" + this.xdata.data.length + "\n sdata    :" + this.xdata.sdata.length + "\n cpointers:" + this.xdata.cpointers.length + " [" + new String(this.xdata.cpointers) + "]");
        }
        x4reaction x4reaction2 = (x4reaction)this.xbib.vReactions.elementAt(0);
        String string = x4reaction2.getPointersOfData();
        int n3 = string.length();
        cArray = string.toCharArray();
        if (bl) {
            System.out.println("___setNewData4VectorCommon:xreacode.getPointersOfData():[" + x4reaction2.getPointersOfData() + "]" + n3);
        }
        int n4 = this.xdata.data.length;
        int n5 = n3 * n4;
        Vector<String> vector = new Vector<String>(n5);
        for (int i = 0; i < n5; ++i) {
            vector.addElement("");
        }
        if (bl) {
            System.out.println("___setNewData4VectorCommon:vData.size()=[" + vector.size() + "]" + n3 + "x" + n4);
        }
        Vector<String> vector2 = new Vector<String>();
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        for (n2 = 0; n2 < n3; ++n2) {
            c = cArray[n2];
            for (n = 0; n < this.xcommon.pointers.length; ++n) {
                if (c != this.xcommon.cpointers[n] || (string = (String)hashtable.get(this.xcommon.headers[n])) != null) continue;
                hashtable.put(this.xcommon.headers[n], "1");
                vector2.addElement(this.xcommon.headers[n]);
            }
            if (!bl) continue;
            System.out.println("___pointer:[" + c + "] vHeaders.size()=[" + vector2.size() + "]" + this.arr2str11(vector2));
        }
        String string2 = "";
        String string3 = "";
        for (int i = 0; i < vector2.size(); ++i) {
            String string4 = (String)vector2.elementAt(i);
            String string5 = "";
            for (n2 = 0; n2 < n3; ++n2) {
                c = cArray[n2];
                String string6 = "";
                for (n = 0; n < this.xcommon.pointers.length; ++n) {
                    if (c != this.xcommon.cpointers[n] || !string4.equals(string = this.xcommon.headers[n])) continue;
                    if (string5.equals("")) {
                        string5 = this.xcommon.units[n];
                    } else if (!string5.equals(this.xcommon.units[n])) {
                        System.out.println("ERROR___header:[" + string4 + "] pointer:[" + c + "] strUnit=[" + string5 + "] stsUnit2=[" + this.xcommon.units[n] + "]");
                        System.exit(0);
                    }
                    string6 = this.xcommon.sdata[0][n];
                }
                if (bl) {
                    System.out.println("___header:[" + string4 + "] pointer:[" + c + "] strUnit=[" + string5 + "] strData=[" + string6 + "]");
                }
                this.put_common2vec(vector, n2, n4, string6);
            }
            string2 = string2 + this.strpad(string4, 11);
            string3 = string3 + this.strpad(string5, 11);
        }
        if (bl) {
            System.out.println("__strHeaders:[" + string2 + "]");
        }
        if (bl) {
            System.out.println("__strUnits:  [" + string3 + "]");
        }
        if (bl) {
            this.outVec(vector);
        }
        Object var21_21 = null;
        Object var22_22 = null;
        Object var23_23 = null;
        Float[][] floatArray = null;
        String[][] stringArray = null;
    }

    public String arr2str11(String[] stringArray) {
        String string = "";
        if (stringArray == null) {
            return string;
        }
        int n = stringArray.length;
        for (int i = 0; i < n; ++i) {
            string = string + "[" + stringArray[i] + "]";
        }
        return string;
    }

    public String arr2str11(Vector vector) {
        String string = "";
        if (vector == null) {
            return string;
        }
        int n = vector.size();
        for (int i = 0; i < n; ++i) {
            String string2 = (String)vector.elementAt(i);
            string = string + "[" + string2 + "]";
        }
        return string;
    }

    public void put_common2vec(Vector vector, int n, int n2, String string) {
        if (vector == null) {
            return;
        }
        int n3 = n * n2;
        for (int i = 0; i < n2; ++i) {
            String string2 = (String)vector.elementAt(n3 + i);
            string2 = string2 + this.strpad("" + string, 11);
            vector.setElementAt(string2, n3 + i);
        }
    }

    public void outVec(Vector vector) {
        if (vector == null) {
            return;
        }
        int n = vector.size();
        for (int i = 0; i < n; ++i) {
            String string = (String)vector.elementAt(i);
            System.out.println(this.strpad("" + i + "/" + n, 12) + " <" + string + ">");
        }
    }

    public String get_StrInFile_00x(int n) {
        sysOut.println(".....x4subent..." + n + "/" + this.allLines.size());
        if (this.allLines == null) {
            return "";
        }
        if (this.allLines.size() < n) {
            return "";
        }
        String string = (String)this.allLines.elementAt(n);
        sysOut.println(".....subent:[" + string + "]");
        return string;
    }

    public String get_StrInFile(int n) {
        if (this.allLinesOriginal == null) {
            return "";
        }
        if (this.allLinesOriginal.size() < n) {
            return "";
        }
        String string = (String)this.allLinesOriginal.elementAt(n);
        return string;
    }

    public String get_Subent() {
        return this.Subent;
    }

    public void println() {
        if (this.allLines.size() > 0) {
            if (this.sub == 1) {
                sysOut.println("....." + this.lastEntryStr);
            }
            for (int i = 0; i < this.allLines.size(); ++i) {
                String string = (String)this.allLines.elementAt(i);
                sysOut.println("     " + string);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void reprint_allLines(PrintWriter printWriter, boolean bl) {
        int n;
        String string;
        if (this.allLines.size() > 0) {
            if (this.sub == 1) {
                if (this.lastEntryStr.startsWith("ENTRY")) {
                    string = this.lastEntryStr;
                } else {
                    if (this.allLines.size() < 0) return;
                    string = (String)this.allLines.elementAt(0);
                    if (string.startsWith("SUBENT")) {
                        string = string.replace("SUBENT", "ENTRY ");
                    } else {
                        if (!string.startsWith("NOSUBENT")) return;
                        string = string.replace("NOSUBENT", "ENTRY   ");
                    }
                }
                printWriter.println("" + string);
            }
            for (n = 0; n < this.allLines.size(); ++n) {
                string = (String)this.allLines.elementAt(n);
                printWriter.println("" + string);
            }
        }
        if (!bl) return;
        string = this.strpad("ENDENTRY", 11);
        n = this.subent1 != null ? this.subent1.nSub : this.nSub;
        string = string + this.padstr("" + n, 11);
        printWriter.println(string);
    }

    public String getKeyword(String string) {
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() < 1) {
            return string2;
        }
        char c = string.charAt(0);
        if (!Character.isLetter(c)) {
            return string2;
        }
        string2 = string.length() < 10 ? string : string.substring(0, 10);
        string2 = string2.trim();
        return string2;
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
        int n = this.str2int(string2, -777);
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
