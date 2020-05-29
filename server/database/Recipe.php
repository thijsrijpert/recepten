<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
class Recipe extends CRUD{

  function __construct(QueryBuilder $query = null){
        $sql = "INSERT INTO Recipe (name, description, countrycode, username, mealtype_name, religion_id, time_of_day, isApproved )
        VALUES (:name , :description, :countrycode , :username , :mealtype_name , :religion_id , :time_of_day , :isApproved)";
        $this->stmt = \database\Database::getConnection()->prepare($sql);

        parent::__construct($query);
  }

  function insert(\model\Model $model) : String{
    $name = $model->getName();
    $description = $model->getDescription();
    $isApproved = $model->getIs_approved();
    $countrycode = $model->getCountrycode();
    $username = $model->getUsername();
    $mealtype_name = $model->getMealtype_name();
    $religion_id = $model->getReligion_id();
    $time_of_day = $model->getTime_of_day();
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':isApproved', $isApproved);
    $this->stmt->bindParam(':countrycode', $countrycode);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->bindParam(':mealtype_name', $mealtype_name);
    $this->stmt->bindParam(':religion_id', $religion_id);
    $this->stmt->bindParam(':time_of_day', $time_of_day);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

  function select(\model\Model $model) : array{

      try{
          $this->select[0]->bindParam(':id', $model->getId());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':name', $model->getName());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':description', $model->getDescription());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':isApproved', $model->getIs_approved());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':countrycode', $model->getCountrycode());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':username', $model->getUsername());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':mealtype_name', $model->getMealtype_name());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':religion_id', $model->getReligion_id());
      }catch(\exception\ModelNullException $e){}

      try{
          $this->select[0]->bindParam(':time_of_day', $model->getTime_of_day());
      }catch(\exception\ModelNullException $e){}




      $this->select[0]->execute();

      $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Recipe');

      return array($this->select[0]->errorCode(), array($results));
  }

    function update(){
      try{
          $this->select[0]->bindParam(':id', $model->getId());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':id', $modelOld->getId());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':name', $model->getName());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':name', $modelOld->getName());
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
          $this->select[0]->bindParam(':isApproved', $model->getIs_approved());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':isApproved', $modelOld->getIs_approved());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':countrycode', $model->getCountrycode());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':countrycode', $modelold->getCountrycode());
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

      try{
          $this->select[0]->bindParam(':mealtype_name', $model->getMealtype_name());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':mealtype_name', $modelOld->getMealtype_name());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':religion_id', $model->getReligion_id());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':religion_id', $modelOld->getReligion_id());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':time_of_day', $model->getTime_of_day());
      }catch(\exception\ModelNullException $e){
          throw new NullPointerException($e->getMessage());
      }

      try{
          $this->select[0]->bindParam(':time_of_day', $modelOld->getTime_of_day());
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
