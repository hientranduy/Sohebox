#!/bin/bash

echo "============================ Cleaning syslog* ..."
sudo truncate -s 0 /var/log/syslog*

echo "============================ Cleaning journal ..."
sudo journalctl --vacuum-size=50M

echo "============================ Current log ..."
cd /var/log
ls -laSh
$SHELL
