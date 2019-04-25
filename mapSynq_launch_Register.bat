Set projectLocation=D:\Selenium\Workspace-Nimble tech\MapSync
cd %projectLocation%
set classpath =%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause