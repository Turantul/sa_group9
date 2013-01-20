@echo off 

cd ../client

REM start client
start mvn exec:java -Dexec.mainClass="sa12.group9.client.Launcher"

cd ../testsuite

@echo on