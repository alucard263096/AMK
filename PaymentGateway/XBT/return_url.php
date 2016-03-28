<html>
<head>
</head>
<body>
<script>
function openApp() {  
  
        if (/android/i.test(navigator.userAgent)) {  
             var isrefresh = getUrlParam('refresh'); // 获得refresh参数  
             if(isrefresh == 1) {  
                 return  
             }  
             window.location.href = 'myapp://amklovebaby/success?order_no=<?php echo $_REQUEST["outTradeNo"]; ?>';  
             window.setTimeout(function () {  
                     window.location.href += '&refresh=1' // 附加一个特殊参数，用来标识这次刷新不要再调用myapp:// 了  
             }, 500);  
         }  
  
}
openApp();
</script>
</body>
</html>