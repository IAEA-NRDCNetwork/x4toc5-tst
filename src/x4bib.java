package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * EXFOR-BIB: x4bib object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-03-14
 * @since           2012-12-15
 *
 * Program:         x4bib.java
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

public class x4bib {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    x4sysid x4sys = null;
    Vector allLines = new Vector();
    Vector keywords = new Vector();
    int N1 = 0;
    int N2 = 0;
    int i0strsect = 0;
    int i1strsect = 0;
    int lnStr = 11;
    int i0strSubent = 0;
    x4subent subent = null;
    boolean flagNotempty = false;
    Vector vReactions = new Vector();
    boolean chkPointer = false;
    Vector vAuthors = new Vector();
    String Author1 = "";
    String Author1orig = "";
    String author1 = "";
    String author1ini = "";
    Vector vReferences = new Vector();
    String Reference1 = "";
    String Publication1 = "";
    int Year1Ref = 1900;
    String doi1Ref = "";
    x4ref xref1 = null;
    boolean isVectorCommon = false;
    String strVectorCommonPointers = "";
    boolean isVectorCommonAlreadyDefined = false;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4bib");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        Vector<String> vector = new Vector<String>();
        vector.addElement("SUBENT        10047001   19990907   19991101                   ");
        vector.addElement("BIB                 11         22                              ");
        vector.addElement("INSTITUTE  (1USABNW)                                           ");
        vector.addElement("REFERENCE  (J,PR/C,3,576,197102)                               ");
        vector.addElement("AUTHOR     (D.G.FOSTER JR,                                     ");
        vector.addElement("               D.W.GLASGOW)                                    ");
        vector.addElement("TITLE      Neutron Total Cross Sections, 2.5 - 15 MeV.         ");
        vector.addElement("            I. Experimental.                                   ");
        vector.addElement("INC-SOURCE (D-LI) Deuterons on thick Li target.                ");
        vector.addElement("METHOD     (TOF) Time-of-flight.                               ");
        vector.addElement("DETECTOR   (SCIN)  NE-213 liquid scintillator                  ");
        vector.addElement("MISC-COL   (MISC1) D(I,I+1)                                    ");
        vector.addElement("           (MISC2) D(I,I+2)                                    ");
        vector.addElement("ERR-ANALYS (ERR-T) Standard deviation                           ");
        vector.addElement("COVARIANCE D(i,j) are formed from the elements of the covariance");
        vector.addElement("            matrix of the cross sections, where                 ");
        vector.addElement("                   D(i,j) = +- sqrt(/V(i,j)/)                   ");
        vector.addElement("            where the sign of the covariance V(i,j) is retained ");
        vector.addElement("            by D(i,j).                                          ");
        vector.addElement("HISTORY    (19730815C)                                          ");
        vector.addElement("           (19810403A) CONVERTED TO REACTION FORMALISM          ");
        vector.addElement("           (19821019A) FIELD HEADING CHANGED.                   ");
        vector.addElement("           (19841205A  DATA HEADING CORRECTION.                 ");
        vector.addElement("          1(19870505A) BIB UPDATE.                              ");
        vector.addElement("           (19990907A) Converted to new date formalism.         ");
        vector.addElement("ENDBIB              22                                          ");
        vector.addElement("NOCOMMON             0          0                               ");
        vector.addElement("ENDSUBENT           25                                          ");
        x4bib x4bib2 = new x4bib(vector, 1);
        x4bib2.printStrings();
        x4bib2.printshort();
        x4bib2.makeAll();
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4bib() {
    }

