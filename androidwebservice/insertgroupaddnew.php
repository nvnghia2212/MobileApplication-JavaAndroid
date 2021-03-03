<?php

require "./connect.php";

$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['emailUser'];
$datetimeadd	= $_POST['dateTimeAdd'];


	$queryinsert = "INSERT INTO groupaddnew VALUES (null, '$namegroup', '$useradd', '$datetimeadd') ";

	if ( mysqli_query($connect,$queryinsert) == true ){

		mysqli_query($connect,"INSERT INTO grouppeople VALUES (null, '$useradd', '$namegroup', '$useradd', '$datetimeadd')");

		echo "insertgroupsuccess";

	}else{
		echo "insertgrouperror";
	}
	

?>