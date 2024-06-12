package zvv.x4;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Class to encapsulate EXFOR Reaction-code and operations
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-08-09
 * @since           2012-03-20
 *
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
 *
 */

public class x4reaction
//extends x4reacstr //??
{

    static PrintWriter sysOut=new PrintWriter(System.out,true);
//  static boolean extDebug=true;
    static boolean extDebug=false;

    public static void main(String args[]) {
        int i, ii, nStr, iStr, acc;
        String  str, str1, str2, str3;
	x4reaction xreac;
	Vector vec1;

	sysOut=new PrintWriter(System.out,true);

        sysOut.println(" x4reaction");
        sysOut.println(" V.Zerkin, IAEA-NDS, Vienna, 2007-2023\n");
//	sysOut.println("args.length: " + args.length+"\n");

//	x4dict.setDirOfArchivedDicts("C:/x4load5/x4dict/");
	x4dict.setDirOfArchivedDicts("x4dict/");
//	x4dict.setDirOfArchivedDicts("dicts-2022-06-29/dicts-2022-06-29/");

	if (args.length>=1) {
	    str=args[0];
	    xreac=new x4reaction(' ',str);
	    xreac.println();
sysOut.println("__Reaction:"+str+" Qvalue1="+xreac.QValue1+"(Mev)");
sysOut.println(" IndepVarFamilyCode:["+xreac.IndepVarFamilyCode+"]"+" nExpectedArgs:"+xreac.nExpectedArgs);
	    System.out.println("___Reacode:["+xreac.originalReacode+"]"
		+" inverseReactionPossible="+xreac.inverseReactionPossible
		);
            System.exit(0);
        }

str="(4-BE-9(92-U-238,X)78-PT-192-L/T,,SIG/RAT)=(92-U-238(4-BE-9,X)78-PT-192-L/T,,SIG/RAT)";
str="(2-HE-4(11-NA-23,N)13-AL-26,,SIG)=(11-NA-23(A,N)13-AL-26,,SIG)";
str="(6-C-12(14-SI-28,EL)6-C-12,,DA,,RTH)=(14-SI-28(6-C-12,EL)14-SI-28,,DA,,RTH)";//2022-01-19
	xreac=new x4reaction(' ',str);
	xreac.println();
if (true) System.exit(0);

	str="(3-LI-7(N,X)1-H-3,,SIG)/(13-AL-27(N,A)11-NA-24,,SIG)";
	str="(6-C-12(12-MG-36,NON),,SIG)/(12-MG-36(6-C-12,NON),,SIG)";
	str="(3-LI-7(N,X)1-H-3,,SIG)/(13-AL-27(P,A)11-NA-24,,SIG)";
	str="(3-LI-7(N,X)1-H-3,,SIG)/(13-AL-27(N,A)11-NA-24,,SIG)";
	str="3-LI-6(HE3,P)4-BE-8,PAR,DA";
str="(4-BE-9(92-U-238,X)78-PT-192-L/T,,SIG/RAT)=(92-U-238(4-BE-9,X)78-PT-192-L/T,,SIG/RAT)";
	xreac=new x4reaction(' ',str);
	xreac.println();
	str1=xreac.getTargets();sysOut.println("___Targets:"+str1);
	str1=xreac.getProjs();	sysOut.println("___Projs:"+str1);
	str1=xreac.getReacs();	sysOut.println("___Reacs:"+str1);
	str1=xreac.getQuants();	sysOut.println("___Quants:"+str1);

	str="13-AL-27(N,A)11-NA-24,,SIG";
	xreac=new x4reaction(' ',str);
	xreac.println();
sysOut.println("__Reaction:"+str+" Qvalue1="+xreac.QValue1+"(Mev)");

	str="13-AL-27(N,2N)13-AL-26,,SIG";
	xreac=new x4reaction(' ',str);
	xreac.println();
sysOut.println("__Reaction:"+str+" Qvalue1="+xreac.QValue1+"(Mev)");

	str="13-AL-27(N,G)13-AL-28,,SIG";
	xreac=new x4reaction(' ',str);
	xreac.println();
sysOut.println("__Reaction:"+str+" Qvalue1="+xreac.QValue1+"(Mev)");

if (true) System.exit(0);


	Float[] ene={new Float(3.5),new Float(4.5),new Float(5.5),new Float(6.5),new Float(7.5),new Float(8.5),new Float(9.5),new Float(11.),new Float(13.),new Float(15.),new Float(17.),new Float(19.),new Float(21.),new Float(23.),new Float(25.),new Float(27.),new Float(29.),new Float(32.5),new Float(37.5),new Float(42.5),new Float(47.5),new Float(52.5),new Float(57.5),new Float(62.5),new Float(67.5),new Float(72.5),new Float(77.5),new Float(82.5),new Float(87.5),new Float(92.5),new Float(97.5),new Float(105.),new Float(115.),new Float(125.),new Float(135.),new Float(145.),new Float(155.),new Float(165.),new Float(175.),new Float(185.),new Float(195.),new Float(205.),new Float(215.),new Float(225.),new Float(235.),new Float(245.),new Float(255.),new Float(265.),new Float(275.),new Float(285.),new Float(295.),new Float(310.),new Float(330.),new Float(350.),new Float(370.),new Float(390.),new Float(410.)};
	Float[] tot={new Float(13.32),new Float(11.40),new Float(10.02),new Float(8.50),new Float(7.38),new Float(6.36),new Float(5.80),new Float(5.55),new Float(5.25),new Float(5.15),new Float(4.95),new Float(4.77),new Float(4.76),new Float(4.68),new Float(4.74),new Float(4.68),new Float(4.68),new Float(4.61),new Float(4.95),new Float(4.52),new Float(4.90),new Float(4.62),new Float(4.80),new Float(4.88),new Float(4.62),new Float(4.97),new Float(4.85),new Float(4.44),new Float(4.48),new Float(4.51),new Float(4.69),new Float(4.42),new Float(4.67),new Float(4.53),new Float(4.62),new Float(4.48),new Float(4.67),new Float(4.78),new Float(4.63),new Float(4.67),new Float(4.74),new Float(4.85),new Float(4.93),new Float(4.69),new Float(4.67),new Float(4.64),new Float(4.59),new Float(4.46),new Float(4.57),new Float(4.69),new Float(4.48),new Float(4.93),new Float(4.57),new Float(4.74),new Float(4.84),new Float(4.84),new Float(4.86)};
	Float[] sta={null,null,new Float(4.30),null,null,null,null,new Float(1.90),null,null,null,null,null,null,null,null,null,new Float(1.20),null,null,null,null,null,null,null,null,null,null,null,null,null,new Float(1.00),null,null,null,null,null,null,null,null,null,new Float(1.90),null,null,null,null,null,null,null,null,null,new Float(1.70),null,null,null,null,new Float(1.10)};
	Float[] sys={null,null,new Float(8.10),null,null,null,null,new Float(3.35),null,null,null,null,null,null,null,null,null,new Float(2.06),null,null,null,null,null,null,null,null,null,null,null,null,null,new Float(4.42),null,null,null,null,null,null,null,null,null,new Float(1.68),null,null,null,null,null,null,null,null,null,new Float(2.06),null,null,null,null,new Float(2.06)};

	x4reaction x4r=new x4reaction(' ',"");
	sta[56]=null;
	x4r.interpolateErrArray(ene,sta,true);
	x4r.interpolateErrArray(ene,sys,true);
	sysOut.println("## \tene \ttotal \tstat \tsys");
	for (ii=0; ii<sta.length; ii++) {
	    sysOut.println(""+ii
		+"\t"+ene[ii]
		+"\t"+"".format("%-6.3f",tot[ii])
		+"\t"+"".format("%-6.3f",sta[ii])
		+"\t"+"".format("%-6.3f",sys[ii])
		);
	}

if (true) System.exit(0);


//	testTypes();
//	System.exit(0);

/*
	xreac=new x4reaction(' ',"9-F-19(N,2N)9-F-18,,SIG");
//	xreac.println();
	xreac=new x4reaction(' ',"9-F-19(N,TOT)");
	xreac=new x4reaction(' ',"(3-LI-6(D,P)3-LI-7,PAR,DA)//(3-LI-6(D,P)3-LI-7,PAR,DA)");
	xreac=new x4reaction(' ',"((12-MG-25(N,N+P)11-NA-24,,SIG)+(12-MG-25(N,D)11-NA-24,,SIG))/(13-AL-27(N,A)11-NA-24,,SIG)");
	xreac=new x4reaction(' ',"(((---11-NA-24,,SIG)+(12-MG-25(N,D)11-NA-24,,SIG))/(13-AL-27(N,A)11-NA-24,,SIG)");
	xreac=new x4reaction(' ',"(((---11-NA-24,,SIG)+(12-MG-25(N,D)11-NA-24,,SIG))/(13-AL-27(N,A)11-NA-24,,SIG))");
	xreac=new x4reaction(' ',"(23-V-51(P,X)19-K-44,(CUM),SIG)/(23-V-51(P,X)19-K-42,IND,SIG)");
	xreac=new x4reaction(' ',"95-AM-241(N,X)ELEM/MASS,(CUM),PY,,SPA");
	xreac=new x4reaction(' ',"(21-SC-45(P,X)18-AR-38,CUM,SIG,,,EXP)/(21-SC-45(P,X)18-AR-36,(CUM),SIG,,,EXP)");
//	xreac=new x4reaction(' ',"");
	extDebug=true;
	xreac=new x4reaction(' ',"((12-MG-26(N,N+P)11-NA-25,,SIG)+(12-MG-26(N,D)11-NA-25,,SIG))=(12-MG-26(N,X)11-NA-25,,SIG)");
	xreac=new x4reaction(' ',"(19-K-CMP(N,TOT),,SIG)=(17-CL-CMP(N,TOT),,SIG)");
	xreac=new x4reaction(' ',"92-U-235(N,F)MASS,CHN,FY,,MXW");
*/
//	xreac=new x4reaction(' ',"(21-SC-45(P,X)18-AR-38,CUM,SIG,,,EXP)/(21-SC-45(P,X)18-AR-36,(CUM),SIG,,,EXP)");
//	xreac=new x4reaction(' ',"(92-U-235(N,F),,SIG,,RES)/(92-U-235(N,TOT),,WID)");
//	xreac=new x4reaction(' ',"(92-U-235(N,F),,SIG,,RES)*((92-U-235(N,F),,WID)/(92-U-235(N,TOT),,WID))");
//	xreac=new x4reaction(' ',"(92-U-235(N,F),,SIG,,RES)*(92-U-235(N,TOT),,WID)");

	xreac=new x4reaction(' ',"2-HE-3(D,P)2-HE-4,,DA");
	System.out.println("___Reacode:["+xreac.originalReacode+"]"
	+" inverseReactionPossible="+xreac.inverseReactionPossible
	);
    }

    public void sysOut_println(String str)
    {
	if (sysOut!=null) sysOut.println(str);
    }
    public void sysOut_print(String str)
    {
	if (sysOut!=null) sysOut.print(str);
    }

    public static void setPrintWriter(PrintWriter out)
    {
	sysOut=out;
    }


    String   DatasetID="";

    boolean flagMFMT_gt_0=false;//check MF>0 (MT can be==0) MT0:true
//  boolean flagMFMT_gt_0=true; //check MF>0 and MT>0       MT0:false
    boolean replaceQvalue2Level=true;//2021-09-16
    boolean flagMT51to5ilvl=false;
    boolean flagSort=false;

    String   reacode="";
    String   reacode2equ="";//2021-10-12
    String   originalReacode="";
    String   d4reactioncode="";
    String   reacombi="";
    String   reacombi0="";
    String   reacombiU="";
    String   C4ReactionCode="";
    String   reacombiQuant236="";
    Vector   reacstrings=new Vector();
    Vector   vReacs=new Vector();
    Vector   vUnits=new Vector();
    Vector   vReacTypes=new Vector();
    Vector   vchrIndepVarFamilyCode=new Vector();
    int      ireac=0;
    char     cpointer=' ';
    x4code   xcode=new x4code();
    Vector   vVars=new Vector();
    Vector   vAllVars=new Vector();//including unknown
    Vector   vMonVars=new Vector();//excluding DATA, including MONIT
//  Vector   fVars=new Vector();
    x4cvar   compVars[]=null;

    x4cvar   xcvarEnergy=null;	//2011/02/11
				//introduced for calculation of q:1/FM
				//momentum transfer
    boolean  koeffEnCM2LabKnown=false;//2015-01-19
    double   koeffEnCM2Lab=1;   //2015-01-19: introduced for optional recalculation
    x4cvar   xcvarAngle=null;	//2011/02/11

    boolean convert_Einc2Lab=true;//2022-01-13

    boolean  flagDataErr_equ_ErrSys=false;//2020-05-06

    //Hashtable hIgnoredVars=new Hashtable();

    String   IndepVarFamilyCode="";
    String   absentVarFamilyCode="";
    char     chrIndepVarFamilyCode[]=new char[]{'?'};
    char     chrIndepVarFamilyCodePresent[]=new char[]{'?'};
    int      numIndepVarFamilyCode[]=new int[]{-1};
    x4subent subent=null;
    boolean  ok=false;
    boolean  flagKnown4me=false;
    String   ReactionType="";
    String   xSF9="";
    int      nExpectedArgs=0;
    int      nCols=0;
    String   strIndepDataRanges="";

    boolean  simpleReac=false;
    boolean  simpleRatio=false;
    boolean  simpleRatio1=false;
    boolean  complexRatio1=false;
    boolean  simpleRatio3div=false;
    boolean  flagNoDim=false;
    boolean  simpleRatio1mult=false;
    boolean  simpleRatio2=false;
    boolean  simpleRatio2Ratio=false;
    x4reacstr xreac1=null;
    x4reacstr xreac2=null;
    Vector   vLinks=new Vector();//2009: links, e.g. other xreacodes
    boolean  resonansEnergyGinenInData=false;

    x4isot xTarg1=new x4isot("");
    x4isot xProj1=new x4isot("");
    x4isot xEjec1=new x4isot("");
    x4isot xProd1=new x4isot("");
    Double QValue1=null;
    Double QValue1capture=null;
    boolean inverseReactionPossible=false;
    String inverseReactionCode="";
    String inverseReactionCodeShort="";
    String strEa="",strSi="";
    double inv_mult_kk1=1;
    boolean inv_mult=false;
    float inv_mult_k2=1;


    x4isot xTarg2=new x4isot("");
    x4isot xProj2=new x4isot("");
    x4isot xEjec2=new x4isot("");
    x4isot xProd2=new x4isot("");
    String c4_i78="";//2019-10-30

    boolean tempGiven=false;
    boolean thicknessGiven=false;

//    double  Energy=0,dEnergy=0,Data=0,dData=0,ThetaLab=0;
//    boolean flagDATA_CM=false;
//    boolean flagANG_CM=false;
    boolean flagRutherfordRatio=false;


    String UnitFamilyCode="?";
    String BasicUnits="?";
    String BasicUnitsFinal="?";
    Double beta_SFC=null;//for S-Factor calc.

    Hashtable convertUnits=new Hashtable();//2021-12-14

    Float enMin=null;
    Float enMax=null;
    String strEnMin="";
    String strEnMax="";

//    x4monfile x4m0=null;
//    x4monfile x4m1=null;
    boolean applyCorrection=false;
    Float Fc_min=null;
    Float Fc_max=null;
    Float Fc_sum=new Float(0);
    Float Fc_ave=new Float(0);

//  boolean addErrFromErrAn2Common=false;//2023-03-09:C1091005.x4
    boolean addErrFromErrAn2Common=true;//2023-03-09:C1091005.x4

    static Hashtable mapUnits2div=new Hashtable();
    static Hashtable mapUnits2mul=new Hashtable();
    static Hashtable mapUnits3div=new Hashtable();
    static Hashtable mapUnits4=new Hashtable();
    static
    {
	mapUnits4=new Hashtable();
	set_mapUnits2div(mapUnits2div);
	set_mapUnits3div(mapUnits3div);
	set_mapUnits2mul(mapUnits2mul);
	set_mapUnits4(mapUnits4);
    }
    static void set_mapUnits2div(Hashtable hh)
    {
	hh.put("[B/SR]/[B]"			,"1/SR");
	hh.put("[ARB-UNITS]//[PART/FIS]"	,"NO-DIM");
	hh.put("[B]/[PART/FIS]" 		,"NO-DIM");
	hh.put("[B]//[B*EV]" 			,"1/EV");
	hh.put("[ARB-UNITS]//[GAM/PART]" 	,"NO-DIM");
	hh.put("[GAM/PART]/[NO-DIM]"	 	,"NO-DIM");//SUBENT:12502007,REACTION:((94-PU-239(N,ABS),,ETA)/(94-PU-239(N,ABS),,ALF))
	hh.put("[PRD/IN/MEV]//[GAM/PART]"	,"1/EV");  //SUBENT:C1783002,REACTION:((27-CO-59(P,G)28-NI-60,,MLT/DE,,MSC)//(27-CO-59(P,G)28-NI-60,PAR,MLT))
	hh.put("[EV-SQ]/[EV]"			,"EV");    //SUBENT:12512002
	hh.put("[PC/FIS/MEV]/[PART/FIS]"	,"1/EV");  //SUBENT:22299007
	hh.put("[PC/FIS/MEV]//[PART/FIS]"	,"1/EV");  //SUBENT:41109010
	hh.put("[B]/[B/SR]"			,"NO-DIM");//SUBENT:F0543002
	hh.put("[MBQ/MUA]//[GAM/PART]"		,"NO-DIM");//SUBENT:O0515007
    }
    static void set_mapUnits3div(Hashtable hh)
    {
	hh.put("([EV]/[EV])/[EV]"		,"1/EV");
    }
    static void set_mapUnits2mul(Hashtable hh)
    {
	hh.put("[EV]*[EV]"			,"EV-SQ");
	hh.put("[ARB-UNITS]*[B]"		,"[ARB-UNITS]");
    }
    static void set_mapUnits4(Hashtable hh)
    {
	hh.put("([GAM/PART]*[B])/([GAM/PART]*[B])"			,"NO-DIM");
	hh.put("([PART/FIS]/[PART/FIS])/([PART/FIS]/[PART/FIS])"	,"NO-DIM");
    }

    x4reaction(x4code xcode)
    {
	this(xcode.cpointer,xcode.code,null);
	this.xcode=xcode;
	if (xcode==null) this.xcode=new x4code();
    }

    x4reaction(x4code xcode,x4subent subent)
    {
	this(xcode.cpointer,xcode.code,subent);
	this.xcode=xcode;
	if (xcode==null) this.xcode=new x4code();
    }


    x4reaction(char cpointer, String reacode00)
    {
	x4treatReacode(cpointer,reacode00,false);
    }
    x4reaction(char cpointer, String reacode00,x4subent subent)
    {
	x4treatReacode(cpointer,reacode00,false,subent);
    }
    x4reaction(char cpointer, String reacode00,boolean replaceEnresByEn)
    {
	x4treatReacode(cpointer,reacode00,replaceEnresByEn);
    }

    void setFlagMT0(boolean flagMFMT_gt_0)
    {
	this.flagMFMT_gt_0=flagMFMT_gt_0;
    }

    void setFlagQvalue2Level(boolean replaceQvalue2Level)
    {
	this.replaceQvalue2Level=replaceQvalue2Level;
    }

    void x4treatReacode(char cpointer, String reacode00,boolean replaceEnresByEn)
    {
	x4treatReacode(cpointer,reacode00,replaceEnresByEn,null);
    }
    void x4treatReacode(char cpointer, String reacode00,boolean replaceEnresByEn,x4subent subent)
    {
	double qval=0;
	this.subent=subent;
	int ind,ierr;
//servlet_responseOutput=sysOut;//20211007
//System.out.println("____x4treatReacode:replaceEnresByEn="+replaceEnresByEn+" reacode="+reacode);
	reacode=myStrReplace(reacode00," ","");
	originalReacode=reacode;
	ind=reacode.indexOf(")=(");
	if (ind>0) {
	    if ((reacode.startsWith("("))&&(reacode.endsWith(")"))) {
		String reacode1=reacode.substring(1,ind);
		String reacode2=reacode.substring(ind+3,reacode.length()-1);
		if ((!reacode1.startsWith("(")&&(!reacode2.startsWith("(")))) {
//		    if (reacode1.length()<=reacode2.length()) reacode=reacode1;
//		    else reacode=reacode2;
		    x4reaction xr1=new x4reaction(' ',reacode1);
		    x4reaction xr2=new x4reaction(' ',reacode2);
//System.out.println("____x4treatReacode:"+" xr1.xProj1.za="+xr1.xProj1.za+" reacode1="+reacode1);
//System.out.println("____x4treatReacode:"+" xr2.xProj1.za="+xr2.xProj1.za+" reacode2="+reacode2);
//		    if (xr1.xProj1.za<=xr2.xProj1.za) reacode=reacode1;
//		    else reacode=reacode2;
		    reacode=reacode1;
		    reacode2equ=reacode2;

		    x4c4obj c4obj=new x4c4obj();
		    int nRow=c4obj.prepareC4Info(subent,xr1,false);
//System.out.println("____x4treatReacode:"+" xr1.xProj1.za="+xr1.xProj1.za+" nRow:"+nRow+" MF:"+c4obj.c4_MF+" MT:"+c4obj.c4_MT+" reacode="+reacode1);
		    if (c4obj.c4_MT<=0) {
			nRow=c4obj.prepareC4Info(subent,xr2,false);
//System.out.println("____x4treatReacode:"+" xr2.xProj1.za="+xr2.xProj1.za+" nRow:"+nRow+" MF:"+c4obj.c4_MF+" MT:"+c4obj.c4_MT+" reacode="+reacode2);
			if (c4obj.c4_MT>0) reacode=reacode2;
		    }


		}
		else
		reacode=reacode1;
	    }
	}
	this.cpointer=cpointer;
	String str;
	str=reaccode2strx(reacode,replaceEnresByEn);
//	sysOut.println("\n>>>str:\n"+reacode+"\n"+str);
	if (vReacs.size()>=1) {
	    xreac1=(x4reacstr)vReacs.elementAt(0);
	    xreac1.subent=subent;
	    xTarg1=new x4isot(xreac1.SF1);	//xiso.println();
	    xProj1=new x4isot(xreac1.SF2);	//xiso.println();
	    if (xreac1.SF3.equals("EL")) xEjec1=new x4isot(xreac1.SF2);
	    else
	    if (xreac1.SF3.equals("INL")) xEjec1=new x4isot(xreac1.SF2);
	    else
	    xEjec1=new x4isot(xreac1.SF3);	//xiso.println();
	    xProd1=new x4isot(xreac1.SF4);	//xiso.println();
	    if (!xreac1.SF9.equals("EXP")) xSF9=xreac1.SF9;


	    if (vReacs.size()>=2) {
		xreac2=(x4reacstr)vReacs.elementAt(1);
		xreac2.subent=subent;
		xTarg2=new x4isot(xreac2.SF1);	//xiso.println();
		xProj2=new x4isot(xreac2.SF2);	//xiso.println();
		if (xreac2.SF3.equals("EL")) xEjec2=new x4isot(xreac2.SF2);
		else
		if (xreac2.SF3.equals("INL")) xEjec2=new x4isot(xreac2.SF2);
		else
		xEjec2=new x4isot(xreac2.SF3);	//xiso.println();
		xProd2=new x4isot(xreac2.SF4);	//xiso.println();
	    }


//System.out.println("....11111...."+"\n"+xreac1.SF1+"\t xTarg1.MASS_mev="+xTarg1.MASS_mev+"\n"+xreac1.SF2+"\t xProj1.MASS_mev="+xProj1.MASS_mev+"\n"+xreac1.SF3+"\t xEjec1.MASS_mev="+xEjec1.MASS_mev+"\n"+xreac1.SF4+"\t xProd1.MASS_mev="+xProd1.MASS_mev);
//System.out.println("....11111...."+"\n"+xreac1.SF1+" xTarg1.Spin="+xTarg1.Spin+"\n"+xreac1.SF2+" xProj1.Spin="+xProj1.Spin+"\n"+xreac1.SF3+" xEjec1.Spin="+xEjec1.Spin+"\n"+xreac1.SF4+" xProd1.Spin="+xProd1.Spin);
	    if (xTarg1.MASS_mev>0)
	    if (xProj1.MASS_mev>0)
	    if (xEjec1.MASS_mev>0)
	    if (xProd1.MASS_mev>0)
	    QValue1=new Double(xTarg1.MASS_mev+xProj1.MASS_mev-xEjec1.MASS_mev-xProd1.MASS_mev);
	    //if (QValue1!=null) System.out.println("....QValue1="+QValue1);

	    if (xTarg1.MASS_mev>0)
	    if (xProj1.MASS_mev>0)
	    if (xProd1.MASS_mev>0) {
		int zz2=xTarg1.zz+xProj1.zz;
		int aa2=xTarg1.aa+xProj1.aa;
		x4isot xEjec2=new x4isot("G");
		Element ele2=Element.getEle(zz2);
		if (ele2!=null) {
		    x4isot xProd2=new x4isot(zz2+"-"+ele2.sym.toUpperCase()+"-"+aa2);
		    QValue1capture=new Double(xTarg1.MASS_mev+xProj1.MASS_mev-xEjec2.MASS_mev-xProd2.MASS_mev);
//		    System.out.println("....QValue1capture="+QValue1capture+" "+xProd2.originalStr);
		}
	    }

//6-C-13(A,N)8-O-16,,SIG
	    if (QValue1!=null)
	    if (xTarg1.Spin!=null)
	    if (xProj1.Spin!=null)
	    if (xEjec1.Spin!=null)
	    if (xProd1.Spin!=null)
	    if (xTarg1.meta.equals(""))
	    if (xProd1.meta.equals(""))
	    if (vReacs.size()==1)
//	    if (xreac1.SF5.equals(""))
//	    if (xreac1.SF6.equals("SIG"))
//	    if ((xreac1.SF5.equals(""))&&(xreac1.SF6.equals("SIG")))
//	    if (((xreac1.SF5.equals(""))&&(xreac1.SF6.equals("SIG")))
//	    || ((xreac1.SF5.equals("PAR"))&&(xreac1.SF6.equals("SIG"))))
//2018	    if ((xreac1.SF6.equals("SIG"))
	    if (
		((xreac1.SF6.equals("SIG"))
		||(xreac1.SF6.equals("DA"))
		)
	    && ((xreac1.SF5.equals(""))
		||(xreac1.SF5.equals("PAR"))
		))
	    if (xreac1.SF7.equals(""))
	    if (xreac1.SF8.equals(""))
//2015	    if ((xreac1.SF9.equals(""))||(xreac1.SF9.equals("EXP")))
	    if (!xreac1.SF1.equals(xreac1.SF4))
	    {
		inverseReactionPossible=true;
		inverseReactionCode=xreac1.SF4+"("+xreac1.SF3+","+xreac1.SF2+")"
		+xreac1.SF1+","
//		+xreac1.SF5
		+","+xreac1.SF6;
		inverseReactionCodeShort=xProd1.shortName
		+"("+xEjec1.shortName+","+xProj1.shortName+")"
		+xTarg1.shortName
//		+","+xreac1.SF5
//		+","+xreac1.SF6.toLowerCase();
		+",Sig";
		ierr=getParamsCalcInverseReact();
		if ((xTarg1.za+xTarg1.meta).equals(xProj1.za+xProj1.meta)) {
		    //direct:AA->BC, e.g. 1-H-2(D,N)2-HE-3,,SIG
		    inv_mult=true;
		    inv_mult_k2=0.5f;
		}
		if ((xEjec1.za+xEjec1.meta).equals(xProd1.za+xProd1.meta)) {
		    //direct:BC->AA, e.g. 2-HE-3(N,D)1-H-2,,SIG
		    inv_mult=true;
		    inv_mult_k2=2;
		}
/*
//2020-03-23
System.out.println("....11111....inverseReactionPossible="+inverseReactionPossible
+" inverseReactionCode="+inverseReactionCode
+"\n SF1=["+xreac1.SF1+"]"+xTarg1.za+"]"+xTarg1.meta+"]"
+"\n SF2=["+xreac1.SF2+"]"+xProj1.za+"]"+xProj1.meta+"]"
+"\n SF3=["+xreac1.SF3+"]"+xEjec1.za+"]"+xEjec1.meta+"]"
+"\n SF4=["+xreac1.SF4+"]"+xProd1.za+"]"+xProd1.meta+"]"
+"\n inv_mult=["+inv_mult+"]"+" inv_mult_k2="+inv_mult_k2
);
*/
	    }
//System.out.println("....11111....inverseReactionPossible="+inverseReactionPossible+" inverseReactionCode="+inverseReactionCode);

/*
System.out.println("....11111....inverseReactionPossible="+inverseReactionPossible
+" inverseReactionCode="+inverseReactionCode
+"\n QValue1=["+QValue1+"]"
+"\n xTarg1.Spin=["+xTarg1.Spin+"]"
+"\n xProj1.Spin=["+xProj1.Spin+"]"
+"\n xEjec1.Spin=["+xEjec1.Spin+"]"
+"\n xProd1.Spin=["+xProd1.Spin+"]"
+"\n xTarg1.meta=["+xTarg1.meta+"]"
+"\n xProd1.meta=["+xProd1.meta+"]"
+"\n vReacs.size()=["+vReacs.size()+"]"
+"\n SF1=["+xreac1.SF1+"]za:"+xTarg1.za+"]meta:"+xTarg1.meta+"]"
+"\n SF2=["+xreac1.SF2+"]za:"+xProj1.za+"]meta:"+xProj1.meta+"]"
+"\n SF3=["+xreac1.SF3+"]za:"+xEjec1.za+"]meta:"+xEjec1.meta+"]"
+"\n SF4=["+xreac1.SF4+"]za:"+xProd1.za+"]meta:"+xProd1.meta+"]"
+"\n inv_mult=["+inv_mult+"]"+" inv_mult_k2="+inv_mult_k2
);
*/

	    if (xTarg1.MASS_mev>0)
	    if (xProj1.MASS_mev>0)
	    {
		koeffEnCM2Lab=1+xProj1.MASS_mev/xTarg1.MASS_mev;
		koeffEnCM2LabKnown=true;
	    }

//	    if (xreac1.SF8.equals("RTH")) flagRutherfordRatio=true;
	    if (xreac1.SF8.indexOf("RTH")>=0) flagRutherfordRatio=true;
	    if (xreac1.SF8.indexOf("LEG")>=0) flagRutherfordRatio=false;//2020-09-29:D0717005.x4
	    if (xreac1.SF8.indexOf("REL")>=0) flagRutherfordRatio=false;
/*
SELECT distinct SF8 FROM REACSTR where SF8 like '%RTH%'
'RTH'
'RTH/REL'
'RTH/AV'
'REL/RTH'
'LEG/RTH'
*/

	    resonansEnergyGinenInData=xreac1.resonansEnergyGinenInData;
//System.out.println("____x4treatReacode:replaceEnresByEn="+replaceEnresByEn+" resonansEnergyGinenInData="+resonansEnergyGinenInData);
//System.out.println("____setIndepVarFamilyCode:replaceEnresByEn="+replaceEnresByEn+" SF58="+SF58+" resonansEnergyGinenInData="+resonansEnergyGinenInData);

//System.out.println("____x4treatReacode::"+" SF58:["+xreac1.SF8+"]"+" xProj1.MASS_mev:"+xProj1.MASS_mev+" xTarg1.MASS_mev="+xTarg1.MASS_mev);
	    //2023-02-13:O2150002:SF8=SFC::39-Y-89(P,G)40-ZR-90,,SIG,,SFC
//	    if (xreac1.SF8.equals("SFC"))
	    if (xreac1.SF8.indexOf("SFC")>=0)
	    if (xTarg1.MASS_mev>0)
	    if (xProj1.MASS_mev>0)
//20230222  if (xProd1.MASS_mev>0) //commented for:C2117003:6-C-12(6-C-12,FUS),,SIG,,SFC
	    {
		int Z1=xProj1.zz;
		int Z2=xTarg1.zz;
		double m1=xProj1.MASS_mev;
		double m2=xTarg1.MASS_mev;
		double m=m1*m2/(m1+m2);
		m=m/931.494;
		double beta=0.98948*Z1*Z2*Math.sqrt(m);
		beta_SFC=new Double(beta);
//		System.out.println("..SF8=SFC:beta="+beta);
	    }

	}

	treatIndepVarFamilyCode();

//System.out.println(".............1111111111........reacombi0="+reacombi0+" "+reacode);
//System.out.println(".............1111111111........reacombiU="+reacombiU+" "+reacode);
//System.out.println(".............1111111111........reacombi ="+reacombi +" "+reacode);
	classifyReaction();
	if (extDebug)
	sysOut.println("\n>>>str:\n"+reacode+"\n"+str+"\n"+reacombi+"\n"+d4reactioncode
	+"\nreacombi0="+reacombi0+"\nsimpleReac="+simpleReac
//	+"\nsimpleRatio1="+simpleRatio1
	+"\nsimpleRatio1     ="+simpleRatio1
	+"\ncomplexRatio1    ="+complexRatio1
	+"\nsimpleRatio1mult ="+simpleRatio1mult
	+"\nsimpleRatio2     ="+simpleRatio2
	+"\nsimpleRatio2Ratio="+simpleRatio2Ratio
	+"\nBasicUnits="+BasicUnits
	+"\nvUnits.size()="+vUnits.size()
	+"\nok="+ok
	+"\nflagKnown4me="+flagKnown4me
	);
    }
/*
//not needed: replaced by adding xvar2
    public void x4treatSF4asElemMass()
    {
	if (xreac1==null) return;
	if (xreac1.SF4==null) return;
	if (xreac1.SF4.equals("")) return;
	System.out.println("___A___absentVars:["+absentVarFamilyCode+"]"+" SF4=["+xreac1.SF4+"]");
//	absentVarFamilyCode=myStrReplace(absentVarFamilyCode,"7","");
    }
*/
    public String reaccode2strx(String str00,boolean replaceEnresByEn)
    {
	x4reacstr xreacstr;
	String str0,str1,str2,str3;
	int i,ii,iii,ll,ifound,i00next,i1,i2,i3,i4;
	boolean found,ok1;
	ireac=0;
	str0=str00;
//	ll=str0.length();
//	char str[]=new char[ll];
//	str0.getChars(0,ll,str,0);
	str0=treatStr0Reactions(str00);
	ll=str0.length();

	if (str00.indexOf("{")>=0) return "";
	if (str00.indexOf("}")>=0) return "";
	if (str00.indexOf("[")>=0) return "";
	if (str00.indexOf("]")>=0) return "";

	char [] str=str0.toCharArray();
	str1="";
	str2="";
	d4reactioncode=str2;
	reacombi="";
	reacombi0="";
	reacstrings=new Vector();
	vReacs=new Vector();
	vUnits=new Vector();
	vReacTypes=new Vector();

	if (extDebug) {
	    sysOut.println(str0);
	    for (ii=0; ii<ll; ii++) sysOut.print(""+(ii%10)); sysOut.println("");
	}

	i00next=0;
	ok=true;
	for (i00next=0; i00next<ll; ) {
	    i1=-1; i2=-1; i3=-1; i4=-1;
	    str3="";
	    for (ii=i00next; ii<ll; ii++) {
		if (Character.isLetterOrDigit(str[ii])) {
		    i1=ii;
		    break;
		}
	    }
	    if (i1<0) break;
	    for (ii++; ii<ll; ii++) {
//		if (str[ii]=='(') {
		if (isBracket1(str[ii])) {
		    i2=ii;
		    break;
		}
	    }
	    if (i1<0) break;
	    for (ii++; ii<ll; ii++) {
//		if (str[ii]==')') {
		if (isBracket1(str[ii])) {
		    i3=ii;
		    break;
		}
	    }
	    if (i3<0) break;
	    for (ii++; ii<ll; ii++) {
		if (isBracket0(str[ii])) {
		    i4=ii-1;
		    break;
		}
	    }
	    if (i4<0) i4=ll-1;
//	    sysOut.println("   ...i1="+i1+" i2="+i2+" i3="+i3+" i4="+i4);
	    for (ii=i1; ii<=i4; ii++) str3=str3+str[ii];
	    for (ii=i00next; ii<i1; ii++) str2=str2+str[ii];
	    str2=str2+"R"+ireac+"#";
//	    sysOut.println("R"+ireac+"#: ["+str3+"]");
//	    sysOut.println("str2=["+str2+"]");
	    str3=myStrReplace(str3,"[","(");
	    str3=myStrReplace(str3,"]",")");
	    reacstrings.addElement(str3);
//2010	    xreacstr=new x4reacstr(str3);
	    xreacstr=new x4reacstr(str3,replaceEnresByEn);
	    vReacs.addElement(xreacstr);
	    //??super.setReacstr(str3);//??
	    if (xreacstr.ok==false) ok=false;

	    i00next=i4+1;
	    ireac++;
	}
	for (ii=i00next; ii<ll; ii++) str2=str2+str[ii];
//	sysOut.println("str2=["+str2+"]");
	d4reactioncode=str2;

	reacombi=d4reactioncode;
	reacombi0=d4reactioncode;
	reacombiU=d4reactioncode;
	if (vReacs.size()==1) {reacombi0="("+reacombi0+")";reacombiU="("+reacombiU+")";}
	for (ii=0; ii<vReacs.size(); ii++) {
//	    reacombi=myStrReplace(reacombi,"(R"+ii+"#)",""+(char)('A'+ii));
	    reacombi=myStrReplace(reacombi,"R"+ii+"#",""+(char)('A'+ii));
	    reacombi=myStrReplace(reacombi,"("+(char)('A'+ii)+")",""+(char)('A'+ii));
//System.out.println(".............0000000000........reacombi0="+reacombi0);
	    reacombi0=myStrReplace(reacombi0,"(R"+ii+"#)","a");
//System.out.println(".............0000000000........reacombi0="+reacombi0);
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    reacombiU=myStrReplace(reacombiU,"(R"+ii+"#)","["+xreacstr.BasicUnits+"]");
	}
	for (ii=0; ii<vReacs.size(); ii++) {
	    reacombi0=myStrReplace(reacombi0,"a+a","a");
	    reacombi0=myStrReplace(reacombi0,"a-a","a");
	    reacombi0=myStrReplace(reacombi0,"(a)","a");
	    reacombi0=myStrReplace(reacombi0,"a=a","a");
	}
	for (ii=0; ii<vReacs.size(); ii++) {
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    str1=xreacstr.BasicUnits;
	    for (iii=0,found=false; iii<vUnits.size(); iii++) {
		str2=(String)vUnits.elementAt(iii);
		if (str1.equals(str2)) {found=true; break;}
	    }
	    if (!found) vUnits.addElement(str1);
	}
	for (ii=0; ii<vReacs.size(); ii++) {
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    str1=xreacstr.ReactionType;
	    for (iii=0,found=false; iii<vReacTypes.size(); iii++) {
		str2=(String)vReacTypes.elementAt(iii);
		if (str1.equals(str2)) {found=true; break;}
	    }
	    if (!found) vReacTypes.addElement(str1);
	}

	C4ReactionCode=getC4ReactionCode(d4reactioncode,vReacs);

	str1=new String(str);
	if (extDebug) println();
//	treatStr0Reactions(str0);
	return str1;
    }
    public Double getQvalue()
    {
	if (ireac!=1) return null;
	if (xreac1==null) return null;
	return xreac1.Qvalue;//MeV
    }
    public String treatStr0Reactions(String str0)
    {
	int i,ii,iii,ll,ifound;
	if (str0==null) return str0;
	ll=str0.length();
	char [] str=str0.toCharArray();
	for (;;) {
	    for (i=0, ifound=0; i<ll; i++) if (str[i]==')') {ifound=1;break;}
	    if (ifound==0) break;
	    for (ii=i, ifound=0; ii>=0; ii--) if (str[ii]=='(') {ifound=1;break;}
	    if (ifound==0) break;
	    for (iii=ii, ifound=0; iii<=i; iii++) if (str[iii]=='[') {ifound=1;break;}
	    if (ifound==0) {
		str[ii]='[';
		str[i] =']';
	    }
	    else {
		str[ii]='{';
		str[i] ='}';
	    }
	}
	for (i=0; i<ll; i++) {
	    if (str[i]=='{') str[i]='(';
	    else
	    if (str[i]=='}') str[i]=')';
	}
	String str1=new String(str);
//	sysOut.println("...str1=["+str1+"]");
	return str1;
    }

    public String reacombi2type(String d4reactioncode)
    {
	int ii;
	reacombi=d4reactioncode;
	reacombi0=d4reactioncode;
	for (ii=0; ii<20; ii++) {
	    reacombi=myStrReplace(reacombi,"(R"+ii+"#)",""+(char)('A'+ii-1));
//	    reacombi=myStrReplace(reacombi,"R"+ii+"#",""+(char)('A'+ii-1));
	    reacombi=myStrReplace(reacombi,"("+(char)('A'+ii-1)+")",""+(char)('A'+ii-1));
	    reacombi0=myStrReplace(reacombi0,"(R"+ii+"#)","a");
	}
	for (ii=0; ii<20; ii++) {
	    reacombi0=myStrReplace(reacombi0,"a+a","a");
	    reacombi0=myStrReplace(reacombi0,"a-a","a");
	    reacombi0=myStrReplace(reacombi0,"(a)","a");
	    reacombi0=myStrReplace(reacombi0,"a=a","a");
	}
	return reacombi0;
    }

    public boolean isBracket0(char ch)
    {
	if ((ch=='(')||(ch==')')) return true;
	else return false;
    }
    public boolean isBracket1(char ch)
    {
	if ((ch=='[')||(ch==']')) return true;
	else return false;
    }


    public void setSubent(x4subent subent)
    {
	this.subent=subent;
	this.DatasetID=subent.Subent+cpointer;
    }

    public void classifyReaction() {
	int ii,nn=0;
	String newUnits=null,str;

	simpleReac=false;

if (extDebug)
System.out.println("\n...classifyReaction(): vUnits.size()="+vUnits.size());
	if (vReacs.size()==1) simpleReac=true;
	if ((reacombi0.equals("a"))&&(vReacs.size()>=1)) simpleReac=true;
if (extDebug)
System.out.println("\n___1___classifyReaction(): vUnits.size()="+vUnits.size()+" simpleReac="+simpleReac);

	if (vUnits.size()==1) {
	    if (reacombi0.equals("a/a"))   simpleRatio1=true;
	    if (reacombi0.equals("(a/a)")) simpleRatio1=true;
	}
	if (vUnits.size()==2) {
	    if (reacombi0.equals("a/a"))   complexRatio1=true;
	    if (reacombi0.equals("(a/a)")) complexRatio1=true;
	    if (reacombi0.equals("a//a"))   complexRatio1=true;//2021-04-13
	    if (reacombi0.equals("(a//a)")) complexRatio1=true;//2021-04-13
	}

//2012-10-22 not finished. 
//Question: general operations with rections (not only /) and units are allowed?
/*
	if (false)
//	if ((vUnits.size()==1)||(vUnits.size()==2)) {
	if (vUnits.size()==2) {
	    newUnits=(String)vUnits.elementAt(0);
	    newUnits=newUnits+"*"+(String)vUnits.elementAt(1);
	    vUnits=new Vector();
	    vUnits.addElement(newUnits);
	    if (reacombi0.equals("a*a"))   simpleRatio1mult=true;
	    if (reacombi0.equals("(a*a)")) simpleRatio1mult=true;
	}
*/
	if ((reacombi0.equals("a*a")) || (reacombi0.equals("(a*a)")))
	if (vUnits.size()==2) {//2021-04-11
	    newUnits=(String)vUnits.elementAt(0)+"*"+(String)vUnits.elementAt(1);
	    x4dict025 x4d025=x4dict025.findinx4dict(newUnits);
	    if (x4d025==null) {
		newUnits=(String)vUnits.elementAt(1)+"*"+(String)vUnits.elementAt(0);
		x4d025=x4dict025.findinx4dict(newUnits);
	    }
	    if (x4d025!=null) {
BasicUnits=x4dict025.getBasicUnits(newUnits);
		vUnits=new Vector();
		vUnits.addElement(newUnits);
		if (reacombi0.equals("a*a"))   simpleRatio1mult=true;
		if (reacombi0.equals("(a*a)")) simpleRatio1mult=true;
	    }
	}

//??	if (vReacTypes.size()==1)
//??	if (vchrIndepVarFamilyCode.size()==1)
	if (vUnits.size()==1)
	{
	    if (reacombi0.equals("a//a"))   simpleRatio2=true;
	    if (reacombi0.equals("(a//a)")) simpleRatio2=true;
	}

//	if (false)
	if (vReacTypes.size()==1)
	if (vchrIndepVarFamilyCode.size()==1)
	if (vUnits.size()==1)
	{
	    if (reacombi0.equals("((a/a)/(a/a))")) simpleRatio2Ratio=true;
	    if (reacombi0.equals("(a/a)/(a/a)")) simpleRatio2Ratio=true;
	    if (reacombi0.equals("(a/a)//(a/a)")) simpleRatio2Ratio=true;//2021-04-11
	}

//	if (false)
	if (vReacTypes.size()==1)
	if (vchrIndepVarFamilyCode.size()==2)
	if (vUnits.size()==1)
	{
	    if (reacombi0.equals("(a/a)//(a/a)")) simpleRatio2Ratio=true;//2021-04-11
	}

//13059
//System.out.println("___vReacTypes.size()="+vReacTypes.size()+" vchrIndepVarFamilyCode.size()="+vchrIndepVarFamilyCode.size()
//+" ___vUnits.size()="+vUnits.size()+" reacombi0="+reacombi0
//+"\nreacombiU:"+reacombiU);
	if (false)
	if (vReacTypes.size()==1)
	if (vchrIndepVarFamilyCode.size()==1)
	if (vUnits.size()==1)
	{
if (reacombi0.equals("(a/a)//(a/a)")) simpleRatio2Ratio=true;//2018-01-09
	}

//System.out.println("___vReacTypes.size()="+vReacTypes.size()+" vchrIndepVarFamilyCode.size()="+vchrIndepVarFamilyCode.size()
//+" ___vUnits.size()="+vUnits.size()
//+" ___simpleRatio2Ratio="+simpleRatio2Ratio
//+" reacombi0="+reacombi0);

	if (vReacTypes.size()==1)
	if (vchrIndepVarFamilyCode.size()==1)
	if (vUnits.size()==1)
	{
	    if (reacombi0.equals("((a*a)/a)")) simpleReac=true;
	    if (reacombi0.equals("(a*a)/a"))   simpleReac=true;
	    if (reacombi0.equals("(a*a/a)"))   simpleReac=true;
	    if (reacombi0.equals("a*a/a"))     simpleReac=true;//2010
	}
//System.out.println(".....reacombi0="+reacombi0+" "+reacode);


	str=(String)mapUnits4.get(reacombiU);
//System.out.println(".....mapUnits4:"+str);
	if (str!=null) {
	    simpleRatio2Ratio=true;
	}

	str=(String)mapUnits2div.get(reacombiU);
//System.out.println(".....p:["+cpointer+"] reacombi0:"+reacombi0+" reacombiU="+reacombiU+" mapUnits2:"+str);
	if (str!=null) {
	    complexRatio1=true;
	    newUnits=str;
	}

	if (reacombi0.equals("a*a")) {
	    str=(String)mapUnits2mul.get(reacombiU);
//System.out.println(".....p:["+cpointer+"] reacombi0:"+reacombi0+" reacombiU="+reacombiU+" mapUnits2mul:"+str+" simpleRatio1mult="+simpleRatio1mult);
	    if (str!=null) {
		newUnits=str;
		simpleRatio1mult=true;//knownMult
	    }
	}

	if ((reacombi0.equals("(a/a)/a"))
	  ||(reacombi0.equals("a/a/a"))
	)
	{
	    str=(String)mapUnits3div.get(reacombiU);
//System.out.println(".....p:["+cpointer+"] reacombi0:"+reacombi0+" reacombiU="+reacombiU+" mapUnits:"+str+" simpleRatio1mult="+simpleRatio1mult);
	    if (str!=null) {
		newUnits=str;
		simpleRatio3div=true;
	    }
	}

//T0173003: [NO-DIM]*[NO-DIM]
	if (vUnits.size()==1)
	{
	    str=(String)vUnits.elementAt(0);
	    if (str.equals("NO-DIM")) {
		newUnits=str;
		flagNoDim=true;
	    }
	}

//System.out.println(".....newUnits:["+newUnits+"} "+reacode);
	if (newUnits==null) newUnits="?";

	boolean ok=false;
	if (simpleRatio1||simpleRatio2) simpleRatio=true;
	if (simpleReac||simpleRatio1||simpleRatio2||simpleRatio2Ratio) ok=true;
	if (simpleRatio1mult) ok=true;
//	if (complexRatio1) ok=true;
	if ((complexRatio1)&&(newUnits!=null)) ok=true;
	if (simpleRatio3div) ok=true;
	if (flagNoDim) ok=true;
	if (ok) {
	    xreac1=(x4reacstr)vReacs.elementAt(0);
	    BasicUnits=xreac1.BasicUnits;
	    flagKnown4me=true;
	    ReactionType=xreac1.ReactionType;
//System.out.println(".....xreac1.x4d236="+xreac1.x4d236+" "+reacode);
	    if (xreac1.x4d236!=null)
	    reacombiQuant236=xreac1.x4d236.shortHelp;
//	    reacombiQuant236=xreac1.x4d236.Code;
	}
	if (simpleRatio1||simpleRatio2||simpleRatio2Ratio) {
	    BasicUnits="NO-DIM";
	    ReactionType="Ratio";
	    reacombiQuant236="Ratio of ["+reacombiQuant236+"]";
	    return;
	}
	if (complexRatio1) {
	    BasicUnits=newUnits;
	    ReactionType="Ratio";
	    reacombiQuant236="Ratio of ["+reacombiQuant236+"]";
	    return;
	}
	if (simpleRatio3div) {
	    BasicUnits=newUnits;
	    ReactionType="Ratio";
	    reacombiQuant236="Ratio of ["+reacombiQuant236+"]";
	    return;
	}
	if (simpleRatio1mult) {
	    if (newUnits!=null) {
		BasicUnits=newUnits;
		ReactionType="Mult";
		reacombiQuant236="Mult. of ["+reacombiQuant236+"]";
		return;
	    }
	}

    }
    public void treatIndepVarFamilyCode() {
	x4reacstr xreacstr;
	int i,ii,iii;
	String str1,str2;
	boolean found;
	vchrIndepVarFamilyCode=new Vector();
	chrIndepVarFamilyCode=null;
//sysOut.println("...treatIndepVarFamilyCode():["+reacode+"]");
//System.out.println("...treatIndepVarFamilyCode():["+reacode+"]"+" vAllVars.size()="+vAllVars);
	for (ii=0; ii<vReacs.size(); ii++) {
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    char []chr1=xreacstr.chrIndepVarFamilyCode;
	    str1=new String(chr1);
//sysOut.println("---["+str1+"]"+ii+"["+xreacstr+"]"+xreacstr.reacstr);
	    for (iii=0,found=false; iii<vchrIndepVarFamilyCode.size(); iii++) {
		char []chr2=(char[])vchrIndepVarFamilyCode.elementAt(iii);
		str2=new String(chr2);
//sysOut.println("???["+str2+"]"+iii+xreacstr.reacstr);
		if (str1.equals(str2)) {found=true; break;}
	    }
	    if (!found) vchrIndepVarFamilyCode.addElement(chr1);
	}
	for (ii=0; ii<vchrIndepVarFamilyCode.size(); ii++) {
	    char []chr1=(char[])vchrIndepVarFamilyCode.elementAt(ii);
	    if (ii==0) chrIndepVarFamilyCode=chr1.clone();
	    else
	    for (i=0; (i<chrIndepVarFamilyCode.length)&&(i<chr1.length); i++) {
	        if (chr1[i]==' ') continue;
		chrIndepVarFamilyCode[i]=chr1[i];
	    }
	}
	nExpectedArgs=0;
	if (chrIndepVarFamilyCode!=null) {
	    IndepVarFamilyCode=new String(chrIndepVarFamilyCode);
	    chrIndepVarFamilyCodePresent=chrIndepVarFamilyCode.clone();
//System.out.println("...IndepVarFamilyCode=["+IndepVarFamilyCode+"]");
	    for (i=0; i<chrIndepVarFamilyCode.length; i++) {
		chrIndepVarFamilyCodePresent[i]=' ';
		if (chrIndepVarFamilyCode[i]==' ') continue;
		if (chrIndepVarFamilyCode[i]=='0') continue;
		nExpectedArgs++;
	    }
	    if (nExpectedArgs>=0) {
		numIndepVarFamilyCode=new int[nExpectedArgs];
		for (i=0,ii=0; i<chrIndepVarFamilyCode.length; i++) {
//System.out.println("-----"+i+"/"+ii+"["+chrIndepVarFamilyCode[i]+"]");
			if (chrIndepVarFamilyCode[i]==' ') continue;
			if (chrIndepVarFamilyCode[i]=='0') continue;
			numIndepVarFamilyCode[ii++]=chrIndepVarFamilyCode[i]-'0';
//System.out.println("====="+i+"/"+ii+"["+chrIndepVarFamilyCode[i]+"]"+"num="+numIndepVarFamilyCode[ii-1]+"["+IndepVarFamilyCode+"]");
		}
	    }
	}

	//---test-output
	for (ii=0; ii<vReacs.size(); ii++) {
//if (ii==0) sysOut.println("...test-output vReacs.size()="+vReacs.size());
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    char []chr1=xreacstr.chrIndepVarFamilyCode;
	    str1=new String(chr1);
//sysOut.println("---["+str1+"]"+ii+"["+xreacstr+"]["+str1+"]"+xreacstr.reacstr);
	}


    }

    public void treat2IndepVarFamilyCode() {
	x4reacstr xreacstr;
	int i,ii,iii;
	String str1,str2;
	boolean found;
	nExpectedArgs=0;
	if (chrIndepVarFamilyCode!=null) {
	    IndepVarFamilyCode=new String(chrIndepVarFamilyCode);
	    chrIndepVarFamilyCodePresent=chrIndepVarFamilyCode.clone();
//System.out.println("...IndepVarFamilyCode=["+IndepVarFamilyCode+"]");
	    for (i=0; i<chrIndepVarFamilyCode.length; i++) {
		chrIndepVarFamilyCodePresent[i]=' ';
		if (chrIndepVarFamilyCode[i]==' ') continue;
		if (chrIndepVarFamilyCode[i]=='0') continue;
		nExpectedArgs++;
	    }
	    if (nExpectedArgs>=0) {
		numIndepVarFamilyCode=new int[nExpectedArgs];
		for (i=0,ii=0; i<chrIndepVarFamilyCode.length; i++) {
//System.out.println("-----"+i+"/"+ii+"["+chrIndepVarFamilyCode[i]+"]");
			if (chrIndepVarFamilyCode[i]==' ') continue;
			if (chrIndepVarFamilyCode[i]=='0') continue;
			numIndepVarFamilyCode[ii++]=chrIndepVarFamilyCode[i]-'0';
//System.out.println("====="+i+"/"+ii+"["+chrIndepVarFamilyCode[i]+"]"+"num="+numIndepVarFamilyCode[ii-1]+"["+IndepVarFamilyCode+"]");
		}
	    }
	}

	//---test-output
	for (ii=0; ii<vReacs.size(); ii++) {
//if (ii==0) sysOut.println("...test-output vReacs.size()="+vReacs.size());
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    char []chr1=xreacstr.chrIndepVarFamilyCode;
	    str1=new String(chr1);
//sysOut.println("---["+str1+"]"+ii+"["+xreacstr+"]["+str1+"]"+xreacstr.reacstr);
	}


    }

//  public String getYFuncXStr(int nX)
    public String getYFormulaStr()
    {
	boolean ok=false;
	String strOut="y=";
	if (compVars!=null) {
//System.out.println("\n\n___getYFormulaStr::compVars="+compVars+" nExpectedArgs="+nExpectedArgs+" compVars.length="+compVars.length);
		if (nExpectedArgs==compVars.length-1) ok=true;
		x4cvar xcvar;
		for (int ii=0; ii<compVars.length; ii++) {
			xcvar=compVars[ii];
			if (ii==0) strOut+="";
			else if (ii==1) strOut+="(";
			else if (ii>1) strOut+=",";
			if (!xcvar.strFamFlag.equals("")) strOut+=xcvar.strFamFlag;
//			if (!xcvar.compVarName.equals("")) strOut+=xcvar.compVarName;
			else {strOut+="x"+(ii+1);ok=false;}
		}
		if (compVars.length>1) strOut+=")";
//System.out.println("\n\n___getYFormulaStr::"+strOut);
	}
	if (ok) return strOut; else
	return getYFormulaStr(nExpectedArgs);
    }
    public String getYFormulaStr(int nX)
    {
	int ii;
	String strOut="y=y(";
	for (ii=0; ii<nX; ii++) {
	    if (ii>0) strOut+=",";
	    strOut+="x"+(ii+1);
	}
	strOut+=")";
	return(strOut);
    }

    public x4var makeX4CommonVar(x4subent sub2,char pointer,String Hdr,String Units,String ival,String strComment) {
	x4var xvar=null;
	Vector vLines=new Vector();
	vLines.addElement("COMMON               1          3");
	vLines.addElement(strpad(Hdr,10)+pointer);
	vLines.addElement(strpad(Units,10));
	if (ival.indexOf(".")<0) ival=ival+".";
	vLines.addElement(ival);
	vLines.addElement("ENDCOMMON            3");
	x4common xcom=new x4common(vLines,1);
//xcom.printStrings();
//xcom.printData();
	xcom.subent=sub2;//2020-05-15:X4:13444006
	xcom.strCompNotes=strComment;
	xvar=new x4var(xcom,0,chrIndepVarFamilyCode);
	return xvar;
    }
/*	vLines.addElement("COMMON               2          3");
	vLines.addElement("ANG        DATA-ERR2");
	vLines.addElement("ADEG       PER-CENT");
	vLines.addElement("125.       18.");
	vLines.addElement("ENDCOMMON            3");
*/

    public x4var makeX4DataVar(x4subent sub2,char pointer,String Hdr,String Units,Vector vStr,String strComment) {
	x4var xvar=null;
	Vector vLines=new Vector();
	vLines.addElement("DATA                 1"+padstr(""+vStr.size(),11));
	vLines.addElement(strpad(Hdr,10)+pointer);
	vLines.addElement(strpad(Units,10));
	String ival;
	for (int ii=0; ii<vStr.size(); ii++) {
	    ival=(String)vStr.elementAt(ii);
//	    if (ival.indexOf(".")<0) ival=ival+".";
	    vLines.addElement(ival);
	}
	vLines.addElement("ENDDATA    "+padstr(""+(2+vStr.size()),11));
	x4data xdat=new x4data(vLines,1);
//xdat.printStrings();
//xdat.printData();
	xdat.subent=sub2;
	xdat.strCompNotes=strComment;
	xvar=new x4var(xdat,0,chrIndepVarFamilyCode);
	return xvar;
    }

    public x4var makeXvarMonitErr(x4subent sub2,char pointer,x4var xvar1,x4var xvar2) {
	x4var xvar3=null;
//	if (xvar1.xdata.flagCommon&&xvar2.xdata.flagCommon) {
	if ((xvar1.xdata.DnRow==1)&&(xvar1.xdata.DnRow==1)) {
	    Float mon=xvar1.getValueInBasicUnits(0);
	    Float err=xvar2.getValueInBasicUnits(0);
	    if ((mon!=null)&&(err!=null)) {
		String strMonitErrPercent="".format("%.2f",100*err.floatValue()/mon.floatValue());
if (false)
sysOut.println("___REJECTED: "+" MONIT:"+mon+" MONIT-ERR:"+err+" PER-CENT:"+(err/mon*100)+"~"+strMonitErrPercent+"%");
		xvar3=makeX4CommonVar(sub2,pointer,xvar2.Header,"PER-CENT",strMonitErrPercent,null);
		xvar3.numDataType=-9;
		xvar2.xvarInPercent=xvar3;
	    }
	    return xvar3;
	}
//sysOut.println("___1___REJECTED: "+" SUBENT:"+subent.Subent+" DatasetID:"+DatasetID
//+" MonRows="+xvar1.xdata.DnRow+" MonErrRows="+xvar2.xdata.DnRow);

	if (xvar1.xdata.DnRow==xvar1.xdata.DnRow) {
	    Vector vStr=new Vector();
	    for (int iRow=0; iRow<xvar1.xdata.DnRow; iRow++) {
		Float mon=xvar1.getValueInBasicUnits(iRow);
		Float err=xvar2.getValueInBasicUnits(iRow);
		String strMonitErrPercent="";
		if ((mon!=null)&&(err!=null)) {
		    strMonitErrPercent="".format("%.2f",100*err.floatValue()/mon.floatValue());
		}
		vStr.addElement(strMonitErrPercent);
	    }
	    xvar3=makeX4DataVar(sub2,pointer,xvar2.Header,"PER-CENT",vStr,null);
	    xvar3.numDataType=-9;
	    xvar2.xvarInPercent=xvar3;
	    return xvar3;
	}

sysOut.println("___2___REJECTED: "+" SUBENT:"+subent.Subent+" DatasetID:"+DatasetID
+" MonRows="+xvar1.xdata.DnRow+" MonErrRows="+xvar2.xdata.DnRow);
	return xvar3;
    }
//grep REJECTED EXFOR-EXP-2020-09-25.tto |grep -v 'MonRows=1 MonErrRows=1'>qq1
//java x4toc5 10536067.x4 




    public int prepareC4F(x4subent sub2,char pointer) {
	return prepareC4F(sub2,pointer,null);
    }

    public int prepareC4F(x4subent sub2,char pointer,Vector vVarsEnRes) {
	int ii,nn=0,iii;
	x4subent sub1;
	x4var xvar,xvar1,xvar2,xvar3;
	boolean chkPointer,found;
	x4reacstr xreacstr=null;
	String str1;
	vVars=new Vector();
tempGiven=false;
thicknessGiven=false;
	sub1=sub2.subent1;
	this.subent=sub2;
	if (sub1==null)  return 0;
	if (sub2.xdata==null)    return 0;
	if (sub2.xdata.DnRow<=0) return 0;
	chkPointer=sub2.xbib.chkPointer;

	if (!flagKnown4me) return 0;
	if (chrIndepVarFamilyCode==null) return 0;

	//vv-- we do not recognize family-code --vv
//	if (new String(chrIndepVarFamilyCode).equals("?")) return 0;

//sysOut.println("...111...chrIndepVarFamilyCode='"+new String(chrIndepVarFamilyCode)+"' xreacstr="+xreacstr+" compVars="+compVars);
//System.out.println("__x4reaction.prepareC4F:"+"...111...chrIndepVarFamilyCode='"+new String(chrIndepVarFamilyCode)+"' xreacstr="+xreacstr+" compVars="+compVars);
		//now: works only for limited cases:
		//1: nReacStr==1
		//2: reacombi0=='a' && nReacStr>=1

//System.out.println("_call addx4vars sub1.xcommon vVars:"+vVars.size());
	if (sub1.xcommon!=null)
	nn+=sub1.xcommon.addx4vars(vVars,chkPointer,pointer,chrIndepVarFamilyCode);
		if (extDebug)	sysOut.print("	 c1:"+nn);
//System.out.println("_call addx4vars sub2.xcommon vVars:"+vVars.size());
	if (sub2.xcommon!=null)
	nn+=sub2.xcommon.addx4vars(vVars,chkPointer,pointer,chrIndepVarFamilyCode);
		if (extDebug)	sysOut.print(" c2:"+nn);
//System.out.println("_call addx4vars sub2.xdata vVars:"+vVars.size());
	nn+=sub2.xdata.addx4vars(vVars,chkPointer,pointer,chrIndepVarFamilyCode);
	if (vVarsEnRes!=null) nn+=sub2.xdata.addx4varsEnRes(vVars,vVarsEnRes);
//System.out.println("_end addx4vars vVars:"+vVars.size());

	str1=new String(chrIndepVarFamilyCode);
if (extDebug)
sysOut.println("___0___chrIndepVarFamilyCode=["+str1+"] R1.SF4=["+xreac1.SF4+"]"
+" ZP=["+xProd1.zz+"]"
+" AP=["+xProd1.aa+"]"
+" MP=["+xProd1.cmeta+"]"
);
	if (str1.indexOf("7")>=0)
	if (!xreac1.SF4.equals(""))
	if ((xreac1.SF4.indexOf("ELEM")<0)&&(xreac1.SF4.indexOf("MASS")<0))
	{
if (extDebug)
sysOut.println("___0___chrIndepVarFamilyCode=["+str1+"] R1.SF4=["+xreac1.SF4+"]"
+" ZP=["+xProd1.zz+"]"
+" AP=["+xProd1.aa+"]"
+" MP=["+xProd1.cmeta+"]"
);
	    xvar2=makeX4CommonVar(sub2,pointer,"ELEMENT","NO-DIM",""+xProd1.zz,null);
	    vVars.addElement(xvar2);
	    xvar2=makeX4CommonVar(sub2,pointer,"MASS","NO-DIM",""+xProd1.aa,null);
	    vVars.addElement(xvar2);
	    if (xProd1.cmeta!=' ') {
//		xvar2=makeX4CommonVar(sub2,pointer,"ISOMER","NO-DIM",""+xProd1.cmeta);
		xvar2=makeX4CommonVar(sub2,pointer,"ISOMER","NO-DIM",""+xProd1.imeta,null);
		vVars.addElement(xvar2);
	    }
	}

	nn+=treatErrAnalysCodes(vVars,sub1.xbib,sub2,pointer);
	nn+=treatErrAnalysCodes(vVars,sub2.xbib,sub2,pointer);

//sysOut.println(" d2="+nn+" vVars.size()="+vVars.size());
		if (extDebug)	sysOut.println(" d2:"+nn);
		if (extDebug)	sysOut.print("	");
//if (extDebug)
//sysOut.println("......chrIndepVarFamilyCode='"+new String(chrIndepVarFamilyCode)+"' xreacstr="+xreacstr);


//System.out.println(" d1="+nn+" vVars.size()="+vVars.size());
		vAllVars=x4var.sort(vVars,false);
		vMonVars=x4var.getVecMonit(vAllVars);
		vVars=x4var.sort(vVars,true);

if (chrIndepVarFamilyCode.length>=6)
if (chrIndepVarFamilyCode[6]==' ')
{
//System.out.println("___1___prepareC4F:tempGiven="+tempGiven+" thicknessGiven="+thicknessGiven+" nExpectedArgs="+nExpectedArgs+" vVars.size()="+vVars.size());
		for (ii=0; ii<vAllVars.size(); ii++) {
		    xvar=(x4var)vAllVars.elementAt(ii);
		    if (xvar.Header.startsWith("TEMP")) {
			tempGiven=true;
			vVars.addElement(xvar);
			chrIndepVarFamilyCode[6]='6';
xvar.defineWhatIsThis(chrIndepVarFamilyCode);
//xvar.xdata.strCompNotes="ADDED-----";
str2CompNotes("ADDED TO IND.VARIABLES: "+xvar.Header+"("+xvar.Units+")");
			break;
		    }
		    if (xvar.Header.startsWith("THICKNESS")) {
			thicknessGiven=true;
			vVars.addElement(xvar);
			chrIndepVarFamilyCode[6]='6';
xvar.defineWhatIsThis(chrIndepVarFamilyCode);
			break;
		    }
		}
		treat2IndepVarFamilyCode();
//System.out.println("___1___prepareC4F:tempGiven="+tempGiven+" thicknessGiven="+thicknessGiven+" nExpectedArgs="+nExpectedArgs+" vVars.size()="+vVars.size());
}

//System.out.println("___1___prepareC4F:d2="+nn+" vVars.size()="+vVars.size()+" vAllVars.size()="+vAllVars.size()+" vMonVars.size()="+vMonVars.size());
//sysOut.println(" d2="+nn+" vVars.size()="+vVars.size());

//2019:tst		vVars=vAllVars;

		absentVarFamilyCode="";
		for (ii=0; ii<chrIndepVarFamilyCode.length; ii++) {
//System.out.println(ii+"___1___prepareC4F:chrIndepVarFamilyCode[ii]="+chrIndepVarFamilyCode[ii]);
		    if (chrIndepVarFamilyCode[ii]==' ') continue;
		    str1=""+chrIndepVarFamilyCode[ii];
		    for (iii=0,found=false; iii<vVars.size(); iii++) {
			xvar=(x4var)vVars.elementAt(iii);
			if ((xvar.whatVar.startsWith(str1))&&xvar.flagValue) {
			  chrIndepVarFamilyCodePresent[ii]=chrIndepVarFamilyCode[ii];
			  found=true;
			  break;
			}
		    }
		    if (!found) {
			if (chrIndepVarFamilyCode[ii]!='6')//??--2012-OtherVars
			absentVarFamilyCode+=(""+chrIndepVarFamilyCode[ii]);
		    }
		}

//extDebug=true;
		if (extDebug)
		for (ii=0; ii<vVars.size(); ii++) {
		    xvar=(x4var)vVars.elementAt(ii);
//		    xvar.defineWhatIsThis(chrIndepVarFamilyCode);
//		    if (false)
		    sysOut.println(" "+(ii+1)+")\t"+xvar.Header
//		    System.out.println("__v_"+(ii+1)+") "+xvar.Header
			+"\twhat:"+xvar.what
			+"\tCOMMON:"+xvar.xdata.flagCommon
			+"\twhatVar:"+xvar.whatVar
			+"\torder="+xvar.strVarOrderFlag
			+"\tDataType="+xvar.DataType
			+"\tFamilyCode="+xvar.FamilyCode
			+"\tvarNum="+xvar.Variable_Number
			+"\tBasicUnits="+BasicUnits
			+"\tvar.BasicUnits="+xvar.BasicUnits
			);
//		    sysOut.println(" "+(ii+1)+")\t"+xvar.toString());
		}

		if (extDebug)
		for (ii=0; ii<vVars.size(); ii++) {
		    xvar=(x4var)vVars.elementAt(ii);
		    sysOut.print(" "+(ii+1)+")"+xvar.Header);
		    if (chkPointer) sysOut.print(":"+xvar.xdata.cpointers[xvar.iCol]);
		    sysOut.println(":"+xvar.xdata.cpointers[xvar.iCol]+" chkPointer="+chkPointer);
		}
		if (extDebug)
		sysOut.println("");

//sysOut.println("-----------nExpectedArgs="+nExpectedArgs+"]]]]]]]]]]]"+reacode);sysOut.flush();
		int lastNum=-1111;
		x4cvar xcvar;
		compVars=new x4cvar[nExpectedArgs+1];
		for (ii=0; ii<compVars.length; ii++) {
			xcvar=new x4cvar(ii);
			xcvar.set_x4reaction(this);
			compVars[ii]=xcvar;
		//	compVars[ii].println();
		}
//sysOut.println("[[[[[[[[vVars.size()="+vVars.size()+" "+ok+" "+flagKnown4me);
		for (ii=0; ii<vVars.size(); ii++) {
		    xvar=(x4var)vVars.elementAt(ii);
//System.out.println("...lastNum="+lastNum+"vs."+xvar.Variable_Number);
		    if (xvar.Variable_Number>=77) continue;
		    if (lastNum!=xvar.Variable_Number) {
//sysOut.println("");
			lastNum=xvar.Variable_Number;
			xcvar=new x4cvar(xvar.Variable_Number);
			xcvar.set_x4reaction(this);
//System.out.println("...lastNum="+lastNum+"vs."+xvar.Variable_Number);
			compVars[xvar.Variable_Number]=xcvar;
		    }

		    //2020-10-09:10135002.x4
		    if ((xvar.Header.indexOf("MONIT")>=0)&&(!xvar.Units.equals("PER-CENT"))) {
if (false)
			sysOut.println("___REJECTED: must be PER-CENT:"
			+"varNum="+xvar.Variable_Number
			+" "+(ii+1)+") "+xvar.Header
			+" what:"+xvar.what
			+" whatVar:"+xvar.whatVar
			+" order="+xvar.strVarOrderFlag
			+" DataType="+xvar.DataType
			+" FamilyCode="+xvar.FamilyCode
			+" Units="+xvar.Units
			+"\n___MonError.link2monit:"+xvar.xvarMONIT
			);

			xvar3=null;
			xvar2=xvar;
			xvar1=xvar.xvarMONIT;
			if (xvar1!=null) {
			    xvar3=makeXvarMonitErr(sub2,pointer,xvar1,xvar2);
/*			    if (xvar1.xdata.flagCommon&&xvar2.xdata.flagCommon) {
				Float mon=xvar1.getValueInBasicUnits(0);
				Float err=xvar2.getValueInBasicUnits(0);
				if ((mon!=null)&&(err!=null)) {
				    String strMonitErrPercent="".format("%.2f",100*err.floatValue()/mon.floatValue());
if (false)
sysOut.println("___REJECTED: "+" MONIT:"+mon+" MONIT-ERR:"+err+" PER-CENT:"+(err/mon*100)+"~"+strMonitErrPercent+"%");
				    xvar3=makeX4CommonVar(sub2,pointer,xvar2.Header,"PER-CENT",strMonitErrPercent,null);
				    xvar3.numDataType=-9;
				    xvar2.xvarInPercent=xvar3;
				}
			    }
			    else {
sysOut.println("___REJECTED: "+" SUBENT:"+subent.Subent+" DatasetID:"+DatasetID
+" MonRows="+xvar1.xdata.DnRow
+" MonErrRows="+xvar2.xdata.DnRow
);
			    }
*/
			}

//			if (xvar3!=null) xvar=xvar3; else
			if (xvar3==null)
			continue;
		    }
if (false)
sysOut.println("___ACCEPTED: "+" xvar="+xvar);

		    compVars[lastNum].add_x4var(xvar);
		    if (false)
		    sysOut.println(""
			+"varNum="+xvar.Variable_Number
			+" "+(ii+1)+")\t"+xvar.Header
			+"\t what:"+xvar.what
			+"\t whatVar:"+xvar.whatVar
			+"\torder="+xvar.strVarOrderFlag
			+"\tDataType="+xvar.DataType
			+"\tFamilyCode="+xvar.FamilyCode
			+"\tUnits="+xvar.Units
			);
//if (xvar.numDataType1==7) str1="#";
//System.out.println("...xvar.numDataType1="+xvar.numDataType1+" xvar.Header="+xvar.Header);
		}
//sysOut.println(" compVars.length="+compVars.length);
		if (vVars.size()==0) compVars=new x4cvar[0];
//test		for (ii=0; ii<compVars.length; ii++) compVars[ii].println();

	if (compVars!=null)
	for (ii=0; ii<compVars.length; ii++) {
//if (ii==0) System.out.println("...0...compVars.length="+compVars.length+" "+originalReacode);
	    xcvar=(x4cvar)compVars[ii];
	    xvar=xcvar.xvar0;
	    if (xvar==null) continue;
//System.out.println("...1...xvar.numDataType1="+xvar.numDataType1+" xvar.Header="+xvar.Header);
	    if ((xvar.numDataType1==1)||(xvar.numDataType1==2)) {
//		if (xcvarEnergy==null)
		xcvarEnergy=xcvar;
//System.out.println("...2...xvar.numDataType1="+xvar.numDataType1+" xvar.Header="+xvar.Header);
	    }
	    if (xvar.numDataType1==4) {
//		if (xcvarAngle==null)
		xcvarAngle=xcvar;
//System.out.println("...4...xvar.numDataType1="+xvar.numDataType1+" xvar.Header="+xvar.Header);
	    }
	}

	prepareC4Err();//2020-05-06
	nCols=nn;
	return nn;
    }

    public int treatErrAnalysCodes(Vector vVars,x4bib xbib,x4subent sub2,char pointer) {
	String ERR_ANALYS_Code=null;
	int nn=0;
	if (xbib==null) return nn;
	Vector codes=xbib.getKWCodes("ERR-ANALYS");
	String code,Header,strComment,code0,free;
	String arr[];
	int ii,ii0,ind;
	x4var xvar,xvar2;
	boolean found,errTotGiven=false;
	for (ii0=0, found=false; ii0<vVars.size(); ii0++) {
	    xvar=(x4var)vVars.elementAt(ii0);
	    if (xvar.flagValue) continue;
//System.out.println("__treatErrAnalysCodes::"+ii0+") "+xvar.Header+" "+xvar.whatVar);
	    if (xvar.Variable_Value==1) continue;//value, not error
	    if (xvar.whatVar.startsWith("0.9"))
	    if (xvar.whatVar.indexOf("1")>0) {
		errTotGiven=true;
		break;
	    }
/*	    System.out.println("__err_"+(ii0+1)+") "+xvar.Header
		+"\twhat:"+xvar.what
		+"\tCOMMON:"+xvar.xdata.flagCommon
		+"\twhatVar:"+xvar.whatVar
		+"\torder="+xvar.strVarOrderFlag
		);
*/
	}
	for (ii=0; ii<codes.size(); ii++) {
	    x4code xcode=(x4code)codes.elementAt(ii);
	    code=xcode.code;
	    ERR_ANALYS_Code=code;
//	    if (code.equals(Header)) return "";
//	    if (!code.startsWith(Header+",")) continue;
	    if (code.indexOf(",")<0) continue;
	    arr=code.split(",");
	    if (arr.length<2) continue;
	    Header=arr[0];
	    for (ii0=0, found=false; ii0<vVars.size(); ii0++) {
		xvar=(x4var)vVars.elementAt(ii0);
		if (Header.equals(xvar.Header)) {found=true;break;}
	    }
	    if (found) break;
	    String strMinErr="",strMaxErr="",strTypErr="";
	    if (arr.length>1) strMinErr=arr[1];
	    if (arr.length>2) strMaxErr=arr[2];
	    if (arr.length>3) strTypErr=arr[3];
	    Float MinErr=str2Float(strMinErr),MaxErr=str2Float(strMaxErr);
/*
	    if (MinErr==null) continue;
	    if (errTotGiven) MaxErr=MinErr;
	    if (MaxErr==null) continue;
*/
	    if (!addErrFromErrAn2Common) continue;//2023-03-09

	    //2021-09-24
	    if ((MinErr==null)&&(MaxErr==null)) continue;
//20230313  if ((MinErr!=null)&&(MaxErr!=null)) { // ,2,4: ave=3 or 2, if err-tot is not given: impact to err-tot=2
	    if ((MinErr!=null)&&(MaxErr!=null)) { // ,2,4: always set min: err-tot=2
//20211004	if (errTotGiven) MaxErr=MinErr;
//20230313	if (errTotGiven) //20230313:commented, so, always set min: err-tot=2
		{
		    if (MinErr>0)
		    MaxErr=MinErr;
		}
	    }
	    else
	    if ((MinErr==null)&&(MaxErr!=null)) {
//2021-12-15	MinErr=new Float(0);// ,,4: ave=2, impact to err-tot:2
//2022-07-20	MinErr=new Float(MaxErr/2);// ,,4 => ,2,4 ave=3, impact to err-tot:3   ::D0999002.x4
		MinErr=new Float(0);//,,10: ave=5, impact to err-tot:2 ::33144001:(ERR-7,,10.) dead time of the detector system (<10%)
	    }
	    else
	    if ((MinErr!=null)&&(MaxErr==null)) {// ,4,: ave=4, impact to err-tot:4
		MaxErr=MinErr;
	    }

	    Float AveErr=(MinErr+MaxErr)/2;
/*	    System.out.println(ii+"/"+codes.size()+")"
		+" "+Header+" ["+x4dict024.getPlottingFlags(Header)+"]"
		+" code=["+code+"]"+arr.length
//		+" strMinErr=["+strMinErr+"]"+" strMaxErr=["+strMaxErr+"]"
		+" MinErr=["+MinErr+"]"+" MaxErr=["+MaxErr+"]"+" AveErr=["+AveErr+"]"
		+" strTypErr=["+strTypErr+"]");
*/
	    String strAveErr=""+AveErr;
	    strAveErr="".format("%.4f",AveErr);
	    AveErr=str2Float(strAveErr);
	    strAveErr=""+AveErr;
//	    System.out.println(ii+"/"+codes.size()+Header+" "+" ["+x4dict024.getPlottingFlags(Header)+"]"+" code=["+code+"]"+arr.length);
	    strComment="ERR-ANALYS:"+strpad("("+code+") ",19)+"ADDED AS COMMON:"
////		+" "+Header
////		+"="+strAveErr+"(PER-CENT)";
//		+"("+Header+")"
//		+"(PER-CENT)="+strAveErr;
		+""+Header+""
		+",PER-CENT="+strAveErr
		+"%"//2020-10-13
		;
	    xvar2=makeX4CommonVar(sub2,pointer,Header,"PER-CENT",""+AveErr,strComment);
	    vVars.addElement(xvar2);
	    nn++;
	}

	//add 1st line of free text to xvar.comment
	for (ii=0; ii<codes.size(); ii++) {
	    x4code xcode=(x4code)codes.elementAt(ii);
	    code=xcode.code;
	    code0=code;
	    ind=code0.indexOf(",");
	    if (ind>=0) code0=code0.substring(0,ind);
	    free="";
//20211217  if (xcode.freeLines.size()>0) {
//20211217	free=(String)xcode.freeLines.elementAt(0);
	    for (int ifree=0,nfree=0; ifree<xcode.freeLines.size(); ifree++) {
		String free1=(String)xcode.freeLines.elementAt(ifree);
		free1=free1.replaceAll("[\\s]{2,}"," ").trim();//remove multiple space double blank
		if (free1.equals("")) continue;
		if (!free.equals("")) free+="\n";
		free+=free1;
		nfree++;
		if (nfree>=1) break;//number of non-empty lines to be stored, now =1
	    }
//	    System.out.println("___#ERR_ANALYS."+ii+") ["+code0+"]"+" free:["+free+"]");
	    for (ii0=0; ii0<vVars.size(); ii0++) {
		xvar=(x4var)vVars.elementAt(ii0);
		if (xvar.Header.equals(code0)) xvar.free1comment=free;
	    }
	}

	return nn;
    }
    public Float str2Float(String str)
    {
	if (str==null) return null;
	String str1=str.trim().toUpperCase();
	try {
	    float rr=Float.parseFloat(str1);
	    return new Float(rr);
	}
	catch(Exception e) {
	}
	return null;
    }

    public void prepareC4Err() {
	int ii,iii;
	x4var xvar;
	if (false)
	sysOut.println("____prepareC4Err():vVars.size()="+vVars.size());
	if (extDebug)
	if (false)
	for (ii=0; ii<vVars.size(); ii++) {
	    xvar=(x4var)vVars.elementAt(ii);
//	    if (false)
	    sysOut.println("_er:"+(ii+1)+") "+xvar.Header
//	    System.out.println("_er:"+(ii+1)+") "+xvar.Header
		+"\twhat:"+xvar.what
		+"\tCOMMON:"+xvar.xdata.flagCommon
		+"\tval/err:"+xvar.Variable_Value
		+" what:"+xvar.whatVar
		+"\torder="+xvar.strVarOrderFlag
		+"\tDataType="+xvar.DataType
		+"\tFamilyCode="+xvar.FamilyCode
		+"\tvarNum="+xvar.Variable_Number
		+"\tBasicUnits="+BasicUnits
		+"\tvar.BasicUnits="+xvar.BasicUnits
		);
//	    sysOut.println(" "+(ii+1)+")\t"+xvar.toString());
	}

	boolean errt=false,dataerr=false,errsys=false;
	boolean errtc=false,dataerrc=false,errsysc=false;
	for (ii=0; ii<vVars.size(); ii++) {
	    xvar=(x4var)vVars.elementAt(ii);
	    if (xvar.Header.endsWith("DATA-ERR")){dataerr=true;if (xvar.xdata!=null)dataerrc=xvar.xdata.flagCommon;}
	    if (xvar.Header.endsWith("ERR-T"))   {errt=true;   if (xvar.xdata!=null)errtc=xvar.xdata.flagCommon;}
	    if (xvar.Header.endsWith("ERR-SYS")) {errsys=true; if (xvar.xdata!=null)errsysc=xvar.xdata.flagCommon;}
	}
	if (false)
	sysOut.println("____prepareC4Err():vVars.size()="+vVars.size()
	+"\n__dataerr: "+dataerr+":"+dataerrc
	+"\n__errt:    "+errt+":"+errtc
	+"\n__errsys:  "+errsys+":"+errsysc
	);

	flagDataErr_equ_ErrSys=false;
	if (dataerr)
	if (dataerrc)
	if (!errsys)
	flagDataErr_equ_ErrSys=true;

	if (false)
	sysOut.println("____prepareC4Err():vVars.size()="+vVars.size()+" flagDataErr_equ_ErrSys="+flagDataErr_equ_ErrSys);

    }






//	E1=(E0*0.76463526 + 2.2153838)/0.94067925 MeV
//	E1=(E0*e0a1 + e0a2)/e0a3
	double E0a1=0, E0a2=0, E0a3=1;


//  forward  calc-> inverse
//  f{b + B} calc-> i{a + A}
//  C-13(a,n)O-16  calc-> O-16(n,a)C-13
//6-C-13(A,N)8-O-16,,SIG
    double ENEa, dENEa, SIGi, dSIGi;
    double e4level=0;

    public int calcInverseReact(double Eb,double dEb,double Sf,double dSf)
    {
	int ierr;
//System.out.println("___0___calcInverseReact("+"Eb:"+(float)Eb+" Sf:"+(float)Sf+")"+" e4level:"+e4level);
	ierr=calcInverseReact_smk0(Eb,dEb,Sf,dSf);
//System.out.println("___1___calcInverseReact("+"Ea:"+(float)ENEa+" Si:"+(float)SIGi+")");
	return ierr;
    }

    public int getParamsCalcInverseReact()
    {
	return getParamsCalcInverseReact_smk0();
    }

    public int getParamsCalcInverseReact_smk0()
    {
	//System.out.println(" inverseReactionPossible="+inverseReactionPossible);
	inv_mult_kk1=1;
	if (!inverseReactionPossible) return -1;
	double mA,ma,mb,mB;//mass
	double jA,ja,jb,jB;//spin
	double Qfi;//Q-value
	double kk1;
	mb=xProj1.MASS_mev;
	mB=xTarg1.MASS_mev;
	ma=xEjec1.MASS_mev;
	mA=xProd1.MASS_mev;
	jb=Math.abs(xProj1.Spin);
	jB=Math.abs(xTarg1.Spin);
	ja=Math.abs(xEjec1.Spin);
	jA=Math.abs(xProd1.Spin);

	Qfi=(mb+mB)-(ma+mA);
//	Qfi*=1e6;//MeV to eV

//	Ea=(Eb*mB/(mb+mB)-Qif)/(mA/(ma+mA));
	String str=" + ";
	if (Qfi<0) {str=" - ";Qfi=-Qfi;}
	strEa="(E0*"+(float)(mB/(mb+mB))+str+(float)Qfi+")/"
	+(float)(mA/(ma+mA))+" MeV";

	E0a1=mB/(mb+mB);
	E0a2=Qfi;
	E0a3=mA/(ma+mA);

	kk1=1
	*(2*jb+1)*(2*jB+1)/(2*ja+1)/(2*jA+1)
	*mb*mB*mB/(mb+mB)/(mb+mB)
	*(ma+mA)*(ma+mA)/ma/mA/mA
	*mb*mB/(mb+mB)
	*(ma+mA)/ma/mA
	;
	if (inv_mult) kk1*=inv_mult_k2;//2020-03-23
	strSi="SIG0*E0/E1*"+(float)kk1;
	return 0;
    }


    public int calcInverseReact_smk0(double Eb,double dEb,double Sf,double dSf)
    {
	//System.out.println(" inverseReactionPossible="+inverseReactionPossible);
	inv_mult_kk1=1;
	if (!inverseReactionPossible) return -1;
	double mA,ma,mb,mB;//mass
	double jA,ja,jb,jB;//spin
	double Qif;//Q-value
	double Ea,dEa,Si,dSi;//result
	mb=xProj1.MASS_mev;
	mB=xTarg1.MASS_mev;
	ma=xEjec1.MASS_mev;
	mA=xProd1.MASS_mev;
	jb=Math.abs(xProj1.Spin);
	jB=Math.abs(xTarg1.Spin);
	ja=Math.abs(xEjec1.Spin);
	jA=Math.abs(xProd1.Spin);

	Qif=(ma+mA)-(mb+mB);
	Qif*=1e6;//MeV to eV
	Qif+=e4level;
/*
System.out.println(""
  +" xTarg1:"+xTarg1.originalStr+"\tza"+xTarg1.za+"\tmB:"+(float)xTarg1.MASS_mev+"\tjB:"+jB
+"\n xProj1:"+xProj1.originalStr+"\tza"+xProj1.za+"\tmb:"+(float)xProj1.MASS_mev+"\tjb:"+jb+" (mb+mB):"+(float)(mb+mB)
+"\n xEjec1:"+xEjec1.originalStr+"\tza"+xEjec1.za+"\tma:"+(float)xEjec1.MASS_mev+"\tja:"+ja                           
+"\n xProd1:"+xProd1.originalStr+"\tza"+xProd1.za+"\tmA:"+(float)xProd1.MASS_mev+"\tjA:"+jA+" (ma+mA):"+(float)(ma+mA)
+"\n Qif:"+(float)(Qif/1e6)+" MeV"+" e4level:"+(float)(e4level/1e6)
);
*/

//?	Ea=(Eb*mB/(mb+mB)+Qif)/(mA/(ma+mA));
	Ea=(Eb*mB/(mb+mB)-Qif)/(mA/(ma+mA));
	dEa=dEb*(mB/(mb+mB))/(mA/(ma+mA));
//2020	Si=Sf
	inv_mult_kk1=1
	*(2*jb+1)*(2*jB+1)/(2*ja+1)/(2*jA+1)
	*mb*mB*mB/(mb+mB)/(mb+mB)
	*(ma+mA)*(ma+mA)/ma/mA/mA
	*Eb/Ea
	;
//	if (inv_mult) Si*=inv_mult_k2;//2020-03-23
	if (inv_mult) inv_mult_kk1*=inv_mult_k2;//2020-03-23
	Si=Sf*inv_mult_kk1;
	dSi=dSf/Sf*Si;

/*
System.out.println("..Eb="+(float)Eb+"\tSf="+(float)Sf+"\tdSf="+(float)dSf+"\trdSf="+(float)(dSf/Sf*100)+"%"
+"\n..Ea="+(float)Ea+"\tSi="+(float)Si+"\tdSi="+(float)dSi+"\trdSi="+(float)(dSi/Si*100)+"%");
if (false)
System.out.println("....11111...."
+"\n\t"+xreac1.SF1+"\t xTarg1.MASS="+xTarg1.MASS_mev/931.4941
+"\n\t"+xreac1.SF2+"\t xProj1.MASS="+xProj1.MASS_mev/931.4941
+"\n\t"+xreac1.SF3+"\t xEjec1.MASS="+xEjec1.MASS_mev/931.4941
+"\n\t"+xreac1.SF4+"\t xProd1.MASS="+xProd1.MASS_mev/931.4941
+"\n\tQ="+((xEjec1.MASS_mev+xProd1.MASS_mev)-(xTarg1.MASS_mev+xProj1.MASS_mev))/931.4941
+"\n\t"+xreac1.SF1+"\t xTarg1.MASS_mev="+xTarg1.MASS_mev
+"\n\t"+xreac1.SF2+"\t xProj1.MASS_mev="+xProj1.MASS_mev
+"\n\t"+xreac1.SF3+"\t xEjec1.MASS_mev="+xEjec1.MASS_mev
+"\n\t"+xreac1.SF4+"\t xProd1.MASS_mev="+xProd1.MASS_mev
+"\n\tQ="+((xEjec1.MASS_mev+xProd1.MASS_mev)-(xTarg1.MASS_mev+xProj1.MASS_mev))
+"\n....Qif="+Qif
);
double kk1j;
kk1j=(2*jb+1)*(2*jB+1)/(2*ja+1)/(2*jA+1);
System.out.println("....kk1j="+kk1j+" jA="+jA+" ja="+ja+" jb="+jb+" jB="+jB);
double kk1=1
*(2*jb+1)*(2*jB+1)/(2*ja+1)/(2*jA+1)
*mb*mB*mB/(mb+mB)/(mb+mB)
*(ma+mA)*(ma+mA)/ma/mA/mA
*mb*mB/(mb+mB)
*(ma+mA)/ma/mA
;
if (inv_mult) kk1*=inv_mult_k2;//2020-03-23
System.out.println("....kk1="+kk1);
*/


	ENEa=Ea;
	dENEa=dEa;
	SIGi=Si;
	dSIGi=dSi;
	return 0;
    }









/*

    public void mf4sig2lab(double Ene,double dEne,double Dat,double dDat,double Angle)
    {
	Energy	=Ene	;
	dEnergy	=dEne	;
	Data	=Dat	;
	dData	=dDat	;
	ThetaLab=Angle  ;
//	myOut.println("	Center_of_mass="+Center_of_mass+" Dat="+Dat);
	if (!Center_of_mass) return;
	cm2lab(Ene/1e6,qvaluekev/1e3,Angle,M1,M2,M3,M4,Dat,dDat,true);
	if (!flagRutherfordRatio) {
		Data=SigmaLab;
		dData=dSigmaLab;
	}
	ThetaLab=THETA1;
    }

    double SigmaLab=0;
    double THETA1=0;
    double dSigmaLab=0;
    double Ratio=0,SinRatio=0;
    String strRatio="";

*/










    public char getC4Status() {
	char c4status=' ';
	if (subent==null) return c4status;
	if (subent.xbib==null) return c4status;
	c4status=getC4Status(subent.xbib);
	if (c4status!=' ') return c4status;
	if (subent.subent1!=null)
	if (subent.subent1.xbib!=null)
	c4status=getC4Status(subent.subent1.xbib);
	return c4status;
    }
    public char getC4Status(x4bib xbib) {
	String str;
	int i,ii,nc=0;
	x4kw xkw;
	x4code xcode;
	Vector keywords=xbib.keywords;
	boolean chk;
	for (i=0; i<keywords.size(); i++) {
	    xkw=(x4kw)keywords.elementAt(i);
	    if (!xkw.keyword.equals("STATUS")) continue;
	    for (ii=0; ii<xkw.codes.size(); ii++) {
		xcode=(x4code)xkw.codes.elementAt(ii);
//System.out.println(".....xkw.keyword="+xkw.keyword+" ["+xcode.code+"]"+" ["+xcode.pointer+"]"+" ["+cpointer+"]");

		//2018-01-02: java x4toc5 22936.x4
//SELECT * FROM KEYWORD
// where KeyWord='STATUS' and Code='SPSDD' and Pointer<>' '
//:::: 22936002; D0008002
		chk=false;
		if (xcode.pointer.equals("")) chk=true;
		else {
		    if (xcode.pointer.equals((""+cpointer).trim())) chk=true;
		}
		if (!chk) continue;

		if (xcode.code.indexOf("PRELM")>=0) return 'P';
		if (xcode.code.indexOf("SPSDD")>=0) return 'S';
		if (xcode.code.indexOf("DEP")>=0)   return 'D';
		if (xcode.code.indexOf("COREL")>=0) return 'C';
		if (xcode.code.indexOf("APRVD")>=0) return 'A';
		if (xcode.code.indexOf("OUTDT")>=0) return 'O';
		if (xcode.code.indexOf("RNORM")>=0) return 'R';
	    }
	    break;
	}
//	sysOut.println(".....x4bib.reaction: "+vReacts.size());
	//pause("----reaction---");
	return ' ';
    }

    public int findEnMinMax() {
	int ii,nn=0;
	x4var xvar;
//System.out.println("____vVars.size()="+vVars.size()+"["+IndepVarFamilyCode+"] nArgs="+nExpectedArgs);
//	for (ii=0; ii<nExpectedArgs; ii++)
//	System.out.println("|||"+ii+"["+numIndepVarFamilyCode[ii]+"]");
	for (ii=0; ii<vVars.size(); ii++) {
	    xvar=(x4var)vVars.elementAt(ii);
if (false)
System.out.println("...."+(ii+1)+")"+xvar.Header
+" "+xvar.DataType
//+" f:"+xvar.FamilyCode
+" t:"+xvar.numDataType1
+" N?"+xvar.Variable_Number
+" "+xvar.strVarOrderFlag
+" what="+xvar.what
+" "+xvar.Variable_Value
+" PLOT:"+xvar.PlottingFlags1
+" "+x4dict024dt.getDataTypeShortExpansion(xvar.DataType)
+" "+x4dict024dt.getPlottingFlagsExpansion(xvar.PlottingFlags1)
);

//+	    if (!xvar.DataType.equals("41")) continue;
	    if (xvar.numDataType1!=2) continue;//EN
//	    if (xvar.strVarOrderFlag.indexOf(".9")>=0) continue;
	    if (xvar.Variable_Value==0) continue; //equ to ^

	    nn=1;
	    xvar.findMinMaxData();
//	    System.out.println(" "+xvar.rMin+" "+xvar.rMax);
//	    System.out.println(" xvar.what.indexOf('Max')="+xvar.what.indexOf("Max"));
//	    if (xvar.Header.indexOf("MAX")<0) {
	    if (xvar.what.indexOf("Max")<0) {
		if (enMin==null) enMin=xvar.rMin;
		else {
		    if (xvar.rMin!=null)
		    if (xvar.rMin.compareTo(enMin)<0) enMin=xvar.rMin;
		}
	    }
//	    if (xvar.Header.indexOf("MIN")<0) {
	    if (xvar.what.indexOf("Min")<0) {
		if (enMax==null) enMax=xvar.rMax;
		else {
		    if (xvar.rMax!=null)
		    if (xvar.rMax.compareTo(enMax)>0) enMax=xvar.rMax;
		}
	    }
//	    System.out.println(" enMin="+float2str(enMin)+" enMax="+float2str(enMax));
	}
	strEnMin=float2str(enMin);
	strEnMax=float2str(enMax);
	return nn;
    }
    DecimalFormat myFormatterE=new DecimalFormat("0.00#E0");
    public String float2str(Float en) {
	String str="";
	if (en!=null) {
//	    str=""+en;
	    str=myFormatterE.format(en.floatValue());
	    str=str.toLowerCase();
	    if (str.endsWith("e0")) str=myStrReplace(str,"e0","");
	    if (str.endsWith(".00")) str=myStrReplace(str,".00",".");
	}
	return str;
    }

    boolean angle2int=true;
    public String findAllMinMax() {
	int ii,nn=0;
	String str="",str1;
//System.out.println("....vVars.size()="+vVars.size()+"["+IndepVarFamilyCode+"] nArgs="+nExpectedArgs);
	for (ii=0; ii<nExpectedArgs; ii++) {
	    str1=findIndepMinMax(numIndepVarFamilyCode[ii]);
	    if (ii>0) str=str+"; ";
	    str=str+str1;
	}
	strIndepDataRanges=str;
	return str;
    }
    Float tmpMin=null;
    Float tmpMax=null;
    String strTmpFlag="";
    Vector vVarsMinMax=new Vector();
    public String findIndepMinMax(int numFam) {
	int ii,nn=0;
	x4var xvar;
	Float rMin=null;
	Float rMax=null;
	Float ff=null;
	String strFamFlag="";
	String strVarMin="";
	String strVarMax="";
	vVarsMinMax=new Vector();
	double dd;
	tmpMin=null;
	tmpMax=null;
	strTmpFlag="";
//System.out.println("....vVars.size()="+vVars.size()+"["+IndepVarFamilyCode+"] nArgs="+nExpectedArgs);
//	for (ii=0; ii<nExpectedArgs; ii++)
//	System.out.println("|||"+ii+"["+numIndepVarFamilyCode[ii]+"]");

	findAllIndepMinMax();//2011

	for (ii=0; ii<vVars.size(); ii++) {
	    xvar=(x4var)vVars.elementAt(ii);

if (false)
System.out.println("...."+(ii+1)+")"+xvar.Header
+" "+xvar.DataType
//+" f:"+xvar.FamilyCode
+" t:"+xvar.numDataType1
+" N?"+xvar.Variable_Number
+" "+xvar.strVarOrderFlag
+" what="+xvar.what
+" "+xvar.Variable_Value
+" PLOT:"+xvar.PlottingFlags1
+" "+x4dict024dt.getDataTypeShortExpansion(xvar.DataType)
+" "+x4dict024dt.getPlottingFlagsExpansion(xvar.PlottingFlags1)
);

//+	    if (!xvar.DataType.equals("41")) continue;
	    if (xvar.numDataType1!=numFam) continue;//EN:2,E2:3,AN:4
//	    if (xvar.strVarOrderFlag.indexOf(".9")>=0) continue;
	    if (xvar.Variable_Value==0) continue; //equ to ^

	    strFamFlag=x4dict024dt.getDataTypeShortExpansion(xvar.DataType);
	    nn=1;

//2011	    xvar.findMinMaxData();
//2011	    rMax=xvar.rMax;
//2011	    rMin=xvar.rMin;
//	    xvar.findMinMaxData();
	    rMax=xvar.rMax1;
	    rMin=xvar.rMin1;
//	    System.out.println(" "+xvar.rMin+" "+xvar.rMax);

	    vVarsMinMax.addElement(xvar);//2019: nnE2

//		System.out.println(" xvar.what.indexOf('Max')="+xvar.what.indexOf("Max"));
	    if (xvar.DataType.equals("62")) {
		strFamFlag=x4dict024dt.getDataTypeShortExpansion("61");
		if (rMax!=null) {
		    dd=rMax.doubleValue();
		    if ((dd>=-1)&&(dd<=1)) {
			if (angle2int)	dd=Math.ceil(Math.acos(dd)/Math.PI*180);
			else		dd=Math.acos(dd)/Math.PI*180;
		    }
		    rMax=new Float(dd);
		}
		if (rMin!=null) {
		    dd=rMin.doubleValue();
		    if ((dd>=-1)&&(dd<=1)) {
			if (angle2int)	dd=Math.floor(Math.acos(dd)/Math.PI*180);
			else		dd=Math.acos(dd)/Math.PI*180;
		    }
		    rMin=new Float(dd);
		}
		ff=rMin; rMin=rMax; rMax=ff;
	    }
//	    if (xvar.Header.indexOf("MAX")<0) {
	    if (xvar.what.indexOf("Max")<0) {
		if (tmpMin==null) tmpMin=rMin;
		else {
		    if (rMin!=null)
		    if (rMin.compareTo(tmpMin)<0) tmpMin=rMin;
		}
	    }
//	    if (xvar.Header.indexOf("MIN")<0) {
	    if (xvar.what.indexOf("Min")<0) {
		if (tmpMax==null) tmpMax=rMax;
		else {
		    if (rMax!=null)
		    if (rMax.compareTo(tmpMax)>0) tmpMax=rMax;
		}
	    }
//	    System.out.println(" tmpMin="+float2str(tmpMin)+" tmpMax="+float2str(tmpMax));
//System.out.println("___3___::"+" numFam="+numFam+" ii="+ii+" tmpMin="+float2str(tmpMin)+" tmpMax="+float2str(tmpMax));
	}
//	strVarMin=float2str(tmpMin);
//	strVarMax=float2str(tmpMax);
	if (numFam==4) {
		strVarMin=Angle2str(tmpMin);
		strVarMax=Angle2str(tmpMax);
	}
	else {
		strVarMin=EnergyLevel2str(tmpMin);
		strVarMax=EnergyLevel2str(tmpMax);
	}
	if (strVarMax.equals(strVarMin)) strVarMax="";
	else strVarMax=" "+strVarMax.trim();
	if (strFamFlag!=null)
	if (!strFamFlag.equals("")) {
		strTmpFlag=strFamFlag;
		return strFamFlag.trim()+"="+strVarMin.trim()+strVarMax;
	}
	return "";
    }


    DecimalFormat twoDigits   = new DecimalFormat( "0.0E00" );
    DecimalFormat threeDigits = new DecimalFormat( "0.00E00" );
//  DecimalFormat myAngFormatter = new DecimalFormat("0.0#");
    DecimalFormat myAngFormatter = new DecimalFormat("0.#");
    public String Angle2str(Float rr)
    {
	if (rr==null) return "";
	String str;
	str=myAngFormatter.format(rr.floatValue());
	if (str.endsWith(".0")) str=myStrReplace(str,".0","");
	return str.toLowerCase();
    }
    public String EnergyLevel2str(Float rrr)
    {
	if (rrr==null) return "";
	String str;
	float rr=rrr.floatValue();
	if (rr==0) return "0";
//	str=threeDigits.format(rr);
	str=twoDigits.format(rr);
	str=myStrReplace(str,"E-0","E-");
	str=myStrReplace(str,"E0","E");
	str=myStrReplace(str,"0E","E");
	str=myStrReplace(str,"0E","E");
	str=myStrReplace(str,".E","E");
//	str=myStrReplace(str,"E-","-");
//	str=myStrReplace(str,"E","+");
	return str.toLowerCase();
    }






    public boolean cmpReactionCode(String reacX)
    {
	String str1,str2;
	str1=reacode;
	str1=myStrReplace(str1,",,,EXP","");
	str1=myStrReplace(str1,",,EXP","");
	str1=myStrReplace(str1,",EXP","");
	str2=reacX;
	str2=myStrReplace(str2,",,,EXP","");
	str2=myStrReplace(str2,",,EXP","");
	str2=myStrReplace(str2,",EXP","");
	return str2.equals(str1);
    }

    public String getC4ReactionCode(String reacCombi,Vector vReacs)
    {
	int ii=0;
	String strOut="";
	String C4Reac;
	x4reacstr xreacstr;
	int iReacstr;
	int zaIncident;
	String SF2;
	String SF3;
	String SF4;
	String SF5;
	String SF6;
	String SF7;
	String SF8;
	String SF9;
	for (ii=0; ii<vReacs.size(); ii++) {
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    iReacstr=ii;
//	    zaIncident=
	    SF2=xreacstr.SF2.trim();
	    SF3=xreacstr.SF3.trim();
	    SF4=xreacstr.SF4.trim();
	    SF5=xreacstr.SF5.trim();
	    SF6=xreacstr.SF6.trim();
	    SF7=xreacstr.SF7.trim();
	    SF8=xreacstr.SF8.trim();
	    SF9=xreacstr.SF9.trim();
	    if (ii==0) strOut=reacCombi;
	    if ((SF4.indexOf("ELEM")<0)&&(SF4.indexOf("MASS")<0)) {
		C4Reac="("+SF2+","+SF3+")"+SF5+","+SF6+","+SF7+","+SF8;
	    }
	    else {
		C4Reac="("+SF2+","+SF3+")"+SF4+","+SF5+","+SF6+","+SF7+","+SF8;
	    }
//	    C4Reac+=","+SF9;
	    for (;;) {
		if (C4Reac.endsWith(",")) C4Reac=C4Reac.substring(0,C4Reac.length()-1);
		else break;
	    }
	    strOut=myStrReplace(strOut,"R"+iReacstr+"#",C4Reac);
        }
	return strOut;
    }








    public int getDataLY()
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	Float rr;
	Vector vec=vVars;
//	Vector vec=vAllVars;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}
//System.out.println("...getDataLY():DnRow="+DnRow+" DnCol="+DnCol);

	for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	    empty_Y_value=true;
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		rr=xvar.getValue(iRow);
		if (rr!=null) {
//System.out.println("...getDataLY():iRow="+iRow+" iCol="+iCol+" rr="+rr+" "+xvar.Header+" "+xvar.isItYValue());
		    if (xvar.isItYValue()) empty_Y_value=false;
		}
	    }
	    if (!empty_Y_value) nRow++;
	}
	return nRow;
    }

    //even if Reaction is not recognized by FamilyCode:[0........]
    public int getDataLY_DATA()
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	Float rr;
//	Vector vec=vVars;
	Vector vec=vAllVars;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}
