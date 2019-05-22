/*
var echarts;

$(function () {

    //config的设置是全局的
    layui.config({
        base: '/plugins/layui-extend/' //存放拓展模块的根目录
    });

    layui.use(['form', 'layer', 'element', 'laydate', 'table', 'echarts'], function () {
        var form = layui.form;
        var layer = parent.layer || layui.layer;
        var laydate = layui.laydate;
        var table = layui.table;

        $(".panel a").on("click", function () {
            var _this = $(this);
            var htmlTab = $(_this.html());
            window.parent.addTab(htmlTab.attr('data-url', (_this.attr('data-url') + '?type=' + _this.find('span').attr('id'))));
        });

        $('#shortcutEntry a').on('click', function (event) {
            event.preventDefault();
            var _this = $(this);
            window.parent.addTabByUrlTitleIcon(_this.attr('href'), _this.attr('title'), _this.data('icon'));
        });


    });//layui.use的结束括号

});//jquery的结束括号

function echartStr(names, brower) {
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '某站点用户访问来源',
            subtext: '默认显示当前天',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "访问来源:{b}<br/>共{c}条,占比:{d}%。"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: names
        }
        , calculable: true
        , series: [
            {
                name: '扫描批次',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: brower
            }
        ]
        , toolbox: {
            show: true,
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        }
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    function eConsole(param) {
        //var mes = '【' + param.type + '】';
        if (typeof param.seriesIndex != 'undefined') {
            // mes += '  seriesIndex : ' + param.seriesIndex;
            // mes += '  dataIndex : ' + param.dataIndex;
            if (param.type == 'click') {
                var peiLenght = option.legend.data.length;
                // alert(peiLenght);// 获取总共给分隔的扇形数
                for (var i = 0; i < peiLenght; i++) {
                    //everyClick(param, i, option.legend.data[i], data_url[i])
                    if (param.seriesIndex == 0 && param.dataIndex == i) {

                        // renderTodayUserTable(option.legend.data[i]);

                        return;
                    }
                }
            } else {
                //alert("出现了未知的错误");
            }
        }
    }


    myChart.on("click", eConsole);
    myChart.on("hover", eConsole);


};

/!**
 * 渲染今日用户访问表格
 * @param {string} chartId 点击的哪一块扇形
 *!/
function renderTodayUserTable(chartId) {
    top.layuiTable(layui.table, {
        elem: '#scanTable'
        , method: "get"
        , contentType: "application/json"
        , limit: 50
        , limits: [10, 50, 200, 500]
        , page: {theme: '#1E9FFF'}
        , height: 480
        , url: '/services/data/todayUserTable.json'
        , where: {
            batchNumber: chartId
        }
        , cols: [[
            {type: 'numbers', title: "#", minWidth: 70, width: 70}
            , {
                field: 'area', title: '地区', width: 170, minWidth: 170, sort: true, templet: function (data) {
                    return '<a style="color:blue" href="https://www.baidu.com/s?ie=UTF-8&wd=' + encodeURI(data.area) + '" title="地区" target="_blank">' + data.area + '</a>';
                }
            }
            , {
                title: '访问来源', minWidth: 220, sort: true, templet: function (d) {
                    return chartId;
                }
            }
        ]]
        , done: function (res, curr, count) {
        }
    });
}
*/

//自己写
$.ajaxSetup({
    global: false,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        console.log('XMLHttpRequest:' + XMLHttpRequest + ';textStatus:' + textStatus + ';errorThrown:' + errorThrown);
        layer.open({
            title: "提示",
            content: "提示：登录请求失败，请检查网络或服务器运行状态:" + textStatus,
        });
    }
});


var main = {
    filter: function (url) {
        layui.table.reload('dgGrid',
            {
                url: url,
                where: {
                    'searchValue': $('#search-name').val()
                }
            });
    },
    setNavTitle: function () {
        $('#sidebar .nav-list li').each(function () {
            var active = true;
            $(this).find('li').each(function () {
                if ($(this).text().trim() == document.title) {
                    $(this).addClass('active');
                    active = false;
                    return false;
                }
            });
            if (!active) {
                $(this).addClass('open')
            }
        });
    },
    getCurrentUser: function () {
        $.ajax({
            url: '/user/getCurrentUser',
            success: function (d) {
                if (d.picture == null) {
                    $('#nav-user-photo').attr('src', '/assets/images/avatars/profile-pic.jpg')
                } else {
                    $('#nav-user-photo').attr('src', '/file/getCurrentProfile')
                }
            }
        });
    },
    setCurrentUserId: function () {
        $('#p-currentUserId').html($('#hidden-currentUserId').val());
    },
    getUrlParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    },
    bytesToSize: function (bytes) {
        if (bytes === 0) return '0 B';
        var k = 1024,
            sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            i = Math.floor(Math.log(bytes) / Math.log(k));

        return (bytes / Math.pow(k, i)).toPrecision(4) + ' ' + sizes[i];
    },
    formatStr: function () {
        for (var i = 1; i < arguments.length; i++) {
            var exp = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
            arguments[0] = arguments[0].replace(exp, arguments[i]);
        }
        return arguments[0];
    }
}


