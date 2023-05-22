# 该镜像需要依赖的基础镜像
FROM openjdk:11
# 将当前目录下的jar包复制到docker容器的/目录下
COPY /target/space-file-0.0.1-SNAPSHOT.jar /space-file-0.0.1-SNAPSHOT.jar
# 运行过程中创建一个idea-space-0.0.1-SNAPSHOT.jar文件
RUN bash -c 'touch /space-file-0.0.1-SNAPSHOT.jar'
# 声明服务运行在8001端口
EXPOSE 8001
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/space-file-0.0.1-SNAPSHOT.jar"]
