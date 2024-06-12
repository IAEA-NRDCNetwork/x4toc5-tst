package zvv.x4;

/**
 * C5-Arrays
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2021-09-28
 * @since           2020-05-04
 *
 * Program:         c5arr.java
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

public class c5arr {
    static final int LXDATA = 13;
    static final int I0ERR = 8;
    static final int LXERR = 4;
    static final int indSys = 8;
    static final int indStat = 9;
    static final int indMrc = 10;
    static final int indTot = 11;
    static final int IROWX4 = 12;
    Float[][] c4data = null;
    int[] c4_MT = null;

    c5arr(int n) {
        this.allocArr(n);
    }

    public int allocArr(int n) {
        try {
            this.c4data = new Float[n][13];
            this.c4_MT = new int[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            System.err.print("\u0007");
            System.err.println("!!!FATAL ERROR!!!\nException: " + outOfMemoryError);
            System.exit(-1);
            return -1;
        }
        return 0;
    }

    public Float getX4iRow(int n) {
        Float f = null;
        if (this.c4data == null) {
            return f;
        }
        if (n >= this.c4data.length) {
            return f;
        }
        if (12 < this.c4data[n].length) {
            f = this.c4data[n][12];
        }
        return f;
    }

    public void getX4iRow(int n, int n2) {
        Float f = null;
        if (this.c4data == null) {
            return;
        }
        if (n >= this.c4data.length) {
            return;
        }
        if (12 < this.c4data[n].length) {
            f = new Float(n2);
        }
        this.c4data[n][12] = f;
    }
}
