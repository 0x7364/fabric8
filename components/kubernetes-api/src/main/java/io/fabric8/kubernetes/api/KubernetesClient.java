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
package io.fabric8.kubernetes.api;

import io.fabric8.kubernetes.api.model.ControllerCurrentState;
import io.fabric8.kubernetes.api.model.ControllerDesiredState;
import io.fabric8.kubernetes.api.model.PodListSchema;
import io.fabric8.kubernetes.api.model.PodSchema;
import io.fabric8.kubernetes.api.model.ReplicationControllerListSchema;
import io.fabric8.kubernetes.api.model.ReplicationControllerSchema;
import io.fabric8.kubernetes.api.model.ServiceListSchema;
import io.fabric8.kubernetes.api.model.ServiceSchema;
import io.fabric8.utils.Filter;
import io.fabric8.utils.Filters;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.fabric8.kubernetes.api.KubernetesHelper.filterLabels;

/**
 * A simple client interface abstracting away the details of working with
 * the {@link io.fabric8.kubernetes.api.KubernetesFactory} and the differences between
 * the core {@link io.fabric8.kubernetes.api.Kubernetes} API and the {@link io.fabric8.kubernetes.api.KubernetesExtensions}
 */
public class KubernetesClient implements Kubernetes, KubernetesExtensions {
    private KubernetesFactory factory;
    private Kubernetes kubernetes;
    private KubernetesExtensions kubernetesExtensions;

    public KubernetesClient() {
    }

    public KubernetesClient(String url) {
        this(new KubernetesFactory(url));
    }

    public KubernetesClient(KubernetesFactory factory) {
        this.factory = factory;
    }

    // Properties
    //-------------------------------------------------------------------------

    public Kubernetes getKubernetes() {
        if (kubernetes == null) {
            kubernetes = getFactory().createKubernetes();
        }
        return kubernetes;
    }

    public KubernetesExtensions getKubernetesExtensions() {
        if (kubernetesExtensions == null) {
            kubernetesExtensions = getFactory().createKubernetesExtensions();
        }
        return kubernetesExtensions;
    }

    public KubernetesFactory getFactory() {
        if (factory == null) {
            factory = new KubernetesFactory();
        }
        return factory;
    }

    public void setFactory(KubernetesFactory factory) {
        this.factory = factory;
    }

    public String getAddress() {
        return getFactory().getAddress();
    }


    // Delegated Kubernetes API
    //-------------------------------------------------------------------------

    @GET
    @Path("pods")
    public PodListSchema getPods() {
        return getKubernetes().getPods();
    }

    @DELETE
    @Path("pods/{podId}")
    public String deletePod(@NotNull String podId) throws Exception {
        return getKubernetes().deletePod(podId);
    }

    @GET
    @Path("replicationControllers/{controllerId}")
    @Produces("application/json")
    public ReplicationControllerSchema getReplicationController(@NotNull String controllerId) {
        return getKubernetes().getReplicationController(controllerId);
    }

    @DELETE
    @Path("replicationControllers/{controllerId}")
    @Produces("application/json")
    public String deleteReplicationController(@NotNull String controllerId) throws Exception {
        return getKubernetes().deleteReplicationController(controllerId);
    }

    @Path("replicationControllers")
    @GET
    @Produces("application/json")
    public ReplicationControllerListSchema getReplicationControllers() {
        return getKubernetes().getReplicationControllers();
    }

    @PUT
    @Path("replicationControllers/{controllerId}")
    @Consumes("application/json")
    public String updateReplicationController(@NotNull String controllerId, ReplicationControllerSchema entity) throws Exception {
        return getKubernetes().updateReplicationController(controllerId, entity);
    }

    @PUT
    @Path("services/{serviceId}")
    @Consumes("application/json")
    public String updateService(@NotNull String serviceId, ServiceSchema entity) throws Exception {
        return getKubernetes().updateService(serviceId, entity);
    }

    @GET
    @Path("services/{serviceId}")
    @Produces("application/json")
    public ServiceSchema getService(@NotNull String serviceId) {
        return getKubernetes().getService(serviceId);
    }

