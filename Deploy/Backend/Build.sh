#!/bin/bash

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
soheboxHome="Repository/Sohebox"
soheboxBackendHome="Develop/Backend"

cd 
cd $soheboxHome
echo "============================ Current in ........."
command -p pwd

echo "============================ Checkout Sohebox ..."
command -p git fetch origin master
command -p git --no-pager diff --name-only FETCH_HEAD $(git merge-base FETCH_HEAD master)
command -p git pull origin master


cd $soheboxBackendHome
echo "============================ Current in ........."
command -p pwd

echo "============================ Build package ..."
command -p mvn clean package -Pjar

$SHELL
