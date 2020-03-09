$(function(){
	var culum = 0;
	var getOffset = 0;
	var searchName = null;
	$("#select").on("change",function(){
		console.log("変更");
		if($("#select").val() == 1){
			$("#forJSCulum").text("1");
			culum = $("#forJSCulum").text();
			console.log($("#forJSCulum").text());
			location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
		};
		
		if($("#select").val() == 2){
			culum = 2;
			location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
		};
	});
	
	$(".numOfButton").click("click",function(){
		console.log($(this).text());
		getOffset = $(this).text();
		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
	});	
	
//　商品名を用いた検索のさいに、イベントがclickで大丈夫かどうか。
//	エンターキーを押下した場合は反応するかどうか。
	
	
	

});