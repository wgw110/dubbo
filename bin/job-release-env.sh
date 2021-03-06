#!/usr/bin/env bash
JOB_HOME=`dirname $0`
JOB_HOME=`dirname $JOB_HOME`
JOPTS="$JOPTS -server -Xmx4096m -Xms4096m -XX:NewSize=1024m -XX:NewRatio=4 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=75 -XX:+PrintGC -XX:+PrintGCDetails -Xloggc:${JOB_HOME}/logs/gc.log -XX:+PrintGCTimeStamps "
