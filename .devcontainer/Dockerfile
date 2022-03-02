#-------------------------------------------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See https://go.microsoft.com/fwlink/?linkid=2090316 for license information.
#-------------------------------------------------------------------------------------------------------------

# To fully customize the contents of this image, use the following Dockerfile instead:
# https://github.com/microsoft/vscode-dev-containers/tree/v0.101.1/containers/ubuntu-18.04-git/.devcontainer/Dockerfile
FROM mcr.microsoft.com/vscode/devcontainers/base:ubuntu-20.04

# ** [Optional] Uncomment this section to install additional packages. **
#
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get -y install --no-install-recommends \
    apt-utils \
    zip \
    unzip \
    git \
    procps \
    curl \
    software-properties-common \
    gpg-agent \
    mysql-client \
    maven \
    2>&1

# Clean up
RUN apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*

# Nodejs
RUN curl -fsSL https://deb.nodesource.com/setup_16.x | sudo -E bash -
RUN sudo apt-get install -y nodejs
RUN npm update -g npm

# Java
RUN wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add -
RUN sudo add-apt-repository 'deb https://apt.corretto.aws stable main'
RUN sudo apt-get update;
RUN sudo apt-get install -y java-17-amazon-corretto-jdk

# Git
RUN sudo git config --global user.name itf-nakamura-akira
RUN sudo git config --global user.email nakamura.akira@itfllc.co.jp

# ENV DEBIAN_FRONTEND=dialog
