<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];
$useradd			= $_POST['userAdd'];

$member				= $_POST['emailUser'];



$querydelete = "DELETE FROM memberdating WHERE member='$member' AND nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd'";

	if ( mysqli_query($connect,$querydelete) == true ){

		$query =mysqli_query($connect,"SELECT * FROM memberdating WHERE nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd'");

		if (mysqli_num_rows($query) < 1) {

			mysqli_query($connect,"DELETE FROM newdating WHERE nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd'");
		}

		echo "deletememberdatingsuccess";

	}else{
		echo "deletememberdatingerror";
	}


?>