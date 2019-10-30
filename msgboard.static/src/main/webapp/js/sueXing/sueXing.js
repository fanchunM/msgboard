/*
   适用于 WK webview 的 原生交互能力库
 */
window.nativeData = {};
var ERRORMSG = "请使用移动设备访问";

function getUserInfoForH5() {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var userInfo = window.bwtJsObj.getUserInfo();
        nativeDatafactory(userInfo);
    } else if (getMobileTypeHkx() == "ios-ui") {
        var userInfo = NativeApp.getUserInfo();
        nativeDatafactory(userInfo);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.getUserInfo.postMessage(null);
    } else {
        alert(ERRORMSG);
    }
}

function getAppInfoForH5() {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var appInfo = window.bwtJsObj.getAppInfo();
        nativeDatafactory(appInfo);
    } else if (getMobileTypeHkx() == "ios-ui") {
        var appInfo = NativeApp.getAppInfo();
        nativeDatafactory(appInfo);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.getAppInfo.postMessage(null);
    } else {
        alert(ERRORMSG);
    }
}

function getDeviceInfoForH5() {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var deviceInfo = window.bwtJsObj.getDeviceInfo();
        nativeDatafactory(deviceInfo);
    } else if (getMobileTypeHkx() == "ios-ui") {
        var deviceInfo = NativeApp.getDeviceInfo();
        nativeDatafactory(deviceInfo);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.getDeviceInfo.postMessage(null);
    } else {
        alert(ERRORMSG);
    }
}

function getCityInfoForH5() {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var cityInfo = window.bwtJsObj.getCityInfo();
        nativeDatafactory(cityInfo);
    } else if (getMobileTypeHkx() == "ios-ui") {
        var cityInfo = NativeApp.getCityInfo();
        nativeDatafactory(cityInfo);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.getCityInfo.postMessage(null);
    } else {
        alert(ERRORMSG);
    }
}

function nativeVerifySign(response, signature) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var result = window.bwtJsObj.nativeVerifySign(response, signature);
        nativeDatafactory(result);
    } else if (getMobileTypeHkx() == "ios-ui") {
        var result = NativeApp.nativeVerifySign(response, signature);
        nativeDatafactory(result);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeVerifySign.postMessage("{'response:'" + response + ",'signature:'" + signature + "}");
    } else {
        alert(ERRORMSG);
    }
}

function closeWebView() {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.closeWebView();
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.closeWebView();
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.closeWebView.postMessage(null);
    } else {
        alert(ERRORMSG);
    }
}

function nativeCallPhone(phoneNum) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeCallPhone(phoneNum);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeCallPhone(phoneNum);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeCallPhone.postMessage(phoneNum);
    } else {
        alert(ERRORMSG);
    }
}

