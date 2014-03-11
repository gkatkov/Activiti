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
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class EndEventJsonConverter extends BaseBpmnJsonConverter {

  public Map<String, ? extends BaseBpmnJsonConverter> getJsonTypes() {
    Map<String, EndEventJsonConverter> convertersToBpmnMap = new HashMap<String, EndEventJsonConverter>(2);
    convertersToBpmnMap.put(STENCIL_EVENT_END_NONE, this);
    convertersToBpmnMap.put(STENCIL_EVENT_END_ERROR, this);
    return convertersToBpmnMap;
  }

  public Map<Class<? extends BaseElement>, ? extends BaseBpmnJsonConverter> getBpmnTypes() {
    Map<Class<? extends BaseElement>, EndEventJsonConverter> convertersToJsonMap = new HashMap<Class<? extends BaseElement>, EndEventJsonConverter>(1);
    convertersToJsonMap.put(EndEvent.class, this);
    return convertersToJsonMap;
  }
  
  protected String getStencilId(FlowElement flowElement) {
    EndEvent endEvent = (EndEvent) flowElement;
    List<EventDefinition> eventDefinitions = endEvent.getEventDefinitions();
    if (eventDefinitions.size() != 1) {
      return STENCIL_EVENT_END_NONE;
    }
    
    EventDefinition eventDefinition = eventDefinitions.get(0);
    if (eventDefinition instanceof ErrorEventDefinition) {
      return STENCIL_EVENT_END_ERROR;
    } else {
      return STENCIL_EVENT_END_NONE;
    }
  }
  
  protected void convertElementToJson(ObjectNode propertiesNode, FlowElement flowElement) {
    EndEvent endEvent = (EndEvent) flowElement;
    addEventProperties(endEvent, propertiesNode);
  }
  
  protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
    EndEvent endEvent = new EndEvent();
    String stencilId = BpmnJsonConverterUtil.getStencilId(elementNode);
    if (STENCIL_EVENT_END_ERROR.equals(stencilId)) {
      convertJsonToErrorDefinition(elementNode, endEvent);
    }
    return endEvent;
  }
}
