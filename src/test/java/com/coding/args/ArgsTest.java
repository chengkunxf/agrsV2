package com.coding.args;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    @Test
    public void should_parser_input_text() {
        String[] argsAsArray = new String[]{"-l true","-p 8080","-d /usr/log"};

        // TODO:把字符串形式的Schema解析成对象
        String schemaDesc = "l:boolean p:integer d:string";
        SchemaFlag schemaFlag = new SchemaFlag(schemaDesc);

        // TODO:根据schema和参数字符串数组获取参数值
        Args args = new Args(schemaFlag, argsAsArray);
        assertThat(args.getValue("l"), is(true));
        assertThat(args.getValue("p"), is(8080));
    }
    
    @Test
    public void should_parse_schema() {

    }

}