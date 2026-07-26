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

package io.ballerina.lib.smb.server;

import io.ballerina.lib.smb.util.SmbUtil;
import io.ballerina.runtime.api.Environment;
import io.ballerina.runtime.api.concurrent.StrandMetadata;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.api.values.BTypedesc;

import static io.ballerina.lib.smb.client.SmbClient.SMB_ERROR;

/**
 * SMB Caller native methods for data-binding delegation to the underlying Client.
 */
public class SmbCaller {

    private SmbCaller() {
    }

    public static Object getJson(Environment env, BObject clientConnector, BString filePath, BTypedesc typeDesc) {
        return invokeClientMethod(env, clientConnector, "getJson", filePath, typeDesc);
    }

    public static Object getXml(Environment env, BObject clientConnector, BString filePath, BTypedesc typeDesc) {
        return invokeClientMethod(env, clientConnector, "getXml", filePath, typeDesc);
    }

    public static Object getCsv(Environment env, BObject clientConnector, BString filePath, BTypedesc typeDesc) {
        return invokeClientMethod(env, clientConnector, "getCsv", filePath, typeDesc);
    }

    public static Object getCsvAsStream(Environment env, BObject clientConnector, BString filePath,
                                        BTypedesc typeDesc) {
        return invokeClientMethod(env, clientConnector, "getCsvAsStream", filePath, typeDesc);
    }

    private static Object invokeClientMethod(Environment env, BObject clientConnector, String methodName,
                                             Object... args) {
        return env.yieldAndRun(() -> {
            try {
                BObject clientObj = clientConnector.getObjectValue(StringUtils.fromString("client"));
                StrandMetadata strandMetadata = new StrandMetadata(true, null);
                return env.getRuntime().callMethod(clientObj, methodName, strandMetadata, args);
            } catch (BError bError) {
                return SmbUtil.createError("client method invocation failed: " + bError.getErrorMessage(),
                        SMB_ERROR);
            }
        });
    }
}
