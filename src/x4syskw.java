package zvv.x4;

/**
 * EXFOR System Keyword object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2007-07-19
 * @since           2007-07-19
 *
 * Program:         x4syskw.java
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

public class x4syskw {
    int iSysID = 0;
    int iType = 0;
    String strSysID = "";
    String[] after = null;
    String[] before = null;
    String presence = "";
    boolean bibFlag = false;
    static final int TYPE_ENTRY = 1;
    static final int TYPE_SUB1 = 2;
    static final int TYPE_SUB2 = 3;
    static final int TYPE_BIB = 4;
    static final int TRANS = 1;
    static final int ENDTRANS = 2;
    static final int ENTRY = 3;
    static final int ENDENTRY = 4;
    static final int SUBENT = 5;
    static final int ENDSUBENT = 6;
    static final int NOSUBENT = 7;
    static final int BIB = 8;
    static final int ENDBIB = 9;
    static final int NOBIB = 10;
    static final int COMMON = 11;
    static final int ENDCOMMON = 12;
    static final int NOCOMMON = 13;
    static final int DATA = 14;
    static final int ENDDATA = 15;
    static final int NODATA = 16;
    static final int TITLE = 17;
    static final int AUTHOR = 18;
    static final int INSTITUTE = 19;
    static final int EXP_YEAR = 20;
    static final int REFERENCE = 21;
    static final int REL_REF = 22;
    static final int MONIT_REF = 23;
    static final int REACTION = 24;
    static final int RESULT = 25;
    static final int MONITOR = 26;
    static final int ASSUMED = 27;
    static final int DECAY_DATA = 28;
    static final int DECAY_MON = 29;
    static final int PART_DET = 30;
    static final int RAD_DET = 31;
    static final int HALF_LIFE = 32;
    static final int EN_SEC = 33;
    static final int EMS_SEC = 34;
    static final int LEVEL_PROP = 35;
    static final int MOM_SEC = 36;
    static final int MISC_COL = 37;
    static final int INC_SOURCE = 38;
    static final int INC_SPECT = 39;
    static final int SAMPLE = 40;
    static final int METHOD = 41;
    static final int FACILITY = 42;
    static final int ANALYSIS = 43;
    static final int DETECTOR = 44;
    static final int CORRECTION = 45;
    static final int COVARIANCE = 46;
    static final int ERR_ANALYS = 47;
    static final int ADD_RES = 48;
    static final int COMMENT = 49;
    static final int CRITIQUE = 50;
    static final int FLAG = 51;
    static final int STATUS = 52;
    static final int HISTORY = 53;
    static final int REQUEST = 54;
    static final int ENDREQUEST = 55;
    static final x4syskw[] kwEntry;
    static final x4syskw[] kwSub1;
    static final x4syskw[] kwSub2;
    static final x4syskw[] kwBib;
    static x4syskw[] kwAll;

    public static void main(String[] stringArray) {
        x4syskw.printAllKw();
    }

    public x4syskw(int n, String string, String[] stringArray, String[] stringArray2) {
        this.iSysID = n;
        this.strSysID = string.trim();
        this.after = stringArray;
        this.before = stringArray2;
    }

    public x4syskw(int n, String string, String string2, String[] stringArray) {
        this.iSysID = n;
        this.strSysID = string.trim();
        this.presence = string2;
        this.before = stringArray;
        this.bibFlag = true;
    }

    static void printAllKw() {
        System.out.print("---printAllKw: L=" + kwAll.length);
        for (int i = 0; i < kwAll.length; ++i) {
            x4syskw x4syskw2 = kwAll[i];
            System.out.print(" ID=" + x4syskw2.iSysID);
            System.out.print(" iType=" + x4syskw2.iType);
            System.out.print(" strSysID=[" + x4syskw2.strSysID + "]");
            System.out.println("");
        }
    }

    static void printKw(String string) {
        System.out.print("---printKw: L=" + kwAll.length);
        x4syskw x4syskw2 = x4syskw.getKw(string);
        if (x4syskw2 == null) {
            System.out.println(" No such KEYWORD: [" + string + "]");
            return;
        }
        System.out.print(" ID=" + x4syskw2.iSysID);
        System.out.print(" iType=" + x4syskw2.iType);
        System.out.print(" strSysID=[" + x4syskw2.strSysID + "]");
        System.out.println("");
    }

    public String toString() {
        String string = "";
        string = string + " ID=" + this.iSysID;
        string = string + " iType=" + this.iType;
        string = string + " strSysID=[" + this.strSysID + "]";
        if (this.bibFlag) {
            string = string + " presence=[" + this.presence + "]";
        }
        if (this.before != null) {
            for (int i = 0; i < this.before.length; ++i) {
                string = string + " be." + i + "[" + this.before[i] + "]";
            }
        }
        return string;
    }

    static x4syskw getKw(String string) {
        for (int i = 0; i < kwAll.length; ++i) {
            x4syskw x4syskw2 = kwAll[i];
            if (!x4syskw2.strSysID.equals(string)) continue;
            return x4syskw2;
        }
        return null;
    }

    static x4syskw getKw(String string, int n) {
        for (int i = 0; i < kwAll.length; ++i) {
            x4syskw x4syskw2 = kwAll[i];
            if (x4syskw2.iType == 2 && n != 1 || x4syskw2.iType == 3 && n == 1 || !x4syskw2.strSysID.equals(string)) continue;
            return x4syskw2;
        }
        return null;
    }

    public String getAllAfter() {
        String string = "";
        if (this.after != null) {
            for (int i = 0; i < this.after.length; ++i) {
                if (i != 0) {
                    string = string + "/";
                }
                string = string + this.after[i];
            }
        }
        return string;
    }

    public boolean checkAfter(String string) {
        if (this.after == null) {
            return false;
        }
        for (int i = 0; i < this.after.length; ++i) {
            String string2 = this.after[i];
            if (!string2.equals(string)) continue;
            return true;
        }
        return false;
    }

    static {
        int n;
        kwEntry = new x4syskw[]{new x4syskw(1, "TRANS", new String[]{"ENTRY"}, new String[]{"-no-"}), new x4syskw(2, "ENDTRANS", new String[]{"-no-"}, new String[]{"ENDENTRY"}), new x4syskw(54, "REQUEST", new String[]{"ENTRY"}, new String[]{"-no-"}), new x4syskw(55, "ENDREQUEST", new String[]{"-no-"}, new String[]{"ENDENTRY"}), new x4syskw(3, "ENTRY", new String[]{"SUBENT", "NOSUBENT"}, new String[]{"ENDENTRY", "TRANS"}), new x4syskw(4, "ENDENTRY", new String[]{"ENTRY", "ENDTRANS", "ENDREQUEST"}, new String[]{"ENDSUBENT", "NOSUBENT"})};
        kwSub1 = new x4syskw[]{new x4syskw(5, "SUBENT", new String[]{"BIB", "NOBIB"}, new String[]{"ENTRY"}), new x4syskw(6, "ENDSUBENT", new String[]{"SUBENT", "ENDENTRY", "NOSUBENT"}, new String[]{"ENDCOMMON", "NOCOMMON"}), new x4syskw(7, "NOSUBENT", new String[]{"SUBENT", "ENDENTRY", "NOSUBENT"}, new String[]{"ENTRY"}), new x4syskw(8, "BIB", new String[]{"-bib-"}, new String[]{"SUBENT"}), new x4syskw(9, "ENDBIB", new String[]{"COMMON", "NOCOMMON"}, new String[]{"-bib-"}), new x4syskw(10, "NOBIB", new String[]{"COMMON", "NOCOMMON"}, new String[]{"SUBENT"}), new x4syskw(11, "COMMON", new String[]{"-common-"}, new String[]{"ENDBIB", "NOBIB"}), new x4syskw(12, "ENDCOMMON", new String[]{"ENDSUBENT"}, new String[]{"-common-"}), new x4syskw(13, "NOCOMMON", new String[]{"ENDSUBENT"}, new String[]{"ENDBIB", "NOBIB"})};
        kwSub2 = new x4syskw[]{new x4syskw(5, "SUBENT", new String[]{"BIB", "NOBIB"}, new String[]{"ENDSUBENT", "NOSUBENT"}), new x4syskw(6, "ENDSUBENT", new String[]{"SUBENT", "ENDENTRY", "NOSUBENT"}, new String[]{"ENDDATA", "NODATA"}), new x4syskw(7, "NOSUBENT", new String[]{"SUBENT", "ENDENTRY", "NOSUBENT"}, new String[]{"ENDSUBENT", "NOSUBENT"}), new x4syskw(8, "BIB", new String[]{"-bib-"}, new String[]{"SUBENT"}), new x4syskw(9, "ENDBIB", new String[]{"COMMON", "NOCOMMON"}, new String[]{"-bib-"}), new x4syskw(10, "NOBIB", new String[]{"COMMON", "NOCOMMON"}, new String[]{"SUBENT"}), new x4syskw(11, "COMMON", new String[]{"-common-"}, new String[]{"ENDBIB", "NOBIB"}), new x4syskw(12, "ENDCOMMON", new String[]{"DATA", "NODATA"}, new String[]{"-common-"}), new x4syskw(13, "NOCOMMON", new String[]{"DATA", "NODATA"}, new String[]{"ENDBIB", "NOBIB"}), new x4syskw(14, "DATA", new String[]{"-data-"}, new String[]{"ENDCOMMON", "NOCOMMON"}), new x4syskw(15, "ENDDATA", new String[]{"ENDSUBENT"}, new String[]{"-data-"}), new x4syskw(16, "NODATA", new String[]{"ENDSUBENT"}, new String[]{"ENDCOMMON", "NOCOMMON"})};
        kwBib = new x4syskw[]{new x4syskw(17, "TITLE", "-except-not-rel-", null), new x4syskw(18, "AUTHOR", "-always-", null), new x4syskw(19, "INSTITUTE", "-always-", null), new x4syskw(20, "EXP-YEAR", "", null), new x4syskw(21, "REFERENCE", "-always-", null), new x4syskw(22, "REL-REF", "", null), new x4syskw(23, "MONIT-REF", "", null), new x4syskw(24, "REACTION", "-always-", null), new x4syskw(25, "RESULT", "-whenCodeExists-", null), new x4syskw(26, "MONITOR", "-except-not-rel-", new String[]{"MONIT", "etc."}), new x4syskw(27, "ASSUMED", "", new String[]{"ASSUM", "etc."}), new x4syskw(28, "DECAY-DATA", "", new String[]{"DECAY-FLAG"}), new x4syskw(29, "DECAY-MON ", "", null), new x4syskw(30, "PART-DET  ", "", null), new x4syskw(31, "RAD-DET   ", "", null), new x4syskw(32, "HALF-LIFE ", "", new String[]{"HL1", "etc."}), new x4syskw(33, "EN-SEC    ", "", new String[]{"E1", "etc."}), new x4syskw(34, "EMS-SEC   ", "", new String[]{"EMS1", "etc."}), new x4syskw(35, "LEVEL-PROP", "", new String[]{"LVL-FLAG"}), new x4syskw(36, "MOM-SEC   ", "", new String[]{"M1", "etc."}), new x4syskw(37, "MISC-COL  ", "", new String[]{"MISC", "etc."}), new x4syskw(38, "INC-SOURCE", "", null), new x4syskw(39, "INC-SPECT ", "", new String[]{"EN-DUMMY", "etc."}), new x4syskw(40, "SAMPLE    ", "", null), new x4syskw(41, "METHOD    ", "-one.gr1-", null), new x4syskw(42, "FACILITY  ", "-one.gr1-", null), new x4syskw(43, "ANALYSIS  ", "-one.gr1-", null), new x4syskw(44, "DETECTOR  ", "-one.gr1-", null), new x4syskw(45, "CORRECTION", "", null), new x4syskw(46, "COVARIANCE", "-except-not-rel-", null), new x4syskw(47, "ERR-ANALYS", "", new String[]{"ERR-*", "*-ERR"}), new x4syskw(48, "ADD-RES ", "", null), new x4syskw(49, "COMMENT ", "", null), new x4syskw(50, "CRITIQUE", "", null), new x4syskw(51, "FLAG    ", "", new String[]{"FLAG"}), new x4syskw(52, "STATUS ", "-except-not-rel-", null), new x4syskw(53, "HISTORY", "-always-", null)};
        int n2 = kwEntry.length + kwSub1.length + kwSub2.length + kwBib.length;
        kwAll = new x4syskw[n2];
        int n3 = 0;
        for (n = 0; n < kwEntry.length; ++n) {
            x4syskw.kwEntry[n].iType = 1;
            x4syskw.kwAll[n3] = kwEntry[n];
            ++n3;
        }
        for (n = 0; n < kwSub1.length; ++n) {
            x4syskw.kwSub1[n].iType = 2;
            x4syskw.kwAll[n3] = kwSub1[n];
            ++n3;
        }
        for (n = 0; n < kwSub2.length; ++n) {
            x4syskw.kwSub2[n].iType = 3;
            x4syskw.kwAll[n3] = kwSub2[n];
            ++n3;
        }
        for (n = 0; n < kwBib.length; ++n) {
            x4syskw.kwBib[n].iType = 4;
            x4syskw.kwAll[n3] = kwBib[n];
            ++n3;
        }
    }
}
