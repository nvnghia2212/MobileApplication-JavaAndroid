<?php

require "./connect.php";

$namegroup		= $_POST['nameGroup'];
$useradd		= $_POST['userAdd'];
$datetimeadd	= $_POST['dateTimeAdd'];

$member			= $_POST['userMember'];


// cái này là: tự rồi nhóm 
// bổ sung thêm: bị xóa khỏi nhóm

	$querydelete = "DELETE FROM grouppeople WHERE member='$member' AND namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'";

	if ( mysqli_query($connect,$querydelete) == true ){

		$query =mysqli_query($connect,"SELECT * FROM grouppeople WHERE namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");
		if (mysqli_num_rows($query) < 1) {

			mysqli_query($connect,"DELETE FROM groupaddnew Where namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");
			// mysqli_query($connect,"DELETE FROM 
			// 	(SELECT T2.* FROM groupaddnew T1, grouppeople T2  
			// 	WHERE T2.namegroup = T1.namegroup
			// 	AND T2.useradd = T1.useradd 
			// 	AND T2.datetimeadd = T1.datetimeadd)");
			mysqli_query($connect,"DELETE FROM chatgroup WHERE namegroup = '$namegroup' AND useradd = '$useradd' AND datetimeadd = '$datetimeadd'");
		}

		echo "deletepeoplegroupsuccess";

	}else{
		echo "deletepeoplegrouperror";
	}
	

?>