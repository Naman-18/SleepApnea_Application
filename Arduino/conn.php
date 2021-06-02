<?php

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "project";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("Database Connection failed: " . $conn->connect_error);
    }
	else{
		echo("Connection established!");
		echo "<br>";
	}
    
    

    if(empty(!$_GET['Spo2']) && empty(!$_GET['Bpm']))
    {
    	$oxygen = $_GET['Spo2'];
    	$bpm = $_GET['Bpm'];

	    $sql = "INSERT INTO sleep_apnea
		
		VALUES ('$oxygen','$bpm')";

		if ($conn->query($sql) === TRUE) {
		    echo "Record entered successfully!";
		} else {
		    echo "Error: " . $sql . "<br>" . $conn->error;
		}
	}
	else{
		echo("Empty");
	}


	$conn->close();
?>