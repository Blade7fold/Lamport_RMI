@ECHO OFF
javac -cp ../  ClientOfGlobalRMIServer.java
java -cp ../ lamportrmi/ClientOfGlobalRMIServer 127.0.0.1 1104
