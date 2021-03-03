<?php

require "./connect.php";

$emailuser 		= $_POST['emailUser'];
$emailfriend 	= $_POST['emailFriend'];

// $emailuser 		= "abc@gmail.com";
// $emailfriend 	= "ngungu@gmail.com";


$query = mysqli_query($connect,"SELECT * FROM requestfrienduser WHERE email = '$emailfriend' AND emailfrienduser = '$emailuser'");


if (!$query || mysqli_num_rows($query) > 0) {

	$queryinsertuser1 = "INSERT INTO frienduser VALUES (null, '$emailuser', '$emailfriend')";
	$queryinsertuser2 = "INSERT INTO frienduser VALUES (null, '$emailfriend', '$emailuser')";
		
		// $deleterqfrienduser1 ="DELETE FROM frienduser Where email = '$emailuser' AND emailfrienduser = '$emailfriend'";
		// $deleterqfrienduser2 ="DELETE FROM frienduser Where email = '$emailfriend' AND emailfrienduser = '$emailuser'";

		if ( ( (mysqli_query($connect,$queryinsertuser1) == true) && (mysqli_query($connect,$queryinsertuser2) == true) ) ){
		
		$deleterqfrienduser1 = mysqli_query($connect,"DELETE FROM requestfrienduser Where email = '$emailfriend' AND emailfrienduser = '$emailuser'");

		echo "became friends";

	}else{
		echo "add friend error";
	}
		

}else{
		echo "add friend error";
	}
	



?>