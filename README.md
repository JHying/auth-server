# AuthService
A simple JWT authentication and user login service.

# Introduction
This project was generated with spring boot 2 and hibernate 5 (integrated into spring orm).

### IDE
IDEA 2022.1
### Build
Maven
### Language
JAVA 11
### Run
JAR
### Datasource
MySQL + Redis

# Design
1. request token. (token is generated with RSA)
2. use for user login. (password is encrypted with AES)
3. If user login is verified then add user info data to redis.
4. There are some other APIs need to request with token: add new user, get auth list, save auth list to redis
5. Use private maven repo https://github.com/JHying/mvn-repo
6. register as an eureka client (for spring cloud structure)
