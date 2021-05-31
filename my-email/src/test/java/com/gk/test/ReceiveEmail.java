package com.gk.test;

import com.sun.mail.imap.IMAPStore;
import net.minidev.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/5/8 0:51
 */
public class ReceiveEmail {


    /**
     *　ReceiveEmail类测试
     */
    public static void main(String args[]) throws MessagingException {
            // 准备连接服务器的会话信息
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.imap.host", "imap.qq.com");
            props.setProperty("mail.imap.port", "143");
            String receiver="15637521598@163.com";//接收者账户
            String postCount="151318664@qq.com";//发送者账户
            String username = receiver;
            String password = "reeokuwvsezabfbg";
            // 创建Session实例对象
            Session session = Session.getInstance(props);

            // 创建IMAP协议的Store对象
            Store store = session.getStore("imap");

            // 连接邮件服务器
            store.connect(postCount, password);

            // 获得收件箱
            Folder folder = store.getFolder("INBOX");
            // 以读写模式打开收件箱
            folder.open(Folder.READ_WRITE);

            // 获得收件箱的邮件列表
            Message[] messages = folder.getMessages();

            // 打印不同状态的邮件数量
            System.out.println("收件箱中共" + messages.length + "封邮件!");
            System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
            System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
            System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

            System.out.println("------------------------开始解析邮件----------------------------------");


            int total = folder.getUnreadMessageCount();
            System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
            // 得到收件箱文件夹信息，获取邮件列表
            Message[] msgs = folder.getMessages();
            System.out.println("\t收件箱的总邮件数：" + msgs.length);
            ShowMail re = null;
            for (int i = msgs.length-1; i >= msgs.length-20; i--) {
                Message a = msgs[i];
                //   获取邮箱邮件名字及时间
                Address[] sss=  a.getReplyTo();
                System.out.println(sss[0]);
               System.out.println(isNew(a));
                System.out.println("aaaa----->"+a.getFlags().contains(Flags.Flag.SEEN));

                System.out.println("==============");
                System.out.println(a.getSubject() + "   接收时间：" + a.getReceivedDate().toLocaleString()+"  contentType()" +a.getContentType());
//                    try {
//
//                            re = new ShowMail((MimeMessage) msgs[i]);
//                            System.out.println("邮件　" + i + "　主题:　" + re.getSubject());
//                            System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
//                            System.out.println("邮件　" + i + "　是否需要回复:　" + re.getReplySign());
//                            System.out.println("邮件　" + i + "　是否已读:　" + re.isNew());
//                            System.out.println("邮件　" + i + "　是否包含附件:　" + re.isContainAttach((Part) msgs[i]));
//                            System.out.println("邮件　" + i + "　发送人地址:　" + re.getFrom());
//                            System.out.println("邮件　" + i + "　收信人地址:　" + re.getMailAddress("to"));
//                            System.out.println("邮件　" + i + "　抄送:　" + re.getMailAddress("cc"));
//                            System.out.println("邮件　" + i + "　暗抄:　" + re.getMailAddress("bcc"));
//                            re.setDateFormat("yy年MM月dd日　HH:mm");
//                            System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
//                            System.out.println("邮件　" + i + "　邮件ID:　" + re.getMessageId());
//                            re.getMailContent((Part) msgs[i]);
//                            System.out.println("邮件　" + i + "　正文内容:　\r\n" + re.getBodyText());
//                            re.setAttachPath("e:\\");
//                            re.saveAttachMent((Part) msgs[i]);
//                    }catch (Exception e){
//                            System.out.println("邮件　" + i +e.getMessage());
//                    }

            }
            System.out.println("\t未读邮件数：" + folder.getUnreadMessageCount());
            System.out.println("\t新邮件数：" + folder.getNewMessageCount());
            System.out.println("----------------End------------------");



            // 关闭资源
            folder.close(false);
            store.close();
        }

//    public boolean receiveEmail() throws Exception {
//        /**
//         * 1. 取得链接
//         */
//        Properties props = new Properties();
//        props.setProperty("mail.store.protocol", "imap");
//        props.setProperty("mail.imap.host", "imap.qq.com");
//        props.setProperty("mail.imap.port", "143");
//        String receiver="15637521598@163.com";//接收者账户
//        String postCount="1010207269@qq.com";//发送者账户
//        String username = receiver;
//        String password = "ektbepcespyibdib";
//        // 创建Session实例对象
//        Session session = Session.getInstance(props);
//
//        // 创建IMAP协议的Store对象
//        Store store = session.getStore("imap");
//
//        // 连接邮件服务器
//        store.connect(postCount, password);
//        Folder folder = store.getFolder("INBOX");
//        folder.open(Folder.READ_WRITE);
//        Message[] messages = folder.getMessages(folder.getMessageCount() - folder.getUnreadMessageCount() + 1, folder.getMessageCount());
//        System.out.println("未读邮件数量:　{}"+ messages.length);
//        for (Message msg : messages) {
//            try {
//                //邮件发送日期超过两天 退出遍历方法
//                if (outDays(msg)) {
//                    System.out.println("邮件{}已过期，退出循环"+msg.getSubject());
//                    continue;
//                }
//                //不是最新邮件，跳过
//                if (isNew(msg)) {
//                    System.out.println("邮件不是新邮件，跳过");
//                    continue;
//                }
//                //是否属于需要处理的邮件
//                if (!needProcessEmail(msg)) {
//                    System.out.println("邮件不需要处理，跳过");
//                    continue;
//                }
//                /**
//                 * 处理邮件信息
//                 */
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        //关闭资源
//        folder.close(true);
//        store.close();
//        return false;
//    }

