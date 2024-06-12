package zvv.x4;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * X4C4 object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-08-08
 * @since           2011-10-31
 *
 * <pre>
 * Program:         x4c4obj.java
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

public class x4c4obj {
    static boolean extDebug = false;
    static boolean debug2 = false;
    x4reaction xreacode = null;
    int c4_zaProj = 0;
    int c4_zaTarg = 0;
    char c4_TargM = (char)32;
    int c4_MF = 0;
    int c4_MT = 0;
    int c4_OpNum = 0;
    int c4_ratioMT = -1;
    char c4_ProdM = (char)32;
    char c4_compValueSetFromCh = (char)48;
    Float c4_decayFlag = null;
    char c4_X4STAT = (char)32;
    char c4_X4CM = (char)32;
    Float[] c4_Data = new Float[13];
    String[] c4_sData = new String[13];
    String c4_sData5inv = null;
    String c4_i78 = "";
    String c4_Refer = "";
    String c4_ReferMod = "";
    String nowProd = "";
    String DatasetID = "X0000000 ";
    String c4_SubentP = "X0000  0 ";
    x4reacstr xreacstrRatioDenom = null;
    int c4_ifound = -1;
    String strBoolCmLab = "----";
    String strBoolCmLabNew = "----";
    String strDataCmLab = "";
    String strLegend1 = "";
    String strLegend2 = "";
    String c4str = "";
    boolean outSysError = true;
    boolean outSysErrorPercent = true;
    boolean outMonData = true;
    boolean out_iRow = false;
    boolean tryToRecoverEnresFromEn = true;
    boolean outC4only = false;
    boolean tryToConvertCM2Lab = false;
    boolean flagMFMT_gt_0 = false;
    x4cvar[] compVars = null;
    int DnRow = 0;
    int DnCol = 0;
    boolean mf4tomf6 = false;
    String[] c4_DataFamFlag = new String[8];
    String[] c4_DataUnits = new String[8];
    String[] c4_DataUnitFamilyCode = new String[8];
    String c4DataUnitFamilyY = "";
    static int lnn = 12;
    static int lii = 1;
    Float[] c4_DataSave;
    int iRowX4 = -1;
    Float monitVal = null;
    Float monitErr = null;
    boolean flag_fixed_widthC5 = true;
    Float enMin = null;
    Float enMax = null;

    public static void main(String[] stringArray) {
        Float f = new Float(6.7000003);
        String string = x4c4obj.real2str(f.floatValue());
        System.out.println("str=[" + string + "]");
        string = x4c4obj.real2str(f);
        System.out.println("str=[" + string + "]");
        f = new Float(0.25990003);
        string = x4c4obj.real2str(f.floatValue());
        System.out.println("str=[" + string + "]");
        string = x4c4obj.real2str(f);
        System.out.println("str=[" + string + "]");
        f = new Float(0.2878307);
        string = x4c4obj.real2str(f.floatValue());
        System.out.println("str=[" + string + "]");
        string = x4c4obj.real2str(f);
        System.out.println("str=[" + string + "]");
        extDebug = true;
        f = new Float(0.0141795);
        f = new Float(0.014179541);
        string = x4c4obj.real2str(f.floatValue());
        System.out.println(f + " str=[" + string + "]");
        string = x4c4obj.real2str(f);
        System.out.println(f + " str=[" + string + "]");
    }

    x4c4obj() {
    }

    public void initDataC4() {
        int n;
        this.c4_zaProj = 0;
        this.c4_zaTarg = 0;
        this.c4_TargM = (char)32;
        this.c4_MF = 0;
        this.c4_MT = 0;
        this.c4_ratioMT = -1;
        this.c4_ProdM = (char)32;
        this.c4_compValueSetFromCh = (char)48;
        this.c4_decayFlag = null;
        this.c4_X4STAT = (char)32;
        this.c4_X4CM = (char)32;
        for (n = 0; n < 13; ++n) {
            this.c4_sData[n] = null;
        }
        for (n = 0; n < this.c4_Data.length; ++n) {
            this.c4_Data[n] = null;
        }
        this.c4_i78 = "";
        this.c4_Refer = "";
        this.c4_ReferMod = "";
        this.c4_SubentP = "X0000000 ";
        this.xreacstrRatioDenom = null;
    }

    public int prepareC4Info(x4subent x4subent2, x4reaction x4reaction2, boolean bl) {
        return this.prepareC4Info(x4subent2, x4reaction2, bl, "");
    }

