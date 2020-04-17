<?php
class Database extends PDO{
    private static $instance = null;
    private $tijdvakStatement;

    public static function getConnection() : PDO{
       if(self::$instance == null){
          $instance = new Database();
       }
       return $instance;
    }

    private function __construct(){
        parent::__construct("mysql:host=127.0.0.1;dbname=beroepsproduct;charset=utf8", "crudBeroepsproduct", "YQyQiRwHGGL1i6Bd");
    }
}
?>
