package com.example.bootsecurity.mapper.genmybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 陈彭伟 | Chen, Danny Pengwei (CN - Chongqing)
 * @Email dannypchen@deloitte.com.cn
 * @phone +86 15215029766
 * @CreatDate 2018/11/1 15:13
 */
public class MybatisDelPlugin extends PluginAdapter {
    //表名
    String tableName;
    //是否有逻辑删除字段
    boolean isHaveDelColumn = false;

    //逻辑删除字段名
    String delColumn = null;
    //默认 0表示未被删除 1表示已被删除
    String valid = "'0'";
    String invalid = "'1'";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
//        初始化配置
        tableName = null;
        isHaveDelColumn = false;
        delColumn = null;
        valid = "'0'";
        invalid = "'1'";

        Properties properties = getProperties();

        //获取全局配置
        String delColumn0 = properties.getProperty("delColumn");
        String valid0 = properties.getProperty("valid");
        String invalid0 = properties.getProperty("invalid");

        //获取当前表的配置
        String delColumn1 = introspectedTable.getTableConfigurationProperty("delColumn");
        String valid1 = introspectedTable.getTableConfigurationProperty("valid");
        String invalid1 = introspectedTable.getTableConfigurationProperty("invalid");

        if (delColumn0 != null && delColumn0.replaceAll(" ", "").length() != 0) {
            this.delColumn = delColumn0;
        }
        if (valid0 != null && valid0.replaceAll(" ", "").length() != 0) {
            this.valid = "'" + valid0 + "'";
        }
        if (invalid0 != null && invalid0.replaceAll(" ", "").length() != 0) {
            this.invalid = "'" + invalid0 + "'";
        }
        if (delColumn1 != null && delColumn1.replaceAll(" ", "").length() != 0) {
            this.delColumn = delColumn1;
        }
        if (valid1 != null && valid1.replaceAll(" ", "").length() != 0) {
            this.valid = "'" + valid1 + "'";
        }
        if (invalid1 != null && invalid1.replaceAll(" ", "").length() != 0) {
            this.invalid = "'" + invalid1 + "'";
        }

        if (this.delColumn == null) {
            return;
        }

