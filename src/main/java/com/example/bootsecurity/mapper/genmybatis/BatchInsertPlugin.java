package com.example.bootsecurity.mapper.genmybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.lang.System.getProperties;

/**
 * @Description 批量插入插件
 * @Author 陈彭伟 | Chen, Danny Pengwei (CN - Chongqing)
 * @Email dannypchen@deloitte.com.cn
 * @phone +86 15215029766
 * @CreatDate 2018/11/16 14:10
 */
public class BatchInsertPlugin extends PluginAdapter {

    //数据库类型  默认mysql,支持mysql,oracle,sqlserver
    String DB_TYPE = "mysql";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        Properties properties = getProperties();
        String value = properties.getProperty("DB_TYPE");
        if (value == null || value.replaceAll(" ", "").length() == 0) {
            DB_TYPE = "mysql";
        } else {
            DB_TYPE = value;
        }
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType recordsType = FullyQualifiedJavaType.getNewListInstance();
        recordsType.addTypeArgument(modelType);

        Method insertByList = new Method();
        insertByList.setName("insertByList");
        insertByList.setReturnType(FullyQualifiedJavaType.getIntInstance());
        insertByList.addParameter(new Parameter(recordsType, "records"));

        List<Method> methods = interfaze.getMethods();
        methods.add(insertByList);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        XmlElement rootElement = document.getRootElement();
        List<XmlElement> xmlElements = rootElement.getElements().stream().map(element -> {
            XmlElement xmlElement = (XmlElement) element;
            return xmlElement;
        }).collect(Collectors.toList());

        XmlElement insertElement = null;
        for (XmlElement element : xmlElements) {
            if (element == null || !element.getName().equals("insert")) {
                continue;
            }
            List<Attribute> attributes = element.getAttributes();
            Attribute attribute = attributes.get(0);
            if (attribute.getValue().equals("insert")) {
                insertElement = element;
                break;
            }
        }
        List<Element> elements = insertElement.getElements();
        int comlueMax = Integer.MAX_VALUE;
        int comlueIndex = -1;
        int valuesIndex = -1;
        String valuesStr = "";
        String comlueStr = "";
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (!(element instanceof TextElement)){
                continue;
            }
            TextElement textElement = (TextElement) elements.get(i);
            if (textElement.getContent().startsWith("values (#{")) {
                valuesIndex = i;
                comlueMax = i;
            }

            if (textElement.getContent().startsWith("insert ")) {
                comlueIndex = i;
            }
            if (comlueIndex != -1 && i >= comlueIndex && i < comlueMax) {
                comlueStr = comlueStr + textElement.getContent().replaceAll("(^insert.+\\()|(\\))", "");
            }

            if (valuesIndex != -1 && i >= valuesIndex) {
                valuesStr = valuesStr + textElement.getContent().replaceAll("  ", "");
            }
        }
        valuesStr = valuesStr.replaceAll("(^values \\()|(}\\)$)|(#\\{)", "");

        String[] valus = valuesStr.split("}, ");

        XmlElement xmlElement;
        if (DB_TYPE.toLowerCase().equals("oracle")) {
            xmlElement = oracleGenerator(tableName,comlueStr, valus);
        } else {
            xmlElement = mysqlGenerator(tableName,comlueStr, valus);
        }

        document.getRootElement().addElement(xmlElement);
        return true;
    }

    public XmlElement mysqlGenerator(String tableName, String comlueStr, String[] valus) {
        XmlElement insertByList = new XmlElement("insert");
        insertByList.addAttribute(new Attribute("id", "insertByList"));
        insertByList.addElement(new TextElement("<!--"));
        insertByList.addElement(new TextElement("   WARNING - @mbg.generated"));
        insertByList.addElement(new TextElement("   ============"));
        insertByList.addElement(new TextElement("   批量插入"));
        insertByList.addElement(new TextElement("   ============"));
        insertByList.addElement(new TextElement("-->"));
        insertByList.addElement(new TextElement("insert into " + tableName + " ("));
        insertByList.addElement(new TextElement("  "+comlueStr));
        insertByList.addElement(new TextElement(")"));
        insertByList.addElement(new TextElement("values"));
        insertByList.addElement(new TextElement("<foreach collection=\"list\" item=\"record\" separator=\",\">"));
        insertByList.addElement(new TextElement("("));

        for (int i = 0; i < valus.length; i++) {
            if (i + 1 != valus.length) {
                insertByList.addElement(new TextElement("  #{record." + valus[i] + "},"));
            } else { //最后一条
                insertByList.addElement(new TextElement("  #{record." + valus[i] + "}"));
            }
        }

        insertByList.addElement(new TextElement(")"));
        insertByList.addElement(new TextElement("</foreach>"));

        return insertByList;
    }

    public XmlElement oracleGenerator(String tableName, String comlueStr, String[] valus) {
        XmlElement insertByList = new XmlElement("insert");
        insertByList.addAttribute(new Attribute("id", "insertByList"));
        insertByList.addElement(new TextElement("<!--"));
        insertByList.addElement(new TextElement("   WARNING - @mbg.generated"));
        insertByList.addElement(new TextElement("   ============"));
        insertByList.addElement(new TextElement("   批量插入"));
        insertByList.addElement(new TextElement("   ============"));
        insertByList.addElement(new TextElement("-->"));
        insertByList.addElement(new TextElement("INSERT INTO " + tableName + " ("));
        insertByList.addElement(new TextElement("  "+comlueStr));
        insertByList.addElement(new TextElement(")"));
        insertByList.addElement(new TextElement("<foreach collection=\"list\" item=\"record\" separator=\"union all\" >"));
        insertByList.addElement(new TextElement("select"));

        for (int i = 0; i < valus.length; i++) {
            if (i + 1 != valus.length) {
                insertByList.addElement(new TextElement("  #{record." + valus[i] + "},"));
            } else { //最后一条
                insertByList.addElement(new TextElement("  #{record." + valus[i] + "}"));
            }
        }

        insertByList.addElement(new TextElement("from dual"));
        insertByList.addElement(new TextElement("</foreach>"));

        return insertByList;
    }
}
