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
package io.fabric8.docker.provider;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;

/**
 * Represents the configuration for a Docker Provider to specify in the profile which docker image to use
 */
@Component(name = DockerConstants.DOCKER_PROVIDER_PID,
        label = "Docker",
        description = "The configuration of the docker image to use in this profile",
        immediate = true, metatype = true)
public class DockerProviderConfig {
    @Property(label = "Image",
            description = "The docker image name used to create a docker container")
    private String image;

    @Property(label = "Command",
            description = "The command to be used to start the docker container which overrides the entry point inside the image")
    private String cmd;

    @Property(label = "Overlay Folder",
            description = "The folder in the profile used to create overlay files into the generated container image")
    private String overlayFolder;

    @Property(label = "Java Library Path",
            description = "The path that shared java libraries should be installed into inside the docker image for Java containers or application servers")
    private String javaLibraryPath;
    @Property(label = "Maven Java Library layout",
            description = "Should libraries be added to a flat directory or use a maven style directory tree")
    private boolean mavenJavaLibraryPathLayout;

    @Property(label = "Java Deploy Path",
            description = "The path that java deployment units (wars, ears etc) should be installed into inside the docker image for Java containers or application servers")
    private String javaDeployPath;

    @Property(label = "Home path",
            description = "The home directory inside the docker image which overlays are applied to")
    private String homePath;

    @Property(label = "Image repository",
            description = "The docker image repository")
    private String imageRepository;

    @Property(label = "Custom Image User Name",
            description = "The user name or image repository URL used for creating custom images")
    private String customImageUserName;

    @Property(label = "Push Custom Images",
            description = "Whether custom images should be pushed to the custom image user name or repository")
    private Boolean customImagePush;

    @Property(label = "Image entry point",
            description = "The docker image entry point command used")
    private String imageEntryPoint;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getJavaLibraryPath() {
        return javaLibraryPath;
    }

    public void setJavaLibraryPath(String javaLibraryPath) {
        this.javaLibraryPath = javaLibraryPath;
    }

    public String getJavaDeployPath() {
        return javaDeployPath;
    }

    public void setJavaDeployPath(String javaDeployPath) {
        this.javaDeployPath = javaDeployPath;
    }

    public String getHomePath() {
        return homePath;
    }

    public void setHomePath(String homePath) {
        this.homePath = homePath;
    }

    public String getImageRepository() {
        return imageRepository;
    }

    public void setImageRepository(String imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String getCustomImageUserName() {
        return customImageUserName;
    }

    public void setCustomImageUserName(String customImageUserName) {
        this.customImageUserName = customImageUserName;
    }

    public Boolean getCustomImagePush() {
        return customImagePush;
    }

    public void setCustomImagePush(Boolean customImagePush) {
        this.customImagePush = customImagePush;
    }

    public String getImageEntryPoint() {
        return imageEntryPoint;
    }

    public void setImageEntryPoint(String imageEntryPoint) {
        this.imageEntryPoint = imageEntryPoint;
    }

    public String getOverlayFolder() {
        return overlayFolder;
    }

    public void setOverlayFolder(String overlayFolder) {
        this.overlayFolder = overlayFolder;
    }

    public boolean isMavenJavaLibraryPathLayout() {
        return mavenJavaLibraryPathLayout;
    }

    public void setMavenJavaLibraryPathLayout(boolean mavenJavaLibraryPathLayout) {
        this.mavenJavaLibraryPathLayout = mavenJavaLibraryPathLayout;
    }
}
