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

    public function __construct(){
      echo 'Connection';
        parent::__construct("mysql:host=localhost;dbname=beroepsproduct;charset=utf8", "crudBeroepsproduct", "gZQ0p8L3kR8vDVJ5");
    }
}
?>
