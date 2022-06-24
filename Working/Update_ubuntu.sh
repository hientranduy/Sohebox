#!/bin/bash

echo "============================ Apt update package index ..."
command -p sudo apt update

echo "============================ Apt upgrade packages ..."
command -p sudo apt upgrade

$SHELL
