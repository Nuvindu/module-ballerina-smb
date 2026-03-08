/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.lib.smb.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * A SocketFactory that redirects all connections to a fixed TCP tunnel endpoint,
 * regardless of the requested host/port. This is used when the proxy is a raw TCP
 * forwarder (e.g. SSH -L port forwarding or netsh portproxy) rather than a SOCKS5 server.
 * No proxy handshake is performed — SMB traffic is sent directly into the tunnel.
 */
public class DirectTcpSocketFactory extends SocketFactory {

    private final String tunnelHost;
    private final int tunnelPort;
    private final int connectTimeout;

    public DirectTcpSocketFactory(String tunnelHost, int tunnelPort, int connectTimeout) {
        this.tunnelHost = tunnelHost;
        this.tunnelPort = tunnelPort;
        this.connectTimeout = connectTimeout;
    }

    @Override
    public Socket createSocket() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(tunnelHost, tunnelPort), connectTimeout);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return createSocket();
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return createSocket();
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return createSocket();
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException {
        return createSocket();
    }
}
