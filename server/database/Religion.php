<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Religion.php');

  class Religion extends CRUD implements CRUDInterface{

      function __construct(QueryBuilder $query = null){
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

      function error_handler($errno, $errstr, $errfile, $errline){

      }
  }
?>
