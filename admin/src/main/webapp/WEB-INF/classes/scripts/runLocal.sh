#!/usr/bin/env bash
docker run -d -e sidbserver=jdbc:mysql://localhost:3306/si_db_central?characterEncoding=UTF-8 -e sidbuser=root -e sidbpassword=password --net=host  ping2ravi/si-admin:latest