package com.coding.args;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    @Test
    @Ignore
    public void should_parser_input_text() {
        String[] argsAsArray = new String[]{"-l true", "-p 8080", "-d /usr/log"};

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:int d:string";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串数组获取参数值
        Args args = new Args(argsAsArray, schemaFlag);
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

        schemaFlag = new SchemaFlag("b:byte");
        assertThat(schemaFlag.getType("b"), is("byte"));
        assertThat(schemaFlag.getDefaultValue("b"), is("This byte type is not supported"));
        assertThat(schemaFlag.getSize(), is(1));
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
        assertThat(schemaFlag.getDefaultValue("b"), is("This byte type is not supported"));
    }

    @Test
    public void should_parse_boolean_text() {
        String[] argsAsArray = new String[]{"-l true"};
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean");
        Args args = new Args(argsAsArray, schemaFlag);
        assertThat(args.getValue("l"), is(true));
    }

    @Test
    public void should_parse_integer_text() {
        String[] argsAsArray = new String[]{"-p 8080"};
        SchemaFlag schemaFlag = new SchemaFlag("p:integer");
        Args args = new Args(argsAsArray, schemaFlag);
        assertThat(args.getValue("p"), is(8080));
    }

    @Test
    public void should_parse_string_text() {
        String[] argsAsArray = new String[]{"-d /usr/log"};
        SchemaFlag schemaFlag = new SchemaFlag("d:string");
        Args args = new Args(argsAsArray, schemaFlag);
        assertThat(args.getValue("d"), is("/usr/log"));
    }

    @Test
    public void should_parse_boolean_default_text() {
        String[] argsAsArray = new String[]{"-l "};
        SchemaFlag schemaFlag = new SchemaFlag("l:boolean");
        Args args = new Args(argsAsArray, schemaFlag);
        assertThat(args.getValue("l"), is(false));
    }

    @Test
    public void should_parse_integer_default_text() {
        String[] argsAsArray = new String[]{"-p "};
        SchemaFlag schemaFlag = new SchemaFlag("p:integer");
        Args args = new Args(argsAsArray, schemaFlag);
        assertThat(args.getValue("p"), is(0));
    }

}