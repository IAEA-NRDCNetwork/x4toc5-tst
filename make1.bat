
@echo.
@echo   ^+---------------------------------------------^+
@echo   ^|               Program x4toc5                ^|
@echo   ^| Translate EXFOR to computational format C5. ^|
@echo   ^|     v.zerkin@gmail.com, IAEA, 2024-06-10.   ^|
@echo   ^+---------------------------------------------^+
@echo   ^|     Compile Java source codes to JAR file   ^|
@echo   ^+---------------------------------------------^+
@echo.

mkdir bin
cd src
rmdir /s /q zvv
javac -d . *.java
robocopy /DCOPY:T /E /XO x4dict zvv\x4\x4dict
jar -cvfm x4toc5.jar manifest.txt zvv\*
copy x4toc5.jar ..
copy x4toc5.jar ..\bin\
copy x4toc5.jar ..\tests\
rmdir /s /q zvv
del x4toc5.jar
