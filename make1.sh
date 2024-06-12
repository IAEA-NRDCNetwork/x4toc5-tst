#!/bin/bash

echo ""
echo "        +---------------------------------------------+"
echo "        |               Program x4toc5                |"
echo "        | Translate EXFOR to computational format C5. |"
echo "        |     v.zerkin@gmail.com, IAEA, 2024-06-10.   |"
echo "        +---------------------------------------------+"
echo "        |     Compile Java source codes to JAR file   |"
echo "        +---------------------------------------------+"
echo ""

mkdir bin
cd src
rm -rf zvv
javac -d . *.java
cp -a x4dict zvv/x4/x4dict
jar -cvfm x4toc5.jar manifest.txt zvv/*
cp -p x4toc5.jar ../bin/
cp -p x4toc5.jar ../tests/
mv x4toc5.jar ..
rm -rf zvv
cd ..
