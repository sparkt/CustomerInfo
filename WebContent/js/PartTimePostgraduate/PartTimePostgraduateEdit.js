var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;
		var id = $("input[name='id']").val();
		// 加载页面数据
		$.get("getPartTimePostgraduate?id="+id,function(data){	
			var d = data.d;
	        // 执行加载数据的方法
			//修改页面从此更新页面
	        $("input[name='name']").val(d.name);
	        $("input[name='sex']").val(d.sex);
	        $("input[name='tel_no']").val(d.tel_no);
	        $("input[name='disclose']").val(d.disclose);
	        $("input[name='age']").val(d.age);
	        $("input[name='nation']").val(d.nation);
	        $("input[name='work_address']").val(d.work_address);
	        $("input[name='comments']").val(d.comments);
	        
//	        if(d.status == 0){
//	        	$("#rad1").attr("checked","checked");
//	        	$("#rad2").attr("checked",false);
//	        }
//	        else{
//	        	$("#rad1").attr("checked",false);
//	        	$("#rad2").attr("checked","checked");
//	        }
	        
	        form.render();
		});

 	form.on("submit(addUser)",function(data){console.log(data.field);
 		var index;
 		 $.ajax({// 异步请求返回给后台
	    	  url:'updatePartTimePostgraduate',
	    	  type:'POST',
	    	  data:data.field,
	    	  dataType:'json',
	    	  beforeSend: function(re){
	    		  index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	          },
	    	  success:function(d){
	    			// 弹出loading
			    	top.layer.close(index);
			  		top.layer.msg("操作成功！");
			   		layer.closeAll("iframe");
			  	 		// 刷新父页面
			  	 	parent.location.reload();
		    		
	    	  },
	    	  error:function(XMLHttpRequest, textStatus, errorThrown){
	    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
	    		        time: 20000, // 20s后自动关闭
	    		        btn: ['知道了']
	    		      });
	           }
	      });
 		return false;
 	})
	
})
