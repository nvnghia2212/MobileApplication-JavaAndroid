<?php

require "./connect.php";

$emailsend			= $_POST['emailSend'];
$emailreceived		= $_POST['emailReceived'];

// $emailsend			= "nvnghia2212@gmail.com";
// $emailreceived		= "nghia@gmail.com";

// $emailsend			= "nghia@gmail.com";
// $emailreceived		= "nvnghia2212@gmail.com";


$query = mysqli_query($connect,"SELECT * FROM messenger a WHERE (a.emailsend ='$emailsend' AND a.emailreceived = '$emailreceived') 
	OR (a.emailsend ='$emailreceived' AND a.emailreceived = '$emailsend') ");

//1 Tạo class
class SendMessenger{
	function SendMessenger($id, $emailsend, $textmess, $emailreceived, $datemess, $timemess){
		$this -> ID = $id;
		$this -> EmailSend = $emailsend;
		$this -> TextMess = $textmess;
		$this -> EmailReceived = $emailreceived;
		$this -> DateMess = $datemess;
		$this -> TimeMess = $timemess;
	}
}
//2 Tạo mảng
$arrayMess = array();
//3 Thêm phần tử vào mảng
while ($row = mysqli_fetch_assoc($query)){
	array_push($arrayMess, new SendMessenger ($row['id'], $row['emailsend'], $row['textmess'], $row['emailreceived'], $row['datemess'], $row['timemess']));
}
//4 Chuyển array -> JSON
echo json_encode($arrayMess);

?>