$.fn.serializeObject = function () {
    var o = {};
    $.each($(this).serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
}

$.fn.loadJson = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        var $oinput = obj.find(":input[name=" + name + "]");
        if ($oinput.attr("type") == "radio"
            || $oinput.attr("type") == "checkbox") {
            $oinput.each(function () {
                if (Object.prototype.toString.apply(ival) == '[object Array]') {//是复选框，并且是数组
                    for (var i = 0; i < ival.length; i++) {
                        if ($(this).val() == ival[i])
                            $(this).attr("checked", "checked");
                    }
                } else {
                    if ($(this).val() == ival)
                        $(this).attr("checked", "checked");
                }
            });
        } else if ($oinput.attr("type") == "textarea") {//多行文本框
            obj.find("[name=" + name + "]").html(ival);
        } else {
            obj.find("[name=" + name + "]").val(ival);
        }
    });
};

$.fn.jqvalidate = function (options) {
    var defaults = {
        errorElement: 'div',
        errorClass: 'help-block',
        focusInvalid: false,
        ignore: "",
        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },
        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
            $(e).remove();
        },
        errorPlacement: function (error, element) {
            if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                var controls = element.closest('div[class*="col-"]');
                if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if (element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if (element.is('.chosen-select')) {
                error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
            }
            else error.insertAfter(element.parent());
        },

        submitHandler: function (form) {
        },
        invalidHandler: function (form) {
        }
    }

    $.extend(defaults, options);

    $(this).validate(defaults);
}


/**
 * 创建模态窗。
 * @param {Object} options 参数配置
 */
$.layerOpen = function (options) {
    var defaults = {
        id: "default" + Math.random(),
        title: '系统窗口',
        type: 2,
        skin: 'layui-layer-molv',
        width: "auto",
        height: "auto",
        shadeClose: false,
        content: '',
        closeBtn: 0,
        shade: 0.3,
        maxmin: true,
        btn: ['确认', '取消'],
        btnclass: ['btn btn-primary', 'btn btn-danger'],
        yes: null
    };
    options = $.extend(defaults, options);
    if (!com.ispc()) {
        options.width = '100%';
        options.height = '100%';
    }

    top.layer.open({
        id: options.id,
        type: options.type,
        scrollbar: false,
        skin: options.skin,
        shade: options.shade,
        shadeClose: options.shadeClose,
        maxmin: options.maxmin,
        title: options.title,
        fix: false,
        area: [options.width, options.height],
        content: options.content,
        btn: options.btn,
        btnclass: options.btnclass,
        yes: function (index, layero) {
            if (options.type == 1) {
                options.yes && options.yes(index, layero);
                return;
            }
            if (options.yes && $.isFunction(options.yes)) {
                var iframebody = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0].contentWindow;
                options.yes(iframebody, iframeWin, index);
            }
        },
        cancel: function () {
            return true;
        },
        success: function (layero, index) {
            if (options.type == 1) {
                options.success && options.success(index, layero);
                return;
            }
            if (options.success) {
                var iframebody = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0].contentWindow;
                options.success(iframebody, iframeWin, index);
            }
        }
    });

};

/**
 * 关闭模态窗。
 */
$.layerClose = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 创建询问框。
 * @param {Object} options 参数配置
 */
$.layerConfirm = function (options) {
    var defaults = {
        title: '提示',
        skin: 'layui-layer-molv',
        content: "",
        icon: 3,
        resize: false,
        shade: 0.3,
        anim: 4,
        btn: ['确认', '取消'],
        btnclass: ['btn btn-primary', 'btn btn-danger'],
        callback: null
    };
    options = $.extend(defaults, options);
    layer.confirm(options.content, {
        title: options.title,
        icon: options.icon,
        btn: options.btn,
        btnclass: options.btnclass,
        resize: options.resize,
        skin: options.skin,
        anim: options.anim
    }, function (index) {
        if (options.callback && $.isFunction(options.callback)) {
            options.callback();
        }
        layer.close(index);
    }, function () {
        return true;
    });
};
/**
 * 创建一个信息弹窗。
 * @param {String} content  显示内容
 * @param {String} type 类型：1，2，3，4，5，6或者字符串'warning','ok','success','err','error','question','lock','longface','info','nopermission'
 * @param {Function} callback 回调函数
 */
