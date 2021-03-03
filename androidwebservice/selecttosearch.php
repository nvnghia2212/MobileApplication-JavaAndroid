<?php

require "./connect.php";

$emailuser		= $_POST['emailUser'];
// $emailuser 		= "abc@gmail.com";

$query = mysqli_query($connect,"SELECT * FROM user c 
		WHERE c.email !='$emailuser' AND c.email not in (select emailfrienduser from frienduser WHERE email = '$emailuser') AND c.email not in (select emailfrienduser from requestfrienduser WHERE email = '$emailuser') ");


//1 Tạo class
class User{
	function User($id, $email, $givenname, $name, $latitude, $longitude, $onloff){
		$this -> ID = $id;
		$this -> Email = $email;
		$this -> GivenName = $givenname;
		$this -> Name = $name;
		$this -> Latitude = $latitude;
		$this -> Longitude = $longitude;
		$this -> OnlOff = $onloff;
	}
}
//2 Tạo mảng
$arrayUser = array();
//3 Thêm phần tử vào mảng
while ($row = mysqli_fetch_assoc($query)){
	array_push($arrayUser, new User ($row['id'], $row['email'], $row['givenname'], $row['name'], $row['latitude'], $row['longitude'],$row['onloff']));
}
//4 Chuyển array -> JSON
echo json_encode($arrayUser);

?>