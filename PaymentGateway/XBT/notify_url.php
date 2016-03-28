<?php

require '../include/common.inc.php';

require ROOT."/libs/xbt_lib/SDK.php";
$sdk = new SDK(OpenSdkConfig::APPID, OpenSdkConfig::KEYSECRET, OpenSdkConfig::SOURCENO);
$mainParams= array(
    'timestamp' => time()*1000,
    'channel' => 'PC',
    'ipAddress' => '',
    'sessionId' => '',
    'deviceFinger' => '',
    'deviceToken' => '',
    'longitude' => '',
    'latitude' => ''
);
?>
<!DOCTYPE html>
<html>
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <title>接口文档测试</title>
    <style>
        input {width: 500px;}
    </style>
</head>
<body>

<h1 id="bind_card">获取绑卡页面URL</h1>
<form method="post" action="index.php#bind_card">
    <input type="hidden" name="apitest" value="bind_card">
    <p>
        <label>urlKey(用户名-必填)</label>
        <input type="text" name="ext[urlKey]" value="bind_card" readonly="readonly">
    </p>
    <p>
        <label>loginName(用户名-必填)</label>
        <input type="text" name="ext[loginName]" required>
    </p>
    <p>
        <label>mobile(手机号-如果有就填写)</label>
        <input type="text" name="ext[mobile]">
    </p>
    <p>
        <label>outCustomerId(第三方会员id-必填)</label>
        <input type="text" name="ext[outCustomerId]" required value="11111">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
    返回数据：
        <pre>
        <?php
        if(isset($_POST['apitest']) && $_POST['apitest'] == 'bind_card') {
            echo  htmlspecialchars($sdk->bindCar($mainParams, $_POST['ext']));
        }
        ?>
        </pre>
    </p>
</form>
<h1 id="cash_desk">获取收银台Url</h1>
<form method="post" action="index.php#cash_desk">
    <input type="hidden" name="apitest" value="cash_desk">
    <p>
        <label>urlKey</label>
        <input type="text" name="ext[urlKey]" value="cash_desk" readonly="readonly">
    </p>
    <p>
        <label>loginName(用户名-必填)</label>
        <input type="text" name="ext[loginName]" required>
    </p>
    <p>
        <label>mobile(手机号-如果有就填写)</label>
        <input type="text" name="ext[mobile]">
    </p>
    <p>
        <label>outTradeNo(外部订单号-必填)</label>
        <input type="text" name="ext[outTradeNo]" required value="<?=time()?>">
    </p>
    <p>
        <label>merchantNo(商家号-必填)</label>
        <input type="text" name="ext[merchantNo]" required value="mer000020150000103017115100151">
    </p>
    <p>
        <label>childMerchantNo(子商家号-必填)</label>
        <input type="text" name="ext[childMerchantNo]" required value="mer000020150000103017115100151">
    </p>
    <p>
        <label>amount(金额单位分-必填)</label>
        <input type="text" name="ext[amount]" required value="10000">
    </p>
    <p>
        <label>currency(币种-必填)</label>
        <input type="text" name="ext[currency]" value="CNY" readonly="readonly">
    </p>
    <p>
        <label>orderBeginTime(外部订单开始时间-必填)</label>
        <input type="text" name="ext[orderBeginTime]" required value="<?=date('Y-m-d H:i:s')?>">
    </p>
    <p>
        <label>orderExpireTime(外部订单过期时间-选填)</label>
        <input type="text" name="ext[orderExpireTime]" value="<?=date('Y-m-d H:i:s',time()+3600)?>">
    </p>
    <p>
        <label>orderName(订单名称-选填)</label>
        <input type="text" name="ext[orderName]" value="测试订单">
    </p>
    <p>
        <label>orderDesc(订单描述-选填)</label>
        <input type="text" name="ext[orderDesc]" value="测试订单">
    </p>
    <p>
        <label>orderUrl(外部订单详情Url-选填)</label>
        <input type="text" name="ext[orderUrl]">
    </p>
    <p>
        <label>orderNotifyUrl(外部订单支付结果通知URL-必填)</label>
        <input type="text" name="ext[orderNotifyUrl]" required value="http://openplatform-luochun-sit:8184/pay/merchant_pay_result">
    </p>
    <p>
        <label>orderFrontNotifyUrl(外部订单前端跳转通知URL-必填)</label>
        <input type="text" name="ext[orderFrontNotifyUrl]" required value="http://www.wjjr.cc/">
    </p>
    <p>
        <label>productNo(产品编号/商品编号-必填)</label>
        <input type="text" name="ext[productNo]" required value="1000">
    </p>
    <p>
        <label>productName(产品名称-必填)</label>
        <input type="text" name="ext[productName]" required value="测试产品">
    </p>
    <p>
        <label>ipAddress(ip地址-选填)</label>
        <input type="text" name="ext[ipAddress]">
    </p>
    <p>
        <label>deviceNumber(设备号-选填)</label>
        <input type="text" name="ext[deviceNumber]">
    </p>
    <p>
        <label>paySource(支付来源-必填)</label>
        <select name="ext[paySource]">
            <option value="ANDROID">ANDROID</option>
            <option value="IOS">IOS</option>
            <option value="PC">PC</option>
            <option value="WIN_MOBILE">WIN_MOBILE</option>
        </select>
    </p>
    <p>
        <label>scenesCode(场景码-选填)</label>
        <input type="text" name="ext[scenesCode]" value="020200002">
    </p>
    <p>
        <label>extendParams(扩展参数-选填)</label>
        <input type="text" name="ext[extendParams]" value="123456">
    </p>
    <p>
        <label>outCustomerId(外部会员号-必填)</label>
        <input type="text" name="ext[outCustomerId]" required value="11111">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php if(isset($_POST['apitest']) && $_POST['apitest'] == 'cash_desk'){
            echo htmlspecialchars($sdk->cashDesk($mainParams, $_POST['ext']));
        }?>
        </pre>
    </p>
