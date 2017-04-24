<?php
session_start();
$response = array();
include_once"Connection.php";

$connection = new Connection();
$conn = $connection->getConnection();

if(!empty($_POST['send_content'])){
    $user_name = $_SESSION['user_name'];
    $send_content = $_POST['send_content'];
    //插入数据库
    $result = $conn->exec("insert into chat(name, content) values('$user_name', '$send_content')");
    //判断是否插入成功
    if($result){
        $response['success'] = 1;
        $response['message'] = "Product successfully created.";
        echo json_encode($response);
    }else{
        $response['success'] = 0;
        $response['message'] = "An error occurred.";
        echo json_encode($response);
    }

}