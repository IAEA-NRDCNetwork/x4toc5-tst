package zvv.x4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Reading file: internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2020-09-25
 * @since           2009-10-20
 *
 * Program:         x4readfile.java
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

public class x4readfile {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    BufferedWriter o3 = null;
    String outTxtFileName = "x4refdif.txt";
    int nstrOut = 0;
    x4fileinterface calling = null;
    String lastReadStr = null;
    String lastEntryStr = "";
    String lastTransStr = "";
    String lastEntryRead = "";
    boolean endOfFile = false;
    boolean flagLastSubentInEntry = false;
    int i0part1 = 0;
    int i0part2 = 0;
    int i0subent = 0;
    Vector interLines = new Vector();
    String Entry = "";
    String Subent = "";
    int EntryID = 0;
    int SubentID = 0;
    String lastKeyword = "";
    int DnCol = 0;
    int DnRow = 0;

    public static void main(String[] stringArray) {
        int n;
        String string = null;
        String string2 = null;
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an original EXFOR file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007\n");
        sysOut.println("args.length: " + stringArray.length + "\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4readfile file.x4");
            System.exit(0);
        }
        if ((n = 0) < stringArray.length) {
            string = stringArray[n++];
        }
        if (n < stringArray.length) {
            string2 = stringArray[n++];
        }
        x4readfile x4readfile2 = new x4readfile();
        x4readfile2.readExforFile(string);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    public void sysOut_println(String string) {
        if (sysOut != null) {
            sysOut.println(string);
        }
    }

    public void sysOut_print(String string) {
        if (sysOut != null) {
            sysOut.print(string);
        }
    }

    x4readfile() {
    }

    public void setx4fileinterface(x4fileinterface x4fileinterface2) {
        this.calling = x4fileinterface2;
    }

    public int readExforFile(String string) {
        int n = 0;
        x4subent x4subent2 = null;
        x4subent x4subent3 = null;
        x4subent x4subent4 = null;
        x4subent x4subent5 = null;
        x4subent x4subent6 = null;
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        String string2 = "";
        this.sysOut_println("---readExforFile:[" + string + "]");
        this.lastReadStr = null;
        this.lastEntryStr = "";
        this.lastTransStr = "";
        BufferedReader bufferedReader = this.openExforFile(string);
        if (bufferedReader == null) {
            if (sysOut != null) {
                this.sysOut_println("---error--- File not found: [" + string + "]");
            }
            return -1;
        }
        x4subent2 = new x4subent();
        x4subent5 = new x4subent();
        this.i0part1 = 0;
        this.i0part2 = 0;
        int n2 = 0;
        int n3 = 0;
        while (true) {
            boolean bl;
            Vector vector3 = this.x4readUntilSubent(bufferedReader);
            vector = vector2;
            vector2 = vector3;
            this.i0part2 = this.i0part1 + vector.size();
            Vector vector4 = this.getNextSubentLines(vector, vector2);
            x4subent4 = new x4subent(vector4, this.lastEntryStr, this.i0subent);
            x4subent4.lastTransStr = this.lastTransStr;
            x4subent4.setTransStr(this.lastTransStr);
            x4subent2 = x4subent3;
            x4subent3 = x4subent4;
            if (vector4.size() > 0) {
                ++n3;
            }
            x4subent6 = x4subent4;
            String string3 = x4subent4.Entry;
            String string4 = x4subent4.Subent;
            if (!string2.equals(string3)) {
                if (x4subent5 != null && !x4subent5.Entry.equals("") && this.calling != null) {
                    this.calling.treatExforEndentry(x4subent5, false);
                }
                if (this.calling != null) {
                    this.calling.treatExforEntry(x4subent6);
                }
                x4subent5 = null;
                bl = true;
            } else {
                bl = false;
            }
            string2 = string3;
            if (this.calling != null && vector4.size() > 0) {
                x4subent6.setSubent1(x4subent5);
                this.calling.treatExforSubent(x4subent6, this.flagLastSubentInEntry);
            }
            if (bl) {
                x4subent5 = x4subent6;
            }
            if (bl) {
                ++n;
            }
            if (this.endOfFile) break;
            this.i0part1 += vector.size();
            ++n2;
        }
        this.closeExforFile(bufferedReader);
        if (x4subent5 != null && !x4subent5.Entry.equals("") && this.calling != null) {
            this.calling.treatExforEndentry(x4subent5, true);
        }
        this.sysOut_println("---readExforFile:[" + string + "]" + " finished." + " nEntry=" + n + " nSubent=" + n3);
        return n;
    }

    public BufferedReader openExforFile(String string) {
        BufferedReader bufferedReader = null;
        this.lastReadStr = null;
        bufferedReader = x4readfile.openInpFile(string);
        if (bufferedReader == null) {
            return null;
        }
        return bufferedReader;
    }

