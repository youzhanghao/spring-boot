FROM 10.19.248.200:30100/g_laikang/jdk-oneapm:1.80-last
MAINTAINER youzhanghao <youzhanghao@ennew.cn>
ADD target/LK_event_service.jar /opt/
ENV STAND_ALONE="No"
ENV ONEAPM_AGENT="Yes"
ENV SECRET_KEY="E10ADC3949BA59ABBE56E057F20F883E"
#CMD java -Dfile.encoding=utf-8  -jar -Duser.timezone="Asia/Shanghai" $JAVA_OPTS /opt/LK_event_service.jar
