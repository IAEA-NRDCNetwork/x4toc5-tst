package zvv.x4;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR variable object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-06-27
 * @since           2012-12-06
 *
 *<pre>
 * Program:         x4var.java
 * Author:          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * Created:         2012-12-06
 * Last modified:   2023-06-27
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
 *                  NO WARRANTY</pre>
 */

public class x4var
implements Cloneable {
    static boolean extDebug = false;
    x4data xdata = null;
    int iCol = 0;
    String Header = "";
    String PseudoHeader = "";
    String Units = "";
    String what = "";
    String whatVar = "";
    float ConversionFactor = 0.0f;
    String BasicUnits = "?";
    x4dict025 x4d025 = null;
    x4dict024 x4d024 = null;
    String UnitsExpansion = "";
    char[] chrIndepVarFamilyCode = null;
    int[] PlottingFlags1int = new int[]{0, 0, 0};
    Float rMin = null;
    Float rMax = null;
    Float nowValue = null;
    Float origValue = null;
    String c4dataValueHeader = null;
    String c4dataValueUnits = null;
    Float c4dataValue = null;
    Float c4errorValue = null;
    Float c4errorPercent = null;
    Float[] c4errorRelArray = null;
    public String free1comment = "";
    public String DataType = "";
    public int numDataType1 = -1;
    public int numDataType2 = 0;
    public int numDataType = 0;
    public String FamilyCode = "";
    public String PlottingFlags = "";
    public String PlottingFlags1 = "";
    public String strVarOrderFlag = "";
    public int Variable_Number = -1;
    public int Variable_Value = -1;
    public int Variable_Error = -1;
    public int Variable_ErrorType = -1;
    public boolean flagValue = false;
    public char cpointer = (char)32;
    x4var xvarMONIT = null;
    x4var xvarInPercent = null;
    String ERR_ANALYS_Code = null;
    Float[] xx4dat = null;
    x4var[] me4dat = null;
    int iRow = 0;
    static int lnn = 12;
    static int lii = 1;
    Float rMin1 = null;
    Float rMax1 = null;

    public x4var clone() {
        x4var x4var2 = null;
        try {
            x4var2 = (x4var)super.clone();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return x4var2;
    }

    public x4var(x4data x4data2, int n) {
        this.xdata = x4data2;
        this.iCol = n;
        if (x4data2.cpointers != null && n < x4data2.cpointers.length) {
            this.cpointer = x4data2.cpointers[n];
        }
        if (x4data2 != null) {
            if (x4data2.headers != null && x4data2.headers.length > n) {
                this.Header = x4data2.headers[n];
            }
            this.x4d024 = x4dict024.findinx4dict(this.Header);
            if (this.x4d024 != null) {
                this.FamilyCode = this.x4d024.FamilyCode;
            }
            if (x4data2.units != null && x4data2.units.length > n) {
                this.Units = x4data2.units[n];
            }
            this.x4d025 = x4dict025.findinx4dict(this.Units);
            if (this.x4d025 != null) {
                this.UnitsExpansion = this.x4d025.shortHelp;
            }
            this.ConversionFactor = x4dict025.getConversionFactor(this.Units);
            String string = x4dict025.getBasicUnits(this.Units);
            if (string != null) {
                this.BasicUnits = string;
            }
        }
    }

    public void setHeader(String string) {
        this.Header = string;
        this.x4d024 = x4dict024.findinx4dict(string);
        if (this.x4d024 != null) {
            this.FamilyCode = this.x4d024.FamilyCode;
        }
    }

    public x4var(x4data x4data2, int n, char[] cArray) {
        this(x4data2, n);
        this.chrIndepVarFamilyCode = cArray;
        if (cArray != null) {
            this.defineWhatIsThis(cArray);
        }
    }

    public x4var(x4data x4data2, String string, int n, char[] cArray) {
        this(x4data2, n);
        this.setHeader(string);
        this.chrIndepVarFamilyCode = cArray;
        if (cArray != null) {
            this.defineWhatIsThis(cArray);
        }
    }

    public void defineWhatIsThis(char[] cArray) {
        char c = ' ';
        int n = 63;
        String string = "";
        String string2 = this.DataType = x4dict024.getDataTypeByHeading(this.Header);
        if (this.DataType != null && this.DataType.length() > 0) {
            c = this.DataType.charAt(0);
            this.numDataType1 = c - 48 - 2;
            if (this.DataType.length() > 1) {
                this.numDataType2 = this.DataType.charAt(1) - 48;
            }
            this.numDataType = this.numDataType1 * 10 + this.numDataType2;
            if (this.Header.equals("MONIT-ERR")) {
                c = '2';
                this.DataType = c + this.DataType.substring(1);
            }
        }
        this.PlottingFlags = x4dict024.getPlottingFlags(this.Header);
        this.what = "ZZZ";
        this.whatVar = "ZZZ";
        this.strVarOrderFlag = "";
        string = "";
        this.Variable_Number = 77;
        int n2 = cArray.length;
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            char c2 = cArray[i];
            if (c2 == ' ') continue;
            char c3 = (char)(c2 + 2);
            if (string2 != null && string2.equals("11")) {
                this.PlottingFlags1 = this.PlottingFlags.substring(3);
                n = 32;
                if (this.PlottingFlags1.length() > 0) {
                    n = this.PlottingFlags1.charAt(0);
                }
                this.flagValue = n >= 49 && n <= 53;
                this.Variable_Value = 1;
                this.PlottingFlags1 = x4var.strpad(this.PlottingFlags1, 3, true);
                if (this.PlottingFlags1.charAt(0) == '9') {
                    this.Variable_Value = 0;
                }
            }
            if (c3 == c) {
                if (c2 == '0') {
                    this.what = "Y";
                    this.strVarOrderFlag = this.strVarOrderFlag + "0";
                    this.Variable_Number = 0;
                    this.PlottingFlags1 = this.PlottingFlags.substring(3);
                } else {
                    this.what = "X" + n3;
                    this.strVarOrderFlag = this.strVarOrderFlag + "" + n3;
                    this.Variable_Number = n3;
                    this.PlottingFlags1 = this.PlottingFlags;
                }
                this.whatVar = "" + c2;
                this.Variable_Value = 1;
                this.PlottingFlags1 = x4var.strpad(this.PlottingFlags1, 3, true);
                if (this.PlottingFlags1.charAt(0) == '9') {
                    this.Variable_Value = 0;
                }
                if (this.PlottingFlags1.charAt(0) == '9' && this.xdata != null && this.xdata.subent != null && this.xdata.subent.xbib != null) {
                    String string3 = this.getErrAnalysCode(this.xdata.subent.xbib, this.Header);
                    if (string3 == null && this.xdata.subent.subent1 != null) {
                        string3 = this.getErrAnalysCode(this.xdata.subent.subent1.xbib, this.Header);
                    }
                    if (string3 != null) {
                        if (string3.equals("U")) {
                            this.PlottingFlags1 = x4var.myStrReplace(this.PlottingFlags1, "5", "4");
                        }
                        if (string3.equals("P")) {
                            this.PlottingFlags1 = x4var.myStrReplace(this.PlottingFlags1, "5", "6");
                        }
                    }
                }
                n = 32;
                if (this.PlottingFlags1.length() > 0) {
                    n = this.PlottingFlags1.charAt(0);
                }
                this.flagValue = n >= 49 && n <= 53;
                string = this.PlottingFlags1.trim().equals("000") ? string + "777" : string + this.PlottingFlags1.trim();
                if (this.PlottingFlags1.length() == 3) {
                    for (int j = 0; j < 3; ++j) {
                        this.PlottingFlags1int[j] = this.PlottingFlags1.charAt(j) - 48;
                    }
                }
                if (string.endsWith("0")) {
                    string = string.substring(0, string.length() - 1);
                }
                if (string.endsWith("0")) {
                    string = string.substring(0, string.length() - 1);
                }
                this.strVarOrderFlag = this.strVarOrderFlag + "." + string;
                this.whatVar = this.whatVar + "." + string;
                this.what = this.what + "." + x4dict024dt.getShortPlottingFlagsExpansion(this.PlottingFlags1);
            }
            if (extDebug) {
                System.out.println(x4var.strpad(this.Header, 10) + " DataType=[" + this.DataType + "]" + " i=" + i + ":from[" + new String(cArray) + "]" + "->[" + c2 + "]->[" + c3 + "]vs[" + c + "] nn=" + n3 + " " + this.what + " ord:" + this.strVarOrderFlag);
            }
            ++n3;
        }
    }

    public String getErrAnalysCode(x4bib x4bib2, String string) {
        this.ERR_ANALYS_Code = null;
        if (x4bib2 == null) {
            return null;
        }
        Vector vector = x4bib2.getKWCodes("ERR-ANALYS");
        String string2 = null;
        for (int i = 0; i < vector.size(); ++i) {
            String[] stringArray;
            String string3;
            x4code x4code2 = (x4code)vector.elementAt(i);
            this.ERR_ANALYS_Code = string3 = x4code2.code;
            if (string3.equals(string)) {
                return "";
            }
            if (!string3.startsWith(string + ",") || (stringArray = string3.split(",")).length < 4) continue;
            return stringArray[3];
        }
        return string2;
    }

    public boolean isItYValue() {
        if (this.Variable_Number != 0) {
            return false;
        }
        return this.Variable_Value != 0;
    }

    public boolean isItXValue() {
        if (this.Variable_Number == 0) {
            return false;
        }
        return this.Variable_Value != 0;
    }

    public boolean isItDataValue() {
        return this.Header.startsWith("DATA");
    }

    public static Vector sort(Vector vector, boolean bl) {
        Vector vector2 = vector;
        for (int i = 0; i < vector2.size(); ++i) {
            x4var x4var2;
            x4var x4var3 = (x4var)vector2.elementAt(i);
            int n = i;
            for (int j = i + 1; j < vector2.size(); ++j) {
                x4var2 = (x4var)vector2.elementAt(j);
                if (x4var2.cmp((x4var)vector2.elementAt(n)) >= 0) continue;
                n = j;
            }
            x4var2 = (x4var)vector2.elementAt(n);
            vector2.setElementAt(x4var2, i);
            vector2.setElementAt(x4var3, n);
        }
        if (bl) {
            vector2 = x4var.removeUnknown(vector2);
        }
        return vector2;
    }

    public static Vector removeUnknown(Vector vector) {
        Vector<x4var> vector2 = new Vector<x4var>();
        for (int i = 0; i < vector.size(); ++i) {
            x4var x4var2 = (x4var)vector.elementAt(i);
            if (x4var2.what.equals("ZZZ")) continue;
            vector2.addElement(x4var2);
        }
        return vector2;
    }

    public static Vector getVecMonit(Vector vector) {
        boolean bl;
        x4var x4var2;
        int n;
        int n2 = 0;
        int n3 = 0;
        x4var x4var3 = null;
        x4var x4var4 = null;
        Vector<x4var> vector2 = new Vector<x4var>();
        boolean bl2 = false;
        for (n = 0; n < vector.size(); ++n) {
            x4var2 = (x4var)vector.elementAt(n);
            bl = false;
            if (x4var2.Header.equals("MONIT")) {
                bl = true;
            }
            if (!bl) continue;
            bl2 = true;
            if (extDebug) {
                System.out.println("___getVecMonit:" + n + " iCol=[" + x4var2.iCol + "]" + " Header=[" + x4var2.Header + "]" + " what=[" + x4var2.what + "]" + "\twhatVar:" + x4var2.whatVar + "\torder=" + x4var2.strVarOrderFlag + "\tDataType=" + x4var2.DataType + "\tFamilyCode=" + x4var2.FamilyCode + "\tvarNum=" + x4var2.Variable_Number);
            }
            vector2.addElement(x4var2);
            x4var3 = x4var2;
            ++n2;
        }
        if (!bl2) {
            return vector2;
        }
        for (n = 0; n < vector.size(); ++n) {
            x4var2 = (x4var)vector.elementAt(n);
            bl = false;
            if (x4var2.Header.equals("MONIT-ERR")) {
                bl = true;
            }
            if (!bl) continue;
            if (extDebug) {
                System.out.println("___getVecMonit:" + n + " iCol=[" + x4var2.iCol + "]" + " Header=[" + x4var2.Header + "]" + " what=[" + x4var2.what + "]" + "\twhatVar:" + x4var2.whatVar + "\torder=" + x4var2.strVarOrderFlag + "\tDataType=" + x4var2.DataType + "\tFamilyCode=" + x4var2.FamilyCode + "\tvarNum=" + x4var2.Variable_Number);
            }
            vector2.addElement(x4var2);
            x4var4 = x4var2;
            ++n3;
        }
        if (n3 == 1 && n2 == 1) {
            x4var4.xvarMONIT = x4var3;
        }
        return vector2;
    }

    public int cmp(x4var x4var2) {
        if (x4var2.Variable_Number < this.Variable_Number) {
            return 1;
        }
        if (x4var2.Variable_Number > this.Variable_Number) {
            return -1;
        }
        if (x4var2.strVarOrderFlag.compareTo(this.strVarOrderFlag) < 0) {
            return 1;
        }
        if (x4var2.strVarOrderFlag.compareTo(this.strVarOrderFlag) > 0) {
            return -1;
        }
        if (this.DataType != null && x4var2.DataType != null) {
            if (x4var2.DataType.toUpperCase().compareTo(this.DataType.toUpperCase()) < 0) {
                return 1;
            }
            if (x4var2.DataType.toUpperCase().compareTo(this.DataType.toUpperCase()) > 0) {
                return -1;
            }
        }
        if (this.Header != null && x4var2.Header != null) {
            if (!x4var2.Header.toUpperCase().startsWith("ERR-")) {
                if (x4var2.Header.toUpperCase().compareTo(this.Header.toUpperCase()) < 0) {
                    return 1;
                }
                if (x4var2.Header.toUpperCase().compareTo(this.Header.toUpperCase()) > 0) {
                    return -1;
                }
            } else {
                if (x4var.getErrHeader(x4var2.Header).compareTo(x4var.getErrHeader(this.Header)) < 0) {
                    return 1;
                }
                if (x4var.getErrHeader(x4var2.Header).compareTo(x4var.getErrHeader(this.Header)) > 0) {
                    return -1;
                }
            }
        }
        if (x4var2.cpointer < this.cpointer) {
            return 1;
        }
        if (x4var2.cpointer > this.cpointer) {
            return -1;
        }
        return 0;
    }

    public static String getErrHeader(String string) {
        if ((string = string.toUpperCase()).length() < 5) {
            return string;
        }
        if (!string.startsWith("ERR-")) {
            return string;
        }
        if (string.length() == 5 && Character.isDigit(string.charAt(4))) {
            string = string.substring(0, 4) + "0" + string.substring(4);
            return string;
        }
        return string;
    }

    public int putToArray(int n, Float f) {
        if (this.xx4dat == null) {
            return -1;
        }
        if (f == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.xx4dat.length) {
            return -1;
        }
        this.xx4dat[n] = new Float(f.floatValue());
        return 0;
    }

    public Float getFromArray(int n) {
        if (this.xx4dat == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.xx4dat.length) {
            return null;
        }
        return this.xx4dat[n];
    }

    public int allocArray(int n) {
        try {
            this.xx4dat = new Float[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            return -1;
        }
        for (int i = 0; i < n; ++i) {
            this.xx4dat[i] = null;
        }
        return 0;
    }

    public int putToArray0(int n, x4var x4var2) {
        if (this.me4dat == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.me4dat.length) {
            return -1;
        }
        if (x4var2 == null) {
            this.me4dat[n] = null;
            return -1;
        }
        this.me4dat[n] = x4var2.clone();
        return 0;
    }

    public x4var getFromArray0(int n) {
        if (this.me4dat == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.me4dat.length) {
            return null;
        }
        return this.me4dat[n];
    }

    public int allocArray0(int n) {
        try {
            this.me4dat = new x4var[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            return -1;
        }
        for (int i = 0; i < n; ++i) {
            this.me4dat[i] = null;
        }
        return 0;
    }

    public Float getValue(int n) {
        this.iRow = n;
        Float f = null;
        if (this.xdata != null) {
            f = this.xdata.getValue(this.iCol, n);
        }
        this.nowValue = f;
        this.origValue = f;
        if (this.xvarInPercent != null) {
            this.xvarInPercent.getValue(n);
        }
        return f;
    }

    public void cleanc4data() {
        this.c4dataValue = null;
        this.c4errorValue = null;
        this.c4errorPercent = null;
    }

    public Float getValueInBasicUnits(int n) {
        this.iRow = n;
        Float f = null;
        double d = 0.0;
        double d2 = 0.0;
        if (this.xdata != null) {
            f = this.xdata.getValue(this.iCol, n);
        }
        this.origValue = f;
        if (f != null) {
            float f2 = f.floatValue();
            if (this.ConversionFactor != 0.0f) {
                if (Math.abs(this.ConversionFactor) >= 1.0f) {
                    f2 *= this.ConversionFactor;
                } else {
                    double d3 = 1.0f / this.ConversionFactor;
                    f2 = (float)((double)f2 / d3);
                }
                f = new Float(f2);
            }
        }
        this.nowValue = f;
        if (this.xvarInPercent != null) {
            this.xvarInPercent.getValueInBasicUnits(n);
        }
        this.c4dataValue = f;
        return f;
    }

    public static int printHeaders(Vector vector, PrintWriter printWriter) {
        if (vector == null) {
            return 0;
        }
        for (int i = 0; i < vector.size(); ++i) {
            x4var x4var2 = (x4var)vector.elementAt(i);
            if (printWriter == null) continue;
            printWriter.println(" " + (i + 1) + ")\t" + x4var2.toString());
        }
        return vector.size();
    }

    public static int getMaxIndepVarNum(Vector vector) {
        int n = 0;
        if (vector == null) {
            return 0;
        }
        for (int i = 0; i < vector.size(); ++i) {
            x4var x4var2 = (x4var)vector.elementAt(i);
            if (x4var2.Variable_Value == 0 || x4var2.Variable_Number <= n) continue;
            n = x4var2.Variable_Number;
        }
        return n;
    }

    public static int printData(Vector vector, PrintWriter printWriter, int n, int n2) {
        x4var x4var2;
        int n3 = 0;
        int n4 = vector.size();
        for (int i = 0; i < vector.size(); ++i) {
            x4var2 = (x4var)vector.elementAt(i);
            int n5 = x4var2.xdata.DnRow;
            if (n5 <= n3) continue;
            n3 = n5;
        }
        if (printWriter != null) {
            printWriter.println(x4var.strpad("#DATA", n) + x4var.strpad(n2 + "", lnn) + x4var.strpad(n4 + "", lnn) + lnn);
        }
        int n6 = 0;
        for (int i = 0; i < n3; ++i) {
            String string = "";
            string = lii > 1 ? x4var.padstr(n6 + 1 + ") ", lii) : x4var.padstr("", lii);
            boolean bl = true;
            for (int j = 0; j < n4; ++j) {
                String string2;
                x4var2 = (x4var)vector.elementAt(j);
                Float f = x4var2.getValue(i);
                if (f == null) {
                    string2 = "";
                } else {
                    string2 = f + "";
                    if (x4var2.isItYValue()) {
                        bl = false;
                    }
                    string2 = x4var.double2str(f.floatValue());
                }
                string = string + x4var.strpad(string2, lnn);
            }
            if (bl) continue;
            if (printWriter != null) {
                printWriter.println(string);
            }
            ++n6;
        }
        if (printWriter != null) {
            printWriter.println(x4var.strpad("#ENDDATA", n) + x4var.strpad(n6 + "", lnn) + n4);
        }
        return n6;
    }

    public static String double2str(double d) {
        int n;
        String string = "";
        string = String.format("%g", d);
        string = x4var.myStrReplace(string, "00000e", "e");
        string = x4var.myStrReplace(string, "0000e", "e");
        string = x4var.myStrReplace(string, "000e", "e");
        string = x4var.myStrReplace(string, "00e", "e");
        if ((string = x4var.myStrReplace(string, "0e", "e")).indexOf(".") >= 0 && string.indexOf("e") < 0) {
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        if ((n = string.indexOf(".")) < 0 && string.indexOf("e") < 0) {
            string = string + ".";
        }
        return string;
    }

    public int findMinMaxData() {
        int n = this.xdata.DnRow;
        x4var x4var2 = this;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Float f = x4var2.getValue(i);
            if (f == null) continue;
            float f2 = f.floatValue();
            if (this.ConversionFactor > 0.0f) {
                f = new Float(f2 *= this.ConversionFactor);
            }
            if (this.rMin == null) {
                this.rMin = f;
            } else {
                float f3 = this.rMin.floatValue();
                if (f2 < f3) {
                    this.rMin = f;
                }
            }
            if (this.rMax == null) {
                this.rMax = f;
                continue;
            }
            float f4 = this.rMax.floatValue();
            if (!(f2 > f4)) continue;
            this.rMax = f;
        }
        return n2;
    }

    public int getNumOfValues() {
        int n = this.xdata.DnRow;
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        x4var x4var2 = this;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            String string;
            String string2;
            Float f = x4var2.getValue(i);
            if (f == null || (string2 = (String)hashtable.get(string = "" + f)) != null) continue;
            ++n2;
            hashtable.put(string, string);
        }
        return n2;
    }

    public void findMinMaxData1clr() {
        this.rMin1 = null;
        this.rMax1 = null;
    }

    public void findMinMaxData1put(Float f) {
        if (f == null) {
            return;
        }
        float f2 = f.floatValue();
        if (this.ConversionFactor > 0.0f) {
            f = new Float(f2 *= this.ConversionFactor);
        }
        if (this.rMin1 == null) {
            this.rMin1 = f;
        } else {
            float f3 = this.rMin1.floatValue();
            if (f2 < f3) {
                this.rMin1 = f;
            }
        }
        if (this.rMax1 == null) {
            this.rMax1 = f;
        } else {
            float f4 = this.rMax1.floatValue();
            if (f2 > f4) {
                this.rMax1 = f;
            }
        }
    }

    public String toString() {
        String string = " " + x4var.strpad(this.Header, 10) + " varNum=" + x4var.strpad("" + this.Variable_Number, 2) + " what=" + x4var.strpad(this.what, 12) + " order=" + x4var.strpad(this.strVarOrderFlag, 10) + " DataType=" + x4var.strpad(this.DataType, 2) + " Units=" + x4var.strpad(this.Units, 3) + " Factor=" + x4var.strpad(this.ConversionFactor + "", 10);
        string = string + " entry=" + this.xdata.subent.Entry;
        string = string + " sub=" + x4var.strpad("" + this.xdata.subent.sub, 3);
        string = this.xdata.flagCommon ? string + " Common" : string + " Data  ";
        string = string + " Col:" + this.iCol + "/" + this.xdata.DnCol + "*" + this.xdata.DnRow;
        return string;
    }

    public static String myStrReplace(String string, String string2, String string3) {
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

    public static String strpad(String string, int n) {
        String string2 = x4var.strpad(string, n, false);
        return string2;
    }

    public static String strpad(String string, int n, boolean bl) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = bl ? string.substring(0, n) : string;
            return string2;
        }
        String string3 = string;
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string3 = string3 + " ";
        }
        return string3;
    }

    public static String padstr(String string, int n) {
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
}
