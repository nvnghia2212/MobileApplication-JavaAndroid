<?php

require "./connect.php";


$emailuser 		= $_POST['emailUser'];
$search 		= $_POST['searchUser'];

// $emailuser 		= "abc@gmail.com";
// $search 		= " nghia@gmail.com";
$stringsearch = trim($search);

if (preg_match("/^[A-Za-z0-9_\.]{1,32}@([a-zA-Z0-9]{1,12})(\.[a-zA-Z]{1,12})+$/", $stringsearch)){

	// $queryselectemail = mysqli_query($connect,"SELECT * FROM (SELECT * FROM user a WHERE a.email LIKE '%$search%' UNION SELECT * FROM user b) c 
	// 	WHERE c.email !='$emailuser' AND c.email not in (select emailfrienduser from frienduser) 
	// 	UNION 
	// 	SELECT * FROM (SELECT * FROM user a WHERE a.email LIKE '%$search%' UNION SELECT * FROM user b) c 
	// 	WHERE c.email !='$emailuser' AND c.email in (select emailfrienduser from frienduser WHERE email != '$emailuser') ");

	$queryselectsearch = mysqli_query($connect,"SELECT * FROM (SELECT * FROM user a WHERE a.email LIKE '%$stringsearch%' UNION SELECT * FROM user b) c WHERE 
		c.email != '$emailuser' AND c.email not in (select emailfrienduser from frienduser WHERE email = '$emailuser') AND c.email not in (select emailfrienduser from requestfrienduser WHERE email = '$emailuser') "); // Mai coi lại chõ search

}else{

	

	$arrsearch = explode(' ', $stringsearch);
	$gn = $arrsearch[0];
	
	// $arr = explode(' ', $search);
	// cắt mảng

	$i = 0;
	$temp = $output = ""; 
// lặp qua các ký tự của chuỗi
	while ($char = @$stringsearch[$i++])
	{
    	if ($char == " "){ 
                // nếu là ký tự trắng
        	$out = " ".$temp.$output;
        	$temp = ""; 
    	} else {
        	$temp .= $char;
    	}   
	}
	$n = $temp.$output;
	

	$queryselectsearch = mysqli_query($connect,"SELECT * FROM (SELECT * FROM user a WHERE a.givenname LIKE '%gn%' AND a.name LIKE '%$n%' UNION SELECT * FROM user b) c WHERE c.email !='$emailuser' AND c.email not in (select emailfrienduser from frienduser WHERE email = '$emailuser') AND c.email not in (select emailfrienduser from requestfrienduser WHERE email = '$emailuser') ");

}

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
	while ($row = mysqli_fetch_assoc($queryselectsearch)){
		array_push($arrayUser, new User ($row['id'], $row['email'], $row['givenname'], $row['name'], $row['latitude'], $row['longitude'],$row['onloff']));
	}
	//4 Chuyển array -> JSON
	echo json_encode($arrayUser);

?>
