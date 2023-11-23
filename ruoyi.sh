#!/bin/bash

JAVA_HOME=/data/cuiweiman/jdk-11.0.21/bin/java
APP_HOME=/home/app/ship-dispatch-system/backend
APP_NAME=contract.jar
LOG_PATH=$APP_HOME/logs

#JVM参数 JDK 11 +
JVM_OPTS="-Dname=$APP_NAME -Xms4G -Xmx4G -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xlog:safepoint,classhisto*=trace,age*,gc*=info:file=$LOG_PATH/gc-%t.log:time,tid,tags:filecount=5,filesize=50m"

if [ "$1" = "" ]; then
  echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
  exit 1
fi

if [ "$APP_NAME" = "" ]; then
  echo -e "\033[0;31m 未输入应用名 \033[0m"
  exit 1
fi

function start() {
  PID=$(ps -ef | grep java | grep $APP_NAME | grep -v grep | awk '{print $2}')

  if [ x"$PID" != x"" ]; then
    echo "$APP_NAME is running..."
  else
    nohup $JAVA_HOME -jar $JVM_OPTS $APP_HOME/$APP_NAME >$LOG_PATH/contract.out 2>&1 &
    echo "Start $APP_NAME success..."
  fi
}

function stop() {
  echo "Stop $APP_NAME"

  PID=""
  query() {
    PID=$(ps -ef | grep java | grep $APP_NAME | grep -v grep | awk '{print $2}')
  }

  query
  if [ x"$PID" != x"" ]; then
    kill -TERM $PID
    echo "$APP_NAME (pid:$PID) exiting..."
    while [ x"$PID" != x"" ]; do
      sleep 1
      query
    done
    echo "$APP_NAME exited."
  else
    echo "$APP_NAME already stopped."
  fi
}

function restart() {
  stop
  sleep 2
  start
}

function status() {
  PID=$(ps -ef | grep java | grep $APP_NAME | grep -v grep | wc -l)
  if [ $PID != 0 ]; then
    echo "$APP_NAME is running..."
  else
    echo "$APP_NAME is not running..."
  fi
}

case $1 in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  restart
  ;;
status)
  status
  ;;
*) ;;

esac
