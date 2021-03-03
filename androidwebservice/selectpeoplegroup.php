<?php

require "./connect.php";


$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['userAdd'];
$datetimeadd	= $_POST['dateTimeAdd'];

$member 		= $_POST['emailUser'];


$query = mysqli_query($connect,"SELECT * FROM user WHERE email IN (SELECT member FROM grouppeople WHERE member != '$member' AND namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd')");


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


// //1 Tạo class
// class User{
// 	function User($id, $member, $namegroup, $useradd, $datetimeadd){
// 		$this -> ID = $id;
// 		$this -> MemBer = $member;
// 		$this -> NameGroup = $namegroup;
// 		$this -> UserAdd = $useradd;
// 		$this -> DateTimeAdd = $datetimeadd;
	
// 	}
// }
// //2 Tạo mảng
// $arrayGroup = array();
// //3 Thêm phần tử vào mảng
// while ($row = mysqli_fetch_assoc($query)){
// 	array_push($arrayGroup, new User ($row['id'],  $row['member'], $row['namegroup'], $row['useradd'], $row['datetimeadd']));
// }
// //4 Chuyển array -> JSON
// echo json_encode($arrayGroup);

?>