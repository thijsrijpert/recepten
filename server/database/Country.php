<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
  require_once(dirname(__FILE__, 1) . '/CRUInterface.php');require_once(dirname(__FILE__, 1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/model/Country.php');
  class Country extends CRUD implements CRUInterface{

    function __construct(QueryBuilder $query = null){
          $sql = "INSERT INTO Country (countrycode, name, description) VALUES (:countrycode, :name, :description)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);

          parent::__construct($query);
    }

      function insert(\model\Model $model) : String {
          $countrycode = $model->getCountrycode();
          $name = $model->getName();
          $description = $model->getDescription();
          $this->stmt->bindParam(':countrycode', $countrycode);
          $this->stmt->bindParam(':name', $name);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Model $model) : array{
        try{
            $this->select[0]->bindParam(':countrycode', $model->getCountrycode());
        }catch(\exception\ModelNullException $e){}

        try{
              $this->select[0]->bindParam(':name', $model->getName());
          }catch(\exception\ModelNullException $e){}

        try{
              $this->select[0]->bindParam(':description', $model->getDescription());
          }catch(\exception\ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Country');

        return array($this->select[0]->errorCode(), array($results));
      }

      function update(\model\Model $model, \model\Model $modelOld) : String{
        try{
            $this->update[0]->bindParam(':countrycodeUpdate', $model->getCountrycode());
        }catch(\exception\ModelNullException $e){
            throw new NullPointerException($e->getMessage());
        }

        try{
            $this->update[0]->bindParam(':countrycode', $modelOld->getCountrycode());
        }catch(\exception\ModelNullException $e){
            throw new NullPointerException($e->getMessage());
        }

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
            $this->update[0]->bindParam('descriptionUpdate', $model->getDescription());
        }catch(\exception\ModelNullException $e){
            throw new NullPointerException($e->getMessage());
        }

        try{
            $this->update[0]->bindParam(':description', $modelOld->getDescription());
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
