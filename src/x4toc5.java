package zvv.x4;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.zip.*;
import java.text.*;
import java.net.*;

/**
 * Translate EXFOR to computational format C5
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2024-06-10
 * @since           2012-05-18
 *
 * <pre>
 * Program:         x4toc5.java
 * Author:          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
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
 */

/*
	History:
	2019-04-23	-nomt0	flag to suppress converting Datasets without known MT
	2019-06-10	processing SF4 as ELEM/MASS when [7] is required in the Family_Independent_Variables
	2019-07-11	added #METHOD
	2019-07-16	x4cvar: remove from vVars x4var with wrong units, e.g. O0226015:<ERR-3,ADEG>
*/

public class x4toc5
implements x4fileinterface
{

    static PrintWriter sysOut0=new PrintWriter(System.out,true);
    PrintWriter sysOut=null;
    boolean extDebug=false;
    boolean extDebug1invda=false;
    boolean extDebug1invda2=false;

//  static String strVersion="2019-04-23";
//  static String strVersion="2019-06-10";
//  static String strVersion="2019-07-18";
//  static String strVersion="2019-11-26";
//  static String strVersion="2020-04-12";
//  static String strVersion="2020-04-17";//if errSys=0: errSys=sum(err-1,err-2,..)
//  static String strVersion="2020-04-20";//added: -ds:file with DatasetIDs
//  static String strVersion="2020-05-05";//added:dYGiven dTotal,dTotal%
//  static String strVersion="2020-05-11";//DATA-ERR given in COMMON is equivalent to ERR-SYS
//  static String strVersion="2020-05-14";//
//  static String strVersion="2020-05-18";//If (ERR-?,,,P) are given use it as MERC
//  static String strVersion="2020-05-22";//fillC4err(): join sys+mrc ... split sys-mrc
//  static String strVersion="2020-06-03";//added: #C4BEGIN, #DATA-HDR
//  static String strVersion="2020-07-09";//added filter: listOfProducts
//  static String strVersion="2020-09-09";//added COMMON from ERR-ANALYS (ERR-*,min,max): 13901005.x4
//  static String strVersion="2020-09-25";//default: interpolation of incomplete C5-stat and C5-sys
//  static String strVersion="2020-09-30";//x4reaction:LEG/RTH:not not verted to B/SR; RTH-B/SR:180->179.999
//  static String strVersion="2020-10-01";//2020-10-01:out message about default: convertRutherfordRatio2xs=true
//  static String strVersion="2020-10-09";//2020-10-09:blocks MONIT-ERR if not PER-CENT; todo:convert to PER-CENT if MONIT is given
//  static String strVersion="2020-10-12";//2020-10-12: MONIT-ERR converted to PER-CENT if MONIT is given only for COMMON, ex: 10135002
//  static String strVersion="2020-10-13";//2020-10-13: MONIT-ERR converted to PER-CENT if MONIT is given impl. for DATA, e.g. 10536067
					  //2020-10-13:Errors for 13744003:40-ZR-0(N,TOT),,SIG,,RAW:: DATA,NO-DIM; DATA-ERR,NO-DIM
//  static String strVersion="2020-10-20";//2020-10-20: added #Headers: dData, dCos, dLVL
//  static String strVersion="2020-11-17";//2020-11-17: added renormalization by M0/M1
//  static String strVersion="2020-11-27";//2020-11-27:
//  static String strVersion="2020-12-14";//2020-12-14: added renormalization by 511 DECAY-DATA and DECAY-MON
//  static String strVersion="2021-04-08";//2021-04-08: replace reac_vec_xvar.xvar having duplicating HEADER
//  static String strVersion="2021-04-19";//2021-04-19: #DATE->8 digits, Summary:nTotalPoints
//  static String strVersion="2021-04-26";//2021-04-26: MT9000 Gamma production to data[5]
//  static String strVersion="2021-06-09";//2021-06-09: E-EXC-CMP --> EN-CM --> EN-LAB
					  //(E,X)0-NN-1:: E.za=-1000, MT9000,0-NN-1:DATA[5]>1.9
//  static String strVersion="2021-07-01";//2021-07-01: decay-data DG renormalization monitors\decay\58-CE-143.g 21647004.x4 +20%
//  static String strVersion="2021-09-16";//2021-09-16: replace Q-Value by E-Level (for partial reactions):10391013.x4
					  //several E-LVL: put min,Max to C5:c4_data[6],c4_data[7]:13160016.x4
//  static String strVersion="2021-09-17";//2021-09-17: -ren:mon,dec added option for renormalize by decay data: 13597002.x4
//  static String strVersion="2021-09-24";//2021-09-24: ERR-ANALYS:(ERR-6,,0.5) -> ADDED AS COMMON:ERR-6,PER-CENT=0.25%: 41455005.x4
//  static String strVersion="2021-09-28";//LVL: set MT from 51 to 50+LVL:10519003.x4 flag:-mt51
//  static String strVersion="2021-09-30";//Sort: MF3,203:6,0; MF4,204:6,4,0; MF5,205:0,4,6; MF6,206:0,4,6 where: 0:EN,4:-COS,6:E2
					  //MF3,MT51-MT63: 10519003.x4
					  //MF4,MT601:     A1495003.x4
					  //MF5,MT9000:    22158003.x4
					  //MF6,MT9000:    22075113.x4
//  static String strVersion="2021-10-01";//Sort: fast sort 22331004.x4 pt:49709 17sec --> 0.5sec
//  static String strVersion="2021-10-05";//Sort: dTime in microsec
					  //debug on data wholes:java x4toc5 C1978008.x4 -norr
//  static String strVersion="2021-10-06";//E-LVL-MIN,E-LVL-MAX: put min,max to C5:c4_data[6],c4_data[7]:10037063.x4
//  static String strVersion="2021-10-07";//x4DecayGammaFile.java: reading Lara data with intensity in ():
					  // 58-CE-139: 165.8575 ; 0.0011 ; (79.90)
//  static String strVersion="2021-10-08";//delEndSpace when output str to C5 file
//  static String strVersion="2021-10-12";//#XCORR:nPnt/nRow
					  //ttout for unknown MF, MT: 10023002.x4
					  //proc: reactions having )=( : select one with MT>0
//  static String strVersion="2021-10-19";//#XCORR: added monRef0: 14209005.x4
//  static String strVersion="2021-11-15";//Process TEMP as ind.var. 41623002.x4 D[6],I78
					  //#CompNotes  CONVERT TEMP: DEG-C TO K  added:273.16 30776014.x4
//  static String strVersion="2021-11-16";//Put Prod1:Data[4],[5] for (*,F) reactions: O2017002.x4
//  static String strVersion="2021-11-24";//ADEG+AMIN -> ADEG.DD C1023002.x4
					  //??FERMI -> BARN //java x4toc5 V1002177.x4 -dict:x4dict/
//  static String strVersion="2021-12-15";//2021-12-15:D0999002.x4 
					  //(ERR-SYS,,8.) ERR-1:7% ERR-2:1% ERR-3:2% ERR1+ERR2+ERR3=7.35% -> set err-sys from 4% to 7.35%
//  static String strVersion="2022-01-07";//2022-01-07: changed dm0 and dm1 from relative to absolute values
//  static String strVersion="2022-01-13";//2022-01-13: introduced x4recation.convert_Einc2Lab=true;
//  static String strVersion="2022-01-14";//2022-01-14: debug inverse reaction calc.
//  static String strVersion="2022-01-20";//2022-01-20: Warning: Theta-Lab ambiguity.
					  //Conversion Theta-CM to Lab for M2>M1: Theta-CM exceeds max=115.40deg pt:121
					  //D0994002.x4: 6-C-12(14-SI-28,EL)6-C-12,,DA,,RTH
//  static String strVersion="2022-01-26";//Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR). Warning: Algorithm changed
					  //D0994002.x4: 6-C-12(14-SI-28,EL)6-C-12,,DA,,RTH
//  static String strVersion="2022-01-27";//Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR).
					  //myRuth.java: M1>M2: Algorithm=2
					  //D0994002.x4: 6-C-12(14-SI-28,EL)6-C-12,,DA,,RTH
//  static String strVersion="2022-03-23";//Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR).
//  static String strVersion="2022-04-07";//MT>9000 - set MT=9000: D4335005
//  static String strVersion="2022-04-22";//REACTION:92-U-233(A,F)MASS,CHN,SIG  MF3-->MF803 ReactionType=FYC
//  static String strVersion="2022-07-20";//MinErr=new Float(0);//,,10: ave=5, impact to err-tot:2 ::33144001
					  //(ERR-7,,10.) dead time of the detector system (<10%)
					  //ERR-ANALYS:(ERR-7,,10.)       ADDED AS COMMON:ERR-7,PER-CENT=5.0%
//  static String strVersion="2022-08-08";//added: EXFOR database version from REQUEST
//  static String strVersion="2022-08-10";//#YEAR taken from sub2:40461005
//  static String strVersion="2022-09-07";//blocked:--- if sum of partial errors > error: replace error by quadtaric sum of 
					  //blocked because of 22414016:(ERR-8,,42.9) Reaction Gamma-decay branching factor error
//  static String strVersion="2022-09-16";//OpNum=4: LEG/RS Data=Data/(2*L+1): 11749005.x4 L:Data[4]
//  static String strVersion="2023-02-13";//2023-02-13:O2150002:SF8=SFC::39-Y-89(P,G)40-ZR-90,,SIG,,SFC
					  //S-factor-->Cross section; units:B*E-->B
//  static String strVersion="2023-02-23";//2023-02-22::SF8=REL::MT:+600
					  //20230222::12551004.x4: TRA 198202 RTE    16     times square-root(E)
					  //2023-02-22:O2388003:REACTION   (1-H-2(P,G)2-HE-3,,SIG,,D4PI) data*4*pi
					  //20230223::A0945004.x4:WARNING EN=0 OPERATION FAILED...DATA CONVERTED S-FACTORS TO BARNS:=0
//  static String strVersion="2023-02-28";////2023-02-22::SF6=FY-->MF:=8 modif:x4c4obj.java
//  static String strVersion="2023-03-13";//ERR-ANALYS:(ERR-SYS,10.,15.) always take min --> ADDED AS COMMON:ERR-SYS,PER-CENT=10.0%
//  static String strVersion="2023-04-13";//comment data lines with DATA-MAX, DATA-MIN, DATA-APRX  O2049009.x4, 41521.x4
//  static String strVersion="2023-04-15";//uncomment data lines with DATA-MAX, DATA-MIN for Resonance energy
					  //10266008.x4 //REACTION:1(94-PU-242(N,0),,EN) DATA:DATA-MIN:1 DATA-MAX:1
//  static String strVersion="2023-07-14";//EN-RSL:MICROSEC/M --> EV  20358002.x4
//  static String strVersion="2023-08-03";//-split:x4outDir
//  static String strVersion="2023-08-04";//sorting order corrected for MF5,205: 6,4,0 --> 4,0,6
//  static String strVersion="2023-08-08";//introduced FcErr to C5 output
//  static String strVersion="2023-08-11";//extended to collect statistics: #/C5CORR DatasetID FcMonAve FcDecayDat FcDecayMon
//  static String strVersion="2023-08-17";//nTotalDatasetsTotal, nTotalDatasetsConvertedTotal
//  static String strVersion="2023-08-28";//convertRutherfordRatio2xs,!tryToConvertCM2Lab, ANG-CM: apply Algotithm=0::X4TOC4::D0147.x4
//  static String strVersion="2023-09-08";//#DATASET: added upd from SUBENT: sub2.x4sys.strN2
    static String strVersion="2024-06-10";//cosmetic before release

    static String strVerDevYears="2010-2024";

    static boolean showComputerName=true;

    public static void main(String args[]) {
        int i, ii, nStr, iStr, acc;
        String  str, str1, str2, str3;
        String  x4inName=null;
        String  x4outName=null;
	String  dirDict="x4dict/";
	String  dirMon="x4prog/monitors/";
	String  dirLevels=null;
//	dirLevels="C:\\x4web2011tom7\\apache-tomcat-7.0.11\\webapps\\exfor\\x4prog\\Endver2\\Inputs\\levels\\";
//	dirLevels="E:\\x4app\\Endver\\Sources\\Inputs\\levels\\";
	dirLevels="levels/";


//	sysOut=new PrintWriter(System.out,true);

        sysOut0.println(" Translate EXFOR to C5 computational format");
	sysOut0.println(" Program x4toc5, ver. "+strVersion);
        sysOut0.println(" V.Zerkin, IAEA, Vienna, "+strVerDevYears);
//	sysOut0.println("args.length: " + args.length+"\n");
        if (args.length<1) {
//	    sysOut0.println("");
	    printProgramHelp(sysOut0);
            System.exit(0);
        }

	Locale.setDefault(new Locale("en","US"));//2020-06-10

//	x4dict.setDirOfArchivedDicts("C:/x4load5/x4dict/");
//	x4dict.setDirOfArchivedDicts("x4dict9101/");

	i=0;
//	if (i<args.length) x4inName=args[i++];
//	if (i<args.length) x4outName=args[i++];

	x4toc5 x4file=new x4toc5();
//	x4file.extDebug=true;

	sysOut0.print(" Running: "+x4file.getMyDateTime());
	if (showComputerName) {
	    str=getComputerName();
	    if (str!=null) sysOut0.print(" on "+str);
	}
	sysOut0.println("");

//	x4outName=x4inName+".c5";
        for (; i<args.length; i++) {
            str=args[i];
//sysOut0.println("___arg:"+i+"\t["+str+"]");
	    if (str.startsWith("#")) {//ignore
		continue;
	    }
	    if (!str.startsWith("-")) {
		x4inName=str;
		if (x4outName==null) x4outName=x4inName+".c5";
		continue;
	    }
	    if (str.toLowerCase().startsWith("-prods:")) {
		str1=str.substring(7).trim();
		if (!str1.equals(""))
		x4file.readListOfProducts(str1);
		if (x4file.hRequestedNuclides!=null)
		sysOut0.println("\t-products: #"+x4file.nRequestedNuclides);
	    }
	    if (str.toLowerCase().startsWith("-split:")) {
		str1=str.substring(7).trim();
		if (!str1.equals("")) x4file.x4outDir=str1;
		else x4file.x4outDir=null;
		sysOut0.println("\t-split:  "+x4file.x4outDir+" #outdir");
	    }
	    if (str.toLowerCase().startsWith("-dlvl:")) {
		str1=str.substring(6).trim();
		if (!str1.equals("")) {
		    dirLevels=str1;
		    x4level.setDirOfLevelFiles(dirLevels);
		    if (x4file.extDebug)
		    sysOut0.println("...dirLevels:\t["+dirLevels+"]");
		}
	    }
	    if (str.toLowerCase().startsWith("-dict:")) {
		str1=str.substring(6).trim();
		if (!str1.equals("")) {
		dirDict=str1;
		x4dict.setDirOfArchivedDicts(dirDict);
		if (x4file.extDebug)
		sysOut0.println("...dirDict:\t["+dirDict+"]");
		}
	    }
	    if (str.toLowerCase().startsWith("-dz:")) {
		str1=str.substring(4).trim();
		if (!str1.equals("")) {
		    x4dict.setZipOfArchivedDicts(str1);
//		    setZipOfArchivedDicts("x4dict.zip");
//		    setZipOfArchivedDicts("x4dict.zip.bin");
		    if (x4file.extDebug)
		    sysOut0.println("...zipDict:\t["+str1+"]");
		}
	    }

	    if ((str.toLowerCase().startsWith("-h"))
	     || (str.toLowerCase().startsWith("--h"))
	    ) {
		printProgramHelp(sysOut0);
	    }
	    if (str.toLowerCase().equals("-c4")) {
		x4file.outC4only=true;
		if (x4file.extDebug)
		sysOut0.println("...outC4only:\t["+x4file.outC4only+"]");
	    }
	    if (str.toLowerCase().equals("-c5")) {
		x4file.outC5also=true;
		if (x4file.extDebug)
		sysOut0.println("...outC5also:\t["+x4file.outC5also+"]");
	    }
	    if (str.toLowerCase().equals("-i")) {
		x4file.alwaysCalcInverseReact=true;
		sysOut0.println("\t-i	 #inverse reactions");

		x4file.tryToConvertCM2Lab=true;//2018
		//sysOut0.println("\t-cm2lab	 #Try to convert C.M. to Lab.");
	    }
	    if (str.toLowerCase().startsWith("-i:")) {
		str1=str.substring(3).trim();
		if (!str1.equals("")) {
		    x4file.setCalcInverseReactFile(str1);
//		    if (x4file.extDebug)
		    sysOut0.println("\t-i:	 #inverse selected reactions");
//		    sysOut0.println("\t-i:"+str1+"  #Recalculate data for inverse reactions");

		    x4file.tryToConvertCM2Lab=true;//2018
		    //sysOut0.println("\t-cm2lab  #Try to convert C.M. to Lab.");
		}
	    }
	    if (str.toLowerCase().equals("-dbv")) {
		x4file.showDatabaseVersion=true;
		//sysOut0.println("\t-dbv    #Show database version");
	    }
	    if (str.toLowerCase().equals("-cite")) {
		x4file.showCitation=true;
		//sysOut0.println("\t-cite    #Show citation");
	    }
	    if (str.toLowerCase().startsWith("-ds:")) {
		str1=str.substring(4).trim();
		if (!str1.equals("")) {
		    x4file.readDatasetList(str1);
		    sysOut0.print("\t-ds:"+str1+"	 #List of DatasetIDs");
		    if (x4file.vTheOnlyDatasetIDs!=null)
		    sysOut0.print(" L:"+x4file.vTheOnlyDatasetIDs.size());
		    sysOut0.println("");
		}
	    }
	    if (str.toLowerCase().equals("-cm2lab")) {
		x4file.tryToConvertCM2Lab=true;
//		sysOut0.println("...tryToConvertCM2Lab:\t["+x4file.tryToConvertCM2Lab+"]");
//		sysOut0.println("\t-cm2lab  #Try to convert C.M. to Lab.");
		sysOut0.println("\t-cm2lab  #convert C.M. to Lab.");
	    }
//2020	    if (str.toLowerCase().equals("-mon")) {
	    if (str.toLowerCase().startsWith("-mon")) {
		x4file.flag_outX4mon=true;
		sysOut0.println("\t-mon     #include Monitor data, if any");
	    }

//	    if (str.toLowerCase().equals("-norr")) {
	    if (str.toLowerCase().equals("-norr") || str.toLowerCase().equals("-nrr")) {
		x4file.convertRutherfordRatio2xs=false;
		sysOut0.println("\t-norr  #do not convert RR to MB/SR");
	    }
	    if (str.toLowerCase().equals("-mt51")) {
		x4file.flagMT51to5ilvl=true;
//		sysOut0.println("\t-mt51    #MT51,MT601 set to MT-1+iLvl");
	    }
	    if (str.toLowerCase().equals("-sort")) {
		x4file.flagSort=true;
//		sysOut0.println("\t-sort    #sort C5");
	    }
	    if (str.toLowerCase().equals("-nomt0")) {
		x4file.flagMFMT_gt_0=true;
		sysOut0.println("\t-nomt0   #MT must be >0");
	    }
	    if (str.toLowerCase().equals("-noqe")) {
		x4file.replaceQvalue2Level=false;
		sysOut0.println("\t-noqe    #do not replace Q-Value by E-Level for partial XS");
	    }
	    if (str.toLowerCase().equals("-c5m")) {
		x4file.out_corrMatrix2=true;
//		sysOut0.println("...out_corrMatrix2:\t["+x4file.out_corrMatrix2+"]");
		sysOut0.println("\t-C5M\t #Generate correlation matrix");
	    }
	    if (str.toLowerCase().startsWith("-o:")) {
		str1=str.substring(3);
		x4outName=str1;
		sysOut0.println("\t-o:      "+x4outName+" #output");
	    }
	    if (str.toLowerCase().equals("-debug")) {
		x4file.extDebug=true;
	    }
	    if (str.toLowerCase().equals("-ps")) {
		x4file.flagShowProcess=true;
		//System.out.println("\tShow process:\t["+x4file.flagShowProcess+"]");
	    }
	}

	//2020-10-01:out message about default conversion
//	if (x4file.convertRutherfordRatio2xs) sysOut0.println("\t-yesrr   #convert RR to MB/SR if any");
//	if (x4file.convertRutherfordRatio2xs) sysOut0.println("\t-yrr     #convert RR to MB/SR, if any");
//	if (x4file.convertRutherfordRatio2xs) sysOut0.println("\t-yqe     #replace Q-Value by E-Level (for partial reactions)");
	if (x4file.convertRutherfordRatio2xs) sysOut0.println("\t-rr      #convert Rutherford-Ratio to B/SR");
	if (x4file.replaceQvalue2Level)       sysOut0.println("\t-qe      #replace Q-Value by E-Level (for partial reactions)");
//	if (x4file.flagMT51to5ilvl)           sysOut0.println("\t-mt51    #replace MT by MT+iLevel (for partial reactions)");
	if (x4file.flagMT51to5ilvl)
	sysOut0.println("\t-mt51    #replace MT by MT+iLevel (for MT:51,601,651,701,751,801)");

	if (x4file.flag_renormX4mon)
	{
	    sysOut0.println("\t-ren:mon #renormalize using modern monitor cross sections data");
	}

	if (x4file.flagSort)                  sysOut0.println("\t-sort    #sort C5 file");

//?	sysOut0.println("");

//	sysOut0.println("---dirDict:\t["+dirDict+"]");

	x4dict.setDirOfArchivedDicts(dirDict);

	if (x4outName==null) x4outName=x4inName+".c5";

	if (x4file.extDebug)
	sysOut0.println("---Converting Exfor File: ["+x4inName+"]"+" to "+"["+x4outName+"]");
	x4file.setPrintWriter(sysOut0);

//	if (extDebug) x4c4obj.extDebug=extDebug;
//	x4file.readExforFile(x4inName,x4outName,"My EXFOR file");
	if (x4inName!=null) {
	    if (x4file.out_corrMatrix2)
		 x4file.readExforFile(x4inName,x4outName,"C5M");
	    else x4file.readExforFile(x4inName,x4outName,"C5");
	}
//	x4file.readExforFile(x4inName,x4outName,"X4TOC5");
//	x4file.readExforFile(x4inName,x4outName,"C5REQUEST");

	sysOut0.print(" Program finished: "+x4file.getMyDateTime());
	sysOut0.println("");

    }


    public static void printProgramHelp(PrintWriter out)
    {
//	out.println(" --help");
	out.println("");
        out.println(" Run:  $ java [flags] x4toc5 file.x4 [options]");
	out.println("");
	out.println(" Options:");
	out.println("   -o:file     output file, default: file.x4.c5");
	out.println("   -dict:dir   directory with EXFOR Dictionaries, default: -dict:x4dict/");
	out.println("   -dlvl:dir   directory with levels,             default: -dlvl:levels/");
	out.println("   -split:dir  output C5 file for every Entry to a file in directory: 1/123/12345.c5");
	out.println("   -c5         output format: C5 (default)");
	out.println("   -c5m        output format: C5M (C5+correlation matrix)");
	out.println("   -c4         output format: C4");
	out.println("   -i          recalculate data to inverse reactions, e.g.:");
	out.println("                  6-C-13(A,N)8-O-16,,SIG --> 8-O-16(N,A)6-C-13,,SIG");
	out.println("                  2-HE-4(P,D)2-HE-3,,DA  --> 2-HE-3(D,P)2-HE-4,,DA");
	out.println("   -i:file     inverse data for the reactions listed in the file");
	out.println("   -cm2lab     convert EN-CM, ANG-CM, DATA-CM from C.M. to Lab. (MF4 only)");
	out.println("   -norr       do not convert DATA: Rutherford-Ratio to B/SR");
	out.println("   -noqe       do not replace Q-Value by E-Level for partial XS");
	out.println("   -mt51       replace MT by MT+iLevel (for MT:51,601,651,701,751,801)");
	out.println("   -nomt0      do not process Datasets with unknown MT");
	out.println("   -sort       sort data by independent variables (EN, AN, E2)");
	out.println("   -h[elp]     print this text");
	out.println("   -ps         show process");
	out.println("   -debug      set debug mode");
	out.println(" Java flags:");
	out.println("   -Xmx<size>  set maximum Java heap size");
	out.println("   -cp <paths> list of directories, JAR archives to search for class files");
	out.println("   -jar <path> file with binaries (archive of classes - compiled java codes)");
	out.println("");
	out.println(" Examples:");
	out.println("   $ java x4toc5 myfile.x4");
	out.println("   $ java x4toc5 x4.x4 -dict:x4dict/");
//	out.println("   $ java -Xmx400M -cp x4toc5.jar zvv.x4.x4toc5 x4.x4");
	out.println("   $ java -Xmx400M -jar x4toc5.jar x4.x4 -i -o:x4.x4.c5i");
    }

    public void setPrintWriter(PrintWriter out)
    {
	sysOut=out;
    }
    public void sysOut_println(String str)
    {
	if (sysOut!=null) sysOut.println(str);
    }
    public void sysOut_print(String str)
    {
	if (sysOut!=null) sysOut.print(str);
    }





    x4outfile o5=null;
    PrintWriter o5prn=null;
    PrintWriter o5lst=null;
    PrintWriter o5lstR=null;
    String x4inName="";
    String x4outName="";
    boolean flagBib1Out=false;
    boolean flagBibxOut=false;
    boolean outC4only=false;
    boolean outC5also=false;
    boolean outC4andC5=false;

    boolean flagShowProcess=false;//2020-09-25
    long seconds0=0, seconds1=0;

    boolean tryToConvertCM2Lab=false;//2015:todo
    boolean convertRutherfordRatio2xs=true;//2019-06-26
    boolean replaceQvalue2Level=true;//2021-09-16

    boolean alwaysCalcInverseReact=false;//2015
    boolean onlyCalcInverseReact=false;//2015

    boolean outSysError=true;
//  boolean out_iRow=true;
    boolean out_iRow=false;
    boolean tryToRecoverEnresFromEn=true;
    String  theOnlyDatasetID=null;
    Vector  vTheOnlyDatasetIDs=null;

    boolean out_corrMatrix2=false;
    boolean flag_fixed_widthC5=true;
//    boolean flag_fixed_widthC5=false;

    boolean flagMFMT_gt_0=false;//check MF>0 (MT can be==0) MT0:true
//  boolean flagMFMT_gt_0=true; //check MF>0 and MT>0       MT0:false
    boolean flagMT51to5ilvl=false;//2021-09-28
    boolean flagSort=false;//2021-09-30

    String x4Title="";
    String strDatabaseDate=null;
    String strX4inDate=null;
    boolean showDatabaseVersion=false;
    boolean showCitation=false;
    int nTotalEntry=0;
    int nTotalEntryConverted=0;
    int nTotalSubent=0;
    int nTotalSubentConverted=0;
    int nTotalDatasets=0;
    int nTotalDatasetsTotal=0;
    int nTotalDatasetsConverted=0;
    int nTotalDatasetsConvertedTotal=0;
    int nEntryDatasets=0;
    int nEntryDatasetsConverted=0;
    int nTotMFok=0;
    int nTotMTok=0;
    int nTotalCM2LABconverted=0;
    int nTotalVectorCommon=0;
    long nPointsTotal=0;
    long nPointsSubent=0;
    long nTotalC4Datalines=0;
    long nTotalC4DatalinesTotal=0;
    Vector vecMT0=new Vector();

    String strDatasetBegin=
"#============================================================================="
    ;

    x4toc5()
    {
    }

    public void outCorrMatrix(boolean outmatrix)
    {
	out_corrMatrix2=outmatrix;
    }

    public void outC4headerLine(PrintWriter o5prn, String str)
    {
	if (!outC4only)
	o5prn.println(str);
    }
    public void addC4headerLine(PrintWriter o5prn, String str0,String str1)
    {
	String strs[]=new String[2];
	strs[0]=str0;
	strs[1]=str1;
	headerStrings.addElement(strs);
	//outC4headerLine(o5prn,str);
    }
    public void printDatasetHeader(PrintWriter o5prn)
    {
	int iih,lnn=16,ii;
	String strs[];
	String str0,str1,str,strunits;
	for (iih=0; iih<headerStrings.size(); iih++) {
	    strs=(String[])headerStrings.elementAt(iih);
	    str0=strs[0];
	    str1=strs[1];
	    str=strpad(str0,lnn)+str1;
	    outC4headerLine(o5prn,str);
	}

/*2020:test
	if (c4obj!=null)
	outC4headerLine(o5prn,"#CompNotes:"+c4obj.compVars.length);
	for (int i1=0; i1<c4obj.compVars.length; i1++) {
	    for (int i2=0; i2<c4obj.compVars[i1].vCompNotes.size(); i2++) {
		String str1c=(String)c4obj.compVars[i1].vCompNotes.elementAt(i2);
		//sysOut.println("\t"+str1);
		outC4headerLine(o5prn,"#CompNotes::"+str1c);
	    }
	}
*/
	if (headerStrings.size()>0)
	if (c4obj!=null)
	{
	    if (!c4obj.strBoolCmLab.equals(c4obj.strBoolCmLabNew))
	    outC4headerLine(o5prn,strpad("#C.M.Flag",lnn)+c4obj.strBoolCmLabNew);
	    else
	    outC4headerLine(o5prn,strpad("#C.M.Flag",lnn)+c4obj.strBoolCmLab+" "+c4obj.c4_X4CM+"  "+c4obj.strDataCmLab);
//	    outC4headerLine(o5prn,c4obj.strLegend1);

	    str0="";str1="";
	    for (ii=0; ii<c4obj.c4_DataFamFlag.length; ii++) {
		if (c4obj.c4_DataFamFlag[ii]==null) {
		    str0+=strpad("",9);
		    str1+=strpad("",9);
		}
		else {
//		    str0+=strpad(c4obj.c4_DataFamFlag[ii],9);
//		    str1+=strpad(c4obj.c4_DataUnits[ii],9);
		    str0+=padstr(c4obj.c4_DataFamFlag[ii],9);
/*20230223
if ((ii==2)&&c4obj.mf4tomf6) str1+=padstr("B/SR/EV",9); else
if ((ii==2)&&(c4obj.c4_DataUnits[ii].equals("B*RT-EV"))) str1+=padstr("B",9); else
//if ((ii==2)&&(c4obj.c4_DataUnits[ii].equals("M"))) str1+=padstr("B",9); else //FERMI
		    str1+=padstr(c4obj.c4_DataUnits[ii],9);
*/
		    strunits=padstr(c4obj.c4_DataUnits[ii],9);
//if (ii==2) {
if ((ii==2)||(ii==3)) {
//System.out.println("::c4obj.compVars[0].vCompNotes.size():"+c4obj.compVars[0].vCompNotes.size());
//System.out.println("::c4obj.compVars[0].convUnits:"+c4obj.compVars[0].convUnits);
    if (c4obj.mf4tomf6) strunits=padstr("B/SR/EV",9); else
//    if (c4obj.xreacode!=null)
//    if (c4obj.xreacode.BasicUnitsFinal.equals("?"))
//    strunits=c4obj.xreacode.BasicUnitsFinal;
    if (c4obj.compVars!=null)
    if (c4obj.compVars[0].convUnits!=null)
    strunits=padstr(c4obj.compVars[0].convUnits,9);
}
		    str1+=strunits;
		}
//		System.out.println("...c4_Data["+ii+"]"
//			+" strFamFlag=["+c4obj.c4_DataFamFlag[ii]+"]"
//			+" BasicUnits=["+c4obj.c4_DataUnits[ii]+"]"
//			);
	    }
	    outC4headerLine(o5prn,strpad("#Headers",22)+str0);
	    outC4headerLine(o5prn,strpad("#Units",22)+str1);

	    outC4headerLine(o5prn,c4obj.strLegend1);
	    outC4headerLine(o5prn,c4obj.strLegend2);
	}
    }


    public void openMainC5Output(String x4outName)
    {
	String str;
	int nEntries=0,i,ii,isub;
	o5=new x4outfile(x4outName);
	o5prn=o5.getPrintWriter();
	if (extDebug)
	sysOut_println("...o5prn=["+o5prn+"]...x4outName=["+x4outName+"]");

	outC4headerLine(o5prn,strpad("#"+x4Title,16)+getMyDateTime2()
	+(strDatabaseDate==null? "" : "    "+myStrReplace(strDatabaseDate,"-",""))
	);
//	outC4headerLine(o5prn,"#_______________File generated by program: x4toc5, ver."+strVersion+", author:V.Zerkin@iaea.org, IAEA-NDS, "+strVerDevYears);
	outC4headerLine(o5prn,"#_______________File generated:             "+getMyDateTime());
//	outC4headerLine(o5prn,"#_________________by program:               x4toc5, ver."+strVersion+", author:V.Zerkin@iaea.org, IAEA-NDS, "+strVerDevYears);
	outC4headerLine(o5prn,"#_________________by program x4toc5:        "+strVersion+", author:V.Zerkin@iaea.org, IAEA-NDS, "+strVerDevYears);

	outC4headerLine(o5prn,"#_______________Data source:");

	if (showDatabaseVersion&&(strDatabaseDate!=null)) {
		outC4headerLine(o5prn,"#_________________EXFOR library file:       "+strDatabaseDate+" International Network of Nuclear Reaction Data Centres (NRDC)");
	}
	else
	if (strX4inDate!=null) {
		outC4headerLine(o5prn,"#_________________EXFOR file:               "+strX4inDate);
	}

//	outC4headerLine(o5prn,"#_________________EXFOR dictionaries:       "+x4dict.versionOfArchiveDicts  +" NRDC, V.McLane(NNDC), O.Schwerer(IAEA), N.Otsuka(IAEA)");
//	outC4headerLine(o5prn,"#_________________X4TOC4:MF/MT dictionary:  "+x4dict.versionOfArchiveDict714+" D.Cullen(IAEA), A.Trkov(IAEA), V.Zerkin(IAEA), Empire-team");
//	outC4headerLine(o5prn,"#_________________X4SF:MF/MT/LR dictionary: "+x4dict.versionOfArchiveDict715+" V.Pronyaev(IAEA,IPPE)");
	outC4headerLine(o5prn,"#_________________EXFOR dictionaries:       "+x4dict.versionOfArchiveDicts  +" "+x4dict.creatorOfArchiveDicts);
	outC4headerLine(o5prn,"#_________________X4TOC4:MF/MT dictionary:  "+x4dict.versionOfArchiveDict714+" "+x4dict.creatorOfArchiveDict714);
	outC4headerLine(o5prn,"#_________________X4SF:MF/MT/LR dictionary: "+x4dict.versionOfArchiveDict715+" "+x4dict.creatorOfArchiveDict715);

	if (!x4level.versionOfLevelFiles.equals(""))
	if (!x4level.versionOfLevelFiles.equals("1970-01-01"))
//	outC4headerLine(o5prn,"#_________________Levels/RIPL:              "+x4level.versionOfLevelFiles+" IAEA-CRP");
//	outC4headerLine(o5prn,"#_________________Nuclear Levels/RIPL:      "+x4level.versionOfLevelFiles+" IAEA Reference Input Parameter Library");
	outC4headerLine(o5prn,"#_________________Nuclear levels/RIPL:      "+x4level.versionOfLevelFiles+" "+x4level.creatorOfLevelFiles);

	if (showDatabaseVersion) {
		outC4headerLine(o5prn,"#_______________Copyright:");
		outC4headerLine(o5prn,"#_________________1. Data source EXFOR: (c) 1970-2023, International Network of Nuclear Reaction Data Centres (NRDC)");
		outC4headerLine(o5prn,"#_________________2. Program x4toc5:    (c) "+strVerDevYears+", International Atomic Energy Agency (IAEA)");
	}
	else {
		outC4headerLine(o5prn,"#_______________Copyright: x4toc5 (c) "+strVerDevYears+", International Atomic Energy Agency (IAEA)");
	}

//	outC4headerLine(o5prn,"#_______________License: Creative Commons - Attribution 4.0 International (CC BY 4.0)");
//	outC4headerLine(o5prn,"#________________________https://creativecommons.org/licenses/by/4.0/");
	outC4headerLine(o5prn,"#_______________License: ");
	outC4headerLine(o5prn,"#_________________Creative Commons - Attribution 4.0 International (CC BY 4.0)");

    }
    public void closeMainC5Output()
    {
	if (o5==null) return;
	int nn=nTotalEntry;
	if (x4outDir!=null) nn=1;
	outC4headerLine(o5prn,strpad("#/"+x4Title,16)
			+strpad(""+nn,16)
			+strpad(""+nTotalDatasetsConverted,16)
			+strpad(""+nTotalDatasets,16)
			+strpad(""+nTotalCM2LABconverted,16)
			+strpad(""+nTotalC4Datalines,16)
	);
//	sysOut_println("...o5prn=["+o5prn+"]");
	o5prn.flush();
	o5.close();
	o5=null;
    }

    String  x4outDir=null;
    String  x4outDirEntry=null;
    String  x4outDirEntryDateModif=null;
    public void openMainC5OutputFile(String x4outName)
    {
	if (x4outDir==null)
	openMainC5Output(x4outName);
    }
    public void closeMainC5OutputFile()
    {
	if (x4outDir==null)
	closeMainC5Output();
    }
    public void openMainC5OutputEntry(String Entry,String strDateModif)
    {
	if (x4outDir==null) return;
	String str=Entry2dir(Entry);
	openMainC5Output(str);
	x4outDirEntry=str;
	x4outDirEntryDateModif=strDateModif;
//	nTotalEntry=0;
	nTotalDatasetsConverted=0;
	nTotalDatasets=0;
	nTotalCM2LABconverted=0;
	nTotalC4Datalines=0;
    }
    public void closeMainC5OutputEntry(String Entry)
    {
	if (x4outDir==null) return;
	closeMainC5Output();
	if (x4outDirEntry!=null) {
	    if (nTotalDatasetsConverted<=0) deleteFile(x4outDirEntry);
	    else {
		if (x4outDirEntryDateModif!=null) 
		setFileDate(x4outDirEntry,x4outDirEntryDateModif);
	    }
	}
    }
    public String Entry2dir(String Entry) {
	String str=x4outDir;
	mkdir(str);
	str+="/"+Entry.substring(0,1);
	mkdir(str);
	str+="/"+Entry.substring(0,3);
	mkdir(str);
	str+="/"+Entry+".c5";
	return str;
    }
    public void mkdir(String fileName)
    {
	File f = new File(fileName);
	if (!f.exists()) {
	    boolean del=f.mkdir();
//	    System.out.println("    ...mkdir: "+fileName+" OK="+del);
	}
    }
    public int setFileDate(String fileName,String strDate)
    {
        File fl;
	String str1;
	java.util.Date dateDate=null;
	int ierr=0;

	if (strDate.length()!=8) return 0;
	String yy=strDate.substring(0,4);
	String mm=strDate.substring(4,6);
	String dd=strDate.substring(6);

        java.util.Date fldate=null;
	fl=new File(fileName);
	fldate=new java.util.Date(fl.lastModified());

	if (mm.equals("00")) mm="01";
	if (dd.equals("00")) dd="01";
	str1=mm+"/"+dd+"/"+yy; //make: 02/18/2004
	try {
	    dateDate=new java.util.Date(str1);
//	    System.out.println(str1+" File:["+fileName+"] date1:["+dateDate+"]");
	    boolean ok=setDateFile(fl,dateDate);
	}
	catch(Exception e) {
	}
	return ierr;
    }
    public boolean setDateFile(File fl,java.util.Date fldate)
    {
	boolean ok=false;
	if (fl.exists()) {
	    ok=fl.setLastModified(fldate.getTime());
	}
	return ok;
    }







    public int readExforFile(String x4inName,String x4outName,String x4Title)
    {
	String str;
	int nEntries=0,i,ii,isub;
	this.x4inName =x4inName ;
	this.x4outName=x4outName;
	x4readfile x4file=new x4readfile();
	x4file.sysOut=this.sysOut;
	x4file.sysOut=null;
	x4file.setx4fileinterface(this);
//	sysOut=sysOut0;
	if (x4outName==null)		x4outName=x4inName+".c5";
	if (x4outName.equals(""))	x4outName=x4inName+".c5";
//	sysOut_println("...x4outName=["+x4outName+"]");

//	if (o5!=null) o5.close();
//	if (true) System.exit(0);

	x4outfile o5l=new x4outfile(x4outName+".lst");
	o5lst=o5l.getPrintWriter();

	readCalcInverseReactFile();

	x4outfile o5lR=null;
	o5l.deleteFile(x4outName+".lstR");
	if (alwaysCalcInverseReact||vInverseReact.size()>0)
	{
	o5lR=new x4outfile(x4outName+".lstR");
	o5lstR=o5lR.getPrintWriter();
	}


	if (outC5also)
	if (outC4only)
	{
	    outC4andC5=true;
	    outC4only=false;
	}

//2020	if (x4Title.equals(""))
	{
//	    x4Title="C5";
	    x4Title="C5.2.3";
	    if (out_corrMatrix2)
//	    x4Title="C5M";
	    x4Title="C5M.2.3";
	}

	strDatabaseDate=readDbDate(x4inName);
	strX4inDate=getFileDate(x4inName);

	this.x4Title=x4Title;
	openMainC5OutputFile(x4outName);


	if (showCitation) {
	x4outfile o5readme=new x4outfile(x4outName+".readme");
	PrintWriter o5preadme=o5readme.getPrintWriter();
	str=myStrReplace(x4outName,"\\","/");
	ii=str.indexOf("/");
	if (ii>0) str=str.substring(ii+1);
	o5preadme.println("#___________Readme for the file: "+str);
/*	o5preadme.println("#___________File citation:\n"
	+"V.Zerkin (IAEA-NDS), Priv.comm.(2022)"+
//	", \""+str+": EXFOR/"+strDatabaseDate+" file translated into C5"
	", \"File "+str+": extracted from EXFOR/"+strDatabaseDate+" and translated to C5"
	+" by program x4toc5/"+strVersion
	+"\""
	);
*/
	o5preadme.println("#___________File citation:\n"
	+"V.Zerkin (IAEA-NDS), Priv.comm.(2023)"
	+", \"File "+str+": extracted from EXFOR database and translated to C5\","
	);
	if (strDatabaseDate==null) o5preadme.println("  Data source: unknown EXFOR file,");
	else o5preadme.println(""
		+"  Data source: EXFOR/ver."+strDatabaseDate
		+", (c) 1970-2023, International Network of Nuclear Reaction Data Centres (NRDC),"
		);
	o5preadme.println(""
	+"  Translation program: x4toc5/ver."+strVersion+", (c) 2023 International Atomic Energy Agency (IAEA), author:V.Zerkin."
	);
	if (o5readme!=null) {o5preadme.flush();o5readme.close();}
	}
/*
V.Zerkin (IAEA-NDS), Priv.comm.(2022), "File EXFOR-EXP-2022-08-08-v2.c5: extracted from EXFOR and translated to C5".
Data source: EXFOR 2022-08-03, (c) 1970-2022 NRDC; 
Translation program: x4toc5, 2022-08-08, (c) 2020-2022 IAEA, author:V.Zerkin@iaea.org
*/

/*
//	sysOut_println("Now: "+getMyDateTime());
	sysOut_println(" Running:  "+getMyDateTime());
	str=getComputerName();
	if (str!=null) sysOut_println(" Computer: "+str);
*/
	sysOut_println("");
	outTotalHeader();

	seconds0=System.currentTimeMillis();
	x4file.readExforFile(x4inName);

	closeMainC5OutputFile();

	if (o5l!=null) o5l.close();
	if (o5lR!=null) o5lR.close();
//	if (nTotalDatasetsConverted<=0) o5.deleteFile(x4outName);
	if (nTotalDatasetsConverted<=0) if (o5!=null) o5.deleteFile(x4outName);

	outTotalSummary();

	x4outfile o5mt0=new x4outfile(x4outName+".mt0");
	sysOut=o5mt0.getPrintWriter();
	printVecMT0(sysOut);
	if (o5mt0!=null) o5mt0.close();

	if (outC4andC5)
	{
	    filterC5toC4(x4outName,x4outName+".c4");
	    outC4andC5=false;
	    outC4only=true;
	}


	if (getFileLength(x4outName+".lst")==0) deleteFile(x4outName+".lst");
	if (getFileLength(x4outName+".mt0")==0) deleteFile(x4outName+".mt0");

	return 0;
    }

    public String readDbDate(String x4inName)
    {
	BufferedReader inFile=null;
	String str,strout=null;
	try {
	    inFile=new BufferedReader(new FileReader(x4inName)); 
	    for (;;) {
		str=inFile.readLine();
		if (str==null) break;
		//System.out.println("["+str+"]");
/*
REQUEST           1001   20220808     110630   20220803          3
*/
		if (str.startsWith("REQUEST")) {
		    if (str.length()>=56) strout=str.substring(45,55).trim();
		}
	    }
	}
	catch(Exception e) {
	}
	if (inFile!=null) try {inFile.close();} catch(Exception e) {}
	//System.out.println("___readDbDate:["+strout+"]");
	//if (true) System.exit(0);
	if (strout==null) return strout;
	if (strout.length()<8) return null;
	strout=strout.substring(0,4)+"-"+strout.substring(4,6)+"-"+strout.substring(6);
	return strout;
    }
    static public String getFileDate(String fileName)
    {
//	SimpleDateFormat formatter=new SimpleDateFormat ("yyyy-MM-dd,HH:mm:ss");
	SimpleDateFormat formatter=new SimpleDateFormat ("yyyy-MM-dd");
        File fl;
        java.util.Date fldate=null;
//	String strdate="1970-01-01,01:00:00";
	String strdate="1970-01-01";
	fl=new File(fileName);
	if (fl.exists()) {
	    fldate=new java.util.Date(fl.lastModified());
	    strdate=new String(formatter.format(fldate));
	}
	return strdate;
    }


    Vector vInverseReact=new Vector();
    String sInverseReact=null;
    public void setCalcInverseReactFile(String fileInName)
    {
	sInverseReact=fileInName;
    }
    public boolean isCalcInverseReact(String reacode)
    {
	int ii;
	String str;
	for (ii=0; ii<vInverseReact.size(); ii++) {
	    str=(String)vInverseReact.elementAt(ii);
	    if (str.equals(reacode)) return true;
	}
	return false;
    }
    public int readCalcInverseReactFile()
    {
	vInverseReact=new Vector();
	if (sInverseReact==null) return 0;
	String fileInName=sInverseReact;
	BufferedReader inFile=null;
	String str;
	x4reaction xreacode;
	int  ind,ierr=0;
	try {
	    inFile=new BufferedReader(new FileReader(fileInName)); 
	    for (;;) {
		str=inFile.readLine();
//sysOut0.println("...r:["+str+"]");
		if (str==null) break;
		str=str.trim();
		if (str.equals("")) continue;
		if (str.startsWith("#")) continue;
		xreacode=new x4reaction(' ',str);
		xreacode.flagMT51to5ilvl=flagMT51to5ilvl;
		xreacode.flagSort=flagSort;
		xreacode.setFlagMT0(flagMFMT_gt_0);
		xreacode.setFlagQvalue2Level(replaceQvalue2Level);
		if (xreacode.inverseReactionPossible) {
//System.out.println("........Inverse: ["+str+"] to ["+xreacode.inverseReactionCode+"]");
vInverseReact.addElement(str);
		}
	    }
	}
	catch(Exception e) {
	    sysOut_println("...Error reading invert reactions list...");
	    sysOut_println("...Invert reactions list: ["+fileInName+"]");
	    ierr=-1;
	}
	if (inFile!=null) try {inFile.close();} catch(Exception e) {}
	return ierr;
    }
    public Vector readDatasetList(String fileInName)
    {
	Vector vvv=new Vector();
        BufferedReader inFile=null;
        String str;
        int ind;
	String arr[];
        try {
            inFile=new BufferedReader(new FileReader(fileInName)); 
            for (;;) {
                str=inFile.readLine();
                if (str==null) break;
		str=str.trim();
//System.out.println("...readDatasetListFile: str=["+str+"]");
		if (str.startsWith("#")) continue;
		if (str.length()<8) continue;
	arr=str.split("\\s+");
	if (arr.length>1) str=arr[0];
		if (str.length()<8) continue;
		if (str.length()>=9) str=str.substring(0,9).trim();
		str=strpad(str,9);
		str=str.trim();
		vvv.addElement(str);
//System.out.println("...readBlacklist: SubP=["+str+"]");
//System.err.println("...readDatasetListFile: SubP=["+str+"]");
            }
        }
        catch(Exception e) {
        }
	if (inFile!=null)
        try {inFile.close();} catch(Exception e) {}
//	vvv=sortVecStr(vvv);
//	vvv=uniqVecStr(vvv);
	if (vvv.size()>0) vTheOnlyDatasetIDs=vvv;
	return vvv;
    }


//  String strHR="======================================================================";
    String strHR="===============================================================================";
    public void outTotalHeader()
    {
	int lss=24;
	sysOut_println(strHR);
	sysOut_println("Translation Log");
	sysOut_println(strHR);
//	sysOut_println("DATASET   MF  MT   "+strpad("REFERENCE",25)+" REACTION");
	sysOut_println("DATASET   MF  MT   POINTS "
	+strpad("REFERENCE",25)+" REACTION");
	sysOut_println(strHR);
    }
    public void outTotalSummary()
    {
	int lss=24;
	sysOut_println(strHR);
	sysOut_println("Translation Summary");
	sysOut_println(strHR);
	if (strDatabaseDate!=null)
	sysOut_println(strpad("EXFOR VERSION",lss)+strDatabaseDate);
	sysOut_println(strpad("ENTRY",lss)+nTotalEntry);
	sysOut_println(strpad("SUBENT",lss)+nTotalSubent);
	sysOut_println(strpad("DATASETS",lss)+nTotalDatasetsTotal);
	sysOut_println(strpad("DATA POINTS",lss)+nPointsTotal);
//	sysOut_println(strpad("TRANSLATED ENTRY",lss)+nTotalEntryConverted);
	sysOut_println(strpad("TRANSLATED ENTRY",lss)+strPercent2(nTotalEntryConverted,nTotalEntry));
//	sysOut_println(strpad("TRANSLATED SUBENT",lss)+nTotalSubentConverted);
	sysOut_println(strpad("TRANSLATED SUBENT",lss)+strPercent2(nTotalSubentConverted,nTotalSubent));
//	sysOut_println(strpad("TRANSLATED DATASETS",lss)+nTotalDatasetsConverted);
	sysOut_println(strpad("TRANSLATED DATASETS",lss)+strPercent2(nTotalDatasetsConvertedTotal,nTotalDatasetsTotal));
//	sysOut_println(strpad("TRANSLATED DATA POINTS",lss)+nTotalC4Datalines);
//2023	sysOut_println(strpad("TRANSLATED DATA POINTS",lss)+strPercent2(nTotalC4Datalines,nPointsTotal));
	sysOut_println(strpad("TRANSLATED DATA POINTS",lss)+strPercent2(nTotalC4DatalinesTotal,nPointsTotal));
	sysOut_println(strHR);
    }
    public String strPercent2(long nTranslated,long nTotal)
    {
	String str=strpad(""+nTranslated,12);
	if (nTranslated>0)
	str+=c4obj.strPercent((float)nTranslated,(float)nTotal)+"%";
	return str;
    }

//  public int copyTxtFile(String fileInName, String fileOutName)
    public int filterC5toC4(String fileInName,String fileOutName)
    {
	BufferedReader inFile  = null;
	BufferedWriter outFile = null;
	String str;
	int  ind,ierr=0;
	try {
	    inFile = new BufferedReader(new FileReader(fileInName)); 
	    outFile = new BufferedWriter(new FileWriter(fileOutName));
	    for (; ; ) {
		str=inFile.readLine();
		if (str==null) break;
		if (str.startsWith("#")) continue;
		if (str.length()>132) str=str.substring(0,132);
		outFile.write(str);
		outFile.newLine();
	    }
	}
	catch(Exception e) {
	    sysOut_println("...Error filtering C5 to C4....");
	    ierr=-1;
	}
	if (inFile!=null) try {inFile.close();} catch(Exception e) {}
	if (outFile!=null) try {outFile.close();} catch(Exception e) {}
	return ierr;
    }

    public void setTheOnlyDataset(String DatasetID)
    {
	theOnlyDatasetID=DatasetID;
	if (theOnlyDatasetID!=null) theOnlyDatasetID=theOnlyDatasetID.trim();
    }

    public void setTheOnlyDatasets(Vector vDatasetIDs)
    {
	vTheOnlyDatasetIDs=vDatasetIDs;
    }

    public boolean isThisDatasetNeeded(String DatasetID)
    {
	int ii;
	String str,str0;
	str0=DatasetID.trim();
	if ((theOnlyDatasetID==null)&&(vTheOnlyDatasetIDs==null)) return true;
	if (theOnlyDatasetID!=null)
	if (theOnlyDatasetID.equals(str0)) return true;
	if (vTheOnlyDatasetIDs!=null)
	for (ii=0; ii<vTheOnlyDatasetIDs.size(); ii++) {
	    str=(String)vTheOnlyDatasetIDs.elementAt(ii);
	    if (str.equals(str0)) return true;
	}
	return false;
    }


//  boolean flag_outX4mon=true;
    boolean flag_outX4mon=false;

    boolean flag_renormX4mon=false;
    boolean tryDecayCorr=false;

    String strCorr=null;


    public void treatExforEntry(x4subent sub1) {
//	System.err.println("---treatExforEntry|||"+sub1.Entry+"|||"+sub1.strDateModif);
	openMainC5OutputEntry(sub1.Entry,sub1.strDateModif);
//	if (sub1!=null) o5prn.println("<entry ="+sub1.Entry+">");
	nTotalEntry++;
	if (flagShowProcess)
	if (sub1!=null) {
		seconds1=System.currentTimeMillis();
		System.err.print("\r"
//		+"\t\t\t\t"
		+nTotalEntry+") ENTRY:"+sub1.Entry
//		+" "+myFormatter.format((seconds1-seconds0))+"sec "
		+" time="+sec2str(getSecDt(seconds0,seconds1))
		+" "+(((int)((seconds1-seconds0)/10.)/nTotalEntry))/100.+"sec/Entry"
		);
		if (nTotalDatasets>0)
		if (nTotalDatasetsConverted>0)
		System.err.print(""
		+" Datasets:"+nTotalDatasetsConverted+"/"+nTotalDatasets
		+":"+((nTotalDatasetsConverted*1000L)/nTotalDatasets/10.f)+"%"
		+" "
		);
		System.err.print(" ");

		if (extDebug) System.err.println("");
	}
	nEntryDatasets=0;
	nEntryDatasetsConverted=0;
    }
    public void treatExforEndentry(x4subent sub1,boolean flagLastEntryInFile) {
	//System.err.println("---treatExforEndentry|||"+sub1.Entry);
//	if (sub1!=null) o5prn.println("</entry ="+sub1.Entry+">");
	if (nEntryDatasetsConverted>0) nTotalEntryConverted++;
	//if (nTotalEntry>3) System.exit(0);//++++++++++++++++++++++++++++++++++++++++++++++++++++++
	closeMainC5OutputEntry(sub1.Entry);
    }
    public int getSecDt(long t0, long t1)
    {
	int dt;
	dt=((int)(t1-t0)+999)/1000;
	return dt;
    }
    public String sec2str(int sec)
    {
	int dd,hh,mm,ss;
	String tm;
	hh=sec/3600;
	dd=hh/24;
	hh=hh%24;
	mm=(sec/60)%60;
	ss=sec%60;
	tm="";
	if (dd>0) {
	    if (dd==1)	tm=tm+dd+"day,";
	    else	tm=tm+dd+"days,";
	}
	if ((hh>0)||(dd>0)) {
	    tm=tm+(hh/10)+(hh%10)+":";
	}
	tm=tm+(mm/10)+(mm%10)+":";
	tm=tm+(ss/10)+(ss%10);
	return tm;
    }

    public void treatExforSubent(x4subent sub2,boolean flagLastSubentInEntry)
    {
	x4subent sub1;
	int i,nRow,nn,nRowVectorCommon=0;
	x4reaction xreacode=null,newReacode;
	Vector vReactions;
	int lnn=16;
	int DnCol=0;
	boolean isVectorCommon=false;
	String DatasetID="";
	//System.err.println("---treatExforSubent|||"+sub2.Entry);

	sub1=sub2.subent1;
//	if (sub1==null) sysOut_println("\n"+sub2.lastEntryStr);
	if (extDebug)
	sysOut_print("\n---treatExforSubent: ");
	if (extDebug)
	if (sub1!=null) sysOut_println("   SUB1="+sub1.Subent+":"+sub1.i0strSubent+"+"+sub1.allLines.size());
	if (sub1!=null)
	if (!sub2.flagNosubent)	nTotalSubent++;

	if (extDebug)
	sysOut_println(" SUB="+sub2.Subent+":"+sub2.i0strSubent+"+"+sub2.allLines.size()
	+" KW="+sub2.x4sys.keyword
	);
//++	sub2.println();
	sub2.makeBib();
//		sub2.xbib.printStrings();
//		sub2.xbib.printshort();
//		sub2.xbib.printAll();
//		sysOut_println("\n\n");
	sub2.makeData();
//		sub2.xcommon.printHeaders();
//		sub2.xcommon.printData();
//		sub2.xdata.printHeaders();
//		sub2.xdata.printData();

	c4obj=null;
	nn=sub2.prepareC4F();//prepare for all pointers

	nPointsSubent=0;

if (extDebug)
if (sub1!=null)
sysOut_println("...Will-try-Reactions:"+sub2.xbib.vReactions.size()+" "+sub2.Subent);

	if (sub1!=null)
	if (sub2!=null)
	if (sub2.xdata!=null)
	if (sub2.xdata.DnRow>0)
	{
	    //Float c4data[][]=null;
	    c5arr c5data=null;
	    vReactions=sub2.xbib.vReactions;
	    isVectorCommon=false;
	    sub2.xbib.prepareVectorCommon();
	    isVectorCommon=sub2.xbib.isVectorCommon;
	    if (isVectorCommon) nTotalVectorCommon++;

//System.out.println("...Reactions:"+vReactions.size()+" "+sub2.Subent+" isVectorCommon="+isVectorCommon);
//	    if (isVectorCommon)
//	    pause("...Reactions:"+vReactions.size()+" "+sub2.Subent+" isVectorCommon="+isVectorCommon);

	    nRowVectorCommon=0;
	    if (isVectorCommon)
	    for (i=0; i<sub2.xbib.getNReactions(); i++) {
		xreacode=sub2.xbib.getReaction(i);
		xreacode.flagSort=flagSort;
		xreacode.flagMT51to5ilvl=flagMT51to5ilvl;
		xreacode.setFlagMT0(flagMFMT_gt_0);
		xreacode.setFlagQvalue2Level(replaceQvalue2Level);
//if (extDebug)
//sysOut_println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
//System.out.println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
		nn=sub2.prepareC4F(xreacode.cpointer);
		nRow=xreacode.getDataLY();
		if (nRow>0) nRowVectorCommon+=nRow;
	    }

	    for (i=0; i<vReactions.size(); i++) {
//		xreacode=(x4reaction)vReactions.elementAt(i);
//	    for (i=0; i<sub2.xbib.getNReactions(); i++) {
		xreacode=sub2.xbib.getReaction(i);
		xreacode.convert_Einc2Lab=tryToConvertCM2Lab;
		xreacode.flagSort=flagSort;
		xreacode.flagMT51to5ilvl=flagMT51to5ilvl;
		xreacode.setFlagMT0(flagMFMT_gt_0);
		xreacode.setFlagQvalue2Level(replaceQvalue2Level);
		nTotalDatasets++;//---counting
		nTotalDatasetsTotal++;//---counting
nPointsTotal+=xreacode.getDataLY_DATA();//2021-04-19
//System.out.println("___2---"+i+" DatasetID=["+xreacode.DatasetID+"]"+" xreacode.getDataLY_DATA()="+xreacode.getDataLY_DATA());
//System.out.println("---2---"+i+" Reaction-pointer=["+xreacode.cpointer+"]"+" isVectorCommon="+isVectorCommon+" vReactions.size()="+vReactions.size());
if (extDebug)
if (xreacode.inverseReactionPossible)
System.out.println("........inverseReaction=["+xreacode.inverseReactionCode+"]");


		if (alwaysCalcInverseReact)
		if (!xreacode.inverseReactionPossible) {
sysOut_println(xreacode.DatasetID+" INVERT REACTION NOT POSSIBLE FOR "+xreacode.reacode);
		    continue;
		}

		//sysOut_println("\n...A..."+i+" Reaction-pointer=["+xreacode.cpointer+"]"+" "+xreacode.reacode);
		if (isVectorCommon) {
//System.out.println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
		    nn=sub2.prepareC4F(xreacode.cpointer);
//sub2.xcommon.printHeaders();
//sub2.xcommon.printData();
//sub2.xdata.printHeaders();
//sub2.xdata.printData();
//xreacode.printDataComput(o5prn,12);//---test
		}



//System.out.println("...---absentVars:["+xreacode.absentVarFamilyCode+"]ID:"+xreacode.DatasetID+" indfam:["+xreacode.IndepVarFamilyCode+"]");
		if (tryToRecoverEnresFromEn)
		if (!xreacode.absentVarFamilyCode.equals(""))
		if (xreacode.absentVarFamilyCode.indexOf("1")>=0)
		{
		    //xreacode=new x4reaction(xreacode.cpointer,xreacode.reacode,true);
		    xreacode.x4treatReacode(xreacode.cpointer,xreacode.reacode,true);
		    xreacode.flagSort=flagSort;
		    xreacode.flagMT51to5ilvl=flagMT51to5ilvl;
		    xreacode.setFlagMT0(flagMFMT_gt_0);
		    xreacode.setFlagQvalue2Level(replaceQvalue2Level);
		    //nn=sub2.prepareC4F();//prepare for all pointers
		    nn=sub2.prepareC4F(xreacode.cpointer);
		}

if (extDebug)
sysOut_println(" ...Reaction-"+i+" "+xreacode.reacode
+" cols="+xreacode.nCols+" known4me="+xreacode.flagKnown4me+" "+sub2.Subent);

//ver:1		if (flag_outX4mon) prepareMonitors(xreacode);

//		sysOut_println("  +++reaction-"+(i+1)+" i0str.file="+(sub2.xbib.i0strSubent+xreacode.xcode.xkw.i0str+xreacode.xcode.i0str));
//2009.02.20	nn=sub2.prepareC4F(xreacode.cpointer);

		nn=xreacode.nCols;

		if (extDebug)
		sysOut_println("...xreacode.nCols="+xreacode.nCols);

		//xreacode.println();
		if (nn<=0) {
//System.out.println(" ...Reaction-"+i+" "+xreacode.reacode+" cols="+xreacode.nCols+" known4me="+xreacode.flagKnown4me+" "+sub2.Subent);
		    continue;
		}
		DnCol=xreacode.vVars.size();

		if (extDebug)
		for (int ii=0; ii<xreacode.vVars.size(); ii++) {
		    x4var xvar=(x4var)xreacode.vVars.elementAt(ii);
//		    if (false)
//		    sysOut.println(" "+(ii+1)+")\t"+xvar.Header
		    System.out.println("___v___"+(ii+1)+")\t"+xvar.Header
			+"\t what:"+xvar.what
			+"\t whatVar:"+xvar.whatVar
			+"\torder="+xvar.strVarOrderFlag
			+"\tDataType="+xvar.DataType
			+"\tFamilyCode="+xvar.FamilyCode
			+"\tvarNum="+xvar.Variable_Number
			+"\tBasicUnits="+xreacode.BasicUnits
			+"\tvar.BasicUnits="+xvar.BasicUnits
			);
		    //sysOut.println(" "+(ii+1)+")\t"+xvar.toString());
		}

		c4obj=new x4c4obj();
		xreacode.c4obj_c5wrk=c4obj;//2020-10-20
		c4obj.initDataC4();
		c4obj.outC4only=outC4only;
		c4obj.tryToConvertCM2Lab=tryToConvertCM2Lab;//2015:todo
		c4obj.outMonData=flag_outX4mon;//2019-11-26
//		xreacode.convert_Einc2Lab=tryToConvertCM2Lab;

		if (extDebug) c4obj.extDebug=true;

//??c4obj.outC4only=outC4only;//2023-02-23
		nRow=c4obj.prepareC4Info(sub2,xreacode,isVectorCommon);
//c4obj.prepareDataUnits();
//c4obj.printDataUnits();
//System.out.println("...c4obj.c4_i78="+c4obj.c4_i78);

/*		//2021-10-12:tested but failed
		if (nRow<0)
		if (!xreacode.reacode2equ.equals(""))
		{
		    xreacode.x4treatReacode(xreacode.cpointer,xreacode.reacode2equ,true,sub2);
		}
*/
		if (extDebug)
		sysOut_println("___1___prepareC4Info="+nRow+" MF"+c4obj.c4_MF+" MT"+c4obj.c4_MT+" nRow="+nRow
+"\n===DatasetID=["+c4obj.DatasetID+"]"
+" theOnlyDatasetID=["+theOnlyDatasetID+"]"
+" vTheOnlyDatasetIDs=["+vTheOnlyDatasetIDs+"]"
+"\n===isThisDatasetNeeded(DatasetID)="+isThisDatasetNeeded(c4obj.DatasetID)
		);

		if (nRow<=0) {
//		    System.out.println("?nRow?:"+xreacode.DatasetID+" cols="+xreacode.nCols+" nRow="+nRow+" known4me="+xreacode.flagKnown4me+"...Reaction-"+i+" "+xreacode.reacode);

//____________________________________Output to terminal here!_____________________________
		    String str1=xreacode.DatasetID+" "+strpad(""+c4obj.c4_MF,3)+" "+strpad(""+c4obj.c4_MT,4)
			+padstr(""+nRow,7)
			+" "+c4obj.c4_Refer;
		    if (xreacode!=null) str1+=" "+xreacode.reacode;
		    sysOut_println(str1);

		    continue;
		}

		DatasetID=sub2.Subent+xreacode.cpointer;
		DatasetID=c4obj.DatasetID;


		xreacode.isVectorCommon=isVectorCommon;
		xreacode.makeC4data2array(-1);
//System.out.println("...c4obj="+xreacode.c4obj);
//System.out.println("...xreacode.c4obj.c4_i78="+xreacode.c4obj.c4_i78);

if (extDebug)
sysOut_println(" ...prepareC4Info="+nRow+" MF"+c4obj.c4_MF+" MT"+c4obj.c4_MT+" nRow="+nRow);

/*
System.out.println("___9___c4dataMonit["+xreacode.c4dataMonitEne.length+"]");
for (int i77=0; i77<xreacode.c4dataMonitEne.length; i77++) {
System.out.println("___9____c4dataMonit:"+" E=["+xreacode.c4dataMonitEne[i77]+"]"
+" M0=["+xreacode.c4dataMonitVal[i77]+"]"+" dM0=["+xreacode.c4dataMonitErr[i77]+"]");
}
*/


//2020		c4data=xreacode.c4data;
		c5data=xreacode.c5data;
if (false)
		if (xreacode.c5data!=null)
		if (xreacode.c5data.c4data!=null)
		System.out.println("\n\n\n"
		+"...xreacode.c4data.length="
		+xreacode.c5data.c4data.length+"x"+xreacode.c5data.c4data[0].length
		+"\n\n\n"
		);


		c4obj_inv=null; xreacode_inv=null;
		onlyCalcInverseReact=alwaysCalcInverseReact;
		if (!onlyCalcInverseReact) onlyCalcInverseReact=isCalcInverseReact(xreacode.reacode);
		if (onlyCalcInverseReact) {
		    c4obj_inv=new x4c4obj();
		xreacode.c4obj_c5wrk=c4obj;//2020-10-20
		    c4obj_inv.initDataC4();
		    c4obj_inv.outC4only=outC4only;
		    xreacode_inv=new x4reaction(xreacode.cpointer
			,xreacode.inverseReactionCode,sub2);
		    xreacode.flagSort=flagSort;
		    xreacode.flagMT51to5ilvl=flagMT51to5ilvl;
		    xreacode_inv.setFlagMT0(flagMFMT_gt_0);
		    xreacode_inv.setFlagQvalue2Level(replaceQvalue2Level);
		    c4obj_inv.prepareC4Info(sub2,xreacode_inv,isVectorCommon,"^");
//		    c4obj_inv.prepareC4Info(sub2,xreacode_inv,isVectorCommon,"~");
		    if (false)
		    System.out.println("........inverseReaction:"
			+" code=["+xreacode_inv.reacode+"]"
			+" MF"+c4obj_inv.c4_MF+" MT"+c4obj_inv.c4_MT);
		}


if (extDebug)
o5prn.println("===DatasetID=["+DatasetID+"]"+" theOnlyDatasetID=["+theOnlyDatasetID+"]");

//		if (theOnlyDatasetID!=null)
//		if (!theOnlyDatasetID.equals(DatasetID.trim())) continue;
		if (!isThisDatasetNeeded(DatasetID)) continue;

		c4_MF=c4obj.c4_MF;
		c4_MT=c4obj.c4_MT;

//		sysOut_println(strDatasetBegin);

//		nRow=x4var.printData(xreacode.vVars,null);//to get nRow without output

//20230908	outC4headerLine(o5prn,strpad("#DATASET",lnn)+DatasetID);
		outC4headerLine(o5prn,strpad("#DATASET",lnn)+strpad(DatasetID,10)+" "+sub2.x4sys.strN2);

boolean oldVersion=false;
//2018		if (oldVersion) printDatasetHeader(o5prn); else
//2018		makeDatasetHeader(sub2,xreacode,i,isVectorCommon,nRowVectorCommon,DatasetID);

if (false)
		sysOut_println("+++ SUB="+sub2.Subent+" React-"+(i+1)
		+" Pnt:["+xreacode.cpointer+"]"
		+" nCols:"+xreacode.vVars.size()
		+" nRows:"+nRow
		+" nReacstr:"+xreacode.ireac
		+" Reacode:"+xreacode.reacode
		);
//		x4var.printHeaders(xreacode.vVars,sysOut);
//		o5prn.println(strpad("#DATA",lnn)	+nRow);
//2010		nRow=printDataX4(xreacode.vVars,sysOut,lnn,nRow);

//test
		//if (nRow>=0)//already checked
////		if (oldVersion)	printDataC4(o5prn,xreacode,16,c5data.c4data); else
		{
		//boolean inv0=onlyCalcInverseReact;
//2020		processDataC4(o5prn,xreacode,c4data);
		processDataC4(o5prn,xreacode,c5data);
		//boolean inv1=onlyCalcInverseReact;
		//if (inv0) if (!inv1) processDataC4(o5prn,xreacode,c4data);
//System.out.println("___onlyCalcInverseReact="+onlyCalcInverseReact
//+" c4obj_inv="+c4obj_inv+" xreacode_inv="+xreacode_inv);

//2018-04-16: next line moved here for cases when Inversion failed because LVL!=0
		makeDatasetHeader(sub2,xreacode,i,isVectorCommon,nRowVectorCommon,DatasetID);

		//extProcessDataC4(xreacode,c4data);
		printDatasetHeader(o5prn);
		printDataC4array(o5prn,xreacode,c5data,true);
		}

//		o5prn.println(strpad("#ENDDATA",lnn)	+nRow);

//xreacode.printDataComput(sysOut,12);//---test

		if (extDebug)
		System.out.println("--- SUB="+sub2.Subent+" React-"+(i+1)
		+" Pnt:["+xreacode.cpointer+"]"
		+" common1="+sub1.xcommon.DnCol+"*"+sub1.xcommon.DnRow
		+" common2="+sub2.xcommon.DnCol+"*"+sub2.xcommon.DnRow
		+" data="+sub2.xdata.DnCol+"*"+sub2.xdata.DnRow
//		+" nVars:"+xreacode.vVars.size()
//		+" nRows:"+nRow
		+" outTbl:"+xreacode.vVars.size()+"*"+nRow
		);

		outC4headerLine(o5prn,strpad("#/C5DATA",lnn)+nRowVectorCommon);

		if (out_corrMatrix2) outCorrMatrixDefault2(o5prn,xreacode);

		outC4headerLine(o5prn,strpad("#/DATASET",lnn)	+DatasetID);

	    }

	}

	if (nPointsSubent>0) nTotalSubentConverted++;

//	sysOut_println("\n\n\n");
	//pause("");
    }

    public void addVecMT0(String strCode,String DatasetID,String strComment)
    {
	int i;
	String str;
//System.out.println("___addVecMT0::"+DatasetID+" ["+strCode+"]");
	for (i=0; i<vecMT0.size(); i++) {
	    str=(String)vecMT0.elementAt(i);
//	    if (str.startsWith(strCode+"\t")) return;
	    if (str.indexOf("\t"+strCode+"\t")>=0) return;
	}
//	vecMT0.addElement(strCode+"\t"+strComment);
	vecMT0.addElement(DatasetID+"\t"+strCode+"\t"+strComment);
    }

    public void printVecMT0(PrintWriter prnOut)
    {
	int i;
	String str;
//System.out.println("___printVecMT0::"+vecMT0.size());
	for (i=0; i<vecMT0.size(); i++) {
	    str=(String)vecMT0.elementAt(i);
	    if (prnOut!=null) {
		prnOut.println(""+str);
//		prnOut.flush();
	    }
	}
    }


    x4c4obj c4obj=null;
    x4c4obj c4obj_inv=null;
    x4reaction xreacode_inv=null;
    Vector headerStrings=new Vector();
//    Float enMin=null;
//    Float enMax=null;

    public int makeDatasetHeader(x4subent sub2,x4reaction xreacode,int ireac
	,boolean isVectorCommon,int nRowVectorCommon,String DatasetID)
    {
	int i,ii,nRow=0,nn,lnn=16,nx,ll1,mf1=0,mt1=0,mf2=0,mt2=0;
	x4reacstr xreac1=null,xreac2;
	x4subent sub1=sub2.subent1;
	x4ref xref1=sub1.xbib.xref1;
	String str,str1,str2;
	String strHowFound="";

//System.out.println("___makeDatasetHeader:onlyCalcInverseReact="+onlyCalcInverseReact);
//System.out.println("___0___makeDatasetHeader:vCompNotes.size()="+xreacode.vCompNotes.size());

	headerStrings=new Vector();

//	nTotalDatasets++;//---counting

	if (xreacode.IndepVarFamilyCode.trim().equals("?")) {
//	    if (false)
	    {
//		System.err.println("...PROBLEM"
		o5lst.println("...PROBLEM"
		+" "+sub2.Subent+xreacode.cpointer
		+" "+xreacode.reacode
		+" col="+xreacode.nCols
		+" know="+xreacode.flagKnown4me
		);
//		System.err.flush();
		o5lst.flush();
	    }
	    return -3;
	}
	nTotalDatasetsConverted++;
	nTotalDatasetsConvertedTotal++;
	nEntryDatasetsConverted++;

	nRow=printDataX4(xreacode.vVars,null,lnn,nRow);//to get nRow without output

//	c4obj=new x4c4obj();
//	c4obj.initDataC4();
//	c4obj.prepareC4Info(sub2,xreacode,isVectorCommon);
//	c4_MF=c4obj.c4_MF;
//	c4_MT=c4obj.c4_MT;
	if (c4_MF==0) return -2;//2010: try to resolve?

//++	if ((c4_MF==0)||(c4_MT==0)) return -2;//run1

	if (c4_MF>0) nTotMFok++;
	if (c4_MT>0) nTotMTok++;

//	if (c4_MT<=0) addVecMT0(xreacode.xreac1.SF3+";"+" MF"+c4_MF+" "+xreacode.reacode);
//	if ((c4_MT<=0)||(c4_MF<=0))
	if ((c4_MT<=0)&&(c4_MF<=0))
	if (false)
//	System.err.println("..."
	o5lst.println("..."
		+" "+sub2.Subent+xreacode.cpointer
//		+" MFMT="+strpad(c4_MF+":"+c4_MT,8)
		+" "+xreacode.reacode
		);

//	if (c4_MT<=0) addVecMT0(xreacode.C4ReactionCode,sub2.Subent+xreacode.cpointer+"\t"+xreacode.reacode);
	if (c4_MT<=0) addVecMT0(xreacode.C4ReactionCode,sub2.Subent+xreacode.cpointer,xreacode.reacode);

//	if (!xreacode.IndepVarFamilyCode.trim().equals("?")) nTotalDatasetsConverted++;

/*
//sysOut_println(c4_SubentP+" MFMT="+strpad(c4_MF+":"+c4_MT,8)+" "+c4_Refer+"");
str1=nTotMTok+"/"+nTotMFok+"/"+nTotalDatasetsConverted+"/"+nTotalDatasets;
//str1=str1+":"+xreacode.IndepVarFamilyCode.trim();
//sysOut_println(strpad(str1,26)+c4_SubentP+" MFMT="+strpad(c4_MF+":"+c4_MT,8)+" "+c4_Refer+"");
//sysOut_print(strpad(str1,30)+c4obj.c4_SubentP+" MFMT="+strpad(c4_MF+":"+c4_MT,8)+" "+c4obj.c4_Refer+"\r");
//str1=strpad(str1,30)+c4obj.c4_SubentP+" MFMT="+strpad(c4_MF+":"+c4_MT,8)+" "+c4obj.c4_Refer;
str1=strpad(str1,30)+" MFMT="+strpad(c4_MF+":"+c4_MT,8)+" "+c4obj.c4_Refer
+" ["+c4obj.c4_SubentP+"]";
if (xreacode!=null) str1+=" "+xreacode.reacode;
*/
//str1=c4obj.DatasetID+" MF:"+strpad(c4_MF+"",3)+" MT:"+strpad(c4_MT+"",4)+" "+c4obj.c4_Refer;

//____________________________________Output to terminal here!_____________________________
//____________________________________Output to terminal here!_____________________________
//____________________________________Output to terminal here!_____________________________
//____________________________________Output to terminal here!_____________________________
//____________________________________Output to terminal here!_____________________________
//____________________________________Output to terminal here!_____________________________
//str1=c4obj.DatasetID+" "+strpad(c4_MF+"",3)+" "+strpad(c4_MT+"",4)+" "+c4obj.c4_Refer;
//20211007	str1=c4obj.DatasetID+" "+strpad(""+c4_MF,3)+" "+strpad(""+c4_MT,4)+" "+c4obj.c4_Refer;
		str1=c4obj.DatasetID+" "+strpad(""+c4_MF,3)+" "+strpad(""+c4_MT,4)
+padstr(""+nRow,7)
+" "+c4obj.c4_Refer;

if (xreacode!=null) str1+=" "+xreacode.reacode;
sysOut_println(str1);


//	o5prn.println(strpad("#DATASET",lnn)	+sub2.Subent+xreacode.cpointer);
//?	o5prn.println(strpad("#DATASET",lnn)	+DatasetID);

//x	addC4headerLine(o5prn,strpad("#DATASET",lnn)	+DatasetID);
//c4obj.printDataUnits();
	//o5prn.println(strpad("#NOW",lnn)	+getMyDateTime());
	addC4headerLine(o5prn,"#SUBENT",strpad(sub2.Subent,11)+sub2.x4sys.strN2);
	addC4headerLine(o5prn,"#ENTRY" ,strpad(sub1.Entry,11) +sub1.x4sys.strN2);
	str=sub1.xbib.getTitle();
//2019	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-2)).trim();
//2020	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-3)).trim();
	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-2)).trim();
	addC4headerLine(o5prn,"#TITLE",str);
	str=sub1.xbib.getAuthors();
