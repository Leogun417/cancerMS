package com.study.cancer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.http.MethodType;
import com.study.cancer.dao.AttachmentMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.AttachmentService;
import com.study.cancer.service.LoginService;
import com.study.cancer.service.MessageService;
import com.study.cancer.websocket.SystemWebSocketHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.FormSubmitEvent;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class BaseController {
    //初始化ascClient需要的几个参数
    final static String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    final static String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    final static String accessKeyId = "LTAIZUXHmIT8vzKn";//你的accessKeyId,参考本文档步骤2
    final static String accessKeySecret = "hiKX4QEx0OR9xnCcTtUqVGAsvYqC0M";//你的accessKeySecret，参考本文档步骤2
    @Resource
    HttpServletRequest request;
    @Resource
    LoginService loginService;
    @Resource
    MessageService messageService;

    @Resource
    SystemWebSocketHandler systemWebSocketHandler;

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonResult login(HttpServletRequest request, String phoneNumber, String password, Model model) {
        CommonResult result = loginService.login(phoneNumber, password);
        request.getSession(true).setAttribute("loginUser", (User) result.getData());
        result.setData("");
        return result;

    }

    public HttpSession getSession() {
        return request.getSession(true);
    }

    public String sendMsg(Integer sendTo, String msg, String tabTitle, String url, String type) throws IOException {
        Message message = new Message();
        message.setContent(msg);
        message.setTabTitle(tabTitle);
        message.setUrl(url);
        message.setType(type);//执行请求0或回执信息1
        message.setSendTo(sendTo);
        message.setSendFrom(((User) getSession().getAttribute("loginUser")).getId());
        CommonResult result = messageService.addMessage(message);
        if (result.isSuccess()) {
            String jsonMessage = JSONObject.toJSON(message).toString();
            systemWebSocketHandler.sendMessageToUser(sendTo + "", new TextMessage(jsonMessage));
            return "success";
        } else {
            return "fail";
        }

    }

    /**
     * 定时器
     */
    class MyTimeTask extends TimerTask {
        private String validateCode;

        public String getValidateCode() {
            return validateCode;
        }

        public void setValidateCode(String validateCode) {
            this.validateCode = validateCode;
        }

        MyTimeTask(String validateCode) {
            this.validateCode = validateCode;
        }

        @Override
        public void run() {
            validateCode = null;
        }
    }


    public String sendSMS(String phoneNumber, Map map, String type) throws ClientException {
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("肿瘤患者化疗管理系统");

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        if (type.equals(SMSConstant.APPLY_FOR_APPEN_TO_PATIENT)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.APPLY_FOR_APPEN_TO_PATIENT);
            request.setTemplateParam("{\"doctorName\":" + map.get("doctorName") + "}");
        } else if (type.equals(SMSConstant.APPLY_FOR_HEALTH_DATA_TO_PATIENT)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.APPLY_FOR_HEALTH_DATA_TO_PATIENT);
            request.setTemplateParam("{\"doctorName\":" + map.get("doctorName") + "}");
        } else if (type.equals(SMSConstant.MODIFY_CODE)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.MODIFY_CODE);
            request.setTemplateParam("{\"code\":" + map.get("code") + "}");
        } else if (type.equals(SMSConstant.NOTICE_PATIENT_TO_HOSPITAL)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.NOTICE_PATIENT_TO_HOSPITAL);
            request.setTemplateParam("{\"groupId\":" + map.get("groupId") + ",\"date\":" + map.get("date") + "}");
        } else if (type.equals(SMSConstant.RECEIPT_TO_PATIENT)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.RECEIPT_TO_PATIENT);
            request.setTemplateParam("{\"doctorName\":" + map.get("doctorName") + "}");
        } else if (type.equals(SMSConstant.REGISTER_CODE)) {
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(SMSConstant.REGISTER_CODE);
            request.setTemplateParam("{\"code\":" + map.get("code") + "}");
        }
        //请求失败这里会抛ClientException异常
        /*SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            return "success";
        }*/
        return "fail";
    }

    @RequestMapping("/sendValidateCode")
    @ResponseBody
    public String sendValidateCode(String phoneNumber, String isRegister) throws ClientException {
        String code = String.valueOf(Math.random() * (9999 - 1000 + 1) + 1000).substring(0, 4);
        //设置定时器使code5分钟失效
        Timer timer = new Timer();
        TimerTask myTimeTask = new MyTimeTask(code);
        getSession().setAttribute("myTimeTask", myTimeTask);
        getSession().setAttribute("generateCode", code);
        timer.schedule(myTimeTask, 5 * 60 * 1000);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code", code);
        if ("1".equals(isRegister)) {
            return sendSMS(phoneNumber, map, SMSConstant.REGISTER_CODE);
        } else {
            return sendSMS(phoneNumber, map, SMSConstant.MODIFY_CODE);
        }
    }

    @RequestMapping("/modifyUserInfo")
    @ResponseBody
    public String modifyUserInfo(User user) {
        CommonResult result = loginService.modifyUserInfo(user);
        if (result.isSuccess()) {
            User newUser = (User) result.getData();
            getLoginUser().setUsername(newUser.getUsername());
            getLoginUser().setSex(newUser.getSex());
            getLoginUser().setAge(newUser.getAge());
            getLoginUser().setIdCardNo(newUser.getIdCardNo());
            if (!"".equals(user.getPassword()) || user.getPassword() != null) {
                getLoginUser().setPassword(newUser.getPassword());
            }
            getLoginUser().setPhoneNumber(newUser.getPhoneNumber());
        }
        return result.getMessage();

    }

    @RequestMapping("/logout")
    public String logout() {
        request.getSession(true).invalidate();
        return "/login";
    }

    @RequestMapping("/userInfo")
    public String userInfo() {
        return "/userInfo";
    }

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/registerPage")
    public String registerPage() {
        return "/register";
    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(HttpServletRequest request, User user, String validateCode) {
        String athorizationLevel = (String) request.getSession(true).getAttribute("athorizationLevel");
        MyTimeTask myTimeTask = (MyTimeTask) getSession().getAttribute("myTimeTask");
        String code = myTimeTask.getValidateCode();
        user.setAthorization(athorizationLevel);
        if (!code.equals(validateCode)) {
            return "验证码输入错误";
        }
        CommonResult result = loginService.register(user);
        return result.getMessage();
    }

    @RequestMapping("/assign")
    @ResponseBody
    public String assign(HttpServletRequest request, String doctorCode) {
        String athorizationLevel = null;
        if (doctorCode != null && !"".equals(doctorCode) && "123456".equals(doctorCode)) {
            //医生注册
            athorizationLevel = "2";
        } else {
            //患者注册
            athorizationLevel = "1";
        }
        request.getSession(true).setAttribute("athorizationLevel", athorizationLevel);
        return doctorCode;
    }

    @RequestMapping("/checkPhone")
    @ResponseBody
    public boolean checkPhone(String phoneNumber) {
        return loginService.checkPhone(phoneNumber);
    }

    @RequestMapping("/checkValidateCode")
    @ResponseBody
    public boolean checkValidateCode(String validateCode) {
        MyTimeTask myTimeTask = (MyTimeTask) getSession().getAttribute("myTimeTask");
        if ("".equals(myTimeTask.getValidateCode()) || myTimeTask.getValidateCode() == null) {
            return false;
        } else {
            return myTimeTask.getValidateCode().equals(validateCode);
        }
    }

    @RequestMapping("/getMenu")
    @ResponseBody
    public Object getMenu() throws UnsupportedEncodingException {
        User loginUser = getLoginUser();
        String athorization = loginUser.getAthorization();
        CommonResult result = loginService.getMenueByLevel(athorization);
        if (result.isSuccess()) {
            List<Menue> data = (List<Menue>) result.getData();
            StringBuffer menueData = new StringBuffer("[");
            for (Menue menue : data) {
                if ("no".equals(menue.getHaschild())) {
                    menueData.append("{" +
                            "text:'" + menue.getNodeText() + "'," +
                            "attributes:{" +
                            "url:'" + menue.getUrl() + "'" +
                            "}" +
                            "},");
                }
            }
            menueData.deleteCharAt(menueData.length() - 1);
            menueData.append("]");
            String menue = menueData.toString();
            Object menue1 = JSON.parse(menue);
            result.setData(menue1);
        } else {
            result.setData("");
        }
        return result;

    }

    public User getLoginUser() {
        return (User) request.getSession(true).getAttribute("loginUser");
    }

    @Resource
    AttachmentService attachmentService;

    /**
     * 获取web程序上下文路径
     *
     * @return
     */
    protected String getContextPath() {
        return request.getContextPath();
    }

    /**
     * 多文件上传
     *
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public CommonResult uploadMore(HttpServletRequest request, Integer recordNo, Integer applyNo, String treatmentProcessId)
            throws IllegalStateException, IOException {//此时的request为DefautMultipartHttpServletRequest而不是单纯的HttpServletRequest，否则无法转化为MultipartHttpServletRequest
        CommonResult result = null;
        // 从配置文件中获取上传地址
        Properties properties = loadFromFile("upload.properties");
        String path = properties.getProperty("uploadUrl");

        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 重命名上传后的文件名
                        String fileName = file.getOriginalFilename();
                        // 获得文件扩展名
                        String prefix = myFileName.substring(myFileName.lastIndexOf(".") + 1);
                        // 定义上传路径，使用转诊单号建立文件夹，便于区分文件
                        File localFile = new File(path + File.separator + recordNo + File.separator + fileName);

                        // 判断是否存在文件夹，如果不存在则创建文件夹
                        File uploadFile = new File(path + File.separator + recordNo);
                        if (!uploadFile.exists() && !uploadFile.isDirectory()) {
                            uploadFile.mkdirs();
                        }

                        file.transferTo(localFile);

                        Attachment attachment = new Attachment();
                        attachment.setAttachmentName(fileName);
                        attachment.setAttachmentPath(path + File.separator + recordNo + File.separator);
                        attachment.setMedicalRecordNo(recordNo + "");
                        attachment.setTreatmentProcessId(treatmentProcessId);
                        if (!"".equals(applyNo)) {
                            attachment.setApplyId(applyNo + "");
                            attachment.setType("apply");
                        } else {
                            attachment.setType("process");
                        }
                        result = attachmentService.addAttachment(attachment);
                    }
                }
                // 记录上传该文件后的时间
                // int finaltime = (int) System.currentTimeMillis();
                // System.out.println(finaltime - pre);
            }
        }
        return result;
    }

    /**
     * 从文件读取属性文件
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Properties loadFromFile(String filename) throws IOException {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        }
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param request
     * @param response
     * @return
     * @Description:
     */
    @ResponseBody
    @RequestMapping("/download")
    public void download(String fileName, String medicalRecordNo, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        // 从配置文件中获取上传地址
        Properties properties = loadFromFile("upload.properties");
        String path = properties.getProperty("uploadUrl");

        // 处理下载文件时中文名称乱码
        File file = new File(path + File.separator + medicalRecordNo + File.separator + fileName);
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + new String(fileName.getBytes("gbk"), "iso-8859-1") + "\"");
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream;charset=UTF-8");

        try {
            InputStream inputStream = new FileInputStream(
                    new File(path + File.separator + medicalRecordNo + File.separator + fileName));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回值要注意，要不然就出现下面这句错误！
        // java+getOutputStream() has already been called for this response
    }
}
