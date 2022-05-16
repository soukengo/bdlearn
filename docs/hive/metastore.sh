#!/bin/bash
WORK_DIR=$(cd $(dirname "$0") || exit; pwd)

LOG_PATH=$WORK_DIR"/logs"
mkdir -p "$LOG_PATH"

case "$1" in
start)
  echo "starting metastore"
  nohup hive --service metastore  >> "$LOG_PATH"/metastore.log 2>&1 &
;;
stop)
  echo "stopping metastore"

  pid=$(ps -ef | grep org.apache.hadoop.hive.metastore.HiveMetaStore | grep -v grep | awk '{print $2}')
  if [ -n "$pid" ]; then
  kill -15 "$pid"
  echo "metastore stop,kill $pid"
  sleep 2
  fi
;;
restart)
  echo "restaring metastore"
  $0 stop
  $0 start
;;
status)
  echo "metastore status"
  pid=$(ps -ef | grep org.apache.hadoop.hive.metastore.HiveMetaStore | grep -v grep | awk '{print $2}')
  if [ -n "$pid" ]; then
    echo "metastore (pid $pid) status is running"
  else
    echo "metastore status is down"
  fi
;;
*)
echo "usage:{start|stop|restart|status}"
exit 1
;;
esac
