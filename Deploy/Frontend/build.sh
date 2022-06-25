#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxFrontendHome="Develop/Frontend"


soheboxDeployHome="Deploy/Frontend"
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


cd $soheboxFrontendHome
echo "============================ Go to to backend home ======================"
command -p pwd


echo "============================ Build package =============================="
ng build --configuration production


$SHELL
