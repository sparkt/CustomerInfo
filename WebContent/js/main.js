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
	
//	// ==================一个table实例================================
//	  table.render({
//	    elem: '#demo',// 渲染对象
//	    height: 315,// 表格高度
//	    url: 'getTaskList', // 数据接口
//	    where: {key: ''},// 给后台传的参数
//	    page: true, // 开启分页
//	    limit: 10,// 每页显示信息条数
//	    id: 'testReload',
//	    cols: [[ // 表头
//	      {field: 'id', title: 'ID', sort: true, fixed: 'left'}
//	      ,{field: 'title', title: '任务标题',lign:'center'}
//	      ,{field: 'create_time', title: '开始时间', lign:'center'}
//	      ,{field: 'deadline', title: '截止时间', lign:'center'} 
//	      ,{field: 'depname', title: '执行人',align:'center' }
//	      ,{field: 'status', title: '状态',align:'center',
//	    	  templet: function(d){
//	    		  if(d.status==1){
//	    			  return '<span style="color:green;">已完成</span>';
//	    		  }else{
//	    			  return '<span style="color:red;">未完成</span>';
//	    		  }}  
//	      }
//	      ,{fixed: 'right',  align:'center', toolbar: '#barDemo'} // 这里的toolbar值是模板元素的选择器
//	    ]]
//	  });
	

	// 动态获取文章总数和待审核文章数量,最新文章
	$.get("../json/newsList.json",
		function(data){
			var waitNews = [];
			$(".allNews span").text(.222222222222222222222222222222222222.length);  // 文章总数
			for(var i=0;i<data.length;i++){
				var newsStr = data[i];
				if(newsStr["newsStatus"] == "待审核"){
					waitNews.push(newsStr);
				}
			}
			$(".waitNews span").text(waitNews.length);  // 待审核文章
			// 加载最新文章
			var hotNewsHtml = '';
			for(var i=0;i<5;i++){
				hotNewsHtml += '<tr>'
		    	+'<td align="left">'+data[i].newsName+'</td>'
		    	+'<td>'+data[i].newsTime+'</td>'
		    	+'</tr>';
			}
			$(".hot_news").html(hotNewsHtml);
		}
	)

	// 法院书记员
	$.get("getsizeC",
		function(data){
			$(".Cc span").text(data.row);
		}
	)

	// 高升专
	$.get("getsizeS",
		function(data){
			$(".Sp span").text(data.row);
		}
	)

	// 教师资格证
	$.get("getsizeT",
		function(data){
			$(".Teacher span").text(data.row);
		}
	)
	// 非全日制研究生
	$.get("getsizeP",
		function(data){
			$(".part span").text(data.row);
		}
	)
    // 财会经济
	$.get("getsizeA",
		function(data){
			$(".Acc span").text(data.row);
		}
	)
    // 普通话培训
	$.get("getsizeM",
		function(data){
			$(".Md span").text(data.row);
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


