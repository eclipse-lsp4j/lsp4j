/*******************************************************************************
 * Copyright (c) 2024 Red Hat Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package com.redhat.devtools.lsp4ij.internal.telemetry;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationManager;
import com.redhat.devtools.intellij.telemetry.core.service.TelemetryMessageBuilder;
import com.redhat.devtools.intellij.telemetry.core.util.Lazy;
import com.redhat.devtools.lsp4ij.telemetry.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Red Hat Telemetry Service, used when <a href="https://github.com/redhat-developer/intellij-redhat-telemetry">intellij-redhat-telemetry</a> is available
 */
public class RedHatTelemetryService implements TelemetryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedHatTelemetryService.class);

    private final Lazy<TelemetryMessageBuilder> builder = new Lazy<>(() -> new TelemetryMessageBuilder(PluginManager.getPluginByClass(this.getClass())));

    public void send(String eventName, Map<String, String> properties) {
        TelemetryMessageBuilder.ActionMessage action = getMessageBuilder().action(eventName);
        if (properties != null) {
            properties.forEach((k, v) -> {
                action.property(k, v);
            });
        }
        asyncSend(action);
    }

    private TelemetryMessageBuilder getMessageBuilder() {
        return builder.get();
    }

    public static void asyncSend(TelemetryMessageBuilder.ActionMessage message) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try{
                message.send();
            } catch (Exception e) {
                LOGGER.error("Failed to send Telemetry data : {}", e.getMessage());
            }
        });
    }

}
