<?php
$con=mysqli_connect("127.0.0.1:3306","langeNacht","nacht","langenacht");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql="INSERT INTO Spieler (idSpieler, nachname, vorname)
VALUES
('$_POST[firstname]','$_POST[lastname]','$_POST[age]')";

if (!mysqli_query($con,$sql))
  {
  die('Error: ' . mysqli_error($con));
  }
echo "1 record added";

mysqli_close($con);
?> 