FROM youzhanghao/centos_jdk:1.8.0_131
MAINTAINER youzhanghao <youzhanghao@ennew.cn>
ADD target/*.jar /opt/
CMD java -Dfile.encoding=utf-8  -jar -Duser.timezone="Asia/Shanghai" $JAVA_OPTS /opt/*.jar
