layui.config({
	base : "js/"
}).use(['form','layer','jquery','laypage','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.$;
		
//==================一个table实例================================
	  table.render({
	    elem: '#demo',//渲染对象
	    url: 'getUserInfoList', //数据接口
	    where: {key: ''},//给后台传的参数
	    page: true, //开启分页
	    limit: 10,//每页显示信息条数
	    toolbar: '#toolbarDemo',
	    id: 'testReload',
	    cols: [[ //表头
		      {field: 'id', title: 'ID', sort: true, fixed: 'left',width:50}
		      ,{field: 'user_name', title: '用户名',width:100}
		      ,{field: 'user_sex', title: '性别',width:50,templet:function(d){
		    	  if(d.user_sex=='男'){
		    		  return '<span style="color: blue">男</span>'
		    	  }else{
		    		  return '<span style="color: red" >女</span>'
		    	  }
		      }} 
		      ,{field: 'phone_no', title: '手机号码'}
		      ,{field: 'user_password' ,title:'用户密码'}
		      ,{field: 'vip_grade' ,title:'等级',width:50}
		      ,{field: 'groups' ,title:'团队'}
		      ,{field: 'status' ,title:'状态',width:80, templet: function(d){
		    	  if(d.status==0){
		    		  return '<span class="layui-badge layui-bg-red">未审核</span>'
		    	  }else{
		    		  return '<span class="layui-badge layui-bg-blue">已审核</span>'
		    	  }
		      }}
		      ,{fixed: 'right', align:'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
		    ]]
	  });
	  
//====================点击【搜索】按钮事件===========================
  var active = {
		  reload : function() {
			  var demoReload = $('#demoReload');
							// 执行重载
			  table.reload('testReload', {
				  page : {
					  curr : 1// 重新从第 1 页开始
					  },
					  where : {//要查询的字段
						  key : demoReload.val(),
						  id : 11
						  }
					  });
			  }
  };
//绑定搜索事件
  $('.layui-btn').on('click', function() {
	  var type = $(this).data('type');
	  active[type] ? active[type].call(this) : '';
	  });
  
//=============绑定【添加】事件============================
	$(window).one("resize",function(){
		$(".add_btn").click(function(){
			var index = layui.layer.open({
				title : "【添加信息】",
				icon: 2,
				type : 2,
				content : "openUserinfoAdd",
				success : function(layero, index){
					setTimeout(function(){
						layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
							tips: 3
						});
					},500)
				}
			})			
			layui.layer.full(index);
		})
	}).resize();
  
//=======================监听工具条====================================
	table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	  var data = obj.data; //获得当前行数据
	  var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
	  var tr = obj.tr; //获得当前行 tr 的DOM对象
	 
	  if(layEvent === 'check'){ //审核通过
		  var index;
	 		 $.ajax({//异步请求返回给后台
		    	  url:'check?phone_no='+data.phone_no,
		    	  type:'POST',
		    //	  data:data.field,
		    	  dataType:'json',
		    	  beforeSend: function(re){
		    		  index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		          },
		    	  success:function(d){
		    			//弹出loading
				    	top.layer.close(index);
				  		top.layer.msg("审核通过！");
				   		//layer.closeAll("iframe");
				  	 		//刷新父页面
				  	 	parent.location.reload();
			    		
		    	  },
		    	  error:function(XMLHttpRequest, textStatus, errorThrown){
		    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
		    		        time: 20000, //20s后自动关闭
		    		        btn: ['知道了']
		    		      });
		           }
		      });
	 		return false;
	 	
		  
	  } else if(layEvent === 'del'){
		  
		  
	  } else if(layEvent === 'edit'){ //编辑
		  var index = layui.layer.open({
              title : "修改信息",
              type : 2,
              content : "openUserinfoEdit?phone_no="+data.phone_no,
              success : function(layero, index){
                  setTimeout(function(){
                      layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                          tips: 3
                      });
                  },500)
              }
          })          
          layui.layer.full(index);
	  }
	});
})


