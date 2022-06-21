#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
appName="sohebox-1.0.0.jar"
soheboxServiceHome="Repository/Sohebox/Develop/Backend/target"

cd 
cd $soheboxServiceHome
echo "============================ Current in ........."
command -p pwd

echo "============================ Start sohebox ..."
command -p java -jar appName


$SHELL
