package zvv.x4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * EXFOR-Dictionary object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-08-03
 * @since           2011-02-23
 *
 * Program:         x4dict.java
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

public class x4dict {
    static PrintWriter sysOut = new PrintWriter(System.out, true);
    static boolean extDebug = false;
    static Hashtable hashDicts = new Hashtable();
    static String dirOfArchiveDicts = "";
    static String nameOfArchiveDicts = "DICT_ARC_NEW.";
    static String zipOfArchiveDicts = null;
    static String versionOfArchiveDicts = "";
    static String locationOfArchiveDicts = "";
    static String versionOfArchiveDict714 = "";
    static String versionOfArchiveDict715 = "";
    static String creatorOfArchiveDicts = "NRDC, V.McLane(NNDC), O.Schwerer(IAEA), N.Otsuka(IAEA)";
    static String creatorOfArchiveDict714 = "D.Cullen(IAEA), A.Trkov(IAEA), V.Zerkin(IAEA), Empire-team";
    static String creatorOfArchiveDict715 = "V.Pronyaev(IAEA,IPPE)";
    static uniDicts[] uniDict = new uniDicts[]{new uniDicts("INSTITUTE", 3, 13, 43, 51, 103, 104, 123), new uniDicts("STATUS", 16, 13, 43, 49, 103), new uniDicts("REL-REF-FLAG", 17, 13, 14, 43, 103), new uniDicts("HISTORY", 15, 13, 43, 59, 103), new uniDicts("FACILITY", 18, 13, 43, 44, 96), new uniDicts("INC-SOURCE", 19, 13, 43, 44, 96), new uniDicts("METHOD", 21, 13, 43, 44, 96), new uniDicts("DETECTOR", 22, 13, 43, 44, 96), new uniDicts("ANALYSIS", 23, 13, 43, 44, 96), new uniDicts("PROCESS", 30, 13, 43, 54, 98), new uniDicts("SF9", 35, 13, 43, 54, 98), new uniDicts("JOURNAL", 5, 13, 23, 76, 123, 49, 51), new uniDicts("REPORT", 6, 13, 23, 51, 98, 44, 50), new uniDicts("CONF", 7, 13, 23, 44, 96, 98, 100), new uniDicts("BOOK", 207, 13, 23, 44, 96, 98, 100)};
    String strOriginal = "";
    String statusFlag = "";
    String strDate = "";
    String Code = "";
    String Key2 = "";
    String shortHelp = "";
    Vector vLongExpansion = new Vector();
    static int i0code = 13;
    static int i1code = 43;
    static int i0shortHelp = 59;
    static int i1shortHelp = 113;
    static int i0key2 = -1;
    static int i1key2 = -1;
    static byte[] zipByteArray = null;

    public static void main(String[] stringArray) {
        int n;
        String string = null;
        sysOut = new PrintWriter(System.out, true);
        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2007-2022\n");
        if (stringArray.length < 1) {
            sysOut.println("Run:      $ java x4dict DICT_ARC_NEW.034");
            System.exit(0);
        }
        if ((n = 0) < stringArray.length) {
            string = stringArray[n++];
        }
        x4dict x4dict2 = new x4dict();
        x4dict.setDirOfArchivedDicts("rrrrr/x4dict/");
        x4dict.setZipOfArchivedDicts("x4dict.zip.bin");
        x4dict2 = new x4dict();
        Vector vector = x4dict034.getGenMods();
        for (int i = 0; i < vector.size(); ++i) {
            String string2 = (String)vector.elementAt(i);
            sysOut.println("genmod-" + (i + 1) + ": [" + string2 + "]");
        }
        System.exit(0);
        x4dict2 = x4dict.findinx4dict("HISTORY", "C");
        if (x4dict2 != null) {
            sysOut.println(" x4f=" + x4dict2.toString());
        }
        if ((x4dict2 = x4dict.findinx4dict("REL-REF-FLAG", "O")) != null) {
            sysOut.println(" x4f=" + x4dict2.toString());
        }
        x4dict.readExforDictionary(34, 13, 16, 59, 113);
        x4dict.printCurrentDicts();
        x4dict.printDict(5);
    }

    public static void setPrintWriter(PrintWriter printWriter) {
        sysOut = printWriter;
    }

    x4dict() {
    }