//System.out.println("...getDataLY_DATA():DnRow="+DnRow+" DnCol="+DnCol);

	for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	    empty_Y_value=true;
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		rr=xvar.getValue(iRow);
		if (rr!=null) {
//System.out.println("...getDataLY_DATA():iRow="+iRow+" iCol="+iCol+" rr="+rr+" "+xvar.Header+" "+xvar.isItDataValue());
		    if (xvar.isItDataValue()) empty_Y_value=false;
		}
	    }
	    if (!empty_Y_value) nRow++;
	}
	return nRow;
    }

    public void findAllIndepMinMax()
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	Float rr;
	Vector vec=vVars;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    xvar.findMinMaxData1clr();
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}
	for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	    empty_Y_value=true;
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		rr=xvar.getValue(iRow);
		if (rr!=null) {
		    if (xvar.isItYValue()) empty_Y_value=false;
		}
	    }
	    if (!empty_Y_value)
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
		if (xvar.isItYValue()) continue;
//		rr=xvar.getValueInBasicUnits(iRow);
		rr=xvar.getValue(iRow);
		if (rr!=null) xvar.findMinMaxData1put(rr);
	    }
	}
    }




    public String getPointersOfData()
    {
	String str="";
	int i,ind;
	Vector vec=vVars;
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    if (!xvar.isItYValue()) continue;
	    if (str.indexOf(""+xvar.cpointer)<0) str=str+xvar.cpointer;
	}
	return str;
    }


    Vector vCompNotes=new Vector();
    Hashtable hCompNotes=new Hashtable();
    public void str2CompNotes(String str)
    {
	String str1;
	str1=(String)hCompNotes.get(str);
	if (str1==null) {
	    hCompNotes.put(str,str);
	    vCompNotes.addElement(str);
	}
	//System.out.println("\t"+str);
    }















    boolean tryToRecoverEnresFromEn=true;
