<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];
$useradd			= $_POST['userAdd'];


$query = mysqli_query($connect,"SELECT * FROM user WHERE email IN (SELECT member FROM memberdating WHERE nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd')");

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