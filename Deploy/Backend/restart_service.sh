#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxDeployHome="Deploy/Backend"
runHome="run"
appName="sohebox-1.0.0.jar"


# Execute scrip search and kill current sevice
$HOME/$soheboxHome/$soheboxDeployHome/stop_service.sh


echo "============================ Sohebox service is restarting ==========="
nohup java -jar $HOME/$soheboxHome/$soheboxDeployHome/$runHome/$appName </dev/null &>/dev/null &
ps -ef | grep [s]ohebox


sleep 3
