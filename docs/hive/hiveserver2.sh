#!/bin/bash
WORK_DIR=$(cd $(dirname "$0") || exit; pwd)

LOG_PATH=$WORK_DIR"/logs"
mkdir -p "$LOG_PATH"

case "$1" in
start)
  echo "starting hiveserver2"
  nohup hive --service hiveserver2  >> "$LOG_PATH"/hiveserver2.log 2>&1 &
;;
stop)
  echo "stopping hiveserver2"

  pid=$(ps -ef | grep org.apache.hive.service.server.HiveServer2 | grep -v grep | awk '{print $2}')
  if [ -n "$pid" ]; then
  kill -15 "$pid"
  echo "hiveserver2 stop,kill $pid"
  sleep 2
  fi
;;
restart)
  echo "restaring hiveserver2"
  $0 stop
  $0 start
;;
status)
  echo "hiveserver2 status"
  pid=$(ps -ef | grep org.apache.hive.service.server.HiveServer2 | grep -v grep | awk '{print $2}')
  if [ -n "$pid" ]; then
    echo "hiveserver2 (pid $pid) status is running"
  else
    echo "hiveserver2 status is down"
  fi
;;
*)
echo "usage:{start|stop|restart|status}"
exit 1
;;
esac
