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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ServiceTask;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class MailTaskJsonConverter extends BaseBpmnJsonConverter {

  public Map<String, ? extends BaseBpmnJsonConverter> getJsonTypes() {
    Map<String, MailTaskJsonConverter> convertersToBpmnMap = new HashMap<String, MailTaskJsonConverter>(1);
    convertersToBpmnMap.put(STENCIL_TASK_MAIL, this);
    return convertersToBpmnMap;
  }

  public Map<Class<? extends BaseElement>, ? extends BaseBpmnJsonConverter> getBpmnTypes() {
    // will be handled by ServiceTaskJsonConverter
    return Collections.emptyMap();
  }
  
  protected String getStencilId(FlowElement flowElement) {
    return STENCIL_TASK_MAIL;
  }
  
  protected void convertElementToJson(ObjectNode propertiesNode, FlowElement flowElement) {
    // will be handled by ServiceTaskJsonConverter
  }
  
  protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
  	ServiceTask task = new ServiceTask();
  	task.setType(ServiceTask.MAIL_TASK);
  	addField(PROPERTY_MAILTASK_TO, elementNode, task);
  	addField(PROPERTY_MAILTASK_FROM, elementNode, task);
  	addField(PROPERTY_MAILTASK_SUBJECT, elementNode, task);
  	addField(PROPERTY_MAILTASK_CC, elementNode, task);
  	addField(PROPERTY_MAILTASK_BCC, elementNode, task);
  	addField(PROPERTY_MAILTASK_TEXT, elementNode, task);
  	addField(PROPERTY_MAILTASK_HTML, elementNode, task);
  	addField(PROPERTY_MAILTASK_CHARSET, elementNode, task);
    
    return task;
  }
  
  protected void addField(String name, JsonNode elementNode, ServiceTask task) {
    FieldExtension field = new FieldExtension();
    field.setFieldName(name.substring(8));
    String value = getPropertyValueAsString(name, elementNode);
    if (StringUtils.isNotEmpty(value)) {
      if ((value.contains("${") || value.contains("#{")) && value.contains("}")) {
        field.setExpression(value);
      } else {
        field.setStringValue(value);
      }
      task.getFieldExtensions().add(field);
    }
  }
}
