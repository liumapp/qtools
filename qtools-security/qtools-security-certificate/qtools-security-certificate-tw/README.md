# 1. 前期准备

## 1.1 发布天威诚信依赖包到私服

### 1.1.1 依赖包

在libs目录下

* iTrusJavaCertAPI-2.7.10.jar

    groupId: com.itrus
    
    artifactId: iTrusJavaCertAPI-2.7.10
    
    version: 2.7.10
    
* javax.wsdl_1.6.2.v201012040545.jar

    groupId: javax.wsdl
        
    artifactId: javax
    
    version: 1.6.2

* javax.wsdl_1.6.2.v201012040545.jar

    groupId: com.ibm.wsdl
        
    artifactId: wsdl
    
    version: 1.6.2
    
* top-core-1.9.jar

    groupId: cn.topca
        
    artifactId: top-core
    
    version: 1.9
    
* top-security-1.9.jar

    groupId: cn.topca.security
        
    artifactId: top-security
    
    version: 1.9
    
* TopBasicCrypto-1.9.jar

    groupId: cn.tca.TopBasicCrypto
        
    artifactId: TopBasicCrypto
    
    version: 1.9
    
* TopESA-java.jar

    groupId: cn.topca.api.cert
        
    artifactId: TopESA-java
    
    version: 1.9
    
* rt.jar

    groupId: sun.security
        
    artifactId: rt
    
    version: 1.0

javax.wsdl_1.6.2.v201012040545.jar包下含有两个group，所以您需要倒入nexus两次（请吐槽天威诚信的开发人员）

### 1.1.2 上传至Nexus私服

进入libs目录，依次执行以下命令发布jar包：

* iTrusJavaCertAPI-2.7.10.jar

        mvn deploy:deploy-file -DgroupId=com.itrus -DartifactId=iTrusJavaCertAPI-2.7.10 -Dversion=2.7.10 -Dpackaging=jar -Dfile=./iTrusJavaCertAPI-2.7.10.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp
        
* javax.wsdl_1.6.2.v201012040545.jar

        mvn deploy:deploy-file -DgroupId=javax.wsdl -DartifactId=javax -Dversion=1.6.2 -Dpackaging=jar -Dfile=./javax.wsdl_1.6.2.v201012040545.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

* javax.wsdl_1.6.2.v201012040545.jar

        mvn deploy:deploy-file -DgroupId=com.ibm.wsdl -DartifactId=wsdl -Dversion=1.6.2 -Dpackaging=jar -Dfile=./javax.wsdl_1.6.2.v201012040545.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

* top-core-1.9.jar

        mvn deploy:deploy-file -DgroupId=cn.topca -DartifactId=top-core -Dversion=1.9 -Dpackaging=jar -Dfile=./top-core-1.9.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

* top-security-1.9.jar

        mvn deploy:deploy-file -DgroupId=cn.topca.security -DartifactId=top-security -Dversion=1.9 -Dpackaging=jar -Dfile=./top-security-1.9.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

* TopBasicCrypto-1.9.jar

        mvn deploy:deploy-file -DgroupId=cn.tca.TopBasicCrypto -DartifactId=TopBasicCrypto -Dversion=1.9 -Dpackaging=jar -Dfile=./TopBasicCrypto-1.9.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

* TopESA-java.jar

        mvn deploy:deploy-file -DgroupId=cn.topca.api.cert -DartifactId=TopESA-java -Dversion=1.9 -Dpackaging=jar -Dfile=./TopESA-java.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp
    
* rt.jar

        mvn deploy:deploy-file -DgroupId=sun.security -DartifactId=rt -Dversion=1.0 -Dpackaging=jar -Dfile=./rt.jar -Durl=http://www.liumapp.com:8081/repository/liumapp/ -DrepositoryId=liumapp

## 1.2 加载依赖

配置项目pom文件，添加以下内容来完成对天威诚信依赖的加载：

    <repositories>
        <repository>
            <id>liumapp</id>
            <url>http://www.liumapp.com:8081/repository/liumapp/</url>
        </repository>
    </repositories>
    
    ...
    
        <dependency>
          <groupId>org.bouncycastle</groupId>
          <artifactId>bcmail-jdk15</artifactId>
          <version>1.46</version>
          <optional>true</optional>
        </dependency>
        <dependency>
          <groupId>commons-codec</groupId>
          <artifactId>commons-codec</artifactId>
          <version>1.6</version>
        </dependency>
        <dependency>
          <groupId>org.apache.axis</groupId>
          <artifactId>axis</artifactId>
          <version>1.4</version>
        </dependency>
        <dependency>
          <groupId>org.bouncycastle</groupId>
          <artifactId>bcprov-jdk15</artifactId>
          <version>1.46</version>
        </dependency>
        <dependency>
          <groupId>javax.xml</groupId>
          <artifactId>jaxrpc</artifactId>
          <version>1.1</version>
        </dependency>
        <dependency>
          <groupId>commons-configuration</groupId>
          <artifactId>commons-configuration</artifactId>
          <version>1.6</version>
        </dependency>
        <dependency>
          <groupId>commons-discovery</groupId>
          <artifactId>commons-discovery</artifactId>
          <version>0.2</version>
        </dependency>
        <dependency>
          <groupId>commons-lang</groupId>
          <artifactId>commons-lang</artifactId>
          <version>2.4</version>
        </dependency>
        <dependency>
          <groupId>commons-logging</groupId>
          <artifactId>commons-logging</artifactId>
          <version>1.1.1</version>
        </dependency>
        <dependency>
          <groupId>org.json</groupId>
          <artifactId>json</artifactId>
          <version>20090211</version>
        </dependency>
        <dependency>
          <groupId>com.itrus</groupId>
          <artifactId>iTrusJavaCertAPI-2.7.10</artifactId>
          <version>2.7.10</version>
        </dependency>
        <dependency>
          <groupId>javax.wsdl</groupId>
          <artifactId>javax</artifactId>
          <version>1.6.2</version>
        </dependency>
        <dependency>
          <groupId>com.ibm.wsdl</groupId>
          <artifactId>wsdl</artifactId>
          <version>1.6.2</version>
        </dependency>
        <dependency>
          <groupId>cn.topca</groupId>
          <artifactId>top-core</artifactId>
          <version>1.9</version>
        </dependency>
        <dependency>
          <groupId>cn.topca.security</groupId>
          <artifactId>top-security</artifactId>
          <version>1.9</version>
        </dependency>
        <dependency>
          <groupId>cn.tca.TopBasicCrypto</groupId>
          <artifactId>TopBasicCrypto</artifactId>
          <version>1.9</version>
        </dependency>
        <dependency>
          <groupId>cn.topca.api.cert</groupId>
          <artifactId>TopESA-java</artifactId>
          <version>1.9</version>
        </dependency>
        <dependency>
          <groupId>sun.security</groupId>
          <artifactId>rt</artifactId>
          <version>1.0</version>
        </dependency>    

    ...

请注意，不要添加以下依赖，会产生冲突（请吐槽天威诚信开发人员）：

    <dependency>
      <groupId>org.bouncycastle</groupId>
      <artifactId>bcprov-jdk15on</artifactId>
      <version>1.54</version>
    </dependency>
    <dependency>
      <groupId>org.bouncycastle</groupId>
      <artifactId>bcpkix-jdk15on</artifactId>
      <version>1.54</version>
    </dependency>

# 2. 配置认证证书

## 2.1 认证文件








