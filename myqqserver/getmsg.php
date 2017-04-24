<?php
$response = array();
include_once"Connection.php";

$connection = new Connection();       //获取数据库链接
$conn = $connection->getConnection();

if(!empty($_POST['user_name'])){
    $user_name = $_POST['user_name'];
    //查找最新的一条消息
    $sql = "select * from chat where id = (select max(id) from chat);";
    $statement = $conn->query($sql);
    $result = $statement->fetchAll(PDO::FETCH_ASSOC);
    if($result){
        $msg = $result[0];
        if($msg['name'] != $user_name){
            $response['success'] = 1;
            $response['user_name'] = $msg['name'];
            $response['message'] = $msg['content'];
            echo json_encode($response);
        }else{
            $response['success'] = 0;
            $response['message'] = "NO new messages";
            echo json_encode($response);
        }
    }else{
        $response['success'] = 0;
        $response['message'] = "Error connect";
        json_encode($response);
    }
}
