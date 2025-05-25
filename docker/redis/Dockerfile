FROM redis:7.4
# WORKDIR /var/lib/mysql
LABEL maintainer="1121728663@qq.com"

EXPOSE 6379

RUN mkdir -p /etc/redis 

COPY redis.conf /etc/redis

# 启动 Redis 服务，指定配置文件
CMD ["redis-server", "/etc/redis/redis.conf"]