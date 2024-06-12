package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * Class to encapsulate EXFOR Reaction-string and operations
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2024-06-09
 * @since           2012-12-06
 *
 * Program:         x4reacstr.java
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
 *
 */

public class x4reacstr {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static boolean tryAttemptToUseOtherVars = false;
    String reacstr = "";
    String Target = "";
    String Quant = "";
    String SF1 = "";
    String SF2 = "";
    String SF3 = "";
    String SF4 = "";
    String SF5 = "";
    String SF6 = "";
    String SF7 = "";
    String SF8 = "";
    String SF9 = "";
    x4isot xTarg = new x4isot("");
    x4isot xProj = new x4isot("");
    x4isot xEjec = new x4isot("");
    x4isot xProd = new x4isot("");
    Double Qvalue = null;
    String Reac = "";
    String SF58 = "";
    String SF58orig = "";
    boolean ok = false;
    int zTarg = 0;
    static Vector genmods = null;
    x4dict236 x4d236 = null;
    x4dict213 x4d213 = null;
    x4dict x4d030 = null;
    x4dict x4d035 = null;
    String sf3hlp = "";
    String sf9hlp = "";
    boolean flagIncEnerIndepVar = true;
    String ReactionType = "";
    String ReactionQuant = "";
    String IndepVarFamilyCode = "";
    char[] chrIndepVarFamilyCode = new char[]{'?'};
    boolean flagElemMass = false;
    boolean flagSpecialQuantity = false;
    String UnitFamilyCode = "?";
    x4dict025 x4d025 = null;
    String BasicUnits = "?";
    Vector vGenmodifiers = new Vector();
    boolean resonansEnergyGinenInData = false;
    int nBrackets = 0;
    boolean seqBrackets = false;
    boolean okBrackets = true;
    String txtError = "";
    String srcError = "";
    int iposSrcError = -1;
    x4subent subent = null;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" x4reacstr");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2021\n");
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4reacstr x4reacstr2 = new x4reacstr("9-F-19(N,TOT)");
        x4reacstr2 = new x4reacstr("9-F-19(N,2N)9-F-18");
        x4reacstr2 = new x4reacstr("9-F-19(N,2N)9-F-18,,SIG");
        x4reacstr2 = new x4reacstr("3-LI-6(D,P)3-LI-7,PAR,DA");
        x4reacstr2 = new x4reacstr("9-F-19(N,INL)9-F-19,PAR,SIG,G");
        x4reacstr2 = new x4reacstr("3-LI-6(D,P)3-LI-7,PAR,DA,,,EXP");
        x4reacstr2 = new x4reacstr("3-LI-6(3-LI-6,2A)2-HE-4,,DA/DA/DE,A/A/A");
        x4reacstr2 = new x4reacstr("63-EU-151(N,EL),,WID/RED,,G");
        x4reacstr2 = new x4reacstr("63-EU-151(N,EL),,WID/RED,,G/FCT/SPA");
        x4reacstr2 = new x4reacstr("63-EU-151(N,EL),,WID/RED,,SPA");
        x4reacstr2 = new x4reacstr("63-EU-151(N,EL),,WID/RED,,FCT/SPA");
        x4reacstr2 = new x4reacstr("63-EU-151(N,EL),,WID/RED,,FCT/SPA/G");
        x4reacstr2 = new x4reacstr("9-F-19(N,INL)9-F-19,PAR,SIG,G,,EXP");
        x4reacstr2 = new x4reacstr("60-ND-148(N,G)60-ND-149,,RI");
        x4reacstr2 = new x4reacstr("60-ND-148(N,G)60-ND-149,,SIG");
        x4reacstr2 = new x4reacstr("92-U-0(0,F),,NU,,FCT");
        x4reacstr2 = new x4reacstr("92-U-238(0,F)ELEM/MASS,CUM,FY");
        x4reacstr2 = new x4reacstr("92-U-238(0,F)56-BA-140,CUM,FY");
        x4reacstr2 = new x4reacstr("39-Y-89(6-C-12,X)43-TC-93,(CUM),SIG");
        x4reacstr2 = new x4reacstr("94-PU-239(N,ABS),,ETA");
        x4reacstr2 = new x4reacstr("94-PU-239(0,F),PR,NU");
        x4reacstr2 = new x4reacstr("94-PU-239(N,F),PR,NU,,MXW");
        x4reacstr2 = new x4reacstr("14-SI-28(A,G)16-S-32,PAR,DA/DA,G/G,REL");
        x4reacstr2 = new x4reacstr("40-ZR-90(N,2N)40-ZR-89-M1/G,,SIG/RAT");
        x4reacstr2 = new x4reacstr("4-BE-9(HE3,P)5-B-11,PAR,DA");
        x4reacstr2 = new x4reacstr("3-LI-6(G,P+D)1-H-3,,DA,T,BRS");
        x4reacstr2 = new x4reacstr("3-LI-6(G,N+2P)1-H-3,,DA,T,BRS");
        x4reacstr2 = new x4reacstr("3-LI-6(A,T+HE3)2-HE-4,,DA/DA,A/T");
        x4reacstr2 = new x4reacstr("2-HE-3(HE3,G+2P)2-HE-4,,DA,,,EXP");
        x4reacstr2 = new x4reacstr("6-C-12(6-C-10,2P+2A)6-C-12,PAR,DA,P+P,REL");
        x4reacstr2 = new x4reacstr("6-C-14(6-C-13,A+4-BE-9)6-C-14,PAR,DA,,REL");
        x4reacstr2 = new x4reacstr("7-N-14(P,P+D+3-LI-6)3-LI-6,,SIG");
        x4reacstr2 = new x4reacstr("96-CM-248(8-O-18,N+8-O-16)96-CM-249,PAR,SIG,G,MSC");
        x4reacstr2 = new x4reacstr("82-PB-208(2-HE-8,2N+2-HE-6)82-PB-208,PAR,SIG");
        x4reacstr2 = new x4reacstr("6-C-12(4-BE-14,2-HE-6+2-HE-8)6-C-12,,SIG");
        x4reacstr2 = new x4reacstr("7-N-14(A,N+P+A+3-LI-6)3-LI-6,,SIG");
        x4reacstr2 = new x4reacstr("20-CA-44(8-O-18,2N+P+2A)23-V-51,,SIG");
        x4reacstr2 = new x4reacstr("1-H-1(N,PIN+PIP+N)1-H-1,,SIG,,MSC");
        x4reacstr2 = new x4reacstr("6-C-12(A,EL)6-C-12,,DA,,,EXP");
        x4reacstr2 = new x4reacstr("1-H-1(A,P)2-HE-4,,DA,,,DERIV ");
        x4reacstr2 = new x4reacstr("13-AL-27(N,A)11-NA-24-G,,SIG,,FIS");
        x4reacstr2 = new x4reacstr("");
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4reacstr() {
    }

    x4reacstr(String string) {
        this.setReacstr(string, false);
    }

    x4reacstr(String string, boolean bl) {
        this.setReacstr(string, bl);
    }

    public void setReacstr(String string, boolean bl) {
        this.reacstr = string;
        this.srcError = string;
        if (extDebug) {
            sysOut.println("\n>>>reacstr:[" + string + "]...");
        }
        this.seqBrackets = this.get1nBrackets(string);
        this.ok = this.set1sfs(string);
        this.okBrackets = true;
        if (this.nBrackets != 0 || !this.seqBrackets) {
            this.okBrackets = false;
        }
        this.SF58 = this.removeGenModifiers();
        this.xTarg = new x4isot(this.SF1);
        this.xProj = new x4isot(this.SF2);
        this.xProd = new x4isot(this.SF4);
        this.xEjec = this.SF3.equals("EL") ? new x4isot(this.SF2) : (this.SF3.equals("INL") ? new x4isot(this.SF2) : new x4isot(this.SF3));
        this.Qvalue = this.QCalc(this.SF1, this.SF2, this.xEjec.originalStr, this.SF4);
        this.x4d030 = x4dict.findinx4dict("PROCESS", this.SF3);
        if (this.x4d030 != null) {
            this.sf3hlp = this.x4d030.shortHelp;
        }
        this.x4d035 = x4dict.findinx4dict("SF9", this.SF9);
        if (this.x4d035 != null) {
            this.sf9hlp = this.x4d035.shortHelp;
        }
        this.flagIncEnerIndepVar = this.isProjectile();
        this.x4d236 = x4dict236.findinx4dict(this.SF58);
        if (this.x4d236 != null) {
            this.ReactionType = x4dict236.getReactionType(this.SF58);
            this.ReactionQuant = this.x4d236.shortHelp;
            this.IndepVarFamilyCode = x4dict213.getIndepVarFamilyCode(this.ReactionType);
            this.setIndepVarFamilyCode(bl);
            this.x4d213 = x4dict213.findinx4dict(this.ReactionType);
            if (this.x4d213 != null) {
                this.Quant = this.x4d213.WebQuantity;
            }
            this.UnitFamilyCode = this.x4d236.UnitFamilyCode;
            this.x4d025 = x4dict025.findByUnitFamilyCode(this.UnitFamilyCode);
            if (this.x4d025 != null) {
                this.BasicUnits = this.x4d025.Units;
            }
            if (this.UnitFamilyCode.equals("NO")) {
                this.BasicUnits = "NO-DIM";
            }
            if (this.SF58orig.indexOf("REL") >= 0) {
                this.BasicUnits = "ARB-UNITS";
            }
        }
        if (extDebug && this.x4d236 == null) {
            sysOut.println("___1___x4d236==null>>> SF1=" + this.SF1 + "\tSF2=" + this.SF2 + "\tSF3=" + this.SF3 + "\tSF58=[" + this.SF58 + "]");
        }
        if (extDebug && this.x4d236 != null) {
            sysOut.println(">>> SF1=" + this.SF1 + "\tSF2=" + this.SF2 + "\tSF3=" + this.SF3 + "\tSF4=" + this.SF4 + "\n>>> SF5=" + this.SF5 + "\tSF6=" + this.SF6 + "\tSF7=" + this.SF7 + "\tSF8=" + this.SF8 + "\tSF9=" + this.SF9 + "\t...Reac=[" + this.Reac + "]" + "\n>>> SF58orig=" + this.SF58orig + "\tSF58=" + this.SF58 + "\n>>> Expansion.236=[" + x4dict236.getExpansion(this.SF58) + "]" + "\n>>> ReactionType=" + this.ReactionType + "\tIndepVarFamilyCode=[" + new String(this.chrIndepVarFamilyCode) + "]" + "\n>>> Expansion.213=[" + x4dict213.getExpansion(this.ReactionType) + "]" + "\n>>> UnitFamilyCode=[" + this.UnitFamilyCode + "]" + "\n>>> BasicUnits=[" + this.BasicUnits + "]");
        }
        if (extDebug) {
            sysOut.println(">>>ok=" + this.ok);
        }
        if (extDebug) {
            sysOut.println(string + " Qvalue=" + this.Qvalue);
        }
    }

    public Double QCalc(String string, String string2, String string3, String string4) {
        Double d = null;
        double d2 = 0.0;
        double d3 = 0.0;
        boolean bl = false;
        if (string4.equals("")) {
            return null;
        }
        if (string4.indexOf("/") >= 0) {
            return null;
        }
        if (string4.indexOf("+") >= 0) {
            return null;
        }
        if (string4.indexOf("ELEM") >= 0) {
            return null;
        }
        if (string4.indexOf("MASS") >= 0) {
            return null;
        }
        if (string3.indexOf("PIP") >= 0) {
            return null;
        }
        if (string3.indexOf("PIN") >= 0) {
            return null;
        }
        if (string3.indexOf("PI0") >= 0) {
            return null;
        }
        if (string.equals(string4) && string2.equals(string3)) {
            return new Double(0.0);
        }
        x4isot x4isot2 = new x4isot(string);
        x4isot x4isot3 = new x4isot(string2);
        x4isot x4isot4 = new x4isot(string3);
        x4isot x4isot5 = new x4isot(string4);
        if (x4isot2.newStr.equals(x4isot4.newStr) && x4isot3.newStr.equals(x4isot5.newStr)) {
            return new Double(0.0);
        }
        if (x4isot2.MASS_mev <= 0.0) {
            return null;
        }
        if (x4isot3.MASS_mev < 0.0) {
            return null;
        }
        if (x4isot5.MASS_mev < 0.0 && string4.endsWith("-G")) {
            x4isot5 = new x4isot(string4.substring(0, string4.length() - 2));
        }
        if (x4isot5.MASS_mev < 0.0) {
            return null;
        }
        d2 = x4isot2.MASS_mev + x4isot3.MASS_mev - x4isot5.MASS_mev;
        if (bl) {
            System.out.println("\ttar:MASS_mev=" + x4isot2.MASS_mev + " " + x4isot2.originalStr + " " + x4isot2.Name);
            System.out.println("\tinc:MASS_mev=" + x4isot3.MASS_mev + " " + x4isot3.originalStr + " " + x4isot3.Name);
        }
        String[] stringArray = string3.split("\\+");
        boolean bl2 = true;
        d3 = 0.0;
        for (int i = 0; i < stringArray.length; ++i) {
            String string5 = stringArray[i].trim();
            if (string5.equals("")) continue;
            String string6 = x4reacstr.getNumPart(string5);
            String string7 = string5.substring(string6.length());
            x4isot x4isot6 = new x4isot(string7);
            int n = 1;
            if (!string6.equals("")) {
                try {
                    n = Integer.parseInt(string6);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n > 0 && x4isot6.MASS_mev >= 0.0) {
                d3 += (double)n * x4isot6.MASS_mev;
                if (!bl) continue;
                System.out.println("\t\tout:" + i + ") [" + stringArray[i] + "] -->" + n + " * " + x4isot6.MASS_mev + " " + x4isot6.originalStr);
                continue;
            }
            bl2 = false;
            break;
        }
        if (bl2) {
            if (bl) {
                System.out.println("\tout:MASS_mev=" + d3 + " " + string3);
                System.out.println("\tprd:MASS_mev=" + x4isot5.MASS_mev + " " + x4isot5.originalStr);
            }
            d = new Double(d2 -= d3);
        }
        return d;
    }

    public static String getNumPart(String string) {
        int n = string.length();
        String string2 = "";
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (Character.isDigit(c)) {
                string2 = string2 + c;
                continue;
            }
            if (c == '-') {
                return "";
            }
            if (Character.isLetter(c)) break;
        }
        return string2;
    }

    private void setIndepVarFamilyCode(boolean bl) {
        int n = 11;
        this.chrIndepVarFamilyCode = new char[n];
        String string = x4reacstr.strpad(this.IndepVarFamilyCode, 11);
        if (string.startsWith("*")) {
            this.flagSpecialQuantity = true;
        }
        if (this.SF58.equals(",EN")) {
            this.resonansEnergyGinenInData = true;
        }
        string.getChars(0, n, this.chrIndepVarFamilyCode, 0);
        if (this.flagIncEnerIndepVar && this.chrIndepVarFamilyCode[1] != '1') {
            this.chrIndepVarFamilyCode[2] = 50;
            if (this.x4d236 != null && this.x4d236.resParamFlag) {
                this.chrIndepVarFamilyCode[2] = 32;
            }
        }
        if (bl && this.chrIndepVarFamilyCode[1] == '1' && this.chrIndepVarFamilyCode[2] == ' ') {
            this.chrIndepVarFamilyCode[1] = 32;
            this.chrIndepVarFamilyCode[2] = 50;
        }
        this.chrIndepVarFamilyCode[0] = 48;
        this.flagElemMass = false;
        if (this.SF4.startsWith("ELEM")) {
            this.flagElemMass = true;
        }
        if (this.SF4.startsWith("MASS")) {
            this.flagElemMass = true;
        }
        if (this.flagElemMass) {
            this.chrIndepVarFamilyCode[7] = 55;
        }
        if (tryAttemptToUseOtherVars) {
            this.chrIndepVarFamilyCode[6] = 54;
        }
    }

    public boolean isProjectile() {
        return !this.SF2.equals("0");
    }

    public void setTarget() {
        this.Target = this.SF1;
        int n = this.Target.indexOf("-");
        if (n > 0) {
            String string = this.SF1.substring(0, n);
            this.zTarg = this.str2int(string, 0);
            this.Target = this.Target.substring(n + 1);
        }
    }

    public boolean set1sfs(String string) {
        boolean bl = true;
        int n = string.length();
        String string2 = string;
        int n2 = string2.indexOf("(");
        int n3 = string2.indexOf(")");
        if (n2 < 0) {
            this.txtError = "No \"(\" in reaction string";
            return false;
        }
        if (n3 < 0) {
            this.txtError = "No \")\" in reaction string";
            return false;
        }
        if (n2 >= n3) {
            this.txtError = "Wrong sequence \"(...)\" in reaction string";
            this.iposSrcError = n2;
            return false;
        }
        if (n2 > 0) {
            this.SF1 = string2.substring(0, n2);
        }
        this.setTarget();
        String string3 = this.Reac = string2.substring(n2 + 1, n3);
        int n4 = string3.indexOf(",");
        if (n4 >= 0) {
            if (n4 > 0) {
                this.SF2 = string3.substring(0, n4);
            }
            this.SF3 = string3.substring(n4 + 1);
        } else {
            bl = false;
            this.SF2 = string3;
        }
        n4 = n3;
        if (++n4 >= n) {
            return false;
        }
        int n5 = string2.indexOf(",", n4);
        if (n5 < 0) {
            this.SF4 = string2.substring(n4);
            return bl;
        }
        this.SF4 = string2.substring(n4, n5);
        this.SF58orig = string2.substring(n5 + 1);
        n4 = n5 + 1;
        if (n4 >= n) {
            return bl;
        }
        n5 = string2.indexOf(",", n4);
        if (n5 < 0) {
            this.SF5 = string2.substring(n4);
            return bl;
        }
        this.SF5 = string2.substring(n4, n5);
        n4 = n5 + 1;
        if (n4 >= n) {
            return bl;
        }
        n5 = string2.indexOf(",", n4);
        if (extDebug) {
            sysOut.println(" ii=" + n4 + " ii2=" + n5 + " ll=" + n);
        }
        if (n5 < 0) {
            this.SF6 = string2.substring(n4);
            if (extDebug) {
                sysOut.println(" SF6=" + this.SF6 + " ii=" + n4 + " ii2=" + n5);
            }
            return bl;
        }
        this.SF6 = string2.substring(n4, n5);
        n4 = n5 + 1;
        if (n4 >= n) {
            return bl;
        }
        n5 = string2.indexOf(",", n4);
        if (n5 < 0) {
            this.SF7 = string2.substring(n4);
            return bl;
        }
        this.SF7 = string2.substring(n4, n5);
        n4 = n5 + 1;
        if (n4 >= n) {
            return bl;
        }
        n5 = string2.indexOf(",", n4);
        if (n5 < 0) {
            this.SF8 = string2.substring(n4);
            return bl;
        }
        this.SF8 = string2.substring(n4, n5);
        n4 = n5 + 1;
        if (n4 >= n) {
            return bl;
        }
        n5 = string2.indexOf(",", n4);
        if (n5 < 0) {
            this.SF9 = string2.substring(n4);
            return bl;
        }
        this.SF9 = string2.substring(n4, n5);
        n4 = n5 + 1;
        if (extDebug) {
            sysOut.println("...set1sfs: ii=" + n4 + " ll=" + n);
        }
        return bl;
    }

    public boolean get1nBrackets(String string) {
        boolean bl = true;
        this.nBrackets = 0;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '(') {
                ++this.nBrackets;
                continue;
            }
            if (c != ')') continue;
            --this.nBrackets;
            if (this.nBrackets >= 0) continue;
            if (this.txtError.equals("")) {
                this.txtError = "Brackets sequence";
                this.iposSrcError = i;
            }
            bl = false;
        }
        if (this.txtError.equals("")) {
            this.txtError = "Brackets are not in balance";
        }
        return bl;
    }

    public boolean isBracket(char c) {
        return c == '(' || c == ')';
    }

    public String removeGenModifiers() {
        boolean bl;
        int n;
        String string = "";
        String string2 = this.SF8;
        this.vGenmodifiers = new Vector();
        if (genmods == null) {
            genmods = x4dict034.getGenMods();
            if (extDebug) {
                for (n = 0; n < genmods.size(); ++n) {
                    string = (String)genmods.elementAt(n);
                    sysOut.println("genmod-" + (n + 1) + ": [" + string + "]");
                }
            }
        }
        this.SF8 = new String("/" + this.SF8 + "/");
        if (extDebug) {
            System.out.print("---[" + this.SF8 + "]---\n");
        }
        do {
            bl = false;
            for (int i = 0; i < genmods.size(); ++i) {
                String string3 = (String)genmods.elementAt(i);
                int n2 = this.SF8.indexOf("/" + string3 + "/");
                if (n2 < 0) continue;
                int n3 = n2 + string3.length() + 1;
                string = n2 > 0 ? this.SF8.substring(0, n2) : "";
                string = string + this.SF8.substring(n3);
                bl = true;
                n = 0;
                boolean bl2 = false;
                while (i < this.vGenmodifiers.size()) {
                    String string4 = (String)this.vGenmodifiers.elementAt(i);
                    if (string4.equals(string3)) {
                        bl2 = true;
                        break;
                    }
                    ++i;
                }
                if (!bl2) {
                    this.vGenmodifiers.addElement(string3);
                }
                this.SF8 = string;
            }
        } while (bl);
        if (this.SF8.startsWith("/")) {
            this.SF8 = this.SF8.substring(1);
        }
        if (this.SF8.endsWith("/")) {
            this.SF8 = this.SF8.substring(0, this.SF8.length() - 1);
        }
        if (extDebug) {
            System.out.print("===[" + this.SF8 + "]===\n");
        }
        String string5 = this.SF5 + "," + this.SF6 + "," + this.SF7 + "," + this.SF8;
        while (string5.endsWith(",")) {
            string5 = string5.substring(0, string5.length() - 1);
        }
        if (extDebug) {
            System.out.print("   SF58:[" + this.SF58 + "]==>[" + string5 + "] SF8new=[" + this.SF8 + "]\n");
        }
        string = string5;
        this.SF8 = string2;
        return string;
    }

    public String getStrSFs() {
        String string = "";
        if (!this.SF1.equals("")) {
            string = string + " SF1=" + this.SF1;
        }
        if (!this.SF2.equals("")) {
            string = string + " SF2=" + this.SF2;
        }
        if (!this.SF3.equals("")) {
            string = string + " SF3=" + this.SF3;
        }
        if (!this.SF4.equals("")) {
            string = string + " SF4=" + this.SF4;
        }
        if (!this.SF5.equals("")) {
            string = string + " SF5=" + this.SF5;
        }
        if (!this.SF6.equals("")) {
            string = string + " SF6=" + this.SF6;
        }
        if (!this.SF7.equals("")) {
            string = string + " SF7=" + this.SF7;
        }
        if (!this.SF8.equals("")) {
            string = string + " SF8=" + this.SF8;
        }
        if (!this.SF9.equals("")) {
            string = string + " SF9=" + this.SF9;
        }
        return string;
    }

    public String getSF58Quant() {
        String string = "";
        if (!this.SF58.equals("")) {
            string = string + " SF58=" + this.SF58;
        }
        if (!this.SF58orig.equals(this.SF58)) {
            string = string + " SF58+=" + this.SF58orig;
        }
        if (this.x4d236 != null) {
            string = string + " ReactionType:" + this.x4d236.ReactionType;
            string = string + " UnitFamilyCode:" + this.x4d236.UnitFamilyCode;
            string = string + "\n\t\t>>> x4dict025.findByUnitFamilyCode:[" + x4dict025.findByUnitFamilyCode(this.UnitFamilyCode) + "]";
        }
        return string;
    }

    public String getSF58QuantWithExpansion() {
        String string = "";
        if (!this.SF58.equals("")) {
            string = string + " SF58=[" + this.SF58 + "]";
        }
        if (!this.SF58orig.equals(this.SF58)) {
            string = string + " SF58+=[" + this.SF58orig + "]";
        }
        if (this.x4d236 != null) {
            string = string + " ReacType=[" + this.x4d236.ReactionType + "]";
            string = string + " UnitFamily=[" + this.x4d236.UnitFamilyCode + "]";
            string = string + " BasicUnits=[" + this.BasicUnits + "]";
            string = string + " VarFamilyCode=[" + new String(this.chrIndepVarFamilyCode).trim() + "]";
            string = string + " Exp.213=[" + x4dict213.getExpansion(this.ReactionType) + "]";
        }
        return string;
    }

    public void println() {
    }

    public static String strpad(String string, int n) {
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

    public int str2int(String string, int n) {
        int n2 = n;
        try {
            n2 = Integer.parseInt(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }
}
