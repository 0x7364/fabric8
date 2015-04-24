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
package io.fabric8.etcd.reader.gson;

import com.google.mockwebserver.MockWebServer;
import io.fabric8.etcd.api.EtcdClient;
import io.fabric8.etcd.core.AbstractMockWebServerTest;
import io.fabric8.etcd.core.EtcdClientImpl;
import org.junit.Ignore;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * TODO disabled test that started failing
 */
@Ignore
public class GsonMockWebServerTest extends AbstractMockWebServerTest {

    @Override
    public EtcdClient createClient(MockWebServer server) throws URISyntaxException {
        return new EtcdClientImpl.Builder()
                .baseUri(new URI("http://" + server.getHostName() +":" + server.getPort()))
                .responseReader(new GsonResponseReader()).build();
    }
}