    public int prepareC4Info(x4subent sub2,x4reaction xreacode
	,boolean isVectorCommon,String invFlag)
    {
	int i,ii,nRow=0,nn,lnn=16,nx,ll1,mf1=0,mt1=0,mf2=0,mt2=0;
	x4reacstr xreac1=null,xreac2;
	x4subent sub1=new x4subent();
	if (sub2!=null) sub1=sub2.subent1;
	x4ref xref1=new x4ref();
//System.out.println("prepareC4Info::Subent:["+sub2.Subent+"]sub1:"+sub1);
	if (sub1.xbib==null) sub1.xbib=new x4bib();
	if (sub1.xbib.xref1!=null)
	xref1=sub1.xbib.xref1;

	if (sub2==null) {sub2=new x4subent();sub2.xbib=new x4bib();}

	String str,str1,str2;
	String strHowFound="";

	this.xreacode=xreacode;

	if (extDebug)
	System.out.println("...prepareC4Info:...xreacode.IndepVarFamilyCode.trim()=["+xreacode.IndepVarFamilyCode.trim()+"]");
	if (xreacode.IndepVarFamilyCode.trim().equals("?")) {
	    return -3;
	}

	initDataC4();
	x4dict714 x4f=null;
	x4dict715 x4f2=null;
//x4dict714.extDebug=true;
	x4f=x4dict714.findinx4dict(xreacode.C4ReactionCode);
		if (extDebug)
		System.out.println("...1...c4_MF="+c4_MF+" C4Reac="+xreacode.C4ReactionCode+"\n"+x4f+"\n"
		+" fullcode="+xreacode.reacode
		);
	if (x4f!=null) {
		if (extDebug)
		System.out.println("...x4f="+x4f.toString());
	    c4_MF=x4f.MF;
	    c4_MT=x4f.MT;
	    c4_OpNum=x4f.OpNum;
	    c4_ifound=1;

	    //2023-02-28
	    if (c4_MF==3)
	    if (xreacode.vReacs.size()==1)
	    {
		if (xreacode.xreac1.SF6.indexOf("FY")>=0) c4_MF=8;
	    }

	}
	else
	if (xreacode.vReacs.size()==1)
	{
		xreac1=xreacode.xreac1;
		if (xreac1!=null)
//		x4f2=x4dict715.find715(xreac1.SF2,xreac1.SF3,xreac1.SF4
//			,xreac1.SF5,xreac1.SF6,xreac1.SF7);
		x4f2=x4dict715.find715(xreac1);	//x4f2=null;
if (extDebug)
System.out.println("...1...c4_MF="+c4_MF+" C4Reac="+xreacode.C4ReactionCode
+"\n___x4dict715:"+x4f2+"\n"
+" fullcode="+xreacode.reacode
+"\n xreac1.ReactionType:"+xreac1.ReactionType
+" xreac1.SF8:"+xreac1.SF8
);
		if (x4f2!=null) {
		    if (extDebug) System.out.println("___2023::x4f2.c4_MF="+c4_MF+" xreac1.SF8=["+xreac1.SF8+"]");
		    if (extDebug) System.out.println("...x4f2="+x4f2.toString());
		    c4_MF=x4f2.MF;
//if (xreac1.SF8.indexOf("REL")>=0) if (x4f2.MF<100) c4_MF=x4f2.MF+600;//2023-02-22
		    c4_MT=x4f2.MT;
		    if (extDebug) System.out.println("...2...c4_MF="+c4_MF);
		    c4_ifound=2;
		}

		if (x4f2==null)
		if (xreac1.SF6.indexOf("/RAT")>0)
		{
		    x4f2=x4dict715.find715(xreac1.SF2,xreac1.SF3,xreac1.SF4
			,xreac1.SF5
//			,xreac1.SF6
			,myStrReplace(xreac1.SF6,"/RAT","")
			,xreac1.SF7);
if (extDebug)
System.out.println("...3...c4_MF="+c4_MF);
		    if (x4f2!=null) {
			if (x4f2.MF>=800) c4_MF=x4f2.MF+1; else
			c4_MF=x4f2.MF+200;
			c4_MT=x4f2.MT;
			c4_ifound=3;
		    }
		}

		if (extDebug)
		System.out.println("...4...c4_MF="+c4_MF);

		if (x4f2==null)
		c4_MF=x4dict715.find715MF(xreac1);

if (xreac1.SF8.indexOf("REL")>=0) if (c4_MF<100) c4_MF=c4_MF+600;//2023-02-22

		if (extDebug)
		System.out.println("...5...c4_MF="+c4_MF);
	}

	if (xreacode.IndepVarFamilyCode.indexOf("5")>=0) c4_MF=154;//2015:D0601005

	if (extDebug)
	System.out.println("...2A...x4f="+x4f+"\n"+"...2b...x4f2="+x4f2
	+"\n target=["+xreacode.xTarg1.originalStr+"]"+" AW="+xreacode.xTarg1.AtomicWeight+" ZA="+xreacode.xTarg1.za
	+"\n reacombi0="+xreacode.reacombi0
	+" reacombiU="+xreacode.reacombiU
	+" size="+xreacode.vReacs.size()
	+" simpleRatio="+xreacode.simpleRatio
	+" c4_MF="+c4_MF
	);

	if ((x4f==null)&&(x4f2==null))
	if ((xreacode.simpleRatio)&&(xreacode.vReacs.size()==2))
	{
	    xreac1=(x4reacstr)xreacode.vReacs.elementAt(0);
	    xreac2=(x4reacstr)xreacode.vReacs.elementAt(1);
	    str1=xreac1.reacstr;
	    str2=xreac2.reacstr;
	    x4reaction reac1=new x4reaction(' ',str1);
	    x4reaction reac2=new x4reaction(' ',str2);
	    str1=reac1.C4ReactionCode;
	    str2=reac2.C4ReactionCode;

	    x4f=x4dict714.findinx4dict(str1);
	    if (x4f!=null) {mf1=x4f.MF;mt1=x4f.MT;c4_OpNum=x4f.OpNum;c4_ifound=10;}
	    if (x4f==null) {
		x4f2=x4dict715.find715(xreac1);
		if (x4f2!=null) {mf1=x4f2.MF;mt1=x4f2.MT;c4_ifound=11;}
	    }

	    x4f=x4dict714.findinx4dict(str2);
	    if (x4f!=null) {mf2=x4f.MF;mt2=x4f.MT;c4_OpNum=x4f.OpNum;c4_ifound=20;}
	    if (x4f==null) {
		x4f2=x4dict715.find715(xreac2);
		if (x4f2!=null) {mf1=x4f2.MF;mt1=x4f2.MT;c4_ifound=21;}
	    }

	    if ((mf2==0)&&(mt2==0))
	    if (xreacode.simpleRatio2) {mf2=mf1;mt2=mt1;}

if (extDebug) System.out.println(".......C4R1="+str1+" MF="+mf1+" MT="+mt1);
if (extDebug) System.out.println(".......C4R2="+str2+" MF="+mf2+" MT="+mt2);

	    if ((mf1>0)&&(mf1==mf2)) {
//		if (mf1==3) c4_MF=mf1+200; else c4_MF=mf1;//add 200 only for MF3
		if (mf1>=800) c4_MF=mf1+1; else
		c4_MF=mf1+200;
		c4_MT=mt1;
		c4_ratioMT=mt2;
		c4_ifound=30;
	    }
	}

//System.out.println("...2d????xreacode.reacombi0="+xreacode.reacombi0);
	if (c4_MF==0) //2010: try to resolve?
	if (xreacode.reacombi0.equals("a"))
	{
		if (extDebug)
		System.out.println("...2d___x4f="+x4f+"\n"+"...2b...x4f2="+x4f2
		+"\n reacombi0="+xreacode.reacombi0
		+" reacombiU="+xreacode.reacombiU
		+" size="+xreacode.vReacs.size()
		+" simpleRatio="+xreacode.simpleRatio
		);

//if (extDebug) x4dict715.extDebug=true;
		xreac1=xreacode.xreac1;
		if (xreac1!=null)
//		x4f2=x4dict715.find715(xreac1.SF2,xreac1.SF3,xreac1.SF4
//			,xreac1.SF5,xreac1.SF6,xreac1.SF7);
		x4f2=x4dict715.find715(xreac1);	//x4f2=null;
		if (x4f2==null) x4f2=x4dict715.find715bySF6(xreac1);//2011?
		if (x4f2!=null) {
		    if (extDebug)
		    System.out.println("...C...x4f2="+x4f2.toString());
		    c4_MF=x4f2.MF;
//		    c4_MT=x4f2.MT;
//+		    if (xreacode.BasicUnits.equals("ARB-UNITS")) c4_MF+=500; //equ:SF8 has "REL"
		    c4_MF+=500; //always add 500
		    if (extDebug)
		    System.out.println("...C...c4_MF="+c4_MF);
		    c4_ifound=40;
		}

//	    return -2;//2010: try to resolve?
	}

	if (extDebug)
	System.out.println("...3.xreacode.ReactionType="+xreacode.ReactionType);

	//2022-04-22:A0480022 REACTION:92-U-233(A,F)MASS,CHN,SIG
//if (false)
//	if (xreacode.vReacs.size()==1)
	if (x4f2!=null)
	{
	    if (c4_MF==3)
	    if (xreac1.ReactionType.startsWith("FY"))
//	    c4_MF=0;
	    c4_MF=803;
	}

	//2019-04-23
	if (xreacode!=null) flagMFMT_gt_0=xreacode.flagMFMT_gt_0;

	if (extDebug)
	System.out.println("___3___xreacode.ReactionType="+xreacode.ReactionType+" MT0="+flagMFMT_gt_0);

/*2021-10-12 move down
	if (c4_MF==0) return -2;//2010: try to resolve?

	if (flagMFMT_gt_0)
	if ((c4_MF==0)||(c4_MT==0)) return -3;//run1
*/
//	if (!xreacode.IndepVarFamilyCode.trim().equals("?")) nTotConverted++;

	c4_X4STAT=xreacode.getC4Status();
//	DatasetID=sub2.Subent+xreacode.cpointer;
//	c4_SubentP=sub2.Entry+padstr(""+sub2.sub,3)+xreacode.cpointer;
	c4_SubentP=sub2.Entry+padstr(""+sub2.sub,3);
	if (isVectorCommon) c4_SubentP+=' ';
	else c4_SubentP+=xreacode.cpointer;
	c4_Refer=sub1.xbib.Author1orig;
	c4_ReferMod=c4_Refer+"*";

	DatasetID=sub2.Subent;
	if (isVectorCommon) DatasetID+=' ';
	else DatasetID+=xreacode.cpointer;

	str=""+sub1.xbib.Year1Ref;
//System.out.println("...bib1:Reference1=["+sub1.xbib.Reference1+"]");
//System.out.println("...bib1:Reference1=["+sub2.xbib.Reference1+"]");
	if (sub1.xbib.Reference1.equals("")) str=""+sub2.xbib.Year1Ref;
	if (str.length()==4) str=str.substring(2);
	str="("+str+")";
	ll1=str.length();

	if (sub1.xbib.vAuthors.size()>1) {
/*	    if (c4_Refer.length()+ll1+7<=25) c4_Refer=c4_Refer+",ET.AL.";
	    else
	    if (c4_Refer.length()+ll1+1<=25) c4_Refer=c4_Refer+",";
*/
/*	    if (c4_Refer.length()+ll1+2<=25) c4_Refer=c4_Refer+",+";
	    else
	    if (c4_Refer.length()+ll1+1<=25) c4_Refer=c4_Refer+",";
*/
	    if (c4_Refer.length()+ll1+1<=25) c4_Refer+=",";
	    if (c4_ReferMod.length()+ll1+1<=25) c4_ReferMod+=",";
	}
//?	if (c4_Refer.length()>25-(ll1+3)) c4_Refer=c4_Refer.substring(0,25-(ll1+3));
//sysOut_println("[[["+c4_Refer+"]]]"+c4_SubentP+"["+sub1.xbib.Author1+"]");
//sysOut_println("[[["+c4_Refer+"]]]"+sub1.xbib.Year1Ref);
	if (c4_Refer.length()+ll1>25) c4_Refer=c4_Refer.substring(0,25-ll1);
	if (c4_ReferMod.length()+ll1>25) c4_ReferMod=c4_ReferMod.substring(0,25-ll1);
	c4_Refer=strpad(c4_Refer,25-ll1)+str;
	c4_ReferMod=strpad(c4_ReferMod,25-ll1)+str;
//	c4_Refer=c4_Refer+" ("+str+")";



//2021-10-12 move from above
	if (c4_MF==0) return -2;//2010: try to resolve?
	if (flagMFMT_gt_0)
	if ((c4_MF==0)||(c4_MT==0)) return -3;//run1



	if (invFlag.length()>0) {
	    c4_Refer=sub1.xbib.Author1orig;
	    c4_ReferMod=c4_Refer+"*";
	    String strEtAl="";
	    if (sub1.xbib.vAuthors.size()>1) strEtAl=",";

	    ll1=c4_Refer.length()+invFlag.length()+strEtAl.length()+str.length();
	    if (ll1>25) c4_Refer=c4_Refer.substring(0,c4_Refer.length()-(ll1-25));
	    c4_Refer=c4_Refer+invFlag+strEtAl+str;

	    ll1=c4_ReferMod.length()+invFlag.length()+strEtAl.length()+str.length();
	    if (ll1>25) c4_ReferMod=c4_ReferMod.substring(0,c4_ReferMod.length()-(ll1-25));
	    c4_ReferMod=c4_ReferMod+invFlag+strEtAl+str;

	}


	c4_zaTarg=xreacode.xTarg1.za;
//?2011	c4_TargM=xreacode.xTarg1.cmeta;
//?2011	c4_ProdM=xreacode.xProd1.cmeta;
	c4_TargM=xreacode.xTarg1.cdmeta;
	c4_ProdM=xreacode.xProd1.cmeta;
	c4_zaProj=xreacode.xProj1.za;
	if ((xreacode.xreac1.SF2.equals("0"))&&(xreacode.xreac1.SF3.equals("0"))) c4_zaProj=0;


	x4reacstr xreacstr;
	if (xreacode.simpleRatio) {
	    if (xreacode.vReacs.size()==2) {
		xreacstr=(x4reacstr)xreacode.vReacs.elementAt(1);
		xreacstrRatioDenom=xreacstr;
//		c4_ratioTarg2M=xreacstr.xTarg.cmeta;
//		c4_ratiozaTarg=xreacstr.xTarg.za;
	    }
	}

	Vector vec=xreacode.vVars;
	DnCol=vec.size();
	x4var xvar;
	for (i=0; i<vec.size(); i++) {
	    xvar=(x4var)vec.elementAt(i);
	    nn=xvar.xdata.DnRow;
	    if (nn>DnRow) DnRow=nn;
	}

	compVars=xreacode.compVars;

//	if (xreacode.xreac1!=null)
//	prnOut.println(strpad("#TARG",ltab)+strpad(""+xreacode.xTarg1.za,ltab)+xreacode.xTarg1.cmeta);

//	if (xreacode.xreac1!=null)
//	if (!xreacode.xreac1.SF4.equals(""))
//	prnOut.println(strpad("#PRODUCT",ltab)+xreacode.xreac1.SF4);

//	prnOut.println(strpad("#C4ReaCode",ltab)+xreacode.C4ReactionCode);
//	prnOut.println(strpad("#ReacRatio",ltab)+xreacode.simpleRatio);
//	prnOut.println(strpad("#vReacs",ltab)+xreacode.vReacs.size());


	int nn2,nn3;
	c4_X4CM	=' ';
	boolean koeffEnCM2LabKnown=false;
	if (xreacode!=null) koeffEnCM2LabKnown=xreacode.koeffEnCM2LabKnown;
	nn=0;nn3=0;
	if (compVars!=null)
	for (ii=0,nn=0,nn2=1,nn3=0; ii<compVars.length; ii++) {
//?	    compVars[ii].prepareCompHeader();
	    xvar=compVars[ii].xvar0;
	    if (xvar==null) continue;
//System.out.println(ii+")___prepareC4Info:...getLabCMFlag:["+x4dict024dt.getLabCMFlag(xvar.PlottingFlags)+"]"+xvar.Header+" koeffEnCM2LabKnown:"+koeffEnCM2LabKnown);
//if (false)
	    boolean cm=false;
	    if (x4dict024dt.getLabCMFlag(xvar.PlottingFlags))
	    {
//2022		if (((xvar.numDataType1!=1)&&(xvar.numDataType1!=2))
//2022			||(!koeffEnCM2LabKnown))
//		if (!koeffEnCM2LabKnown)
		{
//		    c4_X4CM='C';
//		    nn+=nn2;
		    if (ii==0) {nn3|=2;cm=true;}
//2022		    if (!koeffEnCM2LabKnown)
//		    if (xvar.numDataType1==1) nn3|=1;//EN-RES
//20220113	    if (!koeffEnCM2LabKnown)
//		    if (xvar.numDataType1==2) nn3|=1;//EN
		    if ((xvar.numDataType1==1)||(xvar.numDataType1==2)) {
			//if (!compVars[ii].convert_Einc2Lab) {
			if (!xreacode.convert_Einc2Lab) {
			    nn3|=1;//EN
			    cm=true;
			}
		    }
		    if (xvar.numDataType1==3) {nn3|=8;cm=true;}//E2
		    if (xvar.numDataType1==4) {nn3|=4;cm=true;}//ANG
		}
		if (cm) {
		    c4_X4CM='C';
		    nn+=nn2;
		}
	    }
	    nn2*=2;
	}
//	if (nn>1) c4_X4CM=(char)('0'+nn);
	if (nn>1) c4_X4CM=(char)('C'-2+nn3);

	strDataCmLab="";
	strBoolCmLab="";
	for (ii=0,nn2=1; ii<4; ii++) {
		if ((nn3&nn2)!=0) {
//			strDataCmLab=strDataCmLab+strpad("C.M.",9*2);
			strDataCmLab=strDataCmLab+strpad("   C.M.",9*2);
//?			strDataCmLab=strDataCmLab+padstr("C.M.",9)+padstr("",9);
			strBoolCmLab+="1";
		}
		else {
			strDataCmLab=strDataCmLab+strpad("",9*2);
			strBoolCmLab+="0";
		}
		nn2*=2;
	}
	strBoolCmLabNew=strBoolCmLab;

/*	//2023-02-23:tested
	if (outC4only) {
	    outSysError=false;
	    outSysErrorPercent=false;
	    outMonData=false;
	}
*/

//str="# Prj Targ M MF MT PXC  Energy  dEnergy   Data     dData   Cos/LO   dCos/LO   LVL/HL  dLVL/HL I78 Refer (YY)              EntrySubP";
//str="#Proj Targ M MF MT PXC   Energy  dEnergy     Data    dData   Cos/LO  dCos/LO   LVL/HL dLVL/HL I78 Refer (YY)              EntrySubP";
  str="#Proj Targ M MF MT PXC   Energy  dEnergy     Data   dData Cos/LO/ZP dCos/LO/AP LVL/HL dLVL/HL I78 Refer (YY)              EntrySubP";
//if (outSysError)	str+="  dSys     dStat  ";
//if (outSysError)		str+=""+"  dSys   "+"  dStat  "+"  dTot   ";
//if (outSysErrorPercent)	str+=""+"  dSys%  "+"  dStat% "+"  dTot%  "+" dData%  ";
//if (outSysError)		str+=""+"  dSys   "+"  dStat  "+"  dPart  "+"  dTot   ";
//if (outSysErrorPercent)	str+=""+"  dSys%  "+"  dStat% "+"  dPart% "+"  dTot%  "+" dData%  ";
//if (outSysError)		str+=""+"  dSys   "+"  dStat  "+"  dMrc   "+"  dTot   ";
//if (outSysErrorPercent)	str+=""+"  dSys%  "+"  dStat% "+"  dMrc%  "+"  dTot%  "+" dData%  ";
  if (outSysError)		str+=""+"  dSys   "+"  dStat  "+" dOther  "+"  dTot   ";
  if (outSysErrorPercent)	str+=""+"  dSys%  "+"  dStat% "+" dOther% "+"  dTot%  "+" dData%  ";
//if (outMonData)		str+=""+"   M0    "+"  dM0%   "+"   M1    "+"  dM1%   ";
  if (outMonData)		str+=""+"   M0    "+"  dM0    "+"   M1    "+"  dM1    ";
	strLegend1=str;
  str="#---><---->o<-><-->ooo<-------><-------><-------><-------><-------><-------><-------><-------><-><-----------------------><---><->o";
//if (outSysError) {
//  str+="<-------><------->";
//if (outSysError)
//str+="[#dData%,dSys%,dStat%]";
//str+="[#dTot%,dSys%,dStat%]";
//str+="[#dTot(%),dSys(%),dStat(%)]";
//}
//if (outSysError)		str+="<-------><------->";
//if (outSysError)		str+="<-------><-------><------->";
  if (outSysError)		str+="<-------><-------><-------><------->";
//if (outSysErrorPercent)	str+="<-------><-------><------->";
//if (outSysErrorPercent)	str+="<-------><-------><-------><------->";
  if (outSysErrorPercent)	str+="<-------><-------><-------><-------><------->";
  if (outMonData)		str+="<-------><-------><-------><------->";
	strLegend2=str;


	prepareDataUnits();

	nRow=xreacode.getDataLY();
	return nRow;
    }