    x4bib(Vector vector, int n) {
        String string;
        int n2;
        this.x4sys = null;
        this.allLines = vector;
        this.i0strSubent = n;
        for (n2 = 0; n2 < vector.size(); ++n2) {
            string = (String)vector.elementAt(n2);
            if (string.startsWith("NOBIB")) {
                this.i0strsect = n2;
                this.i1strsect = n2;
                this.flagNotempty = false;
                return;
            }
            if (!string.startsWith("BIB") || this.i0strsect > 0) continue;
            this.flagNotempty = true;
            this.i0strsect = n2;
            this.x4sys = new x4sysid(string);
            break;
        }
        while (n2 < vector.size()) {
            string = (String)vector.elementAt(n2);
            if (string.startsWith("ENDBIB")) {
                this.i1strsect = n2;
                break;
            }
            ++n2;
        }
        if (this.x4sys == null) {
            return;
        }
        if (this.i0strsect >= this.i1strsect) {
            return;
        }
        this.N1 = this.x4sys.N1;
        this.N2 = this.x4sys.N2;
        if (extDebug) {
            sysOut.println("---BIB: N1=" + this.N1 + " N2=" + this.N2 + " i0strsect=" + this.i0strsect + " i1strsect=" + this.i1strsect + " i0strSubent=" + n);
        }
        this.setKeywords();
    }

    public void setSubent(x4subent x4subent2) {
        this.subent = x4subent2;
    }

    public int get_iStrInFile() {
        int n = this.i0strSubent;
        return n;
    }

    public String get_Subent() {
        if (this.subent == null) {
            return "";
        }
        return this.subent.get_Subent();
    }

    public String get_StrInFile(int n) {
        if (this.subent != null) {
            return this.subent.get_StrInFile(n);
        }
        return "";
    }

    public void setKeywords() {
        x4kw x4kw2;
        int n;
        int n2;
        if (this.allLines == null) {
            return;
        }
        if (this.allLines.size() <= 0) {
            return;
        }
        if (this.i0strsect >= this.i1strsect) {
            return;
        }
        this.keywords = new Vector();
        Vector<String> vector = new Vector<String>();
        for (int i = n2 = this.i0strsect + 1; i <= this.i1strsect; ++i) {
            String string = (String)this.allLines.elementAt(i);
            x4sysid x4sysid2 = new x4sysid(string);
            if (x4sysid2.keyword.equals("ENDBIB")) break;
            if (x4sysid2.keyword.equals("")) {
                vector.addElement(string);
                continue;
            }
            if (vector.size() > 0) {
                if (extDebug) {
                    sysOut.println("\n=======kw=======" + n2);
                    for (n = 0; n < vector.size(); ++n) {
                        sysOut.println(n + 1 + ">>>>" + (String)vector.elementAt(n));
                    }
                }
                x4kw2 = new x4kw(vector, n2);
                x4kw2.setBib(this);
                this.keywords.addElement(x4kw2);
            }
            vector = new Vector();
            vector.addElement(string);
            n2 = i;
        }
        if (vector.size() > 0) {
            if (extDebug) {
                sysOut.println("\n=======kw=======" + n2);
                for (n = 0; n < vector.size(); ++n) {
                    sysOut.println(n + 1 + ">>>>" + (String)vector.elementAt(n));
                }
            }
            x4kw2 = new x4kw(vector, n2);
            x4kw2.setBib(this);
            this.keywords.addElement(x4kw2);
        }
        if (extDebug) {
            sysOut.println("--------keywords:" + this.keywords.size());
        }
    }

    public void makeAll() {
        this.makeReactions();
        this.prepareVectorCommon();
        this.makeReferences();
        this.makeAuthors();
    }

    public void rollbackVectorCommon() {
        this.isVectorCommon = false;
        this.strVectorCommonPointers = " ";
        if (this.vReactions != null && this.vReactions.size() > 0) {
            x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(0);
            x4reaction2.cpointer = (char)32;
        }
        this.makeReactions();
        if (this.subent != null) {
            this.subent.prepareC4F();
        }
    }

    public void printAll() {
        this.printReactions();
    }

    public void makeReactions() {
        if (this.subent != null && this.subent.subent1 == null) {
            return;
        }
        Vector vector = this.getReactions();
        this.setReactions(vector);
    }

