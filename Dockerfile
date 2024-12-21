# 使用更轻量级的基础镜像，openjdk:21-jre-slim，openjdk:21-jre-alpine，eclipse-temurin:21-jre-alpine
FROM eclipse-temurin:21-jre-alpine

# 作者信息
LABEL maintainer="zmxccxy <zmxccxy@aliyun.com>"

# 创建并设置工作目录
WORKDIR /nas-fmt

# 将打包后的 JAR 文件复制到容器中
COPY target/nas-file-management-tool-0.8.1.jar /nas-fmt/nas-file-management-tool-0.8.1.jar

# 暴露端口
EXPOSE 6996

# 设置容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "nas-file-management-tool-0.8.1.jar"]