//  Float ErrStatPortionOfTotIfErrStatEq0=new Float(0.25);
    Float ErrStatPortionOfTotIfErrStatEq0=null;
    Float ErrStatPortionOfDataIfErrStatEq0=null;

    boolean tryToRecoverElemMassFromSF4=true;

    x4c4obj c4obj_c5wrk=null;

    boolean isVectorCommon=false;
    int     nVectorCommon=0;
    int     nRowTotal=0;
    x4c4obj c4obj=null;
    int     c4_MF=0;
    int     c4_MT=0;
    int     c4_OpNum=0;
//2020    Float   c4data[][]=null;
    c5arr c5data=null;
    boolean c4data_flag_inv[]=null;
    char    c4data_cmetaProd[]=null;//2018-12-19:ISOMER
    Float   c4data_decayFlag[]=null;//2019-09-26:DECAY-FLAG
    char    c4data_compValueSetFromCh[]=null;//2023-04-13

    int     iCovarFlag=0;
    Float   c4ene[]=null;
    Float   c4dataTot[]=null;
    Float   c4dataErrTot[]=null;
    Float   c4dataErrStat[]=null;
    Float   c4dataErrSys[]=null;
    Float   c4dataErrMrc[]=null;
    Float   c4dataMonitEne[]=null;
    Float   c4dataMonitVal[]=null;
    Float   c4dataMonitErr[]=null;
    Float   c4dataFc[]=null;
    Float   c4dataFcErr[]=null;

    float   c4covar[][]=null;

    float   c4covar_ene[]=null;
    float   corrMerc[][]=null;
    x4mkcov mxx=null;

    public void prepareC4obj()
    {
	x4subent sub2;
	Vector   vReactions=null;
	x4reaction xreacode,newReacode;
	int i,nRow,nn;
	int DnCol=0;

	sub2=this.subent;
	xreacode=this;

	c4obj=new x4c4obj();
	c4obj.initDataC4();
	nRow=c4obj.prepareC4Info(sub2,xreacode,false);
//	if (nRow<=0) continue;
	c4_MF=c4obj.c4_MF;
	c4_MT=c4obj.c4_MT;
	c4_OpNum=c4obj.c4_OpNum;

if (sub2!=null)//20230323
if (sub2.xbib!=null)
{
	sub2.xbib.prepareVectorCommon();
	isVectorCommon=sub2.xbib.isVectorCommon;
}

//System.out.println("....Subent="+sub2.Subent+" isVectorCommon="+isVectorCommon);
	if (isVectorCommon) {
//System.out.println("...Reactions:"+vReactions.size()+" "+sub2.Subent+" isVectorCommon="+isVectorCommon);
	    nRowTotal=0;
	    nVectorCommon=sub2.xbib.getNReactions();
	    for (i=0; i<nVectorCommon; i++) {
		xreacode=sub2.xbib.getReaction(i);
//System.out.println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
		nn=sub2.prepareC4F(xreacode.cpointer);
		nRow=xreacode.getDataLY();
		if (nRow>0) nRowTotal+=nRow;
	    }
	}
	else {
	    nRowTotal=xreacode.getDataLY();
	    nVectorCommon=1;
	}
//System.out.println("....Subent="+sub2.Subent+" isVectorCommon="+isVectorCommon+" nRowTotal="+nRowTotal);

    }

    public void setErrstatPortionTot(float proportion)
    {
	if (proportion>=0)
	ErrStatPortionOfTotIfErrStatEq0=new Float(proportion);
	else
	ErrStatPortionOfTotIfErrStatEq0=null;
    }

    public void setErrstatPortionData(float proportion)
    {
	if (proportion>=0)
	ErrStatPortionOfDataIfErrStatEq0=new Float(proportion);
	else
	ErrStatPortionOfDataIfErrStatEq0=null;
    }

    public int makeC4data2array(int iCovarFlag)
    {
	x4subent sub2;
	Vector   vReactions=null;
	x4reaction xreacode,newReacode;
	int i,nRow,nn,iRowData=0;
	int DnCol=0;

//System.out.println("......makeC4data2array():isVectorCommon="+isVectorCommon+" nVectorCommon="+nVectorCommon+" c4obj="+c4obj);
	if (c4obj==null) prepareC4obj();
//x4reaction xxrr=null;System.out.println("_________makeC4data2array():DatasetID="+xxrr.DatasetID);

	sub2=this.subent;
	this.iCovarFlag=iCovarFlag;
	xreacode=this;
	Vector vec=xreacode.vVars;
	Vector vec1=xreacode.vAllVars;
	x4var xvar;

//System.out.println("------nRowTotal="+nRowTotal);
	try {
//	    c4data=new Float[nRowTotal][10];
//2020	    c4data=new Float[nRowTotal][11];//reserved for iRowX4
	    c5data=new c5arr(nRowTotal);//2020-05-04
	    c4data_flag_inv=new boolean[nRowTotal];
	    c4data_cmetaProd=new char[nRowTotal];
	    c4data_compValueSetFromCh=new char[nRowTotal];
	    c4data_decayFlag=new Float[nRowTotal];
	    c4dataMonitEne=new Float[nRowTotal];
	    c4dataMonitVal=new Float[nRowTotal];
	    c4dataMonitErr=new Float[nRowTotal];
	}
	catch(OutOfMemoryError ee) {
	    System.err.print("\007");
	    System.err.println("!!!FATAL ERROR!!!\nException: "+ee);
	    System.exit(-1);
	    return -1;
	}


	for (i=0; i<nVectorCommon; i++) {
		if (isVectorCommon) {
		    xreacode=sub2.xbib.getReaction(i);
//System.out.println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
		    nn=sub2.prepareC4F(xreacode.cpointer);
//sub2.xcommon.printHeaders();
//sub2.xcommon.printData();
//sub2.xdata.printHeaders();
//sub2.xdata.printData();
//xreacode.printDataComput(o5prn,12);//---test
		}

//System.out.println("...---absentVars:["+xreacode.absentVarFamilyCode+"]");
		if (tryToRecoverEnresFromEn)
		if (!xreacode.absentVarFamilyCode.equals(""))
		if (xreacode.absentVarFamilyCode.indexOf("1")>=0)
		{
		    //xreacode=new x4reaction(xreacode.cpointer,xreacode.reacode,true);
		    xreacode.x4treatReacode(xreacode.cpointer,xreacode.reacode,true);
		    //nn=sub2.prepareC4F();//prepare for all pointers
		    nn=sub2.prepareC4F(xreacode.cpointer);
		}

/*
System.out.println("___1___absentVars:["+xreacode.absentVarFamilyCode+"]");
		if (tryToRecoverElemMassFromSF4)
		if (!xreacode.absentVarFamilyCode.equals(""))
		if (xreacode.absentVarFamilyCode.indexOf("7")>=0)
		{
		    xreacode.x4treatSF4asElemMass();
//		    nn=sub2.prepareC4F(xreacode.cpointer);
		}
*/

if (false)
System.out.println(" ...Reaction-"+i+" "+xreacode.reacode
+" cols="+xreacode.nCols+" known4me="+xreacode.flagKnown4me+" "+sub2.Subent);

//		sysOut_println("  +++reaction-"+(i+1)+" i0str.file="+(sub2.xbib.i0strSubent+xreacode.xcode.xkw.i0str+xreacode.xcode.i0str));
//2009.02.20	nn=sub2.prepareC4F(xreacode.cpointer);

		nn=xreacode.nCols;
		//xreacode.println();
		if (nn<=0) continue;
		DnCol=xreacode.vVars.size();

		c4obj=new x4c4obj();
		c4obj.initDataC4();
		nRow=c4obj.prepareC4Info(sub2,xreacode,isVectorCommon);
		if (nRow<=0) continue;

		c4_MF=c4obj.c4_MF;
		c4_MT=c4obj.c4_MT;
		c4_OpNum=c4obj.c4_OpNum;

		if (nRow>=0) getC4DataRow(iRowData,xreacode);
		iRowData+=xreacode.getDataLY();

//xreacode.printDataComput(sysOut,12);//---test

	}

	if (isVectorCommon)
	{
		this.cpointer=' ';
	}

//test	printC4data2array(c5data,"c5data");

	return 0;
    }
    public void printC4data2array(c5arr c5data,String title)
    {
	int ix,iy,lx,ly;
	Float rr;
	Float[][] arr0=c5data.c4data;
	String str0,str;
	if (arr0==null) return;
	ly=arr0.length;
	lx=arr0[0].length;
System.out.println("\n\n\n"+"___printC4data2array:..."+title+":"+ly+"x"+lx+" c4ene="+c4ene
+" c4dataErrTot="+c4dataErrTot);

	str="".format("$$ %3d)",0);
	for (ix=0; ix<lx; ix++) {
//	    str+="#["+padstr(""+ix,8)+"]";
	    str+=" "+padstr(""+ix,10);
	}
	System.out.println(str.trim());

	for (iy=0; iy<ly; iy++) {
	    str="".format("$$ %3d)",(iy+1));
//	    System.out.print("---"+(iy+1)+")");
	    for (ix=0; ix<lx; ix++) {
		rr=arr0[iy][ix];
//		if (rr==null) str+="  <null> ";
		if (rr==null) str+="           ";
		else str+="".format(" %10.4g",rr);
//		else str+="".format("#%10.4g",rr);
	    }
	    System.out.println(str.trim());
	}
	System.out.println("\n\n\n");
    }


    public int makeC4data(int iCovarFlag)
    {
	x4subent sub2;
	Vector   vReactions=null;
	x4reaction xreacode,newReacode;
	int i,nRow,nn,iRowData=0;
	int DnCol=0;
if (servlet_responseOutput!=null) servlet_responseOutput.println("__iCovarFlag:"+iCovarFlag+"________makeC4data__________<br>");

//	System.out.println("......makeC4data():isVectorCommon="+isVectorCommon+" nVectorCommon="+nVectorCommon);
	if (c4obj==null) prepareC4obj();

	sub2=this.subent;
	this.iCovarFlag=iCovarFlag;
	xreacode=this;
	Vector vec=xreacode.vVars;
	Vector vec1=xreacode.vAllVars;
	x4var xvar;

//	System.out.println("---!!!nRowTotal="+nRowTotal);
	try {
//2019	    c4data=new Float[nRowTotal][10];
//2020	    c4data=new Float[nRowTotal][11];//reserved for iRowX4
	    c5data=new c5arr(nRowTotal);//2020-05-04
	    c4data_flag_inv=new boolean[nRowTotal];
	    c4data_cmetaProd=new char[nRowTotal];
	    c4data_compValueSetFromCh=new char[nRowTotal];
	    c4data_decayFlag=new Float[nRowTotal];
	    c4dataMonitEne=new Float[nRowTotal];
	    c4dataMonitVal=new Float[nRowTotal];
	    c4dataMonitErr=new Float[nRowTotal];
	}
	catch(OutOfMemoryError ee) {
//	    System.err.print("\007");
//	    System.err.println("!!!FATAL ERROR!!!\nException: "+ee);
//	    System.exit(-1);
	   return -1;
	}
	if (iCovarFlag>0)
	try {
	    c4ene=new Float[nRowTotal];
	    c4dataTot=new Float[nRowTotal];
	    c4dataErrTot=new Float[nRowTotal];
	    c4dataErrStat=new Float[nRowTotal];
	    c4dataErrSys=new Float[nRowTotal];
	    c4dataErrMrc=new Float[nRowTotal];
	}
	catch(OutOfMemoryError ee) {return -1;}


	if (iCovarFlag>1)
	for (i=0; i<vec.size(); i++) {
//2012	for (i=0; i<vec1.size(); i++) {
		xvar=(x4var)vec.elementAt(i);
		if (xvar.Variable_Value==1) continue;//value, not error
		if (xvar.Variable_Number!=0) continue;//X?, not Y

//2012:todo vvvvvvvvvvvvv
/*
		xvar=(x4var)vec1.elementAt(i);
		if (i<vec.size()) {
		    if (xvar.Variable_Value==1) continue;//value, not error
		    if (xvar.Variable_Number!=0) continue;//X?, not Y
		}
		else {
		    if (xvar.Header.indexOf("ERR")<0) continue;
		}
*/

//System.out.println("......makeC4data():"+" i="+i+" Header:["+xvar.Header+"]"+" c4errorRelArray:"+nRowTotal);
if (servlet_responseOutput!=null) servlet_responseOutput.println("......makeC4data():"+" i="+i+" Header:["+xvar.Header+"]"+" c4errorRelArray:"+nRowTotal+"<br>");
		try {
		    xvar.c4errorRelArray=new Float[nRowTotal];
		}
		catch(OutOfMemoryError ee) {return -1;}
	}


	    for (i=0; i<nVectorCommon; i++) {
		if (isVectorCommon) {
		    xreacode=sub2.xbib.getReaction(i);
//System.out.println("...1..."+i+" Reaction-pointer=["+xreacode.cpointer+"]");
		    nn=sub2.prepareC4F(xreacode.cpointer);
//sub2.xcommon.printHeaders();
//sub2.xcommon.printData();
//sub2.xdata.printHeaders();
//sub2.xdata.printData();
//xreacode.printDataComput(o5prn,12);//---test
		}

//System.out.println("...---absentVars:["+xreacode.absentVarFamilyCode+"]");
		if (tryToRecoverEnresFromEn)
		if (!xreacode.absentVarFamilyCode.equals(""))
		if (xreacode.absentVarFamilyCode.indexOf("1")>=0)
		{
		    //xreacode=new x4reaction(xreacode.cpointer,xreacode.reacode,true);
		    xreacode.x4treatReacode(xreacode.cpointer,xreacode.reacode,true);
		    //nn=sub2.prepareC4F();//prepare for all pointers
		    nn=sub2.prepareC4F(xreacode.cpointer);
		}

/*
System.out.println("___2___absentVars:["+xreacode.absentVarFamilyCode+"]");
		if (tryToRecoverElemMassFromSF4)
		if (!xreacode.absentVarFamilyCode.equals(""))
		if (xreacode.absentVarFamilyCode.indexOf("7")>=0)
		{
		    xreacode.x4treatSF4asElemMass();
//		    nn=sub2.prepareC4F(xreacode.cpointer);
		}
*/

if (false)
System.out.println(" ...Reaction-"+i+" "+xreacode.reacode
+" cols="+xreacode.nCols+" known4me="+xreacode.flagKnown4me+" "+sub2.Subent);

//		sysOut_println("  +++reaction-"+(i+1)+" i0str.file="+(sub2.xbib.i0strSubent+xreacode.xcode.xkw.i0str+xreacode.xcode.i0str));
//2009.02.20	nn=sub2.prepareC4F(xreacode.cpointer);

		nn=xreacode.nCols;
		//xreacode.println();
		if (nn<=0) continue;
		DnCol=xreacode.vVars.size();

		c4obj=new x4c4obj();
		c4obj.initDataC4();
		nRow=c4obj.prepareC4Info(sub2,xreacode,isVectorCommon);
		if (nRow<=0) continue;

		c4_MF=c4obj.c4_MF;
		c4_MT=c4obj.c4_MT;
		c4_OpNum=c4obj.c4_OpNum;

		if (nRow>=0) getC4DataRow(iRowData,xreacode);
		iRowData+=xreacode.getDataLY();

//xreacode.printDataComput(sysOut,12);//---test

	    }

	    if (isVectorCommon)
	    {
		this.cpointer=' ';
	    }

	return 0;
    }

