@echo off 

REM arguments for client
set client="test1 testpw ../music/Samples/5a.mp3"

cd ../client

REM start client
start mvn exec:java -Dexec.mainClass="sa12.group9.client.cli.Console" -Dexec.args=%client%

cd ../testsuite

@echo on