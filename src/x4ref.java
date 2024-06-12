package zvv.x4;

import java.io.DataInputStream;
import java.io.PrintWriter;

/**
 * EXFOR REFERENCE: x4ref object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-03-06
 * @since           2012-01-19
 *
 * Program:         x4ref.java
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

public class x4ref
extends c4sRef {
    static PrintWriter sysOut = null;
    static boolean extDebug = false;
    String Reference = "";
    String Publication = "";
    boolean unknownMonth = true;
    int year = 0;
    int month = 0;
    int day = 0;

    public static void main(String[] stringArray) {
        sysOut = new PrintWriter(System.out, true);
        System.out.println(" x4ref");
        System.out.println(" V.Zerkin, IAEA, Vienna, 2007-2018\n");
        x4dict.setDirOfArchivedDicts("x4dict/");
        x4dict567.init();
        x4ref.setHashDict567(x4dict567.hDict);
        x4ref x4ref2 = new x4ref("B,NEJTRONFIZ,,258,6104");
        x4ref2.println();
        System.out.println("...x4ref:Ref=[" + x4ref2.Ref + "]" + " stdFileName2Reference():[" + x4ref2.getStdReference() + "]" + " stdFileName=[" + x4ref2.stdFileName + "]");
        System.exit(0);
        x4ref2 = new x4ref("K,PR,93,917(F5),1954");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("(C,75KIEV,4,118,1975)=\n(R,INDC(CCP)-099,(4),118,1975)");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("R,AERE-R-72/73,1973");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("J,VAT/I,,(3/109),16,2017");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("J,ANE,17,12,657,1990");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("C,94GATLIN,1,393,1994");
        x4ref2.println();
        x4ref2 = new x4ref("C,94GATLIN,,393,1994");
        x4ref2.println();
        System.exit(0);
        System.out.println("");
        x4ref2 = new x4ref("J,BAS,42,(9),120,1978");
        x4ref2.println();
        x4ref2 = new x4ref("W,LARSON,8007");
        System.out.println("...x4ref:Name=[" + x4ref2.getName() + "]" + " page=[" + x4ref2.strPage + "]");
        x4ref2 = new x4ref("T,TELEZHNIKOV,91");
        x4ref2 = new x4ref("T,Rahman,101,200906");
        System.out.println("...x4ref:Name=[" + x4ref2.getName() + "]" + " page=[" + x4ref2.strPage + "]");
        x4ref2 = new x4ref("B,DEMIDOV,,70,86");
        System.out.println("...x4ref:Ref=[" + x4ref2.Ref + "]" + " Name:[" + x4ref2.getName() + "]" + " page=[" + x4ref2.strPage + "]");
        System.exit(0);
        x4ref2 = new x4ref("C,71KNOX,2,855,1971");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("A,2007NICE,,125(#740),2007");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("J,YK,1984,(3/57),17,198409");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.stdFileName + "]");
        x4ref2 = new x4ref("J,YK,,(3/57),17,198409");
        System.out.println("...x4ref:Publication=[" + x4ref2.stdFileName + "]");
        x4ref2 = new x4ref("J,YK,1,(50),3,1983");
        System.out.println("...x4ref:Publication=[" + x4ref2.stdFileName + "]");
        System.exit(0);
        x4ref2 = new x4ref("X,JINR-D15-2002-200,2002");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("P,UCAR-10062-84/1,8(3),84");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("R,CERN-76-13,304,1976");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("R,ANL/NPBTSTR-023,1989");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("A,2007NICE,,125(#740),2007");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("P,RPI-328-56,14,66");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("C,77KIEV,PROCEEDINGS");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("R,ANL/NPBTSTR-023,1989");
        x4ref2.println();
        System.out.println("...x4ref:Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("A,72KIEV,1,49(1),1972");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("R,KINR-,2007");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        x4ref2 = new x4ref("R,NEANDC(E)-202U,(5),1979");
        x4ref2.println();
        x4ref2 = new x4ref("R,NEANDC(E)-202U,(5),,1979");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("J,NSTS,2,(1),339,2002");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("B,LEVKOVSKIJ,,,1991");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("R,JINR-E6-2004-7,2004");
        x4ref2.println();
        x4ref2 = new x4ref("X,JINR-E6-2004-7,2004");
        x4ref2.println();
        x4ref2 = new x4ref("R,JINR-E6-2004,7,2004");
        x4ref2.println();
        System.out.println("...Publication=[" + x4ref2.Publication + "]");
        System.exit(0);
        x4ref2 = new x4ref("C,2008MACKIN,,(058),2008");
        x4ref2.println();
        x4ref2 = new x4ref("C,65ANTWERP,572(186),1965");
        x4ref2.println();
        x4ref2 = new x4ref("C,2008MACKIN,(077),2008");
        x4ref2.println();
        x4ref2 = new x4ref("C,66WASH,1,456,196603");
        x4ref2.println();
        x4ref2 = new x4ref("C,77KIEV,,197704");
        x4ref2.println();
        System.exit(0);
        x4ref2 = new x4ref("J,NP/A,734,(1),45,72");
        x4ref2.println();
        x4ref2 = new x4ref("J,NP/A,734,(1),45,7205");
        x4ref2.println();
        x4ref2 = new x4ref("J,NP/A,734,(1),45,720528");
        x4ref2.println();
        x4ref2 = new x4ref("J,ANE,8,43,198101");
        x4ref2.println();
        x4ref2 = new x4ref("R,ORNL-TM-5612,7610");
        x4ref2.println();
        x4ref2 = new x4ref("S,ORNL-TM-5612,197610");
        x4ref2.println();
        x4ref2 = new x4ref("W,LARSON,8007");
        x4ref2.println();
        x4ref2 = new x4ref("T,TELEZHNIKOV,91");
        x4ref2.println();
        x4ref2 = new x4ref("B,DEMIDOV,,70,86");
        x4ref2.println();
        x4ref2 = new x4ref("J,PR,82,(5),589,195106");
        x4ref2.println();
        x4ref2 = new x4ref("J,PR,53,321,326,1938");
        x4ref2.println();
        x4ref2 = new x4ref("J,PR/C,60,014604,1999");
        x4ref2.println();
        x4ref2 = new x4ref("R,INDC(NDS)-286-131,1993");
        x4ref2.println();
        x4ref2 = new x4ref("R,INDC(NDS)-286,131,1993");
        x4ref2.println();
        x4ref2 = new x4ref("R,INDC(JPN),173,U,9,1955");
        x4ref2.println();
        x4ref2 = new x4ref("C,2008MACKIN,,(037),2008");
        x4ref2.println();
        x4ref2 = new x4ref("C,65ANTWERP,572(186),1965");
        x4ref2.println();
        x4ref2 = new x4ref("C,2008MACKIN,(077),2008");
        x4ref2.println();
        x4ref2 = new x4ref("C,66WASH,1,456,196603");
        x4ref2.println();
        x4ref2 = new x4ref("C,77KIEV,,197704");
        x4ref2.println();
        String string = "org.gjt.mm.mysql.Driver";
        String string2 = "jdbc:mysql://nds121.iaea.org/x4mysql5nds";
        String string3 = "w#e##bu#sr".replaceAll("#", "");
        String string4 = "we#bu#s#r@1#23".replaceAll("#", "");
        String string5 = "NNDC";
        X4sRef2Web x4sRef2Web = new X4sRef2Web(string, string2, string3, string4, string5);
        x4sRef2Web.readDictRef2Web(string, string2, string3, string4);
        x4ref2 = new x4ref("R,INDC(NDS)-103,40,197905");
        x4ref2 = new x4ref("R,INDC(NDS)-4,40,197905");
        x4ref2.println();
        x4ref x4ref3 = x4ref2;
        String string6 = x4sRef2Web.getLink(x4ref3);
        System.out.println("...getReferenceHtml:aRefBegin=[" + string6 + "]");
        x4ref2 = new x4ref("R,IAEA-186,(2),141,197611");
        x4ref2.println();
        x4ref2 = new x4ref("J,ANE,13,11,627,1986");
        x4ref2.println();
        x4ref2 = new x4ref("J,YK,1984,(3/57),17,198409");
        x4ref2.println();
        x4ref2 = new x4ref("J,YK,1984,3/57,17,198409");
        x4ref2.println();
        x4ref2 = new x4ref("J,PR/C,47,237,9301");
        x4ref2.println();
        x4ref2 = new x4ref("J,PR/C,47,(10),237,9301");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,1,40,JA7,1956");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,13,AG4,564,1968");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,44,(5),72(JC03),199910");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,44,5,72(JC03),199910");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,27,(EC2),727,1982");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,BAP,29,(P2.100),1117,8409");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,YF,14,(5),935,1971");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("C,83KIEV,3,290,1984");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,YK,1984,(3/57),17,198409");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,YK,3/57,15,8409");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,CST,9,(2),113,197505");
        x4ref2.println();
        System.out.println("");
        x4ref2 = new x4ref("J,CST,47,517,2013");
        x4ref2.println();
        System.out.println("");
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4ref() {
    }

    x4ref(String string) {
        this.Reference = string = this.get1stReference(string);
        this.Publication = this.reference2publication(string);
        super.set_c4sRef(this.Publication, string);
    }

    public String get1stReference(String string) {
        String string2 = string;
        string2 = string2.replaceAll("\r", "");
        int n = (string2 = string2.replaceAll("\n", "")).indexOf(")=(");
        if (n > 0) {
            string2 = string2.substring(0, n);
        }
        if (string2.startsWith("(")) {
            string2 = string2.substring(1);
        }
        return string2;
    }

    public void dox4ref(String string) {
        this.Reference = string = this.get1stReference(string);
        this.Publication = this.reference2publication(string);
        super.set_c4sRef(this.Publication, string);
    }

    public void println() {
        if (sysOut != null) {
            sysOut.println("_____x4ref:Reference=[" + this.Reference + "]" + "\n\t___Publication=[" + this.Publication + "]");
        }
        String string = super.getShortRef();
        if (sysOut != null) {
            sysOut.println("..shortRef:[" + string + "]" + "\n\t___stdFileName=" + this.stdFileName);
        }
    }

    public String reference2publication(String string) {
        int n;
        int n2;
        char c;
        String string2 = string;
        String string3 = this.getDateFromRef(string);
        if (extDebug) {
            sysOut.println("\n.....reference2publication:str=[" + string + "]");
        }
        if (extDebug) {
            sysOut.println("\n.....reference2publication:sdat=[" + string3 + "]");
        }
        if (string == null) {
            string = " ";
        }
        if (string.equals("")) {
            string = " ";
        }
        if ((c = string.toUpperCase().charAt(0)) == 'B' || c == 'C') {
            c = 'C';
        } else if (c == 'P' || c == 'R' || c == 'S' || c == 'X') {
            c = 'R';
        } else if (c == 'A') {
            c = 'C';
        } else if (c == 'K') {
            c = 'J';
        }
        if (extDebug) {
            sysOut.println(".....reference2publication:ch=[" + c + "]");
        }
        String string4 = "";
        if (c == 'R') {
            n2 = string.length();
            for (int i = 0; i < n2; ++i) {
                if (i == 13) {
                    if (extDebug) {
                        sysOut.println(".....reference2publication:11+2:[" + string.substring(1, i) + "]");
                    }
                    if (extDebug) {
                        sysOut.println(".....reference2publication:11+3:[" + string.substring(i) + "]");
                    }
                    string4 = string.substring(i).startsWith("-") ? c + string.substring(1, i + 1) : c + string.substring(1, i);
                } else {
                    if (string.charAt(i) != '-') continue;
                    char c2 = i < n2 ? (char)string.charAt(i + 1) : (char)'(';
                    if (extDebug) {
                        sysOut.println(".....reference2publication:ch1=[" + c2 + "]");
                    }
                    if (!Character.isDigit(c2) && c2 != '(' && c2 != 44) continue;
                    string4 = c + string.substring(1, i + 1);
                }
                break;
            }
        } else {
            string4 = c == 'T' ? c + "" : (c == 'W' ? c + "" : ((n = string.indexOf(",", 2)) > 0 ? c + string.substring(1, n) : c + ""));
        }
        if (extDebug) {
            sysOut.println(".....reference2publication:ref=[" + string4 + "]");
        }
        n2 = string4.length();
        String string5 = string.substring(n2);
        if (extDebug) {
            sysOut.println(".....reference2publication:rest[" + string5 + "]");
        }
        n = string5.lastIndexOf(",");
        if (extDebug) {
            sysOut.println(".....reference2publication:ind=" + n + "");
        }
        if (n > 0) {
            string5 = string5.substring(0, n);
        }
        if (n == 0) {
            string5 = "";
        }
        if (extDebug) {
            sysOut.println(".....reference2publication:rest[" + string5 + "]");
        }
        string2 = string4 + ":" + string5 + ":" + string3;
        if (extDebug) {
            sysOut.println(".....reference2publication:out[" + string2 + "]");
        }
        return string2;
    }

    public String getDateFromRef(String string) {
        String string2 = "";
        boolean bl = false;
        if (string == null) {
            return string2;
        }
        int n = string.lastIndexOf(",");
        if (n <= 0) {
            return string2;
        }
        this.unknownMonth = false;
        if (string.startsWith("C") && string.length() > 2 && Character.isDigit(string.charAt(2))) {
            bl = true;
        }
        if (!bl) {
            string = string.substring(n + 1);
        } else {
            String string3 = "";
            for (int i = 2; i < string.length() && Character.isDigit(string.charAt(i)); ++i) {
                string3 = string3 + string.charAt(i);
            }
            string = string3;
        }
        int n2 = string.length();
        if (n2 <= 2) {
            string = "19" + string + "0000";
            this.unknownMonth = true;
        } else if (n2 <= 4) {
            if (string.startsWith("19")) {
                string = string + "0000";
                this.unknownMonth = true;
            } else if (string.startsWith("20")) {
                string = string + "0000";
                this.unknownMonth = true;
            } else {
                string = "19" + string + "00";
            }
        } else if (n2 <= 6) {
            string = string.startsWith("19") ? string + "00" : (string.startsWith("20") ? string + "00" : "19" + string);
        }
        int n3 = this.str2int(string, 0);
        this.year = n3 / 10000;
        this.month = n3 % 10000 / 100;
        this.day = n3 % 100;
        n3 = this.year;
        if (this.month > 0) {
            n3 = n3 * 100 + this.month;
            if (this.day > 0) {
                n3 = n3 * 100 + this.day;
            }
        }
        string2 = "" + n3;
        return string2;
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

    public String myStrReplaceCRLF(String string) {
        string = x4ref.myStrReplace(string, "\n\r", " ");
        string = x4ref.myStrReplace(string, "\r\n", " ");
        string = x4ref.myStrReplace(string, "\n", " ");
        string = x4ref.myStrReplace(string, "\r", " ");
        string = x4ref.myStrReplace(string, "  ", " ");
        string = x4ref.myStrReplace(string, "  ", " ");
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