</form>


<h1 id="my_assets">获取我的资产</h1>
<form method="post" action="index.php#my_assets">
    <input type="hidden" name="apitest" value="my_assets">
    <p>
        <label>loginName(用户名-必填)</label>
        <input type="text" name="ext[urlKey]" value="my_assets" readonly="readonly">
    </p>
    <p>
        <label>loginName(用户名-必填)</label>
        <input type="text" name="ext[loginName]" required>
    </p>
    <p>
        <label>mobile(手机号-如果有就填写)</label>
        <input type="text" name="ext[mobile]">
    </p>
    <p>
        <label>outCustomerId(外部会员号-必填)</label>
        <input type="text" name="ext[outCustomerId]" required value="11111">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php
        if(isset($_POST['apitest']) && $_POST['apitest'] == 'my_assets') {
            echo htmlspecialchars($sdk->myAssets($mainParams, $_POST['ext']));
        }
        ?>
        </pre>
    </p>
</form>

<h1 id="create_user">创建会员</h1>
<form method="post" action="index.php#create_user">
    <input type="hidden" name="apitest" value="create_user">
    <p>
        <label>loginName(用户名-必填)</label>
        <input type="text" name="ext[loginName]" required>
    </p>
    <p>
        <label>mobile(手机号-如果有就填写)</label>
        <input type="text" name="ext[mobile]">
    </p>
    <p>
        <label>outCustomerId(外部会员号-必填)</label>
        <input type="text" name="ext[outCustomerId]" required value="11111">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php if(isset($_POST['apitest']) && $_POST['apitest'] == 'create_user'){
            echo htmlspecialchars($sdk->createUser($mainParams, $_POST['ext']));
        }?>
        </pre>
    </p>
</form>