//2019	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-2)).trim();
//2020	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-3)).trim();
	str=myStrReplace(str,"\n","\n#+"+strpad("",lnn-2)).trim();
	addC4headerLine(o5prn,"#AUTHORS",str);
	addC4headerLine(o5prn,"#AUTHOR1",sub1.xbib.Author1);
	if (sub1.xbib.Year1Ref<=1900)
	    addC4headerLine(o5prn,"#YEAR"	,""+sub2.xbib.Year1Ref);
	else
	    addC4headerLine(o5prn,"#YEAR"	,""+sub1.xbib.Year1Ref);
	if (xref1!=null) {
	    addC4headerLine(o5prn,"#X4REF1",sub1.xbib.Reference1);
	    addC4headerLine(o5prn,"#REFERENCE1",xref1.getShortRef());
	}

//	addC4headerLine(o5prn,"#DATE",sub2.x4sys.strN2);
	str=sub2.x4sys.strN2.trim();
	if (str.length()==6) str="19"+str;
	addC4headerLine(o5prn,"#DATE",str);

	str=sub1.xbib.getCodes("INSTITUTE",xreacode.cpointer,"");
	str=sub2.xbib.getCodes("INSTITUTE",xreacode.cpointer,str);
	if (!str.equals("")) addC4headerLine(o5prn,"#INSTITUTE",str);

	str=sub1.xbib.getCodes("METHOD",xreacode.cpointer,"");
	str=sub2.xbib.getCodes("METHOD",xreacode.cpointer,str);
	if (!str.equals("")) addC4headerLine(o5prn,"#METHOD",str);

