package zvv.x4;

/**
 * Sort-3 internal object
 *
 *<pre>
 * Program:         x4sort3.java
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
 *                  NO WARRANTY</pre>
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2021-10-04
 * @since           2021-10-01
 *
 */

public class x4sort3 {
    static boolean extDebug = false;
    Float[][] arr = null;
    int icol1 = 0;
    int icol2 = 0;
    int icol3 = 0;
    int iord1 = -1;
    int iord2 = -1;
    int iord3 = -1;
    long sec0 = 0L;
    long sec1 = 0L;
    boolean orderChanged = false;
    int allPts;
    int movedPts = 0;

    public static void main(String[] stringArray) {
        System.out.println("Hello");
        Float[][] floatArray = new Float[8][3];
        int n = 0;
        floatArray[n][0] = new Float(2.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(54.0f);
        floatArray[++n][0] = new Float(2.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(52.0f);
        floatArray[++n][0] = new Float(2.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(50.0f);
        floatArray[++n][0] = new Float(2.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(48.0f);
        floatArray[++n][0] = new Float(3.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(46.0f);
        floatArray[++n][0] = new Float(3.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(44.0f);
        floatArray[++n][0] = new Float(3.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(42.0f);
        floatArray[++n][0] = new Float(3.0f);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(40.0f);
        ++n;
        x4sort3 x4sort32 = new x4sort3();
        floatArray = new Float[6][3];
        n = 0;
        floatArray[n][0] = new Float(250010.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(54.0f);
        floatArray[++n][0] = new Float(250020.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(52.0f);
        floatArray[++n][0] = new Float(250030.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(50.0f);
        floatArray[++n][0] = new Float(250040.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(48.0f);
        floatArray[++n][0] = new Float(250040.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(46.0f);
        floatArray[++n][0] = new Float(250050.0);
        floatArray[n][1] = new Float(3.0f);
        floatArray[n][2] = new Float(44.0f);
        ++n;
        int[] nArray = x4sort32.sortArray3(floatArray, 0, 0, 0);
        for (int i = 0; i < nArray.length; ++i) {
            System.out.print("|" + nArray[i]);
        }
        System.out.println("");
    }

    x4sort3() {
    }

    public int[] sortArray3(Float[][] floatArray, int n, int n2, int n3) {
        int n4 = floatArray.length;
        int[] nArray = new int[n4];
        for (int i = 0; i < n4; ++i) {
            nArray[i] = i;
        }
        this.arr = floatArray;
        this.icol1 = n;
        this.icol2 = n2;
        this.icol3 = n3;
        this.iord1 = -1;
        this.iord2 = -1;
        this.iord3 = -1;
        if (n == 4) {
            this.iord1 = 1;
        }
        if (n2 == 4) {
            this.iord2 = 1;
        }
        if (n3 == 4) {
            this.iord3 = 1;
        }
        int[] nArray2 = this.sortLinesFast();
        return nArray2;
    }

    public int myCmpFloat(Float f, Float f2) {
        if (f == null && f2 == null) {
            return 0;
        }
        if (f == null) {
            return -1;
        }
        if (f2 == null) {
            return 1;
        }
        if (f.floatValue() < f2.floatValue()) {
            return -1;
        }
        if (f.floatValue() > f2.floatValue()) {
            return 1;
        }
        return 0;
    }

    public int cmp2arr3(int n, int n2) {
        int n3 = 0;
        n3 = this.myCmpFloat(this.arr[n][this.icol1], this.arr[n2][this.icol1]);
        if (n3 != 0) {
            if (this.iord1 == 1) {
                n3 = -n3;
            }
            return n3;
        }
        if (this.icol2 == this.icol1) {
            return n3;
        }
        n3 = this.myCmpFloat(this.arr[n][this.icol2], this.arr[n2][this.icol2]);
        if (n3 != 0) {
            if (this.iord2 == 1) {
                n3 = -n3;
            }
            return n3;
        }
        if (this.icol3 == this.icol2) {
            return n3;
        }
        n3 = this.myCmpFloat(this.arr[n][this.icol3], this.arr[n2][this.icol3]);
        if (n3 != 0) {
            if (this.iord3 == 1) {
                n3 = -n3;
            }
            return n3;
        }
        return n3;
    }

    public int[] sortLinesFast() {
        int n;
        boolean bl = true;
        int n2 = this.arr.length;
        int[] nArray = new int[n2];
        this.orderChanged = false;
        this.allPts = n2;
        this.movedPts = 0;
        this.sec0 = System.nanoTime();
        for (n = 0; n < n2; ++n) {
            nArray[n] = n;
        }
        for (n = 0; n < n2; ++n) {
            int n3;
            int n4 = n;
            if (n == 0) {
                nArray[n] = n4;
                continue;
            }
            int n5 = n;
            if (!bl) {
                for (int i = 0; i < n; ++i) {
                    int n6 = nArray[i];
                    if (this.cmp2arr3(n6, n4) <= 0) continue;
                    n5 = i;
                    break;
                }
            }
            if (bl) {
                if (this.cmp2arr3(nArray[0], n) > 0) {
                    n3 = 0;
                } else if (this.cmp2arr3(nArray[n - 1], n) < 0) {
                    n3 = n;
                } else {
                    int n7;
                    int n8 = 0;
                    int n9 = n - 1;
                    n3 = (n - 1) / 2;
                    do {
                        if ((n7 = this.cmp2arr3(nArray[n3], n)) > 0) {
                            n9 = n3;
                        } else {
                            n8 = n3;
                        }
                        n3 = (n8 + n9) / 2;
                    } while (n9 - n8 > 1);
                    n3 = n9;
                    n7 = this.cmp2arr3(nArray[n3], n);
                    while (n7 == 0 && n3 < n) {
                        n7 = this.cmp2arr3(nArray[++n3], n);
                    }
                }
                n5 = n3;
            }
            if (n5 >= n) continue;
            for (n3 = n - 1; n3 >= n5; --n3) {
                nArray[n3 + 1] = nArray[n3];
            }
            nArray[n5] = n;
            this.orderChanged = true;
            ++this.movedPts;
        }
        this.sec1 = System.nanoTime();
        return nArray;
    }

    public String getDt() {
        return this.getDtNano2();
    }

    public String getDtMilli() {
        String string = "";
        long l = this.sec1 - this.sec0;
        long l2 = l / 1000L;
        long l3 = l % 1000L;
        long l4 = l2 % 60L;
        long l5 = l2 / 3600L;
        long l6 = l2 / 60L % 60L;
        string = l2 + "." + String.format("%03d", l3) + "sec";
        return string;
    }

    public String getDtNano() {
        String string = "";
        long l = (this.sec1 - this.sec0) / 1000L;
        long l2 = l / 1000L;
        long l3 = l2 / 1000L;
        long l4 = l2 % 1000L;
        long l5 = l % 1000000L;
        long l6 = l3 % 60L;
        long l7 = l3 / 3600L;
        long l8 = l3 / 60L % 60L;
        string = l3 + "." + String.format("%06d", l5) + "sec";
        return string;
    }

    public String getDtNano2() {
        String string = "";
        long l = (this.sec1 - this.sec0) / 1000L;
        double d = (double)l / 1000000.0;
        float f = (float)d;
        string = String.format("%f", d) + "sec";
        return string;
    }
}
