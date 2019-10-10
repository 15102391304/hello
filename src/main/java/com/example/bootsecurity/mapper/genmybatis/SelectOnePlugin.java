package com.example.bootsecurity.mapper.genmybatis;

import com.github.pagehelper.PageHelper;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * @Description 查询单条数据
 * @Author 陈彭伟 | Chen, Danny Pengwei (CN - Chongqing)
 * @Email dannypchen@deloitte.com.cn
 * @phone +86 15215029766
 * @CreatDate 2018/11/17 19:39
 */
public class SelectOnePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        Method selectOneByExampleMothod = new Method();
        selectOneByExampleMothod.setName("selectOneByExample");
        selectOneByExampleMothod.setReturnType(modelType);
        selectOneByExampleMothod.setDefault(true);
        selectOneByExampleMothod.addParameter(new Parameter(exampleType, "example"));
        selectOneByExampleMothod.addBodyLine("PageHelper.startPage(1, 1);");
        selectOneByExampleMothod.addBodyLine("List<" + modelType.getShortName() + "> list = selectByExample(example);");
        selectOneByExampleMothod.addBodyLine("if (list == null || list.size() == 0) {");
        selectOneByExampleMothod.addBodyLine("return null;");
        selectOneByExampleMothod.addBodyLine("} else {");
        selectOneByExampleMothod.addBodyLine("return list.get(0);");
        selectOneByExampleMothod.addBodyLine("}");
        List<Method> methods = interfaze.getMethods();
        int index = 0;
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("selectByExample")) {
                index = i;
            }
        }
        methods.add(index, selectOneByExampleMothod);
        interfaze.addImportedType(new FullyQualifiedJavaType(PageHelper.class.getName()));
        return true;
    }
}
