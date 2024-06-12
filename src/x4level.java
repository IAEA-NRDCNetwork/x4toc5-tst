package zvv.x4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * RIPL-Levels object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-08-08
 * @since           2011-10-31
 *
 * Program:         x4level.java
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

class x4level {
    static PrintWriter myOut = new PrintWriter(System.out, true);
    String Elem = "";
    String Iso = "";
    String strMASS_kev = "";
    int ZZ;
    int ZA;
    int AA;
    int nlvr;
    int ngamr;
    int itmp1;
    int itmp2;
    int qn;
    int lvpr;
    int ndbrlin;
    double elvr1;
    double elvr;
    Vector levels = new Vector();
    static String DirOfLevelFiles = null;
    static String versionOfLevelFiles = "";
    static String creatorOfLevelFiles = "IAEA Reference Input Parameter Library";
    double lvlfound = 0.0;

    public static void main(String[] stringArray) {
        int n;
        x4level x4level2;
        System.out.println("Hello!!");
        String string = "C:\\zerkin\\x4web2011tom7\\apache-tomcat-7.0.11\\webapps\\exfor1\\x4prog\\Endver2\\Inputs\\levels\\";
        string = "C:\\x4web2011tom7\\apache-tomcat-7.0.11\\webapps\\exfor\\x4prog\\Endver2\\Inputs\\levels\\";
        string = "Z:\\zerkin\\levels\\levels\\";
        string = "E:\\x4app\\Endver\\Sources\\Inputs\\levels\\";
        x4level x4level3 = x4level.readLevelsFile(string, 4, 10);
        x4level3 = x4level.readLevelsFile(string, 12, 24);
        if (x4level3 == null) {
            System.exit(0);
        }
        x4level.setDirOfLevelFiles(string);
        x4level3 = x4level.readLevelsFile(6, 13);
        x4level3 = x4level.readLevelsFile(8, 16);
        x4level3 = x4level.readLevelsFile(7, 14);
        if (x4level3 == null) {
            System.exit(0);
        }
        if (x4level3.levels.size() > 1) {
            x4level2 = (x4level)x4level3.levels.elementAt(1);
            System.out.println(" Elem=[" + x4level3.Elem + "]" + " Z=" + x4level3.ZZ + " A=" + x4level3.AA + " iLevel=" + 1 + " E-Level(keV)=" + x4level2.elvr + "");
        }
        System.out.println("dir: " + string);
        Vector vector = x4level3.levels;
        for (n = 0; n < vector.size(); ++n) {
            x4level2 = (x4level)vector.elementAt(n);
            System.out.print(" " + n);
            System.out.print(" Elem=[" + x4level3.Elem + "]");
            System.out.print(" Z=" + x4level3.ZZ);
            System.out.print(" A=" + x4level3.AA);
            System.out.print(" E-Level(keV)=" + x4level2.elvr);
            System.out.println("");
        }
        int n2 = x4level.getLevelKeV(x4level3.levels, 2900.0);
        System.out.print(" 2900.0:ilvl=" + n2);
        n2 = x4level.getLevelKeV(x4level3.levels, 900.0);
        System.out.print(" 900.0:ilvl=" + n2);
        n2 = x4level.getLevelKeV(x4level3.levels, 210000.0);
        System.out.print(" 210000.0:ilvl=" + n2);
        n2 = x4level.getLevelKeV(x4level3.levels, 0.0);
        System.out.println(" 0.0:ilvl=" + n2);
        n2 = x4level.getLevelKeV(string, 4, 10, 720.0);
        System.out.println("4,10,720.:lvl=" + n2);
        n2 = x4level.getLevelKeV(string, 4, 10, 2720.0);
        System.out.println("4,10,2720.:lvl=" + n2);
        n2 = x4level.getLevelKeV(string, 4, 8, 2900.0);
        System.out.println("4,8,2900.:lvl=" + n2);
        x4level3 = x4level.readLevelsFile(4, 8);
        if (x4level3 == null) {
            System.exit(0);
        }
        for (n = 0; n < x4level3.levels.size(); ++n) {
            x4level2 = (x4level)x4level3.levels.elementAt(n);
            System.out.print(" iLev=" + n + "/" + x4level3.levels.size());
            System.out.print(" Elem=[" + x4level3.Elem + "]");
            System.out.print(" Z=" + x4level3.ZZ);
            System.out.print(" A=" + x4level3.AA);
            System.out.print(" E-Level(keV)=" + x4level2.elvr);
            System.out.println("");
        }
    }

