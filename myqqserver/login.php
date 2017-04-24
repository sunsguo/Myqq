<?php
session_start();
$response = array();

include_once("Connection.php");
$connection  = new Connection();
$conn = $connection->getConnection();

if(!empty($_POST['user_name']) && !empty($_POST['user_password'])){
    $user_name = $_POST['user_name'];
    $user_password = $_POST['user_password'];
    //判断用户是否登记
    $sql = "select * from user where name='$user_name' and password='$user_password'";
    $statement = $conn->query($sql);
    $result = $statement->fetchAll(PDO::FETCH_ASSOC);
    if($result){
        $_SESSION['user_name'] = $result[0]['name'];
        $_SESSION['user_password'] = $result[0]['password'];
        $response['success'] = 1;
        $response['message'] = "Welcome login";
        echo json_encode($response);
    }else{
        $response['success'] = 0;
        $response['message'] = "Wrong user name or password";
        echo json_encode($response);
    }
}else{
    $response['success'] = 0;
        $response['message'] = "Plase enter name or password";
        echo json_encode($response);
}