boolean renormalize=false;

    public int makeC4data2(int iCovarFlag)
    {
	x4subent sub2;
	Vector vReactions=null;
	x4reaction xreacode,newReacode;
	int i,iRow,nn;
	int DnCol,DnRow;
	Float[] c4arr;
	Float ErrTot,ErrStat,ErrSys,ErrMrc,DatTot,ErrTotBeforeCorr;
	float errTot,errStat,errSys,errMrc;
	boolean myDebug1=false;
	//myDebug1=true;

renormalize=true;

	if (myDebug1) 
	System.out.println("\n_________makeC4data2():c5data="+c5data+" "+nRowTotal);
	if (c4obj==null) prepareC4obj();
//if (true) System.exit(0);
//x4reaction xxrr=null;System.out.println("_________makeC4data2():DatasetID="+xxrr.DatasetID);

	sub2=this.subent;
	this.iCovarFlag=iCovarFlag;
	xreacode=this;
	Vector vec=xreacode.vVars;
	Vector vec1=xreacode.vAllVars;
	x4var xvar;

//	if (myDebug1) System.out.println("---...---!!!nRowTotal="+nRowTotal);
//	System.out.println("---!!!nRowTotal="+nRowTotal);
//exist	c4data=new Float[nRowTotal][11];

	if (iCovarFlag>0)
	try {
	    c4ene=new Float[nRowTotal];
	    c4dataTot=new Float[nRowTotal];
	    c4dataErrTot=new Float[nRowTotal];
	    c4dataErrStat=new Float[nRowTotal];
	    c4dataErrSys=new Float[nRowTotal];
	    c4dataErrMrc=new Float[nRowTotal];
	    c4dataFc=new Float[nRowTotal];
	    c4dataFcErr=new Float[nRowTotal];
	}
	catch(OutOfMemoryError ee) {return -1;}

	if (c5data==null) return -4;
	if (c5data.c4data==null) return -4;
	DnRow=c5data.c4data.length;
	if (DnRow<=0) return -1;
	if (c5data.c4data[0]==null) return -4;
	DnCol=c5data.c4data[0].length;
	if (DnCol<=0) return -1;

if (extDebug||myDebug1)
System.out.println("_________xreac.BasicUnits=["+xreacode.BasicUnits+"]"
+"\t...DnRow="+DnRow+" DnCol="+DnCol);


	boolean outFcPercent=false;
	boolean outdDataPercent=false;
	boolean outdSysPercent=false;
//	c4arr=new Float[10];
	c4arr=new Float[c5arr.LXDATA];
	for (iRow=0; iRow<DnRow; iRow++) {

	    c4arr=c4obj.getDataArray();
	    for (i=0; i<c5arr.LXDATA; i++) c4arr[i]=c5data.c4data[iRow][i];
	    Float Fc=null;
	    Float FcErr=null;
	    Float dYRM0=null;
	    Float dYRM1=null;
	    Float dYRM2=null;

	    if (iCovarFlag>0) {
		c4ene[iRow]=c4arr[0];
		c4dataTot[iRow]=c4arr[2];
		DatTot=c4arr[2];
		if (DatTot==null) continue;
		ErrTot=c4arr[3];
		ErrStat=c4arr[c5arr.indStat];
		ErrSys=c4arr[c5arr.indSys];
		ErrMrc=c4arr[c5arr.indMrc];

		if (ErrTot!=null)	ErrTot=new Float(ErrTot/DatTot);
		if (ErrStat!=null)	ErrStat=new Float(ErrStat/DatTot);
		if (ErrSys!=null)	ErrSys=new Float(ErrSys/DatTot);
		if (ErrMrc!=null)	ErrMrc=new Float(ErrMrc/DatTot);
		ErrTotBeforeCorr=ErrTot;

if (extDebug)
if (extDebug||myDebug1)
System.out.println("---iRow:"+iRow+" Ene="+c4arr[0]+" dEne="+c4arr[1]+" Data="+DatTot
+" ErrTot:"+(ErrTot==null?"null":"".format("%.2f%%",ErrTot*100))
+" ErrSta:"+(ErrStat==null?"null":"".format("%.2f%%",ErrStat*100))
+" ErrSys:"+(ErrSys==null?"null":"".format("%.2f%%",ErrSys*100))
+" ErrMrc:"+(ErrMrc==null?"null":"".format("%.2f%%",ErrMrc*100))
//+" [1]="+c4arr[1]
+(c4arr[3]==null ? "":" [3]:"+c4arr[3])
+(c4arr[4]==null ? "":" [4]:"+c4arr[4])
+(c4arr[5]==null ? "":" [5]:"+c4arr[5])
+(c4arr[6]==null ? "":" [6]:"+c4arr[6])
+(c4arr[7]==null ? "":" [7]:"+c4arr[7])
+" c4arr[c5arr.indTot]="+c4arr[c5arr.indTot]
//+(c4arr[c5arr.indTot]==null ? "":" [c5arr.indTot]:"+c4arr[c5arr.indTot])
//+" met:["+c4data_cmetaProd[iRow]+"]"
//+" decay:["+c4data_decayFlag[iRow]+"]"
+" mon:"+(c4dataMonitVal==null?"":""+c4dataMonitVal[iRow])
+" dmon:"+(c4dataMonitErr==null?"":""+c4dataMonitErr[iRow])
);



		c4dataErrTot[iRow]=ErrTot;
		c4dataErrStat[iRow]=ErrStat;
		c4dataErrSys[iRow]=ErrSys;
		c4dataErrMrc[iRow]=ErrMrc;
//?		c4dataMonitVal[iRow]=monitVal;
		c4dataFc[iRow]=Fc;
//??		if (c5data.c4data[iRow][3]>0)
//??		if (err_before>0)
//??		c4dataFcErr[iRow]=new Float(c5data.c4data[iRow][3]/err_before);
		c4dataFcErr[iRow]=FcErr;

if (extDebug)
System.out.println("---iRow:"+iRow+" Ene="+c4arr[0]+" Data="+DatTot+" ErrTotBeforeCorr="+ErrTotBeforeCorr+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys);
if (extDebug||myDebug1)
System.out.println("___makeC4data2::"
+" YY[2]="+c5data.c4data[iRow][2]
+" dY[3]="+c5data.c4data[iRow][3]
//+" err_before="+err_before
//+" dY[3]/err_before="+(c5data.c4data[iRow][3]/err_before)
+" dY[indSys]="+c5data.c4data[iRow][c5arr.indSys]
+" dY[indTot]="+c5data.c4data[iRow][c5arr.indTot]
+" dYRM0="+dYRM0
+" dYRM1="+dYRM1
+" dYRM2="+dYRM2
);

	    }
	}
	return 0;
    }

    public String addedFcPercent(Float RRR) {
	String str;
	if (RRR<1) str="-"+"".format("%.3g",100-RRR*100);
	else str="+"+"".format("%.3g",RRR*100-100);
	return str+"%";
    }
    public Float addErr2(Float Err1,Float Err2) {
	if ((Err1==null)&&(Err2==null)) return null;
	if (Err1==null) return Err2;
	if (Err2==null) return Err1;
	Float rr=new Float(Math.sqrt(Err1*Err1+Err2*Err2));
	return rr;
    }

    public Float modifErrByMon(Float Err,Float errM0,Float errM1) {
	if (Err==null) return Err;
	if (errM0==null) return Err;
	if (errM1==null) return Err;
	double err2=(double)(Err*Err);
	double err2M0=(double)(errM0*errM0);
	double err2M1=(double)(errM1*errM1);
	double diff=err2-err2M0+err2M1;
	if (diff<0) return Err;
	Float rr=new Float(Math.sqrt(diff));
	return rr;
    }

    public String getHeaders() {
	x4reaction xreacode=this;
	Vector vec=xreacode.vVars;
	Vector vec1=xreacode.vAllVars;
	x4var xvar;
	String str="";
	int i;
	for (i=0; i<vec1.size(); i++) {
//2012		xvar=(x4var)vec.elementAt(i);
//2012		if (xvar.Variable_Value==1) continue;//value, not error
//2012		if (xvar.Variable_Number!=0) continue;//X?, not Y
		xvar=(x4var)vec1.elementAt(i);
		str+=i+":"+xvar.Variable_Value+":"+xvar.Variable_Number+":"+"["+xvar.Header+"]";
		if (i<vec.size()) {
		    if (xvar.Variable_Value==1) continue;//value, not error
		    if (xvar.Variable_Number!=0) continue;//X?, not Y
		}
		else {
		    if (xvar.Header.indexOf("ERR")<0) continue;
		}
	}
//	xvar.c4errorRelArray
	return str;
    }

