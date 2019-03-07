$(document).ready(function(){
	builderIndex();
	calculateMoney();
	$("#addReceiptsDetailsButton").click(
		function(){
			$("#receiptsDetails").children("div").last().after($("#receiptsDetails").children("div").first().clone());
			$("#receiptsDetails").children("div").find("button").click(
				function(){
					$(this).parent().parent().remove();
					if($("#receiptsDetails").children("div").size()==1){
						$("#receiptsDetails").find("button").attr("disabled",true);
					}
					builderIndex();
					calculateMoney();
				}
			);
			$("#receiptsDetails").find("button").attr("disabled",false);
			builderIndex();
			$(".money").change(
				function(){
					calculateMoney();
				}
			);
			calculateMoney();
		}
	);
	$(".money").change(
		function(){
			calculateMoney();
		}
	);
});// JavaScript Document

function builderIndex(){
	$.each($("#receiptsDetails").children(),function(i,val){
		$("#receiptsDetails").children("div").eq(i).children().eq(0).find("select").attr("name","receiptsDetails["+i+"].costType");
		$("#receiptsDetails").children("div").eq(i).children().eq(1).find("input").attr("name","receiptsDetails["+i+"].money");
		$("#receiptsDetails").children("div").eq(i).children().eq(2).find("input").attr("name","receiptsDetails["+i+"].detail");
				
	});	
}
function calculateMoney(){
	var totalMoney=0;
	$.each($(".money"),function(i,val){
		totalMoney+=parseFloat($(".money").eq(i).val());
	});
	$("#totalMoney").attr("value",totalMoney);
}