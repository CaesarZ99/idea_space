<!-- TOC -->
  * [IDEA SPACE](#idea-space)
    * [声明](#声明)
      * [简介](#简介)
      * [前端](#前端)
      * [后端](#后端)
      * [开发途中遇到的问题](#开发途中遇到的问题)
<!-- TOC -->
## IDEA SPACE

> ### 该项目只是一时的想法，做一个网站，记录自己生活中的一些灵感
> 公网域名访问 http://caesar.icu
> 由于服务器性能问题访问比较慢

### 声明
> ## 如有侵权的地方, 请联系2900128182@qq.com, 立马删除相关部分

---

#### 简介
> <p>&emsp;&emsp;项目一共有三台服务器(腾讯云*1, 华为云*2), 域名为阿里云购买: caesar.icu </p>
> <p>&emsp;&emsp;最开始的想法是使用k8s+docker搭建一主二从的k8s集群, 使用pod来对docker扩缩容和编排管理。后来发现腾讯云无法和华为云内网连通, 导致安装ingress-nginx失败。后来使用两台华为云服务器搭建集群成功(但华为云的devops工具实在无法导入服务器k8s配置(不知道kube config该填什么), 腾讯云devops连接服务器搭建的k8s成功, 可以在页面管理POD,打包/构建/推送镜像并部署在k8s的pod中)</p>
> <p>&emsp;&emsp;后来不知道什么问题, 导致从域名caesar.icu访问k8s集群中的前端项目特别慢, 无奈放弃k8s, 使用nginx和docker来部署项目。</p>
> <p>&emsp;&emsp;nacos作为配置中心, space-main模块为项目核心模块, space-file模块为文件系统(通过spring集成腾讯云的cos对象存储来实现文件的上传下载)</p>
> <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;···持续更新中···since：2023年4月24日</p>

---

#### 前端

- 代码地址: https://github.com/CaesarZ99/idea_space_web
- 技术栈:
    - vue2 + html + css + axios

#### 后端

- 地址: https://github.com/CaesarZ99/idea_space
- 技术栈:
    - redis + spring cloud + nacos + rabbitmq + docker + ...

#### 开发途中遇到的问题

- 问题1: 
  - 部署k8s集群时, 无法连通腾讯云, 使用VPC互通让腾讯云访问华为云, 可腾讯云是轻量级服务器, 导致配置VPC失败
  - 解决措施: 放弃腾讯云, 使用华为云两个服务器部署k8s+docker
  - 参考地址: 
    - [云原生实战](https://www.yuque.com/leifengyang/oncloud/ghnb83)
    - [Kubernetes easy doc](https://k8s.easydoc.net/docs/dRiQjyTY/28366845/6GiNOzyZ/nd7yOvdY)
- 问题2
  - docker部署前端项目, 直接使用npm run dev命令启动项目: 加载app.js(2.1M)过大, 访问前端很慢
  - 解决措施: 重新编写dockerfile, 将build之后的文件copy到nginx的/usr/share/nginx/html/目录下进行访问
  - ```dockerfile
    FROM node:latest AS builder
    #移动当前目录下面的文件到app目录下
    ADD . /app/
    #进入到app目录下面，类似cd
    WORKDIR /app
    RUN npm config set registry https://registry.npmmirror.com/
    #安装依赖
    RUN npm install && npm run build
    FROM nginx
    COPY --from=builder /app/dist /usr/share/nginx/html/
    ```
- 问题3
  - linux通过docker部署nacos时, 启动容器失败, nacos部署在华为云, 持久化mysql在腾讯云
  - 解决措施: docker logs 容器id 查看容器日志, 发现是无法连接mysql, 放行端口后成功
  - ```shell
    docker run -d \
    --name nacos \
    -e PREFER_HOST_MODE=hostname \
    -e MODE= 模式, 非集群使用的是 standalone  \
    -e SPRING_DATASOURCE_PLATFORM=mysql \
    -e MYSQL_SERVICE_HOST=数据库host \
    -e MYSQL_SERVICE_PORT=端口 \
    -e MYSQL_SERVICE_USER=用户名 \
    -e MYSQL_SERVICE_PASSWORD=数据库连接密码 \
    -e MYSQL_SERVICE_DB_NAME=nacos \
    --network=host \
    nacos/nacos-server
    ```
- 问题4
  - 部署了nacos之后, 从nacos获取配置启动项目时报错: 无法获取数据源url, redis连接访问127.0.0.1, 连接拒绝(redis在腾讯云docker中)等错误
  - 解决措施: 查看bootstrap.yml中配置, namespace和group配置正确, @Value(${xxx})能获取值, 仔细检查后发现nacos配置的yml中缺少"spring:"前缀
  - 传送门: 
    - [docker安装nacos](https://huaweicloud.csdn.net/638db194dacf622b8df8c5f2.html?spm=1001.2101.3001.6650.6&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Eactivity-6-125168548-blog-123118920.235%5Ev32%5Epc_relevant_default_base&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Eactivity-6-125168548-blog-123118920.235%5Ev32%5Epc_relevant_default_base&utm_relevant_index=13)
  - ```yaml
    # bootstrap.yml
    server:
      port: 8080
    spring:
      application:
        name: space-main # 服务名称
      cloud:
        nacos:
          config:
            file-extension: yml # nacos配置中心文件后缀
            refresh-enabled: true # 是否实时刷新
            server-addr: nacos服务器ip:8848
      profiles:
        active: dev # nacos配置文件匹配规则:${spring.application.name}-${spring.profiles.active}.${file-extension}
    ```
- 问题5
  - 

