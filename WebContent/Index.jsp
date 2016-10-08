<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<!-- <script src="http://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="graphe.min.js"></script>
</head>
<body>
	<h1>Hello World!</h1>
	<input type="button" id="ajaxBt1" value="run the job" />
	<br />
	
	   
<textarea placeholder="share your thoughts"  name="post" id="post">   </textarea>
<input type="button" id="ajaxBt2" value="make post" />
                   


	<div id="chartContainer" style="height: 300px; width: 100%;">

		<script type="text/javascript">
			window.onload = setupEventHandlers;
			var jsonObject = null;
			


			//setting up the event handlers for the button
			function setupEventHandlers() {

				var ajaxBtn1 = document.getElementById("ajaxBt1");
				
				ajaxBtn1.onclick = handleAjaxBtn1;
			

			}
			


			function handleAjaxBtn1() {
				
				
					
					var xmlhrObj = new XMLHttpRequest();
					xmlhrObj.open("GET",
							"http://localhost:8080/BigDataProject/asyncHello", true);
					
					xmlhrObj.onreadystatechange = function() {
						if (xmlhrObj.readyState == 4 && xmlhrObj.status == 200) {
							

							jsonObject = JSON.parse(xmlhrObj.responseText);

							var dataPoints = jsonObject.hours;
							var chart = new CanvasJS.Chart("chartContainer", {
								title : {
									text : "My First Chart in CanvasJS"
								},
								data : [ {
									// Change type to "doughnut", "line", "splineArea", etc.
									type : "column",
									dataPoints : dataPoints
								} ]
							});
							chart.render();
						}
					};
					xmlhrObj.send();
				

			}
			

		</script>


</body>
</html>