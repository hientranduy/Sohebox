#!/bin/bash


echo "###############################################################################"
echo "###                        start_service.sh                                 ###"
echo "###############################################################################"

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
deployHome="/home/hientran/Repository/Sohebox/Deploy/Backend"
runFolder="run"
appName="sohebox-1.0.0.jar"


$deployHome/stop_service.sh


echo "============================ Sohebox service is restarting ===================="
nohup java -jar $deployHome/$runFolder/$appName </dev/null &>/dev/null &
ps -ef | grep [s]ohebox


sleep 1 
