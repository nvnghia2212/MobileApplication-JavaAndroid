<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];
$useradd			= $_POST['userAdd'];

$member				= $_POST['emailUser'];


$query = mysqli_query($connect,"SELECT * FROM memberdating WHERE nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd' AND member='$member'");

if (mysqli_num_rows($query) > 0) {
	echo "1";
}else{
	echo "0";
}


?>