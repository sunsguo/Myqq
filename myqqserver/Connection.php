<?php
class Connection{
	//保存数据库连接对象
	private $conn;
	
    private   $DB_HOST = "127.0.0.1";
    private   $DB_NAME = "myqq";
    private   $DB_USER = "root";
    private   $DB_PASS = "mysqladmin";

	public function __construct(){
			//定义常量，连接数据库
        $dsn = "mysql:host=" . $this->DB_HOST . ";dbname=" . $this->DB_NAME;
        try{
            $this->conn = new PDO($dsn, $this->DB_USER, $this->DB_PASS);
        }catch(Exception $e){
            //如果数据库连接失败，输出错误信息
            die($e->getMessage());
        }
	}

    public function getConnection(){
        return $this->conn;
    }
}
