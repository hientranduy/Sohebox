#!/bin/bash

echo "============================ Stop rsyslog.service ..."
sudo systemctl stop rsyslog.service

echo "============================ Disable rsyslog.service ..."
sudo systemctl disable rsyslog.service

echo "============================ Current log ..."
cd /var/log
ls -laSh
$SHELL