    public void prepareDataUnits() {
        x4var x4var2;
        int n;
        if (this.compVars == null) {
            return;
        }
        for (n = 0; n < 8; ++n) {
            this.c4_DataFamFlag[n] = null;
        }
        for (n = 0; n < 8; ++n) {
            this.c4_DataUnits[n] = null;
        }
        for (n = 0; n < this.compVars.length; ++n) {
            this.compVars[n].prepareCompHeader();
            x4var2 = this.compVars[n].xvar0;
            if (x4var2 == null) continue;
            String string = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
            if (n == 0) {
                string = "Data";
            }
            if (n == 0) {
                this.c4_DataFamFlag[2] = string;
                this.c4_DataUnits[2] = x4var2.BasicUnits;
                this.c4_DataUnitFamilyCode[2] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
                this.c4DataUnitFamilyY = this.c4_DataUnitFamilyCode[2];
                this.c4_DataFamFlag[3] = "d" + this.c4_DataFamFlag[2];
                this.c4_DataUnits[3] = this.c4_DataUnits[2];
                this.c4_DataUnitFamilyCode[3] = this.c4_DataUnitFamilyCode[2];
            }
            if (x4var2.numDataType1 == 1 || x4var2.numDataType1 == 2) {
                this.c4_DataFamFlag[0] = string = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
                this.c4_DataUnits[0] = x4var2.BasicUnits;
                this.c4_DataUnitFamilyCode[0] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
                if (!this.c4_DataFamFlag[0].equals("")) {
                    this.c4_DataFamFlag[1] = "d" + this.c4_DataFamFlag[0];
                    this.c4_DataUnits[1] = this.c4_DataUnits[0];
                    this.c4_DataUnitFamilyCode[1] = this.c4_DataUnitFamilyCode[0];
                }
            }
            if (x4var2.numDataType1 == 3) {
                this.c4_DataFamFlag[6] = string = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
                this.c4_i78 = string;
                if (this.xreacode != null) {
                    this.xreacode.c4_i78 = this.c4_i78;
                }
                this.c4_DataUnits[6] = x4var2.BasicUnits;
                this.c4_DataUnitFamilyCode[6] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
                if (!this.c4_DataFamFlag[6].equals("")) {
                    this.c4_DataFamFlag[7] = "d" + this.c4_DataFamFlag[6];
                    this.c4_DataUnits[7] = this.c4_DataUnits[6];
                    this.c4_DataUnitFamilyCode[7] = this.c4_DataUnitFamilyCode[6];
                }
            }
            if (x4var2.numDataType1 == 4) {
                this.c4_DataFamFlag[4] = string = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
                this.c4_DataUnits[4] = x4var2.BasicUnits;
                this.c4_DataFamFlag[4] = "COS";
                this.c4_DataUnits[4] = "NO-DIM";
                this.c4_DataUnitFamilyCode[4] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
                if (!this.c4_DataFamFlag[4].equals("")) {
                    this.c4_DataFamFlag[5] = "d" + this.c4_DataFamFlag[4];
                    this.c4_DataUnits[5] = this.c4_DataUnits[4];
                    this.c4_DataUnitFamilyCode[5] = this.c4_DataUnitFamilyCode[4];
                }
            }
            if (x4var2.numDataType1 == 5) {
                this.c4_DataFamFlag[4] = "LO";
                this.c4_DataUnits[4] = "NO-DIM";
                this.c4_DataUnitFamilyCode[4] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
            }
            if (x4var2.numDataType1 == 7) {
                this.c4_DataFamFlag[5] = "ZAProd";
                this.c4_DataUnits[5] = "NO-DIM";
                this.c4_DataUnitFamilyCode[5] = x4var2.x4d025 != null ? x4var2.x4d025.UnitFamilyCode : "";
            }
            if (this.xreacode.simpleRatio && this.xreacode.vReacs.size() == 2) {
                this.c4_DataFamFlag[5] = "ZA-Rat";
                this.c4_DataUnits[5] = "NO-DIM";
                this.c4_DataUnitFamilyCode[2] = "/";
                if (this.c4_ratioMT > 0 && this.c4_sData[4] == null) {
                    this.c4_DataFamFlag[4] = "MT-Rat";
                    this.c4_DataUnits[4] = "NO-DIM";
                }
            }
            if (this.c4_MT < 9000 || this.xreacode.xProd1 == null || this.xreacode.xProd1.Name.equals("") || this.xreacode.xProd1.za < 0) continue;
            this.c4_DataFamFlag[5] = "ZAP";
            this.c4_DataUnits[5] = "NO-DIM";
            this.c4_DataUnitFamilyCode[2] = "ZAP";
        }
        this.mf4tomf6 = false;
        int n2 = 0;
        if (debug2) {
            System.out.println("___2___#PROD=[" + this.xreacode.xreac1.SF4 + "]" + " #X14A=[" + this.xreacode.C4ReactionCode + "]" + " MF=" + this.c4_MF + " MT=" + this.c4_MT);
        }
        if (this.xreacode.xreac1.SF4.equals("0-G-0")) {
            ++n2;
        }
        if (this.c4_MF == 4) {
            ++n2;
        }
        if (this.c4_MF == 6) {
            ++n2;
        }
        if (this.c4_MT == 9000) {
            ++n2;
        }
        if (this.xreacode.C4ReactionCode.endsWith(",X)PAR,DA")) {
            ++n2;
        }
        for (n = 0; n < this.xreacode.vVars.size(); ++n) {
            x4var2 = (x4var)this.xreacode.vVars.elementAt(n);
            if (x4var2.Header.equals("E-MIN")) {
                ++n2;
            }
            if (x4var2.Header.equals("E-MAX")) {
                ++n2;
            }
            if (!debug2) continue;
            System.out.println(" " + (n + 1) + ")\t" + x4var2.Header + "\t what:" + x4var2.what + "\t whatVar:" + x4var2.whatVar + "\torder=" + x4var2.strVarOrderFlag + "\tDataType=" + x4var2.DataType + "\tFamilyCode=" + x4var2.FamilyCode + "\tvarNum=" + x4var2.Variable_Number);
        }
        for (n = 0; n < this.c4_DataFamFlag.length; ++n) {
            if (this.c4_DataFamFlag[n] == null) continue;
            if (n == 0 && this.c4_DataUnits[n].equals("EV")) {
                ++n2;
            }
            if (n == 2 && this.c4_DataUnits[n].equals("B/SR")) {
                ++n2;
            }
            if (n == 6 && this.c4_DataUnits[n].equals("EV")) {
                ++n2;
            }
            if (n == 6 && this.c4_DataFamFlag[n].equals("E2")) {
                ++n2;
            }
            if (!debug2) continue;
            System.out.println("...c4_Data[" + n + "]" + " strFamFlag=[" + this.c4_DataFamFlag[n] + "]" + " BasicUnits=[" + this.c4_DataUnits[n] + "]");
        }
        if (n2 >= 10) {
            this.mf4tomf6 = true;
        }
        if (debug2) {
            System.out.println("___2___#PROD=[" + this.xreacode.xreac1.SF4 + "]" + " #X14A=[" + this.xreacode.C4ReactionCode + "]" + " MF=" + this.c4_MF + " MT=" + this.c4_MT + " iifound=" + n2 + " mf4tomf6=" + this.mf4tomf6);
        }
    }

