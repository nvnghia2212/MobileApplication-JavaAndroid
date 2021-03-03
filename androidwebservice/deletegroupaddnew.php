<?php

require "./connect.php";

$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['emailUser'];
$datetimeadd	= $_POST['dateTimeAdd'];


	$querydelete = "DELETE FROM groupaddnew Where namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'";

	if ( mysqli_query($connect,$querydelete) == true ){

		mysqli_query($connect,"DELETE FROM grouppeople WHERE namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");


		mysqli_query($connect,"DELETE FROM chatgroup WHERE namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");

		echo "deletegroupsuccess";

	}else{
		echo "deletegrouperror";
	}
	

?>