# Authorization Server

## Run spring boot application

- Start the Authorization Server by running `AuthServerApp.java`
- Alternatively, issue the command `mvn clean spring-boot:run` in this directory

## Tasks
### OpenId configuration
1. Have a look at the [openid-configuration](http://localhost:8080/.well-known/openid-configuration)
2. How do you interpret the exposed values?

### Auth Flows
There are different beans returning a RegisteredClientRepository in the [SecurityConfig](src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
Use the <i>@Primary</i> annotation to select the one you want to explore (for the tasks below).

#### Auth Flow with Client Credentials
1. Set the <i>@Primary</i> annotion on the method <i>registeredClientRepositoryWithClientCredentials</i> in the [SecurityConfig](src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
2. Use the [BasicAuthUsernamePasswordGenerator](src/main/java/ch/ti8m/academy/oauth2/generate/BasicAuthUsernamePasswordGenerator.java) to encode the client credentials.
3. Get an access token by executing the shell script, with the client credentials provided:
   ```shell
   curl -X 'POST' 'http://localhost:8080/oauth2/token?grant_type=client_credentials&scope=CUSTOM' --header 'REPLACE_ME'
   ```
4. Use https://jwt.io to decode and inspect the returned access token
   - Where does the priority field come from?
   - Where does the scope field come from?

#### Auth Flow with AuthorizationCode (without PKCE)
1. Set the <i>@Primary</i> annotion on the method <i>registeredClientRepositoryWithAuthorizationCode</i> in the [SecurityConfig](src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
2. Make the Http authorization request [http://localhost:8080/oauth2/authorize](http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.ti8m.com/authorized)
3. Log in (the credentials can be found in the userDetailsService Bean)
4. Get an access token by copying the returned authorization code in the response into the shell script and execute it:
   ```shell
   curl -X 'POST' 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=https://www.ti8m.com/authorized&grant_type=authorization_code&code=qk2nxpEw67f8e-oxMfG9-bKqyJx6NC_9g7CMWrw6JOythC7W_9mjmyRWPQOFQ40iZ_oeXDJTFb4ir9XDm8wv9LRyQemv0W8l5zu4ChbiXJjRED73EdoKZQyT0lc-7zAC' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
   ```
5. Use https://jwt.io to decode and inspect the returned access token

#### Auth Flow with AuthorizationCode (with PKCE)
1. Set the <i>@Primary</i> annotion on the method <i>registeredClientRepositoryWithAuthorizationCodeAndPkce</i> in the [SecurityConfig](src/main/java/ch/ti8m/academy/oauth2/web/SecurityConfig.java)
2. Use the provided tools to generate a <i>verifier</i> and its <i>challenge</i>
3. Copy the challenge into the Http authorization request and execute it: [http://localhost:8080/oauth2/authorize](http:/localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.ti8m.com/authorized&code_challenge=h8_t7bWz0RJbadHxiORESYRq6RjdSK13SU_eUdrvzH4&code_challenge_method=S256)
4. Get an access token by copying the verifier and the returned authorization code in the [Response](https://www.ti8m.com/authorized?code=L0JHopvaOVbBU2CKx_5zjm0RzX8FbAS4ScsoPR2JUX4AitJ3tXtIV-973_BkMhTeRgmgv1ojUO23Lt08aHawOG77IghDRW4m-3efbWPzv024PO2ai6mCtrB3zu8MfDkd)
   into the shell script and execute it:
   ```shell
   curl -X 'POST' 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=https://www.ti8m.com/authorized&grant_type=authorization_code&code=REPLACE_ME&code_challenge_method=S256&code_verifier=REPLACE_ME' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
   ```
5. Use https://jwt.io to decode and inspect the returned access token

## Tools

### Basic Auth

Use the [BasicAuthUsernamePasswordGenerator](src/main/java/ch/ti8m/academy/oauth2/generate/BasicAuthUsernamePasswordGenerator.java) to create
an authorization header in the form <i>Authorization: Basic Y2xpZW50OnNlY3JldA==</i>

### PKCE

1. Use the [VerifierGenerator](src/main/java/ch/ti8m/academy/oauth2/generate/VerifierGenerator.java) to create a random (secret) value, the so called <i>verifier</i>
2. Insert the previously generated <i>verifier</i> into the [ChallengeGenerator](src/main/java/ch/ti8m/academy/oauth2/generate/ChallengeGenerator.java) to create the <i>challenge</i>

## Troubleshooting
Using curl on windows in the terminal if something like this
```
Invoke-WebRequest : A parameter cannot be found that matches parameter name 'X'.
```
is returned, use this command to remove the alias:
```
Remove-item alias:curl
```