    public void printDataUnits() {
        for (int i = 0; i < this.c4_DataFamFlag.length; ++i) {
            if (this.c4_DataFamFlag[i] == null) continue;
            System.out.println("...c4_Data[" + i + "]" + " strFamFlag=[" + this.c4_DataFamFlag[i] + "]" + " BasicUnits=[" + this.c4_DataUnits[i] + "]");
        }
    }

    public void printCompNotes(PrintWriter printWriter) {
        if (printWriter == null) {
            return;
        }
        for (int i = 0; i < this.compVars.length; ++i) {
            for (int j = 0; j < this.compVars[i].vCompNotes.size(); ++j) {
                String string = (String)this.compVars[i].vCompNotes.elementAt(j);
                printWriter.println("\t" + string);
            }
        }
    }

    public int calcInverseReact() {
        int n = -1;
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        if (this.xreacode == null) {
            this.c4_Data[0] = null;
            this.c4_Data[1] = null;
            this.c4_Data[2] = null;
            this.c4_Data[3] = null;
            return -1;
        }
        if (this.c4_Data[0] != null) {
            d = this.c4_Data[0].floatValue();
        }
        if (this.c4_Data[1] != null) {
            d2 = this.c4_Data[1].floatValue();
        }
        if (this.c4_Data[2] != null) {
            d3 = this.c4_Data[2].floatValue();
        }
        if (this.c4_Data[3] != null) {
            d4 = this.c4_Data[3].floatValue();
        }
        this.xreacode.e4level = 0.0;
        if (this.c4_Data[6] != null) {
            this.xreacode.e4level = this.c4_Data[6].floatValue();
        }
        if ((n = this.xreacode.calcInverseReact(d, d2, d3, d4)) != 0) {
            this.c4_Data[0] = null;
            this.c4_Data[1] = null;
            this.c4_Data[2] = null;
            this.c4_Data[3] = null;
            return -1;
        }
        if (this.c4_Data[0] != null) {
            this.c4_Data[0] = new Float(this.xreacode.ENEa);
        }
        if (this.c4_Data[1] != null) {
            this.c4_Data[1] = new Float(this.xreacode.dENEa);
        }
        if (this.c4_Data[2] != null) {
            this.c4_Data[2] = new Float(this.xreacode.SIGi);
        }
        if (this.c4_Data[3] != null) {
            this.c4_Data[3] = new Float(this.xreacode.dSIGi);
        }
        for (int i = 0; i < 4; ++i) {
            if (this.c4_Data[8 + i] == null) continue;
            this.c4_Data[8 + i] = new Float((double)this.c4_Data[8 + i].floatValue() * this.xreacode.inv_mult_kk1);
        }
        return 0;
    }

    public void savePartDYs() {
        this.c4_DataSave = new Float[this.c4_Data.length];
        for (int i = 0; i < this.c4_Data.length; ++i) {
            this.c4_DataSave[i] = this.c4_Data[i] == null ? null : new Float(this.c4_Data[i].floatValue());
        }
    }

    public void restorePartDYs() {
        if (this.c4_Data[3] == null || this.c4_Data[2] == null) {
            this.c4_Data[8] = null;
            this.c4_Data[9] = null;
            this.c4_Data[10] = null;
            this.c4_Data[11] = null;
            return;
        }
        if (this.c4_Data[3].floatValue() == 0.0f || this.c4_Data[2].floatValue() == 0.0f) {
            this.c4_Data[8] = null;
            this.c4_Data[9] = null;
            this.c4_Data[10] = null;
            this.c4_Data[11] = null;
            return;
        }
        if (this.c4_DataSave[3] == null) {
            this.c4_Data[8] = null;
            this.c4_Data[9] = null;
            this.c4_Data[10] = null;
            this.c4_Data[11] = null;
            return;
        }
        double d = this.c4_Data[3].floatValue() / this.c4_DataSave[3].floatValue();
        if (this.c4_Data[8] != null) {
            this.c4_Data[8] = new Float(d * (double)this.c4_DataSave[8].floatValue());
        }
        if (this.c4_Data[9] != null) {
            this.c4_Data[9] = new Float(d * (double)this.c4_DataSave[9].floatValue());
        }
        if (this.c4_Data[10] != null) {
            this.c4_Data[10] = new Float(d * (double)this.c4_DataSave[10].floatValue());
        }
        if (this.c4_Data[11] != null) {
            this.c4_Data[11] = new Float(d * (double)this.c4_DataSave[11].floatValue());
        }
    }

    public String getC4BEGIN() {
        String string = "";
        string = string + x4c4obj.padstr("" + this.c4_zaProj, 5);
        string = string + x4c4obj.padstr("" + this.c4_zaTarg, 6);
        string = string + x4c4obj.strpad("" + this.c4_TargM, 1);
        string = string + x4c4obj.padstr("" + this.c4_MF, 3);
        string = string + x4c4obj.padstr("" + this.c4_MT, 4);
        string = string + x4c4obj.strpad("" + this.c4_ProdM, 1);
        string = string + x4c4obj.strpad("" + this.c4_X4STAT, 1);
        string = string + x4c4obj.strpad("" + this.c4_X4CM, 1);
        return string;
    }

