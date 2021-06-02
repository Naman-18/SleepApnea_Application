<?php
	$server = "localhost";
	$username = "root";
	$password = "";
	$dbname = "project";
	$conn = new mysqli($server,$username,$password,$dbname);
	if(mysqli_connect_errno()){
		die('Unable to connect to database'.mysqli_connect_error);
	}
	$sql = "Select Spo2,Bpm from sleep_apnea";
	$result = $conn->query($sql);
	$temp = array();
	$res = array();
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			$temp["Spo2"] = $row["Spo2"];
			$temp["Bpm"] = $row["Bpm"];
			array_push($res,$temp);
		}
		
	}
	echo json_encode($res);
	$conn->close();
?>