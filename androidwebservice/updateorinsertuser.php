<?php

require "./connect.php";

$emailuser		= $_POST['emailUser'];
$givenname		= $_POST['givennameUser'];
$name 			= $_POST['nameUser'];
$latitude		= $_POST['latitudeUser'];
$longitude		= $_POST['longitudeUser'];
$onloff			= $_POST['onloffUser'];

// $emailuser 		= "xyz@gmail.com";
// $givenname		= "kocon";
// $name 			= "ngu";
// $latitude		= "10.7894560";
// $longitude		= "105.01233210";
// $onloff			= "1";


$query = mysqli_query($connect,"SELECT email FROM user WHERE email='$emailuser'");
if (!$query || mysqli_num_rows($query) > 0){

	$queryupdate = "UPDATE user SET latitude = '$latitude', longitude = '$longitude', onloff = '$onloff' WHERE email='$emailuser'";

	if(mysqli_query($connect,$queryupdate)){
		// Có thể gọi đến table bạn bè. Nhưng nên tạo file json khác
		echo "updatesuccess";
		// echo "<script type='text/javascript'> window.location.href='http://192.168.2.15:8080/androidwebservice/selectfriend.php';
				// </script>";
	}else{
		echo "updateerror";
	}

}else{

	$queryinsert = "INSERT INTO user VALUES (null, '$emailuser', '$givenname', '$name', '$latitude', '$longitude', '$onloff')";

	if(mysqli_query($connect,$queryinsert)){
		echo "addsuccess";
	}else{
		echo "adderror";
	}

}


?>