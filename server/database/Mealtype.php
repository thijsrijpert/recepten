<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Mealtype.php');
  class Mealtype extends CRUD{

      function __construct(QueryBuilder $query = null){
          $sql = "INSERT INTO Mealtype (name) VALUES (:name)";
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

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Mealtype');

        return array($this->select[0]->errorCode(), array($results));
    }

    function update(\model\Model $model, \model\Model $modelOld) : String{
        try{
            $this->update[0]->bindParam(':nameUpdate', $model->getName());
        }catch(\exception\ModelNullException $e){
            throw new NullPointerException($e->getMessage());
        }

        try{
            $this->update[0]->bindParam(':name', $modelOld->getName());
        }catch(\exception\ModelNullException $e){
            throw new NullPointerException($e->getMessage());
        }

        $this->update[0]->execute();

        return $this->update[0]->errorCode();
    }


    function error_handler($errno, $errstr, $errfile, $errline){

    }
  }
?>
