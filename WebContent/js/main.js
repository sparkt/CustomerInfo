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
	$.get("gainUndergraduateNum",
		function(data){
			$(".Undergraduate span").text(data.row);
		}
	)
	
	// 建筑工程
	$.get("gainArchitectNum",
		function(data){
			$(".Architect span").text(data.row);
		}
	)
	
	// 职业资格
	$.get("gainProfessionalNum",
		function(data){
			$(".Professional span").text(data.row);
		}
	)
	

	// 医药卫生
	$.get("gainMedicalScienceNum",
		function(data){
			$(".MedicalScience span").text(data.row);
		}
	)

	// 外语少儿
	$.get("gainForeignLanguageNum",
		function(data){
			$(".ForeignLanguage span").text(data.row);
		}
	)
	// 高升专
	$.get("getsizeS",
		function(data){
			$(".SpecialPromotiom span").text(data.row);
		}
	)
	// 非全日制研究生
	$.get("getsizeP",
		function(data){
			$(".PartTimePostgraduate span").text(data.row);
		}
	)
	// 普通话培训
	$.get("getsizeM",
		function(data){
			$(".Mandarin span").text(data.row);
		}
	)
	// 教师资格证
	$.get("getsizeT",
		function(data){
			$(".Teachercertification span").text(data.row);
		}
	)
		// 法院书记员
	$.get("getsizeC",
		function(data){
			$(".CourtClerk span").text(data.row);
		}
	)
		// 财会经济
	$.get("getsizeA",
		function(data){
			$(".Accounting span").text(data.row);
		}
	)



	// 数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());	
	})

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
 	
 
 })


