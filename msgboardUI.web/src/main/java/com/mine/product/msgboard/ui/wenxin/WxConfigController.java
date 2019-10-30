package com.mine.product.msgboard.ui.wenxin;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 读取配置文件，进行回调微信授权域名
 * @author 何森
 *
 */
@Controller
@RequestMapping("/")
public class WxConfigController {
    @RequestMapping("MP_verify_xCu2ScQDPq5fqxGW.txt")
    @ResponseBody
    private String returnConfigFile(HttpServletResponse response) {
        return "xCu2ScQDPq5fqxGW";
    }
}