/*	str=sub1.xbib.getCode("STATUS",xreacode.cpointer,"SPSDD");
	if (!str.equals("")) addC4headerLine(o5prn,"#STATUS",str);
	else {
	    str=sub2.xbib.getCode("STATUS",xreacode.cpointer,"SPSDD");
	    if (!str.equals("")) addC4headerLine(o5prn,"#STATUS",str);
	}
*/
	if (xreacode.getC4Status()=='S') addC4headerLine(o5prn,"#STATUS","SPSDD");
//	if (!xreacode.xSF9.equals(""))   addC4headerLine(o5prn,"#SF9",xreacode.xSF9);

//System.out.println("___makeDatasetHeader:onlyCalcInverseReact="+onlyCalcInverseReact
//+" c4obj_inv="+c4obj_inv+" xreacode_inv="+xreacode_inv);

if (xreacode_inv!=null) {
//	addC4headerLine(o5prn,"#CALC_OPERATION"	,"Inverse reaction using detailed balance");
	addC4headerLine(o5prn,"#DERIVED"	,"10 Inverse reaction data calculated using detailed balance");
	addC4headerLine(o5prn,"#ORIG_REACTION"	,xreacode.reacode);
	addC4headerLine(o5prn,"#REACTION"	,xreacode_inv.reacode);
//	addC4headerLine(o5prn,"#REACT"		,xreacode.inverseReactionCodeShort);
	addC4headerLine(o5prn,"#C4Reaction"	,xreacode_inv.C4ReactionCode);
	addC4headerLine(o5prn,"#ReactionType"	,xreacode_inv.ReactionType);
	addC4headerLine(o5prn,"#MF"		,""+c4obj_inv.c4_MF);
	addC4headerLine(o5prn,"#MT"		,""+c4obj_inv.c4_MT);
	addC4headerLine(o5prn,"#ORIG_MT"	,""+c4obj.c4_MT);
if (npts_C5_total>npts_C5_inverted) {
	addC4headerLine(o5prn,"#MT_LINES"	,""+npts_C5_inverted);
	addC4headerLine(o5prn,"#ORIG_LINES"	,""+(npts_C5_total-npts_C5_inverted));
//	addC4headerLine(o5prn,"#ORIG_MT_LINES"	,""+(npts_C5_total-npts_C5_inverted));
}
//	if (xreacode.xreac1!=null)
	addC4headerLine(o5prn,"#TARG"	,strpad(""+xreacode_inv.xTarg1.za,lnn)+xreacode_inv.xTarg1.cmeta);
	addC4headerLine(o5prn,"#TARGET"	,xreacode_inv.xTarg1.originalStr);
	addC4headerLine(o5prn,"#PROJ"	,""+c4obj_inv.c4_zaProj);//2020-06-03

	if (xreacode_inv.xreac1!=null)
	if (!xreacode_inv.xreac1.SF4.equals(""))
	addC4headerLine(o5prn,"#PRODUCT"	,xreacode_inv.xreac1.SF4);
	addC4headerLine(o5prn,"#C4ReaCode"	,xreacode_inv.C4ReactionCode);
}
else {
	if (xreacode.originalReacode.indexOf("DERIV")>0)
	addC4headerLine(o5prn,"#DERIVED"	,"3  Derived data in EXFOR");
	if (xreacode.originalReacode.indexOf("CALC")>0)
	addC4headerLine(o5prn,"#DERIVED"	,"7  Calculated data in EXFOR");
	if (xreacode.originalReacode.indexOf("EVAL")>0)
	addC4headerLine(o5prn,"#DERIVED"	,"9  Evaluated data in EXFOR");
	if (xreacode.originalReacode.indexOf("RECOM")>0)
	addC4headerLine(o5prn,"#DERIVED"	,"5  Recommended data in EXFOR");

	if (!xreacode.reacode.equals(xreacode.originalReacode))
	addC4headerLine(o5prn,"#REACTION_X4"	,xreacode.originalReacode);
	addC4headerLine(o5prn,"#REACTION"	,xreacode.reacode);
/*
//[[[[[[[[[[[[[[[[[[[[CompNotes]]]]]]]]]]]]]
//	c4obj.printCompNotes(o5prn);
//	addC4headerLine(o5prn,"#CompNotes"	,""+c4obj.compVars.length);
	for (int i1=0; i1<c4obj.compVars.length; i1++) {
	    for (int i2=0; i2<c4obj.compVars[i1].vCompNotes.size(); i2++) {
		String str1c=(String)c4obj.compVars[i1].vCompNotes.elementAt(i2);
		//sysOut.println("\t"+str1);
		addC4headerLine(o5prn,"#CompNotes"	,""+str1c);
	    }
	}
	addC4headerLine(o5prn,"#CompNotes","__________________________11111111111___________");
*/
	addC4headerLine(o5prn,"#C4Reaction"	,xreacode.C4ReactionCode);
	addC4headerLine(o5prn,"#ReactionType"	,xreacode.ReactionType);
	addC4headerLine(o5prn,"#MF"		,""+c4_MF);
	addC4headerLine(o5prn,"#MT"		,""+c4_MT);
	addC4headerLine(o5prn,"#PROJ"	,""+c4obj.c4_zaProj);//2020-06-03
//	if (xreacode.xreac1!=null)
	addC4headerLine(o5prn,"#TARG"	,strpad(""+xreacode.xTarg1.za,lnn)+xreacode.xTarg1.cmeta);
//	addC4headerLine(o5prn,"#TARGET"	,xreacode.xTarg1.originalStr);
//	addC4headerLine(o5prn,"#TARGET"	,strpad(""+xreacode.xTarg1.originalStr,lnn)
//	+"["+xreacode.xTarg1.X4CompoundType+"] "
//	+xreacode.xTarg1.X4CompoundHelp
//	);
	addC4headerLine(o5prn,"#TARGET"	,""
	+strpad(""+xreacode.xTarg1.za,lnn)
	+strpad("["+xreacode.xTarg1.cmeta+"]",lnn)
	+strpad(""+xreacode.xTarg1.originalStr,lnn)
	+"["+xreacode.xTarg1.X4CompoundType+"] "
	+xreacode.xTarg1.X4CompoundHelp
	);

	if (xreacode.xreac1!=null)
	if (!xreacode.xreac1.Reac.equals(""))
	addC4headerLine(o5prn,"#REAC1"	,xreacode.xreac1.Reac);

	if (xreacode.xreac1!=null)
	if (!xreacode.xreac1.SF4.equals(""))
	addC4headerLine(o5prn,"#PRODUCT"	,xreacode.xreac1.SF4);
	addC4headerLine(o5prn,"#C4ReaCode"	,xreacode.C4ReactionCode);
}

	//2020-02-13
	for (int i1=0; i1<c4obj.compVars.length; i1++) {
	    for (int i2=0; i2<c4obj.compVars[i1].vCompNotes.size(); i2++) {
		String str1c=(String)c4obj.compVars[i1].vCompNotes.elementAt(i2);
		//sysOut.println("\t"+str1);
		addC4headerLine(o5prn,"#CompNotes"	,""+str1c);
	    }
	}
	//2021-10-05
