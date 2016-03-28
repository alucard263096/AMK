<?php

/**
 * Created by Mixiu.
 * Date: 2015/12/2
 * Time: 16:06
 */
class OpenSdkConfig
{

    /**
     * APP应用的APPid
     * @var string
     */
    const APPID = '0f447dda99da4c6288524100d4d19382';

    /**
     * APP应用的appSecret
     * @var string
     */
    const KEYSECRET = 'f6fec505f325419a8b6d748d4d15ac07';

    /**
     * 应用接入来源
     * @var string
     */
    const SOURCENO = '1151000000201308';

    /**
     * 请求类型
     * http/https
     * @var string
     */

    const PROTOCOL = 'http';

    /**
     * host
     */
    const API_HOST = 'demo-openapi.wjjr.cc';

    /**
     * ACCESS_TOKEN 操作授权
     */
    const ACCESS_TOKEN_SCOPE = 'ACCESSTOKEN';

    /**
     * 获取ACCESS_TOKEN的Url
     */
    const ACCESS_TOKEN_URL = '/auth/service_access_token';

    const WIDGET_PAGE_URL = '/service/widget_page';

    /**
     * 收银台地址Url
     */
    const CASH_DESK_URL = '/service/cash_desk';


    /**
     * 创建会员Url
     */
    const CREATE_USER_URL = '/service/createUser';

    /**
     * 更新会员Url
     */
    const UPDATE_USER_URL = '/service/updateUser';

    /**
     * 商家二级结算--普通结算Url
     */
    const CHILD_MERCHANT_COMMON_SETTLE_URL = '/service/childMerchantCommonSettle';

    /**
     * 商家二级结算--订单结算
     */
    const CHILD_MERCHANT_ORDER_SETTLE_URL = '/service/childMerchantOrderSettle';

}