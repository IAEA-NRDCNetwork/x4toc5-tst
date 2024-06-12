package zvv.x4;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.zip.*;

/**
 * EXFOR-Dictionary-770 internal dictionary
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2019-03-13
 * @since           2013-06-17
 *
 * Program:         x4dict770.java
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

/*
 TRA 201201 x4ref   iaeastd2006      u235nf     ,M.Chadwick+,J,NDS,107,2931,2006   
*/
/*
SELECT distinct Code FROM `x4mysql5nds`.`KEYWORD`
where KeyWord='MONIT-REF'and Code like '%MACKLIN%82%'
',,W,MACKLIN,82'
',MACKLIN,W,MACKLIN,82'
',R.L.Macklin,W,MACKLIN,1982'
*/
/*
SELECT distinct K1.Code,K2.Code FROM KEYWORD as K1
inner join KEYWORD as K2 on K1.SubentID=K2.SubentID
where K1.KeyWord='MONIT-REF'and K1.Code like '%MACKLIN%82%' and K2.KeyWord='MONITOR'
',,W,MACKLIN,82',		'79-AU-197(N,G)79-AU-198,,SIG'
',MACKLIN,W,MACKLIN,82',	'79-AU-197(N,G)79-AU-198,,SIG'
',R.L.Macklin,W,MACKLIN,1982',	'79-AU-197(N,G)79-AU-198,,SIG'
',R.L.Macklin,W,MACKLIN,1982',	'79-AU-197(N,G)79-AU-198,,SIG,,MXW'
*/


public class x4dict770 
extends x4dict
{

    static PrintWriter sysOut=new PrintWriter(System.out,true);
//  static boolean extDebug=true;
    static boolean extDebug=false;

    static Vector vDict=null;

    public static void main (String args[]) {
        int i, ii, nStr, iStr, acc;
        String  str, str1, str2, str3;
//	String  dictExtension=null;

	sysOut=new PrintWriter(System.out,true);

        sysOut.println(" Reading an EXFOR-Dictionary file");
        sysOut.println(" V.Zerkin, IAEA, Vienna, 2011-2013\n");
        if (args.length<1) {
            sysOut.println(
            "Run:      $ java x4dict770 dictExtension");
            System.exit(0);
        }

	i=0;
//	if (i<args.length) dictExtension=args[i++];

//	x4dict.setDirOfArchivedDicts("C:/x4load5/x4dict/");
	x4dict.setDirOfArchivedDicts("x4dict/");
//	x4dict.setDirOfArchivedDicts("x4dict9101/");

	init();
	x4dict770 x4f;
//	x4dict770 x4f=new x4dict770();
//	x4f.readDict(dictExtension);
	str=",M.Chadwick+,J,NDS,107,2931,2006";
	boolean chk=x4dict770.exists(770,str);
        sysOut.println(" ["+str+"]: chk="+chk);
        sysOut.println(" [qqq]:      chk="+x4dict.exists(770,"qqq"));
	x4f=(x4dict770)findinx4dict(770,str);
//	if (x4f!=null) sysOut.println(" x4f="+x4f.toString());
	x4f=(x4dict770)findinx4dict(770,"AMIN");
	if (x4f!=null) sysOut.println(" x4f="+x4f.toString());
	str="GEV/A";
	str=",M.Chadwick+,J,NDS,107,2931,2006";
        sysOut.print("...["+str+"]:");
        sysOut.print(" libdir="+x4dict770.getLibdir(str));
        sysOut.print(" react="+x4dict770.getReac(str));
        sysOut.println("");
	str=str+"xx";
        sysOut.print("...["+str+"]:");
        sysOut.print(" libdir="+x4dict770.getLibdir(str));
        sysOut.print(" react="+x4dict770.getReac(str));
        sysOut.println("");

//MONIT-REF  (,,3,ENDF/B-VII.0,,2006)
	str=",,3,ENDF/B-VII.0,,2006";
        sysOut.print("...["+str+"]:");
        sysOut.print(" libdir="+x4dict770.getLibdir(str));
        sysOut.print(" react="+x4dict770.getReac(str));
        sysOut.println("");

//	printCurrentDicts();
//	printDict(770);
    }


    public static void setPrintWriter(PrintWriter out)
    {
	sysOut=out;
    }

/*
*/



    String libdir="";
    String react="";
    String Monit_Ref="";

/*
 TRA 201201 x4ref   iaeastd2006      u235nf     ,M.Chadwick+,J,NDS,107,2931,2006   
*/

    x4dict770()
    {
    }

    x4dict770(String str)
    {
	super(str);
	String str1;
	Float  rr;
	int ind, ind2;
	libdir   =strExtractStr(str,21,37,"");
	react    =strExtractStr(str,38,48,"");
	Monit_Ref=strExtractStr(str,49,49+55-1,"");

	super.Code=Monit_Ref;
//	super.statusFlag="";
//	super.strDate="";
//	super.shortHelp="";
//	sysOut.println("---x4dict770()---["+super.Code+"]"+vDict.size());

    }

    public x4dict x4dict_new(String str)
    {
	x4dict770 x4d;
	if (extDebug)
	sysOut.println(" x4dict770.x4dict_new="+str.trim());
	x4d=new x4dict770(str);
	return (x4dict)x4d;
    }

    public static x4dict770 findinx4dict(String code)
    {
	init();
	x4dict770 x4f=(x4dict770)findinx4dict(code,vDict);
	//sysOut.println("---findinx4dict:770()---["+code+"]"+vDict.size()+" "+x4f);
	return x4f;
    }

    public static String getLibdir(String code)
    {
	x4dict770 x4f=findinx4dict(code);
	if (x4f==null) return "?";
	return x4f.libdir;
    }

    public static String getReac(String code)
    {
	x4dict770 x4f=findinx4dict(code);
	if (x4f==null) return "?";
	return x4f.react;
    }


/*
    public static String getExpansion(String code)
    {
	x4dict770 x4f=findinx4dict(code);
//	if (x4f==null) return null;
	if (x4f==null) return "";
	return x4f.shortHelp;
    }
*/
    public static void init()
    {
	if (vDict==null) {
	    if (extDebug)
	    sysOut.println("---init770()---");
	    x4dict770 x4f;
	    x4f=new x4dict770();
	    String name=x4f.getFileName(770);
	    vDict=x4f.readExforDict(name);
	    hashDicts.put(name,vDict);
	}
    }

    public boolean isItKeyStr(String str)
    {
	String str1;
//	sysOut.println("...isItKeyStr:str=["+str+"]");
	str1=strExtractStr(str,49,49+55-1,"");
//	sysOut.println("...isItKeyStr:str=["+str1+"]");
	if (str1.equals("")) return false;
	return true;
    }

    public String toString()
    {
	String strOut;
	strOut="---x4dict770: "
//	+" Code="	+strpad(Code,28)+" "
	+" "+" libdir    ".trim()+"="	+strpad(""+libdir   ,8)
	+" "+" react     ".trim()+"="	+strpad(""+react    ,8)
	+" "+" Monit_Ref ".trim()+"="	+strpad(""+Monit_Ref,13)
	+" ---x4dict: "
	+super.toString()
	;
	return strOut;
    }

}
