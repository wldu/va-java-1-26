package week5;

/**
 * microservice : centralize ... / reuse / scalability / sharding..
 * 1. discovery service: spring-cloud-eureka
 *
 *      service A  ->  service B
 *              \           /
 *             discovery service
 *             {
 *                 serivceA-name: [{ip1, port1}, {ip2, port2}],
 *                 serivceB-name: [{ip2, port4}]
 *             }
 *
 *      1. start service -> register service info(ip, port, name..) on discovery service
 *      2. service A send request to service B
 *         service A query discovery service : where is service-B(name)
 *         discovery service : serivceB-name: [{ip3, port1}, {ip2, port4}]
 *         service A pick one of [{ip3, port1}, {ip2, port4}]
 *         send request to ip3, port1
 * 2. horizontal scaling vs vertical scaling
 *
 *            |
 *      load balancer
 *      /       \     \     \
 *    node1   node2   node3   node4
 *
 *    sticky session
 * 3. Api Gateway : AWS Gateway / spring-cloud-gateway(spring-cloud-zuul)
 *      AWS Api gateway -> cognito / lambda authorizer
 *
 *      scope endpoint mapping
 *      /verify -> allow xx scope
 *
 *      1. co-relation id
 *          UUID
 *      2. transaction id  / global unique id
 *          [time stamp][machine id][process id][thread id][serial id]
 * 4. Circuit Breaker (resilience4j,  spring-cloud-hystrix)
 *      service A -> service B -> service C
 *
 *      case: if service B not responding, requests from serviceA are experiencing timeout
 *
 *      1. change status from "open" to "close"
 *      2. all following service A requests will not reach out service B
 *         all those requests are returning default/fallback function
 *      3. A background thread in service A keeps checking service B status
 *         once service B starts responding
 *         we change status in service A from "close" to "half open"
 *         after a while , if there is no issues
 *         change status from "half open" to "open"
 * 5. Global transaction (consistency / eventually consistency)
 *      2PC / 3PC
 *      SAGA pattern + change data capture (CDC)
 *
 *      example1:
 *      app -> kafka /message queue
 *       |
 *      db
 *
 *     1. send message to message queue
 *     //app shuts down
 *     2. save data in database
 *
 *     1. save data in database
 *     //app shuts down
 *     2. send message to message queue
 *
 *
 *     example2:
 *       app
 *     |    \
 *    db1   db2
 * 6. Nosql vs SQL
 *    Nosql : Mongodb(document db), Cassandra, Dynamodb(Key-value DB)
 *            Nosql cluster
 *    RDBMS : RDBMS cluster
 * 7. Message Queue (Asynchronous)
 *      producer -> queue -> consumer
 *      serviceA    server   serviceB
 * 8. Deployment
 *    git repo
 *
 *    main branch ------
 *                     \         / -> pull request code review + merge -> trigger webhook -> trigger pipeline
 *      feather branch  ---------
 *
 *   pipeline
 *
 *                                            package to jar
 *                                            use dockerfile -> generate docker image
 *                                            push to ECR
 *                                              |
 *      build -> test -> report(sonarqube)  -> package -> deploy -> post deploy
 *                       code coverage                      |
 *                       code sanity check report         ECS/EKS
 *
 *
 *
 *  Selenium -> get web browser / page elements -> add customized value
 *           -> trigger button
 *           -> expect xx result
 *           -> navigate to next page
 *           -> ...
 *
 *  DockerFile
 *      FROM (pull libraries / other images from ... location)
 *      WORKDIR ..
 *      COPY ..  (copy jar -> to your work dir)
 *      ENV variables ..
 *      ENTRY [java -jar 'name.jar']
 *
 * 9. Log : Splunk
 *     serviceA
 *       |
 *      save log
 *       |
 *     disk  -> Universal forwarder push log -> Splunk centralized log server
 *
 *     in splunk
 *     1. search log by key word : co-relation id, trace id, .. by range , time
 *     2. metric : p99 , p95..
 *     3. alert : ..
 *
 * 10. Monitor
 *      1. Cloudwatch
 *      2. Splunk
 *      3. Api performance... monitors
 *          prometheus + grafana
 *
 *   spring boot actuator -> metrics endpoint + health endpoint + app info
 *
 * 11. Documentation
 *      1. Confluence
 *      2. swagger UI / swagger doc
 *      3. open api spec
 *          yaml
 *          raml (?)
 *
 * 12. Security
 * 13. Cloud AWS
 *  API Gateway
 *  EC2 = ASG
 *      = On demand / Reserved / Spot / Dedicate
 *  ECS = EC2 / Fargate
 *  Lambda :
 *      serverless: python / java ..
 *      can run at most 15min
 *      code start -> warm start / keep few instance running
 *      more memory -> more cpu
 *      limited disk usage
 *
 *  CloudMap = discovery service
 *  SQS = FIFO / Standard
 *  SNS = Publisher subscriber
 *  SES = email
 *
 *  IAM = role / policy
 *  VPC = subnet / network management
 *        NACL
 *  Security Group
 *
 *  Direct Connect / VPN =
 *
 *
 *  ElasticCache = Redis
 *  S3 = Object Storage
 *  Aurora > RDS -> Postgre , MySQL, Oracle
 *  Dynamodb
 *
 *  AWS Connect = step1 -> step2 -> step3 -> step5
 *                          |
 *                        step4
 *
 *  Lex
 *  ..
 *
 * 14. config service(has many properties, centralize properties)
 *
 *      spring boot / client flow
 *      1. start spring boot -> pulling properties from config service
 *      2. load those properties in spring boot app / memory
 *      3. start application
 *
 *      if we change properties in config service
 *      1. spring boot actuator -> provides /restart
 * 15. Graphql : node.js / BFF layer
 *
 *      /query post
 *      request body
 *      graphql make decision -> to call diff endpoints
 *
 *
 * 16. GenAI/ chatbot
 *      user -> query -> service
 *      1. LLM -> return result if we find it in LLM and score is high
 *      2. search RAG(vector db)
 *              1. store vector
 *              2. search -> getting closest top K result
 *      3. verify those results -> get score
 *      4. based on your intents
 *          MCP -> routing(hardcode / LLM agent) -> call diff db / endpoint
 *      5. use LLM to generate readable answer
 *
 *
 * Skill: query order info
 *      1. extract id from user query
 *      2. send request to order endpoint
 *      3. generate result..
 *
 * tools:
 *      1. order endpoint
 *
 *
 * 17. ETL pipeline
 *
 *          1. input : kafka stream(real time data) , data source(db/s3..)
 *          2. AWS Glue(pyspark) / AWS EMR(pyspark)
 *             AWS Glue : serverless
 *             AWS EMR : worker node, master node
 *
 *
 *
 *
 *    Hadoop
 *      Yarn (resources management / allocate resources)
 *      Map-reduce / Spark (computing engine)
 *      Hive (catalog layer)
 *          |                       \
 *          |                        other resources
 *  HDFS (disk / storage layer)
 *
 *
 *
 */