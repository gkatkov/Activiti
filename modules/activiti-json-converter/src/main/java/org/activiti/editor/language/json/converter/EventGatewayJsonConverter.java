/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.editor.language.json.converter;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.FlowElement;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class EventGatewayJsonConverter extends BaseBpmnJsonConverter {

  public Map<String, ? extends BaseBpmnJsonConverter> getJsonTypes() {
    Map<String, EventGatewayJsonConverter> convertersToBpmnMap = new HashMap<String, EventGatewayJsonConverter>(1);
    convertersToBpmnMap.put(STENCIL_GATEWAY_EVENT, this);
    return convertersToBpmnMap;
  }

  public Map<Class<? extends BaseElement>, ? extends BaseBpmnJsonConverter> getBpmnTypes() {
    Map<Class<? extends BaseElement>, EventGatewayJsonConverter> convertersToJsonMap = new HashMap<Class<? extends BaseElement>, EventGatewayJsonConverter>(1);
    convertersToJsonMap.put(EventGateway.class, this);
    return convertersToJsonMap;
  }
  
  protected String getStencilId(FlowElement flowElement) {
    return STENCIL_GATEWAY_EVENT;
  }
  
  protected void convertElementToJson(ObjectNode propertiesNode, FlowElement flowElement) {
  }
  
  protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
    EventGateway gateway = new EventGateway();
    return gateway;
  }
}
