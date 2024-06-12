package zvv.x4;

import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR-CINDA Reference object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-03-06
 * @since           2012-10-19
 *
 * Program:         c4sRef.java
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

public class c4sRef {
    static Hashtable hDict567 = null;
    String Publication = "";
    String RefUnix = "";
    String stdFileName = "x";
    static final String stdFileNameComma = ".";
    static final String stdFileNameSlash = "_";
    String Reference = "";
    String tRefDate = "";
    String RefType = "";
    String Ref = "";
    int iRefYear = 0;
    int cRefYear = 0;
    String tRefYear = "";
    char chRefDic = (char)32;
    char chRef0 = (char)32;
    String strPage = "";
    String strVol = "";
    String strNumber = "";
    String strIssue = "";
    String strPart = "";
    String strPaper = "";
    String strLabelPage = "Page";
    String strLabelVol = "Vol.";
    String strLabelNumber = "Number";
    String strLabelIssue = "Issue";
    String strLabelPart = "Part";
    String strLabelPaper = "Paper";
    String strShortLabelPage = "p.";
    String strShortLabelVol = "Vol.";
    String strShortLabelNumber = "No.";
    String strShortLabelIssue = "Issue.";
    String strShortLabelPart = "Part.";
    String strShortLabelPaper = "Paper.";
    boolean flagPage = false;
    boolean flagVol = false;
    boolean flagNumber = false;
    boolean flagIssue = false;
    boolean flagPart = false;
    boolean flagPaper = false;
    String strDOI = "";
    String strNSR = "";
    String NSRCodenCode = null;
    String NSRCoden = null;
    String NSRReference = null;
    String strBegin = "";
    static Hashtable hJournalsWithNumber = new Hashtable();
    static Hashtable hJournalsWithNumber1 = new Hashtable();
    static Hashtable hConfIgnoreVol = new Hashtable();
    boolean breakets = false;

