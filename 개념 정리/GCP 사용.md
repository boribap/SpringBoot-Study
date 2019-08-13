## GCP 사용

#### 인스턴스 생성

- CentOS 7으로 생성 - MariaDB 사용 --> MySQL  설치해야함 

- mysql 설치 

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

#### 고정 IP 로 변경

#### 방화벽 설정 

| 포트 | 설명                                           |
| ---- | ---------------------------------------------- |
| 8080 | board 어플리케이션을 실행하는 톰캣 포트        |
| 8888 | cloud-config 어플리케이션을 실행하는 톰캣 포트 |
| 3306 | MySQL 포트                                     |
| 9100 | Jenkins 포트                                   |

- 위에 해당하는 방화벽 규칙을 추가해야한다. 