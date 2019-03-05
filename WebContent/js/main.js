layui.config({
	base : "js/"
}).use(['form','element','layer','jquery','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element,
		table = layui.table,
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	});
	
	// ==================一个table实例================================
	  table.render({
	    elem: '#demo',// 渲染对象
	    height: 315,// 表格高度
	    url: 'getTaskList', // 数据接口
	    where: {key: ''},// 给后台传的参数
	    page: true, // 开启分页
	    limit: 10,// 每页显示信息条数
	    id: 'testReload',
	    cols: [[ // 表头
	      {field: 'id', title: 'ID', sort: true, fixed: 'left'}
	      ,{field: 'title', title: '任务标题',lign:'center'}
	      ,{field: 'create_time', title: '开始时间', lign:'center'}
	      ,{field: 'deadline', title: '截止时间', lign:'center'} 
	      ,{field: 'depname', title: '执行人',align:'center' }
	      ,{field: 'status', title: '状态',align:'center',
	    	  templet: function(d){
	    		  if(d.status==1){
	    			  return '<span style="color:green;">已完成</span>';
	    		  }else{
	    			  return '<span style="color:red;">未完成</span>';
	    		  }}  
	      }
	      ,{fixed: 'right',  align:'center', toolbar: '#barDemo'} // 这里的toolbar值是模板元素的选择器
	    ]]
	  });

	// 专升本
	  
	$.get("getCustomerNum?type=1002",
		function(data){
			$(".Undergraduate span").text(data.row);
		}
	)
	
	// 建筑工程
	$.get("getCustomerNum?type=1006",
		function(data){
			$(".Architect span").text(data.row);
		}
	)
	
	// 职业资格
	$.get("getCustomerNum?type=1007",
		function(data){
			$(".Professional span").text(data.row);
		}
	)
	// 医药卫生
	$.get("getCustomerNum?type=1008",
		function(data){
			$(".MedicalScience span").text(data.row);
		}
	)

	// 外语少儿
	$.get("getCustomerNum?type=1009",
		function(data){
			$(".ForeignLanguage span").text(data.row);
		}
	)
	// 高升专
	$.get("getCustomerNum?type=1001",
		function(data){
			$(".SpecialPromotiom span").text(data.row);
		}
	)
	// 非全日制研究生
	$.get("getCustomerNum?type=1003",
		function(data){
			$(".PartTimePostgraduate span").text(data.row);
		}
	)
	// 普通话培训
	$.get("getCustomerNum?type=1004",
		function(data){
			$(".Mandarin span").text(data.row);
		}
	)
	// 教师资格证
	$.get("getCustomerNum?type=1005",
		function(data){
			$(".Teachercertification span").text(data.row);
		}
	)
		// 法院书记员
	$.get("getCustomerNum?type=1011",
		function(data){
			$(".CourtClerk span").text(data.row);
		}
	)
		// 财会经济
	$.get("getCustomerNum?type=1010",
		function(data){
			$(".Accounting span").text(data.row);
		}
	)
	// 已跟进
	$.get("getFollowNum?type=1010",
			function(data){
		$(".Follow span").text(data.row);
	}
	)
	// 待处理
	$.get("getPendingNum?type=1010",
			function(data){
		$(".Pending span").text(data.row);
	}
	)
	// 已成交
	$.get("getTransactionsNum?type=1010",
			function(data){
		$(".Transactions span").text(data.row);
	}
	)



	// 数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());	
	})
/*

	// 系统基本参数
	if(window.sessionStorage.getItem("systemParameter")){
		var systemParameter = JSON.parse(window.sessionStorage.getItem("systemParameter"));
		fillParameter(systemParameter);
	}else{
		$.ajax({
			url : "../json/systemParameter.json",
			type : "get",
			dataType : "json",
			success : function(data){
				fillParameter(data);
			}
		})
	}
*/
	
	// 填充数据方法
 	function fillParameter(data){
 		// 判断字段数据是否存在
 		function nullData(data){
 			if(data == '' || data == "undefined"){
 				return "未定义";
 			}else{
 				return data;
 			}
 		}
 		$(".version").text(nullData(data.version));      // 当前版本
		$(".author").text(nullData(data.author));        // 开发作者
		$(".homePage").text(nullData(data.homePage));    // 网站首页
		$(".server").text(nullData(data.server));        // 服务器环境
		$(".dataBase").text(nullData(data.dataBase));    // 数据库版本
		$(".maxUpload").text(nullData(data.maxUpload));    // 最大上传限制
		$(".userRights").text(nullData(data.userRights));// 当前用户权限
 	}
 	 // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('tubiao'));
    var option = {
    	    title : {
    	        text: '用户成交量和未成交量',
    	        subtext: '一年内数据'
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['成交量','未成交量']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
    	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value'
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'成交',
    	            type:'bar',
    	            data:[0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
    	            markPoint : {
    	                data : [
    	                    {type : 'max', name: '最大值'},
    	                    {type : 'min', name: '最小值'}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name: '平均值'}
    	                ]
    	            }
    	        },
    	        {
    	            name:'未成交',
    	            type:'bar',
    	            data:[0, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
    	            markPoint : {
    	                data : [
    	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
    	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name : '平均值'}
    	                ]
    	            }
    	        }
    	    ]
    	};
    myChart.setOption(option);
 
 })


