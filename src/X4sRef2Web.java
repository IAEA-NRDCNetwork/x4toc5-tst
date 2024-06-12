package zvv.x4;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * EXFOR Reference to Web-link
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2022-06-23
 * @since           2009-07-22
 *
 * Program:         X4sRef2Web.java
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

public class X4sRef2Web {
    static Vector ref2webDict = null;
    static String ref2webPostID = "NDS";
    String strError = "";
    static PrintWriter sysOut = new PrintWriter(System.out);
    String aLinkBegin = "";
    String aLinkEnd = "";
    String aHref = "";
    String aWindowName = "Journal_window";

    public static void main(String[] stringArray) {
        String string = "org.gjt.mm.mysql.Driver";
        String string2 = "jdbc:mysql://nds121.iaea.org/x4mysql5nds";
        String string3 = "w#e##bu#sr".replaceAll("#", "");
        String string4 = "we#bu#s#r@1#23".replaceAll("#", "");
        System.out.println(" V.Zerkin, IAEA, Vienna, 2014\n");
        System.out.println("args.length: " + stringArray.length + "\n");
        PrintWriter printWriter = new PrintWriter(System.out, true);
        X4sRef2Web.setPrintWriter(printWriter);
        X4sRef2Web x4sRef2Web = new X4sRef2Web(string, string2, string3, string4, ref2webPostID);
        c4sRef c4sRef2 = null;
        String string5 = "";
        String string6 = "";
        String string7 = "";
        String string8 = "";
        String string9 = "";
        String string10 = "J,NSTS,2,393,2002";
        String string11 = "J,NSTS:,2,393:2002";
        string10 = "R,IAEA-491,115,1989";
        string11 = "R,IAEA-:491,115:198901";
        c4sRef2 = new c4sRef(string11, string10);
        String string12 = X4sRef2Web.getRef2WebTemplate(c4sRef2.Ref);
        if (string12 != null) {
            System.out.println("...getReferenceHtml-5\n c4ref.Ref      [" + c4sRef2.Ref + "]\n" + " c4ref.iRefYear [" + c4sRef2.iRefYear + "]\n" + " c4ref.strVol   [" + c4sRef2.strVol + "]\n" + " c4ref.strIssue [" + c4sRef2.strIssue + "]\n" + " c4ref.strPage  [" + c4sRef2.strPage + "]\n" + " getRef2WebTemplate [" + string12 + "]\n" + "\n");
        }
        if ((string12 = x4sRef2Web.getLink(c4sRef2)) != null) {
            System.out.println("...getReferenceHtml-5\n c4ref.Ref      [" + c4sRef2.Ref + "]\n" + " c4ref.iRefYear [" + c4sRef2.iRefYear + "]\n" + " c4ref.strVol   [" + c4sRef2.strVol + "]\n" + " c4ref.strIssue [" + c4sRef2.strIssue + "]\n" + " c4ref.strPage  [" + c4sRef2.strPage + "]\n" + " Link           [" + string12 + "]\n" + "\n");
        }
    }

    X4sRef2Web(String string, String string2, String string3, String string4, String string5) {
        this.readDictRef2Web(string, string2, string3, string4);
        if (!string5.equals("")) {
            ref2webPostID = string5;
        }
    }