    public int makeC4Line(int n, int n2) {
        String string;
        int n3;
        Float f;
        int n4;
        Float f2;
        x4var x4var2;
        this.iRowX4 = -1;
        this.c4str = "";
        this.xreacode.c5data.c4_MT[n2] = this.c4_MT;
        if (this.xreacode.IndepVarFamilyCode.trim().equals("?")) {
            return -3;
        }
        Vector vector = this.xreacode.vVars;
        this.DnCol = vector.size();
        this.monitVal = null;
        this.monitErr = null;
        if (this.DnRow <= 0) {
            return -4;
        }
        if (this.xreacode != null) {
            int n5;
            for (n5 = 0; n5 < this.xreacode.vMonVars.size(); ++n5) {
                x4var2 = (x4var)this.xreacode.vMonVars.elementAt(n5);
                f2 = x4var2.getValueInBasicUnits(n);
                if (this.monitVal != null && f2 != null && n5 > 0 && x4var2.BasicUnits.equals("PER-CENT")) {
                    f2 = new Float(this.monitVal.floatValue() * f2.floatValue() / 100.0f);
                }
                if (n5 == 0) {
                    this.monitVal = f2;
                }
                if (n5 != 1) continue;
                this.monitErr = f2;
            }
            for (n5 = 0; n5 < this.xreacode.vAllVars.size(); ++n5) {
                x4var2 = (x4var)this.xreacode.vAllVars.elementAt(n5);
                f2 = x4var2.getValueInBasicUnits(n);
            }
        }
        boolean bl = true;
        for (int i = 0; i < this.DnCol; ++i) {
            x4var2 = (x4var)vector.elementAt(i);
            f2 = x4var2.getValueInBasicUnits(n);
            if (f2 == null || !x4var2.isItYValue()) continue;
            bl = false;
        }
        if (bl) {
            return -5;
        }
        if (this.compVars.length > 0) {
            this.compVars[0].prepareCompValuesC5(this.compVars, n);
        }
        for (n4 = 0; n4 < this.c4_Data.length; ++n4) {
            this.c4_Data[n4] = null;
        }
        for (n4 = 0; n4 < this.c4_sData.length; ++n4) {
            this.c4_sData[n4] = null;
        }
        this.c4_Data[2] = this.compVars[0].compValue;
        this.c4_Data[3] = this.compVars[0].compError;
        this.c4_Data[8] = this.compVars[0].compErrorSys;
        this.c4_Data[9] = this.compVars[0].compErrorStat;
        this.c4_Data[10] = this.compVars[0].compErrorMrc;
        this.c4_Data[11] = this.compVars[0].compErrorGiven;
        if (this.compVars[0].compValue != null) {
            this.c4_sData[2] = x4c4obj.real2str(this.compVars[0].compValue);
        }
        if (this.compVars[0].compError != null) {
            this.c4_sData[3] = x4c4obj.real2str(this.compVars[0].compError);
        }
        if (this.compVars[0].compErrorSys != null) {
            this.c4_sData[8] = x4c4obj.real2str(this.compVars[0].compErrorSys);
        }
        if (this.compVars[0].compErrorStat != null) {
            this.c4_sData[9] = x4c4obj.real2str(this.compVars[0].compErrorStat);
        }
        if (this.compVars[0].compErrorMrc != null) {
            this.c4_sData[10] = x4c4obj.real2str(this.compVars[0].compErrorMrc);
        }
        if (this.compVars[0].compErrorGiven != null) {
            this.c4_sData[11] = x4c4obj.real2str(this.compVars[0].compErrorGiven);
        }
        this.c4_compValueSetFromCh = this.compVars[0].compValueSetFromCh;
        this.c4_i78 = "";
        for (n4 = 0; n4 < this.compVars.length; ++n4) {
            x4var2 = this.compVars[n4].xvar0;
            if (x4var2 == null) continue;
            if (x4var2.numDataType1 == 1 || x4var2.numDataType1 == 2) {
                this.c4_Data[0] = this.compVars[n4].compValue;
                this.c4_Data[1] = this.compVars[n4].compError;
                if (this.compVars[n4].compValue != null) {
                    this.c4_sData[0] = x4c4obj.real2str(this.compVars[n4].compValue.floatValue());
                }
                if (this.compVars[n4].compError != null) {
                    this.c4_sData[1] = x4c4obj.real2str(this.compVars[n4].compError);
                }
            }
            if (x4var2.numDataType1 == 3) {
                this.c4_Data[6] = this.compVars[n4].compValue;
                this.c4_Data[7] = this.compVars[n4].compError;
                if (this.compVars[n4].compValue != null) {
                    this.c4_sData[6] = x4c4obj.real2str(this.compVars[n4].compValue);
                }
                if (this.compVars[n4].compError != null) {
                    this.c4_sData[7] = x4c4obj.real2str(this.compVars[n4].compError);
                }
                this.c4_i78 = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
                if (this.compVars[n4].convDataType != null) {
                    this.c4_i78 = x4dict024dt.getDataTypeShortExpansion(this.compVars[n4].convDataType).trim();
                }
                if (this.xreacode != null) {
                    this.xreacode.c4_i78 = this.c4_i78;
                }
                x4level x4level2 = null;
                f = null;
                if (this.c4_i78.equals("LVL") && this.compVars[n4].compValueMin != null && this.compVars[n4].compValueMax.floatValue() > this.compVars[n4].compValueMin.floatValue()) {
                    this.c4_Data[6] = this.compVars[n4].compValueMin;
                    this.c4_Data[7] = this.compVars[n4].compValueMax;
                    this.c4_sData[6] = x4c4obj.real2str(this.compVars[n4].compValueMin);
                    this.c4_sData[7] = x4c4obj.real2str(this.compVars[n4].compValueMax);
                    this.xreacode.str2CompNotes("LVL: SELECT SMALLEST AND LARGEST VALUES");
                }
                boolean bl2 = false;
                int n6 = 0;
                int n7 = 0;
                if (this.c4_MT >= 51 && this.c4_MT < 91) {
                    bl2 = true;
                    n6 = 51;
                    n7 = 40;
                }
                if (this.c4_MT >= 601 && this.c4_MT < 641) {
                    bl2 = true;
                    n6 = 601;
                    n7 = 48;
                }
                if (this.c4_MT >= 651 && this.c4_MT < 649) {
                    bl2 = true;
                    n6 = 651;
                    n7 = 48;
                }
                if (this.c4_MT >= 701 && this.c4_MT < 749) {
                    bl2 = true;
                    n6 = 701;
                    n7 = 48;
                }
                if (this.c4_MT >= 751 && this.c4_MT < 799) {
                    bl2 = true;
                    n6 = 751;
                    n7 = 48;
                }
                if (this.c4_MT >= 801 && this.c4_MT < 849) {
                    bl2 = true;
                    n6 = 801;
                    n7 = 48;
                }
                boolean bl3 = false;
                if (this.xreacode != null && this.xreacode.replaceQvalue2Level && this.xreacode.reacode.indexOf("PAR") > 0 && this.xreacode.QValue1 != null && this.c4_i78.equals("QVL") && this.c4_Data[6] != null) {
                    this.c4_Data[6] = new Float(this.xreacode.QValue1 - (double)this.compVars[n4].compValue.floatValue());
                    this.c4_sData[6] = x4c4obj.real2str(this.c4_Data[6]);
                    this.xreacode.c4_i78 = this.c4_i78 = "LVL";
                    this.xreacode.str2CompNotes("SET LVL=Qvalue.GS-Qvalue;  #Qvalue.GS=" + this.xreacode.QValue1.floatValue() * 1000000.0f + " (eV)");
                }
                if (this.c4_Data[6] != null) {
                    if (this.c4_Data[7] == null) {
                        bl3 = true;
                    } else if (this.c4_Data[6].floatValue() >= this.c4_Data[7].floatValue()) {
                        bl3 = true;
                    }
                }
                if (this.xreacode.flagMT51to5ilvl && this.c4_i78.equals("LVL") && bl3 && bl2 && (x4level2 = x4level.readLevelsFile(this.xreacode.xProd1.zz, this.xreacode.xProd1.aa)) != null && x4level2.levels.size() > 1) {
                    int n8 = x4level2.findLevelKeV(this.c4_Data[6].floatValue() / 1000.0f);
                    if (n8 >= 0) {
                        int n9 = n8;
                        if (n8 > n7) {
                            n8 = n7;
                        }
                        this.xreacode.c5data.c4_MT[n2] = n6 - 1 + n8;
                        this.xreacode.str2CompNotes("RESET MT" + n6 + " to MT" + (n6 - 1 + n8) + ": ZAP=" + (this.xreacode.xProd1.zz * 1000 + this.xreacode.xProd1.aa) + " iLvl=" + x4c4obj.strpad("" + n9, 3) + " eLvl=" + (float)(x4level2.lvlfound * 1000.0) + " eV");
                    }
                    if (extDebug) {
                        System.out.println(" Elem=[" + x4level2.Elem + "]" + " Z=" + x4level2.ZZ + " A=" + x4level2.AA + " c4_MT=" + this.c4_MT + " E-LVL:" + this.c4_Data[6].floatValue() / 1000.0f + "keV" + " iLevel=" + n8 + " Levels:" + x4level2.levels.size() + "");
                    }
                }
            }
            if (x4var2.numDataType1 == 4) {
                if (this.compVars[n4].compValue == null) continue;
                float f3 = this.compVars[n4].compValue.floatValue();
                float f4 = this.myCos(f3);
                this.c4_Data[4] = Float.valueOf(f4);
                this.c4_sData[4] = x4c4obj.real2str(f4);
                if (this.compVars[n4].compError != null) {
                    float f5 = this.compVars[n4].compError.floatValue();
                    float f6 = Math.abs(this.myCos(f3 - f5) - f4);
                    if (extDebug) {
                        System.out.println("___an=" + f3 + " da=" + f5 + " cosa:" + f4 + "=" + (float)this.cos2ang(f4) + " dcosa:" + f6 + "=" + (float)this.dcos2ang(f4, f6));
                    }
                    this.c4_Data[5] = Float.valueOf(f6);
                    this.c4_sData[5] = x4c4obj.real2str(f6);
                }
            }
            if (x4var2.numDataType1 == 5) {
                if (this.compVars[n4].compValue == null) continue;
                if (this.compVars[n4].compValue != null) {
                    this.c4_Data[4] = this.compVars[n4].compValue;
                    this.c4_sData[4] = x4c4obj.real2str(this.compVars[n4].compValue);
                    float f7 = this.compVars[n4].compValue.floatValue();
                    f7 = f7 >= 0.0f ? 2.0f * f7 + 1.0f : 1.0f;
                }
                if (this.compVars[n4].compError != null) {
                    this.c4_Data[5] = this.compVars[n4].compError;
                }
                if (this.compVars[n4].compError != null) {
                    this.c4_sData[5] = x4c4obj.real2str(this.compVars[n4].compError);
                }
            }
            if (x4var2.numDataType1 == 6) {
                if (this.c4_Data[6] == null) {
                    if (this.compVars[n4].compValue == null) continue;
                    if (extDebug) {
                        System.out.println(n4 + "___compVars[ii].compValue=" + this.compVars[n4].compValue.floatValue());
                    }
                    this.c4_Data[6] = this.compVars[n4].compValue;
                    this.c4_Data[7] = this.compVars[n4].compError;
                    if (this.compVars[n4].compValue != null) {
                        this.c4_sData[6] = x4c4obj.real2str(this.compVars[n4].compValue);
                    }
                    if (this.compVars[n4].compError != null) {
                        this.c4_sData[7] = x4c4obj.real2str(this.compVars[n4].compError);
                    }
                    this.c4_i78 = x4dict024dt.getDataTypeShortExpansion(x4var2.DataType);
                    if (this.compVars[n4].convDataType != null) {
                        this.c4_i78 = x4dict024dt.getDataTypeShortExpansion(this.compVars[n4].convDataType).trim();
                    }
                    if (this.xreacode != null) {
                        this.xreacode.c4_i78 = this.c4_i78;
                    }
                    if (extDebug) {
                        System.out.println(n4 + "___compVars[ii].compValue=" + this.compVars[n4].compValue.floatValue() + " c4_i78=[" + this.c4_i78 + "]");
                    }
                    this.xreacode.str2CompNotes("STORE DATA FOR I78:[" + this.c4_i78 + "]" + " " + x4dict024dt.getDataTypeExpansion(x4var2.DataType));
                } else {
                    this.xreacode.str2CompNotes("CANNOT STORE DATA FOR [" + x4dict024dt.getDataTypeShortExpansion(x4var2.DataType) + "]" + " " + x4dict024dt.getDataTypeExpansion(x4var2.DataType));
                }
            }
            if (x4var2.numDataType1 != 7 || this.compVars[n4].compValue == null) continue;
            this.c4_ProdM = this.compVars[n4].cmeta;
            this.c4_decayFlag = this.compVars[n4].decayFlag;
            if (this.xreacode.ReactionType.equals("FY")) {
                if (this.compVars[n4].aNucl != null) {
                    this.c4_Data[5] = Float.valueOf(0.0f + (float)this.compVars[n4].iMass);
                    this.c4_sData[5] = " " + x4c4obj.strpad(this.compVars[n4].iMass + ".", 8, '0');
                    if (this.xreacode != null && this.xreacode.c4obj_c5wrk != null) {
                        this.xreacode.c4obj_c5wrk.c4_DataFamFlag[5] = "AProd";
                        this.xreacode.c4obj_c5wrk.c4_DataUnits[5] = "NO-DIM";
                        this.xreacode.c4obj_c5wrk.c4_DataUnitFamilyCode[5] = "";
                    }
                }
                if (this.compVars[n4].zNucl == null) continue;
                this.c4_Data[4] = Float.valueOf(0.0f + (float)this.compVars[n4].iElement);
                this.c4_sData[4] = " " + x4c4obj.strpad(this.compVars[n4].iElement + ".", 8, '0');
                if (this.xreacode == null || this.xreacode.c4obj_c5wrk == null) continue;
                this.xreacode.c4obj_c5wrk.c4_DataFamFlag[4] = "ZProd";
                this.xreacode.c4obj_c5wrk.c4_DataUnits[4] = "NO-DIM";
                this.xreacode.c4obj_c5wrk.c4_DataUnitFamilyCode[4] = "";
                continue;
            }
            if (this.c4_sData[5] != null) continue;
            this.c4_sData[5] = "" + (this.compVars[n4].iElement * 1000 + this.compVars[n4].iMass) + ".9";
            this.c4_Data[5] = Float.valueOf((float)(this.compVars[n4].iElement * 1000 + this.compVars[n4].iMass) + 0.9f);
        }
        if (this.xreacode.simpleRatio && this.xreacode.vReacs.size() == 2) {
            x4reacstr x4reacstr2;
            this.xreacstrRatioDenom = x4reacstr2 = (x4reacstr)this.xreacode.vReacs.elementAt(1);
            if (this.c4_sData[5] == null) {
                this.c4_sData[5] = "" + x4reacstr2.xTarg.za + ".9";
            }
            if (this.c4_Data[5] == null) {
                this.c4_Data[5] = Float.valueOf((float)x4reacstr2.xTarg.za + 0.9f);
            }
            if (this.c4_ratioMT > 0 && this.c4_sData[4] == null) {
                this.c4_sData[4] = "" + this.c4_ratioMT + ".9";
                this.c4_Data[4] = Float.valueOf((float)this.c4_ratioMT + 0.9f);
            }
        }
        if (this.c4_MF > 200 && this.xreacode.xProd1 != null && !this.xreacode.xProd1.Name.equals("") && this.xreacode.xProd1.za >= 0 && this.c4_sData[5] == null && !this.outC4only) {
            this.c4_sData[5] = "" + this.xreacode.xProd1.za + this.xreacode.xProd1.dmeta;
            this.c4_Data[5] = Float.valueOf(0.0f + (float)this.xreacode.xProd1.za + (float)this.str2int(this.xreacode.xProd1.dmeta, 0));
        }
        if (this.c4_MT >= 9000 && this.xreacode.xProd1 != null && !this.xreacode.xProd1.Name.equals("") && this.xreacode.xProd1.za >= 0 && this.c4_sData[5] == null && !this.outC4only) {
            if (!(this.c4_MT != 9000 && this.c4_MT != 9001 || this.xreacode.xreac1.SF4.equals(""))) {
                this.c4_sData[5] = "" + this.xreacode.xProd1.za + ".9";
                this.c4_Data[5] = Float.valueOf((float)this.xreacode.xProd1.za + 0.9f);
            } else {
                switch (this.c4_MT % 9000) {
                    case 1: {
                        this.c4_sData[5] = "1.9";
                        this.c4_Data[5] = Float.valueOf(1.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " NEUTRON PRODUCTION");
                        break;
                    }
                    case 2: {
                        this.c4_sData[5] = "1001.9";
                        this.c4_Data[5] = Float.valueOf(1001.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " PROTON PRODUCTION");
                        break;
                    }
                    case 3: {
                        this.c4_sData[5] = "1002.9";
                        this.c4_Data[5] = Float.valueOf(1002.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " DEUTERON PRODUCTION");
                        break;
                    }
                    case 4: {
                        this.c4_sData[5] = "1003.9";
                        this.c4_Data[5] = Float.valueOf(1003.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " TRITON PRODUCTION");
                        break;
                    }
                    case 5: {
                        this.c4_sData[5] = "2003.9";
                        this.c4_Data[5] = Float.valueOf(2003.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " HE3 PRODUCTION");
                        break;
                    }
                    case 6: {
                        this.c4_sData[5] = "2004.9";
                        this.c4_Data[5] = Float.valueOf(1001.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " ALPHA PRODUCTION");
                        break;
                    }
                    default: {
                        this.c4_sData[5] = "0.9";
                        this.c4_Data[5] = Float.valueOf(0.9f);
                        this.xreacode.str2CompNotes("MT=" + this.c4_MT + " SET ZAP=" + this.c4_sData[5] + " GAMMA PRODUCTION");
                    }
                }
            }
        }
        for (n3 = 0; n3 < this.compVars.length; ++n3) {
            x4var2 = this.compVars[n3].xvar0;
            if (x4var2 != null) continue;
        }
        if (this.xreacode.xreac1 != null && this.xreacode.xreac1.SF3.equals("F") && this.c4_sData[4] == null && this.c4_sData[5] == null && this.xreacode.xProd1 != null && !this.xreacode.xProd1.Name.equals("") && this.xreacode.xProd1.za >= 0) {
            this.c4_Data[4] = new Float(this.xreacode.xProd1.zz);
            this.c4_sData[4] = "" + this.xreacode.xProd1.zz;
            this.c4_Data[5] = new Float(this.xreacode.xProd1.aa);
            this.c4_sData[5] = "" + this.xreacode.xProd1.aa;
        }
        if (this.c4_OpNum == 4 && this.c4_Data[4] != null && this.c4_Data[2] != null) {
            this.c4_Data[2] = new Float(this.c4_Data[2].floatValue() / (this.c4_Data[4].floatValue() * 2.0f + 1.0f));
            this.c4_sData[2] = x4c4obj.real2str(this.c4_Data[2]);
            if (this.c4_Data[3] != null) {
                this.c4_Data[3] = new Float(this.c4_Data[3].floatValue() / (this.c4_Data[4].floatValue() * 2.0f + 1.0f));
                this.c4_sData[3] = x4c4obj.real2str(this.c4_Data[3]);
            }
            this.xreacode.str2CompNotes("OPERATION DEFINED:            DATA=DATA/(2*L+1)");
        }
        if (this.xreacode.ReactionType.equals("RE") && this.c4_sData[2] != null && this.c4_sData[0] == null) {
            this.c4_sData[0] = this.c4_sData[2];
            this.c4_sData[1] = this.c4_sData[3];
            this.c4_Data[0] = this.c4_Data[2];
            this.c4_Data[1] = this.c4_Data[3];
            this.xreacode.str2CompNotes("OPERATION...  ENERGY=DATA(Resonance Energy)");
        }
        if (this.mf4tomf6) {
            if (debug2) {
                System.out.println("___2___ Data:" + this.c4_Data[2] + "," + this.c4_Data[2] + " E:" + this.c4_Data[6] + "," + this.c4_Data[7] + " c4_MF=" + this.c4_MF + " c4_MT=" + this.c4_MT);
            }
            if (this.c4_Data[7] != null) {
                float f8 = this.c4_Data[7].floatValue();
                if (this.c4_Data[2] != null) {
                    this.c4_sData[2] = x4c4obj.real2str(this.c4_Data[2].floatValue() / f8);
                }
                if (this.c4_Data[3] != null) {
                    this.c4_sData[3] = x4c4obj.real2str(this.c4_Data[3].floatValue() / f8);
                }
                if (this.outSysError) {
                    for (n3 = 0; n3 < 4; ++n3) {
                        string = this.c4_sData[8 + n3];
                        if (string == null || (f = this.str2Float(string)) == null) continue;
                        this.c4_sData[8 + n3] = x4c4obj.real2str(f.floatValue() / f8);
                    }
                }
            }
        }
        this.c4_sData5inv = "" + this.c4_zaTarg;
        String string2 = "";
        this.c4str = "";
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_zaProj, 5);
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_zaTarg, 6);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_TargM, 1);
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_MF, 3);
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_MT, 4);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_ProdM, 1);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_X4STAT, 1);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_X4CM, 1);
        for (n3 = 0; n3 < 8; ++n3) {
            string = this.c4_sData[n3];
            if (string == null) {
                string = "";
            }
            this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
        }
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_i78, 3);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_Refer, 25);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_SubentP, 9);
        if (this.outSysError) {
            for (n3 = 0; n3 < 4; ++n3) {
                string = this.c4_sData[8 + n3];
                if (string == null) {
                    string = "";
                }
                this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
            }
        }
        if (this.out_iRow && n != n2) {
            this.iRowX4 = n + 1;
            string = "#" + (n + 1);
            this.c4str = this.c4str + string;
        }
        String string3 = this.getC5ext(this.c4_Data);
        if (this.out_iRow && this.c4str.length() > 131 && this.c4str.substring(131).trim().equals("")) {
            this.c4str = string = this.c4str.substring(0, 131);
        }
        return 0;
    }

    public String getC5extFree(Float f, Float f2, Float f3, Float f4, Float f5, Float f6) {
        String string = "";
        String string2 = "";
        string = " # " + x4c4obj.strpad(x4c4obj.strPercent(f3, f), 5);
        string2 = string2 + string;
        string = "," + x4c4obj.strpad(x4c4obj.strPercent(f4, f), 5);
        string2 = string2 + string;
        string = "," + x4c4obj.strpad(x4c4obj.strPercent(f5, f), 5);
        string2 = string2 + string;
        string = "," + x4c4obj.strpad(x4c4obj.strPercent(f6, f), 5);
        string2 = string2 + string;
        string = "," + x4c4obj.strpad(x4c4obj.strPercent(f2, f), 5);
        string2 = string2 + string;
        return string2;
    }

    public String getC5extFixed(Float f, Float f2, Float f3, Float f4, Float f5, Float f6) {
        String string = "";
        String string2 = "";
        string = "" + this.strPercent(f3, f, 9);
        string2 = string2 + string;
        string = "" + this.strPercent(f4, f, 9);
        string2 = string2 + string;
        string = "" + this.strPercent(f5, f, 9);
        string2 = string2 + string;
        string = "" + this.strPercent(f6, f, 9);
        string2 = string2 + string;
        string = "" + this.strPercent(f2, f, 9);
        string2 = string2 + string;
        return string2;
    }

    public String getC5ext(Float f, Float f2, Float f3, Float f4, Float f5, Float f6) {
        if (this.flag_fixed_widthC5) {
            return this.getC5extFixed(f, f2, f3, f4, f5, f6);
        }
        return this.getC5extFree(f, f2, f3, f4, f5, f6);
    }

    public String getC5ext(Float[] floatArray) {
        return this.getC5ext(floatArray[2], floatArray[3], floatArray[8], floatArray[9], floatArray[10], floatArray[11]);
    }

    public String strPercent(Float f, Float f2, int n) {
        int n2;
        String string = x4c4obj.strPercent(f, f2);
        if (!string.equals("") && (n2 = string.length()) < n) {
            string = " " + string;
        }
        string = x4c4obj.strpad(string, n);
        return string;
    }

    public static String strPercent(Float f, Float f2) {
        String string = "";
        if (f2 == null) {
            return string;
        }
        if (f == null) {
            return string;
        }
        if (f2.floatValue() < 0.0f) {
            f2 = Float.valueOf(-f2.floatValue());
        }
        if (f.floatValue() < 0.0f) {
            f2 = Float.valueOf(-f2.floatValue());
        }
        if (f2.floatValue() > 0.0f) {
            f = Float.valueOf(f.floatValue() * 100.0f / f2.floatValue());
            string = String.format("%.2f", f);
            if (string.indexOf(".") >= 2) {
                string = String.format("%.1f", f);
            }
            if (string.equals("0.0")) {
                string = String.format("%.2f", f);
            }
            if (string.equals("0.00")) {
                string = String.format("%.3f", f);
            }
            if (string.endsWith(".0")) {
                string = string.substring(0, string.length() - 2);
            }
            if (string.endsWith(".00")) {
                string = string.substring(0, string.length() - 3);
            }
            if (string.endsWith(".000")) {
                string = string.substring(0, string.length() - 4);
            }
        }
        if (string.equals("0")) {
            string = "";
        }
        return string;
    }

    public int importC4objFrom2array(c5arr c5arr2, int n) {
        for (int i = 0; i < 13; ++i) {
            this.c4_Data[i] = c5arr2.c4data[n][i];
            this.c4_sData[i] = x4c4obj.real2str(this.c4_Data[i]);
        }
        if (this.c4_Data[0] != null) {
            if (this.enMin == null) {
                this.enMin = this.c4_Data[0];
            } else if (this.c4_Data[0].floatValue() < this.enMin.floatValue()) {
                this.enMin = new Float(this.c4_Data[0].floatValue());
            }
            if (this.enMax == null) {
                this.enMax = this.c4_Data[0];
            } else if (this.c4_Data[0].floatValue() > this.enMax.floatValue()) {
                this.enMax = new Float(this.c4_Data[0].floatValue());
            }
        }
        String string = this.getC5ext(this.c4_Data);
        if (this.out_iRow) {
            this.iRowX4 = -1;
            Float f = c5arr2.getX4iRow(n);
            if (f != null) {
                this.iRowX4 = (int)f.floatValue();
            }
        }
        return 0;
    }

    public int exportC4obj2array(c5arr c5arr2, int n) {
        for (int i = 0; i < 13; ++i) {
            c5arr2.c4data[n][i] = this.c4_Data[i];
            this.c4_sData[i] = x4c4obj.real2str(this.c4_Data[i]);
        }
        return 0;
    }

    public int makeC4LineFrom2array(c5arr c5arr2, int n) {
        Object object;
        String string;
        int n2;
        char c = ' ';
        char c2 = '1';
        boolean bl = true;
        this.c4_sData5inv = "" + this.c4_zaTarg;
        String string2 = "";
        this.c4str = "";
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_zaProj, 5);
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_zaTarg, 6);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_TargM, 1);
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_MF, 3);
        this.c4str = this.c4str + x4c4obj.padstr("" + c5arr2.c4_MT[n], 4);
        c = this.c4_ProdM;
        if (this.xreacode != null) {
            if (this.xreacode.c4data_cmetaProd != null && this.xreacode.c4data_cmetaProd.length >= n) {
                c = this.xreacode.c4data_cmetaProd[n];
            }
            if (this.xreacode.c4data_compValueSetFromCh != null && this.xreacode.c4data_compValueSetFromCh.length >= n) {
                c2 = this.xreacode.c4data_compValueSetFromCh[n];
            }
        }
        this.c4str = this.c4str + x4c4obj.strpad("" + c, 1);
        if (bl && c2 != '6' && c2 != '1') {
            this.c4str = "#" + this.c4str.substring(1);
        }
        char c3 = this.c4_X4STAT;
        if (c2 != '1') {
            c3 = c2;
        }
        this.c4str = this.c4str + x4c4obj.strpad("" + c3, 1);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_X4CM, 1);
        for (n2 = 0; n2 < 8; ++n2) {
            this.c4_Data[n2] = c5arr2.c4data[n][n2];
            this.c4_sData[n2] = x4c4obj.real2str(this.c4_Data[n2]);
            string = x4c4obj.real2str(c5arr2.c4data[n][n2]);
            if (string == null) {
                string = "";
            }
            this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
        }
        this.c4str = this.c4str + x4c4obj.padstr("" + this.xreacode.c4_i78, 3);
        String string3 = this.c4_Refer;
        this.c4str = this.c4str + x4c4obj.strpad("" + string3, 25);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_SubentP, 9);
        if (this.outSysError) {
            for (n2 = 0; n2 < 4; ++n2) {
                this.c4_sData[8 + n2] = x4c4obj.real2str(c5arr2.c4data[n][8 + n2]);
                string = x4c4obj.real2str(c5arr2.c4data[n][8 + n2]);
                if (string == null) {
                    string = "";
                }
                this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
            }
        }
        if (this.out_iRow) {
            this.iRowX4 = -1;
            object = c5arr2.getX4iRow(n);
            if (object != null) {
                this.iRowX4 = (int)((Float)object).floatValue();
            }
            if (this.iRowX4 > 0) {
                string = "#" + this.iRowX4;
                this.c4str = this.c4str + string;
            }
        }
        if (this.out_iRow && this.c4str.length() > 131 && this.c4str.substring(131).trim().equals("")) {
            this.c4str = string = this.c4str.substring(0, 131);
        }
        object = this.getC5ext(c5arr2.c4data[n]);
        this.c4str = this.c4str + (String)object;
        this.nowProd = "";
        if (this.xreacode.IndepVarFamilyCode.indexOf("7") >= 0 && this.xreacode.ReactionType.equals("FY") && c5arr2.c4data[n][4] != null && c5arr2.c4data[n][5] != null) {
            this.nowProd = x4cvar.zaToIsotope(c5arr2.c4data[n][4], c5arr2.c4data[n][5], ' ', false);
            if (c != ' ') {
                this.nowProd = this.nowProd + c;
            }
        }
        return 0;
    }

    public int[] sortC4LineFrom2array(c5arr c5arr2) {
        int n = c5arr2.c4data.length;
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = i;
        }
        if (this.xreacode == null) {
            return nArray;
        }
        if (!this.xreacode.flagSort) {
            return nArray;
        }
        int n2 = this.xreacode.c4_MF;
        x4sort3 x4sort32 = null;
        if (n2 == 3 || n2 == 203) {
            x4sort32 = new x4sort3();
            nArray = x4sort32.sortArray3(c5arr2.c4data, 6, 0, 0);
        }
        if (n2 == 4 || n2 == 204) {
            x4sort32 = new x4sort3();
            nArray = x4sort32.sortArray3(c5arr2.c4data, 6, 0, 4);
        }
        if (n2 == 5 || n2 == 205) {
            x4sort32 = new x4sort3();
            nArray = x4sort32.sortArray3(c5arr2.c4data, 4, 0, 6);
        }
        if (n2 == 6 || n2 == 206) {
            x4sort32 = new x4sort3();
            nArray = x4sort32.sortArray3(c5arr2.c4data, 0, 4, 6);
        }
        if (x4sort32 != null && x4sort32.orderChanged) {
            this.xreacode.str2CompNotes("........SORTED........pts:" + x4sort32.allPts + ", mv:" + x4sort32.movedPts + ", time:" + x4sort32.getDt());
        }
        return nArray;
    }

    public static int[] sortArray1(Float[][] floatArray, int n) {
        int n2;
        int n3 = floatArray.length;
        int[] nArray = new int[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            nArray[n2] = n2;
        }
        int n4 = -1;
        if (n == 4) {
            n4 = 1;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            int n5 = n2;
            for (int i = n2 + 1; i < n3; ++i) {
                if (x4c4obj.myCmpFloat(floatArray[nArray[i]][n], floatArray[nArray[n5]][n]) != n4) continue;
                n5 = i;
            }
            int n6 = nArray[n5];
            nArray[n5] = nArray[n2];
            nArray[n2] = n6;
        }
        return nArray;
    }

    public static int myCmpFloat(Float f, Float f2) {
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

    public static int[] sortArray2(Float[][] floatArray, int n, int n2) {
        int n3;
        int n4 = floatArray.length;
        int[] nArray = new int[n4];
        for (n3 = 0; n3 < n4; ++n3) {
            nArray[n3] = n3;
        }
        int n5 = -1;
        int n6 = -1;
        if (n == 4) {
            n5 = 1;
        }
        if (n2 == 4) {
            n6 = 1;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            int n7 = n3;
            for (int i = n3 + 1; i < n4; ++i) {
                if (x4c4obj.myCmpFloat(floatArray[nArray[i]][n], floatArray[nArray[n7]][n]) == n5) {
                    n7 = i;
                    continue;
                }
                if (x4c4obj.myCmpFloat(floatArray[nArray[i]][n], floatArray[nArray[n7]][n]) != 0 || x4c4obj.myCmpFloat(floatArray[nArray[i]][n2], floatArray[nArray[n7]][n2]) != n6) continue;
                n7 = i;
            }
            int n8 = nArray[n7];
            nArray[n7] = nArray[n3];
            nArray[n3] = n8;
        }
        return nArray;
    }

    public static int[] sortArray3(Float[][] floatArray, int n, int n2, int n3) {
        int n4;
        int n5 = floatArray.length;
        int[] nArray = new int[n5];
        for (n4 = 0; n4 < n5; ++n4) {
            nArray[n4] = n4;
        }
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = -1;
        int n10 = -1;
        int n11 = -1;
        if (n == 4) {
            n9 = 1;
        }
        if (n2 == 4) {
            n10 = 1;
        }
        if (n3 == 4) {
            n11 = 1;
        }
        for (n4 = 0; n4 < n5; ++n4) {
            int n12 = n4;
            for (int i = n4 + 1; i < n5; ++i) {
                n6 = x4c4obj.myCmpFloat(floatArray[nArray[i]][n], floatArray[nArray[n12]][n]);
                if (n6 == n9) {
                    n12 = i;
                    continue;
                }
                if (n6 != 0) continue;
                n7 = x4c4obj.myCmpFloat(floatArray[nArray[i]][n2], floatArray[nArray[n12]][n2]);
                if (n7 == n10) {
                    n12 = i;
                    continue;
                }
                if (n7 != 0 || n2 == n3 || (n8 = x4c4obj.myCmpFloat(floatArray[nArray[i]][n3], floatArray[nArray[n12]][n3])) != n11) continue;
                n12 = i;
            }
            if (n12 == n4) continue;
            int n13 = nArray[n12];
            nArray[n12] = nArray[n4];
            nArray[n4] = n13;
        }
        return nArray;
    }

    public int remakeC4Line(int n, int n2, x4c4obj x4c4obj2) {
        String string;
        int n3;
        boolean bl = true;
        if (x4c4obj2 == null) {
            x4c4obj2 = this;
            bl = false;
        }
        String string2 = "";
        this.c4str = "";
        this.c4str = this.c4str + x4c4obj.padstr("" + x4c4obj2.c4_zaProj, 5);
        this.c4str = this.c4str + x4c4obj.padstr("" + x4c4obj2.c4_zaTarg, 6);
        this.c4str = this.c4str + x4c4obj.strpad("" + x4c4obj2.c4_TargM, 1);
        this.c4str = this.c4str + x4c4obj.padstr("" + x4c4obj2.c4_MF, 3);
        this.c4str = this.c4str + x4c4obj.padstr("" + x4c4obj2.c4_MT, 4);
        this.c4str = this.c4str + x4c4obj.strpad("" + x4c4obj2.c4_ProdM, 1);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_X4STAT, 1);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_X4CM, 1);
        for (n3 = 0; n3 < 8; ++n3) {
            string = x4c4obj.real2str(this.c4_Data[n3]);
            if (string == null) {
                string = "";
            }
            this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
        }
        this.c4str = this.c4str + x4c4obj.padstr("" + this.c4_i78, 3);
        this.c4str = this.c4str + x4c4obj.strpad("" + x4c4obj2.c4_Refer, 25);
        this.c4str = this.c4str + x4c4obj.strpad("" + this.c4_SubentP, 9);
        if (this.outSysError) {
            for (n3 = 0; n3 < 4; ++n3) {
                string = this.c4_sData[8 + n3];
                if (string == null) {
                    string = "";
                }
                this.c4str = this.c4str + x4c4obj.padstr("" + string, 9);
            }
        }
        String string3 = this.getC5ext(this.c4_Data);
        this.c4str = this.c4str + string3;
        if (this.out_iRow && n != n2) {
            string = "#" + (n + 1);
            this.c4str = this.c4str + string;
        }
        if (this.out_iRow && this.c4str.length() > 131 && this.c4str.substring(131).trim().equals("")) {
            this.c4str = string = this.c4str.substring(0, 131);
        }
        return 0;
    }

    public float myCos(float f) {
        if (f == 90.0f) {
            return 0.0f;
        }
        return (float)Math.cos((double)f * Math.PI / 180.0);
    }

    public double ang2cos(double d) {
        if (d == 90.0) {
            return 0.0;
        }
        return Math.cos(d * Math.PI / 180.0);
    }

    public double dang2cos(double d, double d2) {
        double d3 = this.ang2cos(d);
        double d4 = Math.abs(this.ang2cos(d - d2) - d3);
        return d4;
    }

    public double cos2ang(double d) {
        if (d == 0.0) {
            return 90.0;
        }
        return Math.acos(d) / Math.PI * 180.0;
    }

    public double dcos2ang(double d, double d2) {
        double d3;
        double d4;
        double d5 = Math.acos(d) / Math.PI * 180.0;
        double d6 = d + d2;
        if (d6 > 1.0) {
            d6 = 1.0;
        }
        if ((d4 = d5 - (d3 = Math.acos(d6) / Math.PI * 180.0)) < 0.0) {
            d4 = 180.0 + d4;
        }
        return d4;
    }

    public Float[] getDataArray() {
        for (int i = 0; i < 13; ++i) {
            this.c4_Data[i] = this.str2Float(this.c4_sData[i]);
        }
        return this.c4_Data;
    }

    public static String real2str(Float f) {
        String string;
        if (f == null) {
            return "";
        }
        if (f.floatValue() >= 0.0f && (string = "" + f).length() <= 9) {
            return string;
        }
        return x4c4obj.real2str(f.floatValue());
    }

    public static String real2str(float f) {
        String string;
        int n;
        String string2 = x4c4obj.float2str0(f = x4c4obj.float2float(f));
        int n2 = string2.length();
        if (n2 <= 9) {
            return string2;
        }
        if (string2.startsWith("0.")) {
            string2 = string2.substring(1);
        } else if (string2.startsWith("-0.")) {
            string2 = "-" + string2.substring(2);
        }
        n2 = string2.length();
        if (n2 <= 9) {
            return string2;
        }
        if (n2 > 9) {
            n2 = 20;
        }
        if ((n = (string = x4c4obj.double2str(f)).length()) < n2) {
            string2 = string;
            n2 = n;
        }
        if (n2 > 9) {
            n2 = 20;
        }
        if ((n = (string = x4c4obj.float2str(f)).length()) < n2) {
            string2 = string;
            n2 = n;
        }
        return string2;
    }

    public static String float2str0(float f) {
        String string = "";
        string = "" + f;
        if (string.endsWith(".0")) {
            string = x4c4obj.myStrReplace(string, ".0", ".");
        }
        return string;
    }

    public static String double2str(float f) {
        int n;
        String string = "";
        string = String.format("%g", Float.valueOf(f));
        string = x4c4obj.myStrReplace(string, "00000e", "e");
        string = x4c4obj.myStrReplace(string, "0000e", "e");
        string = x4c4obj.myStrReplace(string, "000e", "e");
        string = x4c4obj.myStrReplace(string, "00e", "e");
        if ((string = x4c4obj.myStrReplace(string, "0e", "e")).indexOf(".") >= 0 && string.indexOf("e") < 0) {
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

    public static String float2str(float f) {
        String string = "";
        string = x4c4obj.float2str("%9.4e", f);
        if (string.length() > 9 && (string = x4c4obj.float2str("%9.3e", f)).length() > 9) {
            string = x4c4obj.float2str("%9.2e", f);
        }
        return string;
    }

    public static String float2str(String string, float f) {
        String string2 = "";
        string2 = String.format(string, Float.valueOf(f));
        string2 = x4c4obj.myStrReplace(string2, "e+0", "+");
        string2 = x4c4obj.myStrReplace(string2, "e+", "+");
        string2 = x4c4obj.myStrReplace(string2, "e-0", "-");
        int n = (string2 = x4c4obj.myStrReplace(string2, "e-", "-")).indexOf(".");
        if (n < 0 && string2.indexOf("e") < 0) {
            string2 = string2 + ".";
        }
        return string2;
    }

    public static double float2double(float f) {
        double d = 0.0;
        String string = String.format("%e", Float.valueOf(f));
        try {
            d = Double.parseDouble(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return d;
    }

    public static float float2float(float f) {
        double d = 0.0;
        String string = String.format("%e", Float.valueOf(f));
        try {
            d = Double.parseDouble(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return (float)d;
    }

    public int str2int(String string, int n) {
        int n2 = n;
        if (string == null) {
            return n2;
        }
        try {
            n2 = Integer.parseInt(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
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
            string2 = x4c4obj.myStrReplace(string2, " +", "E+");
            string2 = x4c4obj.myStrReplace(string2, " -", "E-");
            string2 = x4c4obj.myStrReplace(string2, " ", "");
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
        String string2 = x4c4obj.strpad(string, n, false);
        return string2;
    }

    public static String strpad(String string, int n, char c) {
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
            string3 = string3 + c;
        }
        return string3;
    }

    public static String strpad(String string, int n, boolean bl) {
        int n2 = string.length();
        if (n2 == n) {
            return string;
        }
        if (n2 > n) {
            String string2 = bl ? string.substring(0, n) : string;
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

    public static String padstr(String string, int n, char c) {
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
            string3 = string3 + c;
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
}