$.layerMsg = function (content, type, callback) {
    if (type !== undefined) {
        var icon = "";
        if (type == true) {
            icon = 1;
        } else {
            icon = 2;
        }

        top.layer.msg(content, {icon: icon, time: 2000}, function () {
            if (callback && $.isFunction(callback)) {
                callback();
            }
        });
    } else {
        top.layer.msg(content, function () {
            if (callback && $.isFunction(callback)) {
                callback();
            }
        });
    }
};

$.procAjaxMsg = function (json, funcSuc, funcErr) {
    if (!json.sucess) {
        return;
    }
    var state = json.sucess;
    if (json.sucess) {
        funcSuc(json);
    } else {
        funcSuc(json);
    }
    switch (state) {

        case "Success":
            if (funcSuc) {
                funcSuc(json);
            }
            break;
        case "Error":
            if (funcErr) {
                funcErr(json);
            }
            break;
        case "nologin":
            //是否登录
            $.alertMsg(json.Message, '系统提示', function () {
                if (window != top) {
                    top.location.href = json.Data;
                }
                else {
                    window.location.href = json.Data;
                }
            }, state);
            break;
        case "nopermission":
            //是否有权
            $.alertMsg(json.Message, '系统提示', null, state);
            break;
    }
};

$.validateUrl = function (url, funcSuc, funcErr, type) {
    $.ajax({
        type: type,
        url: url,
        success: function (data) {
            if (data.Msg) {
                funcErr(data);
            } else {
                funcSuc();
            }
        }
    });
};

$.alertMsg = function (msg, title, funcSuc, icon) {
    if (title === '' || title === undefined) title = '提示';
    if (layer) {
        var type = 1;
        if (icon) {
            switch (icon) {
                case 'ok':
                case 'success':
                    type = 1;
                    break;
                case 'err':
                case 'error':
                    type = 2;
                    break;
                case 'question':
                    type = 3;
                    break;
                case 'lock':
                case 'nopermission':
                    type = 4;
                    break;
                case 'longface':
                    type = 5;
                    break;
                case 'smile':
                    type = 6;
                    break;
                default:
                    type = 1;
            }
        }
        layer.open({
            title: title
            , content: msg
            , icon: type
            , yes: function (index) {
                layer.close(index);
                if (typeof (funcSuc) === 'function') {
                    funcSuc();
                }
            }
        });
    }
    else {
        alert(title + "\r\n " + msg);
        if (funcSuc) {
            funcSuc();
        }
    }
};

/**
 * 控制授权按钮
 * @param {callback} 回调函数
 */
$.fn.authorizeButton = function (callback) {
    var url = location.pathname + location.search;
    var that = this;
    com.ajax({
        url: '/Admin/SysRoleMenu/GetAuthorizeButton',
        data: {url: url},
        success: function (childModules) {
            if (childModules.length > 0) {
                var $toolbar = $(that);
                var _buttons = '';
                $.each(childModules, function (index, item) {
                    _buttons += "<button id='" + item.EnCode + "' type=\"button\" class=\"btn btn-default\">";
                    _buttons += "   <span class='" + item.IconCls + "' aria-hidden='true'></span> " + item.Name + "";
                    _buttons += "</button>";
                });
                $toolbar.html(_buttons);
            }
            if (typeof callback === 'function') {
                callback();
            }
        }
    });

};

/**
 * 获取URL指定参数值。
 * @param {String} name 参数name值
 * @returns 获取URL指定参数值。
 */
$.getQueryString = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return unescape(r[2]);
    return null;
};

/**
 * 序列化和反序列化表单字段。
 * @param {Object} formdata this is form表单数据
 * @param {Function} callback 回调函数
 */
