package com.gk.commen.utils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guokui
 * @class srs
 * @date 2021/6/16 18:38
 */
public class RegexUtils {

    /**
     * 查询分析师编号
     */
    public static final String REGEX_ANALYSTCODE = "(S\\d{13})";
    /**
     * 查询分析师名称 按照分析师
     */
    public static final String REGEX_ANALYST1 = "分析师：([\u4e00-\u9fa5]{2,})\\s";
    /**
     * 查询分析师名称 报告撰写人
     */
    public static final String REGEX_ANALYST2 = "报告撰写人：\\s{0,3}([\u4e00-\u9fa5/、\\s]{2,})\\r\\n";

     /**
     * 查询分析师名称 换行和分析师编号
     */
    public static final String REGEX_ANALYST3 = "[:]{0,1}([\u4e00-\u9fa5]{2,})\\s{0,}((\\w|[执业证书编号：]){0,}\\s{0,1}){0,1}(S\\d{13})";

    /**
     * 查询当前评级
      */
    public static final String REGEX_RATE = "\\s评级\\s([\u4e00-\u9fa5]{2,})\\s";
    /**
     * 查询行业评级
      */
    public static final String REGEX_INRATE = "行业评级\\s\\r\\n([\u4e00-\u9fa5]{2,})\\s{1,5}([\u4e00-\u9fa5]{2,})";
     /**
     * 查询上次评级
      */
    public static final String REGEX_LASTINRATE = "上次评级\\s{1,5}([\u4e00-\u9fa5]{2,})\\s";
    /**
     * 查询公司推荐
      */
    public static final String REGEX_RECOMMENDED = "([\u4e00-\u9fa5]{3,})\\s{0,5}(买入|推荐|未覆盖)\\s\\r\\n";


    public static boolean ispattern(String regex,String s) {
            return Pattern.matches(regex, s);
        }

        /**
         *
         * @param regex
         * @param s
         * @param index
         * @return
         */
        public static Set<String> findPattern(String regex, String s, int index) {
            Set<String> result = new TreeSet< String >();
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(s);
            for (int i=0;m.find();i++) {
                result.add(m.group(index));
                System.out.println("---->"+m.group(0));
            }
            System.out.println("---->"+result);
            return result;
        }

    public static void main(String[] args) throws Exception {
   //     String url = "https://testresource.comein.cn//srs/report/pdf/2021-06-23/695e55ee-a87d-433c-8f8b-9e4a28c53b2a.pdf";
   //     String url = "https://testresource.comein.cn//srs/report/pdf/2021-06-25/45b63e93-fdd1-4832-8db4-15048c30fa4e.pdf";
        String url = "https://testresource.comein.cn//srs/report/pdf/2021-06-25/e7ae7af9-1a8b-4eda-b75a-6e92a7bc2671.pdf";
        URL url01 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url01.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //解析pdf
        //  String pdf = PdfUtils.getTxt(inputStream);
        String pdf = PdfUtils.getTxtByPdfbox(inputStream,3);
        inputStream.close();
        System.out.println(findPattern(REGEX_RECOMMENDED,pdf,1));
        System.out.println("业务："+findPattern(REGEX_INRATE,pdf,2));
        Set<String> set =findPattern(REGEX_ANALYST2,pdf,1);
        List list =new ArrayList();
        for (String name : set) {
            List<String> nameList=Arrays.asList(name.split("/|、|\\s"));
            for (String tname : nameList) {
                if(StringUtils.isNotBlank(tname)){
                    list.addAll(Arrays.asList(tname.trim()));
                }
            }
        }

        System.out.println("分析师："+set+"-------------------->"+list);
    }
}
