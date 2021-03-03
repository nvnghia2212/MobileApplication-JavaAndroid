<?php

require "./connect.php";

$emailuser 		= $_POST['emailUser'];
$emailfriend 	= $_POST['emailFriend'];

// $emailuser 		= "ratngu@gmail.com";
// $emailfriend 	= "ngungu@gmail.com";


$query = mysqli_query($connect,"SELECT * FROM requestfrienduser WHERE email = '$emailfriend' AND emailfrienduser = '$emailuser'");


if (!$query || mysqli_num_rows($query) > 0) {
		
		$deleterqfrienduser ="DELETE FROM requestfrienduser Where email = '$emailfriend' AND emailfrienduser = '$emailuser'";

		if  (mysqli_query($connect,$deleterqfrienduser) == true){
			
			echo "delete request friends success";
		}else{

			echo "delete request friend error";
			}
		

}else{
		echo "delete request friend error";
	}
	



?>