# mardown解析
使用java解析markdown文件转换成html代码
>转换的html代码引用了stackedit的css样式

项目目前支持：
 - 标题
 - 文本样式， 强调文本，删除文本等
 - 链接和图片
 - 代码片
 - 表格
 
 其他样式正实现中
 
 未完全实现所有不发布release版本到中央仓库
 使用方式
 ```aidl
    get clone https://github.com/lzeqian/jmarkdown
```
转换为不带样式的html
 ```java
    MarkDownFile mdf=new MarkDownFile(MarkDownFile.class.getResourceAsStream("/test.md"));
    System.out.println(mdf.process());
```
转换为带样式的html
 ```java
    MarkDownFile mdf=new MarkDownFile(MarkDownFile.class.getResourceAsStream("/test1.md"));
    System.out.println(mdf.processStyle());
```
 
 