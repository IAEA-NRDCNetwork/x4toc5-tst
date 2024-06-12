@cls

@call :myPause "Display help-info"
java -jar x4toc5.jar

@call :myPause "Convert x4 to c5 with default options"
java -jar x4toc5.jar x4.x4

@call :myPause "Convert x4 to c5, split output by Entry"
java -jar x4toc5.jar x4.x4 -split:c5out

@call :myPause "Convert x4 to c4"
java -jar x4toc5.jar x4.x4 -c4 -o:x4.x4.c4

@echo. & @echo. & @echo Inverse reaction data
@call :myPause "Inverse reaction data"
java -jar x4toc5.jar x4.x4 -i -o:x4.x4.c5i

@call :myPause "Generate correlation matrix"
java -Xmx400M -jar x4toc5.jar c5matr1.x4 -c5m

@call :myPause "Replace MT by MT+iLevel from RIPL-Levels for partial reactions"
echo "---Note. Option -mt51 require levels/"
echo "	$ unzip levels.zip"
java -jar x4toc5.jar 10034065.x4 -mt51 -dlvl:levels/

@call :myPause "Keep Q-Value, i.e. do not replace by Energy-Level"
java -jar x4toc5.jar 10034065.x4 -noqe -o:10034065.x4.c5-Qval

@call :myPause "Inverse reaction data for selected reaction"
@rem java -jar x4toc5.jar tst-inv.x4
java -jar x4toc5.jar tst-inv.x4 -i:tst-inv.lst -o:tst-inv.x4.c5i >tst-inv.x4.tto

@call :myPause "Convert C.M. to Lab system"
java -jar x4toc5.jar 20268002.x4 -cm2lab

@pause
@exit

:myPause
@set qq=%1
@set qq=%qq:~1,-1%
@echo.
@echo.
@echo ____Next test____________________________________________________________
@echo.
@echo %qq%
@pause
@cls
@echo %qq%
@exit /b
