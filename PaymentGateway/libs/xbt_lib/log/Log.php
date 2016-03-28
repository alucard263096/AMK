<?php

/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2015/12/2
 * Time: 15:57
 */
class XBTLog
{

    public static function write($word) {
        $file = dirname(__FILE__).'/log.txt';
        $fp = fopen($file,"a");
        flock($fp, LOCK_EX) ;
        fwrite($fp,"执行日期：".date("Y-m-d H:i:s")."\n".$word."\n\n");
        flock($fp, LOCK_UN);
        fclose($fp);
    }
}