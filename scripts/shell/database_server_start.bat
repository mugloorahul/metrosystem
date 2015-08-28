cd C:\work\personal\metro_system\database\derby\databases

SET DERBY_INSTALL=C:\work\personal\metro_system\database\derby

SET CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbynet.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbyclient.jar

java -jar %DERBY_INSTALL%\lib\derbynet.jar start -h localhost -p 1527
