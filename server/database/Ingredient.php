<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__, 1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/model/Ingredient.php');
class Ingredient extends CRUD{

  function __construct(QueryBuilderParent ...$query){
        $sql = "INSERT INTO Ingredient (name, description, is_approved, username) VALUES (:name , :description,
        :is_approved, :username)";
        $this->stmt = \database\Database::getConnection()->prepare($sql);

        parent::__construct($query);
  }

  function insert(\model\Model $model) : String{
    $name = $model->getName();
    $description = $model->getDescription();
    $is_approved = $model->getIs_approved();
    $username = $model->getUsername();
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':is_approved', $is_approved);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->execute();

    return $this->stmt->errorCode();

  }

  function select(\model\Model $model) : array{
    try{
        $this->select[0]->bindParam(':name', $model->getName());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->select[0]->bindParam(':description', $model->getDescription());
    }catch(\exception\ModelNullException $e){}

    try{
        $this->select[0]->bindParam(':is_approved', $model->getIs_approved());
    }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':username', $model->getUsername());
      }catch(\exception\ModelNullException $e){}

    $this->select[0]->execute();

    $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Ingredient');

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

    try{
        $this->select[0]->bindParam(':description', $model->getDescription());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':description', $modelOld->getDescription());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':username', $model->getUsername());
    }catch(\exception\ModelNullException $e){
        throw new NullPointerException($e->getMessage());
    }

    try{
        $this->select[0]->bindParam(':username', $modelOld->getUsername());
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
