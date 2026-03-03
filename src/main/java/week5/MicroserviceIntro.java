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
 * 13. Cloud
 * 14. config service
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * monolithic
 */