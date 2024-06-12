package zvv.x4;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR-Dictionary-227 internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-07-19
 * @since           2011-02-11
 *
 * Program:         x4dict227.java
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

public class x4dict227
extends x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Vector vDict = null;
    static Hashtable TimeUnits = new Hashtable();
    public int irecDate = 0;
    public String Units;
    public String UnitFamilyCode;
    public float ConversionFactor;
    public String Nuclide;
    public String ASymbol;
    public int NumericalEquiv;
    public String UseFlag;
    public String SpinParity;
    public String HalfLifeFlag;
    public String sHalfLife;
    public String HalfLifeUnits;
    public String Stable;
    public float Abundance;
    public double AtomicWeight;
    public String Expansion;
    public double MASS_u;
    public double MASS_kev;
    public double MASS_mev;
    public Double Spin = null;
    public int iZA;
    public int zz;
    public int aa;
    public double HalfLife;
    public double HalfLifeSec = 0.0;
    public String Symbol;
    public String sMeta;
    public String IsotopeName;
    public String IsotopeName2;
    public String IsotopeName3;
    boolean flagNaturalElement = false;

    public static void main(String[] stringArray) {
        x4dict227 x4dict2272;
        int n;
        Vector vector;
        int n2;
        String string = "";
        String string2 = "x4dict/";
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2023\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict227 x4dict/");
            System.exit(0);
        }
        if ((n2 = 0) < stringArray.length) {
            string = stringArray[n2++];
        }
        if (string2.indexOf("/") >= 0) {
            string2 = string;
        }
        x4dict.setDirOfArchivedDicts(string2);
        x4dict227.init();
        boolean bl = x4dict227.exists(227, "1/MILLI-EV");
        sysOut.println("  CS: chk=" + bl);
        sysOut.println(" qqq: chk=" + x4dict.exists(227, "qqq"));
        String string3 = x4dict227.sGetIsotopesOfNaturalElement("CU-0");
        sysOut.println("___str=[" + string3 + "]");
        x4dict227.init();
        x4dict227 x4dict2273 = x4dict227.findinx4dict("CU-0");
        if (x4dict2273 != null) {
            sysOut.println("CU-0: x4f=" + x4dict2273.toString());
        }
        if ((x4dict2273 = x4dict227.findinx4dict("29-CU-0")) != null) {
            sysOut.println("29-CU-0: x4f=" + x4dict2273.toString());
            vector = x4dict2273.vGetIsotopesOfNaturalElement();
            sysOut.println("29-CU-0: vec:" + vector.size());
            for (n = 0; n < vector.size(); ++n) {
                x4dict2272 = (x4dict227)vector.elementAt(n);
                sysOut.println(n + "[" + x4dict2273.Code + "]" + "[" + x4dict2272.IsotopeName2 + "]" + " Abu=" + x4dict2272.Abundance);
            }
        }
        string3 = x4dict227.sGetIsotopesOfNaturalElement("FE-0");
        sysOut.println("FE-0: [" + string3 + "]");
        x4dict2273 = x4dict227.findinx4dict("FE-0");
        if (x4dict2273 != null) {
            sysOut.println("FE-0: x4f=" + x4dict2273.toString());
            vector = x4dict2273.vGetIsotopesOfNaturalElement();
            sysOut.println("FE-0: vec:" + vector.size());
            for (n = 0; n < vector.size(); ++n) {
                x4dict2272 = (x4dict227)vector.elementAt(n);
                sysOut.println(n + "[" + x4dict2273.Code + "]" + "[" + x4dict2272.IsotopeName2 + "]" + " Abu=" + x4dict2272.Abundance);
            }
        }
        if ((x4dict2273 = x4dict227.findinx4dict("1/MILLI-EV")) != null) {
            sysOut.println(" x4f=" + x4dict2273.toString());
        }
        if ((string3 = x4dict227.getUnitFamilyCode("1/MILLI-EV")) != null) {
            sysOut.println(" getUnitFamilyCode('1/MILLI-EV')=" + string3);
        }
        sysOut.println(" getBasicUnits('1/MILLI-EV')=" + x4dict227.getBasicUnits("1/MILLI-EV"));
        sysOut.println(" getConversionFactor('1/MILLI-EV')=" + x4dict227.getConversionFactor("1/MILLI-EV"));
        x4dict2273 = x4dict227.findByUnitFamilyCode("E");
        if (x4dict2273 != null) {
            sysOut.println(" x4f=" + x4dict2273.toString());
        }
        if ((x4dict2273 = x4dict227.findByUnitFamilyCode("E")) != null) {
            sysOut.println(" x4f=" + x4dict2273.toString());
        }
        if ((x4dict2273 = x4dict227.findByZA(new Float(1001.0f))) != null) {
            sysOut.println(" x4f=" + x4dict2273.toString());
        }
        if ((x4dict2273 = x4dict227.findByZA(new Float(1.0f))) != null) {
            sysOut.println(" x4f=" + x4dict2273.toString());
        }
        x4dict227.printNaturalElements();
        x4dict227.printHalfLife("HalfLife-0.txt");
        x4dict227.printAbundances("Abundance-0.txt");
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public x4dict227() {
    }

    public x4dict227(String string) {
        super(string);
        this.Units = x4dict227.strExtractStr(string, 13, 23, "");
        this.UnitFamilyCode = x4dict227.strExtractStr(string, 79, 82, "");
        this.ConversionFactor = x4dict227.strExtractFloat(string, 83, 93, 1.0f);
        this.shortHelp = x4dict227.strExtractStr(string, 44, 78, "");
        this.irecDate = x4dict227.strExtractInt(string, 6, 11, 0);
        if (this.irecDate < 202306) {
            this.old227(string);
        } else {
            this.new227(string);
        }
        this.toString();
    }

    public void new227(String string) {
        this.Nuclide = x4dict227.strExtractStr(string, 13, 26, "");
        String[] stringArray = this.Nuclide.split("-");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (i == 1) {
                this.Symbol = string2;
            }
            if (i == 2) {
                this.ASymbol = string2 + this.Symbol;
            }
            if (i != 3 || !string2.equals("G")) continue;
            this.Nuclide = stringArray[0] + "-" + stringArray[1] + "-" + stringArray[2];
        }
        if (this.Symbol.length() > 1) {
            this.Symbol = this.Symbol.charAt(0) + this.Symbol.substring(1).toLowerCase();
        }
        this.NumericalEquiv = x4dict227.strExtractInt(string, 50, 56, 0);
        this.UseFlag = x4dict227.strExtractStr(string, 57, 57, "");
        this.SpinParity = x4dict227.strExtractStr(string, 58, 63, "");
        if (!this.SpinParity.trim().equals("")) {
            this.Spin = new Double(x4dict227.strExtractFloat(string, 58, 63, 0.0f));
        }
        this.HalfLifeFlag = x4dict227.strExtractStr(string, 64, 64, "");
        this.sHalfLife = x4dict227.strExtractStr(string, 65, 75, "");
        this.sHalfLife = this.sHalfLife.trim();
        this.Stable = x4dict227.strExtractStr(string, 76, 78, "");
        this.Abundance = x4dict227.strExtractFloat(string, 79, 88, 0.0f);
        this.AtomicWeight = x4dict227.strExtractDouble(string, 89, 101, 0.0);
        this.Expansion = x4dict227.strExtractStr(string, 102, 122, "");
        this.AtomicWeight *= 1000000.0;
        if (this.Expansion.trim().startsWith("Natural")) {
            this.flagNaturalElement = true;
        }
        if (this.flagNaturalElement) {
            this.ASymbol = this.Symbol.toUpperCase();
        }
        this.HalfLifeSec = this.HalfLife = (double)x4dict227.strExtractFloat(string, 65, 75, 0.0f);
        this.HalfLifeUnits = "S";
        if (this.sHalfLife.equals("")) {
            this.HalfLifeUnits = "";
        }
        if (this.Stable.equals("S")) {
            this.sHalfLife = "STABLE.";
        }
        this.sMeta = x4dict227.strExtractStr(string, 56, 56, "");
        if (this.flagNaturalElement && this.sMeta.equals("0")) {
            this.NumericalEquiv /= 10;
        }
        if (this.sMeta.equals("0")) {
            this.sMeta = "";
        }
        this.IsotopeName = x4dict227.strExtractStr(string, 17, 24, "");
        if (this.IsotopeName.endsWith("-G")) {
            this.IsotopeName = this.IsotopeName.substring(0, this.IsotopeName.length() - 2);
        }
        if (this.IsotopeName.endsWith("-A")) {
            this.IsotopeName = this.IsotopeName.substring(0, this.IsotopeName.length() - 1) + "M";
        }
        if (this.IsotopeName.endsWith("-M")) {
            this.IsotopeName = this.IsotopeName + this.sMeta;
        }
        this.IsotopeName2 = this.IsotopeName;
        if (this.IsotopeName2.length() > 1) {
            this.IsotopeName2 = this.IsotopeName2.charAt(0) + this.IsotopeName2.substring(1).toLowerCase();
        }
        if (this.IsotopeName2.equals("Nn-1")) {
            this.IsotopeName2 = "NN-1";
        }
        this.iZA = x4dict227.strExtractInt(string, 50, 55, 0);
        this.zz = this.iZA / 1000;
        this.aa = this.iZA % 1000;
        this.IsotopeName3 = x4dict227.padstr(this.zz + "", 3) + "-" + this.IsotopeName2;
        this.MASS_u = this.AtomicWeight / 1000000.0;
        this.MASS_kev = this.MASS_u * 931494.0;
        this.MASS_mev = this.MASS_u * 931.494;
        this.Code = this.Nuclide = this.Nuclide.trim();
        this.shortHelp = this.Expansion;
    }

    public void old227(String string) {
        this.Nuclide = x4dict227.strExtractStr(string, 13, 24, "");
        this.ASymbol = x4dict227.strExtractStr(string, 44, 48, "");
        this.ASymbol = this.ASymbol.trim();
        this.NumericalEquiv = x4dict227.strExtractInt(string, 50, 56, 0);
        this.UseFlag = x4dict227.strExtractStr(string, 57, 57, "");
        this.SpinParity = x4dict227.strExtractStr(string, 58, 63, "");
        if (!this.SpinParity.trim().equals("")) {
            this.Spin = new Double(x4dict227.strExtractFloat(string, 58, 63, 0.0f));
        }
        this.HalfLifeFlag = x4dict227.strExtractStr(string, 64, 64, "");
        this.sHalfLife = x4dict227.strExtractStr(string, 65, 75, "");
        this.HalfLifeUnits = x4dict227.strExtractStr(string, 76, 78, "");
        this.Abundance = x4dict227.strExtractFloat(string, 79, 88, 0.0f);
        this.AtomicWeight = x4dict227.strExtractDouble(string, 89, 101, 0.0);
        this.Expansion = x4dict227.strExtractStr(string, 102, 122, "");
        if (this.Expansion.trim().startsWith("Natural")) {
            this.AtomicWeight = this.Abundance * 1000000.0f;
            this.Abundance = 0.0f;
            this.flagNaturalElement = true;
        }
        this.HalfLife = x4dict227.strExtractFloat(string, 65, 75, 0.0f);
        Double d = (Double)TimeUnits.get(this.HalfLifeUnits);
        if (d != null) {
            this.HalfLifeSec = d * this.HalfLife;
        }
        this.Symbol = "";
        if (string.charAt(16) != '-') {
            this.Symbol = this.Symbol + string.charAt(16);
        }
        if (string.charAt(17) != '-') {
            this.Symbol = this.Symbol + ("" + string.charAt(17)).toLowerCase();
        }
        this.sMeta = x4dict227.strExtractStr(string, 56, 56, "");
        if (this.sMeta.equals("0")) {
            this.sMeta = "";
        }
        this.IsotopeName = x4dict227.strExtractStr(string, 17, 24, "");
        if (this.IsotopeName.endsWith("-A")) {
            this.IsotopeName = this.IsotopeName.substring(0, this.IsotopeName.length() - 1) + "M";
        }
        if (this.IsotopeName.endsWith("-M")) {
            this.IsotopeName = this.IsotopeName + this.sMeta;
        }
        this.IsotopeName2 = this.IsotopeName;
        if (this.IsotopeName2.length() > 1) {
            this.IsotopeName2 = this.IsotopeName2.charAt(0) + this.IsotopeName2.substring(1).toLowerCase();
        }
        if (this.IsotopeName2.equals("Nn-1")) {
            this.IsotopeName2 = "NN-1";
        }
        this.iZA = x4dict227.strExtractInt(string, 50, 55, 0);
        this.zz = this.iZA / 1000;
        this.aa = this.iZA % 1000;
        this.IsotopeName3 = x4dict227.padstr(this.zz + "", 3) + "-" + this.IsotopeName2;
        this.MASS_u = this.AtomicWeight / 1000000.0;
        this.MASS_kev = this.MASS_u * 931494.0;
        this.MASS_mev = this.MASS_u * 931.494;
        this.Code = this.Nuclide = this.Nuclide.trim();
        this.shortHelp = this.Expansion;
    }

    public x4dict x4dict_new(String string) {
        if (extDebug) {
            sysOut.println(" x4dict227.x4dict_new=" + string.trim());
        }
        x4dict227 x4dict2272 = new x4dict227(string);
        return x4dict2272;
    }

    public Vector vGetIsotopesOfNaturalElement() {
        Vector<x4dict227> vector = new Vector<x4dict227>();
        x4dict227.init();
        if (!this.flagNaturalElement) {
            return vector;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (x4dict2272.flagNaturalElement || !x4dict2272.Symbol.equals(this.Symbol) || x4dict2272.Abundance <= 0.0f || x4dict2272.AtomicWeight <= 0.0) continue;
            vector.addElement(x4dict2272);
        }
        return vector;
    }

    public static String sGetIsotopesOfNaturalElement(String string) {
        String string2 = "";
        x4dict227.init();
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 == null) {
            return string2;
        }
        if (!x4dict2272.flagNaturalElement) {
            return string2;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2273 = (x4dict227)vDict.elementAt(i);
            if (x4dict2273.flagNaturalElement || !x4dict2273.Symbol.equals(x4dict2272.Symbol) || x4dict2273.Abundance <= 0.0f || x4dict2273.AtomicWeight <= 0.0) continue;
            if (!string2.equals("")) {
                string2 = string2 + ";";
            }
            string2 = string2 + x4dict2273.IsotopeName2;
        }
        return string2;
    }

    public static int nGetIsotopesOfNaturalElement(String string) {
        int n = 0;
        x4dict227.init();
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 == null) {
            return n;
        }
        if (!x4dict2272.flagNaturalElement) {
            return n;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2273 = (x4dict227)vDict.elementAt(i);
            if (x4dict2273.flagNaturalElement || !x4dict2273.Symbol.equals(x4dict2272.Symbol) || x4dict2273.Abundance <= 0.0f || x4dict2273.AtomicWeight <= 0.0) continue;
            ++n;
        }
        return n;
    }

    public boolean cmpCode(String string) {
        x4dict227.init();
        if (this.Code.equals(string)) {
            return true;
        }
        if (this.Code.trim().equals(string)) {
            return true;
        }
        if (this.IsotopeName.equals(string)) {
            return true;
        }
        return this.IsotopeName2.equals(string);
    }

    public static x4dict227 findinx4dict(String string) {
        x4dict227.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (!x4dict2272.cmpCode(string)) continue;
            return x4dict2272;
        }
        return null;
    }

    public static x4dict227 findByUnitFamilyCode(String string) {
        x4dict227.init();
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (!x4dict2272.UnitFamilyCode.equals(string) || x4dict2272.ConversionFactor != 1.0f) continue;
            return x4dict2272;
        }
        return null;
    }

    public static x4dict227 findByZA(Float f) {
        if (f == null) {
            return null;
        }
        int n = (int)f.floatValue();
        x4dict227.init();
        for (int i = vDict.size() - 1; i >= 0; --i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (n != x4dict2272.iZA) continue;
            return x4dict2272;
        }
        return null;
    }

    public static String getMyTimeUnits(String string) {
        if (string.equals("S")) {
            return "SEC";
        }
        if (string.equals("M")) {
            return "MIN";
        }
        if (string.equals("H")) {
            return "HR";
        }
        if (string.equals("D")) {
            return "DAY";
        }
        if (string.equals("Y")) {
            return "YR";
        }
        if (string.equals("MS")) {
            return "MSEC";
        }
        if (string.equals("US")) {
            return "USEC";
        }
        if (string.equals("NS")) {
            return "NSEC";
        }
        return "??";
    }

    public static String getUnitFamilyCode(String string) {
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 == null) {
            return null;
        }
        return x4dict2272.UnitFamilyCode;
    }

    public static float getConversionFactor(String string) {
        x4dict227 x4dict2272 = x4dict227.findinx4dict(string);
        if (x4dict2272 == null) {
            return 0.0f;
        }
        return x4dict2272.ConversionFactor;
    }

    public static String getBasicUnits(String string) {
        x4dict227.init();
        String string2 = x4dict227.getUnitFamilyCode(string);
        if (string2 == null) {
            return null;
        }
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (!x4dict2272.UnitFamilyCode.equals(string2) || x4dict2272.ConversionFactor != 1.0f) continue;
            return x4dict2272.Units;
        }
        return null;
    }

    public static void init() {
        if (vDict == null) {
            if (extDebug) {
                sysOut.println("---init227()---");
            }
            x4dict227 x4dict2272 = new x4dict227();
            String string = x4dict227.getFileName(227);
            vDict = x4dict2272.readExforDict(string);
            hashDicts.put(string, vDict);
        }
    }

    public String toString() {
        String string = " zz=" + x4dict227.strpad(this.zz + "", 3) + " aa=" + x4dict227.strpad(this.aa + "", 3) + " Code=" + x4dict227.strpad("[" + this.Code + "]", 12) + " " + " Nuclide=" + x4dict227.strpad("[" + this.Nuclide + "]", 12) + " " + " ASymbol=" + x4dict227.strpad(this.ASymbol + "", 5) + " " + " Symbol=" + x4dict227.strpad("[" + this.Symbol + "]", 5) + " " + " IsotopeName=" + x4dict227.strpad("[" + this.IsotopeName + "]", 12) + " " + " IsotopeName2=" + x4dict227.strpad("[" + this.IsotopeName2 + "]", 12) + " " + " NumEq=" + x4dict227.strpad(this.NumericalEquiv + "", 7) + " ";
        string = string + " Expansion=" + x4dict227.strpad(this.Expansion + "", 21) + " ";
        return string;
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict227.strExtractStr(string, 13, 24, "");
        return !string2.equals("");
    }

    public static void printNaturalElements() {
        x4dict227.init();
        int n = 0;
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (!x4dict2272.Code.endsWith("-0") || !(x4dict2272.Abundance > 0.0f) || !(x4dict2272.AtomicWeight > 0.0)) continue;
            sysOut.println(" naturalElem:" + x4dict227.padstr(++n + 1 + ")", 5) + x4dict2272.toString());
        }
    }

    public static void printAbundances(String string) {
        PrintWriter printWriter = sysOut;
        x4dict227.init();
        BufferedWriter bufferedWriter = null;
        if (string != null) {
            bufferedWriter = x4dict227.openOutFile(string);
            printWriter = new PrintWriter(bufferedWriter);
        }
        printWriter.println("#---Abundance---");
        printWriter.println("#---Generated: " + x4dict227.getDateNow() + " by V.Zerkin, IAEA-NDS");
        printWriter.println("#---Source:    EXFOR Dictionary-227");
        printWriter.println("#Isotope-------Abundance(%)--");
        int n = 0;
        for (int i = 0; i < vDict.size(); ++i) {
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (x4dict2272.Code.endsWith("-0") || x4dict2272.Abundance <= 0.0f || x4dict2272.AtomicWeight <= 0.0) continue;
            ++n;
            String string2 = x4dict227.float2strG(x4dict2272.Abundance);
            String string3 = x4dict227.strpad(x4dict2272.IsotopeName3, 14) + " " + x4dict227.strpad(string2, 13) + " %";
            printWriter.println(string3);
        }
        printWriter.println("#---------------------------------------------------");
        printWriter.println("//");
        x4dict227.closeOutFile(bufferedWriter);
    }

    public static void printHalfLife(String string) {
        PrintWriter printWriter = sysOut;
        x4dict227.init();
        BufferedWriter bufferedWriter = null;
        if (string != null) {
            bufferedWriter = x4dict227.openOutFile(string);
            printWriter = new PrintWriter(bufferedWriter);
        }
        printWriter.println("#---HalfLife---");
        printWriter.println("#---Generated: " + x4dict227.getDateNow() + " by V.Zerkin, IAEA-NDS");
        printWriter.println("#---Source:    EXFOR Dictionary-227");
        printWriter.println("#DefUnits: SEC");
        printWriter.println("#Isotope-------T1/2---------Units-------T1/2 in sec--");
        int n = 0;
        for (int i = 0; i < vDict.size(); ++i) {
            Double d;
            x4dict227 x4dict2272 = (x4dict227)vDict.elementAt(i);
            if (x4dict2272.HalfLife <= 0.0 || (d = (Double)TimeUnits.get(x4dict2272.HalfLifeUnits)) == null) continue;
            ++n;
            String string2 = x4dict227.float2strG((float)x4dict2272.HalfLifeSec);
            String string3 = x4dict227.strpad(x4dict2272.IsotopeName3, 14) + " " + x4dict227.strpad(x4dict2272.sHalfLife, 12) + " " + x4dict227.strpad(x4dict227.getMyTimeUnits(x4dict2272.HalfLifeUnits), 6) + "     " + " " + string2;
            printWriter.println(string3);
        }
        printWriter.println("#---------------------------------------------------");
        printWriter.println("//");
        x4dict227.closeOutFile(bufferedWriter);
    }

    static {
        float f = 1.0f;
        TimeUnits.put("S", new Double(f));
        TimeUnits.put("M", new Double(f *= 60.0f));
        TimeUnits.put("H", new Double(f *= 60.0f));
        TimeUnits.put("D", new Double(f *= 24.0f));
        f = (float)((double)f * 365.2422);
        TimeUnits.put("Y", new Double(f));
        f = 0.001f;
        TimeUnits.put("MS", new Double(f));
        f = 1.0E-6f;
        TimeUnits.put("US", new Double(f));
        f = 1.0E-9f;
        TimeUnits.put("NS", new Double(f));
    }
}
