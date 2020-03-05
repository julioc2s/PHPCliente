<?php


/**
 * Date 		:	27.11.2012
 * Description 	:	A sample php service to return json responses for android application
 * Author		: 	Guruparan Giritharan
 * 
 */

 //Get the name of the Method
 //The method name has to be passed as Method via post
 
 $Request_Method=$_REQUEST['method'] or die('Method name not found');
 
 //Connect to the database
 $Connection = mysql_connect("mysql04-farm86.kinghost.net","pcsystems04","nalbea01") or die('Cannot connect to Database');
 
 //Select the database
 mysql_select_db("pcsystems04") or die('Cannot select Database');
 
 //Method to verify the users login
 if($Request_Method=="verifyLogin")
 {
 	//username and password are password are passed via querystrings
 	$username=$_REQUEST['username'];
	$password=$_REQUEST['password'];
	
	//Generate the sql query based on username and password
	$query="select pkcodentregador from entregadores where nome='$username' and senha='$password'";
	
	//Execute the query
	$result = mysql_query($query);
	
	//Get the rowcount
	$rowcount= mysql_num_rows($result);
	
	//if the count is 0 then no matching rows are found
	if($rowcount==0)
	{
		echo json_encode(array('result'=>0));
	}
	//Else there is an employee with the given credentials
	else {
		$row = mysql_fetch_assoc($result);
		//Get and return his employee id
		echo json_encode(array('result'=>$row['pkcodentregador']));
	}
 }

 if($Request_Method=="getUserINFO")
 {
 	//username and password are password are passed via querystrings
 	$ID=$_REQUEST['ID'];
	
	//Generate the sql query based on username and password
	$query="select id, Username, Password, Name, Address, Manager from Employees where ID = " . $ID;
	
	//Execute the query
	$result = mysql_query($query);
	
	//Get the rowcount
	$rowcount= mysql_num_rows($result);
	
	
	
	//if the count is 0 then no matching rows are found
	if($rowcount==0)
	{
		echo json_encode(array('result'=>0));
	}
	//Else there is an employee with the given credentials
	else {
		$row = mysql_fetch_assoc($result);
		//Get and return his employee id
		echo json_encode(array('result'=>$row['id'] 
		. '-' . $row['Username'] 
		. '-' . $row['Password'] 
		. '-' . $row['Name'] 
		. '-' . $row['Address'] 
		. '-' . $row['Manager'] ));
	}
	
	
 }
  
  
 //Method to verify the users login
 if($Request_Method=="excluirRegistro")
 {
 	//username and password are password are passed via querystrings
 	$ID=$_REQUEST['ID'];
 	$IDAdmin=$_REQUEST['IDAdmin'];
	
	//Generate the sql query based on username and password
	$query="delete from Employees where ID = " . $ID . " and Manager = " . $IDAdmin;
	
	//Execute the query
	$result = mysql_query($query);
	
	if(! $result )
	{
  		die('Could not enter data: ' . mysql_error());
	}
	
    echo json_encode(array('result'=>1));

 }  
  
 //Method to verify the users login
 if($Request_Method=="gravarRegistro")
 {
 	//username and password are password are passed via querystrings
 	$username=$_REQUEST['username'];
	$password=$_REQUEST['password'];
	$nome=$_REQUEST['nome'];
	$endereco=$_REQUEST['endereco'];
	$IDAdmin=$_REQUEST['IDAdmin'];
	
	//Generate the sql query based on username and password
	$query="insert into Employees (Username,Password,Name,Address,Manager) values ('" . $username . "','" . $password . "','" . $nome . "','" . $endereco . "'," . $IDAdmin . ") ";
	
	//Execute the query
	$result = mysql_query($query);
	
	if(! $result )
	{
  		die('Could not enter data: ' . mysql_error());
	}
	
    echo json_encode(array('result'=>1));

 } 
 
 //Get all th employees that are managed the by the given emplyee
 if($Request_Method=="getEmployees")
 {
 	$id=$_REQUEST['id'];
	$query="select name,address, ID from Employees where manager=$id";

	$result = mysql_query($query);

	while($row = mysql_fetch_assoc($result))
	{
		$resultArray[] = $row;
	}

	echo json_encode($resultArray);
 }
 
   
 //Metodo para gravar a localizacao
 if($Request_Method=="gravarLocalizacao")
 {
 	//username and password are password are passed via querystrings
 	$id=$_REQUEST['id'];
	$latitude=$_REQUEST['latitude'];
	$longitude=$_REQUEST['longitude'];
	
	//Generate the sql query based on username and password
	
	$query="update entregadores set latitude = '" . $latitude . "'  , longitude = '" . $longitude . "'  where pkcodentregador = " . $id . "";
	
	//Execute the query
	$result = mysql_query($query);
	
	if(! $result )
	{
  		die('Could not enter data: ' . mysql_error());
	}
	
    echo json_encode(array('result'=>1));

 }  
 
 //Close Connection
 mysql_close($Connection);
?>

