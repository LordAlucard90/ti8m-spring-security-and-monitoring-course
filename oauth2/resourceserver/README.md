# Resource Server

## Run spring boot application

- Start the Resource Server by running `ResourceServerApp.java`
- Alternatively, issue the command `mvn clean spring-boot:run` in this directory

## Tasks
1. Set the <i>@Primary</i> annotion on the method <i>registeredClientRepositoryWithAuthorizationCode</i> in the [SecurityConfig](../authserver/src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
2. Start the Authorization Server by running `AuthServerApp.java`
3. Retrieve an access token by following the <i>Auth Flow with AuthorizationCode (with PKCE)</i> procedure
4. Use the retrieved access token to get the <i>demo</i> resource from the Resource Server by executing the shell script: 
   ```shell
   curl -X 'GET' 'http://localhost:9090/demo' --header 'Authorization: Bearer REPLACE_ME_WITH_AN_ACCESS_TOKEN'
   ```