PrintWriter servlet_responseOutput=null;
    public int getC4DataRow(int iRowData0,x4reaction xreacode)
    {
	String str,str1,strFamFlag;
	int iRow,iCol,i,nn,nRow,ii,nn2,nn3;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
	x4reacstr xreacstr;
	String c4str;
	Float[] c4arr;
	Float ErrTot,ErrStat,ErrSys,ErrMrc,DatTot;
	float errTot,errStat,errSys,errMrc;
if (servlet_responseOutput!=null) servlet_responseOutput.println("__________getC4DataRow__________<br>");

//	if (c4_MT==0) return -1;

//++	if ((c4_MF==0)||(c4_MT==0)) return -2;//run1

	if (xreacode.IndepVarFamilyCode.trim().equals("?")) return -3;

	Vector vec=xreacode.vVars;
	Float rr;
	DnCol=vec.size();
	x4var xvar;

	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}

//x	DnRow=xreacode.getDataLY();//2021-10-04
	int DnRow1=xreacode.getDataLY();//20211004
//System.out.println("___1___getC4DataRow:"+" iRowData0="+iRowData0+" DnRow="+DnRow+" DnRow1="+DnRow1+" iCovarFlag="+iCovarFlag);

//	if (DnRow<=0) return -1;

	for (iRow=0,nRow=0; (iRow<DnRow)&&(nRow<DnRow1); iRow++) {
	    ii=c4obj.makeC4Line(iRow,nRow);
//System.out.println("...getC4DataRow:iRow:"+iRow+"/"+DnRow+",nRow:"+nRow+" makeC4Line:"+ii);
if (servlet_responseOutput!=null) servlet_responseOutput.println("___1___getC4DataRow:iRow:"+iRow+"/"+DnRow+",nRow:"+nRow+" makeC4Line:"+ii+"<br>");
	    if (ii<0) continue;
	    c4str=c4obj.c4str;
	    c4arr=c4obj.getDataArray();
	    c4data_cmetaProd[iRowData0+nRow]=c4obj.c4_ProdM;//2018-12-19 ISOMER
	    c4data_decayFlag[iRowData0+nRow]=c4obj.c4_decayFlag;
	    c4data_compValueSetFromCh[iRowData0+nRow]=c4obj.c4_compValueSetFromCh;//2023-04-13 DATA-MAX
//System.out.println("___1___c4data_cmetaProd["+(iRowData0+nRow)+"] c4data_cmetaProd="+c4data_cmetaProd[iRowData0+nRow]);
//System.out.println("___1___c4data_compValueSetFromCh["+(iRowData0+nRow)+"] c4data_compValueSetFromCh="+ c4data_compValueSetFromCh[iRowData0+nRow]);

	    c4dataMonitEne[iRowData0+nRow]=c4arr[0];
	    c4dataMonitVal[iRowData0+nRow]=c4obj.monitVal;
	    c4dataMonitErr[iRowData0+nRow]=c4obj.monitErr;
//System.out.println("___1___c4dataMonit["+(iRowData0+nRow)+"] c4dataMonit:"+"E=["+c4dataMonitEne[iRowData0+nRow]+" M0=["+c4dataMonitVal[iRowData0+nRow]+"]"+" dM0=["+c4dataMonitErr[iRowData0+nRow]+"]");

//2020	    for (i=0; i<10; i++) c4data[iRowData0+nRow][i]=c4arr[i];
	    for (i=0; i<c4arr.length; i++) c5data.c4data[iRowData0+nRow][i]=c4arr[i];

/*2020
	    if (c4data[iRowData0+nRow].length>10) {
		if (c4obj.iRowX4>0) {
		    c4data[iRowData0+nRow][10]=new Float(c4obj.iRowX4);
//System.out.println("...iRowX4["+(iRowData0+nRow)+"] iRowX4="+c4data[iRowData0+nRow][10]);
		}
		else
		c4data[iRowData0+nRow][10]=null;
	    }
*/
	    c5data.getX4iRow(iRowData0+nRow,c4obj.iRowX4);

	    if (iCovarFlag>0) {
		c4ene[iRowData0+nRow]=c4arr[0];
		c4dataTot[iRowData0+nRow]=c4arr[2];
		DatTot=c4arr[2];
		if (DatTot==null) continue;
		ErrTot=c4arr[3];
		ErrStat=c4arr[c5arr.indStat];
		ErrSys=c4arr[c5arr.indSys];
		ErrMrc=c4arr[c5arr.indMrc];

//		if (ErrTot!=null)	ErrTot=new Float(ErrTot/DatTot*100);
//		if (ErrStat!=null)	ErrStat=new Float(ErrStat/DatTot*100);
//		if (ErrSys!=null)	ErrSys=new Float(ErrSys/DatTot*100);
		if (ErrTot!=null)	ErrTot=new Float(ErrTot/DatTot);
		if (ErrStat!=null)	ErrStat=new Float(ErrStat/DatTot);
		if (ErrSys!=null)	ErrSys=new Float(ErrSys/DatTot);
		if (ErrMrc!=null)	ErrMrc=new Float(ErrMrc/DatTot);
if (extDebug)
System.out.println("---"+iRow+" Ene="+c4arr[0]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys+" ErrMrc="+ErrMrc);

		c4dataErrTot[iRowData0+nRow]=ErrTot;
		c4dataErrStat[iRowData0+nRow]=ErrStat;
		c4dataErrSys[iRowData0+nRow]=ErrSys;
		c4dataErrMrc[iRowData0+nRow]=ErrMrc;
if (extDebug)
System.out.println("---"+iRow+" Ene="+c4arr[0]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys+" ErrMrc="+ErrMrc);
	    }
if (servlet_responseOutput!=null) servlet_responseOutput.println("___2___getC4DataRow:iRow:"+iRow+"/"+DnRow+",nRow:"+nRow+" vec.size()="+vec.size()+"<br>");
	    for (i=0; i<vec.size(); i++) {
		xvar=(x4var)vec.elementAt(i);
		if (xvar.Variable_Value==1) continue;//value, not error
		if (xvar.Variable_Number!=0) continue;//X?, not Y
//if (false)
if (extDebug)
		System.out.println("_getC4DataRow_"+iRow+"."+i+")"
//			+"---Num="+xvar.Variable_Number
//			+" Val="+xvar.Variable_Value
			+"\tTyp="+xvar.DataType
			+":"+xvar.PlottingFlags1
			+" "+xvar.Header
//			+"==>"+xvar.numDataType1
			+" "+xvar.what
			+" nowVal="+xvar.nowValue
			+" c4errVal="+xvar.c4errorValue
			+" c4errPercent="+xvar.c4errorPercent
			);
if (servlet_responseOutput!=null)
		servlet_responseOutput.println("_getC4DataRow_"+iRow+"."+i+")"
//			+"---Num="+xvar.Variable_Number
//			+" Val="+xvar.Variable_Value
			+"\tTyp="+xvar.DataType
			+":"+xvar.PlottingFlags1
			+" "+xvar.Header
//			+"==>"+xvar.numDataType1
			+" "+xvar.what
			+" nowVal="+xvar.nowValue
			+" c4errVal="+xvar.c4errorValue
			+" c4errPercent="+xvar.c4errorPercent
			+" xvarInPercent="+xvar.xvarInPercent
			+((xvar.xvarInPercent==null)?"":" xvarInPercent="+xvar.xvarInPercent.nowValue)
			+"<br>"
			);

		if (xvar.c4errorRelArray!=null)
		{
		    if (xvar.c4errorPercent!=null)
		    xvar.c4errorRelArray[iRowData0+nRow]=new Float(xvar.c4errorPercent.floatValue()/100.f);
		    else {
			if (xvar.xvarInPercent!=null)
			if (xvar.xvarInPercent.nowValue!=null)
			xvar.c4errorRelArray[iRowData0+nRow]=new Float(xvar.xvarInPercent.nowValue.floatValue()/100.f);
		    }
		}
	    }
	    nRow++;
	}
	return nRow;
    }

    public void array2percent(Float[] arr0,Float[] arr1,float percent)
    {
	int ii,ll,ll1;
	Float rr;
	double rr2;
	if (arr0==null) return;
	if (arr1==null) return;
	ll=arr0.length;
	ll1=arr0.length;
	for (ii=0; (ii<ll)&&(ii<ll1); ii++) {
	    rr=arr0[ii];
	    if (rr==null) continue;
//	    arr1[ii]=new Float(rr*percent/100f);
	    rr2=(rr*rr)*percent/100f;
	    rr2=Math.sqrt(rr2);
	    arr1[ii]=new Float(rr2);
	}
    }

    public void array2percent(Float[] arr1,float percent)
    {
	int ii,ll;
	ll=arr1.length;
	if (arr1==null) return;
	for (ii=0; ii<ll; ii++) {
	    arr1[ii]=new Float(percent/100f);
	}
    }

    public void array2linear(Float[] arr1)
    {
	int ii,ii0,ii1,ll,iii;
	Float rr,rr0=null,rr1=null;
	float dx,dy,x0,y0,x1,y1,xx,yy;
	ll=arr1.length;
	if (arr1==null) return;
	rr0=arr1[0]; ii0=0;
	rr1=null; ii1=ll;
	for (ii=0; ii<ll; ii++) {
	    rr=arr1[ii];
	    if (rr==null) continue;
	    rr1=rr; ii1=ii;
	    if ((rr0==null)&&(ii>0)) {
		for (iii=0; iii<ii; iii++) arr1[iii]=new Float(rr);
		rr0=rr; ii0=ii;
		continue;
	    }
	    if ((rr0!=null)&&(ii>ii0+1)) {
		x0=c4ene[ii0];	x1=c4ene[ii];	dx=x1-x0;
		y0=arr1[ii0].floatValue();
		y1=arr1[ii].floatValue();
		dy=y1-y0;
		if (dx==0)
		for (iii=ii0+1; iii<ii; iii++) {
		    arr1[iii]=new Float(y0);
		}
		else
		for (iii=ii0+1; iii<ii; iii++) {
		    xx=c4ene[iii]-x0;
		    if (xx==0) yy=y0;
		    else
		    yy=y0+dy/dx*xx;
		    arr1[iii]=new Float(yy);
		}
		rr0=rr; ii0=ii;
		continue;
	    }
	}
	for (iii=ii1+1; iii<ll; iii++) arr1[iii]=new Float(rr1);
    }

    public void array2null(Float[] arr1)
    {
	int ii,ll;
	if (arr1==null) return;
	ll=arr1.length;
	for (ii=0; ii<ll; ii++) {
	    arr1[ii]=null;
	}
    }

    public Float[] getX4ErrArray(String hdr) {
	int i,ii,jj,lx,ly,nn;
	String str;
	Vector vec=this.vVars;
	x4var xvar;
//	System.out.println("---getX4ErrArray:hdr=["+hdr+"]");
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    if (xvar.c4errorRelArray==null) continue;
	    str=xvar.Header;
	    if (hdr.equals(str)) return xvar.c4errorRelArray;
	}
	return null;
    }


    boolean flgErrTot=false;
    boolean flgErrStat=false;
    boolean flgErrSys=false;
    boolean flgErrMrc=false;
    public void checkC5err()
    {
	Float ErrTot,ErrStat,ErrSys,DatTot,ErrMrc;
	int iRow,nRow;
	int DnRow=0,DnCol;
	flgErrTot =false;
	flgErrStat=false;
	flgErrSys =false;
	flgErrMrc =false;
	if (c4ene        ==null) return;
	if (c4dataTot    ==null) return;
	if (c4dataErrTot ==null) return;
	if (c4dataErrStat==null) return;
	if (c4dataErrSys ==null) return;
	if (c4dataErrMrc ==null) return;
	DnRow=c4ene.length;
	for (iRow=0; iRow<DnRow; iRow++) {
	    DatTot=c4dataTot[iRow];
	    if (DatTot==null) continue;
	    ErrTot =c4dataErrTot [iRow];
	    ErrStat=c4dataErrStat[iRow];
	    ErrSys =c4dataErrSys [iRow];
	    ErrMrc =c4dataErrMrc [iRow];
	    if (ErrTot !=null) flgErrTot =true;
	    if (ErrStat!=null) flgErrStat=true;
	    if (ErrSys !=null) flgErrSys =true;
	    if (ErrMrc !=null) flgErrMrc =true;
	}
    }
    public void array2sqrtMinus(Float[] arr0,Float[] arr1,Float[] arr2)
    {
	int ii,ll0,ll1,ll2;
	Float rr0,rr1,rr2;
	if (arr0==null) return;
	if (arr1==null) return;
	if (arr2==null) return;
	ll0=arr0.length;
	ll1=arr1.length;
	ll2=arr2.length;
	for (ii=0; (ii<ll0)&&(ii<ll1)&&(ii<ll1); ii++) {
	    rr0=arr0[ii];
	    if (rr0==null) continue;
	    rr1=arr1[ii];
	    if (rr1==null) {
		arr2[ii]=new Float(rr0);
		continue;
	    }
	    if (rr0>rr1) {
		arr2[ii]=new Float(Math.sqrt(Math.pow(rr0,2)-Math.pow(rr1,2)));
	    }
	    else {
		arr2[ii]=new Float(0);
	    }
	}
    }
    public void setErrArrayValues(Float[] arr,Float ff)
    {
	int ii,ll;
	Float rr;
	if (arr==null) return;
	ll=arr.length;
	for (ii=0; ii<ll; ii++) {
	    if (ff==null) arr[ii]=null;
	    else arr[ii]=new Float(ff);
	}
    }
    public void interpolateErrArray(Float[] ene,Float[] arr,boolean extrapolate)
    {
	int i,ii,ii0,ii1,ll,ll1,nn;
	Float rr,rr0=null,rr1=null,rr2=null;
	float ee0,ee1,de0,de;
	if (ene==null) return;
	if (arr==null) return;
	ll=ene.length;
	ll1=arr.length;
	if (ll!=ll1) return;
	for (ii=0,nn=0; ii<ll; ii++) if (arr[ii]!=null) nn++;
	if (ll==nn) return;
	if (nn==0) return;

	//extrapolate 1st value
	if (extrapolate)
	for (ii=0; ii<ll; ii++) {
	    rr0=arr[ii];
	    if (rr0!=null) {
		for (i=0; i<ii; i++) arr[i]=new Float(rr0);
		break;
	    }
	}
//if (true) return;

	//extrapolate last value
	if (extrapolate)
	for (ii=ll-1; ii>=0; ii--) {
	    rr0=arr[ii];
	    if (rr0!=null) {
		for (i=ll-1; i>ii; i--) arr[i]=new Float(rr0);
		break;
	    }
	}
//if (true) return;

	for (ii0=0; ii0<ll; ii0++) if (arr[ii0]!=null) break;
	for (nn=ll-1; nn>=0; nn--) if (arr[nn]!=null) break;
	for (ii=ii0; ii<=nn; ) {
	    if (arr[ii]!=null) {ii0=ii;rr0=arr[ii0];ii++;continue;}
	    for (ii1=ii; ii1<=nn; ii1++) {
		rr1=arr[ii1];
		if (rr1!=null) break;
	    }
	    ee0=ene[ii0];
	    ee1=ene[ii1];
	    de0=ee1-ee0;
	    if (de0==0) {ii=ii1+1;continue;}
//	    sysOut_println("___0___"+" ii0="+ii0+" ii1="+ii1+" ee0="+ee0+" ee1="+ee1+" rr0="+rr0+" rr1="+rr1+"<br>");
	    for (i=ii0+1; i<ii1; i++) {
		de=ene[i]-ee0;
		rr=rr0+de/de0*(rr1-rr0);
		arr[i]=new Float(rr);
	    }
	    ii=ii1;
	}
    }
    public int fillC4err()
    {
	Float ErrTot,ErrStat,ErrSys,DatTot,ErrMrc=null;
	float errTot,errStat,errSys,errMrc;
	String str,str1;
	int iRow,iCol,i,nn,nRow,ii;
	int DnRow=0,DnCol;
	Float rr;
	if (c4ene        ==null) return -1;
	if (c4dataTot    ==null) return -1;
	if (c4dataErrTot ==null) return -1;
	if (c4dataErrStat==null) return -1;
	if (c4dataErrSys ==null) return -1;
	DnRow=c4ene.length;
	for (iRow=0; iRow<DnRow; iRow++) {
	    if (iCovarFlag>0) {
		DatTot=c4dataTot[iRow];
		if (DatTot==null) continue;
		ErrTot=c4dataErrTot  [iRow];
		ErrStat=c4dataErrStat[iRow];
		ErrSys=c4dataErrSys  [iRow];
		if (c4dataErrMrc!=null) ErrMrc=c4dataErrMrc[iRow];
if (extDebug)
System.out.println("+++"+iRow+" Ene="+c4ene[iRow]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys);
//sysOut_println("+++0+"+iRow+" Ene="+c4ene[iRow]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys+"<br>");

		//zaglushka-2020-05-15: add ErrMrc to ErrSys
		if (ErrMrc!=null) {
		    if (ErrSys==null) ErrSys=new Float(ErrMrc);
		    else ErrSys=new Float(Math.sqrt(Math.abs(Math.pow(ErrSys,2)+Math.pow(ErrMrc,2))));
		}

		if (ErrTot!=null)
		if ((ErrSys==null)&&(ErrStat==null)) {
		    if (ErrStatPortionOfDataIfErrStatEq0!=null) ErrStat=new Float(ErrStatPortionOfDataIfErrStatEq0);
		    else
		    if (ErrStatPortionOfTotIfErrStatEq0!=null)
		    ErrStat=new Float(Math.sqrt(Math.abs(Math.pow(ErrTot,2)*ErrStatPortionOfTotIfErrStatEq0)));
		}
//sysOut_println("---1-"+iRow+" Ene="+c4ene[iRow]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys+"<br>");

		if (ErrSys==null)
		if ((ErrTot!=null)&&(ErrStat!=null)) 
		if (ErrTot>=ErrStat) 
		ErrSys=new Float(Math.sqrt(Math.abs(Math.pow(ErrTot,2)-Math.pow(ErrStat,2))));

		if (ErrStat==null)
		if ((ErrTot!=null)&&(ErrSys!=null)) 
		if (ErrTot>=ErrSys) 
		ErrStat=new Float(Math.sqrt(Math.abs(Math.pow(ErrTot,2)-Math.pow(ErrSys,2))));

		if (ErrTot==null)
		if ((ErrSys!=null)&&(ErrStat!=null)) 
		ErrTot=new Float(Math.sqrt(Math.abs(Math.pow(ErrSys,2)+Math.pow(ErrStat,2))));

		//zaglushka-2020-05-15: sub ErrMrc from ErrSys
		if (ErrMrc!=null) {
		    if (ErrSys!=null)
		    if (ErrSys>=ErrMrc) 
		    ErrSys=new Float(Math.sqrt(Math.abs(Math.pow(ErrSys,2)-Math.pow(ErrMrc,2))));
		    else ErrSys=new Float(0);
		}


		c4dataErrTot[iRow]=ErrTot;
		c4dataErrStat[iRow]=ErrStat;
		c4dataErrSys[iRow]=ErrSys;
if (extDebug)
System.out.println("+++"+iRow+" Ene="+c4ene[iRow]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys);
//sysOut_println("+++1+"+iRow+" Ene="+c4ene[iRow]+" Data="+DatTot+" ErrTot="+ErrTot+" ErrStat="+ErrStat+" ErrSys="+ErrSys+"<br>");
	    }

	}
	return 0;
    }

    public int printC4data(PrintWriter prnOut,int ltab)
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow,ii;
	int DnRow=0,DnCol;
	Float rr;
	if (c5data==null) return -1;
	if (c5data.c4data==null) return -1;
	DnRow=c5data.c4data.length;
	DnCol=c5data.c4data[0].length;
	prnOut.println("#...printC4data:");
	prnOut.println("#c4array "+DnRow+" "+DnCol+" MF:"+c4_MF+" MT:"+c4_MT+" Op:"+c4_OpNum+" if:"+c4obj.c4_ifound);
	for (iRow=0; iRow<DnRow; iRow++) {
	    str1=padstr((iRow+1)+".",ltab);
	    for (iCol=0; iCol<DnCol; iCol++) {
		rr=c5data.c4data[iRow][iCol];
		if (rr==null) str=strpad("",ltab);
		else str=strpad(double2str(rr),ltab);
//		str=iCol+":"+str;
		str1=str1+" "+str;
	    }
	    prnOut.println(str1);
	}
	prnOut.println("#/c4array");
	return 0;
    }

    public int printC4err(PrintWriter prnOut,int ltab)
    {
	String str,str1;
	int iRow,iCol,i,nn,nRow,ii;
	int DnRow=0,DnCol;
	Float rr;
	if (c4ene        ==null) return -1;
	if (c4dataErrTot ==null) return -1;
	if (c4dataErrStat==null) return -1;
	if (c4dataErrSys ==null) return -1;
	DnRow=c4ene.length;
	prnOut.println("#...printC4err:");
	prnOut.println("#c4err "+DnRow);
	str1=padstr("#i ",ltab);
	str1+=" "+strpad("Energy" ,ltab);
	str1+=" "+strpad("DataTot",ltab);
	str1+=" "+strpad("ErrTot,%" ,ltab);
	str1+=" "+strpad("ErrStat,%",ltab);
	str1+=" "+strpad("ErrSys,%" ,ltab);
	prnOut.println(str1);
	for (iRow=0; iRow<DnRow; iRow++) {
	    str1=padstr((iRow+1)+".",ltab);
	    rr=c4ene        [iRow];if (rr==null) str=strpad("",ltab);else str=strpad(double2str(rr),ltab);str1=str1+" "+str;
	    rr=c4dataTot    [iRow];if (rr==null) str=strpad("",ltab);else str=strpad(double2str(rr),ltab);str1=str1+" "+str;
	    rr=c4dataErrTot [iRow];if (rr==null) str=strpad("",ltab);else str=strpad(double2str(rr*100f),ltab);str1=str1+" "+str;
	    rr=c4dataErrStat[iRow];if (rr==null) str=strpad("",ltab);else str=strpad(double2str(rr*100f),ltab);str1=str1+" "+str;
	    rr=c4dataErrSys [iRow];if (rr==null) str=strpad("",ltab);else str=strpad(double2str(rr*100f),ltab);str1=str1+" "+str;
	    prnOut.println(str1);
	}
	prnOut.println("#/c4err");
	return 0;
    }



    public int makeCovarMatrix1()
    {
	String str,str1;
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	if (c4ene        ==null) return -1;
	if (c4dataErrTot ==null) return -1;
	if (c4dataErrStat==null) return -1;
	if (c4dataErrSys ==null) return -1;
	lx=c4ene.length;
	try {c4covar=new float[lx][lx];}
	catch(OutOfMemoryError ee) {return -1;}

	//---cleaning
	for (jj=0; jj<lx; jj++) for (ii=0; ii<lx; ii++) c4covar[jj][ii]=0;

	//---uncorrelated
	for (jj=0; jj<lx; jj++) {
	    if (c4dataErrStat[jj]==null) continue;
	    c4covar[jj][jj]+=Math.pow(c4dataErrStat[jj],2);
	}

	//---fully-correlated
	for (jj=0; jj<lx; jj++) {
	    if (c4dataErrSys[jj]==null) continue;
	    for (ii=0; ii<lx; ii++) {
		if (c4dataErrSys[ii]==null) continue;
c4covar[jj][ii]+=c4dataErrSys[jj].floatValue()*c4dataErrSys[ii].floatValue();
	    }
	}
	return 0;
    }




    public int compressCovarData(int koeff)
    {
	String str,str1;
	int ii,jj,i,nn,lx,lxnew;
	Float rr;
	if (c4ene==null) return -1;
	lx=c4ene.length;
	lxnew=(lx+koeff-1)/koeff;
	try {c4dataErrTot=new Float[lxnew];}
	catch(OutOfMemoryError ee) {return -1;}
//	c4dataErrTot=compressCovarErr(koeff,c4ene,c4dataErrTot );
//++PrintWriter sysOut1=sysOut;sysOut=null;
	c4dataErrStat=compressCovarErr(koeff,c4ene,c4dataErrStat,false,true);
//++sysOut=null;
	c4dataErrSys=compressCovarErr(koeff,c4ene,c4dataErrSys,false,false);
	c4dataErrMrc=compressCovarErr(koeff,c4ene,c4dataErrMrc,false,false);
	for (i=0; i<lxnew; i++) {
	    c4dataErrTot[i]=null;
	    if ((c4dataErrStat[i]==null)&&(c4dataErrSys[i]==null)) continue;
	    if (c4dataErrSys[i]==null) c4dataErrTot[i]=new Float(c4dataErrStat[i]);
	    else
	    if (c4dataErrStat[i]==null) c4dataErrTot[i]=new Float(c4dataErrSys[i]);
	    else
	    if ((c4dataErrStat[i]!=null)&&(c4dataErrSys[i]!=null))
	    c4dataErrTot[i]=new Float(Math.sqrt(Math.abs(Math.pow(c4dataErrSys[i],2)+Math.pow(c4dataErrStat[i],2))));
	}

	c4dataTot=compressCovarErr(koeff,c4ene,c4dataTot,true,false);

	Vector vec=vVars;
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    if (xvar.c4errorRelArray==null) continue;
	    if (xvar.whatVar.indexOf("5")>0) //sys
		xvar.c4errorRelArray=compressCovarErr(koeff,c4ene,xvar.c4errorRelArray,false,false);
	    else
	    if (xvar.whatVar.indexOf("4")>0) //stat
		xvar.c4errorRelArray=compressCovarErr(koeff,c4ene,xvar.c4errorRelArray,false,true);
	    else
	    //?total
		xvar.c4errorRelArray=compressCovarErr(koeff,c4ene,xvar.c4errorRelArray,true,false);
	}