    @DELETE
    @Path("services/{serviceId}")
    @Produces("application/json")
    public String deleteService(@NotNull String serviceId) throws Exception {
        return getKubernetes().deleteService(serviceId);
    }

    @Path("services")
    @POST
    @Consumes("application/json")
    public String createService(ServiceSchema entity) throws Exception {
        return getKubernetes().createService(entity);
    }

    @GET
    @Path("pods/{podId}")
    public PodSchema getPod(@NotNull String podId) {
        return getKubernetes().getPod(podId);
    }

    @PUT
    @Path("pods/{podId}")
    @Consumes("application/json")
    public String updatePod(@NotNull String podId, PodSchema entity) throws Exception {
        return getKubernetes().updatePod(podId, entity);
    }

    @Path("services")
    @GET
    @Produces("application/json")
    public ServiceListSchema getServices() {
        return getKubernetes().getServices();
    }

    @POST
    @Path("pods")
    @Consumes("application/json")
    public String createPod(PodSchema entity) throws Exception {
        return getKubernetes().createPod(entity);
    }

    @Path("replicationControllers")
    @POST
    @Consumes("application/json")
    public String createReplicationController(ReplicationControllerSchema entity) throws Exception {
        return getKubernetes().createReplicationController(entity);
    }


    // Delegated KubernetesExtensions API
    //-------------------------------------------------------------------------


    @POST
    @Path("configs")
    @Consumes("application/json")
    public String createConfig(Object entity) throws Exception {
        return getKubernetesExtensions().createConfig(entity);
    }

    @POST
    @Path("template")
    @Consumes("application/json")
    public String createTemplate(Object entity) throws Exception {
        return getKubernetesExtensions().createTemplate(entity);
    }

    @POST
    @Path("templateConfigs")
    @Consumes("application/json")
    public String createTemplateConfig(Object entity) throws Exception {
        return getKubernetesExtensions().createTemplateConfig(entity);
    }

    // Helper methods
    //-------------------------------------------------------------------------
    public ReplicationControllerSchema getReplicationControllerForPod(String podId) {
        PodSchema pod = getPod(podId);
        return getReplicationControllerForPod(pod);
    }

    public ReplicationControllerSchema getReplicationControllerForPod(PodSchema pod) {
        if (pod != null) {
            Map<String, String> labels = pod.getLabels();
            if (labels != null && labels.size() > 0) {
                ReplicationControllerListSchema replicationControllers = getReplicationControllers();
                List<ReplicationControllerSchema> items = replicationControllers.getItems();
                if (items != null) {
                    List<ReplicationControllerSchema> matched = new ArrayList<>();
                    for (ReplicationControllerSchema item : items) {
                        if (filterLabels(labels, item.getLabels())) {
                            matched.add(item);
                        }
                    }
                    int matchedSize = matched.size();
                    if (matchedSize > 1) {
                        // lets remove all the RCs with no current replicas and hope there's only 1 left
                        List<ReplicationControllerSchema> nonZeroReplicas = Filters.filter(matched, new Filter<ReplicationControllerSchema>() {
                            @Override
                            public boolean matches(ReplicationControllerSchema replicationController) {
                                ControllerDesiredState desiredState = replicationController.getDesiredState();
                                if (desiredState != null) {
                                    Integer desiredReplicas = desiredState.getReplicas();
                                    if (desiredReplicas != null && desiredReplicas.intValue() > 0) {
                                        ControllerCurrentState currentState = replicationController.getCurrentState();
                                        if (currentState != null) {
                                            Integer replicas = currentState.getReplicas();
                                            if (replicas != null && replicas.intValue() > 0) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                                return false;
                            }
                        });
                        int size = nonZeroReplicas.size();
                        if (size > 0) {
                            // lets pick the first one for now :)
                            return nonZeroReplicas.get(0);
                        }
                    }
                    if (matchedSize >= 1) {
                        // otherwise lets pick the first one we found
                        return matched.get(0);
                    }
                }
            }
        }
        return null;
    }

}
