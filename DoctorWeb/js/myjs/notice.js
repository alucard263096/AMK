
myjs_afterResultLoad = function () {
    $("#searchResult a").attr("href", "#").click(function () {
        var notice_id = $(this).parent().parent().attr("oid");
        var rn = Date.parse(new Date());;
        window.open("notice.php?action=edit&rn=" + rn + "&id=" + notice_id, 'newwindow', 'width=900,height=500,scrollbars ');
        $(this).parent().parent().find("td:last").find("a").text("是");
    });
};
myjs_listPageLoad = function () {
    $("#condition_haveread").val("N");
};
var myjs_detailPageLoad = function () {
    $("#mainmenu").hide();
    $("#navbar").hide();
    $("#submenu").hide();
    $("#btnSave").hide();
    $("#btnReturnToList").hide();
};

var setSelectizeValueById = function (id, value) {
    var $select = $('#' + id).selectize();
    $select[0].selectize.setValue(value);
};