//++sysOut=sysOut1;
//2011	c4ene =compressCovarErr(koeff,c4ene,c4ene ,true ,false);
	c4ene =compressCovarEne(koeff,c4ene,c4ene);
//++sysOut=null;
	prepareCovarEne(c4ene);

	return 0;
    }

    public Float[] compressCovarErr(int koeff,Float[] arrEne,Float[] arrErr
	,boolean isData,boolean isErrStat)
    {
	String str,str1;
	int ii,jj,i,nn,lx,kk,i0,lxnew;
	Float rr,rr1;
	Float[] arrOut;
	if (arrEne==null) return null;
	lx=c4ene.length;
	lxnew=(lx+koeff-1)/koeff;
	try {arrOut=new Float[lxnew];}
	catch(OutOfMemoryError ee) {return null;}
	for (i=0; i<lx; i+=koeff) {
	    rr=null;
	    kk=0;
	    for (ii=0; (ii<koeff)&&(i+ii<lx); ii++) {
		rr1=arrErr[i+ii];
		if (rr1==null) continue;
		rr1=new Float(rr1);
		if (!isData)	rr1=rr1*rr1;
		if (rr==null)	rr=rr1;
		else		rr+=rr1;
		kk++;
	    }
	    if (kk>0) {
//++sysOut_println("...ii="+ii+" rr="+rr.floatValue()+" kk="+kk+" isData="+isData+" isErrStat="+isErrStat+"<br>");
		rr=rr/kk;
		if (isErrStat)	rr=rr/kk;
		if (!isData)	rr=new Float(Math.sqrt(rr));
	    }
	    i0=i/koeff;
	    arrOut[i0]=rr;
	}
	return arrOut;
    }