    public static String setDirOfLevelFiles(String string) {
        String string2;
        String string3 = DirOfLevelFiles;
        DirOfLevelFiles = string;
        versionOfLevelFiles = string2 = x4level.getDateFile(DirOfLevelFiles + "z024.dat");
        return string3;
    }

    public static String getDateFile(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String string2 = "1970-01-01";
        File file = new File(string);
        if (file.exists()) {
            date = new Date(file.lastModified());
            string2 = new String(simpleDateFormat.format(date));
        }
        return string2;
    }

    x4level() {
    }

    x4level(String string) {
        this.Elem = this.strExtractStr(string, 4, 5, "").trim();
        this.AA = this.strExtractInt(string, 6, 10, -1);
        this.ZZ = this.strExtractInt(string, 11, 15, -1);
        this.nlvr = this.strExtractInt(string, 16, 20, -1);
        this.ngamr = this.strExtractInt(string, 21, 25, -1);
        this.itmp1 = this.strExtractInt(string, 26, 30, -1);
        this.itmp2 = this.strExtractInt(string, 31, 35, -1);
        this.ZA = this.ZZ * 1000 + this.AA;
        this.Iso = this.Elem + "-" + this.AA;
    }

    x4level(String string, boolean bl) {
        this.elvr1 = this.strExtractDouble(string, 5, 14, -1.0) * 1000.0;
        int n = (int)(this.elvr1 + 0.5);
        this.elvr = n;
        this.ndbrlin = this.strExtractInt(string, 35, 37, 0);
    }

    public static x4level readLevelsFile(int n, int n2) {
        return x4level.readLevelsFile(DirOfLevelFiles, n, n2);
    }

    public static x4level readLevelsFile(String string, int n, int n2) {
        String string2;
        x4level x4level2 = null;
        Vector vector = new Vector();
        String string3 = string + "z" + n / 100 + n / 10 % 10 + n % 10 + ".dat";
        BufferedReader bufferedReader = x4level.openInpFile(string3);
        if (bufferedReader == null) {
            return x4level2;
        }
        boolean bl = false;
        int n3 = 0;
        while ((string2 = x4level.readLineInpFile(bufferedReader)) != null) {
            String string4;
            int n4;
            x4level x4level3 = new x4level(string2);
            if (x4level3.ZZ != n || x4level3.AA != n2) {
                for (n4 = 0; n4 < x4level3.nlvr + x4level3.ngamr && (string4 = x4level.readLineInpFile(bufferedReader)) != null; ++n4) {
                }
            } else {
                for (n3 = 0; n3 < x4level3.nlvr && (string2 = x4level.readLineInpFile(bufferedReader)) != null; ++n3) {
                    x4level x4level4 = new x4level(string2, true);
                    x4level3.levels.addElement(x4level4);
                    for (n4 = 0; n4 < x4level4.ndbrlin && (string4 = x4level.readLineInpFile(bufferedReader)) != null; ++n4) {
                    }
                }
                x4level2 = x4level3;
                break;
            }
            ++n3;
        }
        x4level.closeInpFile(bufferedReader);
        return x4level2;
    }

    public static int getLevelKeV(Vector vector, double d) {
        int n = 0;
        double d2 = 0.0;
        double d3 = 0.0;
        for (int i = 0; i < vector.size(); ++i) {
            x4level x4level2 = (x4level)vector.elementAt(i);
            d3 = x4level2.elvr;
            if (d > d3) {
                d2 = d3;
                if (i != vector.size() - 1) continue;
                return i;
            }
            if (d - d2 < d3 - d) {
                n = i - 1;
                break;
            }
            n = i;
            break;
        }
        return n;
    }

