@echo off

set pathToMongoDB="TODO: set path to MongoDB installation including /bin"

cd %pathToMongoDB%

start mongod.exe --port 44444 --dbpath "%~dp0swa"

cd %~dp0..

start mvn tomcat:run

cd testsuite
@echo on