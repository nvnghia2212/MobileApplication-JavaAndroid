<?php

require "./connect.php";


// $namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['emailUser'];
// $datetimeadd	= $_POST['dateTimeAdd'];



$query = mysqli_query($connect,"SELECT T1.* FROM groupaddnew T1, (SELECT * FROM grouppeople  WHERE member = '$useradd') T2 WHERE T1.useradd = T2.useradd
AND T1.namegroup = T2.namegroup AND T1.datetimeadd = T2.datetimeadd");

//1 Tạo class
class User{
	function User($id, $namegroup, $useradd, $datetimeadd){
		$this -> ID = $id;
		$this -> NameGroup = $namegroup;
		$this -> UserAdd = $useradd;
		$this -> DateTimeAdd = $datetimeadd;
	
	}
}
//2 Tạo mảng
$arrayGroup = array();
//3 Thêm phần tử vào mảng
while ($row = mysqli_fetch_assoc($query)){
	array_push($arrayGroup, new User ($row['id'], $row['namegroup'], $row['useradd'], $row['datetimeadd']));
}
//4 Chuyển array -> JSON
echo json_encode($arrayGroup);

?>