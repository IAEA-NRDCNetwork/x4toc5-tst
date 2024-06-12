package zvv.x4;

/**
 * EXFOR-SystemID: x4sysid object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-03-15
 * @since           2010-06-29
 *
 * Program:         x4sysid.java
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

import java.io.PrintWriter;

class x4sysid {
    static PrintWriter myOut = new PrintWriter(System.out, true);
    String keyword = "";
    String pointer = "";
    String strN1 = "";
    String strN2 = "";
    String strN3 = "";
    String strN4 = "";
    String strN5 = "";
    String strN1shifted = "";
    int N1 = 0;
    int N2 = 0;
    int N3 = 0;
    int N4 = 0;
    int N5 = 0;
    int N1shifted = 0;
    static boolean readingOldFile = false;
    int iPossibleShift = 1;

    public static void main(String[] stringArray) {
        System.out.println("Hello!!");
        x4sysid x4sysid2 = new x4sysid("SUBENT        12634001   20050714   20050909                  1336");
        System.out.print(" keyword=[" + x4sysid2.keyword + "]");
        System.out.print(" pointer=[" + x4sysid2.pointer + "]");
        System.out.print(" strN1  =[" + x4sysid2.strN1 + "]");
        System.out.print(" strN2  =[" + x4sysid2.strN2 + "]");
        System.out.print(" strN3  =[" + x4sysid2.strN3 + "]");
        System.out.print(" strN4  =[" + x4sysid2.strN4 + "]");
        System.out.print(" strN5  =[" + x4sysid2.strN5 + "]");
    }

    x4sysid(String string) {
        String string2 = this.strpad(string, 66);
        this.keyword = this.strExtractStr(string2, 1, 10, "");
        this.keyword = this.delEndSpace(this.keyword);
        this.pointer = this.strExtractStr(string2, 11, 11, "").trim();
        this.strN1 = this.strExtractStr(string2, 12, 22, "").trim();
        this.strN2 = this.strExtractStr(string2, 23, 33, "").trim();
        this.strN3 = this.strExtractStr(string2, 34, 44, "").trim();
        this.strN4 = this.strExtractStr(string2, 45, 55, "").trim();
        this.strN5 = this.strExtractStr(string2, 56, 66, "").trim();
        if (readingOldFile && this.keyword.equals("SUBENT")) {
            this.strN2 = this.strExtractStr(string2, 23, 33 + this.iPossibleShift, "").trim();
            this.strN3 = this.strExtractStr(string2, 34 + this.iPossibleShift, 44 + this.iPossibleShift, "").trim();
            if (this.strN2.length() == 7) {
                this.strN2 = this.strN2.equals("1999012") ? "19990129" : "1" + this.strN2;
            }
            if (this.strN1.equals("31429001") && this.strN2.equals("070421")) {
                this.strN2 = "19970421";
            }
        }
        this.N1 = this.str2int(this.strN1, 0);
        this.N2 = this.str2int(this.strN2, 0);
        this.N3 = this.str2int(this.strN3, 0);
        this.N4 = this.str2int(this.strN4, 0);
        this.N5 = this.str2int(this.strN5, 0);
        int n = this.strN1.indexOf(" ");
        if (n > 0) {
            this.strN1shifted = this.strN1.substring(n).trim();
            this.N1shifted = this.str2int(this.strN1shifted, 0);
        } else {
            this.strN1shifted = this.strN1;
            this.N1shifted = this.N1;
        }
    }

    public String getUpdated() {
        String string = this.strN2.trim();
        if (string.length() == 6) {
            string = "19" + string;
        }
        return string;
    }

    public String toString() {
        String string = "";
        string = string + "KW=[" + this.keyword + "]";
        string = string + "pointer=[" + this.pointer + "]";
        string = string + "strN1  =[" + this.strN1 + "]";
        string = string + "strN2  =[" + this.strN2 + "]";
        string = string + "strN3  =[" + this.strN3 + "]";
        string = string + "strN4  =[" + this.strN4 + "]";
        string = string + "strN5  =[" + this.strN5 + "]";
        return string;
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

    public String delEndSpace(String string) {
        int n = string.length();
        for (int i = n - 1; i >= 0; --i) {
            if (string.substring(i, i + 1).equals(" ")) continue;
            return string.substring(0, i + 1);
        }
        return "";
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
}
