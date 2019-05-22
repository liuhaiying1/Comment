var bodyTab;

$(function () {
    //config的设置是全局的
    layui.config({
        base: '/plugins/layui-extend/' //这是存放拓展模块的根目录
    }).use(['bodyTab', 'form', 'colorpicker'], function () {

        var colorpicker = layui.colorpicker;
        var element = layui.element;
        var form = layui.form;
        var layer = layui.layer;
        bodyTab = layui.bodyTab;

        bodyTab.set({
            openTabNum: "50",       //最大可打开窗口数量
            tabFilter: "bodyTab",  //layui的element模块事件过滤器
            ajaxSettings: {         //ajax参数，与jquery.ajax一致
                    url: "/services/data/menu.json",      //menu.json作为bodyTab该组件的参数
                    type: 'get'
            }
        });

        skins();
        //更换皮肤
        $(".changeSkin").click(function () {
            layer.open({
                title: "更换皮肤",
                area: ["450px", "210px"],
                type: "1",
                content: '<div class="skins_box">' +
                    '<form class="layui-form">' +
                    '<div class="layui-form-item">' +
                    '<input type="radio" name="skin" value="默认" title="默认" lay-filter="default" checked="">' +
                    '<input type="radio" name="skin" value="橙色" title="橙色" lay-filter="orange">' +
                    '<input type="radio" name="skin" value="蓝色" title="蓝色" lay-filter="blue">' +
                    '<input type="radio" name="skin" value="自定义" title="自定义" lay-filter="custom">' +
                    '<div class="skinCustom">' +
                    '<input type="text" class="layui-input topColor" name="topSkin" placeholder="顶部颜色" /><span id="topSkin-form" ></span>' +
                    '<input type="text" class="layui-input leftColor" name="leftSkin" placeholder="左侧颜色" /><span id="leftSkin-form" ></span>' +
                    '<input type="text" class="layui-input menuColor layui-hide" name="btnSkin" placeholder="顶部菜单按钮" /><span id="btnSkin-form" ></span>' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-form-item skinBtn">' +
                    '<a href="javascript:void(0)" class="layui-btn layui-btn-small layui-btn-normal" lay-submit="" lay-filter="changeSkin">确定更换</a>' +
                    '<a href="javascript:void(0)" class="layui-btn layui-btn-small layui-btn-primary" lay-submit="" lay-filter="noChangeSkin">我再想想</a>' +
                    '</div>' +
                    '</form>' +
                    '</div>',
                success: function (index, layero) {

                    colorpicker.render({
                        elem: '#topSkin-form'
                        , color: '#000'
                        , done: function (color) {
                            $('.topColor,.menuColor').val(color);
                            $('.topColor,.menuColor').blur();
                        }
                        , alpha: true
                        , predefine: true
                    });

                    colorpicker.render({
                        elem: '#leftSkin-form'
                        , color: '#000'
                        , done: function (color) {
                            $('.leftColor').val(color);
                            $('.leftColor').blur();
                        }
                        , alpha: true
                        , predefine: true
                    });


                    var skin = window.localStorage.getItem("skin");
                    if (window.localStorage.getItem("skinValue")) {
                        $(".skins_box input[value=" + window.localStorage.getItem("skinValue") + "]").attr("checked", "checked");
                    };
                    if ($(".skins_box input[value=自定义]").attr("checked")) {
                        $(".skinCustom").css("visibility", "inherit");
                        $(".topColor").val(skin.split(',')[0]);
                        $(".leftColor").val(skin.split(',')[1]);
                        $(".menuColor").val(skin.split(',')[2]);
                    };
                    form.render();
                    $(".skins_box").removeClass("layui-hide");
                    $(".skins_box .layui-form-radio").on("click", function () {
                        var skinColor;
                        if ($(this).find("div").text() == "橙色") {
                            skinColor = "orange";
                        } else if ($(this).find("div").text() == "蓝色") {
                            skinColor = "blue";
                        } else if ($(this).find("div").text() == "默认") {
                            skinColor = "";
                        }
                        if ($(this).find("div").text() != "自定义") {
                            $(".topColor,.leftColor,.menuColor").val('');
                            $("body").removeAttr("class").addClass("main_body " + skinColor + "");
                            $(".skinCustom").removeAttr("style");
                            $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                        } else {
                            $(".skinCustom").css("visibility", "inherit");
                        }
                    })
                    var skinStr, skinColor;
                    $(".topColor").focus(function () {
                        layer.open({
                            type: 4,
                            content: ['请输入[十六进制颜色码]或者颜色的[英文单词]', this]
                            , shade: 0
                            , closeBtn: 0
                            , time: 5000
                        });
                    }).blur(function () {
                        layer.closeAll('tips');
                        $(".layui-layout-admin .layui-header").css("cssText", "background-color:" + $(this).val() + " !important");
                        $(".hideMenu").css("cssText", "background-color:" + $(this).val() + " !important");
                    });
                    $(".leftColor").focus(function () {
                        layer.open({
                            type: 4,
                            content: ['请输入[十六进制颜色码]或者颜色的[英文单词]', this]
                            , shade: 0
                            , closeBtn: 0
                            , time: 5000
                        });
                    }).blur(function () {
                        layer.closeAll('tips');
                        $(".layui-bg-black").css("cssText", "background-color:" + $(this).val() + " !important");
                    });

                    form.on("submit(changeSkin)", function (data) {
                        if (data.field.skin != "自定义") {
                            if (data.field.skin == "橙色") {
                                skinColor = "orange";
                            } else if (data.field.skin == "蓝色") {
                                skinColor = "blue";
                            } else if (data.field.skin == "默认") {
                                skinColor = "";
                            }
                            window.localStorage.setItem("skin", skinColor);
                        } else {
                            skinStr = $(".topColor").val() + ',' + $(".leftColor").val() + ',' + $(".menuColor").val();
                            window.localStorage.setItem("skin", skinStr);
                            $("body").removeAttr("class").addClass("main_body");
                        }
                        window.localStorage.setItem("skinValue", data.field.skin);
                        layer.closeAll("page");
                    });
                    form.on("submit(noChangeSkin)", function () {
                        $("body").removeAttr("class").addClass("main_body " + window.localStorage.getItem("skin") + "");
                        $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                        skins();
                        layer.closeAll("page");
                    });
                },
                cancel: function () {
                    $("body").removeAttr("class").addClass("main_body " + window.localStorage.getItem("skin") + "");
                    $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                    skins();
                }
            })
        });

        //退出登陆
        $(".signOut").click(function (event) {
            event.preventDefault();
            layui.layer.load(2, { shade: [0.9, '#FFF'], content: '<div style="width:100px;line-height:32px;text-align:right;">正在退出...</div>' });
            top.sessionStorage.clear();
            //延迟一下使得用户能直观的感受到系统做了什么不得了的事（一本正经）
            setTimeout("top.location.replace('/user/logout.do')", "100");
        });

        //隐藏左侧导航
        $(".hideMenu").click(function () {
            $(".layui-layout-admin").toggleClass("showMenu");
            //渲染顶部窗口
            bodyTab.tabMove();
            //layer.msg("如果页面显示不正常可在右方的页面操作里点击刷新当前页哦~", { time: 900 });
        });


        //手机设备的简单适配
        $('.site-tree-mobile').on('click', function () {
            $('body').addClass('site-mobile');
        });
        $('.site-mobile-shade').on('click', function () {
            $('body').removeClass('site-mobile');
        });

        //刷新当前
        $(".refresh").on("click", function () {
            //获取当前打开的元素
            var showElement = $(".clildFrame .layui-tab-item.layui-show").find("iframe")[0];
            //手动设置一下src刷新
            showElement.src = showElement.src;
            //从缓存取页面刷新
            showElement.contentWindow.location.reload(false);
            //从服务器重新取页面刷新
            showElement.contentWindow.location.reload(true);
            //跳转式刷新
            showElement.contentWindow.location.href = showElement.src;
        });

        //监听切换tab设置当前选中tab
        $(document).on("click", ".top_tab li", function () {
            bodyTab.monitorSwitchTab(this);
        });

        //删除tab，tab关闭 监听
            $(document).on("click", ".top_tab li i.layui-tab-close", function () {
                bodyTab.monitorCloseTab(this);
        });

        // 添加新窗口
        $(document).on("click", ".layui-nav .layui-nav-item a", function (event) {
            event.preventDefault();
            //如果不存在子级
            if ($(this).siblings().length == 0) {
                addTab($(this));
                $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
            }
            $(this).parent("li").siblings().removeClass("layui-nav-itemed");
        });

        //双击时刷新当前窗口
        $(document).on("dblclick", ".layui-nav .layui-nav-item a", function (event) {
            event.preventDefault();
            //如果不存在子级
            if ($(this).siblings().length == 0) {
                $(".refresh")[0].click();
            }
        });

        //刷新后还原打开的窗口
        if (window.sessionStorage.getItem("menu")) {
            bodyTab.refreshRestoreTab();
        }

        //判断是否是刷新来的tab，是的话刷新当前页面
        element.on('tab(bodyTab)', function (data) {
            var notNewTabEle = $(this).find("[layuiTabTypeOpen='notNewTab']");
            if (notNewTabEle[0] != undefined) {
                notNewTabEle.removeAttr('layuiTabTypeOpen');
                $(".refresh")[0].click();
            }

            //切换tab时选中左侧菜单
            bodyTab.selectedMenu($(this).find('cite').html());
        });

        //关闭其他
        $(".closePageOther").on("click", function () {
            bodyTab.CloseOtherTab();
        });
        //关闭全部
        $(".closePageAll").on("click", function () {
            bodyTab.CloseAllTab();
        });

    });//layui.use的结尾

});//jquery的结尾

//更换皮肤
function skins() {
    var skin = window.localStorage.getItem("skin");
    if (skin) {  //如果更换过皮肤
        if (window.localStorage.getItem("skinValue") != "自定义") {
            $("body").addClass(window.localStorage.getItem("skin"));
        } else {
            $(".layui-layout-admin .layui-header").css("cssText", "background-color:" + skin.split(',')[0] + " !important");
            $(".layui-bg-black").css("cssText", "background-color:" + skin.split(',')[1] + " !important");
            $(".hideMenu").css("cssText", "background-color:" + skin.split(',')[2] + " !important");
        }
    }
}

//打开新窗口
function addTab(_this) {
    bodyTab.tabAdd(_this);
}

/**
 * 打开新tab
 * @param {string} url 要打开的地址
 * @param {string} title 显示的标题
 * @param {string} icon 显示的图标
 */
function addTabByUrlTitleIcon(url, title, icon) {
    var html = '<i data-url="' + url + '" ><cite>' + title + '</cite><i class="layui-icon" data-icon="' + icon + '"></i></i>';
    bodyTab.tabAdd($(html));
}