@echo off 

REM arguments for peers
set peer1="test1 testpw 12100 12101 12102"
set peer2="test2 testpw 12200 12201 12202"
set peer3="test3 testpw 12300 12301 12302"
set peer4="test4 testpw 12400 12401 12402"
set peer5="test5 testpw 12500 12501 12502"

cd peer

REM start peers via maven
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher" -Dexec.args=%peer1%
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher" -Dexec.args=%peer2%
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher" -Dexec.args=%peer3%
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher" -Dexec.args=%peer4%
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher" -Dexec.args=%peer5%

cd ../peermanagement

REM start console
start mvn exec:java -Dexec.mainClass="sa12.group9.peer.Launcher"

cd ..

@echo on