    public static int getLevelKeV(String string, int n, int n2, double d) {
        x4level x4level2 = x4level.readLevelsFile(string, n, n2);
        if (x4level2 == null) {
            return -1;
        }
        int n3 = x4level.getLevelKeV(x4level2.levels, d);
        return n3;
    }

    public static double rgetLevelKeV(Vector vector, double d) {
        boolean bl = false;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        for (int i = 0; i < vector.size(); ++i) {
            x4level x4level2 = (x4level)vector.elementAt(i);
            d3 = x4level2.elvr;
            if (d > d3) {
                d2 = d3;
                if (i != vector.size() - 1) continue;
                return d2 * 1000.0;
            }
            if (d - d2 < d3 - d) {
                d4 = d2;
                break;
            }
            d4 = d3;
            break;
        }
        return d4 * 1000.0;
    }

    public static double rgetLevelKeV(String string, int n, int n2, double d) {
        x4level x4level2 = x4level.readLevelsFile(string, n, n2);
        if (x4level2 == null) {
            return -1.0;
        }
        double d2 = x4level.rgetLevelKeV(x4level2.levels, d);
        return d2;
    }

    public int findLevelKeV(double d) {
        int n = 0;
        double d2 = 0.0;
        double d3 = 0.0;
        Vector vector = this.levels;
        this.lvlfound = 0.0;
        for (int i = 0; i < vector.size(); ++i) {
            x4level x4level2 = (x4level)vector.elementAt(i);
            d3 = x4level2.elvr;
            if (d > d3) {
                d2 = d3;
                if (i != vector.size() - 1) continue;
                this.lvlfound = d3;
                return i;
            }
            if (d - d2 < d3 - d) {
                n = i - 1;
                this.lvlfound = d2;
                break;
            }
            n = i;
            this.lvlfound = d3;
            break;
        }
        return n;
    }

    public String strExtractStr(String string, int n, int n2, String string2) {
        String string3 = string2;
        try {
            string3 = string = string.substring(n - 1, n2);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return string3;
    }

    public int strExtractInt(String string, int n, int n2, int n3) {
        int n4 = n3;
        try {
            string = string.substring(n - 1, n2);
            n4 = this.str2int(string, n3);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n4;
    }

    public double strExtractDouble(String string, int n, int n2, double d) {
        double d2 = d;
        String string2 = this.strExtractStr(string, n, n2, "");
        if (string2.equals("")) {
            return d2;
        }
        Double d3 = this.strExtractDouble(string2);
        if (d3 == null) {
            return d2;
        }
        d2 = d3;
        return d2;
    }

    public Double strExtractDouble(String string) {
        Double d;
        block7: {
            d = null;
            double d2 = 0.0;
            if (string == null) {
                return d;
            }
            String string2 = this.strReplaceChar(string, ' ', "");
            if (string2.equals("")) {
                return d;
            }
            try {
                d2 = Double.parseDouble(string2);
                d = new Double(d2);
            }
            catch (Exception exception) {
                String string3 = string2.substring(1).toUpperCase();
                int n = string3.indexOf("+");
                if (n < 0) {
                    n = string3.indexOf("-");
                }
                if (n < 0 || string3.startsWith("E")) break block7;
                string3 = string3.substring(0, n) + "E" + string3.substring(n);
                string2 = string2.substring(0, 1) + string3;
                try {
                    d2 = Double.parseDouble(string2);
                    d = new Double(d2);
                }
                catch (Exception exception2) {
                    // empty catch block
                }
            }
        }
        return d;
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

    public String strReplaceChar(String string, char c, String string2) {
        int n = string.length();
        String string3 = "";
        for (int i = 0; i < n; ++i) {
            char c2 = string.charAt(i);
            string3 = c2 == c ? string3 + string2 : string3 + c2;
        }
        return string3;
    }

    public static BufferedReader openInpFile(String string) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(string));
            return bufferedReader;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static int closeInpFile(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public static String readLineInpFile(BufferedReader bufferedReader) {
        String string = null;
        try {
            string = bufferedReader.readLine();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return string;
    }
}
