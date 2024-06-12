package zvv.x4;

import java.util.Vector;

/**
 * Isotope: x4isot internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-02-15
 * @since           2011-10-27
 *
 * Program:         x4isot.java
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

public class x4isot {
    static boolean extDebug = false;
    int zz = 0;
    int aa = 0;
    int za = 0;
    String sym = "";
    String meta = "";
    String dmeta = "";
    int imeta = 0;
    char cmeta = (char)32;
    char cdmeta = (char)32;
    String Name = "";
    String originalStr = "";
    String newStr = "";
    String partName = "";
    String shortName = "";
    public double MASS_mev = -1.0;
    public Double Spin = null;
    public double AtomicWeight = -1.0;
    public float Abundance = -1.0f;
    String X4Compound = "";
    String X4CompoundType = " ";
    String X4CompoundHelp = "";
    String shortHelp = "";
    x4dict709 x4d709 = null;
    x4dict033 x4d033 = null;
    x4dict227 x4d227 = null;
    x4dict008 x4d008 = null;

    public static void main(String[] stringArray) {
        System.out.println(" V.Zerkin, IAEA, Vienna, 2007-2019\n");
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4isot x4isot2 = new x4isot("21-SC-45-M");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("19-K-0");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("N");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("1-H-D2O");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("11-NA-22-G");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("40-ZR-89-M1/G");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("12-MG-0");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("13-AL-27");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("11-NA-24-M1");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("79-AU-196-M2");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("11-NA-22-G");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("HE6");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("N");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("A");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("D");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("0");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("PIN");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("40-ZR-89-M1/G");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("63-EU-152-M1/G+M2");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("49-IN-116-M1+M2/G");
        System.out.println(x4isot2.strpad(x4isot2.originalStr, 20) + x4isot2.getInfo(1));
        x4isot2 = new x4isot("21-SC-45-M");
        x4isot2.println();
        x4isot2 = new x4isot("19-K-0");
        x4isot2.println();
        x4isot2 = new x4isot("19-K-39");
        x4isot2.println();
        System.exit(0);
        x4isot2 = new x4isot("N");
        x4isot2.println();
        x4isot2 = new x4isot("1-H-D2O");
        x4isot2.println();
        x4isot2 = new x4isot("11-NA-22-G");
        x4isot2.println();
        x4isot2 = new x4isot("40-ZR-89-M1/G");
        x4isot2.println();
        x4isot2 = new x4isot("12-MG-0");
        x4isot2.println();
        x4isot2 = new x4isot("13-AL-27");
        x4isot2.println();
        x4isot2 = new x4isot("11-NA-24-M1");
        x4isot2.println();
        x4isot2 = new x4isot("79-AU-196-M2");
        x4isot2.println();
        x4isot2 = new x4isot("11-NA-22-G");
        x4isot2.println();
        x4isot2 = new x4isot("40-ZR-89-M1/G");
        x4isot2.println();
        x4isot2 = new x4isot("HE6");
        x4isot2.println();
        x4isot2 = new x4isot("N");
        x4isot2.println();
        x4isot2 = new x4isot("A");
        x4isot2.println();
        x4isot2 = new x4isot("D");
        x4isot2.println();
        x4isot2 = new x4isot("0");
        x4isot2.println();
        x4isot2 = new x4isot("PIN");
        x4isot2.println();
        x4isot2 = new x4isot("PIP");
        x4isot2.println();
    }

    public x4isot(String string) {
        this.Name = string;
        this.originalStr = string;
        if (string.trim().equals("")) {
            return;
        }
        this.treatParticleOrIsotope(string);
    }

    public void treatParticleOrIsotope(String string) {
        x4dict227 x4dict2272;
        String string2;
        this.newStr = string2 = string.toUpperCase();
        if (string2.equals("E")) {
            this.za = -1000;
        } else if (string2.equals("0")) {
            this.za = -1;
        } else if (string2.equals("A")) {
            this.treatIsoName("2-HE-4");
        } else if (string2.equals("D")) {
            this.treatIsoName("1-H-2");
        } else if (string2.equals("G")) {
            this.treatIsoName("0-G-0");
        } else if (string2.equals("HE2")) {
            this.treatIsoName("2-HE-2");
        } else if (string2.equals("HE3")) {
            this.treatIsoName("2-HE-3");
        } else if (string2.equals("HE6")) {
            this.treatIsoName("2-HE-6");
        } else if (string2.equals("N")) {
            this.treatIsoName("0-NN-1");
        } else if (string2.equals("P")) {
            this.treatIsoName("1-H-1");
        } else if (string2.equals("T")) {
            this.treatIsoName("1-H-3");
        } else {
            this.treatIsoName(string);
        }
        this.MASS_mev = x4dict033.getMassMev(string, -1.0);
        if (this.MASS_mev < 0.0 && string.endsWith("-G")) {
            this.MASS_mev = x4dict033.getMassMev(string.substring(0, string.length() - 2), -1.0);
        }
        if ((x4dict2272 = x4dict227.findinx4dict(string)) != null) {
            this.AtomicWeight = x4dict2272.AtomicWeight / 1000000.0;
            this.Abundance = x4dict2272.Abundance / 100.0f;
        }
        if (this.zz > 0 && this.aa > 0 && this.AtomicWeight < 0.0) {
            this.AtomicWeight = this.aa;
            if (this.MASS_mev < 0.0) {
                this.MASS_mev = this.AtomicWeight * 931.494;
            }
        }
        this.Spin = x4dict033.getSpin(this.newStr);
        int n = string2.indexOf("-");
        if (n >= 0) {
            this.shortName = string2.substring(n + 1);
            if (this.shortName.length() > 1) {
                this.shortName = this.shortName.substring(0, 1) + this.shortName.substring(1).toLowerCase();
            }
        } else {
            this.shortName = string2.toLowerCase();
        }
    }

    public void treatIsoName(String string) {
        this.newStr = string;
        this.partName = string;
        if (string.equals("2-HE-4")) {
            this.partName = "A";
        } else if (string.equals("0-G-0")) {
            this.partName = "G";
        } else if (string.equals("2-HE-2")) {
            this.partName = "HE2";
        } else if (string.equals("2-HE-3")) {
            this.partName = "HE3";
        } else if (string.equals("2-HE-6")) {
            this.partName = "HE6";
        } else if (string.equals("0-NN-1")) {
            this.partName = "N";
        } else if (string.equals("1-H-1")) {
            this.partName = "P";
        } else if (string.equals("1-H-2")) {
            this.partName = "D";
        } else if (string.equals("1-H-3")) {
            this.partName = "T";
        }
        this.x4d033 = x4dict033.findinx4dict(this.partName);
        if (this.x4d033 != null) {
            this.shortHelp = this.x4d033.shortHelp;
        }
        this.x4d227 = x4dict227.findinx4dict(this.partName);
        if (this.x4d227 != null) {
            this.shortHelp = this.x4d227.shortHelp;
            this.x4d008 = x4dict008.findinx4dict(this.x4d227.Symbol);
            if (this.x4d008 != null && this.shortHelp.equals("")) {
                this.shortHelp = this.x4d008.shortHelp;
            }
        }
        this.x4d709 = x4dict709.findinx4dict(string);
        if (this.x4d709 != null) {
            this.X4Compound = this.x4d709.X4Compound;
            this.X4CompoundType = this.x4d709.CompoundType;
            if (this.X4CompoundType.length() <= 0) {
                this.X4CompoundType = " ";
            }
            this.X4CompoundHelp = this.x4d709.shortHelp;
            this.imeta = 999;
            this.cdmeta = this.cmeta = (char)32;
            this.dmeta = ".8";
            this.zz = this.x4d709.ZTarget;
            this.aa = this.x4d709.ATarget;
            this.za = this.x4d709.ZATarget;
            return;
        }
        String[] stringArray = this.mySplitStr(string, "-");
        for (int i = 0; i < stringArray.length; ++i) {
            int n;
            String string2;
            if (i == 0) {
                string2 = stringArray[i].trim();
                n = this.str2int(string2, -1);
                if (n < 0) break;
                this.zz = n;
                continue;
            }
            if (i == 1) {
                this.sym = string2 = stringArray[i].trim();
                if (!this.shortHelp.equals("")) continue;
                this.x4d008 = x4dict008.findinx4dict(this.sym);
                if (this.x4d008 == null || !this.shortHelp.equals("")) continue;
                this.shortHelp = this.x4d008.shortHelp;
                continue;
            }
            if (i == 2) {
                string2 = stringArray[i].trim();
                n = this.str2int(string2, -1);
                if (n < 0) break;
                this.aa = n;
                continue;
            }
            if (i != 3) continue;
            this.meta = string2 = stringArray[i].trim();
            if (this.meta.equals("G")) {
                this.imeta = 0;
                this.cmeta = (char)71;
                this.cdmeta = (char)48;
                this.dmeta = ".0";
            } else if (this.meta.equals("M")) {
                this.imeta = 1;
                this.cmeta = (char)77;
                this.cdmeta = (char)49;
                this.dmeta = ".1";
            } else if (this.meta.startsWith("M")) {
                this.imeta = this.str2int(this.meta.substring(1), 0);
                this.cmeta = (char)(77 + this.imeta - 1);
                this.cdmeta = (char)(48 + this.imeta - 1);
                this.dmeta = "." + this.imeta;
            } else if (this.meta.startsWith("L")) {
                this.imeta = this.str2int(this.meta.substring(1), 0);
                this.cmeta = (char)63;
                this.cdmeta = (char)63;
                this.dmeta = ".?";
            }
            if (string2.indexOf("+") < 0 && string2.indexOf("/") < 0) continue;
            this.imeta = 9;
            this.cmeta = (char)63;
            this.cmeta = (char)47;
            if (string2.indexOf("+") >= 0) {
                this.cmeta = (char)43;
            }
            if (string2.indexOf("+M2") >= 0) {
                this.imeta = 8;
            }
            this.dmeta = "." + this.imeta;
            this.cdmeta = (char)(48 + this.imeta - 1);
        }
        this.za = this.zz * 1000 + this.aa;
    }

    public void println() {
        System.out.println(" " + this.strpad(this.originalStr, 16) + " Z=" + this.zz + " sym=" + this.sym + " a=" + this.aa + " meta=" + this.meta + " imeta=" + this.imeta + " cmeta=" + this.cmeta + " za=" + this.za + " newStr=" + this.newStr + " Hlp=[" + this.shortHelp + "]" + " Cmp=[" + this.X4CompoundHelp + "]" + " MASS_mev=[" + this.MASS_mev + "]");
    }

    public String getInfo(int n) {
        String string = "";
        if (this.originalStr.equals("0")) {
            return "No particle";
        }
        if (!this.shortHelp.equals("")) {
            string = string + " " + this.shortHelp;
        }
        if (!this.X4CompoundHelp.equals("")) {
            string = string + " " + this.X4CompoundHelp;
        }
        if (n > 0) {
            if (this.aa >= 0) {
                string = " A=" + this.aa + string;
            }
            if (this.zz > 0) {
                string = " Z=" + this.zz + string;
            }
            if (this.aa > 0 && this.x4d033 == null) {
                string = string + "-" + this.aa;
            }
            if (this.imeta > 0 && this.imeta < 8) {
                string = string + "-" + this.meta;
            }
            if (this.imeta >= 8 && this.imeta < 100) {
                string = string + " [" + this.meta + "]";
            } else if (this.cmeta == 'G') {
                string = string + "-Ground";
            }
        }
        return string.trim();
    }

    public String[] mySplitStr(String string, String string2) {
        Vector<String> vector = new Vector<String>();
        String string3 = "";
        String string4 = string;
        while (true) {
            int n;
            if ((n = string4.indexOf(string2)) < 0) break;
            string3 = string4.substring(0, n);
            string4 = string4.substring(n + 1);
            vector.addElement(string3);
        }
        vector.addElement(string4);
        String[] stringArray = new String[vector.size()];
        for (int i = 0; i < vector.size(); ++i) {
            stringArray[i] = string3 = (String)vector.elementAt(i);
        }
        return stringArray;
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

    public String strpad(String string, int n) {
        int n2;
        if (string == null) {
            string = "";
        }
        if ((n2 = string.length()) == n) {
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
}
