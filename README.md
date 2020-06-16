# SpaceBank
spacebank_api open source REST API for Banking  

## Steps to Setup the Spring Boot Back end app (spacebank_api)

1. **Clone the application**

	```bash
	git clone https://github.com/netshiaMR/spacebank_api.git
	cd spacebank_api
	```

2. **Create MySQL database**

	```bash
	create database spacebankdb
	```

3. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.properties` file.

	+ change `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	The server will start on port 8080.

	You can also package the application in the form of a `jar` file and then run it like so -

	```bash
	mvn package
	java -jar target/spacebank-0.0.1.jar
	```
5. **Default Roles**
	
	The spring boot app uses role based authorization powered by spring security. To add the default roles in the database, I have added the following sql queries in `src/main/resources/data.sql` file. Spring boot will automatically execute this script on startup -

	```sql
	INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
	INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
	INSERT IGNORE INTO roles(name) VALUES('ROLE_INVESTOR');
	```

	Any new user who signs up to the app is assigned the `ROLE_USER` by default.