$.fn.formSerialize = function (formdata, callback) {
    var $form = $(this);
    if (formdata) {
        for (var key in formdata) {
            var $field = $form.find("[name=" + key + "]");
            if ($field.length === 0) {
                continue;
            }
            var value = $.trim(formdata[key]);
            var type = $field.attr('type');
            if ($field.hasClass('select2')) {
                type = "select2";
            }
            switch (type) {
                case "checkbox":
                    value === "true" ? $field.attr("checked", 'checked') : $field.removeAttr("checked");
                    break;
                case "select2":
                    if (!$field[0].multiple) {
                        $field.select2().val(value).trigger("change");
                    } else {
                        var values = value.split(',');
                        $field.select2().val(values).trigger("change");
                    }
                    break;
                case "radio":
                    $field.each(function (index, $item) {
                        if ($item.value === value) {
                            $item.checked = true;
                        }
                    });
                    break;
                default:
                    $field.val(value);
                    break;
            }
        }
        // 特殊的表单字段可以在回调函数中手动赋值。
        if (callback && $.isFunction(callback)) {
            callback(formdate);
        }
        return false;
    }
    var postdata = {};
    $form.find('input,select,textarea').each(function (r) {
        var $this = $(this);
        var name = $this.attr('name');
        var type = $this.attr('type');
        switch (type) {
            case "checkbox":
                postdata[name] = $this.is(":checked");
                break;
            default:
                var value = $this.val();
                //if (!$.getQueryString("id")) {
                //    value = value.replace(/&nbsp;/g, '');
                //}
                postdata[name] = value;
                break;
        }
    });
    return postdata;
};


Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
};

window.com = {};
/**
 * 格式化字符串
 * 用法:
 .format_str("{0}-{1}","a","b");
 */
com.format_str = function () {
    for (var i = 1; i < arguments.length; i++) {
        var exp = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        arguments[0] = arguments[0].replace(exp, arguments[i]);
    }
    return arguments[0];
};


//发送ajax请求
com.ajax = function (options) {
    var defaults = {
        url: "",
        data: {},
        type: "post",
        async: true,
        success: null,
        close: true,
        showMsg: false,
        showLoading: true
    };
    options = $.extend(defaults, options);


    var index = 0;
    if (options.showLoading === true) {
        index = layer.load(0, {shade: false});
    }

    $.ajax({
        url: options.url,
        data: options.data,
        type: options.type,
        async: options.async,
        dataType: "json",
        success: function (data) {
            if (options.showLoading === true) {
                layer.close(index);
            }

            if (options.showMsg) {
                if (data.success) {
                    //默认操作成功后，关闭iframe框
                    if (options.close) {
                        $.layerClose();
                    }
                    //然后弹出成功操作的提示
                    $.layerMsg(data.errMsg,
                        data.success,
                        function () {
                            //回调成功后的刷新表格操作
                            if (options.success && $.isFunction(options.success)) {
                                options.success(data);
                            }
                        });
                } else {
                    console.log(data);
                    //操作失败时
                    $.layerMsg(data.errMsg, data.success);
                }
            } else {
                options.success && options.success(data);
            }
        },
        error: function (xhr, status, error) {
            layer.close(index);
            console.log(status);
            console.log(error);
            var msg = xhr.responseText;
            console.log(xhr)
            var errMsg = top.layer.open({
                title: '错误提示',
                area: ['500px', '400px'],
                content: msg
            });
        }
    });
};

com.ignoreEle = function (dom) {
    dom.find('input').addClass("layui-disabled").attr("disabled", "");
    dom.find('textarea').addClass('layui-disabled').attr("disabled", "");
    dom.find('select').addClass('layui-disabled').prop("disabled", true);
    dom.find('button').remove();
    dom.find('a').removeAttr('href').removeAttr('onclick')
};

com.ispc = function () {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
};


;(function ($, com) {
    $.extend(com, {
        isEmail: function (value) {
            if (value == "" || value == undefined) return false;
            //对电子邮箱的验证
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!myreg.test(value)) {
                return false;
            }
            return true;
        },
        guid: function () {
            return Math.random().toString(36).substr(2);
        },
        filter: function (url) {
            layui.table.reload('dgGrid',
                {
                    url: url,
                    where: {
                        'search': $('#search-name').val()
                    }
                });
        },
        bindFilter: function (url) {

            $('#btn-refresh').on('click', function () {
                com.filter(url);
            });

            $('#btn-clear').on('click', function () {
                $('#search-name').val('');
                com.filter(url);
            });

            $('#btn-search').on('click', function () {
                com.filter(url);
            });
        },
    });

})(window.jQuery, window.com);

com.getInfoById = function (url, id, callback, params) {
    com.ajax({
        type: 'get',
        url: url + '/' + id,
        data: (params != "" && params != undefined) ? params : {},
        showLoading: false,
        close: false,
        success: function (data) {
            $('#v-app').formSerialize(data);
            callback && callback(data);
        }
    });
}