//System.out.println("___1___makeDatasetHeader:vCompNotes.size()="+xreacode.vCompNotes.size());
	for (int i2=0; i2<xreacode.vCompNotes.size(); i2++) {
	    String str1c=(String)xreacode.vCompNotes.elementAt(i2);
	    addC4headerLine(o5prn,"#CompNotes"	,""+str1c);
	}

	addC4headerLine(o5prn,"#Quantity"	,xreacode.reacombiQuant236);

//	addC4headerLine(o5prn,"#DataUnits"	,xreacode.BasicUnits);
	if (xreacode.BasicUnitsFinal!="") {
	addC4headerLine(o5prn,"#DataUnits0"	,xreacode.BasicUnits);
	addC4headerLine(o5prn,"#DataUnits"	,xreacode.BasicUnitsFinal);
	}
	else
	addC4headerLine(o5prn,"#DataUnits"	,xreacode.BasicUnits);

	addC4headerLine(o5prn,"#D4REAC"		,xreacode.d4reactioncode);
	addC4headerLine(o5prn,"#ReaCombi"	,xreacode.reacombi0);
	addC4headerLine(o5prn,"#C4FOUND"	,""+c4obj.c4_ifound+" ");
	//addC4headerLine(o5prn,"#C4BEGIN"	,"["+c4obj.c4_MF+"]");
	addC4headerLine(o5prn,"#C4BEGIN"	,"["+c4obj.getC4BEGIN()+"]");
	str=getDATAHDR(xreacode);
	addC4headerLine(o5prn,"#DATA-HDR"	,""+str);

	if (extDebug)
	{
//	addC4headerLine(o5prn,"#IndepVarFamCode","["+xreacode.IndepVarFamilyCode+"]");
	addC4headerLine(o5prn,"#IndVarFamCode"	,"["+xreacode.IndepVarFamilyCode+"]");
	addC4headerLine(o5prn,"#ExpectedUnits"	,"["+xreacode.BasicUnits+"]");
	addC4headerLine(o5prn,"#UnitsCombi"	,"["+xreacode.reacombiU+"]");
	}
	addC4headerLine(o5prn,"#VarFamily"	,"["+xreacode.IndepVarFamilyCode+"]");

	if (xreacode.flagKnown4me==false) return nRow;
	xreac1=xreacode.xreac1;
	if (extDebug) {
	addC4headerLine(o5prn,"#ReactionType1"	,xreac1.ReactionType+" "+xreac1.ReactionQuant);
	}
	nx=x4var.getMaxIndepVarNum(xreacode.vVars);
//	nx=xreacode.nExpectedArgs;
	addC4headerLine(o5prn,"#xVariables"	,""+nx);
	if (nx>0)
	addC4headerLine(o5prn,"#+"		,getYFuncXStr(nx));
//	addC4headerLine(o5prn,"#nxRequired"	,xreacode.nExpectedArgs);
	if (!xreacode.absentVarFamilyCode.equals(""))
	addC4headerLine(o5prn,"#absentVars"	,"["+xreacode.absentVarFamilyCode+"]");

//??	addC4headerLine(o5prn,strpad("#IndepVarFamCode",lnn)	+"["+xreac1.IndepVarFamilyCode+"]");
//	if (xreac1.x4d236!=null)
//	addC4headerLine(o5prn,strpad("#Quantity-R1",lnn)	+xreac1.x4d236.shortHelp);
//	if (xreacode.simpleReac)
//??	addC4headerLine(o5prn,strpad("#BasicUnits-R1",lnn)	+"["+xreac1.BasicUnits+"]");


	if (false)	//todo:printX4Strings -> Vector getX4Strings
	if (flagBib1Out) {
	nn=sub1.xbib.nX4Strings();
	addC4headerLine(o5prn,"#X4BIB1"		,""+nn);
	sub1.xbib.printX4Strings(sysOut,"#$\t\t");
	addC4headerLine(o5prn,"#ENDX4BIB1"	,""+nn);
	}

//	sub2.xbib.printshort();
//	sub2.xbib.println();
//	sub2.xbib.printStrings();
	if (false)	//todo:printX4Strings -> Vector getX4Strings
	if (flagBibxOut) {
	nn=sub2.xbib.nX4Strings();
	addC4headerLine(o5prn,"#X4BIBX"	,""+nn);
	sub2.xbib.printX4Strings(sysOut,"#$\t\t");
	addC4headerLine(o5prn,"#ENDX4BIBX",""+nn);
	}

//2010	addC4headerLine(o5prn,strpad("#COLUMNS",lnn)	+xreacode.vVars.size());
//2010	nRow=printDataHeader(xreacode.vVars,sysOut,lnn);



	addC4headerLine(o5prn,"#ReacRatio"	,""+xreacode.simpleRatio);
	addC4headerLine(o5prn,"#vReacs"		,""+xreacode.vReacs.size());

	nRow=xreacode.getDataLY();
	if (isVectorCommon) nRow=nRowVectorCommon;

//	addC4headerLine(o5prn,"#C5dirmon:",x4getcorr.dirMonitor00);

//2020	addC4headerLine(o5prn,"#C5EXT1"	,"132         (2F9.0)     dSys,dStat         //Absolute systematic and statistical uncertainties");
//	addC4headerLine(o5prn,"#C5EXT1"	,"132         (3F9.0)     dSys,dStat,dTotal       //Absolute systematic, statistical and total uncertainties");
//20209	addC4headerLine(o5prn,"#C5EXT1"	,"132         (3F9.0)     dSys,dStat,dTot       //Absolute systematic, statistical and total uncertainties");
	addC4headerLine(o5prn,"#C5EXT1" ,"132         (4F9.0)     dSys,dStat,dOther,dTot //Absolute fully correlated, uncerrelated, partially correlated and total uncertainties");
	if (!flag_outX4mon)
//	addC4headerLine(o5prn,"#C5EXT2"	,"150          *          dTotal,dSys,dStat  //Relative uncertainties (dData/Data) in per-cents.");
//2020	addC4headerLine(o5prn,"#C5EXT2"	,"150         (3F9.0)     dTotal,dSys,dStat  //Relative uncertainties (dData/Data) in per-cents.");
//20209	addC4headerLine(o5prn,"#C5EXT2"	,"159         (4F9.0)     dSys,dStat,dTot,dData //Relative uncertainties (dData/Data) in per-cents.");
	addC4headerLine(o5prn,"#C5EXT2"	,"168         (5F9.0)     dSys,dStat,dOther,dTot,dData //Relative uncertainties (dData/Data) in per-cents");
	else {
//	addC4headerLine(o5prn,"#C5EXT2"	,"150          *          dTotal,dSys,dStat,M0,dM0,M1,dM1  //Relative uncertainties and monitor data");
//2020	addC4headerLine(o5prn,"#C5EXT2"	,"150         (7F9.0)     dTotal,dSys,dStat,M0,dM0,M1,dM1  //Relative uncertainties and monitor data");
//2020	addC4headerLine(o5prn,"#+"	,"                      //dTotal,dSys,dStat,dM0,dM1: in per-cents;  M0: old montor;  M1: new monitor");
//20209	addC4headerLine(o5prn,"#C5EXT2"	,"150         (8F9.0)     dSys,dStat,dTotal,dData,M0,dM0,M1,dM1  //Relative uncertainties and monitor data");
	addC4headerLine(o5prn,"#C5EXT2"	,"168         (9F9.0)     dSys,dStat,dOther,dTotal,dData,M0,dM0,M1,dM1 //Relative uncertainties and monitor data");
	addC4headerLine(o5prn,"#+"	,"                      //dSys,dStat,dOther,dTot,dData,M0,dM0,M1,dM1:: in per-cents; M0:old montor;  M1:new monitor");
/*	if ((x4m0!=null)||(x4m1!=null))
	if (x4corr!=null)
	addC4headerLine(o5prn,"#X4MONITOR",x4corr.monitorReaction);
	if (x4m0!=null)
	addC4headerLine(o5prn,"#M0"	,x4m0.Title);
//20201213	addC4headerLine(o5prn,"#C5M0"	,x4m0.Title);
	if (x4m1!=null)
	addC4headerLine(o5prn,"#M1"	,x4m1.Title);
//20201213	addC4headerLine(o5prn,"#C5M1"	,x4m1.Title);
*/

	}

	addC4headerLine(o5prn,"#C5DATA"	,""+nRow);
//	addC4headerLine(o5prn,"#C.M.Flag       "+c4obj.strBoolCmLab+" "+c4_X4CM+"  "+str);
//	addC4headerLine(o5prn,"#C.M.Flag       "+c4obj.strBoolCmLab+"  "+c4obj.strDataCmLab);
//	addC4headerLine(o5prn,"#C.M.Flag",c4obj.strBoolCmLab+" "+c4obj.c4_X4CM+"  "+c4obj.strDataCmLab);
//?	addC4headerLine(o5prn,c4obj.strLegend1,"");
//?	addC4headerLine(o5prn,c4obj.strLegend2,"");

	return nRow;
    }
    public String getDATAHDR(x4reaction xreacode)
    {
	String strout="";
	for (int ii=0; ii<xreacode.vVars.size(); ii++) {
	    x4var xvar=(x4var)xreacode.vVars.elementAt(ii);
	    if (xvar.Variable_Value!=1) continue;
	    if (xvar.Variable_Number!=0) continue;
	    strout+=" "+xvar.Header;
	    switch (xvar.PlottingFlags1int[0]) {
		case x4dict024dt.TYPE_VALUE:
				break;
		case x4dict024dt.TYPE_MIN:	strout+=":Col[X]=[2]";
				break;
		case x4dict024dt.TYPE_MAX:	strout+=":Col[X]=[3]";
				break;
		case x4dict024dt.TYPE_APRX:	strout+=":Col[X]=[4]";
				break;
	    }

	}
	return strout.trim();
    }
    public String getYFuncXStr(int nX)
    {
	String strOut="Y = Y";
	if (nX<=0) return strOut;
	int ii;
	strOut+="(";
	for (ii=0; ii<nX; ii++) {
	    if (ii>0) strOut+=",";
	    strOut+="X"+(ii+1);
	}
	strOut+=")";
	return(strOut);
    }



    int	c4_MF=0;
    int	c4_MT=0;
    String c4str="";


