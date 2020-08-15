# doctorVisit

This application was generated using JHipster 6.8.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.8.0](https://www.jhipster.tech/documentation-archive/v6.8.0).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Development

To start your application in the dev profile, run:

    ./mvnw

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Doing API-First development using openapi-generator

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./mvnw generate-sources
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

### Packaging as jar

To build the final jar and optimize the doctorVisit application for production, run:

    ./mvnw -Pprod clean verify

To ensure everything worked, run:

    java -jar target/*.jar

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

or

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.8.0 archive]: https://www.jhipster.tech/documentation-archive/v6.8.0
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.8.0/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.8.0/development/
[service discovery and configuration with the jhipster-registry]: https://www.jhipster.tech/documentation-archive/v6.8.0/microservices-architecture/#jhipster-registry
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.8.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.8.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.8.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.8.0/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.8.0/setting-up-ci/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: https://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/documentation-archive/v6.8.0/doing-api-first-development/


```sql

UPDATE bus_self_diagnose 
set fid = CONCAT(left(fid,1),RIGHT(fid,2)) where LENGTH(fid)=4;

```

redis
wangkuan0203

mysql
mysql@123.com

ssh 
root/wangkuan195961V


nginx 
/usr/local/etc/nginx/servers/doctorvisit.conf



```text
外网 - 网关
http://121.196.184.57:8010
内网 - 网关
http://172.16.125.89:8010/

注册中心
http://www.doctorvisit.icu:8761


https://wk.zhangfan.ink/sys-manage/

https://wk.zhangfan.ink/api/authenticate

https://wk.zhangfan.ink/doctorvisit/api/article/listArticleClass


前端
header ： token=


测试
AppID(小程序ID) wx28e94f16479e6fd6
AppSecret(小程序密钥) c87443a30e54d65ee6af19bbe22d3476
```

```text
pom.xml 跳过验证 openapi-generator 
<skipValidateSpec>true</skipValidateSpec>

mvn -Pprod clean package -Dmaven.test.skip=true

```

```text
websocket

https://my.oschina.net/u/3580577/blog/2088114
https://gitee.com/Yeauty/netty-websocket-spring-boot-starter
https://github.com/YeautyYE/netty-websocket-spring-boot-starter
```


```text
2020-08-10
1.首页搜索“找医生、找医院”，一个接口根据输入的内容，查询医生跟医院，查询出来的医生或者医院要有标识，方便前端展示医生或者医院，前端是联想方式
答：doctor/listDoctorOrHospital  参数name 查询，出来两个列表数据。

2.聊天列表 本地存储下还是 后台有接口呢 
答：还没写

3.单纯聊天websocket实现吗
答：参考后台的websocket实现

4.不同科室怎么定义（数据库表里面没写具体定义id）
啥意思？？？？？

5.智能问询，
POST /front/self/diagnostics/listSelfDiagnose 获取自诊问题列表
POST/front/self/diagnostics/listGSelfDiagnosis 获取自诊结果列表
这两个接口怎么使用呢，用户先输入自己病情描述，这个时候调用哪个接口
答：
1、进页面请求 https://wk.zhangfan.ink/doctorvisit/front/self/diagnostics/listSelfDiagnose?id=0 获取顶级问题列表
2、再根据每次的点击的问题id获取下级问题选项。  
3、当点击的选项数据里有 "diagnosis": **** 字段的时候说明有诊断结果了，根据这个id获取诊断结果。
4、比如：https://wk.zhangfan.ink/doctorvisit/front/self/diagnostics/listGSelfDiagnosis?id=1001

6.生成订单报错 500，咨询订单列表报错 500 
答：已解决

7.患者 没有年龄、手机号字段
答：出生日期为年龄 「'busPatient' on field 'birthday': rejected value [2020年08月04日] 」----> 这个字段用2020-08-04的格式，手机号为mobile

8.生成咨询订单的时候，前端有上传图片的操作
答：接口已添加接受图片，列表添加imgs字段，多张用,隔开

9.文章数据里面少个封面图片字段， 现在的url 字段是 详情的html吗，前端直接加载html吗
答：添加 coverImg 封面图片

10.“我的”界面，需要一个返回 当前收藏数、分享数、关注数的接口
答：https://wk.zhangfan.ink/doctorvisit/front/mine/findMineCount

11.分享 成功，但是已分享列表里面没有
答：调用分享接口了吗？

12.已关注的医生列表没有数据
答：已解决

```