    public Vector getReactions() {
        boolean bl = false;
        Vector<x4reaction> vector = new Vector<x4reaction>();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("REACTION")) continue;
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                x4reaction x4reaction2 = new x4reaction(x4code2, this.subent);
                x4code2.setObj(x4reaction2);
                x4reaction2.setSubent(this.subent);
                vector.addElement(x4reaction2);
            }
            break;
        }
        return vector;
    }

    public void setNewReactions4VectorCommon() {
        int n;
        boolean bl = false;
        x4kw x4kw2 = null;
        x4code x4code2 = null;
        x4code x4code3 = null;
        if (!this.isVectorCommon) {
            return;
        }
        for (n = 0; n < this.keywords.size(); ++n) {
            x4kw2 = (x4kw)this.keywords.elementAt(n);
            if (x4kw2.keyword.equals("REACTION")) break;
        }
        if (x4kw2 == null) {
            return;
        }
        if (!x4kw2.keyword.equals("REACTION")) {
            return;
        }
        if (x4kw2.codes.size() != 1) {
            return;
        }
        x4code3 = (x4code)x4kw2.codes.elementAt(0);
        for (n = 0; n < this.strVectorCommonPointers.length(); ++n) {
            char c = this.strVectorCommonPointers.charAt(n);
            if (n == 0) {
                x4code2 = x4code3;
                x4code2.pointer = "" + c;
                x4code2.cpointer = c;
                continue;
            }
            x4code2 = new x4code(x4code3.i0str, x4code3.i0free, "" + c, x4code3.codeLines, new Vector(), x4code3.nBrackets, x4code3.xkw);
            x4kw2.codes.addElement(x4code2);
        }
        this.rollbackVectorCommon();
    }

    public void setReactions(Vector vector) {
        this.vReactions = vector;
        this.chkPointer = this.vReactions.size() > 1;
    }

    public boolean prepareVectorCommon() {
        if (this.isVectorCommonAlreadyDefined) {
            return this.isVectorCommon;
        }
        boolean bl = false;
        this.isVectorCommon = false;
        if (this.vReactions.size() == 1) {
            x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(0);
            this.strVectorCommonPointers = x4reaction2.getPointersOfData();
            if (this.strVectorCommonPointers.equals("")) {
                return false;
            }
            if (this.strVectorCommonPointers.length() > 1) {
                this.isVectorCommon = true;
            }
        }
        this.isVectorCommonAlreadyDefined = true;
        return this.isVectorCommon;
    }

    public int getNReactions() {
        if (!this.isVectorCommon) {
            return this.vReactions.size();
        }
        return this.strVectorCommonPointers.length();
    }

    public x4reaction getReaction(int n) {
        if (!this.isVectorCommon) {
            if (n < this.vReactions.size()) {
                x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(n);
                return x4reaction2;
            }
            return new x4reaction(' ', "");
        }
        if (this.strVectorCommonPointers.length() > 1) {
            this.chkPointer = true;
        }
        if (n < this.strVectorCommonPointers.length()) {
            x4reaction x4reaction3 = (x4reaction)this.vReactions.elementAt(0);
            x4reaction3.cpointer = this.strVectorCommonPointers.charAt(n);
            return x4reaction3;
        }
        return new x4reaction(' ', "");
    }

    public x4reaction getReactionsByPointer(String string) {
        boolean bl = false;
        string = string.trim();
        for (int i = 0; i < this.vReactions.size(); ++i) {
            x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(i);
            String string2 = ("" + x4reaction2.cpointer).trim();
            if (!string2.equals(string)) continue;
            return x4reaction2;
        }
        return null;
    }

    public x4reaction getReactionByFullcode(String string) {
        boolean bl = false;
        for (int i = 0; i < this.vReactions.size(); ++i) {
            x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(i);
            String string2 = x4reaction2.reacode;
            if (!string2.equals(string)) continue;
            return x4reaction2;
        }
        return null;
    }

    public void makeReferences() {
        x4ref x4ref2;
        String string = "";
        boolean bl = false;
        this.vReferences = new Vector();
        this.init567();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("REFERENCE")) continue;
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                Vector vector = new Vector();
                if (extDebug) {
                    sysOut.println("-----x4bib.reference=[" + x4code2.code + "]");
                }
                vector.addAll(this.splitStdCode(x4code2.code, "="));
                string = this.getDOI_fromFreelines(x4code2.freeLines);
                for (int k = 0; k < vector.size(); ++k) {
                    String string2 = (String)vector.elementAt(k);
                    if (string2.startsWith("(")) {
                        string2 = string2.substring(1);
                    }
                    if (string2.endsWith(")")) {
                        string2 = string2.substring(0, string2.length() - 1);
                    }
                    if (extDebug) {
                        sysOut.println(this.vReferences.size() + "....x4bib.reference=[" + string2 + "] ");
                    }
                    x4ref2 = new x4ref(string2);
                    x4ref2.strDOI = string;
                    this.vReferences.addElement(x4ref2);
                }
            }
            break;
        }
        if (this.vReferences.size() > 0) {
            this.xref1 = x4ref2 = (x4ref)this.vReferences.elementAt(0);
            this.Reference1 = x4ref2.Reference;
            this.Publication1 = x4ref2.Publication;
            this.Year1Ref = x4ref2.year;
            this.doi1Ref = x4ref2.strDOI;
        }
    }

    public String getDOI_fromFreelines(Vector vector) {
        boolean bl = true;
        if (vector.size() <= 0) {
            return "";
        }
        for (int i = 0; i < vector.size(); ++i) {
            String string = (String)vector.elementAt(i);
            if (!(string = string.trim()).toUpperCase().startsWith("#DOI")) continue;
            if ((string = string.substring(4).trim()).startsWith(":")) {
                string = string.substring(1).trim();
            }
            if (string.startsWith("=")) {
                string = string.substring(1).trim();
            }
            return string;
        }
        return "";
    }

    public String getNSR_fromFreelines(Vector vector) {
        boolean bl = true;
        if (vector.size() <= 0) {
            return "";
        }
        for (int i = 0; i < vector.size(); ++i) {
            String string = (String)vector.elementAt(i);
            int n = (string = string.trim()).toUpperCase().indexOf("#NSR");
            if (n < 0) continue;
            if (n > 0) {
                string = string.substring(n);
            }
            if ((string = string.substring(4).trim()).startsWith(":")) {
                string = string.substring(1).trim();
            }
            if (string.startsWith("=")) {
                string = string.substring(1).trim();
            }
            if (string.length() > 8) {
                string = string.substring(0, 8);
            }
            if (!this.isItNsrKeyno(string)) continue;
            return string;
        }
        return "";
    }

    public boolean isItNsrKeyno(String string) {
        if (string.length() != 8) {
            return false;
        }
        String string2 = string.substring(0, 4);
        int n = this.str2int(string2, 0);
        if (n < 1850) {
            return false;
        }
        if (n >= 2100) {
            return false;
        }
        for (n = 4; n < string.length(); ++n) {
            if (Character.isLetterOrDigit(string.charAt(n))) continue;
            return false;
        }
        return true;
    }

    public x4kw getKeyword(String string) {
        boolean bl = false;
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals(string)) continue;
            return x4kw2;
        }
        return null;
    }

    public String getSPSDD() {
        boolean bl = false;
        Vector vector = new Vector();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("STATUS")) continue;
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                if (x4code2.code.indexOf("SPSDD") < 0) continue;
                return "SPSDD";
            }
            break;
        }
        return "";
    }

    public String getTitle() {
        boolean bl = false;
        Vector vector = new Vector();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("TITLE")) continue;
            int n = 0;
            if (n >= x4kw2.codes.size()) break;
            x4code x4code2 = (x4code)x4kw2.codes.elementAt(n);
            return x4code2.getFreeText("\n");
        }
        return "";
    }

    public String getCodes(String string, char c, String string2) {
        String string3 = string2;
        boolean bl = false;
        String string4 = "," + string2 + ",";
        Vector vector = new Vector();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals(string)) continue;
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                if (x4code2.cpointer != ' ' && x4code2.cpointer != c) continue;
                String string5 = x4code2.code;
                String[] stringArray = string5.split(",");
                for (int k = 0; k < stringArray.length; ++k) {
                    String string6 = stringArray[k];
                    if (string4.indexOf("," + string6 + ",") >= 0) continue;
                    if (!string3.equals("")) {
                        string3 = string3 + ",";
                    }
                    string3 = string3 + string6;
                    string4 = "," + string3 + ",";
                }
            }
            break;
        }
        return string3;
    }

    public String getCode(String string, char c, String string2) {
        String string3 = "";
        boolean bl = false;
        String string4 = "," + string2 + ",";
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals(string)) continue;
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                if (x4code2.cpointer != ' ' && x4code2.cpointer != c) continue;
                String string5 = x4code2.code;
                String[] stringArray = string5.split(",");
                for (int k = 0; k < stringArray.length; ++k) {
                    String string6 = stringArray[k];
                    if (!string6.equals(string2)) continue;
                    return string5;
                }
            }
            break;
        }
        return string3;
    }

    public String getAuthors() {
        boolean bl = false;
        Vector vector = new Vector();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("AUTHOR")) continue;
            int n = 0;
            if (n >= x4kw2.codes.size()) break;
            x4code x4code2 = (x4code)x4kw2.codes.elementAt(n);
            String string = x4code2.getCodeText("\n");
            if (string.startsWith("(")) {
                string = string.substring(1);
            }
            if (string.endsWith(")")) {
                string = string.substring(0, string.length() - 1);
            }
            return string;
        }
        return "";
    }

    public void init567() {
        if (x4country.vDict == null) {
            x4country.init();
        }
        if (c4sRef.hDict567 != null) {
            return;
        }
        if (x4dict567.vDict == null) {
            x4dict567.init();
        }
        if (x4dict567.vDict == null) {
            return;
        }
        Vector vector = x4dict567.vDict;
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        for (int i = 0; i < vector.size(); ++i) {
            x4dict567 x4dict5672 = (x4dict567)vector.elementAt(i);
            hashtable.put(x4dict5672.Code, x4dict5672.shortHelp);
        }
        c4sRef.setHashDict567(hashtable);
    }

    public void makeAuthors() {
        int n;
        boolean bl = false;
        this.vAuthors = new Vector();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals("AUTHOR")) continue;
            for (n = 0; n < x4kw2.codes.size(); ++n) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(n);
                this.vAuthors.addAll(this.splitStdCode(x4code2.code, ","));
            }
            break;
        }
        if (this.vAuthors.size() > 0) {
            this.Author1 = (String)this.vAuthors.elementAt(0);
            this.Author1orig = this.Author1.trim();
            n = this.Author1.lastIndexOf(".");
            if (n >= 0) {
                String string = this.Author1.substring(0, n + 1);
                String string2 = this.Author1.substring(n + 1).trim();
                if (string2.length() > 1) {
                    string2 = string2.substring(0, 1) + string2.substring(1).toLowerCase();
                }
                this.author1 = string2;
                this.author1ini = string;
                this.Author1 = string + string2;
            } else {
                this.author1 = this.Author1;
                this.author1ini = "";
            }
            if (this.vAuthors.size() > 1) {
                this.Author1 = this.Author1 + "+";
            }
        }
    }

    public Vector splitStdCode(String string, String string2) {
        String string3 = "";
        Vector<String> vector = new Vector<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, string2);
        int n = stringTokenizer.countTokens();
        int n2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String string4 = stringTokenizer.nextToken().trim();
            vector.addElement(string4);
            ++n2;
        }
        return vector;
    }

    public void printReactions() {
        sysOut.println(".....x4bib.reactions: " + this.vReactions.size());
        for (int i = 0; i < this.vReactions.size(); ++i) {
            x4reaction x4reaction2 = (x4reaction)this.vReactions.elementAt(i);
            sysOut.println("  ...reaction-" + (i + 1) + " i0str.file=" + (this.i0strSubent + x4reaction2.xcode.xkw.i0str + x4reaction2.xcode.i0str));
            x4reaction2.println();
        }
    }

    public Vector getKWCodes(String string) {
        boolean bl = false;
        Vector vector = new Vector();
        this.init567();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            if (!x4kw2.keyword.equals(string)) continue;
            return x4kw2.codes;
        }
        return vector;
    }

    public String strExtractStr(String string, int n, int n2, String string2) {
        String string3 = string2;
        if (string == null) {
            return string3;
        }
        int n3 = string.length();
        string3 = n2 > n3 ? (n > n3 ? "" : string.substring(n - 1)) : string.substring(n - 1, n2);
        string3 = x4bib.delEndSpace(string3);
        return string3;
    }

    public void printStrings() {
        if (this.allLines.size() > 0) {
            for (int i = this.i0strsect; i <= this.i1strsect; ++i) {
                String string = (String)this.allLines.elementAt(i);
                sysOut.println("....." + string);
            }
        }
    }

    public int nX4Strings() {
        if (this.allLines.size() <= 0) {
            return 0;
        }
        return this.i1strsect - this.i0strsect + 1;
    }

    public void printX4Strings(PrintWriter printWriter, String string) {
        if (printWriter == null) {
            return;
        }
        if (this.allLines.size() > 0) {
            for (int i = this.i0strsect; i <= this.i1strsect; ++i) {
                String string2 = (String)this.allLines.elementAt(i);
                printWriter.println(string + string2);
            }
        }
    }

    public void println() {
        x4kw.setPrintWriter(sysOut);
        sysOut.println(".....x4bib: lines: " + this.i0strsect + ".." + this.i1strsect + " N1=" + this.N1 + " keywords: " + this.keywords.size());
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            sysOut.print("x4bib:kw" + (i + 1) + ":\t");
            x4kw2.println();
        }
    }

    public void printshort() {
        int n = 0;
        sysOut.println("\n.....x4bib: lines: " + this.i0strsect + ".." + this.i1strsect + " N1=" + this.N1 + " keywords: " + this.keywords.size());
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            String string = "???";
            x4dict002 x4dict0022 = x4dict002.findinx4dict(x4kw2.keyword);
            if (x4dict0022 != null) {
                string = x4dict0022.shortHelp;
            }
            sysOut.println("kw" + (i + 1) + ":\t" + x4kw2.keyword + "\t" + "line:" + (this.i0strSubent + x4kw2.i0str) + " " + string);
            for (int j = 0; j < x4kw2.codes.size(); ++j) {
                x4code x4code2 = (x4code)x4kw2.codes.elementAt(j);
                String string2 = "";
                if (x4code2.freeLines.size() > 0) {
                    string2 = ":\t[" + (String)x4code2.freeLines.elementAt(0) + "]";
                }
                if (x4code2.freeLines.size() > 1) {
                    string2 = string2 + "...";
                }
                sysOut.println("  " + (j + 1) + ")\t" + x4code2.cpointer + "[" + x4code2.code + "]" + " vCode:" + x4code2.codeLines.size() + " vFree:" + x4code2.freeLines.size() + string2);
                ++n;
            }
        }
        sysOut.println(".....x4bib.keywords: " + this.keywords.size() + " codes: " + n);
    }

    public Vector getLines(boolean bl) {
        String string;
        boolean bl2 = false;
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i < this.keywords.size(); ++i) {
            x4kw x4kw2 = (x4kw)this.keywords.elementAt(i);
            Vector vector2 = x4kw2.getLines();
            for (int j = 0; j < vector2.size(); ++j) {
                string = (String)vector2.elementAt(j);
                vector.addElement(string);
            }
        }
        if (bl) {
            if (this.keywords.size() == 0) {
                vector.addElement(this.strpad("NOBIB", 11) + this.padstr("0", 11) + this.padstr("0", 11));
            } else {
                string = this.strpad("BIB", 11) + this.padstr("" + this.keywords.size(), 11) + this.padstr("" + (vector.size() + 0), 11);
                vector.insertElementAt(string, 0);
                string = this.strpad("ENDBIB", 11) + this.padstr("" + (vector.size() - 1), 11);
                vector.addElement(string);
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

    public double str2float(String string, float f) {
        float f2 = f;
        try {
            f2 = Float.parseFloat(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return f2;
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