    /**
     * @param
     * @param msg
     * @return boolean
     * @author FeianLing
     * @date 2019/8/20
     * @desc 检查当前邮件是否已超过1天, 接收时间大于1天以前 返回true
     */
//    private boolean outDays(Message msg) throws MessagingException {
//        Date date = DateUtils.offsetDate(new Date(), emailOutDays);
//        Date receiveDate = msg.getReceivedDate();
//        System.out.println("邮件接收时间：{}", DateUtils.formatYmdHms(receiveDate));
//        return receiveDate.getTime() - date.getTime() < 0;
//    }
    /**
     * @param
     * @param msg
     * @return boolean
     * @author FeianLing
     * @date 2019/8/20
     * @desc 判断邮件是否是新的邮件
     */
    private static boolean isNew(Message msg) throws MessagingException {
        boolean isNewFlag = false;
        Flags flags = msg.getFlags();
        Flags.Flag[] flagsArr = flags.getSystemFlags();
       // System.out.println("邮件状态：" + JSONObject.toJSONString(flagsArr));
        for (Flags.Flag flag : flagsArr) {
            if (flag == Flags.Flag.SEEN) {
                isNewFlag = true;
                System.out.println("当前邮件为未读状态！");
                break;
            }
        }
        return isNewFlag;
    }
    /**
     * @param
     * @param msg
     * @return boolean
     * @author FeianLing
     * @date 2019/8/20
     * @desc 检查邮件内容是否需要我们处理
     * 1. 检查发件人是否满足要求
     * 2. 检查是否包含附件
     * 3. 检查是否有满足条件的附件
     */
//    private boolean needProcessEmail(Message msg) throws Exception {
//        System.out.println("needProcessEmail  > 当前邮件的标题：{}"+msg.getSubject());
//        // 1. 检查发件人邮箱是否包含在我们监控的邮箱列表里面
//        String from = getFrom(msg);
//        if (!monitoringEmail.contains(from)) {
//            System.out.println("当前邮件的发件人[{}]不是我们要监控的对象"+ from);
//            return false;
//        }
//        if (!isContainAttach((Part) msg)) {
//            System.out.println("发件人满足要求但是附件为空，不满足我们监控的需求！");
//            return false;
//        }
//        Map<String, InputStream> fileMap = new HashMap<>();
//        getFileInputStream(msg, fileMap);
//        if (fileMap.isEmpty()) {
//            System.out.println("尽管邮件中有附件但是邮件中的附件却无一个满足要求！");
//            return false;
//        }
//        return true;
//    }
    /**
     * @param
     * @param part
     * @return java.io.InputStream
     * @author FeianLing
     * @date 2019/8/20
     * @desc 获取文件输入流
     */
//    private void getFileInputStream(Part part, Map<String, InputStream> inputStreamMap) throws Exception {
//        String fileName;
//        if (part.isMimeType("multipart/*")) {
//            Multipart mp = (Multipart) part.getContent();
//            for (int i = 0; i < mp.getCount(); i++) {
//                BodyPart mPart = mp.getBodyPart(i);
//                String disposition = mPart.getDisposition();
//                if ((disposition != null)
//                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
//                        .equals(Part.INLINE)))) {
//                    fileName = mPart.getFileName();
//                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
//                        fileName = MimeUtility.decodeText(fileName);
//                    }
//                    if (checkFileName(fileName)) {
//                        inputStreamMap.put(fileName, mPart.getInputStream());
//                    }
//                } else if (mPart.isMimeType("multipart/*")) {
//                    System.out.println("子邮件里面的附件");
//                    getFileInputStream(mPart, inputStreamMap);
//                } else {
//                    fileName = mPart.getFileName();
//                    if ((fileName != null)
//                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
//                        fileName = MimeUtility.decodeText(fileName);
//                        if (checkFileName(fileName)) {
//                            inputStreamMap.put(fileName, mPart.getInputStream());
//                        }
//                    }
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            getFileInputStream((Part) part.getContent(), inputStreamMap);
//        }
//    }

