<?php

require "./connect.php";

$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['emailUser'];
$datetimeadd	= $_POST['dateTimeAdd'];

$member			= $_POST['member'];


	$queryinsert = "INSERT INTO grouppeople VALUES (null, '$member', '$namegroup', '$useradd', '$datetimeadd')";

	if ( mysqli_query($connect,$queryinsert) == true ){
		

		echo "insertgrouppeoplesuccess";

	}else{
		echo "insertgrouppeopleerror";
	}
	

?>