function toNativeRouter(routeUrl, routeParams, callback) {
    if (callback == undefined) {
        callback = "";
    }
    var params = {
        'routeUrl': routeUrl,
        'routeParams': routeParams,
        'callback': callback
    };
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.toNativeRouter(routeUrl, routeParams, callback);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.toNativeRouter(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.toNativeRouter.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function nativeSelectPic(cameraType, callback) {

    var params = {
        'cameraType': cameraType,
        'callback': callback
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeSelectPic(cameraType, callback);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeSelectPic(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeSelectPic.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function nativeSetStorage(key, value) {
    var params = {
        'key': key,
        'value': value
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeSetStorage(key, value);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeSetStorage(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {

        window.webkit.messageHandlers.nativeSetStorage.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function nativeGetStorage(key) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        var storageResult = window.bwtJsObj.nativeGetStorage(key);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeGetStorage(key);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeGetStorage.postMessage(key);
    } else {
        alert(ERRORMSG);
    }
}

function nativeShare(shareType, shareConfig, callback) {
    var params = {
        'shareType': shareType,
        'shareConfig': shareConfig,
        'callback': callback
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeShare(shareType, shareConfig, callback);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeShare(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeShare.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function openSysPage(pageCode) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.openSysPage(pageCode);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.openSysPage(pageCode);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.openSysPage.postMessage(pageCode);
    } else {
        alert(ERRORMSG);
    }
}

function setWebviewMenu(config) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.setWebviewMenu(config);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.setWebviewMenu(config);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.setWebviewMenu.postMessage(config);
    } else {
        alert(ERRORMSG);
    }
}

function webViewSignUp(tag) {
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.webViewSignUp(tag);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.webViewSignUp(tag);
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.webViewSignUp.postMessage(tag);
    } else {
        alert(ERRORMSG);
    }
}

function nativeCallWebView(tag, functionName, params) {
    var params = {
        'tag': tag,
        'functionName': functionName,
        'params': params
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeCallWebView(tag, functionName, params);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeCallWebView(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeCallWebView.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}
/**
 * @20180920 2.1.0 新增 useRules 参数
 * 0-不使用app 端规则，扫码后直接关闭扫一扫并把码值回给H5
 * 1-使用app 端规则，扫码后匹配原生端规则进行跳转并把值回给H5
 */
function nativeScan(functionName, useRules) {
    var params = {
        'functionName': functionName,
        'useRules': useRules
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.nativeScan(functionName, useRules);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.nativeScan(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.nativeScan.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function getMobileTypeBase() {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var isWk = u.indexOf('WKWebView') > -1;
    var isAdrBase = u.indexOf('adrBaseWebView') > -1;
    var isiOSBase = u.indexOf('UIWebViewBase') > -1;
    if (isAndroid) {
        if (isAdrBase) {
            return true;
        } else {
            return false;
        }
    } else if (isiOS) {
        if (isWk || isiOSBase) {
            return true;
        } else {
            return false;
        }
    }
}

function setHeaderBtn(btnText, functionName, params) {
    var params = {
        'btnText': btnText,
        'functionName': functionName,
        'params': params
    }
    if (getMobileTypeHkx() == "android" || getMobileTypeHkx() == "android-base") {
        window.bwtJsObj.setHeaderBtn(btnText, functionName, params);
    } else if (getMobileTypeHkx() == "ios-ui") {
        NativeApp.setHeaderBtn(JSON.stringify(params));
    } else if (getMobileTypeHkx() == "ios-wk") {
        window.webkit.messageHandlers.setHeaderBtn.postMessage(JSON.stringify(params));
    } else {
        alert(ERRORMSG);
    }
}

function getBaseUrl() {
    const nowUrlBase = window.location.href;
    return nowUrlBase.split("/api_h5")[0] + "/api_h5/";
}

function getApiUrl() {
    const nowUrlApi = window.location.href;
    return nowUrlApi.split("/api_h5")[0] + "/api_v3/app";
}


//iOS callback List
//------------------------WK CALLBACK 开始----------------------------

function getUserInfo(userInfo) {
    nativeDatafactory(userInfo);
    console.log("callbackUser");
}

function getAppInfo(appInfo) {
    nativeDatafactory(appInfo);
    console.log("callbackInfo");
}

function getDeviceInfo(deviceInfo) {
    nativeDatafactory(deviceInfo);
}

function getCityInfo(cityInfo) {
    nativeDatafactory(cityInfo);
}
//callback处理工厂
function nativeDatafactory(nativeJsonStr) {
    var nativeObj = JSON.parse(nativeJsonStr)
    for (var i in nativeObj) {
        eval("window.nativeData." + i + "='" + nativeObj[i] + "'");
    }
    console.log(window.nativeData);
}

//------------------------WK CALLBACK 结束----------------------------

//planB：把vm对象抛给上层
//重试机制处理：status状态标识
function getMobileTypeHkx() {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var isWk = u.indexOf('WKWebView') > -1;
    var isAdrBase = u.indexOf('adrBaseWebView') > -1;
    //var isiOSBase = u.indexOf('UIWebViewBase') > -1;
    if (isAndroid) {
        if (isAdrBase) {
            return "android-base";
        } else {
            return "android";
        }
    } else if (isiOS) {
        if (isWk) {
            return "ios-wk";
        } else {
            return "ios-ui";
        }
    }
}

function dataFactory(value) {
    return value;
}

//禁止alert上的url显示
window.alert = function(name) {
    var iframe = document.createElement("IFRAME");
    iframe.style.display = "none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
}

/*export {
    hkxWK
}*/