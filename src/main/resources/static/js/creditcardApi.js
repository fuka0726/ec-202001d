//
//$(function(){
//	$("#order").on("click",function(){
//		alert("HELLO WORLD");
//		$.ajax({
//			url:"http://localhost:8080/sample-credit-card-web-api/credit-card/payment",
//			dataType:"json",
//			data:{
//			user_id:$("#user_id").text(),
//			order_number:$("#order_number").text(),
//			amount:$("#total-price").text(),	
//			card_number:$("#card_number").val(),	
//			card_exp_year:$("#card_exp_year").val(),	
//			card_exp_month:$("#card_exp_month").val(),	
//			card_cvv:$("#card_cvv").val()	
//			},
//			async:true
//		}).done(function(data){
//			console.dir(JSON.stringify(data));
//			console.log(card_cvv);
//		}).fail(function(XMLHttpRequest,textStatus,errorThrown){
//			alert("正しい結果を得られませんでした");
//			console.log("XMLHttpRequest:"+XMLHttpRequest.status);
//			console.log("textStatus:"+textStatus);
//			console.log("errorThrown:"+errorThrown.message);
//		});
//		
//	});
//	
//});