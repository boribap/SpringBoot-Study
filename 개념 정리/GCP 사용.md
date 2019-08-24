## GCP 사용

#### 인스턴스 생성

- CentOS 7으로 생성 - MariaDB 사용 --> MySQL  설치해야함 



- **mysql 설치** 

  `sudo yum -y install http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm`

  `sudo yum -y install mysql-community-server`

  `sudo systemctl enable mysqld`

  `sudo systemctl start mysqld`

- mysql 보안관리 

  `/usr/bin/mysql_secure_installation`

- mysql 사용자 생성 & 권한 부여 

  root 사용자는 권한이 막강하기 때문 

  `create user '사용자ID'@'localhost' identified by '비밀번호';`

  `create user '사용자ID'@'%' identified by '비밀번호';`

  `grant all privileges on *.* to '사용자ID'@'%';`

  `flush privileges;`

- 문자열 설정 

  mysql 의 기본문자열을 utf-8을 사용하도록 /etc/my.cnf 파일 수정 



- **jdk 설치**

  jdk 파일 다운로드 후 압축해제

- 폴더 이동 및 심볼릭 링크 생성 (jdk의 버전이 바뀔 수 있기 때문에 심볼릭 링크를 만들어 편하게 사용)

  `sudo mv jdk1.8.0_181 /usr/local`

  `cd /usr/local/`

  `sudo ln -s jdk1.8.0/ java`

- jdk 위치 지정

  /etc/profile 에 환경 변수 등록 

  ```
  JAVA_HOME=/usr/local/java
  cLASSPATH=.:$JAVA_HOME/lib/tools.jar
  PATH=$PATH:$JAVA_HOME/bin
  export JAVA_HOME CLASSPATH PATH
  ```

  

- **톰캣 설치**

  톰캣 : WAS(Web Application Server)

  스프링 부트 어플리케이션은 톰캣을 내장하고 있지만 실제 서비스하기에는 어려움이 있음 --> 그래서 톰캣 설치

- board 어플리케이션 구동할 톰캣 & cloud-config 어플리케이션 구동할 톰캣 필요 --> 2개의 톰캣을 생성 

  ```
  cp -r apache-tomcat-8.5.43 apache-tomcat-8.5.43-board
  sudo mv apache-tomcat-8.5.43-board /usr/local/apache-tomcat-8.5.43-board
  sudo mv apache-tomcat-8.5.43 /usr/local/apache-tomcat-8.5.43-config
  ```

- board 톰캣 설정하기 

  apache-tomcat-8.5.43-board 의 conf 폴더의 server.xml 수정 

  ```
  // 성능상 false 로 바꿈 
  <Host name="localhost"  appBase="."             unpackWARs="false" autoDeploy="false">        
  // 톰캣이 사용할 소스의 위치 지정 --> 편의를 위함 
  <Context docBase="/home/아이디/src/board" path="" reloadable="false"/>
  ```

- config 톰캣 설정 

  2개 이상의 톰캣이 있으면 동일 포트를 사용하려고 해서 문제가 생김 --> 포트 변경해주기 

  apache-tomcat-8.5.43-config 의 conf 폴더의 server.xml 수정

  - 톰캣에 접속시 사용하는 포트 (8080 --> 8888)
  - ajp 포트도 변경 (사용하지는 않지만 중복되면 안되므로 바꿔줌) 
    - AJP : Apache JServ Protocol : 아파치와 톰캣을 연동할때 사용. 아파치 웹서버가 톰캣과 같은 WAS 와 연동하기 위한 규약



- **젠킨스 설치**

  - wget 설치 후 해당 명령어로 젠킨스 설치파일 다운

    `sudo yum -y install wget`

    `sudo wget -0 /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins/repo`

    `sudo rpm --import http://pkg.jenkins-ci.org/redhat/jenkins-ci.org.key`

    `sudo yum -y install jenkins`

- 젠킨스 설정 

  - 젠킨스 포트 변경 (9100으로)

  - 우리가 설치한 자바 경로로 젠킨스의 자바 경로 수정 

  - *오류 해결 못함*

    > AWT is not properly configured on this server. Perhaps you need to run your container with "-Djava.awt.headless=true"? See also: https://jenkins.io/redirect/troubleshooting/java.awt.headless 

    위와 같은 오류 발생가 발생해서 `/etc/sysconfig/jenkins` 파일의 `JAVA OPTION 부분` 수정했는데도 오류 안사라짐  --> 그래서 지금 stop 중인 상태
    
    > 다시 인스턴스 생성하고 같은 과정을 반복해서 해결완료 
  
  - 비트버킷 플러그인 설치
  
- 깃 설치 

- 그레이들 설치 

- ssh 등록 과정에서 `Permission denied (publickey,gssapi-keyex,gssapi-with-mic).` 와 같은 오류가 발생해 다시 stop 상태 --> 리부팅하여 해결 

- 톰캣에 JAVA_HOME 설정

#### 고정 IP 로 변경

#### 방화벽 설정 

| 포트 | 설명                                           |
| ---- | ---------------------------------------------- |
| 8080 | board 어플리케이션을 실행하는 톰캣 포트        |
| 8888 | cloud-config 어플리케이션을 실행하는 톰캣 포트 |
| 3306 | MySQL 포트                                     |
| 9100 | Jenkins 포트                                   |

- 위에 해당하는 방화벽 규칙을 추가해야한다. 



