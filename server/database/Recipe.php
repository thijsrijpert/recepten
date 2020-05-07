<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
class RecipeStatement extends CRUD{

  function __construct(QueryBuilder $query = null){
        $sql = "INSERT INTO Recipe (name, description, landcode, username, mealtype, religion_id, time_of_day, is_approved, )
        VALUES (:name , :description, :landcode , :username , :mealtype_name , :religion_id , :time_of_day , :is_approved)";
        $this->stmt = \database\Database::getConnection()->prepare($sql);

        parent::__construct($query);
  }

  function insert(\model\Model $model){
    $name = $model->getName();
    $description = $model->getDescription();
    $landcode = $model->getLandcode();
    $username = $model->getUsername();
    $mealtype_name = $model->getMealtype_name();
    $religion_id = $model->getReligion_id();
    $time_of_day = $model->gettimeofday();
    $is_approved = $model->getIs_approved();
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':landcode', $landcode);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->bindParam(':mealtype_name', $mealtype_name);
    $this->stmt->bindParam(':religion_id', $religion_id);
    $this->stmt->bindParam(':time_of_day', $time_of_day);
    $this->stmt->bindParam(':is_approved', $is_approved);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

  function select(\model\Model $model) : array{
      if(null != $model->getName()){
          $this->select[0]->bindParam(':name', $model->getName());
      }
      if(null != $model->getDescription()){
        $this->select[0]->bindParam(':description', $model->getDescription());
      }
      if(null != $model->getCountry_code()){
        $this->select[0]->bindParam(':name', $model->getCountry_code());
      }
      if(null != $model->getUsername()){
        $this->select[0]->bindParam(':username', $model->getUsername());
      }
      if(null != $model->getMealtype_name()){
        $this->select[0]->bindParam(':mealtype_name', $model->getMealtype_name());
      }
      if(null != $model->getReligion_id()){
        $this->select[0]->bindParam(':religion_id', $model->getReligion_id());
      }
      if(null != $model->gettimeofday()){
        $this->select[0]->bindParam(':time_of_day', $model->gettimeofday());
      }
      if(null != $model->getIs_approved()){
        $this->select[0]->bindParam(':is_approved', $model->getIs_approved());
      }


      $this->select[0]->execute();

      $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Recipe');

      return array($this->select[0]->errorCode(), array($results));
  }

  function error_handler($errno, $errstr, $errfile, $errline){

  }

}

 ?>