    public int closeExforFile(BufferedReader bufferedReader) {
        this.lastReadStr = null;
        x4readfile.closeInpFile(bufferedReader);
        return 0;
    }

    Vector x4readUntilSubent(BufferedReader bufferedReader) {
        Vector<String> vector = new Vector<String>();
        int n = 0;
        while (true) {
            String string;
            if ((string = x4readfile.readLineInpFile(bufferedReader)) == null) {
                this.endOfFile = true;
                break;
            }
            if (string.startsWith("TRANS")) {
                this.lastTransStr = string;
            }
            vector.addElement(string);
            if (this.isSubentStr(string)) break;
            ++n;
        }
        return vector;
    }

    Vector getNextSubentLines(Vector vector, Vector vector2) {
        int n;
        String string;
        Vector<String> vector3 = new Vector<String>();
        Object var6_4 = null;
        Object var7_5 = null;
        this.lastEntryRead = "";
        this.i0subent = this.i0part1 + vector.size();
        this.flagLastSubentInEntry = false;
        if (vector.size() <= 0) {
            return vector3;
        }
        if (vector.size() > 0 && this.isSubentStr(string = (String)vector.elementAt(vector.size() - 1))) {
            vector3.addElement(string);
            --this.i0subent;
        }
        for (n = vector.size() - 1; n >= 0; --n) {
            string = (String)vector.elementAt(n);
            if (!this.isEntryStr(string)) continue;
            this.lastEntryStr = string;
            this.lastEntryRead = string;
            break;
        }
        for (n = 0; n < vector2.size(); ++n) {
            string = (String)vector2.elementAt(n);
            if (this.isEndEntryStr(string)) {
                this.flagLastSubentInEntry = true;
                break;
            }
            if (this.isSubentStr(string)) break;
            vector3.addElement(string);
            if (this.isEndSubentStr(string)) break;
        }
        while (n < vector2.size()) {
            string = (String)vector2.elementAt(n);
            if (this.isEndEntryStr(string)) {
                this.flagLastSubentInEntry = true;
                break;
            }
            ++n;
        }
        if (this.endOfFile) {
            this.flagLastSubentInEntry = true;
        }
        return vector3;
    }

    boolean isEntryStr(String string) {
        if (string == null) {
            return false;
        }
        if (string.startsWith("ENTRY")) {
            return true;
        }
        return string.startsWith("NOENTRY");
    }

    boolean isEndEntryStr(String string) {
        if (string == null) {
            return false;
        }
        return string.startsWith("ENDENTRY");
    }

    boolean isSubentStr(String string) {
        if (string == null) {
            return false;
        }
        if (string.startsWith("SUBENT")) {
            return true;
        }
        return string.startsWith("NOSUBENT");
    }

    boolean isEndSubentStr(String string) {
        if (string == null) {
            return false;
        }
        if (string.startsWith("ENDSUBENT")) {
            return true;
        }
        return string.startsWith("NOSUBENT");
    }

    public int x4readAndInterpreteFile(String string) {
        String string2;
        int n = 0;
        this.Entry = "";
        this.Subent = "";
        this.lastKeyword = "";
        this.interLines = new Vector();
        BufferedReader bufferedReader = x4readfile.openInpFile(string);
        if (bufferedReader == null) {
            return -1;
        }
        int n2 = 0;
        while ((string2 = x4readfile.readLineInpFile(bufferedReader)) != null) {
            string2 = this.strpad(string2, 80);
            string2 = string2.substring(0, 66);
            this.checkInterpretLine(string2);
            ++n2;
        }
        x4readfile.closeInpFile(bufferedReader);
        return n;
    }

    public String getStrTab(String string, int n, int n2) {
        int n3 = (string = x4readfile.delEndSpace(string)).length();
        if (n3 <= 0) {
            return string;
        }
        int n4 = (n3 + n - 1) / n;
        string = this.strpad(string, n4 * n);
        String string2 = "";
        for (int i = 0; i < n4; ++i) {
            String string3 = string.substring(i * n, i * n + n);
            string3 = string3.trim();
            string3 = this.strpad(string3, n);
            string2 = string2 + string3;
        }
        return string2;
    }

    public String getNumTab(String string, int n) {
        int n2 = string.length();
        if (n2 <= 0) {
            return string;
        }
        int n3 = (n2 + n - 1) / n;
        string = this.strpad(string, n3 * n);
        String string2 = "";
        for (int i = 0; i < n3; ++i) {
            int n4;
            String string3 = string.substring(i * n, i * n + n);
            string3 = string3.trim();
            string3 = string3.toUpperCase();
            string3 = this.myStrReplace(string3, " +", "E+");
            string3 = this.myStrReplace(string3, " -", "E-");
            int n5 = (string3 = this.myStrReplace(string3, " ", "")).length();
            if (n5 > 1) {
                char c;
                n4 = string3.indexOf("+", 1);
                if (n4 < 0) {
                    n4 = string3.indexOf("-", 1);
                }
                if (n4 > 0 && (c = string3.charAt(n4 - 1)) != 'E') {
                    string3 = string3.substring(0, n4) + "E" + string3.substring(n4);
                }
            }
            if (string3.endsWith("E+00")) {
                n4 = string3.indexOf("E+00");
                string3 = string3.substring(0, n4);
            }
            if (string3.endsWith("E-00")) {
                n4 = string3.indexOf("E-00");
                string3 = string3.substring(0, n4);
            }
            string3 = this.reformatFloat(string3);
            string3 = this.strpad(string3, n);
            string2 = string2 + string3;
        }
        return string2;
    }

