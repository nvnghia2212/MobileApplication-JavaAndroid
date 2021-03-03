<?php

require "./connect.php";

$nameaddress		= $_POST['nameAddress'];
$dateaddress		= $_POST['dateAddress'];
$datetimeadd		= $_POST['dateTimeAdd'];
$useradd			= $_POST['userAdd'];


$query = mysqli_query($connect,"SELECT * FROM newdating WHERE nameaddress = '$nameaddress' AND dateaddress = '$dateaddress' AND datetimeadd = '$datetimeadd' AND useradd = '$useradd'");

//1 Tạo class
class Dating{
	function Dating($id, $nameaddress, $dateaddress, $datetimeadd, $useradd){
		$this -> ID = $id;
		$this -> NameAddress = $nameaddress;
		$this -> DateAddress = $dateaddress;
		$this -> DateTimeAdd = $datetimeadd;
		$this -> UserAdd = $useradd;
		
	}
}
//2 Tạo mảng
$arrayGroup = array();
//3 Thêm phần tử vào mảng
while ($row = mysqli_fetch_assoc($query)){
	array_push($arrayGroup, new Dating ($row['id'],  $row['nameaddress'], $row['dateaddress'], $row['datetimeadd'], $row['useradd']));
}
//4 Chuyển array -> JSON
echo json_encode($arrayGroup);
?>