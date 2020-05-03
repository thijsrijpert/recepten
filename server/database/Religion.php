<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Religion.php');

  class Religion extends CRUD{

      function __construct(QueryBuilder $query){
            $sql = "INSERT INTO Religion (name) VALUES (:name)";
            $this->stmt = \database\Database::getConnection()->prepare($sql);

            parent::__construct($query);
      }

      function insert(\model\Model $model) : String{
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Model $model) : String{

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
