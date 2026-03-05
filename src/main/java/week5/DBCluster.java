package week5;

/**
 * Single Leader Cluster
 *  1. leader follower
 *  2. primary secondary
 *  3. master slave
 *
 *          Leader node(write)
 *        /     \           \
 *  Follower1   Follower2   Follower3(read node)
 *
 *  Write -> Leader
 *  Read -> Leader / Follower
 *
 *
 * MultiLeader Cluster
 *
 *  Leader1         Leader2         Leader3
 *   /  \            /\               /\
 * Follower1, 2
 *
 * clock vector
 *
 *
 * ALL Leader Cluster, Leaderless Cluster
 *
 *          Node1(1 ~ 10k)
 *
 *
 * Node4(40k ~ ..)           Node2(10k ~ 20k)
 *
 *
 *          Node3(20k ~ 40k)
 *
 *
 *    Consistent Hashing
 *
 *    0 ~ Integer.MAX_VALUE
 *
 *    Node1 (1)
 *    Node2 (10k)
 *    Node3 (20k)
 *    Node4
 *
 *    id -> hash(id) -> index
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    Mongodb Cluster(CP) , B+ Tree structure / Random IO
 *
 *                          Mongos(gateway)     -       Config
 *               /          \               \
 *        sharding1         2                3
 *        Primary[Opslog]
 *        Secondary
 *        Secondary
 *
 *        id range
 *        or
 *        hash range
 *
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    Cassandra , Sequential IO
 *
 *    Cassandra Node
 *    update name to Tom where id = 5
 *    write ->  memtable(ram) -> meet threshold -> dump all data in memtable to SSTable(immutable file) on disk
 *           |
 *        commit log(disk)
 *
 *     SSTable1                 SSTable10
 *     id=5, name=Alex          id=5, name=Tom
 *
 *
 *     Blooming Filter
 *     SSTable1
 *     arr1, hash1  [][true][][][][][][]
 *     arr2, hash2  [][][][][][][][true]
 *     arr3, hash3  [][][][][true][][][]
 *
 *
 *     read -> memtable -> Blooming Filter  -> merge those SSTable -> LWW
 *                          id = 5
 *                         check each SSTable
 *                         arr[hash(id)] == true
 *
 *
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    Cassandra Cluster
 *
 *          Node1(1 ~ 10k)
 *
 *
 * Node4(40k ~ ..)           Node2(10k ~ 20k)
 *
 *
 *          Node3(20k ~ 40k)
 *
 *   Case1
 *   Replica Factor = 3
 *   Read Consistency Level = 1
 *   Write Consistency Level = 1 , if one of replica has committed your data -> success
 *
 *   data stored in node1, node2, node3
 *   read -> node4 -> either node1 or node2 or node3
 *   write -> node4 -> node1 and node2 and node3
 *
 *   Case2 : RC + WC > RF
 *   Replica Factor = 3
 *   Read Consistency Level = 2
 *   Write Consistency Level = 2 , if 2 of replica has committed your data -> success
 *
 *   data stored in node1, node2, node3
 *   write -> node4 -> node1 and node2 and node3
 *
 *   read -> node4 -> send request to lowest latency node to fetch data
 *                    send another request to another node to get hash(data)
 *                    if they are same -> return to user
 *                    if not -> trigger read repair
 *                              it only repairs those nodes that we are reading
 *                              then return to user
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    Dynamodb
 *    Partition Key(could be timestamp), Sorted Key(could be id), other attributes
 *    Partition Key : help you decide sharding / instances
 *    Sorted Key : index in each sharding -> improve read performance
 *
 *
 *    Index Table
 *    Partition Key(Name), timestamp, id
 *
 *
 *     *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 * Consensus Algorithm(RAFT)
 *
 *  Leader     Candidate     Follower
 *
 *  Follower
 *      1. wait for leader heartbeat
 *      2. if not getting heartbeat from leader in next X seconds
 *          convert it self to candidate
 *      3. during election (in each term)
 *          one follower can only vote for one candidate
 *
 *  Candidate
 *      1. ask for all followers to vote
 *          if there are more than half followers voted for this candidate
 *          it will convert it self to Leader
 *
 *  Leader
 *      1. send heartbeat to all other nodes
 *          tell them, i'm the leader
 *      2. if we have multiple leaders
 *          check with each other , will keep the one with more logs
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   ElasticSearch (OpenSearch)
 *
 *      inverted index
 *      word1 -> doc1, doc2, doc3
 *      word2 -> doc2, doc4
 *      ..
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   Cache in Microservice
 *   Redis node(single thread)
 *      -> event loop (queue) -> single thread pull from event loop
 *
 *      backup
 *      1. AOF (append of file)
 *      2. snapshot (dump all data from cache to disk once a while)
 *
 *   Redis Cluster
 *
 *          1 ~ 5k          5k ~ 20k        20k ~ ..
 *          Leader          Leader          Leader
 *          /   \           /   \
 *        f1    f2        f1    f2
 *
 *        hash slot : 24k (?)
 *
 *        key -> hash(key) + total hash slot-> index
 *
 *
 *   How do we use cache and DB  (TTL on cache data)
 *   server - cache
 *    |
 *   DB
 *
 *   cache aside
 *      read : read from cache
 *             if res in cache -> return result
 *             if res not in cache -> search DB -> save to cache -> return result
 *     write : remove that id from cache
 *             save id to db
 *
 *   read through / write through
 *      server <-> cache <-> db
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   Nosql vs SQL
 *   Nosql
 *      1. sharding / partition / scalability
 *      2. not strict schema (column db, key value pair, json)
 *      3. less join
 *      4. duplicate data
 *      5. Cassandra  : write > read  (LWW, light transaction)
 *         MongoDB : read > write (ACID)
 *   SQL
 *      1. ACID
 *      2. strict schema
 *      3. normalization
 *      4. complex join
 *      5..
 *   ElasticSearch
 *      1. text search
 *          product description
 *          log search
 *      2. document search
 *
 *
 *
 *
 *
 */