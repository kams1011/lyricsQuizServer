#!/bin/bash

# 애플리케이션 관련 변수 정의
REPOSITORY=/home/ubuntu/app
APP_NAME=demo
JAR_PATH=$REPOSITORY/build/libs/$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)

# 현재 실행 중인 프로세스 확인
CURRENT_PID=$(pgrep -f $APP_NAME)

# 현재 실행 중인 프로세스가 없으면 종료
if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> 기존 애플리케이션 종료: kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

# 80 포트를 사용하는 프로세스 종료
PORT=80
PORT_PID=$(sudo lsof -t -i:$PORT)
if [ ! -z "$PORT_PID" ]
then
  echo "> 80 포트를 사용하는 프로세스 종료: kill -15 $PORT_PID"
  sudo kill -15 $PORT_PID
  sleep 5
fi

# JAR 파일 실행
if [ -e $JAR_PATH ]
then
  echo "> $JAR_PATH 배포 시작"
  nohup sudo java -jar -Dspring.profiles.active=dev $JAR_PATH > $REPOSITORY/nohup.out 2>&1 &
  echo "> $JAR_PATH 배포 완료"
else
  echo "> 배포할 JAR 파일을 찾을 수 없습니다: $JAR_PATH"
  exit 1
fi
