<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];
$useradd			= $_POST['userAdd'];

$member				= $_POST['emailUser'];



$queryinsert = "INSERT INTO memberdating VALUES (null, '$nameaddress', '$dateaddress', '$datetimeadd', '$useradd', '$member')";

if ( mysqli_query($connect,$queryinsert) == true ){

	echo "insertmemberdatingsuccess";

}else{

	echo "insertmemberdatingerror";

	}
?>