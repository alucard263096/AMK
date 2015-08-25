myjs_aftersave = function (data) {
    if (data == "login_id_duplicate") {
        warningDialog("登录名已经被使用");
        return false;
    }
    return true;
};