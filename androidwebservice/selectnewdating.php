<?php

require "./connect.php";


$emailuser		= $_POST['emailUser'];



// $query = mysqli_query($connect,"SELECT a.* FROM newdating a left join (SELECT * FROM frienduser  WHERE email ='$emailuser') b 
// 	on a.useradd = b.emailfrienduser WHERE a.useradd = '$emailuser'");

$query = mysqli_query($connect,"SELECT a.* FROM newdating a WHERE a.useradd = '$emailuser' 
	OR a.useradd in (SELECT emailfrienduser FROM frienduser WHERE email ='$emailuser')");



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