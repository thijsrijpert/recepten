<?php
namespace database;
  require_once('../database/Database.php');
  require_once('../model/Religion.php');

  class Religion {

      private $stmt;

      function __construct($queryType = 'insert'){
          if('insert'){
            $sql = "INSERT INTO Religion (name) VALUES (:name)";
            $this->stmt = \database\Database::getConnection()->prepare($sql);
          }
      }

      function insert($model) {
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(array $where = array(), \model\Religion &$model) {
          $sql = " SELECT *
                  FROM Religion
                  WHERE name = :name or :name is null
                  ";
          if($where != array()){
              $sql .= " WHERE ";
              for($i = 0, $i <= count($where) -1; $i++;){
                  if($i != count($where) - 1){
                      $sql .= $where[$i][0] . " = " . $where[$i][2] . " AND ";
                  }else{
                      $sql .= $where[$i][0] . " = " . $where[$i][2];
                  }

              }
              echo $sql;
              $stmt = \database\Database::getConnection()->query($sql);

              $model = $stmt->fetchObject(\model\Religion::class);

              return $stmt->errorCode();

          }
      }
  }
?>
