#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxBackendHome="Develop/Backend"
soheboxBuildHome="target"

soheboxDeployHome="Deploy/Backend"
appName="sohebox-1.0.0.jar"

cd 
cd $soheboxHome
echo "============================ Current in ........."
command -p pwd

echo "============================ Checkout Sohebox ..."
command -p git fetch origin master
command -p git pull origin master


cd $soheboxBackendHome
echo "============================ Current in ........."
command -p pwd

echo "============================ Build package ..."
command -p mvn clean package -Pjar

echo "============================ Move package to deploy folder "
cd $soheboxBuildHome
mv $HOME/$soheboxHome/$soheboxBackendHome/$soheboxBuildHome/$appName $HOME/$soheboxHome/$soheboxDeployHome

$SHELL
