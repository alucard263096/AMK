
myjs_afterResultLoad = function () {
    var chart_type = $("#condition_chart_type").val();
    if (chart_type == "P"||chart_type == "C") {
        DiaplayChart(chart_type);
    }  else {
        $("#chart_result").hide();
    }
};
myjs_listPageLoad = function () {
    $("#condition_dimension").val("M");
    $("#searchResult").before("<div id='chart_result'></div>");
    $("#chart_result").hide();
};

function DiaplayChart(chart_type) {
    var categories = new Array();
    var totalincome = new Array();
    var CCtotalincome = new Array();
    var VCtotalincome = new Array();
    var totalordercount = new Array();
    var CCtotalordercount = new Array();
    var VCtotalordercount = new Array();
    q=0;
    $("#searchResult tr").each(function (index) {
        var dateranger =$.trim( $(this).find(".result_dateranger").first().text());
        if (dateranger != "") {
            categories[q] = dateranger;
            var income = parseInt($.trim($(this).find(".result_totalincome").first().text()));
            totalincome[q] = income;
            var ordercount = parseInt($.trim($(this).find(".result_totalordercount").first().text()));
            totalordercount[q] = ordercount;
            var order_list = $.trim($(this).find(".result_order_list").first().text());
            order_list_arr = order_list.split("元");
            
            var VCIncome=0;
            var VCOrderCount = 0;
            var CCIncome = 0;
            var CCOrderCount = 0;
            for (i = 0; i < order_list_arr.length; i++) {
                order_arr=order_list_arr[i].split("-");
                if (order_arr.length == 2) {
                    var act = order_arr[0].substr(0, 2);
                    order_income_str = order_arr[1];
                    order_income = parseInt(order_income_str);
                    if (act == "VC") {
                        VCIncome += order_income;
                        VCOrderCount++;
                    } else if (act == "CC") {
                        CCIncome += order_income;
                        CCOrderCount++;
                    }
                }
            }
            CCtotalincome[q] = CCIncome;
            VCtotalincome[q] = VCIncome;
            CCtotalordercount[q] = CCOrderCount;
            VCtotalordercount[q] = VCOrderCount;

            q++;
        }
    });
    var titletext = "";
    var ytext = "";
    var series = new Array();
    if (chart_type == "P") {
        titletext = "收入趋势图";
        ytext = "元";
        series = [{
            name: '总收入',
            data: totalincome
        }, {
            name: '视频会诊收入',
            data: VCtotalincome
        }, {
            name: '图文咨询收入',
            data: CCtotalincome
        }];
    } else if (chart_type == "C") {
        titletext = "订单数量趋势图";
        ytext = "个";
        series = [{
            name: '总订单数量',
            data: totalordercount
        }, {
            name: '视频会诊订单数量',
            data: VCtotalordercount
        }, {
            name: '图文咨询订单数量',
            data: CCtotalordercount
        }];
    }


    $('#chart_result').show();
    $('#chart_result').highcharts({
        chart: {
            type: 'area'
        },
        title: {
            text: titletext
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: categories,
            tickmarkPlacement: 'on',
            title: {
                enabled: false
            }
        },
        yAxis: {
            title: {
                text: ytext
            },
            labels: {
                formatter: function() {
                    return this.value ;
                }
            }
        },
        tooltip: {
            shared: true,
            valueSuffix: ytext
        },
        plotOptions: {
            area: {
                dataLabels: {
                    enabled: true
                },
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    lineWidth: 1,
                    lineColor: '#666666'
                }
            }
        },
        series: series
    });
}
function DiaplayChartForOrderCount() {

}
