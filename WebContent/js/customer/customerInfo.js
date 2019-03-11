layui.config({//框架的固定，配置的使用
	base : "js/"
}).use(['form','layer','jquery','laypage','table','laytpl'],function(){//组件，使用组件完成功能：from:表单；
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		laytpl = layui.laytpl,
		$ = layui.$;//以上只是将所需要的文件拿出来，以便于后面使用。

//==================一个table实例================================//table怎么设置
	  table.render({
	    elem: '#demo',//渲染对象
	    height:'full-88',//表格高度
	    url: 'queryCustomers', //数据接口
	    where: {key: '',type:$("#type").val()},//给后台传的参数
	    page: true, //开启分页
	    limit: 10,//每页显示信息条数
	    id: 'testReload',
	    cols:  [[ //表头
	    	 {field: 'name', title: '姓名', sort: true, fixed: 'left'}
		      ,{field: 'sex', title: '性别',align:'center',width:70,
		    	  templet: function(d){
		    		  if(d.sex==1){
			    		  return '<span class="layui-badge layui-bg-blue">男</span>'
			    	  }else{
			    		  return '<span class="layui-badge layui-bg-orange">女</span>'
			    	  }
		    	  }}
		      ,{field: 'tel_no', title: '电话', align:'center'}
		      ,{field: 'comments', title: '备注',align:'center'}
		      ,{field: 'status', title: '状态', align:'center',
		    	  templet: function(d){
			    	  if(d.status==6){
			    		  return '<span class="layui-badge layui-bg-green">已成交</span>'
			    	  }else if(d.status==3){
			    		  return '<span class="layui-badge layui-bg-orange">待处理</span>'
			    	  }else if(d.status==2){
			    		  return '<span class="layui-badge layui-bg-blue">已跟进</span>'
			    	  }else{
			    		  return '<span class="layui-badge layui-bg-red">未处理</span>'
			    	  }
			      }}
		      ,{fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
	    ]]
	  });
//====================点击【搜索】按钮事件===========================
  var active = {
		  reload : function() {
			  var demoReload = $('#demoReload');
							// 执行重载
			  table.reload('testReload', {//reload重新加载
				  page : {
					  curr : 1// 重新从第 1 页开始
					  },
					  where : {//要查询的字段
						  key : demoReload.val(),
						  type:$("#type").val()
						  }
					  });
			  }
  };
  var actives = {
		  reload : function() {
			  var demoReload = $('#demoReload');
							// 执行重载
			  table.reload('testReload', {//reload重新加载
					  where : {//要查询的字段
						  key : demoReload.val(),
						  type:$("#type").val()
						  }
					  });
			  }
  };
//绑定搜索事件
  //将事件绑定在按钮上
  $('.layui-btn').on('click', function() {
	  var type = $(this).data('type');
	  active[type] ? active[type].call(this) : '';
	  });
//=======================待处理按钮====================================  
//给点击成交动作，直接应用layui
	table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	  var data = obj.data; //获得当前行数据
	  var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
	  var tr = obj.tr; //获得当前行 tr 的DOM对象
	 
	  if(layEvent === 'chengjiao'){ //成交
		  layer.confirm('确定成交此信息？',{icon:3, title:'提示信息'},function(index){
				var msgid;
				//向服务端发送指令
		 		 $.ajax({//异步请求返回给后台
			    	  url:'completeCustomer',
			    	  type:'POST',
			    	  data:{"id":data.id},
			    	  dataType:'json',
			    	  beforeSend: function(re){
			    		  msgid = top.layer.msg('数据处理中，请稍候',{icon: 16,time:false,shade:0.8});
			          },
			    	  success:function(d){
			    		  top.layer.close(msgid);
			    		  if(d.result){
			    			  actives.reload();
			    		  }else{
			    			  top.layer.msg("操作失败！，数据库操作有问题！！");
			    		  }
				    		
			    	  },
			    	  error:function(XMLHttpRequest, textStatus, errorThrown){
			    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
			    		        time: 20000, //20s后自动关闭
			    		        btn: ['知道了']
			    		      });
			           }
			      });
		 //关闭当前提示	
	      layer.close(index);
	    });
	  } else if(layEvent === 'handle'){ //处理 
		  layer.confirm('确定处理此信息？',{icon:3, title:'提示信息'},function(index){
				var msgid;
				//向服务端发送指令
		 		 $.ajax({//异步请求返回给后台
			    	  url:'dealCustomer',
			    	  type:'POST',
			    	  data:{"id":data.id},
			    	  dataType:'json',
			    	  beforeSend: function(re){
			    		  msgid = top.layer.msg('数据处理中，请稍候',{icon: 16,time:false,shade:0.8});
			          },
			    	  success:function(d){
			    		  top.layer.close(msgid);
			    		  if(d.result){
			    			  actives.reload();//重新加载数据
			    		  }else{
			    			  top.layer.msg("操作失败！，数据库操作有问题！！");
			    		  }
				    		
			    	  },
			    	  error:function(XMLHttpRequest, textStatus, errorThrown){
			    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
			    		        time: 20000, //20s后自动关闭
			    		        btn: ['知道了']
			    		      });
			           }
			      });
		 //关闭当前提示	
	      layer.close(index);
	    });
	  }else if(layEvent === 'edit'){ //编辑
		  var index = layui.layer.open({
              title : "【修改信息】",
              type : 2,
              area: ['800px', '600px'],
              content : "openSpecialPromotiomEdit?id="+data.id,
              success : function(layero, index){
                  setTimeout(function(){
                      layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                          tips: 3
                      });
                  },500)
              }
          })          
          layui.layer.full(index);
	  } else if(layEvent === 'del'){
		  //删除
		  layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
				var msgid;
				//向服务端发送删除指令
		 		 $.ajax({//异步请求返回给后台
			    	  url:'delCustomerModel',
			    	  type:'POST',
			    	  data:{"id":data.id},
			    	  dataType:'json',
			    	  beforeSend: function(re){
			    		  msgid = top.layer.msg('数据处理中，请稍候',{icon: 16,time:false,shade:0.8});
			          },
			    	  success:function(d){
			    		  top.layer.close(msgid);
			    		  if(d.result){
			    			//弹出loading
						   		layer.closeAll("iframe");
						   		obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
						  	 //刷新父页面
						  	 	parent.location.reload();
			    		  }else{
			    			  top.layer.msg("操作失败！，数据库操作有问题！！");
			    		  }
				    		
			    	  },
			    	  error:function(XMLHttpRequest, textStatus, errorThrown){
			    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
			    		        time: 20000, //20s后自动关闭
			    		        btn: ['知道了']
			    		      });
			           }
			      });
		 //关闭当前提示	
	      layer.close(index);
	    });
	  
	  }
	});
})