    x4dict(String string) {
        this.strOriginal = string;
        this.statusFlag = x4dict.strExtractStr(string, 2, 4, "");
        this.strDate = x4dict.strExtractStr(string, 6, 11, "");
        this.Code = x4dict.strExtractStr(string, i0code, i1code, "");
        if (i1shortHelp >= i0shortHelp) {
            this.shortHelp = x4dict.strExtractStr(string, i0shortHelp, i1shortHelp, "");
        }
        if (i0key2 >= 0 && i1key2 >= i0key2) {
            this.Key2 = x4dict.strExtractStr(string, i0key2, i1key2, "");
        }
    }

    public x4dict x4dict_new(String string) {
        x4dict x4dict2 = new x4dict(string);
        x4dict2.strOriginal = string;
        return x4dict2;
    }

    public Vector readExforDict(String string) {
        String string2;
        x4dict x4dict2 = null;
        boolean bl = false;
        Vector<x4dict> vector = new Vector<x4dict>();
        int n = 0;
        if (extDebug) {
            System.err.println("---readExforDict: [" + string + "]");
        }
        BufferedReader bufferedReader = null;
        x4unzip x4unzip2 = null;
        if (zipOfArchiveDicts != null) {
            String string3;
            int n2;
            x4unzip2 = new x4unzip();
            Vector vector2 = null;
            if (zipOfArchiveDicts.toLowerCase().endsWith(".zip")) {
                x4unzip x4unzip3 = new x4unzip();
                if (zipByteArray == null) {
                    zipByteArray = x4unzip3.getBinFile2ByteArray(zipOfArchiveDicts);
                }
                if (zipByteArray != null) {
                    vector2 = x4unzip2.getFileNames(zipByteArray);
                }
            }
            if (vector2 == null) {
                vector2 = x4unzip2.getFileNames(zipOfArchiveDicts);
            }
            if ((n2 = (string3 = x4dict.myStrReplace(string, "\\", "/")).lastIndexOf("/")) >= 0) {
                string3 = string3.substring(n2);
            }
            if (!string3.startsWith("/")) {
                string3 = "/" + string3;
            }
            if (vector2 != null) {
                for (n2 = 0; n2 < vector2.size(); ++n2) {
                    string2 = (String)vector2.elementAt(n2);
                    if (!("/" + string2).endsWith(string3)) continue;
                    bufferedReader = zipByteArray != null ? x4unzip2.unzip2BufferedReader(zipByteArray, string2) : x4unzip2.unzip2BufferedReader(zipOfArchiveDicts, string2);
                    bl = true;
                    break;
                }
            }
        }
        if (bufferedReader == null) {
            bufferedReader = this.openInpFile(string);
        }
        if (bufferedReader == null) {
            if (sysOut != null) {
                sysOut.println("---Error--- File not found: [" + string + "]");
            }
            return vector;
        }
        int n3 = 0;
        while ((string2 = this.readLineInpFile(bufferedReader)) != null) {
            if (!this.isItKeyStr(string2)) {
                if (x4dict2 != null && string2.length() >= 6) {
                    String string4 = string2.substring(6).trim();
                    if (x4dict2.vLongExpansion.size() == 0) {
                        x4dict2.vLongExpansion.addElement(string4);
                        n = this.chkBrks(string4, 0);
                    } else if (n > 0) {
                        x4dict2.vLongExpansion.addElement(string4);
                        n = this.chkBrks(string4, n);
                    }
                }
            } else {
                x4dict2 = this.x4dict_new(string2);
                vector.addElement(x4dict2);
                if (extDebug) {
                    sysOut.println(x4dict.padstr(n3 + ")", 5) + x4dict2.toString());
                }
            }
            ++n3;
        }
        this.closeInpFile(bufferedReader);
        if (extDebug) {
            System.err.println("===readExforDict:[" + string + "] Lines:" + n3);
        }
        if (bl) {
            // empty if block
        }
        return vector;
    }

