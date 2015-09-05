cd G:\work\java\MetroSystem\databases

SET DERBY_INSTALL=G:\work\java\MetroSystem\databases\db-derby-10.11.1.1-bin

SET CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbynet.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbyclient.jar

java -jar %DERBY_INSTALL%\lib\derbynet.jar start -h localhost -p 1527
