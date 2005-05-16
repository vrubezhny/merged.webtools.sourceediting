package org.eclipse.wst.xsd.ui.internal.text;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.extension.ModelQueryExtension;
import org.eclipse.wst.xsd.ui.internal.util.TypesHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XSDModelQueryExtension extends ModelQueryExtension
{  
  public XSDModelQueryExtension()
  {
  }
  
  //public boolean isApplicableChildElement(Node parentNode, String namespace, String name)
  //{
  //  if ("any".equals(name) || "choice".equals(name) || "group".equals(name))
  //  {
  //    return false;
  //  }
  //  return true;
  //}
  
  public String[] getAttributeValues(Element e, String namespace, String name)
  {
    List list = new ArrayList();

    String currentElementName = e.getLocalName();
    Node parentNode = e.getParentNode();
    String parentName = parentNode != null ? parentNode.getLocalName() : "";
    
    if (checkName(name, "type"))
    {      
      if (checkName(currentElementName, "attribute"))
      {
        list = getTypesHelper(e).getBuiltInTypeNamesList();
        list.addAll(getTypesHelper(e).getUserSimpleTypeNamesList());
      }
      else if (checkName(currentElementName, "element"))
      {
        list = getTypesHelper(e).getBuiltInTypeNamesList2();
        list.addAll(getTypesHelper(e).getUserSimpleTypeNamesList());
        list.addAll(getTypesHelper(e).getUserComplexTypeNamesList());
      }
    }
    else if (checkName(name, "itemType"))
    {
      if (checkName(currentElementName, "list"))
      {
        if (checkName(parentName, "simpleType"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserSimpleTypeNamesList());
        }
      }
    }
    else if (checkName(name, "memberTypes"))
    {
      if (checkName(currentElementName, "union"))
      {
        if (checkName(parentName, "simpleType"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserSimpleTypeNamesList());
        }
      }
    }
    else if (checkName(name, "base"))
    {
      if (checkName(currentElementName, "restriction"))
      {
        if (checkName(parentName, "simpleType"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserSimpleTypeNamesList());
        }
        else if (checkName(parentName, "simpleContent"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserComplexTypeNamesList());
        }
        else if (checkName(parentName, "complexContent"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserComplexTypeNamesList());
        }
      }
      else if (checkName(currentElementName, "extension"))
      {
        if (checkName(parentName, "simpleContent"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserComplexTypeNamesList());
        }
        else if (checkName(parentName, "complexContent"))
        {
          list = getTypesHelper(e).getBuiltInTypeNamesList();
          list.addAll(getTypesHelper(e).getUserComplexTypeNamesList());
        }
      }
    }
    else if (checkName(name, "ref"))
    {
      if (checkName(currentElementName, "element"))
      {
        list = getTypesHelper(e).getGlobalElements();
      }
      else if (checkName(currentElementName, "attribute"))
      {
        list = getTypesHelper(e).getGlobalAttributes();
      }
      else if (checkName(currentElementName, "attributeGroup"))
      {
        list = getTypesHelper(e).getGlobalAttributeGroups();
      }
      else if (checkName(currentElementName, "group"))
      {
        list = getTypesHelper(e).getModelGroups();
      }
    }
    else if (checkName(name, "substitutionGroup"))
    {
      if (checkName(currentElementName, "element"))
      {
        list = getTypesHelper(e).getGlobalElements();
      }
    }
        
    String[] result = new String[list.size()];
    list.toArray(result);
    return result;
  } 
  
  protected TypesHelper getTypesHelper(Element element)
  {
    TypesHelper typeHelper = null;
    Document document = element.getOwnerDocument();
    if (document instanceof INodeNotifier)
    {
      INodeNotifier notifier = (INodeNotifier)document;
      XSDModelAdapter adapter = (XSDModelAdapter)notifier.getAdapterFor(XSDModelAdapter.class);
      if (adapter == null)
      {
        adapter = new XSDModelAdapter();       
        notifier.addAdapter(adapter);        
        adapter.createSchema(document.getDocumentElement()); 
      } 
      typeHelper = new TypesHelper(adapter.getSchema());
    }  
    return typeHelper;
  }

  
  protected boolean checkName(String localName, String token)
  {
    if (localName != null && localName.trim().equals(token))
    {
      return true;
    }
    return false;
  }
}