<h1 id="updateUser">更新会员</h1>
<form method="post" action="index.php#updateUser">
    <input type="hidden" name="apitest" value="updateUser">
    <p>
        <label>oldLoginName(用户名-必填)</label>
        <input type="text" name="ext[oldLoginName]" required>
    </p>
    <p>
        <label>newLoginName(手机号-如果有就填写)</label>
        <input type="text" name="ext[newLoginName]">
    </p>
    <p>
        <label>mobile(联系人手机号-如果有就填写)</label>
        <input type="text" name="ext[mobile]">
    </p>
    <p>
        <label>outCustomerId(外部会员号-必填)</label>
        <input type="text" name="ext[outCustomerId]" required value="11111">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php if(isset($_POST['apitest']) && $_POST['apitest'] == 'updateUser'){
            echo htmlspecialchars($sdk->updateUser($mainParams, $_POST['ext']));
        }?>
        </pre>
    </p>
</form>
<h1 id="childMerchantCommonSettle">商家二级结算-普通结算</h1>
<form method="post" action="index.php#childMerchantCommonSettle">
    <input type="hidden" name="apitest" value="childMerchantCommonSettle">
    <p>
        <label>settleAmount(结算金额-必填)</label>
        <input type="text" name="ext[settleAmount]" required>
    </p>
    <p>
        <label>retainAmount(截留资金-选填)</label>
        <input type="text" name="ext[retainAmount]">
    </p>
    <p>
        <label>currency(币种：CNY)</label>
        <input type="text" name="ext[currency]" value="CNY" readonly="readonly">
    </p>
    <p>
        <label>merchantSettleMode(结算方式1/2)</label>
        <input type="text" name="ext[merchantSettleMode]" value="1" required>
    </p>
    <p>
        <label>merchantNo(一级商户号)</label>
        <input type="text" name="ext[merchantNo]" required>
    </p>
    <p>
        <label>outChildMerchantNo(一级商户号/一级商户号的外部二级商户号)</label>
        <input type="text" name="ext[outChildMerchantNo]" required>
    </p>
    <p>
        <label>remark(结算备注)</label>
        <input type="text" name="ext[remark]">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php if(isset($_POST['apitest']) && $_POST['apitest'] == 'childMerchantCommonSettle'){
            echo htmlspecialchars($sdk->childMerchantCommonSettle($mainParams, $_POST['ext']));
        }?>
        </pre>
    </p>
</form>

<h1 id="childMerchantOrderSettle">商家二级结算-订单结算</h1>
<form method="post" action="index.php#childMerchantOrderSettle">
    <input type="hidden" name="apitest" value="childMerchantOrderSettle">
    <p>
        <label>outTradeNoList(外部订单号集合array转成json-必填):["14012124545","14012221212"]</label>
        <input type="text" name="ext[outTradeNoList]" required>
    </p>
    <p>
        <label>settleAmount(结算金额-必填)</label>
        <input type="text" name="ext[settleAmount]" required>
    </p>
    <p>
        <label>retainAmount(截留资金-选填)</label>
        <input type="text" name="ext[retainAmount]">
    </p>
    <p>
        <label>currency(币种：CNY)</label>
        <input type="text" name="ext[currency]" value="CNY" readonly="readonly">
    </p>
    <p>
        <label>merchantSettleMode(结算方式1/2)</label>
        <input type="text" name="ext[merchantSettleMode]" value="1" required>
    </p>
    <p>
        <label>merchantNo(一级商户号)</label>
        <input type="text" name="ext[merchantNo]" required>
    </p>
    <p>
        <label>outChildMerchantNo(一级商户号/一级商户号的外部二级商户号)</label>
        <input type="text" name="ext[outChildMerchantNo]" required>
    </p>
    <p>
        <label>remark(结算备注)</label>
        <input type="text" name="ext[remark]">
    </p>
    <p>
        <button type="submit">提交</button>
    </p>
    <p>
        返回数据：
        <pre>
        <?php if(isset($_POST['apitest']) && $_POST['apitest'] == 'childMerchantOrderSettle'){
            echo htmlspecialchars($sdk->childMerchantOrderSettle($mainParams, $_POST['ext']));
        }?>
        </pre>
    </p>
</form>
</body>
</html>
