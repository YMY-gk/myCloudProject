package com.gk.email.contraler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/5/7 23:19
 */

@RestController
public class EmaiController {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @RequestMapping("/sendEmail")
    public void sendTxtMail() {
        //简单邮件发送
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        String receiver="3436054021@qq.com";
        //ektbepcespyibdib
        //rkpxsnhxzqbebeji
        simpleMailMessage.setTo(new String[] {receiver});
        simpleMailMessage.setFrom("1010207269@qq.com");
        simpleMailMessage.setSubject("Spring Boot Mail 邮件测试【文本】");
        simpleMailMessage.setText("这里是一段简单文本测试邮件。！！！！！请忽略！！！！！");
        // 发送邮件
        mailSender.send(simpleMailMessage);
        System.out.println("邮件已发送");
    }
    /**
     * 发送包含HTML文本的邮件
     * @throws Exception
     */
    @RequestMapping("/sendHtmlMail")
    public void sendHtmlMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        String receiver="3436054021@qq.com";//接收者账户
        String postCount="1010207269@qq.com";//发送者账户
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setFrom(postCount);
        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【HTML】");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
        sb.append("</html>");

        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);
        // 发送邮件
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送");
    }


    /**
     * 发送包含内嵌图片的邮件
     * @throws Exception
     */
    @RequestMapping("/sendAttachedImageMail")
    public void sendAttachedImageMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // multipart模式
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        String receiver="3436054021@qq.com";//接收者账户
        String postCount="1010207269@qq.com";//发送者账户
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setFrom(postCount);
        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
        // cid为固定写法，imageId指定一个标识
        sb.append("<img src='https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png'></body>");
        sb.append("</html>");

        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);

        // 设置imageId
        FileSystemResource img = new FileSystemResource(new File("E:\\元气壁纸缓存\\khealtheye\\wallpaper\\111.jpg"));
        mimeMessageHelper.addInline("imageId", img);

        // 发送邮件
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送");
    }




    /**
     * 发送包含附件的邮件
     * @throws Exception
     */
    @RequestMapping("/sendAttendedFileMail")
    public void sendAttendedFileMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // multipart模式
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        String receiver="3436054021@qq.com";//接收者账户
        String postCount="1010207269@qq.com";//发送者账户
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setFrom(postCount);
        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【附件】");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail annex test</p></body>");
        sb.append("</html>");

        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);
        // 设置附件
        FileSystemResource file = new FileSystemResource(new File("F:\\pdf\\1111.pdf"));
        mimeMessageHelper.addAttachment("PersonalInformation.docx", file);

        // 发送邮件
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送");
    }

}