    /**
     * @param
     * @param fileName
     * @return boolean
     * @author FeianLing
     * @date 2019/8/20
     * @desc 检查文件名称是否符合要求 FTK-88584316 FTK-申请号.pdf
     */
//    private boolean checkFileName(String fileName) {
//        return EmailServiceImpl.FILENAME_REGEX.matcher(fileName).find();
//    }

    /**
     * @param
     * @param part
     * @return boolean
     * @author FeianLing
     * @date 2019/8/20
     * @desc 判断邮件是否包含附件，如果没有包含附件，返回false 反之返回true
     */
//    private boolean isContainAttach(Part part) throws Exception {
//        boolean attachFlag = false;
//        // String contentType = part.getContentType();
//        if (part.isMimeType("multipart/*")) {
//            Multipart mp = (Multipart) part.getContent();
//            for (int i = 0; i < mp.getCount(); i++) {
//                BodyPart mPart = mp.getBodyPart(i);
//                String disposition = mPart.getDisposition();
//                if ((disposition != null)
//                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
//                        .equals(Part.INLINE)))) {
//                    attachFlag = true;
//                } else if (mPart.isMimeType("multipart/*")) {
//                    attachFlag = isContainAttach((Part) mPart);
//                } else {
//                    String conType = mPart.getContentType();
//
//                    if (conType.toLowerCase().indexOf("application") != -1) {
//                        attachFlag = true;
//                    }
//                    if (conType.toLowerCase().indexOf("name") != -1) {
//                        attachFlag = true;
//                    }
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            attachFlag = isContainAttach((Part) part.getContent());
//        }
//        return attachFlag;
//    }

    /**
     * @param
     * @param msg
     * @return java.lang.String
     * @author FeianLing
     * @date 2019/8/20
     * @desc 获取发送地址
     */
//    private String getFrom(Message msg) throws MessagingException {
//        String from = "";
//        InternetAddress[] addresses = (InternetAddress[]) msg.getFrom();
//        if (null == addresses || addresses.length == 0) {
//            System.out.println("无法获取发送人地址信息！");
//            return from;
//        }
//        Address address = addresses[0];
//        System.out.println("发件人地址json：" + JSONObject.toJSONString(address));
//        String form = ((InternetAddress) address).getAddress();
//        return form;
//    }

}
