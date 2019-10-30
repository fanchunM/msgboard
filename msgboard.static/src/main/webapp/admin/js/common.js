function AjaxErrorHandler(data, callback) {
    try {
        var result = $.parseJSON(data.responseText);
        showAlert(result.errorMessage, function(){
            if(callback) callback();
        });
    } catch (e) {
        showAlert("未知错误", function(){
            if(callback) callback();
        });
    }
}

function showAlert(content,callback) {
    alert(content);
    if(callback) callback();
}
/**
 * 正则空值验证
 */
function validateValue(value) {
	if(value.length==0){
		return false;
	}
	return true;
}

/**
 * 特殊字符串验证
 */
function validateNumber(value) {
    var regex = /^[0-9]\d*$/;
    return regex.test(value)
}
function validateNumber02(value) {
    var regex = /^-?\d+$/;
    return regex.test(value)
}

/**
 * 确认框
 * @param title
 * @param confirmCallBack
 * @param cancleCallBack
 * @returns
 */
function confirmDialog(title, confirmCallBack, cancleCallBack) {
    var flag = window.confirm(title);
    if(flag){
        if(confirmCallBack) confirmCallBack();
        return true;
    }else{
        if(cancleCallBack) cancleCallBack();
        return false;
    }
}

/**
 * Date String 转换成 Date
 * @param s 日期字符串
 * @returns {Date}
 */
function convertToDate(s){
    if(s){
        // s = s.replace(/\//g,'-');
        // console.log('before new s' + s);
        var temp = Date.parse(s);
        // console.log('before new Date' + temp);
        return new Date(temp);
    } else
        return new Date();
}

/**
 * 前台分页
 * @param data
 * @returns {*}
 */
function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

/**
函数：格式化日期
参数：formatStr-格式化字符串
d：将日显示为不带前导零的数字，如1
dd：将日显示为带前导零的数字，如01
ddd：将日显示为缩写形式，如Sun
dddd：将日显示为全名，如Sunday
M：将月份显示为不带前导零的数字，如一月显示为1
MM：将月份显示为带前导零的数字，如01
MMM：将月份显示为缩写形式，如Jan
MMMM：将月份显示为完整月份名，如January
yy：以两位数字格式显示年份
yyyy：以四位数字格式显示年份
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
hh：使用12小时制将小时显示为带前导零的数字
H：使用24小时制将小时显示为不带前导零的数字
HH：使用24小时制将小时显示为带前导零的数字
m：将分钟显示为不带前导零的数字
mm：将分钟显示为带前导零的数字
s：将秒显示为不带前导零的数字
ss：将秒显示为带前导零的数字
l：将毫秒显示为不带前导零的数字
ll：将毫秒显示为带前导零的数字
tt：显示am/pm
TT：显示AM/PM
返回：格式化后的日期
*/
Date.prototype.format = function (formatStr) {
    var date = this;
    /**
    函数：填充0字符
    参数：value-需要填充的字符串, length-总长度
    返回：填充后的字符串
    */
    var zeroize = function (value, length) {
        if (!length) {
            length = 2;
        }
        value = new String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
        switch ($0) {
            case 'd': return date.getDate();
            case 'dd': return zeroize(date.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
            case 'M': return date.getMonth() + 1;
            case 'MM': return zeroize(date.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
            case 'yy': return new String(date.getFullYear()).substr(2);
            case 'yyyy': return date.getFullYear();
            case 'h': return date.getHours() % 12 || 12;
            case 'hh': return zeroize(date.getHours() % 12 || 12);
            case 'H': return date.getHours();
            case 'HH': return zeroize(date.getHours());
            case 'm': return date.getMinutes();
            case 'mm': return zeroize(date.getMinutes());
            case 's': return date.getSeconds();
            case 'ss': return zeroize(date.getSeconds());
            case 'l': return date.getMilliseconds();
            case 'll': return zeroize(date.getMilliseconds());
            case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
        }
    });
}

function convertStatus(statusStr){
    if(statusStr == "-1" || statusStr == -1)
        return '撤销删除';
    if(statusStr == "0" || statusStr == 0)
        return '待派发';
    if(statusStr == "1" || statusStr == 1)
        return '退回待派发';
    if(statusStr == "2" || statusStr == 2)
        return '待交办';
    if(statusStr == "3" || statusStr == 3)
        return '退回待交办';
    if(statusStr == "4" || statusStr == 4)
        return '待查阅';
    if(statusStr == "5" || statusStr == 5)
        return '处办中';
    if(statusStr == "6" || statusStr == 6)
        return '待复核';
    if(statusStr == "7" || statusStr == 7)
        return '部门待复审';
    if(statusStr == "8" || statusStr == 8)
        return '部门待审核';
    if(statusStr == "9" || statusStr == 9)
        return '集团待复审';
    if(statusStr == "10" || statusStr == 10)
        return '集团待审批';
    if(statusStr == "11" || statusStr == 11)
        return '待抽查';
    if(statusStr == "12" || statusStr == 12)
        return '已抽查';
    if(statusStr == "15" || statusStr == 15)
        return '转交办';
    if(statusStr == "16" || statusStr == 16)
        return '转交处理完毕';
    if(statusStr == "20" || statusStr == 20)
        return '删除';
}