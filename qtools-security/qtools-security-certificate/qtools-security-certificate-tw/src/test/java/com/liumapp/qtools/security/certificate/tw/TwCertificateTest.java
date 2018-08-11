package com.liumapp.qtools.security.certificate.tw;

import com.liumapp.qtools.security.certificate.tw.cert.CertInfo;
import com.liumapp.qtools.security.certificate.tw.component.RaService;
import com.liumapp.qtools.security.certificate.tw.license.LicenseUtil;
import com.liumapp.qtools.security.certificate.tw.property.Properties;
import com.liumapp.qtools.security.certificate.tw.user.UserInfo;
import com.liumapp.qtools.security.certificate.tw.utils.AxisUtil;
import com.liumapp.qtools.security.certificate.tw.utils.ServerPKCSUtil;
import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * @author liumapp
 * @file TwCertificateTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/11
 */
public class TwCertificateTest extends TestCase {

    private boolean debug = false;

    protected Properties properties;

    protected LicenseUtil licenseUtil;

    protected RaService raService;

    @Override
    protected void setUp() throws Exception {
        properties = new Properties("sign.properties");

        AxisUtil axisUtil = new AxisUtil();
        axisUtil.initProperty(properties);

        raService = new RaService(axisUtil);
        raService.initProperty(properties);

        licenseUtil = new LicenseUtil();
        licenseUtil.initProperty(properties);
        licenseUtil.setKeyStore("demo.ks");
        licenseUtil.setNegativeKeyStorePath("ks/demo.ks");
    }


    /**
     * 返回base64编码的pfx证书
     */
    @Test
    public void testGeneratePfx () {
        String name = "zhangsan";
        String password = "123123";
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(name); // 证书名称
        userInfo.setUserEmail("admin@huluwa.cc"); // 证书所有者Email
        userInfo.setUserAdditionalField1("field1"); // 扩展字段1
        JSONObject jsonObject = new JSONObject();
        try {
            ServerPKCSUtil serverPKCSUtil = new ServerPKCSUtil();
            jsonObject = raService.enrollCertAA(userInfo, serverPKCSUtil.genCsr("RSA"), "", 0);// 不设置证书有效期，默认读取services.properties的属性值
            CertInfo certInfo = new CertInfo();
            if (jsonObject.get("certInfo") != null) {
                certInfo = (CertInfo) jsonObject.get("certInfo");
            }
            String certSignBufP7 = certInfo.getCertSignBuf();// 公钥证书
            String pkcs12Cert = serverPKCSUtil.genP12(password, certSignBufP7);
            jsonObject.put("pfx", pkcs12Cert);
            jsonObject.put("serialNumber", certInfo.getCertSerialNumber());
            jsonObject.remove("certInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.toString());
    }


}
