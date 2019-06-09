### 任务拆解
像机器一样思考，整个任务大概分为如下事情：
1. 构造一个命令行字符串：-l true -p 8080 -d /usr/log
2. 构造一个字符串形式的 schema 对象：l:boolean p:integer d:string
3. 根据命令行字符串和 schema 对象获取参数值： Args(stringArgs,schema ) args.getValue("l") == true


### 测试用例步骤
1. 任务拆解的测试用例,来描述需求,伪实现 Ignore 掉
2. 把字符串解析成 schema 对象,一个 one_schema 的解析 l:boolean,验证类型,size,默认值 
3. schema 参数与结构不匹配,返回异常和异常信息
4. 解析多个schema l:boolean p:integer d:string 验证 size ,类型,默认值
5. 单个命令字符串,单个 schema 的解析 -l true l:boolean, args.getValue("l")
6. 单个命令的解析 -p 8080 p:integer 的解析, args.getValue("p") 
7. 单个命令的解析-d /usr/log d:string 的解析, args.getValue("d")
8. 解析默认值的情况 -l l:boolean 
9. 解析默认值的情况 -p p:integer
10. 去掉 步骤一的 Ignore,全部通过
11. 解析多个参数 input_array 
 
 