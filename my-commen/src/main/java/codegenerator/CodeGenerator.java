package codegenerator;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangXun
 * @date 2021/5/8
 * @description*/
public class CodeGenerator {

    /**
     * 数据库用户名 密码 url 及注释中author的配置
     */
    private static String url="jdbc:mysql://182.254.221.85:3306/hr?useUnicode=true&characterEncoding=utf8";
    private static String username="root";
    private static String password="ROOT_mysql_3306";
    private static String driverName="com.mysql.cj.jdbc.Driver";
    private static String author = "guokui";



    public static void main(String[] args) {
        String prefixDao = "/my-shiro";
      //  String prefixService = "/srs-service";

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成mapper mapper.xml domain的路径
        String projectPathDao = System.getProperty("user.dir")+prefixDao;
        //生成service的路径
       // String projectPathService = System.getProperty("user.dir")+prefixService;
        gc.setOutputDir(projectPathDao +"/src/main/java");
        // TODO 设置用户名
        gc.setAuthor(author);
        // service 命名方式
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImp");
        gc.setServiceName("%sService");
        gc.setControllerName("%sController");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);//是否覆盖文件
        gc.setOpen(false);//打开输出目录
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        //打开swagger2配置，添加ApiModel、ApiModelProperty注解
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        // TODO 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // TODO 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.gk.company");
        pc.setEntity("domain");
        pc.setMapper("mapper");
        pc.setServiceImpl("service");
        pc.setService("service.impl");
        pc.setController("%scotroller");
        mpg.setPackageInfo(pc);


        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        //TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
        //TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        //tableFillList.add(createField);
        //tableFillList.add(modifiedField);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {

            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPathDao +"/src/main/resources/mapper/company"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

       /* // service impl文件输出
        focList.add(new FileOutConfig("templates/serviceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPathService +"cn.comein.service.srs" +tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });*/



        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null).setController(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //生成实体时,生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 设置逻辑删除键
        //strategy.setLogicDeleteFieldName("is_del");
        // TODO 指定生成的bean的数据库表名

        strategy.setInclude("sys_company");
        //strategy.setSuperEntityColumns("id");

        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