        //判断是否有该字段名存在
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        for (IntrospectedColumn introspectedColumn : baseColumns) {
            String actualColumnName = introspectedColumn.getActualColumnName();
            if (this.delColumn.equals(actualColumnName)) {
                isHaveDelColumn = true;
                break;
            }
        }
        //获取表名
        tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
    }

    /**
     * 解决XML中 Example_Where_Clause
     * 有可能进入循环但是又没有数据的bug
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(Field.class.getName());
        if (!isHaveDelColumn) {
            return true;
        }
        List<Method> methods = topLevelClass.getMethods();
        Method method = methods.get(5);
        int index = 0;
        method.addBodyLine(index, "//判断是否所有条件为空  如果为空则返回 new ArrayList()");
        method.addBodyLine(++index, "//有任何疑问联系 Danny Chen");
        method.addBodyLine(++index, "boolean isHave = false;");
        method.addBodyLine(++index, "for (Criteria oredCriterion : oredCriteria) {");
        method.addBodyLine(++index, "int index = 0;");
        method.addBodyLine(++index, "Field[] fields = GeneratedCriteria.class.getDeclaredFields();");
        method.addBodyLine(++index, "for (Field field : fields) {");
        method.addBodyLine(++index, " if (field.getType() .equals(List.class) ){");
        method.addBodyLine(++index, "field.setAccessible(true);");
        method.addBodyLine(++index, "try {");
        method.addBodyLine(++index, "List o = (List) field.get(oredCriterion);");
        method.addBodyLine(++index, "index = index+(o==null?0:o.size());");
        method.addBodyLine(++index, "} catch (IllegalAccessException e) {");
        method.addBodyLine(++index, "e.printStackTrace();");
        method.addBodyLine(++index, " }");
        method.addBodyLine(++index, "}");
        method.addBodyLine(++index, "}");
        method.addBodyLine(++index, "if (index != 0) {");
        method.addBodyLine(++index, "isHave = true;");
        method.addBodyLine(++index, "break;");
        method.addBodyLine(++index, "}");
        method.addBodyLine(++index, "}");
        method.addBodyLine(++index, "if (!isHave) {");
        method.addBodyLine(++index, "return new ArrayList<Criteria>();");
        method.addBodyLine(++index, "}");
        return true;
    }

    /**
     * 封装更多的逻辑删除方法
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!isHaveDelColumn) {
            return true;
        }
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());

        Method deleteByExampleMethod = new Method();
        deleteByExampleMethod.setName("deleteByExample");
        deleteByExampleMethod.setDefault(true);
        deleteByExampleMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        deleteByExampleMethod.addParameter(new Parameter(modelType, "record"));
        deleteByExampleMethod.addParameter(new Parameter(exampleType, "example"));
        deleteByExampleMethod.addBodyLine("return updateByExampleSelective(record, example);");

        Method deleteByPrimaryKeyMethod = new Method();
        deleteByPrimaryKeyMethod.setName("deleteByPrimaryKey");
        deleteByPrimaryKeyMethod.setDefault(true);
        deleteByPrimaryKeyMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        deleteByPrimaryKeyMethod.addParameter(new Parameter(modelType, "record"));
        deleteByPrimaryKeyMethod.addBodyLine("return updateByPrimaryKey(record);");
        List<Method> methods = interfaze.getMethods();
        methods.add(1, deleteByExampleMethod);
        methods.add(3, deleteByPrimaryKeyMethod);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (!isHaveDelColumn) {
            return true;
        }
        XmlElement rootElement = document.getRootElement();
        List<XmlElement> xmlElements = rootElement.getElements().stream().map(element -> {
            XmlElement xmlElement = (XmlElement) element;
            return xmlElement;
        }).collect(Collectors.toList());

        for (XmlElement element : xmlElements) {
            if (element != null && element.getName().equals("sql")) {
                List<Attribute> attributes = element.getAttributes();
                Attribute attribute = attributes.get(0);
                if (attribute.getValue().equals("Example_Where_Clause")) {
                    List<Element> elements = element.getElements();
                    XmlElement xmlElement3 = (XmlElement) elements.get(3);
                    xmlElement3.addElement(0, new TextElement(delColumn + " = " + valid));
                    xmlElement3.addElement(1, new TextElement("<if test=\"oredCriteria != null and oredCriteria.size() != 0\">"));
                    xmlElement3.addElement(2, new TextElement(" and ("));
                    xmlElement3.addElement(3, new TextElement("</if>"));
                    xmlElement3.addElement(new TextElement("<if test=\"oredCriteria != null and oredCriteria.size() != 0\">"));
                    xmlElement3.addElement(new TextElement(" )"));
                    xmlElement3.addElement(new TextElement("</if>"));
                }
            }

            if (element != null && element.getName().equals("select")) {
                List<Attribute> attributes = element.getAttributes();
                Attribute attribute = attributes.get(0);
                if (attribute.getValue().equals("selectByPrimaryKey")) {
                    element.addElement(new TextElement("and " + delColumn + " = " + valid));
                }
                if (attribute.getValue().equals("countByExample")) {
                    element.addElement(new TextElement("<if test=\"_parameter == null\">"));
                    element.addElement(new TextElement(" where " + delColumn + " = " + valid));
                    element.addElement(new TextElement("</if>"));
                }
                if (attribute.getValue().equals("selectByExample")) {
                    element.addElement(8,new TextElement("<if test=\"_parameter == null\">"));
                    element.addElement(9,new TextElement(" where " + delColumn + " = " + valid));
                    element.addElement(10,new TextElement("</if>"));
                    System.out.println();
                }
            }

            if (element != null && element.getName().equals("delete")) {
                List<Attribute> attributes = element.getAttributes();
                Attribute attribute = attributes.get(0);
                if (attribute.getValue().equals("deleteByPrimaryKey")) {
                    Element whereElement = element.getElements().get(4);
                    element.addElement(3, new TextElement("<!--"));
                    element.addElement(4, new TextElement(" 逻辑删除,注释物理删除代码"));
                    element.addElement(5, new TextElement(" ======================"));
                    element.addElement(8, new TextElement(" -->"));
                    element.addElement(new TextElement("update " + tableName + " set " + delColumn + " = " + invalid));
                    element.addElement(whereElement);
                }

                if (attribute.getValue().equals("deleteByExample")) {
                    Element whereElement = element.getElements().get(4);
                    element.addElement(3, new TextElement("<!--"));
                    element.addElement(4, new TextElement(" 逻辑删除,注释物理删除代码"));
                    element.addElement(5, new TextElement(" ======================"));
                    element.addElement(8, new TextElement(" -->"));
                    element.addElement(new TextElement("update " + tableName + " set " + delColumn + " = " + invalid));
                    element.addElement(whereElement);
                    element.addElement(new TextElement("<if test=\"_parameter == null\">"));
                    element.addElement(new TextElement(" where " + delColumn + " = " + valid));
                    element.addElement(new TextElement("</if>"));
                }
            }

        }
        return true;
    }
}
