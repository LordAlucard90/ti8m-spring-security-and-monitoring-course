# Client

## Run spring boot application

- Start the Authorization Server by running `AuthServerApp.java`
- Start the Client by running `ClientApp.java` (Precondition: `AuthServerApp.java` has to be running)
- Alternatively, issue the command `mvn clean spring-boot:run` in this directory

## Tasks
### Custom Authorization Server (AuthServerApp)
3. Set the <i>@Primary</i> annotion on the method <i>registeredClientRepositoryWithAuthorizationCodeAndPkce_ClientApp</i> in the [SecurityConfig](../authserver/src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
2. Open: http://127.0.0.1:7070/index.html -> You're redirected to the login page. Why is this?
3. Login using the credentials ("username", "password").
4. Open: http://127.0.0.1:7070/index.html

### Authorization Server by Github or Google
1. Register a new OAuth application
    1. github: [Register](https://github.com/settings/applications/new) | [Documentation](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app) 
    2. google: [Documentation](https://support.google.com/cloud/answer/6158849?hl=en)
2. Add your <i>clientId</i> and <i>clientSecret</i> in the [application.yml](src/main/resources/application.yml)
3. Try it out