FROM swaggerapi/swagger-ui:latest
MAINTAINER Toukach

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD /employee-service/spec/employee-service.yaml /usr/share/nginx/html/doc/employee-service.yaml

ENV URLS="[{ url: 'doc/employee-service.yaml', name: 'EmployeeService'}]"
ENV URLS_PRIMARY_NAME="EmployeeService"