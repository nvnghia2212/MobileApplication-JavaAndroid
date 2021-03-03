<?php

require "./connect.php";

$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['userAdd'];
$datetimeadd	= $_POST['dateTimeAdd'];

$membersend		= $_POST['emailSend'];
$textsend		= $_POST['textMess'];
$datemess		= $_POST['dateMess'];
$timemess		= $_POST['timeMess'];


	$queryinsert = "INSERT INTO chatgroup VALUES (null, '$membersend','$textsend','$datemess','$timemess','$namegroup', '$useradd', '$datetimeadd')";

	if ( mysqli_query($connect,$queryinsert) == true ){
		
		echo "insertchatgroupsuccess";

	}else{
		echo "insertchatgrouperror";
	}
	

?>