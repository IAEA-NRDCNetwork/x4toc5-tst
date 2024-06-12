package zvv.x4;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Output file: internal object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2015-02-13
 * @since           2013-03-01
 *
 * Program:         x4outfile.java
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

public class x4outfile {
    BufferedWriter o3 = null;
    PrintWriter o3prn = null;
    String outTxtFileName = "x4refdif.txt";
    int nstrOut = 0;

    x4outfile(String string) {
        if (string != null) {
            this.o3 = this.openOutFile(string);
        }
        this.outTxtFileName = string;
    }

    x4outfile(String string, boolean bl) {
        if (string != null) {
            this.o3 = this.openOutFile(string, bl);
        }
        this.outTxtFileName = string;
    }

    public PrintWriter getPrintWriter() {
        if (this.o3 == null) {
            return null;
        }
        if (this.o3prn != null) {
            return this.o3prn;
        }
        this.o3prn = new PrintWriter(this.o3);
        return this.o3prn;
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

    public int close() {
        try {
            this.o3prn.flush();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.o3prn != null) {
            try {
                this.o3prn.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.o3prn = null;
        if (this.o3 == null) {
            return -2;
        }
        try {
            this.o3.close();
            this.o3 = null;
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public int write(String string) {
        try {
            this.o3.write(string);
            return 0;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    public int writeln(String string) {
        try {
            this.o3.write(string);
            this.o3.newLine();
            this.o3.flush();
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
}
