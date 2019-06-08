package com.coding.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_parser_input_text() {
        String argsAsText = "-l true -p 8080 -d /usr/log";

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:integer d:string";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串数组获取参数值
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(true));
        assertThat(args.getValue("p"), is(8080));
    }

    @Test
    public void should_parser_input_text_array() {
        String argsAsText = "-l -p 8080 -d /usr/log -s this,is,all -i 80,90,100";

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:integer d:string s:string[] i:integer[]";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串数组获取参数值
        Args args = new Args(argsAsText, schemaFlag);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));
        assertArrayEquals(new String[]{"this", "is", "all"}, (String[]) args.getValue("s"));
        assertArrayEquals(new Integer[]{80, 90, 100}, (Integer[]) args.getValue("i"));
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

}