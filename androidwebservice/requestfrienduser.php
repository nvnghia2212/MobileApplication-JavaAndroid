<?php

require "./connect.php";

$emailuser 		= $_POST['emailUser'];
$emailfriend 	= $_POST['emailFriend'];

// $emailuser 		= "ngungu@gmail.com";
// $emailfriend 	= "nghia@gmail.com";

// $emailuser 		= "abc@gmail.com";
// $emailfriend 	= "nghia@gmail.com";


$query = mysqli_query($connect,"SELECT * FROM requestfrienduser WHERE email = '$emailuser' AND emailfrienduser = '$emailfriend'");

$stringquery2 = mysqli_query($connect,"SELECT * FROM requestfrienduser WHERE email = '$emailfriend' AND emailfrienduser = '$emailuser'");

if ((!$query || mysqli_num_rows($query) > 0) && (!$stringquery2 || mysqli_num_rows($stringquery2) > 0)){

	$queryinsertuser1 = "INSERT INTO frienduser VALUES (null, '$emailuser', '$emailfriend')";
	$queryinsertuser2 = "INSERT INTO frienduser VALUES (null, '$emailfriend', '$emailuser')";

	if ( ( (mysqli_query($connect,$queryinsertuser1) == true) && (mysqli_query($connect,$queryinsertuser2) == true) ) ){
		
		$deleterqfrienduser1 = mysqli_query($connect,"DELETE FROM requestfrienduser Where email = '$emailuser' AND emailfrienduser = '$emailfriend'");
		$deleterqfrienduser2 = mysqli_query($connect,"DELETE FROM requestfrienduser Where email = '$emailfriend' AND emailfrienduser = '$emailuser'");

		echo "became friends";

	}else{
		echo "add friend error";
	}
	
}else if(!$query || mysqli_num_rows($query) > 0){

	echo "request success";

}else{

	$queryinsert = "INSERT INTO requestfrienduser VALUES (null, '$emailuser', '$emailfriend')";

	if(mysqli_query($connect,$queryinsert)){

		echo "<script type='text/javascript'> window.location.href='http://localhost:8080/androidwebservice/requestfrienduser.php';
				</script>";
		
	}else{
		echo "request error";
	}
	
}

?>