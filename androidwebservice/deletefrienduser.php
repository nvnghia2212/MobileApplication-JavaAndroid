<?php

require "./connect.php";

$emailuser 		= $_POST['emailUser'];
$emailfriend 	= $_POST['emailFriend'];

// $emailuser 		= "ngungu@gmail.com";
// $emailfriend 	= "ratngu@gmail.com";


$query = mysqli_query($connect,"SELECT * FROM frienduser WHERE email = '$emailuser' AND emailfrienduser = '$emailfriend'");
$query2 = mysqli_query($connect,"SELECT * FROM frienduser WHERE email = '$emailfriend' AND emailfrienduser = '$emailuser'");


if ((!$query || mysqli_num_rows($query) > 0) && (!$query2 || mysqli_num_rows($query2) > 0)) {
		
		$deleterqfrienduser1 ="DELETE FROM frienduser Where email = '$emailuser' AND emailfrienduser = '$emailfriend'";
		$deleterqfrienduser2 ="DELETE FROM frienduser Where email = '$emailfriend' AND emailfrienduser = '$emailuser'";

		if  ((mysqli_query($connect,$deleterqfrienduser1) == true) && (mysqli_query($connect,$deleterqfrienduser2) == true)){
			
			echo "delete friends success";
		}else{
			echo "delete friend error";
			}
		

}else{
		echo "delete friend error";
	}
	



?>