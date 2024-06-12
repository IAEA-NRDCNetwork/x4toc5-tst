package zvv.x4;

import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR computational variable object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-07-14
 * @since           2011-10-27
 *
 * <pre>
 * Program:         x4cvar.java
 * Author:          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * Created:         2011-10-27
 * Last modified:   2023-07-14
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

public class x4cvar
implements Cloneable {
    static boolean extDebug = false;
    Vector vVars = new Vector();
    x4var xvar0 = null;
    x4reaction xreac = null;
    Vector vCompNotes = new Vector();
    Hashtable hCompNotes = new Hashtable();
    String convDataType = null;
    String convUnits = null;
    Float rValue = null;
    Float rMin = null;
    Float rMax = null;
    Float rAprx = null;
    x4var xvarMin = null;
    x4var xvarMax = null;
    x4var xvarAprx = null;
    String compValueSetFrom = null;
    char compValueSetFromCh = (char)49;
    Float rMinReaction = null;
    Float rMaxReaction = null;
    Float errTotal_plus = null;
    Float errTotal_minus = null;
    Float errResol_plus = null;
    Float errResol_minus = null;
    Float errStat_plus = null;
    Float errStat_minus = null;
    Float errSys_plus = null;
    Float errSys_minus = null;
    Float errPartialSys_plus = null;
    Float errPartialSys_minus = null;
    Float errPartialStat_plus = null;
    Float errPartialStat_minus = null;
    float errPartialSysAccu2_plus = 0.0f;
    float errPartialSysAccu2_minus = 0.0f;
    float errPartialStatAccu2_plus = 0.0f;
    float errPartialStatAccu2_minus = 0.0f;
    double mult_DAM = -1.0;
    double div_ABU = -1.0;
    Float compErrorSys = null;
    Float compErrorStat = null;
    Float compErrorMrc = null;
    Float errMrc_plus = null;
    Float errMrc_minus = null;
    Float errPartialMrc_plus = null;
    Float errPartialMrc_minus = null;
    float errPartialMrcAccu2_plus = 0.0f;
    float errPartialMrcAccu2_minus = 0.0f;
    Float compValue = null;
    Float compError = null;
    Float compErrorFromMinMaxVal = null;
    Float compErrorGiven = null;
    Float compValueMin = null;
    Float compValueMax = null;
    public int Variable_Number = -1;
    Float zNucl = null;
    Float aNucl = null;
    Float sNucl = null;
    int iElement = 0;
    int iMass = 0;
    int iIsomer = 0;
    char cmeta = (char)32;
    Float decayFlag = null;
    Float degAngle = null;
    Float minAngle = null;
    String cvarnam = "";
    String compVarName = "";
    String strFamFlag = "";
    String strFamFlag0 = "";
    String strFamFlag1 = "";
    String BasicUnits = "";
    String BasicUnits0 = "";
    String BasicUnits1 = "";
    boolean flag_cm0 = false;
    boolean flag_cm1 = false;
    boolean convert_Einc2Lab = true;
    boolean convert_Einc2Lab_done = false;
    Float[] cc4dat = null;
    Float[] cc4err = null;
    String[] cc4prod = null;
    x4cvar[] me4dat = null;
    x4cvar[] me4dat1 = null;
    int iRow = 0;
    static final String[] nucl = new String[]{"NN", "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "*", "Fl", "*", "Lv", "*", "*"};

    public x4cvar clone() {
        x4cvar x4cvar2 = null;
        try {
            x4cvar2 = (x4cvar)super.clone();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return x4cvar2;
    }

    public x4cvar(int n) {
        this.Variable_Number = n;
    }

    public void add_x4var(x4var x4var2) {
        if (this.vVars.size() == 0) {
            this.xvar0 = x4var2;
        }
        this.vVars.addElement(x4var2);
    }

    public void set_x4reaction(x4reaction x4reaction2) {
        this.xreac = x4reaction2;
    }

    public int putToArray(int n, Float f) {
        if (this.cc4dat == null) {
            return -1;
        }
        if (f == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.cc4dat.length) {
            return -1;
        }
        this.cc4dat[n] = new Float(f.floatValue());
        return 0;
    }

    public int putToArrayErr(int n, Float f) {
        if (this.cc4err == null) {
            return -1;
        }
        if (f == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.cc4err.length) {
            return -1;
        }
        this.cc4err[n] = new Float(f.floatValue());
        return 0;
    }

    public int putToArrayProd(int n, String string) {
        if (this.cc4prod == null) {
            return -1;
        }
        if (string == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.cc4prod.length) {
            return -1;
        }
        this.cc4prod[n] = new String(string);
        return 0;
    }

    public Float getFromArray(int n) {
        if (this.cc4dat == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.cc4dat.length) {
            return null;
        }
        return this.cc4dat[n];
    }

    public Float getFromArrayErr(int n) {
        if (this.cc4err == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.cc4err.length) {
            return null;
        }
        return this.cc4err[n];
    }

    public String getFromArrayProd(int n) {
        if (this.cc4prod == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.cc4prod.length) {
            return null;
        }
        return this.cc4prod[n];
    }

    public int allocArray(int n) {
        int n2;
        try {
            this.cc4dat = new Float[n];
            this.cc4err = new Float[n];
            this.cc4prod = new String[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            return -1;
        }
        for (n2 = 0; n2 < n; ++n2) {
            this.cc4dat[n2] = null;
        }
        for (n2 = 0; n2 < n; ++n2) {
            this.cc4err[n2] = null;
        }
        for (n2 = 0; n2 < n; ++n2) {
            this.cc4prod[n2] = null;
        }
        return 0;
    }

    public int putToArray0(int n, x4cvar x4cvar2) {
        if (this.me4dat == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.me4dat.length) {
            return -1;
        }
        if (x4cvar2 == null) {
            this.me4dat[n] = null;
            return -1;
        }
        this.me4dat[n] = x4cvar2.clone();
        return 0;
    }

    public x4cvar getFromArray0(int n) {
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
            this.me4dat = new x4cvar[n];
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

    public int putToArray1(int n, x4cvar x4cvar2) {
        if (this.me4dat1 == null) {
            return -1;
        }
        if (n < 0) {
            return -1;
        }
        if (n >= this.me4dat1.length) {
            return -1;
        }
        if (x4cvar2 == null) {
            this.me4dat1[n] = null;
            return -1;
        }
        this.me4dat1[n] = x4cvar2.clone();
        return 0;
    }

    public x4cvar getFromArray1(int n) {
        if (this.me4dat1 == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if (n >= this.me4dat1.length) {
            return null;
        }
        return this.me4dat1[n];
    }

    public int sameFromArray1() {
        if (this.me4dat1 == null) {
            return 0;
        }
        if (this.me4dat1.length <= 0) {
            return 0;
        }
        if (this.me4dat1.length <= 2) {
            return 0;
        }
        Float f = null;
        x4cvar x4cvar2 = this.me4dat1[0];
        if (x4cvar2 != null) {
            f = this.me4dat1[0].compValue;
        }
        for (int i = 1; i < this.me4dat1.length; ++i) {
            Float f2 = null;
            x4cvar x4cvar3 = this.me4dat1[i];
            if (x4cvar3 != null) {
                f2 = this.me4dat1[i].compValue;
            }
            if (f == null) {
                if (f2 == null) continue;
                return 0;
            }
            if (f2 == null) {
                return 0;
            }
            if (f2.equals(f)) continue;
            return 0;
        }
        return 1;
    }

    public int allocArray1(int n) {
        try {
            this.me4dat1 = new x4cvar[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            return -1;
        }
        for (int i = 0; i < n; ++i) {
            this.me4dat1[i] = null;
        }
        return 0;
    }

    public void cleanCompValue() {
        this.rValue = null;
        this.rMin = null;
        this.rMax = null;
        this.rAprx = null;
        this.xvarMin = null;
        this.xvarMax = null;
        this.xvarAprx = null;
        this.compValueSetFrom = null;
        this.compValueSetFromCh = (char)49;
        this.errTotal_plus = null;
        this.errTotal_minus = null;
        this.errResol_plus = null;
        this.errResol_minus = null;
        this.errStat_plus = null;
        this.errStat_minus = null;
        this.errSys_plus = null;
        this.errSys_minus = null;
        this.errPartialSys_plus = null;
        this.errPartialSys_minus = null;
        this.errPartialSysAccu2_plus = 0.0f;
        this.errPartialSysAccu2_minus = 0.0f;
        this.errPartialStat_plus = null;
        this.errPartialStat_minus = null;
        this.errPartialStatAccu2_plus = 0.0f;
        this.errPartialStatAccu2_minus = 0.0f;
        this.iElement = 0;
        this.iMass = 0;
        this.iIsomer = 0;
        this.zNucl = null;
        this.aNucl = null;
        this.sNucl = null;
        this.cmeta = (char)32;
        this.decayFlag = null;
        this.degAngle = null;
        this.minAngle = null;
        this.compErrorSys = null;
        this.compErrorStat = null;
        this.compValue = null;
        this.compError = null;
        this.compErrorFromMinMaxVal = null;
        this.compErrorGiven = null;
        this.compValueMin = null;
        this.compValueMax = null;
        this.compErrorMrc = null;
        this.errMrc_plus = null;
        this.errMrc_minus = null;
        this.errPartialMrc_plus = null;
        this.errPartialMrc_minus = null;
        this.errPartialMrcAccu2_plus = 0.0f;
        this.errPartialMrcAccu2_minus = 0.0f;
    }

    public boolean prepareCompHeader() {
        Object var3_1 = null;
        float f = 0.0f;
        boolean bl = true;
        for (int i = 0; i < this.vVars.size(); ++i) {
            x4var x4var2 = (x4var)this.vVars.elementAt(i);
            if (x4var2.Variable_Value == 0) continue;
            if (x4var2.numDataType == 42) {
                this.convDataType = "61";
                this.convUnits = "ADEG";
            }
            if (x4var2.numDataType == 43) {
                this.convDataType = "61";
                this.convUnits = "ADEG";
            }
            if (x4var2.numDataType == 1 && this.xreac != null && this.xreac.xreac1 != null && this.xreac.xreac1.SF8 != null && this.xreac.ireac == 1 && this.xreac.xreac1.SF8.equals("DAM") && this.xreac.xTarg1 != null && this.xreac.xTarg1.AtomicWeight > 0.0) {
                this.mult_DAM = this.xreac.xTarg1.AtomicWeight;
            }
            if (x4var2.numDataType == 1 && this.xreac != null && this.xreac.xreac1 != null && this.xreac.xreac1.SF8 != null && this.xreac.ireac == 1) {
                boolean bl2 = false;
                if (this.xreac.xreac1.SF8.equals("A")) {
                    bl2 = true;
                }
                if (this.xreac.xreac1.SF8.endsWith("/A")) {
                    bl2 = true;
                }
                if (bl2 && this.xreac.xTarg1 != null && this.xreac.xTarg1.Abundance > 0.0f) {
                    this.div_ABU = this.xreac.xTarg1.Abundance;
                }
            }
            if (!x4var2.DataType.equals("45") && !x4var2.Units.equals("ANGSTROM")) continue;
            this.convDataType = "41";
            this.convUnits = "EV";
        }
        return bl;
    }

    public void str2CompNotes(String string) {
        String string2 = (String)this.hCompNotes.get(string);
        if (string2 == null) {
            this.hCompNotes.put(string, string);
            this.vCompNotes.addElement(string);
        }
    }

    public boolean prepareCompValue(int n) {
        x4var x4var2;
        int n2;
        Float f = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int n3 = 0;
        boolean bl = true;
        this.compValue = null;
        this.compError = null;
        this.compErrorGiven = null;
        this.iRow = n;
        this.cleanCompValue();
        if (this.xreac != null) {
            for (n2 = 0; n2 < this.xreac.vAllVars.size(); ++n2) {
                x4var2 = (x4var)this.xreac.vAllVars.elementAt(n2);
                if (!x4var2.Header.equals("DECAY-FLAG")) continue;
                this.decayFlag = x4var2.nowValue;
            }
        }
        for (n2 = 0; n2 < this.vVars.size(); ++n2) {
            double d;
            double d2;
            double d3;
            x4var2 = (x4var)this.vVars.elementAt(n2);
            if (x4var2.Variable_Value == 0) continue;
            String string = "null";
            n3 = 0;
            if (x4var2.x4d025 != null) {
                string = " Mul:" + x4var2.x4d025.ConversionFactor + " Adder:" + x4var2.x4d025.Adder + " Op:" + x4var2.x4d025.Operation;
                n3 = x4var2.x4d025.Operation;
                f3 = x4var2.x4d025.Adder;
            }
            x4var2.c4dataValue = null;
            x4var2.c4dataValueHeader = null;
            x4var2.c4dataValueUnits = null;
            f = x4var2.nowValue;
            if (f == null) continue;
            f2 = f.floatValue();
            if (x4var2.numDataType == 1 && this.mult_DAM > 0.0) {
                f = Float.valueOf(f.floatValue() * (float)this.mult_DAM);
                f2 = f.floatValue();
                this.str2CompNotes("CONVERT SIG/ATOMIC-MASS TO SIG: MULT=" + (float)this.mult_DAM);
            }
            if (x4var2.numDataType == 1 && this.div_ABU > 0.0) {
                f = Float.valueOf(f.floatValue() / (float)this.div_ABU);
                f2 = f.floatValue();
                this.str2CompNotes("CONVERT SIG*ABUNDANCE(" + this.xreac.xTarg1.Name + ") TO SIG: MULT=1/" + x4c4obj.double2str((float)this.div_ABU) + "=" + (float)(1.0 / this.div_ABU));
            }
            if (x4var2.numDataType == 42) {
                if (f != null) {
                    d3 = f.doubleValue();
                    if (d3 >= -1.0 && d3 <= 1.0) {
                        d3 = Math.acos(d3) / Math.PI * 180.0;
                        f = new Float(d3);
                    } else if (x4var2.Header.equals("COS")) {
                        this.str2CompNotes("|COS|>1 ERROR: CORRECTED ASSUMING ANG");
                    }
                    this.degAngle = f;
                }
                this.convDataType = "61";
                this.convUnits = "ADEG";
                x4var2.c4dataValueHeader = "ANG";
                x4var2.c4dataValueUnits = "ADEG";
            }
            if (x4var2.numDataType == 41 && f != null) {
                if (x4var2.Units.equals("AMIN")) {
                    this.minAngle = f;
                } else {
                    this.degAngle = f;
                }
            }
            if (x4var2.numDataType == 62) {
                if (f != null) {
                    d3 = f.doubleValue();
                    if (f3 != 0.0f) {
                        f = new Float(d3 += (double)f3);
                        this.str2CompNotes("CONVERT " + x4var2.Header + ": " + x4var2.Units + " TO K  added:" + f3);
                    }
                }
                this.convUnits = "K";
            }
            if (x4var2.DataType.equals("45") || x4var2.Units.equals("ANGSTROM")) {
                float f4 = f2 * 1.0E10f;
                f2 = 0.0818f / (f4 * f4);
                f = new Float(f2);
                this.convDataType = "41";
                this.convUnits = "EV";
                this.str2CompNotes("CONVERT INC-ENERGY: ANGSTROM TO EV");
                if (this.xreac != null) {
                    this.xreac.convertUnits.put("ANGSTROM", "EV");
                }
                x4var2.c4dataValueHeader = "EN";
                x4var2.c4dataValueUnits = "EV";
            }
            if (x4var2.numDataType == 71) {
                this.iElement = (int)f2;
                this.zNucl = f;
            }
            if (x4var2.numDataType == 72) {
                this.iMass = (int)f2;
                this.aNucl = f;
            }
            if (x4var2.numDataType == 73) {
                this.iIsomer = (int)f2;
                this.sNucl = f;
                if (this.iIsomer == 0) {
                    this.cmeta = (char)71;
                } else if (this.iIsomer > 0) {
                    this.cmeta = (char)(77 + this.iIsomer - 1);
                }
            }
            if (n3 == 9 && this.xreac != null && this.xreac.xProj1 != null && this.xreac.xProj1.aa != 0) {
                f = new Float(f2 *= (float)this.xreac.xProj1.aa);
                this.str2CompNotes("CONVERT ENERGY/ATOMIC-MASS TO ENERGY: E1=E0*" + this.xreac.xProj1.aa);
            }
            if (x4var2.numDataType == 22 && this.xreac != null && this.xreac.xProj1 != null) {
                d3 = (double)f2 / 1000000.0;
                double d4 = this.xreac.xProj1.MASS_mev;
                double d5 = Math.sqrt(d3 * d3 + d4 * d4) - d4;
                f2 = (float)(d5 *= 1000000.0);
                f = new Float(f2);
            }
            boolean bl2 = false;
            if ((x4var2.numDataType1 == 1 || x4var2.numDataType1 == 2) && x4var2.Header.equals("E-EXC-CMP") && this.xreac != null && this.xreac.QValue1capture != null) {
                d2 = (double)f2 - (double)this.xreac.QValue1capture.floatValue() * 1000000.0;
                if (extDebug) {
                    System.out.println("...incEnergy:C.M.=" + x4dict024dt.getLabCMFlag(x4var2.PlottingFlags) + " fEnCM2Lab=" + (float)this.xreac.koeffEnCM2Lab + " nowValue=" + f2 + " EnLab=" + (float)d2);
                }
                f2 = (float)d2;
                f = new Float(f2);
                this.str2CompNotes("CONVERTED E-EXC-CMP TO INC-ENERGY C.M.: EN_CM=[E-EXC-CMP]-[Q.CAPT]; Q.CAPT=" + this.xreac.QValue1capture.floatValue() + "MEV");
                bl2 = true;
            }
            if (this.convert_Einc2Lab && (x4var2.numDataType1 == 1 || x4var2.numDataType1 == 2) && (x4dict024dt.getLabCMFlag(x4var2.PlottingFlags) || bl2) && this.xreac != null && this.xreac.convert_Einc2Lab) {
                d2 = (double)f2 * this.xreac.koeffEnCM2Lab;
                if (extDebug) {
                    System.out.println("...incEnergy:C.M.=" + x4dict024dt.getLabCMFlag(x4var2.PlottingFlags) + " fEnCM2Lab=" + (float)this.xreac.koeffEnCM2Lab + " nowValue=" + f2 + " EnLab=" + (float)d2);
                }
                f2 = (float)d2;
                f = new Float(f2);
                this.str2CompNotes("CONVERTED INC-ENERGY C.M. TO LAB: EN_LAB=EN_CM*" + (float)this.xreac.koeffEnCM2Lab);
                this.convert_Einc2Lab_done = true;
            }
            if (x4var2.numDataType == 43 && this.xreac != null && this.xreac.xProj1 != null && this.xreac.xcvarEnergy != null) {
                double d6;
                if (extDebug) {
                    System.out.println("...xreac.xcvarEnergy.compValue=" + this.xreac.xcvarEnergy.compValue);
                }
                d2 = 197.372;
                d = (double)this.xreac.xcvarEnergy.compValue.floatValue() / 1000000.0;
                double d7 = this.xreac.xProj1.MASS_mev;
                if (extDebug) {
                    System.out.println("...xreac.xcvarEnergy.compValue=" + this.xreac.xcvarEnergy.compValue + " xreac.xProj1.MASS_mev=" + d7 + " [" + this.xreac.xProj1.originalStr + "]");
                }
                if (d7 == 0.0) {
                    f = null;
                    continue;
                }
                double d8 = 1.0 / d2 * Math.sqrt(2.0 * d7 * d);
                double d9 = f2 / x4var2.ConversionFactor;
                if (x4var2.Units.endsWith("/C")) {
                    d6 = (double)f2 / 1000000.0;
                    d9 = d6 / d2;
                    if (extDebug) {
                        System.out.println("...1...xreac.xcvarEnergy.compValue=" + this.xreac.xcvarEnergy.compValue + " xreac.xProj1.MASS_mev=" + d7 + "\nkk=" + d8 + " mom=" + d6 + " qq=" + d9 + "\nrnowValue=" + f2 + " ConversionFactor=" + x4var2.ConversionFactor + " xvar.Units=" + x4var2.Units);
                    }
                }
                d6 = 2.0 * Math.asin(d9 / d8 / 2.0) / Math.PI * 180.0;
                if (extDebug) {
                    System.out.println("...2...xreac.xcvarEnergy.compValue=" + this.xreac.xcvarEnergy.compValue + " xreac.xProj1.MASS_mev=" + d7 + "\nkk=" + d8 + " qq=" + d9 + " qq/kk/2=" + d9 / d8 / 2.0 + "\nrnowValue=" + f2 + " ConversionFactor=" + x4var2.ConversionFactor + " angle=" + d6 + " deg\n");
                }
                f2 = (float)d6;
                f = new Float(f2);
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B*RT-EV") && x4var2.x4d025.UnitFamilyCode.equals("BRE") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null) {
                d2 = f2;
                d = this.xreac.xcvarEnergy.compValue.floatValue();
                if (Math.abs(d) > 0.0) {
                    f2 = (float)(d2 /= Math.sqrt(Math.abs(d)));
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...DATA  CONVERTED BARNS*SQRT(E) TO BARNS");
                    this.xreac.BasicUnitsFinal = "B";
                    this.convUnits = "B";
                }
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null && this.xreac.xreac1.SF8.indexOf("RTE") >= 0) {
                d2 = f2;
                d = this.xreac.xcvarEnergy.compValue.floatValue();
                if (Math.abs(d) > 0.0) {
                    f2 = (float)(d2 /= Math.sqrt(Math.abs(d)));
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...RTE:DATA*SQRT(E) CONVERTED TO BARNS");
                    this.xreac.BasicUnitsFinal = "B";
                }
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B/SR") && this.xreac != null && this.xreac.xreac1.SF8.indexOf("D4PI") >= 0) {
                d2 = f2;
                d2 = d2 * 4.0 * Math.PI;
                f2 = (float)d2;
                f = new Float(f2);
                this.str2CompNotes("OPERATION...D4PI:DATA=DATA*4*pi CONVERTED B/SR TO BARNS");
                this.xreac.BasicUnitsFinal = "B";
                this.convUnits = "B";
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B*EV") && x4var2.x4d025.UnitFamilyCode.equals("B*E") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null && this.xreac.xreac1.SF8.indexOf("SFC") >= 0 && this.xreac.beta_SFC != null) {
                d2 = f2;
                d = this.xreac.xcvarEnergy.compValue.floatValue();
                if (this.xreac.xcvarEnergy.convert_Einc2Lab_done || !x4dict024dt.getLabCMFlag(this.xreac.xcvarEnergy.xvar0.PlottingFlags)) {
                    d /= this.xreac.koeffEnCM2Lab;
                }
                if ((d = Math.abs(d / 1000000.0)) > 0.0) {
                    d2 = d2 / d * Math.exp(-1.0 * this.xreac.beta_SFC / Math.sqrt(d));
                    f2 = (float)(d2 /= 1000000.0);
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...DATA  CONVERTED S-FACTORS TO BARNS");
                    this.xreac.BasicUnitsFinal = "B";
                } else {
                    d2 = 0.0;
                    f2 = (float)d2;
                    f = new Float(f2);
                    this.str2CompNotes("WARNING EN=0 OPERATION FAILED...DATA CONVERTED S-FACTORS TO BARNS:=0");
                    this.xreac.BasicUnitsFinal = "B";
                }
                this.convUnits = "B";
            }
            switch (x4var2.PlottingFlags1int[0]) {
                case 1: {
                    this.rValue = f;
                    this.put2MinMaxReaction(f);
                    break;
                }
                case 2: {
                    this.rMin = f;
                    this.xvarMin = x4var2;
                    this.put2MinMaxReaction(f);
                    break;
                }
                case 3: {
                    this.rMax = f;
                    this.xvarMax = x4var2;
                    this.put2MinMaxReaction(f);
                    break;
                }
                case 4: {
                    this.rAprx = f;
                    this.xvarAprx = x4var2;
                    this.put2MinMaxReaction(f);
                }
            }
            x4var2.c4dataValue = f;
            this.setMinMaxValue(f);
        }
        if (this.rValue != null) {
            this.compValue = this.rValue;
            this.compValueSetFromCh = (char)49;
        } else if (this.rMin != null && this.rMax != null) {
            this.compValue = new Float((this.rMin.floatValue() + this.rMax.floatValue()) / 2.0f);
            this.compErrorFromMinMaxVal = new Float(this.rMax.floatValue() - this.compValue.floatValue());
            this.compValueMin = new Float(this.rMin.floatValue());
            this.compValueMax = new Float(this.rMax.floatValue());
            this.str2CompNotes("OPERATION...  Value=(Min+Max)/2  Min:" + this.xvarMin.Header + ", Max:" + this.xvarMax.Header);
            this.compValueSetFrom = "Min2Max";
            this.compValueSetFromCh = (char)54;
        } else if (this.rAprx != null) {
            this.compValue = this.rAprx;
            this.str2CompNotes("OPERATION...  Value=Aprx  Aprx:" + this.xvarAprx.Header);
            this.compValueSetFrom = "Aprx";
            this.compValueSetFromCh = (char)52;
        } else if (this.rMin != null) {
            this.compValue = this.rMin;
            this.str2CompNotes("OPERATION...  Value=Min  Min:" + this.xvarMin.Header);
            this.compValueSetFrom = "Min";
            this.compValueSetFromCh = (char)50;
        } else if (this.rMax != null) {
            this.compValue = this.rMax;
            this.str2CompNotes("OPERATION...  Value=Max  Max:" + this.xvarMax.Header);
            this.compValueSetFrom = "Max";
            this.compValueSetFromCh = (char)51;
        }
        if (this.xvar0 != null && this.xvar0.numDataType1 == 4 && this.degAngle != null && this.minAngle != null) {
            this.compValue = new Float(this.degAngle.floatValue() + this.minAngle.floatValue());
        }
        if (this.xvar0 != null && this.xvar0.numDataType1 == 7) {
            this.compValue = new Float((double)(this.iElement * 1000 + this.iMass * 1) + 0.1 * (double)this.iIsomer);
        }
        if (this.compValue != null) {
            this.prepareCompError();
        }
        return bl;
    }

    public void setMinMaxValue(Float f) {
        if (f == null) {
            return;
        }
        if (this.compValueMin == null) {
            this.compValueMax = this.compValueMin = new Float(f.floatValue());
            return;
        }
        if (f.floatValue() < this.compValueMin.floatValue()) {
            this.compValueMin = new Float(f.floatValue());
        }
        if (f.floatValue() > this.compValueMax.floatValue()) {
            this.compValueMax = new Float(f.floatValue());
        }
    }

    public void prepareCompValuesC5(x4cvar[] x4cvarArray, int n) {
        for (int i = 0; i < x4cvarArray.length; ++i) {
            x4cvarArray[(i + 1) % x4cvarArray.length].prepareCompValue(n);
        }
    }

    public void put2MinMaxReaction(Float f) {
        if (this.rMaxReaction == null) {
            this.rMaxReaction = new Float(f.floatValue());
        } else if (f.floatValue() > this.rMaxReaction.floatValue()) {
            this.rMaxReaction = new Float(f.floatValue());
        }
        if (this.rMinReaction == null) {
            this.rMinReaction = new Float(f.floatValue());
        } else if (f.floatValue() < this.rMinReaction.floatValue()) {
            this.rMinReaction = new Float(f.floatValue());
        }
    }

    public boolean prepareCompError() {
        x4var x4var2 = null;
        Float f = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int n = 0;
        boolean bl = true;
        this.compError = null;
        this.compErrorGiven = null;
        Float f4 = null;
        Float f5 = null;
        Float f6 = null;
        Float f7 = null;
        Float f8 = null;
        Float f9 = null;
        Float f10 = null;
        for (int i = 0; i < this.vVars.size(); ++i) {
            double d;
            double d2;
            double d3;
            x4var2 = (x4var)this.vVars.elementAt(i);
            if (x4var2.Variable_Value == 1 || x4var2.PlottingFlags1int[0] != 9) continue;
            boolean bl2 = true;
            if (!x4var2.BasicUnits.equals("PER-CENT") && x4var2.xvarInPercent != null) {
                this.str2CompNotes("RECALCULATED COLUMN:" + x4var2.Header + "" + "," + x4var2.BasicUnits + "" + "  TO:" + x4var2.xvarInPercent.BasicUnits + "" + (x4var2.xvarInPercent.xdata.DnRow == 1 ? "=" + x4var2.xvarInPercent.nowValue + "%" : ":L=" + x4var2.xvarInPercent.xdata.DnRow));
                x4var2 = x4var2.xvarInPercent;
            }
            if (!(x4var2.numDataType != 1 && x4var2.numDataType != -9 || this.xreac == null || x4var2.BasicUnits.equals("PER-CENT"))) {
                String string = "///";
                if (this.xreac.c4obj != null && this.xreac.c4obj.c4_DataUnits != null && this.xreac.c4obj.c4_DataUnits.length > 2 && this.xreac.c4obj.c4_DataUnits[2] != null) {
                    string = this.xreac.c4obj.c4_DataUnits[2];
                }
                if (!this.eqBasicUnits(x4var2.BasicUnits, this.xreac.BasicUnits) && !this.eqBasicUnits(x4var2.BasicUnits, string)) {
                    bl2 = false;
                    this.str2CompNotes("Ignored column:" + x4var2.Header + "" + "," + x4var2.BasicUnits + "" + "  expected units:" + this.xreac.BasicUnits + "" + ",PER-CENT");
                }
            }
            if (this.xreac != null && x4var2.Header.endsWith("-DIG")) {
                bl2 = false;
                this.str2CompNotes("Ignored column:" + x4var2.Header + "" + "," + x4var2.BasicUnits + "" + " (digitizing error)");
            }
            if (!bl2) continue;
            n = 0;
            if (x4var2.x4d025 != null) {
                n = x4var2.x4d025.Operation;
            }
            x4var2.c4dataValue = null;
            x4var2.c4errorValue = null;
            x4var2.c4errorPercent = null;
            f = x4var2.nowValue;
            if (f == null) continue;
            f2 = f.floatValue();
            if (x4var2.numDataType == 21 && this.xreac != null && this.xreac.xProj1 != null && this.xreac.xcvarEnergy != null && (x4var2.BasicUnits.equals("MICROSEC/M") || x4var2.BasicUnits.equals("NSEC/M"))) {
                double d4 = 0.02766;
                d3 = (double)this.xreac.xcvarEnergy.compValue.floatValue() / 1000000.0;
                f2 = (float)((double)f2 * 1.0E9);
                d2 = d4 * Math.pow(d3, 1.5) * (double)f2;
                f2 = (float)(d2 * 1000000.0);
                f = new Float(f2);
                this.str2CompNotes("OPERATION...CONVERTED RESOLUTION(" + x4var2.BasicUnits + ") TO ERROR(EV) ");
            }
            if (x4var2.numDataType == 1 && this.mult_DAM > 0.0 && !x4var2.BasicUnits.equals("PER-CENT")) {
                f = Float.valueOf(f.floatValue() * (float)this.mult_DAM);
                f2 = f.floatValue();
            }
            if (x4var2.numDataType == 1 && this.div_ABU > 0.0 && !x4var2.BasicUnits.equals("PER-CENT")) {
                f = Float.valueOf(f.floatValue() / (float)this.div_ABU);
                f2 = f.floatValue();
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B*RT-EV") && x4var2.x4d025.UnitFamilyCode.equals("BRE") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null) {
                double d5 = f2;
                d3 = this.xreac.xcvarEnergy.compValue.floatValue();
                if (Math.abs(d3) > 0.0) {
                    f2 = (float)(d5 /= Math.sqrt(Math.abs(d3)));
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...ERROR CONVERTED BARNS*SQRT(E) TO BARNS");
                }
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null && this.xreac.xreac1.SF8.indexOf("RTE") >= 0) {
                double d6 = f2;
                d3 = this.xreac.xcvarEnergy.compValue.floatValue();
                if (Math.abs(d3) > 0.0) {
                    f2 = (float)(d6 /= Math.sqrt(Math.abs(d3)));
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...RTE:ERROR*SQRT(E) CONVERTED TO BARNS");
                }
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B/SR") && this.xreac != null && this.xreac.xreac1.SF8.indexOf("D4PI") >= 0) {
                double d7 = f2;
                d7 = d7 * 4.0 * Math.PI;
                f2 = (float)d7;
                f = new Float(f2);
                this.str2CompNotes("OPERATION...D4PI:ERROR=ERROR*4*pi CONVERTED B/SR TO BARNS");
                this.xreac.BasicUnitsFinal = "B";
            }
            if (x4var2.numDataType == 1 && x4var2.x4d025 != null && x4var2.BasicUnits.equals("B*EV") && x4var2.x4d025.UnitFamilyCode.equals("B*E") && this.xreac != null && this.xreac.xcvarEnergy != null && this.xreac.xcvarEnergy.compValue != null && this.xreac.xreac1.SF8.indexOf("SFC") >= 0 && this.xreac.beta_SFC != null) {
                double d8 = f2;
                d3 = this.xreac.xcvarEnergy.compValue.floatValue();
                if (this.xreac.xcvarEnergy.convert_Einc2Lab_done || !x4dict024dt.getLabCMFlag(this.xreac.xcvarEnergy.xvar0.PlottingFlags)) {
                    d3 /= this.xreac.koeffEnCM2Lab;
                }
                if ((d3 = Math.abs(d3 / 1000000.0)) > 0.0) {
                    d8 = d8 / d3 * Math.exp(-1.0 * this.xreac.beta_SFC / Math.sqrt(d3));
                    f2 = (float)(d8 /= 1000000.0);
                    f = new Float(f2);
                    this.str2CompNotes("OPERATION...ERROR CONVERTED S-FACTORS TO BARNS");
                } else {
                    d8 = 0.0;
                    f2 = (float)d8;
                    f = new Float(f2);
                    this.xreac.BasicUnitsFinal = "B";
                }
            }
            if (x4var2.numDataType == 43 && this.xreac != null && this.xreac.xProj1 != null && this.xreac.xcvarEnergy != null) {
                double d9;
                double d10 = 197.372;
                d3 = (double)this.xreac.xcvarEnergy.compValue.floatValue() / 1000000.0;
                d2 = this.xreac.xProj1.MASS_mev;
                if (d2 == 0.0) {
                    f = null;
                    continue;
                }
                double d11 = 1.0 / d10 * Math.sqrt(2.0 * d2 * d3);
                double d12 = f2 / x4var2.ConversionFactor;
                if (x4var2.Units.endsWith("/C")) {
                    d9 = (double)f2 / 1000000.0;
                    d12 = d9 / d10;
                }
                d9 = 2.0 * Math.asin(d12 / d11 / 2.0) / Math.PI * 180.0;
                f2 = (float)d9;
                f = new Float(f2);
            }
            if ((x4var2.numDataType == 42 || x4var2.numDataType == 43) && f != null && (d = f.doubleValue()) >= -1.0 && d <= 1.0) {
                d = Math.acos(d) / Math.PI * 180.0;
                f = new Float(d);
            }
            if (n == 9 && this.xreac != null && this.xreac.xProj1 != null && this.xreac.xProj1.aa != 0) {
                f = new Float(f2 *= (float)this.xreac.xProj1.aa);
            }
            x4var2.c4dataValue = f;
            if (x4var2.BasicUnits.equals("PER-CENT")) {
                x4var2.c4errorPercent = f;
                f2 = this.compValue.floatValue() * f2 / 100.0f;
                f2 = Math.abs(f2);
                x4var2.c4errorValue = f = new Float(f2);
                if (x4var2.xdata != null && x4var2.xdata.strCompNotes != null) {
                    this.str2CompNotes(x4var2.xdata.strCompNotes);
                }
            } else {
                x4var2.c4errorValue = f;
                if (this.compValue.floatValue() != 0.0f) {
                    float f11 = f2 * 100.0f / this.compValue.floatValue();
                    x4var2.c4errorPercent = new Float(f11);
                }
            }
            boolean bl3 = false;
            if (this.xreac != null) {
                bl3 = this.xreac.flagDataErr_equ_ErrSys;
            }
            if (!x4var2.Header.endsWith("DATA-ERR")) {
                bl3 = false;
            }
            if (!bl3 && x4var2.PlottingFlags1int[1] == 1) {
                if (this.errTotal_plus == null) {
                    this.errTotal_plus = f;
                } else if (f.floatValue() > this.errTotal_plus.floatValue()) {
                    this.errTotal_plus = f;
                }
            }
            if (!bl3 && x4var2.PlottingFlags1int[2] == 1) {
                if (this.errTotal_minus == null) {
                    this.errTotal_minus = f;
                } else if (f.floatValue() > this.errTotal_minus.floatValue()) {
                    this.errTotal_minus = f;
                }
            }
            if (this.errResol_plus == null && x4var2.PlottingFlags1int[1] == 2) {
                this.errResol_plus = f;
            }
            if (this.errResol_minus == null && x4var2.PlottingFlags1int[2] == 2) {
                this.errResol_minus = f;
            }
            if (this.errResol_plus == null && x4var2.PlottingFlags1int[1] == 3) {
                this.errResol_plus = new Float(f2 * 2.0f);
            }
            if (this.errResol_minus == null && x4var2.PlottingFlags1int[2] == 3) {
                this.errResol_minus = new Float(f2 * 2.0f);
            }
            if (x4var2.PlottingFlags1int[1] == 4) {
                this.errPartialStat_plus = f;
                this.errPartialStatAccu2_plus += f2 * f2;
                if (x4var2.Header.indexOf("ERR-S") >= 0) {
                    this.errStat_plus = f;
                }
            }
            if (x4var2.PlottingFlags1int[2] == 4) {
                this.errPartialStat_minus = f;
                this.errPartialStatAccu2_minus += f2 * f2;
                if (x4var2.Header.indexOf("ERR-S") >= 0) {
                    this.errStat_minus = f;
                }
            }
            if (x4var2.PlottingFlags1int[1] == 6) {
                this.errPartialMrc_plus = f;
                this.errPartialMrcAccu2_plus += f2 * f2;
                if (x4var2.Header.indexOf("ERR-P") >= 0) {
                    this.errMrc_plus = f;
                }
            }
            if (x4var2.PlottingFlags1int[2] == 6) {
                this.errPartialMrc_minus = f;
                this.errPartialMrcAccu2_minus += f2 * f2;
                if (x4var2.Header.indexOf("ERR-P") >= 0) {
                    this.errMrc_minus = f;
                }
            }
            if (x4var2.Header.indexOf("ERR-SYS") >= 0) {
                if (this.errSys_plus == null && x4var2.PlottingFlags1int[1] == 5) {
                    this.errSys_plus = f;
                }
                if (this.errSys_minus != null || x4var2.PlottingFlags1int[2] != 5) continue;
                this.errSys_minus = f;
                continue;
            }
            if (bl3) {
                if (this.errSys_plus == null && x4var2.PlottingFlags1int[1] != 0) {
                    this.errSys_plus = f;
                }
                if (this.errSys_minus == null && x4var2.PlottingFlags1int[2] != 0) {
                    this.errSys_minus = f;
                }
                String string = x4cvar.myStrReplace(x4var2.Header, "DATA-ERR", "ERR-SYS");
                string = string + "," + x4var2.Units;
                this.str2CompNotes("Revised column:" + x4var2.Header + "," + x4var2.Units + " to " + string);
                continue;
            }
            if (x4var2.PlottingFlags1int[1] == 5) {
                this.errPartialSys_plus = f;
                this.errPartialSysAccu2_plus += f2 * f2;
            }
            if (x4var2.PlottingFlags1int[2] != 5) continue;
            this.errPartialSys_minus = f;
            this.errPartialSysAccu2_minus += f2 * f2;
        }
        if (this.errPartialSys_plus != null) {
            this.errPartialSys_plus = new Float(Math.sqrt(this.errPartialSysAccu2_plus));
        }
        if (this.errPartialSys_minus != null) {
            this.errPartialSys_minus = new Float(Math.sqrt(this.errPartialSysAccu2_minus));
        }
        if (this.errPartialStat_plus != null) {
            this.errPartialStat_plus = new Float(Math.sqrt(this.errPartialStatAccu2_plus));
        }
        if (this.errPartialStat_minus != null) {
            this.errPartialStat_minus = new Float(Math.sqrt(this.errPartialStatAccu2_minus));
        }
        if (this.errPartialMrc_plus != null) {
            this.errPartialMrc_plus = new Float(Math.sqrt(this.errPartialMrcAccu2_plus));
        }
        if (this.errPartialMrc_minus != null) {
            this.errPartialMrc_minus = new Float(Math.sqrt(this.errPartialMrcAccu2_minus));
        }
        if (this.errSys_plus != null && this.errPartialSys_plus != null && this.errPartialSys_plus.floatValue() > this.errSys_plus.floatValue()) {
            this.errSys_plus = this.errPartialSys_plus;
        }
        if (this.errSys_minus != null && this.errPartialSys_minus != null && this.errPartialSys_minus.floatValue() > this.errSys_minus.floatValue()) {
            this.errSys_minus = this.errPartialSys_minus;
        }
        f5 = this.errSys_plus;
        f6 = this.errSys_minus;
        if (this.errSys_plus == null) {
            f5 = this.errPartialSys_plus;
        }
        if (this.errSys_minus == null) {
            f6 = this.errPartialSys_minus;
        }
        f7 = this.errStat_plus;
        f8 = this.errStat_minus;
        if (this.errStat_plus == null) {
            f7 = this.errPartialStat_plus;
        }
        if (this.errStat_minus == null) {
            f8 = this.errPartialStat_minus;
        }
        f9 = this.errMrc_plus;
        f10 = this.errMrc_minus;
        if (this.errMrc_plus == null) {
            f9 = this.errPartialMrc_plus;
        }
        if (this.errMrc_minus == null) {
            f10 = this.errPartialMrc_minus;
        }
        if (this.errSys_plus == null) {
            this.errSys_plus = f5;
        }
        if (this.errSys_minus == null) {
            this.errSys_minus = f6;
        }
        if (this.errStat_plus == null) {
            this.errStat_plus = f7;
        }
        if (this.errStat_minus == null) {
            this.errStat_minus = f8;
        }
        if (this.errMrc_plus == null) {
            this.errMrc_plus = f9;
        }
        if (this.errMrc_minus == null) {
            this.errMrc_minus = f10;
        }
        if (this.errTotal_plus != null) {
            this.compError = this.errTotal_plus;
            this.compErrorGiven = this.errTotal_plus;
            if (this.errTotal_minus != null && this.errTotal_minus.floatValue() > this.compError.floatValue()) {
                this.compError = this.errTotal_minus;
                this.compErrorGiven = this.errTotal_minus;
            }
        } else if (this.errTotal_minus != null) {
            this.compError = this.errTotal_minus;
            this.compErrorGiven = this.errTotal_minus;
        } else if (this.errResol_plus != null) {
            this.compError = this.errResol_plus;
            if (this.errResol_minus != null && this.errResol_minus.floatValue() > this.compError.floatValue()) {
                this.compError = this.errResol_minus;
            }
        } else if (this.errResol_minus != null) {
            this.compError = this.errResol_minus;
        } else if (this.errStat_plus != null) {
            this.compError = this.errStat_plus;
            if (f5 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f5.floatValue(), 2.0)));
            }
            if (f9 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f9.floatValue(), 2.0)));
            }
            if (this.errStat_minus != null) {
                f4 = this.errStat_minus;
                if (f6 != null) {
                    f4 = new Float(Math.sqrt(Math.pow(f4.floatValue(), 2.0) + Math.pow(f6.floatValue(), 2.0)));
                }
                if (f10 != null) {
                    f4 = new Float(Math.sqrt(Math.pow(f4.floatValue(), 2.0) + Math.pow(f10.floatValue(), 2.0)));
                }
                if (f4.floatValue() > this.compError.floatValue()) {
                    this.compError = f4;
                }
            }
        } else if (this.errStat_minus != null) {
            this.compError = this.errStat_minus;
            if (f6 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f6.floatValue(), 2.0)));
            }
            if (f10 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f10.floatValue(), 2.0)));
            }
        } else if (f5 != null) {
            this.compError = f5;
            if (f9 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f9.floatValue(), 2.0)));
            }
            if (f6 != null) {
                f4 = f6;
                if (f9 != null) {
                    f4 = new Float(Math.sqrt(Math.pow(f4.floatValue(), 2.0) + Math.pow(f6.floatValue(), 2.0)));
                }
                if (f4.floatValue() > this.compError.floatValue()) {
                    this.compError = f4;
                }
            }
        } else if (f6 != null) {
            this.compError = f6;
            if (f10 != null) {
                this.compError = new Float(Math.sqrt(Math.pow(this.compError.floatValue(), 2.0) + Math.pow(f10.floatValue(), 2.0)));
            }
        } else if (f9 != null) {
            this.compError = f9;
            if (f10 != null && f10.floatValue() > this.compError.floatValue()) {
                this.compError = f10;
            }
        } else if (f10 != null) {
            this.compError = f10;
        } else if (this.compErrorFromMinMaxVal != null) {
            this.compError = this.compErrorFromMinMaxVal;
        }
        if (this.errSys_plus != null) {
            this.compErrorSys = this.errSys_plus;
            if (this.errSys_minus != null && this.errSys_minus.floatValue() > this.compErrorSys.floatValue()) {
                this.compErrorSys = this.errSys_minus;
            }
        } else if (this.errSys_minus != null) {
            this.compErrorSys = this.errSys_minus;
        }
        if (this.errStat_plus != null) {
            this.compErrorStat = this.errStat_plus;
            if (this.errStat_minus != null && this.errStat_minus.floatValue() > this.compErrorStat.floatValue()) {
                this.compErrorStat = this.errStat_minus;
            }
        } else if (this.errStat_minus != null) {
            this.compErrorStat = this.errStat_minus;
        }
        if (this.errMrc_plus != null) {
            this.compErrorMrc = this.errMrc_plus;
            if (this.errMrc_minus != null && this.errMrc_minus.floatValue() > this.compErrorMrc.floatValue()) {
                this.compErrorMrc = this.errMrc_minus;
            }
        } else if (this.errMrc_minus != null) {
            this.compErrorMrc = this.errMrc_minus;
        }
        if (this.compErrorStat == null && this.compErrorSys == null) {
            if (this.errPartialSys_plus != null) {
                this.compErrorSys = this.errPartialSys_plus;
                if (this.errPartialSys_minus != null && this.errPartialSys_minus.floatValue() > this.compErrorSys.floatValue()) {
                    this.compErrorSys = this.errPartialSys_minus;
                }
            } else if (this.errPartialSys_minus != null) {
                this.compErrorSys = this.errPartialSys_minus;
            }
        }
        return bl;
    }

    public Float d2f(Double d) {
        if (d == null) {
            return null;
        }
        return new Float(d);
    }

    public String ff2proc(Float f, Float f2) {
        if (f == null) {
            return null;
        }
        if (f2 == null) {
            return null;
        }
        if (f == null && f2.floatValue() == 0.0f) {
            return "0/0";
        }
        if (f2.floatValue() == 0.0f) {
            return f + "/0";
        }
        double d = f.floatValue() / f2.floatValue() * 100.0f;
        String string = String.format("%.2f", d);
        return string + "%";
    }

    public boolean eqBasicUnits(String string, String string2) {
        if (string.equals("ARB-UNITS")) {
            string = "NO-DIM";
        }
        if (string2.equals("ARB-UNITS")) {
            string2 = "NO-DIM";
        }
        return string.equals(string2);
    }

    public String getIsotope() {
        return this.zaToIsotope(this.zNucl, this.aNucl, this.sNucl);
    }

    public String zaToIsotope(Float f, Float f2, Float f3) {
        String string = "";
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        if (extDebug) {
            System.out.println("......Z=" + f + " A=" + f2 + " S=" + f3);
        }
        if (f == null && f2 == null && f3 == null) {
            return "";
        }
        if (f != null) {
            n = f.intValue();
        }
        if (f2 != null) {
            n2 = f2.intValue();
        }
        if (f3 != null) {
            n3 = f3.intValue();
        }
        if (extDebug) {
            System.out.println("......zz=" + n + " aa=" + n2 + " ss=" + n3);
        }
        if (n < 0 && n2 < 0) {
            return "";
        }
        if (n == 0 && n2 == 0) {
            return "G-0";
        }
        if (n > nucl.length) {
            return "";
        }
        string = n >= 0 ? n + "-" + nucl[n] : "XX-XX";
        string = n2 >= 0 ? string + "-" + n2 : string + "-XX";
        if (n3 == 0) {
            string = string + "-g";
        }
        if (n3 > 0) {
            string = string + "-m" + n3;
        }
        return string;
    }

    public static String zaToIsotope(Float f, Float f2, char c, boolean bl) {
        String string = "";
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        if (extDebug) {
            System.out.println("......Z=" + f + " A=" + f2 + " S=" + c);
        }
        if (f == null && f2 == null && c == ' ') {
            return "";
        }
        if (f != null) {
            n = f.intValue();
        }
        if (f2 != null) {
            n2 = f2.intValue();
        }
        if (extDebug) {
            System.out.println("......zz=" + n + " aa=" + n2 + " ss=" + n3);
        }
        if (n < 0 && n2 < 0) {
            return "";
        }
        if (n == 0 && n2 == 0) {
            return "G-0";
        }
        if (n > nucl.length) {
            return "";
        }
        string = bl ? (n >= 0 ? n + "-" + nucl[n].toUpperCase() : "XX-XX") : (n >= 0 ? nucl[n] : "XX");
        string = n2 >= 0 ? string + "-" + n2 : string + "-XX";
        if (c != ' ') {
            n3 = c == 'G' ? 0 : c - 76;
        }
        if (n3 == 0) {
            string = string + "-G";
        } else if (n3 == 1) {
            string = string + "-M";
        } else if (n3 > 1) {
            string = string + "-M" + n3;
        }
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

    public void println() {
        String string = "";
        if (this.xreac != null && this.xreac.subent != null) {
            string = this.xreac.subent.Subent;
        }
        if (this.xreac != null) {
            string = string + this.xreac.cpointer;
        }
        System.out.println("---x4cvar.println()--");
        for (int i = 0; i < this.vVars.size(); ++i) {
            x4var x4var2 = (x4var)this.vVars.elementAt(i);
            System.out.println("---1---varNum=" + x4var2.Variable_Number + " " + string + " " + (i + 1) + ")\t" + x4var2.Header + "\t what:" + x4var2.what + "\t whatVar:" + x4var2.whatVar + "\torder=" + x4var2.strVarOrderFlag + "\tDataType=" + x4var2.DataType + "\tFamilyCode=" + x4var2.FamilyCode + "\tnowValue=" + x4var2.nowValue);
        }
        System.out.println("");
    }
}
