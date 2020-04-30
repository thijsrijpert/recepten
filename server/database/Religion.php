<?php
namespace database;
  require_once('../database/Database.php');
  require_once('../model/Religion.php');

  class Religion extends CRUD{

      function __construct(Query $query){
            $sql = "INSERT INTO Religion (name) VALUES (:name)";
            $this->stmt = \database\Database::getConnection()->prepare($sql);

            parent::__construct($query);
      }

      function insert(Model $model) : String{
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Religion $model) {

          if($model->getName() != null){
              $this->select[0]->bindParam(':name', $model->getName());
          }else if($model->getId()){
              $this->select[0]->bindParam(':id', $model->getId());
          }

          $this->select[0]->execute();

          $model = $this->select[0]->fetchObject(\model\Religion::class);

          return $stmt->errorCode();

      }
  }
?>
