<?php
//情动php会话
session_start();
//定义JSON响应数组
$response = array();

include_once("Connection.php");

$connection = new Connection();       //获取数据库链接
$conn = $connection->getConnection();

//判断是否获取到索取要的输入
if(!empty($_POST['user_name']) && !empty($_POST['user_password'])){
    $user_name = $_POST['user_name'];
    $user_password = $_POST['user_password'];
    //判断用户名是否占用
    $userNameSQL = "select * from user where name='$user_name'";
    try{
        $statement = $conn->prepare($userNameSQL);
        $statement->execute();
        //结果为二位数组
        $result = $statement->fetchAll(PDO::FETCH_ASSOC);
        $statement->closeCursor();   //关闭游标，使语句能再次执行
        if($result != null){
            $response['success'] = 0;
            $response['message'] = "name is useed";
            //返回json响应
            echo json_encode($response); 
            exit();
        }
        //数据库插入数据
        $result = $conn->exec("insert into user(name, password) values('$user_name', '$user_password')");
        //判断数据是否成功插入
        if($result){
            //数据成功插入
            $response['success'] = 1;
            $response['message'] = "Product successfully created.";
            echo json_encode($response);
        }else{
            //数据插入失败
            $response['success'] = 0;
            $response['message'] = "An error occurred.";
           echo json_encode($response);
        }
    }catch(Exception $exception){
        echo $exception->getMessage();
    }
}else{
    //没有输入参数
    $response['success'] = 0;
    $response['message'] = "Required field(s) is missing";
    echo json_encode($response);
}

