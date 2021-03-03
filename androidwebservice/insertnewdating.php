<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];

$useradd			= $_POST['emailUser'];

// $nameaddress		= "abc";
// $dateaddress		= "xxxxx";
// $datetimeadd		= "xxxxxxxxxxxxx";
// $useradd			= "vananh@gmail.com";


$queryinsert = "INSERT INTO newdating VALUES (null, '$nameaddress', '$dateaddress', '$datetimeadd', '$useradd') ";

if ( mysqli_query($connect,$queryinsert) == true ){

	$queryinsert = "INSERT INTO memberdating VALUES (null, '$nameaddress', '$dateaddress', '$datetimeadd', '$useradd', '$useradd')";

	if ( mysqli_query($connect,$queryinsert) == true ){
		echo "insertdatingsuccess";
	}else{
		echo "insertdatingerror";
	}
}

?>