clear

myPause()
{
    echo ""
    echo ""
    echo "____Next test____________________________________________________________"
    echo ""
    echo $1
    echo -n Press ENTER to continue ...\ ; read aaa;
    clear
    echo $1
    echo ""
}


myPause "Display help-info"
java -jar x4toc5.jar

myPause "Convert x4 to c5 with default options"
java -jar x4toc5.jar x4.x4

myPause "Convert x4 to c5, split output by Entry"
java -jar x4toc5.jar x4.x4 -split:c5out

myPause "Convert x4 to c4"
java -jar x4toc5.jar x4.x4 -c4 -o:x4.x4.c4

myPause "Inverse reaction data"
java -jar x4toc5.jar x4.x4 -i -o:x4.x4.c5i

myPause "Generate correlation matrix"
java -Xmx400M -jar x4toc5.jar c5matr1.x4 -c5m

myPause "Replace MT by MT+iLevel from RIPL-Levels for partial reactions"
echo "---Note. Option -mt51 require levels/"
echo "	$ unzip levels.zip"
java -jar x4toc5.jar 10034065.x4 -mt51 -dlvl:levels/

myPause "Keep Q-Value, i.e. do not replace by Energy-Level"
java -jar x4toc5.jar 10034065.x4 -noqe -o:10034065.x4.c5-Qval

myPause "Inverse reaction data for selected reaction"
#java -jar x4toc5.jar tst-inv.x4
java -jar x4toc5.jar tst-inv.x4 -i:tst-inv.lst -o:tst-inv.x4.c5i >tst-inv.x4.tto
echo "---See terminal output in the file: [tst-inv.x4.tto] ---"

myPause "Convert C.M. to Lab system"
java -jar x4toc5.jar 20268002.x4 -cm2lab

echo "---Finished---"
