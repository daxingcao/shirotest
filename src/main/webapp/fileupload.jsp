<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<title>文件上传</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/bootstrap-theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/fileupload.css" />
<script src="<%=request.getContextPath() %>/static/javascript/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/static/javascript/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/static/javascript/jquery.validate.js"></script>
</head>
<body>
<div id="area" class="out-input">
	<div id="file_input" class="input-file">
		拖拽区域
	</div>
	<button class="button-remove">
		<i class="glyphicon glyphicon-remove"></i>
	</button>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" data-keyboard='true' aria-hidden="true">
	<div id="showImage" style="overflow: auto;width: auto;width: 1200px;margin: 50px auto;text-align: center;">
		<img id="real_img" onmousedown='clickMouse(event)' src="">
	</div>
</div>
</body>
<script type="text/javascript">
//鼠标左键点击图片,隐藏模态框
function clickMouse(e){
	if(e.button == 0){
		$("#myModal").modal('hide');
	}
}
$(document).on({
	dragleave:function(e){      //拖离
        e.preventDefault();
    },
    drop:function(e){           //拖后放
        e.preventDefault();
    },
    dragenter:function(e){      //拖进
        e.preventDefault();
    },
    dragover:function(e){       //拖来拖去
        e.preventDefault();
    }
})
//删除按钮样式变化
$(".button-remove").on("mouseover",function(obj){
	$(".button-remove")[0].style.opacity = ".8";
})
$(".button-remove").on("mouseout",function(obj){
	$(".button-remove")[0].style.opacity = ".4";
})
$(".button-remove").on("click",(obj) => {
	box.style.lineHeight = "220px";
	$("#file_input").html("拖拽区域");
})
//查看图片按钮样式变化
function showImage(){
	$(".button_big").mouseover(function(obj){
		$(".button_big")[0].style.backgroundColor = "#666";
		$(".glyphicon-zoom-in")[0].style.color = "white";
	})
	$(".button_big").on("mouseout",function(obj){
		$(".button_big")[0].style.backgroundColor = "transparent";
		$(".glyphicon-zoom-in")[0].style.color = "black";
	})
	$(".button_big").click( () => {
		$("#myModal").modal('show');
	})
}

//定义文件集合字段
var fileList;
//得到装载拖拽文件的元素
var box = document.getElementById("file_input");
//拖拽文件脱离元素或进入元素样式变化
box.addEventListener("dragenter",(ev) => {
	box.style.backgroundColor = "aliceblue";
});
box.addEventListener("dragleave",(ev) => {
	box.style.backgroundColor = "white";
});
//将文件放入元素后操作
box.addEventListener("drop",(ev) => {
	ev.preventDefault(); //取消默认浏览器拖拽效果
	ev.stopPropagation();//取消冒泡,主要阻止火狐拖拽图片打开新标签页图片
    fileList = ev.dataTransfer.files; //获取文件对象
    //检测是否是拖拽文件到页面的操作
    if (fileList.length == 0) {
    	box.style.backgroundColor = "white";
        return false;
    }
    //检测文件是不是图片
    if (fileList[0].type.indexOf('image') === -1) {
        box.style.backgroundColor = "white";
        alert("您拖的不是图片！");
        return false;
    }
    //拖拉图片到浏览器，可以实现预览功能
    var url = window.URL || window.webkitURL;
    var img = url.createObjectURL(fileList[0]);
    var filename = fileList[0].name; //图片名称
    var dom = document.getElementById("real_img");
    dom.setAttribute("src",img);
    dom.setAttribute("alt",filename);
    dom.setAttribute("title",filename);
    //控制图片上传的大小
    var filesize = Math.floor((fileList[0].size) / 1024);
    if (filesize > 500) {
        alert("上传大小不能超过500K.");
        return false;
    }
    //放入文件后,改变元素的样式
    box.style.backgroundColor = "white";
	box.style.lineHeight = "normal";
	//动态的在box元素中生成标签文件块
	var str = "<div class='show'>" +
			  	"<div id='file_view' class='show-img'>" +
					"<img draggable='false' alt='' src='" + img + "'>" +
				"</div>" +
				"<div class='show-info'>" +
					"<p>"+filename+"</p>" +
					"<p>("+filesize+"KB)</p>" +
					"<button class='button_big'><i class='glyphicon glyphicon-zoom-in'></i></button>" +
				"</div>" +
			  "</div>"
	//使用html拼接,每次拼接覆盖掉以前的
    $("#file_input").html(str);
	//标签文件块渲染完成后调用文件块中的元素样式变动,不然会失效
    showImage();
})

//上传文件方法
function uploadFile(){
	if(fileList.length == 0){
		alert("没有选择文件!");
		return;
	}
	var form = new FormData();
	form.append("myfile",fileList[0]);
	
	$.ajax({
    	url:"excel/upload/file",
    	type:"post",
    	processData:false,
		contentType:false,
        data:fd,
        success:function(data){
        	console.log(data);
        }
    })
}

</script>
</html>