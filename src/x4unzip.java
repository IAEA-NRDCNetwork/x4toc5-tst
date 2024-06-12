package zvv.x4;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.sql.*;
import java.text.*;

/**
 * Unzip object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-05-25
 * @since           2013-05-02
 *
 * Program:         x4unzip.java
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

public class x4unzip
{

    boolean extDebug=false;

    public static void main(String args[])
    {
	int i,ii;
	String str;
	Vector files,dirs;
	String zipFileName="xx.zip";
	System.out.println("Hello!!");
	x4unzip x4zip=new x4unzip();
        if (args.length<1) {
            System.out.println(
            "Run:  $ java x4unzip file.zip");
            System.exit(0);
        }
	i=0;
        if (i<args.length) zipFileName=args[i++];
	System.out.println(" zipFileName=["+zipFileName+"]");
	str=x4zip.get1stFileName(zipFileName,true);
	System.out.println(" 1stFileName=["+str+"]");
	x4zip.unzipFile(zipFileName,str,"aa.aa");

	files=x4zip.getFileNames(zipFileName);
	files=x4zip.filterNames(files,"*.txt");
	files=x4zip.filterNames(files,"C-12*");
	for (ii=0; ii<files.size(); ii++) {
	    str=(String)files.elementAt(ii);
	    System.out.println(ii+") FileName=["+str+"]");
	}

	dirs=x4zip.getDirNames(zipFileName);
	for (ii=0; ii<dirs.size(); ii++) {
	    str=(String)dirs.elementAt(ii);
	    System.out.println(ii+") DirName=["+str+"]");
	}
	dirs=x4zip.sort(dirs);
	for (ii=0; ii<dirs.size(); ii++) {
	    str=(String)dirs.elementAt(ii);
	    System.out.println(ii+") DirName=["+str+"]");
	}

	str=x4zip.get1stFileName(zipFileName,true);
	System.out.println(" 1stFileName=["+str+"]");
//	x4zip.unzipFile(zipFileName,str,"aa.aa");
//	Vector strs=x4zip.unzip2vector(zipFileName,str);
//	Vector strs=x4zip.unzip2vector(zipFileName,"sc11/al-27_pp/al-27_pp_180000.sc");
	Vector strs=x4zip.unzip2vector(zipFileName,"sc11/Al-27_pp/Al-27_pp_011000.sc");
	if (strs!=null)
	for (ii=0; ii<strs.size(); ii++) {
	    str=(String)strs.elementAt(ii);
	    System.out.println(""+ii+")\t["+str+"]");
	}

    }


    public long unzipFile(String zipfilename, String zipname, String filename)
    {
	int iret=1;
	ZipFile     zf  =null;
	ZipEntry    ze  =null;
	InputStream ins =null;
	File    outFile =null;
	FileOutputStream fos=null;
        try {
            zf  = new ZipFile(zipfilename);  //get zip object
            ze  = zf.getEntry(zipname);    //get zip entry
	    msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
            ins = zf.getInputStream(ze);  //get an input stream for the entry
            outFile = new File(filename);      //create output file
            fos = new FileOutputStream(outFile); //and an output stream
            int lread;
            byte[] buffer = new byte[4096];            

//	    System.out.println("...ze.getSize()="+ze.getSize());
            while ( (lread = ins.read(buffer, 0, 4096)) > -1) {
              fos.write(buffer, 0, lread);
            }

        }
        catch (Exception x) {
//	    System.out.println(x.toString());
	    iret=-1;
        }
	if (ins!=null) try { ins.close(); } catch (Exception x) {}
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	if (fos!=null) try { fos.close(); } catch (Exception x) {}
	return(iret);
    } 



    long msecEntryTime=0;
    String strEntryDateTime="";
    long uncompressedSize=-1;
    long compressedSize=-1;

    SimpleDateFormat formatter=new SimpleDateFormat ("yyyy-MM-dd,HH:mm:ss");
//  SimpleDateFormat formatter=new SimpleDateFormat ("yyyy-MM-dd");
    public String msec2strdate(long timeMsec)
    {
	java.util.Date dateDate=null;
	String strDate="";
	strEntryDateTime="";
//	timeMsec=1150807460297L;
        try {
//	dateDate=formatter.parse("2008-02-25,16:58:25");
	dateDate=new java.util.Date(timeMsec);
	strDate=new String(formatter.format(dateDate));
//	System.out.println("...strDate="+strDate);
	}
        catch(Exception ex) {
//	    System.out.println("...Exception:"+ex.toString());
        }
	//System.out.println("...msec2strdate:timeMsec="+timeMsec+" strDate="+strDate);
	return strDate;
    }

    public Vector unzip2vector(String zipfilename, String zipname)
    {
	return unzip2vector(zipfilename,zipname,-1);
    }
    public synchronized Vector unzip2vector(String zipfilename, String zipname
	,int nStrLimit)
    {
	int iret=1,istr;
	String str;
	Vector result=new Vector();
	ZipFile     zf  =null;
	ZipEntry    ze  =null;
	InputStream ins =null;
	msecEntryTime=0;
//	File    outFile =null;
//	FileOutputStream fos=null;
	ByteArrayOutputStream fos=new ByteArrayOutputStream();
        try {
            zf  = new ZipFile(zipfilename); //get zip object
            ze  = zf.getEntry(zipname);    //get zip entry
	    msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
            ins = zf.getInputStream(ze); //get an input stream for the entry
//	    outFile = new File(filename);      //create output file
//	    fos = new FileOutputStream(outFile); //and an output stream
            int lread;
            byte[] buffer = new byte[4096];            

//	    System.out.println("...ze.getSize()="+ze.getSize());
            while ( (lread = ins.read(buffer, 0, 4096)) > -1) {
              fos.write(buffer, 0, lread);
            }

        }
        catch (Exception x) {
//	    System.out.println("...unzip2vector: "+x.toString());
	    return null;
        }
	if (ins!=null) try { ins.close(); } catch (Exception x) {}
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	if (fos!=null) try { fos.close(); } catch (Exception x) {}

	ByteArrayInputStream bin = new ByteArrayInputStream(fos.toByteArray());
// 	BufferedReader inFile=new BufferedReader(new InputStreamReader(bin)); 

	BufferedReader inFile=null;
//	    inFile=new BufferedReader(new InputStreamReader(bin)); 
        try {
	    inFile=new BufferedReader(new InputStreamReader(bin)); 
            for (istr=0;;istr++) {
                str=inFile.readLine();
                if (str==null) break;
		if (nStrLimit>0) {
		    if (istr>=nStrLimit) break;
		}
		result.addElement(str);
            }
        }
        catch(Exception e) {
	    System.out.println("Exception: ["+e+"]");
	    return null;
        }
	if (inFile!=null) try {inFile.close(); } catch (Exception x) {}

	zf=null;
	ze=null;
	ins=null;
	fos=null;
	bin=null;
	inFile=null;
	return result;
    } 

    public synchronized long getSize(String zipfilename,String zipname)
    {
	int iret=1,istr;
	String str;
	Vector result=new Vector();
	ZipFile zf=null;
	ZipEntry ze=null;
	msecEntryTime=0;
	strEntryDateTime=null;
	uncompressedSize=-1;
	compressedSize=-1;
        try {
            zf=new ZipFile(zipfilename); //get zip object
            ze=zf.getEntry(zipname);     //get zip entry
	    msecEntryTime=ze.getTime();  //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
	    uncompressedSize=ze.getSize();
	    compressedSize=ze.getCompressedSize();
        }
        catch (Exception x) {
//	    System.out.println("...unzip2vector: "+x.toString());
	    return uncompressedSize;
        }
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	zf=null;
	ze=null;
	return uncompressedSize;
    } 

    public synchronized BufferedReader unzip2BufferedReader(String zipfilename,String zipname)
    {
	int iret=1,istr;
	String str;
	Vector result=new Vector();
	ZipFile     zf  =null;
	ZipEntry    ze  =null;
	InputStream ins =null;
	msecEntryTime=0;
//	File    outFile =null;
//	FileOutputStream fos=null;
	ByteArrayOutputStream fos=new ByteArrayOutputStream();
        try {
            zf  = new ZipFile(zipfilename); //get zip object
            ze  = zf.getEntry(zipname);    //get zip entry
	    msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
            ins = zf.getInputStream(ze); //get an input stream for the entry
//	    outFile = new File(filename);      //create output file
//	    fos = new FileOutputStream(outFile); //and an output stream
            int lread;
            byte[] buffer = new byte[4096];            

//	    System.out.println("...ze.getSize()="+ze.getSize());
            while ( (lread=ins.read(buffer,0,4096)) > -1) {
              fos.write(buffer,0,lread);
            }

        }
        catch (Exception x) {
//	    System.out.println("...unzip2vector: "+x.toString());
	    return null;
        }
	if (ins!=null) try { ins.close(); } catch (Exception x) {}
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	if (fos!=null) try { fos.close(); } catch (Exception x) {}

	ByteArrayInputStream bin=new ByteArrayInputStream(fos.toByteArray());
// 	BufferedReader inFile=new BufferedReader(new InputStreamReader(bin)); 

	BufferedReader inFile=null;
        try {
	    inFile=new BufferedReader(new InputStreamReader(bin)); 
        }
        catch(Exception e) {
	    System.out.println("Exception: ["+e+"]");
	    inFile=null;
        }

	zf=null;
	ze=null;
	ins=null;
	return inFile;
    } 


    public synchronized byte[] unzip2bin(String zipfilename, String zipname)
    {
	int iret=1,istr;
	String str;
	Vector result=new Vector();
	ZipFile     zf  =null;
	ZipEntry    ze  =null;
	InputStream ins =null;
	msecEntryTime=0;
//	File    outFile =null;
//	FileOutputStream fos=null;
	ByteArrayOutputStream fos=new ByteArrayOutputStream();
        try {
            zf  = new ZipFile(zipfilename); //get zip object
            ze  = zf.getEntry(zipname);    //get zip entry
	    msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
            ins = zf.getInputStream(ze); //get an input stream for the entry
            int lread;
            byte[] buffer = new byte[4096];            

//	    System.out.println("...ze.getSize()="+ze.getSize());
            while ( (lread = ins.read(buffer, 0, 4096)) > -1) {
              fos.write(buffer, 0, lread);
            }

        }
        catch (Exception x) {
//	    System.out.println("...unzip2vector: "+x.toString());
	    return null;
        }
	if (ins!=null) try { ins.close(); } catch (Exception x) {}
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	if (fos!=null) try { fos.close(); } catch (Exception x) {}

//	ByteArrayInputStream bin=new ByteArrayInputStream(fos.toByteArray());
	byte[] bin=fos.toByteArray();

	zf=null;
	ze=null;
	ins=null;
	fos=null;
	return bin;
    } 

    public DataInputStream openInpStreamFromJar(String inFileName)
    {
	InputStream inStream=null;
	DataInputStream inFile=null;
        try {
	    inStream=getClass().getResourceAsStream(inFileName);
//	    inFile=new FileInputStream(new InputStreamReader(inStream)); 
	    inFile=new DataInputStream(inStream); 
//System.out.println("\n___openInpStreamFromJar:["+inFileName+"] OK. inFile="+inFile);
	    return inFile;
        }
        catch(Exception ex) {
	    //if (sysOut!=null) sysOut_println("\n ERR-OpenInpFile...["+TxtFileName+"] "+ex.getMessage());
	    System.out.println("\n___openInpStreamFromJar...["+inFileName+"] Exception::\n"+ex.getMessage());
            return null;
        }
    }
    public byte[] getBinFile2ByteArray(String inpFileName)
    {
	byte[] buffer=new byte[4096];
	int lrbuf,iret=0,ib,ibtot=0;
	long lr;
	if (false)
	System.out.println("...getBinFile2ByteArray: "
		+"\n   from: "+inpFileName
		+"\n   to:   Memory"
		);
//	FileInputStream inpStream=null;
	DataInputStream inpStream=null;
	inpStream=openInpStreamFromJar(inpFileName);
//	FileOutputStream outStream=null;
	ByteArrayOutputStream outStream=null;
	try {
//	    inpStream=new FileInputStream(inpFileName);
	    if (inpStream==null)
	    inpStream=new DataInputStream(new FileInputStream(inpFileName));
//	    outStream=new FileOutputStream(outFileName);
	    outStream=new ByteArrayOutputStream();
	    for (;;) {
		lrbuf=inpStream.read(buffer);
		//System.out.println("...getBinFile2ByteArray:["+inpFileName+"] LR="+lrbuf);
		if (lrbuf<=0) break;
		outStream.write(buffer,0,lrbuf);
//if (true) return buffer;
	    }
	}
	catch(Exception ex) {
	    System.out.println("...getBinFile2ByteArray: "
		+"\n from: "+inpFileName
		+"\n to:   Memory"
		+"\n Exception: "+ex+"\n"
		);
	    iret=-1;
	}
	if (inpStream!=null)
        try {inpStream.close();} catch (Exception x) {}
	if (outStream!=null)
        try {outStream.close();} catch (Exception x) {}
	if (iret<0) return null;
//	ByteArrayInputStream bin=new ByteArrayInputStream(fos.toByteArray());
	buffer=outStream.toByteArray();
	//dubug:
	//PrintWriter prn5=new PrintWriter(System.out,true);
	//dumpByteArray(prn5,buffer);
	return buffer;
    }




    public Vector blob2unzip2vector(ResultSet rs, String field)
    {
	return blob2unzip2vector(rs,field,-1);
    }

//---read BLOB from RS
//---unzip first zip-Entry to Vector
    public synchronized Vector blob2unzip2vector(ResultSet rs, String field
//	,String zipname
	,int nStrLimit)
    {
	Vector result=new Vector();
	byte[] buffer = new byte[4096];
	int lrbuf,iret=0;
	long lr;
//	FileOutputStream outStrm = null;
	java.io.InputStream inpStrm = null;
	ByteArrayOutputStream fos=new ByteArrayOutputStream();
	try {
//	    outStrm = new FileOutputStream(binFileName);
	    inpStrm = rs.getBinaryStream(field);
	    if (!rs.wasNull()) {
	 	for (;;) {
		    lrbuf = inpStrm.read(buffer);
		    if (lrbuf<=0) break;
//		    outStrm.write(buffer,0,lrbuf);
		    fos.write(buffer,0,lrbuf);
		}
	    }
	}
	catch(Exception ex) {
	    java.util.Date now=new java.util.Date();
//	    System.out.print("...X4sUnzip:readBlobToFile: "+now+"\n Exception: "+ex+"\n");
	    result=null;
	}
//	if (outStrm!=null) try {outStrm.close();} catch (Exception x) {}
	if (inpStrm!=null) try {inpStrm.close();} catch (Exception x) {}
	if (fos!=null) try {fos.close();} catch (Exception x) {}
	if (result==null) {
	    if (fos!=null) try { fos.reset(); } catch (Exception x) {}
	    return null;
	}

	ByteArrayInputStream bin=new ByteArrayInputStream(fos.toByteArray());

	ZipInputStream zis = new ZipInputStream(bin);


	ZipEntry    ze  =null;
//	InputStream ins =null;
	msecEntryTime=0;
	ByteArrayOutputStream fos2=new ByteArrayOutputStream();
        try {
//	    zf  = new ZipFile(zipfilename); //get zip object
//	    ze  = zf.getEntry(zipname);    //get zip entry
//	    ze  =zis.getEntry(zipname);    //get zip entry
	    ze  =zis.getNextEntry();    //get zip entry
	    msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
	    strEntryDateTime=msec2strdate(msecEntryTime);
//	    ins = zis.getInputStream(ze); //get an input stream for the entry
//	    ins = zis; //get an input stream for the entry
//	    outFile = new File(filename);      //create output file
//	    fos2 = new FileOutputStream(outFile); //and an output stream
            int lread;
//	    byte[] buffer = new byte[4096];            

//	    System.out.println("...ze.getSize()="+ze.getSize());
            while ( (lread=zis.read(buffer,0,4096))>-1) {
              fos2.write(buffer,0,lread);
            }

        }
        catch (Exception x) {
//	    System.out.println("...unzip2vector: "+x.toString());
//	    return null;
	    result=null;
        }
//	if (ins!=null) try { ins.close(); } catch (Exception x) {}
//	if (zf!=null)  try { zf.close(); } catch (Exception x) {}
	if (zis!=null) try { zis.close(); } catch (Exception x) {}
	if (fos2!=null) try { fos2.close(); } catch (Exception x) {}

	if (result==null) {
	    if (fos2!=null) try { fos2.reset(); } catch (Exception x) {}
	    return null;
	}

	int istr;
	String str;

//	ByteArrayInputStream bin = new ByteArrayInputStream(fos.toByteArray());
	bin = new ByteArrayInputStream(fos2.toByteArray());
// 	BufferedReader inFile=new BufferedReader(new InputStreamReader(bin)); 

	BufferedReader inFile=null;
//	    inFile=new BufferedReader(new InputStreamReader(bin)); 
        try {
	    inFile=new BufferedReader(new InputStreamReader(bin)); 
            for (istr=0;;istr++) {
                str=inFile.readLine();
                if (str==null) break;
		if (nStrLimit>0) {
		    if (istr>=nStrLimit) break;
		}
		result.addElement(str);
            }
        }
        catch(Exception e) {
	    System.out.println("Exception: ["+e+"]");
	    return null;
        }
	if (inFile!=null) try {inFile.close(); } catch (Exception x) {}


	return result;
    }


    public long compress_file
    (String filename, String entryname, String outfilename)
    {

	long rLen=0;
	boolean ok=true;
	FileInputStream is=null;
	FileOutputStream os=null;
	ZipOutputStream zos=null;
	deleteFile(outfilename);//??VMS
        try
        {
            byte[] buf = new byte[4096];            //read buffer

            int retval;                             //return value

            is = new FileInputStream(filename);		//set up input stream
            os = new FileOutputStream(outfilename);	//set up output stream
            zos = new ZipOutputStream(os);		//wrap output stream in ZipOutputStream
//	    zos.putNextEntry(new ZipEntry(entryname));	//set entry field in zipfile
	    ZipEntry ze=new ZipEntry(entryname);
	    File file=new File(filename);
	    ze.setTime(file.lastModified());
	    zos.putNextEntry(ze);			//set entry field in zipfile

            //read the input file in and write it to the zipfile
            for (;;) {
                retval = is.read(buf, 0, 4096);
                if (retval == -1) break; //-1: indicates end of file
		rLen += retval;
		zos.write(buf, 0, retval);
            }
        }
        catch(Exception e)
        {
	    System.out.println("compress_file: Exception: " + e);
	    ok=false;
        }

	if (is!=null)
        try {
	    is.close();	//close input stream
	} catch (Exception x) {}

	if (zos!=null)
        try {
            zos.closeEntry();	//close this ZipEntry
            zos.close();	//close output stream
	} catch (Exception x) {}

	if (ok) return(rLen);
	else return(-2);

    }

    public String get1stFileName(String zipfilename,boolean flagFullName) {
	int i,ind;
	ZipFile     zf=null;
	ZipEntry    ze=null;
	Enumeration entries=null;
	String str=null;
        try {
            zf=new ZipFile(zipfilename);  //get zip object
	    entries=zf.entries();
	    for (i=0; entries.hasMoreElements(); i++) {
		ze=(ZipEntry)entries.nextElement();
		str=ze.getName();
		//System.out.println("...str=["+str+"]...");
		if (!str.endsWith("/")) {
		    ind=str.lastIndexOf("/");
		    if (!flagFullName)
		    if (ind>=0) str=str.substring(ind+1);
		    break;
		}
	    }
        }
        catch (Exception x) {
            System.out.println(x.toString());
        }
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	return(str);
    }

    public Vector getFileNames(byte[] zipByteArray) {
	Vector names=new Vector();
	String str=null;
	//System.out.println("___O___x4unzip.name:zipByteArray.length="+zipByteArray.length);
	ByteArrayInputStream bin=new ByteArrayInputStream(zipByteArray);
//	ZipFile        zf=null;
	ZipInputStream zis=new ZipInputStream(bin);
	ZipEntry ze=null;
        try {
//	    zf=new ZipFile(bin);  //get zip object
	    for (;;) {
		ze=zis.getNextEntry();    //get zip entry
		if (ze==null) break;
		str=ze.getName();
		//System.out.println("___N___x4unzip.name:["+str+"]");
		if (!str.endsWith("/"))
		{
		    names.addElement(str);
		}
	    }
        }
        catch (Exception x) {
            System.out.println("___Byte___x4unzip.names.Exception: "+x.toString());
        }
	return names;
    }
    public synchronized BufferedReader unzip2BufferedReader(byte[] zipByteArray,String zipname)
    {
	int iret=1,istr;
	String str;
	Vector result=new Vector();
	InputStream ins=null;
	msecEntryTime=0;

//	ZipFile        zf=null;
	ByteArrayOutputStream fos=new ByteArrayOutputStream();
	ByteArrayInputStream bin=new ByteArrayInputStream(zipByteArray);
	ZipInputStream zis=new ZipInputStream(bin);
	ZipEntry ze=null;
        try {
//	    zf=new ZipFile(bin);  //get zip object
	    for (;;) {
		ze=zis.getNextEntry();    //get zip entry
		if (ze==null) break;
		str=ze.getName();
		if (!str.equals(zipname)) continue;
		msecEntryTime=ze.getTime();   //the entry modification time in number of milliseconds since the epoch
		strEntryDateTime=msec2strdate(msecEntryTime);
		//ins=ze.getInputStream(ze); //get an input stream for the entry
		int lread;
		byte[] buffer=new byte[4096];            
		while ( (lread=zis.read(buffer,0,4096))>-1) {
		    fos.write(buffer,0,lread);
		}
	    }
        }
        catch (Exception x) {
            System.out.println("___Byte___x4unzip.reader.Exception: "+x.toString());
        }

	if (ins!=null) try { ins.close(); } catch (Exception x) {}
	if (zis!=null) try { zis.close(); } catch (Exception x) {}
	if (fos!=null) try { fos.close(); } catch (Exception x) {}

	ByteArrayInputStream bin2=new ByteArrayInputStream(fos.toByteArray());
// 	BufferedReader inFile=new BufferedReader(new InputStreamReader(bin)); 

	BufferedReader inFile=null;
        try {
	    inFile=new BufferedReader(new InputStreamReader(bin2)); 
        }
        catch(Exception e) {
	    System.out.println("__unzip2___Exception: ["+e+"]");
	    inFile=null;
        }

	zis=null;
	ze=null;
	ins=null;
	return inFile;
    } 




    public Vector getFileNames(String zipfilename) {
	return getFileNames(zipfilename,null);
    }
    public Vector getFileNames(String zipfilename,String ext) {
	int i,ind;
	ZipFile     zf=null;
	ZipEntry    ze=null;
	Enumeration entries=null;
	String str=null;
	Vector names=new Vector();
	if (ext!=null) if (ext.trim().equals("")) ext=null;
        try {
            zf=new ZipFile(zipfilename);  //get zip object
	    entries=zf.entries();
	    for (i=0; entries.hasMoreElements(); i++) {
		ze=(ZipEntry)entries.nextElement();
		str=ze.getName();
		//System.out.println("...str=["+str+"]...");
		if (!str.endsWith("/"))
		{
//		    ind=str.lastIndexOf("/");
//		    if (ind>=0) str=str.substring(ind+1);
//		    break;
    if (ext!=null) if (!str.endsWith(ext)) continue;
		    names.addElement(str);
		}
	    }
        }
        catch (Exception x) {
            System.out.println("___File___x4unzip.Exception: "+x.toString());
        }
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	return(names);
    } 

    public Vector getDirNames0win(String zipfilename) {
	int i,ind;
	ZipFile     zf=null;
	ZipEntry    ze=null;
	Enumeration entries=null;
	String str=null;
	Vector names=new Vector();
        try {
            zf=new ZipFile(zipfilename);  //get zip object
	    entries=zf.entries();
	    for (i=0; entries.hasMoreElements(); i++) {
		ze=(ZipEntry)entries.nextElement();
		str=ze.getName();
		System.out.println("...str=["+str+"]...");
		System.out.println("...ze.getName()="+ze.getName()+"...ze.getSize()="+ze.getSize()+"...ze.getTime()="+ze.getTime()+"::"+msec2strdate(ze.getTime()));
		if (str.endsWith("/"))
		{
		    ind=str.lastIndexOf("/");
		    if (ind>=0) str=str.substring(0,ind);
		    names.addElement(str);
		}
	    }
        }
        catch (Exception x) {
            System.out.println(x.toString());
        }
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	return(names);
    } 

    public Vector getDirNames(String zipfilename) {
	int i,ind;
	ZipFile     zf=null;
	ZipEntry    ze=null;
	Enumeration entries=null;
	String str=null;
	String lastDirName=null;
	Vector names=new Vector();
        try {
            zf=new ZipFile(zipfilename);  //get zip object
	    entries=zf.entries();
	    for (i=0; entries.hasMoreElements(); i++) {
		ze=(ZipEntry)entries.nextElement();
		str=ze.getName();
//		System.out.println("...str=["+str+"]...");
/*		if (str.endsWith("/"))
		{
		    ind=str.lastIndexOf("/");
		    if (ind>=0) str=str.substring(0,ind);
		    names.addElement(str);
		}
*/
		ind=str.lastIndexOf("/");
