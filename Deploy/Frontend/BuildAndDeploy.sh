#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="/home/hientran/Repository/Sohebox"

soheboxDevelopFrontendHome="Develop/Frontend"

frontendDeployPath="/var/www/"
frontendDeployFolder="sohebox"
frontendSourcePath="dist"



cd $soheboxHome
echo "============================ Go to to Repo =============================="
command -p pwd
echo "============================ Checkout Sohebox ==========================="
command -p git fetch origin master
command -p git stash
command -p git pull origin master
command -p git stash pop


cd $soheboxHome/$soheboxDevelopFrontendHome
echo "============================ Go to to develop frontend home ============="
command -p pwd
echo "============================ Build package =============================="
ng build --configuration production


echo "============================ Deploy ====================================="
echo "============================ Deploy remove =============================="
command -p sudo rm -rf $frontendDeployPath/$frontendDeployFolder
sleep 1

echo "============================ Deploy copy ================================"
command -p sudo cp -a $soheboxHome/$soheboxDevelopFrontendHome/$frontendSourcePath/. $frontendDeployPath
sleep 1