static int lnn=12;
static int lii=1;
//static int lii=6;

    int npts_C5_inverted=0;
    int npts_C5_total=0;
    Vector vProcessDataC4log=new Vector();

    public int processDataC4(PrintWriter prnOut,x4reaction xreacode
	,c5arr c5data)
//2020	,Float c4data[][])
    {
	String str,str1,strFamFlag;
	int iRow,iCol,i,nn,ii,nn2,nn3,ierr;
//	int nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	x4reacstr xreacstr;

	boolean flagLVL0=true;
	npts_C5_total=0;
	npts_C5_inverted=0;

	vProcessDataC4log=new Vector();

//	if (c4_MT==0) return -1;
//++	if ((c4_MF==0)||(c4_MT==0)) return -2;//run1

	if (xreacode.IndepVarFamilyCode.trim().equals("?")) return -3;

//test	prnOut=new PrintWriter(System.out,true);//test
//	Vector vec=xreacode.vVars;
	Float rr;
	if (c5data==null) return -4;
	if (c5data.c4data==null) return -4;
	DnRow=c5data.c4data.length;
	if (DnRow<=0) return -1;
	if (c5data.c4data[0]==null) return -4;
	DnCol=c5data.c4data[0].length;
	if (DnCol<=0) return -1;

if (false)
System.out.println("\n___0___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);

if (false)
System.out.println("\n____processDataC4: xreac.BasicUnits=["+xreacode.BasicUnits+"]"
	+" DnRow="+DnRow+" DnCol="+DnCol+" CmLab="+c4obj.strBoolCmLab
	+"\n____c4ene="+xreacode.c4ene
	+(xreacode.c4ene==null? "" : ",L="+xreacode.c4ene.length+",[0]="+xreacode.c4ene[0])
	+"\n____c4dataErrTot="+xreacode.c4dataErrTot
	+(xreacode.c4dataErrTot==null? "" : ",L="+xreacode.c4dataErrTot.length+",[0]="+xreacode.c4dataErrTot[0])
	);
//xreacode.printC4data2array(c5data,"x4toc5.c5data");
//System.out.println("...DnRow="+DnRow+" DnCol="+DnCol+" CmLab="+c4obj.strBoolCmLab);

	x4cm2lab c2lab=null;
	x4cm2lab c2lab_inv=null;
	boolean flagInvertedReacAngle=false;
	boolean flags_CM[]=new boolean[4];
	str=c4obj.strBoolCmLab;
	for (i=0,nn=1,nn2=0; i<4; i++,nn*=2) {
	    if (str.charAt(i)=='1') {
		nn2+=nn;
		flags_CM[i]=true;
	    }
	    else flags_CM[i]=false;
	}
//System.out.println("...DnRow="+DnRow+" DnCol="+DnCol+" CmLab="+c4obj.strBoolCmLab+" nn2="+nn2);

//System.out.println("___tryToConvertCM2Lab="+tryToConvertCM2Lab+" nn2="+nn2);

	if (tryToConvertCM2Lab)
//	if ((nn2>=1)&&(nn2<8))
	if (onlyCalcInverseReact||((nn2>=1)&&(nn2<8)))	//2018-04-26
	if (c4_MF==4)
//	if (xreacode.BasicUnits.equals("B/SR"))
	{
	    c2lab=new x4cm2lab();
//test-output	c2lab.setPrintWriter(sysOut);
//	    c2lab.set_x4reaction("3-LI-7(A,EL)3-LI-7,,DA");
//	    c2lab.mf4cm3flags(false,true,false);//en,an,sig
//	    c2lab.mf4sig2lab(2.489e6,0.01e6,78.37e-3,4.213e-3,150.,0.);
	    c2lab.set_x4reaction(xreacode.reacode);
	    if (xreacode.flagRutherfordRatio) flags_CM[1]=false;
	    c2lab.mf4cm3flags(flags_CM[0],flags_CM[2],flags_CM[1]);//en,an,sig

	    if (onlyCalcInverseReact)
	    {
		c2lab_inv=new x4cm2lab();
if (extDebug1invda)
System.out.println("\t\t_________xreacode_inv.reacode="+xreacode_inv.reacode);
//test-output	c2lab_inv.setPrintWriter(sysOut);
		c2lab_inv.set_x4reaction(xreacode_inv.reacode);
		c2lab_inv.mf4cm3flags(false,true,false);
	    }

	}

	//2021-10-05
	if ((!xreacode.BasicUnits.equals("B/SR"))
	   &&(!xreacode.flagRutherfordRatio)
	   &&(xreacode.reacode.indexOf("REL")<0)
	)
        c2lab=null;

	String log_cm2lab_ang0=null;
	String log_cm2lab_ang1=null;
	String log_cm2lab_dat0=null;
	String log_cm2lab_dat1=null;

	String log_inv_ene0=null;
	String log_inv_ene1=null;
	String log_inv_ang0=null;
	String log_inv_ang1=null;
	String log_inv_sig0=null;
	String log_inv_sig1=null;

	String s1,s2,f21;
	boolean trasnformationDone;
//	Float dSys,dStat,dTotal,dData,Data;
	boolean foundTheta0max=false;
	int rriAlgorithm=0;//2022-01-26
	boolean rriAlgorithmChanged=false;
	x4cm2lab c2lab_ruth=null;
	float angle_for_ruth=0;
	for (iRow=0; iRow<DnRow; iRow++) {
	    Float dSys=null,dStat=null,dMrc=null,dTotal=null,dData=null,Data=null;
//	    System.out.println("...processDataC4:iRow="+iRow+"/"+DnRow);
if (extDebug)
	    System.out.println("...processDataC4:iRow="+iRow
+" "+c4obj.c4_i78+" ["+c4obj.c4_DataFamFlag[6]+"]");
	    trasnformationDone=false;
	    boolean cm2labOK=false;

	    ii=c4obj.importC4objFrom2array(c5data,iRow);
if (false)
System.out.println("\n___1___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]+" c4_MF:"+c4_MF);
//System.out.println("20211004...processDataC4:iRow="+iRow+"/"+DnRow+" importC4objFrom2array:"+ii);
	    if (ii<0) continue;

	    flagLVL0=true;
	    if (c4obj.c4_i78.equals("LVL"))
	    if (c4obj.c4_Data[6]!=null)
	    if (c4obj.c4_Data[6].floatValue()>1e-5)
	    flagLVL0=false;

	    if (false)
	    if (!flagLVL0) {
		onlyCalcInverseReact=false;
		c4obj_inv=null; xreacode_inv=null;
	    }

//	    if ((c4_MF==4)||(c4_MF==204))
	    if (convertRutherfordRatio2xs)
	    if (xreacode.flagRutherfordRatio)
	    if (c4obj.c4_Data[4]!=null)
	    angle_for_ruth=(float)cos2ang(c4obj.c4_Data[4]);

//c4obj.c4_Data[0]=new Float(6.700000e+00);
//c4obj.c4_Data[0]=new Float(6.7000003e+00);
if (extDebug)
	    System.out.println("...2...processDataC4:iRow="+iRow
		+" ["+c4obj.c4_i78+"]"//+" ["+c4obj.c4_DataFamFlag[6]+"]"
		+" 0["+c4obj.c4_Data[0]+"]"
		+" 09g["+"".format("%9g",c4obj.c4_Data[0])+"]"
		+" 06e["+"".format("%.6e",c4obj.c4_Data[0])+"]"
		+" 2["+c4obj.c4_Data[2]+"]"
		+" 3["+c4obj.c4_Data[3]+"]"
		+" 4["+c4obj.c4_Data[4]+"]"
+((c4obj.c4_Data[4]==null)?"":" AN=["+(float)cos2ang(c4obj.c4_Data[4])+"]")
		+" 6["+c4obj.c4_Data[6]+"]"
		+" flagLVL0="+flagLVL0
		+" ProdM="+c4obj.c4_ProdM
		+" yValue:"+c4obj.c4_compValueSetFromCh
		);

	    dSys=c4obj.c4_Data[c5arr.indSys];
	    dMrc=c4obj.c4_Data[c5arr.indMrc];
	    dStat=c4obj.c4_Data[c5arr.indStat];
	    dTotal=c4obj.c4_Data[c5arr.indTot];
	    dData=c4obj.c4_Data[3];
	    Data=c4obj.c4_Data[2];
	    if (Data!=null)
	    if (Data.floatValue()!=0.f)
	    {
		if (dSys!=null) dSys=new Float(dSys/Data);
		if (dStat!=null) dStat=new Float(dStat/Data);
		if (dTotal!=null) dTotal=new Float(dTotal/Data);
		if (dData!=null) dData=new Float(dData/Data);
	    }

/*
//2018-04-12: invert DA
	    if (onlyCalcInverseReact)
	    {
//		double Ea=c4obj.c4_Data[0];
//		double Sab=c4obj.c4_Data[2];
		//System.out.println(" E0="+strpad(""+(float)c4obj.c4_Data[0],13)+" Sig0="+strpad(""+(float)c4obj.c4_Data[2],13));
		ierr=c4obj.calcInverseReact();
		//System.out.println(" E1="+strpad(""+(float)c4obj.c4_Data[0],13)+" Sig1="+strpad(""+(float)c4obj.c4_Data[2],13));
//		double kSba=c4obj.c4_Data[2]/Sab/(Ea/c4obj.c4_Data[0]);
//		System.out.println(""
//		+" E0="+strpad(""+Ea,13)
//		+" Sig0="+strpad(""+(float)Sab,13)
//		+" E1="+strpad(""+(float)c4obj.c4_Data[0],13)
//		+" Sig1="+strpad(""+(float)c4obj.c4_Data[2],13)
//		+" kSig="+strpad(""+(float)kSba,13)
//		);
		ii=c4obj.exportC4obj2array(c5data,iRow);
		trasnformationDone=true;
	    }
*/

//System.out.println(" flagRutherfordRatio="+xreacode.flagRutherfordRatio);
//System.out.println("...xreac.BasicUnits="+xreacode.BasicUnits);
//20268002;21177002

//if (cos2ang(c4obj.c4_Data[4])>20) extDebug1invda2=true; else extDebug1invda2=false;

if (extDebug1invda2)
System.out.println("_0_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
+" An="+(float)(cos2ang(c4obj.c4_Data[4]))+"deg"
+" dAn:"+c4obj.c4_Data[5]+"="+(float)(dcos2ang(c4obj.c4_Data[4],c4obj.c4_Data[5]))+"deg"
);
if (extDebug1invda)
System.out.println("_0_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
//+" An="+(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180)+"deg"
+" An:"+c4obj.c4_Data[4]+"="+(float)(cos2ang(c4obj.c4_Data[4]))+"deg"
+" dAn:"+c4obj.c4_Data[5]+"="+(float)(dcos2ang(c4obj.c4_Data[4],c4obj.c4_Data[5]))+"deg"
//+"+"+(float)(Math.acos(c4obj.c4_Data[4]+c4obj.c4_Data[5])/Math.PI*180)
+"+dcos:"+(c4obj.c4_Data[5])
+" Sig1="+(float)(c4obj.c4_Data[2]*1000)+"mb");

	    if (c2lab!=null) {
		double Ene=0,dEne=0,Dat=0,dDat=0,Angle=0,dAngle=0;
		if (c4obj.c4_Data[0]!=null) Ene =c4obj.c4_Data[0];
		if (c4obj.c4_Data[1]!=null) dEne=c4obj.c4_Data[1];
		if (c4obj.c4_Data[2]!=null) Dat =c4obj.c4_Data[2];
		if (c4obj.c4_Data[3]!=null) dDat=c4obj.c4_Data[3];
		if (c4obj.c4_Data[4]!=null) {
//		    Angle=Math.acos(c4obj.c4_Data[4])/Math.PI*180;
		    Angle=cos2ang(c4obj.c4_Data[4]);
		    if (c4obj.c4_Data[5]!=null) {
//			dAngle=Angle-Math.acos(c4obj.c4_Data[4]+c4obj.c4_Data[5])/Math.PI*180;
			dAngle=dcos2ang(c4obj.c4_Data[4],c4obj.c4_Data[5]);
		    }
		}
if (extDebug1invda)
System.out.println("_1_ini_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
+" An="+(float)(Angle)+"deg"+" dAn="+(float)(dAngle)+"deg"
+" Sig1="+(float)(c4obj.c4_Data[2]*1000)+"mb"
+" dSig1="+(float)(c4obj.c4_Data[3]*1000)+"mb"
+" "+c4obj.strBoolCmLab);
if (extDebug1invda)
System.out.println("_1_ini_E1="+(float)(c4obj.c4_Data[0])+"eV"
+" An="+(float)(Angle)+"deg"+" dAn="+(float)(dAngle)+"deg"
+" Sig1="+(c4obj.c4_Data[2])+"b"
+" dSig1="+(c4obj.c4_Data[3])+"b"
+" "+c4obj.strBoolCmLab);
		c4obj.savePartDYs();
		c2lab.mf4sig2lab(Ene,dEne,Dat,dDat,Angle,dAngle);
//System.out.println("____________c2lab.ierr="+c2lab.ierr);
		if (c2lab.ierr==0) cm2labOK=true;
if (!cm2labOK) {//20211005
    xreacode.str2CompNotes("Conversion from C.M. to Lab failed ["+xreacode.DatasetID+"]");
    c2lab=null;
}
if (cm2labOK) {//20211005
    xreacode.str2CompNotes("Conversion from C.M. to Lab: successful ["+xreacode.DatasetID+"]");

	if (c2lab.warningTheta0max)
	if (c2lab.Theta0max!=null)
	if (!foundTheta0max)
	{
//	xreacode.str2CompNotes("Warning: Theta-lab ambiguity. Conversion Theta-CM to Lab for heavy projectile (M2>M1): Theta-CM exceeds max="
	xreacode.str2CompNotes("Warning: Theta-Lab ambiguity. Conversion Theta-CM to Lab for M2>M1: Theta-CM exceeds max="
	+"".format("%.2f",c2lab.Theta0max)
//	+c2lab.Theta0max.floatValue()
	+"deg"
	+" pt:"+iRow
	);
	foundTheta0max=true;
	}

		c4obj.c4_Data[2]=new Float(c2lab.Data);
		c4obj.c4_Data[3]=new Float(c2lab.dData);
//		c4obj.c4_Data[4]=new Float(myCos((float)c2lab.ThetaLab));
//		c4obj.c4_Data[4]=new Float(Math.cos(c2lab.ThetaLab*Math.PI/180));
		c4obj.c4_Data[4]=new Float(ang2cos(c2lab.ThetaLab));
		c4obj.restorePartDYs();
		if (c4obj.c4_Data[5]!=null) {
		    c4obj.c4_Data[5]=new Float(dang2cos(c2lab.ThetaLab,dAngle));
		}
if (extDebug1invda)
System.out.println("_2_lab_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
+" An="+(float)(cos2ang(c4obj.c4_Data[4]))+"deg"
+"+"+(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180 - Math.acos(c4obj.c4_Data[4]+c4obj.c4_Data[5])/Math.PI*180)
+" Sig1="+(float)(c4obj.c4_Data[2]*1000)+"mb"
+" dSig1="+(float)(c4obj.c4_Data[3]*1000)+"mb"
);

//?20211004		ii=c4obj.exportC4obj2array(c5data,iRow);
if (false)
System.out.println("\n___2___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);
		ii=c4obj.exportC4obj2array(c5data,iRow);//?20211004
if (false)
System.out.println("\n___3___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);
		c4obj.c4_X4CM=' ';
		c4obj.strBoolCmLabNew="0000";
}//cm2labOK

//if (extDebug)
{

if (extDebug)
System.out.println("___flags_CM:"+flags_CM[0]+":"+flags_CM[1]+":"+flags_CM[2]
+"...Ene="+(float)Ene+" c2lab.Energy="+(float)c2lab.Energy
+"...Ang="+(float)((int)(Angle*10+0.5))/10.+" c2lab.ThetaLab="+c2lab.ThetaLab);

    if (cm2labOK) //20211005
    if (flags_CM[2]) {
//	s1=""+(float)((int)(Angle*10+0.5))/10.;
//	s2=""+c2lab.ThetaLab;
	s1=real2str((float)((int)(Angle*10+0.5))/10.f);
	s2=real2str((float)c2lab.ThetaLab);
	log_cm2lab_ang1=(iRow+1)+":("+s1+" TO "+s2+")";
	if (log_cm2lab_ang0==null) log_cm2lab_ang0=log_cm2lab_ang1;
if (extDebug)
System.out.println("...c2lab.ThetaCM="+(float)((int)(Angle*10+0.5))/10.+" ThetaLab="+c2lab.ThetaLab);
    }
    if (cm2labOK) //20211005
    if (flags_CM[1]) {
	//s1=""+(float)Dat;
	//s2=""+(float)c2lab.Data;
	//s1="".format("%e",(float)Dat);
	//s2="".format("%e",(float)c2lab.Data);
	s1=real2str((float)Dat);
	s2=real2str((float)c2lab.Data);
	f21="";
//	if (c2lab.Data!=0) if (Dat!=0) f21="F="+real2str((float)(c2lab.Data/Dat));
//	if (c2lab.Data!=0) if (Dat!=0) f21=" fc="+real2str((float)(Dat/c2lab.Data));
	if (c2lab.Data!=0) if (Dat!=0) f21=" fc:"+real2str((float)(setPrecision(c2lab.Data/Dat,4)));
//	,theta_inv,E1_inv,dE1_inv,Sigma_inv,dSigma_inv/Sigma_inv*100));
//	log_cm2lab_dat1=(iRow+1)+":("+s1+" to "+s2+")"+f21;
	log_cm2lab_dat1=(iRow+1)+":("+s1+" to "+s2+f21+")";
	if (log_cm2lab_dat0==null) log_cm2lab_dat0=log_cm2lab_dat1;
if (extDebug)
System.out.println("...c2lab.DataCM="+(float)Dat+" DataLab="+(float)c2lab.Data);
if (extDebug)
System.out.println("...c2lab.Ratio="+(float)c2lab.Ratio);
    }
}
		if (cm2labOK) //20211005
		trasnformationDone=true;
	    }

	    if (convertRutherfordRatio2xs)
	    if (xreacode.flagRutherfordRatio) {
		myRuth r33o;
		r33o=new myRuth();
		int ZATarg,ZAProj;
		float MassesTarg,MassesProj;
		float ene,Theta,yy0,yy1;
		ZATarg=xreacode.xTarg1.za;
		ZAProj=xreacode.xProj1.za;
		MassesTarg=(float)(xreacode.xTarg1.MASS_mev/931.494);
		MassesProj=(float)(xreacode.xProj1.MASS_mev/931.494);
		ene=(float)(c4obj.c4_Data[0]/1e3);
		Theta=(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180);

//2022-01-28:todo:Theta for algorithm=2 must be Theta-CM
//??		if (c2lab.THETA0cm!=null) Theta=c2lab.THETA0cm.floatValue();

	    if (c2lab_ruth==null) {
		c2lab_ruth=new x4cm2lab();
//test-output	c2lab_ruth.setPrintWriter(new PrintWriter(System.out,true));
		c2lab_ruth.set_x4reaction(xreacode.reacode);
		c2lab_ruth.mf4cm3flags(flags_CM[0],flags_CM[2],flags_CM[1]);//en,an,sig
		//System.out.println("__flags_CM: ene:"+flags_CM[0]+" an:"+flags_CM[2]+" data:"+flags_CM[1]+" c2lab:"+c2lab);//en,an,sig
	    }
	    if (flags_CM[2]&&(c2lab==null)) {//ang-cm not translated to ang-lab
		c2lab_ruth.mf4sig2lab(ene,0,1,0,Theta,0);
		if (c2lab_ruth.ierr==0) {
		    Theta=(float)c2lab_ruth.ThetaLab;
		}
	    }
	    r33o.set_x4cm2lab(c2lab_ruth);

double convFactor=0;
/*
if (rriAlgorithm==2) //algorithm=2 already fixed
convFactor=r33o.getRuthTerm0x(ZATarg,ZAProj,MassesTarg,MassesProj,ene,Theta);//C4-algorithm:iAlgorithm=2
else
convFactor=r33o.getRuthTerm(ZATarg,ZAProj,MassesTarg,MassesProj,ene,Theta);//R33-algorithm:iAlgorithm=1, if fails:C4-algorithm:iAlgorithm=2
if (rriAlgorithm==0) rriAlgorithm=r33o.iAlgorithm;
if (r33o.iAlgorithm!=rriAlgorithm) rriAlgorithmChanged=true;
*/

//System.out.println("---rr---tryToConvertCM2Lab:"+tryToConvertCM2Lab+" convertRutherfordRatio2xs:"+convertRutherfordRatio2xs+" flags_CM[2]:"+flags_CM[2]
//+"\tTheta="+Theta+" angle_for_ruth:"+angle_for_ruth+" ene="+ene);

/*
if (c2lab_ruth.flagANG_CM) c2lab_ruth.THETA0cm=new Double(angle_for_ruth);
else c2lab_ruth.THETA0cm=null;
convFactor=r33o.getRuthTerm(ZATarg,ZAProj,MassesTarg,MassesProj,ene,Theta);//R33-algorithm:iAlgorithm=1, if fails:C4-algorithm:iAlgorithm=2
if (rriAlgorithm==0) rriAlgorithm=r33o.iAlgorithm;
if (r33o.iAlgorithm!=rriAlgorithm) rriAlgorithmChanged=true;
*/

//2023-08-28:D0147.x4
//convertRutherfordRatio2xs=true
//if (!tryToConvertCM2Lab && flags_CM[2]) {
if (flags_CM[2]) {
//	convFactor=r33o.getRuthTerm0x(ZATarg,ZAProj,MassesTarg,MassesProj,ene,Theta);//C4-algorithm:iAlgorithm=0
	convFactor=r33o.getRuthTerm0x(ZATarg,ZAProj,MassesTarg,MassesProj,ene,angle_for_ruth);//C4-algorithm:iAlgorithm=0
}
else {//the same as above
	if (c2lab_ruth.flagANG_CM) c2lab_ruth.THETA0cm=new Double(angle_for_ruth);
	else c2lab_ruth.THETA0cm=null;
	convFactor=r33o.getRuthTerm(ZATarg,ZAProj,MassesTarg,MassesProj,ene,Theta);//R33-algorithm:iAlgorithm=1, if fails:C4-algorithm:iAlgorithm=2
	if (rriAlgorithm==0) rriAlgorithm=r33o.iAlgorithm;
	if (r33o.iAlgorithm!=rriAlgorithm) rriAlgorithmChanged=true;
}


		convFactor/=1e3;

//System.out.println("\tTarget:ZA="+ZATarg+"\t MU="+MassesTarg+"\tProj:ZA="+ZAProj+"\t MU="+MassesProj+" Theta="+Theta+" ene="+ene);
//System.out.println("\t...RR="+c4obj.c4_Data[2]+"\t convFactor="+(float)convFactor+" XS="+(float)(c4obj.c4_Data[2]*convFactor));
//if (extDebug)
//System.out.println("\tTheta="+Theta+" ene="+ene+" RR="+c4obj.c4_Data[2]+" XS="+(float)(c4obj.c4_Data[2]*convFactor));

//2022-01-28
if (false)
System.out.println("__ene:"+ene
+" Theta:"+"".format("%.2f",Theta)
+(c2lab_ruth!=null ? " Theta0cm:"+"".format("%.2f",c2lab_ruth.THETA0cm) : "")
+"\t...RR="+c4obj.c4_Data[2]
+"\tconvFactor="+(float)convFactor
+"\tXS="+(float)(c4obj.c4_Data[2]*convFactor)
+"\tiAlgorithm:"+r33o.iAlgorithm
+"\tc2lab_ruth.flagANG_CM="+c2lab_ruth.flagANG_CM
+"\trriAlgorithm:"+rriAlgorithm
);

		c4obj.c4_Data[2]=new Float(c4obj.c4_Data[2]*convFactor);
//		if (c4obj.c4_Data[3]!=null) c4obj.c4_Data[3]*=new Float(convFactor);
		if (c4obj.c4_Data[3]!=null) c4obj.c4_Data[3]=new Float(c4obj.c4_Data[3].floatValue()*convFactor);
		if (c4obj.c4_Data[c5arr.indSys]!=null) c4obj.c4_Data[c5arr.indSys]=new Float(c4obj.c4_Data[c5arr.indSys].floatValue()*convFactor);
		if (c4obj.c4_Data[c5arr.indMrc]!=null) c4obj.c4_Data[c5arr.indMrc]=new Float(c4obj.c4_Data[c5arr.indMrc].floatValue()*convFactor);
		if (c4obj.c4_Data[c5arr.indStat]!=null) c4obj.c4_Data[c5arr.indStat]=new Float(c4obj.c4_Data[c5arr.indStat].floatValue()*convFactor);
		if (c4obj.c4_Data[c5arr.indTot]!=null) c4obj.c4_Data[c5arr.indTot]=new Float(c4obj.c4_Data[c5arr.indTot].floatValue()*convFactor);
		c4obj.c4_DataUnits[2]="B/SR";
		c4obj.c4_DataUnits[3]="B/SR";


//?		if (c4obj.c4_X4CM=='C') c4obj.c4_X4CM=' ';
//		ii=c4obj.remakeC4Line(iRow,nRow,null);
		ii=c4obj.exportC4obj2array(c5data,iRow);
//		if (ii<0) continue;
//		c4str=c4obj.c4str;
//System.out.println("...c4_Data[5]="+c4obj.c4_Data[5]+" c4_MT="+c4obj.c4_MT);
//System.out.println("...c4str=["+c4str+"]");
		trasnformationDone=true;
//xreacode.str2CompNotes("Converted Rutherford ratio to XS");
//xreacode.str2CompNotes("Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR)");//20211005
//System.out.println("___processDataC4:______________Converted Rutherford ratio to XS______________");
	    }

	    if (xreacode.flagRutherfordRatio)
	    if (!convertRutherfordRatio2xs)
	    {
		c4obj.c4_MF=204;
		c4_MF=204;
	    }


	    npts_C5_total++;
	    xreacode.c4data_flag_inv[iRow]=false;

//2018-04-12: invert DA
	    if (onlyCalcInverseReact)
	    if (flagLVL0)
	    {
		npts_C5_inverted++;
		xreacode.c4data_flag_inv[iRow]=true;
		double Ene0=c4obj.c4_Data[0];
		double Sig0=c4obj.c4_Data[2];
		Double Ang0=null,dAng0=null,Ang1=null,dAng1=null;
		double ang0=0,dang0=0,ang1=0,dang1=0;

		if (c4obj.c4_Data[4]!=null) {
		//  ang0=Math.acos(c4obj.c4_Data[4])/Math.PI*180;
		    ang0=cos2ang(c4obj.c4_Data[4]);
		    if (c4obj.c4_Data[5]!=null) {
		//	dang0=(Math.acos(c4obj.c4_Data[4])/Math.PI*180 - Math.acos(c4obj.c4_Data[4]+c4obj.c4_Data[5])/Math.PI*180);
			dang0=dcos2ang(c4obj.c4_Data[4],c4obj.c4_Data[5]);
		    }
if (extDebug1invda)
System.out.println("_z_A0:"+" ang0="+(float)+ang0+"deg"+" dang0="+(float)+dang0+"deg");
		}

/*
System.out.println("[[[E0="+(float)c4obj.c4_Data[0]
+",Sig0="+(float)c4obj.c4_Data[2]
+(c4obj.c4_Data[3]==null? "":",arr[3]="+c4obj.c4_Data[3])
+(c4obj.c4_Data[c5arr.indSys]==null? "":",arr[8]="+c4obj.c4_Data[c5arr.indSys])
+(c4obj.c4_Data[c5arr.indStat]==null? "":",arr[9]="+c4obj.c4_Data[c5arr.indStat])
+(c4obj.c4_Data[c5arr.indMrc]==null? "":",arr[8]="+c4obj.c4_Data[c5arr.indMrc])
+(c4obj.c4_Data[c5arr.indTot]==null? "":",arr[10]="+c4obj.c4_Data[c5arr.indTot])
+(c4obj.c4_Data[11]==null? "":",arr[11]="+c4obj.c4_Data[11])
+(c4obj.c4_sData[9]==null? "":",sarr[9]="+c4obj.c4_sData[9])
+(c4obj.c4_sData[10]==null? "":",sarr[10]="+c4obj.c4_sData[10])
+(c4obj.c4_sData[11]==null? "":",sarr[11]="+c4obj.c4_sData[11])
);
*/
		ierr=c4obj.calcInverseReact();
/*
System.out.println("[[[E1="+(float)c4obj.c4_Data[0]
+",Sig1="+(float)c4obj.c4_Data[2]
+(c4obj.c4_Data[3]==null? "":",arr[3]="+c4obj.c4_Data[3])
+(c4obj.c4_Data[c5arr.indSys]==null? "":",arr[8]="+c4obj.c4_Data[c5arr.indSys])
+(c4obj.c4_Data[c5arr.indStat]==null? "":",arr[9]="+c4obj.c4_Data[c5arr.indStat])
+(c4obj.c4_Data[c5arr.indMrc]==null? "":",arr[8]="+c4obj.c4_Data[c5arr.indMrc])
+(c4obj.c4_Data[c5arr.indTot]==null? "":",arr[10]="+c4obj.c4_Data[c5arr.indTot])
+(c4obj.c4_Data[11]==null? "":",arr[11]="+c4obj.c4_Data[11])
+(c4obj.c4_sData[9]==null? "":",sarr[9]="+c4obj.c4_sData[9])
+(c4obj.c4_sData[10]==null? "":",sarr[10]="+c4obj.c4_sData[10])
+(c4obj.c4_sData[11]==null? "":",sarr[11]="+c4obj.c4_sData[11])
);
*/
		double Ene_inv=c4obj.c4_Data[0];
		double Sig_inv=c4obj.c4_Data[2];
if (extDebug1invda2)
		System.out.println(" E1="+strpad(""+(float)c4obj.c4_Data[0],13)+" Sig1="+strpad(""+(float)c4obj.c4_Data[2],13));
if (extDebug1invda)
System.out.println("_a_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
+" An="+(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180)+"deg"
+"+"+(float)+dang0+" Sig1="+(float)(c4obj.c4_Data[2]*1000)+"mb"
+" c2lab="+c2lab
);

		s1=real2str((float)(Ene0/1e6));
		s2=real2str((float)(Ene_inv/1e6));
		log_inv_ene1=(iRow+1)+":(FROM "+s1+" TO "+s2+")";
		if (log_inv_ene0==null) log_inv_ene0=log_inv_ene1;

		s1=real2str((float)Sig0);
		s2=real2str((float)Sig_inv);
		log_inv_sig1=(iRow+1)+":(FROM "+s1+" TO "+s2+")";
		if (log_inv_sig0==null) log_inv_sig0=log_inv_sig1;

		if (c2lab!=null) {
		    c2lab.lab2cm(Ene0/1e3,ang0,Sig0);//calc:ANG-CM
		    double Ang0_cm=c2lab.thetap;//CM
if (extDebug1invda)
		    System.out.println("\t=====lab2cm:"
			+" EN_CM="+(float)(c2lab.ecm/1e3)+"MeV"
			+" THETA_CM="+(float)c2lab.thetap+"deg"
			+" SIG_CM="+(float)(c2lab.sigmap*1e3)+"mb");
//if (false)
		    if (c2lab_inv!=null) {
			double Ene=0,dEne=0,Dat=0,dDat=0,Angle=0,dAngle=0;
			//Note,:c2lab_inv.mf4cm3flags(false,true,false);
			Ene=Ene_inv;  //Lab:CM=false
			Angle=Ang0_cm;//CM :CM=true
			Dat=Sig_inv;  //Lab:CM=false
			c2lab_inv.mf4sig2lab(Ene,dEne,Dat,dDat,Angle,dAngle);
//if (false)
if (extDebug1invda)
			System.out.println("_b_EnLab="+(float)(c2lab_inv.Energy/1e6)+"MeV"
			+" An="+(float)(c2lab_inv.ThetaLab)+"deg"
			+" Sig1="+(float)(c2lab_inv.Data*1000)+"mb");
			ang1=c2lab_inv.ThetaLab;
			dang1=dang0;
			c4obj.c4_Data[4]=new Float(ang2cos(ang1));
			if (c4obj.c4_Data[5]!=null) {
			    c4obj.c4_Data[5]=new Float(dang2cos(ang1,dang1));
			}

			s1=real2str((float)((int)(ang0*10+0.5))/10.f);
			s2=real2str((float)((int)(ang1*10+0.5))/10.f);
			log_inv_ang1=(iRow+1)+":(FROM "+s1+" TO "+s2+")";
			if (log_inv_ang0==null) log_inv_ang0=log_inv_ang1;

///			c4obj.c4_Data[2]=new Float(c2lab.Data);
///			c4obj.c4_Data[3]=new Float(c2lab.dData);
///			c4obj.c4_Data[4]=new Float(Math.cos(c2lab.ThetaLab*Math.PI/180));
///			c4obj.c4_Data[4]=new Float(Math.cos(c2lab.ThetaLab*Math.PI/180));
			flagInvertedReacAngle=true;
		    }

		}


//		double kSba=c4obj.c4_Data[2]/Sab/(Ea/c4obj.c4_Data[0]);
//		System.out.println(""
//		+" E0="+strpad(""+Ea,13)
//		+" Sig0="+strpad(""+(float)Sab,13)
//		+" E1="+strpad(""+(float)c4obj.c4_Data[0],13)
//		+" Sig1="+strpad(""+(float)c4obj.c4_Data[2],13)
//		+" kSig="+strpad(""+(float)kSba,13)
//		);
//if (!extDebug1invda2) c4obj.initDataC4();
//if (!extDebug1invda2) {c4obj.c4_Data[4]=null;c4obj.c4_Data[5]=null;}
		ii=c4obj.exportC4obj2array(c5data,iRow);

if (extDebug1invda)
System.out.println("_c_E1="+(float)(c4obj.c4_Data[0]/1e6)+"MeV"
+" An="
//+(float)c4obj.c4_Data[4]
+" An:"+c4obj.c4_Data[4]+"="+(float)(cos2ang(c4obj.c4_Data[4]))+"deg"
+" dAn:"+c4obj.c4_Data[5]+"="+(float)(dcos2ang(c4obj.c4_Data[4],c4obj.c4_Data[5]))+"deg"
//+(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180)+"deg"
//+"+-"+(float)(Math.acos(c4obj.c4_Data[5])/Math.PI*180)
//+"+"+(c4obj.c4_Data[5])
//+"+"+(float)(Math.acos(c4obj.c4_Data[4])/Math.PI*180 - Math.acos(c4obj.c4_Data[4]+c4obj.c4_Data[5])/Math.PI*180)
+" Sig1="+(float)(c4obj.c4_Data[2]*1000)+"mb");
		trasnformationDone=true;
	    }


/*2020-05-04
	    if (trasnformationDone)
	    {
if (extDebug1invda)
System.out.println("\t___COS="+c4obj.c4_Data[4]+" DCOS="+c4obj.c4_Data[5]+" Data="+c4obj.c4_Data[2]+" ii="+iRow);
//		Data=c4obj.c4_Data[2];
//		if (Data!=null)
//		if (Data.floatValue()!=0.f)
		if (c4obj.c4_Data[2]!=null)
		if (c4obj.c4_Data[2].floatValue()!=0.f)
		{
if (extDebug)
if ((dSys!=null)||(dStat!=null))
System.out.println("\t...dSys.abs="+c4obj.c4_Data[c5arr.indSys]+" dStat.abs="+c4obj.c4_Data[c5arr.indStat]+" Data.abs="+Data+" dSys.rel="+dSys+" dStat.rel="+dStat);
		    Data=c4obj.c4_Data[2];
		    if (dSys!=null) {
			dSys=new Float(dSys*Data);
			c4obj.c4_Data[c5arr.indSys]=dSys;
		    }
		    if (dStat!=null) {
			dStat=new Float(dStat*Data);
			c4obj.c4_Data[c5arr.indStat]=dStat;
		    }
		    if (dMrc!=null) {
			dMrc=new Float(dMrc*Data);
			c4obj.c4_Data[c5arr.indMrc]=dMrc;
		    }
		    if ((dSys!=null)||(dStat!=null)||(dMrc!=null)) {
			ii=c4obj.exportC4obj2array(c5data,iRow);
if (extDebug)
System.out.println("\t...dSys.abs="+c4obj.c4_Data[c5arr.indSys]+" dStat.abs="+c4obj.c4_Data[c5arr.indStat]+" Data.abs="+Data+" ii="+iRow);
		    }
		}
	    }
*/
	    if (trasnformationDone) ii=c4obj.exportC4obj2array(c5data,iRow);//2020-05-04
	}
if (false)
System.out.println("\n___7___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);

//2018-04-12: Invert DA: Moved up
	if (c2lab!=null) {
	    if (flags_CM[2]) {
		s1="";
		if (log_cm2lab_ang0!=null) {
		    s1=" "+log_cm2lab_ang0;
		    if (!log_cm2lab_ang0.equals(log_cm2lab_ang1)) {
			s1+=" "+log_cm2lab_ang1;
		    }
		}
//		vProcessDataC4log.addElement("\tConverted Angle C.M. TO LAB"+s1);
xreacode.str2CompNotes("Converted Angle C.M. to LAB"+s1);//20211005
	    }
	    if (flags_CM[1]) {
		s1="";
		if (log_cm2lab_dat0!=null) {
		    s1=" "+log_cm2lab_dat0;
		    if (!log_cm2lab_dat0.equals(log_cm2lab_dat1)) {
			s1+=" "+log_cm2lab_dat1;
		    }
		}
//		vProcessDataC4log.addElement("\tConverted Sigma C.M. to LAB"+s1);
xreacode.str2CompNotes("Converted Data C.M. to LAB"+s1);//20211005
	    }
	}

	if (convertRutherfordRatio2xs)
	if (xreacode.flagRutherfordRatio) {
//20211004  vProcessDataC4log.addElement("\tConverted Rutherford ratio TO X.S.");
//?	    vProcessDataC4log.addElement("\tConverted Rutherford ratio to XS");

//if (rriAlgorithm!=2)
//xreacode.str2CompNotes("Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR)");
//else
//xreacode.str2CompNotes("Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR). Warning: algorithm=2");
if (!rriAlgorithmChanged)
xreacode.str2CompNotes("Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR). Algorithm="+rriAlgorithm);
else
xreacode.str2CompNotes("Converted Rutherford ratio to XS: DA(RTH) to DA(B/SR). Warning: Algorithm changed");
	}

//	System.out.println("______________________"+" vCompNotes.size()="+xreacode.vCompNotes.size());
//	vProcessDataC4log.addAll(xreacode.vCompNotes);
	for (int i1=0; i1<xreacode.vCompNotes.size(); i1++) {
	    String str1c=(String)xreacode.vCompNotes.elementAt(i1);
	    vProcessDataC4log.addElement("\t"+str1c);
	}
if (false)
System.out.println("\n___8___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);

//System.out.println("\n___8___processDataC4::xreacode.tempGiven:"+xreacode.tempGiven);
	if (xreacode.tempGiven)
	{
	    int mf1=c4obj.c4_MF%10;
	    int mf10=(c4obj.c4_MF/10)%10;
	    int mf100=c4obj.c4_MF/100;
	    if (mf10==0) {
		mf10=5;
		mf1=mf1+mf10*10+mf100*100;
xreacode.str2CompNotes("RESET MF FROM "+c4_MF+" TO "+mf1+" DUE TO ["+xreacode.c4_i78+"]");
		c4obj.c4_MF=mf1;
		c4_MF=mf1;
	    }
	}


//	c4obj.printCompNotes(sysOut);
	if (onlyCalcInverseReact)
	if (npts_C5_inverted>0)
	{
	    vProcessDataC4log.addElement("\tDATA converted to inverse reaction"
		+" MFMT="+c4obj_inv.c4_MF+":"+c4obj_inv.c4_MT
		+" "+xreacode_inv.reacode);
	    vProcessDataC4log.addElement("\t    E1="+xreacode.strEa);
//	    vProcessDataC4log.addElement("\t    ENE="+xreacode.strEa);
//	    vProcessDataC4log.addElement("\t    SIG="+xreacode.strSi);
	    if (flagInvertedReacAngle)
	    vProcessDataC4log.addElement("\t    A1:(A0->A0CM)->(A1CM=A0CM)->(A1CM->A1)");
//	    vProcessDataC4log.addElement("\t    A1:(A0LAB->A0CM)->(A1CM=A0CM)->(A1CM->A1LAB)");
//	    vProcessDataC4log.addElement("\t    ANG:(A0LAB->A0CM)->(A1CM=A0CM)->(A1CM->A1LAB)");
//	    vProcessDataC4log.addElement("\t    A1CM=A0CM");
//	    vProcessDataC4log.addElement("\t    A1:(A0LAB->A0CM)->(A1CM=A0CM)->(A1CM->A1LAB)");
	    vProcessDataC4log.addElement("\t    SIG1="+xreacode.strSi);
//	    vProcessDataC4log.addElement("\t    SIG="+xreacode.strSi);

	    if (log_inv_ene0!=null) {
		s1=" "+log_inv_ene0;
		if (!getLogStr(log_inv_ene0).equals(getLogStr(log_inv_ene1))) {
		    s1+=" "+log_inv_ene1;
		}
		vProcessDataC4log.addElement("\t      CALC.ENE."+s1);
	    }
	    if (log_inv_ang0!=null) {
		s1=" "+log_inv_ang0;
		if (!getLogStr(log_inv_ang0).equals(getLogStr(log_inv_ang1))) {
		    s1+=" "+log_inv_ang1;
		}
		vProcessDataC4log.addElement("\t      CALC.ANG."+s1);
	    }
	    if (log_inv_sig0!=null) {
		s1=" "+log_inv_sig0;
		if (!getLogStr(log_inv_sig0).equals(getLogStr(log_inv_sig1))) {
		    s1+=" "+log_inv_sig1;
		}
		vProcessDataC4log.addElement("\t      CALC.SIG."+s1);
	    }


	    Float E1thrLevel1=null;
	    x4level x4lev=null, x4lev1=null;

	    x4lev=x4level.readLevelsFile(xreacode.xProd1.zz,xreacode.xProd1.aa);
	    if (x4lev!=null)
	    if (x4lev.levels.size()>1)
	    if (!xreacode.xreac1.SF5.equals("PAR"))
	    {
		x4lev1=(x4level)x4lev.levels.elementAt(1);
if (extDebug)
		System.out.println(""
		+" Elem=["+x4lev.Elem+"]"
		+" Z="+x4lev.ZZ
		+" A="+x4lev.AA
		+" iLevel="+1
		+" E-Level(keV)="+x4lev1.elvr
		+"");
		float qval=xreacode.QValue1.floatValue();//MeV
		float prod1lev1=(float)(x4lev1.elvr/1e3);//MeV
if (extDebug)
System.out.println(""
+"\n\t Targ1:"+xreacode.xreac1.SF1+"\t    mA:MeV="+(float)xreacode.xTarg1.MASS_mev
+"\n\t Proj1:"+xreacode.xreac1.SF2+"\t    ma:MeV="+(float)xreacode.xProj1.MASS_mev
+"\n\t Ejec1:"+xreacode.xreac1.SF3+"\t    mb:MeV="+(float)xreacode.xEjec1.MASS_mev
+"\n\t Prod1:"+xreacode.xreac1.SF4+"\t    mB:MeV="+(float)xreacode.xProd1.MASS_mev
+"\n\t\t\t ma+mA:MeV="+(float)(xreacode.xProj1.MASS_mev+xreacode.xTarg1.MASS_mev)
+"\n\t\t\t mb+mB:MeV="+(float)(xreacode.xEjec1.MASS_mev+xreacode.xProd1.MASS_mev)
);

		float ma=(float)xreacode.xProj1.MASS_mev;
		float mA=(float)xreacode.xTarg1.MASS_mev;
		float e0min4prod1lev1=(prod1lev1-qval)*(ma+mA)/mA;//MeV
//?if (e0min4prod1lev1<0) e0min4prod1lev1=0;
		float emin4prod1lev1=(float)((e0min4prod1lev1*xreacode.E0a1+xreacode.E0a2)/xreacode.E0a3);

		if (emin4prod1lev1>0) {
//vProcessDataC4log.addElement("\t\tCalculations are correct up to E1="+emin4prod1lev1);
vProcessDataC4log.addElement("\t    Product:"
//		+xreacode.xProd1.Name
		+strpad(xreacode.xProd1.Name,11)
		+" : Level1(MeV)="+prod1lev1
		+"\n\t\tQ(MeV)="
//		+qval
		+strpad(""+qval,12)
		+"  Level1-Q="+(prod1lev1-qval)
		+"\n\t\tE0_threshold for Level1 (MeV)="+e0min4prod1lev1
//		+"\n\t\tE0_threshold    ->    E1(MeV)="+emin4prod1lev1
		+"\n\t\t         E1(E0_threshold),MeV="+emin4prod1lev1
);
		    E1thrLevel1=new Float(emin4prod1lev1);
		}
if (extDebug)
		System.out.println(""
		+"\t Prod1=["+xreacode.xProd1.Name+"]"
		+" Level1(MeV)="+prod1lev1
		+"\n Q(MeV)="+qval
		+" L1-Q="+(prod1lev1-qval)
		+" (ma+mA)/mA="+(ma+mA)/mA
		+" Emin4Lev1(MeV)="+emin4prod1lev1
		+"\n E0(MeV):"+(float)(c4obj.enMin/1e6)+" ... "+(float)(c4obj.enMax/1e6)
		+"\n E1(MeV):"+(float)((c4obj.enMin/1e6*xreacode.E0a1+xreacode.E0a2)/xreacode.E0a3)
		+" ... "
		+(float)((c4obj.enMax/1e6*xreacode.E0a1+xreacode.E0a2)/xreacode.E0a3)
//		+"\n E_inv(eV):"+c4obj_inv.enMin+" ... "+c4obj_inv.enMax
		);
	    }

	    x4lev=x4level.readLevelsFile(xreacode_inv.xProd1.zz,xreacode_inv.xProd1.aa);
	    if (x4lev!=null)
	    if (x4lev.levels.size()>1)
	    {
		x4lev1=(x4level)x4lev.levels.elementAt(1);
if (extDebug)
		System.out.println(""
		+" Elem=["+x4lev.Elem+"]"
		+" Z="+x4lev.ZZ
		+" A="+x4lev.AA
		+" iLevel="+1
		+" E-Level(keV)="+x4lev1.elvr
		+"");
		float qval=xreacode_inv.QValue1.floatValue();//MeV
		float prod1lev1=(float)(x4lev1.elvr/1e3);//MeV
if (extDebug)
System.out.println(""
+"\n\t Targ1:"+xreacode_inv.xreac1.SF1+"\t    mA:MeV="+(float)xreacode_inv.xTarg1.MASS_mev
+"\n\t Proj1:"+xreacode_inv.xreac1.SF2+"\t    ma:MeV="+(float)xreacode_inv.xProj1.MASS_mev
+"\n\t Ejec1:"+xreacode_inv.xreac1.SF3+"\t    mb:MeV="+(float)xreacode_inv.xEjec1.MASS_mev
+"\n\t Prod1:"+xreacode_inv.xreac1.SF4+"\t    mB:MeV="+(float)xreacode_inv.xProd1.MASS_mev
+"\n\t\t\t ma+mA:MeV="+(float)(xreacode_inv.xProj1.MASS_mev+xreacode_inv.xTarg1.MASS_mev)
+"\n\t\t\t mb+mB:MeV="+(float)(xreacode_inv.xEjec1.MASS_mev+xreacode_inv.xProd1.MASS_mev)
);
		float ma=(float)xreacode_inv.xProj1.MASS_mev;
		float mA=(float)xreacode_inv.xTarg1.MASS_mev;
		float emin4prod1lev1=(prod1lev1-qval)*(ma+mA)/mA;//MeV
		if (emin4prod1lev1>0) {
vProcessDataC4log.addElement("\t    Product:"
//		+xreacode_inv.xProd1.Name
		+strpad(xreacode_inv.xProd1.Name,11)
		+" : Level1(MeV)="+prod1lev1
		+"\n\t\tQ(MeV)="
//		+qval
		+strpad(""+qval,12)
		+"  Level1-Q="+(prod1lev1-qval)
		+"\n\t\tE1_threshold for Level1 (MeV)="+emin4prod1lev1
);
		    if (E1thrLevel1!=null)
		    if (emin4prod1lev1<E1thrLevel1.floatValue())
		    E1thrLevel1=new Float(emin4prod1lev1);
		}

if (extDebug)
		System.out.println(""
		+"\t Prod1=["+xreacode_inv.xProd1.Name+"]"
		+" Level1(MeV)="+prod1lev1
		+"\n Q(MeV)="+qval
		+" L1-Q="+(prod1lev1-qval)
		+" (ma+mA)/mA="+(ma+mA)/mA
		+" Emin4Lev1(MeV)="+emin4prod1lev1
		+"\n E0(MeV):"+(float)(c4obj.enMin/1e6)+" ... "+(float)(c4obj.enMax/1e6)
		+"\n E1(MeV):"+(float)((c4obj.enMin/1e6*xreacode.E0a1+xreacode.E0a2)/xreacode.E0a3)
		+" ... "
		+(float)((c4obj.enMax/1e6*xreacode.E0a1+xreacode.E0a2)/xreacode.E0a3)
//		+"\n E_inv(eV):"+c4obj_inv.enMin+" ... "+c4obj_inv.enMax
		);
	    }

//E1=(E0*E0a1 + E0a2)/E0a3

	    if (E1thrLevel1!=null)
	    vProcessDataC4log.addElement("\t    Reaction inversion is correct up to E1="
//		+E1thrLevel1.floatValue()
		+real2str((float)(E1thrLevel1.floatValue()))
		+"(MeV)");


	    if (o5lstR!=null)
	    o5lstR.println(xreacode.subent.Subent+xreacode.cpointer+" "+xreacode.inverseReactionCode
//		+"-inv"
		+"^"
		);
	}
	else {
	    if (o5lstR!=null)
	    o5lstR.println(xreacode.subent.Subent+xreacode.cpointer+" "+xreacode.reacode);
	}

/*
//2018-04-12: Invert DA: Move up
	if (c2lab!=null) {
	    if (flags_CM[2]) {
		s1="";
		if (log_cm2lab_ang0!=null) {
		    s1=" "+log_cm2lab_ang0;
		    if (!log_cm2lab_ang0.equals(log_cm2lab_ang1)) {
			s1+=" "+log_cm2lab_ang1;
		    }
		}
		vProcessDataC4log.addElement("\tCONVERTED ANGLE C.M. TO LAB"+s1);
	    }
	    if (flags_CM[1]) {
		s1="";
		if (log_cm2lab_dat0!=null) {
		    s1=" "+log_cm2lab_dat0;
		    if (!log_cm2lab_dat0.equals(log_cm2lab_dat1)) {
			s1+=" "+log_cm2lab_dat1;
		    }
		}
		vProcessDataC4log.addElement("\tCONVERTED SIGMA C.M. TO LAB"+s1);
	    }
	}

	if (convertRutherfordRatio2xs)
	if (xreacode.flagRutherfordRatio) {
	    vProcessDataC4log.addElement("\tCONVERTED RUTHERFORD RATIO TO X.S.");
	}
*/
if (false)
System.out.println("\n___9___processDataC4:xreacode.c5data.c4_MT[nRow]="+xreacode.c5data.c4_MT[0]);
	return iRow;
    }

    public double ang2cos(double ang) {
	if (ang==90) return 0;
	return Math.cos(ang*Math.PI/180);
    }
    public double dang2cos(double ang,double dang) {
	double cosa=ang2cos(ang);
	double dcosa=Math.abs(ang2cos(ang-dang)-cosa);
	return dcosa;
    }
    public double cos2ang(double cosa) {
	if (cosa==0) return 90;
	return Math.acos(cosa)/Math.PI*180;
    }
    public double dcos2ang(double cosa,double dcosa) {
	double ang=Math.acos(cosa)/Math.PI*180;
	double cosad=cosa+dcosa;
	if (cosad>1) cosad=1;
	double ang1=Math.acos(cosad)/Math.PI*180;
	double adng=ang - ang1;
	return adng;
    }
    public String getLogStr(String str0) {
	int ind=str0.indexOf(":");
	if (ind>0) return str0.substring(ind);
	return str0;
    }

/*
    public int extProcessDataC4(x4reaction xreacode,Float c4data[][])
    {
	x4outfile o5ext=new x4outfile(x4outName+".c5tmp");
	PrintWriter o5extProc=o5ext.getPrintWriter();
	printDataC4array(o5extProc,xreacode,c4data,false);
	if (o5ext!=null) o5ext.close();
	return 0;
    }
*/

    public int printDataC4array(PrintWriter prnOut,x4reaction xreacode
//	,Float c4data[][]
	,c5arr c5data
	,boolean outLog)
    {
	String str,str1,strFamFlag;
	int iRow,iCol,i,nn,nRow,ii,nn2,nn3,ierr;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	x4reacstr xreacstr;
	boolean c5line_inv=false;

//	if (c4_MT==0) return -1;
//++	if ((c4_MF==0)||(c4_MT==0)) return -2;//run1

	if (xreacode.IndepVarFamilyCode.trim().equals("?")) return -3;

//test	prnOut=new PrintWriter(System.out,true);//test
//	Vector vec=xreacode.vVars;
	Float rr;
	if (c5data==null) return -4;
	if (c5data.c4data==null) return -4;
	DnRow=c5data.c4data.length;
	if (DnRow<=0) return -1;
	if (c5data.c4data[0]==null) return -4;
	DnCol=c5data.c4data[0].length;
	if (DnCol<=0) return -1;
//System.out.println("___1___printDataC4array()");

//System.out.println("\t\t...xreac.BasicUnits="+xreacode.BasicUnits);
//System.out.println("...DnRow="+DnRow+" DnCol="+DnCol);

	int iSorted[]=c4obj.sortC4LineFrom2array(c5data);
	int iis;
//20210930 for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	for (iis=0,nRow=0; iis<DnRow; iis++) {
	    iRow=iSorted[iis];
	    c5line_inv=false;
	    if (onlyCalcInverseReact)
	    {
		c5line_inv=xreacode.c4data_flag_inv[iRow];
	    }

//	    if (onlyCalcInverseReact)
	    if (c5line_inv)
	    {
		ii=c4obj.importC4objFrom2array(c5data,iRow);
		ii=c4obj.remakeC4Line(iRow,nRow,c4obj_inv);
		if (ii<0) continue;
		c4str=c4obj.c4str;
	    }
	    else
	    {
		ii=c4obj.makeC4LineFrom2array(c5data,iRow);
		if (ii<0) continue;
		c4str=c4obj.c4str;
//System.out.println(iRow+"))___printDataC4array::c4_MT:["+c4obj.c4_MT+"]");

if (hRequestedNuclides!=null)
if (!c4obj.nowProd.equals(""))
if (hRequestedNuclides.get(c4obj.nowProd.toLowerCase())==null) continue;
//System.out.println(iRow+"))___printDataC4array::nowProd:["+c4obj.nowProd+"]");
//c4str="___1___X4CORR___"+c4str;
		String str1mon="";

//System.out.println(iRow+"))___printDataC4array::flag_outX4mon:["+flag_outX4mon+"] x4m0:"+x4m0+" x4m1:"+x4m1);

		if (flag_outX4mon)
		c4str+=""+str1mon;

	    }

//System.out.println(" flagRutherfordRatio="+xreacode.flagRutherfordRatio);
//System.out.println("...xreac.BasicUnits="+xreacode.BasicUnits);

//________________OUTPUT C5 LINE here!!!!!!!_____________________
//________________OUTPUT C5 LINE here!!!!!!!_____________________
//________________OUTPUT C5 LINE here!!!!!!!_____________________
//________________OUTPUT C5 LINE here!!!!!!!_____________________
//________________OUTPUT C5 LINE here!!!!!!!_____________________
	    if (prnOut!=null) {
//c4str="___1___X4CORR___"+c4str;
if (outC4only) if (c4str.length()>131) c4str=c4str.substring(0,131);
//		prnOut.println("___outC5Line:"+c4str);
		c4str=delEndSpace(c4str,131);
//		c4str=delEndSpace("123456789.123456789.          ",60);
//		c4str=delEndSpace("123456789.123456789.          ",20);
//		c4str=delEndSpace("123456789.12345               ",20);
//		c4str=delEndSpace(c4str,500);
//		c4str=strpad(c4str,132);
		prnOut.println(c4str);
		nTotalC4Datalines++;
		nTotalC4DatalinesTotal++;
		nPointsSubent++;
	    }
	    nRow++;
	}

//System.out.println("...outLog:"+outLog+" "+xreacode.vCompNotes.size());
	if (outLog) {
	    c4obj.printCompNotes(sysOut);
	    for (ii=0; ii<vProcessDataC4log.size(); ii++) {
		str=(String)vProcessDataC4log.elementAt(ii);
		sysOut_println(str);
	    }
//	    if (false)//todo:2021-09-30
	    for (ii=0; ii<xreacode.vCompNotes.size(); ii++) {
		str=(String)xreacode.vCompNotes.elementAt(ii);
		if (!str.startsWith("....")) continue;
		sysOut_println("        "+str);
	    }
	}
	return nRow;
    }


//  boolean flag_fixed_widthC5=true;
    boolean flag_mon_absdy=true;//2022-01-07 absolute vs. relative
    public String getStrMonFixed(Float y,Float dy)
    {
	String str1="",strout="";
//c4obj.extDebug=true;
//	if (y!=null) str1=c4obj.real2str(y.floatValue());
	if (y!=null) str1=c4obj.real2str(y);
//c4obj.extDebug=false;
	str1=strpad(str1,9);
	strout+=str1;
//	str1=""+strpad(c4obj.strPercent(dy,y),9);
	if (flag_mon_absdy) {
	    if (dy!=null) str1=c4obj.real2str(dy);
	    else str1="";
	    str1=strpad(str1,9);
	}
	else {
	    str1=""+c4obj.strPercent(dy,y,9);
	}
	strout+=str1;
	return strout;
    }
    public String getStrMonFree(Float y,Float dy)
    {
	String str1="";
//	if (x4m0.YY!=null) c4str+="M0="+x4m0.YY;
//	if (x4m0.YY!=null) str1mon+=c4obj.real2str(x4m0.YY.floatValue());
//	if (x4m0.YY!=null) str1mon+=x4m0.YY.floatValue();
//	if (x4m0.YY!=null) str1mon+=c4obj.double2str(x4m0.YY.floatValue());
	if (y!=null) str1+=c4obj.double2str(y.floatValue());
	if (flag_mon_absdy) {
	    if (dy!=null) str1+=","+c4obj.double2str(y.floatValue());
	    else str1=",";
	}
	else
	str1+=","+c4obj.strPercent(dy,y);
	return str1;
    }




    public float myCos(float ang)
    {
	return (float)Math.cos(ang*Math.PI/180);
    }




    public int printDataX4(Vector vec, PrintWriter prnOut,int ltab,int nRow0)
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	Float rr;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}
//	if (prnOut!=null) prnOut.println("...print: DnRow="+DnRow);

	if (prnOut!=null)
	prnOut.println(strpad("#DATA",ltab)+strpad(nRow0+"",lnn)+strpad(DnCol+"",lnn)+lnn);

	for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	    str1="";
	    if (lii>1) str1=padstr((nRow+1)+") ",lii);
	    else       str1=padstr("",lii);

	    empty_Y_value=true;
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		rr=xvar.getValue(iRow);
//		if (rr==null) str="-no-";
		if (rr==null) str="";
		else {
		    str=rr+"";
		    if (xvar.isItYValue()) empty_Y_value=false;
//		    if (prnOut!=null)
		    str=double2str(rr);
		}
		str1+=strpad(str,lnn);
	    }
	    if (!empty_Y_value) {
		if (prnOut!=null) prnOut.println(str1);
		nRow++;
	    }
	}
	if (prnOut!=null)
	prnOut.println(strpad("#ENDDATA",ltab)+strpad(nRow+"",lnn)+DnCol);
	return nRow;
    }


    public int printDataHeader(Vector vec, PrintWriter prnOut, int ltab)
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	Float rr;
//	String strBegin="$";
	String strBegin="!";
	int lHdr=7;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}
