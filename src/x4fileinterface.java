package zvv.x4;

/**
 * x4fileinterface interface
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2008-12-03
 * @since           2008-12-03
 *
 * Program:         x4fileinterface.java
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

interface x4fileinterface {
    public void treatExforSubent(x4subent var1, boolean var2);

    public void treatExforEntry(x4subent var1);

    public void treatExforEndentry(x4subent var1, boolean var2);
}
