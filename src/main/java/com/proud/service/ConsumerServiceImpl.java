package com.proud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proud.dao.ConsumerDao;
import com.proud.entity.ConsumerEntity;
import com.proud.exception.ConsumerException;
import com.proud.pkg.server.WebResponse;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerDao, ConsumerEntity> implements ConsumerService {
    private static final long SIGN_UP_VERIFICATION_CODE_EXPIRE_TIME = 15 * 60;

    private static final String REDIS_KEY_FORMAT = "VERIFICATION_CODE";

    private final JedisPool jedisPool;

    public ConsumerServiceImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public WebResponse sendMobileNumberSignUpVerificationCode(String mobileCode, String mobileNumber) throws ConsumerException {
        if (!MobileRegularExp.isMobileNumber(mobileCode, mobileNumber)) {
            throw ConsumerException.illegalMobileNumber(mobileCode, mobileNumber);
        }
        Jedis jedis = jedisPool.getResource();
        String key = REDIS_KEY_FORMAT + ":" + mobileCode + mobileNumber;
        if (jedis.exists(key + ":SEND")) {
            throw ConsumerException.verificationCodeSendingFrequent();
        }
        String code = jedis.get(key);
        if (code == null || code.length() == 0) {
            jedis.setex(key + ":SEND", Long.valueOf(60), "1");
            code = ConsumerServiceImpl.generateVerificationCode(999999);
            jedis.setex(key, SIGN_UP_VERIFICATION_CODE_EXPIRE_TIME, code);
        }

        // TODO: 短信运营商调用
        System.out.println("Send sms code: " + code + " to " + mobileCode + " " + mobileNumber);

        return WebResponse.success();
    }

    @Override
    public WebResponse signUp(String mobileCode, String mobileNumber, String password, String verificationCode) throws ConsumerException {
        if (!MobileRegularExp.isMobileNumber(mobileCode, mobileNumber)) {
            throw ConsumerException.illegalMobileNumber(mobileCode, mobileNumber);
        }

        String patternStr ="^(?![A-Za-z\\d]+$)(?![a-z\\d#?!@$%^&*-.]+$)(?![A-Za-z#?!@$%^&*-.]+$)(?![A-Z\\d#?!@$%^&*-.]+$)[a-zA-Z\\d#?!@$%^&*-.]{8,16}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
            throw ConsumerException.illegalPassword();
        }

        Jedis jedis = jedisPool.getResource();
        String key = REDIS_KEY_FORMAT + ":" + mobileCode + mobileNumber;
        String code = jedis.get(key);
        if (code == null || code.length() == 0 || !code.equals(verificationCode)) {
            throw ConsumerException.verificationCodeError();
        }
        jedis.del(key, key + ":SEND");

        ConsumerEntity consumer = new ConsumerEntity();
        consumer.setMobileCode(mobileCode);
        consumer.setMobileNumber(mobileNumber);
        consumer.setPassword(ConsumerServiceImpl.encryptPassword(password));
        consumer.setNickname(mobileNumber);
        save(consumer);

        QueryWrapper<ConsumerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("mobile_number", mobileNumber);

        return WebResponse.success(baseMapper.selectOne(queryWrapper));
    }

    @Override
    public WebResponse signInWithEmail(String email, String password) {
        return null;
    }

    @Override
    public WebResponse signInWithEmailVerificationCode(String email, String verificationCode) {
        return null;
    }

    @Override
    public WebResponse signInWithMobileNumberPassword(String mobileNumber, String password) {

        QueryWrapper<ConsumerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("mobile_number", mobileNumber).ge("password", ConsumerServiceImpl.encryptPassword(password));

        ConsumerEntity consumer = baseMapper.selectOne(queryWrapper);
        if (consumer == null) {
            return WebResponse.failed(404, "consumer not existed or mobile number and password error");
        }

        return WebResponse.success(consumer);
    }

    @Override
    public WebResponse signInWithMobileVerificationCode(String mobileCode, String mobileNumber, String verificationCode) throws ConsumerException {
       return null;
    }


    /**
     * @param bound 验证码极限值
     * @return 长度等同于bound的字符串格式的随机验证码
     */
    public static String generateVerificationCode(int bound) {
        Random rand = new Random();
        int number = rand.nextInt(bound);
        return String.format("%0" + (bound + "").length() + "d", number);
    }

    /**
     * @param password 需要加密的密码
     * @return 加密后的字符串
     */
    public static String encryptPassword(String password) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(("SALT:22611143" + password).getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            for (byte aByte : messageDigest.digest()) {
                temp = Integer.toHexString(aByte & 0xFF);
                if (temp.length() == 1) {
                    //1得到一位的进行补0操作
                    stringBuilder.append("0");
                }
                stringBuilder.append(temp);
            }
            encodeStr =  stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    public enum MobileRegularExp {
        /*以下是项目可能设计到的市场*/
        CN("中国", "^(\\+?0?86\\-?)?1[345789]\\d{9}$"),
        TW("台湾", "^(\\+?886\\-?|0)?9\\d{8}$"),
        HK("香港", "^(\\+?852\\-?)?[569]\\d{3}\\-?\\d{4}$"),
        MS("马来西亚", "^(\\+?6?01){1}(([145]{1}(\\-|\\s)?\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$"),
        PH("菲律宾", "^(\\+?0?63\\-?)?\\d{10}$"),
        TH("泰国", "^(\\+?0?66\\-?)?\\d{10}$"),
        SG("新加坡", "^(\\+?0?65\\-?)?\\d{10}$"),
        /*以下是其他国家的手机号校验正则*/
        DZ("阿尔及利亚", "^(\\+?213|0)(5|6|7)\\d{8}$"),
        SY("叙利亚", "^(!?(\\+?963)|0)?9\\d{8}$"),
        SA("沙特阿拉伯", "^(!?(\\+?966)|0)?5\\d{8}$"),
        US("美国", "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
        CZ("捷克共和国", "^(\\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$"),
        DE("德国", "^(\\+?49[ \\.\\-])?([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$"),
        DK("丹麦", "^(\\+?45)?(\\d{8})$"),
        GR("希腊", "^(\\+?30)?(69\\d{8})$"),
        AU("澳大利亚", "^(\\+?61|0)4\\d{8}$"),
        GB("英国", "^(\\+?44|0)7\\d{9}$"),
        CA("加拿大", "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
        IN("印度", "^(\\+?91|0)?[789]\\d{9}$"),
        NZ("新西兰", "^(\\+?64|0)2\\d{7,9}$"),
        ZA("南非", "^(\\+?27|0)\\d{9}$"),
        ZM("赞比亚", "^(\\+?26)?09[567]\\d{7}$"),
        ES("西班牙", "^(\\+?34)?(6\\d{1}|7[1234])\\d{7}$"),
        FI("芬兰", "^(\\+?358|0)\\s?(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$"),
        FR("法国", "^(\\+?33|0)[67]\\d{8}$"),
        IL("以色列", "^(\\+972|0)([23489]|5[0248]|77)[1-9]\\d{6}"),
        HU("匈牙利", "^(\\+?36)(20|30|70)\\d{7}$"),
        IT("意大利", "^(\\+?39)?\\s?3\\d{2} ?\\d{6,7}$"),
        JP("日本", "^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$"),
        NO("挪威", "^(\\+?47)?[49]\\d{7}$"),
        BE("比利时", "^(\\+?32|0)4?\\d{8}$"),
        PL("波兰", "^(\\+?48)? ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$"),
        BR("巴西", "^(\\+?55|0)\\-?[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$"),
        PT("葡萄牙", "^(\\+?351)?9[1236]\\d{7}$"),
        RU("俄罗斯", "^(\\+?7|8)?9\\d{9}$"),
        RS("塞尔维亚", "^(\\+3816|06)[- \\d]{5,9}$"),
        TR("土耳其", "^(\\+?90|0)?5\\d{9}$"),
        VN("越南", "^(\\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$"),
        /* 以下是匹配所有手机号校验正则*/
        OT("所有", "^(\\+?0)?([0-9]*[1-9][0-9]*)$");

        /**
         * 国际名称
         */
        private String national;

        /**
         * 正则表达式
         */
        private String regularExp;

        public String getNational() {
            return national;
        }

        public void setNational(String national) {
            this.national = national;
        }

        public String getRegularExp() {
            return regularExp;
        }

        public void setRegularExp(String regularExp) {
            this.regularExp = regularExp;
        }

        MobileRegularExp(String national, String regularExp) {
            this.national = national;
            this.regularExp = regularExp;
        }


        public static boolean isMobileNumber(String nationalCode, String mobileNumber) {
            boolean isMobileNumber = false;
            for (MobileRegularExp regularExp : MobileRegularExp.values()) {
                Pattern pattern = Pattern.compile(regularExp.getRegularExp());
                Matcher matcher = pattern.matcher(nationalCode + mobileNumber);
                if (matcher.matches()) {
                    isMobileNumber = true;
                    // 枚举中把最常用的国际区号拍在前面可以减少校验开销
                    break;
                }
            }
            return isMobileNumber;
        }

    }
}
