@echo off

set pathToMongoDB="C:\Users\Michael\Desktop\mongodb-win32-x86_64-2.2.2\bin"

cd %pathToMongoDB%

start mongod.exe --port 44444 --dbpath "%~dp0swa"

cd %~dp0..

start mvn tomcat:run

cd testsuite
@echo on