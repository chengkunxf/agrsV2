package com.coding.args;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    /***
     * ### 任务拆解
     * 像机器一样思考，整个任务大概分为如下事情：
     * 1. 构造一个命令行字符串：-l true -p 8080 -d /usr/log
     * 2. 构造一个字符串形式的 schema 对象：l:boolean p:integer d:string
     * 3. 根据命令行字符串和 schema 对象获取参数值： Args(stringArgs,schema ) args.getValue("l") == true
     *
     *
     * ### 测试用例步骤
     * 1. 把字符串解析成 schema 对象,一个 one_schema 的解析 l:boolean,验证类型,size,默认值,伪实现 Ignore 掉
     * 2. schema 参数与结构不匹配,返回异常和异常信息
     * 3. 解析多个schema l:boolean p:integer d:string 验证 size ,类型,默认值
     * 4. 单个命令字符串,单个 schema 的解析 -l true l:boolean, args.getValue("l")
     * 5. 单个命令的解析 -p 8080 p:integer 的解析, args.getValue("p")
     * 6. 单个命令的解析-d /usr/log d:string 的解析, args.getValue("d")
     * 7. 解析默认值的情况 -l l:boolean
     * 8. 解析默认值的情况 -p p:integer
     * 9. 去掉 步骤一的 Ignore,全部通过
     * 10. 解析多个参数 input_array
     */

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
//    @Ignore
    public void should_parser_input_text() {
        String argsAsText = "-l true -p 8080 -d /usr/log";

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:integer d:string";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串获取参数值
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(true));
        assertThat(args.getValue("p"), is(8080));
    }

    @Test
    public void should_parse_one_schema() {
        // TODO:把字符串形式的Schema解析成对象
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean");
        assertThat(schemaFlag.getType("l"), is("boolean"));
        assertThat(schemaFlag.getDefaultValue("l"), is(false));
        assertThat(schemaFlag.getSize(), is(1));

        schemaFlag = new SchemaFlag("p:integer");
        assertThat(schemaFlag.getType("p"), is("integer"));
        assertThat(schemaFlag.getDefaultValue("p"), is(0));
        assertThat(schemaFlag.getSize(), is(1));

        schemaFlag = new SchemaFlag("d:string");
        assertThat(schemaFlag.getType("d"), is("string"));
        assertThat(schemaFlag.getDefaultValue("d"), is(""));
        assertThat(schemaFlag.getSize(), is(1));
    }

    @Test
    public void should_throws_illegalArgumentException_when_parse_schema() {
        // TODO 异常测试用例，先期望异常，然后在触发，比较直接
        SchemaFlag schemaFlag = new SchemaFlag("b:byte");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("This byte type is not supported");
        schemaFlag.getDefaultValue("b");
    }

    @Test
    public void should_parse_more_schema() {
        // TODO:把字符串形式的Schema解析成对象
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean p:integer d:string b:byte");
        assertThat(schemaFlag.getSize(), is(4));
        assertThat(schemaFlag.getType("l"), is("boolean"));
        assertThat(schemaFlag.getDefaultValue("l"), is(false));


        assertThat(schemaFlag.getType("p"), is("integer"));
        assertThat(schemaFlag.getDefaultValue("p"), is(0));

        assertThat(schemaFlag.getType("d"), is("string"));
        assertThat(schemaFlag.getDefaultValue("d"), is(""));

        assertThat(schemaFlag.getType("b"), is("byte"));
        thrown.expect(IllegalArgumentException.class);
        schemaFlag.getDefaultValue("b");
    }

    @Test
    public void should_parse_boolean_text() {
        String argsAsText = "-l true";
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean");
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(true));
    }

    @Test
    public void should_parse_integer_text() {
        String argsAsText = "-p 8080";
        SchemaFlag schemaFlag = new SchemaFlag("p:integer");
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("p"), is(8080));
    }

    @Test
    public void should_parse_string_text() {
        String argsAsText = "-d /usr/log";
        SchemaFlag schemaFlag = new SchemaFlag("d:string");
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("d"), is("/usr/log"));
    }

    @Test
    public void should_parse_boolean_default_text() {
        String argsAsText = "-l";
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean");
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(false));
    }

    @Test
    public void should_parse_integer_default_text() {
        String argsAsText = "-p";
        SchemaFlag schemaFlag = new SchemaFlag("p:integer");
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("p"), is(0));
    }

    @Test
    public void should_parser_input_text_array() {
        String argsAsText = "-l -p 8080 -d /usr/log -s this,is,all -i 80,90,100";

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:integer d:string s:string[] i:integer[]";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串获取参数值
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));
        assertArrayEquals(new String[]{"this", "is", "all"}, (String[]) args.getValue("s"));
        Integer[] integers = {80, 90, 100};
        assertArrayEquals(integers, (Integer[]) args.getValue("i"));
    }
}