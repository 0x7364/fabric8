/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.zookeeper.bootstrap;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import javax.inject.Inject;
import java.lang.annotation.Documented;
import java.net.InetSocketAddress;

/**
 * Allows easy injection with CDI for a {@link QuorumPeerConfig}
 */
public class InjectableQuorumPeerConfig extends QuorumPeerConfig {
    /**
     *
     * @param clientPort the port the ZooKeeper client uses to connect to
     * @param electionPort the port ZooKeeper uses to perform elections
     * @param dataDir the directory ZooKeeper uses to store its data
     * @param dataLogDir  the directory ZooKeeper uses to store its logs
     * @param tickTime  the keep alive tick time
     * @param initLimit initialisation limit
     * @param syncLimit synchronisation limit
     */
    @Inject
    public InjectableQuorumPeerConfig(@ConfigProperty(name = "ZOOKEEPER_CLIENT_PORT", defaultValue = "2181")
                                      int clientPort,
                                      @ConfigProperty(name = "ZOOKEEPER_ELECTION_PORT", defaultValue = "2182")
                                      int electionPort,
                                      @ConfigProperty(name = "ZOOKEEPER_DATADIR", defaultValue = "ensemble/data")
                                      String dataDir,
                                      @ConfigProperty(name = "ZOOKEEPER_DATA_LOG_DIR", defaultValue = "ensemble/log")
                                      String dataLogDir,
                                      @ConfigProperty(name = "ZOOKEEPER_TICKTIME", defaultValue = "" + ZooKeeperServer.DEFAULT_TICK_TIME)
                                      int tickTime,
                                      @ConfigProperty(name = "ZOOKEEPER_INIT_LIMIT")
                                      int initLimit,
                                      @ConfigProperty(name = "ZOOKEEPER_SYNC_LIMIT")
                                      int syncLimit) {
        this.dataDir = dataDir;
        this.dataLogDir = dataLogDir;
        this.syncLimit = syncLimit;
        this.initLimit = initLimit;
        if (clientPort > 0) {
            this.clientPortAddress = new InetSocketAddress(clientPort);
        }
        this.electionPort = electionPort;
    }
}
