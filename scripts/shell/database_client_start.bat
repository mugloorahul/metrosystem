cd C:\work\personal\metro_system\database\derby\databases

SET DERBY_INSTALL=C:\work\personal\metro_system\database\derby

SET CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbynet.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbyclient.jar

java org.apache.derby.tools.ij

connect 'jdbc:derby://localhost:1527/MetroSystemDB;user=admin;password=admin';
