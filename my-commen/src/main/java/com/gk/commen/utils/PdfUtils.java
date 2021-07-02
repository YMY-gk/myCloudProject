package com.gk.commen.utils;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/5/17 0:39
 */
@Slf4j
public class PdfUtils {
    /**
     * 获取pdf文件String
     * @param
     * @return
     * @throws Exception
     */
    public static String getTxt(InputStream inputStream ,int page) throws Exception {
        StringBuffer txt =new StringBuffer();
        PdfReader reader = new PdfReader(inputStream);// 读取pdf
        int pdfpage =reader.getNumberOfPages();
        page =pdfpage>page?page:pdfpage;
        for (int i=1;i<=page;i++){
            txt.append("\n第"+i+"页\n"+PdfTextExtractor.getTextFromPage(reader, i));
        }
        return txt.toString();
    }
    public static String getTxtByPdfbox(InputStream f,int page) throws Exception {
        String ts = "";
        try {
            String temp = "";
            PDDocument pdfdocument = PDDocument.load(f);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setEndPage(page);
            ts=stripper.getText(pdfdocument);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ts;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url01 = new URL("https://testresource.comein.cn//srs/report/pdf/2021-06-16/c793d634-7dd5-4891-a1fa-4e127279ce0d.pdf");
        HttpURLConnection conn = (HttpURLConnection) url01.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //解析pdf
      //  String pdf = PdfUtils.getTxt(inputStream);
        String pdf01 = PdfUtils.getTxtByPdfbox(inputStream,2);
        log.info(pdf01);
        inputStream.close();
    }
}
