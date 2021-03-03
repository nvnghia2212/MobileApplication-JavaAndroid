<?php

require "./connect.php";


$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['userAdd'];
$datetimeadd	= $_POST['dateTimeAdd'];


$query = mysqli_query($connect,"SELECT * FROM chatgroup WHERE namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");

//1 Tạo class
class User{
	function User($id, $membersend, $textsend, $datemess, $timemess, $namegroup, $useradd, $datetimeadd){
		$this -> ID = $id;
		$this -> MemBer = $membersend;
		$this -> TextSend = $textsend;
		$this -> DateMess = $datemess;
		$this -> TimeMess = $timemess;
		$this -> NameGroup = $namegroup;
		$this -> UserAdd = $useradd;
		$this -> DateTimeAdd = $datetimeadd;
	
	}
}
//2 Tạo mảng
$arrayGroup = array();
//3 Thêm phần tử vào mảng
while ($row = mysqli_fetch_assoc($query)){
	array_push($arrayGroup, new User ($row['id'],  $row['membersend'], $row['textsend'], $row['datemess'], $row['timemess'], $row['namegroup'], $row['useradd'], $row['datetimeadd']));
}
//4 Chuyển array -> JSON
echo json_encode($arrayGroup);

?>