//	prnOut.println("...print: DnRow="+DnRow);

	str1=strpad("#HEADER",ltab)+strpad(""+lHdr,lnn)+strpad(DnCol+"",lnn)+lnn;
	if (prnOut!=null) prnOut.println(str1);

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(xvar.Header,lnn);
	}
	if (prnOut!=null) prnOut.println(str1);

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(xvar.Units,lnn);
	}
	if (prnOut!=null) prnOut.println(str1);

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(xvar.what,lnn);
	}
	if (prnOut!=null) prnOut.println(str1);


//	if (false)
	{
	    str1=padstr(strBegin,lii);
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(xvar.strVarOrderFlag,lnn);
	    }
	    if (prnOut!=null) prnOut.println(str1);
	}

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		if (xvar.xdata.flagCommon)
		str1+=strpad("const",lnn);
		else
		str1+=strpad("variable",lnn);
	}
	if (prnOut!=null) prnOut.println(str1);

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(""+x4dict025.getConversionFactor(xvar.Units),lnn);
	}
	if (prnOut!=null) prnOut.println(str1);

	str1=padstr(strBegin,lii);
	for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		str1+=strpad(x4dict025.getBasicUnits(xvar.Units),lnn);
	}
	if (prnOut!=null) prnOut.println(str1);

	return DnRow;
    }

    float  c4eneLastValue=0;

    public void outCorrMatrixDefault2(PrintWriter o5prn,x4reaction xreacode) {
	int lnn=16;
	int ierr;
	ierr=prepareCorrMatrixDefault2(xreacode);
	if (ierr!=0) return;
//	o5prn.println(strpad("#CORRMATR2 ",lnn)+c4obj.c4_SubentP);
//	o5prn.println(strpad("#CORRMATRIX ",lnn));
//	o5prn.println("#/CORRMATRIX");
    }


    public int prepareCorrMatrixDefault2(x4reaction xreacode) {
	int ierr;
	int iScale;
	float eMin,eMax,part;
	int lxmax=50;
//test	int lxmax=100;
	String strAlgorithm="";
	String strAlgorithm2="";


	boolean canMakeCovar=false;

//?	if (xreacode.ReactionType.equals("CS")) canMakeCovar=true;
//?	if (xreacode.ReactionType.equals("Ratio")) canMakeCovar=true;
//	outC4headerLine(o5prn,strpad("#Quantity",lnn)	+xreacode.reacombiQuant236);"Cross section"

	if (xreacode.IndepVarFamilyCode!=null)
	if (xreacode.IndepVarFamilyCode.trim().equals("0 2"))
	canMakeCovar=true;

	boolean quantMakeCovar=false;
	if (xreacode.ReactionType.equals("CS"))    quantMakeCovar=true;
	if (xreacode.ReactionType.equals("Ratio")) quantMakeCovar=true;
	if (!quantMakeCovar) canMakeCovar=false;

//	o5prn.println("...xreacode.IndepVarFamilyCode=["+xreacode.IndepVarFamilyCode.trim()+"] canMakeCovar="+canMakeCovar);
	
	if (!canMakeCovar) return -1;

//	if (!flag_renormX4mon)//2020-11-18
	xreacode.makeC4data2(2);
	ierr=xreacode.prepareCovarEne(xreacode.c4ene);
//21034004.x4 no Ene at all
//System.err.println("___prepareCorrMatrixDefault2:ierr="+ierr+" xreacode.c4ene.length="+xreacode.c4ene.length);
//System.err.println("___prepareCorrMatrixDefault2:ierr="+ierr+" xreacode.c4ene[0]="+xreacode.c4ene[0]);
	if (ierr!=0) return -1;
	c4eneLastValue=xreacode.c4ene[xreacode.c4ene.length-1];

//	if ((xreacode.enMin==null)||(xreacode.enMax==null)) return -1;

	if (extDebug)
	{
		printFloatArray(xreacode.c4dataErrTot ,"c4dataErrTot");
//xreacode.c4dataErrStat=xreacode.c4dataErrSys;
//xreacode.c4dataErrStat[2]=xreacode.c4dataErrSys[2];
		printFloatArray(xreacode.c4dataErrStat,"c4dataErrStat");
		printFloatArray(xreacode.c4dataErrSys ,"c4dataErrSys");
	}
	ierr=xreacode.prepareCovarEne(xreacode.c4ene);

	xreacode.checkC5err();
//	sysOut.println("\n___c5err___checkC5err:"+" ErrTot:"+xreacode.flgErrTot 
//	+" ErrStat:"+xreacode.flgErrStat+" ErrSys:"+xreacode.flgErrSys +" ErrMrc:"+xreacode.flgErrMrc);

if (extDebug)
	printFloatArray4(xreacode.c4dataErrTot,xreacode.c4dataErrStat
		,xreacode.c4dataErrSys,xreacode.c4dataErrMrc,"tot","stat","sys","mrc",true);

	xreacode.interpolateErrArray(xreacode.c4ene,xreacode.c4dataErrStat,true);
	xreacode.interpolateErrArray(xreacode.c4ene,xreacode.c4dataErrSys,true);

if (extDebug)
	printFloatArray4(xreacode.c4dataErrTot,xreacode.c4dataErrStat
		,xreacode.c4dataErrSys,xreacode.c4dataErrMrc,"tot","stat","sys","mrc",true);

	xreacode.setErrstatPortionTot(0.5f);
	xreacode.fillC4err();
	if (isThereNullInFloatArray(xreacode.c4dataErrTot)) return -1;
if (extDebug)
	{
//		printFloatArray(xreacode.c4dataErrTot ,"c4dataErrTot");
//		printFloatArray(xreacode.c4dataErrStat,"c4dataErrStat");
//		printFloatArray(xreacode.c4dataErrSys ,"c4dataErrSys");
//		printFloatArray(xreacode.c4dataErrMrc ,"c4dataErrMrc");
		printFloatArray4(xreacode.c4dataErrTot,xreacode.c4dataErrStat
//		,xreacode.c4dataErrSys,xreacode.c4dataErrMrc,"tot","stat","sys","mrc",false);
		,xreacode.c4dataErrSys,xreacode.c4dataErrMrc,"tot","stat","sys","mrc",true);
	}
	int iSysGt0=cmpFloatArrays(xreacode.c4dataErrTot,xreacode.c4dataErrStat);
	if (iSysGt0<=0) return -1;

	int koeffCompress=1;
	int lx=xreacode.c4ene.length;
	if (lx>lxmax) koeffCompress=(lx+lxmax-1)/lxmax;

//2013-03-22
	if (lx<=1) return -1;
	if (xreacode.c4ene[0]>=xreacode.c4ene[xreacode.c4ene.length-1]) return -1;

//	if (xreacode.DatasetID.trim().equals("23114002"))
//	koeffCompress=2;

	ierr=xreacode.prepareCovarMatrix(koeffCompress);
	if (ierr!=0) return ierr;

	Float[] errArray=null,errSys1=null,errPrt1=null;

	errArray=xreacode.compressCovarErr(koeffCompress,xreacode.c4ene,xreacode.c4dataErrStat,false,true);
	xreacode.addCovarMatrix_Uncorrelated(errArray);

//	errArray=xreacode.compressCovarErr(koeffCompress,xreacode.c4ene,xreacode.c4dataErrSys,false,false);
//	xreacode.addCovarMatrix_Fullycorrelated(errArray);


	if (isMethodTOF(xreacode)) {
	    iScale=1;
	    eMin=1e-5f;
	    eMax=20e6f;
	    part=0.05f;
	}
	else {
	    iScale=0;
//	    eMin=xreacode.enMin.floatValue();
//	    eMax=xreacode.enMin.floatValue();
	    eMin=xreacode.c4ene[0];
	    eMax=xreacode.c4ene[xreacode.c4ene.length-1];
	    part=0.5f;
	}
	if (extDebug) {
	sysOut.println("...isMethodTOF(xreacode):"+isMethodTOF(xreacode)
	+" iScale="+iScale+" eMin="+eMin+" eMax="+eMax+" part="+part);
	}
	strAlgorithm=""
	+strpad("2",12)
	+strpad(""+koeffCompress,12)
	+strpad("100.",12)
	+strpad("50.",12)
	+strpad("50.",12)
	+strpad(""+iScale,12)
	+strpad(""+eMin,12)
	+strpad(""+eMax,12)
	+strpad(""+part,12)
	;
	strAlgorithm2=""
	+strpad("Type",12)
	+strpad("Groupping",12)
	+strpad("Stat.SERC",12)
	+strpad("Sys.LERC",12)
	+strpad("Sys.MERC",12)
	+strpad("Log/Lin",12)
	+strpad("En-Min",12)
	+strpad("En-Max",12)
	+strpad("Length",12)
	;

	if (extDebug)
	{
	    sysOut.println("\n___AAAAAAAAAAAAAAA___xreacode.flgErrMrc="+xreacode.flgErrMrc);
	}

if (!xreacode.flgErrMrc)
{
	try {errSys1=new Float[lx];}
	catch(OutOfMemoryError ee) {return -1;}

	xreacode.array2percent(xreacode.c4dataErrSys,errSys1,50.f);
	errPrt1=errSys1;

//	printFloatArray(errSys1,"errSys1");
	errArray=xreacode.compressCovarErr(koeffCompress,xreacode.c4ene,errSys1,false,false);
	xreacode.addCovarMatrix_Fullycorrelated(errArray);

//	xreacode.addCovarMatrix_Fullycorrelated(errArray);
	xreacode.addCovarMatrix_Partiallycorrelated(errArray,iScale,eMin,eMax,part);

}
else
{
/*	try {errSys1=new Float[lx];}
	catch(OutOfMemoryError ee) {return -1;}
	xreacode.array2percent(xreacode.c4dataErrSys,errSys1,100.f);
	try {errPrt1=new Float[lx];}
	catch(OutOfMemoryError ee) {return -1;}
	xreacode.array2percent(xreacode.c4dataErrMrc,errPrt1,100.f);
*/
	errSys1=xreacode.c4dataErrSys;
	errPrt1=xreacode.c4dataErrMrc;

	strAlgorithm=""
	+strpad("3",12)
	+strpad(""+koeffCompress,12)
	+strpad("100.",12)
	+strpad("100.",12)
	+strpad("100.",12)
	+strpad(""+iScale,12)
	+strpad(""+eMin,12)
	+strpad(""+eMax,12)
	+strpad(""+part,12)
	;
	strAlgorithm2=""
	+strpad("Type",12)
	+strpad("Groupping",12)
	+strpad("Stat.SERC",12)
	+strpad("Sys.LERC",12)
//	+strpad("Part.MERC",12)
	+strpad("Other.MERC",12)
	+strpad("Log/Lin",12)
	+strpad("En-Min",12)
	+strpad("En-Max",12)
	+strpad("Length",12)
	;


//	printFloatArray(errSys1,"errSys1");
	errArray=xreacode.compressCovarErr(koeffCompress,xreacode.c4ene,errSys1,false,false);
	xreacode.addCovarMatrix_Fullycorrelated(errArray);

	errArray=xreacode.compressCovarErr(koeffCompress,xreacode.c4ene,errPrt1,false,false);
//	xreacode.addCovarMatrix_Fullycorrelated(errArray);
	xreacode.addCovarMatrix_Partiallycorrelated(errArray,iScale,eMin,eMax,part);
}

	if (koeffCompress>1)
	xreacode.compressCovarData(koeffCompress);//++fly

	if (extDebug) {
	xreacode.printCovarMatrix(sysOut);
	xreacode.printCorrMatrix2(sysOut);
	}
//	printCorrMatrix2(sysOut,xreacode);
	printCorrMatrix2(o5prn,xreacode,koeffCompress,strAlgorithm,strAlgorithm2);

	return ierr;
    }

    public int cmpFloatArrays(Float arrTot[],Float arrStat[]) {
	int ii;
	if (arrTot==null) return 0;
	if (arrStat==null) return 0;
	for (ii=0; (ii<arrTot.length)&&(ii<arrStat.length); ii++) {
	    if ((arrTot[ii]!=null)&&(arrStat[ii]!=null))
	    if (arrTot[ii]>arrStat[ii]) return 1;//Tot>Stat, i.e. errSys>0
	}
	return 0;
    }
    public void printFloatArray(Float arr[],String title) {
	int ii;
//	System.out.println("...FloatArray:"+title+"="+arr
	sysOut.println("...FloatArray:"+title+"="+arr
	+((arr==null)?"":" L="+arr.length)
	);
	if (arr==null) return;
	sysOut.print("   ");
	for (ii=0; ii<arr.length; ii++)
	sysOut.print(ii+")"+arr[ii]+" ");
	sysOut.println("");
    }
    public void printFloatArray4(Float arr1[],Float arr2[],Float arr3[],Float arr4[]
		,String ttl1,String ttl2,String ttl3,String ttl4, boolean showSum
	) {
	int ii,ll=0,lnn=6;
	float r1,r2,r3,r4,sum234;
//	System.out.println("...FloatArray:"+title+"="+arr
	sysOut.println("\n___4Err___FloatArray4*100:"
	+((arr1==null)?"":" L="+arr1.length)
	);
	if (arr1==null) return;
	ll=arr1.length;
	sysOut.print(padstr("#)",lnn-3));
	sysOut.print(" 1"+strpad(""+ttl1,lnn-1));
	sysOut.print(" 2"+strpad(""+ttl2,lnn-1));
	sysOut.print(" 3"+strpad(""+ttl3,lnn-1));
	if (showSum) sysOut.print(" 4"+strpad(""+ttl4,lnn-1));
	if (showSum) sysOut.print(" "+strpad("sum234",lnn));
	if (!showSum) sysOut.print(" "+strpad("sum23",lnn));
	sysOut.println("");
	for (ii=0; ii<ll; ii++) {
	    sum234=0;r1=0;r2=0;r3=0;r4=0;
	    sysOut.print(padstr(ii+")",lnn-3));
	    if (arr1[ii]==null) sysOut.print(" "+strpad("",lnn));
	    else {r1=arr1[ii];sysOut.print(" "+strpad("".format("%.2f",100*arr1[ii]),lnn));}

	    if (arr2==null) sysOut.print(" "+strpad("",lnn));
	    else if (arr2[ii]==null) sysOut.print(" "+strpad("",lnn));
	    else {r2=arr2[ii];sysOut.print(" "+strpad("".format("%.2f",100*arr2[ii]),lnn));sum234=sqSum(sum234,arr2[ii]);}

	    if (arr3==null) sysOut.print(" "+strpad("",lnn));
	    else if (arr3[ii]==null) sysOut.print(" "+strpad("",lnn));
	    else {r3=arr3[ii];sysOut.print(" "+strpad("".format("%.2f",100*arr3[ii]),lnn));sum234=sqSum(sum234,arr3[ii]);}

if (showSum) {
	    if (arr4==null) sysOut.print(" "+strpad("",lnn));
	    else if (arr4[ii]==null) sysOut.print(" "+strpad("",lnn));
	    else {r4=arr4[ii];sysOut.print(" "+strpad("".format("%.2f",100*arr4[ii]),lnn));sum234=sqSum(sum234,arr4[ii]);}
}

	    sysOut.print(" "+strpad("".format("%.2f",100*sum234),lnn));

	    r1=sum234;
	    if (r1>0) sysOut.print(" 2/1:"+strpad("".format("%4.1f",100*r2*r2/(r1*r1)),5));
	    if (r1>0) sysOut.print(" 3/1:"+strpad("".format("%4.1f",100*r3*r3/(r1*r1)),5));
if (showSum) {
	    if (r1>0) sysOut.print(" 4/1:"+strpad("".format("%4.1f",100*r4*r4/(r1*r1)),5));
	    if (r3>0) sysOut.print(" 3/34:"+strpad("".format("%4.1f",100*r3*r3/(r3*r3+r4*r4)),5));
	    if (r3>0) sysOut.print(" 4/34:"+strpad("".format("%4.1f",100*r4*r4/(r3*r3+r4*r4)),5));
}
	    sysOut.println("");
	}
	sysOut.println("");
    }
    public float sqSum(float num1,float num2) {
	double sum=Math.sqrt(Math.pow(num1,2)+Math.pow(num2,2));
	return (float)sum;
    }
    public boolean isThereNullInFloatArray(Float arr[]) {
	int ii;
	if (arr==null) return true;
	for (ii=0; ii<arr.length; ii++) if (arr[ii]==null) return true;
	return false;
    }

    public boolean isMethodTOF(x4reaction xreacode)
    {
	String str;
	boolean bbb;
	if (xreacode==null) return false;

	if (xreacode.subent!=null)
	if (xreacode.subent.subent1!=null) {
	    bbb=isMethodTOF(xreacode.subent.subent1.xbib);
	    if (bbb) return true;
	}

	if (xreacode.subent!=null) {
	    bbb=isMethodTOF(xreacode.subent.xbib);
	    if (bbb) return true;
	}

	return false;
    }
    public boolean isMethodTOF(x4bib xbib)
    {
	String str;
	int ii;
	x4kw xkw;
	x4code xcode;
	int ll=-1;
	if (xbib==null) return false;
	xkw=xbib.getKeyword("METHOD");
	if (xkw==null) return false;
	for (ii=0; ii<xkw.codes.size(); ii++) {
	    xcode=(x4code)xkw.codes.elementAt(ii);
	    if (xcode.code.indexOf("TOF")>=0) return true;
	}
	return false;
    }

    public void printCorrMatrix2(PrintWriter prnOut,x4reaction xreacode
	,int koeffCompress,String strAlgorithm,String strAlgorithm2)
    {
	String str1,str2;
	float ee0,ee1;
	int ii,jj,lx,ly,nn,iiplus=0,in5=30;
	boolean lineLimited=false;
	if (xreacode.c4ene  ==null) return;
	if (xreacode.c4covar==null) return;
	float[][]zz;
	zz=xreacode.c4covar;
	lx=zz[0].length;
	ly=lx;
	if (koeffCompress>1) iiplus=1;
	else iiplus=0;

/*
	if (c4covar==null) return -2;
	double[][]zz;
	x4mkcov xx=new x4mkcov();
	zz=xx.matrix2trian_gauss(c4covar);
	if (zz==null) return -2;
	lx=zz[0].length;
	ly=lx;
    public boolean isMatrixPositiveDefinite(float[][]zz0) {
*/


/*
//	prnOut.println("######.....isMatrixPositiveDefinite="+xreacode.mxx.isMatrixPositiveDefinite(xreacode.c4covar));
	x4mkcov mxx=new x4mkcov();
	float[][]mycorr=mxx.covar2corr(xreacode.c4covar);
	float[]myvar=mxx.covar2var(xreacode.c4covar);
//	prnOut.println("######.....isMatrixPositiveDefinite(1)="+mxx.isMatrixPositiveDefinite(mycorr,1));
//	prnOut.println("######.....isMatrixPositiveDefinite(2)="+mxx.isMatrixPositiveDefinite(mycorr,2));
//	prnOut.println("######.....isMatrixPositiveDefinite(3)="+mxx.isMatrixPositiveDefinite(mycorr,3));
//	prnOut.println("######.....isMatrixPositiveDefinite(4)="+mxx.isMatrixPositiveDefinite(mycorr,4));
//	prnOut.println("######.....isMatrixPositiveDefinite(5)="+mxx.isMatrixPositiveDefinite(mycorr,5));
	float[][]mycorr1=mxx.matrix2percents(mycorr,2);
	float[]myvar1=mxx.vector2percents(myvar,3);
	mxx.matrix_percent2nodim(mycorr1);
	mxx.vector_percent2nodim(myvar1);
	float[][]mycovar2=mxx.corr_var2covar(mycorr1,myvar1);
	mxx.printVector100(xreacode.c4dataErrTot,false);
	mxx.printVector100(myvar,false);
	mxx.printVector100(myvar1,false);

	mxx.printMatrix100(xreacode.c4covar,false);
	mxx.printMatrix100(mycorr1,false);
	mxx.printMatrix100(mycovar2,false);
	sysOut.println("######.....isMatrixPositiveDefinite(2)="+mxx.isMatrixPositiveDefinite(mycovar2));
*/



//	prnOut.println("#...Result:CorrelationMatrix");
//	prnOut.print("En(MeV): L="+lx);
//	prnOut.println(strpad("#COVARIANCE",16)+lx);
	prnOut.println(strpad("#COVARIANCE",16)+strpad("2",16)+"Generated");
//	prnOut.println(strpad("#COMMENT",16)+"Default2. EXFOR software ver.2012/05/17, by V.Zerkin@iaea.org (IAEA-NDS)");
//	prnOut.println(strpad("#COMMENT",16)+"Default2. EXFOR software ver.2020-05-18, by V.Zerkin@iaea.org (IAEA-NDS)");
	prnOut.println(strpad("#COMMENT",16)+"Default2. EXFOR software ver.2020-09-24, by V.Zerkin@iaea.org (IAEA-NDS)");
	prnOut.println(strpad("#+",16)+"1) If only total uncertainties are given, assume uncertainties: statistical/systematic=50/50.");
	prnOut.println(strpad("#+",16)+"2) Statistical uncertainties are added to covariance matrix as uncorrelated components");
	prnOut.println(strpad("#+",16)+"3) If Other uncertainties are not given (ALGORITHM=2): split total systematic uncertainties: fully/partially correcated=50/50 (LERC/MERC)");
	prnOut.println(strpad("#+",16)+"   else (ALGORITHM=3): assume total systematic uncertainties fully correlated (LERC), Other - partially correcated (MERC)");
	prnOut.println(strpad("#+",16)+"4) LERC-correlated uncertainties are added to covariance matrix as fully correlated");
	prnOut.println(strpad("#+",16)+"5) MERC-correlated uncertainties are added as partially correlated using parameters:");
//	prnOut.println(strpad("#+",16)+"EXFOR software ver.2012/05/16, by V.Zerkin@iaea.org (IAEA-NDS)");
//	prnOut.println(strpad("#+",16)+"See: http://www-nds.iaea.org/nrdc/nrdc_2012/working/wp2012-42rev.pdf");
	prnOut.println(strpad("#ALGORITHM",16)+strAlgorithm);
	prnOut.println(strpad("#+",16)+strAlgorithm2);
	prnOut.println(strpad("#COVARDATA",16)+strpad("1",12)+strpad(""+lx,12)+lx);
	prnOut.print(strpad("#EnMin(eV)",12));
	prnOut.print(strpad("EnMax(eV)",12));
	prnOut.print(strpad("Data("+xreacode.BasicUnits.toLowerCase()+")",12));
	prnOut.print(strpad("Std.dev.(%)",12));
	prnOut.print(strpad("Correlations(%)",12));
	prnOut.println("");
//	if (lineLimited)
//	prnOut.println("#----------><----------><----------><----------><--><--><--><-->...Format:(30I4)...<-->");
//	else
//	prnOut.println("#----------><----------><----------><----------><--><--><--><-->...Length:unlimited...<-->");
	prnOut.println("#----------><----------><----------><----------><---Values: separated by space; line length: unlimited....");
	for (ii=0; ii<lx; ii++) {
//	    if (ii%6==0) prnOut.println("");
//	    prnOut.print(strpad(" "+float2float(xreacode.c4ene[ii]/1e6f),12));
	    ee0=float2float(xreacode.c4ene[ii]);
	    if (ii<lx-1) ee1=float2float(xreacode.c4ene[ii+iiplus]);
	    else	 ee1=float2float(c4eneLastValue);

//	    prnOut.print(strpad(""+ee0,12)+strpad(""+ee1,12));
	    prnOut.print(strpad(real2str(ee0),12)+strpad(real2str(ee1),12));

	    prnOut.print(strpad(real2str(xreacode.c4dataTot[ii]),12));
//	    prnOut.print("["+xreacode.c4dataTot[ii]+"]");

//	    nn=(int)(Math.sqrt(zz[ii][ii])*1000+0.5);
	    nn=(int)(Math.sqrt(zz[ii][ii])*10000+0.5);
//	    prnOut.print(padstr(" "+(nn/10.),11));
//	    prnOut.print(strpad(""+(nn/10.),12));
	    prnOut.print(strpad(""+(nn/100.),12));
//tst	    prnOut.print("["+(float)(Math.sqrt(zz[ii][ii])*100)+"]");
	    for (jj=0; jj<=ii; jj++) {
//		nn=(int)(zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
//		prnOut.print(padstr(" "+nn,3));

//		nn=(int)(zz[jj][ii]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*1000+0.5);
//		str1=""+(nn/10.);
//		if (ii==jj) str1="100.";
//		str1=padstr(" "+str1,4);
		nn=(int)(zz[jj][ii]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
		str1=""+nn;
//		if (ii==jj) str1="100.";
		str1=padstr(" "+str1,3);

//		prnOut.print(padstr(" "+(nn/10.),4));
		if (lineLimited)
		if (jj!=0)
		if ((jj%in5)==0)
		{
		    prnOut.println("");
		    prnOut.print(strpad(" ",12*3));
		}
//		prnOut.print(padstr(" "+str1,4));
		prnOut.print(str1);
	    }
	    prnOut.println("");
	}
//	prnOut.println("");
/*	prnOut.println("");
	prnOut.println("Correlation(%): "+lx+"*"+lx);
	for (jj=0; jj<lx; jj++) {
	    for (ii=0; ii<=jj; ii++) {
		nn=(int)(zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
		prnOut.print(padstr(" "+nn,3));
	    }
	    prnOut.println("");
	}
*/
//	prnOut.println("");
	prnOut.println("#/COVARDATA");
	prnOut.println("#/COVARIANCE");
    }










    public static double setPrecision(double value,int places) {
	if (places<=1) return value;
	if (places>=15) return value;
	double ddd=value;
	String str0,fmt;
	fmt="%."+(places-1)+"e";
	str0="".format(fmt,value);
//System.out.println("ddd="+ddd+" str0="+str0);
	try {
	    ddd=Double.parseDouble(str0);
	}
	catch(Exception e) {
	    //System.out.println("{"+str+"}"+e);
	}
//	System.out.println("value="+value+" str0="+str0+" ddd="+ddd);
	return ddd;
    }




    public static String real2str(Float rr) {
	if (rr==null) return "";
	return real2str(rr.floatValue());
    }

    public static String real2str(float rr) {
	String str,str1,str2;
	int ind,ll,ll1;
	rr=float2float(rr);
	str=float2str0(rr);
	ll=str.length();
//if (true) return str;
//sysOut_println("---0-"+ll+" "+str);
	if (ll>9) ll=20;
	str1=double2str(rr);
	ll1=str1.length();
//sysOut_println("---1."+ll1+" "+str1);
	if (ll1<ll) {str=str1;ll=ll1;}
	if (ll>9) ll=20;
	str1=float2str(rr);
	ll1=str1.length();
//sysOut_println("---2."+ll1+" "+str1);
	if (ll1<ll) {str=str1;ll=ll1;}
//	str="["+str+"]";
	return str;
    }

    public static String float2str0(float rr) {
	String str="";
	str=""+rr;
//	str=""+((double)rr);
//	str=""+(1.001f*1e6f);
//	str="".format("%g",rr);
//	str="".format("%f",rr);
	if (str.endsWith(".0")) str=myStrReplace(str,".0",".");
	return str;
    }

    public static String double2str(float rr) {
	String str="";
	int ind;
//	str=""+rr;
//	if (str.endsWith(".0")) str=myStrReplace(str,".0",".");
//	if (str.endsWith("e0")) str=myStrReplace(str,"e0","");
//	str="".format("%g",rr);
	str=String.format("%g",rr);
	str=myStrReplace(str,"00000e","e");
	str=myStrReplace(str,"0000e","e");
	str=myStrReplace(str,"000e","e");
	str=myStrReplace(str,"00e","e");
	str=myStrReplace(str,"0e","e");
	if (str.indexOf(".")>=0)
	if (str.indexOf("e")<0) {
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	}
	ind=str.indexOf(".");
	if (ind<0)
	if (str.indexOf("e")<0) str=str+".";
	return str;
    }


    public static String float2str(float rr) {
	String str="";
	int ind;
//	str=""+rr;
//	if (str.endsWith(".0")) str=myStrReplace(str,".0",".");
//	if (str.endsWith("e0")) str=myStrReplace(str,"e0","");
//+	if (rr==0.f) return "";
	str="".format("%9.4e",rr);
//	str=myStrReplace(str,"e","");
//	if (true) return str;
//	if (str.endsWith("e+00")) str=str.substring(0,str.length()-4);
//	str=myStrReplace(str,"e+00","");
	str=myStrReplace(str,"e+0","+");
	str=myStrReplace(str,"e+","+");
//	str=myStrReplace(str,"e-00","-");
	str=myStrReplace(str,"e-0","-");
	str=myStrReplace(str,"e-","-");
	if (false)
	if (str.indexOf(".")>=0)
	if (str.indexOf("e")<0) {
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	if (str.endsWith("0")) str=str.substring(0,str.length()-1);
	}
	ind=str.indexOf(".");
	if (ind<0)
	if (str.indexOf("e")<0) str=str+".";
	return str;
    }










    public static double float2double(float rr) {
	String str;
	double ddd=0;
	str=String.format("%e",rr);
	try  {ddd=Double.parseDouble(str);}
	catch(Exception e) {}
	return ddd;
    }
    public static float float2float(float rr) {
	String str;
	double ddd=0;
	str=String.format("%e",rr);
	try  {ddd=Double.parseDouble(str);}
	catch(Exception e) {}
	return (float)ddd;
    }




    Hashtable hRequestedNuclides=null;
    int nRequestedNuclides=0;
    public void addRequestedNuclide(String nucl)
    {
	String str=nucl.toLowerCase();
	if (hRequestedNuclides==null) hRequestedNuclides=new Hashtable();
	if (str.endsWith("-g")) str=myStrReplace(str,"-g","g"); else
	if (str.endsWith("-m")) str=myStrReplace(str,"-m","m"); else
	if (str.endsWith("-m1")) str=myStrReplace(str,"-m1","m"); else
	if (str.endsWith("-m2")) str=myStrReplace(str,"-m2","n"); else
	if (str.endsWith("-m3")) str=myStrReplace(str,"-m3","o"); else
	if (str.endsWith("-m4")) str=myStrReplace(str,"-m4","p");
	hRequestedNuclides.put(str,"");
	nRequestedNuclides++;
	//System.out.println("___addRequestedNuclide:[" +nucl+"]"+str);
    }
    public void readListOfProducts(Vector vec) {
	String str;
	int i=0;
	for (i=0; i<vec.size(); i++) {
	    str=(String)vec.elementAt(i);
	    addRequestedNuclide(str);
	}
    }
    public void readListOfProducts(String fileName) {
	String str;
	BufferedReader in=null;
	int i=0;
	try {
	    in=new BufferedReader(new FileReader(fileName));
	    for (;;) {
		str=in.readLine();
		if (str==null) break;
		str=str.trim();
		if (str.startsWith("#")) continue;
		if (str.startsWith("!")) continue;
		addRequestedNuclide(str);
		i++;
	    }
	}
	catch(IOException e) {
		//System.out.println("---ERROR-reading file:[" +filename+"]\n"+e);
		//System.exit(-1);
	}
	if (in!=null)
	try {in.close();} catch(IOException e) {}
    }








    public String getMyDateTime()
    {
	java.util.Date now=new java.util.Date();
//	SimpleDateFormat formatter=new SimpleDateFormat ("yyyy/MM/dd:HH:mm:ss");
	SimpleDateFormat formatter=new SimpleDateFormat ("yyyy-MM-dd,HH:mm:ss");
	String str=formatter.format(now);
	return str;
    }
    public String getMyDateTime2()
    {
	java.util.Date now=new java.util.Date();
	SimpleDateFormat formatter=new SimpleDateFormat ("yyyyMMdd    HHmmss");
	String str=formatter.format(now);
	return str;
    }
    public String Date2myTime(java.util.Date now)
    {
	SimpleDateFormat formatter=new SimpleDateFormat ("HHmmss");
	String str=formatter.format(now);
	return str;
    }
    public String Date2myDate(java.util.Date now)
    {
//	SimpleDateFormat formatter=new SimpleDateFormat ("yyyy/MM/dd,HH:mm:ss");
	SimpleDateFormat formatter=new SimpleDateFormat ("yyyyMMdd");
	String str=formatter.format(now);
	return str;
    }





    public String pause(String str) {
	System.out.print("\nPause "+str+" ...");
	System.out.flush();
	DataInputStream kbd = new DataInputStream(System.in);
	String temp=null;
	try { temp=kbd.readLine();}
	catch(Exception e) {}
	return(temp);
    }


    public long getFileLength(String name)
    {
	long lf;
//name=myStrReplace(name,"\\","/");
	File f=new File(name);
	if (f.exists()) {
	    lf=f.length();
	    return(lf);
	}
	else return(-1);
    }

    public void deleteFile(String fileName)
    {
	File f=new File(fileName);
	if (f.exists()) {
	    boolean del=f.delete();
//	    sysOut.println("    ...Delete: "+fileName+" OK="+del);
	}
    }



    public static String myStrReplaceCRLF(String str,String str1)
    {
	str=myStrReplace(str,"\n\r",str1);
	str=myStrReplace(str,"\r\n",str1);
	str=myStrReplace(str,"\n",str1);
	str=myStrReplace(str,"\r",str1);
	return(str);
    }

    public static String strpad(String str, int lpad) {
	String strOut;
	strOut=strpad(str,lpad,false);
	return(strOut);
    }

    public static String strpad(String str, int lpad, char space) {
	String strOut;
	int ii,lstr;
	lstr = str.length();
	if (lstr==lpad) return(str);
	if (lstr>lpad) {
//	    if (cut)
//	    strOut=str.substring(0,lpad); //cut
//	    else
	    strOut=str;
	    return(strOut);
	}
	strOut=str;
	lstr=lpad-lstr;
//	for (ii=0; ii<lstr; ii++) strOut +=" ";
	for (ii=0; ii<lstr; ii++) strOut +=space;
	return(strOut);
    }
    public static String strpad(String str, int lpad, boolean cut) {
	String strOut;
	int ii,lstr;
	lstr = str.length();
	if (lstr==lpad) return(str);
	if (lstr>lpad) {
	    if (cut)
	    strOut=str.substring(0,lpad); //cut
	    else
	    strOut=str;
	    return(strOut);
	}
	strOut=str;
	lstr=lpad-lstr;
	for (ii=0; ii<lstr; ii++) strOut +=" ";
	return(strOut);
    }
    
    public static String padstr(String str, int lpad) {
	String strOut;
	int ii,lstr;
	lstr = str.length();
	if (lstr==lpad) return(str);
	if (lstr>lpad) {
//	    strOut=str.substring(0,lpad); //cut
	    strOut=str;
	    return(strOut);
	}
	strOut="";
	lstr=lpad-lstr;
	for (ii=0; ii<lstr; ii++) strOut +=" ";
	strOut+=str;
	return(strOut);
    }

    public static String padstr(String str, int lpad, char space) {
	String strOut;
	int ii,lstr;
	lstr = str.length();
	if (lstr==lpad) return(str);
	if (lstr>lpad) {
//	    strOut=str.substring(0,lpad); //cut
	    strOut=str;
	    return(strOut);
	}
	strOut="";
	lstr=lpad-lstr;
//	for (ii=0; ii<lstr; ii++) strOut +=" ";
	for (ii=0; ii<lstr; ii++) strOut +=space;
	strOut+=str;
	return(strOut);
    }

/**
 * This method replaces substring in original string
 * @param  str  original string
 * @param  str0 pattern to be raplaced
 * @param  str1 replaced with 
 * @return resulting string 
 */
    public static String myStrReplace(String str, String str0, String str1)
    {
	String strOut;
	int ii,ls,ls0,ls1,i;
	ls  =  str.length();
	ls0 = str0.length();
	ls1 = str1.length();
	strOut="";
	for (i=0; i<ls;) {
	    ii=str.indexOf(str0,i);
	    if (ii>=0) {
		if (ii>i) strOut=strOut+str.substring(i,ii);
		strOut=strOut+str1;
		i=ii+ls0;
	    }
	    else break;
	}
	if (i<ls) strOut=strOut+str.substring(i,ls);
	return(strOut);
    }

    public String delEndSpace(String str) {
	int ii,lstr;
	lstr = str.length();
	for (ii=lstr-1; ii>=0; ii--)
	if (str.substring(ii,ii+1).equals(" ")!=true) return(str.substring(0,ii+1));
	return("");
    }
    public String delEndSpace(String str,int ll) {
	int ii,lstr;
	lstr=str.length();
	for (ii=lstr-1; ii>=ll; ii--)
	if (str.substring(ii,ii+1).equals(" ")!=true) return(str.substring(0,ii+1));
	if (ll==lstr) return str;
	if (ll>lstr) {str=strpad(str,ll);return str;}
	return str.substring(0,ll);
    }

    public static String getComputerName()
    {
	String host;
	host=System.getenv("COMPUTERNAME");
	if (host!=null) return host;
	host=System.getenv("HOSTNAME");
	if (host!=null) return host;
	try {
	    host=InetAddress.getLocalHost().getHostName();
//tst2020   host=InetAddress.getLocalHost().getCanonicalHostName();
	    return host;
	} catch (Exception e) {
	    System.out.println("Exception caught ="+e.getMessage());
	    return null;
	}
    }

/*	InetAddress ip = InetAddress.getLocalHost();
	System.out.println("Name + IP: " + ip.toString());
	System.out.println("Name:" + ip.getHostName());
	System.out.println("IP address: " + ip.getHostAddress());
	System.out.println("Full name: " + ip.getCanonicalHostName());
Name + IP: nb513677/192.168.139.22
Name:nb513677
IP address: 192.168.139.22
Full name: nb513677.iaea.org

*/

}
