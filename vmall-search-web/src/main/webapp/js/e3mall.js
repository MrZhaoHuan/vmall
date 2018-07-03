var E3MALL = {
	checkLogin : function(){
		var _ticket = $.cookie("ticket");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8087/clientManage/validTicket?ticket="+_ticket+"&serverUrl=http://localhost:8085/",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	E3MALL.checkLogin();
});