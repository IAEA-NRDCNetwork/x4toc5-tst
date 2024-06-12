"""
 ***********************************************************************************
 * Copyright (C) 2021-2023 International Atomic Energy Agency (IAEA)               *
 *-----------------------------------------------------------------------------    *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is furnished *
 * to do so, subject to the following conditions:                                  *
 *                                                                                 *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 *                                                                                 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN       *
 * THE SOFTWARE.                                                                   *
 *                                                                                 *
 *-----------------------------------------------------------------------------    *
 *   AUTHOR:                                                                       *
 *   Viktor Zerkin, PhD                                                            *
 *   e-mail: v.zerkin@iaea.org, v.zerkin@gmail.com                                 *
 *   International Atomic Energy Agency                                            *
 *   Nuclear Data Section, P.O.Box 100                                             *
 *   Wagramerstrasse 5, Vienna A-1400, AUSTRIA                                     *
 *   Phone: +43 1 2600 21714; Fax: +43 1 26007                                     *
 *                                                                                 *
 ***********************************************************************************
"""

import datetime
import json
import sys
sys.path.append('./')
from c5subr import *
from c5file import *
import plotly
from plotly.graph_objs import Scatter, Layout 
from pprint import pprint

print("Program: c5plot1sig.py, ver.2024-06-09")
print("Author:  V.Zerkin, IAEA, Vienna, 2023-2024")
print("Purpose: load C5-file, extract data, plot by Plotly\n")

ct=str(datetime.datetime.now())[:19]
print("Running: "+ct+"\n")
#input("Press the <ENTER> key to continue...")

base='./'

def sort_ya1(ds):
    rr=str(ds['year1'])+','+ds['author1']
    return rr

fileName="tst-inv.x4.c5"
xtype='linear'; ytype='linear'
xtype='log'   ; ytype='log'

if (len(sys.argv)>1) and (sys.argv[1]!=''): fileName=str(sys.argv[1])
if (len(sys.argv)>2) and (sys.argv[2]=='lin'): xtype='linear'
if (len(sys.argv)>3) and (sys.argv[3]=='lin'): ytype='linear'

datasets=readC5file(fileName)
nDatasets=len(datasets)
print('-0-Datasets:'+str(nDatasets))

reacode='8-O-16(N,A)6-C-13,,SIG'
invreac='6-C-13(A,N)8-O-16,,SIG'
reacode=''
invreac=''

#datasets=filter_datasets(datasets,'ReactionCode',reacode)
datasets=filter_datasets(datasets,'MF',3)
n1=nDatasets=len(datasets)
print('-1-Datasets:'+str(nDatasets))
#datasets=not_filter_datasets(datasets,'x4status','SPSDD')
#datasets=not_filter_datasets(datasets,'x4status','PRELIM')
n2=nDatasets=len(datasets)
print('-2-Datasets:'+str(nDatasets))
if (nDatasets<=0):
    print("---No data found---")
    sys.exit(2)

xtitle='Incident energy (MeV)'
ytitle='Cross section (mb)'
fx=1e-6;fy=1e3
dss=[];ii=0;iok=0
for dataset in datasets:
    ii+=1
    ds=dataset
    if ds is None: continue
    DatasetID=dataset['DatasetID']
    reac1=dataset['ReactionCode']
    reac1=reac1.replace(',,,DERIV','')
    if (reacode.find(reac1)<0):
        if (reacode==''): reacode=reac1
        else:		  reacode+='; '+reac1
    print(str(iok)+'/'+str(ii)+') R:['+reac1+'] Dataset:'+DatasetID
	+' '+str(ds['year1'])+' '+ds['author1'].title()
	+' #dataPoints:'+str(len(ds['dataLines'])))
    exctractC5Data(ds,fx=fx,fy=fy)
    iok+=1
#    print('	Energy:	',ds['data'][0])
#    print('	dEnergy:',ds['data'][1])
#    print('	*Data:  ',ds['data'][2])
#    print('	*dData: ',ds['data'][3])
    ds['y'] =ds['data'][2]
    ds['dy']=ds['data'][3]
    ds['x'] =ds['data'][0]
    ds['dx']=ds['data'][1]
    ds['x4lbl']=str(ds['year1'])+' '+ds['author1'].title()+' #'+ds['DatasetID']+' pt:'+str(len(ds['x']))
    ds['x4lbl']=reac1+' '+ds['x4lbl']
    if (ds['author1'].find('^')>=0): ds['x4lbl']+=' //inverted ' #+ds['OrigReaction']
    if ds['OrigReaction'] is not None: invreac=' and inverted '+ds['OrigReaction']
    dss.append(ds)

if (len(dss)<=0):
    print("---No datasets for plotting---")
    sys.exit(2)

dss=sorted(dss,key=sort_ya1,reverse=True)

#_________________Preparing EXFOR data for plot_________________
data1=[]; ii=0
for ds in dss:
    tr=Scatter(x=ds['x'],y=ds['y']
	,text=ds['x4lbl']
	,name=ds['x4lbl']
	,marker_symbol=str(ii%33)
	,marker_size=8
	,mode="markers"
	)
    if (ds['dy'] is not None): tr.error_y=dict(type='data',array=ds['dy'],visible=True,thickness=0.9)
    if (ds['dx'] is not None): tr.error_x=dict(type='data',array=ds['dx'],visible=True,thickness=0.9)
    tr.name=str(ii+1)+'. '+tr.name
    if (ds['x4lbl'].find('^')<0): tr.marker.line=dict(color='Black',width=0.8)
    tr.mode="markers+lines"
    tr.line=dict(width=0.8)
    data1.append(tr)
    ii+=1
    #break

plotTitle=reacode + invreac;

#_________________Plot data from EXFOR_________________
plot1={}
plot1['data']=data1
xaxis=dict(title=xtitle,showline=True,linecolor='black',ticks='outside'
,showgrid=True,gridcolor='#aaaaaa',type=xtype)
yaxis={'title':ytitle,'showline':True,'linecolor':'black'
	,'showgrid':True, 'gridcolor':'#aaaaaa','ticks':'outside','type':ytype
	,'zeroline':True, 'zerolinecolor':'#dddddd'#, 'zerolinewidth':0.1
}
xaxis['mirror']='ticks'; yaxis['mirror']='ticks' 

txtDiff=''

plot1['layout']=Layout(title='Cross sections \u03c3(Ei): '+plotTitle
	+' #Datasets:'+str(iok)
	+'/'+str(n2) #total after filtering out: superseded or withdrawn, and preliminary
	+'/'+str(n1) #total
	+'<br><i>EXFOR-C5, by V.Zerkin, IAEA-NDS, 2010-2024, ver.2024-06-09 //running:'+ct+'</i>'
	,xaxis=xaxis,yaxis=yaxis
	,plot_bgcolor='white'
	,legend=dict(traceorder="grouped")
)

outhtml='c5plot1sig'
plotly.offline.plot(plot1,filename=outhtml+'.html')
