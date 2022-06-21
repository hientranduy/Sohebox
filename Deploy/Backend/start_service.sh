#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxRunHome="Deploy/Backend/run"
appName="sohebox-1.0.0.jar"
javaBinHome="/usr/lib/jvm/java-11-openjdk-amd64/bin"



./stop_service.sh


cd 
cd $soheboxHome
cd $soheboxRunHome
echo "============================ Go to to Repo =============================="
command -p pwd


echo "============================ Start sohebox =============================="
nohup java -jar $appName </dev/null &>/dev/null &


$SHELL
