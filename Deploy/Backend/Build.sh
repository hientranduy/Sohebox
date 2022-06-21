#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxBackendHome="Develop/Backend"
soheboxBuildHome="target"

soheboxDeployHome="Deploy/Backend"
appName="sohebox-1.0.0.jar"
javaBinHome="/usr/lib/jvm/java-11-openjdk-amd64/bin"


cd 
cd $soheboxHome
echo "============================ Go to to Repo =============================="
command -p pwd


echo "============================ Checkout Sohebox ==========================="
command -p git fetch origin master
command -p git stash
command -p git pull origin master
command -p git stash pop


cd $soheboxBackendHome
echo "============================ Go to to backend home ======================"
command -p pwd


echo "============================ Build package =============================="
command -p mvn clean package -Pjar


cd 
cd $soheboxHome
cd $soheboxDeployHome
echo "============================ Go to to deploy home ======================="
command -p pwd


echo "============================ Move package to deploy folder =============="
mv $HOME/$soheboxHome/$soheboxBackendHome/$soheboxBuildHome/$appName .


echo "============================ Update prd properties files ================"
$javaBinHome/jar uf $appName BOOT-INF/classes


echo "============================ Move package to run folder ================="
mv $appName run/



$SHELL