    public static void main(String[] stringArray) {
        System.out.println(" c4sref");
        System.out.println(" V.Zerkin, IAEA, Vienna, 2007-2023\n");
        String string = "J,NSTS,2,(1),339,2002";
        String string2 = "J,NSTS:,2,(1),339:2002";
        string = "J,NSTS,2,339,2002";
        string2 = "J,NSTS:,2,339:2002";
        string2 = "R,INDC(CCP)-:133:197905";
        string = "R,INDC(CCP)-133";
        string2 = "R,NEANDC(E)-:202U,(5):1979";
        string = "R,NEANDC(E)-202U,(5),1979";
        string2 = "R,KINR-::1979";
        string = "R,KINR-,1979";
        string = "P,UCRL-ID-115738,5-1,1993";
        string2 = "R,UCRL-ID-:115738,5-1:1993";
        string = "A,2007NICE,,125(#740),2007";
        string2 = "C,2007NICE:,,125(#740):2007";
        string2 = "A,2007NICE:,,125(#740):2007";
        string = "R,ANL/NPBTSTR-023,1989";
        string2 = "R,ANL/NPBTSTR:-023:1989";
        string = "R,EUR-3963,(SUPP.1),196810";
        string2 = "R,EUR-:3963,(SUPP.1):196810";
        string = "R,EPRI-NP-3436,(3),1984";
        string2 = "R,EPRI-NP-:3436,(3):1984";
        string = "P,NEANDC(E)-182,8,7708";
        string2 = "R,NEANDC(E)-:182,8:197708";
        string = "X,JINR-D15-2002-200,2002";
        string2 = "X,JINR-D15-2002-200::2002";
        c4sRef c4sRef2 = new c4sRef(string2, string);
        System.out.println("...Ref=[" + string + "]");
        System.out.println("...Pub=[" + string2 + "]");
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "]");
        System.out.println("...shoetRef=[" + c4sRef2.getShortRef() + "]\n");
        System.exit(0);
        string = "B,MARION,4,(1),157,1960";
        string2 = "B,MARION:,4,(1),157:1960";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        System.exit(0);
        c4sRef2 = new c4sRef("R,JINR-E6-:2004-7:2004", "R,JINR-E6-2004-7,2004");
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        System.exit(0);
        c4sRef2 = new c4sRef("C,2008MACKIN:,,(037):2008", "C,2008MACKIN,,(037),2008");
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        c4sRef2 = new c4sRef("C,65ANTWERP:,572(186):1965", "C,65ANTWERP,572(186),1965");
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        c4sRef2 = new c4sRef("C,66WASH:,1,456:1966", "C,66WASH,1,456,196603");
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        System.exit(0);
        string = "J,PR/C,47,237,9301";
        string2 = "J,PR/C:,47,237:199301";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "J,NP/A,734,(1),45,2004";
        string2 = "J,NP/A:,734,(1),45:2004";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "C,67KHARKOV,,(56),196702";
        string2 = "C,67KHARKOV:,,(56):196702";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "C,66WASH,1,456,196603";
        string2 = "C,66WASH:,1,456:196603";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "B,MARION,4,(1),157,1960";
        string2 = "B,MARION:,4,(1),157:1960";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "C,77KIEV,,197704";
        string2 = "C,77KIEV:,:1977";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
        string = "C,77KIEV,9,197704";
        string2 = "C,77KIEV:,9:197704";
        c4sRef2 = new c4sRef(string2, string);
        System.out.println("...stdFileName=[" + c4sRef2.stdFileName + "] " + c4sRef2.getShortRef() + "\n");
    }

    static void readCNoVol(Hashtable hashtable) {
    }

    static void readJWN1(Hashtable hashtable) {
        hashtable.put("J,AE/S", "[RUS] Atomnaya Energiya, Supplement                   ");
        hashtable.put("J,IZK", "[KAS] Izvestiya Akademii Nauk KazSSSR,Ser.Fiz.-Mat.   ");
        hashtable.put("J,IZU", "[UZ] Izvestiya Akademii Nauk UzSSSR,Ser.Fiz.-Mat.     ");
        hashtable.put("J,KSO", "[RUS] Kratkie Soobscheniya OIYaI (JINR Rapid Commun.) ");
        hashtable.put("J,PTE", "[CCP] Pribory i Tekhnika Eksperimenta                 ");
        hashtable.put("J,SJA/S", "[USA] Soviet Atomic Energy, Supplement                ");
        hashtable.put("J,VAT/I", "[UKR] Vopr.Atomn.Nauki i Tekhn.,Ser.Yad..Fiz..Issledo.");
        hashtable.put("J,VAT/O", "[RUS] Voprosy Atomnoy Nauki i Tekhniki, Seriya Obshch.");
        hashtable.put("J,VAT/Y", "[RUS] Voprosy Atomn.Nauki i Tekhniki,Ser.Fiz.Yad.Reak.");
        hashtable.put("J,VMU", "[RUS] Vestnik Moskovskogo Univ., Seriya Fiz.Astron.   ");
        hashtable.put("J,VTYF", "[RUS] Voprosy Teoreticheskoy i Yadernoy Fiziki        ");
        hashtable.put("J,YK", "[RUS] Vop. At.Nauki i Tekhn.,Ser.Yadernye Konstanty   ");
    }

    static void readJWN2(Hashtable hashtable) {
        hashtable.put("J,AE", "[RUS] Atomnaya Energiya                             ");
        hashtable.put("J,CNP", "[CPR] Chinese J.of Nuclear Physics (Beijing).       ");
        hashtable.put("J,YK", "[RUS] Vop. At.Nauki i Tekhn.,Ser.Yadernye Konstanty ");
    }

    public String getStdReference() {
        return c4sRef.stdFileName2Reference(this.stdFileName);
    }

    public static String stdFileName2Reference(String string) {
        String string2 = c4sRef.myStrReplace(string, stdFileNameSlash, "/");
        if (string.startsWith("C") && string.length() > 2 && !Character.isDigit(string.charAt(2))) {
            string2 = "B" + string2.substring(1);
        }
        return string2;
    }

    c4sRef() {
    }

    c4sRef(String string, String string2) {
        this.set_c4sRef(string, string2);
    }

    public void set_c4sRef(String string, String string2) {
        String string3;
        int n;
        if (string == null) {
            string = " ";
        }
        if (string2 == null) {
            string2 = " ";
        }
        this.Publication = string;
        this.Reference = string2;
        String[] stringArray = this.mySplitStr(string, ":");
        String string4 = "";
        if (string.startsWith("R") && stringArray.length >= 2) {
            if (stringArray[1].startsWith("-")) {
                stringArray[1] = stringArray[1].substring(1);
            }
            if (!stringArray[0].endsWith("-")) {
                stringArray[0] = stringArray[0] + "-";
            }
        }
        for (n = 0; n < stringArray.length; ++n) {
            String string5 = stringArray[n];
            if (n == 0) {
                this.Ref = string5;
            }
            if (n == 1) {
                string4 = string5;
            }
            if (n != 2) continue;
            this.tRefDate = string5;
        }
        this.tRefYear = this.tRefDate;
        if (this.tRefDate.length() > 4) {
            this.tRefYear = this.tRefDate.substring(0, 4);
        }
        this.cRefYear = this.iRefYear = this.str2int(this.tRefYear, 0);
        char c = this.getFirstCharUpperCase(string2);
        this.RefType = new String(new char[]{c});
        if (string4.startsWith(",")) {
            string4 = string4.substring(1);
        }
        String string6 = string4;
        if (string.startsWith("R")) {
            // empty if block
        }
        string4 = c4sRef.myStrReplace(string4, "#", "");
        stringArray = this.mySplitStr(string4, ",");
        int n2 = stringArray.length;
        this.chRefDic = c = this.getFirstCharUpperCase(string);
        this.chRef0 = this.getFirstCharUpperCase(string2);
        this.setFlagsAndLabels(c);
        if ((c == 'C' || c == 'A') && (string3 = this.getStartingDigits(this.Publication.substring(2))).length() > 0) {
            if (string3.length() == 2) {
                string3 = "19" + string3;
            }
            if (!this.tRefYear.equals(string3)) {
                this.iRefYear = this.cRefYear = this.str2int(string3, 0);
                this.tRefYear = "" + this.cRefYear;
            }
        }
        switch (c) {
            case 'A': 
            case 'B': 
            case 'C': {
                this.strPage = this.extractStrPageConf(string4);
                this.strVol = this.strBegin;
                if (n2 <= 0) {
                    this.strVol = "";
                    this.strPage = "";
                    break;
                }
                if (n2 == 1) {
                    this.strVol = "";
                    this.strPage = stringArray[0];
                    break;
                }
                if (n2 == 2) {
                    this.strVol = stringArray[0];
                    this.strPage = stringArray[1];
                    break;
                }
                if (n2 != 3) break;
                this.strVol = stringArray[0];
                this.strPart = stringArray[1];
                this.strPage = stringArray[2];
                if (!this.strPart.startsWith("(") || !this.strPart.endsWith(")")) break;
                this.strPart = this.strPart.substring(1, this.strPart.length() - 1);
                break;
            }
            case 'J': {
                this.strPage = this.extractStrPage(string4);
                string3 = (String)hJournalsWithNumber1.get(this.Ref);
                if (string3 != null && n2 == 2) {
                    n2 = 3;
                    String[] stringArray2 = new String[]{"" + this.iRefYear, stringArray[0], stringArray[1]};
                    stringArray = stringArray2;
                }
                if (n2 == 1) {
                    this.strVol = "";
                    this.strPage = stringArray[0];
                } else if (n2 == 2) {
                    this.strVol = stringArray[0];
                    this.strPage = stringArray[1];
                } else if (n2 == 3) {
                    this.strVol = stringArray[0];
                    this.strIssue = stringArray[1];
                    this.strPage = stringArray[2];
                    if (this.strIssue.startsWith("(") && this.strIssue.endsWith(")")) {
                        this.strIssue = this.strIssue.substring(1, this.strIssue.length() - 1);
                    }
                }
                if (!this.Ref.equals("J,BAP")) break;
                if (n2 == 3) {
                    if (stringArray[1].startsWith("(")) {
                        stringArray[1] = stringArray[1].substring(1);
                    }
                    if (stringArray[1].endsWith(")")) {
                        stringArray[1] = stringArray[1].substring(0, stringArray[1].length() - 1);
                    }
                    if (this.strStartsWithLetter(stringArray[2])) {
                        this.strVol = stringArray[0];
                        this.strIssue = "";
                        this.strPage = stringArray[1] + "(" + stringArray[2] + ")";
                    } else if (this.strStartsWithLetter(stringArray[1])) {
                        this.strVol = stringArray[0];
                        this.strIssue = "";
                        this.strPage = stringArray[2] + "(" + stringArray[1] + ")";
                    }
                }
                if (n2 != 4) break;
                this.strVol = stringArray[0];
                this.strIssue = stringArray[1];
                this.strPage = stringArray[2] + "(" + stringArray[3] + ")";
                break;
            }
            case 'P': 
            case 'R': 
            case 'S': 
            case 'X': {
                this.breakets = false;
                if (n2 == 1) {
                    this.strNumber = stringArray[0];
                    break;
                }
                this.strPage = this.extractStrPage(string4);
                this.strNumber = this.strBegin;
                if (n2 == 3) {
                    this.strNumber = stringArray[0];
                    this.strVol = stringArray[1];
                    this.strPage = stringArray[2];
                    break;
                }
                if (n2 == 2) {
                    this.strPage = this.extractStrPage(string4);
                    this.strNumber = this.strBegin;
                    this.strNumber = stringArray[0];
                    if (!this.breakets) break;
                    this.strVol = "(" + this.strPage + ")";
                    this.strPage = "";
                    break;
                }
                this.strPage = this.extractStrPage(string4);
                this.strNumber = this.strBegin;
                break;
            }
            case '0': 
            case '4': {
                this.strNumber = string4;
                n = string2.indexOf(stdFileNameComma);
                if (n <= 0) break;
                this.strPage = string2.substring(n + 1);
                break;
            }
            case '3': {
                this.strNumber = string6;
                break;
            }
            case 'T': 
            case 'W': {
                this.strVol = string4;
                if (string4.length() > 1) {
                    if (string4.indexOf(",") >= 0) {
                        this.strPage = this.extractStrPage(string4);
                        this.strVol = this.strBegin;
                    }
                    if (this.strVol.length() > 1) {
                        this.strVol = this.strVol.substring(0, 1) + this.strVol.substring(1).toLowerCase();
                    }
                }
                this.strLabelVol = "Name";
            }
        }
        this.RefUnix = this.Publ2UnixRef(string);
    }

    public static void setHashDict567(Hashtable hashtable) {
        hDict567 = hashtable;
    }

    String Publ2UnixRef(String string) {
        int n;
        String string2 = "";
        if (string == null) {
            string = "::";
        }
        if (string.length() < 2) {
            string = this.strpad(string, 2);
        }
        if ((n = string.charAt(0)) == 65) {
            n = 67;
        }
        if (n == 88) {
            n = 82;
        }
        if (n == 75) {
            n = 74;
        }
        int n2 = string.length();
        int n3 = string.lastIndexOf(":");
        if (n3 > 0 && n2 > n3 + 5) {
            string = string.substring(0, n3 + 5);
        }
        this.stdFileName = string;
        switch (n) {
            case 67: 
            case 74: 
            case 84: {
                n3 = string.lastIndexOf(":");
                if (n3 <= 0) break;
                string = string.substring(0, n3);
            }
        }
        if (n == 74) {
            n3 = string.indexOf(":");
            int n4 = string.indexOf("(", n3);
            int n5 = string.indexOf("),", n3);
            if (n4 > 0 && n5 > n4) {
                string = string.substring(0, n4) + string.substring(n5 + 2);
            }
        }
        if ((n3 = string.indexOf(":")) > 0) {
            string = string.substring(0, n3) + string.substring(n3 + 1);
        }
        this.stdFileName = string;
        switch (n) {
            case 67: 
            case 74: 
            case 84: {
                this.stdFileName = string + "," + this.iRefYear;
            }
        }
        if (n == 67) {
            this.stdFileName = this.Ref;
            this.stdFileName = hConfIgnoreVol.get(this.Ref) != null ? this.stdFileName + "," : this.stdFileName + "," + this.strVol;
            if (!this.strPart.equals("")) {
                this.stdFileName = this.stdFileName + ",(" + this.strPart + ")";
            }
            this.stdFileName = this.stdFileName + "," + this.strPage;
            this.stdFileName = this.stdFileName + "," + this.iRefYear;
        }
        if (n == 82) {
            this.stdFileName = this.Ref;
            if (!this.strNumber.equals("")) {
                this.stdFileName = !this.Ref.endsWith("-") ? this.stdFileName + "-" + this.strNumber : this.stdFileName + this.strNumber;
            }
            if (!this.strVol.equals("")) {
                this.stdFileName = !this.strVol.startsWith("(") ? this.stdFileName + ",(" + this.strVol + ")" : this.stdFileName + "," + this.strVol;
            }
            if (!this.strPage.equals("")) {
                this.stdFileName = this.stdFileName + "," + this.strPage;
            }
            this.stdFileName = this.stdFileName + "," + this.iRefYear;
        }
        if (n == 74) {
            String string3 = (String)hJournalsWithNumber.get(this.Ref);
            if (string3 == null) {
                this.stdFileName = this.Ref;
                this.stdFileName = this.stdFileName + "," + this.strVol;
                this.stdFileName = this.stdFileName + "," + this.strPage;
                this.stdFileName = this.stdFileName + "," + this.iRefYear;
            } else {
                if (this.Ref.equals("J,YK") && this.strVol.equals("" + this.iRefYear)) {
                    this.strVol = "";
                }
                this.stdFileName = this.Ref;
                this.stdFileName = this.stdFileName + "," + this.strVol;
                if (!this.strIssue.equals("")) {
                    this.stdFileName = this.stdFileName + ",(" + this.strIssue + ")";
                }
                this.stdFileName = this.stdFileName + "," + this.strPage;
                this.stdFileName = this.stdFileName + "," + this.iRefYear;
            }
        }
        this.stdFileName = c4sRef.myStrReplace(this.stdFileName, ":", ",");
        this.stdFileName = c4sRef.myStrReplace(this.stdFileName, "/", stdFileNameSlash);
        this.stdFileName = c4sRef.myStrReplace(this.stdFileName, "&", "-");
        this.stdFileName = c4sRef.myStrReplace(this.stdFileName, "'", "`");
        this.stdFileName = c4sRef.myStrReplace(this.stdFileName, " ", "");
        if (this.stdFileName.startsWith("A")) {
            this.stdFileName = "C" + this.stdFileName.substring(1);
        }
        if (this.stdFileName.startsWith("X")) {
            this.stdFileName = "R" + this.stdFileName.substring(1);
        }
        return string;
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

    public void setFlagsAndLabels(char c) {
        this.strLabelPage = "Page";
        this.strLabelVol = "Vol";
        this.strLabelNumber = "Number";
        this.strLabelIssue = "Issue";
        this.strLabelPart = "Part";
        this.strShortLabelPage = "p.";
        this.strShortLabelVol = "Vol.";
        this.strShortLabelNumber = "No.";
        this.strShortLabelIssue = "Issue.";
        this.strShortLabelPart = "Part.";
        this.flagPage = false;
        this.flagVol = false;
        this.flagNumber = false;
        this.flagIssue = false;
        this.flagPart = false;
        switch (c) {
            case 'A': 
            case 'B': 
            case 'C': {
                if (!this.strPage.equals("")) {
                    this.flagPage = true;
                }
                if (!this.strVol.equals("")) {
                    this.flagVol = true;
                }
                if (this.strPart.equals("")) break;
                this.flagPart = true;
                break;
            }
            case 'J': 
            case 'K': {
                this.flagPage = true;
                this.flagVol = true;
                this.flagIssue = true;
                break;
            }
            case 'P': 
            case 'R': 
            case 'S': 
            case 'X': {
                this.flagPage = true;
                this.flagNumber = true;
                break;
            }
            case '0': 
            case '4': {
                this.strLabelNumber = "Entry";
                this.strLabelPage = "Subent";
                this.strShortLabelNumber = "AN.";
                this.strShortLabelPage = "SAN.";
                this.flagPage = true;
                this.flagNumber = true;
                break;
            }
            case '3': {
                this.strLabelNumber = "Mat+";
                this.strShortLabelNumber = "Mat.";
                this.flagNumber = true;
                break;
            }
            case 'T': 
            case 'W': {
                this.strLabelVol = "Name";
                this.strShortLabelVol = "Name.";
                this.flagVol = true;
            }
        }
    }

    public String extractStrPage(String string) {
        String string2 = "";
        this.breakets = false;
        int n = string.lastIndexOf(",");
        if (n >= 0) {
            string2 = string.substring(n + 1);
            this.strBegin = string.substring(0, n);
        } else {
            string2 = string;
            this.strBegin = "";
        }
        if (string2.startsWith("(") && string2.endsWith(")")) {
            string2 = string2.substring(1);
            int n2 = string2.length();
            string2 = string2.substring(0, n2 - 1);
            this.breakets = true;
        }
        return string2;
    }

    public String extractStrPageConf(String string) {
        String string2 = "";
        int n = string.lastIndexOf(",");
        if (n >= 0) {
            string2 = string.substring(n + 1);
            this.strBegin = string.substring(0, n);
        } else {
            string2 = string;
            this.strBegin = "";
        }
        return string2;
    }

    public char getFirstCharUpperCase(String string) {
        if (string == null) {
            return ' ';
        }
        if ((string = string.trim()).length() <= 0) {
            return ' ';
        }
        char c = Character.toUpperCase(string.charAt(0));
        return c;
    }

    public String getVolume() {
        if (this.chRefDic == 'T') {
            return "";
        }
        if (this.chRefDic == 'W') {
            return "";
        }
        return this.strVol;
    }

    public String getName() {
        String string = "";
        if (this.chRefDic == 'T') {
            return this.strVol;
        }
        if (this.chRefDic == 'W') {
            return this.strVol;
        }
        if (this.chRef0 == 'B' && this.Ref.length() > 2) {
            string = this.Ref.substring(2).toUpperCase();
            if (string.length() > 1) {
                string = string.substring(0, 1) + string.substring(1).toLowerCase();
            }
            return string;
        }
        return "";
    }

    public String getShortRef() {
        String string = "";
        String string2 = null;
        String string3 = "";
        boolean bl = false;
        if (!this.tRefYear.equals("")) {
            string3 = " (" + this.tRefYear + ")";
        }
        if (this.chRefDic == 'T') {
            return "Thesis: " + this.strVol + string3;
        }
        if (this.chRefDic == 'W') {
            return "Priv.Comm: " + this.strVol + string3;
        }
        String string4 = this.getStrVolPage();
        if (string4.startsWith("Name.")) {
            string4 = string4.substring(5);
        }
        if (hDict567 != null) {
            string2 = (String)hDict567.get(this.Ref);
            if (string2 == null) {
                string2 = "?";
                if (this.Ref != null && this.Ref.length() > 2) {
                    string2 = string2 + this.Ref.substring(2);
                }
            }
        } else if (this.Ref != null && this.Ref.length() > 2 && (string2 = this.Ref.substring(2)).endsWith("-")) {
            string2 = string2.substring(0, string2.length() - 1);
        }
        if (string2 != null) {
            string4 = string2 + ", " + string4.trim();
        }
        string4 = this.strTypeExpanded() + ": " + string4;
        string = string4 + string3;
        return string;
    }

    public String getStrVolPage() {
        String string = "";
        int n = 0;
        if (this.chRefDic == 'R') {
            if (!this.strNumber.equals("")) {
                string = this.addStrVolPage(string, n++, this.strShortLabelNumber + this.strNumber);
            }
            if (!this.strVol.equals("")) {
                string = this.addStrVolPage(string, n++, this.strShortLabelVol + this.strVol);
            }
        } else {
            if (!this.strVol.equals("")) {
                string = this.addStrVolPage(string, n++, this.strShortLabelVol + this.strVol);
            }
            if (!this.strNumber.equals("")) {
                string = this.addStrVolPage(string, n++, this.strShortLabelNumber + this.strNumber);
            }
        }
        if (!this.strIssue.equals("")) {
            string = this.addStrVolPage(string, n++, this.strShortLabelIssue + this.strIssue);
        }
        if (!this.strPart.equals("")) {
            string = this.addStrVolPage(string, n++, this.strShortLabelPart + this.strPart);
        }
        if (!this.strPage.equals("")) {
            string = this.addStrVolPage(string, n++, this.strShortLabelPage + this.strPage);
        }
        return string;
    }

    public String addStrVolPage(String string, int n, String string2) {
        String string3 = string;
        if (n > 0) {
            string3 = string3 + ", ";
        }
        string3 = string3 + string2;
        return string3;
    }

    public String strTypeExpanded() {
        char c = this.getTypeChar(this.Reference);
        if (c == 'A') {
            return "Abst";
        }
        if (c == 'K') {
            return "Jour";
        }
        if (c == 'J') {
            return "Jour";
        }
        if (c == 'C') {
            return "Conf";
        }
        if (c == 'S') {
            return "Conf";
        }
        if (c == 'X') {
            return "Prep";
        }
        if (c == 'R') {
            return "Rept";
        }
        if (c == 'P') {
            return "Prog";
        }
        if (c == 'B') {
            return "Book";
        }
        if (c == 'T') {
            return "Diss";
        }
        if (c == 'W') {
            return "Priv";
        }
        if (c == '0') {
            return "Data";
        }
        if (c == '3') {
            return "Data";
        }
        if (c == '4') {
            return "Data";
        }
        return "[" + c + "]";
    }

    public char getTypeChar(String string) {
        int n = this.getFirstCharUpperCase(string);
        if (n == 67 && string.length() > 2 && !Character.isDigit(string.charAt(2))) {
            n = 66;
        }
        return (char)n;
    }

    public String getCountry() {
        x4dict567 x4dict5672 = x4dict567.findinx4dict(this.Ref);
        if (x4dict5672 != null) {
            return x4dict5672.Country;
        }
        return "";
    }

    public String getStartingDigits(String string) {
        char c;
        String string2 = "";
        if (string == null) {
            string = "";
        }
        int n = string.length();
        for (int i = 0; i < n && Character.isDigit(c = string.charAt(i)); ++i) {
            string2 = string2 + c;
        }
        return string2;
    }

    public boolean strStartsWithLetter(String string) {
        int n = string.length();
        if (n <= 0) {
            return false;
        }
        char c = string.charAt(0);
        return Character.isLetter(c);
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
            String string2 = string.substring(0, n);
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
        int n2;
        if (string == null) {
            string = "";
        }
        if ((n2 = string.length()) == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = string.substring(0, n);
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
            n2 = Integer.parseInt(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    static {
        hJournalsWithNumber = new Hashtable();
        hJournalsWithNumber1 = new Hashtable();
        c4sRef.readJWN1(hJournalsWithNumber1);
        c4sRef.readJWN1(hJournalsWithNumber);
        c4sRef.readJWN2(hJournalsWithNumber);
        hConfIgnoreVol = new Hashtable();
        c4sRef.readCNoVol(hConfIgnoreVol);
    }
}
