/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.unomi.shell.commands;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.unomi.api.segments.Segment;
import org.apache.unomi.api.services.SegmentService;
import org.apache.unomi.persistence.spi.CustomObjectMapper;

@Command(scope = "segment", name = "view", description = "This will allows to view a segment in the Apache Unomi Context Server")
public class SegmentViewCommand extends OsgiCommandSupport {

    private SegmentService segmentService;

    @Argument(index = 0, name = "segmentId", description = "The identifier for the segment", required = true, multiValued = false)
    String segmentIdentifier;


    public void setSegmentService(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Segment segment = segmentService.getSegmentDefinition(segmentIdentifier);
        if (segment == null) {
            System.out.println("Couldn't find an action with id=" + segmentIdentifier);
            return null;
        }
        String jsonRule = CustomObjectMapper.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(segment);
        System.out.println(jsonRule);
        return null;
    }
}
