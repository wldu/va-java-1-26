package week4;

/**
 *  *      1. how oracle works
 *  *      2. security
 *  *          Spring security
 *  *          JWT
 *  *          HTTPS = HTTP + TLS / SSL
 *  *          Oauth2.0
 *  *          OpenID
 *  *          CORS
 *  *          CSRF
 *  *          ..
 *
 *
 *  Security
 *  how to secure webservice /endpoint / application
 *      Symmetric : same key for encryption + decryption
 *      Asymmetric: public key + private key
 *                  public key encrypt -> private key decrypt
 *                  private key encrypt -> public key decrypt
 *     1. authentication : 401 un-authneticated?
 *          2FA
 *          username password
 *          code -> text / email
 *     2. authorization : 403 unauthorized
 *          verify roles
 *          JWT : json web token
 *                header.payload.signature
 *                encode(header.payload.encrypted(signature))
 *
 *
 *     3. HTTPS = Http + TLS / SSL
 *
 *          client                  server(private key)
 *                   -> hi
 *                   <- certificate
 *                      public key
 *                exchange some public value
 *
 *              generate symmetric key from both side
 *              encrypt data by using symmetric key
 *
 *
 *         client: public value a, private value m -> can get n from a, b, M, N
 *
 *         server: public value b, private value n -> can get m from a, b, M, N
 *
 *         server client exchange public value a , b, and value M created by (a, m), value N created by (b, n)
 *         hackers can get : a, b, M, N
 *
 *         hacker cannot get m and n
 *
 * 
 *
 *
 *         CA -> Certificate Authority
 *                  |
 *               generate (intermediate certificate + server certificate) for your server
 *
 *   4. Oauth2.0
 *      implicit flow
 *
 *          client
 *           |      \
 *         app      security provider
 *
 *      explicit flow
 *
 *          client  - security provider
 *            |
 *          app
 *
 *       1. client get google / xx login page
 *       2. google / security center -> redirect + attach access_code
 *       3. client send access_code to app
 *       4. app -> send access_code -> google / security center
 *                 client id, client secret
 *                 <- access_token
 *       5. app generate jwt -> send to client
 *  5. OpenID -> open id token , for authentication
 *      1. SSO
 *      2. open id contains
 *              user phone / name/ other info
 *
 *  6. CORS
 *
 *       browser -> preflight request -> server (API GATEWAY, option)
 *               <- response header
 *                  access-allow-header..: *
 *                  access-allow-method  : *
 *                  access-allow-domain : *
 *      browser -> send real request  -> server
 *                  request header:
 *                  access-allow-header..: *
 *                  access-allow-method  : *
 *                  access-allow-domain : *
 *  7. CSRF
 *
 *      1. check email -> see a link / picture
 *      2. click it
 *      3. default browser popped up
 *
 *
 *      1. post + form
 *      2. or generate random id, put id as hidden attribute in your form
 *
 *  8. sql injection
 *
 *      backend: String sql = "select * from ... where username = " + username + " and password = " + password
 *
 *      user send
 *          1. username : "..."
 *          2. password : "..; drop table"
 *
 *      preparedStatement, jpa setParameter(?1, ?2)
 *
 *  9. log injection
 *
 *
 *
 *
 */

/*
Homework3 (use AI)
1. Terraform to deploy the structure (api gateway + lambda)
    main.tf
    variables.tf
    outputs.tf
    use module
    create diff folder
2. add Lambda authorizer to /verify endpoint
3. request should bring bearer token
4. use EC2 to host spring security / spring boot
    do authentication on spring security
    generate jwt to user
    use Dynamodb -> store userinfo
    add ALB before EC2
5. use chat gpt to generate csv data, sensor data, upload data to s3
6. create aws glue
    create workflow
        job1 -> load csv from s3
        job2 -> clean up csv file -> save cleaned data to a new csv in s3
7. use Step function to orchestrate aws glue + sagemaker training
8. after aws glue workflow is finished -> step function should trigger sagemaker training (read csv) -> generate model

deadline March 8th
 */