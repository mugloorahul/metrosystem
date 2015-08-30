cd G:\work\java\MetroSystem\databases

SET DERBY_INSTALL=G:\work\java\MetroSystem\databases\db-derby-10.11.1.1-bin

SET CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbynet.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbyclient.jar

java org.apache.derby.tools.ij

connect 'jdbc:derby://localhost:1527/MetroSystemDB;create=true;user=admin;password=admin';
