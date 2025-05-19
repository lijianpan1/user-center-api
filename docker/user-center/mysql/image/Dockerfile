FROM mysql:8.0
WORKDIR /
LABEL maintainer="1121728663@qq.com"

EXPOSE 3306

# COPY my.cnf /etc/mysql/conf.d
# COPY mysql.sql /docker-entrypoint-initdb.d

RUN chmod -R ug+rw,o=r /etc/mysql/conf.d \
# && chmod +x /docker-entrypoint-initdb.d/mysql.sql \
&& mkdir -p /var/log/mysql \
&& chown -R 999:999 /etc/mysql /var/lib/mysql /var/log/mysql

# 切换回默认的 MySQL 启动命令
CMD ["mysqld"] 