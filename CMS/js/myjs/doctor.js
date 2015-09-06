
myjs_detailPageLoad = function () {

    var obj = $("#content_worktime").parent().parent().find("label");
    obj.html(obj.html() + "&nbsp;&nbsp;<i class='glyphicon glyphicon-question-sign' ></i>");
    obj.click(function () {
        worktimesetting_tips();
    });
    
};

function worktimesetting_tips() {
    var str = "工作表设定样例格式如下：<br /><br />";
    str += "<div style='font-size:70%'>#1-> 9:00-12:00,13:00-17:00    //设定为周一上班时间9:00到12:00，下午13:00到17:00分<br/>";
    str += "#2->9:00-10:00,11:00-12:00,13:00-17:00    //设定为周二上班时间9:00到10:00,11:00到12:00，下午13:00到17:00分</div><br/><br/><br/><br/>";
    str += "工作表设定格式为：#{周几，输入数字为1-7}->{开始时间}-{结束时间},{开始时间}-{结束时间}  //请必须遵守时间格式，时间段以半小时为单位";

    infoDialog(str);
}