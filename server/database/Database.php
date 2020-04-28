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
        //parent::__construct("mysql:host=192.168.178.200;dbname=beroepsproduct;charset=utf8", "devThijs", "E8uwDmH$*H%m8Q9g");
    }
}
?>
