#!/bin/bash

echo "###############################################################################"
echo "###                        build_and_start.sh                               ###"
echo "###############################################################################"

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="/home/hientran/Repository/Sohebox"
backendHome="/home/hientran/Repository/Sohebox/Develop/Backend"
buildHome="/home/hientran/Repository/Sohebox/Develop/Backend/target"


deployHome="/home/hientran/Repository/Sohebox/Deploy/Backend"
runHome="/home/hientran/Repository/Sohebox/Deploy/Backend/run"
appName="sohebox-1.0.0.jar"
javaBinHome="/usr/lib/jvm/java-11-openjdk-amd64/bin"


echo "============================ Checkout Sohebox ================================="
cd $soheboxHome
command -p pwd
echo "=========== fetch"
command -p git fetch origin master
echo "=========== stash"
command -p git stash
echo "=========== pull"
command -p git pull origin master
echo "=========== stash"
command -p git stash pop


echo "============================ Build package ===================================="
cd $backendHome
command -p pwd
command -p mvn clean package -Pjar


echo "============================ Move package to deploy folder ===================="
mv $buildHome/$appName   $deployHome


echo "============================ Update prd properties files ======================"
cd $deployHome
$javaBinHome/jar uf $appName BOOT-INF/classes


echo "============================ Move package to run folder ======================="
mv $deployHome/$appName   $runHome


echo "============================ Start new package ================================"
$deployHome/start_service.sh

$SHELL