    X4sRef2Web(Connection connection, String string) {
        this.readDictRef2Web(connection);
        if (!string.equals("")) {
            ref2webPostID = string;
        }
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public void readDictRef2Web(String string, String string2, String string3, String string4) {
        if (ref2webDict != null) {
            return;
        }
        String string5 = "";
        int n = 0;
        int n2 = 0;
        int n3 = 3000;
        int n4 = 0;
        int n5 = 30000;
        String string6 = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Vector<ref2webLine> vector = new Vector<ref2webLine>();
        try {
            Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            this.strError = this.strError + classNotFoundException.getMessage() + "]]]";
            sysOut.println(" ClassNotFoundException: " + classNotFoundException.getMessage());
            return;
        }
        try {
            connection = DriverManager.getConnection(string2, string3, string4);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT DISTINCT  Code,MinVol,MinYear,MaxYear,WebPageCode ,MinPage,MaxPage,statFlag  FROM DICT567WEB  WHERE (statFlag='TRA')  ORDER BY MinYear DESC,Code,MinVol ");
            while (resultSet.next()) {
                string5 = resultSet.getString("Code");
                if (resultSet.wasNull()) continue;
                n = resultSet.getInt("MinVol");
                if (resultSet.wasNull()) {
                    n = 0;
                }
                n2 = resultSet.getInt("MinYear");
                if (resultSet.wasNull()) {
                    n2 = 0;
                }
                n3 = resultSet.getInt("MaxYear");
                if (resultSet.wasNull()) {
                    n3 = 3000;
                }
                n4 = resultSet.getInt("MinPage");
                if (resultSet.wasNull()) {
                    n4 = 0;
                }
                n5 = resultSet.getInt("MaxPage");
                if (resultSet.wasNull()) {
                    n5 = 30000;
                }
                string6 = resultSet.getString("WebPageCode");
                if (resultSet.wasNull() || string5.trim().equals("") || string6.trim().equals("")) continue;
                ref2webLine ref2webLine2 = new ref2webLine(string5, n, n2, n3, n4, n5, string6);
                vector.addElement(ref2webLine2);
            }
            resultSet.close();
            resultSet = null;
        }
        catch (SQLException sQLException) {
            sysOut.print("01ref2web-SQL-Exception: " + sQLException.getMessage());
        }
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            statement = null;
            connection = null;
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        ref2webDict = vector;
    }

    public void readDictRef2Web(Connection connection) {
        if (ref2webDict != null) {
            return;
        }
        String string = "";
        int n = 0;
        int n2 = 0;
        int n3 = 3000;
        int n4 = 0;
        int n5 = 30000;
        String string2 = "";
        Statement statement = null;
        ResultSet resultSet = null;
        Vector<ref2webLine> vector = new Vector<ref2webLine>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT DISTINCT  Code,MinVol,MinYear,MaxYear,WebPageCode ,MinPage,MaxPage,statFlag  FROM DICT567WEB  WHERE (statFlag='TRA')  ORDER BY MinYear DESC,Code,MinVol ");
            while (resultSet.next()) {
                string = resultSet.getString("Code");
                if (resultSet.wasNull()) continue;
                n = resultSet.getInt("MinVol");
                if (resultSet.wasNull()) {
                    n = 0;
                }
                n2 = resultSet.getInt("MinYear");
                if (resultSet.wasNull()) {
                    n2 = 0;
                }
                n3 = resultSet.getInt("MaxYear");
                if (resultSet.wasNull()) {
                    n3 = 3000;
                }
                n4 = resultSet.getInt("MinPage");
                if (resultSet.wasNull()) {
                    n4 = 0;
                }
                n5 = resultSet.getInt("MaxPage");
                if (resultSet.wasNull()) {
                    n5 = 30000;
                }
                string2 = resultSet.getString("WebPageCode");
                if (resultSet.wasNull() || string.trim().equals("") || string2.trim().equals("")) continue;
                ref2webLine ref2webLine2 = new ref2webLine(string, n, n2, n3, n4, n5, string2);
                vector.addElement(ref2webLine2);
            }
            resultSet.close();
            resultSet = null;
        }
        catch (SQLException sQLException) {
            sysOut.print("01ref2web-SQL-Exception: " + sQLException.getMessage());
        }
        try {
            statement.close();
            statement = null;
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        ref2webDict = vector;
    }

    public static String getRef2WebTemplate(String string) {
        String string2 = null;
        if (ref2webDict == null) {
            return null;
        }
        for (int i = 0; i < ref2webDict.size(); ++i) {
            ref2webLine ref2webLine2 = (ref2webLine)ref2webDict.elementAt(i);
            if (!ref2webLine2.Code.equals(string)) continue;
            string2 = ref2webLine2.WebPageCode;
            break;
        }
        return string2;
    }

    public static int iCmp(int n, int n2) {
        if (n <= 0) {
            return 0;
        }
        if (n2 == 0) {
            return 0;
        }
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        return 0;
    }

    public static String getRef2WebTemplate(String string, int n, int n2, int n3, int n4) {
        String string2 = null;
        if (ref2webDict == null) {
            return null;
        }
        for (int i = 0; i < ref2webDict.size(); ++i) {
            ref2webLine ref2webLine2 = (ref2webLine)ref2webDict.elementAt(i);
            if (X4sRef2Web.iCmp(n, ref2webLine2.MinVol) < 0 || X4sRef2Web.iCmp(n4, ref2webLine2.MinYear) < 0 || X4sRef2Web.iCmp(n4, ref2webLine2.MaxYear) > 0 || X4sRef2Web.iCmp(n3, ref2webLine2.MinPage) < 0 || X4sRef2Web.iCmp(n3, ref2webLine2.MaxPage) > 0 || ref2webLine2 == null || ref2webLine2.Code == null || string == null || !ref2webLine2.Code.equals(string)) continue;
            string2 = ref2webLine2.WebPageCode;
            break;
        }
        return string2;
    }

    public String getLink(c4sRef c4sRef2) {
        return this.getLink(c4sRef2.Ref, c4sRef2.iRefYear, c4sRef2.strVol, c4sRef2.strIssue, c4sRef2.strPage, c4sRef2.strNumber);
    }

    public String getLink(String string, int n, String string2, String string3, String string4) {
        return this.getLink(string, n, string2, string3, string4, "");
    }

    public String getLink(String string, int n, String string2, String string3, String string4, String string5) {
        int n2;
        int n3;
        String string6 = "";
        this.aLinkBegin = "";
        this.aLinkEnd = "";
        this.aHref = "";
        int n4 = this.str2int(string2, 0);
        String string7 = X4sRef2Web.getRef2WebTemplate(string, n4, n3 = this.str2int(string3, 0), n2 = this.str2int(string4, 0), n);
        if (string7 == null) {
            return string6;
        }
        string7 = this.myStrReplace(string7, "{$year}", "" + n);
        string7 = this.myStrReplace(string7, "{$vol}", string2);
        string7 = this.myStrReplace(string7, "{$issue}", string3);
        string7 = this.myStrReplace(string7, "{$page}", string4);
        string6 = this.padstr(string4, 4, "0");
        string7 = this.myStrReplace(string7, "{$page04}", string6);
        string7 = this.myStrReplace(string7, "{$ndcntr}", ref2webPostID);
        string5 = this.padstr(string5, 4, "0");
        string7 = this.myStrReplace(string7, "{$number}", string5);
        string7 = this.myStrDelEmpty(string7, string3, "[?issue?", "]");
        if (!(string7 = this.myStrReplaceEmpty(string7, string3, "[?noissue?", "]")).equals("")) {
            this.aHref = string7;
            this.aLinkBegin = "<a href=\"" + string7 + "\" target=\"" + this.aWindowName + "\">";
            this.aLinkEnd = "</a>";
        }
        return string7;
    }

    public String myStrDelEmpty(String string, String string2, String string3, String string4) {
        int n;
        int n2 = string.length();
        int n3 = string2.length();
        int n4 = string3.length();
        int n5 = string4.length();
        String string5 = "";
        int n6 = 0;
        while (n6 < n2 && (n = string.indexOf(string3, n6)) >= 0) {
            if (n > n6) {
                string5 = string5 + string.substring(n6, n);
            }
            if ((n = string.indexOf(string4, n6 = n + n4)) < 0) break;
            if (n3 > 0) {
                string5 = string5 + string.substring(n6, n);
            }
            n6 = n + n5;
        }
        if (n6 < n2) {
            string5 = string5 + string.substring(n6, n2);
        }
        return string5;
    }

    public String myStrReplaceEmpty(String string, String string2, String string3, String string4) {
        int n;
        int n2 = string.length();
        int n3 = string2.length();
        int n4 = string3.length();
        int n5 = string4.length();
        String string5 = "";
        int n6 = 0;
        while (n6 < n2 && (n = string.indexOf(string3, n6)) >= 0) {
            if (n > n6) {
                string5 = string5 + string.substring(n6, n);
            }
            if ((n = string.indexOf(string4, n6 = n + n4)) < 0) break;
            if (n3 == 0) {
                string5 = string5 + string.substring(n6, n);
            }
            n6 = n + n5;
        }
        if (n6 < n2) {
            string5 = string5 + string.substring(n6, n2);
        }
        return string5;
    }

    public String myStrReplace(String string, String string2, String string3) {
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

    public String padstr(String string, int n, String string2) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string3 = string;
            return string3;
        }
        String string4 = "";
        n2 = n - n2;
        for (int i = 0; i < n2; ++i) {
            string4 = string4 + string2;
        }
        string4 = string4 + string;
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

    class ref2webLine {
        String Code = "";
        int MinVol = 0;
        int MinYear = 0;
        int MaxYear = 3000;
        int MinPage = 0;
        int MaxPage = 30000;
        String WebPageCode = "";

        ref2webLine(String string, int n, int n2, int n3, int n4, int n5, String string2) {
            this.Code = string;
            this.MinVol = n;
            this.MinYear = n2;
            this.MaxYear = n3;
            this.MinPage = n4;
            this.MaxPage = n5;
            this.WebPageCode = string2;
            if (this.MaxYear <= 0) {
                this.MaxYear = 3000;
            }
        }
    }
}