    public String reformatFloat(String string) {
        int n;
        String string2;
        String string3;
        float f;
        int n2 = string.length();
        if (n2 <= 0) {
            return string;
        }
        try {
            f = Float.parseFloat(string);
        }
        catch (Exception exception) {
            return string;
        }
        String string4 = "" + f;
        string4 = string4.toUpperCase();
        int n3 = string4.indexOf("E");
        if (n3 > 0) {
            string3 = string4.substring(0, n3);
            string2 = string4.substring(n3);
        } else {
            string3 = string4;
            string2 = "";
        }
        n3 = string3.indexOf(".");
        if (n3 < 0) {
            string3 = string3 + ".";
        }
        if ((string4 = string3 + string2).endsWith(".0")) {
            n3 = string4.indexOf(".0");
            string4 = string4.substring(0, n3 + 1);
        }
        if ((n = string4.length()) < n2) {
            return string4;
        }
        return string;
    }

    public void checkInterpretLine(String string) {
        String string2 = this.getKeyword(string);
        if (!string2.equals("")) {
            this.getInterpretedLines(string2, string);
            string = x4readfile.delEndSpace(string);
            this.outFileWriteln(this.o3, string);
            return;
        }
        string = x4readfile.delEndSpace(string);
        this.outFileWriteln(this.o3, string);
    }

    public String getKeyword(String string) {
        String string2 = "";
        if (string == null) {
            return string2;
        }
        if (string.length() < 1) {
            return string2;
        }
        char c = string.charAt(0);
        if (!Character.isLetter(c)) {
            return string2;
        }
        string2 = string.length() < 10 ? string : string.substring(0, 10);
        string2 = string2.trim();
        return string2;
    }

    public void addInterpretedLine(String string) {
        for (int i = 0; i < this.interLines.size(); ++i) {
            String string2 = (String)this.interLines.elementAt(i);
            if (!string2.equals(string)) continue;
            return;
        }
        this.interLines.addElement(string);
    }

    public void getInterpretedLines(String string, String string2) {
        this.DnCol = 0;
        this.DnRow = 0;
        if (string.equals("ENTRY")) {
            this.Entry = string2.substring(11, 22).trim();
            this.Subent = this.Entry + "001";
            this.SubentID = this.x4acc2ID(this.Subent);
            this.EntryID = this.SubentID / 1000;
            if (sysOut != null) {
                this.sysOut_println("...Entry=[" + this.Entry + "]<br>");
            }
            return;
        }
        if (string.equals("SUBENT")) {
            this.Subent = string2.substring(11, 22).trim();
            this.SubentID = this.x4acc2ID(this.Subent);
            this.EntryID = this.SubentID / 1000;
            return;
        }
        if (string.equals("REACTION")) {
            return;
        }
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

    public BufferedWriter openOutFile(String string) {
        BufferedWriter bufferedWriter = null;
        this.deleteFile(string);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(string));
            return bufferedWriter;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public BufferedWriter openOutFile(String string, boolean bl) {
        BufferedWriter bufferedWriter = null;
        if (!bl) {
            this.deleteFile(string);
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(string, bl));
            return bufferedWriter;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public int closeOutFile(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.close();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public int outFileWrite(BufferedWriter bufferedWriter, String string) {
        try {
            bufferedWriter.write(string);
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public int outFileWriteln(BufferedWriter bufferedWriter, String string) {
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public void deleteFile(String string) {
        File file = new File(string);
        if (file.exists()) {
            boolean bl = file.delete();
        }
    }

    public int x4acc2ID(String string) {
        char c = Character.toUpperCase(string.charAt(0));
        String string2 = Character.isDigit(c) ? string : Character.getNumericValue(c) - Character.getNumericValue('A') + 10 + string.substring(1);
        int n = Integer.parseInt(string2);
        return n;
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

    public String myStrReplaceCRLF(String string) {
        string = this.myStrReplace(string, "\n\r", " ");
        string = this.myStrReplace(string, "\r\n", " ");
        string = this.myStrReplace(string, "\n", " ");
        string = this.myStrReplace(string, "\r", " ");
        string = this.myStrReplace(string, "  ", " ");
        string = this.myStrReplace(string, "  ", " ");
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
