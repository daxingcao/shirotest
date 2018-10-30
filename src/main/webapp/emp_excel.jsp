<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/bootstrap-theme.css" />
<script src="<%=request.getContextPath() %>/static/javascript/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/static/javascript/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/static/javascript/jquery.validate.js"></script>
<style type="text/css">
.out-input{
	height: 260px;
    width: 600px;
    margin: auto;
    border: 1px solid #DDDDDD;
    text-align: center;
    border-radius: 6px;
    padding: 20px;
}
.input-file{
	height: 220px;
    border: 1px dashed #aaa;
    line-height: 220px;
    text-align: center;
    border-radius: 6px;
}
.show{
    width: 160px;
    height: calc(100% - 20px);
    float: left;
    border: 1px solid rgba(0,0,0,.2);
    box-shadow: rgba(0,0,0,.2) 0 0 10px;
    margin: 10px;
    padding: 5px;
}
.show-img{
	height: 110px;
}
.show-img img{
	width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
}
.show-info{
	height: 70px;
	font-size: 11px;
	color: #777;
	margin-top: 5px;
}
.show-info button{
	background-color: transparent;
	border: 1px solid;
	border-radius: 10px;
	width: 20px;
	height: 20px;
	padding: 0px;
	position: relative;
	left: 60px;
}
.show-info p{
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.button-remove{
    background-color: transparent;
    border: 0px;
    position: relative;
    top: -240px;
    left: 288px;
    opacity: .4;
}
</style>
</head>
<body>
	<a href="excel/download/excelmodel">下载模板</a>
	<div id="area" class="out-input">
		<div id="file_input" class="input-file">
			拖拽区域
		</div>
		<button class="button-remove">
			<i class="glyphicon glyphicon-remove"></i>
		</button>
	</div>
	<div id="preview"></div>
	<form action="excel/upload/userexcel" enctype="multipart/form-data" method="post">
		<input type="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" multiple/>
		
		<input type="submit" value="submit" />
	</form>
</body>
<script type="text/javascript">
//删除按钮样式变化
$(".button-remove").on("mouseover",function(obj){
	$(".button-remove")[0].style.opacity = ".8";
})
$(".button-remove").on("mouseout",function(obj){
	$(".button-remove")[0].style.opacity = ".4";
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
var box = document.getElementById("file_input");
var fileList;
box.addEventListener("dragenter",(ev) => {
	box.style.backgroundColor = "aliceblue";
});
box.addEventListener("dragleave",(ev) => {
	box.style.backgroundColor = "white";
});
box.addEventListener("drop",
		function(e){
			e.preventDefault(); //取消默认浏览器拖拽效果
			e.stopPropagation();//取消冒泡,主要阻止火狐拖拽图片打开新标签页图片
		    fileList = e.dataTransfer.files; //获取文件对象
		    //检测是否是拖拽文件到页面的操作
		    if (fileList.length == 0) {
		        return false;
		    }
		    //检测文件是不是图片
		    if (fileList[0].type.indexOf('image') === -1) {
		        alert("您拖的不是图片！");
		        return false;
		    }
		    //拖拉图片到浏览器，可以实现预览功能
		    var url = window.URL || window.webkitURL;
		    var img = url.createObjectURL(fileList[0]);
		    var filename = fileList[0].name; //图片名称
		    var filesize = Math.floor((fileList[0].size) / 1024);
		    if (filesize > 500) {
		        alert("上传大小不能超过500K.");
		        return false;
		    }

			box.style.backgroundColor = "white";
			box.style.lineHeight = "normal";
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
		    $("#file_input").html(str);
		    showImage();
		    //上传
		    var fd = new FormData();
		    fd.append('myFile', fileList[0]);

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
)

</script>
</html>