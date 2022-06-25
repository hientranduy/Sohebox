#!/bin/bash

echo "###############################################################################"
echo "###                        renew cert                                       ###"
echo "###############################################################################"

###############################################################################
###                        Setup parameters/inputs                          ###
###############################################################################
certDestHome="/home/hientran/Repository/Sohebox/Develop/Backend/src/main/resources/keystore"
certSourceHome="/etc/letsencrypt/live/sohebox.com"
certName="sohebox_certificate.pfx" 

backendDeployHome="/home/hientran/Repository/Sohebox/Deploy/Backend"


echo "============================ Generate pfx file ================================"
command -p sudo openssl pkcs12 -export -out $certDestHome/$certName -inkey $certSourceHome/privkey.pem -in $certSourceHome/cert.pem -certfile $certSourceHome/chain.pem
sudo chown -R $USER $certDestHome/$certName


$backendDeployHome/build_and_start.sh


$SHELL
