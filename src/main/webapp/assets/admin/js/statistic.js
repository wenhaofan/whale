
$(function(){
 
	while(!echarts){
		alert("加载未完成");
	}
 
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('bar'));

	$.ajax({
	url:"/admin/api/statistic/hotArticle/10",
	success:function(data){
		// 指定图表的配置项和数据
		var option = {
		    title: {
		        text: '热门文章'
		    },
		    tooltip: {},   toolbox: {
		        feature: {
		            dataView: {},
		            saveAsImage: {
		                pixelRatio: 2
		            },
		            restore: {}
		        }
		    },
		    legend: {
		        data:['阅读量']
		    },
		    grid:{
		    	bottom:"30%"
		    },
		    color:'#66baf0',
		    xAxis: {
		        data: data.titleList,
		        axisLabel:{
                    interval:0,
                    rotate:45,
                    margin:2,
                     formatter:function(value)
                    {
                    	if(value.length>7){
                    		return value.substring(0,7)+"...";
                    	}
                        return value;
                    }}
		    },
		    yAxis: {},
		    series: [{
		        type: 'bar',
		        data:data.pvList,
		        barMaxWidth:40
		    }]
		};
		
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);

	}
})

 

$.ajax({
	url:"/admin/api/statistic/statisticsAccessNumDays/7",
	success:function(data){
		echarts.init(document.getElementById('line')).setOption({
		    title: {text: '7天访问量'},
		    tooltip: {},
		    toolbox: {
		        feature: {
		            dataView: {},
		            saveAsImage: {
		                pixelRatio: 2
		            },
		            restore: {}
		        }
		    }, color:'#66baf0',
		    xAxis: { 
		    	type: 'category',
		        data: data.dayList
		    }, grid:{
		    	bottom:"30%"
		    },
		    yAxis: {
		    	type:'value'
		    },
		    series: [{
		        type: 'line',
		        smooth: true,
		        data: data.numList
		    }]
		});
	}
})
})