## 一、Args任务拆解
看懂题目，开始编码之前先花10分钟拆解任务，把任务清单写下来

#### 需求理解：没有趁手的工具，我们可以自己写一个，自己想办法处理传给main函数的参数。
> 1. 之前理解错误了：理解成要将类似sheell的命令：-l -p 8080 -d /usr/logs 转换成 String[] args数组给main函数
> 2. 需求真实字面意思：参数：-l -p 8080 -d /usr/logs 解析出来，解析成什么样子呢，没有最终结果，但是有需求，具体如下：
 
框定需求范围，像机器一样思考，分解问题，找到子问题间的关联：
1. 需要个schema参数构造的描述：如 l:boolean p:integer d:string, 需要一个schema对象;schema对象可以描述，应该有几个标记，标记类型，标记缺省值; 应该有size type defaultvalue
2. args对象解析字符串完毕后，其他程序可以根据标记 flag 来查询 标记的值 ，例如：args.getValue("l") => True;
3. 参数结构不匹配，给出良好的错误信息，介绍出错的原因；

具体测试步骤如下：
1. 创建schema对象，并解析对象；l:boolean p:integer d:string => new Schema("l:boolean p:integer d:string")
2. 将字符串解析为数组；
3. 根据schema和参数字符串数组获取参数值；
4. 给出良好的错误信息，返回出错原因； 例如：参数个数不匹配；



```
* "l:boolean" => ArgSpec("l", "boolean")
* "l:boolean p:integer d:string" => Schema
 
```