//		if (ind>=0) str=str.substring(0,ind+1);
		if (ind>=0) str=str.substring(0,ind);
		else str="";
//		System.out.println("---str=["+str+"]...");
		if (lastDirName==null) lastDirName=str;
		else {
		    if (lastDirName.equals(str)) continue;
		    lastDirName=str;
		}
		names.addElement(str);
	    }
        }
        catch (Exception x) {
            System.out.println(x.toString());
        }
	if (zf!=null) try { zf.close(); } catch (Exception x) {}
	return(names);
    } 

    public String path2fileName(String path) {
	int i,ind;
	String str=path;
	ind=str.lastIndexOf("/");
	if (ind<0) return path;
	str=str.substring(ind+1);
	return str;
    }
    public String path2dirName(String path) {
	int i,ind;
	String str=path;
	ind=str.lastIndexOf("/");
	if (ind<0) return "";
//	str=str.substring(0,ind+1);
	str=str.substring(0,ind);
	return str;
    }


    public Vector sort(Vector vec)
    {
	int i, ii, i0, imin;
	boolean found;
	String str1="", str2="";
	Vector result=vec;
	boolean unique=true;
	//---sort
	for (i0=0; i0<result.size(); i0++) {
	    str1=(String)result.elementAt(i0);
	    imin=i0;
	    for (i=i0+1; i<result.size(); i++) {
		str2=(String)result.elementAt(i);
		if (str2.compareTo((String)result.elementAt(imin))<0) {
		    imin=i;
		}
	    }
	    str2=(String)result.elementAt(imin);
	    result.setElementAt(str2,i0);
	    result.setElementAt(str1,imin);
	}
	if (!unique) return result;

	Vector result2=new Vector();
	for (i0=0; i0<result.size(); i0++) {
	    str2=(String)result.elementAt(i0);
	    //System.out.println("---str2=["+str2+"]");
	    if (i0==0) {
		result2.addElement(str2);
		str1=str2;
		continue;
	    }
	    if (str2.equals(str1)) continue;
	    result2.addElement(str2);
	    str1=str2;
	}
	return result2;
    }

    public Vector filterNames(Vector vec,String filter)
    {
	int i, ii, i0, ind;
	boolean found;
	String str,str1,str2,filter1="",filter2="";
	Vector result=new Vector();
	filter=filter.toLowerCase();
	if (filter.equals("*")) filter="";
	ind=filter.indexOf("*");
	if (ind>=0) {
	    filter1=filter.substring(0,ind);
	    filter2=filter.substring(ind+1);
	}
//System.out.println("...filter1=["+filter1+"]...");
//System.out.println("...filter2=["+filter2+"]...");
	for (i0=0; i0<vec.size(); i0++) {
	    str=(String)vec.elementAt(i0);
	    str1=str.toLowerCase();
	    if (filter1.length()>0) if (!str1.startsWith(filter1)) continue;
	    if (filter2.length()>0) if (!str1.endsWith(filter2)) continue;
//System.out.println("...filter.add=["+str+"]...");
	    result.addElement(str);
	}
	return result;
    }

    public static void deleteFile(String fileName)
    {
	File f = new File(fileName);
	if (f.exists()) {
	    boolean del=f.delete();
	    //responseOutput.println("\nDelete: "+fileName+" OK:"+del);
	}
    }

}
