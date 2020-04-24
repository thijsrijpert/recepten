<?php
namespace database;
class Database extends \PDO{
    private static $instance = null;
    private $tijdvakStatement;

    public static function getConnection() : Database{
       if(self::$instance == null){
          $instance = new \database\Database();
       }
       return $instance;
    }

    public function __construct(){
      echo 'Connection';
        parent::__construct("mysql:host=localhost;dbname=beroepsproduct;charset=utf8", "crudBeroepsproduct", "gZQ0p8L3kR8vDVJ5");
    }
}
?>
