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
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.SubProcess;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class EventSubProcessJsonConverter extends BaseBpmnJsonConverter {

  public Map<String, Class<? extends BaseBpmnJsonConverter>> getJsonTypes() {
    Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<String, Class<? extends BaseBpmnJsonConverter>>(1);
    convertersToBpmnMap.put(STENCIL_EVENT_SUB_PROCESS, EventSubProcessJsonConverter.class);
    return convertersToBpmnMap;
  }

  public Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> getBpmnTypes() {
    Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap = new HashMap<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>>(1);
    convertersToJsonMap.put(EventSubProcess.class, EventSubProcessJsonConverter.class);
    return convertersToJsonMap;
  }
  
  protected String getStencilId(FlowElement flowElement) {
    return STENCIL_EVENT_SUB_PROCESS;
  }

  protected void convertElementToJson(ObjectNode propertiesNode, FlowElement flowElement) {
    SubProcess subProcess = (SubProcess) flowElement;
    propertiesNode.put("activitytype", "Event-Sub-Process");
    propertiesNode.put("subprocesstype", "Embedded");
    ArrayNode subProcessShapesArrayNode = objectMapper.createArrayNode();
    GraphicInfo graphicInfo = model.getGraphicInfo(flowElement.getId());
    processor.processFlowElements(subProcess.getFlowElements(), model, subProcessShapesArrayNode, 
        graphicInfo.getX() + subProcessX, graphicInfo.getY() + subProcessY);
    flowElementNode.put("childShapes", subProcessShapesArrayNode);
  }
  
  protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
    EventSubProcess subProcess = new EventSubProcess();
    JsonNode childShapesArray = elementNode.get(EDITOR_CHILD_SHAPES);
    processor.processJsonElements(childShapesArray, modelNode, subProcess, shapeMap);
    return subProcess;
  }
}