    public int chkBrks(String string, int n) {
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            if (c == '(') {
                ++n;
                continue;
            }
            if (c != ')' || --n > 0) continue;
            return 0;
        }
        return n;
    }

    public static int readExforStdDictionaries() {
        int n = 0;
        return n;
    }

    public static String setDirOfArchivedDicts(String string) {
        String string2 = dirOfArchiveDicts;
        dirOfArchiveDicts = string;
        String string3 = x4dict.getDateFile(dirOfArchiveDicts + "DICT_ARC_NEW.001");
        String[] stringArray = (string + "a").split("/");
        if (stringArray.length > 1) {
            locationOfArchiveDicts = "" + stringArray[stringArray.length - 2];
        }
        versionOfArchiveDicts = string3;
        versionOfArchiveDict714 = string3 = x4dict.getDateFile(dirOfArchiveDicts + "DICT_ARC_NEW.714");
        versionOfArchiveDict715 = string3 = x4dict.getDateFile(dirOfArchiveDicts + "DICT_ARC_NEW.715");
        return string2;
    }

    public static String setZipOfArchivedDicts(String string) {
        String string2 = zipOfArchiveDicts;
        zipOfArchiveDicts = string;
        return string2;
    }

    public static String getFileName(int n) {
        String string = dirOfArchiveDicts + nameOfArchiveDicts + n / 100 + n / 10 % 10 + n % 10;
        return string;
    }

    public static Vector getVectorDict(int n) {
        String string = x4dict.getFileName(n);
        Vector vector = (Vector)hashDicts.get(string);
        if (vector != null) {
            return vector;
        }
        x4dict.readExforStdDictionaries();
        vector = (Vector)hashDicts.get(string);
        if (vector != null) {
            return vector;
        }
        x4dict x4dict2 = new x4dict();
        vector = x4dict2.readExforDict(string);
        hashDicts.put(string, vector);
        return vector;
    }

    public static void readExforDictionary(int n, int n2, int n3, int n4, int n5) {
        String string = x4dict.getFileName(n);
        Vector vector = (Vector)hashDicts.get(string);
        if (vector != null) {
            return;
        }
        x4dict x4dict2 = new x4dict();
        i0code = n2;
        i1code = n3;
        i0shortHelp = n4;
        i1shortHelp = n5;
        vector = x4dict2.readExforDict(string);
        hashDicts.put(string, vector);
    }

    public static void printCurrentDicts() {
        Enumeration enumeration = hashDicts.elements();
        Enumeration enumeration2 = hashDicts.keys();
        int n = 1;
        while (enumeration2.hasMoreElements()) {
            Vector vector = (Vector)enumeration.nextElement();
            String string = (String)enumeration2.nextElement();
            sysOut.println("x4dict: " + string + " L=" + vector.size());
            ++n;
        }
    }

    public static int findUniDictByName(String string) {
        int n = -1;
        for (int i = 0; i < uniDict.length; ++i) {
            if (!string.equals(x4dict.uniDict[i].kwName)) continue;
            n = x4dict.uniDict[i].stdnum;
            i0code = x4dict.uniDict[i].i0code;
            i1code = x4dict.uniDict[i].i1code;
            i0shortHelp = x4dict.uniDict[i].i0help;
            i1shortHelp = x4dict.uniDict[i].i1help;
            i0key2 = x4dict.uniDict[i].i0key2;
            i1key2 = x4dict.uniDict[i].i1key2;
            Vector vector = x4dict.getVectorDict(n);
            return n;
        }
        return n;
    }

    public static x4dict findinx4dict(String string, String string2) {
        int n = -1;
        n = x4dict.findUniDictByName(string);
        if (n < 0) {
            return null;
        }
        Vector vector = x4dict.getVectorDict(n);
        return x4dict.findinx4dict(string2, vector);
    }

    public static x4dict findkey2x4dict(String string, String string2) {
        int n = -1;
        n = x4dict.findUniDictByName(string);
        if (n < 0) {
            return null;
        }
        Vector vector = x4dict.getVectorDict(n);
        return x4dict.findkey2x4dict(string2, vector);
    }

    public static x4dict findinx4dict(int n, String string) {
        Vector vector = x4dict.getVectorDict(n);
        return x4dict.findinx4dict(string, vector);
    }

    public static boolean exists(int n, String string) {
        x4dict x4dict2 = x4dict.findinx4dict(n, string);
        return x4dict2 != null;
    }

    public static x4dict findinx4dict(String string, Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            x4dict x4dict2 = (x4dict)vector.elementAt(i);
            if (!x4dict2.Code.toUpperCase().trim().equals(string.toUpperCase().trim())) continue;
            return x4dict2;
        }
        return null;
    }

    public static x4dict findkey2x4dict(String string, Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            x4dict x4dict2 = (x4dict)vector.elementAt(i);
            if (!x4dict2.Key2.equals(string)) continue;
            return x4dict2;
        }
        return null;
    }

    public static void printDict(int n) {
        Vector vector = x4dict.getVectorDict(n);
        sysOut.println("\n.....printDict: " + n);
        for (int i = 0; i < vector.size(); ++i) {
            x4dict x4dict2 = (x4dict)vector.elementAt(i);
            sysOut.println(x4dict.padstr(i + 1 + ")", 5) + x4dict2.toString());
        }
    }

    public String getExpansion() {
        return this.shortHelp;
    }

    public String toString() {
        String string = "" + this.statusFlag + " " + this.strDate + " " + this.Code + " ";
        if (!this.Key2.equals("")) {
            string = string + "[" + this.Key2 + "] ";
        }
        if (!this.shortHelp.equals("")) {
            string = x4dict.strpad(string, 30) + " ::" + this.shortHelp;
        }
        return string;
    }

    public String strExtractCode(String string, int n, int n2, String string2) {
        String string3 = x4dict.strExtractStr(string, n, n2, string2).trim();
        int n3 = string3.indexOf(" ");
        if (n3 > 0) {
            string3 = string3.substring(0, n3);
        }
        return string3;
    }

    public static String strExtractStr(String string, int n, int n2, String string2) {
        return x4dict.strExtractStr(string, n, n2, string2, true);
    }

    public static String strExtractStr(String string, int n, int n2, String string2, boolean bl) {
        String string3 = string2;
        if (string == null) {
            return string3;
        }
        int n3 = string.length();
        string3 = n2 > n3 ? (n > n3 ? "" : string.substring(n - 1)) : string.substring(n - 1, n2);
        if (bl) {
            string3 = x4dict.delEndSpace(string3);
        }
        return string3;
    }

    public static int strExtractInt(String string, int n, int n2, int n3) {
        string = x4dict.strExtractStr(string, n, n2, "").trim();
        int n4 = x4dict.str2int(string, n3);
        return n4;
    }

    public static float strExtractFloat(String string, int n, int n2, float f) {
        string = x4dict.strExtractStr(string, n, n2, "").trim();
        float f2 = x4dict.str2float(string, f);
        return f2;
    }

    public static double strExtractDouble(String string, int n, int n2, double d) {
        string = x4dict.strExtractStr(string, n, n2, "").trim();
        double d2 = x4dict.str2double(string, d);
        return d2;
    }

    public boolean isItKeyStr(String string) {
        String string2 = x4dict.strExtractStr(string, 1, 1, "");
        if (string2.equals("#")) {
            return false;
        }
        string2 = x4dict.strExtractStr(string, i0code, i0code, "");
        return !string2.equals("");
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

    public BufferedReader openInpFileFromJar(String string) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = this.getClass().getResourceAsStream(string);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public BufferedReader openInpFile(String string) {
        BufferedReader bufferedReader = null;
        bufferedReader = this.openInpFileFromJar(string);
        if (bufferedReader != null) {
            return bufferedReader;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(string));
            return bufferedReader;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public int closeInpFile(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            return 0;
        }
        try {
            bufferedReader.close();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public String readLineInpFile(BufferedReader bufferedReader) {
        String string = null;
        if (bufferedReader == null) {
            return null;
        }
        try {
            string = bufferedReader.readLine();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return string;
    }

    public static BufferedWriter openOutFile(String string) {
        BufferedWriter bufferedWriter = null;
        x4dict.deleteFile(string);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(string));
            return bufferedWriter;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static BufferedWriter openOutFile(String string, boolean bl) {
        BufferedWriter bufferedWriter = null;
        if (!bl) {
            x4dict.deleteFile(string);
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(string, bl));
            return bufferedWriter;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static int closeOutFile(BufferedWriter bufferedWriter) {
        if (bufferedWriter == null) {
            return 0;
        }
        try {
            bufferedWriter.close();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public static int outFileWrite(BufferedWriter bufferedWriter, String string) {
        if (bufferedWriter == null) {
            return -2;
        }
        try {
            bufferedWriter.write(string);
            bufferedWriter.flush();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public static int outFileWriteln(BufferedWriter bufferedWriter, String string) {
        if (bufferedWriter == null) {
            return -2;
        }
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public static void deleteFile(String string) {
        File file = new File(string);
        if (file.exists()) {
            boolean bl = file.delete();
        }
    }

    public static void mkdir(String string) {
        File file = new File(string);
        if (!file.exists()) {
            boolean bl = file.mkdir();
            sysOut.println("    ...mkdir: " + string + " OK=" + bl);
        }
    }

    public static int x4acc2ID(String string) {
        char c = Character.toUpperCase(string.charAt(0));
        String string2 = Character.isDigit(c) ? string : Character.getNumericValue(c) - Character.getNumericValue('A') + 10 + string.substring(1);
        int n = Integer.parseInt(string2);
        return n;
    }

    public static String getDateNow() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String string = new String(simpleDateFormat.format(date));
        return string;
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

    public static String float2str(float f) {
        String string = String.format("%g", Float.valueOf(f));
        return string;
    }

    public static String float2strG(float f) {
        int n;
        String string = "";
        string = String.format("%g", Float.valueOf(f));
        string = x4dict.myStrReplace(string, "00000e", "e");
        string = x4dict.myStrReplace(string, "0000e", "e");
        string = x4dict.myStrReplace(string, "000e", "e");
        string = x4dict.myStrReplace(string, "00e", "e");
        if ((string = x4dict.myStrReplace(string, "0e", "e")).indexOf("e") < 0) {
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        if ((n = string.indexOf(".")) < 0 && string.indexOf("e") < 0) {
            string = string + ".";
        }
        return string;
    }

    public static int str2int(String string, int n) {
        int n2 = n;
        try {
            n2 = Integer.parseInt(string.trim());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    public static double str2double(String string, double d) {
        double d2 = d;
        try {
            d2 = Double.parseDouble(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return d2;
    }

    public static float str2float(String string, float f) {
        float f2 = f;
        try {
            f2 = Float.parseFloat(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return f2;
    }

    public Float str2Float(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.trim().toUpperCase();
        try {
            float f = Float.parseFloat(string2);
            return new Float(f);
        }
        catch (Exception exception) {
            string2 = x4dict.myStrReplace(string2, " +", "E+");
            string2 = x4dict.myStrReplace(string2, " -", "E-");
            string2 = x4dict.myStrReplace(string2, " ", "");
            int n = string2.length();
            if (n > 1) {
                char c;
                int n2 = string2.indexOf("+", 1);
                if (n2 < 0) {
                    n2 = string2.indexOf("-", 1);
                }
                if (n2 > 0 && (c = string2.charAt(n2 - 1)) != 'E') {
                    string2 = string2.substring(0, n2) + "E" + string2.substring(n2);
                }
            }
            try {
                float f = Float.parseFloat(string2);
                return new Float(f);
            }
            catch (Exception exception2) {
                return null;
            }
        }
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

    public static String padstr(String string, int n) {
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

    public String myStrReplaceCRLF(String string) {
        string = x4dict.myStrReplace(string, "\n\r", " ");
        string = x4dict.myStrReplace(string, "\r\n", " ");
        string = x4dict.myStrReplace(string, "\n", " ");
        string = x4dict.myStrReplace(string, "\r", " ");
        string = x4dict.myStrReplace(string, "  ", " ");
        string = x4dict.myStrReplace(string, "  ", " ");
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

    static class uniDicts {
        String kwName;
        int stdnum;
        int i0code;
        int i1code;
        int i0help;
        int i1help;
        int i0key2 = -1;
        int i1key2 = -1;
        boolean ready = false;

        uniDicts(String string, int n, int n2, int n3, int n4, int n5) {
            this(string, n, n2, n3, n4, n5, -1, -1);
        }

        uniDicts(String string, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
            this.kwName = string;
            this.stdnum = n;
            this.i0code = n2;
            this.i1code = n3;
            this.i0help = n4;
            this.i1help = n5;
            this.i0key2 = n6;
            this.i1key2 = n7;
        }

        public String toString() {
            String string = " " + this.kwName + " " + this.stdnum + " " + this.i0code + " " + this.i1code + " " + this.i0help + " " + this.i1help;
            return string;
        }
    }
}
