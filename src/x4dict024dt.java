package zvv.x4;

/**
 * EXFOR-Dictionary-024/Data-Type internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2021-11-29
 * @since           2011-02-27
 *
 * Program:         x4dict024dt.java
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

public class x4dict024dt {
    char chr = (char)32;
    char chAssociatedQuantity = '\u0000';
    String expansion = "";
    String shortExpansion = "";
    x4dict024dt[] arr = null;
    static final int TYPE_VALUE = 1;
    static final int TYPE_MIN = 2;
    static final int TYPE_MAX = 3;
    static final int TYPE_APRX = 4;
    static final int TYPE_MULT = 5;
    static final int TYPE_ERROR = 9;
    static final x4dict024dt[] dtAll = new x4dict024dt[]{new x4dict024dt('0', "Flags, etc.       ", new x4dict024dt[]{new x4dict024dt('1', "flag              "), new x4dict024dt('2', "decay flag        "), new x4dict024dt('3', "level flag        "), new x4dict024dt('4', "miscellaneous data")}), new x4dict024dt('1', "Assumed values", new x4dict024dt[]{new x4dict024dt('1', "monitor       "), new x4dict024dt('5', "assumed       ")}), new x4dict024dt('2', "Data ", new x4dict024dt[]{new x4dict024dt('1', "data "), new x4dict024dt('2', "ratio")}), new x4dict024dt('3', "Resonance parameter", new x4dict024dt[]{new x4dict024dt('1', "quantum number     ", "QN "), new x4dict024dt('2', "energy             ", "ENR")}), new x4dict024dt('4', "Incident energy     ", new x4dict024dt[]{new x4dict024dt('1', "energy              ", "EN "), new x4dict024dt('2', "momentum            ", "Mom"), new x4dict024dt('3', "spectrum energy     ", "SpE"), new x4dict024dt('4', "spectrum temperature", "SpT"), new x4dict024dt('5', "wave-length         ", "WLn")}), new x4dict024dt('5', "Secondary energy  ", new x4dict024dt[]{new x4dict024dt('1', "particle energy   ", "E2   "), new x4dict024dt('2', "level energy      ", "LVL  "), new x4dict024dt('3', "excitation energy ", "EXC  "), new x4dict024dt('4', "Q value           ", "QVL  "), new x4dict024dt('5', "energy degradation", "EDGR "), new x4dict024dt('6', "energy gain       ", "EGN  "), new x4dict024dt('7', "level number      ", "LVN  "), new x4dict024dt('8', "linear momentum   ", "LMOM "), new x4dict024dt('9', "polarity          ", "PLR  ")}), new x4dict024dt('6', "Angle                ", new x4dict024dt[]{new x4dict024dt('1', "angle                ", "ANG"), new x4dict024dt('2', "cosine               ", "COS"), new x4dict024dt('3', "q (momentum transfer)", "QMT"), new x4dict024dt('8', "wave number          ", "WVN")}), new x4dict024dt('7', "Number            ", new x4dict024dt[]{new x4dict024dt('5', "coefficient number", "nCoef"), new x4dict024dt('6', "kq                ", "Kq   ")}), new x4dict024dt('8', "Other variable    ", new x4dict024dt[]{new x4dict024dt('2', "sample temperature", "TMS "), new x4dict024dt('3', "sample thickness  ", "THS "), new x4dict024dt('4', "polarization      ", "POL "), new x4dict024dt('5', "half-life         ", "HL  "), new x4dict024dt('6', "group number      ", "GRP "), new x4dict024dt('7', "decay constant    ", "DCON")}), new x4dict024dt('9', "Isotope/particle identification", new x4dict024dt[]{new x4dict024dt('1', "element          "), new x4dict024dt('2', "mass             "), new x4dict024dt('3', "isomer           "), new x4dict024dt('4', "monitor element  "), new x4dict024dt('5', "monitor mass     "), new x4dict024dt('9', "emitted nucleons ")})};
    static final x4dict024dt[] plt1 = new x4dict024dt[]{new x4dict024dt('1', "Value                    ", "Value"), new x4dict024dt('2', "Minimum                  ", "Min  "), new x4dict024dt('3', "Maximum                  ", "Max  "), new x4dict024dt('4', "Approximate              ", "Aprx "), new x4dict024dt('5', "One of multiple variables", "Mult "), new x4dict024dt('9', "Uncertainty              ", "Error")};
    static final x4dict024dt[] plt2val = new x4dict024dt[]{new x4dict024dt('1', "numerator                ", "nm"), new x4dict024dt('2', "denominator              ", "dn")};
    static final x4dict024dt[] plt23err = new x4dict024dt[]{new x4dict024dt('1', "error                    ", "Err"), new x4dict024dt('2', "resolution               ", "Resl "), new x4dict024dt('3', "half resolution          ", "hRes"), new x4dict024dt('4', "statistical error        ", "sErr"), new x4dict024dt('5', "partial error            ", "pErr"), new x4dict024dt('6', "partial error:MERC       ", "mErr")};
    static final x4dict024dt[] plt7 = new x4dict024dt[]{new x4dict024dt('1', "c.m. system              ")};
    static final x4dict024dt[] familyCodeAll = new x4dict024dt[]{new x4dict024dt('C', "Resonance energy          ", 'B'), new x4dict024dt('E', "Secondary energy          ", 'F'), new x4dict024dt('G', "Angle of outgoing particle", 'H'), new x4dict024dt('I', "Product charge            "), new x4dict024dt('J', "Product mass              "), new x4dict024dt('P', "Number of particles       "), new x4dict024dt('L', "Secondary linear momentum "), new x4dict024dt('M', "Linear momentum           ", 'R'), new x4dict024dt('N', "Coefficient number        "), new x4dict024dt('Z', "Flag                      "), new x4dict024dt('8', "Sample temperature        ", '9'), new x4dict024dt('6', "Half life                 ", '7'), new x4dict024dt('4', "Spin J                    "), new x4dict024dt('2', "Momentum l                "), new x4dict024dt('0', "Parity                    ")};

    public static void main(String[] stringArray) {
        x4dict024dt.printAllDt();
        System.out.println("\n\n");
        String string = x4dict024dt.getDataTypeExpansion("3");
        System.out.println("\tSearch: 3:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("32");
        System.out.println("\tSearch: 32:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("51");
        System.out.println("\tSearch: 51:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("52");
        System.out.println("\tSearch: 52:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion2("5");
        System.out.println("\tSearch: 5:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion2("5E2");
        System.out.println("\tSearch: 5E2:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion2("5LVL");
        System.out.println("\tSearch: 5LVL:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("22");
        System.out.println("\tSearch: 22:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("62");
        System.out.println("\tSearch: 62:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion("63");
        System.out.println("\tSearch: 63:\t[" + string + "]");
        string = x4dict024dt.getDataTypeExpansion2("7");
        System.out.println("\tSearch: 7:\t[" + string + "]");
        string = x4dict024dt.getPlottingFlagsExpansion("955");
        System.out.println("\tSearch: 955:\t[" + string + "]");
    }

    public x4dict024dt(char c, String string, x4dict024dt[] x4dict024dtArray) {
        this.chr = c;
        this.expansion = string.trim();
        this.arr = x4dict024dtArray;
    }

    public x4dict024dt(char c, String string) {
        this.chr = c;
        this.expansion = string.trim();
    }

    public x4dict024dt(char c, String string, String string2) {
        this.chr = c;
        this.expansion = string.trim();
        this.shortExpansion = string2.trim();
    }

    public x4dict024dt(char c, String string, char c2) {
        this.chr = c;
        this.expansion = string.trim();
        this.chAssociatedQuantity = c2;
    }

    public static String getPlottingFlagsExpansion(String string) {
        char c = ' ';
        char c2 = ' ';
        char c3 = ' ';
        String string2 = "";
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        if (string.length() > 1) {
            c2 = string.charAt(1);
        }
        if (string.length() > 2) {
            c3 = string.charAt(2);
        }
        block0: for (int i = 0; i < plt1.length; ++i) {
            x4dict024dt x4dict024dt2 = plt1[i];
            if (x4dict024dt2.chr != c) continue;
            string2 = string2 + x4dict024dt2.expansion;
            if (c <= '5') {
                for (int j = 0; j < plt2val.length; ++j) {
                    x4dict024dt x4dict024dt3 = plt2val[j];
                    if (x4dict024dt3.chr != c2) continue;
                    string2 = string2 + ": " + x4dict024dt3.expansion;
                    return string2;
                }
                if (c2 != '0') {
                    string2 = string2 + ": <wrong-char2>";
                }
                return string2;
            }
            if (c != '9') break;
            for (int j = 0; j < plt23err.length; ++j) {
                x4dict024dt x4dict024dt4 = plt23err[j];
                if (x4dict024dt4.chr != c2 && x4dict024dt4.chr != c3) continue;
                string2 = string2 + ": ";
                if (c2 == c3) {
                    string2 = string2 + "+-";
                } else if (x4dict024dt4.chr == c2) {
                    string2 = string2 + "+";
                } else if (x4dict024dt4.chr == c3) {
                    string2 = string2 + "-";
                }
                string2 = string2 + x4dict024dt4.expansion;
                break block0;
            }
            break;
        }
        return string2;
    }

    public static String getShortPlottingFlagsExpansion(String string) {
        char c = ' ';
        char c2 = ' ';
        char c3 = ' ';
        String string2 = "";
        if (string.equals("000")) {
            return "Param";
        }
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        if (string.length() > 1) {
            c2 = string.charAt(1);
        }
        if (string.length() > 2) {
            c3 = string.charAt(2);
        }
        block0: for (int i = 0; i < plt1.length; ++i) {
            x4dict024dt x4dict024dt2 = plt1[i];
            if (x4dict024dt2.chr != c) continue;
            if (c <= '5') {
                string2 = string2 + x4dict024dt2.shortExpansion;
                for (int j = 0; j < plt2val.length; ++j) {
                    x4dict024dt x4dict024dt3 = plt2val[j];
                    if (x4dict024dt3.chr != c2) continue;
                    string2 = string2 + "-" + x4dict024dt3.shortExpansion;
                    return string2;
                }
                if (c2 != '0') {
                    string2 = string2 + ": <wrong-char2>";
                }
                return string2;
            }
            if (c != '9') break;
            for (int j = 0; j < plt23err.length; ++j) {
                x4dict024dt x4dict024dt4 = plt23err[j];
                if (x4dict024dt4.chr != c2 && x4dict024dt4.chr != c3) continue;
                string2 = string2 + x4dict024dt4.shortExpansion;
                if (x4dict024dt4.chr == c2) {
                    string2 = string2 + "+";
                }
                if (x4dict024dt4.chr != c3) break block0;
                string2 = string2 + "-";
                break block0;
            }
            break;
        }
        return string2;
    }

    public static String getLabCMFlagExpansion(String string) {
        char c = ' ';
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() > 6) {
            c = string.charAt(6);
        }
        for (int i = 0; i < plt7.length; ++i) {
            x4dict024dt x4dict024dt2 = plt7[i];
            if (x4dict024dt2.chr != c) continue;
            string2 = string2 + x4dict024dt2.expansion;
            break;
        }
        return string2;
    }

    public static boolean getLabCMFlag(String string) {
        char c = ' ';
        String string2 = "";
        if (string == null) {
            return false;
        }
        if (string.length() > 6) {
            c = string.charAt(6);
        }
        for (int i = 0; i < plt7.length; ++i) {
            x4dict024dt x4dict024dt2 = plt7[i];
            if (x4dict024dt2.chr != c || x4dict024dt2.chr != '1') continue;
            return true;
        }
        return false;
    }

    public static String getFamilyCodeExpansion(String string) {
        char c = ' ';
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        for (int i = 0; i < familyCodeAll.length; ++i) {
            x4dict024dt x4dict024dt2 = familyCodeAll[i];
            if (x4dict024dt2.chr == c) {
                string2 = string2 + x4dict024dt2.expansion;
                break;
            }
            if (x4dict024dt2.chAssociatedQuantity != c) continue;
            string2 = string2 + "AQ:" + x4dict024dt2.expansion;
            break;
        }
        if (string2.equals("")) {
            string2 = "<no>";
        }
        return string2;
    }

    public static String getDataTypeExpansion(String string) {
        char c = ' ';
        char c2 = ' ';
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        if (string.length() > 1) {
            c2 = string.charAt(1);
        }
        block0: for (int i = 0; i < dtAll.length; ++i) {
            x4dict024dt x4dict024dt2 = dtAll[i];
            if (x4dict024dt2.chr != c) continue;
            string2 = string2 + x4dict024dt2.expansion;
            for (int j = 0; j < x4dict024dt2.arr.length; ++j) {
                x4dict024dt x4dict024dt3 = x4dict024dt2.arr[j];
                if (x4dict024dt3.chr != c2) continue;
                string2 = string2 + ": " + x4dict024dt3.expansion;
                break block0;
            }
            break;
        }
        return string2;
    }

    public static String getDataTypeExpansion2(String string) {
        char c = ' ';
        String string2 = " ";
        String string3 = "";
        if (string == null) {
            return string3;
        }
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        if (string.length() > 1) {
            string2 = string.substring(1);
        }
        block0: for (int i = 0; i < dtAll.length; ++i) {
            x4dict024dt x4dict024dt2 = dtAll[i];
            if (x4dict024dt2.chr != c) continue;
            string3 = string3 + x4dict024dt2.expansion;
            for (int j = 0; j < x4dict024dt2.arr.length; ++j) {
                x4dict024dt x4dict024dt3 = x4dict024dt2.arr[j];
                if (!x4dict024dt3.shortExpansion.equals(string2)) continue;
                string3 = "" + x4dict024dt3.expansion;
                break block0;
            }
            break;
        }
        return string3;
    }

    public static String getDataTypeShortExpansion(String string) {
        char c = ' ';
        char c2 = ' ';
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() > 0) {
            c = string.charAt(0);
        }
        if (string.length() > 1) {
            c2 = string.charAt(1);
        }
        block0: for (int i = 0; i < dtAll.length; ++i) {
            x4dict024dt x4dict024dt2 = dtAll[i];
            if (x4dict024dt2.chr != c) continue;
            for (int j = 0; j < x4dict024dt2.arr.length; ++j) {
                x4dict024dt x4dict024dt3 = x4dict024dt2.arr[j];
                if (x4dict024dt3.chr != c2) continue;
                string2 = string2 + "" + x4dict024dt3.shortExpansion;
                break block0;
            }
            break;
        }
        return string2;
    }

    public static void printAllDt() {
        System.out.println("---printAllDt: L=" + dtAll.length);
        for (int i = 0; i < dtAll.length; ++i) {
            x4dict024dt x4dict024dt2 = dtAll[i];
            System.out.print(" " + x4dict024dt2.chr);
            System.out.print(": " + x4dict024dt2.expansion);
            System.out.println("");
            for (int j = 0; j < x4dict024dt2.arr.length; ++j) {
                x4dict024dt x4dict024dt3 = x4dict024dt2.arr[j];
                System.out.print("    " + x4dict024dt3.chr);
                System.out.print(":[" + x4dict024dt3.shortExpansion + "]");
                System.out.print(": " + x4dict024dt3.expansion);
                System.out.println("");
            }
        }
    }
}
