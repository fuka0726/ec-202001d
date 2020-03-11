$(function(){
	var culum;
	$('#select').val($("#forJSCulum").text());
	var getOffset = $("#forJSGetOffset").text();
	var searchName = $("#forJSSearchName").text();
	$("#select").on("change",function(){
		console.log("変更");
		if($("#select").val() == 1){
			$("#forJSCulum").text("1");
			culum = $("#forJSCulum").text();
			getOffset = 1;
			location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
//			document.getElementById('1').options[1].selected = true;
		};
		
		if($("#select").val() == 2){
			$("#forJSCulum").text("2");
			culum = $("#forJSCulum").text();
			getOffset = 1;
			location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
//			document.getElementById('2').options[2].selected = true;
		};
		
	});
	
//	$("#select".val()).on("change",function(){
//		$("#forJSGetOffset").text("1");
//		var getOffset = $(this).text()
//		var culum = $("#forJSCulum").text();
//		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
//	});
	
	$(".numOfButton").on("click",function(){
		$("#forJSGetOffset").text($(this).text());
		var getOffset = $(this).text();
		var culum = $("#forJSCulum").text();
		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
	});	
	
//　商品名を用いた検索のさいに、イベントがclickで大丈夫かどうか。
//	エンターキーを押下した場合は反応するかどうか。
	
	$("#searchName").on("keypress",function(e){
		if(e.which == 13){
			searchName = $("#searchName").val();
			$("#searchName").text(searchName);
			$("#forJSSearchName").text(searchName);
			culum = $("#forJSCulum").text();
			getOffset = 1;
			location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
			
		}
	});
	
	$("#searchButton").on("click",function(){
		searchName = $("#searchName").val();
		$("#searchName").text(searchName);
		$("#forJSSearchName").text(searchName);
		culum = $("#forJSCulum").text();
		getOffset = 1;
		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
		
	});
	
	
	
//	$("#clearButton").on("click",function(){
////		searchName = "a";
////		$("#searchName").text(searchName);
////		culum = $("#forJSCulum").text();
////		getOffset = 1;
////		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=';
////		$("#forJSCulum").text("1");
//		alert("aaaaaa");
//		culum = $("#forJSCulum").text();
//		getOffset = 1;
//		searchName = "";
//		location.href = '/show-ordered?culum=' + culum + '&getOffset=' + getOffset + '&searchName=' + searchName;
//	});
	
    $("#reset").on("click", function () {
        $("#searchName").val("");
  
    });

	
	
	

});