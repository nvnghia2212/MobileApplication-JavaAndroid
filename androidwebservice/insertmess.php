<?php

require "./connect.php";

$emailsend			= $_POST['emailSend'];
$textmess			= $_POST['textMess'];
$emailreceived		= $_POST['emailReceived'];
$datemess			= $_POST['dateMess'];
$timemess			= $_POST['timeMess'];


$queryinsert = "INSERT INTO messenger VALUES (null, '$emailsend', '$textmess', '$emailreceived', '$datemess', '$timemess')";
	
	if(mysqli_query($connect,$queryinsert)){
		echo "insertsuccess";
	}else{
		echo "inserterror";
	}

?>