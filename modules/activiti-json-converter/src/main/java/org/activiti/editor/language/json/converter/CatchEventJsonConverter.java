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
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class CatchEventJsonConverter extends BaseBpmnJsonConverter {

  public Map<String, Class<? extends BaseBpmnJsonConverter>> getJsonTypes() {
    Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<String, Class<? extends BaseBpmnJsonConverter>>(3);
    convertersToBpmnMap.put(STENCIL_EVENT_CATCH_TIMER, CatchEventJsonConverter.class);
    convertersToBpmnMap.put(STENCIL_EVENT_CATCH_MESSAGE, CatchEventJsonConverter.class);
    convertersToBpmnMap.put(STENCIL_EVENT_CATCH_SIGNAL, CatchEventJsonConverter.class);
    return convertersToBpmnMap;
  }

  public Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> getBpmnTypes() {
    Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap = new HashMap<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>>(1);
    convertersToJsonMap.put(IntermediateCatchEvent.class, CatchEventJsonConverter.class);
    return convertersToJsonMap;
  }
  
  protected String getStencilId(FlowElement flowElement) {
    IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowElement;
    List<EventDefinition> eventDefinitions = catchEvent.getEventDefinitions();
    if (eventDefinitions.size() != 1) {
      // return timer event as default;
      return STENCIL_EVENT_CATCH_TIMER;
    }
    
    EventDefinition eventDefinition = eventDefinitions.get(0);
    if (eventDefinition instanceof MessageEventDefinition) {
      return STENCIL_EVENT_CATCH_MESSAGE;
    } else if (eventDefinition instanceof SignalEventDefinition) {
      return STENCIL_EVENT_CATCH_SIGNAL;
    } else {
      return STENCIL_EVENT_CATCH_TIMER;
    }
  }

  protected void convertElementToJson(ObjectNode propertiesNode, FlowElement flowElement) {
    IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowElement;
    addEventProperties(catchEvent, propertiesNode);
  }
  
  protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
    IntermediateCatchEvent catchEvent = new IntermediateCatchEvent();
    String stencilId = BpmnJsonConverterUtil.getStencilId(elementNode);
    if (STENCIL_EVENT_CATCH_TIMER.equals(stencilId)) {
      convertJsonToTimerDefinition(elementNode, catchEvent);
    } else if (STENCIL_EVENT_CATCH_MESSAGE.equals(stencilId)) {
      convertJsonToMessageDefinition(elementNode, catchEvent);
    } else if (STENCIL_EVENT_CATCH_SIGNAL.equals(stencilId)) {
      convertJsonToSignalDefinition(elementNode, catchEvent);
    }
    return catchEvent;
  }
}
