<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/model/Religion.php');

  class Religion extends CRUD implements CRUInterface{

      function __construct(QueryBuilderParent ...$query){
            $sql = "INSERT INTO Religion (name) VALUES (:name)";
            $this->stmt = \database\Database::getConnection()->prepare($sql);
            parent::__construct($query);
      }

      function insert(\model\Model $model) : String{
          try{
            $name = $model->getName();
            $this->stmt->bindParam(':name', $name);
          }catch(ModelNullException $e){
              throw new NullPointerException($e->getMessage());
          }

          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Model $model) : array{
          try{
              $this->select[0]->bindParam(':name', $model->getName());
          }catch(\exception\ModelNullException $e){}

          try{
              $this->select[0]->bindParam(':id', $model->getId());
          }catch(\exception\ModelNullException $e){}

          $this->select[0]->execute();

          $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Religion');

          return array($this->select[0]->errorCode(), array($results));
      }

      function update(\model\Model $model, \model\Model $modelOld) : String {
          try{
              $this->update[0]->bindParam(':nameUpdate', $model->getName());
          }catch(\exception\ModelNullException $e){}

          try{
              $this->update[0]->bindParam(':idUpdate', $model->getId());
          }catch(\exception\ModelNullException $e){}

          try{
              $this->update[0]->bindParam(':name', $modelOld->getName());
          }catch(\exception\ModelNullException $e){}

          try{
              $this->update[0]->bindParam(':id', $modelOld->getId());
          }catch(\exception\ModelNullException $e){}

          $this->update[0]->execute();

          return $this->update[0]->errorCode();
      }

      function error_handler($errno, $errstr, $errfile, $errline){

      }
  }
?>