//    float c4eneLastValue=0;
//    c4eneLastValue=arrEne0[lx-1];
    public Float[] compressCovarEne(int koeff,Float[] arrEne,Float[] arrEne0)
    {
	String str,str1;
	int ii,jj,i,nn,lx,kk,i0,lxnew;
	Float rr,rr1;
	Float[] arrOut;
//	c4eneLastValue=0;
	if (arrEne==null) return null;
	lx=c4ene.length;
	lxnew=(lx+koeff-1)/koeff;
	try {arrOut=new Float[lxnew];}
	catch(OutOfMemoryError ee) {return null;}
//	c4eneLastValue=arrEne0[lx-1];
	for (i=0; i<lx; i+=koeff) {
	    i0=i/koeff;
	    arrOut[i0]=arrEne0[i];
	}
	return arrOut;
    }

    public int prepareCovarEne(Float[] arrEne)
    {
	String str,str1;
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	if (arrEne==null) return -1;
	lx=arrEne.length;
//	float ene[];
	try {c4covar_ene=new float[lx];}
	catch(OutOfMemoryError ee) {return -1;}
	for (ii=0; ii<lx; ii++) c4covar_ene[ii]=ii;
	for (ii=0,nn=0; ii<lx; ii++) {
	    if (arrEne[ii]!=null) {
		c4covar_ene[ii]=arrEne[ii].floatValue();
		nn++;
	    }
	}
	if (nn==0) return -1;//2020-09-25: no Ene at all: 21034004.x4
	return 0;
    }

    public int prepareCovarMatrix(int koeffCompress)
    {
	String str,str1;
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	if (c4ene==null) return -1;
	lx=c4ene.length;

	//---compress-on-the-fly:
	if (koeffCompress>1) lx=(lx+koeffCompress-1)/koeffCompress;

	try {c4covar=new float[lx][lx];}
	catch(OutOfMemoryError ee) {return -1;}
//	float ene[];
/*
	try {ene=new float[lx];}
	catch(OutOfMemoryError ee) {return -1;}
	for (ii=0; ii<lx; ii++) ene[ii]=ii;
	for (ii=0; ii<lx; ii++) {
		if (c4ene[ii]!=null)
		ene[ii]=c4ene[ii].floatValue();
	}
*/
	Float c4ene1[]=compressCovarEne(koeffCompress,c4ene,c4ene);
	prepareCovarEne(c4ene1);
//	if (c4covar_ene==null) prepareCovarEne();
	for (jj=0; jj<lx; jj++) for (ii=0; ii<lx; ii++) c4covar[jj][ii]=0;
	return 0;
    }

    public int addCovarMatrix_Uncorrelated(Float[] Arr)
    {
	int ii,jj,i,nn,nRow,lx;
	Float rr;
//	if (c4ene==null) return -1;
	if (c4covar_ene==null) return -1;
	if (Arr==null) return -1;
//	lx=c4ene.length;
	lx=c4covar_ene.length;
	for (jj=0; jj<lx; jj++) {
	    if (Arr[jj]==null) continue;
//if (!isEneIntervalOK(jj)) continue;
if (!isEneIntervalOK(c4covar_ene[jj])) continue;
	    c4covar[jj][jj]+=Math.pow(Arr[jj],2);
	}
	return 0;
    }
    public int addCovarMatrix_Fullycorrelated(Float[] Arr)
    {
	int ii,jj,i,nn,nRow,lx;
	Float rr;
//	if (c4ene==null) return -1;
	if (c4covar_ene==null) return -1;
	if (Arr==null) return -1;
	lx=c4covar_ene.length;
	for (jj=0; jj<lx; jj++) {
	    if (Arr[jj]==null) continue;
	    for (ii=0; ii<lx; ii++) {
		if (Arr[ii]==null) continue;
//if (!isEneIntervalOK(jj)) continue;
//if (!isEneIntervalOK(ii)) continue;
if (!isEneIntervalOK(c4covar_ene[jj])) continue;
if (!isEneIntervalOK(c4covar_ene[ii])) continue;
		c4covar[jj][ii]+=Arr[jj].floatValue()*Arr[ii].floatValue();
	    }
	}
	return 0;
    }

    Float[] EneIntervals=null;
    public void setEneIntervals(Float[] EneIntervals)
    {
	this.EneIntervals=EneIntervals;
    }
    public boolean isEneIntervalOK(int iEne)
    {
//	sysOut_println("...iEne="+iEne);
	if (c4ene==null) return false;
	if (iEne<0) return false;
	if (iEne>=c4ene.length) return false;
	if (EneIntervals==null) return true;
	float ee=c4ene[iEne];
//	sysOut_println("...ee="+ee);
	int ii;
	for (ii=0; ii<EneIntervals.length; ii++) {
//2011-11-16 todo: >= and <=
	    if (ee<EneIntervals[ii]) {
		if ((ii%2)==0) return false;
		else return true;
	    }
	}
	if ((EneIntervals.length%2)==0) return false;
	return true;
    }
    public boolean isEneIntervalOK(float Ene)
    {
//	sysOut_println("...iEne="+iEne);
	if (EneIntervals==null) return true;
	float ee=Ene;
//	sysOut_println("...ee="+ee);
	int ii;
	for (ii=0; ii<EneIntervals.length; ii++) {
//2011-11-16 todo: >= and <=
	    if (ee<EneIntervals[ii]) {
		if ((ii%2)==0) return false;
		else return true;
	    }
	}
	if ((EneIntervals.length%2)==0) return false;
	return true;
    }

    public int addCovarMatrix_Partiallycorrelated(Float[] Arr
	,float[][]mymatrix
	)
    {
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	mxx=null;
	corrMerc=null;
	if (Arr==null) return -1;
//	if (c4ene==null) return -1;
//	lx=c4ene.length;
	if (c4covar_ene==null) return -1;
	lx=c4covar_ene.length;

//	float corr[][];
	corrMerc=null;
	mxx=new x4mkcov();
//	corrMerc=mxx.makeCorrMERC(ene);
	corrMerc=mxx.setExternalCorrMERC(c4covar_ene,mymatrix);
	if (corrMerc==null) return -1;

	for (jj=0; jj<lx; jj++) {
	    if (Arr[jj]==null) continue;
	    for (ii=0; ii<lx; ii++) {
		if (Arr[ii]==null) continue;
//if (!isEneIntervalOK(jj)) {corrMerc[jj][ii]=0;continue;}
//if (!isEneIntervalOK(ii)) {corrMerc[jj][ii]=0;continue;}
if (!isEneIntervalOK(c4covar_ene[jj])) {corrMerc[jj][ii]=0;continue;}
if (!isEneIntervalOK(c4covar_ene[ii])) {corrMerc[jj][ii]=0;continue;}
		c4covar[jj][ii]+=
		Arr[jj].floatValue()
		*Arr[ii].floatValue()
		*corrMerc[jj][ii];
		;
	    }
	}
	return 0;
    }
    public int addCovarMatrix_Partiallycorrelated(Float[] Arr
	,int iScale,Float eMin,Float eMax,Float part
	)
    {
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	mxx=null;
	corrMerc=null;
	if (Arr==null) return -1;
//	if (c4ene==null) return -1;
//	lx=c4ene.length;
	if (c4covar_ene==null) return -1;
	lx=c4covar_ene.length;

//	float corr[][];
	corrMerc=null;
	mxx=new x4mkcov();
	mxx.setLogEnergyScale(iScale);
//	mxx.setEnergyMinmax(ene);
	if ((eMin!=null)&&(eMax!=null))
	mxx.setEnergyMinmax(eMin.floatValue(),eMax.floatValue());
	if (part!=null) mxx.setEnergyInterval(part.floatValue());
	corrMerc=mxx.makeCorrMERC(c4covar_ene);
	if (corrMerc==null) return -1;

	for (jj=0; jj<lx; jj++) {
	    if (Arr[jj]==null) continue;
	    for (ii=0; ii<lx; ii++) {
		if (Arr[ii]==null) continue;
//if (!isEneIntervalOK(jj)) {corrMerc[jj][ii]=0;continue;}
//if (!isEneIntervalOK(ii)) {corrMerc[jj][ii]=0;continue;}
if (!isEneIntervalOK(c4covar_ene[jj])) {corrMerc[jj][ii]=0;continue;}
if (!isEneIntervalOK(c4covar_ene[ii])) {corrMerc[jj][ii]=0;continue;}
		c4covar[jj][ii]+=
		Arr[jj].floatValue()
		*Arr[ii].floatValue()
		*corrMerc[jj][ii];
		;
	    }
	}
	return 0;
    }

    public int makeCovarMatrix2()
    {
	String str,str1;
	int ii,jj,i,nn,nRow,lx;
	Float rr;
	if (c4ene        ==null) return -1;
	if (c4dataErrTot ==null) return -1;
	if (c4dataErrStat==null) return -1;
	if (c4dataErrSys ==null) return -1;
	lx=c4ene.length;
	try {c4covar=new float[lx][lx];}
	catch(OutOfMemoryError ee) {return -1;}

	float corr[][];
	float ene[];
	try {ene=new float[lx];}
	catch(OutOfMemoryError ee) {return -1;}
	for (ii=0; ii<lx; ii++) ene[ii]=ii;
	for (ii=0; ii<lx; ii++) {
		if (c4ene[ii]!=null)
		ene[ii]=c4ene[ii].floatValue();
System.out.println("[[[[[["+ii+" "+ene[ii]+"]]]]]]");
	}
	x4mkcov mxx=new x4mkcov();
	mxx.setLogEnergyScale(1);
	mxx.setEnergyMinmax(ene);
	mxx.setEnergyInterval(0.8f);
	corr=mxx.makeCorrMERC(ene);
	mxx.printMatrix(ene,corr,sysOut);

	for (jj=0; jj<lx; jj++) for (ii=0; ii<lx; ii++) c4covar[jj][ii]=0;
	for (jj=0; jj<lx; jj++) {
	    if (c4dataErrStat[jj]==null) continue;
	    c4covar[jj][jj]+=Math.pow(c4dataErrStat[jj],2);
	}
	for (jj=0; jj<lx; jj++) {
	    if (c4dataErrSys[jj]==null) continue;
	    for (ii=0; ii<lx; ii++) {
		if (c4dataErrSys[ii]==null) continue;
		c4covar[jj][ii]+=
		c4dataErrSys[jj].floatValue()
		*c4dataErrSys[ii].floatValue()
		*corr[jj][ii];
		;
	    }
	}
	return 0;
    }


    public int printCovarMatrix2gauss(PrintWriter prnOut) {
	int ii,jj,lx,ly,nn;
	if (c4ene  ==null) return -2;
	if (c4covar==null) return -2;
	double[][]zz;
	x4mkcov xx=new x4mkcov();
	zz=xx.matrix2trian_gauss(c4covar);
	if (zz==null) return -2;
	lx=zz[0].length;
	ly=lx;
//	xx.printMatrixFull(dmatrix);
	prnOut.println("");
//	prnOut.println("#Covariance-Gauss: "+lx+"*"+lx);
	prnOut.println("#Covariance-Gauss: "+lx+"*"+lx+" (*1e+4)");
	for (jj=0; jj<lx; jj++) {
	    for (ii=0; ii<lx; ii++) {
//		prnOut.print(padstr(" "+float2float((float)zz[jj][ii]),13));
//		prnOut.print(" "+zz[jj][ii]);
//2011		prnOut.print(padstr(" "+zz[jj][ii],23));
		prnOut.print(padstr(" "+zz[jj][ii]*1e4,23));
//4.2139305151067674E-4
	    }
	    prnOut.println("");
	}
	prnOut.println("#/Covariance-Gauss");

	boolean positive=true;
	prnOut.println("");
//	prnOut.println("#Covariance-Gauss-Diagonal-Elements: "+lx+"*"+lx);
//2020	prnOut.println("#Covariance-Gauss-Diagonal-Elements: "+lx+"*"+lx+" (*1e+4)");
	prnOut.println("#Covariance-Gauss-Diagonal-Elements: "+lx+" (*1e+4)"+" sqrt(%)");
	for (jj=0; jj<lx; jj++) {
//2011	    prnOut.print(padstr(" "+(jj+1),6)+" "+zz[jj][jj]);
//2020	    prnOut.print(padstr(" "+(jj+1),6)+" "+zz[jj][jj]*1e4);
	    if (zz[jj][jj]>0)
	    prnOut.print(padstr(" "+(jj+1),6)+" "+strpad(""+zz[jj][jj]*1e4,20)+" "+Math.sqrt(zz[jj][jj]*1e4));
	    else
	    prnOut.print(padstr(" "+(jj+1),6)+" "+zz[jj][jj]*1e4);
	    prnOut.println("");
	    if (zz[jj][jj]<=0) positive=false;
	}
	prnOut.println("#/Covariance-Gauss-Diagonal-Elements");
	prnOut.println("");
	prnOut.println("#Positive-Definite: "+positive);
	if (positive) return 1;
	else return 0;
    }

    public void printCovarMatrix(PrintWriter prnOut) {
	int ii,jj,lx,ly,nn;
	if (c4ene  ==null) return;
	if (c4covar==null) return;
	float[][]zz;
	zz=c4covar;
	lx=zz[0].length;
	ly=lx;
//	prnOut.println("#Covariance(%^2): "+lx+"*"+lx);
//	prnOut.println("#Covariance: "+lx+"*"+lx);
	prnOut.println("#Covariance: "+lx+"*"+lx+" (*1e+4)");
	for (jj=0; jj<lx; jj++) {
//	    for (ii=0; ii<=jj; ii++) {
	    for (ii=0; ii<lx; ii++) {
//		nn=(int)(zz[ii][jj]*1000000+0.5);
//		prnOut.print(padstr(" "+nn,4));
//		prnOut.print(padstr(" "+Math.sqrt(zz[ii][jj]),6));
//?		prnOut.print(padstr(" "+zz[ii][jj],13));
//*1e4
		prnOut.print(padstr(" "+float2float(zz[ii][jj]*10000f),13));
//		prnOut.print(padstr(" "+float2float(zz[ii][jj]),13));
//		prnOut.print(padstr(" "+zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj]),6));
//		nn=(int)(zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
//		prnOut.print(padstr(" "+nn,4));
	    }
	    prnOut.println("");
	}
	prnOut.println("#/Covariance");
    }
    public static float float2float(float rr) {
	String str;
	double ddd=0;
	str=String.format("%e",rr);
	try  {ddd=Double.parseDouble(str);}
	catch(Exception e) {}
	return (float)ddd;
    }

    public void printCorrMatrix(PrintWriter prnOut) {
	int ii,jj,lx,ly,nn;
	if (c4ene  ==null) return;
	if (c4covar==null) return;
	float[][]zz;
	zz=c4covar;
	lx=zz[0].length;
	ly=lx;
	prnOut.println("#...printCorrMatrix:");
	for (ii=0; ii<lx; ii++) {
	    if (ii%6==0) prnOut.println("");
	    prnOut.print(strpad(" "+c4ene[ii]/1e6,12));
	}
	prnOut.println("");
	for (jj=0; jj<lx; jj++) {
	    for (ii=0; ii<=jj; ii++) {
		nn=(int)(zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
		prnOut.print(padstr(" "+nn,3));
	    }
	    prnOut.println("");
	}
    }

    public void printCorrMatrix2(PrintWriter prnOut) {
	int ii,jj,lx,ly,nn;
	if (c4ene  ==null) return;
	if (c4covar==null) return;
	float[][]zz;
	zz=c4covar;
	lx=zz[0].length;
	ly=lx;
	prnOut.println("#...Result:CorrelationMatrix");
	prnOut.print("En(MeV): L="+lx);
	for (ii=0; ii<lx; ii++) {
	    if (ii%6==0) prnOut.println("");
	    prnOut.print(strpad(" "+float2float(c4ene[ii]/1e6f),12));
	}
	prnOut.println("");
	prnOut.println("Variance(%): L="+lx);
	for (ii=0; ii<lx; ii++) {
//	    if (ii%6==0) prnOut.println("");
	    nn=(int)(Math.sqrt(zz[ii][ii])*1000+0.5);
	    prnOut.print(padstr(" "+(nn/10.),4));
	}
	prnOut.println("");
	prnOut.println("Correlation(%): "+lx+"*"+lx);
	for (jj=0; jj<lx; jj++) {
	    for (ii=0; ii<=jj; ii++) {
		nn=(int)(zz[ii][jj]/Math.sqrt(zz[ii][ii])/Math.sqrt(zz[jj][jj])*100+0.5);
		prnOut.print(padstr(" "+nn,3));
	    }
	    prnOut.println("");
	}
//	prnOut.println("");
    }

    public void printPartialErrors(PrintWriter prnOut) {
	int i,ii,jj,lx,ly,nn;
	String str;
	if (c4ene==null) return;
	lx=c4ene.length;
	Vector vec=this.vVars;
	x4var xvar;
	boolean somethingPrinted=false;
	boolean somethingToPrint;
	for (ii=0; ii<lx; ii++) {
	    str="";

	    str+=" Err%";
	    if (c4dataErrTot!=null) if (ii<c4dataErrTot.length) 
	    if (c4dataErrTot[ii]!=null)
	    str=str+" Tot="+String.format("%-5.1f",c4dataErrTot[ii]*100f);

	    if (c4dataErrStat!=null) if (ii<c4dataErrStat.length) 
	    if (c4dataErrStat[ii]!=null)
	    str=str+"Sta="+String.format("%-5.1f",c4dataErrStat[ii]*100f);

	    if (c4dataErrSys!=null) if (ii<c4dataErrSys.length) 
	    if (c4dataErrSys[ii]!=null)
	    str=str+"Sys="+String.format("%-5.1f",c4dataErrSys[ii]*100f);

	    for (i=0,somethingToPrint=false; i<vec.size(); i++) {
	    	xvar=(x4var)vec.elementAt(i);
		if (xvar.c4errorRelArray==null) continue;
		if (ii>=xvar.c4errorRelArray.length) continue;
		if (xvar.c4errorRelArray[ii]==null) continue;
		str=str+" "+xvar.Header+"="+(xvar.c4errorRelArray[ii]*100f);
		somethingPrinted=true;
		somethingToPrint=true;
	    }
	    if (somethingToPrint)
	    prnOut.println(strpad((ii+1)+")",5)+" E,MeV="+strpad(""+c4ene[ii]/1e6,9)+str);
	}
	if (somethingPrinted)
	prnOut.println("");
    }









static int lnn=12;
static int lii=1;
//static int lii=6;

    public int printDataComput(PrintWriter prnOut,int ltab)
    {
	String str,str1,strFamFlag;
	int iRow,iCol,i,nn,nRow,ii;
	int DnRow=0,DnCol;
	boolean empty_Y_value;
//test	prnOut=new PrintWriter(System.out,true);//test
	if (compVars==null) return -1;
	Vector vec=vVars;
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
	if (xreac1!=null) {
//	prnOut.println(strpad("#zTarg",ltab)+xreac1.zTarg);
//	prnOut.println(strpad("#Proj-ZA",ltab)+padstr(""+xProj1.za,6));
//	prnOut.println(strpad("#Proj-ZA",ltab)+padstr(""+xProj1.za,6)+xProj1.cmeta);
//	prnOut.println(strpad("#Targ-ZA",ltab)+padstr(""+xTarg1.za,6)+xTarg1.cmeta);
//	prnOut.println(strpad("#Prod-ZA",ltab)+padstr(""+xProd1.za,6)+xProd1.cmeta+"["+xProd1.Name+"]");
	prnOut.println(strpad("#Proj-ZA",ltab)+" "+xProj1.za+xProj1.cmeta);
//	prnOut.println(strpad("#Targ-ZA",ltab)+" "+xTarg1.za+xTarg1.cmeta);
	prnOut.println(strpad("#Targ-ZA",ltab)+" "+xTarg1.za+xTarg1.dmeta);

//	if (!xProd1.Name.equals(""))
	prnOut.println(strpad("#Prod",ltab)+" "+xProd1.Name+"");

	if (!xProd1.Name.equals(""))
	if ((xProd1.Name.indexOf("ELEM")<0)&&(xProd1.Name.indexOf("MASS")<0)) {
//	prnOut.println(strpad("#Prod-ZA",ltab)+" "+xProd1.za+xProd1.cmeta);
//	prnOut.println(strpad("#Prod-ZA",ltab)+" "+xProd1.za);
	prnOut.println(strpad("#Prod-ZA",ltab)+" "+xProd1.za+xProd1.dmeta);
	if (!xProd1.meta.equals(""))
	prnOut.println(strpad("#Prod-Meta",ltab)+" "+xProd1.meta);
	}

	}

	if (prnOut!=null)
	prnOut.println(strpad("#computDATA",ltab)+strpad(" "+getDataLY(),lnn)+strpad(" "+((nExpectedArgs+1)*2),ltab)+lnn);

//++	if (prnOut!=null) prnOut.print("\t");
//?	if (prnOut!=null) prnOut.print("!");
	if (prnOut!=null) prnOut.print("#");
	for (ii=0; ii<compVars.length; ii++) {
	    compVars[ii].prepareCompHeader();
	    xvar=compVars[ii].xvar0;
	    if (xvar==null) continue;
	    strFamFlag=x4dict024dt.getDataTypeShortExpansion(xvar.DataType);
//if (prnOut!=null) prnOut.println("...xvar.DataType["+xvar.DataType+"] strFamFlag=["+strFamFlag+"]");
//	    if (xvar.DataType.equals("62"))
//	    strFamFlag=x4dict024dt.getDataTypeShortExpansion("61");
	    if (compVars[ii].convDataType!=null)
	    strFamFlag=x4dict024dt.getDataTypeShortExpansion(compVars[ii].convDataType);
//if (prnOut!=null) prnOut.print("[[[["+compVars[ii].convDataType+"]]]]");
	    if (xvar.numDataType1==7) {
		strFamFlag="Z-A-M";
		str1="#Nuclide";
	    }
	    else str1="Error";
	    if (ii==0) strFamFlag="Data";
	    if (x4dict024dt.getLabCMFlag(xvar.PlottingFlags)) strFamFlag+=":CM";
	    if (prnOut!=null)
	    prnOut.print(strpad(strFamFlag,ltab)+strpad(str1,ltab));
	}
	if (prnOut!=null) prnOut.println("");

//++	if (prnOut!=null) prnOut.print("\t");
//?	if (prnOut!=null) prnOut.print("!");
	if (prnOut!=null) prnOut.print("#");
	for (ii=0; ii<compVars.length; ii++) {
	    xvar=compVars[ii].xvar0;
	    if (xvar==null) continue;
	    str=xvar.BasicUnits;
//if (prnOut!=null) prnOut.println("...xvar.BasicUnits=["+xvar.BasicUnits+"]");
//	    if (xvar.DataType.equals("62")) str="ADEG";
	    if (compVars[ii].convUnits!=null) str=compVars[ii].convUnits;
//if (prnOut!=null) prnOut.println("...xvar.BasicUnits=["+xvar.BasicUnits+"] convUnits=["+compVars[ii].convUnits+"]");
	    str1=str;
	    if (xvar.numDataType1==7) str1="#";
	    if (prnOut!=null)
	    prnOut.print(strpad(str,ltab)+strpad(str1,ltab));
	}
	if (prnOut!=null) prnOut.println("");

	for (iRow=0,nRow=0; iRow<DnRow; iRow++) {
	    str1="";
	    if (lii>1) str1=padstr((nRow+1)+") ",lii);
	    else       str1=padstr("",lii);

	    empty_Y_value=true;
	    for (iCol=0; iCol<DnCol; iCol++) {
		xvar=(x4var)vec.elementAt(iCol);
//		rr=xvar.getValue(iRow);
		rr=xvar.getValueInBasicUnits(iRow);
//		if (rr==null) str="-no-";
		if (rr==null) str="";
		else {
		    if (xvar.isItYValue()) empty_Y_value=false;
//		    str=double2str(rr);
//		    str=""+rr.floatValue();
		    str=double2str(rr.doubleValue());
		}
		str1+=strpad(str,lnn);
	    }

//test
//for (ii=0; ii<compVars.length; ii++) compVars[ii].println();
//if (true) continue;

	    if (!empty_Y_value) {
//		for (ii=0; ii<compVars.length; ii++) compVars[ii].println();
		for (ii=0; ii<compVars.length; ii++) {
		    compVars[ii].prepareCompValue(iRow);
		}
		str1="";
		if (lii>1) str1=padstr((nRow+1)+") ",lii);
		else       str1=padstr("",lii);
		for (ii=0; ii<compVars.length; ii++) {
//		    compVars[ii].prepareCompValue();

		    rr=compVars[ii].compValue;
		    if (rr==null) str="-----";
		    else str=double2str(rr);
if (compVars[ii].cmeta=='G') if (str.endsWith(".")) str=str+"0";//2011
		    str1+=strpad(str,lnn);

		    rr=compVars[ii].compError;
//		    if (rr==null) str="err?";
//		    if (rr==null) str=".00";
		    if (rr==null) str="-0.";
		    else str=double2str(rr);
		    if (compVars[ii].xvar0!=null)
		    if (compVars[ii].xvar0.numDataType1==7)
			    str="#"+compVars[ii].getIsotope()
//	+"["+compVars[ii].cmeta+"]"
				;
		    str1+=strpad(str,lnn);

		}
		if (prnOut!=null) {
//		    prnOut.print(strpad(""+(nRow+1)+") ",5));
//++		    prnOut.print((nRow+1)+")\t");
//?		    prnOut.print(" ");
		    prnOut.println(str1);
		}
		nRow++;
	    }
	}
	if (prnOut!=null)
	prnOut.println(strpad("#/computDATA",ltab)+strpad(" "+nRow,ltab)+DnCol);
	return nRow;
    }

























    public void println() {
	String str;
	x4reacstr xreacstr;
	int ii;
	sysOut.println("   ..x4reaction:"+"Pointer="+cpointer+""+" nReacStr="+reacstrings.size());
	sysOut.println("    full-code:\t"+reacode
	//??+" [[[[[[[[[[[[[[[[[[[[["+SF1
	);
	sysOut.println("       d4code:\t"+d4reactioncode+"");
	for (ii=0; ii<reacstrings.size(); ii++) {
	    str=(String)reacstrings.elementAt(ii);
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    sysOut.println("\t"+(ii+1)+":\t"+str+"\r\n"+xreacstr.getStrSFs()
		+"\r\n\t\t"+xreacstr.getSF58Quant());
	}
	sysOut.println(" IndepVarFamilyCode:["+IndepVarFamilyCode+"]"+" nExpectedArgs:"+nExpectedArgs);
	sysOut.println("...ok="+ok);
	sysOut.println("");
    }

    public String getStrUnique(Vector vec0,int iflag) {
	Vector vec=new Vector();
	int ii,i2;
	String str0,str1;
	boolean found;
	for (ii=0; ii<vec0.size(); ii++) {
	    str0=(String)vec0.elementAt(ii);
	    if (str0.trim().equals("")) continue;
	    for (i2=0,found=false; i2<vec.size(); i2++) {
		str1=(String)vec.elementAt(i2);
		if (str1.equals(str0)) {found=true;break;}
	    }
//	    sysOut.println("___getStrUnique:"+" str0="+str0+" found="+found);
	    if (!found) vec.addElement(str0);
	}
	for (ii=0,str1=""; ii<vec.size(); ii++) {
	    str0=(String)vec.elementAt(ii);

	    if (iflag==1) {if (str0.length()>1) str0=str0.substring(0,1)+str0.substring(1).toLowerCase();}
	    else
	    if (iflag==2) str0=str0.toLowerCase();

//20201006  if (ii>0) str1+=";";
	    if (ii>0) str1+="|";
	    str1+=str0;
	}
	return str1;
    }
    public String getTargets() {
	x4reacstr xreacstr;
	Vector vec=new Vector();
	int ii;
	for (ii=0; ii<reacstrings.size(); ii++) {
	    xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    vec.addElement(xreacstr.Target);
	}
	return getStrUnique(vec,1);
    }
    public String getQuants() {
	Vector vec=new Vector();
	for (int ii=0; ii<reacstrings.size(); ii++) {
	    x4reacstr xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    vec.addElement(xreacstr.Quant);
	}
	return getStrUnique(vec,0);
    }
    public String getReacs() {
	Vector vec=new Vector();
	String Reac,xProj;
	int ind;
	for (int ii=0; ii<reacstrings.size(); ii++) {
	    x4reacstr xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    if (xreacstr.Reac==null) continue;
	    if (xreacstr.xProj==null) continue;
	    Reac=xreacstr.Reac;
	    xProj=xreacstr.xProj.shortName;
	    ind=Reac.indexOf(",");
	    if (ind>0) Reac=xProj+Reac.substring(Reac.indexOf(","));
//	    vec.addElement(xreacstr.Reac);
	    vec.addElement(Reac);
	}
	return getStrUnique(vec,2);
    }
    public String getProjs() {
	Vector vec=new Vector();
	for (int ii=0; ii<reacstrings.size(); ii++) {
	    x4reacstr xreacstr=(x4reacstr)vReacs.elementAt(ii);
	    if (xreacstr.xProj==null) continue;
//	    vec.addElement(xreacstr.xProj.partName);
	    vec.addElement(xreacstr.xProj.shortName);
	}
	return getStrUnique(vec,2);
    }

    public static String double2str(double rr) {
	return double2str(rr,"%g");
    }
    public static String double2str(double rr,String fmt) {
	String str="";
	int ind;
//	str=""+rr;
//	if (str.endsWith(".0")) str=myStrReplace(str,".0",".");
//	if (str.endsWith("e0")) str=myStrReplace(str,"e0","");
	str="".format(fmt,rr);
//if (true) return str;
	str=myStrReplace(str,"00000e","e");
	str=myStrReplace(str,"0000e","e");
	str=myStrReplace(str,"000e","e");
	str=myStrReplace(str,"00e","e");
	str=myStrReplace(str,"0e","e");
	str=myStrReplace(str,"e+0","e+");
	str=myStrReplace(str,"e-0","e-");
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
	str=myStrReplace(str,"e+","e");
	str=myStrReplace(str,".e","e");
	return str;
    }



    public static String strpad(String str, int lpad) {
	String strOut;
	strOut=strpad(str,lpad,false);
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















int iie6Out=0,NS=0,MAT=5555,MF=33,MT=777;
PrintWriter e6outFile=null;
void e6set(PrintWriter prnOut,int iMAT,int iMF,int iMT,int iNS) {
    iie6Out=0;
    MAT=iMAT ;
    MF =iMF  ;
    MT =iMT  ;
    NS =iNS  ;
    e6outFile=prnOut;
}
String e6float2str(float rr)
{
    int i,ii,ie,ll,ind;
    String str;
    String s1;
    s1=String.format("%11.5e",rr);
    ind=s1.indexOf("e");
    if (ind>0)
    if ((s1.charAt(ind+1)=='-')||(s1.charAt(ind+1)=='+'))
	s1=s1.substring(0,ind)+s1.substring(ind+1);
    str=s1;
    return str;
}
void e6outCR() {
    String str1;
    if (iie6Out<6) return;
    iie6Out=0;
    NS++;
    str1=String.format("%4d%2d%3d%5d",MAT,MF,MT,NS);
    e6outFile.println(str1);
}
void e6outflo(float rr) {
    String str1;
    if (rr==0) str1="0.0";
    else
    if (rr==1) str1="1.0";
    else
    str1=e6float2str(rr);
    str1=padstr(str1,11);
    e6outFile.print(str1);
    iie6Out++;
    e6outCR();
}
void e6outint(int rr) {
    String str1;
    str1=String.format("%10d",rr);
    e6outFile.print(" "+str1);
    iie6Out++;
    e6outCR();
}
void e6outMEND() {
    int ii,mt00;
    for (ii=iie6Out; ii<6; ii++) e6outint(0);
    mt00=MT;
    MT=0;
    NS=99998;
    for (ii=0; ii<6; ii++) e6outint(0);
    MT=mt00;
}

    public void printCovarMatrix3Endf(PrintWriter prnOut,float c4eneLastValue) {
	int i,lstrf,lstrf2,kstrf=5,flagSymmetr=1;
	int ii,jj,lx,ly,lz,nn,kk,lx0,ly0;
	float zt;
	if (c4ene  ==null) return;
	if (c4covar==null) return;
	float[][]zz;
	zz=c4covar;
	lx=zz[0].length;
	ly=lx;
	lx0=lx;
	ly0=ly;
	lx++;	ly++;
//	prnOut.println("#Covariance: "+lx+"*"+lx);

	int iiOut=0,NS=0,MAT=5555,MF=33,MT=777;

	iiOut=0;
	MAT=555;
	MF=33;
	MT=c4_MT;
	NS=0;

	float za=xTarg1.za;
	float awr=za*2;
	e6set(prnOut,MAT,MF,MT,NS);
	e6outflo(za);	//1)ZA
	e6outflo(awr);	//2)AWR
	e6outint(0);	//3)space
	e6outint(0);	//4)space
	e6outint(0);	//space
	e6outint(1);	//5)NL

	e6outflo(0);	//1)XMF1
	e6outflo(0);	//2)XLFS1
	e6outint(0);	//3)MAT1
	e6outint(MT);	//4)MT1 
	e6outint(0);	//5)NC  
	e6outint(1);	//6)NI  

	e6outflo(0);	//1)XMF1
	e6outflo(0);	//2)XLFS1
	e6outint(1);	//3)LS
	e6outint(5);	//4)LB
	jj=lx+1;
	ii=((jj+1)*jj)/2;
	e6outint(ii);	//5)NT  
	e6outint(jj);	//6)NP  

	if (c4eneLastValue<0) c4eneLastValue=c4covar_ene[lx0-1];
	kstrf=6;
	lstrf=10;
	e6outflo(1e-5f);
	for (ii=0; ii<lx0; ii++) e6outflo(c4covar_ene[ii]);
//2012	if (lx0<lx) e6outflo(ene[lx0-1]);
	if (lx0<lx) e6outflo(c4eneLastValue);
	for (ii=0; ii<lx; ii++) e6outflo(0);

	lz=lx*lx;
/*	for (kk=0; kk<lz; kk++) {
		jj=kk/lx;
		ii=kk%lx;
//		zt=zz[jj-1][ii];
		if (ii<jj) continue;
		if (ii==lx-1) continue;
if (jj==0) zt=0; else
		zt=zz[jj-1][ii];
		e6outflo(zt);
	}
*/
	for (jj=0; jj<lx0; jj++)
	for (ii=0; ii<lx0; ii++)
	{
		zt=zz[jj][ii];
		//zt=float2precision3(zt);//wrong, because covar (not corr)
		if (ii<jj) continue;
		e6outflo(zt);
	}
	e6outMEND();
	prnOut.println("");
    }

    public float float2precision3(float rr0)//to 3 digits
    {
	String str="".format("%.2e",rr0);
	try  {
	    float rr=Float.parseFloat(str);
	    return rr;
	}
	catch(Exception e) {
	    return rr0;
	}
    }




}
