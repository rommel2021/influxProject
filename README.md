# 工程简介
这是一个操作influxdb项目的后端程序

# 代码说明
关于插入数据：
influxdb中的数据在插入时可以用Point类，这个类主要的属性：measurement,tag(tags),field(fields)
measurement就是数据要插入的目的表
tag就是这条数据的标签，在influxdb的UI界面查看时，每一个标签都会称为一个表项
fields则往往会让人比较迷惑：UI界面中并没有fields，而一个field值会出现在_value属性的下面；如果插入
语句中有多个fields，那么就会形成多条数据项
    `WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
    Map<String, String> tags = new HashMap<>();
    tags.put("version","3070");
    tags.put("p", "240w");
    Point gpu = Point.measurement("gpu").addTags(tags)
    .addField("price","￥3499")
    .addField("number",996)
    .time(Instant.now(),WritePrecision.MS);
    writeApi.writePoint(gpu);`
# 延伸阅读
# 莫名其妙的错误
在某次运行的时候，忽然抛出一堆看起来和代码无关的错误，error最终nested的是一个kotlin有关的异常
去网上搜索，结果显示的都是和okHttp相关，而且kotlin不是安卓开发的吗，为什么会出现在这里
最终解决方案是导入kotlin-stdlib的依赖，就莫名其妙没有错误了

