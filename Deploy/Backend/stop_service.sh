#!/bin/bash

echo "###############################################################################"
echo "###                        stop_service.sh                                  ###"
echo "###############################################################################"

if ps aux | grep -q "[s]ohebox" ; then 
   echo "============================ Sohebox service is stopping ======================"
   ps -ef | grep [s]ohebox
   kill $(ps aux | grep '[s]ohebox' | awk '{print $2}')
else 
   echo "============================ Sohebox sevice is currently not active ==========="
fi

sleep 1
